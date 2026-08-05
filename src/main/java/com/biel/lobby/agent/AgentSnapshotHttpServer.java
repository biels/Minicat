package com.biel.lobby.agent;

import com.biel.lobby.lobby;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public final class AgentSnapshotHttpServer {
    public static final String SNAPSHOT_PATH = "/minicat/agent/v1/snapshot";
    public static final String HEALTH_PATH = "/minicat/agent/v1/health";
    private static final int MAX_QUERY_BYTES = 2048;
    private static final int MAX_RESPONSE_BYTES = 1_048_576;

    private final lobby plugin;
    private final Map<String, InstanceSnapshots> snapshotsByInstance = new ConcurrentHashMap<>();
    private final String serverSessionId;
    private HttpServer httpServer;
    private ThreadPoolExecutor executor;
    private byte[] expectedAuthorization;

    public AgentSnapshotHttpServer(lobby plugin) {
        this.plugin = plugin;
        long processId = ProcessHandle.current().pid();
        long startedAt = ProcessHandle.current().info().startInstant().orElse(Instant.now()).toEpochMilli();
        serverSessionId = "bcn-paper-" + processId + "-" + startedAt;
    }

    public void start() {
        if (!Boolean.parseBoolean(System.getProperty("minicat.agent-http.enabled", "false"))) {
            plugin.getLogger().info("Minicat agent snapshot endpoint is disabled.");
            return;
        }
        try {
            String bindAddress = System.getProperty("minicat.agent-http.bind", "127.0.0.1").trim();
            InetAddress address = InetAddress.getByName(bindAddress);
            if (!address.isLoopbackAddress()) {
                throw new IllegalArgumentException("Agent snapshot endpoint must bind to a loopback address");
            }
            int port = parsePort(System.getProperty("minicat.agent-http.port", "8765"));
            Path tokenPath = configuredTokenPath();
            String token = Files.readString(tokenPath, StandardCharsets.UTF_8).trim();
            if (token.length() < 32 || token.length() > 512) {
                throw new IllegalArgumentException("Agent HTTP token must contain 32 to 512 characters");
            }
            expectedAuthorization = ("Bearer " + token).getBytes(StandardCharsets.UTF_8);

            httpServer = HttpServer.create(new InetSocketAddress(address, port), 32);
            httpServer.createContext(HEALTH_PATH, this::handleHealth);
            httpServer.createContext(SNAPSHOT_PATH, this::handleSnapshot);
            AtomicInteger threadNumber = new AtomicInteger();
            executor = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(32), runnable -> {
                Thread thread = new Thread(runnable,
                        "minicat-agent-http-" + threadNumber.incrementAndGet());
                thread.setDaemon(true);
                return thread;
            }, new ThreadPoolExecutor.AbortPolicy());
            httpServer.setExecutor(executor);
            httpServer.start();
            plugin.getLogger().info("Minicat agent snapshot endpoint listening on "
                    + address.getHostAddress() + ":" + httpServer.getAddress().getPort());
        } catch (Exception exception) {
            stop();
            plugin.getLogger().log(Level.SEVERE, "Could not start Minicat agent snapshot endpoint", exception);
        }
    }

    public void stop() {
        snapshotsByInstance.clear();
        if (httpServer != null) {
            httpServer.stop(0);
            httpServer = null;
        }
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
        expectedAuthorization = null;
    }

    public boolean isRunning() {
        return httpServer != null;
    }

    public String getServerSessionId() {
        return serverSessionId;
    }

    public void publish(String instanceId, Map<UUID, PublishedSnapshot> playerSnapshots) {
        if (!isRunning()) return;
        snapshotsByInstance.put(normalizeInstance(instanceId),
                new InstanceSnapshots(instanceId, Map.copyOf(playerSnapshots)));
    }

    public void unpublish(String instanceId) {
        snapshotsByInstance.remove(normalizeInstance(instanceId));
    }

    private void handleHealth(HttpExchange exchange) throws IOException {
        try {
            if (!prepareAuthorizedGet(exchange)) return;
            String xml = "<mcAgentHealth schema=\"1.0\" status=\"ok\" serverSessionId=\""
                    + escapeXml(serverSessionId) + "\" publishedInstances=\""
                    + snapshotsByInstance.size() + "\" ts=\"" + Instant.now() + "\"/>\n";
            sendXml(exchange, 200, xml);
        } catch (Exception exception) {
            plugin.getLogger().log(Level.WARNING, "Agent health request failed", exception);
            sendError(exchange, 500, "INTERNAL_ERROR", "Request failed");
        } finally {
            exchange.close();
        }
    }

    private void handleSnapshot(HttpExchange exchange) throws IOException {
        try {
            if (!prepareAuthorizedGet(exchange)) return;
            Map<String, String> query = parseQuery(exchange.getRequestURI().getRawQuery());
            String instanceId = required(query, "instance");
            String playerIdentifier = required(query, "player");
            if (query.size() != 2) {
                throw new BadRequestException("Only instance and player query parameters are supported");
            }

            InstanceSnapshots instance = snapshotsByInstance.get(normalizeInstance(instanceId));
            if (instance == null) {
                sendError(exchange, 404, "INSTANCE_NOT_FOUND", "Unknown or unpublished game instance");
                return;
            }
            PublishedSnapshot snapshot = findPlayerSnapshot(instance, playerIdentifier);
            if (snapshot == null) {
                sendError(exchange, 404, "PLAYER_NOT_FOUND", "Player is not published in this instance");
                return;
            }
            sendXml(exchange, 200, snapshot.xml());
        } catch (BadRequestException exception) {
            sendError(exchange, 400, "BAD_REQUEST", exception.getMessage());
        } catch (Exception exception) {
            plugin.getLogger().log(Level.WARNING, "Agent snapshot request failed", exception);
            sendError(exchange, 500, "INTERNAL_ERROR", "Request failed");
        } finally {
            exchange.close();
        }
    }

    private boolean prepareAuthorizedGet(HttpExchange exchange) throws IOException {
        InetAddress remoteAddress = exchange.getRemoteAddress().getAddress();
        if (remoteAddress == null || !remoteAddress.isLoopbackAddress()) {
            sendError(exchange, 403, "LOOPBACK_REQUIRED", "Endpoint is restricted to loopback clients");
            return false;
        }
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().set("Allow", "GET");
            sendError(exchange, 405, "METHOD_NOT_ALLOWED", "Only GET is supported");
            return false;
        }
        String contentLength = exchange.getRequestHeaders().getFirst("Content-Length");
        if ((contentLength != null && !"0".equals(contentLength))
                || exchange.getRequestHeaders().containsKey("Transfer-Encoding")) {
            sendError(exchange, 400, "REQUEST_BODY_NOT_ALLOWED", "GET requests must not contain a body");
            return false;
        }
        String authorization = exchange.getRequestHeaders().getFirst("Authorization");
        byte[] received = authorization == null ? new byte[0] : authorization.getBytes(StandardCharsets.UTF_8);
        if (expectedAuthorization == null || !MessageDigest.isEqual(expectedAuthorization, received)) {
            Headers headers = exchange.getResponseHeaders();
            headers.set("WWW-Authenticate", "Bearer");
            sendError(exchange, 401, "UNAUTHORIZED", "A valid bearer token is required");
            return false;
        }
        return true;
    }

    private PublishedSnapshot findPlayerSnapshot(InstanceSnapshots instance, String identifier) {
        try {
            PublishedSnapshot byUuid = instance.playerSnapshots().get(UUID.fromString(identifier));
            if (byUuid != null) return byUuid;
        } catch (IllegalArgumentException ignored) {
            // Name lookup below is a convenience; UUID remains the canonical identifier.
        }
        PublishedSnapshot match = null;
        for (PublishedSnapshot candidate : instance.playerSnapshots().values()) {
            if (!candidate.playerName().equalsIgnoreCase(identifier)) continue;
            if (match != null) return null;
            match = candidate;
        }
        return match;
    }

    private Map<String, String> parseQuery(String rawQuery) throws BadRequestException {
        if (rawQuery == null || rawQuery.isBlank()) throw new BadRequestException("Query is required");
        if (rawQuery.getBytes(StandardCharsets.UTF_8).length > MAX_QUERY_BYTES) {
            throw new BadRequestException("Query is too large");
        }
        Map<String, String> values = new LinkedHashMap<>();
        for (String pair : rawQuery.split("&", -1)) {
            int separator = pair.indexOf('=');
            if (separator <= 0) throw new BadRequestException("Malformed query parameter");
            String key = decode(pair.substring(0, separator));
            String value = decode(pair.substring(separator + 1));
            if (key.isBlank() || value.isBlank()) throw new BadRequestException("Empty query parameter");
            if (values.putIfAbsent(key, value) != null) {
                throw new BadRequestException("Duplicate query parameter: " + key);
            }
        }
        return values;
    }

    private String decode(String value) throws BadRequestException {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException("Invalid URL encoding");
        }
    }

    private String required(Map<String, String> query, String key) throws BadRequestException {
        String value = query.get(key);
        if (value == null) throw new BadRequestException("Missing query parameter: " + key);
        return value;
    }

    private void sendError(HttpExchange exchange, int status, String code, String message) throws IOException {
        sendXml(exchange, status, "<mcError schema=\"1.0\" status=\"" + status + "\" code=\""
                + escapeXml(code) + "\" message=\"" + escapeXml(message) + "\"/>\n");
    }

    private void sendXml(HttpExchange exchange, int status, String xml) throws IOException {
        byte[] body = xml.getBytes(StandardCharsets.UTF_8);
        if (body.length > MAX_RESPONSE_BYTES) {
            status = 500;
            body = "<mcError schema=\"1.0\" status=\"500\" code=\"RESPONSE_TOO_LARGE\" message=\"Response exceeds the configured limit\"/>\n"
                    .getBytes(StandardCharsets.UTF_8);
        }
        Headers headers = exchange.getResponseHeaders();
        headers.set("Content-Type", "application/xml; charset=utf-8");
        headers.set("Cache-Control", "no-store");
        headers.set("X-Content-Type-Options", "nosniff");
        exchange.sendResponseHeaders(status, body.length);
        exchange.getResponseBody().write(body);
    }

    private Path configuredTokenPath() {
        String configured = System.getProperty("minicat.agent-http.token-file", "").trim();
        return configured.isEmpty()
                ? plugin.getDataFolder().toPath().resolve("agent-http-token.txt")
                : Path.of(configured);
    }

    private int parsePort(String value) {
        int port = Integer.parseInt(value);
        if (port < 1 || port > 65535) throw new IllegalArgumentException("Invalid agent HTTP port");
        return port;
    }

    private String normalizeInstance(String instanceId) {
        return instanceId.toLowerCase(Locale.ROOT);
    }

    private static String escapeXml(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
    }

    public record PublishedSnapshot(UUID playerId, String playerName, String snapshotId,
                                    Instant capturedAt, String xml) {}

    private record InstanceSnapshots(String instanceId, Map<UUID, PublishedSnapshot> playerSnapshots) {}

    private static final class BadRequestException extends Exception {
        private BadRequestException(String message) {
            super(message);
        }
    }
}
