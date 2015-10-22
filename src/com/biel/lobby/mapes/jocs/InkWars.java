package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.GestorMapes.ContenidorMapa;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamScoreRace;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocTeamScoreRace.EquipScoreRace;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.Matrix;
import com.biel.lobby.utilities.Pair;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;


public class InkWars extends JocEquips {
	HashMap<Block, Pair<String, Double>> highInkBlocks = new HashMap<Block, Pair<String, Double>>();
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
		ArrayList<String> i = new ArrayList<String>();
		i.add("You win by having more painted blocks than the enemy when the time ends");
		i.add("You win by having more than 80% of the map painted");
		i.add("In this map you level up every " + getBlockCountToLevelUp() + " effectively painted blocks");
		i.add("The ink won't dry instantly, use it to your advantage");
		i.add("You reload x4 faster on top of a owned block");
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
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
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
		initializeLevels();
	}
	public void initializeLevels() {
		for(Player p : getPlayers()){
			InkWarsPlayerInfo i = getPlayerInfo(p);
			i.setWeaponLevel(1);
		}
	}

	@Override
	protected void updateScoreBoard(Player ply) {
		super.updateScoreBoard(ply);
		if (JocIniciat && !JocFinalitzat){
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<Integer> values = new ArrayList<Integer>();
			for(Equip e : Equips){
				try {
					list.add(e.getAdjectiuColored());
					EquipInkWars eq = (EquipInkWars)e;
					values.add((int) Math.round(eq.getOwnedPercent()));
				} catch (Exception e1) {

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
			getWorld().playEffect(p.getEyeLocation(), Effect.FIREWORKS_SPARK, 0);
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
			boolean isInBaseRange = p.getLocation().distance(e.getTeamSpawnLocation()) < 10;
			if (isInBaseRange){
				if(!p.getInventory().contains(Material.WORKBENCH)){
					giveWeaponSelectionButton(p);
					sendPlayerMessage(p, ChatColor.YELLOW + "You can now select your ink weapon!");
				}
			}else{
				if(p.getInventory().contains(Material.WORKBENCH)){
					p.getInventory().remove(Material.WORKBENCH);

					InkWeapon activeWeapon = i.getActiveWeapon();
					if (activeWeapon == null) {
						String message = "Don't go to battle with your hands tied behind your back!";
						if(i.getWeaponAlerts() == 2)message = "JUST CHOOSE A WEAPON!";
						if(i.getWeaponAlerts() == 3)message = "OKAY, CONTEMPLATE YOUR DEFEAT.";
						if(i.getWeaponAlerts() >= 4)message = "Won't say anything more.";
						sendPlayerMessage(p, ChatColor.BOLD + message);
						i.registerWeaponAlert();
						e.teleportToTeamSpawn(p);
					}

				}
			}
			//Keep track of player's level based on his alive block count
			if(i.getAlivePaintedBlocks() > i.getWeaponLevel() * getBlockCountToLevelUp()){
				i.setWeaponLevel(i.getWeaponLevel() + 1);
				sendPlayerMessage(p, ChatColor.AQUA + "Level Up! You are now level " + i.getWeaponLevel());
				sendTeamMessage(e, ChatColor.GRAY + "The player " + ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " is now level " + ChatColor.YELLOW + i.getWeaponLevel() + ChatColor.WHITE + "!");
			}
		}
	}
	int getBlockCountToLevelUp(){ //Configurable for multi-map
		int r = 500;
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
		HashMap<Block, Pair<String, Double>> toPaint = new HashMap<Block, Pair<String, Double>>();
		Iterator<Entry<Block, Pair<String, Double>>> iter = highInkBlocks.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Block, Pair<String, Double>> entry = iter.next();
			entry.getValue().setSecond(entry.getValue().getSecond() - (1.0/20.0)); //Every 20s -1
			if(entry.getValue().getSecond() <= 0){
				//Finally decay
				iter.remove();    
				Block b = entry.getKey();
				if (getTeamOwningBlock(b) != null) {
					b.setData(getTeamOwningBlock(b).getColor().getWoolData());
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
						toPaint.put(r, new Pair<String, Double>(p.getName(), ink * (Utils.Possibilitat(16) ? 1 : 1)));
						entry.getValue().setSecond(entry.getValue().getSecond() / 4);
					}
				}
			}
		}
		Iterator<Entry<Block, Pair<String, Double>>> iter2 = toPaint.entrySet().iterator();
		while (iter2.hasNext()) {
			Entry<Block, Pair<String, Double>> entry = iter2.next();
			Block b = entry.getKey();
			Player p = Bukkit.getPlayer(entry.getValue().getFirst());
			if(p != null){
				InkWeapon w = getPlayerInfo(p).getActiveWeapon();
				w.paintBlock(b, entry.getValue().getSecond());
			}
		}

	}
	public EquipInkWars getTeamOwningBlock(Block b){
		if(b == null)return null;
		if(!isPaintable(b))return null;
		for(Equip e : Equips){
			EquipInkWars eq = (EquipInkWars) e;
			if(eq.getColor().getWoolData() == b.getData() || eq.getStrongColor().getWoolData() == b.getData())return eq;
		}
		return null;
	}
	public void giveWeaponSelectionButton(Player p){
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.WORKBENCH), ChatColor.GOLD + "" + ChatColor.BOLD + "Weapon selector",  ChatColor.WHITE + "Opens weapon selection menu."), p, new ItemButton.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemButton.OptionClickEvent event) {
				openWeaponSelectionMenu(event.getPlayer());
			}
		});
		p.getInventory().addItem(button.getItemStack());
	}
	public void openWeaponSelectionMenu(Player p){
		IconMenu menu = new IconMenu(ChatColor.RED + "Weapon selector " + Utils.NombreEntre(0, 100), 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {
				event.setWillClose(true);
				Player ply = event.getPlayer();
				int i = event.getPosition();
				if(i == 0)getPlayerInfo(ply).setActiveWeapon(new RollerInkWeapon(ply));
				if(i == 1)getPlayerInfo(ply).setActiveWeapon(new BrushInkWeapon(ply));
				if(i == 2)getPlayerInfo(ply).setActiveWeapon(new MachinegunInkWeapon(ply));
				if(i == 3)getPlayerInfo(ply).setActiveWeapon(new EnderInkWeapon(ply));
				sendTeamMessage(obtenirEquip(event.getPlayer()), event.getPlayer().getName() + " has selected " + event.getMenu().getOptionNames()[i]);
			}
		});
		int weaponLevel = getPlayerInfo(p).getWeaponLevel();
		String string = Integer.toString(weaponLevel);
		String lvlString = ChatColor.GOLD + "" + ChatColor.BOLD + " [Level " + string + "]";
		menu.setOption(0, new ItemStack(Material.STICK, 1), ChatColor.GREEN + "" + ChatColor.BOLD + "Roller" + lvlString);
		menu.setOption(1, new ItemStack(Material.TORCH, 1), ChatColor.YELLOW + "" + ChatColor.BOLD + "Brush" + lvlString);
		menu.setOption(2, new ItemStack(Material.SNOW_BALL, 1), ChatColor.BLUE + "" + ChatColor.BOLD + "Machinegun" + lvlString);
		menu.setOption(3, new ItemStack(Material.ENDER_PEARL, 1), ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Ender" + lvlString);

		menu.open(p);
	}
	public boolean isPaintable(Block b){ 
		ArrayList<Material> p = new ArrayList<Material>();
		p.add(Material.WOOL);p.add(Material.STAINED_CLAY);p.add(Material.STAINED_GLASS);p.add(Material.STAINED_GLASS_PANE);
		//		p.add(Material.STONE);p.add(Material.STONE);p.add(Material.STONE_B)
		Material t = b.getType();
		return (p.contains(t));
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
		public double getWeaponSpeedModifier(){
			return 0;
		}
		public int neededReloadTicks(){
			return 50;
		}
		public int reloadTickIncrement(){ 
			int i = 1;
			EquipInkWars e = obtenirEquip(getPlayer());
			EquipInkWars teamOwningBlock = getPlayerInfo(getPlayer()).getTeamColorWherePlayerStands();
			boolean isOnItsColor = teamOwningBlock == e;
			boolean isInBaseRange = getPlayer().getLocation().distance(e.getTeamSpawnLocation()) < 10;
			if(isOnItsColor || isInBaseRange)i += 4;
			return i;
		}
		public int getMaxLoad(){
			return 64;
		}
		//Functionality
		public void giveTool(){
			ItemStack toolMaterial = getToolMaterial();
			if(toolMaterial == null)return;
			Utils.giveItemStack(toolMaterial, getPlayer());
			getPlayer().playSound(getPlayer().getEyeLocation(), Sound.CHEST_OPEN, 1F, 1F);
			getPlayer().playSound(getPlayer().getEyeLocation(), Sound.PISTON_EXTEND, 1F, 1F);
		}
		public abstract ItemStack getToolMaterial();
		public void reloadTick(){
			if(getLoadMaterial() == null)return;
			if(getPlayer().getInventory().contains(getLoadMaterial().getType(), getMaxLoad()))return;
			if(reloadTicks >= neededReloadTicks()){				
				ItemStack loadMaterial = getLoadMaterial();
				if(loadMaterial == null)return;
				Utils.giveItemStack(loadMaterial, getPlayer());
				getPlayer().updateInventory();
				getPlayer().playSound(getPlayer().getEyeLocation(), Sound.ITEM_PICKUP, 0.4F, 1F);
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
				EquipInkWars e = newOwnerTeam;
				DyeColor sc = e.getStrongColor();
				EquipInkWars oldOwnerTeam = getTeamOwningBlock(b);
				if(oldOwnerTeam != newOwnerTeam && oldOwnerTeam != null){
					if(highInkBlocks.containsKey(b)){
						double ink = highInkBlocks.get(b).getSecond();
						//if(Utils.Possibilitat(ink, 0.28 + Math.sqrt(getWeaponLevel()) / 2))return;
						return;
					}
				}
				//Register
				if(oldOwnerTeam != newOwnerTeam)getPlayerInfo(getPlayer()).registerBlockPaint(oldOwnerTeam);
				//Paint
				if(forcedly)b.setType(Material.STAINED_CLAY);
				b.setData(sc.getWoolData(), false);
				double pInk = 0;
				if(highInkBlocks.containsKey(b)){
					pInk = highInkBlocks.get(b).getSecond();
					highInkBlocks.remove(b);
				}
				if(oldOwnerTeam != newOwnerTeam)pInk = 0;
				highInkBlocks.put(b, new Pair<String, Double>(getPlayer().getName(), (inkAmount / 3) + pInk / 2)); // 1:3 Tick ratio !!!
			}
		}
		protected void paintRadius(Location c, double r, double inkAmount){
			for(Block b : Utils.getCuboidAround(c, (int) Math.round(r)).getBlocks()) {
				double distance = c.distance(b.getLocation());
				if(distance <= r)paintBlock(b, inkAmount * Math.sqrt(1.0/distance)); //Area=pi*r^2 so r=sqrt(A/pi)
			}
		}
	}
	abstract class ProjectileInkWeapon extends InkWeapon{
		ArrayList<Projectile> onHoldProjectileList = new ArrayList<Projectile>();
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

			if(onHoldProjectileList.contains(proj)){
				Location loc = proj.getLocation();
				BlockIterator iterator = new BlockIterator(getWorld(), loc.toVector(), proj.getVelocity().normalize(), 0, 4);
				Block hitBlock = null;
				Block preHitBlock = null;
				while(iterator.hasNext()) {
					preHitBlock = hitBlock;
					hitBlock = iterator.next();
					if(hitBlock.getTypeId()!=0) //Check all non-solid blockid's here.
					{break;}
				}
				if (hitBlock != null && preHitBlock != null) {	
					onWeaponHit(evt, proj, hitBlock, preHitBlock);
				}
			}
		}
		public abstract void onWeaponHit(ProjectileHitEvent evt, Projectile proj, Block hitBlock, Block preHitBlock);
	}
	class MachinegunInkWeapon extends ProjectileInkWeapon{

		public MachinegunInkWeapon(Player ply) {
			super(ply);
			ply.setMaxHealth(20);
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
				getWorld().playSound(hitBlock.getLocation(), Sound.SLIME_ATTACK, 1, 1.1F);
				getWorld().playSound(hitBlock.getLocation(), Sound.SLIME_WALK, 1, 1.1F);
				getWorld().playEffect(preHitBlock.getLocation(), Effect.SPLASH, 0);
				paintRadius(preHitBlock.getLocation(), 1.1 + Math.sqrt(getWeaponLevel() * 0.75), 4 + getWeaponLevel() / 2.0);
				for(Player p : Utils.getNearbyPlayers(preHitBlock.getLocation(), 1 + getWeaponLevel())){
					if(areEnemies(p, getPlayer())){
						p.damage(2 + (6.5 + (getWeaponLevel() / 2.2)) / (p.getLocation().distance(preHitBlock.getLocation()) * (preHitBlock.getLocation().distance(getPlayer().getEyeLocation()) / 3)), getPlayer());
					}
				}
			}
		}	
		@Override
		public ItemStack getLoadMaterial() {
			return Utils.setItemName(new ItemStack(Material.SNOW_BALL, 1), obtenirEquip(getPlayer()).getChatColor() + "Ink ball");
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
		Player targeted;
		int chargeTicks = 0;
		int toolTicks = 0;
		public EnderInkWeapon(Player ply) {
			super(ply);
			ply.setMaxHealth(10);
		}
		@Override
		public int getMaxLoad() {
			return 2;
		}
		@Override
		public double getWeaponSpeedModifier() {
			// TODO Auto-generated method stub
			return 8;
		}
		@Override
		public int neededReloadTicks() {
			return 550;
		}
		@Override
		public void onWeaponHit(ProjectileHitEvent evt, Projectile proj, Block hitBlock, Block preHitBlock) {
			paintRadius(preHitBlock.getLocation(), Math.sqrt(getWeaponLevel() / 2.0) + 1.75, 4 + getWeaponLevel() * 2 + 1);
			world.playEffect(preHitBlock.getLocation(), Effect.COLOURED_DUST, 0);
			world.playEffect(preHitBlock.getLocation(), Effect.COLOURED_DUST, 0);
			world.playEffect(getPlayer().getEyeLocation(), Effect.COLOURED_DUST, 0);
			getPlayerInfo(getPlayer()).setShieldTicks(20 * 6);
		}

		@Override
		public ItemStack getToolMaterial() {
			ItemStack i = new ItemStack(Material.BLAZE_ROD, 1);
			i.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
			return i;
		}

		@Override
		public ItemStack getLoadMaterial() {
			return Utils.setItemNameAndLore(new ItemStack(Material.ENDER_PEARL, 1), ChatColor.BLUE + "Magic ball", ChatColor.WHITE + "Makes enemies paint for you if it impacts on their face");
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
				Player damaged, Player damager, boolean ranged) {
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(damager == getPlayer() && !evt.isCancelled()){
				if(ranged){
					//LOGIC
					if(damaged == targeted){
						//Add time to existing target
						GUtils.swapPositions(damager, damaged);
						//						int time = 4 * 20 + getWeaponLevel() * 10;
						//						chargeTicks += time;
						//						sendPlayerMessage(targeted, ChatColor.GRAY + getPlayer().getName() + " (+" + Double.toString(time/20.0) + "s)!");
					}else{
						//Change target
						targeted = damaged;
						chargeTicks = 6 * 20; //Starting charges
						damager.playSound(damaged.getEyeLocation(), Sound.FIRE_IGNITE, 1, (float) 1.2);
						damaged.playSound(damaged.getEyeLocation(), Sound.ZOMBIE_REMEDY, 1, (float) 1.2);
						getWorld().playEffect(targeted.getEyeLocation(), Effect.VILLAGER_THUNDERCLOUD, 4);
						getWorld().playEffect(targeted.getEyeLocation(), Effect.VILLAGER_THUNDERCLOUD, 4);
						sendPlayerMessage(targeted, ChatColor.GRAY + getPlayer().getName() + " has tricked you to paint for him (6s)!");
						InkWeapon targetedActiveWeapon = getPlayerInfo(targeted).getActiveWeapon();

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
				toolTicks = 200000;
			}
			toolTicks--;
			if(chargeTicks > 0 && targeted != null){
				rollerLinePaint(1.5 + (getWeaponLevel() / 3) + chargeTicks / (20 * 4), 1.2, targeted);

				if(chargeTicks == 2) {
					//Last
					paintRadius(targeted.getEyeLocation(), Math.sqrt(4.5 + getWeaponLevel() * 0.25), Math.sqrt(4.5 + getWeaponLevel() * 0.3));
					getWorld().playEffect(targeted.getEyeLocation(), Effect.INSTANT_SPELL, 4);
					getWorld().playEffect(targeted.getEyeLocation(), Effect.CLOUD, 4);
					getWorld().playSound(targeted.getEyeLocation(), Sound.FIREWORK_BLAST, 1, 1.2F);
				}
				if(chargeTicks <= 1){	//Because on chargeTicks=0 this object gets kicked from the memory		
					InkWeapon targetedActiveWeapon = getPlayerInfo(targeted).getActiveWeapon();
					if (targetedActiveWeapon != null) {
						if (targetedActiveWeapon instanceof RollerInkWeapon) {
							targetedActiveWeapon.setEnabled(true); //Re-enable his roller
						}
					}
					chargeTicks = 0;
					targeted = null;
				}
				chargeTicks -= 1;
				getWorld().playEffect(targeted.getLocation().add(0.5, 0.12, 0.5), Effect.LAVA_POP, 4);

			}
		}
		@Override
		protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
			super.onPlayerDeath(evt, killed);
			if(killed == targeted){
				paintRadius(targeted.getEyeLocation(), getWeaponLevel() + Math.round(chargeTicks / (20 * 1.5)), 4.5 + getWeaponLevel() * 0.25);
			}
		}
		@Override
		protected void onPlayerDeathByPlayer(PlayerDeathEvent evt,
				Player killed, Player killer) {
			super.onPlayerDeathByPlayer(evt, killed, killer);
			if(killer == getPlayer()){				
				paintRadius(killed.getEyeLocation(), Math.sqrt(getWeaponLevel()) + 7, 5 + getWeaponLevel() / 3);
			}
		}

	}
	class RollerInkWeapon extends InkWeapon{
		public RollerInkWeapon(Player ply) {
			super(ply);
			ply.setMaxHealth(18);
		}
		@Override
		protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
			super.onPlayerMove(evt, p);
			if(p == getPlayer()){
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
		Player targeted;
		int charges = 0;
		public BrushInkWeapon(Player ply) {
			super(ply);
			ply.setMaxHealth(18);
		}
		@Override
		protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
			super.onPlayerMove(evt, p);
			if(p == getPlayer()){
				rollerLinePaint(getWidth(), 3.2 + getWeaponLevel() / 1.8, getPlayer());				
			}
		}
		public double getWidth() {
			return 0.1 + Math.sqrt(getWeaponLevel()) / 4;
		}

		@Override
		public double getWeaponSpeedModifier() {
			// TODO Auto-generated method stub
			return 10;
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
			if(!ranged && damager == getPlayer() && !damaged.hasPotionEffect(PotionEffectType.WITHER) && !evt.isCancelled()){
				if(targeted == null)targeted = damaged;
				if(targeted != damaged){
					targeted = damaged;
					charges = 0;
				}
				evt.setDamage(0.25 + 0.1 * getWeaponLevel());
				getWorld().playSound(damaged.getEyeLocation(), Sound.SLIME_ATTACK, 1, 1.1F + 0.4F * charges);
				getWorld().playSound(damaged.getEyeLocation(), Sound.SLIME_WALK, 1, 1.1F + 0.4F * charges);
				paintRadius(damaged.getEyeLocation(), (0.25 + 0.1 * getWeaponLevel()) * charges, 2);
				charges++;
				if(charges > 3){
					charges = 0;
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) Math.round(20 * (5 + 0.6 * getWeaponLevel())), Math.round(getWeaponLevel() + 2 / 5)), true);
					getWorld().playSound(damaged.getEyeLocation(), Sound.SWIM, 1, 1.25F);
				}
			}else{evt.setCancelled(true);}
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
		private int weaponAlerts = 0;
		private int dmgTicks = 0;
		private int shieldTicks = 0;
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
		public void registerWeaponAlert(){
			weaponAlerts++;
		}
		public int getWeaponAlerts() {
			return weaponAlerts;
		}
		public boolean isShileded(){
			return getShieldTicks() > 0;
		}
		public int getShieldTicks() {
			return shieldTicks;
		}
		public void setShieldTicks(int shieldTicks) {
			this.shieldTicks = shieldTicks;
		}
		@Override
		public void ultraTick() {
			//			EquipInkWars ownerTeam = getTeamOwningBlock(b);
			//			if(oldOwnerTeam != newOwnerTeam && oldOwnerTeam != null){
			//				if(highInkBlocks.containsKey(b)){
			//					double ink = highInkBlocks.get(b).getSecond();
			//					if(Utils.Possibilitat(ink, 0.2 + Math.sqrt(getWeaponLevel()) / 2))return;
			//				}
			//			}
			EquipInkWars e = obtenirEquip(getPlayer());
			Block b = getBlockWherePlayerStands();
			EquipInkWars teamOwningBlock = getTeamColorWherePlayerStands();
			if(teamOwningBlock == null){
				setSpeedModifier(0);
			}
			if(b != null && teamOwningBlock != null){
				boolean isOnItsColor = (teamOwningBlock == e) || isShileded();
				boolean isHighInk = highInkBlocks.containsKey(b);
				double speedMod = 0;
				if(isOnItsColor){speedMod += 5;}else{speedMod += -5;}
				if(isHighInk){speedMod *= 2;}				
				if (getActiveWeapon() != null) {
					speedMod += getActiveWeapon().getWeaponSpeedModifier();
				}
				double dmgTickIncrement = (isOnItsColor ? -8 : 20) ;
				setSpeedModifier(speedMod/2);
				dmgTicks += dmgTickIncrement;
				if(dmgTicks < 0)dmgTicks = 0;
				if(dmgTicks >= 20){	
					//					if(isHighInk){
					//						Player painter = Bukkit.getPlayer(highInkBlocks.get(b).getFirst());
					//						if(painter != null){
					//							getPlayer().damage(1, painter);
					//						}else{
					//							getPlayer().damage(1);	
					//						}
					//					}else{
					//					}
					getPlayer().damage(1);						
					dmgTicks = 0;
				}
			}
			shieldTicks--;
			super.tick();
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
			donarItemsInicials(getPlayer()); //Clears and gives starting (colored) armor
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
			if(b.getType() == Material.WALL_SIGN){
				openWeaponSelectionMenu(p); //For service stations
			}
		}
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		super.onPlayerDeath(evt, killed);
		getPlayerInfo(killed).registerDeath();
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
			setOwnedBlocks(getOwnedBlocks() + increase);
		}
		public double getOwnedPercent(){
			if(getTotalPaintedBlocks() == 0)return 0;
			return ((double)getOwnedBlocks() / getTotalPaintedBlocks()) * 100;
		}
	}
}
