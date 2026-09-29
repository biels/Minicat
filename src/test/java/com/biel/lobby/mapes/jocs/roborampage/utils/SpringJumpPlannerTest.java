package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.biel.lobby.mapes.jocs.roborampage.utils.SpringJumpPlanner.Point;

class SpringJumpPlannerTest {
    @Test void ballisticImpulseLandsAtFourBlockPlatformOnDescent() {
        Point start = new Point(0.5, 1, 0.5), landing = new Point(2, 5, 0.5);
        var jump = SpringJumpPlanner.plan(start, landing, point -> true).orElseThrow();
        assertEquals(0.82, jump.velocityY());
        assertTrue(Math.hypot(jump.velocityX(), jump.velocityZ()) <= 0.42);
        Point end = SpringJumpPlanner.position(start, jump, jump.flightTicks());
        assertEquals(landing.x(), end.x(), 1e-9);
        assertEquals(landing.y(), end.y(), 1e-9);
        assertEquals(landing.z(), end.z(), 1e-9);
        assertTrue(SpringJumpPlanner.position(start, jump, jump.flightTicks() - 0.1).y() > end.y());
        assertTrue(jump.flightTicks() < 20);
    }

    @Test void groundFrictionAndAirDragAreIncludedRatherThanConstantVelocity() {
        Point start = new Point(0, 1, 0), landing = new Point(2, 3, 0);
        var jump = SpringJumpPlanner.plan(start, landing, point -> true).orElseThrow();
        assertEquals(jump.velocityX(), SpringJumpPlanner.position(start, jump, 1).x(), 1e-9);
        assertEquals(jump.velocityX() * (1 + 0.546), SpringJumpPlanner.position(start, jump, 2).x(), 1e-9);
        assertTrue(jump.velocityX() * jump.flightTicks() > landing.x());
    }

    @Test void rejectsTooHighTooFarTooFastAndBlockedSweptBody() {
        Point start = new Point(0, 1, 0);
        assertTrue(SpringJumpPlanner.plan(start, new Point(1, 5.01, 0), point -> true).isEmpty());
        assertTrue(SpringJumpPlanner.plan(start, new Point(4, 3, 0), point -> true).isEmpty());
        assertTrue(SpringJumpPlanner.plan(start, new Point(3.5, 5, 0), point -> true).isEmpty());
        assertTrue(SpringJumpPlanner.plan(start, new Point(1, 3, 0), point -> point.y() < 4).isEmpty());
        assertTrue(SpringJumpPlanner.plan(start, new Point(1, 3, 0), point -> point.x() < 0.9).isEmpty());
    }

    @Test void intermediateStepsMakeAnEightBlockClimbPossibleWithoutOverpoweredSingleLeap() {
        Point base = new Point(0.5, 1, 0.5), first = new Point(2, 5, 0.5), second = new Point(3.5, 9, 0.5);
        assertTrue(SpringJumpPlanner.plan(base, second, point -> true).isEmpty());
        assertTrue(SpringJumpPlanner.plan(base, first, point -> true).isPresent());
        assertTrue(SpringJumpPlanner.plan(first, second, point -> true).isPresent());
    }
}
