package com.biel.FastSurvival.Bows;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BowUtils {
	public enum BowType{TORCH, ENDER, MAGNETIC, EXPLOSIVE, BOUNCY, ICY, WITHER, WATER, MULTI, ELECTRIC}
	static BowType getTypeFromIdString(String s){
		return BowType.values()[Integer.parseInt(s)];
	}
	public static BowType getBowType(ItemStack b){
		String loreid;
		int id;
		if(b.getType() != Material.BOW){return null;}
		try {
			loreid = b.getItemMeta().getLore().get(1);
		} catch (Exception e1) {
			//Bukkit.broadcastMessage("No 1 lore");
			return null;
		}
		try {
			id = Integer.parseInt(loreid);
		} catch (NumberFormatException e) {
			Bukkit.broadcastMessage("N Format");
			return null;
		}
		return getTypeFromIdString(loreid);
	}
}
