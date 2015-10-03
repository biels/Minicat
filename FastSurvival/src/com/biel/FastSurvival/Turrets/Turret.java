package com.biel.FastSurvival.Turrets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.avaje.ebeaninternal.server.subclass.GetterSetterMethods;
import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Turrets.OldTurret.TipusMillora;
import com.biel.FastSurvival.Utils.Utils;

public class Turret{
	public TurretData d;

	//Temp
	Location location;
	World world;
	//----
	public Turret(TurretData d) {
		super();
		if(d == null){Bukkit.broadcastMessage("NullData"); return;}
		this.d = d;
		initTemp();
	}
	public Turret(int iId) {
		super();
		this.d = new TurretData(iId);
		initTemp();
	}
	void initTemp(){
		try {
			location = d.getLocation();
			world = location.getWorld();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("NullPointer handled on init");;
		}
	}
	public void doTurretLogic(){ //1 tick = 1.5s
		if (!d.getBuilt()){return;}
		if (!d.getEnabled()){return;}
		if (!d.getLocation().getBlock().getType().isSolid()){d.setBuilt(false); System.out.println("Inv turret avoided!"); return;}
		attack();
		regen();
		d.setTicks(d.getTicks() - 1);
		d.setElapsedRegenTicks(d.getElapsedRegenTicks() - 1);
	}
	public void attack() {
		if (d.getTicks() <= 0){
			LivingEntity target = getTarget();
			if (target != null){
				//if (!canSee(location.clone().add(0, 2, 0).getBlock(), target.getEyeLocation().getBlock())){return;}
				shotArrow(target);
				d.setNormalShots(d.getNormalShots() + 1);
				if(d.getTier() == 1){

					d.setTicks(2);
				}
				if(d.getTier() == 2){
					d.setTicks(1);
					if (d.getNormalShots() >= 9){
						AtacEspecial();
						d.setTicks(8);
						d.setNormalShots(0);
					}
				}
				if(d.getTier() == 3){
					d.setTicks(1);
					if (d.getNormalShots() >= 4){
						//ArrayList<LivingEntity> nearbyEnemies = Utils.getNearbyEnemies(d.getLocation(), 8);
						//int size = nearbyEnemies.size();
						//randomPotionAttack(Utils.NombreEntre(2, 3), Utils.NombreEntre(2, 4));
						d.setTicks(6);
						d.setNormalShots(0);
					}
				}

			}
		}
	}
	public void regen() {
		if (d.getElapsedRegenTicks() <= 0){
			Repair(1);
			d.setElapsedRegenTicks(getRegenTicks());					
		}
	}
	void shotArrow(LivingEntity target){
		Location targetloc = target.getEyeLocation();
		Location spawnpoint = location.clone().add(new Location(world, 0.5, 2.6, 0.5));
		Vector rawDir = targetloc.toVector().subtract(spawnpoint.toVector());
		Vector dir = rawDir.normalize();
		Vector addUp = new Vector(0, rawDir.length() / 40.0,0);
		dir.add(addUp);
		Arrow arrow = (Arrow)world.spawnEntity(spawnpoint, EntityType.ARROW);
		Player player = Bukkit.getPlayer(d.getOwner());
		if (player != null){
			arrow.setShooter(player);
		}
		if (d.getTier() >= 2){arrow.setFireTicks(Utils.NombreEntre(2, 22)); world.playEffect(spawnpoint, Effect.MOBSPAWNER_FLAMES, 0);}
		arrow.setMetadata("Tower", new FixedMetadataValue(FastSurvival.getPlugin(), d.iId));
		arrow.setMetadata("Special", new FixedMetadataValue(FastSurvival.getPlugin(), false));
		arrow.setBounce(false);

		arrow.setVelocity(dir.multiply(3.4));

		world.playSound(spawnpoint, Sound.IRONGOLEM_HIT, 0.15F, 0.3F);
	}
	public boolean canSee(Block a, Block b)	{
		if(a.getWorld() != b.getWorld()) return false;
		Vector va = a.getLocation().toVector();
		Vector vb = b.getLocation().toVector();
		int distance = (int)a.getLocation().distance(b.getLocation());
		BlockIterator bi = new BlockIterator(a.getWorld(), va, vb.subtract(va).normalize(), 0, distance);
		while(bi.hasNext())
		{
			if(bi.next().getType() != Material.AIR)
			{
				return false;
			}
		}
		return true;
	}
	void AtacEspecial(){
		//final Vector dir = plyr.getLocation().getDirection();


		int i1 = 0;
		int shoots = 1 + Utils.NombreEntre(1, 3);
		if (Utils.Possibilitat(10)){shoots = shoots + 1;}
		int temps = 5;
		while (i1 < shoots){
			FastSurvival.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(FastSurvival.getPlugin(), new Runnable() {
				public void run() {
					Location centerLoc = location.clone().add(0, 1.5, 0);
					int i = 0;
					int espai = 32 - (Utils.NombreEntre(1, 4) * 4);
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
						arrow.setMetadata("Tower", new FixedMetadataValue(FastSurvival.getPlugin(), d.iId));
						arrow.setMetadata("Special", new FixedMetadataValue(FastSurvival.getPlugin(), true));
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
			Bukkit.broadcastMessage("MDD");
		}
	}
	public void PotionAttack(int id, int power, int delayOffSet){
		if (d.getBuilt() == false){return;}
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
		Location spawnpoint = location.clone().add(new Vector(0.5, 3.2, 0.5));
		Vector rawDir = loc.toVector().subtract(spawnpoint.toVector());
		Vector dir = rawDir.normalize();
		Vector addUp = new Vector(0, rawDir.length() / 40.0,0);
		dir.add(addUp);
		ThrownPotion potion = (ThrownPotion)world.spawnEntity(spawnpoint.add(dir.multiply(0.65)), EntityType.SPLASH_POTION);
		potion.setVelocity(dir);
		Potion pot = new Potion(type).splash();
		ItemStack stack1 = pot.toItemStack(1);
		potion.setItem(stack1);
		Player owner = Bukkit.getPlayer(d.getOwner());
		if (owner != null){
			potion.setShooter(owner);
		}

	}
	public void tirarPoció(final Location loc, final PotionType type, int delay){
		FastSurvival.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(FastSurvival.getPlugin(), new Runnable() {
			public void run() {
				tirarPoció(loc, type);
			}
		}, delay);
	}
	public Double getDamage(){
		return (5.75 + (d.getTier() / 1.25) * 3);
	}
	public Double getRange(){
		double r = 13.5 + (d.getTier());
		if (d.getNoPlayers()){r = r + 5.5 + d.getTier();}
		if (d.getTier() == 3){
			r = r + Math.abs(d.getTicks() / 4.5);
			if (r > 38){r = 38;}
		}
		return r;
	}
	LivingEntity getTarget(){
		if (location == null){return null;}
		World world = location.getWorld();
		double mindistance = getRange(); //DistAtac
		LivingEntity target = null;
		for(Entity en : getTargets(mindistance)) {
			double heightBonus = location.getY() - en.getLocation().getY();
			heightBonus = heightBonus / 14;
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
	ArrayList<LivingEntity> getTargets(double dist){
		World world = location.getWorld();
		double mindistance = dist;
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
	Boolean Targetable(Entity en){
		if (en instanceof LivingEntity){
			LivingEntity lE = (LivingEntity) en;
			if (en.isDead()){
				return false;
			}
			if (en instanceof Player){
				if (d.getNoPlayers()){return false;}
				Player plyr = (Player)en;
				if (isFriendly(plyr)){
					return false;
				}


				if (plyr.getGameMode() == GameMode.CREATIVE){
					return false;
				}

			}

			Vector vec = Utils.CrearVector(d.getLocation(), lE.getEyeLocation());
			int length = (int) vec.length();
			length = length - 1;
			if (length < 0){
				length = 0;
			}
			//			BlockIterator iterator = new BlockIterator(en.getWorld(), d.getLocation().toVector(), vec, 0, length);
			//			Block hitBlock = null;
			//
			//			while(iterator.hasNext()) {
			//				hitBlock = iterator.next();
			//				//hitBlock.breakNaturally();
			//				if(!hitBlock.isEmpty()){
			//					if(hitBlock.getPistonMoveReaction() == PistonMoveReaction.BREAK || hitBlock.isLiquid()){
			//						
			//					}else{
			//						//return false;
			//					}
			//				}
			//				
			//			}

			return true;
		}else{
			return false;
		}

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
		for (Location loc : getTurretBlocks()){
			//Bukkit.broadcastMessage(Integer.toString(loc.getBlock().getType().getId()));
			if (loc.getBlock().getType() != Material.AIR){
				return false;
			}
		}
		return true;
	}
	public void Build(){

		//if (canBuild() == false){d.setBuilt(false); return;}

		Material mat = Material.GOLD_BLOCK;
		if(d.getTier() == 1){mat = Material.IRON_BLOCK;}
		if(d.getTier() == 2){mat = Material.DIAMOND_BLOCK;}
		if(d.getTier() == 3){mat = Material.BEDROCK;}
		Location loc = location.clone();
		loc.getBlock().setType(mat);
		loc.setY(loc.getY() + 1);
		Material netherFence = Material.NETHER_FENCE;
		if (!d.getEnabled()){netherFence = Material.FENCE;}
		loc.getBlock().setType(netherFence);
		loc.setY(loc.getY() + 1);
		Material redstoneTorchOn = Material.REDSTONE_TORCH_ON;
		if(d.getTier() == 3){redstoneTorchOn = Material.TORCH;}
		loc.getBlock().setType(redstoneTorchOn);
		d.setBuilt(true);


	}
	public void setEnabled(Boolean enabled){
		d.setEnabled(enabled);
		Build();
	}
	public void Destroy(){
		if (!d.getBuilt()){return;}
		ArrayList<Location> turretBlocks = getTurretBlocks();
		Collections.reverse(turretBlocks);
		for (Location loc : turretBlocks){
			loc.getBlock().setType(Material.AIR);
		}
		d.setBuilt(false);
		TurretUtils.dropTurret(d, d.getLocation());
	}
	public int getRegenTicks(){
		return (int) (18.0 / d.getTier());
	}
	public int getMaxHp(){
		return 50 + (100 * d.getTier());
	}
	public void Repair(int amount){
		int hp = d.getHealth();
		hp = hp + amount;
		if (hp > getMaxHp()){
			hp = getMaxHp();
		}
		d.setHealth(hp);
	}
	public void Hit(int damage){
		World world = location.getWorld();
		Location loceffect = location.clone().add(new Vector(Utils.NombreEntre(0, 1) * 0.3, 1.1 + Utils.NombreEntre(0, 1) * 0.3, Utils.NombreEntre(0, 1) * 0.3));
		int hp = d.getHealth();
		hp = hp - damage;

		int i = Utils.NombreEntre(2, damage + 3);
		world.playSound(loceffect, Sound.HURT_FLESH, 3F, 1F);
		while (i >= 0){
			world.playEffect(loceffect, Effect.SMOKE, Utils.NombreEntre(0, 8));
			i = i - 1;
		}


		if (hp <= 0){
			boolean fire = false;
			if(d.getTier() >= 3){fire = true;}
			world.createExplosion(loceffect.getX(), loceffect.getY(), loceffect.getZ(), 4.8F, fire, false);
			Destroy();
			d.setActive(false);
		}
		d.setHealth(hp);
	}
	public Boolean ContainsTurretBlock(Location loc){
		return getTurretBlocks().contains(loc); //|| ArmorBlocks.contains(loc);
	}
	public ArrayList<Location> getTurretBlocks(){
		ArrayList<Location> TurretBlocks = new ArrayList<Location>();
		Location loc = location.clone();
		TurretBlocks.add(loc.clone());
		loc.setY(loc.getY() + 1);		
		TurretBlocks.add(loc.clone());
		loc.setY(loc.getY() + 1);		
		TurretBlocks.add(loc.clone());
		return TurretBlocks;
	}
	public Boolean isFriendly(Player p){
		if (p.getName().equalsIgnoreCase(d.getOwner())){
			return true;
		}
		for (String s : d.getFriendlyPlayers()){
			if (p.getName().equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	public ArrayList<String> getNearbyNonFriendlySuggestions(){
		ArrayList<Player> nearbyPlayers = Utils.getNearbyPlayers(d.getLocation(), 200D);
		ArrayList<String> r = new ArrayList<String>();
		for (Player p : nearbyPlayers){
			if(!isFriendly(p)){
				r.add(p.getName());
			}
		}
		return r;
	}
	public void addFriendlyPlayer(String s){
		ArrayList<String> friendlyPlayers = d.getFriendlyPlayers();
		friendlyPlayers.add(s);
		d.setFriendlyPlayers(friendlyPlayers);
	}
	public void removeFriendlyPlayer(String s){
		ArrayList<String> friendlyPlayers = d.getFriendlyPlayers();
		friendlyPlayers.remove(s);
		d.setFriendlyPlayers(friendlyPlayers);
	}
	public void registerModification(){
		d.setLastTimeModified(Calendar.getInstance().getTimeInMillis());
	}
	public long getMilisSinceLastMod(){
		return Calendar.getInstance().getTimeInMillis() - d.getLastTimeModified();
	}
	public boolean getCanBeModified(){
		return getMilisSinceLastMod() > 150;
	}
}
