package com.biel.lobby.mapes.jocs.roborampage.utils;

/** Pure balance rules for Robo Rampage's rechargeable chain-lightning weapon. */
public final class TaserRules {
    public static final int BASE_MAXIMUM_CHARGE = 100;
    public static final int MAXIMUM_LEVEL = 5;
    public static final int CHARGE_DRAIN_PER_TICK = 1;
    public static final int MINIMUM_ACTIVATION_CHARGE = 20;
    public static final int RECHARGE_INTERVAL_TICKS = 5;
    public static final int RECHARGE_DELAY_TICKS = 60;
    public static final int PULSE_INTERVAL_TICKS = 10;
    public static final double DAMAGE_PER_PULSE = 2.0;
    public static final double SOURCE_RANGE = 12.0;
    public static final double GHAST_SOURCE_RANGE = 16.0;
    public static final double JUMP_RANGE = 6.0;

    private TaserRules() {}

    public static int maximumCharge(int level) {
        return BASE_MAXIMUM_CHARGE + (normalizedLevel(level) - 1) * 10;
    }

    public static int maximumTargets(int charge, int level) {
        if (charge <= 0) return 0;
        int levelTargetCap = 2 + (normalizedLevel(level) - 1) / 2;
        return Math.min(levelTargetCap, charge / 30 + 1);
    }

    public static int rechargeIntervalTicks(int level) {
        return RECHARGE_INTERVAL_TICKS;
    }

    /** Every energized chassis has a cost; additional branches cannot multiply free damage. */
    public static int chargeDrain(int targetCount) {
        return Math.max(0, targetCount) * CHARGE_DRAIN_PER_TICK;
    }

    public static boolean canActivate(int charge) {
        return charge >= MINIMUM_ACTIVATION_CHARGE;
    }

    public static boolean pulseDue(long tick, long lastPulseTick) {
        return tick - lastPulseTick >= PULSE_INTERVAL_TICKS;
    }

    /** Upgrades improve capacity without manufacturing another fully charged burst. */
    public static int chargeAfterUpgrade(int previousCharge, int upgradedLevel) {
        return Math.clamp(previousCharge, 0, maximumCharge(upgradedLevel));
    }

    public static double sourceRange(boolean ghast) {
        return ghast ? GHAST_SOURCE_RANGE : SOURCE_RANGE;
    }

    public static int normalizedLevel(int level) {
        return Math.max(1, Math.min(MAXIMUM_LEVEL, level));
    }
}
