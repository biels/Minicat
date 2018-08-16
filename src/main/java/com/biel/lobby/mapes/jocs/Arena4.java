package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.ScoreBoardUpdater;

public class Arena4 extends JocEquips {

	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		super.customJocIniciat();
		setBlockBreakPlace(false);
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Arena 4";
	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		equips.add(new Arena4Equip(DyeColor.RED, "vermell", 3)); //Id 0
		equips.add(new Arena4Equip(DyeColor.BLUE, "blau", 0)); //Id 1
		equips.add(new Arena4Equip(DyeColor.GREEN, "verd", 1)); //Id 2
		equips.add(new Arena4Equip(DyeColor.YELLOW, "groc", 2)); //Id 3
		return equips;
	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		items.add(new ItemStack(Material.WOODEN_SWORD));
		return items;
	}

	
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub
		
	}
	
	
	

	void comprovarGuanyador(){
		for (Equip e : Equips){
			Arena4Equip eq = (Arena4Equip) e;
			if (eq.Score >= eq.MaxScore){
				JocFinalitzat();
			}
		}
	}
	void updateScoreboard(){
		ArrayList<String> list = new ArrayList<>();
		ArrayList<Integer> nums = new ArrayList<>();
		for (Equip e : Equips){
			Arena4Equip eq = (Arena4Equip) e;
			list.add(e.getChatColor() + "Equip " + e.getAdjectiu());
			nums.add(eq.getScore());
		}
		ScoreBoardUpdater.setScoreBoard(getPlayers(), "Punts [Max 20]", list, nums);
	}
	@Override
	protected synchronized void gameEvent(Event event) {
		if (event instanceof PlayerDeathEvent){
			PlayerDeathEvent evt = (PlayerDeathEvent)event;
			Player killed = evt.getEntity();
			Player killer = killed.getKiller();
			Arena4Equip eqKilled = (Arena4Equip) obtenirEquip(killed);
			Arena4Equip eqKiller = (Arena4Equip) obtenirEquip(killer);
			if (killer == null){return;}
			if (areEnemies(killed, killer)){
				int suma = 0;
				int resta = 1;
				if(eqKiller.equipObjectiu() == eqKilled){
					suma = 3;
				}else{
					suma = 1;
				}
				if(getTeamSize() < 1){
					suma = 2;
				}
				eqKiller.afegirPunts(suma);
				eqKilled.restarPunts(resta);
				// biel(+3) ha matat a amigiuet(-1)
				String msg = eqKiller.getChatColor() + killer.getName() +"("+ ChatColor.WHITE + "+" + Integer.toString(suma) + eqKiller.getChatColor() + ")" + ChatColor.WHITE + " ha matat a " + killed.getName() + eqKilled.getChatColor() + "("+ ChatColor.WHITE + "-" + Integer.toString(resta) + eqKilled.getChatColor() + ")";
				evt.setDeathMessage(msg);
				updateScoreboard();
				comprovarGuanyador();
			}
		}
	}


	public class Arena4Equip extends Equip{
		int Score = 5;
		public int getScore() {
			return Score;
		}
		public void setScore(int score) {
			Score = score;
		}
		int MaxScore = 20;
		int idEnemic;
		public Arena4Equip(DyeColor color, String adj, int idEnemic) {
			super(color, adj);
			this.idEnemic = idEnemic;
			// TODO Auto-generated constructor stub
		}
		public void restarPunts(int num){
			if (num <= 0){return;}
			int finalscore = Score - num;
			if (finalscore <= 0){Score = 0; return;}
			Score = finalscore;
		}
		public void afegirPunts(int num){
			if (num <= 0){return;}
			int finalscore = Score + num;
			if (finalscore >= MaxScore){Score = MaxScore; return;}
			Score = finalscore;
		}
		public Equip equipObjectiu(){
			return obtenirEquip(idEnemic);
		}
	}




	
	

	

	
	

}
