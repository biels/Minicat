package com.biel.lobby.mapes.jocs.parkour;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.biel.lobby.mapes.jocs.parkour.utils.CourseProfile;
import com.biel.lobby.mapes.jocs.parkour.utils.CourseRun;

class ImportedCourseControllerTest {
    private static final CourseProfile.BlockPosition START = new CourseProfile.BlockPosition(1, -61, 63);
    private static final CourseProfile.Checkpoint CHECKPOINT = new CourseProfile.Checkpoint("tower-plate",
            new CourseProfile.BlockPosition(3, 144, 62), new CourseProfile.Position(3.5, 144.0625, 62.5, 0, 0), null);
    private static final CourseProfile PROFILE = new CourseProfile(1, "spiral3",
            new CourseProfile.Start(new CourseProfile.Position(1.5, -61, 60.5, 0, 10), START),
            new CourseProfile.Sphere(new CourseProfile.Point(0, 310, 100), 30),
            new CourseProfile.Failure(-64, Set.of("LAVA", "FIRE", "SOUL_FIRE"),
                    Set.of("FALL", "VOID", "LAVA", "FIRE", "FIRE_TICK"), null, List.of()), List.of(CHECKPOINT));

    @BeforeAll static void materialRegistry() throws Exception {
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        sun.misc.Unsafe allocator = (sun.misc.Unsafe) field.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = stub(io.papermc.paper.registry.RegistryAccess.class, (method, args) ->
                method.equals("getRegistry") ? stub(Registry.class, (operation, values) -> {
                    if (!operation.equals("get") && !operation.equals("getOrThrow")) return null;
                    return stub(BlockType.class, (blockMethod, ignored) ->
                            blockMethod.equals("isSolid") && values[0].toString().equals("minecraft:stone"));
                }) : null);
        allocator.putObject(allocator.staticFieldBase(providerField), allocator.staticFieldOffset(providerField), Optional.of(provider));
    }

    @Test void startContactBeginsTimeAndExactCheckpointHasOneReward() {
        Fixture fixture = new Fixture();
        Runner runner = fixture.add("Biel");
        ImportedCourseController controller = fixture.controller();
        controller.initializePlayer(runner.player);
        assertEquals(GameMode.ADVENTURE, runner.mode);
        assertEquals(10, runner.location.getPitch());
        assertFalse(controller.getRun("Biel").started());
        runner.location = new Location(fixture.world, 1.5, -61.0625, 63.5);
        controller.tick(8);
        assertFalse(controller.getRun("Biel").started(), "standing below the plate is not contact");
        runner.location = location(fixture.world, START.safePosition(0, 0));
        controller.tick(9);
        assertTrue(controller.getRun("Biel").started());
        runner.location = location(fixture.world, CHECKPOINT.safePosition());
        controller.tick(20);
        controller.tick(21);
        assertEquals(List.of("Biel"), fixture.checkpoints);
        assertEquals("tower-plate", controller.getRun("Biel").checkpointId());
    }

    @Test void recoveryTeleportsAliveToSafePlateAndClearsFallAndVelocityOnce() {
        Fixture fixture = new Fixture();
        Runner runner = fixture.add("Biel");
        ImportedCourseController controller = fixture.start(runner);
        runner.location = location(fixture.world, CHECKPOINT.safePosition());
        controller.tick(20);
        runner.location = new Location(fixture.world, 4, -65, 61, 157, 25);
        runner.velocity = new Vector(2, -3, 1);
        runner.fallDistance = 40;
        runner.fireTicks = 50;
        controller.tick(25);
        assertEquals(3.5, runner.location.getX());
        assertEquals(144.0625, runner.location.getY());
        assertEquals(157, runner.location.getYaw());
        assertEquals(25, runner.location.getPitch());
        assertEquals(new Vector(), runner.velocity);
        assertEquals(0, runner.fallDistance);
        assertEquals(0, runner.fireTicks);
        assertEquals(1, controller.getRun("Biel").failures());
        assertFalse(controller.recover(runner.player));
        controller.tick(26);
        assertEquals(List.of("Biel"), fixture.failures);
        assertEquals(List.of("Biel"), fixture.checkpoints);
        assertEquals(GameMode.ADVENTURE, runner.mode);
    }

    @Test void unrelatedPlateAndAirAboveACheckpointCannotClaimProgress() {
        Fixture fixture = new Fixture();
        Runner runner = fixture.add("Biel");
        ImportedCourseController controller = fixture.start(runner);
        fixture.blocks.put(new Cell(-28, 147, 101), Material.CRIMSON_PRESSURE_PLATE);
        runner.location = new Location(fixture.world, -27.5, 147.0625, 101.5);
        controller.tick(20);
        runner.location = new Location(fixture.world, 3.5, 145, 62.5);
        controller.tick(21);
        assertTrue(fixture.checkpoints.isEmpty());
        assertNull(controller.getRun("Biel").checkpointId());
    }

    @Test void reconnectUsesNewEntityAndRetainsCheckpointAndTimer() {
        Fixture fixture = new Fixture();
        Runner original = fixture.add("Biel");
        ImportedCourseController controller = fixture.start(original);
        original.location = location(fixture.world, CHECKPOINT.safePosition());
        controller.tick(20);
        original.online = false;
        controller.tick(30);
        Runner replacement = fixture.add("Biel");
        replacement.location.setYaw(100);
        controller.resumePlayer(replacement.player);
        assertEquals(144.0625, replacement.location.getY());
        assertEquals(100, replacement.location.getYaw());
        assertEquals(0, controller.getRun("Biel").failures());
        assertEquals(22, controller.getRun("Biel").elapsedTicks(30));
        controller.tick(38);
        assertEquals(1, fixture.checkpoints.size());
        assertSame(CHECKPOINT.safePosition(), controller.getRun("Biel").returnPosition());
    }

    @Test void finishedOfflineSpectatingAndInactiveSeatsIgnoreHazards() {
        Fixture fixture = new Fixture();
        Runner runner = fixture.add("Biel");
        ImportedCourseController controller = fixture.start(runner);
        runner.active = false;
        runner.location.setY(-65);
        controller.tick(20);
        runner.active = true;
        runner.online = false;
        controller.tick(21);
        runner.online = true;
        runner.mode = GameMode.SPECTATOR;
        controller.tick(22);
        runner.mode = GameMode.ADVENTURE;
        runner.location = new Location(fixture.world, 0, 310, 100);
        controller.tick(25);
        controller.tick(26);
        assertEquals(List.of(17L), fixture.finishedTicks);
        runner.location.setY(-65);
        controller.tick(30);
        assertFalse(controller.recover(runner.player));
        assertTrue(fixture.failures.isEmpty());
        controller.resumePlayer(runner.player);
        assertEquals(GameMode.SPECTATOR, runner.mode);
    }

    @Test void twoWorldsWithSamePlayerNameNeverShareProgress() {
        Fixture first = new Fixture(), second = new Fixture();
        Runner firstRunner = first.add("Biel"), secondRunner = second.add("Biel");
        ImportedCourseController firstController = first.start(firstRunner), secondController = second.start(secondRunner);
        firstRunner.location = location(first.world, CHECKPOINT.safePosition());
        firstController.tick(20);
        assertNull(secondController.getRun("Biel").checkpointId());
        firstRunner.currentWorld = second.world;
        firstRunner.location = new Location(second.world, 0, -65, 0);
        firstController.tick(30);
        assertFalse(firstController.recover(firstRunner.player));
        assertTrue(first.failures.isEmpty());
        assertTrue(second.failures.isEmpty());
        firstController.clear();
        assertNull(firstController.getRun("Biel"));
        assertNotNull(secondController.getRun("Biel"));
    }

    @Test void nativeBootsAllowLavaAndUndeclaredWaterIsNotAFailure() {
        Fixture fixture = new Fixture();
        Runner runner = fixture.add("Biel");
        ImportedCourseController controller = fixture.start(runner);
        fixture.blocks.put(new Cell(20, 1, 20), Material.LAVA);
        runner.location = new Location(fixture.world, 20.5, 1, 20.5);
        runner.tags.add("fire_boots");
        controller.tick(20);
        assertFalse(controller.shouldRecoverDamage(runner.player, DamageCause.LAVA));
        assertTrue(controller.shouldRecoverDamage(runner.player, DamageCause.FALL));
        assertTrue(fixture.failures.isEmpty());
        fixture.blocks.put(new Cell(20, 1, 20), Material.WATER);
        runner.tags.clear();
        controller.tick(21);
        assertTrue(fixture.failures.isEmpty());
        fixture.blocks.put(new Cell(20, 1, 20), Material.LAVA);
        controller.tick(22);
        assertEquals(1, fixture.failures.size());
    }

    @Test void invalidPlateHeadroomOrSupportPreventsLaunch() {
        Fixture missingPlate = new Fixture();
        missingPlate.blocks.remove(new Cell(3, 144, 62));
        assertThrows(IllegalArgumentException.class, missingPlate::controller);
        Fixture blockedHead = new Fixture();
        blockedHead.blocks.put(new Cell(3, 145, 62), Material.STONE);
        assertThrows(IllegalArgumentException.class, blockedHead::controller);
        Fixture missingSupport = new Fixture();
        missingSupport.blocks.remove(new Cell(3, 143, 62));
        assertThrows(IllegalArgumentException.class, missingSupport::controller);
    }

    @Test void undamagingFoundationFloorContactReturnsTheLivingRunner() {
        Fixture fixture = new Fixture(withSurfaces(new CourseProfile.Surface("BEDROCK", -64, -64)));
        Runner runner = fixture.add("Biel");
        ImportedCourseController controller = fixture.start(runner);
        runner.location = location(fixture.world, CHECKPOINT.safePosition());
        controller.tick(20);
        fixture.blocks.put(new Cell(20, -64, 20), Material.BEDROCK);
        runner.location = new Location(fixture.world, 20.5, -63, 20.5);
        runner.fallDistance = 0;
        controller.tick(30);
        assertEquals(144.0625, runner.location.getY());
        assertEquals(1, controller.getRun("Biel").failures());
        assertEquals(List.of("Biel"), fixture.failures);
        assertEquals(0, runner.damageCalls, "surface failure needs no damage or death event");
        assertEquals(GameMode.ADVENTURE, runner.mode);
        controller.tick(31);
        assertEquals(1, fixture.failures.size());
    }

    @Test void otherBedrockHeightsAndLowerWaterOrAirRemainSafe() {
        for (Material material : List.of(Material.BEDROCK, Material.WATER, Material.AIR)) {
            Fixture fixture = new Fixture(withSurfaces(new CourseProfile.Surface("BEDROCK", -64, -64)));
            Runner runner = fixture.add("Biel");
            ImportedCourseController controller = fixture.start(runner);
            int blockY = material == Material.BEDROCK ? -62 : -64;
            fixture.blocks.put(new Cell(20, blockY, 20), material);
            runner.location = new Location(fixture.world, 20.5, blockY + 1, 20.5);
            controller.tick(20);
            assertEquals(blockY + 1, runner.location.getY(), material.name());
            assertTrue(fixture.failures.isEmpty(), material.name());
        }
    }

    @Test void headContactDoesNotClaimADeclaredFailureSurface() {
        Fixture fixture = new Fixture(withSurfaces(new CourseProfile.Surface("BEDROCK", 10, 10)));
        Runner runner = fixture.add("Biel");
        ImportedCourseController controller = fixture.start(runner);
        fixture.blocks.put(new Cell(20, 10, 20), Material.BEDROCK);
        runner.location = new Location(fixture.world, 20.5, 9, 20.5);
        controller.tick(20);
        assertEquals(9, runner.location.getY());
        assertTrue(fixture.failures.isEmpty());
    }

    @Test void profileWithoutSurfacesKeepsItsPreviousFoundationBehavior() {
        Fixture fixture = new Fixture();
        Runner runner = fixture.add("Biel");
        ImportedCourseController controller = fixture.start(runner);
        fixture.blocks.put(new Cell(20, -64, 20), Material.BEDROCK);
        runner.location = new Location(fixture.world, 20.5, -63, 20.5);
        controller.tick(20);
        assertEquals(-63, runner.location.getY());
        assertTrue(fixture.failures.isEmpty());
    }

    @Test void invalidSurfaceMaterialOrWorldLayerPreventsLaunch() {
        assertThrows(IllegalArgumentException.class,
                () -> new Fixture(withSurfaces(new CourseProfile.Surface("UNKNOWN_MATERIAL", -64, -64))).controller());
        assertThrows(IllegalArgumentException.class,
                () -> new Fixture(withSurfaces(new CourseProfile.Surface("BEDROCK", -65, -64))).controller());
        assertThrows(IllegalArgumentException.class,
                () -> new Fixture(withSurfaces(new CourseProfile.Surface("BEDROCK", 320, 320))).controller());
    }

    private static CourseProfile withSurfaces(CourseProfile.Surface... surfaces) {
        CourseProfile.Failure failure = PROFILE.failure();
        return new CourseProfile(PROFILE.schemaVersion(), PROFILE.engine(), PROFILE.start(), PROFILE.finish(),
                new CourseProfile.Failure(failure.minY(), failure.hazardMaterials(), failure.damageCauses(),
                        failure.defaultMaxDrop(), failure.volumes(), List.of(surfaces)), PROFILE.checkpoints());
    }

    private record Cell(int x, int y, int z) {}
    private static Location location(World world, CourseProfile.Position position) {
        return new Location(world, position.x(), position.y(), position.z(), position.yaw(), position.pitch());
    }

    private static final class Fixture {
        final CourseProfile profile;
        final Map<Cell, Material> blocks = new HashMap<>();
        final Map<String, Runner> runners = new HashMap<>();
        final List<String> checkpoints = new ArrayList<>(), failures = new ArrayList<>();
        final List<Long> finishedTicks = new ArrayList<>();
        final World world = stub(World.class, (method, args) -> switch (method) {
            case "getName" -> "parkour-test";
            case "getMinHeight" -> -64;
            case "getMaxHeight" -> 320;
            case "getPlayers" -> runners.values().stream().map(runner -> runner.player).toList();
            case "getBlockAt" -> {
                Cell cell = args[0] instanceof Location location ? new Cell(location.getBlockX(), location.getBlockY(), location.getBlockZ())
                        : new Cell((Integer) args[0], (Integer) args[1], (Integer) args[2]);
                Material material = blocks.getOrDefault(cell, Material.AIR);
                yield stub(Block.class, (blockMethod, ignored) -> switch (blockMethod) {
                    case "getType" -> material;
                    case "getY" -> cell.y();
                    case "isPassable" -> material != Material.STONE;
                    default -> null;
                });
            }
            default -> null;
        });

        Fixture() { this(PROFILE); }
        Fixture(CourseProfile profile) {
            this.profile = profile;
            blocks.put(new Cell(1, -62, 60), Material.STONE);
            for (CourseProfile.BlockPosition plate : List.of(START, CHECKPOINT.trigger())) {
                blocks.put(new Cell(plate.x(), plate.y(), plate.z()), Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
                blocks.put(new Cell(plate.x(), plate.y() - 1, plate.z()), Material.STONE);
            }
        }
        Runner add(String name) { Runner runner = new Runner(this, name); runners.put(name, runner); return runner; }
        ImportedCourseController controller() {
            return new ImportedCourseController(world, profile, player -> runners.get(player.getName()).active,
                    player -> checkpoints.add(player.getName()), player -> failures.add(player.getName()),
                    (player, ticks) -> finishedTicks.add(ticks));
        }
        ImportedCourseController start(Runner runner) {
            ImportedCourseController controller = controller();
            controller.initializePlayer(runner.player);
            runner.location = location(world, START.safePosition(0, 0));
            controller.tick(8);
            return controller;
        }
    }

    private static final class Runner {
        final Player player;
        final Set<String> tags = new HashSet<>();
        Location location;
        World currentWorld;
        GameMode mode = GameMode.SURVIVAL;
        Vector velocity = new Vector();
        float fallDistance;
        int fireTicks;
        int damageCalls;
        boolean online = true, active = true;
        Runner(Fixture fixture, String name) {
            currentWorld = fixture.world;
            location = new Location(currentWorld, 0, 0, 0);
            player = stub(Player.class, (method, args) -> switch (method) {
                case "getName" -> name;
                case "getWorld" -> currentWorld;
                case "isOnline" -> online;
                case "getGameMode" -> mode;
                case "setGameMode" -> { mode = (GameMode) args[0]; yield null; }
                case "getLocation" -> location.clone();
                case "getScoreboardTags" -> tags;
                case "teleport" -> { location = ((Location) args[0]).clone(); yield true; }
                case "setVelocity" -> { velocity = (Vector) args[0]; yield null; }
                case "setFallDistance" -> { fallDistance = (Float) args[0]; yield null; }
                case "setFireTicks" -> { fireTicks = (Integer) args[0]; yield null; }
                case "damage", "setHealth" -> { damageCalls++; yield null; }
                default -> null;
            });
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
            if (method.getReturnType() == float.class) return 0F;
            if (method.getReturnType() == double.class) return 0D;
            if (method.getReturnType() == long.class) return 0L;
            return null;
        }));
    }
}
