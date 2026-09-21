package com.biel.lobby.mapes.jocs.roborampage.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

/** Pure rules recovered from the 2015 Robo Rampage prototype, plus bounded live limits. */
public final class RoboRampageRules {
    public static final double PLAYER_DAMAGE_MULTIPLIER = 0.25;
    public static final double ROBOT_DAMAGE_MULTIPLIER = 2.65;
    public static final int POWER_UP_TICKS = 20 * 20;
    public static final int POWER_UP_AMPLIFIER = 1;

    private static final int MAX_ZOMBIES = 10;
    private static final int MAX_SKELETONS = 6;
    private static final int MAX_BLAZES = 4;
    private static final int MAX_ROBOTS = 24;

    private RoboRampageRules() {}

    public enum RobotType { ZOMBIE, SKELETON, BLAZE }

    public enum HelmetVariant { IRON_HELMET, IRON_BLOCK, REDSTONE_BLOCK, LAPIS_BLOCK }

    public enum PowerUp { NONE, STRENGTH, SPEED }

    public record DeathReward(
            ScrapMaterial baseScrap, PowerUp powerUp, int extraIronBlocks, boolean extinguishKiller) {}

    public record RobotCounts(int zombies, int skeletons, int blazes) {
        public int total() { return zombies + skeletons + blazes; }

        public int count(RobotType type) {
            return switch (type) {
                case ZOMBIE -> zombies;
                case SKELETON -> skeletons;
                case BLAZE -> blazes;
            };
        }
    }

    public record SpawnLimits(int zombies, int skeletons, int blazes, int total) {
        public int limit(RobotType type) {
            return switch (type) {
                case ZOMBIE -> zombies;
                case SKELETON -> skeletons;
                case BLAZE -> blazes;
            };
        }
    }

    /** The exact height formulas in the October 2015 source, including their unbounded growth. */
    public static SpawnLimits legacySpawnLimits(int scrapHeight) {
        int height = Math.max(0, scrapHeight);
        int zombies = height / 2 + 2;
        int skeletons = height / 2;
        int blazes = height / 3;
        return new SpawnLimits(zombies, skeletons, blazes, zombies + skeletons + blazes);
    }

    /** Keeps the old height curve and unlock order while preventing runaway entity counts. */
    public static SpawnLimits liveSpawnLimits(int scrapHeight, int playerCount) {
        SpawnLimits legacy = legacySpawnLimits(scrapHeight);
        int players = Math.max(1, playerCount);
        int zombies = Math.min(MAX_ZOMBIES, legacy.zombies() + players - 1);
        int skeletons = Math.min(MAX_SKELETONS, legacy.skeletons() + (players - 1) / 2);
        int blazes = Math.min(MAX_BLAZES, legacy.blazes());
        int total = Math.min(MAX_ROBOTS, 6 + players * 4);
        return new SpawnLimits(zombies, skeletons, blazes, total);
    }

    /** One bounded decision replaces the original main-thread-blocking while(true) loop. */
    public static Optional<RobotType> chooseSpawn(
            SpawnLimits limits, RobotCounts counts, RandomGenerator random) {
        if (counts.total() >= limits.total()) return Optional.empty();
        List<RobotType> available = new ArrayList<>();
        for (RobotType type : RobotType.values()) {
            if (counts.count(type) < limits.limit(type)) available.add(type);
        }
        if (available.isEmpty()) return Optional.empty();
        return Optional.of(available.get(random.nextInt(available.size())));
    }

    /** The old code rolled in order; later successful rolls overwrite earlier helmets. */
    public static HelmetVariant zombieHelmet(boolean ironBlockRoll, boolean redstoneRoll) {
        HelmetVariant helmet = ironBlockRoll ? HelmetVariant.IRON_BLOCK : HelmetVariant.IRON_HELMET;
        return redstoneRoll ? HelmetVariant.REDSTONE_BLOCK : helmet;
    }

    /** The old skeleton has a sword, not a bow, and can additionally roll a lapis helmet. */
    public static HelmetVariant skeletonHelmet(
            boolean ironBlockRoll, boolean redstoneRoll, boolean lapisRoll) {
        HelmetVariant helmet = ironBlockRoll ? HelmetVariant.IRON_BLOCK : HelmetVariant.IRON_HELMET;
        if (redstoneRoll) helmet = HelmetVariant.REDSTONE_BLOCK;
        if (lapisRoll) helmet = HelmetVariant.LAPIS_BLOCK;
        return helmet;
    }

    public static HelmetVariant randomZombieHelmet(RandomGenerator random) {
        return zombieHelmet(percentRoll(random, 15), percentRoll(random, 10));
    }

    public static HelmetVariant randomSkeletonHelmet(RandomGenerator random) {
        return skeletonHelmet(percentRoll(random, 15), percentRoll(random, 12), percentRoll(random, 14));
    }

    public static DeathReward groundRobotReward(HelmetVariant helmet) {
        return switch (helmet) {
            case IRON_HELMET -> new DeathReward(ScrapMaterial.IRON, PowerUp.NONE, 0, false);
            case IRON_BLOCK -> new DeathReward(ScrapMaterial.IRON, PowerUp.NONE, 1, false);
            case REDSTONE_BLOCK -> new DeathReward(ScrapMaterial.IRON, PowerUp.STRENGTH, 0, false);
            case LAPIS_BLOCK -> new DeathReward(ScrapMaterial.IRON, PowerUp.SPEED, 0, false);
        };
    }

    public static DeathReward blazeReward() {
        return new DeathReward(ScrapMaterial.GOLD, PowerUp.NONE, 0, true);
    }

    /** Mirrors Utils.Possibilitat: its historical random range is [0, 101), not [0, 100). */
    private static boolean percentRoll(RandomGenerator random, int percent) {
        return random.nextDouble(101.0) <= percent;
    }
}
