package com.biel.lobby.status;

import com.biel.lobby.Mapa;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.MapaResetejable.MapMode;
import com.biel.lobby.status.LiveStatusDocument.InstanceEntry;
import com.biel.lobby.status.LiveStatusDocument.PlayerEntry;
import com.biel.lobby.status.LiveStatusDocument.ServerState;
import com.biel.lobby.utilities.data.DataAPI;
import com.biel.lobby.utilities.data.ForwardedAccount;
import com.biel.lobby.utilities.data.MatchData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Publishes the live status projection: a cache of the present, never read as history.
 * Everything is captured on the main thread; PostgreSQL is written by DataAPI's own writer.
 */
public final class LiveStatusPublisher implements Listener {
    private static final long CAPTURE_INTERVAL_TICKS = 100;
    private static final Duration HEARTBEAT_INTERVAL = Duration.ofSeconds(30);
    private static final long STOP_WAIT_MILLIS = 3000;

    /** Who a connected player is, resolved once when they joined. */
    private record Presence(int playerId, String name, Instant onlineSince) { }

    private final lobby plugin;
    private final DataAPI dataAPI;
    private final UUID serverSession = UUID.randomUUID();
    private final Map<UUID, Presence> presenceByPlayer = new HashMap<>();
    private BukkitTask captureTask;
    private String lastPublishedComparableJson;
    private Instant lastPublishedAt;
    private boolean captureFailureLogged;

    public LiveStatusPublisher(lobby plugin, DataAPI dataAPI) {
        this.plugin = plugin;
        this.dataAPI = dataAPI;
    }

    public void start() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        for (Player player : Bukkit.getOnlinePlayers()) recordPresence(player);
        captureTask = Bukkit.getScheduler().runTaskTimer(plugin, this::publishWhenDue, CAPTURE_INTERVAL_TICKS, CAPTURE_INTERVAL_TICKS);
    }

    /** Publishes that the server is gone; call before the database connection is closed. */
    public void stop() {
        if (captureTask != null) captureTask.cancel();
        captureTask = null;
        HandlerList.unregisterAll(this);
        presenceByPlayer.clear();
        String stoppedJson = LiveStatusDocument.stopped(serverSession).toJson(Instant.now());
        dataAPI.publishLiveStatusAndWait(serverSession, stoppedJson, STOP_WAIT_MILLIS);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        recordPresence(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        presenceByPlayer.remove(event.getPlayer().getUniqueId());
    }

    private void recordPresence(Player player) {
        int playerId = ForwardedAccount.required()
                ? ForwardedAccount.of(player).playerId()
                : dataAPI.getPlayerId(player.getName());
        if (playerId < 1) {
            plugin.getLogger().warning("Live status: no account id for " + player.getName() + "; they are left out of it.");
            return;
        }
        presenceByPlayer.put(player.getUniqueId(), new Presence(playerId, player.getName(), Instant.now()));
    }

    private void publishWhenDue() {
        try {
            Instant capturedAt = Instant.now();
            LiveStatusDocument document = capture();
            String comparableJson = document.toComparableJson();
            boolean changed = !comparableJson.equals(lastPublishedComparableJson);
            boolean heartbeatDue = lastPublishedAt == null || Duration.between(lastPublishedAt, capturedAt).compareTo(HEARTBEAT_INTERVAL) >= 0;
            if (!changed && !heartbeatDue) return;
            dataAPI.publishLiveStatus(serverSession, document.toJson(capturedAt));
            lastPublishedComparableJson = comparableJson;
            lastPublishedAt = capturedAt;
            captureFailureLogged = false;
        } catch (RuntimeException failure) {
            if (!captureFailureLogged) plugin.getLogger().log(Level.WARNING, "Live status capture failed", failure);
            captureFailureLogged = true;
        }
    }

    private LiveStatusDocument capture() {
        List<PlayerEntry> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Presence presence = presenceByPlayer.get(player.getUniqueId());
            if (presence == null) continue;
            Mapa map = plugin.gest.getMapWherePlayerIs(player);
            String place = map instanceof Joc ? map.getMapName() : null;
            players.add(new PlayerEntry(presence.playerId(), presence.name(), place, presence.onlineSince()));
        }
        List<InstanceEntry> instances = new ArrayList<>();
        for (Joc game : plugin.gest.getAllGameInstances()) {
            if (game.getWorld() == null) continue;
            instances.add(captureInstance(game));
        }
        return new LiveStatusDocument(serverSession, ServerState.RUNNING, players, instances);
    }

    private InstanceEntry captureInstance(Joc game) {
        List<Player> playing = game.getPlayers();
        List<Player> spectating = game.getSpectators();
        Set<String> addresses = new HashSet<>();
        for (Player viewer : playing) addAddress(addresses, viewer);
        for (Player viewer : spectating) addAddress(addresses, viewer);
        String map = game.getMapMode() == MapMode.MULTIPLE ? game.getMultiMapName() : game.getGameName();
        MatchData match = game.getMatchData();
        Integer matchId = match == null || match.getId() < 1 ? null : match.getId();
        return new InstanceEntry(game.getMapName(), game.getGameName(), map, game.getGameState().name(), matchId,
                game.getStartInstant(), playerIds(playing), playerIds(spectating), addresses.size());
    }

    private static void addAddress(Set<String> addresses, Player player) {
        String address = Joc.numericAddressOf(player);
        if (address != null) addresses.add(address);
    }

    private List<Integer> playerIds(List<Player> online) {
        List<Integer> playerIds = new ArrayList<>();
        for (Player player : online) {
            Presence presence = presenceByPlayer.get(player.getUniqueId());
            if (presence != null) playerIds.add(presence.playerId());
        }
        return playerIds;
    }
}
