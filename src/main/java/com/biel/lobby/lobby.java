package com.biel.lobby;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.MapaResetejable;
import com.biel.lobby.agent.AgentSnapshotHttpServer;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.HologramFacade;
import com.biel.lobby.utilities.Options;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.DataAPI;
import com.biel.lobby.utilities.data.PlayerData;

public final class lobby extends JavaPlugin {
	boolean ranked = true;
	public GestorMapes gest;
	public DataAPI dataAPI;
	private AgentSnapshotHttpServer agentSnapshotHttpServer;
	@SuppressWarnings("unused")
	@Override
	public void onEnable(){
		// TODO Insert logic to be performed when the plugin is enabled
		new LoginListener();

		getLobbyWorld().setAutoSave(true);
		MapaResetejable.cleanupStaleRuntimeWorlds();

		gest = new GestorMapes();
		agentSnapshotHttpServer = new AgentSnapshotHttpServer(this);
		agentSnapshotHttpServer.start();
		dataAPI = new DataAPI();
	}

	@Override
	public void onDisable() {
		if (agentSnapshotHttpServer != null) {
			agentSnapshotHttpServer.stop();
			agentSnapshotHttpServer = null;
		}
		if (dataAPI != null) dataAPI.closeConnection();
		HologramFacade.deleteAll();
	}
	public AgentSnapshotHttpServer getAgentSnapshotHttpServer() {
		return agentSnapshotHttpServer;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("prova")){
			if(sender instanceof Player && !sender.isOp()){
				sender.sendMessage(ChatColor.RED + "Aquesta ordre requereix permisos d'operador.");
				return true;
			}
			if(args.length == 1 && args[0].equalsIgnoreCase("llista")){
				if (gest.getAllGameInstances().isEmpty()) {
					sender.sendMessage("No hi ha instàncies de joc actives.");
				} else {
					sender.sendMessage(ChatColor.GOLD + "Instàncies de joc actives:");
					for (Joc game : gest.getAllGameInstances()) {
						sender.sendMessage("- " + game.getGameName() + " / " + game.getMapName()
								+ " (jugadors=" + game.getPlayers().size() + ", edició=" + game.getEditMode() + ")");
					}
				}
				return true;
			}
			if(args.length >= 2 && args[0].equalsIgnoreCase("elimina")){
				String worldName = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
				GestorMapes.InstanceRemovalResult result = gest.removeGameInstance(worldName);
				switch (result) {
					case REMOVED:
						sender.sendMessage(ChatColor.GREEN + "Instància descarregada; els fitxers temporals s'eliminaran en 10 segons.");
						break;
					case NOT_FOUND:
						sender.sendMessage(ChatColor.RED + "No hi ha cap instància registrada amb aquest nom de món.");
						break;
					case HAS_PLAYERS:
						sender.sendMessage(ChatColor.RED + "No es pot eliminar una instància amb jugadors.");
						break;
					case EDIT_MODE:
						sender.sendMessage(ChatColor.RED + "No es pot eliminar una instància en mode d'edició.");
						break;
					case UNLOAD_FAILED:
						sender.sendMessage(ChatColor.RED + "Paper no ha pogut descarregar el món; no s'ha eliminat la instància.");
						break;
				}
				return true;
			}
			if(args.length < 1 || args.length > 2){
				sender.sendMessage("Ús: /prova <joc> [variant] | /prova llista | /prova elimina <món>");
				return true;
			}
			Integer mapId = null;
			if(args.length == 2){
				try {
					mapId = Integer.valueOf(args[1]);
				} catch (NumberFormatException exception) {
					sender.sendMessage(ChatColor.RED + "La variant ha de ser un número.");
					return true;
				}
			}
			Joc createdGame = gest.createGameInstance(args[0], mapId);
			if(createdGame == null){
				sender.sendMessage(ChatColor.RED + "No s'ha pogut crear el joc " + args[0] + ".");
			} else {
				sender.sendMessage(ChatColor.GREEN + "Instància creada: " + createdGame.getGameName() + " / " + createdGame.getMapName());
			}
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("Aquesta ordre només es pot executar com a jugador.");
			return true;
		}
		Player ply = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("minicatjoin")){
			if(args.length == 0){
				ply.sendMessage(ChatColor.RED + "Falta el nom de la instància.");
				return true;
			}
			String worldName = String.join(" ", args);
			GestorMapes.InstanceJoinResult result = gest.joinGameInstance(ply, worldName);
			switch (result) {
				case JOINED:
					break;
				case NOT_FOUND:
					ply.sendMessage(ChatColor.RED + "Aquesta instància ja no existeix.");
					break;
				case UNAVAILABLE:
					ply.sendMessage(ChatColor.RED + "Aquesta instància ja no admet jugadors.");
					break;
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("m")){

			gest.ObrirMenuMapes(ply);
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("r")){

			ranked = !ranked;
			Bukkit.broadcastMessage("Transferència d'elo " + (ranked ? "activada" : "desactivada"));
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("a")){
			gest.openAllGamesMenu(ply);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("o")){
			Options.giveCommonOptionsMenu(ply);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("l")){
			if (args.length == 1){
				if(args[0] == "@" && ply.isOp()){
					Bukkit.getOnlinePlayers().forEach(Com::teleportPlayerToLobby);
					Bukkit.broadcastMessage(ChatColor.GRAY + "Tots els jugadors han estat transportats al lobby");
					return true;
				}
				Player player = Bukkit.getPlayer(args[0]);
				if (player == null) {
					ply.sendMessage("No es troba el jugador.");
					return false;
				}
				if (ply.isOp()) {
					Com.teleportPlayerToLobby(player);
					player.sendMessage(ChatColor.GRAY + "Has estat transportat al lobby per "
							+ ply.getName());
				}
			}	
			if (args.length == 0){
				Com.teleportPlayerToLobby(ply);
			}

			return true;
		}
		if(cmd.getName().equalsIgnoreCase("p")){
			GestorPropietats pMapaActual = Utils.getpMapaFromWorld(ply.getWorld());

			if (ply.isOp()){
				if (args.length == 2){
					pMapaActual.EstablirPropietat(args [0], args [1]);	    				
				}
				if (args.length == 1){
					Location loc = ply.getLocation().getBlock().getLocation();
					loc.setY(loc.getY() - 1);
					loc.setZ(loc.getZ());
					loc.setX(loc.getX());
					pMapaActual.EstablirLocation(args[0], loc);	    				
				}	
				String p = pMapaActual.ObtenirPropietat(args[0]);
				if (p != null){
					Bukkit.broadcastMessage(ply.getWorld().getName() + ": " + ChatColor.YELLOW + args[0] + "=" + ChatColor.GREEN + p);
					return true;
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("e")){
			if (ply.isOp()){
				Mapa mapWherePlayerIs = gest.getMapWherePlayerIs(ply);
				if (mapWherePlayerIs instanceof MapaResetejable){
					MapaResetejable m = (MapaResetejable) mapWherePlayerIs;
					m.setEditMode(!m.getEditMode());
					if (m.getEditMode()){
						ply.setGameMode(GameMode.CREATIVE);
					}
					return true;
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("s")){
			if (ply.isOp()){
				Mapa mapWherePlayerIs = gest.getMapWherePlayerIs(ply);
				if (mapWherePlayerIs instanceof MapaResetejable){
					MapaResetejable m = (MapaResetejable) mapWherePlayerIs;
					m.save();
					return true;
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("elo")){
			PlayerData playerData = new PlayerData(ply.getName());
			if(playerData.getRank() == -1){
				ply.sendMessage(ChatColor.GOLD + "Fes partides per determinar la teva posició");
				return true;
			}
			ply.sendMessage(ChatColor.DARK_AQUA + "Elo: " + ChatColor.WHITE + Math.round(playerData.getElo()) + ChatColor.YELLOW + " #" + playerData.getRank()); 
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("top") || cmd.getName().equalsIgnoreCase("ranking")){
			Com.displayRanking(ply);
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("prog")){
			Mapa m = Com.getGest().getMapWherePlayerIs(ply);
			if(m != null){
				if(m instanceof Joc){
					Joc joc = (Joc) m;
					ply.sendMessage("Progrés estimat: " + Math.round(joc.getGameProgressETA() * 10000) / 100 + "%");
				}else{
					ply.sendMessage("Has d'estar en un joc per fer això");
				}
			}else{
				ply.sendMessage("Has d'estar en una partida per fer això");
			}
			return true;
		}
		return false;
	}
	static public lobby getPlugin() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("lobby");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof lobby)) {
			return null; // Maybe you want throw an exception instead
		}

		return (lobby) plugin;
	}
	static public World getLobbyWorld(){
		return Bukkit.getWorlds().get(0);
	}
	public static Boolean isOnLobby(Player ply){
		return getLobbyWorld().getPlayers().contains(ply);
	}
	public boolean isInRankedMode(){
		return ranked;
	}
}
