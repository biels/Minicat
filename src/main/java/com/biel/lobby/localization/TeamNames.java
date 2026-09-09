package com.biel.lobby.localization;

import java.util.Locale;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;

/** Presentation only: legacy team identifiers and custom proper names stay unchanged. */
public final class TeamNames {
    private TeamNames() {}
    public static Component component(Player viewer, DyeColor color, String name) {
        return isColorName(color, name) ? Messages.component(viewer, key(color)) : Component.text(name);
    }
    public static String marker(DyeColor color, String name) {
        return isColorName(color, name) ? Messages.sharedItemMarker(key(color)) : name;
    }
    static boolean isColorName(DyeColor color, String name) {
        var aliases = switch (color) {
            case WHITE -> java.util.Set.of("white", "blanc");
            case ORANGE -> java.util.Set.of("orange", "taronja");
            case MAGENTA -> java.util.Set.of("magenta");
            case LIGHT_BLUE -> java.util.Set.of("light_blue", "light blue", "blau clar");
            case YELLOW -> java.util.Set.of("yellow", "groc");
            case LIME -> java.util.Set.of("lime", "verd llima", "llima");
            case PINK -> java.util.Set.of("pink", "rosa");
            case GRAY -> java.util.Set.of("gray", "gris", "grey");
            case LIGHT_GRAY -> java.util.Set.of("light_gray", "light gray", "gris clar", "silver", "light grey", "platejat");
            case CYAN -> java.util.Set.of("cyan", "cian");
            case PURPLE -> java.util.Set.of("purple", "lila", "morat");
            case BLUE -> java.util.Set.of("blue", "blau");
            case BROWN -> java.util.Set.of("brown", "marró");
            case GREEN -> java.util.Set.of("green", "verd");
            case RED -> java.util.Set.of("red", "vermell");
            case BLACK -> java.util.Set.of("black", "negre");
        };
        return aliases.contains(name.strip().toLowerCase(Locale.ROOT));
    }
    private static MessageKey key(DyeColor color) {
        return switch (color) {
            case WHITE -> MessageKey.TEAM_NAME_WHITE;
            case ORANGE -> MessageKey.TEAM_NAME_ORANGE;
            case MAGENTA -> MessageKey.TEAM_NAME_MAGENTA;
            case LIGHT_BLUE -> MessageKey.TEAM_NAME_LIGHT_BLUE;
            case YELLOW -> MessageKey.TEAM_NAME_YELLOW;
            case LIME -> MessageKey.TEAM_NAME_LIME;
            case PINK -> MessageKey.TEAM_NAME_PINK;
            case GRAY -> MessageKey.TEAM_NAME_GRAY;
            case LIGHT_GRAY -> MessageKey.TEAM_NAME_LIGHT_GRAY;
            case CYAN -> MessageKey.TEAM_NAME_CYAN;
            case PURPLE -> MessageKey.TEAM_NAME_PURPLE;
            case BLUE -> MessageKey.TEAM_NAME_BLUE;
            case BROWN -> MessageKey.TEAM_NAME_BROWN;
            case GREEN -> MessageKey.TEAM_NAME_GREEN;
            case RED -> MessageKey.TEAM_NAME_RED;
            case BLACK -> MessageKey.TEAM_NAME_BLACK;
        };
    }
}
