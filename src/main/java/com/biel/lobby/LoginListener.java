package com.biel.lobby;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.biel.BielAPI.Utils.GUtils;
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
		if(name.contains("lucas") || name.contains("pecas") || name.contains("pecas")){
			return;
		}
		if(name.contains("amiguet") || name.equalsIgnoreCase("amiguet")){
			World w = lobby.getLobbyWorld();			
			Slime s = (Slime) w.spawnEntity(w.getSpawnLocation().add(0, 2, 0), EntityType.SLIME);
			String pilofrase = GUtils.getRandomListItem(getPilofrases());
			Com.sendLobbyMessage(pilofrase);
			s.setCustomName("Pilota");
			s.setCustomNameVisible(true);
			s.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * Utils.NombreEntre(1, 9), 2));
			player.setPassenger(s);
			//s.setHealth(1);
		}
		Com.teleportPlayerToLobby(player);
		player.sendMessage("Pots fer /l per tornar al lobby en qualsevol moment.");
		player.sendMessage(ChatColor.DARK_RED + "[NOVETAT] " + ChatColor.WHITE + "Sistema de rànquing basat en ELO. Ordres /elo i /top");
		player.sendMessage(ChatColor.DARK_RED + "[NOVETAT] " + ChatColor.WHITE + "Sistema d'espectadors. Entra en una partida iniciada que admeti el mode espectador.");
		player.sendMessage(ChatColor.GOLD + "[NOVETAT] " + ChatColor.WHITE + "Servidor sense lag. Hostejat en línia de fibra òptica de baixa latència i 30Mb/s de velocitat de pujada.");
		player.sendMessage(ChatColor.AQUA + "[TELEGRAM] " + ChatColor.UNDERLINE + "https://telegram.me/servidorminicat");

		//		ply.setAllowFlight(true);
		//		ply.setCanPickupItems(false);
		//		ply.setFlying(true);
		//		if(ply.getName().equalsIgnoreCase("123dani") || ply.getName().equalsIgnoreCase("adria")){
		//			event.setJoinMessage(ChatColor.RED + "" + ChatColor.BOLD + "Un faggot s'ha connectat");
		//		}
		//		if(ply.getName().equalsIgnoreCase("Martinosky")){
		//			event.setJoinMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "El RUC s'ha connectat");
		//		}
		//		if(ply.getName().equalsIgnoreCase("amiguet")){
		//			event.setJoinMessage("La pilota ha rodolat");
		//		}
		////		if(ply.getName().equalsIgnoreCase("_tor3k4_")){
		////			event.setJoinMessage(ChatColor.RED + "" + ChatColor.BOLD + "OC.TC rules (Toni OP)");
		////		}
		//		if(ply.getName().equalsIgnoreCase("BielCAT")){
		//			event.setJoinMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "El creador s'ha connectat!");
		//		}
	}
	public ArrayList<String> getPilofrases(){
		ArrayList<String> l = new ArrayList<>();
		l.add("Piloteta rodoneta");
		l.add("La pizza, la pizza, es crema la pizza");
		l.add("Piloteta boniquetaa");
		l.add("Ma, manassa; pilota, pilotassa");
		l.add("Roll da ball!!");
		l.add("Amigüet");
		return l;
	}
	@EventHandler
	public void autoRespawn(PlayerDeathEvent e)	{
		//		 if ((e.getEntity() instanceof Player)) {
		//	          Player p = e.getEntity();
		//	          p.setHealth(p.getMaxHealth());
		//	          p.teleport(p.getWorld().getSpawnLocation());
		//	        }
		//	}
		//		final Player player = e.getEntity();
		//		Bukkit.getScheduler().scheduleSyncDelayedTask(lobby.getPlugin(), new Runnable(){ public void run() {
		//			if(player.isDead()){
		//				((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
		//			}
		//
		//
		//		}}, 5);
	}
	//	@EventHandler
	//	public void autoRespawn(PlayerDeathEvent e)	{
	//		
	//	}
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {

		//		int food = e.getFoodLevel();
		//
		//		for (Player p : Bukkit.getOnlinePlayers()) {
		//			p.setFoodLevel(food);
		//		}
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
		//evt.setMotd(Integer.toString(games) + " partides en curs");
		try {
			int num = 5;
			while(Com.getRankingString(num + 1).length() <= 140 && num <= 10){
				num++;
			}
			String lastMotd = Com.getRankingString(num);
			evt.setMotd(lastMotd);
		} catch (Exception e) {
			evt.setMotd(ChatColor.GREEN + "Carregant rànquing...");
		}

	}
	@EventHandler
	public void onPlayerChatEvent(PlayerChatEvent evt) {

	}
	@EventHandler
	public void onPlayerChatEvent(AsyncPlayerChatEvent evt) {
		String msg = evt.getMessage();
		if(msg.contains("ch"))return;
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
		msg = msg.replaceAll("en fi", "En fi (copyright JoniMega)");
		msg = msg.replaceAll("En fi", "En fi (copyright JoniMega)");
		msg = msg.replaceAll("Enfi", "En fi (copyright JoniMega)");
		if(evt.getPlayer().getName().contains("amiguet"))msg = msg.replaceAll("calla", "sii soc molt grossa!");
		evt.setMessage(msg);
		boolean off = msg.contains("inves") || msg.contains("polla") || msg.contains("gilip") || msg.contains("tont") || msg.contains("retr") || msg.contains("retard");
		if(off){
			if(Com.isOnLobby(evt.getPlayer())){
				if(Utils.Possibilitat(100))evt.setMessage("quin server més guai!!");
				if(Utils.Possibilitat(60))evt.setMessage("com mola el server!");
				if(Utils.Possibilitat(10))evt.setMessage("sou els millors!!");
			}else{
				evt.setMessage("bona partida! ;)");
				if(Utils.Possibilitat(40))evt.setMessage("bona partida!! :D");
			}
			if(Utils.Possibilitat(5))evt.setMessage("ehem.. anava a dir... millor callo xD");
			if(Utils.Possibilitat(8))evt.setMessage("ja començo a perdre els papers, no em feu gaire cas jaja");
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
		if (evt.getEntity() instanceof Slime){
			if(Utils.Possibilitat(100)){
				//evt.getEntity().teleport(Com.getLobbyWorld().getSpawnLocation().add(0, 3, 0));
			}
		}
		if (evt.getEntity() instanceof Player){
			Player ply = (Player) evt.getEntity();
			if(lobby.isOnLobby(ply)){
				if (evt.getCause() == DamageCause.VOID){
					Com.teleportPlayerToLobby(ply);


				}
				evt.setCancelled(true);
			}



		}

	}

}
