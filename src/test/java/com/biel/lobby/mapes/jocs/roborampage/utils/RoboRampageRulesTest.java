package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

class RoboRampageRulesTest {
    @Test
    void introductionsGuaranteeOneNewChassisPerEarlyWaveWithoutBypassingCaps() {
        var sequence = List.of(RoboRampageRules.RobotType.ZOMBIE, RoboRampageRules.RobotType.SKELETON,
                RoboRampageRules.RobotType.SPRINGER, RoboRampageRules.RobotType.CUTTER,
                RoboRampageRules.RobotType.COMPACTOR, RoboRampageRules.RobotType.BLAZE,
                RoboRampageRules.RobotType.GHAST);
        for (int wave = 1; wave <= 7; wave++) {
            var introduction = RoboRampageRules.introRobotForWave(wave);
            assertEquals(sequence.get(wave - 1), introduction.orElseThrow());
            if (wave == 5) continue; // Boss uses its dedicated spawn path.
            var limits = RoboRampageRules.liveSpawnLimits(0, 1, wave);
            for (int seed = 0; seed < 100; seed++) {
                assertEquals(introduction, RoboRampageRules.chooseSpawn(limits,
                        new RoboRampageRules.RobotCounts(0, 0, 0, 0, 0), introduction, new Random(seed)));
            }
        }
        assertTrue(RoboRampageRules.introRobotForWave(8).isEmpty());
        var limits = RoboRampageRules.liveSpawnLimits(0, 1, 3);
        assertTrue(RoboRampageRules.chooseSpawn(limits,
                new RoboRampageRules.RobotCounts(2, 1, 0, 0, 0, 2),
                RoboRampageRules.introRobotForWave(3), new Random(1)).isEmpty());
    }

    @Test
    void ordinaryRobotsLoseFortyPercentOfHealthAndHeavyRobotsStayBelowTwentyThree() {
        assertEquals(12, RoboRampageRules.groundRobotProfile(
                RoboRampageRules.HelmetVariant.IRON_HELMET).maximumHealth());
        assertEquals(22, RoboRampageRules.groundRobotProfile(
                RoboRampageRules.HelmetVariant.IRON_BLOCK).maximumHealth());
        assertEquals(12, RoboRampageRules.cutterProfile().maximumHealth());
        assertEquals(12, RoboRampageRules.springerProfile().maximumHealth());
    }

    @Test
    void liveLimitsPreserveUnlockOrderAndStayBounded() {
        var bottom = RoboRampageRules.liveSpawnLimits(0, 1, 1);
        assertEquals(2, bottom.zombies());
        assertEquals(0, bottom.skeletons());
        assertEquals(0, bottom.blazes());
        assertEquals(0, bottom.cutters());
        assertEquals(0, bottom.ghasts());

        var highFourPlayer = RoboRampageRules.liveSpawnLimits(255, 4, 20);
        assertTrue(highFourPlayer.zombies() <= 6);
        assertTrue(highFourPlayer.skeletons() <= 4);
        assertTrue(highFourPlayer.blazes() <= 2);
        assertTrue(highFourPlayer.cutters() <= 1);
        assertTrue(highFourPlayer.ghasts() <= 1);
        assertTrue(highFourPlayer.total() <= 14);
    }

    @Test
    void wavesUnlockChassisIndependentlyFromHeapShape() {
        var waveTwo = RoboRampageRules.liveSpawnLimits(0, 1, 2);
        assertEquals(1, waveTwo.skeletons());
        assertEquals(0, waveTwo.blazes());
        assertEquals(0, waveTwo.cutters());
        assertEquals(0, waveTwo.ghasts());

        var waveThree = RoboRampageRules.liveSpawnLimits(0, 1, 3);
        assertEquals(0, waveThree.blazes());
        assertEquals(1, waveThree.springers());
        assertEquals(0, waveThree.cutters());
        assertEquals(0, waveThree.ghasts());

        var waveFour = RoboRampageRules.liveSpawnLimits(0, 1, 4);
        assertEquals(1, waveFour.cutters());
        assertEquals(0, waveFour.ghasts());
        assertEquals(1, RoboRampageRules.liveSpawnLimits(0, 1, 6).blazes());
        assertEquals(1, RoboRampageRules.liveSpawnLimits(0, 1, 7).ghasts());
    }

    @Test
    void spawnChoiceReturnsEmptyImmediatelyWhenCapsAreFull() {
        var limits = new RoboRampageRules.SpawnLimits(2, 1, 1, 1, 1, 6);
        var counts = new RoboRampageRules.RobotCounts(2, 1, 1, 1, 1);
        assertTrue(RoboRampageRules.chooseSpawn(limits, counts, new Random(1)).isEmpty());
    }

    @Test
    void spawnChoiceNeverSelectsAFullRole() {
        var limits = new RoboRampageRules.SpawnLimits(2, 1, 1, 1, 1, 6);
        var counts = new RoboRampageRules.RobotCounts(2, 0, 0, 0, 0);
        for (int seed = 0; seed < 100; seed++) {
            var chosen = RoboRampageRules.chooseSpawn(limits, counts, new Random(seed)).orElseThrow();
            assertTrue(chosen != RoboRampageRules.RobotType.ZOMBIE);
        }
    }

    @Test
    void ordinaryRobotsDropTwoBlocksAndCriticalKillsAddOne() {
        for (var helmet : List.of(
                RoboRampageRules.HelmetVariant.IRON_HELMET,
                RoboRampageRules.HelmetVariant.REDSTONE_BLOCK,
                RoboRampageRules.HelmetVariant.LAPIS_BLOCK)) {
            var reward = RoboRampageRules.liveGroundRobotReward(helmet);
            assertEquals(2, reward.rollBlockCount(new Random(1), false));
            assertEquals(3, reward.rollBlockCount(new Random(1), true));
        }

        var blazeReward = RoboRampageRules.liveBlazeReward();
        assertEquals(2, blazeReward.rollBlockCount(new Random(1), false));
        assertEquals(3, blazeReward.rollBlockCount(new Random(1), true));
    }

    @Test
    void ironHeadRobotsDropThreeToFiveBlocksBeforeCriticalBonus() {
        var reward = RoboRampageRules.liveGroundRobotReward(RoboRampageRules.HelmetVariant.IRON_BLOCK);
        boolean sawThree = false;
        boolean sawFive = false;
        for (int seed = 0; seed < 100; seed++) {
            int normalBlockCount = reward.rollBlockCount(new Random(seed), false);
            int criticalBlockCount = reward.rollBlockCount(new Random(seed), true);
            assertTrue(normalBlockCount >= 3 && normalBlockCount <= 5);
            assertEquals(normalBlockCount + 1, criticalBlockCount);
            sawThree |= normalBlockCount == 3;
            sawFive |= normalBlockCount == 5;
        }
        assertTrue(sawThree && sawFive);
    }

    @Test
    void blockHeadsHaveReadableBaselineProfiles() {
        var ordinary = RoboRampageRules.groundRobotProfile(RoboRampageRules.HelmetVariant.IRON_HELMET);
        var ironHead = RoboRampageRules.groundRobotProfile(RoboRampageRules.HelmetVariant.IRON_BLOCK);
        var overclocker = RoboRampageRules.groundRobotProfile(RoboRampageRules.HelmetVariant.REDSTONE_BLOCK);

        assertTrue(ironHead.maximumHealth() > ordinary.maximumHealth());
        assertTrue(ironHead.movementSpeed() < ordinary.movementSpeed());
        assertTrue(ironHead.knockbackResistance() > ordinary.knockbackResistance());
        assertTrue(overclocker.movementSpeed() > ordinary.movementSpeed());
    }

    @Test
    void cutterTradesPlayerPressureForImmediateScaffoldDamage() {
        var cutter = RoboRampageRules.cutterProfile();
        var ordinary = RoboRampageRules.groundRobotProfile(RoboRampageRules.HelmetVariant.IRON_HELMET);

        assertTrue(cutter.maximumHealth() <= ordinary.maximumHealth());
        assertTrue(cutter.movementSpeed() < ordinary.movementSpeed());
        assertEquals(3, RoboRampageRules.scaffoldDamagePerAttack(RoboRampageRules.RobotType.CUTTER));
        assertEquals(1, RoboRampageRules.scaffoldDamagePerAttack(RoboRampageRules.RobotType.ZOMBIE));
    }

    @Test
    void flyingRobotCeilingsFollowTheSettledHeap() {
        assertEquals(18, RoboRampageRules.maximumFlyingHeight(2, 10, RoboRampageRules.RobotType.BLAZE));
        assertEquals(20, RoboRampageRules.maximumFlyingHeight(2, 10, RoboRampageRules.RobotType.GHAST));
        assertEquals(10, RoboRampageRules.maximumFlyingHeight(2, -4, RoboRampageRules.RobotType.GHAST));
    }

    @Test
    void waveFiveSupplyGuaranteesRangedReadinessForTheFirstAerialWave() {
        assertFalse(RoboRampageRules.needsGuaranteedRangedSupply(4, false, false));
        assertTrue(RoboRampageRules.needsGuaranteedRangedSupply(5, false, false));
        assertTrue(RoboRampageRules.needsGuaranteedRangedSupply(5, true, false));
        assertTrue(RoboRampageRules.needsGuaranteedRangedSupply(5, false, true));
        assertFalse(RoboRampageRules.needsGuaranteedRangedSupply(5, true, true));
    }

    @Test
    void everyWaveClearGuaranteesExactlyOneTaserUpgradeUntilMaximumLevel() {
        for (int seed = 0; seed < 100; seed++) {
            var plan = RoboRampageRules.supplyPlan(1, true, true, true, true, new Random(seed));
            assertTrue(plan.taserUpgrade());
            assertTrue(plan.majorReward() != RoboRampageRules.SupplyReward.TASER_UPGRADE);
            assertEquals(1, plan.healingPotions());
        }

        var maximumLevelPlan = RoboRampageRules.supplyPlan(1, true, true, true, false, new Random(1));
        assertFalse(maximumLevelPlan.taserUpgrade());
    }

    @Test
    void guaranteedRangedSupplyKeepsTheTaserUpgrade() {
        var plan = RoboRampageRules.supplyPlan(5, false, false, true, true, new Random(1));
        assertEquals(RoboRampageRules.SupplyReward.BOW, plan.majorReward());
        assertTrue(plan.taserUpgrade());
        assertEquals(1, plan.healingPotions());
    }

    @Test
    void livePlayerDamageIsReducedByTwentyPercentFromTheRecoveredBalance() {
        assertEquals(
                RoboRampageRules.PLAYER_DAMAGE_MULTIPLIER * 0.8,
                RoboRampageRules.LIVE_PLAYER_DAMAGE_MULTIPLIER);
    }

    @Test
    void ghastDeathIsASignificantButBoundedScrapEvent() {
        var reward = RoboRampageRules.liveGhastReward();
        boolean sawMinimum = false;
        boolean sawMaximum = false;
        for (int seed = 0; seed < 100; seed++) {
            int blocks = reward.rollBlockCount(new Random(seed), false);
            assertTrue(blocks >= 8 && blocks <= 12);
            sawMinimum |= blocks == 8;
            sawMaximum |= blocks == 12;
        }
        assertTrue(sawMinimum && sawMaximum);
    }

    @Test
    void everyGhastProjectileImpactContributesOneIronScrapBlock() {
        assertEquals(ScrapMaterial.IRON, RoboRampageRules.ghastProjectileImpactScrap());
    }

    @Test
    void waveQuotasScaleButRemainFinite() {
        assertEquals(5, RoboRampageRules.waveRobotQuota(1, 1));
        assertEquals(11, RoboRampageRules.waveRobotQuota(1, 4));
        assertEquals(22, RoboRampageRules.waveRobotQuota(100, 100));
        assertEquals(10, RoboRampageRules.scaffoldingPerPlayer(1));
        assertEquals(16, RoboRampageRules.scaffoldingPerPlayer(99));
    }
}
