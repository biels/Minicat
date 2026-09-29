package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(4, TaserRules.rechargeIntervalTicks(1));
        assertEquals(3, TaserRules.rechargeIntervalTicks(5));
        assertEquals(5, TaserRules.normalizedLevel(99));
    }

    @Test
    void directRangeIsLongerAndEspeciallyGenerousForGhasts() {
        assertEquals(12.0, TaserRules.sourceRange(false));
        assertEquals(16.0, TaserRules.sourceRange(true));
    }

    @Test
    void oneChargeCanKillAHeavyRobotAndOrdinaryTargetsNeedFourPulses() {
        double normalHealth = RoboRampageRules.groundRobotProfile(RoboRampageRules.HelmetVariant.IRON_HELMET).maximumHealth();
        double heavyHealth = RoboRampageRules.groundRobotProfile(RoboRampageRules.HelmetVariant.IRON_BLOCK).maximumHealth();
        assertEquals(4, Math.ceil(normalHealth / TaserRules.DAMAGE_PER_PULSE));
        assertTrue(Math.ceil(heavyHealth / TaserRules.DAMAGE_PER_PULSE) * TaserRules.PULSE_INTERVAL_TICKS
                <= TaserRules.BASE_MAXIMUM_CHARGE / TaserRules.CHARGE_DRAIN_PER_TICK);
    }
}
