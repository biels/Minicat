package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class DemolitionRulesTest {
    @Test
    void liftHasFiniteReachExposureAndABoundedVerticalImpulse() {
        assertEquals(0.9, DemolitionRules.launchSpeed(0, 1), 1e-9);
        assertEquals(0.725, DemolitionRules.launchSpeed(4, 1), 1e-9);
        assertTrue(DemolitionRules.launchSpeed(4, 0.5) < DemolitionRules.launchSpeed(4, 1));
        assertEquals(0, DemolitionRules.launchSpeed(8, 1));
        assertEquals(0, DemolitionRules.launchSpeed(9, 1));
        assertEquals(0, DemolitionRules.launchSpeed(2, 0));
        assertEquals(0, DemolitionRules.launchSpeed(Double.NaN, 1));
        assertEquals(0, DemolitionRules.launchSpeed(2, Double.POSITIVE_INFINITY));
        assertTrue(DemolitionRules.launchSpeed(0, 5) <= DemolitionRules.MAX_VERTICAL_SPEED);
    }

    @Test
    void landingProtectionIsPerPlayerSingleUseAndExpiresAtTheDeadline() {
        DemolitionRules rules = new DemolitionRules();
        UUID first = UUID.randomUUID();
        UUID second = UUID.randomUUID();
        rules.protectLanding(first, 10);
        rules.protectLanding(second, 10);
        assertTrue(rules.consumeFallProtection(first, 169));
        assertFalse(rules.consumeFallProtection(first, 169));
        assertFalse(rules.consumeFallProtection(second, 170));
    }

    @Test
    void repeatedLaunchesRefreshProtectionAndCleanupRemovesIt() {
        DemolitionRules rules = new DemolitionRules();
        UUID first = UUID.randomUUID();
        rules.protectLanding(first, 0);
        rules.protectLanding(first, 100);
        rules.prune(160);
        assertTrue(rules.consumeFallProtection(first, 200));
        rules.protectLanding(first, 200);
        rules.forgetPlayer(first);
        assertFalse(rules.consumeFallProtection(first, 201));
        rules.protectLanding(first, 201);
        rules.clear();
        assertFalse(rules.consumeFallProtection(first, 202));
    }
}
