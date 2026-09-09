package com.biel.lobby.mapes.jocs.obsidiandefenders.utils;

import org.bukkit.util.Vector;
import org.bukkit.ChatColor;

/** Display text and movement calculations for Obsidian interactions. */
public final class Interactions {
    public static final double PORTAL_RADIUS = 2;
    public static final double PICKAXE_RADIUS = 4;
    public static final int PICKAXE_PICKUP_DELAY = 10;

    public static String[] lookoutLines(long redGold, long blueGold, int redKills, int blueKills) {
        return new String[]{
            ChatColor.GOLD + "Punt de Guaita",
            ChatColor.WHITE + "Or total",
            ChatColor.RED + formatGold(redGold) + ChatColor.GRAY + " vs " + ChatColor.BLUE + formatGold(blueGold),
            ChatColor.WHITE + "Kills: " + ChatColor.RED + redKills + ChatColor.GRAY + " vs " + ChatColor.BLUE + blueKills
        };
    }

    public static String formatGold(long nuggets) {
        return java.math.BigDecimal.valueOf(nuggets, 3).setScale(2, java.math.RoundingMode.HALF_UP).toPlainString() + "k";
    }

    public static boolean inPortal(Vector player, Vector entrance) {
        double dx = player.getX() - entrance.getX(), dz = player.getZ() - entrance.getZ();
        return Math.abs(player.getY() - entrance.getY()) < 1 && dx * dx + dz * dz <= PORTAL_RADIUS * PORTAL_RADIUS;
    }

    public static Vector outwardPush(Vector player, Vector center, Vector fallbackDirection) {
        Vector outward = player.clone().subtract(center);
        if (Math.abs(outward.getY()) > 2) return new Vector();
        outward.setY(0);
        double distance = outward.length();
        if (distance > PICKAXE_RADIUS) return new Vector();
        if (distance < 0.01) {
            outward = fallbackDirection.clone().setY(0);
            if (outward.lengthSquared() < 0.01) outward = new Vector(1, 0, 0);
        }
        return outward.normalize().multiply(0.4).setY(0.2);
    }

    public static Vector lootVelocity(Vector origin, Vector target) {
        Vector toward = target.clone().subtract(origin);
        double distance = toward.length();
        return distance < 0.001 ? new Vector() : toward.multiply(Math.min(0.4, distance) / distance);
    }
}
