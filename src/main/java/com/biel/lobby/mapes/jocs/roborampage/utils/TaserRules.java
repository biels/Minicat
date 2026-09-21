package com.biel.lobby.mapes.jocs.roborampage.utils;

/** Pure balance rules for Robo Rampage's rechargeable chain-lightning weapon. */
public final class TaserRules {
    public static final int MAXIMUM_CHARGE = 100;
    public static final int CHARGE_DRAIN_PER_TICK = 1;
    public static final int RECHARGE_INTERVAL_TICKS = 5;
    public static final int RECHARGE_DELAY_TICKS = 40;
    public static final int PULSE_INTERVAL_TICKS = 10;
    public static final double DAMAGE_PER_PULSE = 2.0;
    public static final double SOURCE_RANGE = 10.0;
    public static final double JUMP_RANGE = 6.0;
    public static final int MINIMUM_DROP_POINTS = 18;
    public static final int MAXIMUM_DROP_POINTS = 26;

    private TaserRules() {}

    public static int maximumTargets(int charge) {
        if (charge <= 0) return 0;
        return Math.min(5, charge / 20 + 1);
    }

    public static int robotDropPoints(RoboRampageRules.RobotType type) {
        return type == RoboRampageRules.RobotType.BLAZE ? 3 : 1;
    }
}
