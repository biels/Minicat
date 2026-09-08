package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Input;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.inkwars.InkSplash;
import com.biel.lobby.mapes.jocs.inkwars.InkStream;
import com.biel.lobby.mapes.jocs.inkwars.InkSurfaceFlow;
import com.biel.lobby.mapes.jocs.inkwars.WetInk;
import com.biel.lobby.mapes.jocs.inkwars.SquidMotion;
import com.biel.lobby.mapes.jocs.inkwars.SquidCollision;
import com.biel.lobby.utilities.PaperMessages;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;


public class InkWars extends JocEquips {
	/** A block never holds more ink than this; what a stroke adds beyond it is lost. */
	static final double MAX_INK_PER_BLOCK = 3;
	/** The most ink that leaves a block in one flow step. */
	static final double FLOW_STEP = 0.6;
	static final int TICKS_BETWEEN_FLOW_STEPS = 3;
	final HashMap<Block, WetInk> wetInk = new HashMap<>();
	static final BlockFace[] SIDES = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
	/** Ticks of slower swimming after the squid rounds a corner. */
	static final int CORNER_TICKS = 10;
	/**
	 * The squid is a cart on whatever surface it touches: a heading and a speed. Blocks per tick at full speed (a sprint is 0.28); speed gained per tick with the keys
	 * along the heading; speed lost per tick with the keys against it (the brake); the share of speed kept per tick when coasting; how far the heading turns per tick;
	 * and below this speed the heading simply snaps to the keys, so a standing squid sets off in any direction.
	 */
	static final double SQUID_TOP_SPEED = 0.65;
	static final double SQUID_ACCELERATION = 0.02;
	/**
	 * A paid jet burst can exceed swimming speed up to this ceiling. After release,
	 * overspeed decays back to ordinary swimming without taking control away.
	 */
	static final double SQUID_THRUST_COST = 10.0 / 32;
	static final double SQUID_THRUST_CEILING = 1.4;
	static final double SQUID_OVERSPEED_KEPT = 0.97;
	static final double SQUID_COAST = 0.985;
	static final int SQUID_JUMP_BUFFER_TICKS = 3;
	static final int SQUID_EDGE_GRACE_TICKS = 2;
	static final int SQUID_TURBO_TRAIL_TICKS = 12;
	static final InkStream.Load SQUID_TRAIL_LOAD = new InkStream.Load(0.12, 0.65, 0, InkWars.HOSE_GRAVITY);
	static final double SQUID_CRAWL_SPEED = 0.06;
	/** Gravity in this world, vanilla is 0.08: jumps go higher, falls and leaps take longer, and nothing here hurts on landing. A squid in the air feels far less. */
	static final double INK_GRAVITY = 0.05;
	static final double SQUID_GRAVITY = 0.026;
	/** A squid idle on a wall sinks this much per tick, the little gravity it does feel there; pressing toward a wall climbs at no less than this speed, from a standstill. */
	static final double SQUID_WALL_SAG = 0.006;
	static final double SQUID_CLIMB_MIN_SPEED = 0.2;
	/**
	 * The squid's ink reserve, 0 to 1: refilled by a fixed share per tick on its own colour, fast or still alike; drained on neutral ground by a share per tick plus a share
	 * per block swum, faster on enemy ink; at zero the squid is forced out. The ground is judged by what it was before this dive's own strip, so the strip feeds nobody.
	 * About a second and a half on own ink fills it; a full reserve buys about twenty blocks of neutral ground at full speed, twelve of enemy ink.
	 */
	static final double RESERVE_REFILL = 0.03;
	static final double RESERVE_DRAIN_NEUTRAL = 0.015;
	static final double RESERVE_DRAIN_NEUTRAL_PER_BLOCK = 0.028;
	static final double RESERVE_DRAIN_ENEMY = 0.024;
	static final double RESERVE_DRAIN_ENEMY_PER_BLOCK = 0.05;
	/** Ink sacs shown in the squid's hand for a full reserve. */
	static final int INK_SACS_FOR_FULL_RESERVE = 32;
	/** The squid's hop off a floor and its leap off a wall, in blocks per tick upward; in the air the keys still turn it and push it at this share of the throttle, and the speed bleeds by this share per tick. */
	static final double SQUID_HOP = 0.42;
	static final double SQUID_WALL_LEAP = 0.35;
	static final double SQUID_AIR_THROTTLE = 0.3;
	static final double SQUID_AIR_DRAG = 0.98;
	/**
	 * The squid's body is a point the camera sits on, kept this radius off every surface; over a floor it rides this high above the block,
	 * steps this tall are taken in stride up or down, and the camera sits a standing player's eye height above the seat, which is how the carrier is placed.
	 */
	static final double SQUID_RADIUS = 0.3;
	static final double SQUID_RIDE_HEIGHT = 0.5;
	static final double SQUID_STEP = 0.6;
	static final double SQUID_CAMERA_HEIGHT = 1.62;
	static final double SQUID_CONTACT_SKIN = 0.002;
	static final double SQUID_MOVEMENT_STEP = 0.15;
	static final int SQUID_CONTACT_GRACE_TICKS = 3;
	static final int SQUID_DETACH_TICKS = 5;
	/** The Roller's head sits this far ahead of the feet; the stroke is laid there. */
	static final double ROLLER_AHEAD = 1.0;
	/**
	 * The Hose runs whenever it is in hand: a jet of ink parcels thrown at this speed in blocks per tick, this many a tick, scattered this much at the nozzle,
	 * each carrying this much ink into a splash of this radius where it lands, stinging a body it hits this much.
	 * Pinching the tip, right-click held, is the second row: faster, a line instead of a spray, one parcel a tick into a smaller spot, a harder sting:
	 * about twice the reach for half the paint. The two blend over half a second either way, so the jet tightens and relaxes rather than switching.
	 * A press of right-click keeps the pinch this many ticks, longer than the client's repeat of a held click, so holding it is one steady pinch.
	 */
	static final double HOSE_SPEED = 0.9, HOSE_SCATTER = 0.035, HOSE_PARCEL_INK = 0.22, HOSE_SPLASH_RADIUS = 0.8, HOSE_STING = 1.0, HOSE_PARCELS_PER_TICK = 2, HOSE_GRAVITY = 0.06;
	static final double PINCHED_SPEED = 2.2, PINCHED_SCATTER = 0.006, PINCHED_PARCEL_INK = 0.3, PINCHED_SPLASH_RADIUS = 0.5, PINCHED_STING = 1.5, PINCHED_PARCELS_PER_TICK = 1, PINCHED_GRAVITY = 0.022;
	static final int PINCH_BLEND_TICKS = 10;
	static final int PINCH_TRIGGER_TICKS = 6;
	final Predicate<Block> solid = block -> !block.isPassable();
	int wetInkTicks = 0;
	int paintableFloorBlocks = 0;
	static final int BASE_PAINT_RADIUS = 6;
	/** Seconds before the end during which the sidebar hides the shares. */
	static final int BLACKOUT_SECONDS = 60;
	boolean blackoutAnnounced = false;
	@Override
	public String getGameName() {
		return "InkWars";
	}
	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		//		ArrayList<Equip> equips = new ArrayList<Equip>();
		//		equips.add(new EquipInkWars(DyeColor.ORANGE, DyeColor.RED, "Orange")); //Id 0
		//		equips.add(new EquipInkWars(DyeColor.LIGHT_BLUE, DyeColor.BLUE, "Blue")); //Id 1
		//		return equips;
		return null;
	}
	@Override
	public EquipInkWars obtenirEquip(Player ply) { //Auto cast team getter
		return obtenirEquip(ply, EquipInkWars.class);
	}
	@Override
	protected ArrayList<String> getGameInfo(Player p) {
		ArrayList<String> i = new ArrayList<>();
		i.add("You win by having more painted blocks than the enemy when the time ends");
		i.add("You win by having more than 80% of the map painted");
		i.add("In this map you level up every " + getBlockCountToLevelUp() + " effectively painted blocks");
		i.add("The ink won't dry instantly, use it to your advantage");
		i.add("One kit: the Roller paints the floor ahead of you while you walk with it, the Hose in hand throws a jet of ink that flies in an arc, hold right-click to pinch the tip for a longer thinner shot, ink balls splash at range");
		i.add("Press sneak on your own ink for squid form: invisible, fast, healing, with momentum; press again to stand up");
		i.add("The squid lives on ink (the green bar): it refills on your colour, drains on neutral ground, faster on enemy ink, and at zero you are thrown back on your feet; the strip you lay as you go does not count as yours until you stand up");
		i.add("A squid runs up any wall it touches, across ceilings and round every corner as if the floor continued; the keys work the same everywhere: on a wall, toward it climbs, away from it descends; look where you like; jump hops, leaps off a wall, drops off a ceiling");
		i.add("A squid gathers speed on a straight line and bleeds it on sharp turns; right-click the ink sacs to burn ten of them for a thrust past the top speed; what is left when you stand up goes off as a splash that hurts");
		i.add("Ink balls reload x5 faster on your own colour, x8 while submerged");
		return i;
	}
	@Override
	protected void setCustomGameRules() {
		// KeepInventory = true (auto) @  getResetPlayerOnRespawn()
	}
	@Override
	protected void donarEfectesInicials(Player ply) {
		// TODO Auto-generated method stub
		super.donarEfectesInicials(ply);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 3, true), true);
		ply.getAttribute(Attribute.GRAVITY).setBaseValue(INK_GRAVITY);
		ply.getAttribute(Attribute.FALL_DAMAGE_MULTIPLIER).setBaseValue(0);
	}
	@Override
	public boolean getResetPlayerOnRespawn() {
		return false;
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
		return 0; //No skills
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		Equip e = obtenirEquip(ply);
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		return items;
	}
	@Override
	protected void customJocIniciat() {
		super.customJocIniciat();
		setGiveStartingItemsRespawn(false);
		equipKits();
		countPaintableFloor();
		paintBases();
	}
	/** Every floor block that can take ink, counted once at the start: the sidebar shows each team's share of that, not of what has been painted so far. */
	void countPaintableFloor(){
		paintableFloorBlocks = 0;
		for(org.bukkit.Chunk chunk : getWorld().getLoadedChunks()){
			org.bukkit.ChunkSnapshot snapshot = chunk.getChunkSnapshot();
			int floor = getWorld().getMinHeight();
			int ceiling = getWorld().getMaxHeight() - 1;
			for(int x = 0; x < 16; x++){
				for(int z = 0; z < 16; z++){
					int top = Math.min(ceiling, snapshot.getHighestBlockYAt(x, z) + 1);
					for(int y = floor; y <= top; y++){
						Material material = snapshot.getBlockType(x, y, z);
						if(material.isAir())continue;
						Block block = chunk.getBlock(x, y, z);
						if(!(isPaintable(block) || isPaintableUnsafely(block)))continue;
						if(y < ceiling && !snapshot.getBlockType(x, y + 1, z).isAir())continue;
						paintableFloorBlocks++;
					}
				}
			}
		}
		Bukkit.getLogger().info("[lobby] InkWars " + getWorld().getName() + ": " + paintableFloorBlocks + " paintable floor blocks");
	}
	/** A disc of the team's colour around each spawn: a runway for the first squid, and the place the reserve refills between sorties. */
	void paintBases(){
		for(Equip e : Equips){
			EquipInkWars team = (EquipInkWars) e;
			Location spawn = team.getTeamSpawnLocation();
			if(spawn == null)continue;
			for(int dx = -BASE_PAINT_RADIUS; dx <= BASE_PAINT_RADIUS; dx++){
				for(int dz = -BASE_PAINT_RADIUS; dz <= BASE_PAINT_RADIUS; dz++){
					if(dx * dx + dz * dz > BASE_PAINT_RADIUS * BASE_PAINT_RADIUS)continue;
					for(int dy = 1; dy >= -4; dy--){
						Block candidate = spawn.clone().add(dx, dy, dz).getBlock();
						if(candidate.isPassable())continue;
						paint(candidate, team, "", 1.0);
						break;
					}
				}
			}
		}
	}
	public void equipKits() {
		for(Player p : getPlayers()){
			getPlayerInfo(p).setInkLevel(1);
			equipKit(p);
		}
	}
	/** Everybody carries the same kit from the first second; whoever arrives without one, a rejoin, gets it on the next heartbeat. */
	void equipKit(Player p) {
		InkWarsPlayerInfo info = getPlayerInfo(p);
		if (info.getKit() != null) return;
		info.setKit(new InkKit(p));
	}

	@Override
	protected void updateScoreBoard(Player ply) {
		super.updateScoreBoard(ply);
		if (JocIniciat && !JocFinalitzat){
			ArrayList<String> list = new ArrayList<>();
			ArrayList<Integer> values = new ArrayList<>();
			boolean blackout = getRemainingSeconds() <= BLACKOUT_SECONDS;
			for(Equip e : Equips){
				try {
					EquipInkWars eq = (EquipInkWars)e;
					list.add(blackout ? e.getAdjectiuColored() + ChatColor.GRAY + " ??" : e.getAdjectiuColored());
					values.add(blackout ? 0 : (int) Math.round(eq.getOwnedPercent()));
				} catch (Exception ignored) {

				}
			}
			ScoreBoardUpdater.setScoreBoard(ply, ChatColor.AQUA + "Ink" + ChatColor.DARK_AQUA + "Wars " + getTimer(), list, values);
		}

	}
	@Override
	protected boolean isRecallEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	protected String getTimer() {
		if(JocFinalitzat || !JocIniciat)return ChatColor.GOLD + "--:--";
		int secs = getRemainingSeconds();
		int min = Math.round(secs / 60);
		int dsecs = (int) (((secs / 60.0) - min) * 60);
		ChatColor col = ChatColor.DARK_GREEN;
		double t = getRemainingTimePercent();
		if(t <= 50){col = ChatColor.GREEN;}
		if(t <= 25){col = ChatColor.YELLOW;}
		if(t <= 12){col = ChatColor.RED;}
		if(t <= 5){col = ChatColor.DARK_RED;}
		return col + "" + min + ":" + dsecs;
	}
	public int getRemainingSeconds() {
		return getGameFinishSeconds() - segonsTranscorreguts();
	}
	public double getRemainingTimePercent(){
		return 100 * getRemainingSeconds() / getGameFinishSeconds();
	}
	int getGameFinishSeconds(){ //Configurable for multi-map
		int r = 4 * 60 + 40 + getPlayers().size() * 10;
		if(pMapaActual().ExisteixPropietat("GameFinishSeconds")){
			r = pMapaActual().ObtenirPropietatInt("GameFinishSeconds");
		}
		return r;
	}
	public void checkForWinner(){
		if(!blackoutAnnounced && getRemainingSeconds() <= BLACKOUT_SECONDS && getRemainingSeconds() > 0){
			blackoutAnnounced = true;
			for(Player p : getPlayers()){
				p.sendTitle(ChatColor.GOLD + "Last minute!", ChatColor.GRAY + "The count is hidden until the end", 5, 50, 15);
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 0.7F);
			}
		}
		for(Equip e : Equips){
			EquipInkWars eq = (EquipInkWars)e;
			if(segonsTranscorreguts() > 120 && (eq.getOwnedPercent() >= 80)){
				sendGlobalMessage(eq.getAdjectiuColored() + " team"  + ChatColor.GOLD + " got a clear ink color dominance around the map!");
				//winAction(eq);
				winGame(eq);
			}
		}
		if (getRemainingSeconds() <= 0) {
			EquipInkWars w = null;
			for (Equip e : Equips) {
				EquipInkWars eq = (EquipInkWars) e;
				if (w == null) w = eq;
				if (eq.getOwnedPercent() > w.getOwnedPercent()) {
					w = eq;
				}
			}
			if (w != null) {
				revealShares();
				winGame(w);
			}
		}
	}
	/** The end of the blackout: every share on screen, and the winner's fireworks. */
	void revealShares(){
		StringBuilder shares = new StringBuilder();
		for(Equip e : Equips){
			EquipInkWars eq = (EquipInkWars) e;
			if(shares.length() > 0)shares.append(ChatColor.GRAY + "  ");
			shares.append(eq.getAdjectiuColored()).append(ChatColor.WHITE + " ").append(Math.round(eq.getOwnedPercent())).append("%");
		}
		for(Player p : getPlayers()){
			p.sendTitle(ChatColor.AQUA + "Time!", shares.toString(), 5, 80, 20);
			p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 1F, 1F);
		}
	}
	public void winAction(EquipInkWars eq) {
		for(Player p : eq.getPlayers()){
			getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORK_SHOOT, 0);
		}
		sendGlobalMessage(eq.getChatColor() + "" + ChatColor.BOLD + "The " + eq.getAdjectiu().toLowerCase() + " team won!");
		JocFinalitzat();
	}
	public int getTotalPaintedBlocks(){
		int r = 0;
		for(Equip e : Equips){
			EquipInkWars eq = (EquipInkWars)e;
			r += eq.getOwnedBlocks();
		}
		return r;
	}
	@Override
	public void ultraHeartbeat() { //Every tick (20 ticks = 1s)
		super.ultraHeartbeat();
		if (JocIniciat) {
			tickWetInk();
			tickKits();
		}
	}
	@Override
	public void heartbeat() { //Every second
		super.heartbeat();
		if (JocIniciat) {
			controlLevels();
			updateScoreBoards();
			checkForWinner();
		}
	}
	public void controlLevels() {
		for(Player p : getPlayers()){
			EquipInkWars e = obtenirEquip(p);
			InkWarsPlayerInfo i = getPlayerInfo(p);
			equipKit(p);
			if(i.getAlivePaintedBlocks() > i.getInkLevel() * getBlockCountToLevelUp()){
				i.setInkLevel(i.getInkLevel() + 1);
				p.playSound(p.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1.3F);
				getWorld().spawnParticle(Particle.HAPPY_VILLAGER, p.getLocation().add(0, 1, 0), 20, 0.6, 0.8, 0.6, 0);
				sendPlayerMessage(p, ChatColor.AQUA + "Level Up! You are now level " + i.getInkLevel());
				sendTeamMessage(e, ChatColor.GRAY + "The player " + ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " is now level " + ChatColor.YELLOW + i.getInkLevel() + ChatColor.WHITE + "!");
			}
		}
	}
	int getBlockCountToLevelUp(){ //Configurable for multi-map
		int r = 250;
		if(pMapaActual().ExisteixPropietat("LevelUpBlocks")){
			r = pMapaActual().ObtenirPropietatInt("LevelUpBlocks");
		}
		return r;
	}
	public void tickKits(){
		for(Player p : getPlayers()){
			InkKit kit = getPlayerInfo(p).getKit();
			if (kit != null) kit.tick();
		}
	}
	/** Every tick the wet ink dries by one per second; every few ticks the ink above what its surface holds moves: along the floor, down the wall, off the edge. */
	protected void tickWetInk(){
		wetInkTicks++;
		boolean flowStep = wetInkTicks % TICKS_BETWEEN_FLOW_STEPS == 0;
		ArrayList<InkMove> moves = new ArrayList<>();
		Iterator<Entry<Block, WetInk>> iter = wetInk.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Block, WetInk> entry = iter.next();
			Block b = entry.getKey();
			WetInk wet = entry.getValue();
			wet.amount -= 1.0 / 20.0;
			if(wet.amount <= 0){
				iter.remove();
				EquipInkWars owner = getTeamOwningBlock(b);
				if (owner != null) b.setType(getPaintMaterial(b.getType(), owner.getColor()), false);
				continue;
			}
			if(!flowStep)continue;
			InkSurfaceFlow.Surface surface = InkSurfaceFlow.of(b, solid);
			double excess = wet.amount - surface.face().holds;
			if(excess <= 0 || surface.outlets().isEmpty())continue;
			InkSurfaceFlow.Outlet chosen = null;
			double bestScore = 0;
			for(InkSurfaceFlow.Outlet outlet : surface.outlets()){
				WetInk targetInk = wetInk.get(outlet.target());
				double score = outlet.weight() / (0.3 + (targetInk == null ? 0 : targetInk.amount)); // gravity first, then the driest neighbour
				if(score > bestScore){
					bestScore = score;
					chosen = outlet;
				}
			}
			if(chosen == null)continue;
			double moved = Math.min(excess, FLOW_STEP);
			wet.amount -= moved;
			moves.add(new InkMove(b, chosen.target(), getTeamOwningBlock(b), wet.painterName, moved));
		}
		for(InkMove move : moves){
			if(move.team == null)continue;
			paint(move.target, move.team, move.painterName, move.amount);
			if(move.target.getY() < move.source.getY() - 1){ // a drip off an edge
				Particle.DustOptions drop = new Particle.DustOptions(move.team.getStrongColor().getColor(), 1.2F);
				getWorld().spawnParticle(Particle.DUST, move.source.getLocation().add(0.5, -0.3, 0.5), 3, 0.2, 0.4, 0.2, 0, drop);
			}
		}
	}
	record InkMove(Block source, Block target, EquipInkWars team, String painterName, double amount) {}

	/**
	 * Lays ink on a block for a team. Same colour adds up, to the block's capacity. Wet enemy ink is subtracted first: the stroke sinks into it and is lost
	 * unless it outweighs what is left, and only then does the block flip. Returns whether the block ends up in the team's colour.
	 */
	boolean paint(Block b, EquipInkWars team, String painterName, double ink){
		if(isShape(b))b = b.getRelative(BlockFace.DOWN); // ink on a stair or slab lands on the block beneath
		boolean forcedly = isPaintableUnsafely(b);
		if(!isPaintable(b) && !forcedly)return false;
		EquipInkWars oldOwner = getTeamOwningBlock(b);
		WetInk wet = wetInk.get(b);
		if(oldOwner != null && oldOwner != team && wet != null){
			if(wet.amount >= ink){
				wet.amount -= ink;
				return false;
			}
			ink -= wet.amount;
		}
		if(oldOwner != team)registerBlockPaint(painterName, team, oldOwner);
		Material painted = getPaintMaterial(b.getType(), team.getStrongColor());
		if(b.getType() != painted)b.setType(painted, false);
		double carried = (wet != null && oldOwner == team) ? wet.amount : 0;
		wetInk.put(b, new WetInk(painterName, Math.min(MAX_INK_PER_BLOCK, carried + ink)));
		return true;
	}
	/** Brings a block already in the team's colour back to fully wet without adding ink: the body swimming through it. */
	void rewet(Block b, EquipInkWars team, String painterName){
		if(getTeamOwningBlock(b) != team)return;
		WetInk wet = wetInk.get(b);
		if(wet != null && wet.amount >= 1)return;
		Material painted = getPaintMaterial(b.getType(), team.getStrongColor());
		if(b.getType() != painted)b.setType(painted, false);
		wetInk.put(b, new WetInk(painterName, 1));
	}
	/** The score of a block changing hands goes to the painter if they are online and still on that team, else to the team alone. */
	void registerBlockPaint(String painterName, EquipInkWars team, EquipInkWars oldOwner){
		Player painter = Bukkit.getPlayer(painterName);
		if(painter != null && obtenirEquip(painter) == team){
			getPlayerInfo(painter).registerBlockPaint(oldOwner);
			return;
		}
		if(oldOwner != null)oldOwner.incrementOwnedBlocks(-1);
		team.incrementOwnedBlocks(1);
	}
	public EquipInkWars getTeamOwningBlock(Block b){
		if(b == null)return null;
		if(isShape(b))return getTeamOwningBlock(b.getRelative(BlockFace.DOWN)); // a stair or slab wears the colour beneath it
		if(!isPaintable(b))return null;
		DyeColor blockColor = getPaintColor(b.getType());
		for(Equip e : Equips){
			EquipInkWars eq = (EquipInkWars) e;
			if(eq.getColor() == blockColor || eq.getStrongColor() == blockColor)return eq;
		}
		return null;
	}
	/** The dyeable families, the ones with sixteen colours; the longer suffix first so glazed terracotta is not mistaken for terracotta. */
	static final String[] DYED_SUFFIXES = {"_GLAZED_TERRACOTTA", "_STAINED_GLASS_PANE", "_STAINED_GLASS", "_CONCRETE_POWDER", "_CONCRETE", "_TERRACOTTA", "_WOOL", "_CARPET"};
	/** Shapes with no dyed twin in vanilla: never painted or scored themselves, they carry the colour of the block beneath them. */
	static final String[] SHAPE_SUFFIXES = {"_STAIRS", "_SLAB", "_FENCE", "_FENCE_GATE", "_WALL", "_TRAPDOOR", "_LEAVES"};
	public boolean isPaintable(Block b){
		return getPaintColor(b.getType()) != null;
	}
	private DyeColor getPaintColor(Material material){
		String materialName = material.name();
		for (DyeColor color : DyeColor.values()) {
			for (String suffix : DYED_SUFFIXES) {
				if (materialName.equals(color.name() + suffix)) return color;
			}
		}
		return null;
	}
	/** The coloured block a block becomes: its own family in the colour; plain glass, panes and iron bars their stained twins; anything else coloured terracotta. */
	private Material getPaintMaterial(Material currentMaterial, DyeColor color){
		String materialName = currentMaterial.name();
		for (String suffix : DYED_SUFFIXES) {
			if (materialName.endsWith(suffix)) return Material.valueOf(color.name() + suffix);
		}
		if (currentMaterial == Material.GLASS) return Material.valueOf(color.name() + "_STAINED_GLASS");
		if (currentMaterial == Material.GLASS_PANE || currentMaterial == Material.IRON_BARS) return Material.valueOf(color.name() + "_STAINED_GLASS_PANE");
		return Material.valueOf(color.name() + "_TERRACOTTA");
	}
	/** Blocks with no colour yet that take one: plain glass, panes and bars keep their shape, plain terracotta its own family, and any full occluding block turns into terracotta. */
	public boolean isPaintableUnsafely(Block b){
		Material t = b.getType();
		if (t == Material.BARRIER || isPaintable(b)) return false;
		if (t == Material.GLASS || t == Material.GLASS_PANE || t == Material.IRON_BARS || t == Material.TERRACOTTA) return true;
		return t.isBlock() && t.isOccluding();
	}
	public boolean isShape(Block b){
		String name = b.getType().name();
		for (String suffix : SHAPE_SUFFIXES) if (name.endsWith(suffix)) return true;
		return false;
	}
	/**
	 * The one kit everybody carries. The held item decides what paints: the Roller stick paints the floor a step ahead of a walking player,
	 * the Hose torch throws a jet of ink that flies in an arc while right-click is held, ink balls splash where they land and hurt around the impact.
	 * Melee does nothing here; ink kills.
	 */
	class InkKit extends PlayerWorldEventBus{
		static final int ROLLER_SLOT = 0;
		static final int HOSE_SLOT = 1;
		static final int BALL_SLOT = 2;
		private int reloadTicks = 0;
		private final InkStream hose = new InkStream();
		/** How far the tip is squeezed, 0 open to 1 pinched, blending toward where the trigger says; the trigger holds for a few ticks past each press of right-click. */
		private double pinch = 0;
		private int pinchTriggerTicks = 0;
		/** Parcels owed but not yet thrown: the per-tick count blends between the modes, so fractions carry over. */
		private double parcelCarry = 0;
		private int hoseSoundTicks = 0;
		private int landingsSinceSplat = 0;
		/** True while this kit is dealing ink damage through the direct damage call, which the melee hook would otherwise cancel. */
		private boolean dealingInkDamage = false;
		private boolean valid = true;
		private final ArrayList<Projectile> inkBalls = new ArrayList<>();

		public InkKit(Player ply) {
			super(ply);
		}
		int level() {
			return getPlayerInfo(getPlayer()).getInkLevel();
		}
		@Override
		public boolean isValid() {
			return valid;
		}
		public void destroy() {
			valid = false;
		}
		@Override
		protected boolean getPlayerSpecificEventFiltering() {
			return false; // a projectile hit carries its shooter too deep for the reflection filter; every hook checks the player itself
		}
		boolean isSubmerged(){
			return getPlayerInfo(getPlayer()).isSubmerged();
		}
		ChatColor teamColour(){
			return obtenirEquip(getPlayer()).getChatColor();
		}
		ItemStack rollerItem(){
			return Utils.setItemNameAndLore(new ItemStack(Material.STICK, 1), teamColour() + "Roller", ChatColor.WHITE + "Hold it and walk: paints the floor under you.");
		}
		ItemStack hoseItem(){
			return Utils.setItemNameAndLore(new ItemStack(Material.TORCH, 1), teamColour() + "Hose", ChatColor.WHITE + "In hand it runs: a jet of ink that flies in an arc and lands where it lands.", ChatColor.WHITE + "Hold right-click: pinch the tip for a longer, thinner shot.");
		}
		ItemStack inkBallItem(){
			return Utils.setItemName(new ItemStack(Material.SNOWBALL, 1), teamColour() + "Ink ball");
		}
		/** The tools into fixed hotbar slots, the Roller in hand. */
		public void give(){
			Player p = getPlayer();
			p.getInventory().setItem(ROLLER_SLOT, rollerItem());
			p.getInventory().setItem(HOSE_SLOT, hoseItem());
			p.getInventory().setHeldItemSlot(ROLLER_SLOT);
			p.playSound(p.getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 1F, 1F);
			p.playSound(p.getEyeLocation(), Sound.BLOCK_PISTON_EXTEND, 1F, 1F);
		}
		public void tick(){
			tickHose();
			if(isSubmerged())return; // a squid has no tools in hand, only its ink; a jet already in the air still comes down
			if(pinchTriggerTicks > 0)pinchTriggerTicks--;
			double pinchWanted = pinchTriggerTicks > 0 ? 1 : 0;
			pinch += Math.max(-1.0 / PINCH_BLEND_TICKS, Math.min(1.0 / PINCH_BLEND_TICKS, pinchWanted - pinch));
			if(getPlayer().getInventory().getItemInMainHand().getType() == Material.TORCH)sprayHose();
			reloadTick();
			if(wetInkTicks % 20 == 0)restoreTools();
		}
		/** The hose is the torch in the main hand and its jet leaves the hand: well to the right of the eyes, a little forward, at chest height; it throws along the look. */
		void sprayHose(){
			Player p = getPlayer();
			Location eyes = p.getEyeLocation();
			Vector look = eyes.getDirection();
			Vector right = new Vector(-look.getZ(), 0, look.getX());
			if(right.lengthSquared() > 1e-6)right.normalize();
			Location nozzle = eyes.clone().add(look.clone().multiply(0.3)).add(right.multiply(0.5)).add(0, -0.5, 0);
			double levelBonus = level() * 0.02;
			InkStream.Load load = new InkStream.Load(blend(HOSE_PARCEL_INK, PINCHED_PARCEL_INK) + levelBonus, blend(HOSE_SPLASH_RADIUS + level() * 0.05, PINCHED_SPLASH_RADIUS + level() * 0.03), blend(HOSE_STING, PINCHED_STING), blend(HOSE_GRAVITY, PINCHED_GRAVITY));
			parcelCarry += blend(HOSE_PARCELS_PER_TICK, PINCHED_PARCELS_PER_TICK);
			int count = (int) parcelCarry;
			parcelCarry -= count;
			hose.emit(nozzle, look, blend(HOSE_SPEED, PINCHED_SPEED) + levelBonus, blend(HOSE_SCATTER, PINCHED_SCATTER), load, count);
			hoseSoundTicks++; // the open jet bloops, the pinched jet hisses, crossfading with the squeeze
			if(hoseSoundTicks % 3 == 0 && pinch < 0.99)getWorld().playSound(nozzle, Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, (float) (0.22 * (1 - pinch)), (float) (1.4 + Math.random() * 0.4));
			if(hoseSoundTicks % 5 == 0 && pinch > 0.01)getWorld().playSound(nozzle, Sound.BLOCK_LAVA_EXTINGUISH, (float) (0.12 * pinch), 1.9F);
		}
		/** A hose figure between its open and its pinched value, by how far the tip is squeezed. */
		double blend(double open, double pinched){
			return open + (pinched - open) * pinch;
		}
		/** Every parcel in the air flies one tick; the ones that came down splash where they landed, and one that met an enemy stings them. */
		void tickHose(){
			if(hose.isEmpty())return;
			Player shooter = getPlayer();
			ItemStack drop = new ItemStack(Material.valueOf(obtenirEquip(shooter).getStrongColor().name() + "_CONCRETE")); // crumbs of the team's colour that fly with the jet
			for(InkStream.Landing landing : hose.advance(getWorld(), body -> body instanceof Player hit && hit != shooter && areEnemies(hit, shooter))){
				InkStream.Load load = landing.load();
				if(landing.body() instanceof Player hit){
					if(load.sting() > 0)hurt(hit, load.sting());
					splashDown(hit.getLocation(), load.splashRadius(), load.ink());
					getWorld().playSound(hit.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 0.4F, 1.4F);
					continue;
				}
				Location impact = landing.where().toLocation(getWorld()).add(landing.surfaceNormal().clone().multiply(0.3));
				splash(impact, landing.velocity(), landing.surfaceNormal(), load.splashRadius(), load.ink());
				if(++landingsSinceSplat >= 6){ // the impact zone crackles, not every parcel
					landingsSinceSplat = 0;
					getWorld().playSound(impact, Sound.ENTITY_SLIME_SQUISH_SMALL, 0.25F, (float) (1.1 + Math.random() * 0.4));
				}
			}
			int parity = wetInkTicks % 2;
			int index = 0;
			for(InkStream.Flight flight : hose.flights()){
				if(index++ % 2 != parity)continue;
				Vector at = flight.position();
				Vector along = flight.velocity();
				getWorld().spawnParticle(Particle.ITEM, at.getX(), at.getY(), at.getZ(), 0, along.getX(), along.getY(), along.getZ(), 1.0, drop);
			}
		}
		/** Ink damage from this kit: the ball's splash, the hose's jet, the squid's surge. Direct damage reads as melee to the hook below, so it is flagged past it. */
		void hurt(Player enemy, double amount){
			dealingInkDamage = true;
			try {
				enemy.damage(amount, getPlayer());
			} finally {
				dealingInkDamage = false;
			}
		}
		/** A dropped or lost tool comes back to its slot: the kit is the player, not loot. */
		void restoreTools(){
			Player p = getPlayer();
			if(!p.getInventory().contains(Material.STICK))p.getInventory().setItem(ROLLER_SLOT, rollerItem());
			if(!p.getInventory().contains(Material.TORCH))p.getInventory().setItem(HOSE_SLOT, hoseItem());
		}
		/** The squid's hand: the tools go away and a stack of ink sacs shows how much ink it carries. */
		void showInkSacs(double reserve){
			Player p = getPlayer();
			p.getInventory().setItem(ROLLER_SLOT, null);
			p.getInventory().setItem(HOSE_SLOT, null);
			p.getInventory().remove(Material.SNOWBALL);
			int sacs = Math.max(1, (int) Math.ceil(reserve * INK_SACS_FOR_FULL_RESERVE));
			p.getInventory().setItem(ROLLER_SLOT, Utils.setItemNameAndLore(new ItemStack(Material.INK_SAC, sacs), teamColour() + "Ink", ChatColor.WHITE + "Your ink: gathered on your colour, spent elsewhere.", ChatColor.WHITE + "Right-click: burn ten sacs for a thrust forward, past the top speed."));
			p.getInventory().setHeldItemSlot(ROLLER_SLOT);
		}
		void updateInkSacs(double reserve){
			Player p = getPlayer();
			int sacs = Math.max(1, (int) Math.ceil(reserve * INK_SACS_FOR_FULL_RESERVE));
			ItemStack held = p.getInventory().getItem(ROLLER_SLOT);
			if(held == null || held.getType() != Material.INK_SAC){
				showInkSacs(reserve);
				return;
			}
			if(held.getAmount() != sacs){
				held.setAmount(sacs);
				p.getInventory().setItem(ROLLER_SLOT, held);
			}
		}
		/** Back on the feet: the ink goes, the tools return. */
		void hideInkSacs(){
			Player p = getPlayer();
			p.getInventory().remove(Material.INK_SAC);
			restoreTools();
			p.getInventory().setHeldItemSlot(ROLLER_SLOT);
		}
		public int maxInkBalls(){
			return 6 + Math.round(level() / 2f);
		}
		public int neededReloadTicks(){
			return 50 - level() * 2;
		}
		/** Reloading runs five times faster on the team's colour or in the base. */
		public int reloadTickIncrement(){
			InkWarsPlayerInfo info = getPlayerInfo(getPlayer());
			EquipInkWars e = obtenirEquip(getPlayer());
			boolean onOwnColour = info.getTeamColorWherePlayerStands() == e;
			boolean inBase = getPlayer().getLocation().distance(e.getTeamSpawnLocation()) < 10;
			return onOwnColour || inBase ? 5 : 1;
		}
		/** Ink balls reload only while their slot is the one in hand, into that slot. */
		public void reloadTick(){
			Player p = getPlayer();
			if(p.getInventory().getHeldItemSlot() != BALL_SLOT)return;
			ItemStack balls = p.getInventory().getItem(BALL_SLOT);
			if(balls != null && balls.getType() != Material.SNOWBALL)return; // something else sits in the slot
			int have = balls == null ? 0 : balls.getAmount();
			if(have >= maxInkBalls())return;
			if(reloadTicks >= neededReloadTicks()){
				ItemStack refilled = inkBallItem();
				refilled.setAmount(have + 1);
				p.getInventory().setItem(BALL_SLOT, refilled);
				p.playSound(p.getEyeLocation(), Sound.ENTITY_ITEM_PICKUP, 0.4F, 1F);
				reloadTicks = 0;
			}else{
				reloadTicks += reloadTickIncrement();
			}
		}
		/** A stroke needs the feet to move: turning the head in place lays no ink, so nobody pumps a puddle by wiggling the mouse. */
		boolean movedFeet(PlayerMoveEvent evt){
			return evt.getTo() != null && evt.getFrom().distanceSquared(evt.getTo()) > 1e-4;
		}
		@Override
		protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
			super.onPlayerMove(evt, p);
			if(p != getPlayer() || isSubmerged() || !movedFeet(evt))return;
			if(p.getInventory().getItemInMainHand().getType() != Material.STICK)return;
			rollerLinePaint(1 + Math.sqrt(level()), 0.4 + level() / 24.0, p, ROLLER_AHEAD);
			if(wetInkTicks % 5 == 0)getWorld().playSound(p.getLocation(), Sound.BLOCK_SLIME_BLOCK_STEP, 0.35F, 0.9F);
		}
		@Override
		protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
			super.onPlayerInteract(evt, p);
			if(p != getPlayer() || evt.getHand() != EquipmentSlot.HAND)return;
			Material inHand = p.getInventory().getItemInMainHand().getType();
			boolean rightClick = evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() == Action.RIGHT_CLICK_AIR;
			if(rightClick && inHand == Material.INK_SAC && isSubmerged()){
				evt.setCancelled(true);
				getPlayerInfo(p).thrust();
				return;
			}
			if(inHand != Material.TORCH || !rightClick)return;
			evt.setCancelled(true); // the torch is the hose, it is never placed
			if(isSubmerged())return;
			if(pinchTriggerTicks == 0)p.playSound(p.getEyeLocation(), Sound.ENTITY_SLIME_SQUISH_SMALL, 0.5F, 1.5F); // the squeeze starting
			pinchTriggerTicks = PINCH_TRIGGER_TICKS;
		}
		/** A squid takes no damage: the ink is what it pays with. */
		@Override
		protected void onPlayerDamage(org.bukkit.event.entity.EntityDamageEvent evt, Player damaged) {
			super.onPlayerDamage(evt, damaged);
			if(damaged == getPlayer() && isSubmerged())evt.setCancelled(true);
		}
		/** A sneak would throw the squid off its carrier: while the engine still owns the body the client's dismount is refused; the engine's own eject is not. */
		@Override
		protected void onEntityDismount(EntityDismountEvent evt, Entity rider, Entity dismounted) {
			super.onEntityDismount(evt, rider, dismounted);
			if(rider != getPlayer())return;
			InkWarsPlayerInfo info = getPlayerInfo(getPlayer());
			if(info.swimForm && info.squid.submerged && dismounted == info.squid.carrier && evt.isCancellable())evt.setCancelled(true);
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(damager == getPlayer() && !ranged && !dealingInkDamage)evt.setCancelled(true);
		}
		@Override
		protected void onProjectileLaunch(ProjectileLaunchEvent evt, Projectile proj) {
			super.onProjectileLaunch(evt, proj);
			ProjectileSource shooter = proj.getShooter();
			if(!(shooter instanceof Player) || shooter != getPlayer())return;
			if(isSubmerged()){ // Surface first: nothing is thrown from under the ink
				evt.setCancelled(true);
				getPlayer().playSound(getPlayer().getEyeLocation(), Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 0.6F, 0.8F);
				return;
			}
			inkBalls.add(proj);
		}
		@Override
		protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
			super.onProjectileHit(evt, proj);
			if(!inkBalls.remove(proj) || !(proj instanceof Snowball))return;
			Block hitBlock = evt.getHitBlock();
			Vector surfaceNormal = new Vector(0, 1, 0);
			if (hitBlock != null && evt.getHitBlockFace() != null) {
				surfaceNormal = evt.getHitBlockFace().getDirection();
			} else if (evt.getHitEntity() != null) {
				hitBlock = evt.getHitEntity().getLocation().getBlock();
			} else {
				hitBlock = proj.getLocation().getBlock();
			}
			Vector incoming = proj.getVelocity();
			if (incoming.lengthSquared() < 1e-4) incoming = proj.getLocation().toVector().subtract(getPlayer().getEyeLocation().toVector());
			Location impact = proj.getLocation().add(surfaceNormal.clone().multiply(0.3)); // a little off the surface, on the open side
			getWorld().playSound(hitBlock.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1, 1.1F);
			getWorld().playSound(hitBlock.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1.1F);
			splash(impact, incoming, surfaceNormal, 1.9 + Math.sqrt(level() * 0.75), 1.3 + level() / 6.0);
			for(Player p : Utils.getNearbyPlayers(impact, 1 + level())){
				if(areEnemies(p, getPlayer())){
					double targetDistance = Math.max(p.getLocation().distance(impact), 0.25);
					double shotDistance = Math.max(impact.distance(getPlayer().getEyeLocation()), 0.25);
					double splashDamage = 2 + (6.5 + (level() / 2.2)) / (targetDistance * (shotDistance / 3));
					hurt(p, splashDamage);
				}
			}
		}
		//Painting methods
		/**
		 * A stroke across the player's path: a line on the floor perpendicular to where they face, centred the given distance ahead of the feet.
		 * Each sample along the line paints the floor under it, so a step up or down along the stroke gets its share and a riser ahead takes the roller's head.
		 * Looking straight up or down has no across, so the stroke is the block underfoot.
		 */
		public void rollerLinePaint(double halfWidth, double ink, Player p, double ahead){
			for(Block floor : rollerStrokeBlocks(halfWidth, p.getLocation(), p.getLocation().getDirection().setY(0), ahead))paintBlock(floor, ink);
		}
		/** The floor blocks a stroke covers, in order across a forward direction from a point on the floor, each once. */
		public ArrayList<Block> rollerStrokeBlocks(double halfWidth, Location feet, Vector forward, double ahead){
			ArrayList<Block> stroke = new ArrayList<>();
			forward = forward.clone().setY(0);
			if(forward.lengthSquared() < 1e-6){
				Block underfoot = floorUnder(feet);
				if(underfoot != null)stroke.add(underfoot);
				return stroke;
			}
			forward.normalize();
			Vector across = new Vector(0, 1, 0).crossProduct(forward).normalize();
			Location centre = feet.clone().add(forward.multiply(ahead));
			for(double offset = -halfWidth; offset <= halfWidth + 1e-9; offset += 0.5){
				Block floor = floorUnder(centre.clone().add(across.clone().multiply(offset)));
				if(floor != null && !stroke.contains(floor))stroke.add(floor);
			}
			return stroke;
		}
		/** The floor a roller's head rests on at a point: the first solid block from the point down two, or the block itself when it is solid, a riser. */
		Block floorUnder(Location point){
			Block block = point.getBlock();
			for(int depth = 0; depth <= 2; depth++){
				if(!block.isPassable())return block;
				block = block.getRelative(BlockFace.DOWN);
			}
			return null;
		}
		public void paintBlock(Block b, double inkAmount){
			paint(b, obtenirEquip(getPlayer()), getPlayer().getName(), inkAmount);
		}
		/** A splash cast as a fan of rays from the impact: ink lands on the surfaces the rays reach, never through a wall. */
		protected void splash(Location impact, Vector incoming, Vector surfaceNormal, double radius, double ink){
			for(InkSplash.Deposit deposit : InkSplash.cast(impact, incoming, surfaceNormal, InkSplash.Shape.of(radius, ink), solid)){
				paintBlock(deposit.block(), deposit.amount());
			}
		}
		/** Ink falling straight down around a point on the ground: a body, a surfacing swimmer. */
		protected void splashDown(Location feet, double radius, double ink){
			splash(feet.clone().add(0, 0.4, 0), new Vector(0, -1, 0), new Vector(0, 1, 0), radius, ink);
		}
	}
	@Override
	public InkWarsPlayerInfo getPlayerInfo(Player p) {
		return getPlayerInfo(p, InkWarsPlayerInfo.class);		
	}
	public class InkWarsPlayerInfo extends PlayerInfo{
		private int paintedBlockCount = 0;
		private int alivePaintedBlocks = 0;
		private InkKit kit = null;
		private int inkLevel = 1;
		private int dmgTicks = 0;
		private boolean swimForm = false;
		private final Squid squid = new Squid();
		private Location lastLocation = null;

		public InkWarsPlayerInfo() {
			super();
		}
		public int getPaintedBlockCount() {
			return paintedBlockCount;
		}
		public int getAlivePaintedBlocks() {
			return alivePaintedBlocks;
		}
		@Override
		public void ultraTick() {
			super.ultraTick();
			Player p = getPlayer();
			Location now = p.getLocation();
			Vector moved = lastLocation == null || lastLocation.getWorld() != now.getWorld() ? new Vector() : now.toVector().subtract(lastLocation.toVector());
			lastLocation = now;
			EquipInkWars team = obtenirEquip(p);
			EquipInkWars colourUnderfoot = getTeamColorWherePlayerStands();
			boolean onOwnColour = colourUnderfoot == team;
			boolean onEnemyColour = colourUnderfoot != null && !onOwnColour;

			if(now.getY() < getWorld().getMinHeight() + 4){ // off the map: back to base, on the feet
				swimForm = false;
				squid.reset();
				team.teleportToTeamSpawn(p);
				return;
			}
			boolean wantsToSwim = swimForm && kit != null;
			if(wantsToSwim && !squid.submerged)squid.dive(moved);
			if(!wantsToSwim && squid.submerged)squid.surface();
			if(squid.submerged)squid.tick(team);
			else squid.tickPendingSurge();

			applyInkSpeed(onOwnColour, onEnemyColour);
			tickEnemyInkDamage(onEnemyColour);
		}
		public boolean isSubmerged(){
			return squid.submerged;
		}
		/** The ink sacs in hand: ten of them burn for a thrust forward, past the top speed; with fewer than ten, only a dry pop. */
		public void thrust(){
			if(!squid.submerged)return;
			squid.thrust();
		}
		/** One press of sneak switches form: in squid form the body dives wherever it stands and stays a squid as long as its ink lasts; a second press stands it up. */
		public void toggleSwimForm(){
			Player p = getPlayer();
			if(!swimForm && !squid.submerged && getTeamColorWherePlayerStands() != obtenirEquip(p)){ // no ink of yours here to dive into
				squid.sound(Squid.SwimSound.EMPTY, p.getLocation(), 0);
				PaperMessages.sendActionBar(p, ChatColor.RED + "Dive on your own ink", 30);
				return;
			}
			swimForm = !swimForm;
			if(swimForm)squid.blockedSurfaceTicks = 0;
			if(swimForm){
				PaperMessages.sendActionBar(p, obtenirEquip(p).getChatColor() + "Squid form", 30);
			}else{
				PaperMessages.sendActionBar(p, ChatColor.GRAY + "On your feet", 30);
			}
		}
		/** Own colour is fast, enemy colour is mud. Under the ink the squid carries itself, no potion. Refreshed every tick so they vanish the moment the ground changes. */
		private void applyInkSpeed(boolean onOwnColour, boolean onEnemyColour){
			Player p = getPlayer();
			int speedLevels = onOwnColour && !squid.submerged ? 1 : 0;
			int slownessLevels = onEnemyColour && !squid.submerged ? 2 : 0;
			if(speedLevels > 0)p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, speedLevels - 1, true, false));
			else p.removePotionEffect(PotionEffectType.SPEED);
			if(slownessLevels > 0)p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 5, slownessLevels - 1, true, false));
			else p.removePotionEffect(PotionEffectType.SLOWNESS);
		}
		/** Enemy ink stings, one heart a second; it does not execute. A squid pays in reserve instead. */
		private void tickEnemyInkDamage(boolean onEnemyColour){
			if(!onEnemyColour || squid.submerged){
				dmgTicks = 0;
				return;
			}
			dmgTicks++;
			if(dmgTicks >= 20){
				getPlayer().damage(1);
				dmgTicks = 0;
			}
		}
		public void registerBlockPaint(EquipInkWars oldOwner){
			paintedBlockCount++;
			alivePaintedBlocks++;
			getPlayer().setLevel(alivePaintedBlocks);
			EquipInkWars e = obtenirEquip(getPlayer());
			if (oldOwner != null) oldOwner.incrementOwnedBlocks(-1);
			e.incrementOwnedBlocks(1);
		}
		public void registerDeath(){
			swimForm = false;
			squid.reset();
			alivePaintedBlocks *= 0.8; //Reduce player points by 20%
			getPlayer().setLevel(alivePaintedBlocks);
			sendPlayerMessage(getPlayer(), ChatColor.RED + "You have lost 20% of your points");
		}
		public InkKit getKit() {
			return kit;
		}
		public void setKit(InkKit newKit) {
			if (kit != null) kit.destroy();
			swimForm = false;
			squid.reset();
			donarItemsInicials(getPlayer()); //Clears and gives starting (colored) armor
			kit = newKit;
			kit.give();
		}
		public int getInkLevel() {
			return inkLevel;
		}
		public void setInkLevel(int inkLevel) {
			this.inkLevel = inkLevel;
		}
		public EquipInkWars getTeamColorWherePlayerStands(){
			return getTeamOwningBlock(getBlockWherePlayerStands());
		}

		/**
		 * The squid: a body that treats floor, walls, ceilings and corners as one continuous surface and never feels gravity while it touches one.
		 * The client runs no physics for it at all: the player rides an invisible carrier that the engine places every tick, so the body is a point
		 * the camera sits on, kept a small radius off every surface, and every collision, bend, hop and fall is the engine's own arithmetic against the block grid.
		 * It lives on an ink reserve, refilled on its own colour and drained elsewhere; while the reserve lasts it stays a squid and lays a strip that thins with the reserve.
		 */
		class Squid {
			boolean submerged = false;
			int submergedTicks = 0;
			/** Where the body is attached: a floor, a wall on one side, a ceiling overhead, or nothing (in the air). */
			Surface surface = Surface.AIR;
			BlockFace wallSide = null;
			int cornerTicks = 0;
			/** The body's centre, the point the camera sits on. */
			Vector centre = new Vector();
			/** Unit vector in the plane of the surface, and the speed along it, blocks per tick. */
			Vector heading = new Vector(1, 0, 0);
			double speed = 0;
			/** The engine's own vertical speed in the air, blocks per tick. */
			double verticalSpeed = 0;
			/** The last tick's displacement: what a forced exit carries as momentum. */
			Vector pushed = new Vector();
			/** The block the body rides on: the floor under it, the wall beside it, the ceiling over it. */
			Block gripped = null;
			Contact gripContact = null;
			int missingContactTicks = 0;
			int detachTicks = 0;
			BlockFace detachedFace = null;
			double movementFraction = 1;
			boolean firstMovementStep = true;
			Vector controlForward = null;
			Vector controlRight = null;
			Vector cameraCentre = null;
			float carrierYaw = 0;
			Location lastClearStandingSpot = null;
			int blockedSurfaceTicks = 0;
			int lastSurfaceAttemptTick = -1;
			/** What the ground was before this dive's own strip painted it, by block: the reserve judges the ground by this, so the squid cannot live on the ink it lays. */
			final HashMap<Block, EquipInkWars> groundUnderStrip = new HashMap<>();
			/** The ink: gathered swimming on the team's colour, spent swimming elsewhere, and what the surge throws. Zero on every dive. */
			double reserve = 0;
			double pendingSurge = -1;
			Vector flight = new Vector();
			boolean jumpHeld = false;
			int jumpBufferedTicks = 0;
			int floorGraceTicks = 0;
			int turboTrailTicks = 0;
			int trailParcelsThisTick = 0;
			double trailDistanceSinceParcel = 0;
			boolean thrustPending = false;
			int turboJetTick = SquidMotion.JET_DURATION_TICKS;
			double turboJetPitch = 0;
			double turboJetStrength = 0;
			float lastControlYaw = Float.NaN;
			enum SwimSound { DIVE, SURFACE, CONTACT, JUMP, LAND, TURBO, PUSH, TAIL, READY, EMPTY, RIPPLE }
			final EnumMap<SwimSound, Long> lastSoundNanos = new EnumMap<>(SwimSound.class);

			boolean allowSound(SwimSound cue, long now){
				SwimSound group = cue == SwimSound.SURFACE ? SwimSound.DIVE : cue;
				long cooldown = switch(group){
					case DIVE -> 180_000_000L;
					case CONTACT, LAND, JUMP -> 150_000_000L;
					case READY -> 1_000_000_000L;
					case EMPTY, RIPPLE -> 600_000_000L;
					default -> 120_000_000L;
				};
				Long previous = lastSoundNanos.get(group);
				if(previous != null && now - previous < cooldown)return false;
				lastSoundNanos.put(group, now);
				return true;
			}
			void sound(SwimSound cue, Location at, double impact){
				if(!allowSound(cue, System.nanoTime()))return;
				float variation = (float) (Math.random() * 0.12 - 0.06);
				switch(cue){
					case DIVE -> {
						getWorld().playSound(at, Sound.ENTITY_PLAYER_SWIM, 0.28F, 1.3F + variation);
						getWorld().playSound(at, Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 0.12F, 1.5F + variation);
					}
					case SURFACE -> getWorld().playSound(at, Sound.ENTITY_GENERIC_SPLASH, 0.28F, 1.55F + variation);
					case CONTACT -> getWorld().playSound(at, Sound.BLOCK_SLIME_BLOCK_STEP, 0.16F, 1.5F + variation);
					case JUMP -> getWorld().playSound(at, Sound.ENTITY_PLAYER_SWIM, 0.24F, 1.65F + variation);
					case LAND -> {
						double weight = Math.min(1, Math.max(0, impact) / 0.8);
						getWorld().playSound(at, Sound.ENTITY_GENERIC_SPLASH, (float) (0.12 + 0.38 * weight), (float) (1.65 - 0.45 * weight) + variation);
					}
					case TURBO -> {
						getWorld().playSound(at, Sound.ENTITY_SQUID_SQUIRT, 0.55F, 1.35F + variation);
						getWorld().playSound(at, Sound.ENTITY_BREEZE_SHOOT, 0.38F, 1.5F + variation);
					}
					case PUSH -> getWorld().playSound(at, Sound.ENTITY_PLAYER_SWIM, 0.16F, 1.1F + (float) (0.35 * impact) + variation);
					case TAIL -> getWorld().playSound(at, Sound.ENTITY_PLAYER_SWIM, 0.22F, 1.15F + variation);
					case READY -> player().playSound(at, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.22F, 1.8F);
					case EMPTY -> player().playSound(at, Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 0.22F, 1.05F);
					case RIPPLE -> getWorld().playSound(at, Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 0.10F, 1.3F + variation);
				}
			}
			/** The carrier the player rides while a squid: an empty item display, nothing to see, no physics, placed by the engine every tick. */
			ItemDisplay carrier = null;
			/** Where the player sits relative to the carrier, measured once riding; the carrier is placed so the camera lands on the centre. */
			Vector seatOffset = new Vector(0, -0.6, 0);

			Player player(){
				return getPlayer();
			}
			EquipInkWars team(){
				return obtenirEquip(player());
			}
			/** Diving: the body goes under the ink, the armour and the tools with it, only a ripple and the ink in hand stay. The walking speed is carried into the swim; the ink starts at zero. */
			void dive(Vector moved){
				Player p = player();
				submerged = true;
				submergedTicks = 0;
				cornerTicks = 0;
				missingContactTicks = 0;
				detachTicks = 0;
				controlForward = null;
				controlRight = null;
				blockedSurfaceTicks = 0;
				lastSurfaceAttemptTick = -1;
				reserve = 0;
				groundUnderStrip.clear();
				jumpHeld = true; // a jump held through the dive is not a hop
				jumpBufferedTicks = 0;
				floorGraceTicks = 0;
				turboTrailTicks = 0;
				thrustPending = false;
				lastControlYaw = Float.NaN;
				turboJetTick = SquidMotion.JET_DURATION_TICKS;
				turboJetStrength = 0;
				Location feet = p.getLocation();
				centre = feet.toVector().add(new Vector(0, SQUID_RIDE_HEIGHT, 0));
				cameraCentre = p.getEyeLocation().toVector();
				lastClearStandingSpot = feet.clone();
				surface = Surface.FLOOR;
				wallSide = null;
				gripped = getBlockWherePlayerStands();
				gripContact = probe(new Vector(0, -1, 0), SQUID_RIDE_HEIGHT);
				verticalSpeed = 0;
				setMomentum(new Vector(moved.getX(), 0, moved.getZ()));
				if(gripContact == null || gripContact.face() != BlockFace.UP || Math.abs(centre.getY() - gripContact.point().getY() - SQUID_RIDE_HEIGHT) > 0.15 || moved.getY() > 0.05){
					surface = Surface.AIR;
					verticalSpeed = Math.abs(moved.getY()) < 3 ? moved.getY() : p.getVelocity().getY();
					gripped = null;
					gripContact = null;
				}
				pushed = new Vector();
				mount();
				p.getInventory().setArmorContents(null);
				kit.showInkSacs(reserve);
				sound(SwimSound.DIVE, feet, 0);
				getWorld().spawnParticle(Particle.SPLASH, feet.clone().add(0, 0.2, 0), 25, 0.6, 0.1, 0.6, 0);
			}
			void mount(){
				Player p = player();
				carrierYaw = p.getLocation().getYaw();
				carrier = getWorld().spawn(seatLocation(), ItemDisplay.class, display -> {
					display.setPersistent(false);
					display.setInvulnerable(true);
					display.setGravity(false);
					display.setTeleportDuration(1);
				});
				carrier.addPassenger(p);
			}
			/** Where the carrier goes so that the player's eyes land on the centre. */
			Location seatLocation(){
				Vector seat = (cameraCentre == null ? centre : cameraCentre).clone().subtract(new Vector(0, SQUID_CAMERA_HEIGHT, 0)).subtract(seatOffset);
				Location at = seat.toLocation(getWorld());
				// Vehicle teleport rotation is also applied to the rider. Only move this carrier.
				at.setYaw(carrierYaw);
				return at;
			}
			/** The seat measured from where the player actually sits on the carrier: exact whatever the client's riding pose does. */
			void measureSeat(){
				Player p = player();
				Vector measured = p.getLocation().toVector().subtract(carrier.getLocation().toVector());
				if(Math.abs(measured.getX()) < 0.01 && Math.abs(measured.getZ()) < 0.01 && Math.abs(measured.getY()) < 2)seatOffset = measured;
			}
			boolean riding(){
				return carrier != null && carrier.isValid() && player().getVehicle() == carrier;
			}
			/** Where the player stands when the body comes out: on the floor under a floor squid, else where the body was, the feet a little below the centre. */
			Location standingSpot(){
				Vector feet = surface == Surface.FLOOR ? centre.clone().subtract(new Vector(0, SQUID_RIDE_HEIGHT, 0)) : centre.clone().subtract(new Vector(0, 0.9, 0));
				if(surface == Surface.WALL)feet.add(wallSide.getDirection().multiply(-0.08));
				if(surface == Surface.CEILING)feet.setY(centre.getY() - 1.8);
				Location at = feet.toLocation(getWorld());
				at.setYaw(player().getLocation().getYaw());
				at.setPitch(player().getLocation().getPitch());
				return at;
			}
			boolean standingClear(Location feet){
				Vector body = feet.toVector().add(new Vector(0, 0.9, 0));
				Vector extent = new Vector(0.3, 0.899, 0.3);
				return bodyClear(body, extent);
			}
			boolean bodyClear(Vector body, Vector extent){
				for(CollisionBlock obstacle : collisionBlocks(body, extent, new Vector())){
					if(SquidCollision.overlaps(body, extent, obstacle.bounds()))return false;
				}
				return true;
			}
			/**
			 * Coming out: armour back, and the charge goes off as a surge, here and now on the floor, at the landing point along the flight otherwise.
			 * The momentum is handed to the player, so letting go keeps it, this time on the feet.
			 */
			void surface(){
				if(!submerged)return;
				Location exit = standingSpot();
				if(riding() && !standingClear(exit)){
					if(lastSurfaceAttemptTick != wetInkTicks){
						lastSurfaceAttemptTick = wetInkTicks;
						if(blockedSurfaceTicks++ % 20 == 0)PaperMessages.sendActionBar(player(), ChatColor.GRAY + "Waiting for room to stand", 25);
					}
					if(reserve > 0 || blockedSurfaceTicks < 20)return;
					if(lastClearStandingSpot == null || !standingClear(lastClearStandingSpot))return;
					exit = lastClearStandingSpot.clone();
					exit.setYaw(player().getLocation().getYaw());
					exit.setPitch(player().getLocation().getPitch());
				}
				Vector momentum = velocity();
				if(surface == Surface.WALL)momentum.add(wallSide.getDirection().multiply(-0.12));
				if(surface == Surface.CEILING)momentum.setY(Math.min(-0.08, momentum.getY()));
				boolean onFloor = surface == Surface.FLOOR;
				surfaceQuietly(exit);
				Player p = player();
				sound(SwimSound.SURFACE, p.getLocation(), 0);
				getWorld().spawnParticle(Particle.SPLASH, p.getLocation().add(0, 0.2, 0), 25, 0.6, 0.1, 0.6, 0);
				if(onFloor){
					releaseSurge(reserve, p.getLocation(), new Vector(0, -1, 0));
				}else{
					pendingSurge = reserve;
					flight = momentum;
				}
				p.setVelocity(momentum);
				reserve = 0;
				p.setExp(0);
			}
			void surfaceQuietly(){
				if(!riding()){
					surfaceQuietly(player().getLocation());
					return;
				}
				Location exit = standingSpot();
				if(!standingClear(exit) && lastClearStandingSpot != null && standingClear(lastClearStandingSpot))exit = lastClearStandingSpot.clone();
				surfaceQuietly(exit);
			}
			void surfaceQuietly(Location exit){
				submerged = false;
				turboJetTick = SquidMotion.JET_DURATION_TICKS;
				turboJetStrength = 0;
				turboTrailTicks = 0;
				Player p = player();
				if(carrier != null){
					if(riding()){
						carrier.removePassenger(p);
						p.teleport(exit);
					}
					carrier.remove();
					carrier = null;
				}
				surface = Surface.AIR;
				wallSide = null;
				gripped = null;
				gripContact = null;
				cameraCentre = null;
				blockedSurfaceTicks = 0;
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
				Utils.donarItemsPlayer(p, getStartingItems(p));
				if(kit != null)kit.hideInkSacs();
			}
			void reset(){
				surfaceQuietly();
				reserve = 0;
				groundUnderStrip.clear();
				pendingSurge = -1;
				setMomentum(new Vector());
			}
			void tickPendingSurge(){
				if(pendingSurge < 0)return;
				Player p = player();
				if(!p.isOnGround())return;
				Vector incoming = flight.lengthSquared() < 1e-4 ? new Vector(0, -1, 0) : flight;
				releaseSurge(pendingSurge, p.getLocation(), incoming);
				pendingSurge = -1;
			}
			/** The surge: the ink gathered while swimming, thrown around the body as it comes out. A full charge is a grenade: a wide splash and a bite on enemies near it. */
			void releaseSurge(double amount, Location feet, Vector incoming){
				if(kit == null)return;
				Player p = player();
				if(amount < 0.1)return; // an empty squid stands up with nothing to throw
				double radius = 1.5 + 2.5 * amount;
				kit.splash(feet.clone().add(0, 0.4, 0), incoming, new Vector(0, 1, 0), radius, 0.7 + 1.5 * amount);
				getWorld().playSound(feet, Sound.ENTITY_SLIME_ATTACK, (float) (0.6 + amount), (float) (1.3 - 0.5 * amount));
				getWorld().spawnParticle(Particle.SPLASH, feet.clone().add(0, 0.3, 0), (int) (30 + 80 * amount), radius * 0.4, 0.3, radius * 0.4, 0);
				for(Player enemy : Utils.getNearbyPlayers(feet, radius)){
					if(areEnemies(enemy, p))kit.hurt(enemy, 2 + 4 * amount);
				}
			}

			/** One tick under the ink: the body, the reserve, the strip, then the movement on whatever surface it holds, and the carrier placed where the body ended. */
			void tick(EquipInkWars team){
				Player p = player();
				if(!riding()){ // thrown off by something else: a death, a teleport, a lost carrier
					swimForm = false;
					surface();
					return;
				}
				measureSeat();
				submergedTicks++;
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, true, false));
				p.setFallDistance(0);
				if(submergedTicks % 20 == 0)Utils.healDamageable(p, 1.0);
				if(cornerTicks > 0)cornerTicks--;
				if(detachTicks > 0)detachTicks--;

				Block touched = gripped;
				EquipInkWars ground = groundColour(touched);
				if(surface != Surface.AIR)tickReserve(team, ground, pushed);
				if(reserve <= 0 && ground != team && surface != Surface.AIR){
					forcedOut();
					if(!submerged)return;
				}
				ripple(team, pushed);

				Keys keys = readKeys();
				prepareJump(keys);
				trailParcelsThisTick = 0;
				Vector before = centre.clone();
				int steps = Math.max(1, (int) Math.ceil((speed + (surface == Surface.AIR ? Math.abs(verticalSpeed) : 0) + SQUID_ACCELERATION) / SQUID_MOVEMENT_STEP));
				movementFraction = 1.0 / steps;
				double stripDistance = 0;
				for(int step = 0; step < steps; step++){
					firstMovementStep = step == 0;
					Vector stepStart = centre.clone();
					switch(surface){
						case FLOOR -> tickFloor(keys);
						case WALL -> tickWall(keys);
						case CEILING -> tickCeiling(keys);
						case AIR -> tickAir(keys);
					}
					stripDistance += centre.distance(stepStart);
					if(stripDistance >= 0.5 || step == steps - 1){
						layStripAlong(team, stepStart);
						stripDistance = 0;
					}
				}
				pushed = centre.clone().subtract(before);
				if(turboJetTick == 3 || turboJetTick == 6)sound(SwimSound.PUSH, surfacePoint(), turboJetStrength);
				if(turboJetTick == SquidMotion.JET_PUSH_TICKS)sound(SwimSound.TAIL, surfacePoint(), 0);
				if(turboTrailTicks > 0)turboTrailTicks--;
				Location standing = standingSpot();
				if(standingClear(standing))lastClearStandingSpot = standing;
				updateCamera();
				carrier.teleport(seatLocation()); // a vehicle keeps its passengers through a teleport since 1.21.10
				showMeters();
			}
			void updateCamera(){
				if(cameraCentre == null){
					cameraCentre = centre.clone();
					return;
				}
				cameraCentre.setX(centre.getX()).setZ(centre.getZ());
				// Follow resolved travel; desired velocity can point into a blocked ceiling or wall.
				if(surface != Surface.FLOOR)cameraCentre.setY(cameraCentre.getY() + pushed.getY());
				cameraCentre.add(centre.clone().subtract(cameraCentre).multiply(0.45));
				// Never smooth the camera through the surface the body has just rounded.
				Vector cameraOffset = cameraCentre.clone().subtract(centre);
				if(cameraOffset.lengthSquared() > 1e-6){
					RayTraceResult obstruction = getWorld().rayTraceBlocks(centre.toLocation(getWorld()), cameraOffset, cameraOffset.length() + 0.1, FluidCollisionMode.NEVER, true);
					if(obstruction != null)cameraCentre = centre.clone();
				}
			}
			/** The colour the reserve judges the ground by: what was there before this dive's own strip, else what is there now. */
			EquipInkWars groundColour(Block touched){
				if(touched != null && groundUnderStrip.containsKey(touched))return groundUnderStrip.get(touched);
				return getTeamOwningBlock(touched);
			}
			/** Gathered per block swum on the team's colour, spent per tick and per block elsewhere: a long run on own ink is what pays for a crossing. */
			void tickReserve(EquipInkWars team, EquipInkWars ground, Vector moved){
				double blocksSwum = moved.length();
				boolean wasFull = reserve >= 1;
				if(ground == team)reserve = Math.min(1, reserve + RESERVE_REFILL);
				else if(ground == null)reserve -= RESERVE_DRAIN_NEUTRAL + RESERVE_DRAIN_NEUTRAL_PER_BLOCK * blocksSwum;
				else reserve -= RESERVE_DRAIN_ENEMY + RESERVE_DRAIN_ENEMY_PER_BLOCK * blocksSwum;
				reserve = Math.max(0, reserve);
				if(!wasFull && reserve >= 1)sound(SwimSound.READY, player().getLocation(), 0);
			}
			/** Start a paid jet with an immediate bite, a powered push and a short release. */
			void thrust(){
				Player p = player();
				if(!tryThrust(p.getEyeLocation().getDirection())){
					sound(SwimSound.EMPTY, p.getLocation(), 0);
					return;
				}
				Location burst = surfacePoint();
				sound(SwimSound.TURBO, burst, 0);
				Particle.DustOptions dust = new Particle.DustOptions(team().getStrongColor().getColor(), 1.6F);
				Vector back = heading.clone().multiply(-1);
				getWorld().spawnParticle(Particle.DUST, burst.clone().add(back.multiply(0.6)), 24, 0.35, 0.25, 0.35, 0, dust);
				showMeters();
			}
			boolean tryThrust(Vector aim){
				if(reserve < SQUID_THRUST_COST || !Double.isFinite(aim.lengthSquared()) || aim.lengthSquared() < 1e-9)return false;
				Vector direction = aim.clone().normalize();
				Vector normal = surfaceNormal();
				boolean launch = surface == Surface.AIR || direction.dot(normal) > 0.2;
				if(!launch){
					direction.subtract(normal.clone().multiply(direction.dot(normal)));
					if(surface == Surface.WALL && direction.lengthSquared() < 1e-9)direction = new Vector(0, 1, 0);
					if(direction.lengthSquared() < 1e-9)return false;
					direction.normalize();
				}
				Contact blocked = sweep(centre, direction.clone().multiply(0.1));
				if(blocked != null && blocked.gap() < 0.05)return false;
				double launchPitch = direction.getY();
				Vector boost = direction.multiply(Math.min(SQUID_THRUST_CEILING, velocity().length() + SquidMotion.JET_KICK));
				if(launch){
					if(surface != Surface.AIR){
						detachedFace = faceOf(normal);
						detachTicks = SQUID_DETACH_TICKS;
					}
					takeOff(boost.getY());
					setMomentum(boost.clone().setY(0));
				}else setMomentum(boost);
				reserve -= SQUID_THRUST_COST;
				thrustPending = true;
				turboJetTick = 0;
				turboJetPitch = launchPitch;
				turboJetStrength = SquidMotion.jetEnvelope(0);
				trailDistanceSinceParcel = 0;
				jumpBufferedTicks = 0;
				floorGraceTicks = 0;
				turboTrailTicks = SQUID_TURBO_TRAIL_TICKS;
				return true;
			}
			/** No ink left: the squid is forced back onto its feet with whatever momentum it had, and the surge goes off. */
			void forcedOut(){
				Player p = player();
				if(swimForm){
					sound(SwimSound.EMPTY, p.getLocation(), 0);
					PaperMessages.sendActionBar(p, ChatColor.RED + "Out of ink", 30);
				}
				swimForm = false;
				surface();
			}
			/** The point on the surface under the body, where the ink shows. */
			Location surfacePoint(){
				return surfacePointAt(centre);
			}
			/** The point on the current surface under a body centred at a given point. */
			Location surfacePointAt(Vector bodyCentre){
				Vector at = switch(surface){
					case FLOOR -> bodyCentre.clone().subtract(new Vector(0, SQUID_RIDE_HEIGHT - 0.15, 0));
					case WALL -> bodyCentre.clone().add(wallSide.getDirection().multiply(SQUID_RADIUS - 0.1));
					case CEILING -> bodyCentre.clone().add(new Vector(0, SQUID_RADIUS - 0.1, 0));
					case AIR -> bodyCentre.clone();
				};
				return at.toLocation(getWorld());
			}
			/** The block of the current surface a body centred at a given point rides on; none in the air. */
			Block blockAt(Vector bodyCentre){
				Vector at = switch(surface){
					case FLOOR -> bodyCentre.clone().subtract(new Vector(0, SQUID_RIDE_HEIGHT + 0.05, 0));
					case WALL -> bodyCentre.clone().add(wallSide.getDirection().multiply(SQUID_RADIUS + 0.05));
					case CEILING -> bodyCentre.clone().add(new Vector(0, SQUID_RADIUS + 0.05, 0));
					case AIR -> null;
				};
				return at == null ? null : at.toLocation(getWorld()).getBlock();
			}
			/** The strip laid along the whole of this tick's path, a stroke every half block, so a thrust leaves no gaps. */
			void layStripAlong(EquipInkWars team, Vector before){
				if(surface == Surface.AIR)return;
				Vector step = centre.clone().subtract(before);
				int samples = Math.max(1, (int) Math.ceil(step.length() / 0.5));
				for(int i = 1; i <= samples; i++){
					Vector at = before.clone().add(step.clone().multiply((double) i / samples));
					Block touched = blockAt(at);
					if(touched != null)layStrip(team, touched, at);
				}
			}
			void ripple(EquipInkWars team, Vector moved){
				if(surface != Surface.AIR && submergedTicks % 16 == 0 && moved.lengthSquared() > 0.01){
					sound(SwimSound.RIPPLE, surfacePoint(), 0);
				}
				if(submergedTicks % 2 != 0)return;
				Particle.DustOptions dust = new Particle.DustOptions(team.getStrongColor().getColor(), 1.4F);
				getWorld().spawnParticle(Particle.DUST, surfacePoint(), (int) (3 + moved.length() * 12), 0.45, 0.05, 0.45, 0, dust);
			}
			/**
			 * The strip the squid lays: on its own colour a full re-wet, elsewhere a stroke across the heading that thins with the reserve down to the one block under the body.
			 * What the ground was is remembered before the stroke covers it.
			 */
			void layStrip(EquipInkWars team, Block touched, Vector at){
				Player p = player();
				if(getTeamOwningBlock(touched) == team){
					rewet(touched, team, p.getName());
					if(surface == Surface.WALL)rewet(touched.getRelative(BlockFace.UP), team, p.getName());
					return;
				}
				if(kit == null)return;
				double ink = 0.15 + 0.35 * reserve;
				double halfWidth = 0.5 + 1.5 * reserve;
				ArrayList<Block> stroke = new ArrayList<>();
				if(surface == Surface.WALL){
					stroke.add(touched);
					if(gripable(touched.getRelative(BlockFace.UP)))stroke.add(touched.getRelative(BlockFace.UP)); // the body is two blocks tall on a wall
				}else if(surface != Surface.FLOOR || halfWidth < 0.9){
					stroke.add(touched);
				}else{
					stroke = kit.rollerStrokeBlocks(halfWidth, surfacePointAt(at), new Vector(heading.getX(), 0, heading.getZ()), 0);
				}
				for(Block b : stroke){
					EquipInkWars owner = getTeamOwningBlock(b);
					if(owner != team)groundUnderStrip.putIfAbsent(b, owner);
					kit.paintBlock(b, ink);
				}
			}
			/** The ink on the experience bar and as the stack of sacs in hand. */
			void showMeters(){
				Player p = player();
				p.setExp((float) Math.min(0.999, Math.max(0, reserve)));
				if(submergedTicks % 4 == 0 && kit != null)kit.updateInkSacs(reserve);
			}

			//--- the keys
			/** What the hands are doing this tick: the direction the keys point on the floor, in the look's frame, and whether jump was just pressed. */
			record Keys(Vector flat, boolean jumpPressed, double forward, double sideways) {}
			/** Read straight from the client's input, not inferred from where the body went: the keys in the yaw's frame, and the press of jump. */
			Keys readKeys(){
				Player p = player();
				Input input = p.getCurrentInput();
				double yaw = Math.toRadians(p.getLocation().getYaw());
				Vector forward = new Vector(-Math.sin(yaw), 0, Math.cos(yaw));
				Vector right = new Vector(-forward.getZ(), 0, forward.getX());
				double forwardAmount = (input.isForward() ? 1 : 0) - (input.isBackward() ? 1 : 0);
				double sidewaysAmount = (input.isRight() ? 1 : 0) - (input.isLeft() ? 1 : 0);
				if(controlForward == null || surface == Surface.AIR || (surface == Surface.FLOOR && forwardAmount == 0 && sidewaysAmount == 0)){
					Vector normal = surfaceNormal();
					controlForward = surface == Surface.WALL ? bendOntoWall(forward, normal) : forward.clone();
					controlRight = surface == Surface.WALL ? bendOntoWall(right, normal) : right.clone();
				}else if(surface == Surface.FLOOR && (cornerTicks <= CORNER_TICKS - 2 || p.getLocation().getYaw() != lastControlYaw)){
					controlForward = forward.clone();
					controlRight = right.clone();
				}
				Vector keys = forward.clone().multiply(forwardAmount).add(right.clone().multiply(sidewaysAmount));
				if(keys.lengthSquared() > 1e-6)keys.normalize();
				boolean jumpPressed = input.isJump() && !jumpHeld;
				jumpHeld = input.isJump();
				lastControlYaw = p.getLocation().getYaw();
				return new Keys(keys, jumpPressed, forwardAmount, sidewaysAmount);
			}
			void prepareJump(Keys keys){
				jumpBufferedTicks = keys.jumpPressed() ? SQUID_JUMP_BUFFER_TICKS : Math.max(0, jumpBufferedTicks - 1);
				floorGraceTicks = surface == Surface.FLOOR ? SQUID_EDGE_GRACE_TICKS + 1 : Math.max(0, floorGraceTicks - 1);
			}
			boolean consumeJump(Keys keys){
				if(!firstMovementStep || (!keys.jumpPressed() && jumpBufferedTicks == 0))return false;
				jumpBufferedTicks = 0;
				floorGraceTicks = 0;
				return true;
			}
			Vector movementKeys(Keys keys){
				if(surface == Surface.AIR)return keys.flat();
				Vector wanted = controlForward.clone().multiply(keys.forward()).add(controlRight.clone().multiply(keys.sideways()));
				return wanted.lengthSquared() < 1e-6 ? wanted : wanted.normalize();
			}
			Vector surfaceNormal(){
				return switch(surface){
					case WALL -> wallSide.getDirection().multiply(-1);
					case CEILING -> new Vector(0, -1, 0);
					default -> new Vector(0, 1, 0);
				};
			}
			Vector velocity(){
				Vector velocity = heading.clone().multiply(speed);
				if(surface == Surface.AIR)velocity.setY(verticalSpeed);
				return velocity;
			}
			/** The floor's keys bent onto a wall by the same rule as the road: toward the wall climbs, away from it descends, along it runs along. */
			Vector bendOntoWall(Vector flatKeys, Vector normal){
				if(flatKeys.lengthSquared() < 1e-6)return flatKeys;
				double into = -flatKeys.dot(normal);
				Vector bent = flatKeys.clone().add(normal.clone().multiply(into)).add(new Vector(0, into, 0));
				return bent.lengthSquared() < 1e-6 ? bent : bent.normalize();
			}

			//--- the grid
			/** A block the squid can hold: anything paintable, the plain solid blocks a splash would paint, and the shapes that wear the colour beneath them; never a barrier. */
			boolean gripable(Block b){
				return isPaintable(b) || isPaintableUnsafely(b) || (isShape(b) && !b.isPassable());
			}
			/** A surface the probe found: the block, the face of it that faces the body, and how far the body's edge is from that face along the probe. */
			record Contact(Block block, BlockFace face, double gap, Vector point, BoundingBox shape) {}
			record CollisionBlock(Block block, BoundingBox bounds) {}
			ArrayList<CollisionBlock> collisionBlocks(Vector from, Vector extent, Vector displacement){
				Vector end = from.clone().add(displacement);
				ArrayList<CollisionBlock> obstacles = new ArrayList<>();
				int minY = Math.max(getWorld().getMinHeight(), (int) Math.floor(Math.min(from.getY(), end.getY()) - extent.getY()) - 1);
				int maxY = Math.min(getWorld().getMaxHeight() - 1, (int) Math.floor(Math.max(from.getY(), end.getY()) + extent.getY()));
				for(int x = (int) Math.floor(Math.min(from.getX(), end.getX()) - extent.getX()); x <= Math.floor(Math.max(from.getX(), end.getX()) + extent.getX()); x++){
					for(int z = (int) Math.floor(Math.min(from.getZ(), end.getZ()) - extent.getZ()); z <= Math.floor(Math.max(from.getZ(), end.getZ()) + extent.getZ()); z++){
						for(int y = minY; y <= maxY; y++){
							Block block = getWorld().getBlockAt(x, y, z);
							if(block.isPassable())continue;
							for(BoundingBox local : block.getCollisionShape().getBoundingBoxes()){
								obstacles.add(new CollisionBlock(block, local.clone().shift(x, y, z)));
							}
						}
					}
				}
				return obstacles;
			}
			BlockFace faceOf(Vector normal){
				if(Math.abs(normal.getY()) > 0.5)return normal.getY() > 0 ? BlockFace.UP : BlockFace.DOWN;
				return sideOf(normal);
			}
			Contact sweep(Vector from, Vector displacement){
				Vector extent = new Vector(SQUID_RADIUS, SQUID_RADIUS, SQUID_RADIUS);
				ArrayList<CollisionBlock> obstacles = collisionBlocks(from, extent, displacement);
				ArrayList<BoundingBox> bounds = new ArrayList<>();
				for(CollisionBlock obstacle : obstacles)bounds.add(obstacle.bounds());
				SquidCollision.Hit hit = SquidCollision.sweep(from, extent, displacement, bounds);
				if(hit == null)return null;
				CollisionBlock obstacle = obstacles.get(hit.obstacleIndex);
				Vector point = hit.position.clone().subtract(hit.normal.clone().multiply(SQUID_RADIUS));
				return new Contact(obstacle.block(), faceOf(hit.normal), displacement.length() * hit.fraction, point, obstacle.bounds());
			}
			/** Feels from a point along a direction for the first block face within the body's radius plus the reach; the gap is from the body's edge, not its centre. Null when nothing is that close. */
			Contact probe(Vector from, Vector direction, double reach){
				if(direction.lengthSquared() < 1e-9)return null;
				Vector unit = direction.clone().normalize();
				RayTraceResult hit = getWorld().rayTraceBlocks(from.toLocation(getWorld()), unit, SQUID_RADIUS + reach, FluidCollisionMode.NEVER, true);
				if(hit == null || hit.getHitBlock() == null || hit.getHitBlockFace() == null)return null;
				double gap = hit.getHitPosition().clone().subtract(from).dot(unit) - SQUID_RADIUS;
				BoundingBox shape = hit.getHitBlock().getBoundingBox();
				for(BoundingBox local : hit.getHitBlock().getCollisionShape().getBoundingBoxes()){
					BoundingBox candidate = local.clone().shift(hit.getHitBlock().getLocation());
					if(candidate.clone().expand(0.001).contains(hit.getHitPosition())){shape = candidate; break;}
				}
				return new Contact(hit.getHitBlock(), hit.getHitBlockFace(), gap, hit.getHitPosition(), shape);
			}
			Contact probe(Vector direction, double reach){
				if(direction.lengthSquared() < 1e-9)return null;
				return sweep(centre, direction.clone().normalize().multiply(Math.max(SQUID_CONTACT_SKIN, reach)));
			}
			/** Whether a contact is a wall the squid can hold: a side face of a gripable block. */
			boolean holdableWall(Contact contact){
				return contact != null && contact.face().getModY() == 0 && gripable(contact.block());
			}
			/** Puts the body's edge exactly on a face: the centre one radius off its plane, on the face's open side. */
			void restOn(Contact contact){
				BlockFace face = contact.face();
				double offset = SQUID_RADIUS + SQUID_CONTACT_SKIN;
				if(face.getModX() != 0)centre.setX(contact.point().getX() + face.getModX() * offset);
				else if(face.getModY() != 0)centre.setY(contact.point().getY() + face.getModY() * offset);
				else centre.setZ(contact.point().getZ() + face.getModZ() * offset);
				gripContact = contact;
				missingContactTicks = 0;
			}
			void advance(Vector direction, double distance){
				if(distance <= 0 || direction.lengthSquared() < 1e-9)return;
				Vector start = centre.clone();
				Vector displacement = direction.clone().normalize().multiply(distance);
				Contact obstruction = sweep(centre, displacement);
				if(obstruction != null){
					if(surface == Surface.FLOOR && obstruction.face().getModY() == 0){
						double rise = obstruction.shape().getMaxY() + SQUID_RIDE_HEIGHT - centre.getY();
						if(rise > 0 && rise <= SQUID_STEP){
							Vector raised = centre.clone().add(new Vector(0, rise, 0));
							if(sweep(centre, new Vector(0, rise, 0)) == null && sweep(raised, displacement) == null){
								emitTurboTrail(start, raised);
								emitTurboTrail(raised, raised.clone().add(displacement));
								centre = raised.add(displacement);
								return;
							}
						}
					}
					distance = Math.max(0, obstruction.gap() - SQUID_CONTACT_SKIN);
				}
				centre.add(direction.clone().normalize().multiply(distance));
				emitTurboTrail(start, centre);
			}
			void emitTurboTrail(Vector from, Vector to){
				if(kit != null)emitTurboTrail(kit.hose, from, to);
			}
			void emitTurboTrail(InkStream stream, Vector from, Vector to){
				if(turboTrailTicks <= 0 || from.distanceSquared(to) < 1e-8)return;
				double distance = from.distance(to);
				Vector direction = to.clone().subtract(from).multiply(1 / distance);
				// Carry spacing across collision substeps without drawing a chord through a corner.
				for(double offset = 0.25 - trailDistanceSinceParcel; offset <= distance; offset += 0.25){
					if(trailParcelsThisTick >= 8)break;
					Vector sample = from.clone().add(direction.clone().multiply(offset));
					Vector sampleStart = sample.clone().subtract(direction.clone().multiply(Math.min(0.001, offset)));
					stream.emitTrail(sampleStart.toLocation(getWorld()), sample.toLocation(getWorld()), velocity(), SQUID_TRAIL_LOAD, Math.max(0.1, turboJetStrength));
					trailParcelsThisTick++;
				}
				trailDistanceSinceParcel = (trailDistanceSinceParcel + distance) % 0.25;
			}
			/** A face the body cannot hold or pass: the heading loses what pointed into it and the body slides along, a little slower. */
			void slideAlong(BlockFace face){
				setMomentum(SquidMotion.slide(heading.clone().multiply(speed), face.getDirection()));
			}
			/**
			 * Past the end of the gripped face, round the outside edge: the new face belongs to the block just held, on the side the body came from.
			 * A ray from the body cannot find it, the body sits outside the block's column, so it is taken from the block itself. Null when that face is not open,
			 * or when the body has not reached it by the given tolerance.
			 */
			Contact aroundEdge(double leastGap){
				BlockFace travelled = sideOf(new Vector(heading.getX(), 0, heading.getZ()));
				if(gripped == null || gripContact == null || travelled == null || (wallSide != null && (travelled == wallSide || travelled == wallSide.getOppositeFace())))return null;
				if(!gripped.getRelative(travelled).isPassable())return null; // the surface goes on in that direction, this is not its edge
				boolean alongX = travelled.getModX() != 0;
				boolean positive = travelled.getModX() + travelled.getModZ() > 0;
				double bodyCentre = alongX ? centre.getX() : centre.getZ();
				BoundingBox shape = gripContact.shape();
				double plane = alongX ? (positive ? shape.getMaxX() : shape.getMinX()) : (positive ? shape.getMaxZ() : shape.getMinZ());
				double gap = positive ? (bodyCentre - SQUID_RADIUS) - plane : plane - (bodyCentre + SQUID_RADIUS);
				if(gap < leastGap)return null;
				Vector point = centre.clone();
				if(alongX)point.setX(plane); else point.setZ(plane);
				return new Contact(gripped, travelled, Math.max(0, gap), point, shape);
			}

			//--- the bends
			/**
			 * The universal bend: passing from a surface with one normal onto a surface with another, the part of the heading that pointed into the new surface
			 * is turned to run away from the old one. Floor into wall becomes up, wall down to floor becomes away, a corner either way turns onto the new wall.
			 */
			void redirect(Vector oldNormal, Vector newNormal){
				Vector turned = SquidMotion.rotateTangent(heading.clone().multiply(speed), oldNormal, newNormal);
				if(turned.lengthSquared() > 1e-9)setMomentum(turned);
				if(controlForward != null){
					controlForward = SquidMotion.rotateTangent(controlForward, oldNormal, newNormal);
					controlRight = SquidMotion.rotateTangent(controlRight, oldNormal, newNormal);
				}
				cornerTicks = CORNER_TICKS;
			}
			/** Onto a wall at whatever angle: the road bends, the speed is kept, the body rests on the face. */
			void attachToWall(Contact wall, Vector oldNormal){
				BlockFace side = wall.face().getOppositeFace();
				boolean newContact = surface != Surface.WALL || wallSide != side;
				if(surface == Surface.AIR){
					Vector incoming = velocity();
					Vector tangent = SquidMotion.slide(incoming, wall.face().getDirection());
					double inward = Math.max(0, -incoming.dot(wall.face().getDirection()));
					tangent.setY(tangent.getY() + inward * (incoming.getY() < -0.1 ? -1 : 1));
					if(tangent.lengthSquared() > 1e-6)setMomentum(tangent.normalize().multiply(incoming.length()));
					controlForward = bendOntoWall(controlForward, wall.face().getDirection());
					controlRight = bendOntoWall(controlRight, wall.face().getDirection());
				}else redirect(oldNormal, side.getDirection().multiply(-1));
				surface = Surface.WALL;
				wallSide = side;
				gripped = wall.block();
				restOn(wall);
				verticalSpeed = 0;
				if(newContact)sound(SwimSound.CONTACT, surfacePoint(), 0);
			}
			/** Onto a ceiling: the road bends to run along it, the speed is kept, the body rests under it. */
			void attachToCeiling(Contact ceiling, Vector oldNormal){
				boolean newContact = surface != Surface.CEILING;
				redirect(oldNormal, new Vector(0, -1, 0));
				flattenHeading(oldNormal);
				surface = Surface.CEILING;
				wallSide = null;
				gripped = ceiling.block();
				restOn(ceiling);
				verticalSpeed = 0;
				if(newContact)sound(SwimSound.CONTACT, surfacePoint(), 0);
			}
			/** Onto a floor: the road bends to run along it, the body rides at its height over the block. */
			void landOnFloor(Vector oldNormal, Block floor, double top){
				redirect(oldNormal, new Vector(0, 1, 0));
				flattenHeading(oldNormal);
				if(controlForward != null)controlRight = controlForward.clone().crossProduct(new Vector(0, 1, 0));
				surface = Surface.FLOOR;
				wallSide = null;
				gripped = floor;
				Vector floorCentre = centre.clone().setY(top + SQUID_RIDE_HEIGHT);
				if(bodyClear(floorCentre, new Vector(SQUID_RADIUS, SQUID_RADIUS, SQUID_RADIUS)))centre = floorCentre;
				verticalSpeed = 0;
				missingContactTicks = 0;
			}
			void flattenHeading(Vector fallback){
				heading.setY(0);
				if(heading.lengthSquared() < 1e-6)heading = fallback.clone().setY(0);
				if(heading.lengthSquared() < 1e-6)heading = new Vector(1, 0, 0);
				heading.normalize();
			}
			void takeOff(double upward){
				Vector momentum = heading.clone().multiply(speed);
				surface = Surface.AIR;
				wallSide = null;
				gripped = null;
				verticalSpeed = upward;
				setMomentum(momentum.setY(0));
				if(turboJetTick < SquidMotion.JET_DURATION_TICKS && velocity().lengthSquared() > 1e-9)turboJetPitch = velocity().clone().normalize().getY();
				cornerTicks = 0;
				controlForward = new Vector(-Math.sin(Math.toRadians(player().getLocation().getYaw())), 0, Math.cos(Math.toRadians(player().getLocation().getYaw())));
				controlRight = new Vector(-controlForward.getZ(), 0, controlForward.getX());
			}
			/** Off the wall into the air, away from it, still a squid. */
			void leapOff(Vector normal){
				Player p = player();
				Vector launch = SquidMotion.wallJump(velocity(), normal, 0.4, SQUID_WALL_LEAP);
				detachedFace = faceOf(normal);
				detachTicks = SQUID_DETACH_TICKS;
				takeOff(launch.getY());
				setMomentum(launch.setY(0));
				sound(SwimSound.JUMP, p.getLocation(), 0);
			}

			//--- the surfaces
			/**
			 * On a floor the cart is steered by the keys. Ahead, a wall the body would touch bends the road up it at any angle, speed kept, the rest of the tick's
			 * travel climbing; a lip no higher than a step is stepped over; anything else stops the body at its face and it slides along. The body then rides at its
			 * height over whatever floor is under it, up or down a step; with none within a step down, it is in the air. Jump hops, momentum kept.
			 */
			void tickFloor(Keys keys){
				flattenHeading(new Vector(1, 0, 0));
				if(firstMovementStep)steer(movementKeys(keys), 1, true);
				if(consumeJump(keys)){
					takeOff(SQUID_HOP);
					sound(SwimSound.JUMP, player().getLocation(), 0);
					return;
				}
				double travel = speed * movementFraction;
				if(travel > 1e-4){
					Vector low = centre.clone().add(new Vector(0, -SQUID_RIDE_HEIGHT + 0.15, 0));
					Vector high = centre.clone().add(new Vector(0, -SQUID_RIDE_HEIGHT + SQUID_STEP + 0.1, 0));
					Contact lowHit = probe(low, heading, travel);
					Contact highHit = probe(high, heading, travel);
					Contact bodyHit = probe(heading, travel);
					if(bodyHit != null && bodyHit.face().getModY() == 0 && bodyHit.shape().getMaxY() - (centre.getY() - SQUID_RIDE_HEIGHT) > SQUID_STEP && (highHit == null || bodyHit.gap() < highHit.gap()))highHit = bodyHit;
					Contact wall = holdableWall(highHit) ? highHit : holdableWall(lowHit) && !isShape(lowHit.block()) ? lowHit : null;
					if(wall != null && movementKeys(keys).dot(wall.face().getDirection()) > -0.25)wall = null;
					if(wall != null){
						double toFace = Math.max(0, Math.min(travel, wall.gap()));
						advance(heading, toFace);
						attachToWall(wall, new Vector(0, 1, 0));
						advance(heading, travel - toFace);
						return;
					}
					if(highHit != null){ // too tall to step, not a wall to hold
						double toFace = Math.max(0, Math.min(travel, highHit.gap()));
						advance(heading, toFace);
						slideAlong(highHit.face());
						advance(heading, speed * movementFraction * (1 - toFace / travel));
					}else{
						advance(heading, travel);
					}
				}
				Vector castFrom = centre.clone().add(new Vector(0, SQUID_STEP, 0));
				if(!castFrom.toLocation(getWorld()).getBlock().isPassable())castFrom = centre.clone();
				double below = (castFrom.getY() - centre.getY()) + SQUID_RIDE_HEIGHT + SQUID_STEP;
				RayTraceResult floor = getWorld().rayTraceBlocks(castFrom.toLocation(getWorld()), new Vector(0, -1, 0), below, FluidCollisionMode.NEVER, true);
				if(floor == null || floor.getHitBlock() == null || floor.getHitBlockFace() != BlockFace.UP){
					takeOff(0);
					return;
				}
				gripped = floor.getHitBlock();
				Vector floorCentre = centre.clone().setY(floor.getHitPosition().getY() + SQUID_RIDE_HEIGHT);
				if(sweep(centre, floorCentre.clone().subtract(centre)) == null && bodyClear(floorCentre, new Vector(SQUID_RADIUS, SQUID_RADIUS, SQUID_RADIUS)))centre = floorCentre;
				gripContact = new Contact(gripped, BlockFace.UP, 0, floor.getHitPosition(), gripped.getBoundingBox());
			}
			/**
			 * On a wall the keys work as on the floor, bent onto the wall: toward it climbs, away from it descends, along it runs; the look is free. Jump leaps off.
			 * Ahead along the road: another wall is a concave corner and the road bends onto it, a floor under a descent bends onto it, a ceiling over a climb bends onto it,
			 * anything unholdable stops the body and it slides. Then the body rests on the wall beside it; when that face ends: above the block's top the road goes over
			 * onto the roof, round an outside edge it bends onto the next face, otherwise the air.
			 */
			void tickWall(Keys keys){
				Vector normal = wallSide.getDirection().multiply(-1);
				if(consumeJump(keys)){
					if(turboJetTick < SquidMotion.JET_DURATION_TICKS)applyJet();
					leapOff(normal);
					return;
				}
				Vector bent = movementKeys(keys);
				if(firstMovementStep)steer(bent, 1, true);
				if(bent.getY() > 0.5 && heading.dot(bent) > 0.5 && speed < SQUID_CLIMB_MIN_SPEED)speed = SQUID_CLIMB_MIN_SPEED;
				if(speed < SQUID_CRAWL_SPEED){
					Contact below = probe(new Vector(0, -1, 0), SQUID_WALL_SAG);
					if(below != null && below.face() == BlockFace.UP){
						landOnFloor(normal, below.block(), below.point().getY());
						return;
					}
					advance(new Vector(0, -1, 0), SQUID_WALL_SAG * movementFraction);
				}
				double travel = speed * movementFraction;
				if(travel > 1e-4){
					Contact ahead = probe(heading, travel);
					if(ahead == null){
						advance(heading, travel);
					}else{
						double toFace = Math.max(0, Math.min(travel, ahead.gap()));
						advance(heading, toFace);
						if(holdableWall(ahead) && ahead.face().getOppositeFace() != wallSide){
							attachToWall(ahead, normal);
							cornerTicks = CORNER_TICKS;
							advance(heading, travel - toFace);
							return;
						}
						if(ahead.face() == BlockFace.UP && heading.getY() < 0){
							landOnFloor(normal, ahead.block(), ahead.point().getY());
							advance(heading, travel - toFace);
							return;
						}
						if(ahead.face() == BlockFace.DOWN && heading.getY() > 0 && gripable(ahead.block())){
							attachToCeiling(ahead, normal);
							cornerTicks = CORNER_TICKS;
							advance(heading, travel - toFace);
							return;
						}
						slideAlong(ahead.face());
						advance(heading, speed * movementFraction * (1 - toFace / travel));
					}
				}
				Contact beside = probe(wallSide.getDirection(), 0.12);
				if(holdableWall(beside) && beside.face().getOppositeFace() == wallSide){
					gripped = beside.block();
					restOn(beside);
					return;
				}
				if(gripped != null && gripContact != null && centre.getY() >= gripContact.shape().getMaxY() + SQUID_RADIUS && heading.getY() > 0){
					// over the top: onto the roof, the body carried into the block's column so the roof is under it
					Vector roofCentre = centre.clone().subtract(normal.clone().multiply(SQUID_RADIUS + 0.1));
					roofCentre.setY(gripContact.shape().getMaxY() + SQUID_RIDE_HEIGHT);
					if(sweep(centre, roofCentre.clone().subtract(centre)) == null){
						landOnFloor(normal, gripped, gripContact.shape().getMaxY());
						centre = roofCentre;
						return;
					}
				}
				Contact edge = aroundEdge(-0.35);
				if(edge != null){
					attachToWall(edge, normal);
					cornerTicks = CORNER_TICKS;
					return;
				}
				if(firstMovementStep)missingContactTicks++;
				if(missingContactTicks <= SQUID_CONTACT_GRACE_TICKS)return;
				takeOff(heading.getY() * speed);
			}
			/**
			 * Under a ceiling the keys work as on the floor and the body rests up against it. Jump lets go into a fall. Ahead: a wall is a concave corner and the road
			 * bends down it. When the ceiling ends: the block's side face is the road up onto its roof, taken from the block just held once the body has cleared it;
			 * before that the body holds on; with no face to turn onto, the air.
			 */
			void tickCeiling(Keys keys){
				Vector normal = new Vector(0, -1, 0);
				if(consumeJump(keys)){
					if(turboJetTick < SquidMotion.JET_DURATION_TICKS)applyJet();
					detachedFace = BlockFace.DOWN;
					detachTicks = SQUID_DETACH_TICKS;
					takeOff(0);
					sound(SwimSound.JUMP, player().getLocation(), 0);
					return;
				}
				flattenHeading(new Vector(1, 0, 0));
				if(firstMovementStep)steer(movementKeys(keys), 1, true);
				double travel = speed * movementFraction;
				if(travel > 1e-4){
					Contact ahead = probe(heading, travel);
					if(ahead == null){
						advance(heading, travel);
					}else{
						double toFace = Math.max(0, Math.min(travel, ahead.gap()));
						advance(heading, toFace);
						if(holdableWall(ahead)){
							attachToWall(ahead, normal);
							cornerTicks = CORNER_TICKS;
							advance(heading, travel - toFace);
							return;
						}
						slideAlong(ahead.face());
						advance(heading, speed * movementFraction * (1 - toFace / travel));
					}
				}
				Contact above = probe(new Vector(0, 1, 0), 0.12);
				if(above != null && above.face() == BlockFace.DOWN && gripable(above.block())){
					gripped = above.block();
					restOn(above);
					return;
				}
				Contact edge = aroundEdge(0);
				if(edge != null){
					attachToWall(edge, normal);
					cornerTicks = CORNER_TICKS;
					return;
				}
				if(firstMovementStep)missingContactTicks++;
				if(missingContactTicks > SQUID_CONTACT_GRACE_TICKS && aroundEdge(-0.35) == null)takeOff(0);
			}
			/**
			 * In the air the squid keeps its form and flies its own arc: the engine owns the vertical speed and the momentum, the keys still turn it and push a little.
			 * The first surface on its path becomes its road again: a wall it would touch, the floor it falls onto, a ceiling it rises into.
			 */
			void tickAir(Keys keys){
				flattenHeading(new Vector(1, 0, 0));
				if(firstMovementStep){
					if(floorGraceTicks > 0 && consumeJump(keys)){
						verticalSpeed = SQUID_HOP;
						sound(SwimSound.JUMP, player().getLocation(), 0);
					}
					steer(keys.flat(), SQUID_AIR_THROTTLE, false);
					speed *= SQUID_AIR_DRAG;
					verticalSpeed = (verticalSpeed - SQUID_GRAVITY) * 0.98;
				}
				Vector velocity = heading.clone().multiply(speed).add(new Vector(0, verticalSpeed, 0));
				double length = velocity.length() * movementFraction;
				if(length < 1e-6)return;
				Contact hit = probe(velocity, length);
				if(hit == null){
					advance(velocity, length);
					return;
				}
				double toFace = Math.max(0, Math.min(length, hit.gap()));
				advance(velocity, toFace);
				double remainingTime = movementFraction * (1 - toFace / length);
				boolean canAttach = !(detachTicks > 0 && hit.face() == detachedFace);
				if(holdableWall(hit) && canAttach && keys.flat().dot(hit.face().getDirection()) < -0.25){
					attachToWall(hit, new Vector(0, 1, 0));
					advance(heading, speed * remainingTime);
					return;
				}
				if(hit.face() == BlockFace.UP){
					double impactSpeed = Math.max(0, -verticalSpeed);
					landOnFloor(new Vector(0, 1, 0), hit.block(), hit.point().getY());
					sound(SwimSound.LAND, surfacePoint(), impactSpeed);
					advance(heading, speed * remainingTime);
					return;
				}
				if(hit.face() == BlockFace.DOWN){
					if(verticalSpeed > 0 && gripable(hit.block()) && canAttach){
						attachToCeiling(hit, new Vector(0, 1, 0));
						advance(heading, speed * remainingTime);
						return;
					}
					verticalSpeed = 0;
					advance(heading, speed * remainingTime);
					return;
				}
				slideAlong(hit.face());
				Vector remainingVelocity = velocity();
				advance(remainingVelocity, remainingVelocity.length() * remainingTime);
			}

			/** Intent redirects propulsion immediately; only a short sideways slip survives a turn. */
			void steer(Vector keys, double throttle, boolean coast){
				if(thrustPending){
					thrustPending = false;
					applyJet();
					return;
				}
				if(keys.lengthSquared() <= 1e-6){
					if(coast)speed *= SQUID_COAST;
				}else{
					setMomentum(SquidMotion.steer(heading.clone().multiply(speed), keys, SQUID_ACCELERATION * throttle, SQUID_TOP_SPEED));
				}
				if(turboJetTick < SquidMotion.JET_DURATION_TICKS)applyJet();
				else if(speed > SQUID_TOP_SPEED)speed = Math.max(SQUID_TOP_SPEED, speed * SQUID_OVERSPEED_KEPT);
				speed = Math.max(0, speed);
			}
			void applyJet(){
				if(turboJetTick >= SquidMotion.JET_DURATION_TICKS){turboJetStrength = 0; return;}
				thrustPending = false;
				Vector direction = heading.clone();
				if(surface == Surface.AIR)direction.multiply(Math.sqrt(Math.max(0, 1 - turboJetPitch * turboJetPitch))).setY(turboJetPitch);
				Contact obstruction = sweep(centre, direction.clone().multiply(0.05));
				if(obstruction != null && obstruction.gap() < 0.005){
					turboJetTick = SquidMotion.JET_DURATION_TICKS;
					turboJetStrength = 0;
					return;
				}
				turboJetStrength = SquidMotion.jetEnvelope(turboJetTick);
				Vector accelerated = SquidMotion.jetStep(velocity(), direction, turboJetTick++, SQUID_THRUST_CEILING);
				if(surface == Surface.AIR){
					verticalSpeed = accelerated.getY();
					setMomentum(accelerated.setY(0));
				}else setMomentum(accelerated);
			}
			void setMomentum(Vector velocity){
				speed = Math.min(SQUID_THRUST_CEILING, velocity.length());
				if(speed > 1e-4)heading = velocity.clone().normalize();
			}
			BlockFace sideOf(Vector direction){
				if(Math.abs(direction.getX()) < 0.05 && Math.abs(direction.getZ()) < 0.05)return null;
				if(Math.abs(direction.getX()) > Math.abs(direction.getZ()))return direction.getX() > 0 ? BlockFace.EAST : BlockFace.WEST;
				return direction.getZ() > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
			}
		}
	}
	enum Surface { FLOOR, WALL, CEILING, AIR }
	@Override
	protected void onPlayerToggleSneak(PlayerToggleSneakEvent evt, Player p) {
		super.onPlayerToggleSneak(evt, p);
		if(!evt.isSneaking() || !JocIniciat || JocFinalitzat)return;
		if(!getPlayers().contains(p))return;
		getPlayerInfo(p).toggleSwimForm();
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		super.onPlayerDeath(evt, killed);
		getPlayerInfo(killed).registerDeath();
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		super.onPlayerDeathByPlayer(evt, killed, killer);
		InkKit killerKit = getPlayerInfo(killer).getKit();
		if (killerKit == null) return;
		// The splat: a kill is painted where the body fell
		killerKit.splashDown(killed.getLocation(), 3 + Math.sqrt(killerKit.level()), 2);
		getWorld().playSound(killed.getLocation(), Sound.ENTITY_SLIME_DEATH, 1F, 0.8F);
		getWorld().playSound(killed.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 1F, 0.9F);
	}
	class EquipInkWars extends Equip{ //Special team class for this game mode
		private DyeColor strongColor; 
		private int ownedBlocks = 0;
		public EquipInkWars(DyeColor color, DyeColor strongColor, String adj) {
			super(color, adj);
			this.strongColor = strongColor;
		}
		public DyeColor getStrongColor() {
			return strongColor;
		}
		public void setStrongColor(DyeColor strongColor) {
			this.strongColor = strongColor;
		}
		public int getOwnedBlocks() {
			return ownedBlocks;
		}
		public void setOwnedBlocks(int ownedBlocks) {
			this.ownedBlocks = ownedBlocks;
		}
		public void incrementOwnedBlocks(int increase){
			setOwnedBlocks(Math.max(0, getOwnedBlocks() + increase));
		}
		/** Share of the map's paintable floor, or of what has been painted when the floor could not be counted. */
		public double getOwnedPercent(){
			int total = paintableFloorBlocks > 0 ? paintableFloorBlocks : getTotalPaintedBlocks();
			if(total == 0)return 0;
			return Math.min(100, ((double)getOwnedBlocks() / total) * 100);
		}
	}
}
