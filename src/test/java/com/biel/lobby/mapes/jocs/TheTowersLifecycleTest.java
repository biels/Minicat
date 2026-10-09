package com.biel.lobby.mapes.jocs;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.biel.lobby.Mapa;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamScoreRace;

/** Exercises The Towers scoring lifecycle without loading a Bukkit world. */
class TheTowersLifecycleTest {
    private static final Map<String, Player> online = new HashMap<>();

    @BeforeAll
    static void installServer() throws Exception {
        PluginManager plugins = proxy(PluginManager.class, (method, args) -> null);
        Field server = Bukkit.class.getDeclaredField("server");
        server.setAccessible(true);
        server.set(null, proxy(Server.class, (method, args) -> switch (method) {
            case "getPlayer", "getPlayerExact" -> online.get(args[0]);
            case "getPluginManager" -> plugins;
            default -> null;
        }));
        installSoundRegistry();
    }

    @Test
    void enemyPitScoresOnceAndReturnsPlayerToOwnSpawn() throws Exception {
        Fixture fixture = new Fixture();
        fixture.game.checkScoring(fixture.red);
        assertEquals(1, fixture.redTeam.getScore());
        assertEquals(fixture.redSpawn, fixture.redLocation);
        fixture.game.checkScoring(fixture.red);
        assertEquals(1, fixture.redTeam.getScore(), "returning to spawn prevents duplicate scoring");
    }

    @Test
    void ownPitAndInvalidPlayersDoNotScore() throws Exception {
        Fixture fixture = new Fixture();
        fixture.redLocation = new Location(fixture.world, 1, 1, 1);
        fixture.game.checkScoring(fixture.red);
        assertEquals(0, fixture.redTeam.getScore());

        fixture.redLocation = new Location(fixture.world, 20, 1, 1);
        fixture.redDead = true;
        fixture.game.checkScoring(fixture.red);
        fixture.redDead = false;
        fixture.redMode = GameMode.CREATIVE;
        fixture.game.checkScoring(fixture.red);
        fixture.redMode = GameMode.SPECTATOR;
        fixture.game.checkScoring(fixture.red);
        fixture.redMode = GameMode.SURVIVAL;
        fixture.redWorld = proxy(World.class, (method, args) -> null);
        fixture.game.checkScoring(fixture.red);
        fixture.game.running = false;
        fixture.redWorld = fixture.world;
        fixture.game.checkScoring(fixture.red);
        assertEquals(0, fixture.redTeam.getScore());
    }

    @Test
    void failedTeleportDoesNotAwardPoint() throws Exception {
        Fixture fixture = new Fixture();
        fixture.teleportSucceeds = false;
        fixture.game.checkScoring(fixture.red);
        assertEquals(0, fixture.redTeam.getScore());
    }

    @Test
    void exactTargetEndsMatchOnce() throws Exception {
        Fixture fixture = new Fixture();
        fixture.redTeam.setScore(9);
        fixture.game.checkScoring(fixture.red);
        assertEquals(10, fixture.redTeam.getScore());
        assertEquals(1, fixture.game.wins);
        assertFalse(fixture.game.running);
    }

    private static final class Fixture {
        final TestGame game = new TestGame();
        final World world = proxy(World.class, (method, args) -> null);
        final Location redSpawn = new Location(world, -5, 2, 0);
        final Location blueSpawn = new Location(world, 5, 2, 0);
        final Player red;
        final JocTeamScoreRace.EquipScoreRace redTeam;
        Location redLocation = new Location(world, 20, 1, 1);
        World redWorld = world;
        GameMode redMode = GameMode.SURVIVAL;
        boolean redDead;
        boolean teleportSucceeds = true;

        Fixture() throws Exception {
            red = player("red", () -> redLocation, () -> redWorld, () -> redMode, () -> redDead,
                    destination -> { if (teleportSucceeds) redLocation = destination; return teleportSucceeds; });
            online.put(red.getName(), red);
            redTeam = game.new EquipScoreRace(DyeColor.RED, "red") {
                @Override public Location getTeamSpawnLocation() { return redSpawn.clone(); }
            };
            JocTeamScoreRace.EquipScoreRace blueTeam = game.new EquipScoreRace(DyeColor.BLUE, "blue") {
                @Override public Location getTeamSpawnLocation() { return blueSpawn.clone(); }
            };
            set(JocEquips.Equip.class, redTeam, "Players", new ArrayList<>(List.of(red.getName())));
            set(Mapa.class, game, "world", world);
            set(Joc.class, game, "JocIniciat", true);
            set(Joc.class, game, "JocFinalitzat", false);
            set(Joc.class, game, "InfoStorage", new ArrayList<>());
            set(JocEquips.class, game, "Equips", new ArrayList<>(List.of(redTeam, blueTeam)));
            set(TheTowers.class, game, "scoringPits", List.of(
                    new BoundingBox(-10, 0, -10, -1, 10, 10),
                    new BoundingBox(10, 0, -10, 30, 10, 10)));
            online.put(red.getName(), red);
        }
    }

    private static final class TestGame extends TheTowers {
        @Override public boolean JocEnMarxa() { return running; }
        @Override public ArrayList<Player> getPlayers() { return new ArrayList<>(online.values()); }
        @Override public ArrayList<Player> getViewers() { return new ArrayList<>(); }
        @Override public void winGame(Equip e) { wins++; running = false; }
        @Override protected void updateScoreBoard(Player player) {}
        @Override public void updateScoreBoards() {}
        boolean running = true;
        int wins;
    }

    private interface Value<T> { T get(); }
    private interface Teleport { boolean move(Location destination); }

    private static Player player(String name, Value<Location> location, Value<World> world,
            Value<GameMode> mode, Value<Boolean> dead, Teleport teleport) {
        UUID id = UUID.nameUUIDFromBytes(name.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return proxy(Player.class, (method, args) -> switch (method) {
            case "getName" -> name;
            case "getUniqueId" -> id;
            case "getLocation" -> location.get();
            case "getWorld" -> world.get();
            case "getGameMode" -> mode.get();
            case "isDead" -> dead.get();
            case "isOnline" -> true;
            case "teleport" -> teleport.move((Location) args[0]);
            default -> null;
        });
    }

    private static void set(Class<?> type, Object target, String name, Object value) throws Exception {
        Field field = type.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static void installSoundRegistry() throws Exception {
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Object allocator = unsafeField.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = proxy(io.papermc.paper.registry.RegistryAccess.class, (method, args) -> {
            if (method.equals("getRegistry")) {
                return proxy(Registry.class, (operation, values) -> operation.equals("get") || operation.equals("getOrThrow")
                        ? proxy(Sound.class, (ignored, unused) -> null) : null);
            }
            return null;
        });
        Object base = unsafeClass.getMethod("staticFieldBase", Field.class).invoke(allocator, providerField);
        long offset = (long) unsafeClass.getMethod("staticFieldOffset", Field.class).invoke(allocator, providerField);
        unsafeClass.getMethod("putObject", Object.class, long.class, Object.class)
                .invoke(allocator, base, offset, Optional.of(provider));
    }

    @SuppressWarnings("unchecked")
    private static <T> T proxy(Class<T> type, java.util.function.BiFunction<String, Object[], Object> body) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (object, method, args) -> {
            if (method.getName().equals("equals")) return object == args[0];
            if (method.getName().equals("hashCode")) return System.identityHashCode(object);
            Object result = body.apply(method.getName(), args);
            if (result == null && method.getReturnType() == boolean.class) return false;
            if (result == null && method.getReturnType() == int.class) return 0;
            return result;
        });
    }
}
