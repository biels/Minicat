package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Match-owned upgrade claims survive item removal and duplicate pickup delivery. */
public final class SupplyUpgradeClaims {
    private record Core(UUID owner, int wave) {}
    private final Map<UUID, Core> unclaimed = new HashMap<>();
    private final Map<UUID, Integer> lastClaimedWave = new HashMap<>();

    public void register(UUID item, UUID owner, int wave) {
        unclaimed.put(item, new Core(owner, wave));
    }

    public boolean claim(UUID item, UUID player) {
        Core core = unclaimed.get(item);
        if (core == null || !core.owner().equals(player)
                || core.wave() <= lastClaimedWave.getOrDefault(player, 0)) return false;
        unclaimed.remove(item);
        lastClaimedWave.put(player, core.wave());
        return true;
    }

    public void discardUnclaimed() { unclaimed.clear(); }
    public void close() { unclaimed.clear(); lastClaimedWave.clear(); }
}
