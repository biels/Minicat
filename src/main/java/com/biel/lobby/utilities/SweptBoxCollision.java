package com.biel.lobby.utilities;

import java.util.List;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/** Sweeps an axis-aligned body against actual world collision boxes. */
public final class SweptBoxCollision {
    private static final double EPSILON = 1e-9;

    private SweptBoxCollision() {}

    public static final class Hit {
        public final double fraction;
        public final Vector position;
        public final Vector normal;
        public final int obstacleIndex;
        public final boolean startedInside;

        private Hit(double fraction, Vector position, Vector normal, int obstacleIndex, boolean startedInside) {
            this.fraction = fraction;
            this.position = position;
            this.normal = normal;
            this.obstacleIndex = obstacleIndex;
            this.startedInside = startedInside;
        }
    }

    /** Returns null when the complete displacement is unobstructed; touching alone is not overlap. */
    public static Hit sweep(Vector center, Vector halfExtents, Vector displacement, List<BoundingBox> obstacles) {
        Hit nearest = null;
        for (int obstacleIndex = 0; obstacleIndex < obstacles.size(); obstacleIndex++) {
            Hit candidate = sweepBox(center, halfExtents, displacement, obstacles.get(obstacleIndex), obstacleIndex);
            if (candidate != null && (nearest == null || candidate.fraction < nearest.fraction)) nearest = candidate;
        }
        return nearest;
    }

    public static boolean overlaps(Vector center, Vector halfExtents, BoundingBox obstacle) {
        return center.getX() + halfExtents.getX() > obstacle.getMinX() + EPSILON
                && center.getX() - halfExtents.getX() < obstacle.getMaxX() - EPSILON
                && center.getY() + halfExtents.getY() > obstacle.getMinY() + EPSILON
                && center.getY() - halfExtents.getY() < obstacle.getMaxY() - EPSILON
                && center.getZ() + halfExtents.getZ() > obstacle.getMinZ() + EPSILON
                && center.getZ() - halfExtents.getZ() < obstacle.getMaxZ() - EPSILON;
    }

    private static Hit sweepBox(Vector center, Vector halfExtents, Vector displacement,
                                BoundingBox obstacle, int obstacleIndex) {
        // Expanding the obstacle converts the body sweep into a point sweep.
        double[] minimum = {obstacle.getMinX() - halfExtents.getX(), obstacle.getMinY() - halfExtents.getY(),
                obstacle.getMinZ() - halfExtents.getZ()};
        double[] maximum = {obstacle.getMaxX() + halfExtents.getX(), obstacle.getMaxY() + halfExtents.getY(),
                obstacle.getMaxZ() + halfExtents.getZ()};
        double[] origin = {center.getX(), center.getY(), center.getZ()};
        double[] movement = {displacement.getX(), displacement.getY(), displacement.getZ()};

        if (overlaps(center, halfExtents, obstacle)) {
            double escapeDistance = Double.POSITIVE_INFINITY;
            int escapeAxis = 0;
            double escapeSign = -1;
            for (int axis = 0; axis < 3; axis++) {
                if (origin[axis] - minimum[axis] < escapeDistance) {
                    escapeDistance = origin[axis] - minimum[axis];
                    escapeAxis = axis;
                    escapeSign = -1;
                }
                if (maximum[axis] - origin[axis] < escapeDistance) {
                    escapeDistance = maximum[axis] - origin[axis];
                    escapeAxis = axis;
                    escapeSign = 1;
                }
            }
            Vector normal = axisVector(escapeAxis, escapeSign);
            Vector corrected = center.clone().add(normal.clone().multiply(escapeDistance));
            return new Hit(0, corrected, normal, obstacleIndex, true);
        }

        double enter = Double.NEGATIVE_INFINITY;
        double leave = Double.POSITIVE_INFINITY;
        int contactAxis = -1;
        double contactSign = 0;
        for (int axis = 0; axis < 3; axis++) {
            if (Math.abs(movement[axis]) < EPSILON) {
                // A body travelling exactly along a face must not collide with that face's edges.
                if (origin[axis] <= minimum[axis] + EPSILON || origin[axis] >= maximum[axis] - EPSILON) return null;
                continue;
            }
            double first = (minimum[axis] - origin[axis]) / movement[axis];
            double second = (maximum[axis] - origin[axis]) / movement[axis];
            double axisEnter = Math.min(first, second);
            double axisLeave = Math.max(first, second);
            if (axisEnter > enter) {
                enter = axisEnter;
                contactAxis = axis;
                contactSign = movement[axis] > 0 ? -1 : 1;
            }
            leave = Math.min(leave, axisLeave);
            if (enter >= leave - EPSILON) return null;
        }
        if (contactAxis < 0 || enter < -EPSILON || enter > 1 + EPSILON || leave <= EPSILON) return null;
        double fraction = Math.max(0, Math.min(1, enter));
        return new Hit(fraction, center.clone().add(displacement.clone().multiply(fraction)),
                axisVector(contactAxis, contactSign), obstacleIndex, false);
    }

    private static Vector axisVector(int axis, double value) {
        return new Vector(axis == 0 ? value : 0, axis == 1 ? value : 0, axis == 2 ? value : 0);
    }
}
