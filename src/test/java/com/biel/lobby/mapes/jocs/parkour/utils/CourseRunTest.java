package com.biel.lobby.mapes.jocs.parkour.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseRunTest {
    private static final CourseProfile.Position ENTRY = new CourseProfile.Position(1.5, -61, 60.5, 0, 10);
    private static CourseProfile.Checkpoint checkpoint(String id, int y) {
        CourseProfile.BlockPosition trigger = new CourseProfile.BlockPosition(3, y, 62);
        return new CourseProfile.Checkpoint(id, trigger, trigger.safePosition(0, 0), null);
    }

    @Test void startPlateOwnsTimingAndCannotRestartARunningCourse() {
        CourseRun run = new CourseRun(ENTRY);
        assertEquals(0, run.elapsedTicks(20));
        assertFalse(run.checkpoint(checkpoint("first", 10), 20));
        assertTrue(run.start(25, ENTRY));
        assertFalse(run.start(100, ENTRY));
        assertEquals(75, run.elapsedTicks(100));
    }

    @Test void backtrackingMovesReturnPointWithoutFarmingAwards() {
        CourseRun run = new CourseRun(ENTRY);
        run.start(0, ENTRY);
        CourseProfile.Checkpoint first = checkpoint("first", 10), second = checkpoint("second", 20);
        assertTrue(run.checkpoint(first, 10));
        assertFalse(run.checkpoint(first, 11));
        assertTrue(run.checkpoint(second, 20));
        assertFalse(run.checkpoint(first, 25));
        assertEquals("first", run.checkpointId());
        assertEquals(first.safePosition(), run.returnPosition());
        assertEquals(2, run.completedCheckpoints().size());
        assertThrows(UnsupportedOperationException.class, () -> run.completedCheckpoints().clear());
    }

    @Test void failureHasOneEventAndTeleportGuardBlocksReentry() {
        CourseRun run = new CourseRun(ENTRY);
        run.start(0, ENTRY);
        run.checkpoint(checkpoint("first", 10), 10);
        assertTrue(run.fail(20));
        assertFalse(run.fail(20));
        assertFalse(run.fail(27));
        assertFalse(run.checkpoint(checkpoint("second", 20), 27));
        assertFalse(run.finish(27));
        assertEquals(1, run.failures());
        assertEquals("first", run.checkpointId());
        assertTrue(run.fail(28));
        assertEquals(2, run.failures());
    }

    @Test void finishIsIdempotentAndFreezesElapsedTime() {
        CourseRun run = new CourseRun(ENTRY);
        assertFalse(run.finish(10));
        run.start(20, ENTRY);
        assertTrue(run.finish(120));
        assertFalse(run.finish(150));
        assertFalse(run.fail(150));
        assertFalse(run.checkpoint(checkpoint("first", 10), 150));
        assertEquals(100, run.elapsedTicks(150));
    }

    @Test void resumeGuardPreservesProgressAndDoesNotCountAFailure() {
        CourseRun run = new CourseRun(ENTRY);
        run.start(20, ENTRY);
        run.checkpoint(checkpoint("first", 10), 30);
        run.guard(100);
        assertTrue(run.guarded(107));
        assertFalse(run.guarded(108));
        assertEquals("first", run.checkpointId());
        assertEquals(0, run.failures());
        assertEquals(90, run.elapsedTicks(110));
    }
}
