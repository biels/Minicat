package com.biel.lobby.utilities;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class PlayerTagState {
    private static final Map<UUID, TagStyle> TAGS = new ConcurrentHashMap<>();
    private static final TagStyle DEFAULT_STYLE = new TagStyle("", "");

    private PlayerTagState() {
    }

    public static void setPrefix(Player player, String prefix) {
        update(player, style(player).withPrefix(normalizeLegacyColors(prefix)));
    }

    public static void setSuffix(Player player, String suffix) {
        update(player, style(player).withSuffix(normalizeLegacyColors(suffix)));
    }

    public static String getPrefix(Player player) {
        return style(player).prefix();
    }

    public static void remove(Player player) {
        TAGS.remove(player.getUniqueId());
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            Team team = viewer.getScoreboard().getEntryTeam(player.getName());
            if (team == null) {
                continue;
            }
            team.removeEntry(player.getName());
            if (team.getName().startsWith("mt_") && team.getEntries().isEmpty()) {
                team.unregister();
            }
        }
    }

    public static void assignScoreboard(Player viewer, Scoreboard scoreboard) {
        applyAll(scoreboard);
        viewer.setScoreboard(scoreboard);
    }

    public static void applyAll(Scoreboard scoreboard) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            apply(scoreboard, target);
        }
    }

    private static void update(Player player, TagStyle style) {
        TAGS.put(player.getUniqueId(), style);
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            apply(viewer.getScoreboard(), player);
        }
    }

    private static void apply(Scoreboard scoreboard, Player player) {
        TagStyle desiredStyle = style(player);
        Team team = scoreboard.getEntryTeam(player.getName());
        if (team != null && !team.getName().startsWith("mt_")) {
            boolean sharedStyleMatches = team.getEntries().stream()
                    .map(Bukkit::getPlayerExact)
                    .filter(Objects::nonNull)
                    .allMatch(member -> style(member).equals(desiredStyle));
            if (!sharedStyleMatches) {
                team = null;
            }
        }
        if (team == null) {
            String teamName = "mt_" + player.getUniqueId().toString().replace("-", "").substring(0, 12);
            team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.registerNewTeam(teamName);
            }
            team.addEntry(player.getName());
        }
        applyStyle(team, desiredStyle);
    }

    private static void applyStyle(Team team, TagStyle style) {
        team.prefix(PaperMessages.legacy(style.prefix()));
        team.suffix(PaperMessages.legacy(style.suffix()));
        ChatColor color = lastColor(style.prefix());
        team.setColor(color != null ? color : ChatColor.RESET);
    }

    private static ChatColor lastColor(String text) {
        String colors = ChatColor.getLastColors(text);
        for (int index = colors.length() - 1; index >= 0; index--) {
            ChatColor color = ChatColor.getByChar(colors.charAt(index));
            if (color != null && color.isColor()) {
                return color;
            }
        }
        return null;
    }

    private static String normalizeLegacyColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text == null ? "" : text);
    }

    private static TagStyle style(Player player) {
        return TAGS.getOrDefault(player.getUniqueId(), DEFAULT_STYLE);
    }

    private record TagStyle(String prefix, String suffix) {
        private TagStyle {
            prefix = prefix == null ? "" : prefix;
            suffix = suffix == null ? "" : suffix;
        }

        private TagStyle withPrefix(String newPrefix) {
            return new TagStyle(newPrefix, suffix);
        }

        private TagStyle withSuffix(String newSuffix) {
            return new TagStyle(prefix, newSuffix);
        }
    }
}
