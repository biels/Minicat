package com.biel.lobby.utilities.data;

import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.entity.Player;
import java.util.Collection;

/** Properties are trusted only after Paper verifies Velocity modern forwarding. */
public record ForwardedAccount(int playerId, boolean operator) {
    public static boolean required() {
        return Boolean.getBoolean("minicat.accounts.required");
    }

    public static ForwardedAccount of(Player player) {
        return fromProperties(player.getPlayerProfile().getProperties());
    }

    public static ForwardedAccount fromProperties(Collection<ProfileProperty> properties) {
        String playerId = null;
        String operator = null;
        for (ProfileProperty property : properties) {
            if (property.getName().equals("minicat:player_id")) {
                if (playerId != null) throw new IllegalArgumentException("Duplicate forwarded player id");
                playerId = property.getValue();
            }
            if (property.getName().equals("minicat:operator")) {
                if (operator != null) throw new IllegalArgumentException("Duplicate forwarded operator flag");
                operator = property.getValue();
            }
        }
        if (playerId == null || !playerId.matches("[1-9][0-9]{0,9}") ||
                !("true".equals(operator) || "false".equals(operator))) {
            throw new IllegalArgumentException("Missing or malformed forwarded account");
        }
        int parsedPlayerId = Integer.parseInt(playerId);
        if (parsedPlayerId <= 0) throw new IllegalArgumentException("Invalid forwarded player id");
        return new ForwardedAccount(parsedPlayerId, Boolean.parseBoolean(operator));
    }

    /** Refuse an unsafe partial activation before the server accepts any players. */
    public static void verifyForwardingConfiguration() {
        if (!required()) return;
        var configuration = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(
                new java.io.File("config/paper-global.yml"));
        if (!configuration.getBoolean("proxies.velocity.enabled") ||
                configuration.getString("proxies.velocity.secret", "").isBlank()) {
            throw new IllegalStateException("Account admission requires Paper Velocity modern forwarding and its secret");
        }
    }
}
