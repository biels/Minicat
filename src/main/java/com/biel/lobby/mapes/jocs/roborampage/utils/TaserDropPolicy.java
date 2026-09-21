package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.random.RandomGenerator;

import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.RobotType;

/** Pity-bounded team drop cadence: surprising, but never an unbounded unlucky streak. */
public final class TaserDropPolicy {
    private int accumulatedPoints;
    private int nextDropThreshold;

    public TaserDropPolicy(RandomGenerator random) {
        nextDropThreshold = randomThreshold(random);
    }

    public boolean recordRobotDeath(
            RobotType robotType, int createdTaserCount, int participantCount, RandomGenerator random) {
        accumulatedPoints = Math.min(
                TaserRules.MAXIMUM_DROP_POINTS,
                accumulatedPoints + TaserRules.robotDropPoints(robotType));
        if (createdTaserCount >= Math.max(1, participantCount) || accumulatedPoints < nextDropThreshold) {
            return false;
        }
        accumulatedPoints = 0;
        nextDropThreshold = randomThreshold(random);
        return true;
    }

    public int accumulatedPoints() { return accumulatedPoints; }

    public int nextDropThreshold() { return nextDropThreshold; }

    private static int randomThreshold(RandomGenerator random) {
        return random.nextInt(TaserRules.MINIMUM_DROP_POINTS, TaserRules.MAXIMUM_DROP_POINTS + 1);
    }
}
