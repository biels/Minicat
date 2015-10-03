package com.biel.FastSurvival.Turrets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.Material;

import java.awt.List;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Utils.GestorPropietats;
import com.biel.FastSurvival.Utils.Utils;

public class OldTurret implements Listener, Serializable {
	/**
	 * 
	 */
	static String path = "Turrets";
	static ArrayList<OldTurret> instances = new ArrayList<OldTurret>();
	public static ArrayList<OldTurret> getInstances(){
		return instances;
	}
	public static void saveInstances(){
		for(File file: new File(path).listFiles()) file.delete();
		for (OldTurret t : instances){
			//if (t.hp <= 0){continue;}
			t.Save();
		}
	}
	public static void loadInstances(){
		instances.clear();
		File[] listFiles = new File(path).listFiles();
		Bukkit.broadcastMessage("Number of files: " + Integer.toString(listFiles.length));
		for (File f : listFiles){
			GestorPropietats g = new GestorPropietats(f.getAbsolutePath());
			instances.add(new OldTurret(g));
		}
	}

	final FastSurvival plugin = FastSurvival.getPlugin();
	int id = 0;
	Boolean headless = false;
	final Location  location;
	final OfflinePlayer creador;
	int equip = 0;
	ArrayList<Location> TurretBlocks = new ArrayList<Location>();
	ArrayList<Location> ArmorBlocks = new ArrayList<Location>();
	private ArrayList<Millora> Millores = new ArrayList<Millora>();
	int tirs = 0;
	int tirsquim = 0;
	int xp = 0;
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	int hp = 50;
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	int hpEscut = 0;
	Boolean damaged = false;
	Boolean hasInventory = true;
	Boolean linkCreador = false;
	Boolean isAdmin = false;
	Boolean built = false;
	Boolean attacking = false;
	Boolean autoUpgrade = false;
	public Boolean getAutoUpgrade() {
		return autoUpgrade;
	}
	public void setAutoUpgrade(Boolean autoUpgrade) {
		this.autoUpgrade = autoUpgrade;
	}
	String invString = "--";
	private int taskId;
	private int taskEscutId;
	//Estats
	int VelAtac = 22;
	int Atac = 2;
	int distAtac = 14;
	int xpPerTir = 1;
	int maxHpEscut = 0;
	int tempsEscut = 70;
	Boolean foc = false;
	//Save-Load
	public void Save(){
		GestorPropietats p = new GestorPropietats(path + "/" + id + ".txt");
		p.deleteFile();
		//Main
		p.EstablirPropietat("id", id);
		p.EstablirPropietat("headless", headless);
		if (creador != null) {
			p.EstablirPropietat("creador", creador.getName());
		}
		p.EstablirPropietat("equip", equip);
		p.EstablirLocation("location", location);
		p.EstablirLocations("TurretBlocks", TurretBlocks);
		p.EstablirLocations("ArmorBlocks", ArmorBlocks);
		ArrayList<String> lvls = new ArrayList<String>();
		for(Millora m : Millores){
			lvls.add(Integer.toString(m.lvl));
		}
		p.EstablirLlista("lvls", lvls);
		//Props
		p.EstablirPropietat("tirs", tirs);
		p.EstablirPropietat("tirsquim", tirsquim);
		p.EstablirPropietat("xp", xp);
		p.EstablirPropietat("hp", hp);
		//
		p.EstablirPropietat("hpEscut", hpEscut);
		p.EstablirPropietat("damaged", damaged);
		p.EstablirPropietat("hasInventory", hasInventory);
		p.EstablirPropietat("linkCreador", linkCreador);
		p.EstablirPropietat("isAdmin", isAdmin);
		p.EstablirPropietat("built", built);
		p.EstablirPropietat("attacking", attacking);
		p.EstablirPropietat("autoUpgrade", autoUpgrade);
		//Stats
		p.EstablirPropietat("VelAtac", VelAtac);
		p.EstablirPropietat("Atac", Atac);
		p.EstablirPropietat("distAtac", distAtac);
		p.EstablirPropietat("xpPerTir", xpPerTir);
		p.EstablirPropietat("maxHpEscut", maxHpEscut);
		p.EstablirPropietat("tempsEscut", tempsEscut);
		p.EstablirPropietat("foc", foc);
		//Altres
		p.EstablirPropietat("invString", invString);

	}
	public OldTurret(GestorPropietats p) {
		this.id = p.ObtenirPropietatInt("id");
		this.headless = p.ObtenirPropietatBoolean("headless");
		this.creador = Bukkit.getOfflinePlayer(p.ObtenirPropietat("creador"));
		this.equip = p.ObtenirPropietatInt("equip");
		this.location = p.ObtenirLocation("location");
		this.TurretBlocks = p.ObtenirLocations("TurretBlocks");
		this.ArmorBlocks = p.ObtenirLocations("ArmorBlocks");
		inicialitzarMillores();
		//		String[] lvls = p.ObtenirLlista("lvls");
		//		for(Millora m : Millores){
		//			m.lvl = Integer.parseInt(lvls[Millores.indexOf(m)]);
		//		}
		this.tirs = p.ObtenirPropietatInt("tirs");
		this.tirsquim = p.ObtenirPropietatInt("tirsquim");
		this.xp = p.ObtenirPropietatInt("xp");
		this.hp = p.ObtenirPropietatInt("hp");
		//
		this.hpEscut = p.ObtenirPropietatInt("hpEscut");
		this.damaged = p.ObtenirPropietatBoolean("damaged");
		this.hasInventory = p.ObtenirPropietatBoolean("hasInventory");
		this.linkCreador = p.ObtenirPropietatBoolean("linkCreador");
		this.isAdmin = p.ObtenirPropietatBoolean("isAdmin");
		this.built = p.ObtenirPropietatBoolean("built");
		this.attacking = p.ObtenirPropietatBoolean("attacking");
		this.autoUpgrade = p.ObtenirPropietatBoolean("autoUpgrade");
		//Stats
		this.VelAtac = p.ObtenirPropietatInt("VelAtac");
		this.Atac = p.ObtenirPropietatInt("Atac");
		this.distAtac = p.ObtenirPropietatInt("distAtac");
		this.xpPerTir = p.ObtenirPropietatInt("xpPerTir");
		this.maxHpEscut = p.ObtenirPropietatInt("maxHpEscut");
		this.tempsEscut = p.ObtenirPropietatInt("tempsEscut");
		this.foc = p.ObtenirPropietatBoolean("foc");
		//Altres
		this.invString = p.ObtenirPropietat("invString");

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		if (built)this.Build();
		if (attacking)this.Attack();
	}
	//------
	public OldTurret(int id, Location location, Player creador, Boolean headless, Boolean admin) {
		this.location = location;
		this.creador = creador;
		this.id = id;
		//this.equip = equip;
		this.headless = headless;
		this.isAdmin = admin;
		//		Bukkit.broadcastMessage(this.toString());
		//		Bukkit.broadcastMessage(Integer.toString(id));

		plugin.getServer().getPluginManager().registerEvents( this, plugin);
		inicialitzarMillores();

	}
	public static OldTurret createTurret(Location location, Player creador, Boolean headless, Boolean admin){
		instances.add(new OldTurret(instances.size(), location, creador, headless, admin));
		return instances.get(instances.size() - 1);
	}
	public static OldTurret getTurret(int id){
		for (OldTurret t : getInstances()){
			if (t.id == id){
				return t;
			}
		}
		return null;
	}
	public static OldTurret getAdmin(Player plyr){
		for (OldTurret t : getInstances()){
			if (t.creador == plyr && t.isAdmin == true){
				return t;
			}
		}
		return null;
	}
	public static ItemStack getSimpleItemStack(){
		ItemStack i = new ItemStack(Material.ARROW);
		i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
		return Utils.setItemName(i, ChatColor.YELLOW + "Torre de defensa");
	}
	public OldTurret getAdmin(){
		if (linkCreador == true){
			for (OldTurret t : getInstances()){
				if (t.creador == creador && t.isAdmin == true){
					return t;
				}
			}
		}
		return null;
	}
	public void updateChildStats(){
		for (OldTurret t : getInstances()){
			if (t.creador == creador && t.isAdmin == false){
				//Stats
				t.Atac = Atac;
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
	public static Boolean canBeBuiltAt(Location loc){
		ArrayList<Location> TurretBlocks = new ArrayList<Location>();
		TurretBlocks.add(loc.clone());
		loc.setY(loc.getY() + 1);		
		TurretBlocks.add(loc.clone());
		loc.setY(loc.getY() + 1);		
		TurretBlocks.add(loc.clone());
		for (Location l : TurretBlocks){
			//Bukkit.broadcastMessage(Integer.toString(loc.getBlock().getType().getId()));
			if (l.getBlock().getType() != Material.AIR){

				return false;
			}
		}
		return true;
	}
	public Boolean canBuild(){
		defineTurretBlocks();
		for (Location loc : TurretBlocks){
			//Bukkit.broadcastMessage(Integer.toString(loc.getBlock().getType().getId()));
			if (loc.getBlock().getType() != Material.AIR){

				return false;
			}
		}
		return true;
	}
	public void Build(){
		if (headless == false){
			if (canBuild() == false){built = false; return;}

			Material mat = Material.GOLD_BLOCK;
			if(equip == 1){mat = Material.REDSTONE_BLOCK;}else{mat = Material.LAPIS_BLOCK;}
			TurretBlocks.clear();
			Location loc = location.clone();
			loc.getBlock().setType(mat);
			TurretBlocks.add(loc.clone());
			loc.setY(loc.getY() + 1);
			loc.getBlock().setType(Material.NETHER_FENCE);
			TurretBlocks.add(loc.clone());
			loc.setY(loc.getY() + 1);
			loc.getBlock().setType(Material.REDSTONE_TORCH_ON);
			TurretBlocks.add(loc.clone());
			resetArmorCD();
			built = true;
		}

	}
	private World getWorld() {
		return FastSurvival.getWorld();
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
		attacking = false;
	}
	public void Learn(int xpadd){
		if (linkCreador){
			OldTurret turr = getAdmin();
			turr.xp = turr.xp + xpadd;
		}else{
			xp = xp + xpadd;
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
			ArrayList <Byte> dirs = new ArrayList <Byte>();
			dirs.add((byte) 0x2);
			dirs.add((byte) 0x3);
			dirs.add((byte) 0x4);
			dirs.add((byte) 0x5);
			ArrayList <BlockFace> faces = new ArrayList <BlockFace>();
			faces.add(BlockFace.NORTH);
			faces.add(BlockFace.SOUTH);
			faces.add(BlockFace.WEST);
			faces.add(BlockFace.EAST);
			for (BlockFace face : faces){

				Block block = iLoc.getBlock().getRelative(face);
				if (block.getType() != Material.AIR && block.getPistonMoveReaction() != PistonMoveReaction.BREAK){
					continue;
				}
				block.setType(Material.WALL_SIGN);
				Sign sign = (Sign)block.getState();
				if (creador != null){
					sign.setLine(1,creador.getName());
				}else{
					//sign.setLine(1,"---------------");
					//sign.setLine(2,"---------------");
				}

				sign.update();

				block.setData(dirs.get(faces.indexOf(face)));
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
		taskEscutId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			public void run() {
				//Bukkit.broadcastMessage("CD acabat!");

				hpEscut = maxHpEscut;
				if (CheckArmor()){
					getWorld().playSound(location, Sound.PISTON_EXTEND, 3F, 1F);
				}

			}
		}, CD * 20);
		//Bukkit.broadcastMessage("CD init: " + Integer.toString(CD));
	}
	public void randomPotionAttack(int attacks, int power){
		ArrayList<Integer> chosen = new ArrayList<Integer>();
		int count = 0;
		while (count <= attacks){
			int maxid = 2;
			int n = -1;
			while (n == -1){
				int newnumber = Utils.NombreEntre(0, maxid);
				if (!chosen.contains(newnumber)){
					n = newnumber;
				}
			}
			chosen.add(n);
			count++;
		}
		int attackcount = 0;
		int remainingpower = power;
		for (int id : chosen){
			PotionAttack(id, remainingpower, 20 * attackcount);
			attackcount++;
			remainingpower = (remainingpower / 2) + 1;
		}
	}
	public void PotionAttack(int id, int power, int delayOffSet){
		if (built == false){return;}
		if (id == 0){ //Harming

			ArrayList <BlockFace> faces = new ArrayList <BlockFace>();
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
					tirarPoció(loc, PotionType.INSTANT_DAMAGE, (3 * count) + 1 + delayOffSet);
					iLoc = loc;
					count++;
				}


			}
		}
		if (id == 1){ //Poison
			int radius = 6;
			int espai = 40 - power;
			if (espai <= 8){espai = 8;}
			ArrayList<Location> locs = Utils.getLocationsCircle(location.clone(), (double) radius, espai);
			for (Location loc : locs){
				tirarPoció(loc, PotionType.POISON, delayOffSet);
				//loc.getBlock().setType(Material.COBBLESTONE);
			}
			location.getBlock().setType(Material.BEDROCK);
		}
		if (id == 2){ //Poison
			int count = 0;
			int radius = 5;
			int espai = 40 - (power * 2);
			if (espai <= 5){espai = 5;}
			ArrayList<Location> locs = Utils.getLocationsCircle(location.clone(), (double) radius, espai);
			for (Location loc : locs){
				tirarPoció(loc, PotionType.WEAKNESS, delayOffSet + (count * 2));
				count++;
			}
		}
		if (id == 3){ //Slow
			int count = 0;
			int radius = 8;
			int espai = 40 - (power * 2);
			if (espai <= 5){espai = 5;}
			ArrayList<Location> locs = Utils.getLocationsCircle(location.clone(), (double) radius, espai);
			for (Location loc : locs){
				tirarPoció(loc, PotionType.WEAKNESS, delayOffSet + (count * 2));
				tirarPoció(loc, PotionType.WEAKNESS, delayOffSet - (count * 2));
				count++;
			}
		}
	}
	public void tirarPoció(Location loc, PotionType type){
		World world = Bukkit.getServer().getWorlds().get(0);
		Location spawnpoint = location.clone().add(new Location(world, 0.5, 3.2, 0.5));
		Vector rawDir = loc.toVector().subtract(spawnpoint.toVector());
		Vector dir = rawDir.normalize();
		Vector addUp = new Vector(0, rawDir.length() / 40.0,0);
		dir.add(addUp);
		ThrownPotion potion = (ThrownPotion)world.spawnEntity(spawnpoint.add(dir.multiply(0.65)), EntityType.SPLASH_POTION);
		potion.setVelocity(dir);
		Potion pot = new Potion(type).splash();
		ItemStack stack1 = pot.toItemStack(1);
		potion.setItem(stack1);
		potion.setShooter(creador.getPlayer());
	}
	public void tirarPoció(final Location loc, final PotionType type, int delay){
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				tirarPoció(loc, type);
			}
		}, delay);
	}
	public void Attack(){
		//if (headless = false){
		if (built == false){return;}
		//}
		attacking = true;
		Bukkit.broadcastMessage(Integer.toString(id) + ": " + "Attacking");
		taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run() {
				World world = plugin.getServer().getWorlds().get(0);
				LivingEntity target = getTarget();
				if (target != null){
					if (tirs >= 12 && getByTipus(TipusMillora.MECÀNICA).lvl > 0){
						AtacEspecial();
						tirs = 0;
						return;
					}
					if (tirsquim >= 5 && getByTipus(TipusMillora.QUÍMICA).lvl > 0 && getTargets().size() > 4){
						int lvl = getByTipus(TipusMillora.QUÍMICA).lvl;
						randomPotionAttack((int) (1 + Math.rint(lvl/3)), lvl * 3 + 1);
						tirsquim = 0;
						return;
					}
					Location targetloc = getTarget().getEyeLocation();
					Location spawnpoint = location.clone().add(new Location(world, 0.5, 2.6, 0.5));
					//            		if (headless = false){
					//            			spawnpoint = spawnpoint.add(new Vector(0, 2, 0));
					//            		}
					Vector rawDir = targetloc.toVector().subtract(spawnpoint.toVector());
					Vector dir = rawDir.normalize();
					Vector addUp = new Vector(0, rawDir.length() / 40.0,0);
					dir.add(addUp);
					Arrow arrow = (Arrow)world.spawnEntity(spawnpoint, EntityType.ARROW);
					//TNTPrimed arrow = (TNTPrimed)world.spawnEntity(spawnpoint, EntityType.PRIMED_TNT);
					arrow.setShooter(creador.getPlayer());
					if (foc == true){arrow.setFireTicks(20); world.playEffect(spawnpoint, Effect.MOBSPAWNER_FLAMES, 0);}
					arrow.setMetadata("Tower", new FixedMetadataValue(plugin, id));
					arrow.setMetadata("Special", new FixedMetadataValue(plugin, false));
					arrow.setBounce(false);

					arrow.setVelocity(dir.multiply(3.4));

					world.playSound(spawnpoint, Sound.IRONGOLEM_HIT, 1, 0.3F);
					Learn(xpPerTir);
					tirs = tirs + 1;
					tirsquim = tirsquim + 1;
					//Torxa(true);
				}else{
					//Torxa(false);
				}

			}
			LivingEntity getTarget(){
				World world = plugin.getServer().getWorlds().get(0);
				double mindistance = distAtac;
				LivingEntity target = null;
				for(Entity en : getTargets()) {
					double heightBonus = location.getY() - en.getLocation().getY();
					heightBonus = heightBonus /5;
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
				World world = plugin.getServer().getWorlds().get(0);
				double mindistance = distAtac;
				ArrayList<LivingEntity> targets = new ArrayList<LivingEntity>();
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
				final World world = plugin.getServer().getWorlds().get(0);
				final Location centerLoc = location.clone().add(0, 1.5, 0);
				int i1 = 0;
				int shoots = 1 + getByTipus(TipusMillora.MECÀNICA).lvl;
				int temps = 5;
				while (i1 < shoots){
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							int i = 0;
							int espai = 32 - (getByTipus(TipusMillora.MECÀNICA).lvl * 4);
							while (i <= 360){
								float angle = i;
								double toRadians = Math.PI / 180;
								//Location locSpawn = plyr.getLocation().add(0,1,0);
								Location spawnpoint = centerLoc.clone().add(new Location(world,Math.cos(angle * toRadians) + 0.5, 0, Math.sin(angle * toRadians) + 0.5));

								Vector dir2 = spawnpoint.toVector().subtract(centerLoc.toVector()).normalize().multiply(0.5);
								Arrow arrow = (Arrow)world.spawnEntity(spawnpoint, EntityType.ARROW);
								//Bukkit.broadcastMessage(Float.toString(plyr.getLocation().getYaw()));
								//arrow.setShooter(creador);
								//arrow.setItem(item)
								arrow.setMetadata("Tower", new FixedMetadataValue(plugin, id));
								arrow.setMetadata("Special", new FixedMetadataValue(plugin, true));
								arrow.setFireTicks(200);
								arrow.setVelocity(dir2.multiply(8));
								i= i + espai;

							}
							world.playSound(centerLoc, Sound.GLASS, 1, 1F);
						}
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
		final World world = plugin.getServer().getWorlds().get(0);
		Location loceffect = location.clone().add(new Vector(Utils.NombreEntre(0, 1) * 0.3, 1.1 + Utils.NombreEntre(0, 1) * 0.3, Utils.NombreEntre(0, 1) * 0.3));
		damaged = true;
		if (hpEscut > 0){
			hpEscut = hpEscut - damage;
			Boolean armorSate = CheckArmor();
			if (armorSate == false){
				world.playSound(loceffect, Sound.ZOMBIE_WOODBREAK, 3F, 1F);
				resetArmorCD();
			}else{
				world.playSound(loceffect, Sound.ZOMBIE_WOOD, 3F, 1F);
			}

		}else{
			hp = hp - damage;

			int i = Utils.NombreEntre(2, damage + 3);
			world.playSound(loceffect, Sound.HURT_FLESH, 3F, 1F);
			while (i >= 0){
				world.playEffect(loceffect, Effect.SMOKE, Utils.NombreEntre(0, 8));
				i = i - 1;
			}
			resetArmorCD();
		}

		if (hp <= 0){
			world.createExplosion(loceffect.getX(), loceffect.getY(), loceffect.getZ(), 4.8F, false, false);
			Destroy();
			Stop();
		}
	}
	Boolean anyUpgradePossible(){
		for (Millora mill : Millores){
			if (mill.possibleUpgrade()){
				return true;
			}
		}
		return false;
	}
	public Boolean ContainsTurretBlock(Location loc){
		return TurretBlocks.contains(loc) || ArmorBlocks.contains(loc);
	}
	@EventHandler
	public void onHit(ProjectileHitEvent evt) {

		if (evt.getEntity() instanceof Arrow){
			Arrow entity = (Arrow)evt.getEntity();

			World world = entity.getWorld();
			Location loc = entity.getLocation();

			Projectile proj = (Projectile)entity;

			ProjectileSource source = proj.getShooter();
			LivingEntity shooter;
			if(source instanceof LivingEntity){
				shooter = (LivingEntity) source;
			}

			//Location land = loc.add(entity.getVelocity().normalize().multiply(0.8));
			Arrow arrow = (Arrow)proj;
			if((arrow.getShooter() instanceof Player)){
				Player player = (Player)arrow.getShooter();
				World world1 = arrow.getWorld();
				BlockIterator iterator = new BlockIterator(world1, arrow.getLocation().toVector(), arrow.getVelocity().normalize(), 0, 4);
				Block hitBlock = null;

				while(iterator.hasNext()) {
					hitBlock = iterator.next();
					// hitBlock.breakNaturally();
					if(hitBlock.getTypeId()!=0) //Check all non-solid blockid's here.
					{ break;}
				}
				//land.getBlock().setType(Material.IRON_BLOCK);
				if (ContainsTurretBlock(hitBlock.getLocation())){
					Boolean hit = true;

					if (player == creador){
						hit = false;
					}

					if (hit){
						Hit(10);
					}

				}

			}

		}

	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		World world = plugin.getServer().getWorlds().get(0);
		Player ply = evt.getPlayer();
		Block blk = evt.getBlock();
		Boolean containsTurretBlock = ContainsTurretBlock(blk.getLocation());
		if (containsTurretBlock && ply.getGameMode() == GameMode.CREATIVE){
			Stop();
			Destroy();
			evt.setCancelled(true);
		}
		if (containsTurretBlock  && ply.getGameMode() != GameMode.CREATIVE){
			if (ply == creador){
				Stop();
				Destroy();
			}
			evt.setCancelled(true);
		}



	}
	void Drop(){

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
			Inventory inv = Bukkit.getServer().createInventory(plyr, 9, invString);
			if (linkCreador == false){
				for (Millora mill : Millores){
					inv.addItem(mill.toItemStack());
				}
			}



			plyr.openInventory(inv);
		}


	}
	@EventHandler
	public void onInteract(PlayerInteractEvent evt) {
		Player plyr = evt.getPlayer();
		World world = plugin.getServer().getWorlds().get(0);
		ItemStack stack = evt.getItem();
		Inventory pinv = plyr.getInventory();
		Boolean containsTurretBlock = false;
		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK || evt.getAction() == Action.LEFT_CLICK_BLOCK){
			containsTurretBlock = ContainsTurretBlock(evt.getClickedBlock().getLocation());
		}
		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (containsTurretBlock){
				if (plyr.getItemInHand().getType() == Material.EXP_BOTTLE){
					Learn(1000);
					evt.setCancelled(true);
					return;
				}
				if (hasInventory == false){
					return;
				}


				plugin.pTemp.EstablirLocation("LastTurretOpen" + plyr.getName(), evt.getClickedBlock().getLocation());
				openOrRefreshInventory(plyr);
				evt.setCancelled(true);
			}
		}
		if (evt.getAction() == Action.LEFT_CLICK_BLOCK){
			if (containsTurretBlock){
				int dmg = 1;
				switch (plyr.getItemInHand().getType()){

				case DIAMOND_AXE:
					dmg = 3;
					break;

				case DIAMOND_HOE:
					dmg = 2;
					break;
				case DIAMOND_LEGGINGS:
					break;
				case DIAMOND_ORE:
					break;
				case DIAMOND_PICKAXE:
					dmg = 4;
					break;
				case DIAMOND_SPADE:
					dmg = 3;
					break;
				case DIAMOND_SWORD:
					dmg = 7;
					break;

				case GOLD_AXE:
					dmg = 6;
					break;
				case IRON_SWORD:
					dmg = 5;
					break;
				case STONE_SWORD:
					dmg = 4;
					break;
				case WOOD_SWORD:
					dmg = 3;
					break;
				default:
					break;

				}
				Hit(dmg);
				evt.setCancelled(true);
			}
		}

	}
	@EventHandler
	public void inventoryclick(InventoryClickEvent evt){
		//Bukkit.broadcastMessage(evt.getAction().name());
		if (evt.getInventory().getName().equals(invString)){
			if (evt.getRawSlot() < 9 && evt.getRawSlot() > -1){ // the top inv rawslots are numbered 0 to 53 starting top left, -999 is returned if u click outside the inv view screen
				ItemStack itemclicked = evt.getCurrentItem();
				ItemStack cursor = evt.getCursor();
				evt.setCancelled(true);


				if (cursor.getTypeId() == 0){ //if player has no item on the cursor
					Player plyr = (Player) evt.getWhoClicked();
					World world = plugin.getServer().getWorlds().get(0);

					Location lastTurret;
					if (isAdmin){
						lastTurret = plyr.getLocation();
					}else{
						lastTurret = plugin.pTemp.ObtenirLocation("LastTurretOpen" + plyr.getName());
					}

					if (ContainsTurretBlock(lastTurret) || isAdmin){
						//do your stuff here
						Millora mill = Millores.get(evt.getRawSlot());

						if (mill.possibleUpgrade()){
							if (evt.getAction() == InventoryAction.PICKUP_ALL){
								mill.lvlUp();

							}
							if (evt.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY){
								mill.upgradeMaximum();

							}
							if (anyUpgradePossible() == false){
								plyr.closeInventory();
							}else{
								openOrRefreshInventory(plyr);
							}

						}

					}

				}
			}
		}

	}

	public enum TipusMillora {MAL, VELOCITAT_ATAC, FOC, DIST_ATAC, RESISTÈNCIA, QUÍMICA, MECÀNICA, MAGNETISME, APRENENTATGE};
	public class Millora implements Serializable{
		String name;
		String Description;
		Material material;
		int Cost;
		int lvl;
		int max = -1;
		public TipusMillora tipus;
		public Millora(TipusMillora millora) {
			tipus = millora;
			switch (millora) {
			case MAL:  name = "Fletxes esmolades";
			Description = "+1 mal";
			material = Material.IRON_AXE;
			Cost = 25;
			break;
			case VELOCITAT_ATAC:  name = "Recàrrega ràpida";
			Description = "+10% Velocitat d'atac";
			Cost = 38;
			material = Material.FEATHER;
			max = 10;
			break;
			case FOC:  name = "Fletxes infernals";
			Description = "Les fletxes cremen als enemics";
			material = Material.BLAZE_POWDER;
			Cost = 60;
			max = 1;
			break;
			case DIST_ATAC:  name = "Distància atac";
			Description = "+2 blocs dist. atac";
			material = Material.BOW;
			Cost = 24;
			break;
			case RESISTÈNCIA:  name = "Escut protector";
			Description = "-4s Regen. Escut, +6Hp escut, +2Hp";
			material = Material.IRON_CHESTPLATE;
			Cost = 42;
			max = 20;
			break;
			case QUÍMICA:  name = "Química";
			Description = "Habilitats amb pocions";
			material = Material.BREWING_STAND_ITEM;
			Cost = 500;
			break;
			case MECÀNICA:  name = "Mecànica avançada";
			Description = "Habilitats especials cada 10 tirs";
			material = Material.PISTON_BASE;
			Cost = 50;
			max = 5;
			break;
			case MAGNETISME:  name = "Magnetisme";
			Description = "Els atacs atreuen i alenteixen a l'enemic";
			material = Material.IRON_INGOT;
			Cost = 50;
			break;
			case APRENENTATGE:  name = "Aprenentatge";
			Description = "x2 punts d'experiència";
			material = Material.BOOK;
			Cost = 75;
			break;
			default:
				break;

			}
		}
		int getCost(){
			return (int) (Cost * (((double)lvl + 1) / 4) + Math.floor(Math.sqrt(Cost * lvl)));
		}
		Boolean getMaxed(){
			if (max == -1){return false;}
			if (lvl + 1 <= max){return false;}else{return true;}
		}

		public void lvlUp(){
			lvlUp(false);
		}
		public void setLevel(int l){
			lvl = l;
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
			if (getCost() <= xp && getMaxed() == false){
				return true;
			}else{
				return false;
			}
		}
		ItemStack toItemStack(){
			ItemStack itemstack = new ItemStack(material, 1); // A stack of diamonds
			ItemMeta meta = (ItemMeta) itemstack.getItemMeta();
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
			ArrayList<String> lore = new ArrayList<String>();
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
				hp = hp + 2;
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
				xpPerTir = xpPerTir * 2;
				break;
			default:
				break;

			}
		}
	}

}



