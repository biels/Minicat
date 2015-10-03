package com.biel.FastSurvival.SpecialItems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class SpecialItemsListener implements Listener {
	@EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent evt) {
		Player p = evt.getPlayer();
		
	}
}
