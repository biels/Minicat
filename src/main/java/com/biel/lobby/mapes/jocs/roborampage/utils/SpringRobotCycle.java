package com.biel.lobby.mapes.jocs.roborampage.utils;

/** Grounded spring compression, a committed jump, and recovery without midair recharging. */
public final class SpringRobotCycle {
    public enum Action { NONE, WARN, JUMP, CANCEL }
    public static final int WINDUP_TICKS = 20;
    public static final int COOLDOWN_TICKS = 100;

    private int cooldownTicks = 40;
    private int windupTicks;

    public Action tick(boolean targetReachable, boolean grounded, boolean energized) {
        if (cooldownTicks > 0) cooldownTicks--;
        if (windupTicks > 0) {
            if (!targetReachable || !grounded || energized) {
                windupTicks = 0;
                cooldownTicks = COOLDOWN_TICKS;
                return Action.CANCEL;
            }
            if (--windupTicks == 0) {
                cooldownTicks = COOLDOWN_TICKS;
                return Action.JUMP;
            }
        } else if (cooldownTicks == 0 && targetReachable && grounded && !energized) {
            windupTicks = WINDUP_TICKS;
            return Action.WARN;
        }
        return Action.NONE;
    }

    public boolean windingUp() { return windupTicks > 0; }
}
