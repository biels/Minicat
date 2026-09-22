package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.IntSupplier;
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

/** Tracks the temporary player-built scaffold layer and bounded robot damage against it. */
final class ScaffoldController implements AutoCloseable {
    private static final int MAX_TRACKED_SCAFFOLDING = 128;
    private static final int HITS_TO_BREAK = 3;
    private static final double ROBOT_REACH_SQUARED = 2.6 * 2.6;

    private final World world;
    private final Location battleCenter;
    private final int arenaRadius;
    private final IntSupplier targetHeight;
    private final Supplier<? extends Iterable<Mob>> robots;
    private final ToIntFunction<Mob> scaffoldDamageByRobot;
    private final Set<BlockPosition> placedScaffolding = new HashSet<>();
    private final Map<BlockPosition, Integer> damageByPosition = new HashMap<>();

    ScaffoldController(
            World world,
            Location battleCenter,
            int arenaRadius,
            IntSupplier targetHeight,
            Supplier<? extends Iterable<Mob>> robots,
            ToIntFunction<Mob> scaffoldDamageByRobot) {
        this.world = world;
        this.battleCenter = battleCenter.clone();
        this.arenaRadius = arenaRadius;
        this.targetHeight = targetHeight;
        this.robots = robots;
        this.scaffoldDamageByRobot = scaffoldDamageByRobot;
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
        pruneMissingBlocks();
        for (Mob robot : robots.get()) {
            nearestReachableScaffold(robot.getLocation()).ifPresent(position ->
                    damage(position, Math.max(1, scaffoldDamageByRobot.applyAsInt(robot))));
        }
    }

    private Optional<BlockPosition> nearestReachableScaffold(Location robotLocation) {
        return placedScaffolding.stream()
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
