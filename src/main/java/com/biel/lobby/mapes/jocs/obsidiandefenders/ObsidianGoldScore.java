package com.biel.lobby.mapes.jocs.obsidiandefenders;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Score is owned currency plus completed purchases, so losses and transfers cannot inflate it. */
final class ObsidianGoldScore {
    private record Wallet(int team, long nuggets) {}
    private final Map<UUID, Wallet> wallets = new HashMap<>();
    private final long[] spent = new long[2];

    void clear() {
        wallets.clear();
        java.util.Arrays.fill(spent, 0);
    }

    void updateBalance(UUID player, int team, long nuggets) {
        Objects.checkIndex(team, spent.length);
        if (nuggets < 0) throw new IllegalArgumentException("Gold balance cannot be negative");
        wallets.put(player, new Wallet(team, nuggets));
    }

    void recordPurchase(UUID player, int team, long remainingNuggets, long paidNuggets) {
        Objects.checkIndex(team, spent.length);
        if (paidNuggets <= 0 || remainingNuggets < 0) throw new IllegalArgumentException("Invalid gold purchase");
        long totalSpent = Math.addExact(spent[team], paidNuggets);
        updateBalance(player, team, remainingNuggets);
        spent[team] = totalSpent;
    }

    void removePlayer(UUID player) {
        wallets.remove(player);
    }

    long total(int team) {
        long total = spent[team];
        for (Wallet wallet : wallets.values()) if (wallet.team() == team) total = Math.addExact(total, wallet.nuggets());
        return total;
    }
}
