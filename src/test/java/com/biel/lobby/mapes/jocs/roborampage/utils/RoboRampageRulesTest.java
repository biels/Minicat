package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

class RoboRampageRulesTest {
    @Test
    void liveLimitsPreserveUnlockOrderAndStayBounded() {
        var bottom = RoboRampageRules.liveSpawnLimits(0, 1, 1);
        assertEquals(2, bottom.zombies());
        assertEquals(0, bottom.skeletons());
        assertEquals(0, bottom.blazes());
        assertEquals(0, bottom.cutters());
        assertEquals(0, bottom.ghasts());

        var highFourPlayer = RoboRampageRules.liveSpawnLimits(255, 4, 20);
        assertTrue(highFourPlayer.zombies() <= 10);
        assertTrue(highFourPlayer.skeletons() <= 6);
        assertTrue(highFourPlayer.blazes() <= 4);
        assertTrue(highFourPlayer.cutters() <= 2);
        assertTrue(highFourPlayer.ghasts() <= 2);
        assertTrue(highFourPlayer.total() <= 24);
    }

    @Test
    void wavesUnlockChassisIndependentlyFromHeapShape() {
        var waveTwo = RoboRampageRules.liveSpawnLimits(0, 1, 2);
        assertEquals(1, waveTwo.skeletons());
        assertEquals(0, waveTwo.blazes());
        assertEquals(0, waveTwo.cutters());
        assertEquals(0, waveTwo.ghasts());

        var waveThree = RoboRampageRules.liveSpawnLimits(0, 1, 3);
        assertEquals(1, waveThree.blazes());
        assertEquals(1, waveThree.cutters());
        assertEquals(0, waveThree.ghasts());

        var waveFour = RoboRampageRules.liveSpawnLimits(0, 1, 4);
        assertEquals(1, waveFour.ghasts());
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

        assertTrue(cutter.maximumHealth() < ordinary.maximumHealth());
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
    void waveThreeSupplyGuaranteesRangedReadinessForTheFirstGhastWave() {
        assertFalse(RoboRampageRules.needsGuaranteedRangedSupply(2, false, false));
        assertTrue(RoboRampageRules.needsGuaranteedRangedSupply(3, false, false));
        assertTrue(RoboRampageRules.needsGuaranteedRangedSupply(3, true, false));
        assertTrue(RoboRampageRules.needsGuaranteedRangedSupply(3, false, true));
        assertFalse(RoboRampageRules.needsGuaranteedRangedSupply(3, true, true));
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
        var plan = RoboRampageRules.supplyPlan(3, false, false, true, true, new Random(1));
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
        assertEquals(7, RoboRampageRules.waveRobotQuota(1, 1));
        assertEquals(16, RoboRampageRules.waveRobotQuota(1, 4));
        assertEquals(30, RoboRampageRules.waveRobotQuota(100, 100));
        assertEquals(10, RoboRampageRules.scaffoldingPerPlayer(1));
        assertEquals(16, RoboRampageRules.scaffoldingPerPlayer(99));
    }
}
