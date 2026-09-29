package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Proxy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Locale;
import java.util.Optional;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.Pathfinder.PathResult;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.MobGoals;

class SpringRobotControllerTest {
    @BeforeAll static void registries() throws Exception {
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        sun.misc.Unsafe allocator = (sun.misc.Unsafe) field.get(null);
        Field providerField = Class.forName("io.papermc.paper.registry.RegistryAccessHolder").getDeclaredField("INSTANCE");
        Object provider = stub(io.papermc.paper.registry.RegistryAccess.class, (method, args) ->
                method.equals("getRegistry") ? stub(Registry.class, (operation, values) -> {
                    if (!operation.equals("get") && !operation.equals("getOrThrow")) return null;
                    if (args[0].toString().toLowerCase(Locale.ROOT).contains("sound")) return stub(Sound.class, (m, a) -> null);
                    return stub(BlockType.class, (m, a) -> switch (m) {
                        case "isAir" -> values[0].toString().equals("minecraft:air");
                        case "isSolid" -> values[0].toString().equals("minecraft:stone") || values[0].toString().equals("minecraft:iron_block");
                        default -> null;
                    });
                }) : null);
        allocator.putObject(allocator.staticFieldBase(providerField), allocator.staticFieldOffset(providerField), Optional.of(provider));
    }

    @Test void ownedSpringerWarnsAndJumpsTowardTargetWithBoundedLift() {
        Fixture f = new Fixture();
        f.tick(40);
        assertFalse(f.robotAI);
        f.tick(19);
        assertEquals(1, f.velocities.size(), "windup only holds horizontal movement");
        f.tick(1);
        Vector jump = f.velocities.getLast();
        assertEquals(0.42, jump.getX(), 1e-9);
        assertEquals(0.62, jump.getY(), 1e-9);
        assertEquals(0, jump.getZ(), 1e-9);
        assertTrue(f.robotAI);
        f.grounded = false;
        f.tick(200);
        assertEquals(2, f.velocities.size(), "cooldown cannot create air jumps");
    }

    @Test void taserOnLaunchTickCancelsJumpAndRestoresAI() {
        Fixture f = new Fixture();
        f.tick(59);
        f.energized = true;
        f.tick(1);
        assertTrue(f.robotAI);
        assertEquals(1, f.velocities.size());
        f.tick(200);
        assertEquals(1, f.velocities.size());
    }

    @Test void deadInactiveSpectatorUnreachableAndUnownedTargetsCannotTriggerSpring() {
        for (int invalid = 0; invalid < 7; invalid++) {
            Fixture f = new Fixture();
            switch (invalid) {
                case 0 -> f.dead = true;
                case 1 -> f.active = false;
                case 2 -> f.mode = GameMode.SPECTATOR;
                case 3 -> f.playerLocation.setX(9);
                case 4 -> f.playerLocation.setY(6);
                case 5 -> f.lineOfSight = false;
                case 6 -> f.robots.clear();
            }
            f.tick(200);
            assertTrue(f.velocities.isEmpty());
            assertTrue(f.robotAI);
        }
    }

    @Test void targetBecomingSpectatorCancelsCommittedWindup() {
        Fixture f = new Fixture();
        f.tick(40);
        f.mode = GameMode.SPECTATOR;
        f.tick(20);
        assertTrue(f.robotAI);
        assertEquals(1, f.velocities.size());
    }

    @Test void removalAndCloseReleaseWindupAndPreservePreexistingDisabledAI() {
        Fixture removed = new Fixture();
        removed.tick(40);
        removed.robots.clear();
        removed.tick(1);
        assertTrue(removed.robotAI);
        assertEquals(1, removed.velocities.size());

        Fixture closed = new Fixture();
        closed.robotAI = false;
        closed.tick(40);
        closed.controller.close();
        closed.controller.close();
        closed.tick(200);
        assertFalse(closed.robotAI);
        assertEquals(1, closed.velocities.size());
    }

    @Test void elevatedPlayerMakesSpringerApproachBeforeWarningAndLaunchWithNativeAIEnabled() {
        Fixture f = new Fixture();
        f.robotLocation.setX(-5.5); f.robotLocation.setZ(0.5);
        f.playerLocation.setX(2.5); f.playerLocation.setY(5); f.playerLocation.setZ(0.5);
        f.platform(2, 4, 0);
        f.tick(40);
        assertNotNull(f.approachDestination);
        assertTrue(f.robotAI, "walking toward a launch point uses native navigation");
        assertTrue(f.velocities.isEmpty(), "no warning or jump before the robot reaches the launch point");
        f.robotLocation.setX(f.approachDestination.getX());
        f.robotLocation.setZ(f.approachDestination.getZ());
        f.tick(10);
        assertFalse(f.robotAI);
        f.tick(20);
        assertTrue(f.robotAI, "NoAI must be restored for native gravity and travel");
        assertEquals(0.82, f.velocities.getLast().getY(), 1e-9);
        assertTrue(Math.hypot(f.velocities.getLast().getX(), f.velocities.getLast().getZ()) <= 0.42);
        assertTrue(f.flightGoal.shouldActivate(), "planned flight blocks ordinary movement goals without disabling AI");
        f.grounded = false; f.tick(1);
        f.robotLocation.setX(2.5); f.robotLocation.setY(5); f.robotLocation.setZ(0.5);
        f.grounded = true; f.tick(1);
        assertFalse(f.flightGoal.shouldActivate());
        f.controller.close();
        assertEquals(1, f.removedGoals);
    }

    @Test void platformRemovalHeadroomObstructionAndLargeTargetMoveCancelCommittedJump() {
        for (int invalid = 0; invalid < 3; invalid++) {
            Fixture f = new Fixture();
            f.robotLocation.setX(0.5); f.robotLocation.setZ(0.5);
            f.playerLocation.setX(2.5); f.playerLocation.setY(5); f.playerLocation.setZ(0.5);
            f.platform(2, 4, 0);
            f.tick(40);
            assertFalse(f.robotAI);
            switch (invalid) {
                case 0 -> f.blocks.remove("2:4:0");
                case 1 -> f.blocks.put("2:6:0", Material.IRON_BLOCK);
                case 2 -> f.playerLocation.setX(-3.5);
            }
            f.tick(20);
            assertTrue(f.robotAI);
            assertEquals(1, f.velocities.size(), "cancellation must not become a silent ground hop");
            assertFalse(f.flightGoal.shouldActivate());
        }
    }

    @Test void unreachableRoutesHaveBoundedPathfinderWorkAndTooHighOrOutOfShaftPlatformsAreRejected() {
        Fixture f = new Fixture();
        f.robotLocation.setX(-5.5); f.robotLocation.setZ(0.5);
        f.playerLocation.setX(2.5); f.playerLocation.setY(8); f.playerLocation.setZ(0.5);
        for (int x = 0; x < 6; x++) f.platform(x, 4, 0);
        f.pathReachable = false;
        f.tick(1);
        assertTrue(f.pathAttempts <= 6);
        assertNull(f.approachDestination);
        f.tick(9);
        assertTrue(f.pathAttempts <= 6, "path searches are throttled to one replan every ten ticks");
        assertTrue(f.velocities.isEmpty());
        Fixture tooHigh = new Fixture();
        tooHigh.playerLocation.setY(7);
        tooHigh.platform(2, 5, 0); tooHigh.platform(9, 4, 0);
        tooHigh.tick(60);
        assertTrue(tooHigh.velocities.isEmpty());
        assertEquals(0, tooHigh.pathAttempts);
    }

    @Test void landedSpringerCanUseAnIntermediateStepAfterRecovery() {
        Fixture f = new Fixture();
        f.robotLocation.setX(0.5); f.robotLocation.setZ(0.5);
        f.playerLocation.setX(3.5); f.playerLocation.setY(9); f.playerLocation.setZ(0.5);
        f.platform(2, 4, 0); f.platform(3, 8, 0);
        f.tick(60);
        assertEquals(0.82, f.velocities.getLast().getY(), 1e-9);
        f.grounded = false; f.tick(1);
        f.robotLocation.setX(2.5); f.robotLocation.setY(5); f.robotLocation.setZ(0.5);
        f.grounded = true; f.tick(1);
        f.tick(118);
        assertEquals(4, f.velocities.size(), "two warnings and two bounded four-block impulses");
        assertEquals(0.82, f.velocities.getLast().getY(), 1e-9);
    }

    private static final class Fixture {
        final UUID playerId = UUID.randomUUID(), robotId = UUID.randomUUID();
        final List<Mob> robots = new ArrayList<>();
        final List<Vector> velocities = new ArrayList<>();
        final List<Location> platforms = new ArrayList<>();
        final Map<String, Material> blocks = new HashMap<>();
        final Map<PathResult, Location> pathDestinations = new HashMap<>();
        boolean grounded = true, active = true, robotAI = true, lineOfSight = true;
        boolean dead, energized;
        boolean pathReachable = true;
        int pathAttempts, removedGoals;
        Location approachDestination;
        Goal<?> flightGoal;
        GameMode mode = GameMode.SURVIVAL;
        final World world = stub(World.class, (method, args) -> method.equals("getBlockAt") ? block(args) : null);
        final Location playerLocation = new Location(world, 4, 1, 0);
        final Location robotLocation = new Location(world, 0, 1, 0);
        final Pathfinder pathfinder = stub(Pathfinder.class, (method, args) -> switch (method) {
            case "findPath" -> {
                pathAttempts++;
                PathResult path = stub(PathResult.class, (operation, values) -> operation.equals("canReachFinalPoint") && pathReachable);
                pathDestinations.put(path, ((Location) args[0]).clone());
                yield path;
            }
            case "moveTo" -> { approachDestination = pathDestinations.get(args[0]); yield true; }
            default -> null;
        });
        final MobGoals mobGoals = stub(MobGoals.class, (method, args) -> switch (method) {
            case "addGoal" -> { flightGoal = (Goal<?>) args[2]; yield null; }
            case "removeGoal" -> { removedGoals++; yield null; }
            default -> null;
        });
        final Player player = stub(Player.class, (method, args) -> switch (method) {
            case "getUniqueId" -> playerId;
            case "getWorld" -> world;
            case "getLocation" -> playerLocation.clone();
            case "isOnline" -> true;
            case "isDead" -> dead;
            case "getGameMode" -> mode;
            default -> null;
        });
        final Mob robot = stub(Mob.class, (method, args) -> switch (method) {
            case "getUniqueId" -> robotId;
            case "getWorld" -> world;
            case "getLocation" -> robotLocation.clone();
            case "isValid" -> true;
            case "isOnGround" -> grounded;
            case "getVelocity" -> new Vector();
            case "getPathfinder" -> pathfinder;
            case "hasLineOfSight" -> lineOfSight;
            case "hasAI" -> robotAI;
            case "setAI" -> { robotAI = (boolean) args[0]; yield null; }
            case "setVelocity" -> { velocities.add(((Vector) args[0]).clone()); yield null; }
            default -> null;
        });
        final SpringRobotController controller = new SpringRobotController(world, new Location(world, 0, 1, 0), 7,
                () -> robots, () -> List.of(player), p -> active, id -> energized, () -> platforms, mobGoals);
        Fixture() { robots.add(robot); }
        void tick(int count) { for (int tick = 0; tick < count; tick++) controller.tick(); }
        void platform(int x, int y, int z) {
            blocks.put(x + ":" + y + ":" + z, Material.SCAFFOLDING);
            platforms.add(new Location(world, x + 0.5, y + 1, z + 0.5));
        }
        Block block(Object[] args) {
            int x, y, z;
            if (args[0] instanceof Location location) { x = location.getBlockX(); y = location.getBlockY(); z = location.getBlockZ(); }
            else { x = (int) args[0]; y = (int) args[1]; z = (int) args[2]; }
            Material material = blocks.getOrDefault(x + ":" + y + ":" + z, y == 0 ? Material.STONE : Material.AIR);
            return stub(Block.class, (method, values) -> method.equals("getType") ? material : null);
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
