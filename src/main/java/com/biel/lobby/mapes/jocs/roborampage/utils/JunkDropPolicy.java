package com.biel.lobby.mapes.jocs.roborampage.utils;

/** Sustained severe unevenness earns an occasional bulky delivery, never a continuous stream. */
public final class JunkDropPolicy {
    static final int MINIMUM_SETTLED_BLOCKS = 12;
    static final int UNEVENNESS_THRESHOLD = 4;
    static final int REQUIRED_SAMPLES = 8;
    static final int COOLDOWN_SAMPLES = 45;

    private int unevenSamples;
    private int cooldownSamples;

    /** Called once per second. */
    public boolean sample(int settledBlockCount, int unevenness, boolean bulkyDropInFlight) {
        if (cooldownSamples > 0) {
            cooldownSamples--;
            unevenSamples = 0;
            return false;
        }
        if (bulkyDropInFlight || settledBlockCount < MINIMUM_SETTLED_BLOCKS
                || unevenness < UNEVENNESS_THRESHOLD) {
            unevenSamples = 0;
            return false;
        }
        unevenSamples++;
        if (unevenSamples < REQUIRED_SAMPLES) return false;
        unevenSamples = 0;
        cooldownSamples = COOLDOWN_SAMPLES;
        return true;
    }
}
