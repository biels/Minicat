package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.random.RandomGenerator;

/**
 * Authoritative integer-cell scrap model. Planning reserves final cells immediately,
 * while height/progression only sees cells after the renderer materializes them.
 */
public final class ScrapGrid {
    public static final int DEFAULT_WIDTH = 15;
    public static final int DEFAULT_DEPTH = 15;
    private static final int MAX_LATERAL_MOVES = 3;

    private final int width;
    private final int depth;
    private final Map<Cell, ScrapMaterial> reserved = new LinkedHashMap<>();
    private final Set<Cell> materialized = new LinkedHashSet<>();

    public ScrapGrid() {
        this(DEFAULT_WIDTH, DEFAULT_DEPTH);
    }

    public ScrapGrid(int width, int depth) {
        if (width < 1 || depth < 1) throw new IllegalArgumentException("Scrap grid dimensions must be positive");
        this.width = width;
        this.depth = depth;
    }

    public record Cell(int x, int y, int z) {
        public Cell add(Cell other) { return new Cell(x + other.x, y + other.y, z + other.z); }
    }

    public record Segment(Cell destination, int durationTicks) {
        public Segment {
            if (durationTicks < 1) throw new IllegalArgumentException("Motion duration must be positive");
        }
    }

    public record BlockMotion(ScrapMaterial material, Cell start, List<Segment> segments) {
        public BlockMotion {
            segments = List.copyOf(segments);
            if (segments.isEmpty()) throw new IllegalArgumentException("A block motion needs a destination");
        }

        public Cell destination() { return segments.getLast().destination(); }
    }

    public record DropPlan(List<BlockMotion> blocks, Map<Cell, ScrapMaterial> finalCells, boolean bulky) {
        public DropPlan {
            blocks = List.copyOf(blocks);
            finalCells = Map.copyOf(finalCells);
        }
    }

    public DropPlan planDrop(
            ScrapShape shape, int requestedX, int requestedZ, int launchClearance,
            boolean bulky, RandomGenerator random) {
        int minOffsetX = shape.blocks().stream().mapToInt(block -> block.offset().x()).min().orElseThrow();
        int maxOffsetX = shape.blocks().stream().mapToInt(block -> block.offset().x()).max().orElseThrow();
        int minOffsetZ = shape.blocks().stream().mapToInt(block -> block.offset().z()).min().orElseThrow();
        int maxOffsetZ = shape.blocks().stream().mapToInt(block -> block.offset().z()).max().orElseThrow();
        int originX = clamp(requestedX, -minOffsetX, width - 1 - maxOffsetX);
        int originZ = clamp(requestedZ, -minOffsetZ, depth - 1 - maxOffsetZ);

        int impactOriginY = 1;
        for (ScrapShape.Block block : shape.blocks()) {
            int supportHeight = columnHeight(
                    originX + block.offset().x(), originZ + block.offset().z(), reserved.keySet());
            impactOriginY = Math.max(impactOriginY, supportHeight + 1 - block.offset().y());
        }
        int launchOriginY = impactOriginY + Math.max(3, launchClearance);

        List<ScrapShape.Block> ordered = new ArrayList<>(shape.blocks());
        ordered.sort(Comparator.comparingInt((ScrapShape.Block block) -> block.offset().y())
                .thenComparingInt(block -> block.offset().x())
                .thenComparingInt(block -> block.offset().z()));

        Map<Cell, ScrapMaterial> finalCells = new LinkedHashMap<>();
        List<BlockMotion> motions = new ArrayList<>();
        Set<Cell> occupied = new LinkedHashSet<>(reserved.keySet());
        for (ScrapShape.Block block : ordered) {
            occupied.add(new Cell(
                    originX + block.offset().x(), impactOriginY + block.offset().y(), originZ + block.offset().z()));
        }
        for (ScrapShape.Block block : ordered) {
            Cell start = new Cell(
                    originX + block.offset().x(), launchOriginY + block.offset().y(), originZ + block.offset().z());
            Cell impact = new Cell(
                    originX + block.offset().x(), impactOriginY + block.offset().y(), originZ + block.offset().z());
            List<Segment> segments = new ArrayList<>();
            segments.add(new Segment(impact, Math.min(20, Math.max(4, (launchOriginY - impactOriginY) * 2))));
            occupied.remove(impact);
            Cell current = descend(impact, occupied, segments);
            for (int move = 0; move < MAX_LATERAL_MOVES; move++) {
                List<Cell> lowerNeighbours = lowerNeighbours(current, occupied);
                if (lowerNeighbours.isEmpty()) break;
                Cell side = lowerNeighbours.get(random.nextInt(lowerNeighbours.size()));
                segments.add(new Segment(new Cell(side.x(), current.y(), side.z()), 3));
                current = descend(new Cell(side.x(), current.y(), side.z()), occupied, segments);
            }
            occupied.add(current);
            finalCells.put(current, block.material());
            motions.add(new BlockMotion(block.material(), start, segments));
        }
        reserved.putAll(finalCells);
        return new DropPlan(motions, finalCells, bulky);
    }

    public void markMaterialized(DropPlan plan) {
        if (!reserved.entrySet().containsAll(plan.finalCells().entrySet())) {
            throw new IllegalStateException("Cannot materialize cells that are not reserved by this grid");
        }
        materialized.addAll(plan.finalCells().keySet());
    }

    public void release(DropPlan plan) {
        for (Cell cell : plan.finalCells().keySet()) {
            if (!materialized.contains(cell)) reserved.remove(cell);
        }
    }

    /** Restores already-solid scrap, useful when reconstructing authoritative world state. */
    public void restoreSettled(Map<Cell, ScrapMaterial> cells) {
        for (Map.Entry<Cell, ScrapMaterial> entry : cells.entrySet()) {
            Cell cell = entry.getKey();
            if (!inside(cell.x(), cell.z()) || cell.y() < 1) {
                throw new IllegalArgumentException("Settled scrap cell is outside the arena: " + cell);
            }
            if (reserved.putIfAbsent(cell, entry.getValue()) != null) {
                throw new IllegalArgumentException("Duplicate settled scrap cell: " + cell);
            }
            materialized.add(cell);
        }
    }

    public int maximumSettledHeight() {
        return materialized.stream().mapToInt(Cell::y).max().orElse(0);
    }

    public int settledBlockCount() { return materialized.size(); }

    public int unevenness() {
        int minimum = Integer.MAX_VALUE;
        int maximum = 0;
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                int height = columnHeight(x, z, materialized);
                minimum = Math.min(minimum, height);
                maximum = Math.max(maximum, height);
            }
        }
        return maximum - (minimum == Integer.MAX_VALUE ? 0 : minimum);
    }

    /** Lowest 3x3 neighbourhood; ties are randomized so repeated deliveries do not stack exactly. */
    public Cell lowestPatch(RandomGenerator random) {
        int best = Integer.MAX_VALUE;
        List<Cell> candidates = new ArrayList<>();
        for (int x = 2; x <= width - 3; x++) {
            for (int z = 1; z <= depth - 2; z++) {
                int total = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dz = -1; dz <= 1; dz++) total += columnHeight(x + dx, z + dz, materialized);
                }
                if (total < best) {
                    best = total;
                    candidates.clear();
                }
                if (total == best) candidates.add(new Cell(x - 2, 0, z - 1));
            }
        }
        if (candidates.isEmpty()) return new Cell(0, 0, 0);
        return candidates.get(random.nextInt(candidates.size()));
    }

    public boolean containsSettled(Cell cell) { return materialized.contains(cell); }

    public Map<Cell, ScrapMaterial> settledCells() {
        Map<Cell, ScrapMaterial> result = new LinkedHashMap<>();
        for (Cell cell : materialized) result.put(cell, reserved.get(cell));
        return Map.copyOf(result);
    }

    private Cell descend(Cell start, Set<Cell> occupied, List<Segment> segments) {
        Cell destination = start;
        while (destination.y() > 1) {
            Cell below = new Cell(destination.x(), destination.y() - 1, destination.z());
            if (occupied.contains(below)) break;
            destination = below;
        }
        if (!destination.equals(start)) {
            int distance = start.y() - destination.y();
            segments.add(new Segment(destination, Math.min(20, Math.max(4, distance * 2))));
        }
        return destination;
    }

    private List<Cell> lowerNeighbours(Cell current, Set<Cell> occupied) {
        int currentSupport = columnHeight(current.x(), current.z(), occupied);
        List<Cell> result = new ArrayList<>();
        for (int[] direction : List.of(new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1})) {
            int x = current.x() + direction[0];
            int z = current.z() + direction[1];
            if (!inside(x, z)) continue;
            int neighbourSupport = columnHeight(x, z, occupied);
            Cell sideAtCurrentHeight = new Cell(x, current.y(), z);
            if (neighbourSupport < currentSupport && !occupied.contains(sideAtCurrentHeight)) {
                result.add(new Cell(x, neighbourSupport + 1, z));
            }
        }
        return result;
    }

    private int columnHeight(int x, int z, Set<Cell> cells) {
        int height = 0;
        for (Cell cell : cells) {
            if (cell.x() == x && cell.z() == z) height = Math.max(height, cell.y());
        }
        return height;
    }

    private boolean inside(int x, int z) { return x >= 0 && x < width && z >= 0 && z < depth; }

    private static int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }
}
