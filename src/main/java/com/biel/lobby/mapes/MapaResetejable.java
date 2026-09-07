package com.biel.lobby.mapes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.biel.lobby.Com;
import com.biel.lobby.Mapa;
import com.biel.lobby.utilities.GestorPropietats;

/**
 * A game map that starts from a template and is thrown away afterwards.
 *
 * Paper 26.2 keeps every world inside the main save, at
 * {@code <save>/dimensions/<namespace>/<key>}, and chooses that folder from the
 * world's key. Live instances are created under the {@code liveworlds} namespace,
 * so one folder holds an instance whole: its region files and the property files
 * the game reads. Creating an instance copies a template into that folder and
 * loads it; deleting one unloads it and removes the folder.
 *
 * A template is the same folder minus identity: no {@code data/paper/metadata.dat}
 * (the world UUID), no {@code pTemp.txt}, no {@code pPlayers/}. A template that
 * still carries a {@code level.dat} is in the pre-26.2 format and goes through
 * {@link TemplateImport} once, before the game is offered.
 */
public abstract class MapaResetejable extends Mapa {
	static final String LiveWorldNamespace = "liveworlds";
	static final String FolderMaps = "mapes";
	static final String FolderOriginals = "mapes-originals";
	static final String FolderCopies = "copies";
	static final String WorldUuidFile = "data/paper/metadata.dat";
	static final String PropertiesFile = "pMapaActual.txt";
	static final String TempPropertiesFile = "pTemp.txt";
	static final String PlayersFolder = "pPlayers";
	private static final String BackupTimestampFormat = "yyyyMMdd-HHmmss";
	private int multiMapId;
	protected Boolean EditMode = false;
	public enum MapMode{SINGLE, MULTIPLE};
	public MapaResetejable() {
		super();
	}
	/**
	 * Loads the live world. Its files must already be in place: an instance is made
	 * by reserveLiveWorld() on the main thread, copyWorldFiles() off it, then this.
	 */
	public void initialize() {
		loadVirtualWorld();
	}

	//--WHERE A LIVE WORLD LIVES--
	/**
	 * The key of the live world for an instance name, in the live worlds namespace.
	 * Paper derives the path part from the name the way it would for any world, and
	 * names the world after the key ("Arena 42" becomes liveworlds:arena_42, named
	 * liveworlds_arena_42); the instance keeps its own name for players and commands.
	 */
	static NamespacedKey liveWorldKey(String instanceName) {
		return new NamespacedKey(LiveWorldNamespace, new WorldCreator(instanceName).key().getKey());
	}
	/** {@code <save>/dimensions/liveworlds}: the folder Paper gives every live world. */
	@SuppressWarnings("deprecation")
	static File liveWorldsRoot() {
		File mainSave = new File(Bukkit.getWorldContainer(), Bukkit.getUnsafe().getMainLevelName());
		return new File(new File(mainSave, "dimensions"), LiveWorldNamespace);
	}
	static File liveWorldFolder(String worldName) {
		return new File(liveWorldsRoot(), liveWorldKey(worldName).getKey());
	}
	private File getLiveWorldFile() {
		return liveWorldFolder(NomWorld);
	}
	/** Removes every live world folder that no loaded world owns. Startup only. */
	public static void deleteUnloadedLiveWorlds() {
		File[] folders = liveWorldsRoot().listFiles(File::isDirectory);
		if (folders == null) return;
		List<String> loadedFolders = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) loadedFolders.add(canonicalPath(world.getWorldFolder()));
		for (File folder : folders) {
			if (loadedFolders.contains(canonicalPath(folder))) continue;
			try {
				FileUtils.deleteDirectory(folder);
				Com.getPlugin().getLogger().info("Removed unloaded live world folder: " + folder.getName());
			} catch (IOException failure) {
				Com.getPlugin().getLogger().log(Level.SEVERE, "Could not remove live world folder " + folder, failure);
			}
		}
	}
	private static String canonicalPath(File file) {
		try {
			return file.getCanonicalPath();
		} catch (IOException failure) {
			return file.getAbsolutePath();
		}
	}
	private static void deleteLiveWorldFolder(File folder) {
		try {
			FileUtils.deleteDirectory(folder);
		} catch (IOException failure) {
			Com.getPlugin().getLogger().log(Level.SEVERE, "Could not delete live world folder " + folder, failure);
		}
	}

	//--CREATE--
	/** The game name followed by one more than the highest number among its live world folders. */
	private String nextLiveWorldName() {
		String keyPrefix = liveWorldKey(getGameName()).getKey();
		Pattern numbered = Pattern.compile("^" + Pattern.quote(keyPrefix) + "(\\d+)$");
		int highest = 1;
		File[] folders = liveWorldsRoot().listFiles(File::isDirectory);
		if (folders != null) {
			for (File folder : folders) {
				Matcher match = numbered.matcher(folder.getName());
				if (match.matches()) highest = Math.max(highest, Integer.parseInt(match.group(1)));
			}
		}
		return getGameName() + (highest + 1);
	}
	/**
	 * Claims the next free live world name by creating its folder at once, so two
	 * instances being created at the same time cannot pick the same name. Main
	 * thread only.
	 */
	public void reserveLiveWorld(){
		if (getGameName().equals("")) throw new IllegalStateException("A game without a name cannot reserve a world");
		NomWorld = nextLiveWorldName();
		File liveWorld = getLiveWorldFile();
		if (!liveWorld.mkdirs()) {
			throw new IllegalStateException("Could not reserve live world folder " + liveWorld);
		}
	}
	/**
	 * Copies the template into the reserved folder and drops the world UUID that a
	 * template must not carry. Pure file work, so it runs off the main thread.
	 */
	public void copyWorldFiles(){
		try {
			FileUtils.copyDirectory(getWorldOriginMappedFile(), getLiveWorldFile());
			Files.deleteIfExists(new File(getLiveWorldFile(), WorldUuidFile).toPath());
		} catch (IOException failure) {
			throw new IllegalStateException("El mon no s'ha pogut copiar: " + getGameName(), failure);
		}
	}
	/** Removes the folder of a creation that never loaded a world. */
	public void discardLiveWorldFiles(){
		deleteLiveWorldFolder(getLiveWorldFile());
	}
	private void loadVirtualWorld(){
		world = Bukkit.createWorld(new WorldCreator(liveWorldKey(NomWorld)));
		if (world == null) {
			throw new IllegalStateException("Paper no ha pogut carregar el mon " + NomWorld);
		}
		setWorld(world);
	}

	//--DELETE--
	public boolean deleteVirtualWorld(){
		if (world == null) return false;
		File liveWorld = world.getWorldFolder();
		if (!Bukkit.unloadWorld(world, false)) return false;
		world = null;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Com.getPlugin(), () -> {
			deleteLiveWorldFolder(liveWorld);
			Bukkit.broadcastMessage("Mapa esborrat! - " + NomWorld);
		}, 200L);
		return true;
	}

	//--TEMPLATES--
	public boolean isWorld(File folder){
		return new File(folder, "region").isDirectory();
	}
	public MapMode getMapMode(){
		return isWorld(getMapOriginFile()) ? MapMode.SINGLE : MapMode.MULTIPLE;
	}
	/** The template maps of this game: the game itself for a single-map game, else every world folder under it. */
	public ArrayList<String> getMultiWorldList(){
		ArrayList<String> maps = new ArrayList<>();
		if (getMapMode() == MapMode.SINGLE) {
			maps.add(getGameName());
			return maps;
		}
		File[] folders = getMapOriginFile().listFiles(File::isDirectory);
		if (folders == null) return maps;
		for (File folder : folders) {
			if (isWorld(folder)) maps.add(folder.getName());
		}
		return maps;
	}
	public String getMultiMapName() {
		return getActiveMultipleMapName();
	}
	public void setMultiMapId(int multiMapId) {
		this.multiMapId = multiMapId;
	}
	public static ArrayList<String> getAllMapNames(){
		ArrayList<String> result = new ArrayList<>();
		File folder = new File(FolderMaps);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File[] fileEntries = folder.listFiles();
		for(File fileEntry : fileEntries){
			if (fileEntry.isDirectory()){
				result.add(fileEntry.getName());
			}
		}
		return result;
	}
	private File getMapOriginFile() {
		return new File(FolderMaps, getGameName());
	}
	private File templateFolder(String mapName) {
		return getMapMode() == MapMode.SINGLE ? getMapOriginFile() : new File(getMapOriginFile(), mapName);
	}
	private File getWorldOriginMappedFile() {
		return templateFolder(getActiveMultipleMapName());
	}
	/**
	 * @return The name of the current map. To get the game's name, please use getGameName().
	 */
	public String getActiveMultipleMapName() {
		MapMode m = getMapMode();
		if(m == MapMode.SINGLE)return getGameName();
		return getMultiWorldList().get(multiMapId);
	}
	/**
	 * Brings every template of this game that is still in the pre-26.2 format into
	 * the current one. False when one of them could not be imported.
	 */
	public boolean importOriginalTemplates(){
		boolean allImported = true;
		for (String mapName : getMultiWorldList()) {
			File template = templateFolder(mapName);
			if (!TemplateImport.isOriginal(template)) continue;
			File archive = getMapMode() == MapMode.SINGLE
					? new File(FolderOriginals, getGameName())
					: new File(new File(FolderOriginals, getGameName()), mapName);
			try {
				TemplateImport.run(template, archive);
			} catch (IOException | RuntimeException failure) {
				Com.getPlugin().getLogger().log(Level.SEVERE, "Could not import template " + template, failure);
				allImported = false;
			}
		}
		return allImported;
	}

	//--EDIT MODE--
	public Boolean getEditMode() {
		return EditMode;
	}
	public void setEditMode(Boolean editMode) {
		EditMode = editMode;
		sendGlobalMessage("Mode edició = " + Boolean.toString(editMode));
	}
	/** Writes the live world back over its template, keeping the previous template under copies/. */
	public void save(){
		if (!EditMode){
			Bukkit.broadcastMessage("Ha fallat l'operació: No hi ha el mode d'edició activat");
			return;
		}
		world.save(true);
		File template = getWorldOriginMappedFile();
		File liveWorld = world.getWorldFolder();
		String backupName = getGameName() + "-" + getActiveMultipleMapName() + "-" + new SimpleDateFormat(BackupTimestampFormat).format(new Date());
		File backup = new File(FolderCopies, backupName);
		try {
			FileUtils.copyDirectory(template, backup);
			FileUtils.copyDirectory(liveWorld, template);
			stripInstanceState(template);
			Bukkit.broadcastMessage(ChatColor.GOLD + "Mapa guardat (" + NomWorld + "), copia de seguretat (" + backupName + ")");
		} catch (IOException failure) {
			Com.getPlugin().getLogger().log(Level.SEVERE, "Could not save " + NomWorld + " over " + template, failure);
			Bukkit.broadcastMessage("El mon no s'ha pogut copiar (guardant)");
		}
	}
	/** What a template must not carry: the world UUID and the per-match files. */
	private static void stripInstanceState(File template) throws IOException {
		Files.deleteIfExists(new File(template, WorldUuidFile).toPath());
		Files.deleteIfExists(new File(template, TempPropertiesFile).toPath());
		FileUtils.deleteDirectory(new File(template, PlayersFolder));
	}

	//--PROPERTIES--
	public GestorPropietats pMapaActual(){
		return new GestorPropietats(new File(getLiveWorldFile(), PropertiesFile).getPath());
	}
	/**
	 * The properties a template map would start with, read from the template itself,
	 * so a menu can describe a map before any instance of it exists. Null when the
	 * template has no properties file. Pass null for a single-map game.
	 */
	public GestorPropietats pTemplate(String templateMapName){
		File template = templateMapName == null ? getMapOriginFile() : new File(getMapOriginFile(), templateMapName);
		File properties = new File(template, PropertiesFile);
		return properties.isFile() ? new GestorPropietats(properties.getPath()) : null;
	}
	public GestorPropietats pTemp(){
		return new GestorPropietats(new File(getLiveWorldFile(), TempPropertiesFile).getPath());
	}
	public GestorPropietats pPlayer(Player ply){
		File playersFolder = new File(getLiveWorldFile(), PlayersFolder);
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
