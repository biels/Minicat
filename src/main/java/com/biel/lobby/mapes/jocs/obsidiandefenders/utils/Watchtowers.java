package com.biel.lobby.mapes.jocs.obsidiandefenders.utils;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.bukkit.util.Vector;

/** Match-local launcher state and calculations over a configured watchtower layout. */
public final class Watchtowers {
    public static final int RELOAD_TICKS = 100;
    public static final int FLIGHT_TIMEOUT_TICKS = 200;

    public record Position(int x, int y, int z) {
        public Vector vector() { return new Vector(x, y, z); }
    }

    public record Tower(int id, int team, int rearX, int plateY, int middleZ, int direction, int lowerButtonY) {
        public List<Position> plates() {
            return List.of(new Position(rearX, plateY, middleZ - 1), new Position(rearX, plateY, middleZ),
                    new Position(rearX, plateY, middleZ + 1));
        }
        public List<Position> buttons() {
            return List.of(new Position(rearX + 2 * direction, plateY, middleZ),
                    new Position(rearX - 2 * direction, lowerButtonY, middleZ - 2),
                    new Position(rearX - 2 * direction, lowerButtonY, middleZ + 2));
        }
        public boolean onPlates(Vector feet) {
            return Double.isFinite(feet.getX()) && Double.isFinite(feet.getY()) && Double.isFinite(feet.getZ())
                    && feet.getX() >= rearX + 0.125 && feet.getX() <= rearX + 0.875
                    && feet.getZ() >= middleZ - 0.875 && feet.getZ() <= middleZ + 1.875
                    && feet.getY() >= plateY && feet.getY() <= plateY + 0.15;
        }
    }

    private final TeamUpgrades upgrades;
    private final List<Tower> towers;
    private final long[] reloadUntil;
    public Watchtowers(TeamUpgrades upgrades, List<Tower> towers) {
        this.upgrades = upgrades;
        this.towers = List.copyOf(towers);
        for (int id = 0; id < towers.size(); id++) {
            if (towers.get(id).id() != id) throw new IllegalArgumentException("Tower ids must be contiguous from zero");
        }
        reloadUntil = new long[towers.size()];
    }
    public List<Tower> towers() { return towers; }
    public boolean unlocked(int team) { return upgrades.has(team, TeamUpgrades.Upgrade.LAUNCHERS); }

    public int reloadSeconds(int tower, long tick) {
        return (int) Math.max(0, (reloadUntil[tower] - tick + 19) / 20);
    }

    public boolean fire(int tower, long tick) {
        if (!unlocked(towers.get(tower).team()) || tick < reloadUntil[tower]) return false;
        reloadUntil[tower] = tick + RELOAD_TICKS;
        return true;
    }

    public record Waiting(UUID player, long since) {}

    public static final class FallProtection {
        public final long started;
        private long landed = -1;
        public FallProtection(long started) { this.started = started; }
        public void land(long tick) { if (landed < 0) landed = tick; }
        public boolean expired(long tick) {
            return tick - started > FLIGHT_TIMEOUT_TICKS || (landed >= 0 && tick > landed + 1);
        }
    }

    public static UUID passenger(UUID activator, List<Waiting> waiting) {
        if (waiting.stream().anyMatch(candidate -> candidate.player().equals(activator))) return activator;
        return waiting.stream().min(Comparator.comparingLong(Waiting::since).thenComparing(Waiting::player))
                .map(Waiting::player).orElse(null);
    }
}
