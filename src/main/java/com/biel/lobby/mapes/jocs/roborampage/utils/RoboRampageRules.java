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
    private static final int MAX_CUTTERS = 2;
    private static final int MAX_GHASTS = 2;
    private static final int MAX_ROBOTS = 24;
    private static final int MAX_WAVE_ROBOTS = 30;

    private RoboRampageRules() {}

    public enum RobotType { ZOMBIE, SKELETON, BLAZE, CUTTER, GHAST }

    public enum WavePhase { ASSAULT, CLEANUP, SUPPLY }

    public enum SupplyReward { ARMOR, BOW, TASER_UPGRADE }

    public enum HelmetVariant { IRON_HELMET, IRON_BLOCK, REDSTONE_BLOCK, LAPIS_BLOCK }

    public enum PowerUp { NONE, STRENGTH, SPEED }

    public record DeathReward(
            ScrapMaterial baseScrap, PowerUp powerUp, int extraIronBlocks, boolean extinguishKiller) {}

    public record LiveDeathReward(
            ScrapMaterial scrapMaterial,
            PowerUp powerUp,
            int minimumBlockCount,
            int maximumBlockCount,
            boolean extinguishKiller) {
        public LiveDeathReward {
            if (minimumBlockCount < 1 || maximumBlockCount < minimumBlockCount) {
                throw new IllegalArgumentException("Invalid scrap block range");
            }
        }

        public int rollBlockCount(RandomGenerator random, boolean criticalKill) {
            int blockCount = random.nextInt(minimumBlockCount, maximumBlockCount + 1);
            return blockCount + (criticalKill ? 1 : 0);
        }
    }

    public record GroundRobotProfile(
            double maximumHealth, double movementSpeed, double knockbackResistance) {}

    public record RobotCounts(int zombies, int skeletons, int blazes, int cutters, int ghasts) {
        public int total() { return zombies + skeletons + blazes + cutters + ghasts; }

        public int count(RobotType type) {
            return switch (type) {
                case ZOMBIE -> zombies;
                case SKELETON -> skeletons;
                case BLAZE -> blazes;
                case CUTTER -> cutters;
                case GHAST -> ghasts;
            };
        }
    }

    public record SpawnLimits(int zombies, int skeletons, int blazes, int cutters, int ghasts, int total) {
        public int limit(RobotType type) {
            return switch (type) {
                case ZOMBIE -> zombies;
                case SKELETON -> skeletons;
                case BLAZE -> blazes;
                case CUTTER -> cutters;
                case GHAST -> ghasts;
            };
        }
    }

    public record SupplyPlan(SupplyReward majorReward, boolean taserUpgrade) {
        public SupplyPlan {
            if (majorReward == SupplyReward.TASER_UPGRADE) {
                throw new IllegalArgumentException("Taser upgrades are guaranteed separately from the major reward");
            }
        }
    }

    /** The exact height formulas in the October 2015 source, including their unbounded growth. */
    public static SpawnLimits legacySpawnLimits(int scrapHeight) {
        int height = Math.max(0, scrapHeight);
        int zombies = height / 2 + 2;
        int skeletons = height / 2;
        int blazes = height / 3;
        return new SpawnLimits(zombies, skeletons, blazes, 0, 0, zombies + skeletons + blazes);
    }

    /** Keeps the old height curve while waves provide predictable chassis unlocks. */
    public static SpawnLimits liveSpawnLimits(int scrapHeight, int playerCount) {
        return liveSpawnLimits(scrapHeight, playerCount, Integer.MAX_VALUE);
    }

    public static SpawnLimits liveSpawnLimits(int scrapHeight, int playerCount, int waveNumber) {
        SpawnLimits legacy = legacySpawnLimits(scrapHeight);
        int players = Math.max(1, playerCount);
        int wave = Math.max(1, waveNumber);
        int zombies = Math.min(MAX_ZOMBIES, legacy.zombies() + players - 1);
        int skeletons = wave >= 2
                ? Math.max(1, Math.min(MAX_SKELETONS, legacy.skeletons() + (players - 1) / 2))
                : 0;
        int blazes = wave >= 3 ? Math.max(1, Math.min(MAX_BLAZES, legacy.blazes())) : 0;
        int cutters = wave >= 3 ? Math.min(MAX_CUTTERS, (players + 1) / 2) : 0;
        int ghasts = wave >= 4 ? Math.min(MAX_GHASTS, 1 + Math.max(0, wave - 7) / 4) : 0;
        int total = Math.min(MAX_ROBOTS, 6 + players * 4);
        return new SpawnLimits(zombies, skeletons, blazes, cutters, ghasts, total);
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

    /** Finite waves grow with both party size and elapsed waves, but never become runaway swarms. */
    public static int waveRobotQuota(int waveNumber, int playerCount) {
        int wave = Math.max(1, waveNumber);
        int players = Math.max(1, playerCount);
        return Math.min(MAX_WAVE_ROBOTS, 5 + wave * 2 + (players - 1) * 3);
    }

    public static int scaffoldingPerPlayer(int waveNumber) {
        return 8 + Math.min(4, Math.max(1, waveNumber)) * 2;
    }

    /** Minimal material-driven identities; advanced behaviors remain separate from equipment rewards. */
    public static GroundRobotProfile groundRobotProfile(HelmetVariant helmet) {
        return switch (helmet) {
            case IRON_HELMET -> new GroundRobotProfile(20, 0.23, 0);
            case IRON_BLOCK -> new GroundRobotProfile(36, 0.17, 0.65);
            case REDSTONE_BLOCK -> new GroundRobotProfile(16, 0.32, 0.1);
            case LAPIS_BLOCK -> new GroundRobotProfile(22, 0.24, 0.15);
        };
    }

    public static GroundRobotProfile cutterProfile() {
        return new GroundRobotProfile(18, 0.21, 0.2);
    }

    public static int scaffoldDamagePerAttack(RobotType type) {
        return type == RobotType.CUTTER ? 3 : 1;
    }

    public static double maximumFlyingHeight(double battleCenterY, int settledScrapHeight, RobotType type) {
        double chassisClearance = type == RobotType.GHAST ? 8 : 6;
        return battleCenterY + Math.max(0, settledScrapHeight) + chassisClearance;
    }

    /** Wave four introduces Ghasts, so wave-three supplies must close any ranged-kit gap. */
    public static boolean needsGuaranteedRangedSupply(
            int completedWaveNumber, boolean carriesBow, boolean carriesArrow) {
        return completedWaveNumber >= 3 && (!carriesBow || !carriesArrow);
    }

    /** Every cleared wave grants one Taser level, plus an independent equipment reward. */
    public static SupplyPlan supplyPlan(
            int completedWaveNumber,
            boolean carriesBow,
            boolean carriesArrow,
            boolean hasArmorUpgrade,
            boolean canUpgradeTaser,
            RandomGenerator random) {
        if (needsGuaranteedRangedSupply(completedWaveNumber, carriesBow, carriesArrow)) {
            return new SupplyPlan(SupplyReward.BOW, canUpgradeTaser);
        }
        List<SupplyReward> availableMajorRewards = new ArrayList<>();
        availableMajorRewards.add(SupplyReward.BOW);
        if (hasArmorUpgrade) availableMajorRewards.add(SupplyReward.ARMOR);
        return new SupplyPlan(
                availableMajorRewards.get(random.nextInt(availableMajorRewards.size())),
                canUpgradeTaser);
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

    public static LiveDeathReward liveGroundRobotReward(HelmetVariant helmet) {
        DeathReward recoveredReward = groundRobotReward(helmet);
        int minimumBlockCount = helmet == HelmetVariant.IRON_BLOCK ? 3 : 2;
        int maximumBlockCount = helmet == HelmetVariant.IRON_BLOCK ? 5 : 2;
        return new LiveDeathReward(
                recoveredReward.baseScrap(),
                recoveredReward.powerUp(),
                minimumBlockCount,
                maximumBlockCount,
                recoveredReward.extinguishKiller());
    }

    public static LiveDeathReward liveBlazeReward() {
        DeathReward recoveredReward = blazeReward();
        return new LiveDeathReward(
                recoveredReward.baseScrap(),
                recoveredReward.powerUp(),
                2,
                2,
                recoveredReward.extinguishKiller());
    }

    public static LiveDeathReward liveGhastReward() {
        return new LiveDeathReward(ScrapMaterial.IRON, PowerUp.NONE, 8, 12, false);
    }

    /** Mirrors Utils.Possibilitat: its historical random range is [0, 101), not [0, 100). */
    private static boolean percentRoll(RandomGenerator random, int percent) {
        return random.nextDouble(101.0) <= percent;
    }
}
