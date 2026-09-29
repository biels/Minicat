package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.biel.lobby.localization.MessageKey;

class IntroductionControllerTest {
    @Test void firstEncountersTakePriorityAndLessonsAreSpacedAndShownOnlyOnce() {
        List<MessageKey> shown = new ArrayList<>();
        IntroductionController introductions = new IntroductionController((name, explanation) -> shown.add(explanation));
        introductions.tip(MessageKey.ROBO_RAMPAGE_INFO_TASER);
        introductions.tip(MessageKey.ROBO_RAMPAGE_INFO_JETPACK);
        introductions.introduce(MessageKey.ROBO_RAMPAGE_ENEMY_WORKER, MessageKey.ROBO_RAMPAGE_INTRO_WORKER);
        introductions.introduce(MessageKey.ROBO_RAMPAGE_ENEMY_WORKER, MessageKey.ROBO_RAMPAGE_INTRO_WORKER);
        tick(introductions, 60);
        assertTrue(shown.isEmpty());
        introductions.tick();
        assertEquals(List.of(MessageKey.ROBO_RAMPAGE_INTRO_WORKER), shown);
        tick(introductions, 140);
        assertEquals(1, shown.size());
        introductions.tick();
        assertEquals(MessageKey.ROBO_RAMPAGE_INFO_TASER, shown.getLast());
        tick(introductions, 141);
        assertEquals(List.of(MessageKey.ROBO_RAMPAGE_INTRO_WORKER,
                MessageKey.ROBO_RAMPAGE_INFO_TASER, MessageKey.ROBO_RAMPAGE_INFO_JETPACK), shown);
        tick(introductions, 500);
        assertEquals(3, shown.size());
    }

    @Test void teardownDropsPendingAndRejectsFutureLessons() {
        List<MessageKey> shown = new ArrayList<>();
        IntroductionController introductions = new IntroductionController((name, explanation) -> shown.add(explanation));
        introductions.tip(MessageKey.ROBO_RAMPAGE_INFO_TNT);
        introductions.close();
        introductions.close();
        introductions.introduce(MessageKey.ROBO_RAMPAGE_ENEMY_WORKER, MessageKey.ROBO_RAMPAGE_INTRO_WORKER);
        tick(introductions, 1000);
        assertTrue(shown.isEmpty());
    }

    private void tick(IntroductionController controller, int ticks) {
        for (int tick = 0; tick < ticks; tick++) controller.tick();
    }
}
