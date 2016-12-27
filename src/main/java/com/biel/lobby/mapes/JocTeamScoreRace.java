package com.biel.lobby.mapes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.ScoreBoardUpdater;

public abstract class JocTeamScoreRace extends JocEquips {

	protected abstract int getFinishScore();
	
	public void comprovarGuanyador(){
		EquipScoreRace winner = getOrderedWinnerList().get(0);
		if (winner.getScore() >= getFinishScore()){
			sendGlobalMessage(ChatColor.GREEN + "" + ChatColor.BOLD + winner.getDisplayName() + " ha guanyat la partida!");
			winGame(winner);
		}
	}
	
	public ArrayList<EquipScoreRace> getOrderedWinnerList(){
		ArrayList<EquipScoreRace> arr = getSpecificTeams();
		arr.sort((o1, o2) -> {
            // TODO Auto-generated method stub
            return (o2.getScore() - o1.getScore());
        });
		return arr;
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		if (JocIniciat){
			ArrayList<String> list = new ArrayList<>();
			ArrayList<Integer> values = new ArrayList<>();
			
			for (Equip e : Equips){
				EquipScoreRace eq = (EquipScoreRace)e;
				String finalString = eq.getDisplayName();

				if (finalString.length() > 16){
					finalString = finalString.substring(0, 16);
				}
				list.add(finalString);
				values.add(eq.getScore());
			}

			ScoreBoardUpdater.setScoreBoard(getPlayers(), "Max: " + getFinishScore(), list, values);

		}
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		// TODO Auto-generated method stub
		super.onPlayerDeath(evt, killed);
		//HPuntos api = new HPuntos(); //Eliminat del projecte
		//api.getDatabase().
		
	}
	
	public ArrayList<EquipScoreRace> getSpecificTeams() {
		ArrayList<EquipScoreRace> teams = new ArrayList<>();
		for (Equip e :  Equips){
			EquipScoreRace eq = (EquipScoreRace)e;
			teams.add(eq);
		}
		return teams;
	}
	
	public class EquipScoreRace extends Equip{
		int score = 0;
		public EquipScoreRace(DyeColor color, String adj) {
			super(color, adj);
			// TODO Auto-generated constructor stub
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
			updateScoreBoards();
			comprovarGuanyador();
		}
		public void incrementScore(int amount){
			setScore(getScore() + amount);
		}
		public void incrementScore(){
			incrementScore(1);
		}
	}
}
