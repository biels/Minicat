package com.biel.lobby;

import org.bukkit.GameRules;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

/** Applies the server-wide death-screen policy to every loaded world. */
public final class ImmediateRespawn implements Listener {
    public static void configure(World world) {
        world.setGameRule(GameRules.IMMEDIATE_RESPAWN, true);
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        configure(event.getWorld());
    }
}
