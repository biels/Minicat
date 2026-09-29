package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.*;
import static com.biel.lobby.mapes.jocs.roborampage.utils.SpringRobotCycle.Action.*;

import org.junit.jupiter.api.Test;

class SpringRobotCycleTest {
    private SpringRobotCycle warning() {
        SpringRobotCycle cycle = new SpringRobotCycle();
        for (int tick = 0; tick < 39; tick++) assertEquals(NONE, cycle.tick(true, true, false));
        assertEquals(WARN, cycle.tick(true, true, false));
        return cycle;
    }

    @Test void springAlwaysWarnsForOneSecondThenWaitsFiveSecondsAndRequiresGround() {
        SpringRobotCycle cycle = warning();
        for (int tick = 0; tick < 19; tick++) assertEquals(NONE, cycle.tick(true, true, false));
        assertEquals(JUMP, cycle.tick(true, true, false));
        for (int tick = 0; tick < 100; tick++) assertEquals(NONE, cycle.tick(true, false, false));
        assertEquals(WARN, cycle.tick(true, true, false));
    }

    @Test void taserCancelsEvenOnLaunchTickAndCannotAllowImmediateRecompression() {
        SpringRobotCycle cycle = warning();
        for (int tick = 0; tick < 19; tick++) cycle.tick(true, true, false);
        assertEquals(CANCEL, cycle.tick(true, true, true));
        assertFalse(cycle.windingUp());
        for (int tick = 0; tick < 99; tick++) assertEquals(NONE, cycle.tick(true, true, false));
        assertEquals(WARN, cycle.tick(true, true, false));
    }

    @Test void losingTargetOrGroundCancelsAndContinuousTaserBlocksWindup() {
        assertEquals(CANCEL, warning().tick(false, true, false));
        assertEquals(CANCEL, warning().tick(true, false, false));
        SpringRobotCycle cycle = new SpringRobotCycle();
        for (int tick = 0; tick < 400; tick++) assertEquals(NONE, cycle.tick(true, true, true));
        assertEquals(WARN, cycle.tick(true, true, false));
    }
}
