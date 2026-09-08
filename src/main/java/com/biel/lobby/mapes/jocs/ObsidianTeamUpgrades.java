package com.biel.lobby.mapes.jocs;

import java.util.function.BooleanSupplier;

/** Purchase state belongs to a team for one match; all times are server ticks. */
final class ObsidianTeamUpgrades {
    static final int PURCHASE_DELAY_TICKS = 1200;
    enum Upgrade {
        LAUNCHERS(50), ARCHERS(100), ARMOR(150);
        final int price;
        Upgrade(int price) { this.price = price; }
    }
    enum Purchase { BOUGHT, ENEMY, LOADING, COMPLETE, FAILED }
    private final int[] levels = new int[2];
    private final long[] nextPurchaseTick = new long[2];

    int level(int team) { return levels[team]; }
    boolean has(int team, Upgrade upgrade) { return levels[team] > upgrade.ordinal(); }
    Upgrade next(int team) { return levels[team] == Upgrade.values().length ? null : Upgrade.values()[levels[team]]; }
    int remainingSquares(int team, long tick) {
        return next(team) == null ? 0 : (int) Math.clamp((nextPurchaseTick[team] - tick + 299) / 300, 0, 4);
    }
    boolean ready(int team, long tick) { return next(team) != null && tick >= nextPurchaseTick[team]; }

    Purchase purchase(int team, int buyerTeam, long tick, BooleanSupplier completePurchase) {
        if (team != buyerTeam) return Purchase.ENEMY;
        if (next(team) == null) return Purchase.COMPLETE;
        if (!ready(team, tick)) return Purchase.LOADING;
        if (!completePurchase.getAsBoolean()) return Purchase.FAILED;
        levels[team]++;
        nextPurchaseTick[team] = tick + PURCHASE_DELAY_TICKS;
        return Purchase.BOUGHT;
    }
}
