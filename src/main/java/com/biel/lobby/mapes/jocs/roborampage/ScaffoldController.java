package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

/** Tracks the temporary player-built scaffold layer and bounded robot damage against it. */
final class ScaffoldController implements AutoCloseable {
    private static final int MAX_TRACKED_SCAFFOLDING = 128;
    private static final int HITS_TO_BREAK = 3;
    private static final double ROBOT_REACH_SQUARED = 2.6 * 2.6;
    static final int CUTTER_WINDUP_TICKS = 24;
    static final int CUTTER_RECOVERY_TICKS = 40;

    private final World world;
    private final Location battleCenter;
    private final int arenaRadius;
    private final IntSupplier targetHeight;
    private final Supplier<? extends Iterable<Mob>> robots;
    private final ToIntFunction<Mob> scaffoldDamageByRobot;
    private final Predicate<Mob> interruptedByTaser;
    private final Set<BlockPosition> placedScaffolding = new HashSet<>();
    private final Map<BlockPosition, Integer> damageByPosition = new HashMap<>();
    private final Map<UUID, SawAttack> sawAttacks = new HashMap<>();
    private long currentTick;

    private static final class SawAttack {
        final Mob robot;
        final BlockPosition target;
        final boolean originalAI;
        final long strikeAt;
        long recoveryUntil;

        SawAttack(Mob robot, BlockPosition target, long strikeAt) {
            this.robot = robot;
            this.target = target;
            this.originalAI = robot.hasAI();
            this.strikeAt = strikeAt;
        }
    }

    ScaffoldController(
            World world,
            Location battleCenter,
            int arenaRadius,
            IntSupplier targetHeight,
            Supplier<? extends Iterable<Mob>> robots,
            ToIntFunction<Mob> scaffoldDamageByRobot) {
        this(world, battleCenter, arenaRadius, targetHeight, robots, scaffoldDamageByRobot, robot -> false);
    }

    ScaffoldController(
            World world, Location battleCenter, int arenaRadius, IntSupplier targetHeight,
            Supplier<? extends Iterable<Mob>> robots, ToIntFunction<Mob> scaffoldDamageByRobot,
            Predicate<Mob> interruptedByTaser) {
        this.world = world;
        this.battleCenter = battleCenter.clone();
        this.arenaRadius = arenaRadius;
        this.targetHeight = targetHeight;
        this.robots = robots;
        this.scaffoldDamageByRobot = scaffoldDamageByRobot;
        this.interruptedByTaser = interruptedByTaser;
    }

    boolean handlePlacement(BlockPlaceEvent event, Block block) {
        if (block.getType() != Material.SCAFFOLDING || !withinBuildArea(block)
                || placedScaffolding.size() >= MAX_TRACKED_SCAFFOLDING) return false;
        event.setCancelled(false);
        placedScaffolding.add(BlockPosition.of(block));
        return true;
    }

    boolean handleBreak(BlockBreakEvent event, Block block) {
        BlockPosition position = BlockPosition.of(block);
        if (!placedScaffolding.remove(position)) return false;
        damageByPosition.remove(position);
        event.setCancelled(false);
        return true;
    }

    void tickRobotDamage() {
        currentTick++;
        if (currentTick % 20 == 0) pruneMissingBlocks();
        Set<UUID> liveRobotIds = new HashSet<>();
        for (Mob robot : robots.get()) {
            if (!robot.isValid() || robot.isDead() || robot.getWorld() != world) continue;
            liveRobotIds.add(robot.getUniqueId());
            if (scaffoldDamageByRobot.applyAsInt(robot) >= HITS_TO_BREAK) {
                tickCutter(robot);
            } else if (currentTick % 20 == 0) {
                nearestReachableScaffold(robot.getLocation()).ifPresent(position ->
                        damage(position, Math.max(1, scaffoldDamageByRobot.applyAsInt(robot))));
            }
        }
        for (UUID robotId : new ArrayList<>(sawAttacks.keySet())) {
            if (!liveRobotIds.contains(robotId)) finishAttack(robotId);
        }
    }

    boolean isSawBusy(UUID robotId) { return sawAttacks.containsKey(robotId); }

    private void tickCutter(Mob robot) {
        UUID robotId = robot.getUniqueId();
        SawAttack attack = sawAttacks.get(robotId);
        if (attack == null) {
            if (interruptedByTaser.test(robot)) return;
            nearestReachableScaffold(robot.getLocation()).ifPresent(target -> {
                sawAttacks.put(robotId, new SawAttack(robot, target, currentTick + CUTTER_WINDUP_TICKS));
                robot.setAI(false);
                robot.setVelocity(new Vector(0, robot.getVelocity().getY(), 0));
                world.playSound(robot.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1.0F, 0.65F);
                warnSawTarget(target);
            });
            return;
        }
        if (attack.recoveryUntil > 0) {
            if (currentTick >= attack.recoveryUntil) finishAttack(robotId);
            return;
        }
        if (interruptedByTaser.test(robot) || !placedScaffolding.contains(attack.target)
                || attack.target.block(world).getType() != Material.SCAFFOLDING
                || attack.target.center(world).distanceSquared(robot.getLocation()) > ROBOT_REACH_SQUARED) {
            attack.recoveryUntil = currentTick + CUTTER_RECOVERY_TICKS;
            world.playSound(robot.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 0.6F, 0.65F);
            return;
        }
        if (currentTick >= attack.strikeAt) {
            damage(attack.target, HITS_TO_BREAK);
            attack.recoveryUntil = currentTick + CUTTER_RECOVERY_TICKS;
        } else if (currentTick % 4 == 0) {
            warnSawTarget(attack.target);
            world.playSound(robot.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 0.65F,
                    0.7F + (CUTTER_WINDUP_TICKS - (attack.strikeAt - currentTick)) / 30.0F);
        }
    }

    private void warnSawTarget(BlockPosition target) {
        world.spawnParticle(Particle.CRIT, target.center(world), 10, 0.35, 0.4, 0.35, 0.04);
    }

    private void finishAttack(UUID robotId) {
        SawAttack attack = sawAttacks.remove(robotId);
        if (attack != null && attack.robot.isValid() && !attack.robot.isDead()) {
            attack.robot.setAI(attack.originalAI);
        }
    }

    void destroyInBlast(Iterable<Block> affectedBlocks) {
        for (Block block : affectedBlocks) {
            BlockPosition position = BlockPosition.of(block);
            if (placedScaffolding.contains(position)) collapseColumnFrom(position);
        }
    }

    void collapseNear(Location origin, double radius) {
        for (BlockPosition position : new ArrayList<>(placedScaffolding)) {
            Location center = position.center(world);
            double dx = center.getX() - origin.getX(), dz = center.getZ() - origin.getZ();
            if (Math.abs(center.getY() - origin.getY()) <= 1.5 && dx * dx + dz * dz <= radius * radius
                    && placedScaffolding.contains(position)) collapseColumnFrom(position);
        }
    }

    private Optional<BlockPosition> nearestReachableScaffold(Location robotLocation) {
        return placedScaffolding.stream()
                .filter(position -> position.block(world).getType() == Material.SCAFFOLDING)
                .filter(position -> position.center(world).distanceSquared(robotLocation) <= ROBOT_REACH_SQUARED)
                .min(Comparator.comparingDouble(position -> position.center(world).distanceSquared(robotLocation)));
    }

    private void damage(BlockPosition position, int damageAmount) {
        Block block = position.block(world);
        int hitCount = damageByPosition.merge(position, damageAmount, Integer::sum);
        world.spawnParticle(Particle.BLOCK, block.getLocation().add(0.5, 0.5, 0.5),
                8 + damageAmount * 3, 0.3, 0.3, 0.3, block.getBlockData());
        world.playSound(block.getLocation(), Sound.BLOCK_SCAFFOLDING_HIT,
                0.55F + damageAmount * 0.1F, 0.8F + Math.min(hitCount, HITS_TO_BREAK) * 0.12F);
        if (hitCount < HITS_TO_BREAK) return;
        collapseColumnFrom(position);
    }

    private void collapseColumnFrom(BlockPosition struckPosition) {
        for (BlockPosition position : new ArrayList<>(placedScaffolding)) {
            if (position.x() != struckPosition.x() || position.z() != struckPosition.z()
                    || position.y() < struckPosition.y()) continue;
            Block block = position.block(world);
            if (block.getType() == Material.SCAFFOLDING) {
                world.spawnParticle(Particle.BLOCK, block.getLocation().add(0.5, 0.5, 0.5),
                        14, 0.35, 0.35, 0.35, block.getBlockData());
                block.setType(Material.AIR, false);
            }
            placedScaffolding.remove(position);
            damageByPosition.remove(position);
        }
        world.playSound(struckPosition.center(world), Sound.BLOCK_SCAFFOLDING_BREAK, 1.0F, 0.7F);
    }

    private boolean withinBuildArea(Block block) {
        int horizontalDistanceX = Math.abs(block.getX() - battleCenter.getBlockX());
        int horizontalDistanceZ = Math.abs(block.getZ() - battleCenter.getBlockZ());
        int minimumY = battleCenter.getBlockY() - 2;
        int maximumY = battleCenter.getBlockY() + targetHeight.getAsInt() + 8;
        return block.getWorld() == world && horizontalDistanceX <= arenaRadius
                && horizontalDistanceZ <= arenaRadius && block.getY() >= minimumY && block.getY() <= maximumY;
    }

    private void pruneMissingBlocks() {
        placedScaffolding.removeIf(position -> {
            boolean missing = position.block(world).getType() != Material.SCAFFOLDING;
            if (missing) damageByPosition.remove(position);
            return missing;
        });
    }

    @Override
    public void close() {
        for (UUID robotId : new ArrayList<>(sawAttacks.keySet())) finishAttack(robotId);
        for (BlockPosition position : new ArrayList<>(placedScaffolding)) {
            Block block = position.block(world);
            if (block.getType() == Material.SCAFFOLDING) block.setType(Material.AIR, false);
        }
        placedScaffolding.clear();
        damageByPosition.clear();
    }

    private record BlockPosition(int x, int y, int z) {
        static BlockPosition of(Block block) {
            return new BlockPosition(block.getX(), block.getY(), block.getZ());
        }

        Block block(World world) { return world.getBlockAt(x, y, z); }

        Location center(World world) { return new Location(world, x + 0.5, y + 0.5, z + 0.5); }
    }
}
