package com.biel.lobby.mapes.jocs;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.biel.lobby.localization.MessageArgument;
import com.biel.lobby.localization.MessageKey;

/** Exercises the game's actual roster/timer hooks; native equipment is checked on dev. */
class TNTRunLifecycleTest {
    static final Map<String, Player> onlinePlayers = new HashMap<>();
    static final Set<Integer> cancelledTasks = new HashSet<>();

    @BeforeAll static void server() throws Exception {
        PluginManager plugins = stub(PluginManager.class, (method, args) -> null);
        BukkitScheduler scheduler = stub(BukkitScheduler.class, (method, args) -> {
            if (method.equals("cancelTask")) cancelledTasks.add((int) args[0]);
            return null;
        });
        Field server = Bukkit.class.getDeclaredField("server");
        server.setAccessible(true);
        server.set(null, stub(Server.class, (method, args) -> switch (method) {
            case "getPlayer", "getPlayerExact" -> onlinePlayers.get(args[0]);
            case "getPluginManager" -> plugins;
            case "getScheduler" -> scheduler;
            default -> null;
        }));
    }

    @BeforeEach void clearServer() { onlinePlayers.clear(); cancelledTasks.clear(); }

    @Test void assignsDistinctCarriersAtAllRosterSizes() {
        for (int count = 0; count <= 12; count++) {
            TestGame game = new TestGame(count);
            game.tntInicial();
            assertEquals((count + 2) / 3, game.tntPlayers.size());
            assertEquals(game.tntPlayers.size(), new HashSet<>(game.tntPlayers).size());
        }
    }

    @Test void timerRunsForSixtySecondsAfterThreeSecondPreparation() {
        TestGame game = new TestGame(4);
        game.tntInicial();
        game.ProgTask();
        assertEquals(80, game.initialDelay);
        assertEquals(20, game.period);
        Runnable countdown = game.repeating;
        for (int second = 0; second < 59; second++) countdown.run();
        assertEquals(1, game.temps);
        assertEquals(4, game.getAliveNames().size());
        countdown.run();
        assertEquals(2, game.getAliveNames().size());
        assertEquals(1, game.startedRounds);
        assertTrue(cancelledTasks.contains(1));
    }

    @Test void offlineCarrierCannotEscapeEliminationAtDeadline() {
        TestGame game = new TestGame(3);
        Player carrier = game.players.getFirst();
        game.posarTNT(carrier);
        game.players.remove(carrier);
        onlinePlayers.remove(carrier.getName());
        game.explotarJugadors();
        assertFalse(game.isAlive(carrier));
        assertTrue(game.tntPlayers.isEmpty());
        assertTrue(game.returnedPlayers.isEmpty());
    }

    @Test void passHasTwoSecondImmunityAndUsesTheReconnectedEntityAtExpiry() {
        TestGame game = new TestGame(3);
        Player from = game.players.getFirst(), to = game.players.get(1);
        game.posarTNT(from);
        game.passarTNT(from, to);
        assertTrue(game.hasTNT(to));
        assertTrue(game.isImmune(from));
        assertEquals(40, game.delays.getFirst());
        game.passarTNT(to, from);
        assertTrue(game.hasTNT(to));
        Player reconnected = player(from.getName());
        onlinePlayers.put(from.getName(), reconnected);
        game.players.set(0, reconnected);
        game.restored.clear();
        game.delayed.getFirst().run();
        assertFalse(game.isImmune(reconnected));
        assertSame(reconnected, game.restored.getFirst());
    }

    @Test void olderImmunityExpiryCannotClearRenewedProtection() {
        TestGame game = new TestGame(3);
        Player player = game.players.getFirst();
        game.giveImmunity(player, 2);
        game.giveImmunity(player, 2);
        game.delayed.getFirst().run();
        assertTrue(game.isImmune(player));
        game.delayed.get(1).run();
        assertFalse(game.isImmune(player));
    }

    @Test void departedAndEliminatedPlayersCannotPassTnt() {
        TestGame game = new TestGame(4);
        Player from = game.players.getFirst(), target = game.players.get(1);
        game.posarTNT(from);
        game.getAliveNames().remove(target.getName());
        game.passarTNT(from, target);
        assertTrue(game.hasTNT(from));
        game.players.remove(from);
        game.passarTNT(from, game.players.getLast());
        assertTrue(game.hasTNT(from));
    }

    @Test void expiryAfterLeaveResolvesStateWithoutRetainingTheOldEntity() {
        TestGame game = new TestGame(3);
        Player player = game.players.getFirst();
        game.giveImmunity(player, 2);
        onlinePlayers.remove(player.getName());
        game.restored.clear();
        game.delayed.getFirst().run();
        assertTrue(game.restored.isEmpty());
        assertFalse(game.isImmune(player));
    }

    @Test void deliberateLeaveRemovesAliveNameAndReplacesLastCarrier() {
        TestGame game = new TestGame(3);
        Player carrier = game.players.getFirst();
        game.posarTNT(carrier);
        game.customLeave(carrier, new ArrayList<>());
        assertFalse(game.isAlive(carrier));
        assertEquals(1, game.startedRounds);
    }

    @Test void lastOpponentLeavingCompletesMatch() {
        TestGame game = new TestGame(2);
        game.customLeave(game.players.getFirst(), new ArrayList<>());
        assertFalse(game.JocEnMarxa());
        assertEquals(1, game.getAliveNames().size());
        assertEquals(0, game.startedRounds);
    }

    @Test void abandonedSeatRemovesOfflineParticipant() throws Exception {
        TestGame game = new TestGame(3);
        Player player = game.players.getFirst();
        var occupy = com.biel.lobby.mapes.Joc.class.getDeclaredMethod("occupySeat", Player.class);
        occupy.setAccessible(true);
        occupy.invoke(game, player);
        onlinePlayers.remove(player.getName());
        game.onSeatAbandoned(game.getSeats().getFirst(), new ArrayList<>());
        assertFalse(game.isAlive(player));
    }

    @Test void endedMatchCannotPassOrRunRetainedCountdown() {
        TestGame game = new TestGame(3);
        game.posarTNT(game.players.getFirst());
        game.ProgTask();
        game.running = false;
        game.repeating.run();
        game.passarTNT(game.players.getFirst(), game.players.getLast());
        assertEquals(60, game.temps);
        assertTrue(game.hasTNT(game.players.getFirst()));
    }

    @Test void olderHyperSpeedExpiryCannotEndANewerBurst() throws Exception {
        TestGame game = new TestGame(3);
        game.hipervelocitat();
        game.hipervelocitat();
        Field hyperSpeed = TNTRun.class.getDeclaredField("hyperSpeed");
        hyperSpeed.setAccessible(true);
        game.delayed.getFirst().run();
        assertTrue(hyperSpeed.getBoolean(game));
        game.delayed.get(1).run();
        assertFalse(hyperSpeed.getBoolean(game));
    }

    @Test void staleRoundCountdownCannotAdvanceTheNextRound() {
        TestGame game = new TestGame(3);
        game.ProgTask();
        game.round++;
        game.repeating.run();
        assertEquals(60, game.temps);
    }

    @Test void eliminationReturnsOnlyPlayersStillInTheMatch() {
        TestGame game = new TestGame(4);
        Player carrier = game.players.getFirst();
        game.posarTNT(carrier);
        game.players.remove(carrier); // Online in the lobby, but no longer in this instance.
        game.explotarJugadors();
        assertTrue(game.returnedPlayers.isEmpty());
        assertFalse(game.isAlive(carrier));
    }

    private static class TestGame extends TNTRun {
        final ArrayList<Player> players = new ArrayList<>();
        final List<Player> restored = new ArrayList<>(), returnedPlayers = new ArrayList<>();
        final List<Runnable> delayed = new ArrayList<>();
        final List<Long> delays = new ArrayList<>();
        boolean running = true;
        int startedRounds;
        Runnable repeating;
        long initialDelay, period;
        TestGame(int count) {
            for (int index = 0; index < count; index++) {
                Player player = player("TntQA" + index);
                players.add(player);
                onlinePlayers.put(player.getName(), player);
            }
            setAlivePlayers(players);
        }
        @Override public boolean JocEnMarxa() { return running; }
        @Override public ArrayList<Player> getPlayers() { return players; }
        @Override void restoreRoundState(Player player) { restored.add(player); }
        @Override void applyEffects(Player player) {}
        @Override void startRound() { startedRounds++; }
        @Override public void comprovarGuanyador() { if (getAliveNames().size() <= 1) running = false; }
        @Override public void anunciarPerdedor(String name) {}
        @Override protected void updateScoreBoard(Player player) {}
        @Override public void updateScoreBoards() {}
        @Override public void sendGlobalMessage(String message) {}
        @Override public void sendGlobalMessage(MessageKey key, MessageArgument... arguments) {}
        @Override public double getPunishForLeaving() { return 0; }
        @Override public boolean hasHostPrivilleges(String name) { return false; }
        @Override protected void returnEliminatedPlayerToLobby(Player player) { returnedPlayers.add(player); }
        @Override public int scheduleGameplayTask(Runnable task, long delay) { delayed.add(task); delays.add(delay); return delayed.size() + 100; }
        @Override public int scheduleGameplayRepeatingTask(Runnable task, long delay, long period) {
            repeating = task; initialDelay = delay; this.period = period; return 1;
        }
    }

    private static Player player(String name) {
        UUID id = UUID.nameUUIDFromBytes(name.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return stub(Player.class, (method, args) -> switch (method) {
            case "getName" -> name;
            case "getUniqueId" -> id;
            default -> null;
        });
    }
    interface Invocation { Object call(String method, Object[] args); }
    @SuppressWarnings("unchecked") static <T> T stub(Class<T> type, Invocation body) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (object, method, args) -> {
            if (method.getName().equals("equals")) return object == args[0];
            if (method.getName().equals("hashCode")) return System.identityHashCode(object);
            return body.call(method.getName(), args);
        });
    }
}
