package com.biel.lobby.mapes;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

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
	static String FolderLiveMetadata = "LiveMetadata";
	static String FolderMaps = "mapes"; 
	static String FolderCopies = "copies";
	private int multiMapId;
	protected Boolean EditMode = false;
	public enum MapMode{SINGLE, MULTIPLE};
	public MapaResetejable() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * Loads the live world. Its files must already be in place: an instance is made
	 * by reserveLiveWorld() on the main thread, copyWorldFiles() off it, then this.
	 */
	public void initialize() {
		loadVirtualWorld();
	}
	public static void cleanupStaleRuntimeWorlds() {
		File metadataRoot = new File(FolderLiveMetadata);
		File[] metadataDirectories = metadataRoot.listFiles(File::isDirectory);
		if (metadataDirectories == null) return;

		File worldContainer = Bukkit.getWorldContainer();
		for (File metadataDirectory : metadataDirectories) {
			String worldName = metadataDirectory.getName();
			if (!worldName.matches("[A-Za-z][A-Za-z0-9 _-]*\\d+")) {
				Com.getPlugin().getLogger().warning("Skipping unexpected live metadata directory: " + worldName);
				continue;
			}
			if (Bukkit.getWorld(worldName) != null) {
				Com.getPlugin().getLogger().warning("Skipping loaded runtime world during startup cleanup: " + worldName);
				continue;
			}

			File legacyWorldDirectory = new File(worldContainer, worldName);
			File paperWorldDirectory = paperDimensionDirectory(worldName);
			try {
				FileUtils.deleteDirectory(legacyWorldDirectory);
				FileUtils.deleteDirectory(paperWorldDirectory);
				FileUtils.deleteDirectory(metadataDirectory);
				Com.getPlugin().getLogger().info("Removed stale runtime world: " + worldName);
			} catch (IOException exception) {
				Com.getPlugin().getLogger().log(java.util.logging.Level.SEVERE,
						"Could not remove stale runtime world " + worldName, exception);
			}
		}
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

	/**
	 * Claims the next free live world name by creating its metadata directory at
	 * once, so two instances being created at the same time cannot pick the same
	 * name. Main thread only.
	 */
	public void reserveLiveWorld(){
		if (getGameName().equals("")) throw new IllegalStateException("A game without a name cannot reserve a world");
		NomWorld = getLiveWorldAvaliableName(FolderLiveMetadata);
		File metadataDirectory = getLiveMetadataFile();
		if (!metadataDirectory.mkdirs()) {
			throw new IllegalStateException("Could not reserve live metadata directory " + metadataDirectory);
		}
	}
	/**
	 * Copies the template into the reserved live folder. Pure file work that touches
	 * nothing in Bukkit, so it runs off the main thread: copying a world inside a
	 * menu click used to freeze the whole server for the duration.
	 */
	public void copyWorldFiles(){
		File worldOrigin = getWorldOriginMappedFile();
		File worldLive = getLiveWorldFile();
		File metadataDirectory = getLiveMetadataFile();
		try {
			copyDirectory(worldOrigin, worldLive);
			File sourceProperties = new File(worldOrigin, "pMapaActual.txt");
			if (sourceProperties.isFile()) {
				FileUtils.copyFile(sourceProperties, new File(metadataDirectory, "pMapaActual.txt"));
			}
			new File(worldLive, "uid.dat").delete();
		} catch (IOException e) {
			throw new IllegalStateException("El mon no s'ha pogut copiar: " + getGameName(), e);
		}
	}
	/** Removes what a creation that never loaded a world left on disk. */
	public void discardLiveWorldFiles(){
		deleteFolder(getLiveWorldFile());
		deleteFolder(getLiveMetadataFile());
	}
	/**
	 * Where Paper 26.2 keeps a world's region files once it has migrated the legacy
	 * folder: {@code world/dimensions/minecraft/<key>}, the key being the world name
	 * lowercased with every character a resource location cannot hold turned into an
	 * underscore ("Arena 42" becomes "arena_42"). Paper refuses to migrate over a
	 * directory that is already there, so a leftover from an earlier instance with the
	 * same name makes the next creation fail.
	 */
	static File paperDimensionDirectory(String worldName) {
		String key = worldName.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9/._-]", "_");
		File dimensionsRoot = new File(new File(new File(Bukkit.getWorldContainer(), "world"), "dimensions"), "minecraft");
		return new File(dimensionsRoot, key);
	}
	private static void deletePaperDimensionDirectory(String worldName) {
		File directory = paperDimensionDirectory(worldName);
		if (!directory.isDirectory()) return;
		try {
			FileUtils.deleteDirectory(directory);
		} catch (IOException exception) {
			Com.getPlugin().getLogger().log(java.util.logging.Level.WARNING,
					"Could not remove the migrated world directory " + directory, exception);
		}
	}
	private void loadVirtualWorld(){
		if (isWorldLoaded()) return;
		// A fresh copy of the template is about to be migrated into this directory.
		deletePaperDimensionDirectory(getLiveWorldFolder());
		world = Bukkit.createWorld(new WorldCreator(getLiveWorldFolder()));
		if (world == null) {
			throw new IllegalStateException("Paper no ha pogut carregar el mon " + getLiveWorldFolder());
		}
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
		return NomWorld;
	}
	public static void deleteLiveWorldsFolder(){
		try {
			FileUtils.deleteDirectory(new File(FolderLiveWorlds));
		} catch (IOException e) {
			Com.getPlugin().getLogger().log(java.util.logging.Level.WARNING, "Error esborrant els mons temporals", e);
		}
	}
	public boolean deleteVirtualWorld(){
		if (world == null) return false;
		File worldLive = world.getWorldFolder();
		File metadataDirectory = getLiveMetadataFile();
		String worldName = NomWorld;
		if (!Bukkit.unloadWorld(world, false)) return false;
		world = null;
		Com.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Com.getPlugin(), () -> {
            deleteFolder(worldLive);
			deleteFolder(metadataDirectory);
			deletePaperDimensionDirectory(worldName);
            Bukkit.broadcastMessage("Mapa esborrat! - " + NomWorld);
        }, 200L);
		return true;
	}
	public void save(){
		if (EditMode){
			world.save();
			//Copy world
			File worldOrigin = getWorldOriginMappedFile();
			File worldLive = world.getWorldFolder();
			String copyName = getLiveWorldAvaliableName(FolderCopies);
			File worldCopy = new File(FolderCopies + "/" + copyName);
			try {
				//Copy
				copyDirectory(worldOrigin, worldCopy);
				//Save
				copyDirectory(worldLive, worldOrigin);
				File liveProperties = new File(getLiveMetadataFile(), "pMapaActual.txt");
				if (liveProperties.isFile()) {
					FileUtils.copyFile(liveProperties, new File(worldOrigin, "pMapaActual.txt"));
				}
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
	private File getLiveMetadataFile() {
		return new File(FolderLiveMetadata, NomWorld);
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
		return new GestorPropietats(new File(getLiveMetadataFile(), "pMapaActual.txt").getPath());
	}
	/**
	 * The properties a template map would start with, read from the template itself,
	 * so a menu can describe a map before any instance of it exists. Null when the
	 * template has no properties file. Pass null for a single-map game.
	 */
	public GestorPropietats pTemplate(String templateMapName){
		File templateFolder = templateMapName == null ? getMapOriginFile() : new File(getMapOriginFile(), templateMapName);
		File properties = new File(templateFolder, "pMapaActual.txt");
		return properties.isFile() ? new GestorPropietats(properties.getPath()) : null;
	}
	public GestorPropietats pTemp(){
		return new GestorPropietats(new File(getLiveMetadataFile(), "pTemp.txt").getPath());
	}
	public GestorPropietats pPlayer(Player ply){
		File playersFolder = new File(getLiveMetadataFile(), "pPlayers");
		if (!playersFolder.exists()) {
			playersFolder.mkdir();
		}
		return new GestorPropietats(new File(playersFolder, ply.getName() + ".txt").getPath());
	}
	@Override
	protected synchronized void gameEvent(Event event) {
		if (!EditMode){
			super.gameEvent(event);
		}		
	}

}
