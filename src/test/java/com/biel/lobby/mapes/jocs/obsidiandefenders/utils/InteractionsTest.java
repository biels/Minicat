package com.biel.lobby.mapes.jocs.obsidiandefenders.utils;

import org.bukkit.util.Vector;

public final class InteractionsTest {
    @org.junit.jupiter.api.Test
    void interactionRules() {
        portalBoundaries();
        pickaxePush();
        lootFlight();
        lookoutDisplay();
        System.out.println("Obsidian interaction checks passed");
    }

    private static void portalBoundaries() {
        Vector entrance = new Vector(10.5, 20, -30.5);
        require(Interactions.inPortal(entrance, entrance), "center activates");
        require(Interactions.inPortal(entrance.clone().add(new Vector(2, 0, 0)), entrance), "two-block edge activates");
        require(!Interactions.inPortal(entrance.clone().add(new Vector(2.01, 0, 0)), entrance), "outside edge does not activate");
        require(!Interactions.inPortal(entrance.clone().add(new Vector(1.5, 0, 1.5)), entrance), "radius is circular, not square");
        require(!Interactions.inPortal(entrance.clone().add(new Vector(0, 1, 0)), entrance), "floor above excluded");
        require(!Interactions.inPortal(entrance.clone().add(new Vector(0, -1, 0)), entrance), "floor below excluded");
    }

    private static void pickaxePush() {
        Vector center = new Vector(664.5, 42, -1401.5);
        for (Vector offset : new Vector[]{new Vector(1, -1, 0), new Vector(-1, -1, 0), new Vector(0, 0, 4)}) {
            Vector player = center.clone().add(offset);
            Vector push = Interactions.outwardPush(player, center, new Vector());
            require(push.dot(offset.clone().setY(0)) > 0, "push must be radial and outward");
            close(0.4, push.clone().setY(0).length(), "horizontal force is bounded");
            close(0.2, push.getY(), "vertical force is bounded");
            require(player.equals(center.clone().add(offset)), "input vectors are not mutated");
        }
        require(Interactions.outwardPush(center.clone().add(new Vector(4.01, 0, 0)), center, new Vector()).isZero(), "outside radius excluded");
        require(Interactions.outwardPush(center.clone().add(new Vector(0, 2.01, 0)), center, new Vector()).isZero(), "other level excluded");
        Vector centered = Interactions.outwardPush(center, center, new Vector(0, 1, 0));
        centered.checkFinite();
        require(centered.lengthSquared() > 0, "exact-center player still receives a finite push");
        require(Interactions.PICKAXE_PICKUP_DELAY == 10, "half-second pickup window");
    }

    private static void lootFlight() {
        Vector origin = new Vector(1, 2, 3);
        require(Interactions.lootVelocity(origin, origin).isZero(), "arrived loot does not normalize zero");
        Vector nearby = new Vector(1.1, 2, 3);
        close(0.1, Interactions.lootVelocity(origin, nearby).length(), "nearby loot does not overshoot");
        Vector far = new Vector(5, 4, -2);
        Vector velocity = Interactions.lootVelocity(origin, far);
        close(0.4, velocity.length(), "flight speed is bounded");
        require(velocity.dot(far.clone().subtract(origin)) > 0, "loot flies toward collector");
        require(origin.equals(new Vector(1, 2, 3)) && far.equals(new Vector(5, 4, -2)), "flight preserves input vectors");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void lookoutDisplay() {
        String[] lines = Interactions.lookoutLines(123, 45, 6, 7);
        require(lines.length == 4, "display fits the four sign lines");
        require(org.bukkit.ChatColor.stripColor(lines[0]).equals("Punt de Guaita"), "lookout title");
        require(org.bukkit.ChatColor.stripColor(lines[1]).equals("Or total"), "gold label has its own sign line");
        require(org.bukkit.ChatColor.stripColor(lines[2]).equals("0.12k vs 0.05k"), "red gold appears before blue gold");
        require(org.bukkit.ChatColor.stripColor(lines[3]).equals("Kills: 6 vs 7"), "kill order matches gold order");
        require(lines[2].contains(org.bukkit.ChatColor.RED + "0.12k") && lines[2].contains(org.bukkit.ChatColor.BLUE + "0.05k"), "team colors label the values");
        require(org.bukkit.ChatColor.stripColor(Interactions.lookoutLines(0, 0, 0, 0)[2]).equals("0.00k vs 0.00k"), "empty teams show zero");
        require(Interactions.formatGold(3000000000L).equals("3000000.00k"), "large team totals do not overflow or truncate");
        require(Interactions.formatGold(1250).equals("1.25k"), "thousand nugget units");
        require(Interactions.formatGold(4).equals("0.00k") && Interactions.formatGold(5).equals("0.01k"), "half-up rounding to ten nuggets");
    }

    private static void close(double expected, double actual, String message) {
        require(Math.abs(expected - actual) < 1e-6, message + ": " + actual);
    }
}
