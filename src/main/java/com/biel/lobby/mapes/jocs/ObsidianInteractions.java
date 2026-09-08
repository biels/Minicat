package com.biel.lobby.mapes.jocs;

import org.bukkit.util.Vector;
import org.bukkit.ChatColor;

/** Shared positions, display text and movement calculations for Obsidian interactions. */
final class ObsidianInteractions {
    static final double PORTAL_RADIUS = 2;
    static final double PICKAXE_RADIUS = 4;
    static final int PICKAXE_PICKUP_DELAY = 10;

    static Vector lookoutFloor() {
        return new Vector(662, 73, -1392);
    }

    static String[] lookoutLines(long redGold, long blueGold, int redKills, int blueKills) {
        return new String[]{
            ChatColor.GOLD + "Punt de Guaita",
            ChatColor.WHITE + "Or total",
            ChatColor.RED + formatGold(redGold) + ChatColor.GRAY + " vs " + ChatColor.BLUE + formatGold(blueGold),
            ChatColor.WHITE + "Kills: " + ChatColor.RED + redKills + ChatColor.GRAY + " vs " + ChatColor.BLUE + blueKills
        };
    }

    static String formatGold(long nuggets) {
        return java.math.BigDecimal.valueOf(nuggets, 3).setScale(2, java.math.RoundingMode.HALF_UP).toPlainString() + "k";
    }

    record PortalPosition(Vector entrance, Vector arrival, float arrivalYaw) {}

    static PortalPosition portal(int team) {
        return switch (team) {
            case 0 -> new PortalPosition(new Vector(616.5, 41, -1422.5), new Vector(613.5, 41, -1371.5), 180);
            case 1 -> new PortalPosition(new Vector(712.5, 41, -1379.5), new Vector(713.5, 41, -1428.5), 0);
            default -> throw new IllegalArgumentException("Unknown team: " + team);
        };
    }

    static boolean inPortal(Vector player, Vector entrance) {
        double dx = player.getX() - entrance.getX(), dz = player.getZ() - entrance.getZ();
        return Math.abs(player.getY() - entrance.getY()) < 1 && dx * dx + dz * dz <= PORTAL_RADIUS * PORTAL_RADIUS;
    }

    static Vector outwardPush(Vector player, Vector center, Vector fallbackDirection) {
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

    static Vector lootVelocity(Vector origin, Vector target) {
        Vector toward = target.clone().subtract(origin);
        double distance = toward.length();
        return distance < 0.001 ? new Vector() : toward.multiply(Math.min(0.4, distance) / distance);
    }
}
