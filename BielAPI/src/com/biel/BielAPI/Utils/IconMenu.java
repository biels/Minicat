package com.biel.BielAPI.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.biel.BielAPI.Com;
import com.biel.BielAPI.events.*;

public class IconMenu extends EventBus{

	private String name;
	private int size;
	private OptionClickEventHandler handler;
	private Plugin plugin;

	private String[] optionNames;
	private ItemStack[] optionIcons;

	public IconMenu(String name, int size, OptionClickEventHandler handler) {
		//super(p);
		this.name = name + ChatColor.GRAY + GUtils.NombreEntre(0, 1000);
		this.size = size;
		this.handler = handler;
		this.plugin = Com.getPlugin();
		this.optionNames = new String[size];
		this.optionIcons = new ItemStack[size];
		//plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
		optionNames[position] = name;
		optionIcons[position] = setItemNameAndLore(icon, name, info);
		return this;
	}
	
	public IconMenu setOption(int position, ItemStack icon, String name, ArrayList<String> info) {
		optionNames[position] = name;
		optionIcons[position] = setItemNameAndLore(icon, name, info.toArray(new String[info.size()]));
		return this;
	}

	public void open(Player player) {
		Inventory inventory = Bukkit.createInventory(player, size, name);
		for (int i = 0; i < optionIcons.length; i++) {
			if (optionIcons != null) {
				inventory.setItem(i, optionIcons[i]);
			}
		}
		player.openInventory(inventory);
	}

	public void destroy() {
		//Bukkit.broadcastMessage("Destroyed: " + name);
		destroyEventBus();
		handler = null;
		plugin = null;
		optionNames = null;
		optionIcons = null;
	}
	public boolean isThisOne(Inventory inventory, InventoryHolder h) {
		return inventory.getTitle().equals(name) && inventory.getHolder() == h;
	}
	@Override
	protected void onInventoryClose(InventoryCloseEvent evt, Inventory inv) {
		// TODO Auto-generated method stub
		super.onInventoryClose(evt, inv);
		Inventory inventory = evt.getInventory();
		if (isThisOne(inventory, evt.getPlayer())) {
			destroy();
			//TODO GET THIS OUT https://hub.spigotmc.org/jira/browse/SPIGOT-943
		}
	}
	@Override
	protected void onInventoryClick(InventoryClickEvent evt, Inventory inv) {
		// TODO Auto-generated method stub
		super.onInventoryClick(evt, inv);
		if (isThisOne(evt.getInventory(), evt.getWhoClicked())) {
			evt.setCancelled(true);
			int slot = evt.getRawSlot();
			if (optionNames != null && slot >= 0 && slot < size && optionNames[slot] != null) {
				Plugin plugin = this.plugin;
				OptionClickEvent e = new OptionClickEvent((Player)evt.getWhoClicked(), slot, optionNames[slot], this);
				handler.onOptionClick(e);
				if (e.willClose()) {
					final Player p = (Player)evt.getWhoClicked();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							p.closeInventory();
						}
					}, 1);
				}
				if (e.willDestroy()) {
					destroy();
				}
			}
		}
	}


	public interface OptionClickEventHandler {
		public void onOptionClick(OptionClickEvent event);
	}

	public class OptionClickEvent {
		private Player player;
		private int position;
		private String name;
		private boolean close;
		private boolean destroy;
		private IconMenu menu;
		public OptionClickEvent(Player player, int position, String name, IconMenu menu) {
			this.player = player;
			this.position = position;
			this.name = name;
			this.close = true;
			this.destroy = false;
			this.menu = menu;
		}

		public Player getPlayer() {
			return player;
		}

		public int getPosition() {
			return position;
		}

		public String getName() {
			return name;
		}

		public boolean willClose() {
			return close;
		}

		public boolean willDestroy() {
			return destroy;
		}

		public void setWillClose(boolean close) {
			this.close = close;
		}

		public void setWillDestroy(boolean destroy) {
			this.destroy = destroy;
		}
		public IconMenu getMenu(){
			return menu;
		}
	}

	private static ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getOptionNames() {
		return optionNames;
	}

	public void setOptionNames(String[] optionNames) {
		this.optionNames = optionNames;
	}

	public ItemStack[] getOptionIcons() {
		return optionIcons;
	}

	public void setOptionIcons(ItemStack[] optionIcons) {
		this.optionIcons = optionIcons;
	}

}
