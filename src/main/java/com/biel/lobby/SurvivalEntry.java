package com.biel.lobby;

import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/** Requests the proxy's survival destination; its account admission decides access. */
public final class SurvivalEntry {
    private static final String CHANNEL = "BungeeCord";
    private final Plugin plugin;

    public SurvivalEntry(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, CHANNEL);
    }

    public void connect(Player player) {
        var message = ByteStreams.newDataOutput();
        message.writeUTF("Connect");
        message.writeUTF("survival");
        player.sendPluginMessage(plugin, CHANNEL, message.toByteArray());
    }
}
