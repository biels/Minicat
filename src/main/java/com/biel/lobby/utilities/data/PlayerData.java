package com.biel.lobby.utilities.data;

import org.bukkit.entity.Player;

import com.biel.lobby.Com;

public class PlayerData {
	int id;
	String userName;
	public PlayerData(int id) {
		super();
		this.id = id;
		this.userName = Com.getDataAPI().getPlayerName(id);
	}
	public PlayerData(String user_name) {
		super();
		this.userName = user_name;
		this.id = Com.getDataAPI().getPlayerId(user_name);
	}
	public PlayerData(Player ply) {
		this(ply.getName());
	}
	public int getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public double getMoney(){
		return Com.getDataAPI().getMoney(id);
	}
	public void setMoney(double value){
		Com.getDataAPI().setMoney(id, value);
	}
	public void addMoney(double amount){
		setMoney(getMoney() + amount);
	}
	public double getScore(){
		return Com.getDataAPI().getScore(id);
	}
	public void setScore(double value){
		Com.getDataAPI().setScore(id, value);
	}
	public void addScore(double amount){
		setMoney(getScore() + amount);
	}
	public double getElo(){
		return Com.getDataAPI().getElo(id);
	}
	public void setElo(double value){
		Com.getDataAPI().setElo(id, value);
	}
	public void addElo(double amount){
		setElo(getElo() + amount);
	}
	public double getRelativeElo(){
		return getElo() - Com.getDataAPI().getAvgElo();
	}
	public int getRank(){
		if(!Com.getDataAPI().getRanking().contains(id))return -1;
		return Com.getDataAPI().getRanking().indexOf(id) + 1;
	}
}
