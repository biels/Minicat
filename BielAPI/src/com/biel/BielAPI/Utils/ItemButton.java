package com.biel.BielAPI.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.biel.BielAPI.BielAPI;
import com.biel.BielAPI.Com;

public class ItemButton implements Listener {
	private static ArrayList<ItemButton> instances = new ArrayList<ItemButton>();
	
	private OptionClickEventHandler handler;
	private Plugin plugin;

	private ItemStack itemStack;
	private String pName;
	private Object data;
	
	public ItemButton(ItemStack item, Player ply, OptionClickEventHandler handler) {
		this.handler = handler;
		this.pName = ply.getName();
		this.itemStack = item;
		this.plugin = Com.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		//------------------
		instances.add(this);
	}
	public Boolean isCurrentStack(ItemStack stack){
		if (itemStack == null || stack == null){return false;}
		return (stack.hashCode() == itemStack.hashCode());
	}
		
	public Player getPlayer(){
		return Bukkit.getPlayer(pName);
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void destroy() {
		HandlerList.unregisterAll(this);
		handler = null;
		plugin = null;
		
	}
	public static void clearButtons(Player ply){
		ArrayList<ItemButton> instancesToRemove = new ArrayList<ItemButton>();
		for (ItemButton but : instances){
			if (but.getPlayer() == ply){
				instancesToRemove.add(but);
			}
		}
		for (ItemButton but : instancesToRemove){
			if (but.getPlayer() == ply){
				but.destroy();
				instances.remove(but);
			}
		}
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	@EventHandler(priority=EventPriority.MONITOR)
	void onInteract(PlayerInteractEvent evt) {
		if (!isCurrentStack(evt.getItem())){return;}
		if (!pName.equals(evt.getPlayer().getName())){return;}
		if(evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			Plugin plugin = this.plugin;
			OptionClickEvent e = new OptionClickEvent(evt.getPlayer(), evt);
			e.setData(data);
			if (handler != null) {
				handler.onOptionClick(e);
			}
			evt.setCancelled(true);
		}
	}

	public interface OptionClickEventHandler {
		public void onOptionClick(OptionClickEvent event);
	}

	public class OptionClickEvent {
		private Player player;
		private Object data;
		private PlayerInteractEvent evt;
		public OptionClickEvent(Player player, PlayerInteractEvent evt) {
			this.player = player;
			this.evt = evt;
		}

		public Player getPlayer() {
			return player;
		}
		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public PlayerInteractEvent getOnInteractEvent(){
			return evt;
		}

		
	}

	private static ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}

}