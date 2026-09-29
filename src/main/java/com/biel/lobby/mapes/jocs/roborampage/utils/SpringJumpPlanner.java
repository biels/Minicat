package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.Optional;
import java.util.function.Predicate;

/** Predicts an unsteered living-entity jump using vanilla air drag and gravity. */
public final class SpringJumpPlanner {
    public static final double MAXIMUM_STEP_HEIGHT = 4;
    public static final double MAXIMUM_HORIZONTAL_DISTANCE = 3.5;
    public static final double MAXIMUM_HORIZONTAL_SPEED = 0.42;
    public static final double VERTICAL_SPEED = 0.82;
    public static final double GRAVITY = 0.08;
    public static final double VERTICAL_DRAG = 0.98;
    public static final double HORIZONTAL_DRAG = 0.91;
    public static final double LAUNCH_GROUND_DRAG = 0.6 * HORIZONTAL_DRAG;
    private static final int MAXIMUM_FLIGHT_TICKS = 40;

    private SpringJumpPlanner() {}

    public record Point(double x, double y, double z) {
        public double horizontalDistanceSquared(Point other) {
            double dx = x - other.x, dz = z - other.z;
            return dx * dx + dz * dz;
        }
    }

    public record Jump(double velocityX, double velocityY, double velocityZ, double flightTicks) {}

    /** Lands at the target plane on descent, rather than aiming an arbitrary hop at it. */
    public static Optional<Jump> plan(Point origin, Point landing, Predicate<Point> bodyClear) {
        double rise = landing.y - origin.y;
        double distanceSquared = origin.horizontalDistanceSquared(landing);
        if (rise < 0.5 || rise > MAXIMUM_STEP_HEIGHT
                || distanceSquared > MAXIMUM_HORIZONTAL_DISTANCE * MAXIMUM_HORIZONTAL_DISTANCE) return Optional.empty();
        double height = 0, velocityY = VERTICAL_SPEED, horizontalFactor = 0;
        for (int tick = 1; tick <= MAXIMUM_FLIGHT_TICKS; tick++) {
            double nextHeight = height + velocityY;
            // The first travel tick starts grounded; floor/scaffold friction applies once.
            double drag = tick == 1 ? 1 : LAUNCH_GROUND_DRAG * Math.pow(HORIZONTAL_DRAG, tick - 2);
            if (velocityY < 0 && height >= rise && nextHeight <= rise) {
                double fraction = (height - rise) / -velocityY;
                double factor = horizontalFactor + drag * fraction;
                double velocityX = (landing.x - origin.x) / factor;
                double velocityZ = (landing.z - origin.z) / factor;
                if (velocityX * velocityX + velocityZ * velocityZ
                        > MAXIMUM_HORIZONTAL_SPEED * MAXIMUM_HORIZONTAL_SPEED) return Optional.empty();
                Jump jump = new Jump(velocityX, VERTICAL_SPEED, velocityZ, tick - 1 + fraction);
                return clearTrajectory(origin, jump, bodyClear) ? Optional.of(jump) : Optional.empty();
            }
            height = nextHeight;
            horizontalFactor += drag;
            velocityY = (velocityY - GRAVITY) * VERTICAL_DRAG;
        }
        return Optional.empty();
    }

    public static Point position(Point origin, Jump jump, double elapsedTicks) {
        double x = origin.x, y = origin.y, z = origin.z;
        double velocityX = jump.velocityX, velocityY = jump.velocityY, velocityZ = jump.velocityZ;
        int wholeTicks = (int) Math.floor(elapsedTicks);
        for (int tick = 0; tick < wholeTicks; tick++) {
            x += velocityX;
            y += velocityY;
            z += velocityZ;
            velocityX *= tick == 0 ? LAUNCH_GROUND_DRAG : HORIZONTAL_DRAG;
            velocityY = (velocityY - GRAVITY) * VERTICAL_DRAG;
            velocityZ *= tick == 0 ? LAUNCH_GROUND_DRAG : HORIZONTAL_DRAG;
        }
        double fraction = elapsedTicks - wholeTicks;
        return new Point(x + velocityX * fraction, y + velocityY * fraction, z + velocityZ * fraction);
    }

    private static boolean clearTrajectory(Point origin, Jump jump, Predicate<Point> bodyClear) {
        // Quarter-tick samples cover the entire ascent/descent and the final landing body.
        for (double time = 0; time < jump.flightTicks; time += 0.25) {
            if (!bodyClear.test(position(origin, jump, time))) return false;
        }
        return bodyClear.test(position(origin, jump, jump.flightTicks));
    }
}
