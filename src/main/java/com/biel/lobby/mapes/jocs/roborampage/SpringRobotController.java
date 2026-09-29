package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import com.biel.lobby.mapes.jocs.roborampage.utils.SpringJumpPlanner;
import com.biel.lobby.mapes.jocs.roborampage.utils.SpringJumpPlanner.Jump;
import com.biel.lobby.mapes.jocs.roborampage.utils.SpringJumpPlanner.Point;
import com.biel.lobby.mapes.jocs.roborampage.utils.SpringRobotCycle;
import com.destroystokyo.paper.entity.Pathfinder.PathResult;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import com.destroystokyo.paper.entity.ai.MobGoals;

/** Springers approach reachable scaffold steps, then telegraph a committed physical jump. */
final class SpringRobotController implements AutoCloseable {
    static final double HORIZONTAL_SPEED = 0.42;
    static final double VERTICAL_SPEED = 0.62;
    static final double MAXIMUM_RANGE = 6;
    private static final double PURSUIT_RANGE = 12;
    private static final double BODY_RADIUS = 0.31;
    private static final double BODY_HEIGHT = 1.95;
    private static final double ARRIVAL_DISTANCE_SQUARED = 0.45 * 0.45;
    private static final int REPLAN_INTERVAL_TICKS = 10;
    private static final int MAXIMUM_PATH_ATTEMPTS_PER_REPLAN = 6;
    private static final GoalKey<Mob> FLIGHT_GOAL_KEY = GoalKey.of(Mob.class,
            new NamespacedKey("minicat", "robo_rampage_spring_flight"));
    private final World world;
    private final Location battleCenter;
    private final int arenaRadius;
    private final Supplier<List<Mob>> springRobots;
    private final Supplier<List<Player>> participants;
    private final Predicate<Player> activeParticipant;
    private final Predicate<UUID> energized;
    private final Supplier<List<Location>> scaffoldTops;
    private final MobGoals mobGoals;
    private final Map<UUID, SpringState> springs = new HashMap<>();
    private int ticks;
    private boolean closed;

    private record Pursuit(UUID playerId, Location playerPosition, Location landing,
            Location launchPoint, PathResult approachPath) {
        boolean platform() { return landing != null; }
    }
    private static final class SpringState {
        final Mob robot;
        final SpringRobotCycle cycle = new SpringRobotCycle();
        final Goal<Mob> flightGoal;
        Pursuit pursuit;
        Location committedOrigin;
        int nextPlanAt;
        int flightUntil;
        boolean observedAirborne;
        boolean heldAI;
        boolean previousAI;
        SpringState(Mob robot) {
            this.robot = robot;
            flightGoal = new Goal<>() {
                public boolean shouldActivate() { return flightUntil > 0; }
                public boolean shouldStayActive() { return flightUntil > 0; }
                public void tick() { robot.getPathfinder().stopPathfinding(); }
                public GoalKey<Mob> getKey() { return FLIGHT_GOAL_KEY; }
                public EnumSet<GoalType> getTypes() { return EnumSet.of(GoalType.MOVE, GoalType.JUMP); }
            };
        }
        void restoreAI() {
            if (heldAI && robot.isValid() && !robot.isDead()) robot.setAI(previousAI);
            heldAI = false;
        }
        void release() {
            restoreAI();
            pursuit = null;
            committedOrigin = null;
            flightUntil = 0;
            observedAirborne = false;
        }
    }

    /** Ground-hop fixture constructor; matches wire their arena and owned scaffold tops. */
    SpringRobotController(World world, Supplier<List<Mob>> springRobots, Supplier<List<Player>> participants,
            Predicate<Player> activeParticipant, Predicate<UUID> energized) {
        this(world, null, 0, springRobots, participants, activeParticipant, energized, List::of, null);
    }
    SpringRobotController(World world, Location battleCenter, int arenaRadius,
            Supplier<List<Mob>> springRobots, Supplier<List<Player>> participants,
            Predicate<Player> activeParticipant, Predicate<UUID> energized, Supplier<List<Location>> scaffoldTops) {
        this(world, battleCenter, arenaRadius, springRobots, participants, activeParticipant, energized,
                scaffoldTops, Bukkit.getMobGoals());
    }
    SpringRobotController(World world, Location battleCenter, int arenaRadius,
            Supplier<List<Mob>> springRobots, Supplier<List<Player>> participants,
            Predicate<Player> activeParticipant, Predicate<UUID> energized,
            Supplier<List<Location>> scaffoldTops, MobGoals mobGoals) {
        this.world = world;
        this.battleCenter = battleCenter == null ? null : battleCenter.clone();
        this.arenaRadius = arenaRadius;
        this.springRobots = springRobots;
        this.participants = participants;
        this.activeParticipant = activeParticipant;
        this.energized = energized;
        this.scaffoldTops = scaffoldTops;
        this.mobGoals = mobGoals;
    }

    void tick() {
        if (closed) return;
        ticks++;
        List<Player> players = participants.get().stream().filter(this::validPlayer).toList();
        List<Location> platforms = scaffoldTops.get();
        Set<UUID> present = new HashSet<>();
        for (Mob robot : springRobots.get()) {
            if (!robot.isValid() || robot.isDead() || robot.getWorld() != world) continue;
            UUID robotId = robot.getUniqueId();
            present.add(robotId);
            SpringState state = springs.computeIfAbsent(robotId, ignored -> {
                SpringState created = new SpringState(robot);
                if (mobGoals != null) mobGoals.addGoal(robot, 0, created.flightGoal);
                return created;
            });
            boolean interrupted = energized.test(robotId);
            if (state.flightUntil > 0) {
                state.cycle.tick(false, robot.isOnGround(), interrupted);
                if (!robot.isOnGround()) state.observedAirborne = true;
                if (interrupted || ticks >= state.flightUntil || (state.observedAirborne && robot.isOnGround())) {
                    state.release();
                    state.nextPlanAt = ticks;
                }
                continue;
            }
            if (!state.cycle.windingUp() && ticks >= state.nextPlanAt) {
                state.pursuit = findPursuit(robot, players, platforms);
                state.nextPlanAt = ticks + REPLAN_INTERVAL_TICKS;
                if (state.pursuit != null && state.pursuit.approachPath() != null && !interrupted) {
                    robot.getPathfinder().moveTo(state.pursuit.approachPath(), 1.15);
                }
            }
            Player target = state.pursuit == null ? null : players.stream()
                    .filter(player -> player.getUniqueId().equals(state.pursuit.playerId())).findFirst().orElse(null);
            boolean ready = target != null && (state.cycle.windingUp()
                    ? validCommitment(state, target) : readyToJump(robot, state.pursuit, target));
            var action = state.cycle.tick(ready, robot.isOnGround(), interrupted);
            switch (action) {
                case WARN -> {
                    state.committedOrigin = robot.getLocation();
                    state.previousAI = robot.hasAI();
                    state.heldAI = true;
                    robot.setAI(false);
                    robot.setVelocity(new Vector(0, robot.getVelocity().getY(), 0));
                    world.playSound(robot.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 0.7F, 0.8F);
                }
                case JUMP -> launch(state, target);
                case CANCEL -> state.release();
                case NONE -> { }
            }
            if (state.cycle.windingUp() && ticks % 4 == 0) {
                world.spawnParticle(Particle.CRIT, robot.getLocation().add(0, 0.3, 0), 8, 0.25, 0.15, 0.25, 0.02);
            }
        }
        for (UUID robotId : new ArrayList<>(springs.keySet())) {
            if (!present.contains(robotId)) removeState(robotId);
        }
    }

    private void launch(SpringState state, Player target) {
        Mob robot = state.robot;
        Vector velocity;
        if (state.pursuit.platform()) {
            Jump jump = plannedJump(robot.getLocation(), state.pursuit.landing()).orElseThrow();
            velocity = new Vector(jump.velocityX(), jump.velocityY(), jump.velocityZ());
            state.flightUntil = ticks + (int) Math.ceil(jump.flightTicks()) + 12;
            robot.getPathfinder().stopPathfinding();
            // NoAI also stops LivingEntity.travel(), so restore AI before native ballistic flight.
            state.restoreAI();
        } else {
            velocity = target.getLocation().toVector().subtract(robot.getLocation().toVector()).setY(0);
            if (velocity.lengthSquared() > 0.001) velocity.normalize().multiply(HORIZONTAL_SPEED);
            velocity.setY(VERTICAL_SPEED);
            state.release();
        }
        robot.setVelocity(velocity);
        world.playSound(robot.getLocation(), Sound.BLOCK_PISTON_EXTEND, 0.8F, 1.2F);
        world.spawnParticle(Particle.CLOUD, robot.getLocation(), 12, 0.3, 0.1, 0.3, 0.025);
    }

    private Pursuit findPursuit(Mob robot, List<Player> players, List<Location> platforms) {
        Location origin = robot.getLocation();
        int[] pathAttemptsLeft = {MAXIMUM_PATH_ATTEMPTS_PER_REPLAN};
        for (Player player : players.stream().filter(candidate -> horizontalDistanceSquared(origin, candidate.getLocation())
                <= PURSUIT_RANGE * PURSUIT_RANGE).sorted(Comparator.comparingDouble(candidate ->
                        candidate.getLocation().distanceSquared(origin))).toList()) {
            Location target = player.getLocation();
            if (target.getY() - origin.getY() > 0.75) {
                var steps = platforms.stream().filter(top -> top.getY() - origin.getY() >= 0.5
                                && top.getY() - origin.getY() <= SpringJumpPlanner.MAXIMUM_STEP_HEIGHT)
                        .filter(top -> top.getY() <= target.getY() + 0.5
                                && horizontalDistanceSquared(top, target) <= MAXIMUM_RANGE * MAXIMUM_RANGE)
                        .filter(this::validLanding)
                        .sorted(Comparator.comparingDouble(top -> top.distanceSquared(target)))
                        .limit(12).toList();
                for (Location landing : steps) {
                    if (plannedJump(origin, landing).isPresent()) {
                        return new Pursuit(player.getUniqueId(), target, landing, origin, null);
                    }
                    Pursuit approach = approachStep(robot, player, landing, pathAttemptsLeft);
                    if (approach != null) return approach;
                }
            }
            if (groundReachable(robot, player)) return new Pursuit(player.getUniqueId(), target, null, origin, null);
        }
        return null;
    }

    private Pursuit approachStep(Mob robot, Player target, Location landing, int[] pathAttemptsLeft) {
        if (pathAttemptsLeft[0] <= 0) return null;
        Location origin = robot.getLocation();
        List<Location> candidates = new ArrayList<>();
        for (double distance : new double[]{2.5, 1.5, 0.5}) {
            for (int direction = 0; direction < 8; direction++) {
                double angle = direction * Math.PI / 4;
                Location launch = new Location(world, landing.getX() + Math.cos(angle) * distance,
                        origin.getY(), landing.getZ() + Math.sin(angle) * distance);
                if (withinShaft(launch) && supported(launch) && plannedJump(launch, landing).isPresent()) candidates.add(launch);
            }
        }
        candidates.sort(Comparator.comparingDouble(candidate -> candidate.distanceSquared(origin)));
        for (Location candidate : candidates) {
            if (pathAttemptsLeft[0]-- <= 0) return null;
            PathResult path = robot.getPathfinder().findPath(candidate);
            if (path != null && path.canReachFinalPoint()) {
                return new Pursuit(target.getUniqueId(), target.getLocation(), landing, candidate, path);
            }
        }
        return null;
    }

    private boolean readyToJump(Mob robot, Pursuit pursuit, Player target) {
        if (!pursuit.platform()) return groundReachable(robot, target);
        return horizontalDistanceSquared(robot.getLocation(), pursuit.launchPoint()) <= ARRIVAL_DISTANCE_SQUARED
                && validLanding(pursuit.landing()) && plannedJump(robot.getLocation(), pursuit.landing()).isPresent();
    }
    private boolean validCommitment(SpringState state, Player target) {
        if (!state.pursuit.platform()) return groundReachable(state.robot, target);
        return target.getLocation().distanceSquared(state.pursuit.playerPosition()) <= 9
                && state.robot.getLocation().distanceSquared(state.committedOrigin) <= 0.25
                && validLanding(state.pursuit.landing())
                && plannedJump(state.robot.getLocation(), state.pursuit.landing()).isPresent();
    }
    private java.util.Optional<Jump> plannedJump(Location origin, Location landing) {
        if (!withinShaft(origin) || !withinShaft(landing)) return java.util.Optional.empty();
        return SpringJumpPlanner.plan(point(origin), point(landing), feet -> withinShaft(location(feet)) && bodyClear(feet, true));
    }
    private boolean validLanding(Location top) {
        return top.getWorld() == world && withinShaft(top)
                && top.clone().subtract(0, 0.1, 0).getBlock().getType() == Material.SCAFFOLDING
                && bodyClear(point(top), false);
    }
    private boolean bodyClear(Point feet, boolean allowScaffoldPassThrough) {
        for (double x : new double[]{feet.x() - BODY_RADIUS, feet.x() + BODY_RADIUS}) {
            for (double z : new double[]{feet.z() - BODY_RADIUS, feet.z() + BODY_RADIUS}) {
                for (int y = (int) Math.floor(feet.y() + 0.01); y <= Math.floor(feet.y() + BODY_HEIGHT); y++) {
                    Material material = world.getBlockAt((int) Math.floor(x), y, (int) Math.floor(z)).getType();
                    if (!material.isAir() && !(allowScaffoldPassThrough && material == Material.SCAFFOLDING)) return false;
                }
            }
        }
        return true;
    }
    private boolean supported(Location feet) {
        Material support = feet.clone().subtract(0, 0.1, 0).getBlock().getType();
        return support.isSolid() || support == Material.SCAFFOLDING;
    }
    private boolean withinShaft(Location feet) {
        return battleCenter == null || Math.abs(feet.getX() - (battleCenter.getBlockX() + 0.5)) + BODY_RADIUS <= arenaRadius + 0.5
                && Math.abs(feet.getZ() - (battleCenter.getBlockZ() + 0.5)) + BODY_RADIUS <= arenaRadius + 0.5;
    }
    private boolean validPlayer(Player player) {
        return player.isOnline() && !player.isDead() && player.getWorld() == world
                && player.getGameMode() != GameMode.SPECTATOR && activeParticipant.test(player);
    }
    private boolean groundReachable(Mob robot, Player player) {
        return horizontalDistanceSquared(robot.getLocation(), player.getLocation()) <= MAXIMUM_RANGE * MAXIMUM_RANGE
                && Math.abs(player.getLocation().getY() - robot.getLocation().getY()) <= 2.5 && robot.hasLineOfSight(player);
    }
    private static double horizontalDistanceSquared(Location first, Location second) {
        double x = first.getX() - second.getX(), z = first.getZ() - second.getZ();
        return x * x + z * z;
    }
    private static Point point(Location location) { return new Point(location.getX(), location.getY(), location.getZ()); }
    private Location location(Point point) { return new Location(world, point.x(), point.y(), point.z()); }
    private void removeState(UUID robotId) {
        SpringState state = springs.remove(robotId);
        state.release();
        if (mobGoals != null) mobGoals.removeGoal(state.robot, state.flightGoal);
    }
    @Override public void close() {
        if (closed) return;
        closed = true;
        for (UUID robotId : new ArrayList<>(springs.keySet())) removeState(robotId);
    }
}
