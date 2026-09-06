package com.biel.lobby.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.events.EventBus;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.Torres;

public class Turret extends EventBus {
	final lobby plugin;
	int id = 0;
	Torres joc = null;
	Boolean headless = false;
	final Location location;
	final World world;
	final Player creador;
	Equip equip = null;
	final ArrayList<Location> TurretBlocks = new ArrayList<>();
	final ArrayList<Location> ArmorBlocks = new ArrayList<>();
	private ArrayList<Millora> Millores = new ArrayList<>();
	int tirs = 0;
	int tirsquim = 0;
	public int xp = 0;
	public boolean destroyed = false;
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	public int hp = 50;
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	int hpEscut = 0;
	public Boolean damaged = false;
	public Boolean hasInventory = true;
	public Boolean linkCreador = false;
	public Boolean isAdmin = false;
	public Boolean built = false;
	public Boolean autoUpgrade = false;
	/** A permanent turret is never worn down by the siege phase (the base laser). */
	public boolean permanent = false;
	private static final int ARROW_HIT_DAMAGE = 15;
	/** Hit points a placed turret gains per Escut protector level; the template's own hp is never shot at. */
	public static final int SHIELD_UPGRADE_HP_BONUS = 2;
	/** Both special attacks fire on this shot count instead of an arrow. */
	private static final int SPECIAL_ATTACK_EVERY_SHOTS = 12;
	private static final int MELEE_HIT_DAMAGE = 5;
	private static final long MELEE_HIT_INTERVAL_MILLIS = 1000;
	private final Map<UUID, Long> lastMeleeHitMillis = new HashMap<>();
	public Boolean getAutoUpgrade() {
		return autoUpgrade;
	}
	public void setAutoUpgrade(Boolean autoUpgrade) {
		this.autoUpgrade = autoUpgrade;
	}
	String invString = "";
	private static final class TurretInventoryHolder implements InventoryHolder {
		private final Turret turret;
		private Inventory inventory;

		private TurretInventoryHolder(Turret turret) {
			this.turret = turret;
		}

		@Override
		public Inventory getInventory() {
			return inventory;
		}
	}
	private int taskId;
	private int taskEscutId;
	//Estats
	public int VelAtac = 22;
	/** Raw arrow damage in half hearts, before the game's damage multiplier. 7 is a fully drawn vanilla bow. */
	public int Atac = 7;
	public int distAtac = 14;
	public int xpPerTir = 1;
	public int maxHpEscut = 0;
	public int tempsEscut = 70;
	public Boolean foc = false;
	//------
	public Turret(lobby plugin, int id, Location location, Player creador, Torres joc, Equip equip, Boolean headless, Boolean admin) {
		this.plugin = plugin;
		this.location = location;
		this.world = location.getWorld();
		this.creador = creador;
		this.joc = joc;
		this.id = id;
		this.equip = equip;
		this.headless = headless;
		this.isAdmin = admin;
		//		Bukkit.broadcastMessage(this.toString());
		//		Bukkit.broadcastMessage(Integer.toString(id));

		inicialitzarMillores();

	}
	public Location getLocation(){
		return location.clone();
	}
	public int getUpgradeHpBonus(){
		return SHIELD_UPGRADE_HP_BONUS * getByTipus(TipusMillora.RESISTÈNCIA).lvl;
	}
	public static Turret createTurret(lobby plugin, Location location, Player creador, Torres joc, Equip equip, Boolean headless, Boolean admin){
		getTurrets(joc).add(new Turret(plugin, getTurrets(joc).size(), location, creador, joc, equip, headless, admin));
		return getTurrets(joc).get(getTurrets(joc).size() - 1);
	}
	public static Turret getTurret(Torres joc, int id){
		for (Turret t : getTurrets(joc)){
			if (t.id == id){
				return t;
			}
		}
		return null;
	}
	public static ArrayList<Turret> getTurrets(Torres joc) {
		return joc.Turrets;
	}
	/** The turret that fired this arrow, or null when the entity is not a turret arrow or the turret is gone. */
	public static Turret fromArrow(Torres joc, Entity entity){
		if (!(entity instanceof Arrow arrow) || !arrow.hasMetadata("Tower")) return null;
		for (MetadataValue value : arrow.getMetadata("Tower")){
			return getTurret(joc, value.asInt());
		}
		return null;
	}
	public static Turret getAdmin(Torres joc, Player plyr){
		for (Turret t : getTurrets(joc)){
			if (t.creador == plyr && t.isAdmin == true){
				return t;
			}
		}
		return null;
	}
	public Turret getAdmin(){
		if (linkCreador == true){
			for (Turret t : joc.Turrets){
				if (t.creador == creador && t.isAdmin == true){
					return t;
				}
			}
		}
		return null;
	}
	public void updateChildStats(){
		ArrayList<Turret> turrets = joc.Turrets;
		if (turrets == null)return;
		for (Turret t : turrets){
			if (t.creador == creador && t.isAdmin == false){
				//Stats
				t.Atac = Atac;
				boolean fireRateChanged = t.VelAtac != VelAtac;
				t.VelAtac = VelAtac;
				t.distAtac = distAtac;
				t.xpPerTir = xpPerTir;
				t.foc = foc;
				t.maxHpEscut = maxHpEscut;
				t.tempsEscut =  tempsEscut;
				t.xp = xp;
				//Millores
				t.Millores = Millores;
				//altres
				t.resetArmorCD();
				if (fireRateChanged && t.built){ // The firing task has a fixed period; only a restart applies the new one
					t.Stop();
					t.Attack();
				}
			}
		}
	}
	public void defineTurretBlocks(){
		TurretBlocks.clear();
		Location loc = location.clone();
		TurretBlocks.add(loc.clone());
		loc.setY(loc.getY() + 1);		
		TurretBlocks.add(loc.clone());
		loc.setY(loc.getY() + 1);		
		TurretBlocks.add(loc.clone());
	}
	public Boolean canBuild(){
		defineTurretBlocks();
		for (Location loc : TurretBlocks){
			//Bukkit.broadcastMessage(Integer.toString(loc.getBlock().getType().getId()));
			if (loc.getBlock().getType() != Material.AIR){
				return false;
			}
			if(Tag.LEAVES.isTagged(loc.getBlock().getRelative(BlockFace.DOWN).getType())){
				return false;
			}
		}
		return true;
	}
	public void Build(){
		if (headless == false){
			if (canBuild() == false){built = false; return;}

			Material mat = Material.GOLD_BLOCK;
			if(equip.getId() == 0){mat = Material.REDSTONE_BLOCK;}else{mat = Material.LAPIS_BLOCK;}
			TurretBlocks.clear();
			Location loc = location.clone();
			loc.getBlock().setType(mat);
			TurretBlocks.add(loc.clone());
			loc.setY(loc.getY() + 1);
			loc.getBlock().setType(Material.NETHER_BRICK_FENCE);
			TurretBlocks.add(loc.clone());
			loc.setY(loc.getY() + 1);
			loc.getBlock().setType(Material.SEA_LANTERN); // Solid so arrows can hit the top third; the muzzle sits above it
			TurretBlocks.add(loc.clone());
			resetArmorCD();
			built = true;
		}

	}
	public void Destroy(){
		if (headless == true){
			return;
		}
		DestroyArmor();
		for (Location loc : TurretBlocks){
			loc.getBlock().setType(Material.AIR);
		}

		//TurretBlocks.clear();
		built = false;
		resetArmorCD();
		destroyed = true;
		destroyEventBus();
	}
	public void inicialitzarMillores(){
		Millores.clear();
		for (TipusMillora tipus : TipusMillora.values()){
			Millores.add(new Millora(tipus));
		}

	}
	public Millora getByTipus(TipusMillora tipus){
		for (Millora mill : Millores){
			if (mill.tipus == tipus){
				return mill;
			}
		}
		return null;
	}

	public void Stop(){
		plugin.getServer().getScheduler().cancelTask(taskId);
	}
	public void Learn(int xpadd){
		if (linkCreador){
			Turret turr = getAdmin();
			turr.xp = turr.xp + xpadd;
		}else{
			double balancingMultiplier = 1D;
			if (creador != null) {
				balancingMultiplier = joc.getBalancingMultiplier(creador);
			}
			xp = (int) (xp + xpadd * balancingMultiplier);
		}
		if (this.autoUpgrade){AutoUpgradeRandom();}
	}
	public void AutoUpgradeRandom(){
		while (true){
			for (Millora mill : Millores){
				if (Utils.Possibilitat(20)){
					if (mill.possibleUpgrade()){
						mill.lvlUp();
					}
					return;
				}
			}
		}

	}
	public Boolean CheckArmor(){
		DestroyArmor();
		if (hpEscut <= 0){
			return false;
		}else{
			int h = 0;
			if (hpEscut > 10){
				h = 1;
			}
			BuildArmor(h);
			return true;
		}



	}
	public void BuildArmor(int height){
		int h = height;
		ArmorBlocks.clear();
		while (h >= 0){
			Location iLoc = location.clone().add(new Vector(0,h,0));
			ArrayList <BlockFace> faces = new ArrayList<>();
			faces.add(BlockFace.NORTH);
			faces.add(BlockFace.SOUTH);
			faces.add(BlockFace.WEST);
			faces.add(BlockFace.EAST);
			for (BlockFace face : faces){

				Block block = iLoc.getBlock().getRelative(face);
				if (block.getType() != Material.AIR && block.getPistonMoveReaction() != PistonMoveReaction.BREAK){
					continue;
				}
				block.setType(Material.OAK_WALL_SIGN, false);
				Directional signData = (Directional) block.getBlockData();
				signData.setFacing(face);
				block.setBlockData(signData, false);
				Sign sign = (Sign)block.getState();
				if (creador != null){
					sign.setLine(1,creador.getName());
				}else{
					//sign.setLine(1,"---------------");
					//sign.setLine(2,"---------------");
				}

				sign.update();
				ArmorBlocks.add(block.getLocation());
			}
			h = h - 1;
		}

	}
	public void DestroyArmor(){
		for(Location loc : ArmorBlocks){
			loc.getBlock().setType(Material.AIR);
		}
		ArmorBlocks.clear();
	}
	public void resetArmorCD(){
		//Bukkit.broadcastMessage("CD r! : " + Integer.toString(getByTipus(TipusMillora.RESISTÈNCIA).lvl) );
		if (built == false){setArmorCD(-1); return;}
		if (getByTipus(TipusMillora.RESISTÈNCIA).lvl > 0){
			//Bukkit.broadcastMessage("CD ir!");
			int cd = tempsEscut;
			if (damaged == false){
				cd = cd / 2 ;
			}
			setArmorCD(cd);
		}
	}
	public void setArmorCD(int CD){
		//Bukkit.broadcastMessage("CD i!");
		plugin.getServer().getScheduler().cancelTask(taskEscutId);
		if (CD < 0){return;}
		taskEscutId = joc.scheduleGameplayTask(() -> {
            //Bukkit.broadcastMessage("CD acabat!");

            hpEscut = maxHpEscut;
            if (CheckArmor()){
                world.playSound(location, Sound.BLOCK_PISTON_EXTEND, 3F, 1F);
            }

        }, CD * 20);
		//Bukkit.broadcastMessage("CD init: " + Integer.toString(CD));
	}
	private static final int POTION_EFFECT_KINDS = 3; // harming lines, poison ring, weakness ring
	public void randomPotionAttack(int attacks, int power){
		ArrayList<Integer> chosen = new ArrayList<>();
		for (int id = 0; id < POTION_EFFECT_KINDS; id++) chosen.add(id);
		java.util.Collections.shuffle(chosen);
		int attackcount = 0;
		int remainingpower = power;
		for (int id : chosen.subList(0, Math.min(attacks, POTION_EFFECT_KINDS))){
			PotionAttack(id, remainingpower, 20 * attackcount);
			attackcount++;
			remainingpower = (remainingpower / 2) + 1;
		}
	}
	public void PotionAttack(int id, int power, int delayOffSet){
		if (built == false){return;}
		if (id == 0){ //Harming

			ArrayList <BlockFace> faces = new ArrayList<>();
			faces.add(BlockFace.NORTH);
			faces.add(BlockFace.SOUTH);
			faces.add(BlockFace.WEST);
			faces.add(BlockFace.EAST);
			for (BlockFace face : faces){
				Location iLoc = location.clone();
				int range = 2 + power;
				int count = 0;
				while (count <= range){
					Block block = iLoc.getBlock().getRelative(face);
					Location loc = block.getLocation().clone();
					tirarPoció(loc, PotionType.HARMING, (3 * count) + 1 + delayOffSet);
					iLoc = loc;
					count++;
				}


			}
		}
		if (id == 1){ //Poison
			int radius = 6;
			int espai = Math.max(45, 120 - power * 15);
			ArrayList<Location> locs = Utils.getLocationsCircle(location.clone(), (double) radius, espai);
			for (Location loc : locs){
				tirarPoció(loc, PotionType.POISON, delayOffSet);
				//loc.getBlock().setType(Material.COBBLESTONE);
			}
		}
		if (id == 2){ //Poison
			int count = 0;
			int radius = 5;
			int espai = Math.max(60, 120 - power * 10);
			ArrayList<Location> locs = Utils.getLocationsCircle(location.clone(), (double) radius, espai);
			for (Location loc : locs){
				tirarPoció(loc, PotionType.WEAKNESS, delayOffSet + (count * 2));
				count++;
			}
		}
	}
	public void tirarPoció(Location loc, PotionType type){
		//Bukkit.broadcastMessage("P0ció");
		World world = Bukkit.getServer().getWorlds().get(0);
		Location spawnpoint = location.clone().add(new Location(world, 0.5, 3.2, 0.5));
		Vector rawDir = loc.toVector().subtract(spawnpoint.toVector());
		Vector dir = rawDir.normalize();
		Vector addUp = new Vector(0, rawDir.length() / 40.0,0);
		dir.add(addUp);
		ThrownPotion potion = (ThrownPotion)world.spawnEntity(spawnpoint.add(dir.multiply(0.65)), EntityType.SPLASH_POTION);
		potion.setVelocity(dir);
		ItemStack stack1 = Utils.createPotion(type, 1, true);
		potion.setItem(stack1);
		potion.setShooter(creador);
	}
	public void tirarPoció(final Location loc, final PotionType type, int delay){
		joc.scheduleGameplayTask(() -> tirarPoció(loc, type), delay);
	}
	public void checkIntegrity(){
		if(built && canBuild()){
			Build();
		}
	}
	public void Attack(){
		//if (headless = false){
		if (built == false){return;}
		//}
		taskId = joc.scheduleGameplayRepeatingTask(new Runnable(){
			public void run() {
				checkIntegrity();
				LivingEntity target = getTarget();
				if (target != null){
					if (tirs >= SPECIAL_ATTACK_EVERY_SHOTS && getByTipus(TipusMillora.MECÀNICA).lvl > 0){
						AtacEspecial();
						tirs = 0;
						return;
					}
					if (tirsquim >= SPECIAL_ATTACK_EVERY_SHOTS && getByTipus(TipusMillora.QUÍMICA).lvl > 0 ){
						int lvl = getByTipus(TipusMillora.QUÍMICA).lvl;
						randomPotionAttack(1 + lvl / 3, lvl);
						tirsquim = 0;
						return;
					}
					Location targetloc = target.getEyeLocation();
					Location spawnpoint = location.clone().add(new Location(world, 0.5, 3.2, 0.5));
					Vector horizontalToTarget = targetloc.toVector().subtract(spawnpoint.toVector()).setY(0);
					if (horizontalToTarget.lengthSquared() > 0) spawnpoint.add(horizontalToTarget.normalize().multiply(0.7));
					Vector rawDir = targetloc.toVector().subtract(spawnpoint.toVector());
					Vector dir = rawDir.normalize();
					Vector addUp = new Vector(0, rawDir.length() / 40.0,0);
					dir.add(addUp);
					Arrow arrow = (Arrow)world.spawnEntity(spawnpoint, EntityType.ARROW);
					//TNTPrimed arrow = (TNTPrimed)world.spawnEntity(spawnpoint, EntityType.TNT);
					arrow.setShooter(creador);
					if (foc == true){arrow.setFireTicks(20); world.playEffect(spawnpoint, Effect.MOBSPAWNER_FLAMES, 0);}
					arrow.setMetadata("Tower", new FixedMetadataValue(plugin, id));
					arrow.setMetadata("Special", new FixedMetadataValue(plugin, false));
					arrow.setVelocity(dir.multiply(3.4));

					world.playSound(spawnpoint, Sound.ENTITY_IRON_GOLEM_ATTACK, 1, 0.3F);
					Learn(xpPerTir);
					tirs = tirs + 1;
					tirsquim = tirsquim + 1;
					//Torxa(true);
				}else{
					//Torxa(false);
				}

			}
			LivingEntity getTarget(){

				double mindistance = distAtac;
				LivingEntity target = null;
				for(Entity en : getTargets()) {
					double heightBonus = location.getY() - en.getLocation().getY();
					if (heightBonus < 0){heightBonus = 0;}
					double distance = location.distance(en.getLocation());
					if(distance < (mindistance + heightBonus)) {
						//if(((LivingEntity) en).getNoDamageTicks() == 0){
						mindistance = distance;
						target = (LivingEntity)en;
						//}

					}


				}
				return target;
			}
			ArrayList<LivingEntity> getTargets(){
				double mindistance = distAtac;
				ArrayList<LivingEntity> targets = new ArrayList<>();
				for(Entity en : world.getEntities()) {
					if (Targetable(en)){
						double heightBonus = location.getY() - en.getLocation().getY();
						if (heightBonus < 0){heightBonus = 0;}
						double distance = location.distance(en.getLocation());
						if(distance < (mindistance + heightBonus)) {
							//mindistance = distance;
							targets.add((LivingEntity)en);
						}
					}

				}
				return targets;
			}
			void AtacEspecial(){
				//final Vector dir = plyr.getLocation().getDirection();

				final Location centerLoc = location.clone().add(0, 1.5, 0);
				int i1 = 0;
				int shoots = 1 + getByTipus(TipusMillora.MECÀNICA).lvl;
				int temps = 5;
				while (i1 < shoots){
					joc.scheduleGameplayTask(() -> {
                        int i = 0;
                        int espai = 45; // 8 arrows per ring
                        while (i < 360){
                            float angle = i;
                            double toRadians = Math.PI / 180;
                            //Location locSpawn = plyr.getLocation().add(0,1,0);
                            Location spawnpoint = centerLoc.clone().add(new Location(world,Math.cos(angle * toRadians) + 0.5, 0, Math.sin(angle * toRadians) + 0.5));

                            Vector dir2 = spawnpoint.toVector().subtract(centerLoc.toVector()).normalize().multiply(0.5);
                            Arrow arrow = (Arrow)world.spawnEntity(spawnpoint, EntityType.ARROW);
                            //Bukkit.broadcastMessage(Float.toString(plyr.getLocation().getYaw()));
                            arrow.setShooter(creador); // Team checks and kill credit follow the creator
                            arrow.setMetadata("Tower", new FixedMetadataValue(plugin, id));
                            arrow.setMetadata("Special", new FixedMetadataValue(plugin, true));
                            arrow.setFireTicks(200);
                            arrow.setVelocity(dir2.multiply(8));
                            i= i + espai;

                        }
                        world.playSound(centerLoc, Sound.BLOCK_GLASS_BREAK, 1, 1F);
                    },temps * i1);
					i1 = i1 + 1;
				}
			}

		},10, VelAtac);
	}
	Boolean Targetable(Entity en){
		if (en instanceof LivingEntity){
			if (en.isDead()){
				return false;
			}
			if (en instanceof Player){
				Player plyr = (Player)en;
				if (en == creador){
					return false;
				}
				if (equip != null){
					if (equip.getPlayers().contains(plyr)){
						return false;
					}
				}

				if (plyr.getGameMode() == GameMode.CREATIVE){
					return false;
				}

			}
			return true;
		}else{
			return false;
		}

	}
	void Hit(int damage){

		Location loceffect = location.clone().add(new Vector(Utils.NombreEntre(0, 1) * 0.3, 1.1 + Utils.NombreEntre(0, 1) * 0.3, Utils.NombreEntre(0, 1) * 0.3));
		damaged = true;
		if (hpEscut > 0){
			hpEscut = hpEscut - damage;
			Boolean armorSate = CheckArmor();
			if (armorSate == false){
				world.playSound(loceffect, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 3F, 1F);
				resetArmorCD();
			}else{
				world.playSound(loceffect, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 3F, 1F);
			}

		}else{
			hp = hp - damage;

			int i = Utils.NombreEntre(2, 8);
			world.playSound(loceffect, Sound.ENTITY_PLAYER_HURT, 3F, 1F);
			while (i >= 0){
				world.playEffect(loceffect, Effect.SMOKE, Utils.NombreEntre(0, 8));
				i = i - 1;
			}
			resetArmorCD();
		}
		destroyIfDead(loceffect);
	}
	/** Siege wear: hit points lost with no attacker, no shield involved. */
	public void decay(int damage){
		hp = hp - damage;
		Location loceffect = location.clone().add(0.5, 1.5, 0.5);
		world.playEffect(loceffect, Effect.SMOKE, 4);
		destroyIfDead(loceffect);
	}
	public void disableShield(){
		maxHpEscut = 0;
		hpEscut = 0;
		setArmorCD(-1);
		DestroyArmor();
	}
	private void destroyIfDead(Location loceffect){
		if (hp <= 0){
			world.createExplosion(loceffect.getX(), loceffect.getY(), loceffect.getZ(), 4.6F, false, false);
			Stop();
			Destroy();
		}
	}
	/** Enemies only: a team's own turrets, or a lone turret's creator, take no damage from them. */
	boolean canBeAttackedBy(Player player){
		if (equip != null) return !equip.getPlayers().contains(player);
		return player != creador;
	}
	Boolean anyUpgradePossible(){
		for (Millora mill : Millores){
			if (mill.possibleUpgrade()){
				return true;
			}
		}
		return false;
	}
	int upgradeLvlSum(){
		int r = 0;
		for (Millora mill : Millores){
			r += mill.lvl;
		}
		return r;
	}
	public Boolean ContainsTurretBlock(Location loc){
		return TurretBlocks.contains(loc) || ArmorBlocks.contains(loc);
	}
	
	@Override
	protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
		super.onProjectileHit(evt, proj);
		if (!(proj instanceof Arrow arrow)) return;
		if (arrow.hasMetadata("Tower")) return; // Turret fire does not besiege turrets
		if (!(arrow.getShooter() instanceof Player player)) return;
		Block hitBlock = evt.getHitBlock();
		if (hitBlock == null || !ContainsTurretBlock(hitBlock.getLocation())) return;
		if (!canBeAttackedBy(player)) return;
		Hit(ARROW_HIT_DAMAGE);
		player.playSound(player.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 0.9F);
		arrow.remove();
	}
	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockBreak(evt, blk);
		Player ply = evt.getPlayer();
		if (ContainsTurretBlock(blk.getLocation()) && ply.getGameMode() == GameMode.CREATIVE){
			Stop();
			Destroy();
			evt.setCancelled(true);
		}
	}
	String generateInvString(){
		String dreta = "Torre";
		if (linkCreador == true){
			dreta = dreta + "(" + creador.getName() + ")";
		}
		String strsp = "";
		String strhp = ChatColor.RED + Integer.toString(hp) + " hp";
		String strxp = " " + ChatColor.BLUE + Integer.toString(xp) + " xp";
		if (linkCreador == true){
			strxp = "";
			if(hpEscut > 0){
				strsp = ChatColor.YELLOW + Integer.toString(hpEscut) + " sp";
			}

		}
		String esquerra = strsp + strhp + strxp;
		String espais = "";
		int nespais = 32 - (dreta.length() + esquerra.length());
		int x = 0;
		while( x < nespais ) {
			espais = espais + " ";
			x = x+1;
		}
		String fin = dreta + espais + esquerra;
		if (fin.length() > 32){
			fin = fin.substring(0, 32);
		}
		return(fin);
	}
	public void openOrRefreshInventory(Player plyr){
		if (headless == false){
			invString = generateInvString();
			TurretInventoryHolder holder = new TurretInventoryHolder(this);
			Inventory inv = Bukkit.getServer().createInventory(holder, 9, invString);
			holder.inventory = inv;
			if (linkCreador == false){
				for (Millora mill : Millores){
					inv.addItem(mill.toItemStack());
				}
			}



			plyr.openInventory(inv);
		}


	}
	@Override
	protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerInteract(evt, p);

		Player plyr = evt.getPlayer();

		ItemStack stack = evt.getItem();
		Inventory pinv = plyr.getInventory();
		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (ContainsTurretBlock(evt.getClickedBlock().getLocation())){
				if (plyr.getItemInHand().getType() == Material.EXPERIENCE_BOTTLE){
					Learn(1000);
					evt.setCancelled(true);
					return;
				}
				if (hasInventory == false){
					return;
				}
				if (equip != null){
					if (equip.getPlayers().contains(plyr)){
						return;
					}
				}

				joc.pTemp().EstablirLocation("LastTurretOpen" + plyr.getName(), evt.getClickedBlock().getLocation());
				openOrRefreshInventory(plyr);
				evt.setCancelled(true);
			}
		}
		if (evt.getAction() == Action.LEFT_CLICK_BLOCK){
			if (ContainsTurretBlock(evt.getClickedBlock().getLocation()) && canBeAttackedBy(plyr)){
				long now = System.currentTimeMillis();
				Long lastHit = lastMeleeHitMillis.get(plyr.getUniqueId());
				if (lastHit != null && now - lastHit < MELEE_HIT_INTERVAL_MILLIS) return;
				lastMeleeHitMillis.put(plyr.getUniqueId(), now);
				Hit(MELEE_HIT_DAMAGE);
			}
		}

	
	}
	@Override
	protected void onInventoryClick(InventoryClickEvent evt, Inventory inv) {
		// TODO Auto-generated method stub
		super.onInventoryClick(evt, inv);
		Inventory topInventory = evt.getView().getTopInventory();
		if (!(topInventory.getHolder() instanceof TurretInventoryHolder holder) || holder.turret != this) return;

		// The upgrade panel is a control surface, not a container. Cancel every
		// transfer route before interpreting the click as an upgrade request.
		evt.setCancelled(true);
		int slot = evt.getRawSlot();
		if (slot < 0 || slot >= topInventory.getSize() || slot >= Millores.size()) return;
		ItemStack cursor = evt.getCursor();
		if (cursor != null && !cursor.getType().isAir()) return;
		if (!(evt.getWhoClicked() instanceof Player plyr)) return;

		Millora upgrade = Millores.get(slot);
		if (!upgrade.possibleUpgrade()) return;
		if (evt.isShiftClick()) {
			upgrade.upgradeMaximum();
		} else if (evt.getClick().isLeftClick() || evt.getClick().isRightClick()) {
			upgrade.lvlUp();
		} else {
			return;
		}

		if (anyUpgradePossible()) {
			openOrRefreshInventory(plyr);
		} else {
			plyr.closeInventory();
		}

		//Bukkit.broadcastMessage(evt.getAction().name());
		// TODO UPDATE
//		if (evt.getInventory().getName().equals(invString)){
//			if (evt.getRawSlot() < 9 && evt.getRawSlot() > -1){ // the top inv rawslots are numbered 0 to 53 starting top left, -999 is returned if u click outside the inv view screen
//				ItemStack itemclicked = evt.getCurrentItem();
//				ItemStack cursor = evt.getCursor();
//				evt.setCancelled(true);
//
//
//				if (cursor.getType().getId() == 0){ //if player has no item on the cursor
//					Player plyr = (Player) evt.getWhoClicked();
//
//
//					Location lastTurret;
//					if (isAdmin){
//						lastTurret = plyr.getLocation();
//					}else{
//						lastTurret = joc.pTemp().ObtenirLocation("LastTurretOpen" + plyr.getName(), world);
//					}
//
//					if (ContainsTurretBlock(lastTurret) || isAdmin){
//						//do your stuff here
//						Millora mill = Millores.get(evt.getRawSlot());
//
//						if (mill.possibleUpgrade()){
//							if (evt.getAction() == InventoryAction.PICKUP_ALL){
//								mill.lvlUp();
//
//							}
//							if (evt.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
//								mill.upgradeMaximum();
//
//							}
//							if (anyUpgradePossible() == false){
//								plyr.closeInventory();
//							}else{
//								openOrRefreshInventory(plyr);
//							}
//
//						}
//
//					}
//
//				}
//			}
//		}
	}

	@Override
	protected void onInventoryDrag(InventoryDragEvent evt, Inventory inv) {
		super.onInventoryDrag(evt, inv);
		Inventory topInventory = evt.getView().getTopInventory();
		if (topInventory.getHolder() instanceof TurretInventoryHolder holder && holder.turret == this) {
			evt.setCancelled(true);
		}
	}

	public enum TipusMillora {MAL, VELOCITAT_ATAC, FOC, DIST_ATAC, RESISTÈNCIA, QUÍMICA, MECÀNICA, MAGNETISME, APRENENTATGE};
	public class Millora{
		String name;
		String Description;
		Material material;
		int Cost;
		public int lvl;
		int max = -1;
		public TipusMillora tipus;
		public Millora(TipusMillora millora) {
			tipus = millora;
			switch (millora) {
			case MAL:  name = "Fletxes esmolades";
			Description = "+2 mal";
			material = Material.IRON_AXE;
			Cost = 25;
			max = 5;
			break;
			case VELOCITAT_ATAC:  name = "Recàrrega ràpida";
			Description = "-2 ticks de recàrrega (mín. 10)";
			Cost = 38;
			material = Material.FEATHER;
			max = 6;
			break;
			case FOC:  name = "Fletxes infernals";
			Description = "Les fletxes cremen als enemics";
			material = Material.BLAZE_POWDER;
			Cost = 70;
			max = 1;
			break;
			case DIST_ATAC:  name = "Distància atac";
			Description = "+2 blocs dist. atac";
			material = Material.BOW;
			Cost = 30;
			max = 5;
			break;
			case RESISTÈNCIA:  name = "Escut protector";
			Description = "-4s Regen. Escut, +6Hp escut, +2Hp";
			material = Material.IRON_CHESTPLATE;
			Cost = 42;
			max = 20;
			break;
			case QUÍMICA:  name = "Química";
			Description = "Pluja de pocions cada 12 tirs";
			material = Material.BREWING_STAND;
			Cost = 50;
			max = 5;
			break;
			case MECÀNICA:  name = "Mecànica avançada";
			Description = "Anells de fletxes cada 12 tirs";
			material = Material.PISTON;
			Cost = 100;
			max = 5;
			break;
			case MAGNETISME:  name = "Magnetisme";
			Description = "Les fletxes atreuen i alenteixen l'enemic";
			material = Material.IRON_INGOT;
			Cost = 22;
			max = 3;
			break;
			case APRENENTATGE:  name = "Aprenentatge";
			Description = "+1 punt d'experiència per tir";
			material = Material.BOOK;
			Cost = 75;
			max = 4;
			break;
			default:
				break;

			}
		}
		int getCost(){
			return (int) (Cost * Math.pow((((double)lvl + 1 + (upgradeLvlSum() / 4.0))), 1.2) / 2);
		}
		Boolean getMaxed(){
			if (max == -1){return false;}
			return lvl + 1 > max;
		}

		public void lvlUp(){
			lvlUp(false);
		}
		public void lvlUp(Boolean free){
			if (free == false){
				xp = xp - getCost();
			}
			lvl = lvl + 1;
			acciólvlUp();
			if(isAdmin){
				updateChildStats();
			}
		}
		void upgradeMaximum(){
			while (possibleUpgrade()){
				lvlUp(); 
			}
		}
		Boolean possibleUpgrade(){
			return getCost() <= xp && getMaxed() == false;
		}
		ItemStack toItemStack(){
			ItemStack itemstack = new ItemStack(material, 1); // A stack of diamonds
			ItemMeta meta = itemstack.getItemMeta();
			ChatColor color = ChatColor.GREEN;
			if (possibleUpgrade() == false){
				color = ChatColor.YELLOW;
			}
			String displayName = name;
			if (lvl > 0){
				String lvlStr = "(lvl " + Integer.toString(lvl) + ")";
				if (getMaxed()){
					lvlStr = lvlStr + ChatColor.RED + "(MAX)";
				}
				displayName = name + ChatColor.BLUE + lvlStr;
			}
			meta.setDisplayName(color + displayName);
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.WHITE + Description);
			if (getMaxed() == false){
				lore.add(ChatColor.AQUA + "Cost: " + Integer.toString(getCost()) + " xp");
			}

			meta.setLore(lore);
			itemstack.setItemMeta(meta);
			return itemstack;
		}
		public void acciólvlUp() {
			switch (tipus) {
			case MAL: 
				Atac = Atac + 2;
				break;
			case VELOCITAT_ATAC:  
				int nouValor = VelAtac - 2;
				if (nouValor <=10){
					nouValor = 10;
				}
				VelAtac = nouValor;
				Stop();
				Attack();
				break;
			case FOC:  
				foc = true;
				break;
			case DIST_ATAC:  name = "Distància atac";
			distAtac = distAtac + 2;
			break;
			case RESISTÈNCIA:  
				maxHpEscut = maxHpEscut + 6;
				hp = hp + SHIELD_UPGRADE_HP_BONUS;
				if (isAdmin){ // Placed turrets get the bonus too; the template's hp is never shot at
					for (Turret t : joc.Turrets){
						if (t.creador == creador && !t.isAdmin) t.hp = t.hp + SHIELD_UPGRADE_HP_BONUS;
					}
				}
				tempsEscut = tempsEscut - 4;
				if (tempsEscut < 10){
					tempsEscut = 10;
				}
				break;
			case MECÀNICA:  

				break;
			case MAGNETISME:  

				break;
			case APRENENTATGE:  
				xpPerTir = xpPerTir + 1;
				break;
			default:
				break;

			}
		}
	}

}
