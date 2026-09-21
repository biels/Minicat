package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ScrapGridTest {
    @Test
    void carConservesAllTwentyFourIntegerBlocksInsideArena() {
        ScrapGrid grid = new ScrapGrid();
        ScrapGrid.DropPlan plan = grid.planDrop(ScrapShapes.car(), 6, 7, 8, true, new Random(12));

        assertEquals(24, plan.blocks().size());
        assertEquals(24, plan.finalCells().size());
        assertEquals(24, plan.blocks().stream().map(ScrapGrid.BlockMotion::destination).distinct().count());
        int firstFall = plan.blocks().getFirst().start().y()
                - plan.blocks().getFirst().segments().getFirst().destination().y();
        for (ScrapGrid.BlockMotion block : plan.blocks()) {
            assertEquals(firstFall, block.start().y() - block.segments().getFirst().destination().y(),
                    "The car must fall as one integer-block assembly before it breaks apart");
        }
        for (ScrapGrid.Cell cell : plan.finalCells().keySet()) {
            assertTrue(cell.x() >= 0 && cell.x() < 15);
            assertTrue(cell.z() >= 0 && cell.z() < 15);
            assertTrue(cell.y() >= 1);
        }
        assertEquals(0, grid.maximumSettledHeight(), "Reserved motion must not advance progression");
        grid.markMaterialized(plan);
        assertEquals(24, grid.settledBlockCount());
        assertTrue(grid.maximumSettledHeight() > 0);
    }

    @Test
    void stackedDropsNeverReserveTheSameCell() {
        ScrapGrid grid = new ScrapGrid();
        Set<ScrapGrid.Cell> occupied = new HashSet<>();
        for (int index = 0; index < 20; index++) {
            ScrapGrid.DropPlan plan = grid.planDrop(
                    ScrapShapes.single(ScrapMaterial.IRON), 7, 7, 4, false, new Random(index));
            for (ScrapGrid.Cell cell : plan.finalCells().keySet()) assertTrue(occupied.add(cell));
            grid.markMaterialized(plan);
        }
        assertEquals(20, grid.settledBlockCount());
        assertEquals(20, grid.settledCells().size());
    }

    @Test
    void plannedMotionIsBoundedAndEndsAtItsReservedCell() {
        ScrapGrid grid = new ScrapGrid();
        ScrapGrid.DropPlan plan = grid.planDrop(ScrapShapes.car(), -100, 100, 20, true, new Random(4));
        for (ScrapGrid.BlockMotion block : plan.blocks()) {
            int duration = block.segments().stream().mapToInt(ScrapGrid.Segment::durationTicks).sum();
            assertTrue(duration <= 80, "A block should settle in a few seconds, not run indefinitely");
            assertTrue(plan.finalCells().containsKey(block.destination()));
        }
    }

    @Test
    void releaseReturnsUnmaterializedReservationButKeepsSolidScrap() {
        ScrapGrid grid = new ScrapGrid();
        ScrapGrid.DropPlan cancelled = grid.planDrop(
                ScrapShapes.single(ScrapMaterial.IRON), 3, 3, 4, false, new Random(1));
        grid.release(cancelled);
        ScrapGrid.DropPlan replacement = grid.planDrop(
                ScrapShapes.single(ScrapMaterial.GOLD), 3, 3, 4, false, new Random(1));
        assertEquals(cancelled.finalCells().keySet(), replacement.finalCells().keySet());
        grid.markMaterialized(replacement);
        grid.release(replacement);
        assertEquals(1, grid.settledBlockCount());
    }

    @Test
    void restoredWorldStateRejectsInvalidOrDuplicateCells() {
        ScrapGrid grid = new ScrapGrid();
        Map<ScrapGrid.Cell, ScrapMaterial> existing = Map.of(
                new ScrapGrid.Cell(4, 1, 4), ScrapMaterial.IRON,
                new ScrapGrid.Cell(4, 2, 4), ScrapMaterial.GOLD);
        grid.restoreSettled(existing);
        assertEquals(2, grid.maximumSettledHeight());
        assertThrows(IllegalArgumentException.class, () -> grid.restoreSettled(existing));
        assertThrows(IllegalArgumentException.class, () -> grid.restoreSettled(Map.of(
                new ScrapGrid.Cell(-1, 1, 0), ScrapMaterial.IRON)));
    }

    @Test
    void lowestPatchTargetsTheBottomRatherThanTheHighestRidge() {
        ScrapGrid grid = new ScrapGrid();
        Map<ScrapGrid.Cell, ScrapMaterial> ridge = new LinkedHashMap<>();
        for (int y = 1; y <= 6; y++) ridge.put(new ScrapGrid.Cell(7, y, 7), ScrapMaterial.IRON);
        grid.restoreSettled(ridge);
        ScrapGrid.Cell target = grid.lowestPatch(new Random(2));
        assertFalse(target.x() == 7 && target.z() == 7);
        assertEquals(6, grid.unevenness());
    }
}
