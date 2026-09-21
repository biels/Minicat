package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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

    @Test
    void ordinaryRobotsDropTwoBlocksAndCriticalKillsAddOne() {
        for (var helmet : List.of(
                RoboRampageRules.HelmetVariant.IRON_HELMET,
                RoboRampageRules.HelmetVariant.REDSTONE_BLOCK,
                RoboRampageRules.HelmetVariant.LAPIS_BLOCK)) {
            var reward = RoboRampageRules.liveGroundRobotReward(helmet);
            assertEquals(2, reward.rollBlockCount(new Random(1), false));
            assertEquals(3, reward.rollBlockCount(new Random(1), true));
        }

        var blazeReward = RoboRampageRules.liveBlazeReward();
        assertEquals(2, blazeReward.rollBlockCount(new Random(1), false));
        assertEquals(3, blazeReward.rollBlockCount(new Random(1), true));
    }

    @Test
    void ironHeadRobotsDropThreeToFiveBlocksBeforeCriticalBonus() {
        var reward = RoboRampageRules.liveGroundRobotReward(RoboRampageRules.HelmetVariant.IRON_BLOCK);
        boolean sawThree = false;
        boolean sawFive = false;
        for (int seed = 0; seed < 100; seed++) {
            int normalBlockCount = reward.rollBlockCount(new Random(seed), false);
            int criticalBlockCount = reward.rollBlockCount(new Random(seed), true);
            assertTrue(normalBlockCount >= 3 && normalBlockCount <= 5);
            assertEquals(normalBlockCount + 1, criticalBlockCount);
            sawThree |= normalBlockCount == 3;
            sawFive |= normalBlockCount == 5;
        }
        assertTrue(sawThree && sawFive);
    }
}
