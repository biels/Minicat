package com.biel.lobby;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.biel.lobby.utilities.PaperMessages;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.Pair;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.MapaResetejable;
import com.biel.lobby.mapes.MapaResetejable.MapMode;
import com.biel.lobby.mapes.jocs.*;


public class GestorMapes implements Listener{
	public enum InstanceRemovalResult { REMOVED, NOT_FOUND, HAS_PLAYERS, EDIT_MODE, UNLOAD_FAILED }
	public enum InstanceJoinResult { JOINED, NOT_FOUND, UNAVAILABLE }
	public lobby plugin;
	ArrayList<Pair<String, Double>> auto_ratings;
	ArrayList<ContenidorMapa> Mapes = new ArrayList<>();
	public GestorMapes() {

		this.plugin = lobby.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		//Mapes.add(new ContenidorJoc(ObsidianDefenders.class, "Obsidian defenders", Material.OBSIDIAN, DevelopmentState.NotWorking));
		Mapes.add(new ContenidorJoc(Spleef.class, "Spleef", Material.SNOW, DevelopmentState.Release));
		Mapes.add(new ContenidorJoc(RainbowClay.class, "Rainbow Clay", Material.RED_TERRACOTTA, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(Torres.class, "Torres de defensa", Material.ARROW, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(Quakecraft.class, "Quakecraft", Material.STONE_HOE, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(Dominion.class, "Dominion", Material.DIAMOND, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(TeamDeathMatch.class, "Team Death Match", Material.IRON_SWORD, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(Arena4.class, "Arena 4", Material.BAKED_POTATO, DevelopmentState.Alpha));
		//Mapes.add(new ContenidorJoc(Coliseu.class, "Coliseu", Material.QUARTZ_BLOCK, DevelopmentState.NotWorking));
		//Mapes.add(new ContenidorJoc(TheTowers.class, "The Towers", Material.EXPERIENCE_BOTTLE, DevelopmentState.NotWorking));
		Mapes.add(new ContenidorJoc(TNTRun.class, "TNT Run", Material.TNT, DevelopmentState.KnownIssues));
		//Mapes.add(new ContenidorJoc(RoboRampage.class, "ToTheSky", Material.LAPIS_BLOCK, DevelopmentState.InDevelopment));
		//Mapes.add(new ContenidorJoc(Arena1v1.class, "Arena 1v1", Material.WOODEN_SWORD, DevelopmentState.PreAlpha));
		Mapes.add(new ContenidorJoc(ArenaAllvAll.class, "Arena ALLvsALL", Material.SAND, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(BaseLunar.class, "Base Lunar", Material.GLASS, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(BoletumDTC.class, "Boletus DTC", Material.MUSHROOM_STEW, DevelopmentState.Beta));
		//Mapes.add(new ContenidorJoc(DominionTitan.class, "Dominion Titan", Material.DIAMOND_AXE, DevelopmentState.Beta));
		//Mapes.add(new ContenidorJoc(DominionKOTH.class, "Dominion KOTH", Material.DIAMOND_BARDING, DevelopmentState.Beta));
		//Mapes.add(new ContenidorJoc(TeamDeathMatchJaneatorForest.class, "TDM Janeator Forest", Material.SAPLING, DevelopmentState.Alpha));
		//Mapes.add(new ContenidorJoc(InfernoRush.class, "Inferno Rush", Material.BLAZE_POWDER, DevelopmentState.InDevelopment));
		Mapes.add(new ContenidorJoc(KingSkeletonChallenge.class, "King Skeleton", Material.GOLDEN_HELMET, DevelopmentState.PreAlpha));
		//Mapes.add(new ContenidorJoc(WarehouseKOTH.class, "Warehouse KOTH", Material.OAK_PLANKS DevelopmentState.Beta));
		//Mapes.add(new ContenidorJoc(OniChan.class, "Oni-Chan", Material.CAKE, DevelopmentState.InDevelopment));
		//Mapes.add(new ContenidorJoc(PixelRift.class, "Pixel Rift", Material.ITEM_FRAME, DevelopmentState.KnownIssues));
		//Mapes.add(new ContenidorJoc(ResourceRush.class, "Resource Rush", Material.DIAMOND_ORE, DevelopmentState.InDevelopment));
		Mapes.add(new ContenidorJoc(Parkour.class, "ParkourFlow", Material.GOLD_BLOCK, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(InkWars.class, "Ink Wars", Material.COAL_BLOCK, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(RedstoneWars.class, "Redstone Wars", Material.REDSTONE_BLOCK, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(PilotaSplash.class, "Pilota Splash", Material.SLIME_BALL, DevelopmentState.Alpha));
		//Mapes.add(new ContenidorJoc(TempleQuest.class, "Temple Quest", Material.QUARTZ_BLOCK, DevelopmentState.InDevelopment));
		Mapes.add(new ContenidorJoc(OneInTheChamber.class, "OneInTheChamber", Material.BOW, DevelopmentState.Alpha));
		// No Mapes/BedWars template exists in either recovered server tree.
		// Keep the implementation source, but do not advertise an instance that cannot be created.
		// Mapes.add(new ContenidorJoc(BedWars.class, "Bed Wars", Material.RED_BED, DevelopmentState.InDevelopment));
	}
	public void queryAutoRatings() {
		auto_ratings = Com.getDataAPI().getAutoRating();
		Mapes.sort((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()));
	}
	public void ObrirMenuMapes(Player ply){
		queryAutoRatings();
		IconMenu menu = new IconMenu(ChatColor.RED + "Tots els mapes", (int) (9 * (Math.ceil(Mapes.size() / 9) + 1)), event -> {

			event.setWillClose(false);
            int pos = event.getPosition();
            ContenidorMapa cont = Mapes.get(pos);
            cont.playerClick(event.getPlayer());

        });

		for(ContenidorMapa mapa : Mapes){

			int count = 1;
			if(mapa.getPlayerAmount() > 0 ) count = mapa.getPlayerAmount();

			ItemStack icon = new ItemStack(mapa.mat, count);

			menu.setOption(Mapes.indexOf(mapa), icon, mapa.getDisplayName(), mapa.getDescription());

		}


		menu.open(ply);
	}
	public ArrayList<Mapa> getAllInstances(){
		ArrayList<Mapa> all = new ArrayList<>();
		for (ContenidorMapa c : Mapes){
			if (c instanceof ContenidorJoc){
				ContenidorJoc contenidorJoc = (ContenidorJoc) c;
				all.addAll(contenidorJoc.getInstàncies());
			}

		}
		return all;
	}
	public List<Joc> getAllGameInstances(){
		return getAllInstances().stream()
				.filter(Joc.class::isInstance)
				.map(Joc.class::cast)
				.collect(Collectors.toList());
	}
	public InstanceRemovalResult removeGameInstance(String worldName){
		for (ContenidorMapa container : Mapes){
			if (!(container instanceof ContenidorJoc)) continue;
			ContenidorJoc gameContainer = (ContenidorJoc) container;
			for (Joc game : new ArrayList<>(gameContainer.Instàncies)){
				if (game.getMapName().equalsIgnoreCase(worldName)){
					return gameContainer.removeMap(game);
				}
			}
		}
		return InstanceRemovalResult.NOT_FOUND;
	}
	public InstanceJoinResult joinGameInstance(Player player, String worldName){
		for (Joc game : getAllGameInstances()){
			if (!game.getMapName().equalsIgnoreCase(worldName)) continue;
			if (!game.canJoin(player)) return InstanceJoinResult.UNAVAILABLE;
			game.Join(player);
			return InstanceJoinResult.JOINED;
		}
		return InstanceJoinResult.NOT_FOUND;
	}
	public Mapa getMapWherePlayerIs(Player p){
		if (lobby.isOnLobby(p)){return null;}
		for(Mapa m : getAllInstances()){
			if(m.getWorld().getPlayers().contains(p)){
				return m;
			}
		}
		//Bukkit.broadcastMessage("Jugador desaparegut");
		return null;
	}
	/** The registry entry for a game class, or null when the class is not registered. */
	public ContenidorJoc getGameContainer(Class<?> gameClass){
		for (ContenidorMapa container : Mapes){
			if (container instanceof ContenidorJoc && container.ClassMapa == gameClass) return (ContenidorJoc) container;
		}
		return null;
	}
	/** Starts creating an instance of the named game; null when no such game is registered. */
	public CompletableFuture<Joc> createGameInstance(String gameName, Integer mapId){
		for (ContenidorMapa container : Mapes){
			if (container instanceof ContenidorJoc && container.ClassMapa.getSimpleName().equalsIgnoreCase(gameName)){
				return ((ContenidorJoc) container).addMap(mapId);
			}
		}
		return null;
	}
	public abstract class ContenidorMapa implements Listener{
		public lobby plugin;

		Class<?> ClassMapa;
		String nom;
		Material mat;
		public String getNom() {
			return nom;
		}
		public ArrayList<String> getDescription(){
			ArrayList<String> l = new ArrayList<>();
			int playerAmount = getPlayerAmount();
			if(playerAmount > 0)l.add(ChatColor.GREEN + "" + playerAmount + " jugador" + (playerAmount > 1 ? "s" : ""));
			return l;
		}
		public double getRating(){
			return 0;
		}
		public abstract int getPlayerAmount();
		public abstract int getMapCount();

		public String getDisplayName() {

			if(this.getMapCount() == 0) {
				return ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + nom + ChatColor.RESET;
			}

			return ChatColor.BOLD + nom + ChatColor.RESET;

		}
		public void setNom(String nom) {
			this.nom = nom;
		}


		public ContenidorMapa(Class<?> classMapa, String nom, Material mat) {
			plugin = lobby.getPlugin();
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			ClassMapa = classMapa;
			this.nom = nom;
			this.mat = mat;

		}
		abstract void playerClick(Player ply);

	}
	/**
	 * How finished a game is. Besides labelling the menu it sets the rating weight a
	 * game plays for unless its map says otherwise: experimental games still count,
	 * but lightly, so a broken round cannot move anyone far.
	 */
	public enum DevelopmentState {
		NotWorking(0), KnownIssues(2), InDevelopment(2), PreAlpha(2), Alpha(4), Beta(8), Release(12);
		private final int defaultEloK;
		DevelopmentState(int defaultEloK) {
			this.defaultEloK = defaultEloK;
		}
		/** Rating weight (ELO K) for games in this state when the map defines no K of its own. */
		public int getDefaultEloK() {
			return defaultEloK;
		}
	}
	public class ContenidorJoc extends ContenidorMapa{
		public ContenidorJoc(Class<?> classMapa, String nom, Material mat, DevelopmentState s) {
			super(classMapa, nom, mat);
			// TODO Auto-generated constructor stub
			developmentState = s;
		}
		ArrayList<Joc> Instàncies = new ArrayList<>();

		DevelopmentState developmentState = DevelopmentState.Release;
		public DevelopmentState getDevelopmentState() {
			return developmentState;
		}
		public void setDevelopmentState(DevelopmentState developmentState) {
			this.developmentState = developmentState;
		}
		public String getDevelopmentString(){
			switch(developmentState){
			case InDevelopment:
				return ChatColor.GREEN + "[En desenvolupament]";
			case Alpha:
				return ChatColor.DARK_RED + "[Alpha]";
			case Beta:
				return ChatColor.GOLD + "[Beta]";
			case NotWorking:
				return ChatColor.STRIKETHROUGH + "" + ChatColor.RED + "[No Funciona]";
			case KnownIssues:
				return ChatColor.RED + "[Errors coneguts]";
			case PreAlpha:
				return ChatColor.RED + "[Pre-Alpha]";
			case Release:
				return "";
			default:
				break;
			}
			return "";
		}
		@Override
		public ArrayList<String> getDescription() {

			ArrayList<String> l = super.getDescription();
			l.add(0, getRatingString() + ChatColor.DARK_GRAY + " (" + Math.round(getRating() * 10D) / 10D + "%)");
			l.add(1, rankingLine(developmentState.getDefaultEloK()));

			if(this.getMapCount() == 0) {
				l.add(2, ChatColor.RED + "No hi ha mapes disponibles");
			}

			return l;
		}
		/** What a game or map plays for, as the menu shows it; maps may override the game's default K. */
		String rankingLine(double baseK){
			if (!plugin.isInRankedMode()) return ChatColor.DARK_GRAY + "Rànquing desactivat al servidor";
			return Joc.describeRanking(baseK);
		}
		@Override
		public int getPlayerAmount() {
			// TODO Auto-generated method stub
			return Instàncies.stream().mapToInt(j -> j.getPlayers().size()).sum();
		}

		@Override
		public int getMapCount() {

			Joc tempInstance = getTempInstance();
			return tempInstance.getMultiWorldList().size();

		}

		@Override
		void playerClick(Player ply) {
			// TODO Auto-generated method stub
			ObrirMenu(ply);
		}
		@Override
		public String getDisplayName() {
			// TODO Auto-generated method stub
			return super.getDisplayName() + " " + getDevelopmentString();
		}
		public Joc getTempInstance(){
			try {
				return (Joc) ClassMapa.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		/** Creations under way, by template map id (-1 for a single-map game). */
		private final Map<Integer, CompletableFuture<Joc>> pendingCreations = new HashMap<>();

		/**
		 * Creates an instance without freezing the server: the world folder is copied
		 * off the main thread and the world is loaded on it once the copy is done. A
		 * second request for the same template while one is under way gets the pending
		 * one instead of starting another. The future completes on the main thread.
		 */
		public CompletableFuture<Joc> addMap(Integer map){
			Integer templateKey = map == null ? -1 : map;
			CompletableFuture<Joc> pending = pendingCreations.get(templateKey);
			if (pending != null) return pending;

			CompletableFuture<Joc> creation = new CompletableFuture<>();
			Joc newInstance;
			try {
				newInstance = (Joc) ClassMapa.newInstance();
				if(map != null){
					newInstance.setMultiMapId(map);
				}
				newInstance.reserveLiveWorld();
			} catch (ReflectiveOperationException | RuntimeException e) {
				plugin.getLogger().log(Level.SEVERE, "Could not start creating an instance of " + nom, e);
				creation.completeExceptionally(e);
				return creation;
			}
			pendingCreations.put(templateKey, creation);
			Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
				RuntimeException copyFailure = null;
				try {
					newInstance.copyWorldFiles();
				} catch (RuntimeException e) {
					copyFailure = e;
				}
				RuntimeException outcome = copyFailure;
				Bukkit.getScheduler().runTask(plugin, () -> finishCreation(templateKey, creation, newInstance, outcome));
			});
			return creation;
		}
		private void finishCreation(Integer templateKey, CompletableFuture<Joc> creation, Joc newInstance, RuntimeException copyFailure){
			pendingCreations.remove(templateKey);
			RuntimeException failure = copyFailure;
			if (failure == null) {
				try {
					newInstance.initialize();
					Instàncies.add(newInstance);
					creation.complete(newInstance);
					return;
				} catch (RuntimeException e) {
					failure = e;
				}
			}
			plugin.getLogger().log(Level.SEVERE, "Could not create an instance of " + nom, failure);
			if (newInstance.getWorld() != null) {
				newInstance.deleteVirtualWorld();
			} else {
				newInstance.discardLiveWorldFiles();
			}
			newInstance.destroyEventBus();
			creation.completeExceptionally(failure);
		}
		/** Sends the player into an instance once it exists, and tells them if it never does. */
		void joinWhenCreated(CompletableFuture<Joc> creation, Player ply){
			PaperMessages.sendActionBar(ply, ChatColor.YELLOW + "Creant una instància de " + nom + "...", 100);
			ply.playSound(ply.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.4F);
			creation.whenComplete((game, failure) -> {
				if (!ply.isOnline()) return;
				if (failure != null) {
					ply.sendMessage(ChatColor.RED + "No s'ha pogut crear la instància de " + nom + ".");
					return;
				}
				if (!lobby.isOnLobby(ply)) {
					// They went somewhere else while the world was being copied; do not
					// pull them out of it, just tell them where the new one is.
					ply.sendMessage(ChatColor.GRAY + "La instància " + game.getMapName() + " ja està llesta: /minicatjoin " + game.getMapName());
					return;
				}
				game.Join(ply);
			});
		}
		Boolean AlgunMapaDisponible(){
			for(Joc map : Instàncies){
				if (map.getGameState() == Joc.GameState.WaitingForPlayers){
					return true;
				}
			}
			return false;
		}
		Joc getInstànciaFromWorld(World world){
			for(Joc map : Instàncies){
				if (map.world.getName().equals(world.getName())){
					return map;
				}
			}
			return null;
		}
		@EventHandler
		public void onPlayerChangedWorld(PlayerChangedWorldEvent evt) {

			Player ply = evt.getPlayer();
			Joc map = getInstànciaFromWorld(evt.getFrom());

			if (map != null) {

				checkNecessary(map);
			}
		}

		@EventHandler
		public void onPlayerQuit(PlayerQuitEvent evt) {
			Joc map = getInstànciaFromWorld(evt.getPlayer().getWorld());
			if (map == null) return;
			Bukkit.getScheduler().runTask(plugin, () -> checkNecessary(map));
		}

		void checkNecessary(Joc map){
			if (!Instàncies.contains(map) || map.getWorld() == null) return;

			if(map.getEditMode())return;

			if (map.getWorld().getPlayers().size() == 0){
				if (map.JocEnMarxa()) map.JocFinalitzat();
				removeMap(map);
			}
		}
		InstanceRemovalResult removeMap(Joc map){
			if (!Instàncies.contains(map)) return InstanceRemovalResult.NOT_FOUND;
			if (map.getWorld() == null) return InstanceRemovalResult.UNLOAD_FAILED;
			if (!map.getWorld().getPlayers().isEmpty()) return InstanceRemovalResult.HAS_PLAYERS;
			if (map.getEditMode()) return InstanceRemovalResult.EDIT_MODE;
			if (!map.deleteVirtualWorld()) return InstanceRemovalResult.UNLOAD_FAILED;
			map.clearAllExternals();
			Instàncies.remove(map);
			map.destroyEventBus();
			return InstanceRemovalResult.REMOVED;
		}
		public DyeColor getGameColor(Joc joc){
			switch(joc.getGameState()){
			case InGame:
				return DyeColor.RED;
			case Preparing:
				return DyeColor.YELLOW;
			case WaitingForPlayers:
				return DyeColor.GREEN;
			case Complete:
				return DyeColor.PURPLE;
			case Resetejant:
				return DyeColor.GRAY;
			case Editant:
				return DyeColor.BLUE;
			default:
				break;
			}
			return DyeColor.WHITE;
		}
		public double getRating(){
			Joc tempInstance = getTempInstance();
			return Math.sqrt(auto_ratings.stream().filter(p -> p.getFirst().equals(tempInstance.getGameName())).mapToDouble(Pair::getSecond).findAny().orElse(0) / 100D) * 100D;
		}
		public String getRatingString(){
			Character c = '\u272E';
			int n = 10;
			double rating = getRating();
			int colorPoint = (int) Math.round(rating / n);
			String r = "";
			ChatColor tcolor;
			tcolor = ChatColor.GOLD;
			if(rating >= 75)tcolor = ChatColor.AQUA;
			r += tcolor; 
			for (int i = 0; i < n; i++) {
				if(i == colorPoint){r += ChatColor.GRAY;}
				r += c;
			}
			return r ;
		}
		public void ObrirMenu(Player ply){
			Joc tempInstance = getTempInstance();
			IconMenu menu = new IconMenu("Instàncies disponibles", 27, event -> {

                event.setWillClose(false);
                Joc tempInstance1 = getTempInstance();

                int pos = event.getPosition();
                MapMode m = tempInstance1.getMapMode();

                if (pos < (m == MapMode.MULTIPLE ? 27 - tempInstance1.getMultiWorldList().size() : 26)){
                    Joc map = Instàncies.get(pos); /*Open*/
                    map.Join(event.getPlayer());
                } else {
                    event.setWillClose(true);
                    joinWhenCreated(addMap(m == MapMode.MULTIPLE ? 26 - event.getPosition() : null), event.getPlayer()); /*New*/
                }

            });

			if(this.getMapCount() == 0) {

				String msg = ChatColor.RED + "" + ChatColor.ITALIC + "No hi ha mapes disponibles";
				PaperMessages.sendActionBar(ply, msg, 150);
				ply.playSound(ply.getLocation(), Sound.ENTITY_VILLAGER_NO, 100.0F, 1.0F);
				return;

			}

			for(Joc mapa : Instàncies){
				ItemStack stack = new ItemStack(getWoolMaterial(getGameColor(mapa)));
				stack.setAmount(Math.max(1, mapa.getPlayers().size()));
				String tStr = new SimpleDateFormat("mm:ss").format(new Date(mapa.tempsTranscorregut()));//Integer.toString(mapa.segonsTranscorreguts());
				double gameProgressETA = mapa.getGameProgressETA();
				String progressStr = ChatColor.AQUA + "Progrés: " + Math.round(gameProgressETA * 1000) / 10 + "% ETA";
				menu.setOption(Instàncies.indexOf(mapa), stack,
						ChatColor.GREEN + "ENTRAR: " + mapa.NomWorld,
						ChatColor.WHITE + mapa.getGameName(),
						ChatColor.WHITE + mapa.getGameState().name(),
						rankingLine(mapa.getEloBaseK()),
						ChatColor.GREEN + "Jugadors: " + Integer.toString(mapa.getPlayers().size()),
						ChatColor.YELLOW + "Espectadors: " + mapa.getSpectators().size(),
						"Temps: " + tStr, progressStr);
			}
			MapMode mapMode = tempInstance.getMapMode();
			if (!AlgunMapaDisponible() || mapMode == MapMode.MULTIPLE){
				if(mapMode == MapMode.SINGLE)menu.setOption(26, new ItemStack(Material.EMERALD, 1),
						ChatColor.GREEN + "Afegeix", ChatColor.WHITE + "Crea una nova instància",
						rankingLine(tempInstance.getTemplateEloBaseK(null)));
				if(mapMode == MapMode.MULTIPLE){
					ArrayList<String> multiWorldList = tempInstance.getMultiWorldList();
					for (int i = 0; i < multiWorldList.size(); i++) {
						String name = multiWorldList.get(i);
						menu.setOption(26 - i, new ItemStack(Material.EMERALD, 1),
								ChatColor.GREEN + name, ChatColor.WHITE + "Crear una nova instància",
								rankingLine(tempInstance.getTemplateEloBaseK(name)));
					}
				}
			}

			menu.open(ply);
		}

		public ArrayList<Joc> getInstàncies() {
			return Instàncies;
		}
		public void setInstàncies(ArrayList<Joc> instàncies) {
			Instàncies = instàncies;
		}

	}

	public void openAllGamesMenu(Player ply){
		IconMenu menu = new IconMenu("Tots els jocs", 27, event -> {

            event.setWillClose(true);
            List<Joc> allInstances = getGames();

            int pos = event.getPosition();
            Joc joc = allInstances.get(pos);
            joc.Join(event.getPlayer());
        });
		List<Joc> games = getGames();
		for(Joc mapa : games){
			ItemStack stack = new ItemStack(Material.BLACK_WOOL);
			stack.setAmount(Math.max(1, mapa.getPlayers().size()));
			String tStr = new SimpleDateFormat("mm:ss").format(new Date(mapa.tempsTranscorregut()));//Integer.toString(mapa.segonsTranscorreguts());
			menu.setOption(games.indexOf(mapa), stack, mapa.getGameName(),ChatColor.WHITE + mapa.getGameName() + " (" + mapa.NomWorld + ")", ChatColor.WHITE + mapa.getGameState().name(), ChatColor.GREEN + "Jugadors: " + Integer.toString(mapa.getPlayers().size()), ChatColor.YELLOW + "Espectadors:" + mapa.getSpectators().size(), "Temps: " + tStr);
		}	

		menu.open(ply);
	}
	//	public ItemStack getIconForInstance(Mapa m){
	//		ItemStack stack = new ItemStack(Material.OAK_PLANKS
	//		if (m instanceof Joc){
	//			Joc j = (Joc) m;
	//			Wool wool = new Wool(getGameColor(j));
	//			stack = wool.toItemStack();
	//			stack.setAmount(mapa.getPlayers().size());
	//			String tStr = new SimpleDateFormat("mm:ss").format(new Date(mapa.tempsTranscorregut()));//Integer.toString(mapa.segonsTranscorreguts());
	//			menu.setOption(Instàncies.indexOf(j), stack, j.getMapName(),ChatColor.WHITE + j.NomWorld, ChatColor.WHITE + j.getGameState().name(), ChatColor.GREEN + "Jugadors: " + Integer.toString(j.getPlayers().size()), ChatColor.YELLOW + "Espectadors:" + j.getSpectators().size(), "Temps: " + tStr);
	//		
	//		}
	//	}
	public List<Joc> getGames() {
		return getAllInstances().stream().filter(m -> m instanceof Joc).map(m -> (Joc)m).collect(Collectors.toList());
	}
	private Material getWoolMaterial(DyeColor color) {
		return Material.valueOf(color.name() + "_WOOL");
	}
}
