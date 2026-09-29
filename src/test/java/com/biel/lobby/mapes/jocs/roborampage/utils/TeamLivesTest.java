package com.biel.lobby.mapes.jocs.roborampage.utils;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TeamLivesTest {
    @Test void threeDeathsEliminateAndDuplicateEventsDoNotConsumeLives() {
        TeamLives lives = new TeamLives();
        UUID player = UUID.randomUUID();
        lives.start(List.of(player));
        for (int remaining = 2; remaining >= 0; remaining--) {
            assertTrue(lives.die(player));
            assertFalse(lives.die(player));
            assertEquals(remaining, lives.remaining(player));
            assertEquals(remaining == 0, lives.defeated());
            lives.respawn(player);
        }
        assertFalse(lives.canPlay(player));
        assertFalse(lives.die(player));
    }

    @Test void teamContinuesWithAnotherLifeIncludingAReconnectingSeat() {
        TeamLives lives = new TeamLives();
        UUID first = UUID.randomUUID(), reconnecting = UUID.randomUUID();
        lives.start(List.of(first, reconnecting));
        for (int death = 0; death < 3; death++) { lives.die(first); lives.respawn(first); }
        assertFalse(lives.defeated());
        // A disconnected seat is untouched during grace. Respawn/reconnect cannot refill it.
        lives.die(reconnecting);
        lives.respawn(reconnecting);
        assertEquals(2, lives.remaining(reconnecting));
        lives.abandon(reconnecting);
        assertTrue(lives.defeated());
    }

    @Test void lateSpectatorsCannotAcquireLivesAndAnEmptyRosterIsNotACombatLoss() {
        TeamLives lives = new TeamLives();
        assertFalse(lives.defeated());
        UUID participant = UUID.randomUUID(), spectator = UUID.randomUUID();
        lives.start(List.of(participant));
        assertFalse(lives.contains(spectator));
        assertFalse(lives.die(spectator));
        lives.respawn(spectator);
        assertFalse(lives.canPlay(spectator));
        lives.abandon(participant);
        lives.abandon(participant);
        assertTrue(lives.defeated());
    }
}
