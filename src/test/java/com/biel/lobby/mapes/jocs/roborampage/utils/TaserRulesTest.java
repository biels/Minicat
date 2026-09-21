package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TaserRulesTest {
    @Test
    void chargeBandsContractFromFiveTargetsToOne() {
        assertEquals(5, TaserRules.maximumTargets(100));
        assertEquals(5, TaserRules.maximumTargets(80));
        assertEquals(4, TaserRules.maximumTargets(79));
        assertEquals(2, TaserRules.maximumTargets(20));
        assertEquals(1, TaserRules.maximumTargets(19));
        assertEquals(0, TaserRules.maximumTargets(0));
    }

    @Test
    void blazeAdvancesTheDropMeterThreeTimesAsFar() {
        assertEquals(1, TaserRules.robotDropPoints(RoboRampageRules.RobotType.ZOMBIE));
        assertEquals(1, TaserRules.robotDropPoints(RoboRampageRules.RobotType.SKELETON));
        assertEquals(3, TaserRules.robotDropPoints(RoboRampageRules.RobotType.BLAZE));
    }
}
