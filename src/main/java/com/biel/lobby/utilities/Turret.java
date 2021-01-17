package com.biel.lobby.utilities;

import java.util.ArrayList;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
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
	public Boolean getAutoUpgrade() {
		return autoUpgrade;
	}
	public void setAutoUpgrade(Boolean autoUpgrade) {
		this.autoUpgrade = autoUpgrade;
	}
	String invString = "";
	private int taskId;
	private int taskEscutId;
	//Estats
	public int VelAtac = 22;
	public int Atac = 2;
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
	public Boolean canBuild(){
		defineTurretBlocks();
		for (Location loc : TurretBlocks){
			//Bukkit.broadcastMessage(Integer.toString(loc.getBlock().getType().getId()));
			if (loc.getBlock().getType() != Material.AIR){
				return false;
			}
			if(loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.LEGACY_LEAVES){
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
			loc.getBlock().setType(Material.LEGACY_NETHER_FENCE);
			TurretBlocks.add(loc.clone());
			loc.setY(loc.getY() + 1);
			loc.getBlock().setType(Material.LEGACY_REDSTONE_TORCH_ON);
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
			ArrayList <Byte> dirs = new ArrayList<>();
			dirs.add((byte) 0x2);
			dirs.add((byte) 0x3);
			dirs.add((byte) 0x4);
			dirs.add((byte) 0x5);
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
				block.setType(Material.LEGACY_WALL_SIGN);
				Sign sign = (Sign)block.getState();
				if (creador != null){
					sign.setLine(1,creador.getName());
				}else{
					//sign.setLine(1,"---------------");
					//sign.setLine(2,"---------------");
				}

				sign.update();
//              TODO UPDATE
//				block.setData(dirs.get(faces.indexOf(face)));
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
		taskEscutId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            //Bukkit.broadcastMessage("CD acabat!");

            hpEscut = maxHpEscut;
            if (CheckArmor()){
                world.playSound(location, Sound.BLOCK_PISTON_EXTEND, 3F, 1F);
            }

        }, CD * 20);
		//Bukkit.broadcastMessage("CD init: " + Integer.toString(CD));
	}
	public void randomPotionAttack(int attacks, int power){
		ArrayList<Integer> chosen = new ArrayList<>();
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

			ArrayList <BlockFace> faces = new ArrayList<>();
			faces.add(BlockFace.NORTH);
			faces.add(BlockFace.SOUTH);
			faces.add(BlockFace.WEST);
			faces.add(BlockFace.EAST);
			for (BlockFace face : faces){
				Location iLoc = location.clone();
				int range = 8 + power;
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
		Potion pot = new Potion(type).splash();
		ItemStack stack1 = pot.toItemStack(1);
		potion.setItem(stack1);
		potion.setShooter(creador);
	}
	public void tirarPoció(final Location loc, final PotionType type, int delay){
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> tirarPoció(loc, type), delay);
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
		taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run() {
				checkIntegrity();
				LivingEntity target = getTarget();
				if (target != null){
					if (tirs >= 12 && getByTipus(TipusMillora.MECÀNICA).lvl > 0){
						AtacEspecial();
						tirs = 0;
						return;
					}
					if (tirsquim >= 3 && getByTipus(TipusMillora.QUÍMICA).lvl > 0 ){//getTargets().size() > 4
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
					arrow.setShooter(creador);
					if (foc == true){arrow.setFireTicks(20); world.playEffect(spawnpoint, Effect.MOBSPAWNER_FLAMES, 0);}
					arrow.setMetadata("Tower", new FixedMetadataValue(plugin, id));
					arrow.setMetadata("Special", new FixedMetadataValue(plugin, false));
					arrow.setBounce(false);
					arrow.setKnockbackStrength(0);
					
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
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
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

		if (hp <= 0){
			world.createExplosion(loceffect.getX(), loceffect.getY(), loceffect.getZ(), 4.6F, false, false);
			Stop();
			Destroy();
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
		// TODO Auto-generated method stub
		super.onProjectileHit(evt, proj);


		if (evt.getEntity() instanceof Arrow){
			Arrow entity = (Arrow)evt.getEntity();

			World world = entity.getWorld();
			Location loc = entity.getLocation();


			LivingEntity shooter = (LivingEntity) proj.getShooter();

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
					if(GUtils.isValidSolidBlock(hitBlock)){ break;}
				}
				if (hitBlock != null) {
					//land.getBlock().setType(Material.IRON_BLOCK);
					if (ContainsTurretBlock(hitBlock.getLocation())) {
						Boolean hit = true;
						if (equip != null) {
							if (equip.getPlayers().contains(player) == true) {
								hit = false;
							}
						} else {
							if (player == creador) {
								hit = false;
							}
						}
						arrow.setBounce(hit);
						if (hit) {
							Hit(10);
							player.playSound(player.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 0.9F);
							arrow.remove();
						}

					}
				}

			}

		}
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
			Inventory inv = Bukkit.getServer().createInventory(plyr, 9, invString);
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
			if (ContainsTurretBlock(evt.getClickedBlock().getLocation())){
				Hit(3);		
			}
		}

	
	}
	@Override
	protected void onInventoryClick(InventoryClickEvent evt, Inventory inv) {
		// TODO Auto-generated method stub
		super.onInventoryClick(evt, inv);

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
			Cost = 70;
			max = 1;
			break;
			case DIST_ATAC:  name = "Distància atac";
			Description = "+2 blocs dist. atac";
			material = Material.BOW;
			Cost = 30;
			break;
			case RESISTÈNCIA:  name = "Escut protector";
			Description = "-4s Regen. Escut, +6Hp escut, +2Hp";
			material = Material.IRON_CHESTPLATE;
			Cost = 42;
			max = 20;
			break;
			case QUÍMICA:  name = "Química";
			Description = "Habilitats amb pocions";
			material = Material.LEGACY_BREWING_STAND_ITEM;
			Cost = 50;
			break;
			case MECÀNICA:  name = "Mecànica avançada";
			Description = "Habilitats especials cada 10 tirs";
			material = Material.LEGACY_PISTON_BASE;
			Cost = 100;
			max = 5;
			break;
			case MAGNETISME:  name = "Magnetisme";
			Description = "Els atacs atreuen i alenteixen a l'enemic";
			material = Material.IRON_INGOT;
			Cost = 22;
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



