package com.biel.FastSurvival.SpecialItems;

import java.io.File;

import org.bukkit.Bukkit;

import com.biel.FastSurvival.Utils.GestorPropietats;

public class SpecialItemData {
	int iId;

	public SpecialItemData(int iId) {
		super();
		this.iId = iId;
	}

	//Data def
	//GET
	public int getID(){
		return getProps().ObtenirPropietatInt("id");
	}
	public Double getValue(){
		return getProps().ObtenirPropietatDouble("value");
	}
	public Double getMaxValue(){
		return getProps().ObtenirPropietatDouble("maxvalue");
	}
	public Double getModifier(){
		return getProps().ObtenirPropietatDouble("mod");
	}
	public long getLastTime(){
		return getProps().ObtenirPropietatLong("lastTime");
	}
	//SET
	public void setID(int value){
		getProps().EstablirPropietat("id", value);
	}
	public void setValue(Double value){
		getProps().EstablirPropietat("value", value);
	}
	public void setMaxValue(Double value){
		getProps().EstablirPropietat("maxvalue", value);
	}
	public void setModifier(Double value){
		getProps().EstablirPropietat("mod", value);
	}
	public void setLastTime(long value){
		getProps().EstablirPropietat("lastTime", value);
	}
	//HAS
	public Boolean hasID(){
		return getProps().ExisteixPripietat("id");
	}
	public Boolean hasValue(){
		return getProps().ExisteixPripietat("value");
	}
	public Boolean hasMaxValue(){
		return getProps().ExisteixPripietat("maxvalue");
	}
	public Boolean hasModifier(){
		return getProps().ExisteixPripietat("mod");
	}
	public Boolean hasLastTime(){
		return getProps().ExisteixPripietat("lastTime");
	}
	//----
	public GestorPropietats getProps(){
		String folder = getFolder();
		String rutaArxiuPropietats = folder + "/" + Integer.toString(iId) + ".txt";
		return new GestorPropietats(rutaArxiuPropietats);
	}

	static String getFolder() {
		String folder = "SpecialItemData";
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
}
