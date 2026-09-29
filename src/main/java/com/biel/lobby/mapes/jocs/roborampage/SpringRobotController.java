package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.biel.lobby.mapes.jocs.roborampage.utils.SpringRobotCycle;

/** Match-owned Springers telegraph a short leap; Taser contact releases the spring safely. */
final class SpringRobotController implements AutoCloseable {
    static final double HORIZONTAL_SPEED = 0.42;
    static final double VERTICAL_SPEED = 0.62;
    static final double MAXIMUM_RANGE = 6;
    private final World world;
    private final Supplier<List<Mob>> springRobots;
    private final Supplier<List<Player>> participants;
    private final Predicate<Player> activeParticipant;
    private final Predicate<UUID> energized;
    private final Map<UUID, SpringState> springs = new HashMap<>();
    private int ticks;
    private boolean closed;

    private static final class SpringState {
        final Mob robot;
        final SpringRobotCycle cycle = new SpringRobotCycle();
        UUID committedTarget;
        boolean heldAI;
        boolean previousAI;

        SpringState(Mob robot) { this.robot = robot; }

        void release() {
            if (heldAI && robot.isValid() && !robot.isDead()) robot.setAI(previousAI);
            heldAI = false;
            committedTarget = null;
        }
    }

    SpringRobotController(World world, Supplier<List<Mob>> springRobots, Supplier<List<Player>> participants,
            Predicate<Player> activeParticipant, Predicate<UUID> energized) {
        this.world = world;
        this.springRobots = springRobots;
        this.participants = participants;
        this.activeParticipant = activeParticipant;
        this.energized = energized;
    }

    void tick() {
        if (closed) return;
        ticks++;
        List<Player> players = participants.get().stream().filter(this::validPlayer).toList();
        Set<UUID> present = new HashSet<>();
        for (Mob robot : springRobots.get()) {
            if (!robot.isValid() || robot.isDead() || robot.getWorld() != world) continue;
            UUID robotId = robot.getUniqueId();
            present.add(robotId);
            SpringState state = springs.computeIfAbsent(robotId, ignored -> new SpringState(robot));
            Player target = state.cycle.windingUp()
                    ? players.stream().filter(player -> player.getUniqueId().equals(state.committedTarget))
                            .findFirst().orElse(null)
                    : players.stream().filter(player -> reachable(robot, player))
                            .min(Comparator.comparingDouble(player -> player.getLocation()
                                    .distanceSquared(robot.getLocation())))
                            .orElse(null);
            boolean targetReachable = target != null && reachable(robot, target);
            var action = state.cycle.tick(targetReachable, robot.isOnGround(), energized.test(robotId));
            switch (action) {
                case WARN -> {
                    state.committedTarget = target.getUniqueId();
                    state.previousAI = robot.hasAI();
                    state.heldAI = true;
                    robot.setAI(false);
                    robot.setVelocity(new Vector(0, robot.getVelocity().getY(), 0));
                    world.playSound(robot.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 0.7F, 0.8F);
                }
                case JUMP -> {
                    Vector direction = target.getLocation().toVector().subtract(robot.getLocation().toVector()).setY(0);
                    if (direction.lengthSquared() > 0.001) direction.normalize().multiply(HORIZONTAL_SPEED);
                    direction.setY(VERTICAL_SPEED);
                    state.release();
                    robot.setVelocity(direction);
                    world.playSound(robot.getLocation(), Sound.BLOCK_PISTON_EXTEND, 0.8F, 1.2F);
                    world.spawnParticle(Particle.CLOUD, robot.getLocation(), 12, 0.3, 0.1, 0.3, 0.025);
                }
                case CANCEL -> state.release();
                case NONE -> { }
            }
            if (state.cycle.windingUp() && ticks % 4 == 0) {
                world.spawnParticle(Particle.CRIT, robot.getLocation().add(0, 0.3, 0), 8, 0.25, 0.15, 0.25, 0.02);
            }
        }
        for (UUID robotId : new ArrayList<>(springs.keySet())) {
            if (!present.contains(robotId)) springs.remove(robotId).release();
        }
    }

    private boolean validPlayer(Player player) {
        return player.isOnline() && !player.isDead() && player.getWorld() == world
                && player.getGameMode() != GameMode.SPECTATOR && activeParticipant.test(player);
    }

    private boolean reachable(Mob robot, Player player) {
        Location origin = robot.getLocation(), target = player.getLocation();
        double x = target.getX() - origin.getX(), z = target.getZ() - origin.getZ();
        return x * x + z * z <= MAXIMUM_RANGE * MAXIMUM_RANGE
                && Math.abs(target.getY() - origin.getY()) <= 2.5 && robot.hasLineOfSight(player);
    }

    @Override public void close() {
        closed = true;
        springs.values().forEach(SpringState::release);
        springs.clear();
    }
}
