package com.biel.lobby.mapes.jocs;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.biel.lobby.Mapa;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.minions.Minion;
import com.destroystokyo.paper.entity.Pathfinder;

/** Geometry, terrain preservation and the real builder/Minion lifecycle without a server. */
class TheTowersBridgeTest {
    private static final Map<UUID, Entity> entities = new HashMap<>();

    @BeforeAll
    static void installServer() throws Exception {
        PluginManager plugins = stub(PluginManager.class, (method, args) -> null);
        set(Bukkit.class, null, "server", stub(Server.class, (method, args) -> switch (method) {
            case "getEntity" -> entities.get(args[0]);
            case "getPluginManager" -> plugins;
            default -> null;
        }));
        Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) unsafeField.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = stub(io.papermc.paper.registry.RegistryAccess.class, (method, args) ->
                method.equals("getRegistry") ? stub(Registry.class, (operation, values) -> {
                    if (!operation.equals("get") && !operation.equals("getOrThrow")) return null;
                    String key = values[0].toString();
                    return stub(BlockType.class, (trait, ignored) -> switch (trait) {
                        case "isAir" -> key.equals("minecraft:air");
                        case "isSolid" -> !key.equals("minecraft:air") && !key.equals("minecraft:water") && !key.equals("minecraft:snow");
                        default -> null;
                    });
                }) : null);
        unsafe.putObject(unsafe.staticFieldBase(providerField), unsafe.staticFieldOffset(providerField), Optional.of(provider));
    }

    @Test
    void surveyedStairsExpandToAdjacentFloorsInBothDirections() throws Exception {
        Fixture fixture = new Fixture();
        for (int direction : List.of(-1, 1)) {
            List<Location> route = TheTowers.expandBridgeRoute(List.of(
                    fixture.at(direction * 62, 196, 1138), fixture.at(direction * 60, 198, 1138),
                    fixture.at(direction * 2, 198, 1138), fixture.at(0, 196, 1138)), fixture.game.arenaBounds);
            assertEquals(63, route.size());
            assertEquals(196, route.getFirst().getBlockY());
            assertEquals(198, route.get(2).getBlockY());
            assertEquals(196, route.getLast().getBlockY());
            for (int step = 1; step < route.size(); step++) {
                assertEquals(1, Math.abs(route.get(step).getBlockX() - route.get(step - 1).getBlockX()));
                assertTrue(Math.abs(route.get(step).getBlockY() - route.get(step - 1).getBlockY()) <= 1);
                assertEquals(1138, route.get(step).getBlockZ());
            }
        }
    }

    @Test
    void invalidGeometryCannotLeaveArenaOrCreateUnboundedRoutes() throws Exception {
        Fixture fixture = new Fixture();
        assertThrows(IllegalArgumentException.class, () -> TheTowers.expandBridgeRoute(
                List.of(fixture.at(0, 0, 0), fixture.at(1, 2, 0)), fixture.game.arenaBounds));
        assertThrows(IllegalArgumentException.class, () -> TheTowers.expandBridgeRoute(
                List.of(fixture.at(0, 0, 0), fixture.at(1, 0, 1)), fixture.game.arenaBounds));
        assertThrows(IllegalArgumentException.class, () -> TheTowers.expandBridgeRoute(
                List.of(fixture.at(0, 0, 0), fixture.at(128, 0, 0)), fixture.game.arenaBounds));
        assertThrows(IllegalArgumentException.class, () -> TheTowers.expandBridgeRoute(
                List.of(fixture.at(0, 0, 0), fixture.at(3, 0, 0)), new BoundingBox(-1, 0, 0, 4, 3, 1)));
        World other = stub(World.class, (method, args) -> null);
        assertThrows(IllegalArgumentException.class, () -> TheTowers.expandBridgeRoute(
                List.of(fixture.at(0, 0, 0), new Location(other, 3, 0, 0)), fixture.game.arenaBounds));
    }

    @Test
    void quartzWideningPreservesPlayerBlocksLiquidsAndProtectedInfrastructure() throws Exception {
        Fixture fixture = new Fixture();
        fixture.terrain.put(new Cell(0, 0, 0), Material.STONE);
        assertTrue(fixture.game.prepareBridgeFloor(fixture.at(0, 0, 0), 3));
        assertEquals(Material.STONE, fixture.material(0, 0, 0), "an existing player or map floor remains intact");
        assertEquals(Material.QUARTZ_BLOCK, fixture.material(0, 0, -1));
        assertEquals(Material.QUARTZ_BLOCK, fixture.material(0, 0, 1));
        assertEquals(Material.AIR, fixture.material(0, 0, 2), "the bridge is never wider than three");
        for (Material preserved : List.of(Material.WATER, Material.CHEST, Material.ENCHANTING_TABLE, Material.BEDROCK)) {
            fixture.terrain.put(new Cell(2, 0, 0), preserved);
            assertFalse(fixture.game.canPlaceBridge(fixture.block(2, 0, 0)));
            fixture.game.prepareBridgeFloor(fixture.at(2, 0, 0), 1);
            assertEquals(preserved, fixture.material(2, 0, 0));
        }
        fixture.game.protectedAreas = List.of(new BoundingBox(4, 0, 0, 5, 1, 1));
        fixture.game.prepareBridgeFloor(fixture.at(4, 0, 0), 1);
        assertEquals(Material.AIR, fixture.material(4, 0, 0), "protected air is also preserved");
    }

    @Test
    void interruptedBuilderRetainsBlocksAndRetriesTheNarrowPass() throws Exception {
        Fixture fixture = new Fixture();
        TheTowers.BridgeLane lane = fixture.lane(0, 0, 5);
        fixture.routes(lane);
        fixture.game.requestBuilder(fixture.team);
        TheTowers.BridgeBuilder builder = fixture.builder();
        builder.buildAndWalk();
        assertEquals(Material.QUARTZ_BLOCK, fixture.material(1, 0, 0));
        assertEquals(0, lane.completedWidth, "placing ahead cannot count as arrival");
        builder.onMinionDeath(null, null);
        fixture.game.discharge(builder);
        assertFalse(lane.active);
        assertEquals(1, lane.nextWidth(), "death does not unlock widening");
        fixture.game.requestBuilder(fixture.team);
        assertNotSame(builder, fixture.builder());
        assertEquals(Material.QUARTZ_BLOCK, fixture.material(1, 0, 0), "partial progress survives the retry");
    }

    @Test
    void actualMiddleArrivalUnlocksWideningAndIntactBridgesDoNotSpawnBuilders() throws Exception {
        Fixture fixture = new Fixture();
        TheTowers.BridgeLane lane = fixture.lane(0, 0, 5);
        fixture.routes(lane);
        fixture.game.requestBuilder(fixture.team);
        fixture.walkToEnd(fixture.builder());
        assertEquals(1, lane.completedWidth);
        assertTrue(fixture.game.minions().isEmpty(), "the builder despawns at the middle");
        fixture.game.requestBuilder(fixture.team);
        fixture.walkToEnd(fixture.builder());
        assertEquals(3, lane.completedWidth);
        for (int x = 0; x <= 5; x++) for (int z = -1; z <= 1; z++) {
            assertEquals(Material.QUARTZ_BLOCK, fixture.material(x, 0, z));
        }
        fixture.game.requestBuilder(fixture.team);
        assertTrue(fixture.game.minions().isEmpty(), "there is no useless walk over a complete bridge");
        fixture.terrain.remove(new Cell(3, 0, 1));
        fixture.game.requestBuilder(fixture.team);
        fixture.walkToEnd(fixture.builder());
        assertEquals(Material.QUARTZ_BLOCK, fixture.material(3, 0, 1), "later builders repair holes");
    }

    @Test
    void overlappingRowsStayReservedAndReinforcementPrecedesNewBalconies() throws Exception {
        Fixture fixture = new Fixture();
        TheTowers.BridgeLane north = fixture.lane(0, 0, 5), south = fixture.lane(0, 28, 5), fartherNorth = fixture.lane(-4, 0, 5);
        fixture.routes(north, south, fartherNorth);
        fixture.game.requestBuilder(fixture.team);
        fixture.game.requestBuilder(fixture.team);
        for (int kill = 0; kill < 30; kill++) fixture.game.requestBuilder(fixture.team);
        assertEquals(2, fixture.game.minions().size(), "two active builders cap a team's army");
        assertTrue(north.active && south.active);
        assertFalse(fartherNorth.active, "overlapping center paths cannot run two builders at once");
        assertEquals(12, ((int[]) field(TheTowers.class, fixture.game, "builderCredits"))[0], "waiting kill credits are bounded");
        fixture.walkToEnd((TheTowers.BridgeBuilder) fixture.game.minions().getFirst());
        assertSame(north, fixture.game.chooseBridgeLane(0), "the second pass widens the completed lane first");
    }

    @Test
    void pregameAndCompletedGameNeverRequestBuildersOrPeriodicSpawns() throws Exception {
        Fixture fixture = new Fixture();
        fixture.routes(fixture.lane(0, 0, 5));
        assertFalse(fixture.game.periodicBuilderDue(299));
        assertTrue(fixture.game.periodicBuilderDue(300));
        fixture.game.running = false;
        assertFalse(fixture.game.periodicBuilderDue(600));
        fixture.game.requestBuilder(fixture.team);
        assertTrue(fixture.game.minions().isEmpty());
    }

    @Test
    void blockedBuilderExpiresAndCannotCompleteItsLane() throws Exception {
        Fixture fixture = new Fixture();
        TheTowers.BridgeLane lane = fixture.lane(0, 0, 5);
        fixture.routes(lane);
        fixture.game.requestBuilder(fixture.team);
        TheTowers.BridgeBuilder builder = fixture.builder();
        fixture.terrain.put(new Cell(1, 1, 0), Material.STONE);
        builder.buildAndWalk();
        fixture.game.second = 11;
        builder.buildAndWalk();
        assertTrue(fixture.game.minions().isEmpty());
        assertFalse(lane.active);
        assertEquals(0, lane.completedWidth);
        assertEquals(Material.STONE, fixture.material(1, 1, 0), "a blockage is never broken or bypassed");
    }

    @Test
    void builderLifetimeAlsoReleasesItsLaneWithoutFinishing() throws Exception {
        Fixture fixture = new Fixture();
        TheTowers.BridgeLane lane = fixture.lane(0, 0, 5);
        fixture.routes(lane);
        fixture.game.requestBuilder(fixture.team);
        fixture.game.second = 120;
        fixture.builder().buildAndWalk();
        assertTrue(fixture.game.minions().isEmpty());
        assertFalse(lane.active);
        assertEquals(1, lane.nextWidth());
    }

    @Test
    void sharedMatchCleanupRemovesBuildersAndPendingCredits() throws Exception {
        Fixture fixture = new Fixture();
        TheTowers.BridgeLane lane = fixture.lane(0, 0, 5);
        fixture.routes(lane);
        fixture.game.requestBuilder(fixture.team);
        fixture.game.requestBuilder(fixture.team);
        assertTrue(lane.active);
        assertEquals(1, ((int[]) field(TheTowers.class, fixture.game, "builderCredits"))[0]);
        fixture.game.clearExternals();
        assertTrue(fixture.game.minions().isEmpty());
        assertFalse(lane.active);
        assertEquals(0, ((int[]) field(TheTowers.class, fixture.game, "builderCredits"))[0]);
        assertEquals(0, lane.completedWidth, "cleanup never invents a completed bridge");
    }

    private record Cell(int x, int y, int z) {}

    private static final class Fixture {
        final TestGame game = new TestGame();
        final Map<Cell, Material> terrain = new HashMap<>();
        final World world;
        final JocEquips.Equip team;

        Fixture() throws Exception {
            world = stub(World.class, (method, args) -> {
                if (!method.equals("getBlockAt")) return null;
                if (args[0] instanceof Location point) return block(point.getBlockX(), point.getBlockY(), point.getBlockZ());
                return block((int) args[0], (int) args[1], (int) args[2]);
            });
            set(Mapa.class, game, "world", world);
            game.arenaBounds = new BoundingBox(-200, -5, -200, 200, 205, 1200);
            game.protectedAreas = List.of();
            team = game.new EquipScoreRace(DyeColor.RED, "red");
            set(JocEquips.class, game, "Equips", new ArrayList<>(List.of(team)));
        }

        Location at(int x, int y, int z) { return new Location(world, x, y, z); }
        Material material(int x, int y, int z) { return terrain.getOrDefault(new Cell(x, y, z), Material.AIR); }
        Block block(int x, int y, int z) {
            Cell cell = new Cell(x, y, z);
            return stub(Block.class, (method, args) -> switch (method) {
                case "getWorld" -> world;
                case "getLocation" -> at(x, y, z);
                case "getType" -> terrain.getOrDefault(cell, Material.AIR);
                case "setType" -> { terrain.put(cell, (Material) args[0]); yield null; }
                case "isPassable" -> material(x, y, z) == Material.AIR || material(x, y, z) == Material.WATER;
                case "isLiquid" -> material(x, y, z) == Material.WATER;
                default -> null;
            });
        }
        TheTowers.BridgeLane lane(int fromX, int z, int endX) {
            return new TheTowers.BridgeLane(TheTowers.expandBridgeRoute(List.of(at(fromX, 0, z), at(endX, 0, z)), game.arenaBounds));
        }
        void routes(TheTowers.BridgeLane... lanes) throws Exception {
            set(TheTowers.class, game, "bridgeLanes", List.of(List.of(lanes), List.of()));
        }
        TheTowers.BridgeBuilder builder() { return (TheTowers.BridgeBuilder) game.minions().getFirst(); }
        void walkToEnd(TheTowers.BridgeBuilder builder) {
            int attempts = 0;
            while (game.minions().contains(builder) && attempts++ < 200) {
                builder.buildAndWalk();
                Body body = game.bodies.get(builder.entityId());
                if (body.destination != null) body.location = body.destination.clone();
            }
            assertTrue(attempts < 200, "the simulated physical walk reaches the middle");
        }
    }

    private static final class TestGame extends TheTowers {
        boolean running = true;
        int second;
        final Map<UUID, Body> bodies = new HashMap<>();
        @Override public boolean JocEnMarxa() { return running; }
        @Override public int segonsTranscorreguts() { return second; }
        @Override public ArrayList<Player> getViewers() { return new ArrayList<>(); }
        @Override public void enlist(Minion minion, Location at) {
            try {
                Body body = new Body(at);
                bodies.put(body.id, body);
                entities.put(body.id, body.mob);
                set(Minion.class, minion, "entityId", body.id);
                @SuppressWarnings("unchecked") List<Minion> army = (List<Minion>) field(JocEquips.class, this, "minions");
                army.add(minion);
            } catch (Exception failure) { throw new AssertionError(failure); }
        }
    }

    private static final class Body {
        final UUID id = UUID.randomUUID();
        final Mob mob;
        Location location, destination, requestedDestination;
        boolean alive = true;
        Body(Location spawn) {
            location = spawn;
            Pathfinder pathfinder = stub(Pathfinder.class, (method, args) -> switch (method) {
                case "findPath" -> {
                    requestedDestination = ((Location) args[0]).clone();
                    assertEquals(0, args[1], "each step requests its exact native endpoint");
                    yield stub(Pathfinder.PathResult.class, (name, values) -> name.equals("canReachFinalPoint") ? true : null);
                }
                case "moveTo" -> { destination = requestedDestination.clone(); yield true; }
                case "stopPathfinding" -> { destination = null; yield null; }
                default -> null;
            });
            mob = stub(Mob.class, (method, args) -> switch (method) {
                case "getUniqueId" -> id;
                case "getWorld" -> location.getWorld();
                case "getLocation" -> location.clone();
                case "getPathfinder" -> pathfinder;
                case "isValid" -> alive;
                case "isDead" -> !alive;
                case "remove" -> { alive = false; yield null; }
                default -> null;
            });
        }
    }

    private interface Answer { Object call(String method, Object[] args); }
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
    private static void set(Class<?> type, Object target, String name, Object value) throws Exception {
        Field field = type.getDeclaredField(name); field.setAccessible(true); field.set(target, value);
    }
    private static Object field(Class<?> type, Object target, String name) throws Exception {
        Field field = type.getDeclaredField(name); field.setAccessible(true); return field.get(target);
    }
}
