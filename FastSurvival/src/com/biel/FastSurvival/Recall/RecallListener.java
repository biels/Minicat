package com.biel.FastSurvival.Recall;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.biel.FastSurvival.FastSurvival;

public class RecallListener implements Listener {
//Link
//Teleport
//Exemple - Material.WATCH
	/* Recall - Linked
	 * X: 605, Y: 72, Z: 562
	 * Cast: 5s, Idle: 12s
	 */
	/* Recall - Unlinked
	 * Right click to link
	 * Cast: 5s, Idle: 12s
	 */
	@EventHandler
    public void onInteract(PlayerInteractEvent evt) {
		Player p = evt.getPlayer();
		ItemStack i = p.getItemInHand();
		Inventory inv = p.getInventory();
		if(!RecallUtils.isValidRecallItem(i)){return;}
		//Link
		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			Block b = evt.getClickedBlock();
			
			RecallUtils.playerLinkItemInHand(p, b.getLocation());
			
		}
		if (evt.getAction() == Action.LEFT_CLICK_AIR || evt.getAction() == Action.LEFT_CLICK_BLOCK){
			//p.sendMessage("Recall!");
			RecallUtils.playerRecallClickItemInHand(p);
		}
		evt.setCancelled(true);
	}
	//SAFE
	@EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent evt) {
		if (RecallUtils.isRecallAnimItem(evt.getItem().getItemStack())){
			evt.setCancelled(true);
			evt.getItem().remove();
		}
	}
	@EventHandler
    public void onInventoryPickupItem(InventoryPickupItemEvent evt) {
		if (RecallUtils.isRecallAnimItem(evt.getItem().getItemStack())){
			evt.setCancelled(true);
			evt.getItem().remove();
		}
	}
	@EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent evt) {
//		for (ItemStack stack : evt.getInventory().getMatrix()){
//			if (RecallUtils.isRecallAnimItem(stack)){
//				evt.get_-----------
//				evt.getItem().remove();
//			}
//		}
	}
}
