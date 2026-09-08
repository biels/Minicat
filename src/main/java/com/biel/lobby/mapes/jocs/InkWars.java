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
import org.bukkit.util.BlockIterator;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.inkwars.InkSplash;
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
	/** The squid's ink reserve, 0 to 1: refilled per tick on its own colour, drained per tick on neutral ground and faster on enemy ink; at zero the squid is forced out. */
	static final double RESERVE_REFILL = 0.05;
	static final double RESERVE_DRAIN_NEUTRAL = 0.012;
	static final double RESERVE_DRAIN_ENEMY = 0.02;
	/** Ink charge gained per block swum; a full charge is one surge. */
	static final double CHARGE_PER_BLOCK = 0.09;
	/** Looking within this angle of straight away from a wall lets go of it into a jump. */
	static final double DETACH_COS = Math.cos(Math.toRadians(30));
	static final double BRUSH_REACH = 4.5;
	final Predicate<Block> solid = block -> !block.isPassable();
	int wetInkTicks = 0;
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
		i.add("One kit: the Roller paints the floor while you walk with it, the Brush paints the wall you look at (hold right-click), ink balls splash at range");
		i.add("Press sneak once for squid form: invisible, fast, healing, with momentum; press again to stand up");
		i.add("The squid lives on ink (the green bar): it refills on your colour, drains on neutral ground, faster on enemy ink, and at zero you are thrown back on your feet");
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
			for(Equip e : Equips){
				try {
					list.add(e.getAdjectiuColored());
					EquipInkWars eq = (EquipInkWars)e;
					values.add((int) Math.round(eq.getOwnedPercent()));
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
				//winAction(w);
				winGame(w);
			}
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
		Material paintBase = forcedly ? Material.WHITE_TERRACOTTA : b.getType();
		Material painted = getPaintMaterial(paintBase, team.getStrongColor());
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
		if(!isPaintable(b))return null;
		DyeColor blockColor = getPaintColor(b.getType());
		for(Equip e : Equips){
			EquipInkWars eq = (EquipInkWars) e;
			if(eq.getColor() == blockColor || eq.getStrongColor() == blockColor)return eq;
		}
		return null;
	}
	public boolean isPaintable(Block b){
		return getPaintColor(b.getType()) != null;
	}
	private DyeColor getPaintColor(Material material){
		String materialName = material.name();
		for (DyeColor color : DyeColor.values()) {
			for (String suffix : new String[]{"_WOOL", "_TERRACOTTA", "_STAINED_GLASS", "_STAINED_GLASS_PANE"}) {
				if (materialName.equals(color.name() + suffix)) return color;
			}
		}
		return null;
	}
	private Material getPaintMaterial(Material currentMaterial, DyeColor color){
		String materialName = currentMaterial.name();
		for (String suffix : new String[]{"_WOOL", "_TERRACOTTA", "_STAINED_GLASS", "_STAINED_GLASS_PANE"}) {
			if (materialName.endsWith(suffix)) return Material.valueOf(color.name() + suffix);
		}
		return Material.valueOf(color.name() + "_TERRACOTTA");
	}
	public boolean isPaintableUnsafely(Block b){ 
		Material t = b.getType();
		return (t.isBlock() && t.isOccluding() && t != Material.BARRIER) && !isPaintable(b);
	}
	/**
	 * The one kit everybody carries. The held item decides what paints: the Roller stick paints the floor under a walking player,
	 * the Brush torch paints the wall in the crosshair while right-click is held, ink balls splash where they land and hurt around the impact.
	 * Melee does nothing here; ink kills.
	 */
	class InkKit extends PlayerWorldEventBus{
		static final int ROLLER_SLOT = 0;
		static final int BRUSH_SLOT = 1;
		private int reloadTicks = 0;
		private int brushCooldownTicks = 0;
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
		ItemStack brushItem(){
			return Utils.setItemNameAndLore(new ItemStack(Material.TORCH, 1), teamColour() + "Brush", ChatColor.WHITE + "Hold right-click: paints the wall you look at, four blocks away at most.");
		}
		ItemStack inkBallItem(){
			return Utils.setItemName(new ItemStack(Material.SNOWBALL, 1), teamColour() + "Ink ball");
		}
		/** The tools into fixed hotbar slots, the Roller in hand. */
		public void give(){
			Player p = getPlayer();
			p.getInventory().setItem(ROLLER_SLOT, rollerItem());
			p.getInventory().setItem(BRUSH_SLOT, brushItem());
			p.getInventory().setHeldItemSlot(ROLLER_SLOT);
			p.playSound(p.getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 1F, 1F);
			p.playSound(p.getEyeLocation(), Sound.BLOCK_PISTON_EXTEND, 1F, 1F);
		}
		public void tick(){
			if(brushCooldownTicks > 0)brushCooldownTicks--;
			reloadTick();
			if(wetInkTicks % 20 == 0)restoreTools();
		}
		/** A dropped or lost tool comes back to its slot: the kit is the player, not loot. */
		void restoreTools(){
			Player p = getPlayer();
			if(!p.getInventory().contains(Material.STICK))p.getInventory().setItem(ROLLER_SLOT, rollerItem());
			if(!p.getInventory().contains(Material.TORCH))p.getInventory().setItem(BRUSH_SLOT, brushItem());
		}
		public int maxInkBalls(){
			return 6 + Math.round(level() / 2f);
		}
		public int neededReloadTicks(){
			return 50 - level() * 2;
		}
		/** Reloading runs five times faster on the team's colour or in the base, eight times under the ink. */
		public int reloadTickIncrement(){
			InkWarsPlayerInfo info = getPlayerInfo(getPlayer());
			if(info.isSubmerged())return 8;
			EquipInkWars e = obtenirEquip(getPlayer());
			boolean onOwnColour = info.getTeamColorWherePlayerStands() == e;
			boolean inBase = getPlayer().getLocation().distance(e.getTeamSpawnLocation()) < 10;
			return onOwnColour || inBase ? 5 : 1;
		}
		public void reloadTick(){
			if(getPlayer().getInventory().contains(Material.SNOWBALL, maxInkBalls()))return;
			if(reloadTicks >= neededReloadTicks()){
				Utils.giveItemStack(inkBallItem(), getPlayer());
				getPlayer().updateInventory();
				getPlayer().playSound(getPlayer().getEyeLocation(), Sound.ENTITY_ITEM_PICKUP, 0.4F, 1F);
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
			rollerLinePaint(1 + Math.sqrt(level()), 0.4 + level() / 24.0, p);
		}
		@Override
		protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
			super.onPlayerInteract(evt, p);
			if(p != getPlayer() || evt.getHand() != EquipmentSlot.HAND)return;
			if(evt.getAction() != Action.RIGHT_CLICK_BLOCK && evt.getAction() != Action.RIGHT_CLICK_AIR)return;
			if(p.getInventory().getItemInMainHand().getType() != Material.TORCH)return;
			evt.setCancelled(true); // the torch is a brush, it is never placed
			if(brushCooldownTicks > 0 || isSubmerged())return;
			RayTraceResult sight = p.rayTraceBlocks(BRUSH_REACH);
			if(sight == null || sight.getHitBlock() == null)return;
			Vector normal = sight.getHitBlockFace() == null ? new Vector(0, 1, 0) : sight.getHitBlockFace().getDirection();
			Location impact = sight.getHitPosition().toLocation(getWorld()).add(normal.clone().multiply(0.3));
			splash(impact, p.getEyeLocation().getDirection(), normal, 1.1 + level() * 0.1, 0.9);
			getWorld().playSound(impact, Sound.ENTITY_SLIME_SQUISH, 0.5F, 1.5F);
			brushCooldownTicks = 3;
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(damager == getPlayer() && !ranged)evt.setCancelled(true);
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
					p.damage(splashDamage, getPlayer());
				}
			}
		}
		//Painting methods
		public void rollerLinePaint(double width, double ink, Player p){
			Vector normal = new Vector(0, 1, 0);
			Vector forward = p.getLocation().getDirection();
			Vector paintDir = normal.crossProduct(forward).normalize().multiply(width);
			Vector startLoc = p.getLocation().toVector().subtract(paintDir);
			BlockIterator i = new BlockIterator(getWorld(), startLoc, paintDir, -1, (int) Math.round(2 * width));
			for (;i.hasNext();) {
				Block b = i.next();
				if (Utils.pointToLineDistance(startLoc, paintDir, b.getLocation().toVector()) < 0.8) {
					paintBlock(b, ink);
				}
			}
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

			boolean wantsToSwim = swimForm && kit != null && squid.reserve > 0.05;
			if(swimForm && !wantsToSwim && !squid.submerged && squid.reserve <= 0.05)swimForm = false; // no ink to dive with
			if(wantsToSwim && !squid.submerged)squid.dive(moved);
			if(!wantsToSwim && squid.submerged)squid.surface(moved);
			if(squid.submerged){
				squid.tick(team, moved);
			}else{
				squid.tickPendingSurge();
				if(onOwnColour)squid.reserve = Math.min(1, squid.reserve + RESERVE_REFILL);
				p.setExp((float) Math.min(0.999, Math.max(0, squid.reserve)));
			}

			applyInkSpeed(onOwnColour, onEnemyColour);
			tickEnemyInkDamage(onEnemyColour);
		}
		public boolean isSubmerged(){
			return squid.submerged;
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
			double reserve = 1;
			double charge = 0;
			double pendingSurge = -1;
			Vector flight = new Vector();
			final HashSet<Location> fakeCeiling = new HashSet<>();

			Player player(){
				return getPlayer();
			}
			EquipInkWars team(){
				return obtenirEquip(player());
			}
			/** Diving: the body goes under the ink, the armour with it, only a ripple stays visible. The walking speed is carried into the swim; the reserve is whatever was left. */
			void dive(Vector moved){
				submerged = true;
				submergedTicks = 0;
				cornerTicks = 0;
				surface = Surface.FLOOR;
				wallSide = null;
				setMomentum(new Vector(moved.getX(), 0, moved.getZ()));
				Player p = player();
				p.getInventory().setArmorContents(null);
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
					releaseSurge(charge, p.getLocation(), new Vector(0, -1, 0));
				}else{
					pendingSurge = charge;
					flight = moved.clone();
				}
				charge = 0;
			}
			void surfaceQuietly(){
				submerged = false;
				surface = Surface.AIR;
				wallSide = null;
				Player p = player();
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
				dropFakeCeiling();
				Utils.donarItemsPlayer(p, getStartingItems(p));
			}
			void reset(){
				surfaceQuietly();
				charge = 0;
				reserve = 1;
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
					if(areEnemies(enemy, p))enemy.damage(2 + 4 * amount, p);
				}
			}

			/** One tick under the ink: the body, the reserve, the strip, then the movement on whatever surface it holds. */
			void tick(EquipInkWars team, Vector moved){
				Player p = player();
				submergedTicks++;
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, true, false));
				p.setFallDistance(0);
				if(submergedTicks % 20 == 0)Utils.healDamageable(p, 1.0);
				charge = Math.min(1, charge + moved.length() * CHARGE_PER_BLOCK);
				if(cornerTicks > 0)cornerTicks--;

				Block touched = touchedBlock();
				EquipInkWars colourTouched = getTeamOwningBlock(touched);
				if(surface != Surface.AIR)tickReserve(team, colourTouched);
				if(reserve <= 0){
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
				Player p = player();
				if(surface == Surface.WALL && wallSide != null){
					Block beside = p.getLocation().getBlock().getRelative(wallSide);
					return gripable(beside) ? beside : beside.getRelative(BlockFace.UP);
				}
				if(surface == Surface.FLOOR)return getBlockWherePlayerStands();
				return null;
			}
			void tickReserve(EquipInkWars team, EquipInkWars colourTouched){
				if(colourTouched == team)reserve = Math.min(1, reserve + RESERVE_REFILL);
				else if(colourTouched == null)reserve -= RESERVE_DRAIN_NEUTRAL;
				else reserve -= RESERVE_DRAIN_ENEMY;
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
			/** The strip the squid lays: on its own colour a full re-wet, elsewhere a stroke that thins with the reserve down to the one block under the body. */
			void layStrip(EquipInkWars team, Block touched){
				Player p = player();
				if(getTeamOwningBlock(touched) == team){
					rewet(touched, team, p.getName());
					if(surface == Surface.WALL)rewet(touched.getRelative(BlockFace.UP), team, p.getName());
					return;
				}
				if(kit == null)return;
				if(surface == Surface.WALL){
					kit.paintBlock(touched, 0.15 + 0.35 * reserve);
					return;
				}
				double halfWidth = 0.5 + 1.5 * reserve;
				if(halfWidth < 0.9)kit.paintBlock(touched, 0.15 + 0.35 * reserve);
				else kit.rollerLinePaint(halfWidth, 0.15 + 0.35 * reserve, p);
			}
			/** Reserve on the experience bar, surge on the action bar. */
			void showMeters(){
				Player p = player();
				p.setExp((float) Math.min(0.999, Math.max(0, reserve)));
				if(submergedTicks % 4 != 0)return;
				int filled = (int) Math.round(charge * 8);
				p.sendActionBar(PaperMessages.legacy(ChatColor.GRAY + "Surge " + ChatColor.AQUA + "▮".repeat(filled) + ChatColor.DARK_GRAY + "▯".repeat(8 - filled)));
			}

			//--- surfaces
			/** A block the squid can hold: anything paintable, and the plain solid blocks a splash would paint; never a barrier. */
			boolean gripable(Block b){
				return isPaintable(b) || isPaintableUnsafely(b);
			}
			boolean wallAt(Block feet, BlockFace side){
				return gripable(feet.getRelative(side)) || gripable(feet.getRelative(side).getRelative(BlockFace.UP));
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
			Vector normalOf(Surface s, BlockFace side){
				if(s == Surface.FLOOR)return new Vector(0, 1, 0);
				return side.getDirection().multiply(-1);
			}
			void attachToWall(BlockFace side, Vector oldNormal){
				redirect(oldNormal, side.getDirection().multiply(-1));
				surface = Surface.WALL;
				wallSide = side;
			}
			void landOnFloor(Vector oldNormal){
				redirect(oldNormal, new Vector(0, 1, 0));
				heading.setY(0);
				if(heading.lengthSquared() < 1e-6)heading = oldNormal.clone().setY(0);
				if(heading.lengthSquared() < 1e-6)heading = new Vector(1, 0, 0);
				heading.normalize();
				surface = Surface.FLOOR;
				wallSide = null;
			}

			/**
			 * On a floor the cart is steered by the keys. Running into a wall at more than a glancing angle bends the road up it, speed kept;
			 * a glancing wall only takes the part of the heading that pointed into it. Off an edge the squid is in the air.
			 */
			void tickFloor(Vector moved){
				Player p = player();
				steerByKeys(moved);
				Block feet = p.getLocation().getBlock();
				BlockFace ahead = sideOf(heading);
				if(ahead != null && wallAt(feet, ahead)){
					double into = -heading.dot(ahead.getDirection().multiply(-1));
					if(into > 0.5){
						attachToWall(ahead, new Vector(0, 1, 0));
						pushAlongSurface();
						return;
					}
					Vector normal = ahead.getDirection().multiply(-1);
					heading.subtract(normal.multiply(heading.dot(normal)));
					if(heading.lengthSquared() > 1e-6)heading.normalize();
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
			 * Looking into the wall brakes; looking straight out lets go into a jump, still a squid. When the gripped face ends: another face beside means a corner
			 * and the road bends onto it; a climb that runs out of wall goes over the top onto the roof; a descent that meets the floor bends onto it; otherwise the air.
			 */
			void tickWall(Vector moved){
				Player p = player();
				Block feet = p.getLocation().getBlock();
				Vector normal = wallSide.getDirection().multiply(-1);
				Vector look = p.getLocation().getDirection();
				double outward = look.dot(normal);
				if(outward > DETACH_COS){
					detach(normal);
					return;
				}
				if(!wallAt(feet, wallSide)){
					BlockFace next = null;
					BlockFace alongSide = sideOf(heading);
					if(alongSide != null && alongSide != wallSide && wallAt(feet, alongSide))next = alongSide;
					else for(BlockFace side : SIDES)if(side != wallSide && wallAt(feet, side)){ next = side; break; }
					if(next != null){
						Vector oldNormal = normal;
						attachToWall(next, oldNormal);
						cornerTicks = CORNER_TICKS;
						speed *= 0.85;
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
					return;
				}
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
			/** In the air the squid keeps its form and flies; the first surface it meets becomes its road again. */
			void tickAir(Vector moved){
				Player p = player();
				Block feet = p.getLocation().getBlock();
				BlockFace ahead = sideOf(new Vector(moved.getX(), 0, moved.getZ()));
				if(ahead != null && wallAt(feet, ahead) && Math.abs(moved.getX()) + Math.abs(moved.getZ()) > 0.05){
					setMomentum(new Vector(moved.getX(), 0, moved.getZ()));
					attachToWall(ahead, new Vector(0, 1, 0));
					pushAlongSurface();
					return;
				}
				if(p.isOnGround()){
					setMomentum(new Vector(moved.getX(), 0, moved.getZ()));
					surface = Surface.FLOOR;
					wallSide = null;
				}
			}
			void detach(Vector normal){
				Player p = player();
				double launch = Math.max(speed, 0.4);
				surface = Surface.AIR;
				wallSide = null;
				heading = normal.clone();
				pushed = normal.clone().multiply(launch);
				p.setVelocity(new Vector(pushed.getX(), 0.35, pushed.getZ()));
				p.playSound(p.getLocation(), Sound.ENTITY_SQUID_SQUIRT, 0.8F, 1.1F);
			}
			/** The velocity for the current surface: along the heading at the speed, hugging a wall a little; on a wall the vertical part is the heading's, gravity is not consulted. */
			void pushAlongSurface(){
				Player p = player();
				pushed = heading.clone().multiply(speed);
				if(surface == Surface.WALL){
					Vector into = wallSide.getDirection().multiply(0.1);
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
		public double getOwnedPercent(){
			if(getTotalPaintedBlocks() == 0)return 0;
			return ((double)getOwnedBlocks() / getTotalPaintedBlocks()) * 100;
		}
	}
}
