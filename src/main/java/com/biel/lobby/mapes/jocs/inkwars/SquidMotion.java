package com.biel.lobby.mapes.jocs.inkwars;

import org.bukkit.util.Vector;

/** Geometry operations shared by surface transitions and free-flight collisions. */
public final class SquidMotion {
    private static final double EPSILON = 1.0e-12;

    private SquidMotion() {
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
