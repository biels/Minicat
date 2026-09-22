package com.biel.lobby.mapes.jocs.roborampage.utils;

/** One bounded boss attack cycle; the world adapter owns targeting and effects. */
public final class CompactorCycle {
    public enum Phase { APPROACH, SLAM_WINDUP, THROW_WINDUP, RECOVERY, STUNNED }
    public enum Action { NONE, WARN_SLAM, WARN_THROW, SLAM, THROW, STUN }

    public static final int WINDUP_TICKS = 30;
    public static final int RECOVERY_TICKS = 40;
    public static final int STUN_TICKS = 60;
    public static final int TASER_CONTACT_TICKS = 10;
    public static final int STUN_IMMUNITY_TICKS = 200;
    public static final double SLAM_RADIUS = 3.5;

    private Phase phase = Phase.APPROACH;
    private int remainingTicks = 60;
    private int taserContactTicks;
    private int stunImmunityTicks;

    public Action tick(boolean targetAvailable, boolean targetInSlamRange, boolean energized, boolean enraged) {
        if (stunImmunityTicks > 0) stunImmunityTicks--;
        if (!targetAvailable) {
            phase = Phase.APPROACH;
            remainingTicks = 40;
            taserContactTicks = 0;
            return Action.NONE;
        }
        if (windingUp() && energized && stunImmunityTicks == 0) {
            if (++taserContactTicks >= TASER_CONTACT_TICKS) {
                phase = Phase.STUNNED;
                remainingTicks = STUN_TICKS;
                stunImmunityTicks = STUN_IMMUNITY_TICKS;
                taserContactTicks = 0;
                return Action.STUN;
            }
        } else {
            taserContactTicks = 0;
        }
        if (--remainingTicks > 0) return Action.NONE;
        switch (phase) {
            case APPROACH -> {
                phase = targetInSlamRange ? Phase.SLAM_WINDUP : Phase.THROW_WINDUP;
                remainingTicks = WINDUP_TICKS;
                return targetInSlamRange ? Action.WARN_SLAM : Action.WARN_THROW;
            }
            case SLAM_WINDUP, THROW_WINDUP -> {
                Action action = phase == Phase.SLAM_WINDUP ? Action.SLAM : Action.THROW;
                phase = Phase.RECOVERY;
                remainingTicks = RECOVERY_TICKS;
                taserContactTicks = 0;
                return action;
            }
            case RECOVERY, STUNNED -> {
                phase = Phase.APPROACH;
                remainingTicks = enraged ? 20 : 50;
            }
        }
        return Action.NONE;
    }

    public Phase phase() { return phase; }
    public boolean windingUp() { return phase == Phase.SLAM_WINDUP || phase == Phase.THROW_WINDUP; }
    public boolean exposed() { return phase == Phase.RECOVERY || phase == Phase.STUNNED; }
    public boolean canBeInterrupted() { return stunImmunityTicks == 0; }
    public double incomingDamageMultiplier() { return exposed() ? 1.5 : 0.35; }

    public static boolean isBossWave(int wave) { return wave > 0 && wave % 5 == 0; }
    public static double maximumHealth(int players) { return 240 + 140 * (Math.clamp(players, 1, 4) - 1); }
    public static int deathScrap(int players) { return 24 + 8 * (Math.clamp(players, 1, 4) - 1); }
}
