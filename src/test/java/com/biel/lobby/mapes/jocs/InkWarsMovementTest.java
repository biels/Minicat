package com.biel.lobby.mapes.jocs;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.bukkit.util.VoxelShape;

import com.biel.lobby.mapes.jocs.InkWars.InkWarsPlayerInfo.Squid;
import com.biel.lobby.mapes.jocs.InkWars.InkWarsPlayerInfo.Squid.Keys;
import com.biel.lobby.mapes.jocs.inkwars.SquidCollision;

/** Runs the real movement controller against deterministic collision shapes without a server. */
public final class InkWarsMovementTest {
    public static void main(String[] args) throws Exception {
        installSoundRegistry();
        slabLanding();
        wallClimbAndCrest();
        wallJumpMomentum();
        wallRecaptureSuppression();
        standingClearance();
        floorStep();
        stairStep();
        insideWallCorner();
        outsideWallCorner();
        ceilingAndRoof();
        wallGap();
        ceilingControlFrame();
        System.out.println("InkWars movement controller checks passed");
    }

    private static void installSoundRegistry() throws Exception {
        // Paper resolves Sound constants through a server service; the fixture only needs opaque sound values.
        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Object allocator = unsafeField.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = proxy(io.papermc.paper.registry.RegistryAccess.class, (object, method, args) -> {
            if (method.getName().equals("getRegistry")) {
                return proxy(Registry.class, (registry, operation, values) -> {
                    if (operation.getName().equals("get") || operation.getName().equals("getOrThrow")) {
                        return proxy(Sound.class, InkWarsMovementTest::defaultValue);
                    }
                    return defaultValue(registry, operation, values);
                });
            }
            return defaultValue(object, method, args);
        });
        Object base = unsafeClass.getMethod("staticFieldBase", Field.class).invoke(allocator, providerField);
        long offset = (long) unsafeClass.getMethod("staticFieldOffset", Field.class).invoke(allocator, providerField);
        unsafeClass.getMethod("putObject", Object.class, long.class, Object.class)
                .invoke(allocator, base, offset, Optional.of(provider));
    }

    private static void slabLanding() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(0, 0, 0, Material.STONE_SLAB, new BoundingBox(0, 0, 0, 1, 0.5, 1));
        Squid squid = fixture.squid(new Vector(0.5, 2, 0.5), InkWars.Surface.AIR);
        squid.verticalSpeed = -2;
        squid.tickAir(keys(new Vector(), false));
        require(squid.surface == InkWars.Surface.FLOOR, "falling squid must land on slab");
        close(0.5 + InkWars.SQUID_RIDE_HEIGHT, squid.centre.getY(), "landing follows slab top");
        close(0.5, squid.centre.getX(), "landing preserves horizontal position");
    }

    private static void floorStep() throws Exception {
        Fixture fixture = new Fixture();
        for (int x = -1; x <= 3; x++) fixture.add(x, 0, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(1, 1, 0, Material.STONE_SLAB, new BoundingBox(0, 0, 0, 1, 0.5, 1));
        fixture.add(2, 1, 0, Material.STONE_SLAB, new BoundingBox(0, 0, 0, 1, 0.5, 1));
        Squid squid = fixture.squid(new Vector(0.5, 1.5, 0.5), InkWars.Surface.FLOOR);
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.4;
        for (int tick = 0; tick < 4; tick++) fixture.move(squid, keys(new Vector(1, 0, 0), false));
        require(squid.surface == InkWars.Surface.FLOOR, "slab step remains floor movement");
        require(squid.centre.getX() > 1.3, "squid moves onto slab without stalling");
        close(2, squid.centre.getY(), "step follows slab elevation");
    }

    private static void ceilingControlFrame() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(0, 3, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.squid(new Vector(0.5, 2.5, 0.5), InkWars.Surface.AIR);
        squid.controlForward = new Vector(0, 0, 1);
        squid.controlRight = new Vector(-1, 0, 0);
        squid.heading = new Vector(0, 0, 1);
        squid.speed = 0.3;
        Block obstacle = fixture.block(0, 3, 0);
        squid.attachToCeiling(new Squid.Contact(obstacle, BlockFace.DOWN, 0, new Vector(0.5, 3, 0.5), obstacle.getBoundingBox()), new Vector(0, 1, 0));
        squid.attachToWall(new Squid.Contact(obstacle, BlockFace.NORTH, 0, new Vector(0.5, 3.5, 0), obstacle.getBoundingBox()), new Vector(0, -1, 0));
        squid.landOnFloor(new Vector(0, 0, -1), obstacle, 4);
        Vector expectedRight = squid.controlForward.clone().crossProduct(new Vector(0, 1, 0)).normalize();
        require(expectedRight.dot(squid.controlRight) > 0.999, "ceiling route preserves floor control handedness");
    }

    private static void stairStep() throws Exception {
        Fixture fixture = new Fixture();
        for (int x = -1; x <= 3; x++) fixture.add(x, 0, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(1, 1, 0, Material.STONE_STAIRS, new BoundingBox(0, 0, 0, 1, 0.5, 1), new BoundingBox(0.5, 0.5, 0, 1, 1, 1));
        fixture.add(2, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.squid(new Vector(0.5, 1.5, 0.5), InkWars.Surface.FLOOR);
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.3;
        for (int tick = 0; tick < 6; tick++) fixture.move(squid, keys(new Vector(1, 0, 0), false));
        require(squid.surface == InkWars.Surface.FLOOR, "stair traversal stays on floor");
        require(squid.centre.getX() > 2, "squid traverses both stair treads");
        close(2.5, squid.centre.getY(), "stair reaches upper floor");
    }

    private static void insideWallCorner() throws Exception {
        Fixture fixture = new Fixture();
        for (int z = 0; z <= 2; z++) fixture.add(1, 1, z, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(0, 1, 2, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(-1, 1, 2, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.wallSquid(new Vector(0.69, 1.5, 0.5), new Vector(0, 0, 1));
        for (int tick = 0; tick < 7; tick++) fixture.move(squid, keys(new Vector(0, 0, 1), false));
        require(squid.surface == InkWars.Surface.WALL && squid.wallSide == BlockFace.SOUTH, "inside corner changes to connected wall");
        require(squid.heading.getX() < -0.9, "inside corner bends momentum away from old wall");
    }

    private static void outsideWallCorner() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(1, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.wallSquid(new Vector(0.69, 1.5, 0.5), new Vector(0, 0, 1));
        for (int tick = 0; tick < 4; tick++) fixture.move(squid, keys(new Vector(0, 0, 1), false));
        require(squid.surface == InkWars.Surface.WALL && squid.wallSide == BlockFace.NORTH, "outside corner wraps onto far face");
        require(squid.heading.getX() > 0.9, "outside corner carries momentum around edge");
    }

    private static void ceilingAndRoof() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(1, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(1, 2, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        for (int x = -2; x <= 1; x++) fixture.add(x, 3, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.wallSquid(new Vector(0.69, 1.5, 0.5), new Vector(0, 1, 0));
        boolean ceilingSeen = false;
        boolean edgeWallSeen = false;
        for (int tick = 0; tick < 45; tick++) {
            fixture.move(squid, keys(new Vector(1, 0, 0), false));
            ceilingSeen |= squid.surface == InkWars.Surface.CEILING;
            edgeWallSeen |= ceilingSeen && squid.surface == InkWars.Surface.WALL;
            if (edgeWallSeen && squid.surface == InkWars.Surface.FLOOR) break;
        }
        require(ceilingSeen, "wall climb bends onto overhang underside");
        require(edgeWallSeen, "ceiling edge wraps onto outer wall");
        require(squid.surface == InkWars.Surface.FLOOR, "outer wall crests overhang roof");
        close(4.5, squid.centre.getY(), "overhang roof height");
    }

    private static void wallGap() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(1, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.wallSquid(new Vector(0.69, 1.5, 0.5), new Vector(0, 0.6, 0.8));
        fixture.solids.clear();
        fixture.blocks.clear();
        // Stale contact stays below a distant top, modeling a missing wall section rather than a roof.
        squid.gripContact = new Squid.Contact(squid.gripped, BlockFace.WEST, 0, new Vector(1, 1.5, 0.5), new BoundingBox(1, 1, 0, 2, 10, 1));
        for (int tick = 0; tick < 8 && squid.surface == InkWars.Surface.WALL; tick++) fixture.move(squid, keys(new Vector(1, 0, 0), false));
        require(squid.surface == InkWars.Surface.AIR, "missing wall releases after contact grace");
        require(squid.verticalSpeed > 0, "leaving wall gap preserves upward velocity");
        require(squid.speed > 0, "leaving wall gap preserves horizontal velocity");
    }

    private static void wallClimbAndCrest() throws Exception {
        Fixture fixture = new Fixture();
        for (int x = -2; x <= 3; x++) fixture.add(x, 0, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        for (int y = 1; y <= 3; y++) fixture.add(1, y, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.squid(new Vector(0.55, 1 + InkWars.SQUID_RIDE_HEIGHT, 0.5), InkWars.Surface.FLOOR);
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.3;
        Keys forward = keys(new Vector(1, 0, 0), false);
        squid.tickFloor(forward);
        require(squid.surface == InkWars.Surface.WALL, "moving into wall must attach");
        double attachmentHeight = squid.centre.getY();
        for (int tick = 0; tick < 30 && squid.surface == InkWars.Surface.WALL; tick++) squid.tickWall(forward);
        require(squid.centre.getY() > attachmentHeight, "holding forward must climb");
        require(squid.surface == InkWars.Surface.FLOOR, "continuous wall must crest onto roof");
        close(4 + InkWars.SQUID_RIDE_HEIGHT, squid.centre.getY(), "crest reaches roof height");
        require(squid.centre.getX() >= 1, "crest places roof under body");
        require(squid.heading.getX() > 0.9, "climb momentum bends forward across roof");
    }

    private static void wallJumpMomentum() throws Exception {
        Fixture fixture = new Fixture();
        Squid squid = fixture.squid(new Vector(0.69, 2, 0.5), InkWars.Surface.WALL);
        squid.wallSide = BlockFace.EAST;
        squid.heading = new Vector(0, 0.6, 0.8);
        squid.speed = 0.5;
        squid.tickWall(keys(new Vector(), true));
        require(squid.surface == InkWars.Surface.AIR, "wall jump detaches");
        close(0.4, squid.heading.getZ() * squid.speed, "wall jump retains sideways momentum");
        require(squid.heading.getX() * squid.speed < -0.39, "wall jump pushes away");
        require(squid.verticalSpeed > 0.3, "wall jump preserves upward movement and adds lift");
        require(squid.detachTicks > 0 && squid.detachedFace == BlockFace.WEST, "jump protects against immediate recapture");
    }

    private static void standingClearance() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(0, 0, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        fixture.add(0, 2, 0, Material.STONE_SLAB, new BoundingBox(0, 0, 0, 1, 0.5, 1));
        Squid squid = fixture.squid(new Vector(0.5, 1.5, 0.5), InkWars.Surface.FLOOR);
        require(!squid.standingClear(squid.standingSpot()), "low ceiling prevents human expansion");
        require(squid.standingClear(new Location(fixture.world, 2.5, 1, 0.5)), "open location permits standing");
        squid.surface = InkWars.Surface.CEILING;
        squid.centre = new Vector(0.5, 1.69, 0.5);
        require(squid.standingSpot().getY() + 1.8 <= squid.centre.getY() + 1e-8, "ceiling conversion expands downward");
        squid.surface = InkWars.Surface.WALL;
        squid.wallSide = BlockFace.EAST;
        double centerX = squid.centre.getX();
        require(squid.standingSpot().getX() < centerX, "wall conversion separates outward");
    }

    private static void wallRecaptureSuppression() throws Exception {
        Fixture fixture = new Fixture();
        fixture.add(1, 1, 0, Material.WHITE_CONCRETE, new BoundingBox(0, 0, 0, 1, 1, 1));
        Squid squid = fixture.squid(new Vector(0.6, 1.5, 0.5), InkWars.Surface.AIR);
        squid.heading = new Vector(1, 0, 0);
        squid.speed = 0.3;
        squid.detachTicks = 4;
        squid.detachedFace = BlockFace.WEST;
        squid.tickAir(keys(new Vector(1, 0, 0), false));
        require(squid.surface == InkWars.Surface.AIR, "recent wall jump must not reattach despite inward input");
        require(squid.centre.getX() <= 1 - InkWars.SQUID_RADIUS, "detach protection must still collide with wall");

        Squid glancing = fixture.squid(new Vector(0.6, 1.5, 0.5), InkWars.Surface.AIR);
        glancing.heading = new Vector(1, 0, 0);
        glancing.speed = 0.3;
        glancing.tickAir(keys(new Vector(), false));
        require(glancing.surface == InkWars.Surface.AIR, "air contact without inward input must not capture");
    }

    private static Keys keys(Vector direction, boolean jump) {
        return new Keys(direction, jump, direction.lengthSquared() > 0 ? 1 : 0, 0);
    }

    private static final class TestGame extends InkWars {
        private World fixtureWorld;
        private Player fixturePlayer;

        @Override public World getWorld() { return fixtureWorld; }
        @Override public boolean isPaintable(Block block) { return !block.isPassable(); }

        class Info extends InkWarsPlayerInfo {
            @Override protected Player getPlayer() { return fixturePlayer; }
        }
    }

    private static final class Fixture {
        private final Map<String, Block> blocks = new HashMap<>();
        private final List<Block> solids = new ArrayList<>();
        private final UUID worldId = UUID.randomUUID();
        private final World world;
        private final TestGame game;

        Fixture() throws Exception {
            world = proxy(World.class, (object, method, args) -> switch (method.getName()) {
                case "getBlockAt" -> args[0] instanceof Location location
                        ? block(location.getBlockX(), location.getBlockY(), location.getBlockZ())
                        : block((int) args[0], (int) args[1], (int) args[2]);
                case "getMinHeight" -> -64;
                case "getMaxHeight" -> 320;
                case "getUID" -> worldId;
                case "getName" -> "movement-fixture";
                case "rayTraceBlocks" -> rayTrace((Location) args[0], (Vector) args[1], (double) args[2]);
                case "playSound" -> null;
                default -> defaultValue(object, method, args);
            });
            // The normal constructor registers a live plugin event bus; this fixture only exercises geometry.
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Object allocator = unsafeField.get(null);
            game = (TestGame) unsafeClass.getMethod("allocateInstance", Class.class).invoke(allocator, TestGame.class);
            game.fixtureWorld = world;
            game.fixturePlayer = proxy(Player.class, (object, method, args) -> switch (method.getName()) {
                case "getLocation" -> new Location(world, 0, 2, 0, -90, 0);
                case "getWorld" -> world;
                case "playSound" -> null;
                default -> defaultValue(object, method, args);
            });
        }

        Squid squid(Vector center, InkWars.Surface surface) {
            Squid squid = game.new Info().new Squid();
            squid.centre = center;
            squid.surface = surface;
            squid.controlForward = new Vector(1, 0, 0);
            squid.controlRight = new Vector(0, 0, 1);
            return squid;
        }

        Squid wallSquid(Vector center, Vector heading) {
            Squid squid = squid(center, InkWars.Surface.WALL);
            squid.wallSide = BlockFace.EAST;
            squid.heading = heading.clone();
            squid.controlForward = heading.clone();
            squid.speed = 0.3;
            squid.gripped = block(1, 1, 0);
            squid.gripContact = new Squid.Contact(squid.gripped, BlockFace.WEST, 0, new Vector(1, center.getY(), center.getZ()), squid.gripped.getBoundingBox());
            return squid;
        }

        void move(Squid squid, Keys keys) {
            int steps = Math.max(1, (int) Math.ceil((squid.speed + (squid.surface == InkWars.Surface.AIR ? Math.abs(squid.verticalSpeed) : 0) + InkWars.SQUID_ACCELERATION) / InkWars.SQUID_MOVEMENT_STEP));
            squid.movementFraction = 1D / steps;
            for (int step = 0; step < steps; step++) {
                squid.firstMovementStep = step == 0;
                switch (squid.surface) {
                    case FLOOR -> squid.tickFloor(keys);
                    case WALL -> squid.tickWall(keys);
                    case CEILING -> squid.tickCeiling(keys);
                    case AIR -> squid.tickAir(keys);
                }
                for (Block block : solids) for (BoundingBox local : block.getCollisionShape().getBoundingBoxes()) {
                    require(!SquidCollision.overlaps(squid.centre, new Vector(InkWars.SQUID_RADIUS, InkWars.SQUID_RADIUS, InkWars.SQUID_RADIUS), local.clone().shift(block.getLocation())),
                            "body overlaps " + block.getLocation() + " at " + squid.centre + " on " + squid.surface);
                }
            }
        }

        void add(int x, int y, int z, Material material, BoundingBox... localShapes) {
            Block block = makeBlock(x, y, z, material, localShapes);
            blocks.put(key(x, y, z), block);
            solids.add(block);
        }

        Block block(int x, int y, int z) {
            return blocks.computeIfAbsent(key(x, y, z), ignored -> makeBlock(x, y, z, Material.AIR));
        }

        Block makeBlock(int x, int y, int z, Material material, BoundingBox... localShapes) {
            BoundingBox aggregate = localShapes.length == 0 ? new BoundingBox() : localShapes[0].clone();
            for (BoundingBox localShape : localShapes) aggregate.union(localShape);
            VoxelShape shape = proxy(VoxelShape.class, (object, method, args) -> switch (method.getName()) {
                case "getBoundingBoxes" -> List.of(localShapes);
                case "overlaps" -> java.util.Arrays.stream(localShapes).anyMatch(box -> box.overlaps((BoundingBox) args[0]));
                default -> defaultValue(object, method, args);
            });
            return proxy(Block.class, (object, method, args) -> switch (method.getName()) {
                case "getX" -> x;
                case "getY" -> y;
                case "getZ" -> z;
                case "getWorld" -> world;
                case "getLocation" -> new Location(world, x, y, z);
                case "getType" -> material;
                case "isPassable", "isEmpty" -> localShapes.length == 0;
                case "getCollisionShape" -> shape;
                case "getBoundingBox" -> aggregate.clone().shift(x, y, z);
                case "getRelative" -> {
                    BlockFace face = (BlockFace) args[0];
                    int distance = args.length == 2 ? (int) args[1] : 1;
                    yield block(x + face.getModX() * distance, y + face.getModY() * distance, z + face.getModZ() * distance);
                }
                default -> defaultValue(object, method, args);
            });
        }

        RayTraceResult rayTrace(Location origin, Vector direction, double distance) {
            RayTraceResult nearest = null;
            double nearestDistance = Double.POSITIVE_INFINITY;
            for (Block block : solids) for (BoundingBox local : block.getCollisionShape().getBoundingBoxes()) {
                RayTraceResult hit = local.clone().shift(block.getLocation()).rayTrace(origin.toVector(), direction, distance);
                if (hit != null && hit.getHitPosition().distanceSquared(origin.toVector()) < nearestDistance) {
                    nearestDistance = hit.getHitPosition().distanceSquared(origin.toVector());
                    nearest = new RayTraceResult(hit.getHitPosition(), block, hit.getHitBlockFace());
                }
            }
            return nearest;
        }
    }

    private static String key(int x, int y, int z) { return x + ":" + y + ":" + z; }

    private static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler));
    }

    private static Object defaultValue(Object object, Method method, Object[] args) {
        return switch (method.getName()) {
            case "equals" -> object == args[0];
            case "hashCode" -> System.identityHashCode(object);
            case "toString" -> "movement fixture " + object.getClass().getInterfaces()[0].getSimpleName();
            default -> {
                if (method.getReturnType() == boolean.class) yield false;
                if (method.getReturnType() == int.class) yield 0;
                if (method.getReturnType() == long.class) yield 0L;
                if (method.getReturnType() == double.class) yield 0D;
                if (method.getReturnType() == float.class) yield 0F;
                yield null;
            }
        };
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void close(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 1e-6) throw new AssertionError(message + ": " + actual);
    }
}
