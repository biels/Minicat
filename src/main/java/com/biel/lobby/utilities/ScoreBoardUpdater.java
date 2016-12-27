package com.biel.lobby.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import be.maximvdw.featherboard.api.FeatherBoardAPI;

import com.biel.lobby.Com;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;

public class ScoreBoardUpdater {
//	static public void updateScoreboards(resetejador plugin){
//		for(Player online : Bukkit.getOnlinePlayers()){
//    		updatePlayer(plugin, online);
//    	}
//	}
//	static public void updatePlayer(resetejador plugin, Player p){
//		ScoreboardManager manager = Bukkit.getScoreboardManager();
//    	Scoreboard board = manager.getNewScoreboard();
//    	
//    	
//    	if (plugin.mapa.equalsIgnoreCase("RainbowClay")) {
//    		Objective objective = board.registerNewObjective("Estadístiques", "dummy");
//        	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//        	objective.setDisplayName("Estadístiques");
//        	Boolean intEquip1 = false;
//        	Boolean intEquip2 = false;
//        	ChatColor core10 = ChatColor.GREEN; if(plugin.pTemp.ObtenirPropietatInt("Core10") == 1){core10 = ChatColor.STRIKETHROUGH;}else{intEquip1 = true;}
//        	ChatColor core11 = ChatColor.GREEN; if(plugin.pTemp.ObtenirPropietatInt("Core11") == 1){core11 = ChatColor.STRIKETHROUGH;}else{intEquip1 = true;}
//        	ChatColor core20 = ChatColor.GREEN; if(plugin.pTemp.ObtenirPropietatInt("Core20") == 1){core20 = ChatColor.STRIKETHROUGH;}else{intEquip2 = true;}
//        	ChatColor core21 = ChatColor.GREEN; if(plugin.pTemp.ObtenirPropietatInt("Core21") == 1){core21 = ChatColor.STRIKETHROUGH;}else{intEquip2 = true;}
//        	ChatColor wRed = ChatColor.GREEN; if(plugin.pTemp.ObtenirPropietatInt("WRed") == 1){wRed = ChatColor.STRIKETHROUGH;}else{intEquip1 = true;}
//        	ChatColor wMagenta = ChatColor.GREEN; if(plugin.pTemp.ObtenirPropietatInt("WMagenta") == 1){wMagenta = ChatColor.STRIKETHROUGH;}else{intEquip1 = true;}
//        	ChatColor wGreen = ChatColor.GREEN; if(plugin.pTemp.ObtenirPropietatInt("WGreen") == 1){wGreen = ChatColor.STRIKETHROUGH;}else{intEquip2 = true;}
//        	ChatColor wBlue = ChatColor.GREEN; if(plugin.pTemp.ObtenirPropietatInt("WBlue") == 1){wBlue = ChatColor.STRIKETHROUGH;}else{intEquip2 = true;}
//        	setScore(objective, ChatColor.RED + "Equip vermell", 10);	 
//        	setScore(objective, core10 + "Core vermell L", 9);
//        	setScore(objective, core11 + "Core vermell R", 8);
//        	setScore(objective, wRed + "Llana vemella", 7);
//        	setScore(objective, wMagenta + "Llana magenta", 6);
//        	setScore(objective, ChatColor.BLUE + "Equip blau", 5);	 
//        	setScore(objective, core20 + "Core blau L", 4);
//        	setScore(objective, core21 + "Core blau R", 3);
//        	setScore(objective, wBlue + "Llana blava", 2);
//        	setScore(objective, wGreen + "Llana verda", 1);
//        	if (intEquip1 == false){
//        		plugin.JocFinalitzat(plugin.ObtenirEquip(1));
//        	}
//        	if (intEquip2 == false){
//        		plugin.JocFinalitzat(plugin.ObtenirEquip(2));
//        	}
//    	}
//    	
//    	p.setScoreboard(board);
//	}
	static public void updateTeamScore(JocEquips j){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		for (Equip e : j.Equips){
			Team Ghosts = board.registerNewTeam(e.getAdjectiu());
			for(Player p : e.getPlayers()){
				p.setScoreboard(board);
				Ghosts.addPlayer(p);
				Com.setHeadColor(p, ColorConverter.chatToRaw(e.getChatColor()));
				
			}
			Ghosts.setCanSeeFriendlyInvisibles(true);
			Ghosts.setAllowFriendlyFire(false);
			//Ghosts.setPrefix(e.getChatColor() + "");
			//Ghosts.setPrefix(e.getChatColor() + "[" + e.getAdjectiu() +"]");		
			
		} 

	}
	@Deprecated
	static public void updateSpectatorScore(ArrayList<Player> ply){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Team Ghosts = board.registerNewTeam("Ghost");
        Ghosts.setCanSeeFriendlyInvisibles(true);
        Ghosts.setAllowFriendlyFire(false);
        for(Player p : ply){
        	p.setScoreboard(board);
        }
	}
	static public void setScore(Objective objective, String name, int value){
		Score score = objective.getScore(Bukkit.getOfflinePlayer(name)); //Get a fake offline player
		score.setScore(value);
	}
	static public void setScoreBoard(List<Player> ply, String title, ArrayList<String> items, ArrayList<Integer> values){
		for (Player p : ply){
			setScoreBoard(p, title, items, values);
		}
	}
	static public void setScoreBoard(Player ply, String title, ArrayList<String> items, ArrayList<Integer> values){
		//if(FeatherBoardAPI.isToggled(ply))FeatherBoardAPI.toggle(ply); //Disable
		ScoreboardManager manager = Bukkit.getScoreboardManager();
    	Scoreboard board = manager.getNewScoreboard();
    	Objective objective = board.registerNewObjective("Nom", "dummy");
    	objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    	objective.setDisplayName(title);
    	Collections.reverse(items);
    	if (values != null){
    		Collections.reverse(values);
    	}
    	for (String item : items){
    		int value;
    		if (values == null){
    			value = items.indexOf(item);
    		}else{
    			try {
    				value = values.get(items.indexOf(item));
    			} catch (IndexOutOfBoundsException e) {
    				//Bukkit.broadcastMessage("Eceptio");
    				value = 0;
    			}
    		}
    		setScore(objective, item, value);
    	}

    	ply.setScoreboard(board);
	}
	static public void clearScoreBoard(Player ply){
		ScoreboardManager mng = Bukkit.getScoreboardManager();
		ply.setScoreboard(mng.getNewScoreboard());
		
	}
}
