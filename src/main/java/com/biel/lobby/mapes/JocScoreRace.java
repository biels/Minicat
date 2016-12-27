package com.biel.lobby.mapes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.utilities.ScoreBoardUpdater;

public abstract class JocScoreRace extends Joc {

	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void teletransportarTothom() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void customJocFinalitzat() {
		// TODO Auto-generated method stub
		planificarReseteig(6);
	}

	protected abstract int getFinishScore();
	protected int getScore(Player ply){
		return getPlayerInfo(ply).getValue();
	}
	protected int getSpree(Player ply){
		return getPlayerInfo(ply).getSpree();
	}
	protected void setScore(Player ply, int value){
		PlayerInfo i = getPlayerInfo(ply);
		i.setValue(value);
		//--
		updateScoreBoards();
		comprovarGuanyador();
	}
	protected void setSpree(Player ply, int value){
		PlayerInfo i = getPlayerInfo(ply);
		i.setSpree(value);
		ply.setLevel(value);
	}
	protected void incrementSpree(Player ply, int amount){
		setSpree(ply, getSpree(ply) + amount);
	}
	protected void incrementSpree(Player ply){
		incrementSpree(ply, 1);
	}
	protected void resetSpree(Player ply){
		setSpree(ply, 0);
		updateScoreBoards();
	}
	protected void incrementScore(Player ply, int amount){
		incrementSpree(ply, amount);
		setScore(ply, getScore(ply) + amount);
	}
	protected void incrementScore(Player ply){
		incrementScore(ply, 1);
	}
	protected void resetScore(Player ply){
		setScore(ply, 0);
	}
	public void comprovarGuanyador(){
		ArrayList<Player> wList = getOrderedWinnerList();
		Player winner = wList.get(0);
		if (getScore(winner) >= getFinishScore()){
			sendGlobalMessage(ChatColor.GREEN + "" + ChatColor.BOLD + winner.getName() + " ha guanyat!");
			if (getDisplayRanking()){displayRanking();}
			winGame(wList);
		}
	}
	public void displayRanking(){
		ArrayList<Player> w = getOrderedWinnerList();
		sendGlobalMessage("-----Ranking-----");
		for (Player p : w){
			int index = w.indexOf(p) + 1;
			String msg = "";
			String c = "";
			c = "" + ChatColor.BLUE;
			if (index <= 1){c = "" + ChatColor.GREEN;}
			if (index <= 3){c = "" + ChatColor.YELLOW;}
			if (index == w.size()){c = "" + ChatColor.RED;}
			msg += c;
			msg += Integer.toString(index);
			msg += " - ";
			msg += p.getName();
			sendGlobalMessage(msg);
		}
	}
	public boolean getDisplayRanking(){
		return true;
	}
	public ArrayList<Player> getOrderedWinnerList(){
		ArrayList<Player> arr = getPlayers();
		arr.sort((o1, o2) -> {
			// TODO Auto-generated method stub
			return (getScore(o2) - getScore(o1));
		});
		return arr;
	}
	@Override
	public double getGameProgressETA() {
		// TODO Auto-generated method stub
		ArrayList<Player> w = getOrderedWinnerList();
		if(w.size() == 0)return 0;
		int best = getScore(w.get(0));
		double bestToFinishRatio = best / (double)getFinishScore();
		return super.getGameProgressETA() * 0.2 + bestToFinishRatio * 0.8;
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		if (JocIniciat){
			ArrayList<String> list = new ArrayList<>();
			ArrayList<Integer> values = new ArrayList<>();
			ArrayList<Player> w = getOrderedWinnerList();
			if (w.size() > 0){
				int best = getScore(w.get(0));


				for (Player p : w){
					String c = "";
					int index = w.indexOf(p) + 1;
					c = "" + ChatColor.BLUE;
					if (index <= 3){c = "" + ChatColor.YELLOW;}
					if (index <= 1){c = "" + ChatColor.GREEN;}
					
					if (index == w.size() && w.size() >= 2){c = "" + ChatColor.RED;}
					String name = p.getName();					
					String finalString = c + StringUtils.abbreviate(name, 9);
					int spree = getSpree(p);
					if (spree > 0){
						//finalString += ChatColor.WHITE + " ("+ ChatColor.YELLOW + Integer.toString(spree) + ChatColor.WHITE + ")";
						finalString += " ("+ Integer.toString(spree) + ")";

					}
					if (finalString.length() > 16){
						finalString = finalString.substring(0, 16);
					}
					list.add(finalString);
					values.add(getScore(p));
				}

				ScoreBoardUpdater.setScoreBoard(getPlayers(), "Taula [" + Integer.toString(best) + "/" + Integer.toString(getFinishScore()) + "]", list, values);
			}
		}
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		// TODO Auto-generated method stub
		super.onPlayerDeath(evt, killed);
		resetSpree(killed);
		
	}
}
