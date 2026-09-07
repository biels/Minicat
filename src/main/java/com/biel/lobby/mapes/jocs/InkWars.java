package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.utilities.Pair;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;


public class InkWars extends JocEquips {
	HashMap<Block, Pair<String, Double>> highInkBlocks = new HashMap<>();
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
		i.add("Sneak on your own colour to dive into the ink: invisible, fast, healing, and you swim up your own walls");
		i.add("You reload x4 faster on your own colour, x8 while submerged");
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
		double m = getBalancingMultiplier(obtenirEquip(ply));
		ply.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 3, true), true);
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
		armPlayers();
	}
	public void armPlayers() {
		for(Player p : getPlayers()){
			getPlayerInfo(p).setWeaponLevel(1);
			armWithRollerIfUnarmed(p);
		}
	}
	/** Nobody walks onto the field with empty hands: the Roller is the default until the selector at the base says otherwise. */
	void armWithRollerIfUnarmed(Player p) {
		InkWarsPlayerInfo info = getPlayerInfo(p);
		if (info.getActiveWeapon() != null) return;
		info.setActiveWeapon(new RollerInkWeapon(p));
		sendPlayerMessage(p, ChatColor.YELLOW + "You start with the Roller. Change weapon at the selector in your base.");
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
			processHighInkBlocks();
			tickPlayerWeapons();
		}
	}
	@Override
	public void heartbeat() { //Every second
		super.heartbeat();
		if (JocIniciat) {
			controlWeaponSelector();
			updateScoreBoards();
			checkForWinner();
		}
	}
	public void controlWeaponSelector() {
		for(Player p : getPlayers()){				
			EquipInkWars e = obtenirEquip(p);
			InkWarsPlayerInfo i = getPlayerInfo(p);
			armWithRollerIfUnarmed(p);
			boolean isInBaseRange = p.getLocation().distance(e.getTeamSpawnLocation()) < 10;
			if (isInBaseRange){
				if(!p.getInventory().contains(Material.CRAFTING_TABLE)){
					giveWeaponSelectionButton(p);
					sendPlayerMessage(p, ChatColor.YELLOW + "You can now select your ink weapon!");
				}
			}else if(p.getInventory().contains(Material.CRAFTING_TABLE)){
				p.getInventory().remove(Material.CRAFTING_TABLE);
			}
			//Keep track of player's level based on his alive block count
			if(i.getAlivePaintedBlocks() > i.getWeaponLevel() * getBlockCountToLevelUp()){
				i.setWeaponLevel(i.getWeaponLevel() + 1);
				p.playSound(p.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1.3F);
				getWorld().spawnParticle(Particle.HAPPY_VILLAGER, p.getLocation().add(0, 1, 0), 20, 0.6, 0.8, 0.6, 0);
				sendPlayerMessage(p, ChatColor.AQUA + "Level Up! You are now level " + i.getWeaponLevel());
				sendTeamMessage(e, ChatColor.GRAY + "The player " + ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " is now level " + ChatColor.YELLOW + i.getWeaponLevel() + ChatColor.WHITE + "!");
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
	public void tickPlayerWeapons(){
		for(Player p : getPlayers()){
			InkWarsPlayerInfo i = getPlayerInfo(p);
			InkWeapon activeWeapon = i.getActiveWeapon();
			if (activeWeapon != null) {
				activeWeapon.tick();
			}
		}
	}
	protected void processHighInkBlocks(){
		//InkDecay
		HashMap<Block, Pair<String, Double>> toPaint = new HashMap<>();
		Iterator<Entry<Block, Pair<String, Double>>> iter = highInkBlocks.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Block, Pair<String, Double>> entry = iter.next();
			entry.getValue().setSecond(entry.getValue().getSecond() - (1.0/20.0)); // The ink dries by 1 every second
			if(entry.getValue().getSecond() <= 0){
				//Finally decay
				iter.remove();
				Block b = entry.getKey();
				EquipInkWars owner = getTeamOwningBlock(b);
				if (owner != null) {
					b.setType(getPaintMaterial(b.getType(), owner.getColor()), false);
				}
			}else{
				//Expansion physics
				Block b = entry.getKey();
				double ink = entry.getValue().getSecond();
				int chance = (int) (7 + Math.round(ink * 12)); //Liquidity
				if(Utils.Possibilitat(chance, 100 * 20)){
					Block r = b.getRelative(BlockFace.DOWN);
					double d = b.getLocation().distance(r.getLocation());
					Player p = Bukkit.getPlayer(entry.getValue().getFirst());
					if(p != null){
						toPaint.put(r, new Pair<>(p.getName(), ink * (Utils.Possibilitat(16) ? 1 : 1)));
						entry.getValue().setSecond(entry.getValue().getSecond() / 4);
					}
				}
			}
		}
		for (Entry<Block, Pair<String, Double>> entry : toPaint.entrySet()) {
			Block b = entry.getKey();
			Player p = Bukkit.getPlayer(entry.getValue().getFirst());
			if (p != null) {
				InkWeapon w = getPlayerInfo(p).getActiveWeapon();
				w.paintBlock(b, entry.getValue().getSecond());
			}
		}

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
	public void giveWeaponSelectionButton(Player p){
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.CRAFTING_TABLE), ChatColor.GOLD + "" + ChatColor.BOLD + "Weapon selector",  ChatColor.WHITE + "Opens weapon selection menu."), p, event -> openWeaponSelectionMenu(event.getPlayer()));
		p.getInventory().addItem(button.getItemStack());
	}
	public void openWeaponSelectionMenu(Player p){
		IconMenu menu = new IconMenu(ChatColor.RED + "Weapon selector " + Utils.NombreEntre(0, 100), 9, event -> {
            event.setWillClose(true);
            Player ply = event.getPlayer();
            int i = event.getPosition();
			InkWeapon selectedWeapon = null;
			if(i == 0)selectedWeapon = new RollerInkWeapon(ply);
			if(i == 1)selectedWeapon = new BrushInkWeapon(ply);
			if(i == 2)selectedWeapon = new MachinegunInkWeapon(ply);
			if(i == 3)selectedWeapon = new EnderInkWeapon(ply);
			if (selectedWeapon == null) return;
			getPlayerInfo(ply).setActiveWeapon(selectedWeapon);
			selectedWeapon.showInstructions();
            sendTeamMessage(obtenirEquip(event.getPlayer()), event.getPlayer().getName() + " has selected " + event.getMenu().getOptionNames()[i]);
        });
		int weaponLevel = getPlayerInfo(p).getWeaponLevel();
		String string = Integer.toString(weaponLevel);
		String lvlString = ChatColor.GOLD + "" + ChatColor.BOLD + " [Level " + string + "]";
		menu.setOption(0, new ItemStack(Material.STICK, 1), ChatColor.GREEN + "" + ChatColor.BOLD + "Roller" + lvlString, ChatColor.WHITE + "Paint a wide trail by moving.");
		menu.setOption(1, new ItemStack(Material.TORCH, 1), ChatColor.YELLOW + "" + ChatColor.BOLD + "Brush" + lvlString, ChatColor.WHITE + "Paint a fast, narrow trail by moving.");
		menu.setOption(2, new ItemStack(Material.SNOWBALL, 1), ChatColor.BLUE + "" + ChatColor.BOLD + "Machinegun" + lvlString, ChatColor.WHITE + "Throw ink balls to paint impact zones.");
		menu.setOption(3, new ItemStack(Material.ENDER_PEARL, 1), ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Ender" + lvlString, ChatColor.WHITE + "Pearl control, shield, swap, and a melee baton.");

		menu.open(p);
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
	abstract class InkWeapon extends PlayerWorldEventBus{
		private int reloadTicks = 0;
		private boolean validWeapon = true;
		private boolean enabled = true;
		public InkWeapon(Player ply) {
			super(ply);

		}
		public int getWeaponLevel() {
			return getPlayerInfo(getPlayer()).getWeaponLevel();
		}
		@Override
		public boolean isValid() {
			return validWeapon;
		}
		public boolean isEnabled() {
			return enabled;
		}
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		public void destroy() {
			validWeapon = false;
		}
		public void tick(){
			reloadTick();
		}
		/** Extra Speed potion levels the weapon grants wherever its owner stands. */
		public int getSpeedLevelBonus(){
			return 0;
		}
		public double getMaxHealth(){
			return 20;
		}
		public int neededReloadTicks(){
			return 50;
		}
		public int reloadTickIncrement(){ 
			InkWarsPlayerInfo info = getPlayerInfo(getPlayer());
			if(info.isSubmerged())return 8;
			int i = 1;
			EquipInkWars e = obtenirEquip(getPlayer());
			boolean isOnItsColor = info.getTeamColorWherePlayerStands() == e;
			boolean isInBaseRange = getPlayer().getLocation().distance(e.getTeamSpawnLocation()) < 10;
			if(isOnItsColor || isInBaseRange)i += 4;
			return i;
		}
		boolean isSubmerged(){
			return getPlayerInfo(getPlayer()).isSubmerged();
		}
		public int getMaxLoad(){
			return 64;
		}
		//Functionality
		public void giveTool(){
			ItemStack toolMaterial = getToolMaterial();
			if(toolMaterial == null)return;
			Utils.giveItemStack(toolMaterial, getPlayer());
			getPlayer().playSound(getPlayer().getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 1F, 1F);
			getPlayer().playSound(getPlayer().getEyeLocation(), Sound.BLOCK_PISTON_EXTEND, 1F, 1F);
		}
		public abstract ItemStack getToolMaterial();
		public String[] getInstructions(){
			return new String[0];
		}
		public void showInstructions(){
			for (String line : getInstructions()) {
				sendPlayerMessage(getPlayer(), ChatColor.LIGHT_PURPLE + "[Weapon] " + ChatColor.WHITE + line);
			}
		}
		public void reloadTick(){
			if(getLoadMaterial() == null)return;
			if(getPlayer().getInventory().contains(getLoadMaterial().getType(), getMaxLoad()))return;
			if(reloadTicks >= neededReloadTicks()){				
				ItemStack loadMaterial = getLoadMaterial();
				if(loadMaterial == null)return;
				Utils.giveItemStack(loadMaterial, getPlayer());
				getPlayer().updateInventory();
				getPlayer().playSound(getPlayer().getEyeLocation(), Sound.ENTITY_ITEM_PICKUP, 0.4F, 1F);
				reloadTicks = 0;
			}else{
				reloadTicks += reloadTickIncrement();
			}
		}
		public int getReloadTicks() {
			return reloadTicks;
		}
		public void setReloadTicks(int reloadTicks) {
			this.reloadTicks = reloadTicks;
		}
		public abstract ItemStack getLoadMaterial();
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
			boolean forcedly = isPaintableUnsafely(b);
			boolean paintable = isPaintable(b);
			if((paintable || forcedly) && isEnabled()){
				EquipInkWars newOwnerTeam = obtenirEquip(getPlayer());
                DyeColor sc = newOwnerTeam.getStrongColor();
				EquipInkWars oldOwnerTeam = getTeamOwningBlock(b);
				if(oldOwnerTeam != newOwnerTeam && oldOwnerTeam != null){
					if(highInkBlocks.containsKey(b)){
						// Wet enemy ink resists: the wetter, the likelier the stroke slides off; a higher level bites through more often
						double wetInk = highInkBlocks.get(b).getSecond();
						if(Math.random() < wetInk / (wetInk + 0.5 + Math.sqrt(getWeaponLevel()) / 2))return;
					}
				}
				//Register
				if(oldOwnerTeam != newOwnerTeam)getPlayerInfo(getPlayer()).registerBlockPaint(oldOwnerTeam);
				//Paint
				Material paintBase = forcedly ? Material.WHITE_TERRACOTTA : b.getType();
				Material painted = getPaintMaterial(paintBase, sc);
				if(b.getType() != painted)b.setType(painted, false);
				double pInk = 0;
				if(highInkBlocks.containsKey(b)){
					pInk = highInkBlocks.get(b).getSecond();
					highInkBlocks.remove(b);
				}
				if(oldOwnerTeam != newOwnerTeam)pInk = 0;
				highInkBlocks.put(b, new Pair<>(getPlayer().getName(), (inkAmount / 3) + pInk / 2)); // 1:3 Tick ratio !!!
			}
		}
		protected void paintRadius(Location c, double r, double inkAmount){
			for(Block b : Utils.getCuboidAround(c, (int) Math.round(r)).getBlocks()) {
				double distance = c.distance(b.getLocation());
				if (distance <= r) {
					double cappedDistance = Math.max(distance, 0.25);
					paintBlock(b, inkAmount * Math.sqrt(1.0 / cappedDistance)); //Area=pi*r^2 so r=sqrt(A/pi)
				}
			}
		}
	}
	abstract class ProjectileInkWeapon extends InkWeapon{
		ArrayList<Projectile> onHoldProjectileList = new ArrayList<>();
		public ProjectileInkWeapon(Player ply) {
			super(ply);

		}
		@Override
		protected boolean getPlayerSpecificEventFiltering() {
			return false;
		}
		@Override
		protected void onProjectileLaunch(ProjectileLaunchEvent evt,
				Projectile proj) {
			super.onProjectileLaunch(evt, proj);

			ProjectileSource s = proj.getShooter();
			if(s instanceof Player){
				Player p = (Player) s;
				if(p == getPlayer()){
					if(isSubmerged()){ // Surface first: nothing is thrown from under the ink
						evt.setCancelled(true);
						p.playSound(p.getEyeLocation(), Sound.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, 0.6F, 0.8F);
						return;
					}
					registerProjectile(proj);
				}
			}
		}
		public void registerProjectile(Projectile proj) {
			onHoldProjectileList.add(proj);
		}
		@Override
		protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
			super.onProjectileHit(evt, proj);

			if(onHoldProjectileList.remove(proj)){
				Block hitBlock = evt.getHitBlock();
				Block paintCenterBlock;
				if (hitBlock != null) {
					paintCenterBlock = evt.getHitBlockFace() == null
							? hitBlock : hitBlock.getRelative(evt.getHitBlockFace());
				} else if (evt.getHitEntity() != null) {
					hitBlock = evt.getHitEntity().getLocation().getBlock();
					paintCenterBlock = hitBlock;
				} else {
					hitBlock = proj.getLocation().getBlock();
					paintCenterBlock = hitBlock;
				}
				onWeaponHit(evt, proj, hitBlock, paintCenterBlock);
			}
		}
		public abstract void onWeaponHit(ProjectileHitEvent evt, Projectile proj, Block hitBlock, Block preHitBlock);
	}
	class MachinegunInkWeapon extends ProjectileInkWeapon{

		public MachinegunInkWeapon(Player ply) {
			super(ply);
		}
		@Override
		public int getMaxLoad() {
			return 4 + Math.round(getWeaponLevel() / 2);
		}
		@Override
		public int neededReloadTicks() {
			return 52 - Math.round(getWeaponLevel() * 2);
		}
		@Override
		public void onWeaponHit(ProjectileHitEvent evt, Projectile proj, Block hitBlock, Block preHitBlock) {
			if(proj instanceof Snowball){
				Snowball s = (Snowball) proj;
				getWorld().playSound(hitBlock.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1, 1.1F);
				getWorld().playSound(hitBlock.getLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1.1F);
//				getWorld().playEffect(preHitBlock.getLocation(), Effect.SPLASH, 0);
				paintRadius(preHitBlock.getLocation(), 1.1 + Math.sqrt(getWeaponLevel() * 0.75), 4 + getWeaponLevel() / 2.0);
				for(Player p : Utils.getNearbyPlayers(preHitBlock.getLocation(), 1 + getWeaponLevel())){
					if(areEnemies(p, getPlayer())){
						double targetDistance = Math.max(p.getLocation().distance(preHitBlock.getLocation()), 0.25);
						double shotDistance = Math.max(preHitBlock.getLocation().distance(getPlayer().getEyeLocation()), 0.25);
						double splashDamage = 2 + (6.5 + (getWeaponLevel() / 2.2))
								/ (targetDistance * (shotDistance / 3));
						p.damage(splashDamage, getPlayer());
					}
				}
			}
		}	
		@Override
		public ItemStack getLoadMaterial() {
			return Utils.setItemName(new ItemStack(Material.SNOWBALL, 1), obtenirEquip(getPlayer()).getChatColor() + "Ink ball");
		}
		@Override
		public String[] getInstructions() {
			return new String[]{"Throw snowballs to paint and damage around their impact.", "Reload faster while standing in your base or on your team's color."};
		}
		@Override
		public ItemStack getToolMaterial() {
			return null; //No tool
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
				Player damaged, Player damager, boolean ranged) {
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(!ranged && damager == getPlayer()){
				evt.setCancelled(true);
			}
		}
	}
	class EnderInkWeapon extends ProjectileInkWeapon{
		String targetedName; // A name, not a Player: the target may disconnect and come back as a new Player object
		int chargeTicks = 0;
		int toolTicks = -1;
		public EnderInkWeapon(Player ply) {
			super(ply);
		}
		Player getTargeted(){ // Null while the target is offline
			return targetedName == null ? null : Bukkit.getPlayer(targetedName);
		}
		boolean isTargeted(Player p){
			return p.getName().equals(targetedName);
		}
		@Override
		public double getMaxHealth() {
			return 10;
		}
		@Override
		public int getMaxLoad() {
			return 2;
		}
		@Override
		public int getSpeedLevelBonus() {
			return 1;
		}
		@Override
		public int neededReloadTicks() {
			return 550;
		}
		@Override
		public void onWeaponHit(ProjectileHitEvent evt, Projectile proj, Block hitBlock, Block preHitBlock) {
			paintRadius(preHitBlock.getLocation(), Math.sqrt(getWeaponLevel() / 2.0) + 1.75, 4 + getWeaponLevel() * 2 + 1);
//			world.playEffect(preHitBlock.getLocation(), Effect.COLOURED_DUST, 0);
//			world.playEffect(preHitBlock.getLocation(), Effect.COLOURED_DUST, 0);
//			world.playEffect(getPlayer().getEyeLocation(), Effect.COLOURED_DUST, 0);
			getPlayerInfo(getPlayer()).setShieldTicks(20 * 6);
		}

		@Override
		public ItemStack getToolMaterial() {
			ItemStack i = new ItemStack(Material.BLAZE_ROD, 1);
			i.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			return Utils.setItemNameAndLore(i, ChatColor.GOLD + "Warp baton", ChatColor.WHITE + "Melee hit: 4 damage and knockback.", ChatColor.GRAY + "Consumed on hit; recharges after 5 seconds.");
		}

		@Override
		public ItemStack getLoadMaterial() {
			return Utils.setItemNameAndLore(new ItemStack(Material.ENDER_PEARL, 1), ChatColor.LIGHT_PURPLE + "Control pearl", ChatColor.WHITE + "Impact: paint a large zone and gain a 6s shield.", ChatColor.WHITE + "Hit enemy: their movement paints for your team for 6s.", ChatColor.WHITE + "Hit the marked enemy again: swap positions.", ChatColor.GRAY + "Reloads faster on your color or inside your base.");
		}
		@Override
		public String[] getInstructions() {
			return new String[]{"Throw a pearl at terrain to paint a large zone and gain a 6-second shield.", "Hit an enemy to make their movement paint for your team for 6 seconds; hit that marked enemy again to swap positions.", "Use the Warp baton for a knockback melee hit; it is consumed and returns after 5 seconds.", "Pearls reload faster in your base or while standing on your team's color."};
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
				Player damaged, Player damager, boolean ranged) {
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(damager == getPlayer() && !evt.isCancelled()){
				if(ranged){
					//LOGIC
					if(isTargeted(damaged)){
						//Add time to existing target
						GUtils.swapPositions(damager, damaged);
						//						int time = 4 * 20 + getWeaponLevel() * 10;
						//						chargeTicks += time;
						//						sendPlayerMessage(targeted, ChatColor.GRAY + getPlayer().getName() + " (+" + Double.toString(time/20.0) + "s)!");
					}else{
						//Change target
						targetedName = damaged.getName();
						chargeTicks = 6 * 20; //Starting charges
						damager.playSound(damaged.getEyeLocation(), Sound.BLOCK_FURNACE_FIRE_CRACKLE, 1, (float) 1.2);
						damaged.playSound(damaged.getEyeLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1, (float) 1.2);
//						getWorld().playEffect(targeted.getEyeLocation(), Effect.VILLAGER_THUNDERCLOUD, 4);
//						getWorld().playEffect(targeted.getEyeLocation(), Effect.VILLAGER_THUNDERCLOUD, 4);
						sendPlayerMessage(damaged, ChatColor.GRAY + getPlayer().getName() + " has tricked you to paint for him (6s)!");
						InkWeapon targetedActiveWeapon = getPlayerInfo(damaged).getActiveWeapon();

						if (targetedActiveWeapon != null) {
							if (targetedActiveWeapon instanceof RollerInkWeapon) {
								targetedActiveWeapon.setEnabled(false); //Disable his roller
							}
						}			
					}
					getPlayerInfo(damaged).setShieldTicks(chargeTicks);
					toolTicks = 4;
				}else{
					if(getPlayer().getItemInHand().getType() == Material.BLAZE_ROD){
						getPlayer().getInventory().remove(getToolMaterial().getType());
						evt.setDamage(4D);
						toolTicks = 20 * 5;										
					}else{evt.setCancelled(true);}
				}
			}
		}
		@Override
		public void tick() {
			super.tick();
			if(toolTicks == 0){
				getPlayer().getInventory().addItem(getToolMaterial());
				toolTicks = -1;
			}
			if(toolTicks > 0)toolTicks--;
			Player targeted = getTargeted();
			if(chargeTicks > 0 && targeted != null){
				rollerLinePaint(1.5 + (getWeaponLevel() / 3) + chargeTicks / (20 * 4), 1.2, targeted);

				if(chargeTicks == 2) {
					//Last
					paintRadius(targeted.getEyeLocation(), Math.sqrt(4.5 + getWeaponLevel() * 0.25), Math.sqrt(4.5 + getWeaponLevel() * 0.3));
//					getWorld().playEffect(targeted.getEyeLocation(), Effect.INSTANT_SPELL, 4);
//					getWorld().playEffect(targeted.getEyeLocation(), Effect.CLOUD, 4);
//					getWorld().playSound(targeted.getEyeLocation(), Sound.ENTITY_FIREWORK_BLAST, 1, 1.2F);
				}
				if(chargeTicks <= 1){	//Because on chargeTicks=0 this object gets kicked from the memory		
					InkWeapon targetedActiveWeapon = getPlayerInfo(targeted).getActiveWeapon();
					if (targetedActiveWeapon != null) {
						if (targetedActiveWeapon instanceof RollerInkWeapon) {
							targetedActiveWeapon.setEnabled(true); //Re-enable his roller
						}
					}
					chargeTicks = 0;
					targetedName = null;
				}
				chargeTicks -= 1;
//				getWorld().playEffect(targeted.getLocation().add(0.5, 0.12, 0.5), Effect.LAVA_POP, 4);

			}
		}
		@Override
		protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
			super.onPlayerDeath(evt, killed);
			if(isTargeted(killed)){
				paintRadius(killed.getEyeLocation(), getWeaponLevel() + Math.round(chargeTicks / (20 * 1.5)), 4.5 + getWeaponLevel() * 0.25);
			}
		}

	}
	class RollerInkWeapon extends InkWeapon{
		public RollerInkWeapon(Player ply) {
			super(ply);
		}
		@Override
		public double getMaxHealth() {
			return 18;
		}
		@Override
		protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
			super.onPlayerMove(evt, p);
			if(p == getPlayer() && !isSubmerged()){
				rollerLinePaint(getWidth(), 1.2 + getWeaponLevel() / 8.0, getPlayer());				
			}
		}
		public double getWidth() {
			return 1 + Math.sqrt(getWeaponLevel());
		}


		@Override
		public ItemStack getLoadMaterial() {
			return null; //No material
		}
		@Override
		public ItemStack getToolMaterial() {
			return Utils.setItemNameAndLore(new ItemStack(Material.STICK, 1), obtenirEquip(getPlayer()).getChatColor() + "Roller", ChatColor.WHITE + "Paints on a straight line perpendicular to your facing direction and the floor");
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
				Player damaged, Player damager, boolean ranged) {
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(!ranged && damager == getPlayer()){
				evt.setCancelled(true);
			}
		}
	}
	class BrushInkWeapon extends InkWeapon{
		String targetedName; // A name, not a Player: the target may disconnect and come back as a new Player object
		int charges = 0;
		public BrushInkWeapon(Player ply) {
			super(ply);
		}
		Player getTargeted(){ // Null while the target is offline
			return targetedName == null ? null : Bukkit.getPlayer(targetedName);
		}
		@Override
		public double getMaxHealth() {
			return 18;
		}
		@Override
		protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
			super.onPlayerMove(evt, p);
			if(p == getPlayer() && !isSubmerged()){
				rollerLinePaint(getWidth(), 3.2 + getWeaponLevel() / 1.8, getPlayer());				
			}
		}
		public double getWidth() {
			return 0.1 + Math.sqrt(getWeaponLevel()) / 4;
		}

		@Override
		public int getSpeedLevelBonus() {
			return 1;
		}
		@Override
		public ItemStack getLoadMaterial() {
			return null; //No material
		}
		@Override
		public ItemStack getToolMaterial() {
			return Utils.setItemNameAndLore(new ItemStack(Material.TORCH, 1), obtenirEquip(getPlayer()).getChatColor() + "Brush", ChatColor.WHITE + "Paints on a straight line perpendicular to your facing direction and the floor");
		}
		@Override
		public void tick() {
			super.tick();
			Player targeted = getTargeted();
			if(targeted != null){
				if(targeted.hasPotionEffect(PotionEffectType.WITHER)){
					for(Player p : Utils.getNearbyPlayers(targeted, 1.5)){
						if(areAllies(targeted, p))p.damage(0.1, getPlayer());
					}
				}
			}
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
				Player damaged, Player damager, boolean ranged) {
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(damager != getPlayer())return;
			if(!ranged && damager == getPlayer() && !damaged.hasPotionEffect(PotionEffectType.WITHER) && !evt.isCancelled()){
				if(!damaged.getName().equals(targetedName)){
					if(targetedName != null)charges = 0;
					targetedName = damaged.getName();
				}
				evt.setDamage(0.25 + 0.1 * getWeaponLevel());
				getWorld().playSound(damaged.getEyeLocation(), Sound.ENTITY_SLIME_ATTACK, 1, 1.1F + 0.4F * charges);
				getWorld().playSound(damaged.getEyeLocation(), Sound.ENTITY_SLIME_JUMP, 1, 1.1F + 0.4F * charges);
				paintRadius(damaged.getEyeLocation(), (0.25 + 0.1 * getWeaponLevel()) * charges, 2);
				charges++;
				if(charges > 3){
					charges = 0;
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) Math.round(20 * (5 + 0.6 * getWeaponLevel())), Math.round(getWeaponLevel() + 2 / 5)), true);
					getWorld().playSound(damaged.getEyeLocation(), Sound.ENTITY_GENERIC_SWIM, 1, 1.25F);
				}
			}else if(!evt.isCancelled()){evt.setCancelled(true);}
		}
	}

	@Override
	public InkWarsPlayerInfo getPlayerInfo(Player p) {
		return getPlayerInfo(p, InkWarsPlayerInfo.class);		
	}
	public class InkWarsPlayerInfo extends PlayerInfo{
		private int paintedBlockCount = 0;
		private int alivePaintedBlocks = 0;
		private InkWeapon activeWeapon = null;
		private int weaponLevel = 1;
		private int dmgTicks = 0;
		private int shieldTicks = 0;
		private boolean submerged = false;
		private int submergedTicks = 0;
		//-----

		public InkWarsPlayerInfo() {
			super();
		}
		public int getPaintedBlockCount() {
			return paintedBlockCount;
		}
		public int getAlivePaintedBlocks() {
			return alivePaintedBlocks;
		}
		public boolean isShielded(){
			return getShieldTicks() > 0;
		}
		public int getShieldTicks() {
			return shieldTicks;
		}
		public void setShieldTicks(int shieldTicks) {
			boolean shieldWasInactive = this.shieldTicks <= 0;
			this.shieldTicks = Math.max(0, shieldTicks);
			if(shieldWasInactive && this.shieldTicks > 0){
				getWorld().spawnParticle(Particle.END_ROD, getPlayer().getLocation().add(0, 1, 0), 18, 0.5, 0.8, 0.5, 0.02);
				getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.7F, 1.6F);
			}
		}
		@Override
		public void ultraTick() {
			super.ultraTick();
			Player p = getPlayer();
			EquipInkWars team = obtenirEquip(p);
			EquipInkWars colourUnderfoot = getTeamColorWherePlayerStands();
			boolean onOwnColour = colourUnderfoot == team;
			boolean onEnemyColour = colourUnderfoot != null && !onOwnColour && !isShielded();
			Block ownWallAhead = ownWallAhead(team);

			boolean wantsToSwim = p.isSneaking() && getActiveWeapon() != null && (onOwnColour || ownWallAhead != null);
			if(wantsToSwim && !submerged)dive();
			if(!wantsToSwim && submerged)surface();
			if(submerged)tickSubmerged(team, ownWallAhead);

			applyInkSpeed(onOwnColour, onEnemyColour);
			tickEnemyInkDamage(onEnemyColour);
			tickShield();
		}
		public boolean isSubmerged(){
			return submerged;
		}
		/** Sneaking on the team's own colour with a weapon in hand: the body goes under the ink, the armour with it, only a ripple stays visible. */
		private void dive(){
			submerged = true;
			submergedTicks = 0;
			Player p = getPlayer();
			p.getInventory().setArmorContents(null);
			p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 0.8F, 0.7F);
			getWorld().spawnParticle(Particle.SPLASH, p.getLocation().add(0, 0.2, 0), 25, 0.6, 0.1, 0.6, 0);
		}
		/** Coming out of the ink: armour back, a splash of paint where the body surfaces. */
		private void surface(){
			if(!submerged)return;
			surfaceQuietly();
			Player p = getPlayer();
			p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 0.8F, 1.3F);
			getWorld().spawnParticle(Particle.SPLASH, p.getLocation().add(0, 0.2, 0), 25, 0.6, 0.1, 0.6, 0);
			InkWeapon weapon = getActiveWeapon();
			if(weapon != null)weapon.paintRadius(p.getLocation(), 1.5, 2);
		}
		/** The flag and the armour only, for a death or a weapon change that re-issues the kit anyway. */
		private void surfaceQuietly(){
			submerged = false;
			Player p = getPlayer();
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
			Utils.donarItemsPlayer(p, getStartingItems(p));
		}
		/** The own-colour wall the player faces, at feet or head height, or null. That wall is swimmable. */
		private Block ownWallAhead(EquipInkWars team){
			Player p = getPlayer();
			Vector facing = p.getLocation().getDirection().setY(0);
			if(facing.lengthSquared() < 0.01)return null;
			facing.normalize();
			Block feet = p.getLocation().getBlock();
			int dx = (int) Math.round(facing.getX());
			int dz = (int) Math.round(facing.getZ());
			for(int dy = 0; dy <= 1; dy++){
				Block wall = feet.getRelative(dx, dy, dz);
				if(getTeamOwningBlock(wall) == team)return wall;
			}
			return null;
		}
		/** Submerged: unseen, healing, a ripple in the team colour, up the own-colour wall ahead at full speed; every block the body touches is wetted again. */
		private void tickSubmerged(EquipInkWars team, Block ownWallAhead){
			Player p = getPlayer();
			submergedTicks++;
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 0, true, false));
			p.setFallDistance(0);
			if(submergedTicks % 20 == 0)Utils.healDamageable(p, 1.0);
			if(submergedTicks % 2 == 0){
				Particle.DustOptions ripple = new Particle.DustOptions(team.getStrongColor().getColor(), 1.4F);
				getWorld().spawnParticle(Particle.DUST, p.getLocation().add(0, 0.15, 0), 4, 0.45, 0.05, 0.45, 0, ripple);
			}
			InkWeapon weapon = getActiveWeapon();
			Block floor = getBlockWherePlayerStands();
			if(floor != null && getTeamOwningBlock(floor) == team)weapon.paintBlock(floor, 1.5);
			if(ownWallAhead != null){
				weapon.paintBlock(ownWallAhead, 1.5);
				Vector facing = p.getLocation().getDirection().setY(0).normalize();
				p.setVelocity(new Vector(facing.getX() * 0.12, 0.28, facing.getZ() * 0.12));
			}
		}
		/** Own colour is fast, enemy colour is mud. Potion levels refreshed every tick so they vanish the moment the ground changes. */
		private void applyInkSpeed(boolean onOwnColour, boolean onEnemyColour){
			Player p = getPlayer();
			int speedLevels = 0;
			if(onOwnColour)speedLevels += 1;
			if(submerged)speedLevels += 1;
			if(getActiveWeapon() != null)speedLevels += getActiveWeapon().getSpeedLevelBonus();
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
		private void tickShield(){
			if(shieldTicks <= 0)return;
			if(shieldTicks % 5 == 0){
				getWorld().spawnParticle(Particle.END_ROD, getPlayer().getLocation().add(0, 1, 0), 3, 0.45, 0.65, 0.45, 0);
			}
			shieldTicks--;
			if(shieldTicks == 0){
				getWorld().spawnParticle(Particle.SMOKE, getPlayer().getLocation().add(0, 1, 0), 10, 0.4, 0.6, 0.4, 0.02);
				getPlayer().playSound(getPlayer().getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.6F, 1.8F);
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
			surfaceQuietly();
			alivePaintedBlocks *= 0.8; //Reduce player points by 20%
			getPlayer().setLevel(alivePaintedBlocks);
			sendPlayerMessage(getPlayer(), ChatColor.RED + "You have lost 20% of your points");
		}
		public InkWeapon getActiveWeapon() {
			return activeWeapon;
		}
		public void setActiveWeapon(InkWeapon newWeapon) {
			InkWeapon pW = this.activeWeapon;
			if (pW != null) {
				pW.destroy();
			}
			submerged = false;
			getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
			double healthBeforeSwitch = getPlayer().getHealth();
			getPlayer().setMaxHealth(newWeapon.getMaxHealth());
			donarItemsInicials(getPlayer()); //Clears and gives starting (colored) armor
			getPlayer().setHealth(Math.min(healthBeforeSwitch, getPlayer().getMaxHealth()));
			this.activeWeapon = newWeapon;
			this.activeWeapon.giveTool();
		}
		public int getWeaponLevel() {
			return weaponLevel;
		}
		public void setWeaponLevel(int weaponLevel) {
			this.weaponLevel = weaponLevel;
		}
		public void weaponLevelUp(){
			setWeaponLevel(getWeaponLevel() + 1);
		}
		public EquipInkWars getTeamColorWherePlayerStands(){
			return getTeamOwningBlock(getBlockWherePlayerStands());
		}

	}
	@Override
	protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
		super.onPlayerInteract(evt, p);
		Block b = evt.getClickedBlock();
		if(b != null){
			if(b.getType() == Material.OAK_WALL_SIGN){
				openWeaponSelectionMenu(p); //For service stations
			}
		}
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		super.onPlayerDeath(evt, killed);
		getPlayerInfo(killed).registerDeath();
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		super.onPlayerDeathByPlayer(evt, killed, killer);
		InkWeapon killerWeapon = getPlayerInfo(killer).getActiveWeapon();
		if (killerWeapon == null) return;
		// The splat: a kill is painted where the body fell, whatever the weapon
		killerWeapon.paintRadius(killed.getLocation(), 3 + Math.sqrt(killerWeapon.getWeaponLevel()), 6);
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
