package com.biel.lobby.mapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class JocLastStanding extends Joc{
	/**
	 * Who is still in the round, by name. A Player object in this list went stale the
	 * moment its holder reconnected (a new entity, never equal again), which left a
	 * ghost that could neither be eliminated nor win, so the round never ended.
	 */
	private ArrayList<String> aliveNames = new ArrayList<>();
	public void initAlivePlayers() {
		setAlivePlayers(getPlayers());
	}
	/** The alive players who are online right now. */
	public ArrayList<Player> getAlivePlayers() {
		ArrayList<Player> alive = new ArrayList<>();
		for (String name : aliveNames) {
			Player player = Bukkit.getPlayer(name);
			if (player != null) alive.add(player);
		}
		return alive;
	}
	public List<String> getAliveNames() {
		return aliveNames;
	}

	public void setAlivePlayers(List<Player> alivePlayers) {
		aliveNames = new ArrayList<>();
		for (Player player : alivePlayers) aliveNames.add(player.getName());
	}
	
	public boolean isAlive(Player ply){
		return isAlive(ply.getName());
	}
	public boolean isAlive(String name){
		return aliveNames.contains(name);
	}
	void removeAlive(Player ply){
		removeAlive(ply.getName());
	}
	void removeAlive(String name){
		aliveNames.remove(name);
		anunciarPerdedor(name);
		//addSpectator(ply);
		Player player = Bukkit.getPlayer(name);
		if (player != null) updateScoreBoard(player);
		PlayerInfo info = getPlayerInfo(name);
		if (info != null) info.setAlive(false);
		comprovarGuanyador();
	}
	public void removeIfAlive(Player ply){
		removeIfAlive(ply.getName());
	}
	public void removeIfAlive(String name){
		if (isAlive(name)){
			removeAlive(name);
		}
	}

	public Player getRandomAlivePlayer() {
		ArrayList<Player> alive = getAlivePlayers();
		if (alive.isEmpty()) return null;
		Random rand = new Random();
		return alive.get(rand.nextInt(alive.size()));

	}

	public Boolean anyoneAlive(){
		return(aliveNames.size() != 0);
	}
	public void anunciarPerdedor(String name){
		sendGlobalMessage(ChatColor.RED + name + " ha perdut!");
	}
	public void comprovarGuanyador(){
		if (aliveNames.size() == 1){
			String winnerName = aliveNames.get(0);
			sendGlobalMessage(ChatColor.GREEN + "" + ChatColor.BOLD + winnerName + " ha guanyat!");
			winGame(Bukkit.getPlayer(winnerName));
		}
		if (aliveNames.size() == 0){
			sendGlobalMessage(ChatColor.YELLOW + "No hi ha guanyadors!");
			winGame((Player) null);
		}
	}
	@Override
	public String getWinnerDisplayName() {
		if (aliveNames.size() == 1){
			return aliveNames.get(0);
		}
		return super.getWinnerDisplayName();
	}
	@Override
	protected void customJocIniciat() {
		initAlivePlayers();
		
	}
	@Override
	protected void customJocFinalitzat() {
		planificarReseteig(15);
		
	}
}
