package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SpringRobotControllerTest {
    @BeforeAll static void registries() throws Exception { MobilityAndCutterControllerTest.registries(); }

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

    private static final class Fixture {
        final UUID playerId = UUID.randomUUID(), robotId = UUID.randomUUID();
        final List<Mob> robots = new ArrayList<>();
        final List<Vector> velocities = new ArrayList<>();
        boolean grounded = true, active = true, robotAI = true, lineOfSight = true;
        boolean dead, energized;
        GameMode mode = GameMode.SURVIVAL;
        final World world = stub(World.class, (method, args) -> null);
        final Location playerLocation = new Location(world, 4, 1, 0);
        final Location robotLocation = new Location(world, 0, 1, 0);
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
            case "hasLineOfSight" -> lineOfSight;
            case "hasAI" -> robotAI;
            case "setAI" -> { robotAI = (boolean) args[0]; yield null; }
            case "setVelocity" -> { velocities.add(((Vector) args[0]).clone()); yield null; }
            default -> null;
        });
        final SpringRobotController controller = new SpringRobotController(world, () -> robots,
                () -> List.of(player), p -> active, id -> energized);
        Fixture() { robots.add(robot); }
        void tick(int count) { for (int tick = 0; tick < count; tick++) controller.tick(); }
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
