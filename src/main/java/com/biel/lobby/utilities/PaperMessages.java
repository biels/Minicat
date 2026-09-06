package com.biel.lobby.utilities;

import java.time.Duration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.biel.lobby.Com;

public final class PaperMessages {
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacySection();
    private static final Duration TICK = Duration.ofMillis(50);

    private PaperMessages() {
    }

    public static Component legacy(String text) {
        return LEGACY.deserialize(text == null ? "" : text);
    }

    public static void sendActionBar(Player player, String message, long durationTicks) {
        Component component = legacy(message);
        for (long delay = 0; delay < durationTicks; delay += 40) {
            Bukkit.getScheduler().runTaskLater(Com.getPlugin(), () -> {
                if (player.isOnline()) {
                    player.sendActionBar(component);
                }
            }, delay);
        }
        Bukkit.getScheduler().runTaskLater(Com.getPlugin(), () -> {
            if (player.isOnline()) {
                player.sendActionBar(Component.empty());
            }
        }, durationTicks);
    }

    public static void showTitle(Player player, int fadeInTicks, int stayTicks, int fadeOutTicks,
                                 String title, String subtitle) {
        player.showTitle(Title.title(
                legacy(title),
                legacy(subtitle),
                Title.Times.times(TICK.multipliedBy(fadeInTicks), TICK.multipliedBy(stayTicks),
                        TICK.multipliedBy(fadeOutTicks))));
    }
}
