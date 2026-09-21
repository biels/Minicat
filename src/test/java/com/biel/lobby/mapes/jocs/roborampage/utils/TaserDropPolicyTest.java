package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

class TaserDropPolicyTest {
    @Test
    void dropArrivesWithinTheBoundedPointWindow() {
        var random = new Random(11);
        var policy = new TaserDropPolicy(random);
        int threshold = policy.nextDropThreshold();

        for (int point = 1; point < threshold; point++) {
            assertFalse(policy.recordRobotDeath(RoboRampageRules.RobotType.ZOMBIE, 0, 1, random));
        }
        assertTrue(policy.recordRobotDeath(RoboRampageRules.RobotType.ZOMBIE, 0, 1, random));
    }

    @Test
    void capRetainsEarnedProgressUntilAnotherTaserIsAllowed() {
        var random = new Random(3);
        var policy = new TaserDropPolicy(random);
        for (int point = 0; point < TaserRules.MAXIMUM_DROP_POINTS; point++) {
            assertFalse(policy.recordRobotDeath(RoboRampageRules.RobotType.ZOMBIE, 1, 1, random));
        }

        assertTrue(policy.recordRobotDeath(RoboRampageRules.RobotType.ZOMBIE, 1, 2, random));
    }
}
