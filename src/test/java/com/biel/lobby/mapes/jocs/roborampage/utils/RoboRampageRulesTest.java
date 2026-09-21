package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

class RoboRampageRulesTest {
    @Test
    void liveLimitsPreserveUnlockOrderAndStayBounded() {
        var bottom = RoboRampageRules.liveSpawnLimits(0, 1);
        assertEquals(2, bottom.zombies());
        assertEquals(0, bottom.skeletons());
        assertEquals(0, bottom.blazes());

        var highFourPlayer = RoboRampageRules.liveSpawnLimits(255, 4);
        assertTrue(highFourPlayer.zombies() <= 10);
        assertTrue(highFourPlayer.skeletons() <= 6);
        assertTrue(highFourPlayer.blazes() <= 4);
        assertTrue(highFourPlayer.total() <= 24);
    }

    @Test
    void spawnChoiceReturnsEmptyImmediatelyWhenCapsAreFull() {
        var limits = new RoboRampageRules.SpawnLimits(2, 1, 1, 4);
        var counts = new RoboRampageRules.RobotCounts(2, 1, 1);
        assertTrue(RoboRampageRules.chooseSpawn(limits, counts, new Random(1)).isEmpty());
    }

    @Test
    void spawnChoiceNeverSelectsAFullRole() {
        var limits = new RoboRampageRules.SpawnLimits(2, 1, 1, 4);
        var counts = new RoboRampageRules.RobotCounts(2, 0, 0);
        for (int seed = 0; seed < 100; seed++) {
            var chosen = RoboRampageRules.chooseSpawn(limits, counts, new Random(seed)).orElseThrow();
            assertTrue(chosen == RoboRampageRules.RobotType.SKELETON
                    || chosen == RoboRampageRules.RobotType.BLAZE);
        }
    }
}
