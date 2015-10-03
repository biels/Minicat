package com.biel.FastSurvival.Bows;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.bergerkiller.bukkit.common.Task;
import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Bows.BowUtils.BowType;
import com.biel.FastSurvival.Turrets.TurretUtils;
import com.biel.FastSurvival.Utils.Utils;
import com.biel.FastSurvival.Utils.WGUtils;

public class CustomBowsListener implements Listener {
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent evt) {
		if(evt.isCancelled()){return;}
		if (!(evt.getEntity() instanceof Arrow)){return;}
		Arrow arr = (Arrow) evt.getEntity();
		if (!(arr.getShooter() instanceof Skeleton)){return;}
		Skeleton sk = (Skeleton) arr.getShooter();
		ItemStack item = sk.getEquipment().getItemInHand();
		BowType type = BowUtils.getBowType(item);
		if(type == null){return;}
		float f = 1F;
		arr.setMetadata("BowType", new FixedMetadataValue(FastSurvival.getPlugin(), type.ordinal()));
		arr.setMetadata("Force", new FixedMetadataValue(FastSurvival.getPlugin(), f));
		switch(type){
		case BOUNCY:
			break;
		case ELECTRIC:
			break;
		case ENDER:
			break;
		case EXPLOSIVE:
			break;
		case ICY:
			break;
		case MAGNETIC:
			break;
		case MULTI:
			multiShot(arr, sk, type, f);
			break;
		case TORCH:
			break;
		case WATER:
			break;
		case WITHER:
			Utils.healDamageable(sk, 3.2);
			break;
		default:
			break;
		
		}
		
	}
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent evt) {	
		if (!(evt.getEntity() instanceof Player)){return;}
		if (evt.getBow() == null){return;}
		if (!(evt.getProjectile() instanceof Arrow)){return;}
		Arrow arr = (Arrow) evt.getProjectile();
		Player p = (Player) evt.getEntity();
		ItemStack item = evt.getBow();
		BowType type = BowUtils.getBowType(item);
		if(type == null){return;}
		float f = evt.getForce();
		arr.setMetadata("BowType", new FixedMetadataValue(FastSurvival.getPlugin(), type.ordinal()));
		arr.setMetadata("Force", new FixedMetadataValue(FastSurvival.getPlugin(), f));
		Location pLoc = p.getLocation();
		World world = arr.getWorld();
		switch(type){
		case ENDER:
			world.playSound(pLoc, Sound.ENDERMAN_TELEPORT, 4 * f, 1);
			break;
		case EXPLOSIVE:
			world.playSound(pLoc, Sound.FUSE, 5 * f, 2);
			break;
		case MAGNETIC:
			world.playSound(pLoc, Sound.IRONGOLEM_HIT, 5 * f, 1.4F);
			break;
		case TORCH:
			world.playSound(pLoc, Sound.ITEM_PICKUP, 7 * f, 1.2F);
			break;
		case BOUNCY:
			world.playSound(pLoc, Sound.SLIME_ATTACK, 7 * f, 1.2F);
			break;
		case ICY:
			break;
		case WATER:
			world.playSound(pLoc, Sound.SPLASH, 7 * f, 1.2F);
			break;
		case WITHER:
			world.playSound(pLoc, Sound.WITHER_SHOOT, 7 * f, 1.2F);
			break;
		case ELECTRIC:
			break;
		case MULTI:
			multiShot(arr, p, type, f);
			p.getWorld().playSound(p.getLocation(), Sound.DOOR_CLOSE, (float)  f, 1F);
			
			break;
		default:
			break;
		
		
		}
	}
	public void multiShot(Arrow arr, LivingEntity p, BowType type, float f) {
		int amp = Math.round(1 + 6 * f);
		int i = amp * -1;
		while (i <= amp){
			float angle = p.getLocation().getYaw() + (5 * i) + 90;
			double toRadians = Math.PI / 180;
			//Location locSpawn = plyr.getLocation().add(0,1,0);
			Location spawnpoint = p.getLocation().add(0,1.05,0).add(new Location(p.getWorld(),Math.cos(angle * toRadians), 0, Math.sin(angle * toRadians)));
			
			Vector dir2 = spawnpoint.toVector().subtract(p.getLocation().add(0,1,0).toVector()).normalize().multiply(0.5);
			Arrow arrow = (Arrow)p.getWorld().spawnEntity(spawnpoint, EntityType.ARROW);
			//Bukkit.broadcastMessage(Float.toString(plyr.getLocation().getYaw()));
			arrow.setShooter(p);
		    arrow.setFireTicks((int) (100 * f));
			arrow.setVelocity(dir2.normalize().setY(arr.getVelocity().normalize().getY()).normalize().multiply(arr.getVelocity().length()));
			arrow.setMetadata("BowType", new FixedMetadataValue(FastSurvival.getPlugin(), type.ordinal()));
			arrow.setMetadata("Force", new FixedMetadataValue(FastSurvival.getPlugin(), f));
			arrow.setTicksLived(20 * 4 + 10);
			i= i + 1;
			
		}
	}
	@EventHandler
	public void onHit(ProjectileHitEvent evt) {	
		//WorldGuard danger
		if (!(evt.getEntity() instanceof Arrow)){return;}
		Arrow arr = (Arrow) evt.getEntity();
		if (!(arr.getShooter() instanceof LivingEntity)){return;}
		LivingEntity p = (LivingEntity) arr.getShooter();
		Location l = arr.getLocation();
		MetadataValue metadataType = Utils.getMetadata(arr, "BowType");
		if(metadataType == null){return;}
		BowType type = BowType.values()[metadataType.asInt()];
		if(type == null){return;}
		float f = Utils.getMetadata(arr, "Force").asFloat();
		World world = l.getWorld();
		switch(type){
		case ENDER:
			FastSurvival.getWorld().playSound(l, Sound.GLASS, 10 * f, 1);
			FastSurvival.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 4 , (int) (28 * f));
			break;
		case EXPLOSIVE:
			
			//arr.remove();
			break;
		case MAGNETIC:
			FastSurvival.getWorld().playEffect(l, Effect.SMOKE, 4 , (int) (28 * f));
			break;
		case TORCH:
			if (p instanceof Player){
				if (!WGUtils.canBuild((Player)p, l)){
					break;
				}
			}
			l.getBlock().setType(Material.TORCH);
			arr.remove();
			break;
		case BOUNCY:
			arr.remove();
			break;
		case ICY:
			arr.remove();
			break;
		case WATER:
			if (true){
				if (p instanceof Player){
					if (!WGUtils.canBuild((Player)p, l)){
						break;
					}
				}
				Block b = l.getBlock();
				if (b.getPistonMoveReaction() != PistonMoveReaction.BREAK && b.isEmpty() == false){break;}
				if (b.isLiquid()){break;}
				
				b.setType(Material.WATER);
				Utils.BreakBlockLater(b, (int)(20 * 2 * f), false);
				arr.remove();
			}
			break;
		case WITHER:
			if(true){
				if (p instanceof Player){
					if (WGUtils.canBuild((Player)p, l)){
						break;
					}
				}
				FastSurvival.getWorld().createExplosion(l, 0.5F * f);
			}
			break;
		case ELECTRIC:
			arr.remove();
			ultraStrike(l, f, p);
			final Location lf = l;
			final float ff = f *  2.3F;
			final UUID id = p.getUniqueId();
			Task myTask = new Task(FastSurvival.getPlugin()) {
			    @Override
			    public void run() {
			    	ultraStrike(lf, ff, null);
			    	LivingEntity entityFromUUID = (LivingEntity)Utils.getEntityFromUUID(id, lf.getWorld());
					for(LivingEntity ent : Utils.getNearbyEnemies(entityFromUUID, lf, ff * 16, false)){
						int time = (int) (5 * 20 + ff * 3);
						ent.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time, 0));
						ent.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, time, 0));
						ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time * 2, 0));
						ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time / 3, 0));
						
						ent.damage(5, entityFromUUID);;
						ent.setFallDistance(0);
						ent.setVelocity(Vector.getRandom().multiply(1.1));
					}
			    }
			}.start(2 * 10);
			for(LivingEntity ent : Utils.getNearbyEnemies(p, l, f * 10, false)){
				int time = (int) (10 * 20 + f * 3);
				ent.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time, 0));
				ent.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, time, 0));
				ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time * 2, 0));
				ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time / 3, 0));
				
				ent.damage(7, p);
				ent.setFireTicks(20 * 2);
				ent.setVelocity(Vector.getRandom().normalize());
			}
			
			world.strikeLightningEffect(l);
			break;
		case MULTI:
			break;
		default:
			break;
		
		
		
		}
	}
	private void ultraStrike(Location l, float f, LivingEntity p) {
		World world = l.getWorld();
		Location location = world.getHighestBlockAt(l.getBlockX(), l.getBlockZ()).getLocation();
		ArrayList<Block> finalLocs = new ArrayList<Block>();
		try {
			for (Location lo : Utils.getLocationsCircle(location.add(new Vector(0, -1, 0)), 6.8D * f + 1, 4)) {
				Location lob = world.getHighestBlockAt(lo.getBlockX(), lo.getBlockZ()).getLocation();
				Block b = lob.getBlock().getRelative(BlockFace.DOWN);
				if (b.getPistonMoveReaction() == PistonMoveReaction.BREAK || TurretUtils.getTurretAt(b.getLocation()) != null){
					b = b.getRelative(BlockFace.DOWN); 
				}
				finalLocs.add(b);
				
				
			}
			for (Block b : finalLocs) {
				
				//b.setType(Material.DIAMOND_BLOCK);
				if (b.getType() == Material.WOOD){b.setType(Material.COBBLESTONE); continue;};
				if (b.getType() == Material.LOG){b.setType(Material.STONE); continue;};
				if(b.getType() != Material.AIR){
					if (p != null){
						if (p instanceof Player){
							if (!WGUtils.canBuild((Player)p, l)){
								continue;
							}
						}
					}
					
					float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 0));
					float y = (float) -2 + (float) (Math.random() * ((2 - -2) + 0));
					float z = (float) -1 + (float) (Math.random() * ((1 - -1) + 0));

					@SuppressWarnings("deprecation")
					FallingBlock fallingBlock = b.getWorld().spawnFallingBlock(
							b.getLocation().add(new Vector(0, 1.5 ,0)), b.getType(), b.getData());
					fallingBlock.setDropItem(false);
					Vector vr = new Vector(x, y, z);
					//fallingBlock.setVelocity(Utils.CrearVector(l, location).setY(0).add(vr));
					fallingBlock.setVelocity(new Vector (0, 1, 0));
					b.setType(Material.AIR);
				}
			}
			world.strikeLightningEffect(location);
		} catch (Exception e) {
			
		}
		
	}
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {	
		
		if (evt.isCancelled()){return;}
		evt.getEntity().setMetadata("LastDamager", new FixedMetadataValue(FastSurvival.getPlugin(), evt.getDamager().getUniqueId()));
		//---
		if (!(evt.getDamager() instanceof Arrow)){return;}
		Arrow arr = (Arrow) evt.getDamager();
		if (!(evt.getEntity() instanceof LivingEntity)){return;}
		LivingEntity damaged = (LivingEntity) evt.getEntity();
		if (!(arr.getShooter() instanceof LivingEntity)){return;}
		LivingEntity damager = (LivingEntity) arr.getShooter();
		Location l = arr.getLocation();
		World world = l.getWorld();
		
		MetadataValue metadataType = Utils.getMetadata(arr, "BowType");
		if(metadataType == null){return;}
		BowType type = BowType.values()[metadataType.asInt()];
		if(type == null){return;}
		float f = Utils.getMetadata(arr, "Force").asFloat();
		Double dmg = evt.getDamage();
		
		switch(type){
		case ENDER:
			damager.teleport(damaged.getLocation().clone().add(Vector.getRandom().add(new Vector(0,0.8,0))));
			break;
		case EXPLOSIVE:
			if(true){
				dmg = 1D;
				evt.setDamage(1D);
				MetadataValue metadataAcc = Utils.getMetadata(damaged, "ExplAcc");
				float ExplAcc;
				if (metadataAcc != null){
					ExplAcc = metadataAcc.asFloat();
				}else{
					ExplAcc = 0F;
				}
				
				
				ExplAcc = (float) (ExplAcc + (1.5 * f));
				damaged.setMetadata("ExplAcc", new FixedMetadataValue(FastSurvival.getPlugin(), ExplAcc));
				FastSurvival.getWorld().playEffect(l, Effect.SMOKE, 4 , (int) (28 * f));
				FastSurvival.getWorld().playSound(damaged.getLocation(), Sound.CREEPER_DEATH, 7 * f, 1.4F);
				
				//world.createExplosion(damaged.getLocation(), fExplosion);
			}		
			break;
		case MAGNETIC:
			damaged.setVelocity(new Vector(0,0,0));
			Vector vM = Utils.CrearVector(damaged.getLocation(), damager.getLocation());
			//vM.normalize();
			vM.multiply(0.8);
			vM.multiply(f);
			Location newLoc = damaged.getLocation().clone().add(vM);
			if (newLoc.getBlock().getType().isSolid()){break;}
			damaged.teleport(newLoc);
			break;
		case TORCH:
			damaged.setFireTicks((int) (20 * 5 * f));
			dmg = dmg / 3;
			break;
		case BOUNCY:
			if(true){ 
				ArrayList<LivingEntity> nearbyEnemies = Utils.getNearbyEnemies(damaged, 10 + (f * 6), true);
				ArrayList<UUID> bouncedEnemies = new ArrayList<UUID>();
				nearbyEnemies.remove(damager);
				int initialtimes = (int) (30 + (f * 3));
				int times = initialtimes;
				//Bukkit.broadcastMessage(Integer.toString(times) + " - " + nearbyEnemies.size());
				MetadataValue metadata = Utils.getMetadata(arr, "Bounced");
				MetadataValue metadata2 = Utils.getMetadata(arr, "BouncedTimes");
				if(metadata != null){
					ArrayList<UUID> idArr = (ArrayList<UUID>)metadata.value();
					for(UUID id : idArr){
						Entity ent = Utils.getEntityFromUUID(id, world);
						if (ent != null){
							if(ent instanceof LivingEntity){
								LivingEntity le = (LivingEntity)ent;
								nearbyEnemies.remove(le);
							}
						}
						bouncedEnemies.add(id);
					}
					
				}
				if(metadata2 != null){
					times = metadata2.asInt();

				}
				dmg = (double) (10 * f);
					//First obj
					dmg = (dmg * 0.8)/(6/(initialtimes - times + 1));

				if (damaged instanceof Player && dmg > 7){
					dmg = 6.2;
				}
				LivingEntity bounced = Utils.getNearestEntity(damaged.getLocation(), nearbyEnemies);
				if (bounced != null && times >= 0){
					Vector v = Utils.CrearVector(damaged.getLocation(), bounced.getLocation());
					double lv = v.length();
					double yOffset = 0.6 + (lv / 40);
					v.add(new Vector(0,yOffset,0));
					Location spawnLoc = damaged.getLocation().add(v.clone().normalize().multiply(1.5D));
					Arrow arrow = (Arrow)world.spawnEntity(spawnLoc, EntityType.ARROW);
					arrow.setShooter(damager);
					//arrow.setFireTicks(20000);
					arrow.setVelocity(v);
					bouncedEnemies.add(bounced.getUniqueId());
					arrow.setMetadata("Bounced", new FixedMetadataValue(FastSurvival.getPlugin(), bouncedEnemies));
					arrow.setMetadata("BouncedTimes", new FixedMetadataValue(FastSurvival.getPlugin(), times - 1));
					//- normal
					arrow.setMetadata("BowType", new FixedMetadataValue(FastSurvival.getPlugin(), type.ordinal()));
					arrow.setMetadata("Force", new FixedMetadataValue(FastSurvival.getPlugin(), f));
				}
				break;
			}			
		case ICY:
			if(true){
//				int lvl = 0;
				//if (damaged.hasPotionEffect(PotionEffectType.SLOW)){
//					for(PotionEffect eff : damaged.getActivePotionEffects()){
//						if (eff.getType() == PotionEffectType.SLOW){
//							lvl = eff.getAmplifier() + 1;
//						}
//					}
				//}
				
				
//				dmg = dmg / 8;
//				if(lvl < 2){break;}
				//effect
				if (damaged.hasPotionEffect(PotionEffectType.WEAKNESS)){
					return;
				}
				int time = (int) (20 * 6 * f);
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time * 2, 0));
				if (damaged instanceof Player){
					((Player)damaged).playSound(damager.getLocation(), Sound.BURP, 1, 0.5F);
					time = time / 2;
				}
				ArrayList <BlockFace> faces = new ArrayList <BlockFace>();
				faces.add(BlockFace.NORTH);
				faces.add(BlockFace.SOUTH);
				faces.add(BlockFace.WEST);
				faces.add(BlockFace.EAST);
				for (BlockFace face : faces){
					
					Block block = damaged.getLocation().getBlock().getRelative(face);
					if (block.getPistonMoveReaction() == PistonMoveReaction.BREAK){
						continue;
					}
					block.setType(Material.ICE);
					Utils.BreakBlockLater(block, time, false);
					
				}
				Location tpL = damaged.getLocation().getBlock().getLocation().add(new Vector(0.5,0,0.5));
				tpL.setPitch(damaged.getLocation().getPitch());
				tpL.setYaw(damaged.getLocation().getYaw());
				damaged.teleport(tpL);
				Block gblock = damaged.getLocation().add(0, 2, 0).getBlock();
				//if (gblock.getPistonMoveReaction() == PistonMoveReaction.BREAK){
					gblock.setType(Material.GOLD_BLOCK);
					Utils.BreakBlockLater(gblock, time, false);
				//}
				
				
				//----
				break;
			}
		case WATER:
			dmg = dmg / 5;
			break;
		case WITHER:
			dmg = dmg / 6;
			if (true){
				int t = (int) (20 * 20 * f);
				int lvl = 0;
				if (damaged instanceof Player){
					t = t / 3;
				}
				if (f >= 0.9 && Utils.Possibilitat(40)){
					lvl = 1;
				}
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, t, lvl, false));
				damager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, t / 4, 0, false));
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, t / 3, 0, false));
			}
			break;
		case ELECTRIC:
			break;
		case MULTI:
			break;
		default:
			break;
		
		
		
		}
		evt.setDamage(dmg);
		//Bukkit.broadcastMessage(Double.toString(evt.getDamage()));
	}
	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt) {	
		if(evt.isCancelled()){return;}
		//Bukkit.broadcastMessage(Double.toString(evt.getDamage()));
		if (!(evt.getEntity() instanceof LivingEntity)){return;}
		LivingEntity damaged = (LivingEntity) evt.getEntity();
		World w = damaged.getWorld();
		EntityDamageEvent lastDamageCause = damaged.getLastDamageCause();
		if (lastDamageCause == null){return;}
		if (!(lastDamageCause.getEntity() instanceof LivingEntity)){return;}
		LivingEntity damager = (LivingEntity) lastDamageCause.getEntity();
		BowType type = BowUtils.getBowType(Utils.getItemInHand(damaged));
		if (type != null){	if (type == BowType.EXPLOSIVE){return;}}
		
		MetadataValue metadataAcc = Utils.getMetadata(damaged, "ExplAcc");
		if (metadataAcc == null){return;}	
		//if (evt.getDamage() <= 5){return;}	
		if (evt.getCause() == DamageCause.ENTITY_EXPLOSION || evt.getCause() == DamageCause.BLOCK_EXPLOSION) {evt.setDamage(evt.getDamage() * 0.6);return;}	
		//Bukkit.broadcastMessage(evt.getCause().name());
		
		float ExplAcc = metadataAcc.asFloat();
		if (ExplAcc == 0F){return;}
		w.createExplosion(damaged.getEyeLocation().toVector().toLocation(w), ExplAcc);
		damaged.setMetadata("ExplAcc", new FixedMetadataValue(FastSurvival.getPlugin(), 0F));
		
	}
	@EventHandler
	public void onEntityDamageWither(EntityDamageEvent evt) {
		if(evt.isCancelled()){return;}
		if (evt.getCause() != DamageCause.WITHER){return;}
		if (!(evt.getEntity() instanceof LivingEntity)){return;}
		LivingEntity damaged = (LivingEntity) evt.getEntity();
		MetadataValue metadata = Utils.getMetadata(damaged, "LastDamager");
		if (metadata == null){return;}
		//Bukkit.broadcastMessage("wither meta0");
		//Bukkit.broadcastMessage(metadata.value().getClass().getName());
		Entity ent = Utils.getEntityFromUUID((UUID)metadata.value(), damaged.getWorld());
		//Bukkit.broadcastMessage(ent.getClass().getName());
		if (ent == null){return;}
		//Bukkit.broadcastMessage("wither meta1");
		if (!(ent instanceof LivingEntity)){return;}
		//if (ent instanceof Player){Bukkit.broadcastMessage("is a Player!");}
		//Bukkit.broadcastMessage("wither meta2");
		LivingEntity damager = (LivingEntity) ent;
		Utils.healDamageable(damager, evt.getDamage() * 2);
		//damager.setHealth(20.0);
		//damager.getLocation().getBlock().setType(Material.GOLD_BLOCK);
		//Bukkit.broadcastMessage("Curant el wither");
	}
	@EventHandler
	public void onEntityDamageByEntityMark(EntityDamageByEntityEvent evt) {	
		if(evt.isCancelled()){return;}
		//if (evt.getDamager() == null){return;}
		//Bukkit.broadcastMessage("MarkStart");
		LivingEntity damager;
		if (evt.getDamager() instanceof Projectile){
			//Bukkit.broadcastMessage("MarkProjectile");
			Projectile arr = (Projectile) evt.getDamager();
			
			if (!(arr.getShooter() instanceof LivingEntity)){return;}
			damager = (LivingEntity) arr.getShooter();
			//Bukkit.broadcastMessage("MarkSetLivingEntity");
			
		}else{
			//Bukkit.broadcastMessage("MarkElseDirect");
			if (!(evt.getDamager() instanceof LivingEntity)){return;}
			//Bukkit.broadcastMessage("MarkElseLivingEntity");
			damager = (LivingEntity) evt.getDamager();
		}
		
		evt.getEntity().setMetadata("LastDamager", new FixedMetadataValue(FastSurvival.getPlugin(), damager.getUniqueId()));
		//---
	}
//	@EventHandler
//	public void onEntityDamageByEntityMark(EntityDeathEvent evt) {	
//		
//	}
	@EventHandler
	public void onLightningStrike(LightningStrikeEvent evt) {	
		if(evt.isCancelled()){return;}
//		LightningStrike light = evt.getLightning();
//		if (!light.isEffect()){
//			int f = Utils.NombreEntre(5, 10);
//			ultraStrike(light.getLocation(), f, null);
//			for(LivingEntity ent : Utils.getNearbyEnemies(light.getLocation(), f * 10)){
//				int time = (int) (10 * 20 + f * 3);
//				ent.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time, 0));
//				ent.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, time, 0));
//				ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time * 2, 0));
//				ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time / 3, 0));
//
//				ent.damage(6, null);
//				ent.setFireTicks(20 * 2);
//				ent.setVelocity(Vector.getRandom().normalize().multiply(0.86));
//			}
//		}
	}
}
