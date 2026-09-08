package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
import org.bukkit.util.Vector;

import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.inkwars.InkSplash;
import com.biel.lobby.mapes.jocs.inkwars.InkStream;
import com.biel.lobby.mapes.jocs.inkwars.InkSurfaceFlow;
import com.biel.lobby.mapes.jocs.inkwars.WetInk;
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
	static final double SQUID_TOP_SPEED = 0.85;
	static final double SQUID_ACCELERATION = 0.03;
	static final double SQUID_BRAKE = 0.03;
	static final double SQUID_COAST = 0.992;
	static final double SQUID_TURN_RATE = Math.toRadians(3.5);
	static final double SQUID_CRAWL_SPEED = 0.06;
	/** Gravity in this world, vanilla is 0.08: jumps go higher, falls and leaps take longer, and nothing here hurts on landing. */
	static final double INK_GRAVITY = 0.05;
	/**
	 * The squid's ink reserve, 0 to 1: refilled per tick on its own colour, more the faster it swims; drained on neutral ground by a share per tick plus a share
	 * per block swum, faster on enemy ink; at zero the squid is forced out. The ground is judged by what it was before this dive's own strip, so the strip feeds nobody.
	 * A full reserve buys about seventeen blocks of neutral ground at full speed, ten of enemy ink; twenty blocks of own ink refill it.
	 */
	static final double RESERVE_REFILL = 0.012;
	static final double RESERVE_DRAIN_NEUTRAL = 0.02;
	static final double RESERVE_DRAIN_NEUTRAL_PER_BLOCK = 0.035;
	static final double RESERVE_DRAIN_ENEMY = 0.03;
	static final double RESERVE_DRAIN_ENEMY_PER_BLOCK = 0.06;
	/** Ink sacs shown in the squid's hand for a full reserve. */
	static final int INK_SACS_FOR_FULL_RESERVE = 32;
	/** Looking within this angle of straight away from a wall lets go of it into a jump. */
	static final double DETACH_COS = Math.cos(Math.toRadians(30));
	/**
	 * The squid feels a surface before its body touches it: the probe along the heading reaches this far beyond the next tick's travel,
	 * and the attachment carries the body the rest of the way in one push, so a wall is never a collision, only a bend.
	 */
	static final double PROBE_MARGIN = 0.2;
	/** The Roller's head sits this far ahead of the feet; the stroke is laid there. */
	static final double ROLLER_AHEAD = 1.0;
	/**
	 * The Hose runs whenever it is in hand: a jet of ink parcels thrown at this speed in blocks per tick, this many a tick, scattered this much at the nozzle,
	 * each carrying this much ink into a splash of this radius where it lands, stinging a body it hits this much.
	 * Pinching the tip (a left click, another lets go) is the second row: faster, a line instead of a spray, one parcel a tick into a smaller spot, a harder sting:
	 * about twice the reach for half the paint.
	 */
	static final double HOSE_SPEED = 0.9, HOSE_SCATTER = 0.035, HOSE_PARCEL_INK = 0.22, HOSE_SPLASH_RADIUS = 0.8, HOSE_STING = 1.0;
	static final int HOSE_PARCELS_PER_TICK = 2;
	static final double PINCHED_SPEED = 1.4, PINCHED_SCATTER = 0.008, PINCHED_PARCEL_INK = 0.3, PINCHED_SPLASH_RADIUS = 0.5, PINCHED_STING = 1.5;
	static final int PINCHED_PARCELS_PER_TICK = 1;
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
		i.add("One kit: the Roller paints the floor ahead of you while you walk with it, the Hose in hand throws a jet of ink that flies in an arc, left-click pinches the tip for a longer thinner shot, ink balls splash at range");
		i.add("Press sneak once for squid form: invisible, fast, healing, with momentum; press again to stand up");
		i.add("The squid lives on ink (the green bar): it refills on your colour, drains on neutral ground, faster on enemy ink, and at zero you are thrown back on your feet; the strip you lay as you go does not count as yours until you stand up");
		i.add("A squid runs up any wall it hits and round corners as if the floor continued; on a wall you go where you look, look straight out to jump off");
		i.add("Swimming fast charges a surge (the meter): surfacing or landing releases it as a splash that hurts");
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
		int r = 150;
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
		/** The tip squeezed: a longer, thinner, harder jet. */
		private boolean pinched = false;
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
			return Utils.setItemNameAndLore(new ItemStack(Material.TORCH, 1), teamColour() + "Hose", ChatColor.WHITE + "In hand it runs: a jet of ink that flies in an arc and lands where it lands.", ChatColor.WHITE + "Left-click: pinch the tip for a longer, thinner shot; again to let go.");
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
			if(getPlayer().getInventory().getItemInMainHand().getType() == Material.TORCH)sprayHose();
			reloadTick();
			if(wetInkTicks % 20 == 0)restoreTools();
		}
		/** The hose is invisible, only its jet shows: the nozzle sits at the hand, a little right of and below the eyes, and throws along the look. */
		void sprayHose(){
			Player p = getPlayer();
			Location eyes = p.getEyeLocation();
			Vector look = eyes.getDirection();
			Vector right = new Vector(-look.getZ(), 0, look.getX());
			if(right.lengthSquared() > 1e-6)right.normalize();
			Location nozzle = eyes.clone().add(look.clone().multiply(0.4)).add(right.multiply(0.25)).add(0, -0.25, 0);
			double levelBonus = level() * 0.02;
			if(pinched){
				hose.emit(nozzle, look, PINCHED_SPEED + levelBonus, PINCHED_SCATTER, new InkStream.Load(PINCHED_PARCEL_INK + levelBonus, PINCHED_SPLASH_RADIUS + level() * 0.03, PINCHED_STING), PINCHED_PARCELS_PER_TICK);
				if(wetInkTicks % 5 == 0)getWorld().playSound(nozzle, Sound.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, 0.5F, 1.9F);
			}else{
				hose.emit(nozzle, look, HOSE_SPEED + levelBonus, HOSE_SCATTER, new InkStream.Load(HOSE_PARCEL_INK + levelBonus, HOSE_SPLASH_RADIUS + level() * 0.05, HOSE_STING), HOSE_PARCELS_PER_TICK);
				if(wetInkTicks % 5 == 0)getWorld().playSound(nozzle, Sound.ENTITY_SLIME_SQUISH, 0.3F, 1.7F);
			}
		}
		/** The tip squeezed or let go. */
		void togglePinch(){
			pinched = !pinched;
			Player p = getPlayer();
			p.playSound(p.getEyeLocation(), Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 0.8F, pinched ? 1.6F : 0.9F);
			PaperMessages.sendActionBar(p, pinched ? teamColour() + "Pinched: long thin jet" : ChatColor.GRAY + "Open: wide jet", 30);
		}
		/** Every parcel in the air flies one tick; the ones that came down splash where they landed, and one that met an enemy stings them. */
		void tickHose(){
			if(hose.isEmpty())return;
			Player shooter = getPlayer();
			Particle.DustOptions drop = new Particle.DustOptions(obtenirEquip(shooter).getStrongColor().getColor(), 1.1F);
			for(InkStream.Landing landing : hose.advance(getWorld(), body -> body instanceof Player hit && hit != shooter && areEnemies(hit, shooter))){
				InkStream.Load load = landing.load();
				if(landing.body() instanceof Player hit){
					hurt(hit, load.sting());
					splashDown(hit.getLocation(), load.splashRadius(), load.ink());
					continue;
				}
				Location impact = landing.where().toLocation(getWorld()).add(landing.surfaceNormal().clone().multiply(0.3));
				splash(impact, landing.velocity(), landing.surfaceNormal(), load.splashRadius(), load.ink());
			}
			int parity = wetInkTicks % 2;
			int index = 0;
			for(Vector position : hose.positions()){
				if(index++ % 2 == parity)getWorld().spawnParticle(Particle.DUST, position.getX(), position.getY(), position.getZ(), 1, 0, 0, 0, 0, drop);
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
			p.getInventory().setItem(ROLLER_SLOT, Utils.setItemNameAndLore(new ItemStack(Material.INK_SAC, sacs), teamColour() + "Ink", ChatColor.WHITE + "Your ink: gathered on your colour, spent elsewhere.", ChatColor.WHITE + "Right-click: blow it around you and stand up."));
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
		}
		@Override
		protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
			super.onPlayerInteract(evt, p);
			if(p != getPlayer() || evt.getHand() != EquipmentSlot.HAND)return;
			Material inHand = p.getInventory().getItemInMainHand().getType();
			boolean rightClick = evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() == Action.RIGHT_CLICK_AIR;
			boolean leftClick = evt.getAction() == Action.LEFT_CLICK_BLOCK || evt.getAction() == Action.LEFT_CLICK_AIR;
			if(rightClick && inHand == Material.INK_SAC && isSubmerged()){
				evt.setCancelled(true);
				getPlayerInfo(p).blowInk();
				return;
			}
			if(inHand != Material.TORCH)return;
			if(rightClick)evt.setCancelled(true); // the torch is the hose, it is never placed
			if(leftClick && !isSubmerged()){
				evt.setCancelled(true);
				togglePinch();
			}
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
			for(Block floor : rollerStrokeBlocks(halfWidth, p, ahead))paintBlock(floor, ink);
		}
		/** The floor blocks a stroke covers, in order across, each once. */
		public ArrayList<Block> rollerStrokeBlocks(double halfWidth, Player p, double ahead){
			ArrayList<Block> stroke = new ArrayList<>();
			Location feet = p.getLocation();
			Vector forward = feet.getDirection().setY(0);
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
			if(!wantsToSwim && squid.submerged)squid.surface(moved);
			if(squid.submerged)squid.tick(team, moved);
			else squid.tickPendingSurge();

			applyInkSpeed(onOwnColour, onEnemyColour);
			tickEnemyInkDamage(onEnemyColour);
		}
		public boolean isSubmerged(){
			return squid.submerged;
		}
		/** The ink sac in hand: the ink goes off around the body and the squid stands up, the same as a second press of sneak. */
		public void blowInk(){
			if(!squid.submerged)return;
			swimForm = false;
			getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_SQUID_SQUIRT, 1F, 0.8F);
		}
		/** One press of sneak switches form: in squid form the body dives wherever it stands and stays a squid as long as its ink lasts; a second press stands it up. */
		public void toggleSwimForm(){
			swimForm = !swimForm;
			Player p = getPlayer();
			if(swimForm){
				p.playSound(p.getLocation(), Sound.ENTITY_SQUID_SQUIRT, 0.7F, 1.4F);
				PaperMessages.sendActionBar(p, obtenirEquip(p).getChatColor() + "Squid form", 30);
			}else{
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_SWIM, 0.6F, 1.2F);
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
		 * The squid: a body that treats floor, walls and corners as one continuous surface and never feels gravity while it touches one.
		 * Its engine owns the speed: a heading and a scalar the client cannot reset, redirected at every change of surface so a 90-degree edge is just a bend in the road.
		 * It lives on an ink reserve, refilled on its own colour and drained elsewhere; while the reserve lasts it stays a squid and lays a strip that thins with the reserve.
		 */
		class Squid {
			boolean submerged = false;
			int submergedTicks = 0;
			/** Where the body is attached: a floor, a wall on one side, or nothing (in the air). */
			Surface surface = Surface.AIR;
			BlockFace wallSide = null;
			int cornerTicks = 0;
			/** Unit vector in the plane of the surface, and the speed along it, blocks per tick. */
			Vector heading = new Vector(1, 0, 0);
			double speed = 0;
			/** What was pushed last tick, so the client's own steering is read against it. */
			Vector pushed = new Vector();
			/** The gap still open between the body and the wall it just attached to: closed by the next push, so the body arrives on the wall the tick it bends up it. */
			double snapIntoWall = 0;
			/** The wall block the body holds, from the last probe toward the wall. */
			Block gripped = null;
			/** What the ground was before this dive's own strip painted it, by block: the reserve judges the ground by this, so the squid cannot live on the ink it lays. */
			final HashMap<Block, EquipInkWars> groundUnderStrip = new HashMap<>();
			/** The ink: gathered swimming on the team's colour, spent swimming elsewhere, and what the surge throws. Zero on every dive. */
			double reserve = 0;
			double pendingSurge = -1;
			Vector flight = new Vector();
			final HashSet<Location> fakeCeiling = new HashSet<>();

			Player player(){
				return getPlayer();
			}
			EquipInkWars team(){
				return obtenirEquip(player());
			}
			/** Diving: the body goes under the ink, the armour and the tools with it, only a ripple and the ink in hand stay. The walking speed is carried into the swim; the ink starts at zero. */
			void dive(Vector moved){
				submerged = true;
				submergedTicks = 0;
				cornerTicks = 0;
				reserve = 0;
				groundUnderStrip.clear();
				surface = Surface.FLOOR;
				wallSide = null;
				setMomentum(new Vector(moved.getX(), 0, moved.getZ()));
				Player p = player();
				p.getInventory().setArmorContents(null);
				kit.showInkSacs(reserve);
				p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 0.8F, 0.7F);
				getWorld().spawnParticle(Particle.SPLASH, p.getLocation().add(0, 0.2, 0), 25, 0.6, 0.1, 0.6, 0);
			}
			/**
			 * Coming out: armour back, and the charge goes off as a surge, here and now on the ground, at the landing point along the flight when in the air.
			 * The velocity is left alone, so letting go keeps the momentum, this time as a player.
			 */
			void surface(Vector moved){
				if(!submerged)return;
				surfaceQuietly();
				Player p = player();
				p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 0.8F, 1.3F);
				getWorld().spawnParticle(Particle.SPLASH, p.getLocation().add(0, 0.2, 0), 25, 0.6, 0.1, 0.6, 0);
				if(p.isOnGround()){
					releaseSurge(reserve, p.getLocation(), new Vector(0, -1, 0));
				}else{
					pendingSurge = reserve;
					flight = moved.clone();
				}
				reserve = 0;
				p.setExp(0);
			}
			void surfaceQuietly(){
				submerged = false;
				surface = Surface.AIR;
				wallSide = null;
				gripped = null;
				snapIntoWall = 0;
				Player p = player();
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
				dropFakeCeiling();
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
				double radius = 1.5 + 2.5 * amount;
				kit.splash(feet.clone().add(0, 0.4, 0), incoming, new Vector(0, 1, 0), radius, 0.7 + 1.5 * amount);
				if(amount < 0.1)return;
				getWorld().playSound(feet, Sound.ENTITY_SLIME_ATTACK, (float) (0.6 + amount), (float) (1.3 - 0.5 * amount));
				getWorld().spawnParticle(Particle.SPLASH, feet.clone().add(0, 0.3, 0), (int) (30 + 80 * amount), radius * 0.4, 0.3, radius * 0.4, 0);
				for(Player enemy : Utils.getNearbyPlayers(feet, radius)){
					if(areEnemies(enemy, p))kit.hurt(enemy, 2 + 4 * amount);
				}
			}

			/** One tick under the ink: the body, the reserve, the strip, then the movement on whatever surface it holds. */
			void tick(EquipInkWars team, Vector moved){
				Player p = player();
				submergedTicks++;
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, true, false));
				p.setFallDistance(0);
				if(submergedTicks % 20 == 0)Utils.healDamageable(p, 1.0);
				if(cornerTicks > 0)cornerTicks--;

				Block touched = touchedBlock();
				EquipInkWars ground = groundColour(touched);
				if(surface != Surface.AIR)tickReserve(team, ground, moved);
				if(reserve <= 0 && ground != team && surface != Surface.AIR){
					forcedOut();
					return;
				}
				ripple(team, moved);
				if(touched != null)layStrip(team, touched);

				switch(surface){
					case FLOOR -> tickFloor(moved);
					case WALL -> tickWall(moved);
					case AIR -> tickAir(moved);
				}
				if(surface == Surface.FLOOR)tickCrawlPose();
				else dropFakeCeiling();
				showMeters();
			}
			/** The block the body rides on: under the feet on a floor, the gripped block on a wall, none in the air. */
			Block touchedBlock(){
				if(surface == Surface.WALL)return gripped;
				if(surface == Surface.FLOOR)return getBlockWherePlayerStands();
				return null;
			}
			/** The colour the reserve judges the ground by: what was there before this dive's own strip, else what is there now. */
			EquipInkWars groundColour(Block touched){
				if(touched != null && groundUnderStrip.containsKey(touched))return groundUnderStrip.get(touched);
				return getTeamOwningBlock(touched);
			}
			/** Gathered per block swum on the team's colour, spent per tick and per block elsewhere: a long run on own ink is what pays for a crossing. */
			void tickReserve(EquipInkWars team, EquipInkWars ground, Vector moved){
				double blocksSwum = moved.length();
				if(ground == team)reserve = Math.min(1, reserve + RESERVE_REFILL * (0.3 + blocksSwum * 4));
				else if(ground == null)reserve -= RESERVE_DRAIN_NEUTRAL + RESERVE_DRAIN_NEUTRAL_PER_BLOCK * blocksSwum;
				else reserve -= RESERVE_DRAIN_ENEMY + RESERVE_DRAIN_ENEMY_PER_BLOCK * blocksSwum;
				reserve = Math.max(0, reserve);
			}
			/** No ink left: the squid is forced back onto its feet with whatever momentum it had, and the surge goes off. */
			void forcedOut(){
				Player p = player();
				swimForm = false;
				p.playSound(p.getLocation(), Sound.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 1F, 0.6F);
				PaperMessages.sendActionBar(p, ChatColor.RED + "Out of ink", 30);
				surface(pushed);
			}
			void ripple(EquipInkWars team, Vector moved){
				if(submergedTicks % 2 != 0)return;
				Particle.DustOptions dust = new Particle.DustOptions(team.getStrongColor().getColor(), 1.4F);
				getWorld().spawnParticle(Particle.DUST, player().getLocation().add(0, 0.15, 0), (int) (3 + moved.length() * 12), 0.45, 0.05, 0.45, 0, dust);
			}
			/**
			 * The strip the squid lays: on its own colour a full re-wet, elsewhere a stroke that thins with the reserve down to the one block under the body.
			 * What the ground was is remembered before the stroke covers it.
			 */
			void layStrip(EquipInkWars team, Block touched){
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
				if(surface == Surface.WALL || halfWidth < 0.9)stroke.add(touched);
				else stroke = kit.rollerStrokeBlocks(halfWidth, p, 0);
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

			//--- surfaces
			/** A block the squid can hold: anything paintable, and the plain solid blocks a splash would paint; never a barrier. */
			boolean gripable(Block b){
				return isPaintable(b) || isPaintableUnsafely(b);
			}
			/** A surface the probe found: the block, the face of it that faces the body, and how far the body's edge is from that face along the probe. */
			record Contact(Block block, BlockFace face, double gap) {}
			/**
			 * Feels along a direction for the first block face within the next tick's travel plus the margin, with rays from the ankles, the middle and the crown of the body.
			 * The reach and the gap are measured from the body's edge in that direction, not from its centre, so the answer is "will the body touch it", whatever the angle.
			 * Null when nothing is that close.
			 */
			Contact probe(Vector direction, double travel){
				if(direction.lengthSquared() < 1e-6)return null;
				Player p = player();
				Vector unit = direction.clone().normalize();
				double height = p.getHeight();
				double[] rayHeights = height <= 0.7 ? new double[]{0.15, height - 0.15} : new double[]{0.15, height / 2, height - 0.15};
				Contact nearest = null;
				for(double rayHeight : rayHeights){
					double extent = 0.3 * (Math.abs(unit.getX()) + Math.abs(unit.getZ())) + (unit.getY() > 0 ? unit.getY() * (height - rayHeight) : -unit.getY() * rayHeight);
					Location origin = p.getLocation().add(0, rayHeight, 0);
					RayTraceResult hit = getWorld().rayTraceBlocks(origin, unit, extent + travel + PROBE_MARGIN, FluidCollisionMode.NEVER, true);
					if(hit == null || hit.getHitBlock() == null || hit.getHitBlockFace() == null)continue;
					double gap = hit.getHitPosition().subtract(origin.toVector()).dot(unit) - extent;
					if(nearest == null || gap < nearest.gap())nearest = new Contact(hit.getHitBlock(), hit.getHitBlockFace(), gap);
				}
				return nearest;
			}
			/** Whether a contact is a wall the squid can hold: a side face of a gripable block. */
			boolean holdableWall(Contact contact){
				return contact != null && contact.face().getModY() == 0 && gripable(contact.block());
			}
			/** The wall the body holds, if it is still there within reach on the gripped side. */
			Contact wallBeside(){
				Contact beside = probe(wallSide.getDirection(), 1.0);
				return holdableWall(beside) && beside.face().getOppositeFace() == wallSide ? beside : null;
			}
			/** Past the end of the gripped face: the wall round the corner, the heading's own side first. */
			Contact wallAround(){
				ArrayList<BlockFace> order = new ArrayList<>();
				BlockFace along = sideOf(heading);
				if(along != null && along != wallSide)order.add(along);
				for(BlockFace side : SIDES)if(side != wallSide && !order.contains(side))order.add(side);
				for(BlockFace side : order){
					Contact around = probe(side.getDirection(), 0.5);
					if(holdableWall(around) && around.face().getOppositeFace() == side)return around;
				}
				return null;
			}
			/**
			 * The universal bend: passing from a surface with one normal onto a surface with another, the part of the heading that pointed into the new surface
			 * is turned to run away from the old one. Floor into wall becomes up, wall down to floor becomes away, a corner either way turns onto the new wall.
			 */
			void redirect(Vector oldNormal, Vector newNormal){
				double into = -heading.dot(newNormal);
				heading.add(newNormal.clone().multiply(into)).add(oldNormal.clone().multiply(into));
				if(heading.lengthSquared() < 1e-6)heading = oldNormal.clone();
				heading.normalize();
			}
			/** Onto a wall at whatever angle: the road bends, the speed is kept, and the gap still open to the wall is closed by the next push. */
			void attachToWall(Contact wall, Vector oldNormal){
				BlockFace side = wall.face().getOppositeFace();
				redirect(oldNormal, side.getDirection().multiply(-1));
				surface = Surface.WALL;
				wallSide = side;
				gripped = wall.block();
				snapIntoWall = Math.max(0, wall.gap());
			}
			void landOnFloor(Vector oldNormal){
				redirect(oldNormal, new Vector(0, 1, 0));
				heading.setY(0);
				if(heading.lengthSquared() < 1e-6)heading = oldNormal.clone().setY(0);
				if(heading.lengthSquared() < 1e-6)heading = new Vector(1, 0, 0);
				heading.normalize();
				surface = Surface.FLOOR;
				wallSide = null;
				gripped = null;
			}

			/**
			 * On a floor the cart is steered by the keys. A wall the body is about to touch, at any angle, bends the road up it with the speed kept:
			 * the probe finds it a tick before the hitbox would, so the body never stops against it. Off an edge the squid is in the air.
			 */
			void tickFloor(Vector moved){
				Player p = player();
				steerByKeys(moved);
				if(speed >= SQUID_CRAWL_SPEED){
					Contact ahead = probe(heading, speed);
					if(holdableWall(ahead)){
						attachToWall(ahead, new Vector(0, 1, 0));
						pushAlongSurface();
						return;
					}
				}
				if(!p.isOnGround() && moved.getY() < -0.05){
					surface = Surface.AIR;
					return;
				}
				double fall = (moved.getY() - INK_GRAVITY) * 0.98;
				pushed = heading.clone().multiply(speed);
				p.setVelocity(new Vector(pushed.getX(), fall, pushed.getZ()));
			}
			/**
			 * On a wall the squid goes where it looks: the look projected onto the wall is the target, the heading turns toward it and the throttle is on.
			 * Looking into the wall brakes; looking straight out lets go into a jump, still a squid. Ahead along the road: another wall is a concave corner and the road
			 * bends onto it, a ceiling ends the climb and the rest of the heading runs along the seam, a floor under a descent bends onto it. When the gripped face ends:
			 * a wall round the corner and the road bends onto it, a climb that runs out of wall goes over the top onto the roof, otherwise the air.
			 */
			void tickWall(Vector moved){
				Player p = player();
				Vector normal = wallSide.getDirection().multiply(-1);
				Vector look = p.getLocation().getDirection();
				if(look.dot(normal) > DETACH_COS){
					detach(normal);
					return;
				}
				Contact ahead = speed > 1e-3 ? probe(heading, speed) : null;
				if(ahead != null){
					if(holdableWall(ahead) && ahead.face().getOppositeFace() != wallSide){
						attachToWall(ahead, normal);
						cornerTicks = CORNER_TICKS;
						pushAlongSurface();
						return;
					}
					if(ahead.face() == BlockFace.UP && heading.getY() < 0){
						landOnFloor(normal);
						pushAlongSurface();
						return;
					}
					if(ahead.face() == BlockFace.DOWN && heading.getY() > 0){
						heading.setY(0);
						if(heading.lengthSquared() < 1e-6)heading = look.clone().subtract(normal.clone().multiply(look.dot(normal))).setY(0);
						if(heading.lengthSquared() < 1e-6){
							heading = normal.clone();
							speed = 0;
						}else{
							heading.normalize();
						}
					}
				}
				Contact beside = wallBeside();
				if(beside == null){
					Contact around = wallAround();
					if(around != null){
						attachToWall(around, normal);
						cornerTicks = CORNER_TICKS;
						pushAlongSurface();
						return;
					}
					if(heading.getY() > 0.2){
						// over the top: onto the roof, heading into the wall's block
						landOnFloor(normal);
						pushed = heading.clone().multiply(Math.max(speed, 0.2));
						p.setVelocity(new Vector(pushed.getX(), 0.3, pushed.getZ()));
						return;
					}
					surface = Surface.AIR;
					wallSide = null;
					gripped = null;
					return;
				}
				gripped = beside.block();
				if(p.isOnGround() && heading.getY() < -0.1){
					landOnFloor(normal);
					pushAlongSurface();
					return;
				}
				Vector target = look.clone().subtract(normal.clone().multiply(look.dot(normal)));
				if(look.dot(normal) < -0.85 || target.lengthSquared() < 0.05){
					speed *= 0.9; // looking into the wall: brake
				}else{
					target.normalize();
					turnHeadingToward(target);
					speed += SQUID_ACCELERATION * (cornerTicks > 0 ? 0.5 : 1);
				}
				speed = Math.max(0, Math.min(SQUID_TOP_SPEED, speed));
				pushAlongSurface();
			}
			/** In the air the squid keeps its form and flies; the first surface it meets becomes its road again, a wall a tick before the body would hit it. */
			void tickAir(Vector moved){
				Player p = player();
				Vector flight = new Vector(moved.getX(), 0, moved.getZ());
				if(flight.lengthSquared() > 0.0025){
					Contact ahead = probe(flight, flight.length());
					if(holdableWall(ahead)){
						setMomentum(flight);
						attachToWall(ahead, new Vector(0, 1, 0));
						pushAlongSurface();
						return;
					}
				}
				if(p.isOnGround()){
					setMomentum(flight);
					surface = Surface.FLOOR;
					wallSide = null;
					gripped = null;
				}
			}
			void detach(Vector normal){
				Player p = player();
				double launch = Math.max(speed, 0.4);
				surface = Surface.AIR;
				wallSide = null;
				gripped = null;
				heading = normal.clone();
				pushed = normal.clone().multiply(launch);
				p.setVelocity(new Vector(pushed.getX(), 0.35, pushed.getZ()));
				p.playSound(p.getLocation(), Sound.ENTITY_SQUID_SQUIRT, 0.8F, 1.1F);
			}
			/**
			 * The velocity for the current surface: along the heading at the speed. On a wall the body is pressed into it a little, plus whatever gap was still open
			 * when it attached, and the vertical part is the heading's, gravity is not consulted.
			 */
			void pushAlongSurface(){
				Player p = player();
				pushed = heading.clone().multiply(speed);
				if(surface == Surface.WALL){
					Vector into = wallSide.getDirection().multiply(0.1 + snapIntoWall);
					snapIntoWall = 0;
					p.setVelocity(new Vector(pushed.getX() + into.getX(), pushed.getY(), pushed.getZ() + into.getZ()));
				}else{
					p.setVelocity(new Vector(pushed.getX(), Math.max(pushed.getY(), 0), pushed.getZ()));
				}
			}

			//--- the cart
			/**
			 * What the client moved beyond what was pushed last tick is the keys; only their direction counts. Along the heading they are throttle or brake,
			 * across it they steer by a fixed angle per tick; resting, the speed coasts down. Below crawling speed the heading snaps to the keys.
			 */
			void steerByKeys(Vector moved){
				Vector observed = new Vector(moved.getX(), 0, moved.getZ());
				Vector keys = observed.clone().subtract(new Vector(pushed.getX(), 0, pushed.getZ()));
				boolean pressing = keys.lengthSquared() > 0.0004;
				if(pressing)keys.normalize();
				Vector flat = new Vector(heading.getX(), 0, heading.getZ());
				if(flat.lengthSquared() < 1e-6)flat = new Vector(1, 0, 0);
				heading = flat.normalize();
				if(!pressing){
					speed *= SQUID_COAST;
				}else if(speed < SQUID_CRAWL_SPEED){
					heading = keys.clone();
					speed += SQUID_ACCELERATION;
				}else{
					double along = keys.dot(heading);
					if(along >= 0)speed += SQUID_ACCELERATION * along * (cornerTicks > 0 ? 0.5 : 1);
					else speed -= SQUID_BRAKE * -along;
					turnHeadingToward(keys);
				}
				speed = Math.max(0, Math.min(SQUID_TOP_SPEED, speed));
			}
			/** Turns the heading toward a unit target by at most the turn rate, in whatever plane the two span. */
			void turnHeadingToward(Vector target){
				double dot = Math.max(-1, Math.min(1, heading.dot(target)));
				double wanted = Math.acos(dot);
				if(wanted <= SQUID_TURN_RATE){
					heading = target.clone();
					return;
				}
				Vector perpendicular = target.clone().subtract(heading.clone().multiply(dot));
				if(perpendicular.lengthSquared() < 1e-9)return; // straight behind: no side to turn to yet
				perpendicular.normalize();
				heading = heading.clone().multiply(Math.cos(SQUID_TURN_RATE)).add(perpendicular.multiply(Math.sin(SQUID_TURN_RATE))).normalize();
			}
			void setMomentum(Vector velocity){
				pushed = velocity.clone();
				speed = Math.min(SQUID_TOP_SPEED, velocity.length());
				if(speed > 1e-4)heading = velocity.clone().normalize();
			}
			BlockFace sideOf(Vector direction){
				if(Math.abs(direction.getX()) < 0.05 && Math.abs(direction.getZ()) < 0.05)return null;
				if(Math.abs(direction.getX()) > Math.abs(direction.getZ()))return direction.getX() > 0 ? BlockFace.EAST : BlockFace.WEST;
				return direction.getZ() > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
			}

			//--- the pose
			/**
			 * The crawl pose is the squid: the client is shown barriers at head height in a 3x3 patch around it, so it cannot stand and drops to the swimming pose,
			 * camera and hitbox at squid height. Only this client sees them, and nobody else needs to since the body is invisible. Looking up clears them so a jump is always possible.
			 */
			void tickCrawlPose(){
				Player p = player();
				if(p.getLocation().getPitch() <= -30){
					dropFakeCeiling();
					return;
				}
				Block head = p.getLocation().getBlock().getRelative(BlockFace.UP);
				HashSet<Location> wanted = new HashSet<>();
				for(int dx = -1; dx <= 1; dx++){
					for(int dz = -1; dz <= 1; dz++){
						Block cell = head.getRelative(dx, 0, dz);
						if(cell.isPassable())wanted.add(cell.getLocation());
					}
				}
				for(Location gone : new ArrayList<>(fakeCeiling)){
					if(wanted.contains(gone))continue;
					p.sendBlockChange(gone, gone.getBlock().getBlockData());
					fakeCeiling.remove(gone);
				}
				for(Location fresh : wanted){
					if(fakeCeiling.add(fresh))p.sendBlockChange(fresh, Material.BARRIER.createBlockData());
				}
			}
			void dropFakeCeiling(){
				if(fakeCeiling.isEmpty())return;
				Player p = player();
				for(Location fake : fakeCeiling){
					if(p != null && fake.getWorld() == p.getWorld())p.sendBlockChange(fake, fake.getBlock().getBlockData());
				}
				fakeCeiling.clear();
			}
		}
	}
	enum Surface { FLOOR, WALL, AIR }
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
