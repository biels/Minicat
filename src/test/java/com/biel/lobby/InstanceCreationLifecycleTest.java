package com.biel.lobby;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.jocs.Spleef;

/** Runs the actual creation callbacks and retirement guards with controlled copy/tick queues. */
class InstanceCreationLifecycleTest {
    private final List<Runnable> copyTasks = new ArrayList<>();
    private final List<Runnable> mainTasks = new ArrayList<>();
    private GestorMapes.ContenidorJoc container;

    @BeforeEach void installServer() throws Exception {
        lobby plugin = allocate(lobby.class);
        PluginManager plugins = proxy(PluginManager.class, (object, method, arguments) ->
                method.getName().equals("getPlugin") ? plugin : null);
        BukkitScheduler scheduler = proxy(BukkitScheduler.class, (object, method, arguments) -> {
            if (method.getName().equals("runTaskAsynchronously")) copyTasks.add((Runnable) arguments[1]);
            if (method.getName().equals("runTask")) mainTasks.add((Runnable) arguments[1]);
            return null;
        });
        Field server = Bukkit.class.getDeclaredField("server");
        server.setAccessible(true);
        Server fixtureServer = proxy(Server.class, (object, method, arguments) -> switch (method.getName()) {
            case "getPluginManager" -> plugins;
            case "getScheduler" -> scheduler;
            default -> null;
        });
        server.set(null, fixtureServer);
        Field pluginServer = org.bukkit.plugin.java.JavaPlugin.class.getDeclaredField("server");
        pluginServer.setAccessible(true);
        pluginServer.set(plugin, fixtureServer);
        GestorMapes maps = allocate(GestorMapes.class);
        maps.plugin = plugin;
        container = maps.new ContenidorJoc(CreationGame.class, "creation fixture", Material.STONE,
                GestorMapes.DevelopmentState.Release);
    }

    @Test void abandonedPlayerCreationRetiresTheEmptyWorldAndItsResources() {
        CompletableFuture<Joc> creation = container.createForPlayers(null);
        finishCopy();
        CreationGame game = (CreationGame) creation.join();
        assertTrue(container.getInstàncies().contains(game));
        assertFalse(game.isDestroyed());
        nextTick();
        assertTrue(container.getInstàncies().isEmpty());
        assertTrue(game.unloaded);
        assertTrue(game.externalsCleared);
        assertTrue(game.isDestroyed());
    }

    @Test void sharedCreationWaitsForEveryJoinBeforeCheckingOccupancy() {
        CompletableFuture<Joc> first = container.createForPlayers(null);
        CompletableFuture<Joc> second = container.createForPlayers(null);
        assertSame(first, second);
        assertEquals(1, copyTasks.size());
        Player player = player();
        // A disconnected waiter does nothing; a different waiter still claims the world.
        second.thenAccept(game -> ((CreationGame) game).players.add(player));
        first.thenAccept(game -> assertFalse(((CreationGame) game).unloaded));
        finishCopy();
        nextTick();
        CreationGame game = (CreationGame) first.join();
        assertFalse(game.unloaded);
        assertTrue(container.getInstàncies().contains(game));
        game.players.clear();
        container.checkNecessary(game);
        assertTrue(game.unloaded);
    }

    @Test void failedJoinCallbackDoesNotLeakItsUnclaimedWorld() {
        CompletableFuture<Joc> creation = container.createForPlayers(null);
        CompletableFuture<Void> join = creation.thenAccept(game -> { throw new IllegalStateException("join refused"); });
        finishCopy();
        assertTrue(join.isCompletedExceptionally());
        nextTick();
        assertTrue(((CreationGame) creation.join()).unloaded);
    }

    @Test void cancelledPlayerRequestDoesNotLeaveALoadedWorld() {
        CompletableFuture<Joc> creation = container.createForPlayers(null);
        assertTrue(creation.cancel(false));
        finishCopy();
        CreationGame game = (CreationGame) container.getInstàncies().getFirst();
        nextTick();
        assertTrue(game.unloaded);
    }

    @Test void explicitDiagnosticCreationKeepsItsEmptyWorld() {
        CompletableFuture<Joc> creation = container.addMap(null);
        finishCopy();
        nextTick();
        assertFalse(((CreationGame) creation.join()).unloaded);
        assertEquals(1, container.getInstàncies().size());
    }

    @Test void diagnosticRequestCanRetainAnAlreadyPendingPlayerCreation() {
        CompletableFuture<Joc> creation = container.createForPlayers(null);
        assertSame(creation, container.addMap(null));
        finishCopy();
        nextTick();
        assertFalse(((CreationGame) creation.join()).unloaded);
    }

    @Test void playerRequestCannotRemoveTheRetentionOfADiagnosticCreation() {
        CompletableFuture<Joc> creation = container.addMap(null);
        assertSame(creation, container.createForPlayers(null));
        finishCopy();
        nextTick();
        assertFalse(((CreationGame) creation.join()).unloaded);
    }

    @Test void cleanupPreservesEditingWorldsAndDroppedSeats() {
        CompletableFuture<Joc> editing = container.createForPlayers(0);
        editing.thenAccept(game -> ((CreationGame) game).editing = true);
        CompletableFuture<Joc> dropped = container.createForPlayers(1);
        dropped.thenAccept(game -> ((CreationGame) game).waitingForRejoin = true);
        finishCopy();
        finishCopy();
        nextTick();
        assertFalse(((CreationGame) editing.join()).unloaded);
        assertFalse(((CreationGame) dropped.join()).unloaded);
    }

    @Test void failedUnloadDoesNotDestroyTheStillRegisteredWorld() {
        CompletableFuture<Joc> creation = container.createForPlayers(null);
        creation.thenAccept(game -> ((CreationGame) game).unloadAllowed = false);
        finishCopy();
        nextTick();
        CreationGame game = (CreationGame) creation.join();
        assertFalse(game.unloaded);
        assertFalse(game.externalsCleared);
        assertFalse(game.isDestroyed());
        assertTrue(container.getInstàncies().contains(game));
    }

    private void finishCopy() {
        copyTasks.removeFirst().run();
        nextTick();
    }

    private void nextTick() {
        List<Runnable> tasks = new ArrayList<>(mainTasks);
        mainTasks.clear();
        tasks.forEach(Runnable::run);
    }

    public static final class CreationGame extends Spleef {
        final List<Player> players = new ArrayList<>();
        boolean unloaded;
        boolean externalsCleared;
        boolean editing;
        boolean waitingForRejoin;
        boolean unloadAllowed = true;

        @Override public void reserveLiveWorld() { }
        @Override public void copyWorldFiles() { }
        @Override public void initialize() {
            world = proxy(World.class, (object, method, arguments) ->
                    method.getName().equals("getPlayers") ? players : null);
        }
        @Override public Boolean getEditMode() { return editing; }
        @Override public boolean isWaitingForDroppedPlayers() { return waitingForRejoin; }
        @Override public boolean deleteVirtualWorld() {
            if (!unloadAllowed) return false;
            unloaded = true;
            world = null;
            return true;
        }
        @Override public void clearAllExternals() { externalsCleared = true; }
    }

    private static Player player() {
        UUID id = UUID.randomUUID();
        return proxy(Player.class, (object, method, arguments) -> switch (method.getName()) {
            case "getName" -> "creation-player";
            case "getUniqueId" -> id;
            default -> null;
        });
    }

    private static <T> T allocate(Class<T> type) throws Exception {
        Field singleton = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        singleton.setAccessible(true);
        return type.cast(((sun.misc.Unsafe) singleton.get(null)).allocateInstance(type));
    }

    private static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler));
    }
}
