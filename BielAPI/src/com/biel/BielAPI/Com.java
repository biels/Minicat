package com.biel.BielAPI;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Com {
//HIO
	static public BielAPI getPlugin() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("BielAPI");
	
		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof BielAPI)) {
			return null; // Maybe you want throw an exception instead
		}
	
		return (BielAPI) plugin;
	}

	

}
