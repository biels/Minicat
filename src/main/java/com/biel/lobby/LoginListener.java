package com.biel.lobby;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.PlayerTagState;

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
	public void onPlayerQuit(PlayerQuitEvent event) {
		PlayerTagState.remove(event.getPlayer());
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
			plugin.getLogger().log(java.util.logging.Level.WARNING, "Error carregant el rànquing", e);
			evt.setMotd(ChatColor.GREEN + "Carregant rànquing...");
		}

	}

	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent evt) {

		String msg = evt.getMessage();
		Player ply = evt.getPlayer();

		 for (int i = 15; i > 0; i--) {
		 	msg = msg.replaceAll("la" + StringUtils.repeat("g", i), "grei" + StringUtils.repeat("x", i));
		 }

		 msg = msg.replaceAll("llagosta", "la greixosta");
		 msg = msg.replaceAll("[Ll]+[Aa]+[Gg]+", "greix");
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

		evt.setCancelled(true);
		String filteredMessage = msg;
		Bukkit.getScheduler().runTask(plugin, () -> broadcastChat(ply, filteredMessage));

	}

	private void broadcastChat(Player player, String filteredMessage) {
		if (!player.isOnline()) return;

		boolean onLobby = lobby.isOnLobby(player);
		String message = filteredMessage;
		if (containsFilteredWord(message)) {
			if (onLobby) {
				if (Utils.Possibilitat(60)) message = "quin server més guai!!";
				if (Utils.Possibilitat(60)) message = "com mola el server!";
				if (Utils.Possibilitat(10)) message = "sou els millors!!";
			} else {
				message = "bona partida! ;)";
				if (Utils.Possibilitat(40)) message = "bona partida!! :D";
			}
			if (Utils.Possibilitat(5)) message = "ehem.. anava a dir... millor callo xD";
			if (Utils.Possibilitat(8)) message = "ja començo a perdre els papers, no em feu gaire cas jaja";
			if (Utils.Possibilitat(3)) message = "lluiscab we love u";
		}

		Mapa map = plugin.gest.getMapWherePlayerIs(player);
		String zone = onLobby
				? ChatColor.GOLD + "[" + ChatColor.AQUA + "Lobby" + ChatColor.GOLD + "] " + ChatColor.GRAY
				: map != null ? map.getMapDisplayName() + ChatColor.RESET : ChatColor.DARK_GRAY + "[Sense zona] ";
		String playerName = (onLobby ? ChatColor.GRAY : PlayerTagState.getPrefix(player)) + player.getDisplayName();
		Bukkit.broadcastMessage(zone + ChatColor.GRAY + playerName + ChatColor.GRAY + ": " + message);
	}

	private boolean containsFilteredWord(String message) {
		return message.contains("inves")
				|| message.contains("polla")
				|| message.contains("gilip")
				|| message.contains("tont")
				|| message.contains("retr")
				|| message.contains("retard");
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
