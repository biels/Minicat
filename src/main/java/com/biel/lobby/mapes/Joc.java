package com.biel.lobby.mapes;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.EloUtils;
import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.BielAPI.events.EventUtils;
import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.utilities.CBUtils;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.MatchData;
import com.biel.lobby.utilities.data.PlayerData;
import com.biel.lobby.utilities.events.skills.SkillPool;
import com.biel.lobby.utilities.events.skills.types.specificskills.*;
import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import com.biel.lobby.utilities.events.statuseffects.AuraRendererStatusEffect;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect
;
import com.connorlinfoot.bountifulapi.BountifulAPI;

import org.inventivetalent.menubuilder.chat.ChatListener;
import org.inventivetalent.menubuilder.chat.ChatMenuBuilder;
import org.inventivetalent.menubuilder.chat.LineBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class Joc extends MapaResetejable {
	protected Boolean JocIniciat = false;
	protected Boolean JocFinalitzat = false;
	ArrayList<Player> Espectadors = new ArrayList<>();
	ArrayList<PlayerInfo> InfoStorage = new ArrayList<>();
	protected SkillPool s = new SkillPool();
	protected MatchData matchData;
	protected boolean won = false;
	protected boolean unfairFlag = false;
	protected String host;
	
	//--Other--
	private Boolean blockBreakPlace = false;
	private Boolean giveStartingItemsRespawn = false;
	private Boolean showPlayerHealthBar = true;
	private Boolean isSnowLauncherEnabled = false;

	private Long startTimeMillis = 0L;
	private Long heartbeatCount = 0L;
	private Long ultraHeartbeatCount = 0L;
	private int heartbeatId = -1;
	private Long announceCount = 0L;
    private ArrayList<Integer> handledBukkitSchedulerTasks = new ArrayList<>();
	
	public Joc() {
		super();
		//Bukkit.broadcastMessage("Class Joc Constructor");		
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		clearExternals();
		
		super.finalize();
		//System.out.println("La instància de " + getGameName() + " s'ha destruït");
	}
	@Override
	public void initialize() {
		super.initialize();
		world.setPVP(false);
		setDefaultGameRules();
		establirSpawnPrincipal();
		establirTempsInicial(); //Pre-game lobby time
		registerSkills();
		scheduleHeartbeat();
		scheduleAnnouncer();
	}
	public void setDefaultGameRules(){
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setGameRuleValue("doFireTick", "false");
		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("doMobLoot", "false");
		if (!getResetPlayerOnRespawn()){
			world.setGameRuleValue("keepInventory", "true");
		}
		setCustomGameRules();
	}
	protected abstract void setCustomGameRules();
	public void JocIniciat(){
		if (JocIniciat){Bukkit.broadcastMessage("S'ha intentat iniciar una partida que ja estava iniciada. Operació anul·lada!"); return;}
		Bukkit.broadcastMessage(getGameDisplayName() + "S'ha iniciat la partida!");
		JocIniciat = true;
		//---
		customJocIniciat();
		world.setPVP(true);
		donarItemsInicials();
		teletransportarTothom();
		establirTempsInicial();
		resetHeartbeat();
		//---
		matchData = MatchData.registerStart(this);
		for(Player p : getPlayers()){
			getPlayerInfo(p);
		}
		updateScoreBoards();
		sendGameInfo();
		//sendGlobalMessage("W:" + getWorld().getName());

	}	
	
	public boolean JocEnMarxa(){
		return JocIniciat && !JocFinalitzat;
	}
	/**
	 * Method to clear external elements under any quit circumstance. For example this can be used to clear tasks;
	 */
	public void clearExternals(){
		//Override to use
		cancelAllTasks();
		s.clear();
	}
	public void clearExternals(Player p){
		
	}
	private void cancelAllTasks(){
		handledBukkitSchedulerTasks.forEach(tId -> Bukkit.getScheduler().cancelTask(tId));
	}
	public void handleTask(int tId){
		handledBukkitSchedulerTasks.add(tId);
	}
	private Long establirTempsInicial() {
		return startTimeMillis = System.currentTimeMillis();
	}
	public Long tempsTranscorregut() {
		return System.currentTimeMillis() - startTimeMillis;
	}
	public int segonsTranscorreguts(){
		return (int) (tempsTranscorregut() / 1000);
	}
	public void JocFinalitzat(){
		if (!JocIniciat){Bukkit.broadcastMessage("S'ha intentat finalitzar una partida que no havia començat."); return;}
		if (JocFinalitzat){Bukkit.broadcastMessage("S'ha intentat finalitzar una partida que ja havia acabat."); return;}
		//---
		world.setPVP(false);
		customJocFinalitzat();
		clearExternals();
		if(!won)matchData.registerEnd(-1); //Tie / no winner
		registerTimestamps(true);
		//---
		JocFinalitzat = true;
		updateScoreBoards();
	}
	public void winGame(Player p){ //TODO
		if(won)return;
		if(p == null){			
			JocFinalitzat();
			Bukkit.broadcastMessage(getGameDisplayName() + "La partida ha finalitzat sense guanyadors");
			return;
		}
		won = true;
		Bukkit.broadcastMessage(getGameDisplayName() + p.getName() + " ha guanyat a " + ChatColor.UNDERLINE + getGameName());
		matchData.registerEnd(p);
		JocFinalitzat();
		ArrayList<Player> wList = new ArrayList<>();
		wList.add(p);
		updateElo(wList);
	}
	public void winGame(ArrayList<Player> wList){ //TODO
		if(wList == null)return;
		if(wList.size() < 1)return;
		if(won)return;
		Player p = wList.get(0);
		if(p == null){			
			JocFinalitzat();
			Bukkit.broadcastMessage(getGameDisplayName() + "La partida ha finalitzat sense guanyadors");
			return;
		}
		won = true;
		Bukkit.broadcastMessage(getGameDisplayName() + p.getName() + " ha guanyat a " + ChatColor.UNDERLINE + getGameName());
		matchData.registerEnd(p);
		JocFinalitzat();
		updateEloOrdered(wList);
	}
	protected boolean onlyPlayersFromSameIP(){
		String n = null;
		for(Player p : getPlayers()){
			String hostName = p.getAddress().getHostName();
			if(n == null) n = hostName;
			if(n.equals(hostName))return false;
		}
		return true;
	}
	protected boolean canBeRanked(){
		return(segonsTranscorreguts() > (onlyPlayersFromSameIP() ? 60 * 15 : 20) && getEloK() != 0 && Com.getPlugin().isInRankedMode() && !unfairFlag);
	}
	protected void updateElo(ArrayList<Player> winners){
		if(!canBeRanked()){
			sendGlobalMessage(ChatColor.BLUE + "Partida irrellevant al rànquing");
			return;
		}
		ArrayList<Player> loosers = new ArrayList<>();
		getPlayers().forEach(p -> {if(!winners.contains(p))loosers.add(p);});
		ArrayList<Double> elo_winners = new ArrayList<>();
		winners.forEach(p -> elo_winners.add(new PlayerData(p.getName()).getElo()));
		ArrayList<Double> elo_loosers = new ArrayList<>();
		loosers.forEach(p -> elo_loosers.add(new PlayerData(p.getName()).getElo()));
		ArrayList<ArrayList<Double>> r = EloUtils.calculateEloGroupChange(elo_winners, elo_loosers, getEloK(), false);
		r.get(0).forEach(e -> registerEloChange(winners.get(r.get(0).indexOf(e)), e));
		r.get(1).forEach(e -> registerEloChange(loosers.get(r.get(1).indexOf(e)), e));
	}
	protected void updateEloOrdered(ArrayList<Player> orderedWinners){
		if(!canBeRanked()){
			sendGlobalMessage(ChatColor.BLUE + "Partida irrellevant al rànquing");
			return;
		}
		ArrayList<Double> elo_winners = new ArrayList<>();
		orderedWinners.forEach(p -> elo_winners.add(new PlayerData(p.getName()).getElo()));
		ArrayList<Double> r = EloUtils.calculateEloGroupChange(elo_winners, getEloK(), false);
		r.forEach(e -> registerEloChange(orderedWinners.get(r.indexOf(e)), e));
	}
	protected void registerEloChange(Player p, double change){
		PlayerData playerData = new PlayerData(p.getName());
		playerData.addElo(change);
		String cStr = (change > 0 ? ChatColor.DARK_GREEN + "+" : ChatColor.DARK_RED + "") + Double.toString(Math.round(change * 10)/10);
		p.sendMessage(ChatColor.DARK_AQUA + "Elo: " + ChatColor.WHITE + Math.round(playerData.getElo()) + "(" + cStr  + ChatColor.WHITE + ")");
	}
	double getEloK(){
		int r = 12;
		if(pMapaActual().ExisteixPropietat("K")){
			r = pMapaActual().ObtenirPropietatInt("K");
		}
		return r * getEloM();
	}
	
	double getEloM(){
		return 1;
	}
	protected void teleportToRandomSpawn(Player d) {
		Location loc;
		loc = getOptimalSpawnLoc(d);
		d.teleport(loc);
	}
	public double getMinimumHeight(){
		double r = 10.0;
		if(pMapaActual().ExisteixPropietat("MinHeight")){
			r = pMapaActual().ObtenirPropietatInt("MinHeight");
		}
		return r;
	}
	protected Location getRandomSpawnLoc(Player p) {
		ArrayList<Location> locs = pMapaActual().ObtenirLocations("s", world);
		//locs.stream().sorted((l1, l2) -> GUtils.getNearbyEnemies(l1, 40).size());
		Collections.shuffle(locs);
		Location l = locs.get(0);
		l.add(0, 2, 0);
		return l;
	}
	protected Location getOptimalSpawnLoc(Player pl) {
		if(getPlayers().size() == 1)return getRandomSpawnLoc(pl);
		ArrayList<Location> locs = pMapaActual().ObtenirLocations("s", world); //Llista spawns
		Location l = locs.stream().sorted((l1, l2) -> (int) (GUtils.getNearestEntity(l2, getEnemies(pl)).getLocation().distanceSquared(l2) - GUtils.getNearestEntity(l1, getEnemies(pl)).getLocation().distanceSquared(l1))).skip(Utils.NombreEntre(0, 3)).findFirst().get();
		l.add(0, 2, 0);
		return l;	
	}
	public void sendGameInfo(){
		for (Player p : getPlayers()){
			sendGameInfo(p);
		}
	}
	public void sendGameInfo(Player p){
		ArrayList<String> gameInfo = getGameInfo(p);
		if (gameInfo != null) {
			sendPlayerMessage(p, ChatColor.BLUE + "" + ChatColor.BOLD + "----->>> Info <<<-----");
			for (String s : gameInfo) {
				sendPlayerMessage(p, ChatColor.WHITE + "" + ChatColor.BOLD + " + " + ChatColor.RESET + s);
			}
			sendPlayerMessage(p, ChatColor.BLUE + "" + ChatColor.BOLD + "----------------------");
		}
	}
	protected ArrayList<String> getGameInfo(Player p){
		return null;
	}
	public void setHost(Player p){
		boolean change = host != null;
		host = p.getName();
		if(change){
			donarItemsInicials(p);
			sendGlobalMessage(p.getName() + " és administrador de la partida");
		}
	}
	public void setHost(String name){
		host = name;
	}
	public boolean hasHostPrivilleges(Player p){
		return host.equalsIgnoreCase(p.getName());
	}
	@Override
	public void Join(Player ply) {
		// TODO Auto-generated method stub
		if(canJoin(ply)){
			if(getPlayers().size() == 0)setHost(ply);			
		}
		super.Join(ply);
	}

	public boolean canJoin(Player ply) {
		switch (getGameState()){
		case Complete:
			return false;
		case Editant:
			return ply.isOp();
		case InGame:
			return getAllowSpectators();
		case Preparing:
			return true;
		case Resetejant:
			return false;
		case WaitingForPlayers:
			return true;

		}
		return true;
	}
	protected void donarItemsInicials(){
		for (Player ply : getPlayers()){
			donarItemsInicials(ply);
		}
	}
	protected void donarItemsInicials(Player ply) {
		ply.setHealth(ply.getMaxHealth());
		Utils.clearPlayer(ply);
		giveFixedPlaceItems(ply);
		giveRemainingUnlockers(ply);
		donarEfectesInicials(ply);
		if (getStartingItems(ply) != null){			
			Utils.donarItemsPlayer(ply, getStartingItems(ply));
		}
	}
	public void giveRemainingUnlockers(Player ply) {
		s.giveUnlockers(ply, getPlayerInfo(ply).getUnselectedSkillAmount());
	}
	public void giveFixedPlaceItems(Player ply) {
		
	}
	protected void donarEfectesInicials(Player ply){

	}
	protected void registerSkills(){
		s.registerSkill(new CalciumSourceSkill(null));
		s.registerSkill(new AssaultSkill(null));
		s.registerSkill(new GravityBendingSkill(null));
		s.registerSkill(new FrostArcherSkill(null));
		s.registerSkill(new SwordsmanSkill(null));
		s.registerSkill(new VampireSkill(null));
		s.registerSkill(new DeflectorSkill(null));
		s.registerSkill(new BerserkSkill(null));
		s.registerSkill(new SpeedyArcher(null));
		s.registerSkill(new DiamondCoreSkill(null));
		//s.registerSkill(new CreeperSkill(null));
		//s.registerSkill(new ExternalCombustionEngine(null));
		s.registerSkill(new CorinthianHelmetSkill(null));
		s.registerSkill(new MagicArcherSkill(null));
	}
	protected abstract ArrayList<ItemStack> getStartingItems(Player ply);
	protected abstract void teletransportarTothom();
	protected abstract void customJocIniciat();
	protected abstract void customJocFinalitzat();
	protected boolean canBeDropped(ItemStack i, Player p){
		if (getStartingItems(p) != null) {
			//r = !getStartingItems(p).contains(i);
			if (i.getType() == Material.CHEST) {
				return false;
			} 
			if (i.getType() == Material.DIAMOND_BLOCK) {
				return false;
			} 
			if (Utils.isArmor(i)) {
				return false;
			} 
		}
		return true;
	}
	public ArrayList<Player> getViewers(){
		return (ArrayList<Player>) world.getPlayers();
	}
	public ArrayList<Player> getPlayers(){
		ArrayList<Player> viewers = getViewers();
		viewers.removeAll(Espectadors);
		return viewers; //Futurs espectadors
	}
	public ArrayList<Player> getEnemies(Player p){
		ArrayList<Player> enemies = getViewers();
		enemies.remove(p);
		return enemies; //Futurs espectadors
	}
	public List<Player> getSpectators(){
		return Espectadors; //Futurs espectadors
	}
	public boolean getAllowSpectators(){
		return true;
	}
	public Boolean isSpectator(Player ply){
		return Espectadors.contains(ply);
	}
	void donarItemsEspectador(Player ply){
		giveRandomCameraItem(ply);

	}

	public String getWinnerDisplayName(){
		return "[-]";
	}
	void giveRandomCameraItem(Player ply){
		ItemButton.clearButtons(ply);
		PlayerInventory inventory = ply.getInventory();
		inventory.clear();
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.GOLD_BLOCK), ChatColor.AQUA + "Càmera aleatòria"), ply, event -> {
            Player p = event.getPlayer();
            teleportCameraRandomly(p);
        });
		inventory.setItem(0, button.getItemStack());
	}
	private void teleportCameraRandomly(Player p) {
		ArrayList<Player> players = getPlayers();
		if(players.size() != 0){
			Collections.shuffle(players);
			p.teleport(players.get(0));
		}
	}
	public void addSpectator(Player ply){
		for (Player p : getPlayers()) {
			
			p.sendMessage(getGameDisplayName() + ply.getName() + " ha entrar com a espectador");
		}
		if (!getSpectators().contains(ply)){
			Espectadors.add(ply);
		}
		Utils.clearPlayer(ply);
		donarItemsEspectador(ply);
		updateScoreBoard(ply);
		//ply.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 400, 0));
		//ply.setAllowFlight(true);
		//ply.setCanPickupItems(false);
		//ply.setFlying(true);
		ply.setGameMode(GameMode.SPECTATOR);
		teleportCameraRandomly(ply);
		//ScoreBoardUpdater.updateSpectatorScore(Espectadors);
	}
	public void removeSpectator(Player ply){
		if (getSpectators().contains(ply)){
			Utils.clearPlayer(ply);
			ply.setAllowFlight(false);
			ply.setCanPickupItems(true);
			ply.setFlying(false);
			Espectadors.remove(ply);
			//ScoreBoardUpdater.updateSpectatorScore(Espectadors);
		}
	}
	private static String getHealthProgressBar(Player ply){
		String c = "|";//'\u2B1B';
		double n = 10;
		double percent = ply.getHealth() * 100 / ply.getMaxHealth();
		int colorPoint = (int) Math.ceil(percent / n);
		ChatColor color = ChatColor.DARK_GREEN;
		if(percent < 65)color = ChatColor.GREEN;
		if(percent < 40)color = ChatColor.YELLOW;
		if(percent < 25)color = ChatColor.RED;
		if(percent < 15)color = ChatColor.DARK_RED;
		if(ply.hasPotionEffect(PotionEffectType.ABSORPTION)) color = ChatColor.GOLD;
		
		String r = color + "";
		for (int i = 0; i < n; i++) {
			if(i == colorPoint){r += ChatColor.GRAY;}
			r += c;
		}
		return r;
	}
	protected void updateHealthSuffix(Player p){
		String suffix = " " +  getHealthProgressBar(p);
		Com.setSuffix(p, suffix);
	}
	@Override
	protected void onPlayerMoveDistributed(PlayerMoveEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerMoveDistributed(evt, p);
		if(Utils.Possibilitat(100) && getShowPlayerHealthBar()){
			if(getDisplayHealthBar())updateHealthSuffix(p);
		}
	}
	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		PlayerInfo i = getPlayerInfo(p);
		if(i.isImmune()){
			evt.setCancelled(true);
			getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.BLUE.getDyeData());   				 		
		}
		double m = 1;		
		m =  175/(CBUtils.getPing(p)+1);
		if(m > 1)m = 1;
		if(m < 0.5)m = 0.5;
		evt.setDamage(evt.getDamage() * Math.sqrt(m));
	}
	public boolean getDisplayHealthBar(){
		return true;
	}
	public ItemStack getSnowLauncher(int amount){
		isSnowLauncherEnabled = true;
		ItemStack ball = new ItemStack(Material.SNOW_BALL);
		ball.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
		ball.setAmount(amount);
		return Utils.setItemNameAndLore(ball, "Llançador de neu", "Et transporta a l'enemic que impacti");
	}
	public boolean giveSnowLauncherOnKill(){
		return false;
	}
	
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
			Player damaged, Player damager, boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		getPlayerInfo(damaged).setLastDamager(damager);
		//-- SNOW LAUNCHER
		if(ranged){
			org.bukkit.entity.Entity proj = evt.getDamager();
			if(proj instanceof Snowball){
				Snowball ball = (Snowball) proj;
				if (isSnowLauncherEnabled) { //TODO decide based on flag
					evt.setCancelled(true);
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 1, 0));
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 1, 0));
					//damaged.setVelocity(new Vector(0, 0.1, 0));
					damager.teleport(damaged.getEyeLocation().add(0, 0.5, 0), TeleportCause.PLUGIN);
					GUtils.healDamageable(damager, 0.4D);
					damager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 2, 1));
				}
			}
		}
		//---
		if (isSpectator(damager) || isSpectator(damaged)){evt.setCancelled(true);}
		if(getDisplayHealthBar())updateHealthSuffix(damaged);
		PlayerInfo i = getPlayerInfo(damaged);
		if(i.isImmune()){
			evt.setCancelled(true);
			
			
			BountifulAPI.sendActionBar(damager, ChatColor.GRAY + "El jugador " + damaged.getName() + " és invulnerable.", 150);
			
			getWorld().playSound(damager.getLocation(), Sound.ENCHANT_THORNS_HIT, 1.2F, 0.88F);
			getWorld().playEffect(damaged.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.BLUE.getDyeData());   				
			getWorld().playEffect(damager.getEyeLocation(), Effect.FIREWORKS_SPARK, DyeColor.RED.getDyeData());   		
		}
		if(!evt.isCancelled()){
			i.setDamageDealt(i.getDamageDealt() + evt.getDamage());
		}
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		// TODO Auto-generated method stub
		super.onPlayerDeath(evt, killed);
		ArrayList<ItemStack> rem = new ArrayList<>();
		List<ItemStack> drops = evt.getDrops();
		for(ItemStack i : drops){
			Material t = i.getType();
			boolean cname = false; 
			if(i.hasItemMeta()){
				cname = i.getItemMeta().hasDisplayName();
			}
			if(!canBeDropped(i, killed)){
				if (i.getType() == i.getType()){
					ItemStack remi = i.clone();
					remi.setAmount(i.getAmount());
					rem.add(remi);
				}
			}
		}
		drops.removeAll(rem);
		List<ItemStack> endContents = GUtils.subtractInventoryContents(drops, getStartingItems(killed));
		drops.clear();
		drops.addAll(endContents);
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {

		super.onPlayerDeathByPlayer(evt, killed, killer);
		if(killed == killer) evt.setDeathMessage(ChatColor.YELLOW + killed.getName() + ChatColor.RED + " s'ha suïcidat");
		if(giveSnowLauncherOnKill()) killer.getInventory().addItem(getSnowLauncher(1));

	}
	public void allOnTheLobby(){
		for (Player p : world.getPlayers()){
			Com.teleportPlayerToLobby(p);
		}
	}
	void establirSpawnPrincipal(){
		if (pMapaActual().ExisteixPropietat("spawn")){
			Location loc = pMapaActual().ObtenirLocation("spawn", getWorld());
			world.setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		}
	}
	public void planificarReseteig(int delay){
		sendGlobalMessage(ChatColor.BLUE + "Esborrant el mapa en " + Double.toString(delay/20) + "s");
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(lobby.getPlugin(), () -> allOnTheLobby(), delay);
	}
	
	@Override
	protected void customJoin(Player ply){
		//getPlayerInfo(ply);
		Utils.clearPlayer(ply);
		if (getGameState() == GameState.InGame) {
			
			addSpectator(ply);
			
		} else {
			sendGlobalMessage(getGameDisplayName() +  ply.getName() + " ha entrat al joc");
			donarItemsPreparatiusGenerals(ply);
			
		}
		
		updateScoreBoard(ply);
		anunciarWiki(ply, false);
	}
	@Override
	protected void customLeave(Player ply, List<String> attatchments) {
		// TODO Auto-generated method stub
		if(hasHostPrivilleges(ply) && getPlayers().size() > 1 && !JocIniciat){
			setHost(GUtils.getRandomListItem(getPlayers().stream().filter(p -> p!=ply).collect(Collectors.toList())));
		}
		double punishForLeaving = getPunishForLeaving(ply);
		if(CBUtils.getPing(ply) > 400){
			attatchments.add(ChatColor.GOLD + "[Error de xarxa]");
			if(punishForLeaving > 0){
				attatchments.add(ChatColor.GREEN + "[No penalitzat]");
			}
		}else{
			if(punishForLeaving != 0 && !isSpectator(ply)){
				attatchments.add(ChatColor.RED + "[Penalitzat]");
				punishPlayerElo(ply, punishForLeaving);
			}			
		}
	}
	public void punishPlayerElo(Player ply, double amount){
		unfairFlag = true;
		registerEloChange(ply, amount * -1);
	}
	public double getPunishForLeaving(Player ply){
		double max_punish = 4.2 + getEloK() / 8 + getAvgGameLength().toHours() * 4;
		double p = max_punish;
		if(getGameProgressETA() < 0.25)p = 0;
		p = max_punish * (getGameProgressETA() - 0.2);
		if(getGameProgressETA() > 0.8)p = max_punish;
		if(!JocEnMarxa() || getEloK() == 0 || getPlayers().size() <= 1)p = 0;
		return Math.max(0, p);
	}
	public double getGameProgressETA(){
		if(!JocEnMarxa()){
			if(!JocIniciat)return 0;
			if(JocFinalitzat)return 1;
		}
		return getGameTime().toMillis() / (double)getAvgGameLength().toMillis();
	}
	public Duration getGameTime(){
		return Duration.ofSeconds(segonsTranscorreguts());
		
	}
	public Duration getAvgGameLength(){
		return Duration.ofSeconds((long) Com.getDataAPI().getAvgGameLength(Com.getDataAPI().getGameId(getGameName())));
		
	}
	
	double lastProgressETA = 0;
	public void lobbyProgressAnoouncerTick(){
		double gameProgressETA = getGameProgressETA() * 100;
		float[] array = {10F, 25F, 50F, 75F, 90F, 100F, 110F, 120F, 130F, 150F, 175F, 200F, 300F};
        for (float anArray : array) {
            if (Utils.testPointUpDown(anArray, lastProgressETA, gameProgressETA)) {
                String status = "";
                if (anArray == 10F) status = "ha començat fa poc";
                if (anArray == 25F) status = "ha començat fa una estona";
                if (anArray == 50F) status = "va per la meitat aproximadament";
                if (anArray == 75F) status = "acabarà aviat";
                if (anArray == 90F) status = "és a l'etapa decisiva. Acabarà aviat.";
                if (anArray == 100F) status = "hauria d'acabar en breu";
                if (anArray >= 110F) status = "està durant més del previst";
                if (anArray >= 200F) status = "està durant el doble del previst";
                if (anArray >= 210F) status = "durarà tota l'eterinitat";
                Com.sendLobbyMessage(ChatColor.GRAY + "La partida de " + ChatColor.DARK_AQUA + getGameName() + ChatColor.GRAY + " " + status);
                if (anArray >= 150) {
                    sendGlobalMessage("És possible que la partida s'hagi estancat. En aquest cas, feu /l i començeu-ne una de nova.");
                }
            }
        }
		
		lastProgressETA = gameProgressETA;
	}
	public void anunciarWiki(Player p, boolean requested){

		/*
		if(pMapaActual().ExisteixPropietat("wiki")){
			String pageName = pMapaActual().ObtenirPropietat("wiki");
			boolean local = p.getAddress().getHostString().startsWith("10.0.0.");
			if(!requested)p.sendMessage("Pots veure la informació del mapa a:");
			if(requested)p.sendMessage("Article del mapa " + getGameName() + ":");
			p.sendMessage(ChatColor.BLUE + getWikiLink(pageName, local));
			p.sendMessage("(Clic->Si per obrir)");
		}else{
			sendPlayerMessage(p, "Aquest mode de joc no té un article a la wiki");
		}
		*/

	}
	public String getWikiLink(String pageName, boolean local){
		String protocol = "http://";
		String domain = "ordinadorcasa.no-ip.org";
		if (local){
			domain = "10.0.0.100";
		}
		String wiki = "/w";
		String wikirequest = "/index.php?title=";
		return protocol + domain + wiki + wikirequest + pageName;

	}
	protected void updateScoreBoards(){
		getViewers().forEach(this::updateScoreBoard);
	}
	protected void updateScoreBoard(Player ply){
		//sendGlobalMessage(ply.getName() + " actualitzat");
	}
	public int segonsPerIniciar(){
		int pOnLobby = lobby.getLobbyWorld().getPlayers().size();
		int t = 0 + (pOnLobby * 3);
		Boolean anyOp = false;
		for(Player p : getPlayers()){
			if (p.isOp()){
				anyOp = true;
			}
		}
		if (anyOp){
			return t + 1;
		}
		if(pOnLobby != 0){
			return t;
		}

		return 5;
	}
	public boolean canBeStartedBy(Player ply, boolean message){
        //Jugadors al lobby
        //Admins a la partida
        int r = segonsPerIniciar() - segonsTranscorreguts();
        if (r <= 0 || ply.isOp()){
            return true;
        }else{
            if(message)ply.sendMessage("Cal esperar almenys " + ChatColor.YELLOW + Integer.toString(r) + ChatColor.WHITE + "s per iniciar la partida" );
            return false;
        }
    }
	public void iniciarCommand(Player ply){
		if(canBeStartedBy(ply, true)){
		    JocIniciat();
        }
	}
	protected void donarItemsPreparatiusGenerals(final Player ply){
		ItemButton.clearButtons(ply);
		PlayerInventory inventory = ply.getInventory();

		ItemButton btnStartGame = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BLAZE_ROD), ChatColor.GREEN + "Inicia la partida"), ply, event -> iniciarCommand(event.getPlayer()));
		if(hasHostPrivilleges(ply))inventory.setItem(0, btnStartGame.getItemStack());
		ItemButton btnWiki = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.POWERED_RAIL), ChatColor.BOLD + "Wiki " + getGameName()), ply, event -> anunciarWiki(event.getPlayer(), true));
		ItemButton button2 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.SKULL_ITEM), ChatColor.GREEN + "Afegir jugadors"), ply, event -> {
            final List<Player> lobbyPlayers = lobby.getLobbyWorld().getPlayers();
            IconMenu menu = new IconMenu("Afegeix...", 27, event12 -> {
				event12.setWillClose(true);
				//Obrir mapa
				int pos = event12.getPosition();
				if (pos != 26){
				Player pl = lobbyPlayers.get(pos);
				if (lobby.isOnLobby(Bukkit.getPlayer(pl.getName()))){
				Join(pl);
				}else{
				ply.sendMessage("El jugador ha marxat del lobby abans de ser teletransportat.");
				}
				
				}else{
					for(Player pl : lobbyPlayers){
						if (lobby.isOnLobby(Bukkit.getPlayer(pl.getName()))){
							Join(pl);
						} else {
							ply.sendMessage("El jugador ha marxat del lobby abans de ser teletransportat (" + pl.getName() + ")");
						}
					}
				
				}
			
			});

            for(Player p : lobbyPlayers){
                Material m = Com.getSkullIconMaterial(p);
                ItemStack stack = new ItemStack(m, 1);
                //stack.setAmount(eq.getPlayers().size());
                menu.setOption(lobbyPlayers.indexOf(p), stack, ChatColor.AQUA + p.getName(),ChatColor.WHITE +  "Desde: lobby");
            }
            //if (AlgunMapaDisponible() == false){
            menu.setOption(26, new ItemStack(Material.SPONGE, 1), ChatColor.YELLOW + "Afegir tots", ChatColor.WHITE + "Afegeix tots els jugadors del lobby a la partida actual");
            //}

            menu.open(ply);
        });
		//if(hasHostPrivilleges(ply))inventory.setItem(7, button2.getItemStack()); // AND isOp()
		inventory.setItem(6, btnWiki.getItemStack());
		ItemButton btnInvitePlayers = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.DETECTOR_RAIL), ChatColor.GREEN + "Convidar jugadors"), ply, event -> {
            final List<Player> lobbyPlayers = lobby.getLobbyWorld().getPlayers();
            IconMenu menu = new IconMenu("Convida...", 27, event1 -> {
			event1.setWillClose(true);
			//Obrir mapa
			int pos = event1.getPosition();
			if (pos != 26){
				
				Player pl = lobbyPlayers.get(pos);
				
				if (lobby.isOnLobby(Bukkit.getPlayer(pl.getName()))){
					inviteToGame(pl);
				} else {
					ply.sendMessage("El jugador ha marxat del lobby abans de ser convidat.");
				}
				
			} else {
				for(Player pl : lobbyPlayers){
					if (lobby.isOnLobby(Bukkit.getPlayer(pl.getName()))){
						inviteToGame(pl);
					} else {
						ply.sendMessage("El jugador ha marxat del lobby abans de ser convidat (" + pl.getName() + ")");
					}
					
				}
			
			}

            });

            for(Player p : lobbyPlayers){
                Material m = Com.getSkullIconMaterial(p);
                ItemStack stack = new ItemStack(m, 1);
                //stack.setAmount(eq.getPlayers().size());
                menu.setOption(lobbyPlayers.indexOf(p), stack, ChatColor.AQUA + p.getName(),ChatColor.WHITE +  "Des de: lobby");
            }

            menu.setOption(26, new ItemStack(Material.SPONGE, 1), ChatColor.YELLOW + "Convidar tothom", ChatColor.WHITE + "Afegeix tots els jugadors del lobby a la partida actual");


            menu.open(ply);
        });
		if(hasHostPrivilleges(ply))inventory.setItem(8, btnInvitePlayers.getItemStack());
		//		if (this instanceof JocEquips){
		//			if (ply.isOp()){
		//				Utils.donarItem(ply, Material.IRON_SPADE, ChatColor.RED + "Bloqueja el canvi d'equip");
		//			}
		//			Utils.donarItem(ply, Material.WOOL, ChatColor.GREEN + "Canvia d'equip");
		//		}

	}
	public void inviteToGame(Player player){
		
		// String join = "\n\n    " + ChatColor.GREEN + ChatColor.UNDERLINE + host + ChatColor.RESET + ChatColor.GREEN + " t'ha convidat a " + getGameName();
		// String join2 = "\n    " + ChatColor.GOLD + ChatColor.UNDERLINE + "Clica aquí per entrar al joc\n\n";
		
		TextComponent clickToJoinMsg = new TextComponent("    Fes clic aquí per a entrar a la partida");

		
		player.sendMessage(ChatColor.GREEN  + "\n\n    " +  ChatColor.ITALIC + host + ChatColor.RESET + "" + ChatColor.GREEN + " t'ha convidat a " + getGameName() + "");
		new ChatMenuBuilder().withLine(
			new LineBuilder().append(
					new ChatListener() {
						public void onClick(Player player) {
			                // player.sendMessage("You clicked me!");
			                Join(player);
			            }
					
					},
					clickToJoinMsg
					
				)
		).show(player);
		player.sendMessage("\n");
		
		player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 0);
	
	}
	public Boolean getBlockBreakPlace() {
		return blockBreakPlace;
	}
	public void setBlockBreakPlace(Boolean blockBreakPlace) {
		this.blockBreakPlace = blockBreakPlace;
	}

	public Boolean getGiveStartingItemsRespawn() {
		return giveStartingItemsRespawn;
	}
	public void setGiveStartingItemsRespawn(Boolean giveStartingItemsRespawn) {
		this.giveStartingItemsRespawn = giveStartingItemsRespawn;
	}

	public Boolean getShowPlayerHealthBar() {
		return showPlayerHealthBar;
	}
	public void setShowPlayerHealthBar(Boolean showPlayerHealthBar) {
		this.showPlayerHealthBar = showPlayerHealthBar;
	}
	@Override
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockPlace(evt, blk);
		if (evt.getPlayer() != null){
			Player ply = evt.getPlayer();
			if(!blockBreakPlace){
				evt.setCancelled(true);
			}
			if (ply.getGameMode() == GameMode.CREATIVE){
				evt.setCancelled(false);
			}
			if(!evt.isCancelled()){
				PlayerInfo i = getPlayerInfo(ply);
				i.setBlocksPlaced(i.getBlocksPlaced() + 1);
			}
		}
	}
	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockBreak(evt, blk);
		if (evt.getPlayer() != null){
			Player ply = evt.getPlayer();
			if(!blockBreakPlace){
				evt.setCancelled(true);
			}
			if (ply.getGameMode() == GameMode.CREATIVE){
				evt.setCancelled(false);
			}
			if(!evt.isCancelled()){
				PlayerInfo i = getPlayerInfo(ply);
				i.setBlocksBroken(i.getBlocksBroken() + 1);
			}
		}
	}
	
	public boolean getResetPlayerOnRespawn(){
		return true;
	}
	protected int getBaseSkillUnlockerAmount(){
		return 0; //TEMPORARILY DISABLED SKILLS BY DEFAULT, MAPS CAN STILL HAVE MANY SLOTS ENABLED IF THEY REQUEST SO @getBaseSkillUnlockerAmount()
	}
	@Override
	protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerRespawnAfterTick(evt, p);
		PlayerInfo i = getPlayerInfo(p);
		i.lastMoveEvent = ZonedDateTime.now();
		i.setImmune(true);
		i.lastRespawnEvent = ZonedDateTime.now();
		if (getResetPlayerOnRespawn()){
			Utils.clearPlayer(p);			
			donarItemsInicials(p);
		}

	}
	@Override
	protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerMove(evt, p);
		PlayerInfo i = getPlayerInfo(p);
		i.lastMoveEvent = ZonedDateTime.now();
		Vector v = Utils.CrearVector(evt.getFrom(), evt.getTo());
		if(v.getX() != 0 || v.getZ() != 0 || evt.getFrom().getYaw() != evt.getTo().getYaw())
		i.setImmune(false);
	}
	
	@Override
	protected void onProjectileLaunch(ProjectileLaunchEvent evt, Projectile proj) {
		// TODO Auto-generated method stub
		super.onProjectileLaunch(evt, proj);
		if(proj.getType() == EntityType.SPLASH_POTION){
			ProjectileSource shooter = proj.getShooter();
			if (shooter instanceof LivingEntity) {
				Vector dir = ((LivingEntity)shooter).getLocation().getDirection();
				proj.setVelocity(dir.multiply(5));
			}
		}
	}
	@Override
	protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
		// TODO Auto-generated method stub
		super.onProjectileHit(evt, proj);
		
	}
	public enum GameState {InGame, Preparing, WaitingForPlayers, Complete, Resetejant, Editant}
	public GameState getGameState(){
		if (EditMode){
			return GameState.Editant;
		}
		if (JocFinalitzat){
			return GameState.Complete;
		}
		if (JocIniciat){
			return GameState.InGame;
		}else{
			if (world != null){
				if (world.getPlayers().size() == 0){
					return GameState.WaitingForPlayers;
				}else{
					return GameState.Preparing;
				}
			}else{
				return GameState.WaitingForPlayers;
			}

		}
	}
	//	public PlayerInfo getPlayerInfo(String p){
	//		Player ply = Bukkit.getPlayer(p);
	//		if (ply == null){return null;}
	//		return getPlayerInfo(ply);
	//	}

	@SuppressWarnings("unchecked")
	public <T extends PlayerInfo> T getPlayerInfo(Player p, Class<T> type){
		for (PlayerInfo i : InfoStorage){
			if (p.getName().equals(i.getName())){
				return (T) i;
			}
		}
		PlayerInfo playerInfo;
		try {
			playerInfo = (PlayerInfo) type.getConstructors()[0].newInstance(this);
			playerInfo.setName(p.getName());
			InfoStorage.add(playerInfo);
			return getPlayerInfo(p, type);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public PlayerInfo getPlayerInfo(Player p){
		return getPlayerInfo(p, PlayerInfo.class);
	}
	public class PlayerInfo{
		String name;
		int value;
		int spree;
		int additionalSkills = 0;
		double speedModifier = 0;
		boolean immune = true;
		Player lastDamager = null;
		ZonedDateTime lastMoveEvent = ZonedDateTime.now();
		ZonedDateTime lastRespawnEvent = ZonedDateTime.now();
		int kills = 0;
		int deaths = 0;
		boolean isAlive = true;
		double damageDealt = 0;
		int blocksBroken = 0;
		int blocksPlaced = 0;
		int objectivesCompleted = 0;
		ArrayList<StatusEffect> effects = new ArrayList<>();
		private ArrayList<AuraInfo> auras = new ArrayList<>();
		AuraRendererStatusEffect auraRenderer = null;
		public int getSpree() {
			return spree;
		}
		public void setSpree(int spree) {
			this.spree = spree;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		protected Player getPlayer() {
			if(name == null)return null;
			return Bukkit.getPlayer(name);
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public double getSpeedModifier() {
			return speedModifier;
		}
		public void setSpeedModifier(double speedModifier) {
			this.speedModifier = speedModifier;
		}
		public int getKills() {
			return kills;
		}
		public void setKills(int kills) {
			this.kills = kills;
		}
		public int getDeaths() {
			return deaths;
		}
		public void setDeaths(int deaths) {
			this.deaths = deaths;
		}
		public boolean isAlive() {
			return isAlive;
		}
		public void setAlive(boolean isAlive) {
			this.isAlive = isAlive;
		}
		public double getDamageDealt() {
			return damageDealt;
		}
		public void setDamageDealt(double damageDealt) {
			this.damageDealt = damageDealt;
		}
		public Player getLastDamager() {
			return lastDamager;
		}
		public void setLastDamager(Player lastDamager) {
			this.lastDamager = lastDamager;
		}
		public int getBlocksBroken() {
			return blocksBroken;
		}
		public void setBlocksBroken(int blocksBroken) {
			this.blocksBroken = blocksBroken;
		}
		public int getBlocksPlaced() {
			return blocksPlaced;
		}
		public void setBlocksPlaced(int blocksPlaced) {
			this.blocksPlaced = blocksPlaced;
		}
		public int getObjectivesCompleted() {
			return objectivesCompleted;
		}
		public void setObjectivesCompleted(int objectivesCompleted) {
			this.objectivesCompleted = objectivesCompleted;
		}
		public boolean isImmune() {
			return immune || isAFK(); //|| Duration.between(lastRespawnEvent, ZonedDateTime.now()).compareTo(Duration.ofSeconds(4)) > 0;
		}
		public void setImmune(boolean immune) {
			this.immune = immune;
		}
		public Duration getIdleTime(){
			return Duration.between(lastMoveEvent, ZonedDateTime.now());
		}
		public boolean isAFK(){
			return getIdleTime().getSeconds() > 10;
		}
		public ArrayList<AuraInfo> getAuras() {
			ArrayList<AuraInfo> aurasFull = new ArrayList<>();
			aurasFull.addAll(auras);
			aurasFull.addAll(getRealtimeAuras());
			return aurasFull;
		}
		public ArrayList<AuraInfo> getRealtimeAuras(){
			ArrayList<AuraInfo> a = new ArrayList<>();
			if(isImmune())a.add(new AuraInfo("AFK", 8, 6, new ItemStack(Material.BARRIER, 1)));
			return a;
		}
		public void setAuras(ArrayList<AuraInfo> auras) {
			this.auras = auras;
		}
		public void clearAuras() {
			auras.clear();
		}
		public void addAura(AuraInfo info){
			
			auras.add(info);
		}
		public void removeAura(String name){
			auras.removeIf(a -> a.getName().equalsIgnoreCase(name));
		}
		public void ultraTick(){		
			updateSpeedSlowPotionEffects();
			//getPlayer().sendMessage("Ping:" + ChatColor.GREEN + "" + CBUtils.getPing(getPlayer()));
			for(StatusEffect e : effects){
				if(e.isValid())e.tick();
			}
			removeExpiredEffects();
			updatePlayerActionBar();
			//updateSpeedSlowPotionEffects();
		}
		public void tick(){
			//if(!hasStatusEffect(AuraRendererStatusEffect.class))addStatusEffect(new AuraRendererStatusEffect(getPlayer()));
		}
		
		//---------AURAS----------
		public void renderAuras(){
			
		}
		
		//---------AURAS----------
		public void updateSpeedSlowPotionEffects(){
			//Bukkit.broadcastMessage("Done");
			int speedPercentage = (int) Math.round(getSpeedModifier());
			if(speedPercentage == 0)return;
			int SP = 0;
			int SL = 0;
			if (speedPercentage > 0) {
				SP += Math.floor(speedPercentage / 20D);
				double rem = speedPercentage / 20D - SP;
				SP += rem * 4;
				SL += rem * 4;
			}else{
				speedPercentage *= -1;
				SL += Math.floor(speedPercentage / 15D);
				double rem = speedPercentage / 15D - SL;
				SL += Math.round(rem * 3 * 3);
				SP += Math.round(rem * 3 * 2);
			}
			if (SP > 0) {
				getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1, SP - 1, true, true));
			}else{
				getPlayer().removePotionEffect(PotionEffectType.SPEED);
			}
			if (SL > 0) {
				getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, SL - 1, true, true));
			}else{
				getPlayer().removePotionEffect(PotionEffectType.SLOW);
			}
		}
		public ArrayList<StatusEffect> getStatusEffects() {
			return effects;
		}
		public void addStatusEffect(StatusEffect effect){
			if (!hasStatusEffect(effect.getClass())) {
				effects.add(effect);
			}
		}
		public void removeStatusEffect(StatusEffect effect){
			effects.remove(effect);
			sendGlobalMessage(effect.getName());
		}
		public void removeStatusEffect(Class<? extends StatusEffect> type){
			if(hasStatusEffect(type))effects.remove(getStatusEffect(type));
		}
		public void removeExpiredEffects(){
			ArrayList<StatusEffect> toRemove = new ArrayList<>();
			for(StatusEffect e : getStatusEffects()){
				if (e.hasExpired()) {
					e.clearExternals();
					toRemove.add(e);
				}
			}
			effects.removeAll(toRemove);
		}
		public String getStatusEffectsText(){
			String t = "";
			for(StatusEffect e : effects){
				String displayText = e.getDisplayText();
				if(displayText != null){
					String spacing = " ";
					if(effects.indexOf(e) == 0)spacing = "";
					t += spacing + displayText;
				}
			}
			return t;
		}
		public boolean hasStatusEffect(Class<? extends StatusEffect> type){
			for(StatusEffect e : effects){
				String name = e.getClass().getName();
				String name2 = type.getName();
				if (name.equals(name2))return true;
			}
			return false;
		}
		public boolean hasStatusEffect(String name){
			for(StatusEffect e : effects){
				String n = e.getName();
				if (name.equals(n))return true;
			}
			return false;
		}
		@SuppressWarnings("unchecked")
		public <T extends StatusEffect> T getStatusEffect(Class<T> type){
			for(StatusEffect e : effects){
				String name = e.getClass().getName();
				String name2 = type.getName();
				if (name.equals(name2))return (T) e;
			}
			return null;
		}
		@SuppressWarnings("unchecked")
		public <T extends StatusEffect> T getStatusEffect(String name){
			for(StatusEffect e : effects){
				String n = e.getName();
				if (name.equals(n))return (T) e;
			}
			return null;
		}
		public void updatePlayerActionBar(){
			if (getPlayer() == null)return;
			// ActionBarAPI.sendActionBar(getPlayer(), getStatusEffectsText());
		}
		public int getAdditionalSkills() {
			return additionalSkills;
		}
		public void setAdditionalSkills(int additionalSkills) {
			this.additionalSkills = additionalSkills;
		}
		public void addAdditionalSkill(){
			setAdditionalSkills(getAdditionalSkills() + 1);
			Player p = getPlayer();
			if(p != null){
				giveRemainingUnlockers(p);
			}
			sendPlayerMessage(p, ChatColor.AQUA + "Tens una nova habilitat per triar!");
		}
		public int getTotalSkills(){
			return getBaseSkillUnlockerAmount() + getAdditionalSkills();
		}
		public int getUnselectedSkillAmount(){
			int r = getTotalSkills() - s.getSkillsForPlayer(getPlayer()).size();
			return (r < 0 ? 0 : r);
		}
		public Block getBlockWherePlayerStands(){
			BlockIterator i = new BlockIterator(getWorld(), getPlayer().getLocation().toVector(), new Vector(0, -1, 0), 1D, 30);
			for (;i.hasNext();) {
				Block b = i.next();
				if(!b.isEmpty())return b;
			}
			return null;
		}
	}
	//	PVP
	public Boolean areAllies(Player ply, Player ply2){
		return ply.equals(ply2); //Default AllvAll
	}
	public Boolean areEnemies(Player ply, Player ply2){
		return !areAllies(ply, ply2);
	}

	//Heartbeat
	private void scheduleHeartbeat(){
		heartbeatId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Com.getPlugin(), () -> {
            if (ultraHeartbeatCount % 20 == 0) {
                heartbeat();
            }
            ultraHeartbeat();
        }, 1, 1);
		handleTask(heartbeatId);
	}
	public void ultraHeartbeat(){
		ultraHeartbeatCount++;
		for(PlayerInfo i : InfoStorage){
			i.ultraTick();
		}
		s.tickPool();
	}
	private void killHeartbeat(){
		Bukkit.getServer().getScheduler().cancelTask(heartbeatId);
	}
	public void heartbeat(){
		heartbeatCount++;
		//if (!JocIniciat){startSystemTick();}
		for(PlayerInfo i : InfoStorage){
			i.tick();
		}
		lobbyProgressAnoouncerTick();
		registerTimestamps(false);
	}
	public Long getHeartbeatCount() {
		return heartbeatCount;
	}
	public Long getUltraHeartbeatCount() {
		return ultraHeartbeatCount;
	}
	public void resetHeartbeat(){
		heartbeatCount = 0L;
	}
	// FI Heartbeat
	//Heartbeat
	private void scheduleAnnouncer(){
        int announceId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Com.getPlugin(), () -> announce(), 20 * 20, 20 * 75);
		handleTask(announceId);
	}

	public void announce(){
		announceCount++;
		String pref = "[" + Com.getMinicatString() + ChatColor.WHITE + "] > " + ChatColor.GRAY;
		sendGlobalMessage(pref + "Joc: " + getGameName() + ", Mapa: " + getActiveMultipleMapName() + ", Progrés: " + Math.round(getGameProgressETA() * 10 * 100)  / 10 + "%");
	}
	
	public Long getAnnounceCount() {
		return announceCount;
	}
	// FI Heartbeat
	public void registerTimestamps(boolean ending){
		if(matchData == null)return;
		for(Player p : getPlayers()){
			PlayerInfo i = getPlayerInfo(p);
			matchData.registerTimestamp(p, ending, i.getKills(), i.getDeaths(), i.getDamageDealt(), i.isAlive(), p.getItemInHand().getTypeId(), i.getBlocksPlaced(), i.getBlocksBroken(), i.getObjectivesCompleted(), i.getSpree());
		}
	}
	//Eventr filtering
	@Override
	protected Boolean verifyEvent(Event evt) {
		// TODO Auto-generated method stub
		return super.verifyEvent(evt) && !EventUtils.interactsWithAny(evt, getSpectators(), 6);
	}

	protected Color getDeterministicColorForPlayer(Player p, boolean alternate){
		int hashCode = p.getName().hashCode();
		Random random = new Random(hashCode);
		Color color1 = Color.fromBGR(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		Color color2 = GUtils.getContrastColor(color1);
		return alternate ? color2 : color1;
	}
}