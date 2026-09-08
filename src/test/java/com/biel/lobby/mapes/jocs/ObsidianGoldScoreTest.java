package com.biel.lobby.mapes.jocs;

import java.util.UUID;

public final class ObsidianGoldScoreTest {
    public static void main(String[] args) {
        ObsidianGoldScore score = new ObsidianGoldScore();
        UUID red = UUID.randomUUID(), teammate = UUID.randomUUID(), blue = UUID.randomUUID();
        score.updateBalance(red, 0, 100);
        score.updateBalance(teammate, 0, 20);
        score.updateBalance(blue, 1, 50);
        totals(score, 120, 50, "team wallets aggregate");

        score.recordPurchase(red, 0, 70, 30);
        totals(score, 120, 50, "spending keeps earned value in the score");
        score.updateBalance(red, 0, 69);
        totals(score, 119, 50, "Q drop lowers score by its value");
        score.updateBalance(red, 0, 70);
        totals(score, 120, 50, "own pickup restores value exactly once");
        score.updateBalance(red, 0, 70);
        totals(score, 120, 50, "repeated samples cannot increase score");

        score.updateBalance(red, 0, 6);
        totals(score, 56, 50, "full-stack drop removes all 64 nuggets");
        score.updateBalance(blue, 1, 70);
        totals(score, 56, 70, "enemy partial pickup adds only the amount collected");
        score.updateBalance(teammate, 0, 64);
        totals(score, 100, 70, "remaining loot can go to another player");
        score.updateBalance(teammate, 0, 0);
        totals(score, 36, 70, "chest deposit, destruction or other loss removes owned gold");
        score.updateBalance(red, 0, 70);
        totals(score, 100, 70, "withdrawing or recovering the same gold restores it");

        score.recordPurchase(red, 0, 50, 20);
        totals(score, 100, 70, "a second purchase adds only its actual price");
        score.updateBalance(red, 0, 50);
        totals(score, 100, 70, "death with kept inventory does not change score");
        // No update during the reconnect grace: the last owned balance remains.
        totals(score, 100, 70, "temporary disconnect retains the last balance");
        score.updateBalance(red, 0, 50);
        totals(score, 100, 70, "reconnect does not count income twice");
        score.removePlayer(red);
        totals(score, 50, 70, "abandonment removes carried gold but retains past spending");
        score.removePlayer(red);
        totals(score, 50, 70, "cleanup is idempotent");

        score.updateBalance(teammate, 0, 3000000000L);
        totals(score, 3000000050L, 70, "team totals use long arithmetic");
        score.updateBalance(teammate, 1, 3000000000L);
        totals(score, 50, 3000000070L, "team change moves wallet, not historical purchases");
        score.clear();
        totals(score, 0, 0, "new match resets wallets and spending");
        boolean rejected = false;
        try { score.recordPurchase(red, 0, 0, -1); } catch (IllegalArgumentException expected) { rejected = true; }
        require(rejected, "invalid purchase rejected");
        totals(score, 0, 0, "rejected purchase leaves score unchanged");
        System.out.println("Obsidian owned-plus-spent gold checks passed");
    }

    private static void totals(ObsidianGoldScore score, long red, long blue, String message) {
        require(score.total(0) == red && score.total(1) == blue,
                message + ": actual " + score.total(0) + " vs " + score.total(1));
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
