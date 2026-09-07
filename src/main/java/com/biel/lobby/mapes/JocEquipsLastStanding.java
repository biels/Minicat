package com.biel.lobby.mapes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.biel.lobby.utilities.ScoreBoardUpdater;



public abstract class JocEquipsLastStanding extends JocEquips {
	ArrayList<Integer> AliveTeamIDs; 
	/** Who is still in the round, by name; see JocLastStanding for why not by Player. */
	private ArrayList<String> aliveNames = new ArrayList<>();
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
		setAlivePlayers(this.getPlayers());
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
	public void removeAlive(Player ply){
		removeAlive(ply.getName());
	}
	public void removeAlive(String name){
		aliveNames.remove(name);
		//Comprova la integritat de l'equip.
		Equip e = teamOfName(name);
		if (e == null) return;
		int size = getAliveNamesTeam(e).size();
		if(size == 0){
			removeAlive(e);
		}else{
			sendGlobalMessage("El jugador" + e.getChatColor() + name + ChatColor.GRAY + " ha estat eliminat");
			sendGlobalMessage("Queden " + Integer.toString(size) + "jugadors a l'equip " + e.getAdjectiuColored());
		}
	}
	public void removeIfAlive(Player ply){
		removeIfAlive(ply.getName());
	}
	public void removeIfAlive(String name){
		if(isAlive(name))removeAlive(name);
	}
	//Mix
	public ArrayList<Player> getAlivePlayersTeam(Equip e){
		ArrayList<Player> r = new ArrayList<>();
		for(Player p : e.getPlayers()){
			if(isAlive(p))r.add(p);
		}
		return r;
	}
	/** The team's alive members whether online or not; a drop must not read as an elimination. */
	public ArrayList<String> getAliveNamesTeam(Equip e){
		ArrayList<String> r = new ArrayList<>();
		for(String name : e.getPlayerNames()){
			if(isAlive(name))r.add(name);
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
