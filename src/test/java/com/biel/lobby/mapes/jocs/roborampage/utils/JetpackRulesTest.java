package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JetpackRulesTest {
    @Test
    void startsReadyAndProvidesExactlyThreeSecondsWithoutAutomaticRestart() {
        JetpackRules.State state = new JetpackRules.State();
        assertEquals(100, state.chargePercent());
        assertTrue(state.toggle());
        for (int tick = 1; tick <= 60; tick++) assertTrue(state.tick(tick, true, true, false));
        assertEquals(0, state.fuelTicks());
        assertFalse(state.firing());
        assertFalse(state.toggle());
        assertFalse(state.tick(61, true, true, false));
    }

    @Test
    void rechargeRequiresSupportAndAOneSecondPauseAndIsCapped() {
        JetpackRules.State state = new JetpackRules.State();
        state.toggle();
        for (int tick = 1; tick <= 60; tick++) state.tick(tick, true, true, false);
        for (int tick = 61; tick < 80; tick++) state.tick(tick, true, true, true);
        assertEquals(0, state.fuelTicks());
        assertFalse(state.tick(80, true, true, true));
        assertEquals(1, state.fuelTicks());
        state.tick(82, true, true, false);
        assertEquals(1, state.fuelTicks(), "elapsed time alone cannot refill a hovering player");
        for (int tick = 83; tick <= 300; tick++) state.tick(tick, true, false, true);
        assertEquals(60, state.fuelTicks());
        assertEquals(100, state.chargePercent());
        assertFalse(state.firing(), "recharge does not reactivate thrust");
    }

    @Test
    void switchingItemsAndRespawningStopFlightWithoutRefilling() {
        JetpackRules.State state = new JetpackRules.State();
        state.toggle();
        state.tick(1, true, true, false);
        assertFalse(state.tick(2, true, false, false));
        assertEquals(59, state.fuelTicks());
        assertFalse(state.firing());
        assertTrue(state.consumeFallProtection(2), "switching to a weapon still protects the pending landing");
        state.toggle();
        state.tick(3, true, true, false);
        state.resetFlight();
        assertEquals(58, state.fuelTicks());
        assertFalse(state.firing());
        assertFalse(state.consumeFallProtection(4), "respawn cannot retain protection from a previous ascent");
    }

    @Test
    void landingProtectionIsOneUseExpiresAndDoesNotSurviveAnOrdinaryLanding() {
        JetpackRules.State state = new JetpackRules.State();
        assertFalse(state.consumeFallProtection(0));
        state.toggle();
        state.tick(1, true, true, false);
        state.toggle();
        assertTrue(state.consumeFallProtection(101));
        assertFalse(state.consumeFallProtection(101));
        state.toggle();
        state.tick(102, true, true, false);
        state.toggle();
        assertFalse(state.consumeFallProtection(203));
        state.toggle();
        state.tick(204, true, true, false);
        state.toggle();
        state.tick(205, true, true, true);
        state.tick(206, true, true, true);
        assertFalse(state.consumeFallProtection(206), "landing consumes an unused shield before another fall");
    }

    @Test
    void groundedSamplingLeavesOneTickForTheNativeLandingDamageEvent() {
        JetpackRules.State state = new JetpackRules.State();
        state.toggle();
        state.tick(1, true, true, false);
        state.toggle();
        state.tick(2, true, true, true);
        assertTrue(state.consumeFallProtection(2));
        assertFalse(state.consumeFallProtection(2));
    }

    @Test
    void invalidParticipantsCannotThrustRechargeOrKeepFallProtection() {
        JetpackRules.State state = new JetpackRules.State();
        state.toggle();
        state.tick(1, true, true, false);
        assertFalse(state.tick(2, false, true, true));
        assertFalse(state.firing());
        assertFalse(state.consumeFallProtection(2));
        state.tick(100, false, true, true);
        assertEquals(59, state.fuelTicks());
    }
}
