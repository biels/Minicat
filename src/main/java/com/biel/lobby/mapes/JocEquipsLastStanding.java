package com.biel.lobby.mapes;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.biel.lobby.utilities.ScoreBoardUpdater;



public abstract class JocEquipsLastStanding extends JocEquips {
	ArrayList<Integer> AliveTeamIDs; 
	ArrayList<Player> AlivePlayers;
	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		initAlivePlayers();
		initAliveTeams();
		super.customJocIniciat();	
		fillAliveTeams();
	}
	public void initAliveTeams() { //Init method
		AliveTeamIDs = new ArrayList<>();
	}
	public void fillAliveTeams() {
		for(Equip e : Equips){
			if(e.getPlayers().size() == 0)continue;
			AliveTeamIDs.add(e.getId());
		}
	}
	public ArrayList<Equip> getAliveTeams(){
		ArrayList<Equip> r = new ArrayList<>();
		for(Equip e : Equips){
			if(isAlive(e))r.add(e);
		}
		return r;
	}
	public void removeAlive(int id) {
		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(id);
		AliveTeamIDs.removeAll(temp);
		//+ Cosetes
		sendGlobalMessage("L'equip " + obtenirEquip(id).getAdjectiuColored() + ChatColor.GRAY + " ha estat eliminat completament.");
		comprovarGuanyador();
		updateScoreBoards();
	}
	public void removeAlive(Equip e) {
		removeAlive(e.getId());		
	}
	public boolean isAlive(int id){
		return AliveTeamIDs.contains(id);
	}
	public boolean isAlive(Equip e){
		return isAlive(e.getId());
	}
	public void removeIfAlive(Equip e){
		if(isAlive(e))removeAlive(e);
	}
	public void removeIfAlive(int id){
		if(isAlive(id))removeAlive(id);
	}
	public boolean anyoneAlive(){
		return(AliveTeamIDs.size() != 0);
	}
	private Equip getWinner() {
		Integer last = AliveTeamIDs.get(0);
		return obtenirEquip(last);
	}
	public void comprovarGuanyador(){
		Equip winnerTeam = null;
		if (AliveTeamIDs.size() == 1){
			winnerTeam = getWinner();
			sendGlobalMessage(ChatColor.GRAY + "L'equip " + winnerTeam.getChatColor() + winnerTeam.getAdjectiu() + ChatColor.GRAY + " ha guanyat la partida!");
			winGame(winnerTeam);
		}
		if (AliveTeamIDs.size() == 0){
			sendGlobalMessage(ChatColor.YELLOW + "No hi ha guanyadors!");
			winGame(winnerTeam);
		}
	}
	//Players
	public void initAlivePlayers() { //initMethod
		AlivePlayers = this.getPlayers();
	}
	public ArrayList<Player> getAlivePlayers() {
		return AlivePlayers;
	}
	public void setAlivePlayers(ArrayList<Player> alivePlayers) {
		AlivePlayers = alivePlayers;
	}	
	public boolean isAlive(Player ply){
		return AlivePlayers.contains(ply);		
	}
	void removeAlive(Player ply){
		AlivePlayers.remove(ply);
		//Comprova la integritat de l'equip.
		Equip e = obtenirEquip(ply);
		int size = getAlivePlayersTeam(e).size();
		if(size == 0){
			removeAlive(e);
		}else{
			sendGlobalMessage("El jugador" + e.getChatColor() + ply.getName() + ChatColor.GRAY + " ha estat eliminat");
			sendGlobalMessage("Queden " + Integer.toString(size) + "jugadors a l'equip " + e.getAdjectiuColored());
		}
	}
	public void removeIfAlive(Player ply){
		if(isAlive(ply))removeAlive(ply);
	}
	//Mix
	public ArrayList<Player> getAlivePlayersTeam(Equip e){
		ArrayList<Player> r = new ArrayList<>();
		for(Player p : e.getPlayers()){
			if(isAlive(p))r.add(p);
		}
		return r;
	}
	protected String getTimer(){
		return "";
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		if (JocIniciat){
			ArrayList<String> list = new ArrayList<>();
			ArrayList<Integer> values = new ArrayList<>();
			String t = getTimer();
			for(Equip e : Equips){
				list.add(isAlive(e) ? e.getDisplayName() : ChatColor.STRIKETHROUGH + e.getDisplayName());
				values.add(getAlivePlayersTeam(e).size());
			}
			ScoreBoardUpdater.setScoreBoard(getPlayers(), "Estad. " + (!t.equals("") ? " " + ChatColor.GOLD + t : ""), list, values);
		}
	}

}
