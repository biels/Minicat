package com.biel.lobby.mapes.jocs.obsidiandefenders;

import java.util.Arrays;

public final class TeamUpgradesTest {
    @org.junit.jupiter.api.Test
    void purchaseProgression() {
        var upgrades = new TeamUpgrades();
        int[] wallet = {300}, payments = {0};
        require(upgrades.ready(0, 0) && upgrades.ready(1, 0), "first upgrade available immediately");
        require(buy(upgrades, 0, 10, wallet, payments) == TeamUpgrades.Purchase.BOUGHT, "launchers bought");
        require(wallet[0] == 250 && payments[0] == 1, "50g charged");
        for (int elapsed = 0; elapsed < 1200; elapsed++) {
            require(upgrades.remainingSquares(0, 10 + elapsed) == 4 - elapsed / 300, "one square lost every 15s");
            require(buy(upgrades, 0, 10 + elapsed, wallet, payments) == TeamUpgrades.Purchase.LOADING, "rapid clicks blocked");
        }
        require(wallet[0] == 250 && payments[0] == 1, "loading never charges");
        require(Arrays.equals(UpgradeController.lines(upgrades, 0, 10),
                new String[]{"Llançadors", "activats", "Millora 1/3", "\u00a77\u25a0 \u25a0 \u25a0 \u25a0"}), "current status and gray squares");
        require(upgrades.ready(1, 10), "other team independent");
        wallet[0] = 99;
        require(buy(upgrades, 0, 1210, wallet, payments) == TeamUpgrades.Purchase.FAILED, "100g required");
        require(upgrades.ready(0, 1210) && upgrades.level(0) == 1, "failure does not restart lock");
        wallet[0] = 250;
        require(buy(upgrades, 0, 1210, wallet, payments) == TeamUpgrades.Purchase.BOUGHT, "archers at exact minute");
        require(wallet[0] == 150 && upgrades.has(0, TeamUpgrades.Upgrade.ARCHERS), "archers apply immediately");
        require(!upgrades.has(0, TeamUpgrades.Upgrade.ARMOR), "armor still locked");
        require(buy(upgrades, 0, 2409, wallet, payments) == TeamUpgrades.Purchase.LOADING, "second full minute");
        require(buy(upgrades, 0, 2410, wallet, payments) == TeamUpgrades.Purchase.BOUGHT, "armor bought for 150g");
        require(wallet[0] == 0 && upgrades.level(0) == 3, "all three upgrades paid once");
        require(!upgrades.ready(0, 9999) && upgrades.remainingSquares(0, 2410) == 0, "complete has no cooldown or readiness");
        require(buy(upgrades, 0, 9999, wallet, payments) == TeamUpgrades.Purchase.COMPLETE, "cannot buy level four");
        require(new TeamUpgrades().level(0) == 0, "fresh match resets progression");
        System.out.println("Obsidian upgrade prices, transactions, countdown signs and team independence passed");
    }
    private static TeamUpgrades.Purchase buy(TeamUpgrades upgrades, int team, long tick, int[] wallet, int[] payments) {
        return upgrades.purchase(team, team, tick, () -> {
            int price = upgrades.next(team).price;
            if (wallet[0] < price) return false;
            wallet[0] -= price; payments[0]++;
            return true;
        });
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
