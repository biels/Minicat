package com.biel.lobby.mapes;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class JocLastStanding extends Joc{
	
	ArrayList<Player> AlivePlayers;
	public void initAlivePlayers() {
		AlivePlayers = this.getPlayers();
	}
	public ArrayList<Player> getAlivePlayers() {
		return AlivePlayers;
	}

	public void setAlivePlayers(ArrayList<Player> alivePlayers) {
		AlivePlayers = alivePlayers;
	}
	
	public boolean isAlive(Player ply){
//		for (Player p : AlivePlayers){
//			Bukkit.broadcastMessage(ChatColor.GREEN + p.getName());
//		}
		return AlivePlayers.contains(ply);
		
	}
	void removeAlive(Player ply){
		AlivePlayers.remove(ply);
		anunciarPerdedor(ply);
		//addSpectator(ply);
		updateScoreBoard(ply);
		getPlayerInfo(ply).setAlive(false);
		comprovarGuanyador();
	}
	public void removeIfAlive(Player ply){
		if (isAlive(ply)){
			removeAlive(ply);
		}   
	}
	public Boolean anyoneAlive(){
		return(AlivePlayers.size() != 0);
	}
	public void anunciarPerdedor(Player ply){
		sendGlobalMessage(ChatColor.RED + ply.getName() + " ha perdut!");
	}
	public void comprovarGuanyador(){
		Player winner = null;
		if (getAlivePlayers().size() == 1){
			winner = getWinner();
			sendGlobalMessage(ChatColor.GREEN + "" + ChatColor.BOLD + winner.getName() + " ha guanyat!");
			winGame(winner);
		}
		if (getAlivePlayers().size() == 0){
			sendGlobalMessage(ChatColor.YELLOW + "No hi ha guanyadors!");
			winGame(winner);
		}
	}
	private Player getWinner() {
		return getAlivePlayers().get(0);
	}
	@Override
	public String getWinnerDisplayName() {
		if (getAlivePlayers().size() == 1){
			return getWinner().getName();
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
