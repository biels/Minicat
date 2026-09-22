package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TaserRulesTest {
    @Test
    void chargeBandsContractFromFiveTargetsToOne() {
        assertEquals(5, TaserRules.maximumTargets(100, 1));
        assertEquals(5, TaserRules.maximumTargets(80, 1));
        assertEquals(4, TaserRules.maximumTargets(79, 1));
        assertEquals(2, TaserRules.maximumTargets(20, 1));
        assertEquals(1, TaserRules.maximumTargets(19, 1));
        assertEquals(0, TaserRules.maximumTargets(0, 1));
    }

    @Test
    void upgradesIncreaseCapacityTargetsAndRechargeSpeedWithinBounds() {
        assertEquals(100, TaserRules.maximumCharge(1));
        assertEquals(180, TaserRules.maximumCharge(5));
        assertEquals(7, TaserRules.maximumTargets(180, 5));
        assertEquals(5, TaserRules.rechargeIntervalTicks(1));
        assertEquals(3, TaserRules.rechargeIntervalTicks(5));
        assertEquals(5, TaserRules.normalizedLevel(99));
    }
}
