package com.biel.lobby.mapes.jocs.inkwars;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.jocs.inkwars.InkWars.InkWarsPlayerInfo;
import com.biel.lobby.mapes.jocs.inkwars.utils.InkStream;
import com.biel.lobby.mapes.jocs.inkwars.InkWars.InkWarsPlayerInfo.Squid;

class InkWarsLifecycleTest {
    private static final Map<String, Player> onlinePlayers = new HashMap<>();

    @BeforeAll
    static void installServer() throws Exception {
        PluginManager plugins = proxy(PluginManager.class, (object, method, args) -> null);
        field(Bukkit.class, "server").set(null, proxy(Server.class, (object, method, args) -> switch (method.getName()) {
            case "getPluginManager" -> plugins;
            case "getPlayer" -> onlinePlayers.get(args[0]);
            case "getLogger" -> Logger.getLogger("InkWarsLifecycleTest");
            case "getName", "getVersion", "getBukkitVersion" -> "test";
            default -> null;
        }));
    }

    @Test
    void disconnectReleasesResourcesButKeepsProgressForRejoin() throws Exception {
        Fixture fixture = new Fixture();
        fixture.info.setInkLevel(4);
        field(InkWars.InkKit.class, "reloadTicks").setInt(fixture.kit, 17);
        fixture.online = false;
        fixture.game.onSeatDropped(fixture.player);
        fixture.assertResourcesReleased();
        assertSame(fixture.kit, fixture.info.getKit());
        assertFalse(fixture.kit.isDestroyed());
        assertEquals(4, fixture.info.getInkLevel());
        assertEquals(17, field(InkWars.InkKit.class, "reloadTicks").getInt(fixture.kit));
        assertFalse(fixture.kit.verifyEvent(new PlayerToggleFlightEvent(fixture.player, true)));
        fixture.online = true;
        assertTrue(fixture.kit.verifyEvent(new PlayerToggleFlightEvent(fixture.player, true)));
    }

    @Test
    void deliberateLeaveRetiresKitAndCleanupIsIdempotent() throws Exception {
        Fixture fixture = new Fixture();
        fixture.game.customLeave(fixture.player, new ArrayList<>());
        fixture.assertResourcesReleased();
        assertNull(fixture.info.getKit());
        assertTrue(fixture.kit.isDestroyed());
        fixture.game.customLeave(fixture.player, new ArrayList<>());
        fixture.assertResourcesReleased();
    }

    @Test
    void abandonedOfflineSeatDoesNotNeedAPlayerObject() throws Exception {
        Fixture fixture = new Fixture();
        onlinePlayers.remove(fixture.player.getName());
        fixture.squid.submerged = true;
        fixture.game.onSeatAbandoned(fixture.game.getSeats().getFirst(), new ArrayList<>());
        fixture.assertResourcesReleased();
        assertTrue(fixture.kit.isDestroyed());
        assertNull(fixture.info.getKit());
    }

    @Test
    void teardownAfterWorldChangeDoesNotTouchPlayerEquipment() throws Exception {
        Fixture fixture = new Fixture();
        fixture.playerWorld = proxy(World.class, (object, method, args) -> null);
        fixture.squid.submerged = true;
        fixture.game.wetInk.put(null, null);
        fixture.game.running = false;
        assertFalse(fixture.kit.isValid());
        fixture.game.clearExternals();
        fixture.assertResourcesReleased();
        assertTrue(fixture.kit.isDestroyed());
        assertTrue(fixture.game.wetInk.isEmpty());
        assertEquals(0, fixture.equipmentChanges);
        fixture.game.clearExternals();
        fixture.assertResourcesReleased();
    }

    @Test
    void kitRejectsSpectatorsAndUnloadedWorlds() throws Exception {
        Fixture fixture = new Fixture();
        fixture.game.players.clear();
        assertFalse(fixture.kit.verifyEvent(new PlayerToggleFlightEvent(fixture.player, true)));
        fixture.game.fixtureWorld = null;
        assertFalse(fixture.kit.isValid());
    }

    private static class TestGame extends InkWars {
        World fixtureWorld;
        boolean running = true;
        final ArrayList<Player> players = new ArrayList<>();

        @Override public World getWorld() { return fixtureWorld; }
        @Override public ArrayList<Player> getPlayers() { return players; }
        @Override public boolean JocEnMarxa() { return running; }
        @Override public double getPunishForLeaving() { return 0; }
        @Override public boolean hasHostPrivilleges(String name) { return false; }

        class Kit extends InkKit {
            Kit(Player player) { super(player); }
            // The fixture exercises lifetime and filtering without a plugin-wide dispatcher.
            @Override protected void setWorld(World world) {}
            @Override protected World getWorld() { return fixtureWorld; }
        }
    }

    private static class Fixture {
        final TestGame game = new TestGame();
        final Player player;
        final InkWarsPlayerInfo info;
        final TestGame.Kit kit;
        final Squid squid;
        World playerWorld;
        boolean online = true;
        int carrierRemovals;
        int projectileRemovals;
        int equipmentChanges;
        final List<Projectile> projectiles;
        final InkStream hose;

        @SuppressWarnings("unchecked")
        Fixture() throws Exception {
            game.fixtureWorld = proxy(World.class, (object, method, args) -> null);
            playerWorld = game.fixtureWorld;
            String name = UUID.randomUUID().toString();
            UUID id = UUID.randomUUID();
            player = proxy(Player.class, (object, method, args) -> switch (method.getName()) {
                case "getName" -> name;
                case "getUniqueId" -> id;
                case "getWorld" -> playerWorld;
                case "isOnline" -> online;
                case "getInventory", "removePotionEffect", "setExp", "teleport" -> {
                    equipmentChanges++;
                    throw new AssertionError("Cleanup should not touch this player's equipment or location");
                }
                default -> null;
            });
            onlinePlayers.put(name, player);
            game.players.add(player);
            var occupy = Joc.class.getDeclaredMethod("occupySeat", Player.class);
            occupy.setAccessible(true);
            occupy.invoke(game, player);
            info = game.getPlayerInfo(player);
            kit = game.new Kit(player);
            field(InkWarsPlayerInfo.class, "kit").set(info, kit);
            squid = (Squid) field(InkWarsPlayerInfo.class, "squid").get(info);
            squid.reserve = 0.8;
            hose = (InkStream) field(InkWars.InkKit.class, "hose").get(kit);
            hose.emit(new Location(playerWorld, 0, 1, 0), new Vector(1, 0, 0), 1, 0,
                    new InkStream.Load(1, 1, 1, 0.1), 3);
            assertFalse(hose.isEmpty());
            squid.carrier = proxy(ItemDisplay.class, (object, method, args) -> {
                if (method.getName().equals("remove")) carrierRemovals++;
                return null;
            });
            projectiles = (List<Projectile>) field(InkWars.InkKit.class, "inkBalls").get(kit);
            projectiles.add(proxy(Projectile.class, (object, method, args) -> {
                if (method.getName().equals("remove")) projectileRemovals++;
                return null;
            }));
        }

        void assertResourcesReleased() {
            assertNull(squid.carrier);
            assertFalse(squid.submerged);
            assertEquals(0, squid.reserve);
            assertEquals(1, carrierRemovals);
            assertEquals(1, projectileRemovals);
            assertTrue(projectiles.isEmpty());
            assertTrue(hose.isEmpty());
        }
    }

    private static Field field(Class<?> type, String name) throws Exception {
        Field field = type.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (object, method, args) -> {
            if (method.getName().equals("equals")) return object == args[0];
            if (method.getName().equals("hashCode")) return System.identityHashCode(object);
            Object result = handler.invoke(object, method, args);
            if (result == null && method.getReturnType() == boolean.class) return false;
            return result;
        }));
    }
}
