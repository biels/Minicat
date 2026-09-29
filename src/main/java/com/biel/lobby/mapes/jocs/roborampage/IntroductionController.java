package com.biel.lobby.mapes.jocs.roborampage;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.BiConsumer;

import org.bukkit.entity.Player;

import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;

import net.kyori.adventure.title.Title;

/** First encounters get one short caption, with space between lessons. */
final class IntroductionController implements AutoCloseable {
    private static final int SPACING_TICKS = 20 * 7;
    private final BiConsumer<MessageKey, MessageKey> display;
    private final Queue<Lesson> encounters = new ArrayDeque<>();
    private final Queue<Lesson> tips = new ArrayDeque<>();
    private final Set<MessageKey> introduced = new HashSet<>();
    private int remainingTicks = 60;
    private boolean closed;

    private record Lesson(MessageKey name, MessageKey explanation) {}

    IntroductionController(Supplier<? extends List<Player>> audience) {
        this((name, explanation) -> {
            for (Player player : audience.get()) {
                if (!player.isOnline()) continue;
                Messages.send(player, explanation);
                if (name != null) player.showTitle(Title.title(
                        Messages.component(player, name), Messages.component(player, explanation),
                        Title.Times.times(Duration.ofMillis(200), Duration.ofSeconds(4), Duration.ofMillis(400))));
            }
        });
    }

    IntroductionController(BiConsumer<MessageKey, MessageKey> display) { this.display = display; }

    void introduce(MessageKey name, MessageKey explanation) {
        if (!closed && introduced.add(explanation)) {
            (name == null ? tips : encounters).add(new Lesson(name, explanation));
        }
    }

    void tip(MessageKey explanation) { introduce(null, explanation); }

    void tick() {
        if (closed || remainingTicks-- > 0 || encounters.isEmpty() && tips.isEmpty()) return;
        Lesson lesson = (encounters.isEmpty() ? tips : encounters).remove();
        display.accept(lesson.name(), lesson.explanation());
        remainingTicks = SPACING_TICKS;
    }

    @Override public void close() {
        closed = true;
        encounters.clear();
        tips.clear();
        introduced.clear();
    }
}
