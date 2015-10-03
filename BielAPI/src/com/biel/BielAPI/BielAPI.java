package com.biel.BielAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.biel.BielAPI.events.EventBusManager;
import com.biel.BielAPI.events.GeneralListener;

public class BielAPI extends JavaPlugin {
	int version = 1;
	public EventBusManager evtgest;
	public GeneralListener generalListener;
	@Override
	public void onEnable() {
		generalListener = new GeneralListener();
		System.out.println("Listener initialized");
		evtgest = new EventBusManager();
		System.out.println("EventBus initialized");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("BielAPI")){
			sender.sendMessage("API v" + version);
			if(args[0].equalsIgnoreCase("stats")){
				sender.sendMessage(evtgest.getStats());
			}
			return true;
		}
		return false;
	}
}
