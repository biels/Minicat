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
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Options;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.DataAPI;
import com.biel.lobby.utilities.data.PlayerData;

public final class lobby extends JavaPlugin {

	boolean ranked = true;
	public GestorMapes gest;
	public DataAPI dataAPI;

	@SuppressWarnings("unused")
	@Override

	public void onEnable() {

		new LoginListener();

		getLobbyWorld().setAutoSave(true);

		gest = new GestorMapes();
		dataAPI = new DataAPI();

    }

	@Override
	public void onDisable() { }

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

		Player ply = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("m")) {

			gest.ObrirMenuMapes(ply);
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("r")) {

			ranked = !ranked;
			Bukkit.broadcastMessage("Transferència d'elo " + (ranked ? "activada" : "desactivada"));
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("a")) {
			gest.openAllGamesMenu(ply);
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("o")) {
			Options.giveCommonOptionsMenu(ply);
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("l")) {

			if (args.length == 1) {
				if(args[0].equals("@") && ply.isOp()){
					Bukkit.getOnlinePlayers().forEach(Com::teleportPlayerToLobby);
					Bukkit.broadcastMessage(ChatColor.GRAY + "Tots els jugadors han estat transportats al lobby");
					return true;
				}

				Player player = Bukkit.getPlayer(args[0]);
				if (player == null) {
					ply.sendMessage("Us: /l <jugador>");
					return true;
				}

				if (ply.isOp()) {
					Com.teleportPlayerToLobby(player);
					player.sendMessage(ChatColor.GRAY + "Has estat transportat al lobby per " + ply.getName());
				}
			}

			if (args.length == 0){
				Com.teleportPlayerToLobby(ply);
			}

			return true;

		}

		if(cmd.getName().equalsIgnoreCase("p")) {

			GestorPropietats pMapaActual = Utils.getpMapaFromWorld(ply.getWorld());

			if(!ply.isOp()) {
			    ply.sendMessage(ChatColor.RED + "  Permis denegat!");
			    return true;
            }

            if (args.length == 2) {
                pMapaActual.EstablirPropietat(args [0], args [1]);
            }

            if (args.length == 1) {
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

		if(cmd.getName().equalsIgnoreCase("e")){

            if(!ply.isOp()) {
                ply.sendMessage(ChatColor.RED + "  Permis denegat!");
                return true;
            }

            Mapa mapWherePlayerIs = gest.getMapWherePlayerIs(ply);

            if (mapWherePlayerIs instanceof MapaResetejable){

                MapaResetejable m = (MapaResetejable) mapWherePlayerIs;
                m.setEditMode(!m.getEditMode());

                if (m.getEditMode()) {
                    ply.setGameMode(GameMode.CREATIVE);
                    ply.sendMessage("Mode d'edicio activat");
                }

                return true;
            }


		}

		if(cmd.getName().equalsIgnoreCase("s")){

            if(!ply.isOp()) {
                ply.sendMessage(ChatColor.RED + "  Permis denegat!");
                return true;
            }

            Mapa mapWherePlayerIs = gest.getMapWherePlayerIs(ply);

            if (mapWherePlayerIs instanceof MapaResetejable){
                MapaResetejable m = (MapaResetejable) mapWherePlayerIs;
                m.save();
                return true;
            }

		}

		if(cmd.getName().equalsIgnoreCase("elo")) {

			PlayerData playerData = new PlayerData(ply.getName());

			if(playerData.getRank() == -1) {
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

		if(cmd.getName().equalsIgnoreCase("prog")) {

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

		if (!(plugin instanceof lobby)) {
			return null;
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