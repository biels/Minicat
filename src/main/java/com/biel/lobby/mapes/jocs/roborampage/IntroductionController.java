package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.bukkit.entity.Player;

import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;

/** Brief encounter/status captions; reserves the action bar while each is visible. */
final class IntroductionController implements AutoCloseable {
    private static final int DISPLAY_TICKS = 80;
    private static final int GAP_TICKS = 20;
    private final Consumer<MessageKey> display;
    private final Queue<MessageKey> encounters = new ArrayDeque<>();
    private final Queue<MessageKey> tips = new ArrayDeque<>();
    private final Set<MessageKey> introduced = new HashSet<>();
    private MessageKey activeLesson;
    private int remainingTicks = GAP_TICKS;
    private boolean activeEncounter;
    private boolean closed;

    IntroductionController(Supplier<? extends List<Player>> audience) {
        this(key -> {
            for (Player player : audience.get()) {
                if (player.isOnline()) player.sendActionBar(Messages.component(player, key));
            }
        });
    }

    IntroductionController(Consumer<MessageKey> display) { this.display = display; }

    void introduce(MessageKey explanation) {
        if (!closed && introduced.add(explanation)) {
            encounters.add(explanation);
            // New enemies take the status slot from a control tip immediately.
            if (activeLesson != null && !activeEncounter) {
                tips.add(activeLesson);
                activeLesson = null;
                remainingTicks = 0;
            }
        }
    }

    void tip(MessageKey explanation) {
        if (!closed && introduced.add(explanation)) tips.add(explanation);
    }

    boolean isDisplaying() { return !closed && activeLesson != null; }

    void tick() {
        if (closed) return;
        if (activeLesson != null) {
            if (--remainingTicks <= 0) {
                activeLesson = null;
                remainingTicks = GAP_TICKS;
            } else if (remainingTicks % 10 == 0) display.accept(activeLesson);
            return;
        }
        if (remainingTicks-- > 0 || encounters.isEmpty() && tips.isEmpty()) return;
        activeEncounter = !encounters.isEmpty();
        activeLesson = (activeEncounter ? encounters : tips).remove();
        remainingTicks = DISPLAY_TICKS;
        display.accept(activeLesson);
    }

    @Override public void close() {
        closed = true;
        activeLesson = null;
        encounters.clear();
        tips.clear();
        introduced.clear();
    }
}
