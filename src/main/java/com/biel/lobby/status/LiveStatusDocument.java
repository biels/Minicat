package com.biel.lobby.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * The live status projection, schema 1: who is online and what each instance is doing.
 * It holds no positions, inventories, chat or addresses. Lists are kept in a fixed order so
 * that an unchanged server serializes to the same text.
 */
public record LiveStatusDocument(UUID serverSession, ServerState state, List<PlayerEntry> players, List<InstanceEntry> instances) {
    private static final int SCHEMA = 1;

    public enum ServerState { RUNNING, STOPPED }

    /** @param place the world name of the game instance the player is in; null in the lobby */
    public record PlayerEntry(int playerId, String name, String place, Instant onlineSince) { }

    /**
     * @param matchId the match_history id of the started match; null before it starts or without a database
     * @param startedAt when the match started; null before it does
     * @param connections the number of distinct network addresses among players and spectators
     */
    public record InstanceEntry(String world, String game, String map, String state, Integer matchId, Instant startedAt,
                                List<Integer> players, List<Integer> spectators, int connections) {
        public InstanceEntry {
            players = players.stream().sorted().toList();
            spectators = spectators.stream().sorted().toList();
        }
    }

    public LiveStatusDocument {
        players = players.stream().sorted(Comparator.comparingInt(PlayerEntry::playerId)).toList();
        instances = instances.stream().sorted(Comparator.comparing(InstanceEntry::world)).toList();
    }

    /** What the server publishes as it shuts down: nobody is online and nothing is running. */
    public static LiveStatusDocument stopped(UUID serverSession) {
        return new LiveStatusDocument(serverSession, ServerState.STOPPED, List.of(), List.of());
    }

    public String toJson(Instant capturedAt) {
        return toJsonObject(capturedAt).toString();
    }

    /** The document without its capture time, to tell whether anything changed since the last publication. */
    public String toComparableJson() {
        return toJsonObject(null).toString();
    }

    private JsonObject toJsonObject(Instant capturedAt) {
        JsonObject document = new JsonObject();
        document.addProperty("schema", SCHEMA);
        document.addProperty("serverSession", serverSession.toString());
        if (capturedAt != null) document.addProperty("capturedAt", timestamp(capturedAt));
        document.addProperty("state", state.name().toLowerCase(Locale.ROOT));
        JsonArray playerArray = new JsonArray();
        for (PlayerEntry player : players) {
            JsonObject entry = new JsonObject();
            entry.addProperty("playerId", player.playerId());
            entry.addProperty("name", player.name());
            entry.addProperty("place", player.place());
            entry.addProperty("onlineSince", timestamp(player.onlineSince()));
            playerArray.add(entry);
        }
        document.add("players", playerArray);
        JsonArray instanceArray = new JsonArray();
        for (InstanceEntry instance : instances) {
            JsonObject entry = new JsonObject();
            entry.addProperty("world", instance.world());
            entry.addProperty("game", instance.game());
            entry.addProperty("map", instance.map());
            entry.addProperty("state", instance.state());
            entry.addProperty("matchId", instance.matchId());
            entry.addProperty("startedAt", instance.startedAt() == null ? null : timestamp(instance.startedAt()));
            entry.add("players", idArray(instance.players()));
            entry.add("spectators", idArray(instance.spectators()));
            entry.addProperty("connections", instance.connections());
            instanceArray.add(entry);
        }
        document.add("instances", instanceArray);
        return document;
    }

    private static JsonArray idArray(List<Integer> playerIds) {
        JsonArray array = new JsonArray();
        playerIds.forEach(array::add);
        return array;
    }

    private static String timestamp(Instant instant) {
        return instant.truncatedTo(ChronoUnit.SECONDS).toString();
    }
}
