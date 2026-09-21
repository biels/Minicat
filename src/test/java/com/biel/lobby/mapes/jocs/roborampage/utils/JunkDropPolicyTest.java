package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class JunkDropPolicyTest {
    @Test
    void requiresEnoughMaterialSustainedUnevennessAndCooldown() {
        ScrapGrid grid = new ScrapGrid();
        Map<ScrapGrid.Cell, ScrapMaterial> ridge = new LinkedHashMap<>();
        for (int y = 1; y <= 12; y++) ridge.put(new ScrapGrid.Cell(7, y, 7), ScrapMaterial.IRON);
        grid.restoreSettled(ridge);
        JunkDropPolicy policy = new JunkDropPolicy();

        for (int sample = 1; sample < JunkDropPolicy.REQUIRED_SAMPLES; sample++) {
            assertFalse(policy.sample(grid.settledBlockCount(), grid.unevenness(), false));
        }
        assertTrue(policy.sample(grid.settledBlockCount(), grid.unevenness(), false));
        for (int sample = 0; sample < JunkDropPolicy.COOLDOWN_SAMPLES; sample++) {
            assertFalse(policy.sample(grid.settledBlockCount(), grid.unevenness(), false));
        }
        for (int sample = 1; sample < JunkDropPolicy.REQUIRED_SAMPLES; sample++) {
            assertFalse(policy.sample(grid.settledBlockCount(), grid.unevenness(), false));
        }
        assertTrue(policy.sample(grid.settledBlockCount(), grid.unevenness(), false));
    }

    @Test
    void ordinaryBumpsAndInFlightCarsDoNotTriggerAnotherDelivery() {
        ScrapGrid lowBump = new ScrapGrid();
        Map<ScrapGrid.Cell, ScrapMaterial> cells = new LinkedHashMap<>();
        for (int x = 0; x < 12; x++) cells.put(new ScrapGrid.Cell(x, 1, 0), ScrapMaterial.IRON);
        lowBump.restoreSettled(cells);
        JunkDropPolicy policy = new JunkDropPolicy();
        for (int sample = 0; sample < 30; sample++) {
            assertFalse(policy.sample(lowBump.settledBlockCount(), lowBump.unevenness(), false));
        }

        ScrapGrid ridge = new ScrapGrid();
        Map<ScrapGrid.Cell, ScrapMaterial> tall = new LinkedHashMap<>();
        for (int y = 1; y <= 12; y++) tall.put(new ScrapGrid.Cell(7, y, 7), ScrapMaterial.IRON);
        ridge.restoreSettled(tall);
        for (int sample = 0; sample < 30; sample++) {
            assertFalse(policy.sample(ridge.settledBlockCount(), ridge.unevenness(), true));
        }
    }
}
