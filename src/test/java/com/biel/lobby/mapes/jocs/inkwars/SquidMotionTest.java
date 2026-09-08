package com.biel.lobby.mapes.jocs.inkwars;

import java.util.Random;
import org.bukkit.util.Vector;

/** Standalone geometry regression checks; no server or test framework required. */
public final class SquidMotionTest {
    private static final double TOLERANCE = 1.0e-9;

    public static void main(String[] args) {
        Vector floor = new Vector(0, 1, 0);
        Vector wall = new Vector(-1, 0, 0);
        Vector ceiling = new Vector(0, -1, 0);
        Vector originalVelocity = new Vector(0.8, 0, 0.3);
        Vector climb = SquidMotion.rotateTangent(originalVelocity, floor, wall);
        vectorEquals(new Vector(0, 0.8, 0.3), climb, "Floor-to-wall climb");
        vectorEquals(new Vector(-0.8, 0, 0.3),
                SquidMotion.rotateTangent(climb, wall, ceiling), "Wall-to-ceiling wrap");
        vectorEquals(originalVelocity, SquidMotion.rotateTangent(climb, wall, floor), "Roof crest");
        vectorEquals(originalVelocity, SquidMotion.rotateTangent(originalVelocity, floor, ceiling),
                "Opposite normals preserve heading");
        vectorEquals(new Vector(0.8, 0, 0.3), originalVelocity, "Input velocity unchanged");
        vectorEquals(new Vector(0, 1, 0), floor, "Input normal unchanged");
        vectorEquals(new Vector(), SquidMotion.rotateTangent(new Vector(), floor, wall), "Resting squid");
        vectorEquals(new Vector(), SquidMotion.rotateTangent(new Vector(0, 2, 0), floor, wall),
                "Normal velocity does not become climbing energy");
        vectorEquals(climb, SquidMotion.rotateTangent(originalVelocity,
                new Vector(0, 4, 0), new Vector(-2, 0, 0)), "Non-unit normals");

        vectorEquals(new Vector(0.4, 0, -0.3),
                SquidMotion.slide(new Vector(0.4, -0.8, -0.3), floor), "Landing keeps lateral speed");
        vectorEquals(new Vector(0.4, 0.8, -0.3),
                SquidMotion.slide(new Vector(0.4, 0.8, -0.3), floor), "Departure is unconstrained");
        vectorEquals(new Vector(-0.5, 1, 0.3),
                SquidMotion.wallJump(new Vector(0.1, 0.8, 0.3), wall, 0.5, 0.2),
                "Wall jump retains upward and sideways momentum");

        Random random = new Random(43127);
        for (int sample = 0; sample < 1000; sample++) {
            Vector sourceNormal = randomVector(random).normalize();
            Vector destinationNormal = randomVector(random).normalize();
            Vector velocity = randomVector(random);
            Vector tangent = velocity.clone().subtract(sourceNormal.clone().multiply(velocity.dot(sourceNormal)));
            Vector rotated = SquidMotion.rotateTangent(velocity, sourceNormal, destinationNormal);
            scalarEquals(tangent.length(), rotated.length(), "Bend conserves tangent speed");
            scalarEquals(0, rotated.dot(destinationNormal), "Bend stays on destination plane");
            vectorEquals(tangent, SquidMotion.rotateTangent(rotated, destinationNormal, sourceNormal),
                    "Reverse bend recovers original tangent");
            Vector slid = SquidMotion.slide(velocity, destinationNormal);
            if (slid.dot(destinationNormal) < -TOLERANCE || slid.length() > velocity.length() + TOLERANCE) {
                throw new AssertionError("Collision must neither penetrate nor add energy");
            }
        }
        try {
            SquidMotion.slide(originalVelocity, new Vector());
            throw new AssertionError("Zero normal must be rejected");
        } catch (IllegalArgumentException expected) {
            // A missing contact must be handled by the caller, not normalized here.
        }
        System.out.println("SquidMotionTest: all checks passed");
    }

    private static Vector randomVector(Random random) {
        return new Vector(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1,
                random.nextDouble() * 2 - 1);
    }

    private static void vectorEquals(Vector expected, Vector actual, String context) {
        double distance = expected.distance(actual);
        if (!Double.isFinite(distance) || distance > TOLERANCE) {
            throw new AssertionError(context + ": expected " + expected + ", got " + actual);
        }
    }

    private static void scalarEquals(double expected, double actual, String context) {
        if (!Double.isFinite(actual) || Math.abs(expected - actual) > TOLERANCE) {
            throw new AssertionError(context + ": expected " + expected + ", got " + actual);
        }
    }
}
