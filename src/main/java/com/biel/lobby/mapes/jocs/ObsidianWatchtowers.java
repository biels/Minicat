package com.biel.lobby.mapes.jocs;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.bukkit.util.Vector;

/** Match-local upgrades and the four surveyed watchtower layouts. */
final class ObsidianWatchtowers {
    static final int RELOAD_TICKS = 100;
    static final int FLIGHT_TIMEOUT_TICKS = 200;

    record Position(int x, int y, int z) {
        Vector vector() { return new Vector(x, y, z); }
    }

    record Tower(int id, int team, int rearX, int middleZ, int direction) {
        List<Position> plates() {
            return List.of(new Position(rearX, 52, middleZ - 1), new Position(rearX, 52, middleZ),
                    new Position(rearX, 52, middleZ + 1));
        }
        List<Position> buttons() {
            return List.of(new Position(rearX + 2 * direction, 52, middleZ),
                    new Position(rearX - 2 * direction, 42, middleZ - 2),
                    new Position(rearX - 2 * direction, 42, middleZ + 2));
        }
        boolean onPlates(Vector feet) {
            return Double.isFinite(feet.getX()) && Double.isFinite(feet.getY()) && Double.isFinite(feet.getZ())
                    && feet.getX() >= rearX + 0.125 && feet.getX() <= rearX + 0.875
                    && feet.getZ() >= middleZ - 0.875 && feet.getZ() <= middleZ + 1.875
                    && feet.getY() >= 52 && feet.getY() <= 52.15;
        }
    }

    static final List<Tower> TOWERS = List.of(new Tower(0, 0, 617, -1409, 1),
            new Tower(1, 0, 617, -1391, 1), new Tower(2, 1, 709, -1409, -1),
            new Tower(3, 1, 709, -1391, -1));

    static Position purchaseButton(int team) {
        return switch (team) {
            case 0 -> new Position(611, 42, -1369);
            case 1 -> new Position(715, 42, -1431);
            default -> throw new IllegalArgumentException("Unknown team " + team);
        };
    }

    private final ObsidianTeamUpgrades upgrades;
    private final long[] reloadUntil = new long[4];
    ObsidianWatchtowers(ObsidianTeamUpgrades upgrades) { this.upgrades = upgrades; }
    boolean unlocked(int team) { return upgrades.has(team, ObsidianTeamUpgrades.Upgrade.LAUNCHERS); }

    int reloadSeconds(int tower, long tick) {
        return (int) Math.max(0, (reloadUntil[tower] - tick + 19) / 20);
    }

    boolean fire(int tower, long tick) {
        if (!unlocked(TOWERS.get(tower).team()) || tick < reloadUntil[tower]) return false;
        reloadUntil[tower] = tick + RELOAD_TICKS;
        return true;
    }

    record Waiting(UUID player, long since) {}

    static final class FallProtection {
        final long started;
        private long landed = -1;
        FallProtection(long started) { this.started = started; }
        void land(long tick) { if (landed < 0) landed = tick; }
        boolean expired(long tick) {
            return tick - started > FLIGHT_TIMEOUT_TICKS || (landed >= 0 && tick > landed + 1);
        }
    }

    static UUID passenger(UUID activator, List<Waiting> waiting) {
        if (waiting.stream().anyMatch(candidate -> candidate.player().equals(activator))) return activator;
        return waiting.stream().min(Comparator.comparingLong(Waiting::since).thenComparing(Waiting::player))
                .map(Waiting::player).orElse(null);
    }
}
