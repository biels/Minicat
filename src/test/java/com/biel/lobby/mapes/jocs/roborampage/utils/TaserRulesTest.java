package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaserRulesTest {
    @Test void smallNetworksContractAsChargeRunsLow() {
        assertEquals(2, TaserRules.maximumTargets(100, 1));
        assertEquals(2, TaserRules.maximumTargets(30, 1));
        assertEquals(1, TaserRules.maximumTargets(29, 1));
        assertEquals(0, TaserRules.maximumTargets(0, 1));
        assertEquals(3, TaserRules.maximumTargets(120, 3));
        assertEquals(4, TaserRules.maximumTargets(140, 5));
        assertEquals(3, TaserRules.maximumTargets(89, 5));
        assertEquals(2, TaserRules.maximumTargets(59, 5));
    }

    @Test void moreBranchesCostMoreChargeAndUpgradesNeverAccelerateRecharge() {
        assertEquals(0, TaserRules.chargeDrain(0));
        assertEquals(1, TaserRules.chargeDrain(1));
        assertEquals(4, TaserRules.chargeDrain(4));
        assertEquals(100, TaserRules.maximumCharge(1));
        assertEquals(140, TaserRules.maximumCharge(5));
        assertEquals(5, TaserRules.rechargeIntervalTicks(1));
        assertEquals(5, TaserRules.rechargeIntervalTicks(5));
        assertEquals(5, TaserRules.normalizedLevel(99));
        assertTrue(TaserRules.RECHARGE_DELAY_TICKS
                + TaserRules.maximumCharge(1) * TaserRules.rechargeIntervalTicks(1)
                > TaserRules.maximumCharge(1) * 4, "full recovery takes much longer than one burst");
    }

    @Test void everyLevelHasAFiniteCrowdBudgetSmallerThanOneEarlyWave() {
        for (int level = 1; level <= TaserRules.MAXIMUM_LEVEL; level++) {
            int charge = TaserRules.maximumCharge(level), tick = 0;
            double totalDamage = 0;
            while (charge > 0) {
                int targets = TaserRules.maximumTargets(charge, level);
                if (tick % TaserRules.PULSE_INTERVAL_TICKS == 0) {
                    totalDamage += targets * TaserRules.DAMAGE_PER_PULSE;
                }
                charge -= TaserRules.chargeDrain(targets);
                tick++;
            }
            assertTrue(totalDamage <= 32, "level " + level + " cannot multiply free chain damage");
            assertTrue(totalDamage < RoboRampageRules.waveRobotQuota(1, 1) * 12);
        }
    }

    @Test void baseBurstCanKillAnOrdinaryTargetButNeedsAnotherToolAgainstAHeavy() {
        int pulses = (TaserRules.BASE_MAXIMUM_CHARGE - 1) / TaserRules.PULSE_INTERVAL_TICKS + 1;
        double damage = pulses * TaserRules.DAMAGE_PER_PULSE;
        assertTrue(damage >= RoboRampageRules.groundRobotProfile(
                RoboRampageRules.HelmetVariant.IRON_HELMET).maximumHealth());
        assertTrue(damage < RoboRampageRules.groundRobotProfile(
                RoboRampageRules.HelmetVariant.IRON_BLOCK).maximumHealth());
    }

    @Test void restartingNeedsRecoveryAndCannotResetPulseCooldown() {
        assertFalse(TaserRules.canActivate(19));
        assertTrue(TaserRules.canActivate(20));
        assertFalse(TaserRules.pulseDue(105, 100));
        assertTrue(TaserRules.pulseDue(110, 100));
        assertTrue(TaserRules.MINIMUM_ACTIVATION_CHARGE / TaserRules.chargeDrain(1)
                >= CompactorCycle.TASER_CONTACT_TICKS, "a recovered burst still interrupts a warned boss");
    }

    @Test void upgradesPreserveActualChargeWithoutRefillingTheWeapon() {
        assertEquals(17, TaserRules.chargeAfterUpgrade(17, 2));
        assertEquals(0, TaserRules.chargeAfterUpgrade(0, 5));
        assertEquals(140, TaserRules.chargeAfterUpgrade(999, 5));
    }

    @Test void rangeStaysGenerousEnoughToReachFlyingTargets() {
        assertEquals(12.0, TaserRules.sourceRange(false));
        assertEquals(16.0, TaserRules.sourceRange(true));
    }
}
