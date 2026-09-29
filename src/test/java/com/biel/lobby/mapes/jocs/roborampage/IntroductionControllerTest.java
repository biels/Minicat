package com.biel.lobby.mapes.jocs.roborampage;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.biel.lobby.localization.MessageKey;

class IntroductionControllerTest {
    @Test void captionsReserveStatusRefreshThenReturnItToToolsWithoutRepeatingEncounters() {
        List<MessageKey> shown = new ArrayList<>();
        IntroductionController introductions = new IntroductionController(shown::add);
        introductions.tip(MessageKey.ROBO_RAMPAGE_INFO_TASER);
        introductions.introduce(MessageKey.ROBO_RAMPAGE_INTRO_WORKER);
        introductions.introduce(MessageKey.ROBO_RAMPAGE_INTRO_WORKER);
        tick(introductions, 20);
        assertFalse(introductions.isDisplaying());
        introductions.tick();
        assertTrue(introductions.isDisplaying());
        assertEquals(List.of(MessageKey.ROBO_RAMPAGE_INTRO_WORKER), shown);
        tick(introductions, 79);
        assertTrue(introductions.isDisplaying());
        assertEquals(8, shown.size(), "refresh the short caption so charge messages cannot erase it");
        introductions.tick();
        assertFalse(introductions.isDisplaying());
        tick(introductions, 21);
        assertEquals(MessageKey.ROBO_RAMPAGE_INFO_TASER, shown.getLast());
        tick(introductions, 200);
        assertFalse(introductions.isDisplaying());
        int completedDisplays = shown.size();
        introductions.introduce(MessageKey.ROBO_RAMPAGE_INTRO_WORKER);
        tick(introductions, 500);
        assertEquals(completedDisplays, shown.size());
    }

    @Test void newEnemyInterruptsAToolTipAndTheTipResumesLater() {
        List<MessageKey> shown = new ArrayList<>();
        IntroductionController introductions = new IntroductionController(shown::add);
        introductions.tip(MessageKey.ROBO_RAMPAGE_INFO_TNT);
        tick(introductions, 21);
        introductions.introduce(MessageKey.ROBO_RAMPAGE_INTRO_SPRINGER);
        introductions.tick();
        assertEquals(MessageKey.ROBO_RAMPAGE_INTRO_SPRINGER, shown.getLast());
        tick(introductions, 101);
        assertEquals(MessageKey.ROBO_RAMPAGE_INFO_TNT, shown.getLast());
    }

    @Test void teardownReleasesStatusAndDropsPendingAndFutureLessons() {
        List<MessageKey> shown = new ArrayList<>();
        IntroductionController introductions = new IntroductionController(shown::add);
        introductions.tip(MessageKey.ROBO_RAMPAGE_INFO_TNT);
        tick(introductions, 21);
        introductions.close();
        introductions.close();
        assertFalse(introductions.isDisplaying());
        introductions.introduce(MessageKey.ROBO_RAMPAGE_INTRO_WORKER);
        tick(introductions, 1000);
        assertEquals(List.of(MessageKey.ROBO_RAMPAGE_INFO_TNT), shown);
    }

    private void tick(IntroductionController controller, int ticks) {
        for (int tick = 0; tick < ticks; tick++) controller.tick();
    }
}
