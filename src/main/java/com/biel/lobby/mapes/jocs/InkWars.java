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
	/** Ticks of slower climbing after the grip turns a corner. */
	static final int CORNER_TICKS = 10;
	/**
	 * The squid is a cart: a heading and a speed. Blocks per tick at full speed (a sprint is 0.28); speed gained per tick with the keys along the heading;
	 * speed lost per tick with the keys against it (the brake); the share of speed kept per tick when coasting; how far the heading turns per tick;
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
	static final double CLIMB_SPEED = 0.28;
	/** Ink charge gained per block swum; a full charge is one surge. */
	static final double CHARGE_PER_BLOCK = 0.09;
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
		i.add("Press sneak once for squid form: on your own colour you dive in, invisible, fast, healing, with momentum; press again to stand up");
		i.add("Swimming, face a wall in your colour to climb it; the grip follows corners; swimming fast charges ink, surfacing or landing releases it as a splash");
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
		// The squid
		private boolean swimForm = false;
		private boolean submerged = false;
		private int submergedTicks = 0;
		private BlockFace gripSide = null;
		private BlockFace previousGripSide = null;
		private int cornerTicks = 0;
		/** Horizontal velocity the squid carries, blocks per tick; it is what was pushed last tick, so the client's own steering is read against it. */
		private Vector momentum = new Vector();
		/** The cart's state behind the momentum: where it points and how fast it goes. */
		private Vector heading = new Vector(1, 0, 0);
		private double speed = 0;
		private Location lastLocation = null;
		/** Ink gathered by swimming, 0 to 1; released as a surge when the body comes out. */
		private double charge = 0;
		/** A surge waiting for the landing after the body left the ink in the air; negative when none. */
		private double pendingSurge = -1;
		private Vector flight = new Vector();
		/** The barriers only this client sees at head height around it, forcing the crawl pose while submerged on a floor; a patch, so a step never uncovers the head for a tick. */
		private final HashSet<Location> fakeCeiling = new HashSet<>();

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
			gripSide = swimForm && kit != null ? ownWallBeside(team) : null;

			boolean wantsToSwim = swimForm && kit != null && (onOwnColour || gripSide != null);
			if(wantsToSwim && !submerged)dive(moved);
			if(!wantsToSwim && submerged)surface(moved);
			if(submerged)tickSubmerged(team, moved);
			else tickPendingSurge();
			previousGripSide = submerged ? gripSide : null;

			applyInkSpeed(onOwnColour, onEnemyColour);
			tickEnemyInkDamage(onEnemyColour);
			p.setExp((float) Math.min(0.999, Math.max(0, pendingSurge >= 0 ? pendingSurge : charge)));
		}
		public boolean isSubmerged(){
			return submerged;
		}
		/** One press of sneak switches form: in squid form the body dives wherever it touches its own colour, out of it the player stands whatever the ground. */
		public void toggleSwimForm(){
			swimForm = !swimForm;
			Player p = getPlayer();
			if(swimForm){
				p.playSound(p.getLocation(), Sound.ENTITY_SQUID_SQUIRT, 0.7F, 1.4F);
				PaperMessages.sendActionBar(p, obtenirEquip(p).getChatColor() + "Squid form" + ChatColor.GRAY + ": you dive on your own colour", 40);
			}else{
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_SWIM, 0.6F, 1.2F);
				PaperMessages.sendActionBar(p, ChatColor.GRAY + "On your feet", 30);
			}
		}
		/** Diving: the body goes under the ink, the armour with it, only a ripple stays visible. The walking speed is carried into the swim. */
		private void dive(Vector moved){
			submerged = true;
			submergedTicks = 0;
			cornerTicks = 0;
			setMomentum(new Vector(moved.getX(), 0, moved.getZ()));
			Player p = getPlayer();
			p.getInventory().setArmorContents(null);
			p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 0.8F, 0.7F);
			getWorld().spawnParticle(Particle.SPLASH, p.getLocation().add(0, 0.2, 0), 25, 0.6, 0.1, 0.6, 0);
		}
		/**
		 * Coming out of the ink: armour back, and the charge goes off as a surge. On the ground it splashes here and now; in the air it waits for the landing
		 * and splashes along the flight. The velocity is left alone, so letting go of a wall or flying off an edge keeps the momentum.
		 */
		private void surface(Vector moved){
			if(!submerged)return;
			surfaceQuietly();
			Player p = getPlayer();
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
		private void tickPendingSurge(){
			if(pendingSurge < 0)return;
			Player p = getPlayer();
			if(!p.isOnGround())return;
			Vector incoming = flight.lengthSquared() < 1e-4 ? new Vector(0, -1, 0) : flight;
			releaseSurge(pendingSurge, p.getLocation(), incoming);
			pendingSurge = -1;
		}
		/** The surge: the ink gathered while swimming, thrown around the body as it comes out. A full charge is a grenade: a wide splash and a bite on enemies near it. */
		private void releaseSurge(double amount, Location feet, Vector incoming){
			if(kit == null)return;
			Player p = getPlayer();
			double radius = 1.5 + 2.5 * amount;
			kit.splash(feet.clone().add(0, 0.4, 0), incoming, new Vector(0, 1, 0), radius, 0.7 + 1.5 * amount);
			if(amount < 0.1)return;
			getWorld().playSound(feet, Sound.ENTITY_SLIME_ATTACK, (float) (0.6 + amount), (float) (1.3 - 0.5 * amount));
			getWorld().spawnParticle(Particle.SPLASH, feet.clone().add(0, 0.3, 0), (int) (30 + 80 * amount), radius * 0.4, 0.3, radius * 0.4, 0);
			for(Player enemy : Utils.getNearbyPlayers(feet, radius)){
				if(areEnemies(enemy, p))enemy.damage(2 + 4 * amount, p);
			}
		}
		/** The flag, the pose and the armour only, for a death or a kit change that re-issues the kit anyway. */
		private void surfaceQuietly(){
			submerged = false;
			gripSide = null;
			previousGripSide = null;
			Player p = getPlayer();
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			dropFakeCeiling();
			Utils.donarItemsPlayer(p, getStartingItems(p));
		}
		/**
		 * The side with an own-colour wall against the body, at feet or head height. A climb starts only on the side the player faces;
		 * once gripped, the grip follows the wall around corners: the facing side first, then the side held last, then any side.
		 * On the ground, looking away from the wall lets go.
		 */
		private BlockFace ownWallBeside(EquipInkWars team){
			Player p = getPlayer();
			Block feet = p.getLocation().getBlock();
			BlockFace facing = facingSide();
			if(facing != null && isOwnWall(feet, facing, team))return facing;
			if(gripSide == null || p.isOnGround())return null;
			if(isOwnWall(feet, gripSide, team))return gripSide;
			for(BlockFace side : SIDES)if(isOwnWall(feet, side, team))return side;
			return null;
		}
		private boolean isOwnWall(Block feet, BlockFace side, EquipInkWars team){
			Block beside = feet.getRelative(side);
			return getTeamOwningBlock(beside) == team || getTeamOwningBlock(beside.getRelative(BlockFace.UP)) == team;
		}
		private BlockFace facingSide(){
			return sideOf(getPlayer().getLocation().getDirection());
		}
		/** Submerged: unseen, healing, a ripple in the team colour that thickens with speed, every block the body touches wet again, ink charging with the distance swum. */
		private void tickSubmerged(EquipInkWars team, Vector moved){
			Player p = getPlayer();
			submergedTicks++;
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, true, false));
			p.setFallDistance(0);
			if(submergedTicks % 20 == 0)Utils.healDamageable(p, 1.0);
			double speed = Math.sqrt(moved.getX() * moved.getX() + moved.getZ() * moved.getZ());
			charge = Math.min(1, charge + speed * CHARGE_PER_BLOCK);
			if(submergedTicks % 2 == 0){
				Particle.DustOptions ripple = new Particle.DustOptions(team.getStrongColor().getColor(), 1.4F);
				getWorld().spawnParticle(Particle.DUST, p.getLocation().add(0, 0.15, 0), (int) (3 + speed * 12), 0.45, 0.05, 0.45, 0, ripple);
			}
			Block floor = getBlockWherePlayerStands();
			if(floor != null)rewet(floor, team, p.getName());
			if(cornerTicks > 0)cornerTicks--;
			if(gripSide != null){
				tickWallGrip(team, moved);
				dropFakeCeiling();
			}else if(previousGripSide != null && !p.isOnGround()){
				// the wall ended under the climb: the momentum carries the body over the edge onto the top
				Vector over = previousGripSide.getDirection();
				setMomentum(new Vector(over.getX() * 0.3, 0, over.getZ() * 0.3));
				p.setVelocity(new Vector(momentum.getX(), 0.2, momentum.getZ()));
				dropFakeCeiling();
			}else{
				tickFloorSwim(moved);
				tickCrawlPose();
			}
		}
		/**
		 * The squid on a floor runs on the cart model. The vertical velocity is what the client will do on its own next tick, read from what it just did,
		 * so a jump is never overwritten by the server's stale idea of it. A wall that stops the body turns it back into a player and throws it off the wall.
		 */
		private void tickFloorSwim(Vector moved){
			Player p = getPlayer();
			double observedSpeed = Math.sqrt(moved.getX() * moved.getX() + moved.getZ() * moved.getZ());
			if(speed > 0.15 && observedSpeed < speed * 0.3 && deflectOffWall())return;
			steer(moved);
			double fall = (moved.getY() - INK_GRAVITY) * 0.98;
			p.setVelocity(new Vector(momentum.getX(), fall, momentum.getZ()));
		}
		/**
		 * The wall the heading ran into, if there is one: the body comes out of the ink as a player and keeps its speed along the wall while the part that went into
		 * the wall comes back out of it, damped, along the wall's normal. Returns false when nothing solid is there to bounce off.
		 */
		private boolean deflectOffWall(){
			Player p = getPlayer();
			BlockFace side = sideOf(heading);
			if(side == null)return false;
			Block feet = p.getLocation().getBlock();
			if(!solid.test(feet.getRelative(side)) && !solid.test(feet.getRelative(side).getRelative(BlockFace.UP)))return false;
			Vector normal = side.getDirection().multiply(-1);
			double intoWall = momentum.dot(normal); // negative: the momentum pointed into the wall
			Vector alongWall = momentum.clone().subtract(normal.clone().multiply(intoWall));
			Vector thrown = alongWall.multiply(0.9).add(normal.clone().multiply(-intoWall * 0.6));
			swimForm = false;
			p.setVelocity(new Vector(thrown.getX(), 0.15, thrown.getZ()));
			setMomentum(new Vector());
			p.playSound(p.getLocation(), Sound.BLOCK_SLIME_BLOCK_HIT, 1F, 0.8F);
			PaperMessages.sendActionBar(p, ChatColor.GRAY + "Off the wall", 20);
			return true;
		}
		private BlockFace sideOf(Vector direction){
			if(Math.abs(direction.getX()) < 0.05 && Math.abs(direction.getZ()) < 0.05)return null;
			if(Math.abs(direction.getX()) > Math.abs(direction.getZ()))return direction.getX() > 0 ? BlockFace.EAST : BlockFace.WEST;
			return direction.getZ() > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
		}
		/**
		 * The cart. What the client moved beyond the momentum pushed last tick is the keys; only their direction counts.
		 * The part of the keys along the heading is throttle or brake: speed grows slowly with them, drops faster against them, and coasts down when they rest.
		 * The part across the heading is steering: the heading turns toward the keys by a fixed angle per tick, so a turn takes room and a U-turn takes two seconds.
		 * Below crawling speed the heading snaps to the keys. A wall that stops the body takes most of its speed.
		 */
		private void steer(Vector moved){
			Vector observed = new Vector(moved.getX(), 0, moved.getZ());
			double observedSpeed = observed.length();
			Vector keys = observed.clone().subtract(momentum);
			boolean pressing = keys.lengthSquared() > 0.0004;
			if(pressing)keys.normalize();

			if(speed > 0.1 && observedSpeed < speed * 0.3)speed *= 0.6; // stopped by something that is not a wall
			if(!pressing){
				speed *= SQUID_COAST;
			}else if(speed < SQUID_CRAWL_SPEED){
				heading = keys.clone();
				speed += SQUID_ACCELERATION;
			}else{
				double along = keys.dot(heading);
				if(along >= 0)speed += SQUID_ACCELERATION * along;
				else speed -= SQUID_BRAKE * -along;
				turnHeadingToward(keys);
			}
			speed = Math.max(0, Math.min(SQUID_TOP_SPEED, speed));
			momentum = heading.clone().multiply(speed);
		}
		private void turnHeadingToward(Vector keys){
			double cross = heading.getX() * keys.getZ() - heading.getZ() * keys.getX();
			double dot = heading.dot(keys);
			double wanted = Math.atan2(cross, dot);
			double turn = Math.max(-SQUID_TURN_RATE, Math.min(SQUID_TURN_RATE, wanted));
			double cos = Math.cos(turn), sin = Math.sin(turn);
			heading = new Vector(heading.getX() * cos - heading.getZ() * sin, 0, heading.getX() * sin + heading.getZ() * cos).normalize();
		}
		/** Sets the momentum and the cart state behind it in one go. */
		private void setMomentum(Vector newMomentum){
			momentum = newMomentum.clone();
			speed = Math.min(SQUID_TOP_SPEED, momentum.length());
			if(speed > 1e-4)heading = momentum.clone().normalize();
		}
		/**
		 * Gripping a wall the body climbs at full speed while its momentum runs along the wall. When the grip turns a corner the momentum is directed onto the new wall,
		 * the part that was heading into it is dropped and the rest loses a little, and the climb is slower for half a second.
		 */
		private void tickWallGrip(EquipInkWars team, Vector moved){
			Player p = getPlayer();
			Vector into = gripSide.getDirection();
			if(previousGripSide != null && previousGripSide != gripSide){
				cornerTicks = CORNER_TICKS;
				setMomentum(momentum.clone().subtract(into.clone().multiply(momentum.dot(into))).multiply(0.85));
			}
			Block feet = p.getLocation().getBlock();
			rewet(feet.getRelative(gripSide), team, p.getName());
			rewet(feet.getRelative(gripSide).getRelative(BlockFace.UP), team, p.getName());
			steer(moved);
			setMomentum(momentum.clone().subtract(into.clone().multiply(momentum.dot(into)))); // along the wall only
			double climb = cornerTicks > 0 ? CLIMB_SPEED * 0.55 : CLIMB_SPEED;
			p.setVelocity(new Vector(momentum.getX() + into.getX() * 0.1, climb, momentum.getZ() + into.getZ() * 0.1));
		}
		/**
		 * The crawl pose is the squid: the client is shown a barrier in its head block, so it cannot stand and drops to the swimming pose, camera and hitbox at squid height.
		 * Only this client sees it, and nobody else needs to since the body is invisible. Looking up more than thirty degrees clears it, so a jump out of the ink is always possible.
		 */
		private void tickCrawlPose(){
			Player p = getPlayer();
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
		private void dropFakeCeiling(){
			if(fakeCeiling.isEmpty())return;
			Player p = getPlayer();
			for(Location fake : fakeCeiling){
				if(p != null && fake.getWorld() == p.getWorld())p.sendBlockChange(fake, fake.getBlock().getBlockData());
			}
			fakeCeiling.clear();
		}
		/** Own colour is fast, enemy colour is mud. Under the ink the squid carries itself, no potion. Refreshed every tick so they vanish the moment the ground changes. */
		private void applyInkSpeed(boolean onOwnColour, boolean onEnemyColour){
			Player p = getPlayer();
			int speedLevels = onOwnColour && !submerged ? 1 : 0;
			int slownessLevels = onEnemyColour && !submerged ? 2 : 0;
			if(speedLevels > 0)p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, speedLevels - 1, true, false));
			else p.removePotionEffect(PotionEffectType.SPEED);
			if(slownessLevels > 0)p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 5, slownessLevels - 1, true, false));
			else p.removePotionEffect(PotionEffectType.SLOWNESS);
		}
		/** Enemy ink stings, one heart a second; it does not execute. */
		private void tickEnemyInkDamage(boolean onEnemyColour){
			if(!onEnemyColour){
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
			surfaceQuietly();
			charge = 0;
			pendingSurge = -1;
			setMomentum(new Vector());
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
			surfaceQuietly();
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
	}
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
