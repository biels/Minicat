package com.biel.lobby.mapes.jocs.inkwars;

import java.util.List;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/** Standalone regression checks; run with the Paper API and its dependencies on the classpath. */
public final class SquidCollisionTest {
    private static final Vector HALF_EXTENTS = new Vector(0.25, 0.15, 0.25);

    public static void main(String[] args) {
        BoundingBox slab = new BoundingBox(0, 0, 0, 1, 0.5, 1);
        SquidCollision.Hit slabLanding = hit(new Vector(0.5, 2, 0.5), new Vector(0, -3, 0), slab);
        close(0.65, slabLanding.position.getY(), "slab surface plus body height");
        equal(new Vector(0, 1, 0), slabLanding.normal, "slab normal");

        BoundingBox insetWall = new BoundingBox(1.375, 0, 0, 1.625, 3, 1);
        SquidCollision.Hit wallHit = hit(new Vector(0, 1, 0.5), new Vector(2, 0, 0), insetWall);
        close(1.125, wallHit.position.getX(), "inset wall collision position");
        equal(new Vector(-1, 0, 0), wallHit.normal, "wall normal");

        BoundingBox ledge = new BoundingBox(1, 0, 0, 2, 1, 1);
        hit(new Vector(0, 1.1, 0.5), new Vector(3, 0, 0), ledge);
        BoundingBox thin = new BoundingBox(10, 0, 0, 10.01, 2, 1);
        close(0.0975, hit(new Vector(0, 1, 0.5), new Vector(100, 0, 0), thin).fraction, "fast thin wall");

        noHit(new Vector(0.5, 0.65, 0.5), new Vector(3, 0, 0), slab, "tangential floor travel");
        noHit(new Vector(0.5, 0.65, 0.5), new Vector(0, 1, 0), slab, "leaving floor");
        noHit(new Vector(0.5, 0.65, 0.5), new Vector(), slab, "stationary touching floor");
        close(0, hit(new Vector(0.5, 0.65, 0.5), new Vector(0, -1, 0), slab).fraction, "entering touched floor");

        BoundingBox far = new BoundingBox(3, 0, 0, 4, 4, 1);
        BoundingBox near = new BoundingBox(0, 2, 0, 4, 3, 1);
        SquidCollision.Hit diagonal = SquidCollision.sweep(new Vector(0, 0, 0.5), HALF_EXTENTS,
                new Vector(4, 4, 0), List.of(far, near));
        if (diagonal == null || diagonal.obstacleIndex != 1) throw new AssertionError("nearest diagonal collision");
        close(1.85 / 4, diagonal.fraction, "diagonal collision time");

        Vector embedded = new Vector(0.5, 0.55, 0.5);
        SquidCollision.Hit recovery = hit(embedded, new Vector(), slab);
        if (!recovery.startedInside || SquidCollision.overlaps(recovery.position, HALF_EXTENTS, slab)) {
            throw new AssertionError("initial overlap must resolve to an actual face");
        }
        close(0.65, recovery.position.getY(), "nearest escape surface");
        close(0.55, embedded.getY(), "input vector remains unchanged");
        if (SquidCollision.overlaps(new Vector(0.5, 0.65, 0.5), HALF_EXTENTS, slab)) {
            throw new AssertionError("touching is not overlap");
        }
        System.out.println("SquidCollision checks passed");
    }

    private static SquidCollision.Hit hit(Vector center, Vector movement, BoundingBox obstacle) {
        SquidCollision.Hit result = SquidCollision.sweep(center, HALF_EXTENTS, movement, List.of(obstacle));
        if (result == null) throw new AssertionError("expected collision at " + center + " moving " + movement);
        return result;
    }

    private static void noHit(Vector center, Vector movement, BoundingBox obstacle, String message) {
        if (SquidCollision.sweep(center, HALF_EXTENTS, movement, List.of(obstacle)) != null) throw new AssertionError(message);
    }

    private static void equal(Vector expected, Vector actual, String message) {
        if (expected.distanceSquared(actual) > 1e-16) throw new AssertionError(message + ": " + actual);
    }

    private static void close(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 1e-8) throw new AssertionError(message + ": " + actual);
    }
}
