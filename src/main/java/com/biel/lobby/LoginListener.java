package com.biel.lobby;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.biel.lobby.utilities.Utils;

public class LoginListener implements Listener {
	public lobby plugin;

	public LoginListener() {
		this.plugin = lobby.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getLogger().info("Listener created!");

	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		
		Com.getDataAPI().registerNewPlayer(player);
		Com.teleportPlayerToLobby(player);

		player.setCanPickupItems(true);
		
	}
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent evt) {
		evt.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent evt) {
		if (evt.getPlayer() != null){
			Player ply = evt.getPlayer();
			if(lobby.isOnLobby(ply)){

				evt.setCancelled(true);
			}

		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent evt) {
		if (evt.getPlayer() != null){
			Player ply = evt.getPlayer();
			if(lobby.isOnLobby(ply)){

				evt.setCancelled(true);
			}

		}
	}
	@EventHandler
	public void onPing(ServerListPingEvent evt) {

		int games = lobby.getPlugin().gest.getAllInstances().size();

		if(Com.getDataAPI().isInDatalessMode()){
			evt.setMotd(ChatColor.GOLD + "Minicat " + ChatColor.RED + "[dev mode]");
			return;
		}

		try {
			int num = 5;
			while(Com.getRankingString(num + 1).length() <= 140 && num <= 10){
				num++;
			}
			String lastMotd = Com.getRankingString(num);
			evt.setMotd(lastMotd);
		} catch (Exception e) {
			System.out.println("Error en carrgear el rànquing: " + e.toString());
			evt.setMotd(ChatColor.GREEN + "Carregant rànquing...");
		}

	}

	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent evt) {

		String msg = evt.getMessage();
		if(msg.contains("ch")) return;

		if (msg=="llagosta") msg = msg.replaceAll("llagosta", "la greixosta");
		else msg = msg.replaceAll("[Ll]{1,}[Aa]{1,}[Gg]{1,}", "greix");
		for (int i = 15; i > 0; i--) {
			msg = msg.replaceAll("la" + StringUtils.repeat("g", i), "grei" + StringUtils.repeat("x", i));			
		}

		msg = msg.replaceAll("l.a.g", "g.r.e.i.x");
		msg = msg.replaceAll("l a g", "g r e i x");
		msg = msg.replaceAll("l ag ", "gr eix ");
		msg = msg.replaceAll("l ag", "gr eix");
		msg = msg.replaceAll("ping", "ping pong");
		msg = msg.replaceAll("bug", "escarbat");
		msg = msg.replaceAll("en fi", "en fi (copyright JoniMega)");
		msg = msg.replaceAll("en fi", "En fi (copyright JoniMega)");
		msg = msg.replaceAll("Enfi", "En fi (copyright JoniMega)");

		evt.setMessage(msg);

		if(msg.contains("inves") || msg.contains("polla") || msg.contains("gilip") || msg.contains("tont") || msg.contains("retr") || msg.contains("retard")){
			if(Com.isOnLobby(evt.getPlayer())){
				if(Utils.Possibilitat(60))evt.setMessage("quin server més guai!!");
				if(Utils.Possibilitat(60))evt.setMessage("com mola el server!");
				if(Utils.Possibilitat(10))evt.setMessage("sou els millors!!");
			} else {
				evt.setMessage("bona partida! ;)");
				if(Utils.Possibilitat(40))evt.setMessage("bona partida!! :D");
			}
			if(Utils.Possibilitat(5)) evt.setMessage("ehem.. anava a dir... millor callo xD");
			if(Utils.Possibilitat(8)) evt.setMessage("ja començo a perdre els papers, no em feu gaire cas jaja");
			if(Utils.Possibilitat(3)) evt.setMessage("lluiscab we love u");

		}

	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		if (evt.getTo().getBlockY() < 60) {
			Player ply = evt.getPlayer();
			if (lobby.isOnLobby(ply)) {
				Com.teleportPlayerToLobby(ply);
			} 
		}
	}
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent evt) {

		if (evt.getEntity() instanceof Player) {

			Player ply = (Player) evt.getEntity();
			if(lobby.isOnLobby(ply)) {

				if (evt.getCause() == DamageCause.VOID){
					Com.teleportPlayerToLobby(ply);
				}

				evt.setCancelled(true);
			}

		}

	}

}