package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/** The starting roster owns three lives each; disconnects never create new lives. */
public final class TeamLives {
    public static final int STARTING_LIVES = 3;
    private final Map<UUID, Integer> remainingLives = new LinkedHashMap<>();
    private final java.util.Set<UUID> awaitingRespawn = new java.util.HashSet<>();

    public void start(Collection<UUID> participants) {
        remainingLives.clear();
        awaitingRespawn.clear();
        participants.forEach(playerId -> remainingLives.put(playerId, STARTING_LIVES));
    }

    public int remaining(UUID playerId) { return remainingLives.getOrDefault(playerId, 0); }
    public boolean contains(UUID playerId) { return remainingLives.containsKey(playerId); }
    public boolean canPlay(UUID playerId) { return remaining(playerId) > 0; }

    /** Returns false for duplicate death delivery, spectators and eliminated players. */
    public boolean die(UUID playerId) {
        if (!canPlay(playerId) || !awaitingRespawn.add(playerId)) return false;
        remainingLives.computeIfPresent(playerId, (ignored, lives) -> lives - 1);
        return true;
    }

    public void respawn(UUID playerId) { awaitingRespawn.remove(playerId); }

    /** A deliberate leave or expired reconnect grace retires the existing roster slot. */
    public void abandon(UUID playerId) {
        remainingLives.computeIfPresent(playerId, (ignored, lives) -> 0);
        awaitingRespawn.remove(playerId);
    }

    public boolean defeated() {
        return !remainingLives.isEmpty() && remainingLives.values().stream().noneMatch(lives -> lives > 0);
    }
}
