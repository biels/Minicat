package com.biel.FastSurvival.BuilderWand;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BuilderWandListener implements Listener {
	@EventHandler
    public void onInteract(PlayerInteractEvent evt) {
		Action a = evt.getAction();
		Player ply = evt.getPlayer();
		if (a == Action.RIGHT_CLICK_BLOCK){
			if (BuilderWandUtils.isWandItem(ply.getItemInHand())){
				BuilderWandUtils.onWandUse(evt.getClickedBlock(), evt.getBlockFace(), ply);
				evt.setCancelled(true);
			}
		}
	}
		
}
