package com.biel.lobby.mapes.jocs.obsidiandefenders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public final class ObsidianWatchtowersTest {
    @org.junit.jupiter.api.Test
    void launchersAndTrajectories() {
        var purchases = new ObsidianTeamUpgrades();
        var upgrades = new ObsidianWatchtowers(purchases);
        int[] gold = {49}, payments = {0};
        var score = new ObsidianGoldScore();
        UUID buyer = new UUID(0, 1), friend = new UUID(0, 2), enemy = new UUID(0, 3);
        score.updateBalance(buyer, 0, 49);
        java.util.function.BooleanSupplier pay = () -> {
            if (gold[0] < 50) return false;
            gold[0] -= 50; payments[0]++;
            score.recordPurchase(buyer, 0, gold[0], 50);
            return true;
        };
        require(purchases.purchase(0, 1, 0, pay) == ObsidianTeamUpgrades.Purchase.ENEMY, "enemy cannot buy");
        require(payments[0] == 0, "enemy attempt has no side effects");
        require(purchases.purchase(0, 0, 0, pay) == ObsidianTeamUpgrades.Purchase.FAILED, "49 gold fails");
        require(gold[0] == 49 && !upgrades.unlocked(0), "failed transaction does not unlock towers");
        require(score.total(0) == 49, "failed payment retains score");
        try {
            purchases.purchase(0, 0, 0, () -> { throw new IllegalStateException("blocked"); });
            throw new AssertionError("exception expected");
        } catch (IllegalStateException expected) {
            require(!upgrades.unlocked(0) && purchases.ready(0, 0), "exception does not advance upgrade or cooldown");
        }
        gold[0] = 50; score.updateBalance(buyer, 0, 50);
        require(purchases.purchase(0, 0, 0, pay) == ObsidianTeamUpgrades.Purchase.BOUGHT, "50 gold buys");
        require(gold[0] == 0 && upgrades.unlocked(0) && !upgrades.unlocked(1), "both own towers only");
        require(score.total(0) == 50, "upgrade spending stays in score");
        require(purchases.purchase(0, 0, 0, pay) == ObsidianTeamUpgrades.Purchase.LOADING, "second/offhand purchase cannot charge again");
        require(payments[0] == 1, "repeat purchase leaves completed transaction alone");
        require(!upgrades.fire(2, 1), "locked tower cannot fire");
        require(upgrades.fire(0, 1) && !upgrades.fire(0, 1), "one launch per reload");
        require(upgrades.fire(1, 1), "other tower reload independent");
        require(upgrades.reloadSeconds(0, 1) == 5 && upgrades.reloadSeconds(0, 100) == 1, "reload ceiling");
        require(!upgrades.fire(0, 100) && upgrades.fire(0, 101), "exact 100 tick reload");
        require(ObsidianWatchtowers.passenger(buyer, List.of()) == null, "empty plates select nobody");
        var waiting = List.of(new ObsidianWatchtowers.Waiting(friend, 1), new ObsidianWatchtowers.Waiting(buyer, 10));
        require(ObsidianWatchtowers.passenger(buyer, waiting).equals(buyer), "self-launch takes priority");
        require(ObsidianWatchtowers.passenger(enemy, waiting).equals(friend), "lower button launches longest waiting participant");
        require(ObsidianWatchtowers.passenger(enemy, List.of(new ObsidianWatchtowers.Waiting(friend, 1),
                new ObsidianWatchtowers.Waiting(buyer, 1))).equals(buyer), "deterministic tie break");

        var coordinates = new HashSet<ObsidianWatchtowers.Position>();
        for (var tower : ObsidianWatchtowers.TOWERS) {
            var center = new Vector(tower.rearX() + 0.5, 52.0625, tower.middleZ() + 0.5);
            require(tower.plates().size() == 3 && tower.buttons().size() == 3, "three plates and three buttons");
            for (var position : tower.plates()) require(coordinates.add(position), "unique plate");
            for (var position : tower.buttons()) require(coordinates.add(position), "unique button");
            require(tower.onPlates(center), "center occupant");
            require(tower.onPlates(center.clone().add(new Vector(0, 0, 1))), "side plate occupant");
            require(!tower.onPlates(center.clone().add(new Vector(tower.direction(), 0, 0))), "ladder not a passenger");
            require(!tower.onPlates(center.clone().add(new Vector(0, 1, 0))), "jumping above plate not armed");
            require(!tower.onPlates(center.clone().setY(42)), "lower button operator not a passenger");
            require(!tower.onPlates(center.clone().setX(Double.NaN)), "nonfinite rejected");
        }
        require(coordinates.size() == 24, "exactly 24 hardware blocks");
        require(ObsidianWatchtowers.purchaseButton(0).equals(new ObsidianWatchtowers.Position(611, 42, -1369)), "red purchase position");
        require(ObsidianWatchtowers.purchaseButton(1).equals(new ObsidianWatchtowers.Position(715, 42, -1431)), "blue purchase position");
        var protection = new ObsidianWatchtowers.FallProtection(10);
        require(!protection.expired(50), "airborne protection");
        protection.land(50); protection.land(51);
        require(!protection.expired(51) && protection.expired(52), "fall event ordering grace then protection consumed");
        require(new ObsidianWatchtowers.FallProtection(0).expired(201), "abnormal flight expiry");

        var tower = ObsidianWatchtowers.TOWERS.getFirst();
        List<BoundingBox> boxes = new ArrayList<>(List.of(new BoundingBox(650, 40, -1413, 657, 51, -1404)));
        var terrain = new ObsidianLauncherTrajectory.Terrain() {
            public List<BoundingBox> obstacles(Vector feet, Vector movement) { return boxes; }
            public boolean leaves(Vector feet) { return true; }
        };
        Vector origin = new Vector(617.5, 52.5625, -1408.5);
        Vector impulse = ObsidianLauncherTrajectory.find(tower, origin, terrain);
        require(impulse != null && impulse.getX() > 0 && impulse.getY() > 0, "collision-checked jungle impulse");
        require(impulse.getZ() == 0 && impulse.lengthSquared() < 20, "center aim and bounded speed");
        boxes.add(new BoundingBox(625, 40, -1420, 627, 90, -1380));
        require(ObsidianLauncherTrajectory.find(tower, origin, terrain) == null, "blocked corridor refuses unsafe impulse");
        System.out.println("Obsidian watchtower purchase, layouts, selection, reload, flight guard and trajectory checks passed");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
