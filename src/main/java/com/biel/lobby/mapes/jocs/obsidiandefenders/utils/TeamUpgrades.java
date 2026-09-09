package com.biel.lobby.mapes.jocs.obsidiandefenders.utils;

import java.util.function.BooleanSupplier;

/** Purchase state belongs to a team for one match; all times are server ticks. */
public final class TeamUpgrades {
    public static final int PURCHASE_DELAY_TICKS = 1200;
    public enum Upgrade {
        LAUNCHERS(50), ARCHERS(100), ARMOR(150);
        public final int price;
        Upgrade(int price) { this.price = price; }
    }
    public enum Purchase { BOUGHT, ENEMY, LOADING, COMPLETE, FAILED }
    private final int[] levels = new int[2];
    private final long[] nextPurchaseTick = new long[2];

    public int level(int team) { return levels[team]; }
    public boolean has(int team, Upgrade upgrade) { return levels[team] > upgrade.ordinal(); }
    public Upgrade next(int team) { return levels[team] == Upgrade.values().length ? null : Upgrade.values()[levels[team]]; }
    public int remainingSquares(int team, long tick) {
        return next(team) == null ? 0 : (int) Math.clamp((nextPurchaseTick[team] - tick + 299) / 300, 0, 4);
    }
    public boolean ready(int team, long tick) { return next(team) != null && tick >= nextPurchaseTick[team]; }

    public Purchase purchase(int team, int buyerTeam, long tick, BooleanSupplier completePurchase) {
        if (team != buyerTeam) return Purchase.ENEMY;
        if (next(team) == null) return Purchase.COMPLETE;
        if (!ready(team, tick)) return Purchase.LOADING;
        if (!completePurchase.getAsBoolean()) return Purchase.FAILED;
        levels[team]++;
        nextPurchaseTick[team] = tick + PURCHASE_DELAY_TICKS;
        return Purchase.BOUGHT;
    }
}
