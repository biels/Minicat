package com.biel.lobby.utilities.data;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.MapaResetejable.MapMode;

public class MatchData {
	int id;
	int currentFrame = 0;
	public MatchData(int id) {
		super();
		this.id = id;
	}
	public static MatchData registerStart(int gameId, int mapId, String teams){
		return Com.getDataAPI().registerMatchStart(gameId, mapId, teams);
	}
	public static MatchData registerStart(Joc instance){
		int gameId = Com.getDataAPI().getGameId(instance.getGameName());
		int mapId = Com.getDataAPI().getMapId((instance.getMapMode() == MapMode.MULTIPLE ? instance.getMultiMapName() : instance.getGameName()), gameId);
		return registerStart(gameId, mapId, getTeamsString(instance));
	}
	public static String getTeamsString(Joc joc){ //1,2,3;3,5,4
		ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
		if(joc instanceof JocEquips){
			for(Equip e : ((JocEquips) joc).Equips){
				ArrayList<Integer> t = new ArrayList<>();
				e.getPlayers().forEach(((p) -> t.add(new PlayerData(p).getId())));
				arr.add(t);
			}
		}else{
			ArrayList<Integer> t = new ArrayList<>();
			joc.getPlayers().forEach(((p) -> t.add(new PlayerData(p).getId())));
			arr.add(t);
		}
		joc.getGameName();
		ArrayList<String> teamStrings = new ArrayList<>();
		for(ArrayList<Integer> l : arr){teamStrings.add(StringUtils.join(l, ','));}		
		return StringUtils.join(teamStrings, ';');
	}
	public void registerEnd(int winner){
		Com.getDataAPI().registerMatchEnd(id, winner);
	}
	public void registerEnd(Equip winner){
		registerEnd(winner.getId());
	}
	public void registerEnd(Player winner){
		registerEnd(new PlayerData(winner).getId());
	}
	public void registerTimestamp(Player p, boolean ending, int kills, int deaths, double damageDealt, boolean isAlive, int itemInHand, int blocksPlaced, int blocksBroken, int objectivesCompleted, int spree){
		Com.getDataAPI().registerTimestamp(id, new PlayerData(p).getId(), (ending ? -1 : currentFrame), kills, deaths, damageDealt, isAlive, itemInHand, blocksPlaced, blocksBroken, objectivesCompleted, spree);
		currentFrame++;
	}
}
