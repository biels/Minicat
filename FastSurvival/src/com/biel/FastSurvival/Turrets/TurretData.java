package com.biel.FastSurvival.Turrets;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.biel.FastSurvival.Turrets.TurretLogic.*;
import com.biel.FastSurvival.Utils.GestorPropietats;
import com.biel.FastSurvival.Utils.Utils;

public class TurretData {
	int iId;
	public TurretData(int iId) {
		super();
		this.iId = iId;
	}
	
	//DATA
	//GET
	public Boolean getActive(){
		return getProps().ObtenirPropietatBoolean("active");
	}
	public Boolean getBuilt(){
		return getProps().ObtenirPropietatBoolean("built");
	}
	public Boolean getEnabled(){
		return getProps().ObtenirPropietatBoolean("enabled");
	}
	public Boolean getNoPlayers(){
		return getProps().ObtenirPropietatBoolean("noPlayers");
	}
	public int getTier(){
		return getProps().ObtenirPropietatInt("tier");
	}
	public int getHealth(){
		return getProps().ObtenirPropietatInt("hp");
	}
	public int getElapsedRegenTicks(){
		return getProps().ObtenirPropietatInt("regenTicks");
	}
	public long getLastTimeModified(){
		return getProps().ObtenirPropietatLong("lastModTime");
	}
	public int getMaxHealth(){
		return getProps().ObtenirPropietatInt("maxHp");
	}
	public int getKills(){
		return getProps().ObtenirPropietatInt("kills");
	}
	public long getTicks(){
		return getProps().ObtenirPropietatLong("ticks");
	}
	public long getNormalShots(){
		return getProps().ObtenirPropietatLong("normalShots");
	}
	public Location getLocation(){
		return getProps().ObtenirLocation("loc");
	}
	public ArrayList<AttackGroups> getAttackGroups(){
		ArrayList<AttackGroups> list = new ArrayList<AttackGroups>();
		for (Integer s : getProps().ObtenirArrayInt("attackGroups")){
			list.add(AttackGroups.values()[s]);
		}
		return list;
	}
	public ArrayList<Upgrades> getUpgrades(){
		ArrayList<Upgrades> list = new ArrayList<Upgrades>();
		for (Integer s : getProps().ObtenirArrayInt("upgrades")){
			list.add(Upgrades.values()[s]);
		}
		return list;
	}
	public String getOwner(){
		return getProps().ObtenirPropietat("owner");
	}
	public ArrayList<String> getFriendlyPlayers(){
		String[] obtenirLlista = getProps().ObtenirLlista("friendlyPlayers");
		
		if (obtenirLlista == null){return new ArrayList<String>();}
		if (obtenirLlista.length == 0){return new ArrayList<String>();}
		List<String> asList = Arrays.asList(obtenirLlista);
		if (asList == null){return new ArrayList<String>();}
		return new ArrayList<String>(asList);
	}
	
	//SET
	public void setActive(Boolean value){
		getProps().EstablirPropietat("active", value);
	}
	public void setBuilt(Boolean value){
		getProps().EstablirPropietat("built", value);
	}
	public void setEnabled(Boolean value){
		getProps().EstablirPropietat("enabled", value);
	}
	public void setNoPlayers(Boolean value){
		getProps().EstablirPropietat("noPlayers", value);
	}
	public void setTier(int value){
		getProps().EstablirPropietat("tier", value);
	}
	public void setLastTimeModified(long value){
		getProps().EstablirPropietat("lastModTime", value);
	}
	public void setHealth(int value){
		getProps().EstablirPropietat("hp", value);
	}
	public void setElapsedRegenTicks(int value){
		getProps().EstablirPropietat("regenTicks", value);
	}
	public void setMaxHealth(int value){
		getProps().EstablirPropietat("maxHp", value);
	}
	public void setKills(int value){
		getProps().EstablirPropietat("kills", value);
	}
	public void setTicks(long value){
		getProps().EstablirPropietat("ticks", value);
	}
	public void setNormalShots(long value){
		getProps().EstablirPropietat("normalShots", value);
	}
	public void setLocation(Location value){
		getProps().EstablirLocation("loc", value);
	}
	public void setAttackGroups(ArrayList<AttackGroups> value){
		ArrayList<String> list = new ArrayList<String>();
		for (AttackGroups s : value){
			list.add(Integer.toString(s.ordinal()));
		}
		getProps().EstablirArray("attackGroups", list);
	}
	public void setUpgrades(ArrayList<Upgrades> value){
		ArrayList<String> list = new ArrayList<String>();
		for (Upgrades s : value){
			list.add(Integer.toString(s.ordinal()));
		}
		getProps().EstablirArray("upgrades", list);
	}
	public void setOwner(String value){
		getProps().EstablirPropietat("owner", value);
	}
	public void setFriendlyPlayers(ArrayList<String> value){
		getProps().EstablirLlista("friendlyPlayers", value);
	}
	
	//HAS
	public Boolean hasActive(){
		return getProps().ExisteixPripietat("active");
	}
	public Boolean hasTier(){
		return getProps().ExisteixPripietat("tier");
	}
	public Boolean hasTicks(){
		return getProps().ExisteixPripietat("ticks");
	}
	public Boolean hasBuilt(){
		return getProps().ExisteixPripietat("built");
	}
	public Boolean hasLocation(){
		return getProps().ExisteixPripietat("loc");
	}
	public Boolean hasAttackGroups(){
		return getProps().ExisteixPripietat("attackGroups");
	}
	public Boolean hasOwner(){
		return getProps().ExisteixPripietat("owner");
	}
	public Boolean hasFriendlyPlayers(){
		return getProps().ExisteixPripietat("friendlyPlayers");
	}
	public Boolean hasUpgrades(){
		return getProps().ExisteixPripietat("upgrades");
	}
	
	public GestorPropietats getProps(){
		String folder = getFolder();
		String rutaArxiuPropietats = folder + "/" + Integer.toString(iId) + ".txt";
		return new GestorPropietats(rutaArxiuPropietats);
	}

	static String getFolder() {
		String folder = "TurretData";
		File f = new File(folder);
		if (!f.exists()){f.mkdirs();}
		return folder;
	}
	public Boolean exists(){
		return getProps().exists();
	}
	public static int getNextiId(){
		File f = new File(getFolder());
		int highest = 0;
		for (File txt : f.listFiles()){
			if (!txt.isDirectory()){
				String name = txt.getName();

				String Idonly = name.substring(0, name.length() - 4);
				//Bukkit.broadcastMessage(Idonly);

				int id = Integer.parseInt(Idonly);
				if (id > highest){
					highest = id;

				}
			}

		}
		return highest + 1;
	}
	public static ArrayList<TurretData> getAllTurrets(){
		ArrayList<TurretData> all = new ArrayList<TurretData>();
		File f = new File(getFolder());
		for (File txt : f.listFiles()){
			if (!txt.isDirectory()){
				String name = txt.getName();

				String Idonly = name.substring(0, name.length() - 4);
				//Bukkit.broadcastMessage(Idonly);

				int id = Integer.parseInt(Idonly);
				all.add(new TurretData(id));
			}

		}
		return all;
	}
}
