package com.biel.lobby.mapes;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.biel.lobby.Com;
import com.biel.lobby.Mapa;
import com.biel.lobby.utilities.GestorPropietats;


public abstract class MapaResetejable extends Mapa {
	static String FolderLiveWorlds = "LiveWorlds"; 
	static String FolderMaps = "mapes"; 
	static String FolderCopies = "copies";
	private int multiMapId;
	protected Boolean EditMode = false;
	public enum MapMode{SINGLE, MULTIPLE};
	public MapaResetejable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void initialize() {
		createVirtualWorld();
	}
	String getLiveWorldAvaliableName(String where){
		String nouNom = "";
		int LastNum = 1;
		File folder = new File(where);
		if (!folder.exists()) {
			folder.mkdir();
		}
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()){
				String nomArxiu = fileEntry.getName();
				int mapLength = getGameName().length() ; // -1
				if (nomArxiu.length() < mapLength){continue;}
				//Bukkit.broadcastMessage("Substring: " + nomArxiu.substring(mapLength));
				if(getGameName().equals(nomArxiu.substring(0, mapLength))){
					int num = Integer.parseInt(nomArxiu.substring(mapLength));
					if (num > LastNum){
						LastNum = num;
					}
					//nouNom = getMapName() + Integer.toString(num + 1);
					//Bukkit.broadcastMessage("Numero: " + Integer.toString(num) + "---------" + nomArxiu.substring(0, mapLength));
				}
			}
		}
		nouNom = getGameName() + Integer.toString(LastNum + 1);
		if (nouNom.equals("")){
			nouNom = getGameName() + "1";
		}
		//Bukkit.broadcastMessage(nouNom);
		return nouNom;
	}

	void createVirtualWorld(){
		if (getGameName().equals("")){return;}
		if (isWorldLoaded() == true){return;}
		NomWorld = getLiveWorldAvaliableName(FolderLiveWorlds);
		//Copy world
		File worldOrigin = getWorldOriginMappedFile();
		File worldLive = getLiveWorldFile();
		try {
			copyDirectory(worldOrigin, worldLive);
			File uid = new File(worldLive.getPath() + "/" + "uid.dat");
			uid.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Bukkit.broadcastMessage("El mon no s'ha pogut copiar");
			e.printStackTrace();
		}
		//-----
		world = Bukkit.createWorld(new WorldCreator(getLiveWorldFolder()));
		updateWorldToRegisteredHandler();
	}
	private void updateWorldToRegisteredHandler() {
		setWorld(world);
	}
	//--MAP-MODE--
	public boolean isWorld(File folder){
		ArrayList<String> result = new ArrayList<>();
		if (!folder.exists()) return false;
		File[] fileEntries = folder.listFiles((dir, name) -> name.equals("region"));
		return fileEntries.length != 0;
	}
	public MapMode getMapMode(){
		return isWorld(getMapOriginFile()) ? MapMode.SINGLE : MapMode.MULTIPLE;
	}
	public ArrayList<String> getMultiWorldList(){

		ArrayList<String> r = new ArrayList<>();

		if(getMapMode() == MapMode.MULTIPLE) {

			File folder = getMapOriginFile();
			File[] files = folder.listFiles();

			if(files != null && files.length > 0) {
				for(File f : files) {
					if(f.isDirectory()){
						r.add(f.getName());
					}
				}
			}

		}
		if(getMapMode() == MapMode.SINGLE) {
			r.add(getMapName());


		}
		return r;
	}
	public String getMultiMapName() {
		return getActiveMultipleMapName();
	}
	public void setMultiMapId(int multiMapId) {
		this.multiMapId = multiMapId;
	}
	//------------
	public static ArrayList<String> getAllMapNames(){
		ArrayList<String> result = new ArrayList<>();
		File folder = new File(FolderMaps);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File[] fileEntries = folder.listFiles();
		for(File fileEntry : fileEntries){
			if (fileEntry.isDirectory()){
				String nomArxiu = fileEntry.getName();
				result.add(nomArxiu);
			}
		}
		return result;
	}
	private String getLiveWorldFolder() {
		return FolderLiveWorlds + "/" + NomWorld;
	}
	public static void deleteLiveWorldsFolder(){
		try {
			FileUtils.deleteDirectory(new File(FolderLiveWorlds));
		} catch (IOException e) {
			System.out.println("Error borrando los liveworlds");
		}
	}
	public void deleteVirtualWorld(){
		Bukkit.unloadWorld(world, false);
		Com.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Com.getPlugin(), () -> {
            File worldLive = getLiveWorldFile();
            deleteFolder(worldLive);
            Bukkit.broadcastMessage("Mapa esborrat! - " + NomWorld);
        }, 200L);

	}
	public void save(){
		if (EditMode){
			world.save();
			//Copy world
			File worldOrigin = getWorldOriginMappedFile();
			File worldLive = getLiveWorldFile();
			String copyName = getLiveWorldAvaliableName(FolderCopies);
			File worldCopy = new File(FolderCopies + "/" + copyName);
			try {
				//Copy
				copyDirectory(worldOrigin, worldCopy);
				//Save
				copyDirectory(worldLive, worldOrigin);
				Bukkit.broadcastMessage(ChatColor.GOLD + "Mapa guardat (" + NomWorld + "), copia de seguretat (" + copyName + ")");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Bukkit.broadcastMessage("El mon no s'ha pogut copiar (guardant)");
				e.printStackTrace();
			}
		}else{
			Bukkit.broadcastMessage("Ha fallat l'operació: No hi ha el mode d'edició activat");
		}
	}
	private File getLiveWorldFile() {
		return new File(getLiveWorldFolder());
	}
	private File getMapOriginFile() {
		return new File(FolderMaps + "/" + getGameName());
	}
	private File getWorldOriginMappedFile() {
		MapMode m = getMapMode();
		if(m == MapMode.SINGLE)return new File(FolderMaps + "/" + getGameName());
		if(m == MapMode.MULTIPLE)return new File(FolderMaps + "/" + getGameName() + "/" + getActiveMultipleMapName());
		return null;
	}
	/**
	 * @return The name of the current map. To get the game's name, please use getGameName().
	 */
	public String getActiveMultipleMapName() {
		MapMode m = getMapMode();
		if(m == MapMode.SINGLE)return getGameName();
		return getMultiWorldList().get(multiMapId);
	}
	public Boolean getEditMode() {
		return EditMode;
	}
	public void setEditMode(Boolean editMode) {
		EditMode = editMode;
		sendGlobalMessage("Mode edició = " + Boolean.toString(editMode));
	}
	public GestorPropietats pMapaActual(){
		return new GestorPropietats(getLiveWorldFolder() + "/" + "pMapaActual.txt");
	}
	public GestorPropietats pTemp(){
		return new GestorPropietats(getLiveWorldFolder() + "/" + "pTemp.txt");
	}
	public GestorPropietats pPlayer(Player ply){
		File playersFolder = new File(getLiveWorldFolder() + "/" + "pPlayers");
		if (!playersFolder.exists()) {
			playersFolder.mkdir();
		}
		return new GestorPropietats(getLiveWorldFolder() + "/" + "pPlayers" + "/" + ply.getName() + ".txt");
	}
	@Override
	protected synchronized void gameEvent(Event event) {
		if (!EditMode){
			super.gameEvent(event);
		}		
	}

}
