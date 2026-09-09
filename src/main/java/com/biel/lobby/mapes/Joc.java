package com.biel.lobby.mapes;
import com.biel.lobby.localization.*;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.UUID;
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
import com.biel.lobby.GestorMapes;
import com.biel.lobby.lobby;
import com.biel.lobby.utilities.Catalan;
import com.biel.lobby.utilities.CBUtils;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.PaperMessages;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.MatchData;
import com.biel.lobby.utilities.data.PlayerData;
import com.biel.lobby.utilities.events.skills.SkillPool;
import com.biel.lobby.utilities.events.skills.types.specificskills.*;
import org.bukkit.block.BlockFace;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import com.biel.lobby.utilities.events.statuseffects.FrozenStatusEffect;
import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import com.biel.lobby.utilities.events.statuseffects.AuraRendererStatusEffect;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect
;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

public abstract class Joc extends MapaResetejable {
	protected Boolean JocIniciat = false;
	protected Boolean JocFinalitzat = false;
	/** The roster: one seat per person who has been in this match; see {@link Seat}. */
	private final ArrayList<Seat> seats = new ArrayList<>();
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
	private ArrayList<Integer> handledLifecycleSchedulerTasks = new ArrayList<>();
	
	public Joc() {
		super();
		//Bukkit.broadcastMessage("Class Joc Constructor");		
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
		world.setGameRule(GameRules.ADVANCE_TIME, false);
		world.setGameRule(GameRule.DO_FIRE_TICK, false);
		world.setGameRule(GameRules.SPAWN_MOBS, false);
		world.setGameRule(GameRules.MOB_DROPS, false);
		if (!getResetPlayerOnRespawn()){
			world.setGameRule(GameRules.KEEP_INVENTORY, true);
		}
		setCustomGameRules();
	}
	protected abstract void setCustomGameRules();

	//---------- Seats: the roster that outlives a connection ----------

	/**
	 * A place in this match held by a person. Membership used to be presence in the
	 * world, so a lost connection was a leave: penalized at once, and the instance torn
	 * down the next tick if nobody was left. The seat is what a reconnecting player
	 * takes back. It is keyed by name like every other per-player store here, and it
	 * keeps the UUID only for the login lookup, which is by UUID.
	 */
	public class Seat {
		public enum Role { PLAYER, SPECTATOR }
		public enum State { OCCUPIED, DROPPED, VACANT }
		final String name;
		final UUID uuid;
		Role role = Role.PLAYER;
		State state = State.OCCUPIED;
		long droppedAtMillis = 0;
		int abandonTaskId = -1;
		Seat(Player holder) {
			name = holder.getName();
			uuid = holder.getUniqueId();
		}
		public String getName() { return name; }
		public UUID getUuid() { return uuid; }
		public Role getRole() { return role; }
		public State getState() { return state; }
		public boolean isOccupied() { return state == State.OCCUPIED; }
		public boolean isDropped() { return state == State.DROPPED; }
		/** The holder if they are online, whatever world they are in. */
		public Player getPlayer() { return Bukkit.getPlayer(uuid); }
		private void cancelAbandonment() {
			if (abandonTaskId != -1) Bukkit.getScheduler().cancelTask(abandonTaskId);
			abandonTaskId = -1;
		}
	}
	public Seat seatOf(String name) {
		for (Seat seat : seats) if (seat.name.equals(name)) return seat;
		return null;
	}
	public Seat seatOf(Player ply) {
		return seatOf(ply.getName());
	}
	public Seat seatOf(UUID uuid) {
		for (Seat seat : seats) if (seat.uuid.equals(uuid)) return seat;
		return null;
	}
	public List<Seat> getSeats() {
		return Collections.unmodifiableList(seats);
	}
	/** Everyone playing (not spectating) who has not abandoned the match: online or dropped. The rated set. */
	public List<String> getParticipantNames() {
		List<String> names = new ArrayList<>();
		for (Seat seat : seats) if (seat.role == Seat.Role.PLAYER && seat.state != Seat.State.VACANT) names.add(seat.name);
		return names;
	}
	/** Whether an empty world must be kept: a match in progress with someone expected back. */
	public boolean isWaitingForDroppedPlayers() {
		if (!JocEnMarxa()) return false;
		for (Seat seat : seats) if (seat.isDropped()) return true;
		return false;
	}
	/** The seat a reconnecting player can take back: dropped, in a match that has not ended. */
	public Seat resumableSeatOf(Player ply) {
		Seat seat = seatOf(ply.getUniqueId());
		return seat != null && seat.isDropped() && !JocFinalitzat && world != null ? seat : null;
	}
	/** Occupies the player's seat, making one the first time; the role is the caller's to set. */
	private Seat occupySeat(Player ply) {
		Seat seat = seatOf(ply);
		if (seat == null) {
			seat = new Seat(ply);
			seats.add(seat);
		}
		seat.cancelAbandonment();
		seat.state = Seat.State.OCCUPIED;
		return seat;
	}
	/** How long a dropped player has to come back before the seat is abandoned. A map may set RejoinGrace. */
	public int getRejoinGraceSeconds() {
		if (pMapaActual().ExisteixPropietat("RejoinGrace")) return pMapaActual().ObtenirPropietatInt("RejoinGrace");
		return 120;
	}
	/**
	 * The connection went: the seat is kept for the grace and the match goes on without
	 * the player, since presence is world-derived. No penalty and no elimination until
	 * the grace is over; both were what the ping check in the old leave path was
	 * guessing at, and coming back is a better test than the ping at the moment of
	 * quitting. Before the start the host passes on at once, because the others are
	 * waiting on the start button.
	 */
	private void dropSeat(Player ply) {
		Seat seat = seatOf(ply);
		if (seat == null || !seat.isOccupied()) return;
		seat.state = Seat.State.DROPPED;
		seat.droppedAtMillis = System.currentTimeMillis();
		onSeatDropped(ply);
		if (!JocIniciat && hasHostPrivilleges(seat.name)) passHostToAnotherThan(seat.name);
		int graceSeconds = getRejoinGraceSeconds();
		sendGlobalMessage(MessageKey.MATCH_DISCONNECTED, MessageArgument.text("player", seat.name), MessageArgument.text("time", formatSeconds(graceSeconds)));
		seat.abandonTaskId = Bukkit.getScheduler().scheduleSyncDelayedTask(Com.getPlugin(), () -> abandonSeat(seat), 20L * graceSeconds);
		handleLifecycleTask(seat.abandonTaskId);
	}
	private static String formatSeconds(int seconds) {
		if (seconds % 60 == 0) return (seconds / 60) + " min";
		return seconds + " s";
	}
	/**
	 * The player is back. They are left exactly where Paper put them, with the inventory
	 * Paper restored: routing them through Join would wipe what the match gave them.
	 * Only what Paper does not carry is rebuilt here, and the game adds its own in
	 * {@link #onSeatResumed}.
	 */
	public void resumeSeat(Player ply) {
		Seat seat = resumableSeatOf(ply);
		if (seat == null) return;
		occupySeat(ply);
		if (ply.getWorld() != world) {
			// Not where they left: the seat outlived a world it should not have. Put them back the plain way.
			ply.teleport(getResumeLocation(ply), TeleportCause.PLUGIN);
			if (seat.role == Seat.Role.PLAYER) donarItemsInicials(ply);
		}
		if (seat.role == Seat.Role.SPECTATOR) ply.setGameMode(GameMode.SPECTATOR);
		PlayerInfo info = getPlayerInfo(ply);
		info.lastMoveEvent = ZonedDateTime.now();
		info.lastRespawnEvent = ZonedDateTime.now();
		info.setImmune(true);
		Com.setHeadColor(ply, ChatColor.GRAY);
		if (getDisplayHealthBar() && getShowPlayerHealthBar()) updateHealthSuffix(ply); else Com.setSuffix(ply, "");
		onSeatResumed(ply);
		updateScoreBoard(ply);
		sendGlobalMessage(MessageKey.MATCH_RETURNED, MessageArgument.text("player", ply.getName()));
		Messages.send(ply, MessageKey.MATCH_JOINED, MessageArgument.text("player", ply.getName()), MessageArgument.text("game", getGameName()));
		sendGameInfo(ply);
	}
	/** Where a returning player goes when they are not in the world any more. */
	protected Location getResumeLocation(Player ply) {
		return getRandomSpawnLoc(ply);
	}
	/**
	 * The grace ran out: the seat is vacated and the leave takes effect with the name,
	 * since there is no Player to pass. Then the instance may be released.
	 */
	private void abandonSeat(Seat seat) {
		if (!seat.isDropped()) return;
		vacate(seat, null);
		GestorMapes.ContenidorJoc container = Com.getGest().getGameContainer(getClass());
		if (container != null) container.checkNecessary(this);
	}
	/**
	 * The seat is given up, by the player present ({@code present}, a deliberate leave)
	 * or by the grace running out ({@code present} null). Host transfer, the leaver
	 * penalty and the announcement are the same either way.
	 */
	private void vacate(Seat seat, Player present) {
		seat.cancelAbandonment();
		List<String> attatchments = new ArrayList<>();
		// The leave is judged with the seat still counted: a 1v1 whose other player
		// drops must not read as "alone, nothing to penalize".
		if (present != null) customLeave(present, attatchments); else onSeatAbandoned(seat, attatchments);
		seat.state = Seat.State.VACANT;
		Bukkit.broadcastMessage(getGameDisplayName() + seat.name + ChatColor.GRAY + " ha abandonat la partida" + (attatchments.isEmpty() ? "" : " " + String.join(" ", attatchments)));
	}
	/** The connection was just lost; the player is still online for this call. Cancel what holds them. */
	protected void onSeatDropped(Player ply) {
	}
	/** The player is back in the world. Re-show what the game draws per player and the world does not keep. */
	protected void onSeatResumed(Player ply) {
	}
	/**
	 * The grace ran out with the player away. What their absence means to the rules
	 * goes here; the default is the shared leave logic. {@link #customLeave} is the
	 * same moment with the player present.
	 */
	protected void onSeatAbandoned(Seat seat, List<String> attatchments) {
		registerLeave(seat, attatchments);
	}
	/** The leave itself, by name: the host passes on before the start, and a leaver who mattered pays. */
	private void registerLeave(Seat seat, List<String> attatchments) {
		if (hasHostPrivilleges(seat.name) && !JocIniciat) passHostToAnotherThan(seat.name);
		double punishForLeaving = getPunishForLeaving();
		if (punishForLeaving != 0 && seat.role == Seat.Role.PLAYER) {
			attatchments.add(ChatColor.RED + "[Penalitzat]");
			punishPlayerElo(seat.name, punishForLeaving);
		}
	}
	private void passHostToAnotherThan(String leavingName) {
		List<Player> others = getPlayers().stream().filter(p -> !p.getName().equals(leavingName)).collect(Collectors.toList());
		if (!others.isEmpty()) setHost(GUtils.getRandomListItem(others));
	}
	@Override
	protected void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent evt, Player p) {
		// Not a leave any more: the seat is kept for the grace. Mapa's version called Leave here.
		if (p.getWorld() == getWorld()) dropSeat(p);
	}
	/**
	 * A deliberate leave: /l, a teleport out, a world change. A player without an
	 * occupied seat here has nothing to leave, which is what used to fire twice on a
	 * reconnect (once on the quit, once when the login sent them to the lobby).
	 */
	@Override
	public void Leave(Player ply) {
		Seat seat = seatOf(ply);
		if (seat == null || !seat.isOccupied()) return;
		vacate(seat, ply);
	}

	//---------- End of seats ----------

	public void JocIniciat(){
		if (JocIniciat){Bukkit.broadcastMessage("S'ha intentat iniciar una partida que ja estava iniciada. Operació anul·lada!"); return;}
		if (!canStartGame()) return;
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
	protected boolean canStartGame(){
		return true;
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
	public void clearAllExternals(){
		clearExternals();
		handledLifecycleSchedulerTasks.forEach(tId -> Bukkit.getScheduler().cancelTask(tId));
	}
	public void clearExternals(Player p){
		
	}
	private void cancelAllTasks(){
		handledBukkitSchedulerTasks.forEach(tId -> Bukkit.getScheduler().cancelTask(tId));
	}
	public void handleTask(int tId){
		handledBukkitSchedulerTasks.add(tId);
	}
	public void handleLifecycleTask(int taskId){
		handledLifecycleSchedulerTasks.add(taskId);
	}
	public int scheduleGameplayTask(Runnable task, long delayTicks){
		int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(Com.getPlugin(), task, delayTicks);
		handleTask(taskId);
		return taskId;
	}
	public int scheduleGameplayRepeatingTask(Runnable task, long delayTicks, long periodTicks){
		int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Com.getPlugin(), task, delayTicks, periodTicks);
		handleTask(taskId);
		return taskId;
	}
	public int scheduleTrackedBlockRemoval(Block block, long delayTicks, boolean dropItems){
		if (world == null || block.getWorld() != world) return -1;
		Material expectedMaterial = block.getType();
		return scheduleGameplayTask(() -> {
			if (world == null || block.getWorld() != world || block.getType() != expectedMaterial) return;
			if (dropItems) {
				block.breakNaturally();
			} else {
				block.setType(Material.AIR);
			}
		}, delayTicks);
	}
	/**
	 * Shuts a player in ice for {@code ticks}: ice on the four sides at foot level and over
	 * the head, the player centred in the cell and unable to break out ({@link FrozenStatusEffect});
	 * the ice melts away on its own. The frost archer's prison and the gel snowmen's.
	 */
	/**
	 * One frame of an aura at a creature's feet: twelve dust motes of the colour on a ring
	 * of the radius, turned by the angle, and a few soul flames rising inside it. The
	 * Guardian's and the hero snowman's; the caller advances the angle every frame.
	 */
	public static void auraRing(Location feet, Color colour, double radius, double turn, int soulFlames) {
		Particle.DustOptions dust = new Particle.DustOptions(colour, 1.1F);
		for (int i = 0; i < 12; i++) {
			double angle = turn + i * Math.PI / 6;
			feet.getWorld().spawnParticle(Particle.DUST, feet.clone().add(radius * Math.cos(angle), 0.1, radius * Math.sin(angle)), 1, 0, 0, 0, 0, dust);
		}
		for (int i = 0; i < soulFlames; i++) {
			double angle = Math.random() * 2 * Math.PI, r = Math.random() * radius;
			feet.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, feet.clone().add(r * Math.cos(angle), 0.1, r * Math.sin(angle)), 0, 0, 1, 0, 0.04);
		}
	}

	public void encaseInIce(Player victim, int ticks) {
		Block feet = victim.getLocation().getBlock();
		for (BlockFace face : List.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST)) {
			Block side = feet.getRelative(face);
			if (side.getType().isSolid()) continue;
			side.setType(Material.ICE);
			scheduleTrackedBlockRemoval(side, ticks, false);
		}
		Block roof = feet.getRelative(0, 2, 0);
		if (!roof.getType().isSolid()) {
			roof.setType(Material.ICE);
			scheduleTrackedBlockRemoval(roof, ticks, false);
		}
		Location centred = victim.getLocation();
		centred.setX(feet.getX() + 0.5);
		centred.setZ(feet.getZ() + 0.5);
		victim.teleport(centred);
		FrozenStatusEffect frozen = new FrozenStatusEffect(victim);
		frozen.setRemainingTicks(ticks);
		getPlayerInfo(victim).addStatusEffect(frozen);
		world.playSound(victim.getLocation(), Sound.BLOCK_GLASS_PLACE, 1F, 0.7F);
		world.playSound(victim.getLocation(), Sound.ENTITY_PLAYER_HURT_FREEZE, 1F, 1F);
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
		// Fence gameplay first. Cleanup and persistence failures must not leave a
		// logically completed match running forever.
		JocFinalitzat = true;
		// Whoever is away when it ends is neither abandoned nor penalized; there is nothing left to come back to.
		for (Seat seat : seats) seat.cancelAbandonment();
		try {
			if (world != null) world.setPVP(false);
			customJocFinalitzat();
		} catch (RuntimeException exception) {
			Com.getPlugin().getLogger().log(java.util.logging.Level.SEVERE,
					"Game-specific finalization failed for " + getGameName(), exception);
		} finally {
			clearExternals();
		}
		try {
			if(!won && matchData != null) matchData.registerEnd(-1); //Tie / no winner
			registerTimestamps(true);
		} catch (RuntimeException exception) {
			Com.getPlugin().getLogger().log(java.util.logging.Level.SEVERE,
					"Could not persist final match state for " + getGameName(), exception);
		}
		try {
			updateScoreBoards();
		} catch (RuntimeException exception) {
			Com.getPlugin().getLogger().log(java.util.logging.Level.WARNING,
					"Could not update final scoreboards for " + getGameName(), exception);
		}
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
		updateElo(List.of(p.getName()));
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
		updateEloOrdered(wList.stream().map(Player::getName).collect(Collectors.toList()));
	}
	protected boolean onlyPlayersFromSameIP(){
		String firstAddress = null;
		for(Player p : getPlayers()){
			java.net.InetSocketAddress socketAddress = p.getAddress();
			if (socketAddress == null) return false;
			java.net.InetAddress resolvedAddress = socketAddress.getAddress();
			String numericAddress = resolvedAddress != null
					? resolvedAddress.getHostAddress()
					: socketAddress.getHostString();
			if(firstAddress == null) {
				firstAddress = numericAddress;
			} else if(!firstAddress.equals(numericAddress)) {
				return false;
			}
		}
		return firstAddress != null;
	}
	protected boolean canBeRanked(){
		return(segonsTranscorreguts() > (onlyPlayersFromSameIP() ? 60 * 15 : 20) && getEloK() != 0 && Com.getPlugin().isInRankedMode() && !unfairFlag);
	}
	/**
	 * Rates the match by seat, not by presence: a player away for a moment at the end
	 * is still on their side of the result, and one who abandoned was already charged.
	 */
	protected void updateElo(List<String> winnerNames){
		if(!canBeRanked()){
			sendGlobalMessage(ChatColor.BLUE + "Partida irrellevant al rànquing");
			return;
		}
		List<String> participants = getParticipantNames();
		List<String> winners = winnerNames.stream().filter(participants::contains).collect(Collectors.toList());
		List<String> loosers = participants.stream().filter(name -> !winners.contains(name)).collect(Collectors.toList());
		ArrayList<Double> elo_winners = readRatings(winners);
		ArrayList<Double> elo_loosers = readRatings(loosers);
		if(elo_winners == null || elo_loosers == null){
			announceRatingsUnavailable();
			return;
		}
		ArrayList<ArrayList<Double>> changes = EloUtils.calculateEloGroupChange(elo_winners, elo_loosers, getEloK(), false);
		// Results come back in player order. Looking a change up by its value, as this
		// used to, collided whenever two players earned the same amount - which every
		// pair of newcomers does, since they all start at the average - and applied it
		// twice to the first while the second got nothing.
		ArrayList<Double> winnerChanges = changes.get(0);
		ArrayList<Double> looserChanges = changes.get(1);
		for (int i = 0; i < winners.size(); i++) registerEloChange(winners.get(i), winnerChanges.get(i));
		for (int i = 0; i < loosers.size(); i++) registerEloChange(loosers.get(i), looserChanges.get(i));
	}
	protected void updateEloOrdered(List<String> orderedWinnerNames){
		if(!canBeRanked()){
			sendGlobalMessage(ChatColor.BLUE + "Partida irrellevant al rànquing");
			return;
		}
		List<String> participants = getParticipantNames();
		List<String> orderedWinners = orderedWinnerNames.stream().filter(participants::contains).collect(Collectors.toList());
		ArrayList<Double> elo_winners = readRatings(orderedWinners);
		if(elo_winners == null){
			announceRatingsUnavailable();
			return;
		}
		ArrayList<Double> changes = EloUtils.calculateEloGroupChange(elo_winners, getEloK(), false);
		for (int i = 0; i < orderedWinners.size(); i++) registerEloChange(orderedWinners.get(i), changes.get(i));
	}
	/**
	 * Every player's stored rating, in the same order, or null when any of them
	 * cannot be read. A match is rated with real ratings or not at all: a stand-in
	 * value for one player would move everyone else's rating by the wrong amount.
	 */
	private ArrayList<Double> readRatings(List<String> names){
		ArrayList<Double> ratings = new ArrayList<>(names.size());
		for(String name : names){
			OptionalDouble rating = new PlayerData(name).readElo();
			if(rating.isEmpty()) return null;
			ratings.add(rating.getAsDouble());
		}
		return ratings;
	}
	private void announceRatingsUnavailable(){
		Com.getPlugin().getLogger().warning("Ratings unavailable at the end of " + getGameName() + " / " + getMapName() + "; the match was not rated");
		sendGlobalMessage(ChatColor.RED + "No s'ha pogut llegir l'elo d'algun jugador; aquesta partida no puntua.");
	}
	/** By name, so it works for a player who is away; they are told only if online. */
	protected void registerEloChange(String name, double change){
		PlayerData playerData = new PlayerData(name);
		Player p = Bukkit.getPlayer(name);
		if(!playerData.addElo(change)){
			Com.getPlugin().getLogger().warning("Could not update the rating of " + name + " after " + getGameName() + ": the database did not answer");
			if(p != null) p.sendMessage(ChatColor.RED + "No s'ha pogut actualitzar el teu elo.");
			return;
		}
		String cStr = (change > 0 ? ChatColor.DARK_GREEN + "+" : ChatColor.DARK_RED + "") + String.format(Locale.ROOT, "%.1f", change);
		if(p != null) p.sendMessage(ChatColor.DARK_AQUA + "Elo: " + ChatColor.WHITE + Math.round(playerData.getElo()) + " (" + cStr  + ChatColor.WHITE + ")");
	}
	/**
	 * The rating weight this map plays for before team balance is applied: the map's
	 * own K property when it has one, else the default for the game's development
	 * state, so experimental games still count but lightly. Zero means unranked.
	 */
	public double getEloBaseK(){
		return eloBaseKFrom(pMapaActual());
	}
	/** What a template map would play for, before any instance of it exists. Null name for a single-map game. */
	public double getTemplateEloBaseK(String templateMapName){
		return eloBaseKFrom(pTemplate(templateMapName));
	}
	private double eloBaseKFrom(GestorPropietats properties){
		if(properties != null && properties.ExisteixPropietat("K")){
			return properties.ObtenirPropietatInt("K");
		}
		GestorMapes.ContenidorJoc registration = Com.getGest().getGameContainer(getClass());
		// A game outside the registry cannot be reached from the menu; it plays for nothing.
		return registration == null ? 0 : registration.getDevelopmentState().getDefaultEloK();
	}
	double getEloK(){
		return getEloBaseK() * getEloM();
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
		if (getPlayers().size() == 1) return getRandomSpawnLoc(pl);
		ArrayList<Location> locs = pMapaActual().ObtenirLocations("s", world); //Llista spawns
		Location l = locs.stream()
				.sorted((l1, l2) -> (int) (GUtils
						.getNearestEntity(l2, getEnemies(pl))
						.getLocation().distanceSquared(l2) - GUtils.getNearestEntity(l1, getEnemies(pl))
						.getLocation().distanceSquared(l1))
				).skip(Utils.NombreEntre(0, 3))
				.findFirst()
				.get();
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
			if (com.biel.lobby.guide.GameGuide.of(getGameName()).exists()) sendPlayerMessage(p, ChatColor.GRAY + "Obre el llibre amb /info o /i.");
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
			sendGlobalMessage(MessageKey.MATCH_ADMIN, MessageArgument.text("player", p.getName()));
		}
	}
	public void setHost(String name){
		host = name;
	}
	public boolean hasHostPrivilleges(Player p){
		return hasHostPrivilleges(p.getName());
	}
	public boolean hasHostPrivilleges(String name){
		return host != null && host.equalsIgnoreCase(name);
	}
	@Override
	public void Join(Player ply) {
		if (!canJoin(ply)) {
			Messages.send(ply, MessageKey.MATCH_JOIN_UNAVAILABLE);
			return;
		}
		if(getPlayers().size() == 0)setHost(ply);
		occupySeat(ply).role = Seat.Role.PLAYER;
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
		ArrayList<ItemStack> startingItems = getStartingItems(ply);
		if (startingItems != null){
			Utils.donarItemsPlayer(ply, startingItems);
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
		viewers.removeIf(this::isSpectator);
		return viewers; //Futurs espectadors
	}
	public ArrayList<Player> getEnemies(Player p){
		ArrayList<Player> enemies = getViewers();
		enemies.remove(p);
		return enemies; //Futurs espectadors
	}
	/** The spectators who are online and in the world. */
	public List<Player> getSpectators(){
		List<Player> spectators = new ArrayList<>();
		for (Player viewer : getViewers()) if (isSpectator(viewer)) spectators.add(viewer);
		return spectators;
	}
	public boolean getAllowSpectators(){
		return true;
	}
	public Boolean isSpectator(Player ply){
		Seat seat = seatOf(ply);
		return seat != null && seat.role == Seat.Role.SPECTATOR;
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
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.GOLD_BLOCK), Messages.sharedItemMarker(MessageKey.MATCH_CAMERA)), ply, event -> {
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
			
			Messages.send(p, MessageKey.MATCH_SPECTATING, MessageArgument.text("player", ply.getName()));
		}
		occupySeat(ply).role = Seat.Role.SPECTATOR;
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
		if (isSpectator(ply)){
			Utils.clearPlayer(ply);
			ply.setAllowFlight(false);
			ply.setCanPickupItems(true);
			ply.setFlying(false);
			occupySeat(ply).role = Seat.Role.PLAYER;
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
			getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORK_SHOOT, DyeColor.BLUE.getDyeData());
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
		ItemStack ball = new ItemStack(Material.SNOWBALL);
		ball.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
		ball.setAmount(Math.max(1, amount));
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
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 1, 0));
					//damaged.setVelocity(new Vector(0, 0.1, 0));
					damager.teleport(damaged.getEyeLocation().add(0, 0.5, 0), TeleportCause.PLUGIN);
					GUtils.healDamageable(damager, 0.4D);
					damager.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * 2, 1));
				}
			}
		}
		//---
		if (isSpectator(damager) || isSpectator(damaged)){evt.setCancelled(true);}
		if(getDisplayHealthBar())updateHealthSuffix(damaged);
		PlayerInfo i = getPlayerInfo(damaged);
		if(i.isImmune()){
			evt.setCancelled(true);
			
			
			PaperMessages.sendActionBar(damager, ChatColor.GRAY + "El jugador " + damaged.getName() + " és invulnerable.", 150);
			
			getWorld().playSound(damager.getLocation(), Sound.ENCHANT_THORNS_HIT, 1.2F, 0.88F);
			getWorld().playEffect(damaged.getEyeLocation(), Effect.FIREWORK_SHOOT, DyeColor.BLUE.getDyeData());
			getWorld().playEffect(damager.getEyeLocation(), Effect.FIREWORK_SHOOT, DyeColor.RED.getDyeData());
		}
		if(!evt.isCancelled()){
			PlayerInfo attackerInfo = getPlayerInfo(damager);
			attackerInfo.setDamageDealt(attackerInfo.getDamageDealt() + evt.getDamage());
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
		int resetTaskId = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(lobby.getPlugin(), () -> {
			if (world != null) allOnTheLobby();
		}, delay);
		handleLifecycleTask(resetTaskId);
	}
	
	@Override
	protected void customJoin(Player ply){
		//getPlayerInfo(ply);
		Utils.clearPlayer(ply);
		if (getGameState() == GameState.InGame) {
			
			addSpectator(ply);
			
		} else {
			sendGlobalMessage(MessageKey.MATCH_JOINED, MessageArgument.text("player", ply.getName()), MessageArgument.text("game", getGameName()));
			donarItemsPreparatiusGenerals(ply);
			
		}
		
		updateScoreBoard(ply);
		com.biel.lobby.guide.GameGuide.of(getGameName()).openOnce(ply);
	}
	/** The player leaves on purpose, still here to be told; a lost connection goes through the seat instead. */
	@Override
	protected void customLeave(Player ply, List<String> attatchments) {
		Seat seat = seatOf(ply);
		if (seat != null) registerLeave(seat, attatchments);
	}
	public void punishPlayerElo(String name, double amount){
		unfairFlag = true;
		registerEloChange(name, amount * -1);
	}
	public double getPunishForLeaving(){
		// A leaver is punished only where the match could have counted: the same
		// conditions a result needs, plus enough of the match played to have mattered.
		if(!JocEnMarxa() || getEloK() == 0 || getParticipantNames().size() <= 1 || !Com.getPlugin().isInRankedMode()) return 0;
		double progress = getGameProgressETA();
		if(progress < 0.25) return 0;
		// Hours as a fraction: toHours() truncates, which made this term zero for every game shorter than an hour.
		double maxPunish = 4.2 + getEloK() / 8 + getAvgGameLength().toMillis() / 3_600_000d * 4;
		if(progress > 0.8) return maxPunish;
		return Math.max(0, maxPunish * (progress - 0.2));
	}
	public double getGameProgressETA(){
		if(!JocEnMarxa()){
			if(!JocIniciat)return 0;
			if(JocFinalitzat)return 1;
		}
		long avgGameLengthMillis = getAvgGameLength().toMillis();
		//Without a reference length there is no progress to estimate. Dividing by it
		//anyway yields Infinity, which Math.round turns into Long.MAX_VALUE at every
		//display site - the announcer once read "Progres: 922337203685477580%".
		if(avgGameLengthMillis <= 0)return 0;
		return getGameTime().toMillis() / (double)avgGameLengthMillis;
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
                Com.sendLobbyMessage(ChatColor.GRAY + "La partida " + Catalan.de(ChatColor.DARK_AQUA + getGameName()) + ChatColor.GRAY + " " + status);
                if (anArray >= 150) {
                    sendGlobalMessage("És possible que la partida s'hagi estancat. En aquest cas, feu /l i començeu-ne una de nova.");
                }
            }
        }
		
		lastProgressETA = gameProgressETA;
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
            if(message)Messages.send(ply, MessageKey.MATCH_START_WAIT, MessageArgument.number("seconds", r));
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

		ItemButton btnStartGame = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BLAZE_ROD), Messages.sharedItemMarker(MessageKey.MATCH_START_BUTTON)), ply, event -> iniciarCommand(event.getPlayer()));
		if(hasHostPrivilleges(ply))inventory.setItem(0, btnStartGame.getItemStack());
		ItemButton infoButton = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.POWERED_RAIL), Messages.sharedItemMarker(MessageKey.MATCH_INFO_BUTTON)), ply, event -> com.biel.lobby.guide.GameGuide.of(getGameName()).open(event.getPlayer()));
		ItemButton button2 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.PLAYER_HEAD), Messages.sharedItemMarker(MessageKey.MATCH_ADD_BUTTON)), ply, event -> {
            final List<Player> lobbyPlayers = lobby.getLobbyWorld().getPlayers();
            IconMenu menu = new IconMenu(Messages.menuTitleMarker(MessageKey.MATCH_ADD_BUTTON), 27, event12 -> {
				event12.setWillClose(true);
				//Obrir mapa
				int pos = event12.getPosition();
				if (pos != 26){
				Player pl = lobbyPlayers.get(pos);
				if (lobby.isOnLobby(Bukkit.getPlayer(pl.getName()))){
				Join(pl);
				}else{
				Messages.send(ply, MessageKey.MATCH_PLAYER_LEFT, MessageArgument.text("player", pl.getName()));
				}
				
				}else{
					for(Player pl : lobbyPlayers){
						if (lobby.isOnLobby(Bukkit.getPlayer(pl.getName()))){
							Join(pl);
						} else {
							Messages.send(ply, MessageKey.MATCH_PLAYER_LEFT, MessageArgument.text("player", pl.getName()));
						}
					}
				
				}
			
			});

            for(Player p : lobbyPlayers){
                Material m = Com.getSkullIconMaterial(p);
                ItemStack stack = new ItemStack(m, 1);
                //stack.setAmount(eq.getPlayers().size());
                menu.setOption(lobbyPlayers.indexOf(p), stack, ChatColor.AQUA + p.getName(),Messages.sharedItemMarker(MessageKey.MATCH_FROM_LOBBY));
            }
            //if (AlgunMapaDisponible() == false){
            menu.setOption(26, new ItemStack(Material.SPONGE, 1), Messages.sharedItemMarker(MessageKey.MATCH_ADD_ALL), Messages.sharedItemMarker(MessageKey.MATCH_ADD_ALL_LORE));
            //}

            menu.open(ply);
        });
		//if(hasHostPrivilleges(ply))inventory.setItem(7, button2.getItemStack()); // AND isOp()
		inventory.setItem(6, infoButton.getItemStack());
		ItemButton btnInvitePlayers = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.DETECTOR_RAIL), Messages.sharedItemMarker(MessageKey.MATCH_INVITE_BUTTON)), ply, event -> {
            final List<Player> lobbyPlayers = lobby.getLobbyWorld().getPlayers();
            IconMenu menu = new IconMenu(Messages.menuTitleMarker(MessageKey.MATCH_INVITE_BUTTON), 27, event1 -> {
			event1.setWillClose(true);
			//Obrir mapa
			int pos = event1.getPosition();
			if (pos != 26){
				
				Player pl = lobbyPlayers.get(pos);
				
				if (lobby.isOnLobby(Bukkit.getPlayer(pl.getName()))){
					inviteToGame(pl);
				} else {
					Messages.send(ply, MessageKey.MATCH_PLAYER_LEFT, MessageArgument.text("player", pl.getName()));
				}
				
			} else {
				for(Player pl : lobbyPlayers){
					if (lobby.isOnLobby(Bukkit.getPlayer(pl.getName()))){
						inviteToGame(pl);
					} else {
						Messages.send(ply, MessageKey.MATCH_PLAYER_LEFT, MessageArgument.text("player", pl.getName()));
					}
					
				}
			
			}

            });

            for(Player p : lobbyPlayers){
                Material m = Com.getSkullIconMaterial(p);
                ItemStack stack = new ItemStack(m, 1);
                //stack.setAmount(eq.getPlayers().size());
                menu.setOption(lobbyPlayers.indexOf(p), stack, ChatColor.AQUA + p.getName(),Messages.sharedItemMarker(MessageKey.MATCH_FROM_LOBBY));
            }

            menu.setOption(26, new ItemStack(Material.SPONGE, 1), Messages.sharedItemMarker(MessageKey.MATCH_INVITE_ALL), Messages.sharedItemMarker(MessageKey.MATCH_INVITE_ALL_LORE));


            menu.open(ply);
        });
		if(hasHostPrivilleges(ply))inventory.setItem(8, btnInvitePlayers.getItemStack());
		//		if (this instanceof JocEquips){
		//			if (ply.isOp()){
		//				Utils.donarItem(ply, Material.LEGACY_IRON_SPADE, ChatColor.RED + "Bloqueja el canvi d'equip");
		//			}
		//			Utils.donarItem(ply, Material.WHITE_WOOL, ChatColor.GREEN + "Canvia d'equip");
		//		}

	}
	public void inviteToGame(Player player){
		
		// String join = "\n\n    " + ChatColor.GREEN + ChatColor.UNDERLINE + host + ChatColor.RESET + ChatColor.GREEN + " t'ha convidat a " + getGameName();
		// String join2 = "\n    " + ChatColor.GOLD + ChatColor.UNDERLINE + "Clica aquí per entrar al joc\n\n";
		
		Messages.send(player, MessageKey.MATCH_INVITE, MessageArgument.text("player", host), MessageArgument.text("game", getGameName()));
		Component clickToJoinMsg = Messages.component(player, MessageKey.MATCH_INVITE_CLICK)
				// RUN_COMMAND is handled by both vanilla clients and protocol bots. Paper's
				// server-side callback click action can be acknowledged by a non-vanilla
				// client without ever invoking the callback.
				.clickEvent(ClickEvent.runCommand("/minicatjoin " + getMapName()));
		player.sendMessage(clickToJoinMsg);
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
		// Moving or turning at all clears spawn immunity. The magnitudes are taken
		// as absolute values because the test used to be signed: a player who only
		// ever walked in -X/-Z, or who only ever turned one way, stayed immune for
		// the whole life. Immunity is set true on every respawn, so that made a
		// player unkillable by accident depending on which way they happened to
		// face - and in Quakecraft it also broke the railgun chain, since the
		// victim collector skips immune players entirely.
		float yawTurn = Math.abs(evt.getFrom().getYaw() - evt.getTo().getYaw());
		if(Math.abs(v.getX()) > 0.01 || Math.abs(v.getZ()) > 0.01 || yawTurn > 1)
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
	/**
	 * By name, for a player who may be away. Null when none exists: the record is made
	 * by the Player form, which knows the game's own PlayerInfo class to instantiate.
	 */
	public PlayerInfo getPlayerInfo(String name){
		for (PlayerInfo i : InfoStorage){
			if (name.equals(i.getName())) return i;
		}
		return null;
	}
	public class PlayerInfo{
		String name;
		int value;
		int spree;
		int additionalSkills = 0;
		double speedModifier = 0;
		boolean immune = true;
		// By name: a Player held here went stale when its holder reconnected, and the
		// kill credit with it.
		String lastDamagerName = null;
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
		private boolean hasActivePlayer() {
			Player player = getPlayer();
			return player != null && player.isOnline() && world != null && player.getWorld() == world;
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
		/** The last player to hurt this one, if they are online. */
		public Player getLastDamager() {
			return lastDamagerName == null ? null : Bukkit.getPlayer(lastDamagerName);
		}
		public void setLastDamager(Player lastDamager) {
			this.lastDamagerName = lastDamager == null ? null : lastDamager.getName();
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
				getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 1, SL - 1, true, true));
			}else{
				getPlayer().removePotionEffect(PotionEffectType.SLOWNESS);
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
			Player player = getPlayer();
			if (player == null || !player.isOnline() || world == null || player.getWorld() != world) return null;
			BlockIterator i = new BlockIterator(getWorld(), player.getLocation().toVector(), new Vector(0, -1, 0), 1D, 30);
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
			if (i.hasActivePlayer()) i.ultraTick();
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
			if (i.hasActivePlayer()) i.tick();
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
			matchData.registerTimestamp(p, ending, i.getKills(), i.getDeaths(), i.getDamageDealt(), i.isAlive(), p.getInventory().getItemInMainHand().getType().getKey().toString(), i.getBlocksPlaced(), i.getBlocksBroken(), i.getObjectivesCompleted(), i.getSpree());
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
