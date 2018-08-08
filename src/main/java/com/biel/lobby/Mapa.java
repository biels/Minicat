package com.biel.lobby;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.biel.BielAPI.events.WorldEventBus;
import com.biel.lobby.mapes.MapaContinu;
import com.biel.lobby.mapes.MapaResetejable;

public abstract class Mapa extends WorldEventBus{

	public lobby plugin;

	public String NomWorld = "";
	String OriginalMapName = "";
	protected World world;
	int BukkitWorldId;

	public Mapa() {
		super();
		this.plugin = lobby.getPlugin();
		if(isWorldLoaded())setWorld(getWorld());
	}

	public  abstract  String getGameName();
	
	protected Boolean isWorldLoaded(){
        return Bukkit.getWorld(NomWorld) != null;
	}
	
	public String getGameDisplayName() {
		return ChatColor.GOLD + "[" + ChatColor.AQUA + getGameName() + ChatColor.GOLD + "] " + ChatColor.GRAY;
	}

	public String getMapName() {
		return NomWorld;
	}

	public String getMapDisplayName() {
		return ChatColor.GOLD + "[" + ChatColor.AQUA + getMapName() + ChatColor.GOLD + "] " + ChatColor.GRAY;
	}

	public void Join(Player ply){

		ply.teleport(world.getSpawnLocation(), TeleportCause.PLUGIN);
		ply.setBedSpawnLocation(world.getSpawnLocation(), true);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			
			if (lobby.isOnLobby(p)) {
				
				p.sendMessage(ChatColor.GRAY + ply.getName() + " ha entrat a " + getGameName() + " (" + NomWorld + ")");
			}
			
		}
		ply.getInventory().clear();


		Com.setSuffix(ply, "");
		Com.setHeadColor(ply, ChatColor.GRAY);
		customJoin(ply);
	}

	protected abstract void customJoin(Player ply);
	protected abstract void customLeave(Player ply, List<String> attatchments);

	public void Leave(Player ply) {

		List<String> attatchments = new ArrayList<>();
		customLeave(ply, attatchments);
		
		Bukkit.broadcastMessage(getGameDisplayName() + ply.getName() + ChatColor.GRAY + " ha abandonat la partida");
		
	}

	@Override
	protected void onPlayerChangedWorld(PlayerChangedWorldEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerChangedWorld(evt, p);
	}

	@Override
	protected void onPlayerTeleport(PlayerTeleportEvent evt, Player p, Location from, Location to, TeleportCause c) {

		super.onPlayerTeleport(evt, p, from, to, c);
		if(from.getWorld() != to.getWorld()){
			if(from.getWorld() == getWorld()){
				Leave(p);
			}
		}
	}

	@Override
	protected void onPlayerQuit(PlayerQuitEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerQuit(evt, p);
		if(p.getWorld() == getWorld())Leave(p);
	}

	public World getWorld(){
		return world;
	}

	public void sendGlobalMessage(String message){
		for (Player p : world.getPlayers()){
			p.sendMessage(message);
		}
	}
	
		
 	public void sendGlobalSound(Sound sound, float volume, float pitch){
 		for (Player p : world.getPlayers()){
 			p.playSound(p.getLocation(), sound, volume, pitch);
 		}
 	}
	
	public void sendPlayerMessage(Player p, String message) {		
		p.sendMessage(message);
	}
	enum TipusMapa{Continu, Resetejable};

	public TipusMapa getTipusMapa() {

		if (this instanceof MapaResetejable){
			return TipusMapa.Resetejable;
		}
		if(this instanceof MapaContinu){
			return TipusMapa.Continu;
		}

		Bukkit.broadcastMessage("El mapa és de tipus indeterminat");
		return null;

	}

	public void copyDirectory(File sourceLocation , File targetLocation) throws IOException {

		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (String aChildren : children) {
				copyDirectory(new File(sourceLocation, aChildren),
						new File(targetLocation, aChildren));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}

	}
	public static void deleteFolder(File folder) {

		File[] files = folder.listFiles();
		if(files!=null) { //some JVMs return null for empty dirs
			for(File f: files) {
				if(f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();

	}

}