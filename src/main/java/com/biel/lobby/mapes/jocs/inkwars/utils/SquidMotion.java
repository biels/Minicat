package com.biel.lobby.mapes.jocs.inkwars.utils;

import org.bukkit.util.Vector;

/** Geometry operations shared by surface transitions and free-flight collisions. */
public final class SquidMotion {
    private static final double EPSILON = 1.0e-12;
    public static final int JET_PUSH_TICKS = 8;
    public static final int JET_DURATION_TICKS = 11;
    public static final double JET_KICK = 0.12;

    public static double jetEnvelope(int tick) {
        if (tick < 0 || tick >= JET_DURATION_TICKS) return 0;
        if (tick == 0) return 0.7;
        if (tick < JET_PUSH_TICKS) return 1;
        double release = (tick - JET_PUSH_TICKS + 1) / 3.0;
        return 1 - release * release * (3 - 2 * release);
    }

    /** Powered travel uses acceleration and speed-squared drag, not an imposed speed curve. */
    public static Vector jetStep(Vector velocity, Vector direction, int tick, double speedLimit) {
        double speed = velocity.length();
        Vector next = velocity.clone().multiply(Math.max(0, 1 - 0.04 * speed));
        if (direction.lengthSquared() > EPSILON) {
            next.add(direction.clone().normalize().multiply(0.065 * jetEnvelope(tick)));
        }
        if (next.lengthSquared() > speedLimit * speedLimit) next.normalize().multiply(speedLimit);
        return next;
    }

    private SquidMotion() {
    }

    /** Responds to steering immediately, retaining only a short sideways drift on turns. */
    public static Vector steer(Vector velocity, Vector input, double acceleration, double topSpeed) {
        double inputLength = input.length();
        double previousSpeed = velocity.length();
        if (!Double.isFinite(inputLength) || !Double.isFinite(previousSpeed)
                || !Double.isFinite(acceleration) || acceleration < 0
                || !Double.isFinite(topSpeed) || topSpeed < 0) {
            throw new IllegalArgumentException("Steering requires finite motion and nonnegative speed settings");
        }
        if (inputLength < EPSILON) return velocity.clone();
        Vector direction = input.clone().multiply(1 / inputLength);
        double alignment = previousSpeed < EPSILON ? 1
                : Math.max(-1, Math.min(1, velocity.dot(direction) / previousSpeed));
        double forwardSpeed = previousSpeed * (0.8 + 0.2 * alignment) + acceleration;
        if (previousSpeed <= topSpeed) forwardSpeed = Math.min(topSpeed, forwardSpeed);
        Vector steered = direction.clone().multiply(forwardSpeed);
        if (alignment >= 0) {
            Vector sidewaysDrift = velocity.clone().subtract(direction.clone().multiply(velocity.dot(direction)));
            steered.add(sidewaysDrift.multiply(0.25));
        }
        double speedLimit = Math.max(previousSpeed, topSpeed);
        double steeredSpeed = steered.length();
        if (steeredSpeed > speedLimit) steered.multiply(speedLimit / steeredSpeed);
        return steered;
    }

    /** Carries surface-tangent velocity around the shortest bend between outward normals. */
    public static Vector rotateTangent(Vector velocity, Vector fromNormal, Vector toNormal) {
        Vector sourceNormal = unitNormal(fromNormal);
        Vector destinationNormal = unitNormal(toNormal);
        Vector tangentVelocity = tangent(velocity, sourceNormal);
        double tangentSpeed = tangentVelocity.length();
        if (tangentSpeed < EPSILON) return new Vector();

        Vector rotationAxis = sourceNormal.clone().crossProduct(destinationNormal);
        double sine = rotationAxis.length();
        double cosine = Math.max(-1, Math.min(1, sourceNormal.dot(destinationNormal)));
        Vector rotatedVelocity;
        if (sine < EPSILON) {
            // Parallel planes share a tangent space. For a half-turn, choose the
            // travel direction as the rotation axis rather than inventing a turn.
            rotatedVelocity = tangentVelocity;
        } else {
            rotationAxis.multiply(1 / sine);
            rotatedVelocity = tangentVelocity.clone().multiply(cosine)
                    .add(rotationAxis.clone().crossProduct(tangentVelocity).multiply(sine))
                    .add(rotationAxis.clone().multiply(rotationAxis.dot(tangentVelocity) * (1 - cosine)));
        }

        // Remove accumulated roundoff without bleeding speed over repeated bends.
        return tangent(rotatedVelocity, destinationNormal).normalize().multiply(tangentSpeed);
    }

    /** Removes only velocity directed into the surface; outward motion remains free. */
    public static Vector slide(Vector velocity, Vector outwardNormal) {
        Vector normal = unitNormal(outwardNormal);
        double inwardSpeed = Math.min(0, velocity.dot(normal));
        return velocity.clone().subtract(normal.multiply(inwardSpeed));
    }

    /** Keeps travel along the wall and adds departure impulses in world coordinates. */
    public static Vector wallJump(Vector velocity, Vector outwardNormal,
                                  double outwardImpulse, double upwardImpulse) {
        Vector normal = unitNormal(outwardNormal);
        return tangent(velocity, normal)
                .add(normal.multiply(outwardImpulse))
                .add(new Vector(0, upwardImpulse, 0));
    }

    private static Vector tangent(Vector velocity, Vector unitNormal) {
        return velocity.clone().subtract(unitNormal.clone().multiply(velocity.dot(unitNormal)));
    }

    private static Vector unitNormal(Vector normal) {
        double length = normal.length();
        if (!Double.isFinite(length) || length < EPSILON) {
            throw new IllegalArgumentException("Surface normal must be finite and nonzero");
        }
        return normal.clone().multiply(1 / length);
    }
}
