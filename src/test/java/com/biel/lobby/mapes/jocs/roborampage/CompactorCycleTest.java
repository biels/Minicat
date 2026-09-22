package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;
import static com.biel.lobby.mapes.jocs.roborampage.utils.CompactorCycle.Action.*;
import static com.biel.lobby.mapes.jocs.roborampage.utils.CompactorCycle.Phase.*;
import com.biel.lobby.mapes.jocs.roborampage.utils.CompactorCycle;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules;
import java.util.Random;
import org.junit.jupiter.api.Test;

class CompactorCycleTest {
    private CompactorCycle warning(boolean near) {
        CompactorCycle cycle = new CompactorCycle();
        for (int tick = 0; tick < 59; tick++) assertEquals(NONE, cycle.tick(true, near, false, false));
        assertEquals(near ? WARN_SLAM : WARN_THROW, cycle.tick(true, near, false, false));
        return cycle;
    }

    @Test void attacksCommitForFullWarningThenExposeArmor() {
        for (boolean near : new boolean[]{true, false}) {
            CompactorCycle cycle = warning(near);
            assertEquals(0.35, cycle.incomingDamageMultiplier());
            for (int tick = 0; tick < 29; tick++) assertEquals(NONE, cycle.tick(true, !near, false, true));
            assertEquals(near ? SLAM : THROW, cycle.tick(true, !near, false, true));
            assertEquals(RECOVERY, cycle.phase());
            assertEquals(1.5, cycle.incomingDamageMultiplier());
            for (int tick = 0; tick < 39; tick++) cycle.tick(true, true, false, true);
            assertTrue(cycle.exposed());
            cycle.tick(true, true, false, true);
            assertEquals(APPROACH, cycle.phase());
        }
    }

    @Test void continuousTaserInterruptsEvenOnStrikeTickAndCannotStunLock() {
        CompactorCycle cycle = warning(true);
        for (int tick = 0; tick < 20; tick++) cycle.tick(true, true, false, false);
        for (int tick = 0; tick < 9; tick++) assertEquals(NONE, cycle.tick(true, true, true, false));
        assertEquals(STUN, cycle.tick(true, true, true, false));
        assertEquals(STUNNED, cycle.phase());
        assertTrue(cycle.exposed());
        for (int tick = 0; tick < 60; tick++) assertNotEquals(STUN, cycle.tick(true, true, true, true));
        assertEquals(APPROACH, cycle.phase());
        for (int tick = 0; tick < 20; tick++) cycle.tick(true, true, true, true);
        for (int tick = 0; tick < 29; tick++) assertNotEquals(STUN, cycle.tick(true, true, true, true));
        assertEquals(SLAM, cycle.tick(true, true, true, true), "immunity allows next warned attack");
        for (int tick = 0; tick < 90; tick++) cycle.tick(false, false, true, true);
        assertTrue(cycle.canBeInterrupted());
    }

    @Test void brokenTaserContactDoesNotAccumulateAndLostTargetCancelsAttack() {
        CompactorCycle cycle = warning(false);
        for (int tick = 0; tick < 9; tick++) cycle.tick(true, false, true, false);
        cycle.tick(true, false, false, false);
        for (int tick = 0; tick < 9; tick++) assertNotEquals(STUN, cycle.tick(true, false, true, false));
        assertEquals(NONE, cycle.tick(false, false, true, false));
        assertEquals(APPROACH, cycle.phase());
        for (int tick = 0; tick < 39; tick++) assertEquals(NONE, cycle.tick(true, true, false, false));
        assertEquals(WARN_SLAM, cycle.tick(true, true, false, false));
    }

    @Test void enrageShortensApproachWithoutShorteningWarningOrRecovery() {
        for (boolean enraged : new boolean[]{false, true}) {
            CompactorCycle cycle = warning(true);
            for (int tick = 0; tick < 70; tick++) cycle.tick(true, true, false, enraged);
            int approach = enraged ? 20 : 50;
            for (int tick = 0; tick < approach - 1; tick++) assertEquals(NONE, cycle.tick(true, true, false, enraged));
            assertEquals(WARN_SLAM, cycle.tick(true, true, false, enraged));
        }
    }

    @Test void bossCadenceAndPartyScalingAreBoundedAndNeverLeakIntoOrdinarySpawns() {
        assertFalse(CompactorCycle.isBossWave(0));
        assertFalse(CompactorCycle.isBossWave(4));
        assertTrue(CompactorCycle.isBossWave(5));
        assertTrue(CompactorCycle.isBossWave(10));
        assertEquals(240, CompactorCycle.maximumHealth(0));
        assertEquals(660, CompactorCycle.maximumHealth(100));
        assertEquals(24, CompactorCycle.deathScrap(1));
        assertEquals(48, CompactorCycle.deathScrap(100));
        var limits = RoboRampageRules.liveSpawnLimits(200, 20, 100);
        for (int seed = 0; seed < 100; seed++) {
            assertNotEquals(RoboRampageRules.RobotType.COMPACTOR, RoboRampageRules.chooseSpawn(limits,
                    new RoboRampageRules.RobotCounts(0, 0, 0, 0, 0), new Random(seed)).orElseThrow());
        }
    }
}
