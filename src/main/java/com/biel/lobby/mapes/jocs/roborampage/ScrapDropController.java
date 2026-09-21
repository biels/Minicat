package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.random.RandomGenerator;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import com.biel.lobby.mapes.jocs.roborampage.utils.ScrapGrid;
import com.biel.lobby.mapes.jocs.roborampage.utils.ScrapMaterial;
import com.biel.lobby.mapes.jocs.roborampage.utils.ScrapShape;
import com.biel.lobby.mapes.jocs.roborampage.utils.ScrapShapes;

/** Bukkit presentation for {@link ScrapGrid}; all authoritative positions remain integer cells. */
final class ScrapDropController implements AutoCloseable {
    private static final int ARENA_RADIUS = 7;
    private static final int MAX_MOVING_BLOCKS = 128;
    private static final int MAX_QUEUED_DROPS = 512;
    private static final double CRITICAL_EJECTION_DISTANCE = 3.0;
    private static final int CRITICAL_EJECTION_TICKS = 6;

    private final World world;
    private final int originX;
    private final int floorY;
    private final int originZ;
    private final RandomGenerator random;
    private final ScrapGrid grid = new ScrapGrid();
    private final Queue<PendingDrop> pendingDrops = new ArrayDeque<>();
    private final List<ActiveDrop> activeDrops = new ArrayList<>();
    private int movingBlockCount;
    private int settledRobotBlocks;
    private int settledJunkBlocks;

    ScrapDropController(World world, Location battleCenter, RandomGenerator random) {
        this.world = world;
        this.originX = battleCenter.getBlockX() - ARENA_RADIUS;
        this.floorY = battleCenter.getBlockY() - 1;
        this.originZ = battleCenter.getBlockZ() - ARENA_RADIUS;
        this.random = random;
    }

    public boolean dropRobotScrap(Location worldLocation, ScrapMaterial material) {
        int localX = clamp(worldLocation.getBlockX() - originX, 0, ScrapGrid.DEFAULT_WIDTH - 1);
        int localZ = clamp(worldLocation.getBlockZ() - originZ, 0, ScrapGrid.DEFAULT_DEPTH - 1);
        return enqueue(new PendingDrop(ScrapShapes.single(material), localX, localZ, 4, false, null));
    }

    public boolean ejectRobotScrap(Location source, Vector direction, ScrapMaterial material) {
        Vector horizontalDirection = direction.clone().setY(0);
        if (horizontalDirection.lengthSquared() < 1.0E-6) return dropRobotScrap(source, material);
        Location landingArea = source.clone().add(
                horizontalDirection.normalize().multiply(CRITICAL_EJECTION_DISTANCE));
        int localX = clamp(landingArea.getBlockX() - originX, 0, ScrapGrid.DEFAULT_WIDTH - 1);
        int localZ = clamp(landingArea.getBlockZ() - originZ, 0, ScrapGrid.DEFAULT_DEPTH - 1);
        Location displayOrigin = source.clone().subtract(0.5, 0, 0.5);
        return enqueue(new PendingDrop(
                ScrapShapes.single(material), localX, localZ, 4, false, displayOrigin));
    }

    public boolean dropCorrectiveCar() {
        ScrapGrid.Cell target = grid.lowestPatch(random);
        return enqueue(new PendingDrop(ScrapShapes.car(), target.x(), target.z(), 12, true, null));
    }

    public void tick() {
        drainPendingDrops();
        Iterator<ActiveDrop> iterator = activeDrops.iterator();
        while (iterator.hasNext()) {
            ActiveDrop drop = iterator.next();
            drop.advance();
            if (!drop.finishedMoving() || !canMaterialize(drop.plan)) continue;
            materialize(drop);
            movingBlockCount -= drop.renderedBlocks.size();
            iterator.remove();
        }
    }

    public int settledHeight() { return grid.maximumSettledHeight(); }

    public int unevenness() { return grid.unevenness(); }

    public int settledBlockCount() { return grid.settledBlockCount(); }

    public int settledRobotBlocks() { return settledRobotBlocks; }

    public int settledJunkBlocks() { return settledJunkBlocks; }

    public boolean hasBulkyDropInFlight() {
        return activeDrops.stream().anyMatch(drop -> drop.plan.bulky())
                || pendingDrops.stream().anyMatch(PendingDrop::bulky);
    }

    public boolean isStandingOnTargetScrap(Player player, int targetHeight) {
        Location feet = player.getLocation();
        ScrapGrid.Cell below = new ScrapGrid.Cell(
                feet.getBlockX() - originX,
                feet.getBlockY() - 1 - floorY,
                feet.getBlockZ() - originZ);
        return below.y() >= targetHeight && grid.containsSettled(below);
    }

    public Location safePlayerSpawn() {
        Location best = null;
        double bestDistance = Double.MAX_VALUE;
        double centerX = originX + ARENA_RADIUS + 0.5;
        double centerZ = originZ + ARENA_RADIUS + 0.5;
        for (int localX = 0; localX < ScrapGrid.DEFAULT_WIDTH; localX++) {
            for (int localZ = 0; localZ < ScrapGrid.DEFAULT_DEPTH; localZ++) {
                int worldX = originX + localX;
                int worldZ = originZ + localZ;
                Block surface = world.getHighestBlockAt(worldX, worldZ);
                Block feet = surface.getRelative(0, 1, 0);
                Block head = surface.getRelative(0, 2, 0);
                if (!feet.isEmpty() || !head.isEmpty()) continue;
                double distance = Math.pow(worldX + 0.5 - centerX, 2) + Math.pow(worldZ + 0.5 - centerZ, 2);
                if (distance >= bestDistance) continue;
                bestDistance = distance;
                best = new Location(world, worldX + 0.5, surface.getY() + 1, worldZ + 0.5);
            }
        }
        return best != null ? best : new Location(world, centerX, floorY + 1, centerZ);
    }

    private boolean enqueue(PendingDrop pendingDrop) {
        if (pendingDrops.size() >= MAX_QUEUED_DROPS) return false;
        pendingDrops.add(pendingDrop);
        return true;
    }

    private void drainPendingDrops() {
        while (!pendingDrops.isEmpty()) {
            PendingDrop pending = pendingDrops.peek();
            if (movingBlockCount + pending.shape().blocks().size() > MAX_MOVING_BLOCKS) return;
            ScrapGrid.DropPlan plan = grid.planDrop(
                    pending.shape(), pending.localX(), pending.localZ(), pending.launchClearance(),
                    pending.bulky(), random);
            ActiveDrop active = new ActiveDrop(plan, pending.displayOrigin());
            activeDrops.add(active);
            movingBlockCount += active.renderedBlocks.size();
            pendingDrops.remove();
            if (pending.bulky()) {
                world.playSound(worldLocation(plan.blocks().getFirst().start()), Sound.ENTITY_IRON_GOLEM_DAMAGE, 1.2F, 0.65F);
            }
        }
    }

    private boolean canMaterialize(ScrapGrid.DropPlan plan) {
        for (ScrapGrid.Cell cell : plan.finalCells().keySet()) {
            Block block = worldBlock(cell);
            if (!block.isEmpty()) return false;
            BoundingBox blockBounds = new BoundingBox(
                    block.getX(), block.getY(), block.getZ(),
                    block.getX() + 1, block.getY() + 1, block.getZ() + 1);
            for (Entity entity : world.getNearbyEntities(blockBounds)) {
                if (!(entity instanceof LivingEntity living) || !living.isValid() || living.isDead()) continue;
                if (living instanceof Player player && player.getGameMode() == GameMode.SPECTATOR) continue;
                return false;
            }
        }
        return true;
    }

    private void materialize(ActiveDrop drop) {
        for (RenderedBlock rendered : drop.renderedBlocks) rendered.remove();
        for (Map.Entry<ScrapGrid.Cell, ScrapMaterial> entry : drop.plan.finalCells().entrySet()) {
            worldBlock(entry.getKey()).setType(material(entry.getValue()), false);
        }
        grid.markMaterialized(drop.plan);
        if (drop.plan.bulky()) {
            settledJunkBlocks += drop.plan.finalCells().size();
            Location landing = worldLocation(drop.plan.blocks().getFirst().destination()).add(0.5, 0.5, 0.5);
            world.playSound(landing, Sound.BLOCK_ANVIL_LAND, 1.4F, 0.65F);
            world.spawnParticle(Particle.BLOCK, landing, 35, 2.0, 1.0, 1.0, material(ScrapMaterial.IRON).createBlockData());
        } else {
            settledRobotBlocks += drop.plan.finalCells().size();
        }
    }

    private Location worldLocation(ScrapGrid.Cell cell) {
        return new Location(world, originX + cell.x(), floorY + cell.y(), originZ + cell.z());
    }

    private Block worldBlock(ScrapGrid.Cell cell) {
        return world.getBlockAt(originX + cell.x(), floorY + cell.y(), originZ + cell.z());
    }

    private static Material material(ScrapMaterial material) {
        return switch (material) {
            case IRON -> Material.IRON_BLOCK;
            case GOLD -> Material.GOLD_BLOCK;
            case COAL -> Material.COAL_BLOCK;
            case GLASS -> Material.LIGHT_BLUE_STAINED_GLASS;
        };
    }

    @Override
    public void close() {
        for (ActiveDrop drop : activeDrops) {
            for (RenderedBlock rendered : drop.renderedBlocks) rendered.remove();
            grid.release(drop.plan);
        }
        activeDrops.clear();
        pendingDrops.clear();
        movingBlockCount = 0;
    }

    private record PendingDrop(
            ScrapShape shape,
            int localX,
            int localZ,
            int launchClearance,
            boolean bulky,
            Location displayOrigin) {}

    private final class ActiveDrop {
        private final ScrapGrid.DropPlan plan;
        private final List<RenderedBlock> renderedBlocks;

        private ActiveDrop(ScrapGrid.DropPlan plan, Location displayOrigin) {
            this.plan = plan;
            this.renderedBlocks = plan.blocks().stream()
                    .map(motion -> new RenderedBlock(motion, displayOrigin))
                    .toList();
        }

        private void advance() { renderedBlocks.forEach(RenderedBlock::advance); }

        private boolean finishedMoving() { return renderedBlocks.stream().allMatch(RenderedBlock::finished); }
    }

    private final class RenderedBlock {
        private final ScrapGrid.BlockMotion motion;
        private final BlockDisplay display;
        private boolean awaitingEjection;
        private int nextSegment;
        private int ticksRemaining;

        private RenderedBlock(ScrapGrid.BlockMotion motion, Location displayOrigin) {
            this.motion = motion;
            this.awaitingEjection = displayOrigin != null;
            Location initialLocation = awaitingEjection ? displayOrigin : worldLocation(motion.start());
            this.display = world.spawn(initialLocation, BlockDisplay.class, spawned -> {
                spawned.setBlock(material(motion.material()).createBlockData());
                spawned.setViewRange(48);
                spawned.setShadowRadius(0.8F);
                spawned.setShadowStrength(0.7F);
            });
        }

        private void advance() {
            if (ticksRemaining > 0) {
                ticksRemaining--;
                return;
            }
            if (awaitingEjection) {
                awaitingEjection = false;
                display.setTeleportDuration(CRITICAL_EJECTION_TICKS);
                display.teleport(worldLocation(motion.start()));
                ticksRemaining = CRITICAL_EJECTION_TICKS;
                return;
            }
            if (nextSegment >= motion.segments().size()) return;
            ScrapGrid.Segment segment = motion.segments().get(nextSegment++);
            display.setTeleportDuration(segment.durationTicks());
            display.teleport(worldLocation(segment.destination()));
            ticksRemaining = segment.durationTicks();
        }

        private boolean finished() {
            return !awaitingEjection && nextSegment >= motion.segments().size() && ticksRemaining == 0;
        }

        private void remove() {
            if (display.isValid()) display.remove();
        }
    }

    private static int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
}
