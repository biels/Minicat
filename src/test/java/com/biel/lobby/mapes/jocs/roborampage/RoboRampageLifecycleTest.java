package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.bukkit.plugin.PluginManager;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.jocs.roborampage.utils.TeamLives;

/** Actual game hooks, with shared spectator UI and persistence replaced by fixture boundaries. */
class RoboRampageLifecycleTest {
    @BeforeAll static void server() throws Exception {
        PluginManager plugins = stub(PluginManager.class, (method, args) -> null);
        Field server = Bukkit.class.getDeclaredField("server");
        server.setAccessible(true);
        server.set(null, stub(Server.class, (method, args) ->
                method.equals("getPluginManager") ? plugins : null));
    }

    @Test void finalLifeIsExcludedImmediatelyAndBecomesSpectatorAfterRespawn() throws Exception {
        Fixture f = new Fixture();
        for (int death = 0; death < 2; death++) {
            assertTrue(f.lives.die(f.id));
            f.game.onPlayerRespawnAfterTick(null, f.player);
        }
        assertTrue(f.lives.die(f.id));
        assertFalse(f.game.getPlayers().contains(f.player), "elimination fences gameplay before respawn");
        assertTrue(f.game.accepts(f.player), "respawn event must remain deliverable before the spectator role");
        f.game.onPlayerRespawnAfterTick(null, f.player);
        assertEquals(GameMode.SPECTATOR, f.mode);
        assertTrue(f.game.isSpectator(f.player));
        assertFalse(f.game.accepts(f.player), "shared event verification rejects the spectator");
        assertFalse(f.lives.canPlay(f.id));
    }

    @Test void survivingRespawnKeepsEquipmentAndUnlocksTheNextLifeConsumption() throws Exception {
        Fixture f = new Fixture();
        assertFalse(f.game.getResetPlayerOnRespawn());
        assertTrue(f.lives.die(f.id));
        f.game.onPlayerRespawnAfterTick(null, f.player);
        assertEquals(2, f.lives.remaining(f.id));
        assertTrue(f.game.getPlayers().contains(f.player));
        assertFalse(f.game.isSpectator(f.player));
        assertEquals(0, f.equipmentChanges, "respawn must preserve upgraded equipment");
        assertTrue(f.lives.die(f.id), "the real respawn hook clears duplicate-death suppression");
        assertEquals(1, f.lives.remaining(f.id));
    }

    @Test void eliminatedReconnectCannotRestoreLivesOrActiveParticipation() throws Exception {
        Fixture f = new Fixture();
        f.lives.abandon(f.id);
        f.game.onSeatResumed(f.player);
        assertTrue(f.game.isSpectator(f.player));
        assertEquals(0, f.lives.remaining(f.id));
        assertFalse(f.game.getPlayers().contains(f.player));
        f.game.onSeatResumed(f.player);
        assertEquals(1, f.game.spectatorTransitions);
    }

    @Test void graceExpiryRetiresOfflineRosterAndEndsMatchOnce() throws Exception {
        Fixture f = new Fixture();
        f.game.viewers.clear(); // No live Player object is needed when reconnect grace expires.
        var seat = f.game.getSeats().getFirst();
        f.game.onSeatAbandoned(seat, new ArrayList<>());
        assertTrue(f.lives.defeated());
        assertEquals(0, f.lives.remaining(f.id));
        assertEquals(1, f.game.finalizations);
        assertEquals(200, f.game.resetDelay);
        f.game.onSeatAbandoned(seat, new ArrayList<>());
        assertEquals(1, f.game.finalizations);
    }

    @Test void deliberateLeaveRetiresParticipantWithoutCreatingNewLives() throws Exception {
        Fixture f = new Fixture();
        f.game.viewers.clear(); // A world-change leave happens after the player leaves the arena.
        f.game.customLeave(f.player, new ArrayList<>());
        assertEquals(0, f.lives.remaining(f.id));
        assertEquals(1, f.game.finalizations);
        f.game.customLeave(f.player, new ArrayList<>());
        assertEquals(1, f.game.finalizations);
    }

    private static final class TestGame extends RoboRampage {
        final ArrayList<Player> viewers = new ArrayList<>();
        final Set<UUID> spectators = new HashSet<>();
        int spectatorTransitions, finalizations, resetDelay;
        TestGame(World world) { this.world = world; JocIniciat = true; }
        @Override public ArrayList<Player> getViewers() { return new ArrayList<>(viewers); }
        @Override public Boolean isSpectator(Player player) { return spectators.contains(player.getUniqueId()); }
        @Override public void addSpectator(Player player) {
            spectators.add(player.getUniqueId());
            spectatorTransitions++;
            player.setGameMode(GameMode.SPECTATOR);
        }
        @Override public double getPunishForLeaving() { return 0; }
        @Override public void JocFinalitzat() { JocFinalitzat = true; finalizations++; }
        @Override public void planificarReseteig(int ticks) { resetDelay = ticks; }
        boolean accepts(Player player) { return verifyEvent(new PlayerToggleFlightEvent(player, true)); }
    }

    private static final class Fixture {
        final UUID id = UUID.randomUUID();
        final World world = stub(World.class, (method, args) -> null);
        final TestGame game = new TestGame(world);
        GameMode mode = GameMode.SURVIVAL;
        int equipmentChanges;
        final Player player = stub(Player.class, (method, args) -> switch (method) {
            case "getUniqueId" -> id;
            case "getName" -> id.toString();
            case "getWorld" -> world;
            case "isOnline" -> true;
            case "getGameMode" -> mode;
            case "setGameMode" -> { mode = (GameMode) args[0]; yield null; }
            case "getInventory", "teleport", "removePotionEffect" -> {
                equipmentChanges++;
                throw new AssertionError("Surviving respawn must preserve equipment");
            }
            default -> null;
        });
        final TeamLives lives;
        Fixture() throws Exception {
            game.viewers.add(player);
            var occupy = Joc.class.getDeclaredMethod("occupySeat", Player.class);
            occupy.setAccessible(true);
            occupy.invoke(game, player);
            Field field = RoboRampage.class.getDeclaredField("teamLives");
            field.setAccessible(true);
            lives = (TeamLives) field.get(game);
            lives.start(List.of(id));
        }
    }

    @FunctionalInterface private interface Answer { Object call(String method, Object[] args); }
    private static <T> T stub(Class<T> type, Answer answer) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("equals")) return proxy == args[0];
            if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
            Object value = answer.call(method.getName(), args);
            if (value != null) return value;
            if (method.getReturnType() == boolean.class) return false;
            if (method.getReturnType() == int.class) return 0;
            return null;
        }));
    }
}
