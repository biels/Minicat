package com.biel.lobby.mapes.jocs.roborampage.utils;

/** Match-owned jetpack fuel and one-use landing protection, independent of items. */
public final class JetpackRules {
    public static final int MAXIMUM_FUEL_TICKS = 60;
    public static final int RECHARGE_DELAY_TICKS = 20;
    public static final int RECHARGE_INTERVAL_TICKS = 2;
    public static final int FALL_PROTECTION_TICKS = 100;
    public static final double VERTICAL_SPEED = 0.36;
    public static final double FORWARD_SPEED = 0.16;

    private JetpackRules() {}

    public static final class State {
        private int fuelTicks = MAXIMUM_FUEL_TICKS;
        private boolean firing;
        private boolean observedAirborne;
        private long landedAtTick = Long.MIN_VALUE / 2;
        private long lastThrustTick = Long.MIN_VALUE / 2;
        private long fallProtectedUntil = Long.MIN_VALUE / 2;

        public int fuelTicks() { return fuelTicks; }
        public int chargePercent() { return (fuelTicks * 100 + MAXIMUM_FUEL_TICKS - 1) / MAXIMUM_FUEL_TICKS; }
        public boolean firing() { return firing; }

        public boolean toggle() {
            firing = !firing && fuelTicks > 0;
            return firing;
        }

        /** Returns whether this tick should apply thrust. Recharge requires physical support. */
        public boolean tick(long tick, boolean eligible, boolean holdingJetpack, boolean supported) {
            if (!eligible) {
                resetFlight();
                return false;
            }
            if (!holdingJetpack || fuelTicks == 0) firing = false;
            if (!supported && tick <= fallProtectedUntil) {
                observedAirborne = true;
                landedAtTick = Long.MIN_VALUE / 2;
            }
            if (firing) {
                fuelTicks--;
                lastThrustTick = tick;
                fallProtectedUntil = tick + FALL_PROTECTION_TICKS;
                landedAtTick = Long.MIN_VALUE / 2;
                if (!supported) observedAirborne = true;
                if (fuelTicks == 0) firing = false;
                return true;
            }
            if (supported && observedAirborne) {
                // Give the native landing damage event a tick to consume the shield,
                // even if scheduler support sampling happens before that event.
                if (landedAtTick == Long.MIN_VALUE / 2) landedAtTick = tick;
                else if (tick > landedAtTick) {
                    fallProtectedUntil = Long.MIN_VALUE / 2;
                    observedAirborne = false;
                }
            }
            if (supported && tick - lastThrustTick >= RECHARGE_DELAY_TICKS
                    && tick % RECHARGE_INTERVAL_TICKS == 0) {
                fuelTicks = Math.min(MAXIMUM_FUEL_TICKS, fuelTicks + 1);
            }
            return false;
        }

        public boolean consumeFallProtection(long tick) {
            if (tick > fallProtectedUntil) return false;
            fallProtectedUntil = Long.MIN_VALUE / 2;
            return true;
        }

        /** Respawning, teleporting away or becoming a spectator never refills fuel. */
        public void resetFlight() {
            firing = false;
            observedAirborne = false;
            landedAtTick = Long.MIN_VALUE / 2;
            fallProtectedUntil = Long.MIN_VALUE / 2;
        }
    }
}
