package com.biel.lobby.mapes.jocs.parkour.utils;

import java.util.HashSet;
import java.util.Set;

/** One roster seat's course progress; reconnecting does not reset or duplicate it. */
public final class CourseRun {
    public static final int TELEPORT_GUARD_TICKS = 8;
    private CourseProfile.Position returnPosition;
    private Double maxDrop;
    private String checkpointId;
    private final Set<String> completedCheckpoints = new HashSet<>();
    private long startTick = -1;
    private long finishTick = -1;
    private long guardedUntilTick;
    private int failures;

    public CourseRun(CourseProfile.Position entry) { returnPosition = entry; }
    public CourseProfile.Position returnPosition() { return returnPosition; }
    public Double maxDrop() { return maxDrop; }
    public String checkpointId() { return checkpointId; }
    public Set<String> completedCheckpoints() { return Set.copyOf(completedCheckpoints); }
    public int failures() { return failures; }
    public boolean started() { return startTick >= 0; }
    public boolean finished() { return finishTick >= 0; }
    public boolean guarded(long tick) { return tick < guardedUntilTick; }
    public long elapsedTicks(long tick) { return started() ? Math.max(0, (finished() ? finishTick : tick) - startTick) : 0; }

    public boolean start(long tick, CourseProfile.Position startReturnPosition) {
        if (started() || finished() || guarded(tick)) return false;
        startTick = tick;
        returnPosition = startReturnPosition;
        return true;
    }

    /** Backtracking changes the return point, but each ID awards at most once. */
    public boolean checkpoint(CourseProfile.Checkpoint checkpoint, long tick) {
        if (!started() || finished() || guarded(tick)) return false;
        checkpointId = checkpoint.id();
        returnPosition = checkpoint.safePosition();
        maxDrop = checkpoint.maxDrop();
        return completedCheckpoints.add(checkpoint.id());
    }

    public boolean fail(long tick) {
        if (finished() || guarded(tick)) return false;
        failures++;
        guard(tick);
        return true;
    }

    public boolean finish(long tick) {
        if (!started() || finished() || guarded(tick)) return false;
        finishTick = tick;
        return true;
    }

    public void guard(long tick) { guardedUntilTick = tick + TELEPORT_GUARD_TICKS; }
}
