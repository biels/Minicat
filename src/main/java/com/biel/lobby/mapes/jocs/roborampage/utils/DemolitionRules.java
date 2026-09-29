package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Server-independent blast tuning and one-landing fall protection. */
public final class DemolitionRules {
    public static final float BLAST_POWER = 4.0F;
    public static final double BLAST_RADIUS = BLAST_POWER * 2;
    public static final double ROBOT_DAMAGE_MULTIPLIER = 1.5;
    public static final long FALL_PROTECTION_TICKS = 160;
    public static final double MAX_VERTICAL_SPEED = 0.9;

    private final Map<UUID, Long> fallProtectionDeadlines = new HashMap<>();

    /** Zero means out of reach or sheltered; repeated blasts never stack vertical speed. */
    public static double launchSpeed(double distance, double exposure) {
        if (!Double.isFinite(distance) || !Double.isFinite(exposure)
                || distance < 0 || distance >= BLAST_RADIUS || exposure <= 0) return 0;
        double impact = (1 - distance / BLAST_RADIUS) * Math.min(1, exposure);
        return 0.55 + 0.35 * impact;
    }

    public void protectLanding(UUID playerId, long currentTick) {
        fallProtectionDeadlines.put(playerId, currentTick + FALL_PROTECTION_TICKS);
    }

    public boolean consumeFallProtection(UUID playerId, long currentTick) {
        Long deadline = fallProtectionDeadlines.remove(playerId);
        return deadline != null && currentTick < deadline;
    }

    public void forgetPlayer(UUID playerId) { fallProtectionDeadlines.remove(playerId); }

    public void prune(long currentTick) {
        fallProtectionDeadlines.values().removeIf(deadline -> currentTick >= deadline);
    }

    public void clear() { fallProtectionDeadlines.clear(); }
}
