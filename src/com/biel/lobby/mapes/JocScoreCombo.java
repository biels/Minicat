package com.biel.lobby.mapes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.mapes.jocs.InkWars.InkWarsPlayerInfo;
import com.biel.lobby.utilities.ScoreBoardUpdater;

public abstract class JocScoreCombo extends Joc {

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

		planificarReseteig(40 / (getPlayers().size() + 1));
	}
	public enum Rank{
		SS(99), S(95), A(90), B(80), C(70), D(60), F(40), FF(5);
		private int value;

		private Rank(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		public static Rank getRank(double percent){
			Rank r = Rank.FF;
			for(Rank rank : Rank.values()){
				if(percent > rank.getValue() && rank.getValue() > r.getValue())r = rank;
			}
			return r;
		}
		public String getFormattedString(){
			ChatColor c = ChatColor.GRAY;
			switch(this){
			case A:
				c = ChatColor.DARK_GREEN;
				break;
			case B:
				c = ChatColor.DARK_BLUE;
				break;
			case C:
				c = ChatColor.DARK_PURPLE;
				break;
			case D:
				c = ChatColor.DARK_RED;
				break;
			case S:
				c = ChatColor.GOLD;
				break;
			case SS:
				c = ChatColor.YELLOW;
			case F:
				c = ChatColor.DARK_GRAY;
				break;
			case FF:
				c = ChatColor.BLACK;
				break;
			default:
				break;			
			}
			return c + "" + ChatColor.BOLD + name();
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return getFormattedString();
		}
	}
	public enum Score{
		FAIL(0), N50(50), N100(100), N200(200), N300(300), C300(320);
		private int value;

		private Score(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		public int getAccuracyValue() {
			if(value > 300)return 300;
			return value;
		}
		public String getFormattedString(){
			switch(this){
			case C300:
				return ChatColor.DARK_BLUE + "3" + ChatColor.GOLD + "0" + ChatColor.DARK_RED + "0";
			case FAIL:
				return ChatColor.RED + "Fail!";
			case N100:
				return ChatColor.BLUE + "100";
			case N200:
				return ChatColor.GREEN + "200";
			case N300:
				return ChatColor.GOLD + "300";
			case N50:
				return ChatColor.DARK_GRAY + "50";
			default:
				return "";			
			}
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return getFormattedString();
		}
	}
	protected int getScore(Player ply){
		return getPlayerInfo(ply).getValue();
	}
	protected double getCombo(Player ply){
		JocScoreComboPlayerInfo i = getPlayerInfo(ply);
		int spree = i.getSpree();
		if(spree < 10)resetCombo(ply);
		return i.getSpree() / 10.0;
	}
	protected void setScore(Player ply, int value){
		PlayerInfo i = getPlayerInfo(ply);
		i.setValue(value);
		ply.setLevel((int) Math.round(getPlayerInfo(ply).getAccuracy()));
		//--
		updateScoreBoards();
		//comprovarGuanyador();
	}
	protected void setCombo(Player ply, double value){
		PlayerInfo i = getPlayerInfo(ply);
		i.setSpree((int) Math.round(value * 10));

	}
	protected void incrementCombo(Player ply, double amount){
		setCombo(ply, getCombo(ply) + amount);
	}
	protected void incrementCombo(Player ply){
		incrementCombo(ply, 0.1);
	}
	protected void resetCombo(Player ply){
		setCombo(ply, 1);
		//updateScoreBoards();
	}
	protected void incrementScore(Player ply, Score amount){
		if(amount == Score.FAIL){
			resetCombo(ply);
		}else{
			setScore(ply, (int) (getScore(ply) + Math.round(amount.getValue() * getCombo(ply))));
			incrementCombo(ply);			
		}
		getPlayerInfo(ply).getScoreHistory().add(amount);
		updateScoreBoards();
		Com.setSuffix(ply, Rank.getRank(getPlayerInfo(ply).getAccuracy()).getFormattedString());
	}

	protected void resetScore(Player ply){
		setScore(ply, 0);
	}
	public void comprovarGuanyador(){
		ArrayList<Player> orderedWinnerList = getOrderedWinnerList();
		if(orderedWinnerList.size() == 0)return;
		Player winner = orderedWinnerList.get(0);
		sendGlobalMessage(ChatColor.GREEN + "" + ChatColor.BOLD + winner.getName() + " ha guanyat!");
		if (getDisplayRanking()){displayRanking();}
		getPlayers().forEach(p -> getPlayerInfo(p).getScoreFancyReport(ChatColor.WHITE).forEach(l -> p.sendMessage(l)));
		getPlayers().forEach(p -> {p.getInventory().setHeldItemSlot(3); p.getInventory().setItem(3, getFancyBookReport());});
		winGame(winner);
	}
	public void displayRanking(){
		ArrayList<Player> w = getOrderedWinnerList();
		sendGlobalMessage("-----Ranking-----");
		for (Player p : w){
			int index = w.indexOf(p) + 1;
			String msg = "";
			String c = "";
			if (true){c = "" + ChatColor.BLUE;}
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
		Collections.sort(arr, new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				// TODO Auto-generated method stub
				return (getScore(o2) - getScore(o1));
			}	
		});
		return arr;
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		if (JocIniciat){
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<Integer> values = new ArrayList<Integer>();
			ArrayList<Player> w = getOrderedWinnerList();
			if (w.size() > 0){
				int best = getScore(w.get(0));


				for (Player p : w){
					String c = "";
					int index = w.indexOf(p) + 1;
					if (true){c = "" + ChatColor.BLUE;}
					if (index <= 3){c = "" + ChatColor.YELLOW;}
					if (index <= 1){c = "" + ChatColor.GREEN;}

					if (index == w.size() && w.size() >= 2){c = "" + ChatColor.RED;}
					String name = p.getName();					
					String finalString = Rank.getRank(getPlayerInfo(p).getAccuracy()) + c + StringUtils.abbreviate(name, 5);
					double combo = getCombo(p);
					if (combo > 0){
						//finalString += ChatColor.WHITE + " ("+ ChatColor.YELLOW + Integer.toString(spree) + ChatColor.WHITE + ")";
						finalString += "(x"+ Double.toString(combo) + ")";

					}
					if (finalString.length() > 16){
						finalString = finalString.substring(0, 16);
					}
					list.add(finalString);
					values.add(getScore(p));
				}

				ScoreBoardUpdater.setScoreBoard(getPlayers(), getGameName(), list, values);
			}
		}
	}
	@Override
	public boolean getDisplayHealthBar() {
		// TODO Auto-generated method stub
		return false;
	}
	public ItemStack getFancyBookReport(){
		ItemStack itembook = new ItemStack(Material.WRITTEN_BOOK); 
		BookMeta book = (BookMeta) itembook.getItemMeta();
		book.setAuthor(getGameName());
		book.setTitle(ChatColor.YELLOW + "Resultats");
		getPlayers().stream().sorted((Player p1, Player p2) -> Integer.compare(getScore(p2), getScore(p1))).forEach(p -> {
			ArrayList<String> scoreFancyReport = getPlayerInfo(p).getScoreFancyReport(ChatColor.BLACK);
			scoreFancyReport.add(0, ChatColor.BLACK + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + p.getName() + ChatColor.RESET);
			book.addPage(StringUtils.join(scoreFancyReport, ChatColor.WHITE + System.lineSeparator() + ChatColor.BLACK));
		});
		itembook.setItemMeta(book);
		return itembook;
	}
	@Override
	public JocScoreComboPlayerInfo getPlayerInfo(Player p) {
		return getPlayerInfo(p, JocScoreComboPlayerInfo.class);		
	}
	public class JocScoreComboPlayerInfo extends PlayerInfo{
		boolean inGame = true;
		ArrayList<Score> scoreHistory = new ArrayList<Score>();
		public JocScoreComboPlayerInfo() {
			super();
		}
		public ArrayList<Score> getScoreHistory() {
			return scoreHistory;
		}		
		public String getScoreInlineReport(){
			ArrayList<String> pieces = new ArrayList<String>();
			for(Score s : Score.values()){

				pieces.add(getScoreTypeLabeled(s, ChatColor.WHITE));
			}
			return String.join(", ", pieces);
		}
		public String getScoreTypeLabeled(Score s, ChatColor c) {
			long scoreTypeAmount = getScoreTypeAmount(s);
			return s.getFormattedString() + c + " x" + scoreTypeAmount;
		}
		public ArrayList<String> getScoreFancyReport(ChatColor c){
			ArrayList<String> lines = new ArrayList<String>();
			String tab = "     ";
			lines.add("Qualificació: " + Rank.getRank(getAccuracy()));
			lines.add("Precisió: " + Math.round(getAccuracy() * 100)/100.0 + "%");
			lines.add("Punts: " + getScore(getPlayer()));
			lines.add(getScoreTypeLabeled(Score.N300, c) + tab + getScoreTypeLabeled(Score.C300, c));
			lines.add(getScoreTypeLabeled(Score.N200, c) + tab + getScoreTypeLabeled(Score.N100, c));
			lines.add( "  " + getScoreTypeLabeled(Score.N50, c) + tab + getScoreTypeLabeled(Score.FAIL, c));
			return lines;
		}
		public long getScoreTypeAmount(Score type) {
			return getScoreHistory().stream().filter(t -> t == type).count();
		}
		public double getAccuracy(){
			return scoreHistory.stream().mapToInt(Score::getAccuracyValue).sum() * 100 / ((double) scoreHistory.size() * 300);
		}
		public boolean isInGame() {
			return inGame;
		}
		public void setInGame(boolean inGame) {
			this.inGame = inGame;
		}
	}


}
