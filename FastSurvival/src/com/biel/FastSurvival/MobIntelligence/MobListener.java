package com.biel.FastSurvival.MobIntelligence;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Yellow;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.bergerkiller.bukkit.common.controller.EntityController;
import com.bergerkiller.bukkit.common.entity.CommonEntity;
import com.biel.FastSurvival.Bows.BowRecipeGenerator;
import com.biel.FastSurvival.Bows.BowUtils;
import com.biel.FastSurvival.Bows.BowUtils.BowType;
import com.biel.FastSurvival.Dimensions.Moon.MoonUtils;
import com.biel.FastSurvival.SpecialItems.SpecialItemsUtils;
import com.biel.FastSurvival.Utils.Utils;

public class MobListener implements Listener {
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent evt) {	
		//if (evt.getSpawnReason() == SpawnReason.NATURAL){
		LivingEntity ent = evt.getEntity();
		if (ent instanceof Skeleton){
			Skeleton sk = (Skeleton) ent;
			ItemStack i = null;
			Boolean fet = false;
			if (Utils.Possibilitat(4) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.BOUNCY);
				sk.getEquipment().setChestplate(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, Color.LIME));
				fet = true;
			}
			if (Utils.Possibilitat(4) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.ELECTRIC);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.WHITE));
				sk.getEquipment().setChestplate(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, Color.WHITE));
				sk.getEquipment().setLeggings(Utils.createColoredArmor(Material.LEATHER_LEGGINGS, Color.WHITE));
				sk.getEquipment().setBoots(Utils.createColoredArmor(Material.LEATHER_BOOTS, Color.WHITE));
				fet = true;
			}
			if (Utils.Possibilitat(7) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.MULTI);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.MAROON));
				sk.getEquipment().setChestplate(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, Color.OLIVE));
				//sk.getEquipment().setLeggings(Utils.createColoredArmor(Material.LEATHER_LEGGINGS, Color.WHITE));
				sk.getEquipment().setBoots(Utils.createColoredArmor(Material.LEATHER_BOOTS, Color.MAROON));
				fet = true;
			}
			if (Utils.Possibilitat(4) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.EXPLOSIVE);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.RED));
				sk.getEquipment().setChestplate(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, Color.GRAY));
				//sk.getEquipment().setLeggings(Utils.createColoredArmor(Material.LEATHER_LEGGINGS, Color.WHITE));
				sk.getEquipment().setBoots(Utils.createColoredArmor(Material.LEATHER_BOOTS, Color.MAROON));
				fet = true;
			}
			if (Utils.Possibilitat(5) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.ENDER);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.PURPLE));
				sk.getEquipment().setChestplate(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, Color.PURPLE));
				sk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
				fet = true;
			}
			if (Utils.Possibilitat(7) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.ICY);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.AQUA));
				sk.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
				fet = true;
			}
			if (Utils.Possibilitat(5) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.WATER);
				sk.getEquipment().setLeggings(Utils.createColoredArmor(Material.LEATHER_LEGGINGS, Color.NAVY));
				sk.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
				sk.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
				sk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200 * 20, 1));
				fet = true;
			}
			if (Utils.Possibilitat(6) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.MAGNETIC);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.BLUE));
				sk.getEquipment().setBoots(Utils.createColoredArmor(Material.LEATHER_BOOTS, Color.BLUE));
				fet = true;
			}
			if (Utils.Possibilitat(4) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.WITHER);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.BLACK));
				sk.getEquipment().setChestplate(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, Color.BLACK));
				fet = true;
			}
			if (Utils.Possibilitat(4) && fet == false){
				i = BowRecipeGenerator.getBow(BowType.TORCH);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.ORANGE));
				sk.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
				fet = true;
			}
			if (i != null && fet == true){
				sk.getEquipment().setItemInHand(i);
				sk.getEquipment().setItemInHandDropChance(0.08F);
			}

			//Bukkit.broadcastMessage("SS");
		}else{
			//evt.setCancelled(true);
		}
		if (ent instanceof Zombie){
			Zombie sk = (Zombie) ent;
			ItemStack i = null;
			Boolean fet = false;
			//				if (Utils.Possibilitat(2) && fet == false){
			//					i = new ItemStack(Material.IRON_BLOCK);
			//					sk.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
			//					ItemStack cpItem = new ItemStack(Material.IRON_CHESTPLATE);
			//					cpItem.addEnchantment(Enchantment.THORNS, Utils.NombreEntre(1, 3));
			//					sk.getEquipment().setChestplate(cpItem);
			//					sk.getEquipment().setChestplate(new ItemStack(Material.IRON_LEGGINGS));
			//					sk.getEquipment().setChestplate(new ItemStack(Material.IRON_BOOTS));
			//					sk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
			//					sk.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
			//					
			//					sk.setMaxHealth(sk.getMaxHealth() * Utils.NombreEntre(1, 5));
			//					fet = true;
			//				}
			if (Utils.Possibilitat(6) && fet == false){
				i = new ItemStack(Material.WOOD_SWORD);
				sk.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
				sk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
				fet = true;
			}
			if (Utils.Possibilitat(4) && fet == false){
				i = new ItemStack(Material.DIAMOND_SWORD);
				sk.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				sk.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
				fet = true;
			}
			if (Utils.Possibilitat(10) && fet == false){
				i = new ItemStack(Material.STICK);
				//sk.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				//sk.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
				fet = true;
			}
			if (Utils.Possibilitat(2) && fet == false){
				i = new ItemStack(Material.IRON_PICKAXE);
				sk.getEquipment().setHelmet(Utils.createColoredArmor(Material.LEATHER_HELMET, Color.YELLOW));
				fet = true;
			}
			if (Utils.Possibilitat(2) && fet == false){
				i = new ItemStack(Material.IRON_AXE);
				fet = true;
				//				CommonEntity<Zombie> entity = (CommonEntity<Zombie>) CommonEntity.get(ent);
				//				entity.setController(new EntityController<CommonEntity<Zombie>>() {
				////					@Override
				////					public void onTick() {
				////						super.onTick();
				////						if (Utils.Possibilitat(5)){
				////							CommonEntity<Zombie> e = this.entity;
				////							Location l = e.getLocation();
				////							for (Player p : Utils.getNearbyPlayers(l, 10)){
				////								Vector dir = Utils.CrearVector(l, p.getLocation());
				////								BlockIterator iterator = new BlockIterator(e.getWorld(), l.toVector(), dir.normalize(), 0.0D, 6);
				////								Block hitBlock = null;
				////								while (iterator.hasNext()) {
				////									hitBlock = iterator.next();
				////
				////									if (hitBlock.getTypeId() != 0) {
				////										hitBlock.setType(Material.AIR);
				////										break;
				////									}
				////								}
				////							}
				////						}
				////					}
				//					//				    @Override
				//					//				    public boolean onBlockCollision(Block block, BlockFace hitFace) {
				//					////				        block.getWorld().createExplosion(block.getLocation(), 5.0f);
				//					////				        return false;
				//					//				    }
				//				});
			}

			if (i != null && fet == true){
				sk.getEquipment().setItemInHand(i);
				sk.getEquipment().setItemInHandDropChance(0.1F);
			}

		}
		if (ent instanceof Spider){
			Spider sk = (Spider) ent;
			ItemStack i = new ItemStack(Material.BOW);
			Boolean fet = false;
			if (Utils.Possibilitat(24) && fet == false){ // 15
				Skeleton s = (Skeleton)evt.getEntity().getWorld().spawnEntity(sk.getLocation(), EntityType.SKELETON);
				s.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
				s.getEquipment().setItemInHand(i);
				sk.setPassenger(s);
				sk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
				fet = true;
			}

		}
		if (ent instanceof Creeper){
			Creeper sk = (Creeper) ent;
			ItemStack i = null;
			Boolean fet = false;
			if (Utils.Possibilitat(18) && fet == false){

				sk.setPowered(true);
				sk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
				fet = true;
			}
			if(MoonUtils.IsInMoon(ent)){
				evt.setCancelled(true);
			}
		}
		if (MoonUtils.IsInMoon(ent)){
			ent.getEquipment().setHelmet(MoonUtils.getSpaceGlass());
			ent.getEquipment().setHelmetDropChance(0.28F);
			if (!ent.hasPotionEffect(PotionEffectType.SPEED)){ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));}

			ent.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
			ent.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
			ent.setMaxHealth(ent.getMaxHealth() + Utils.NombreEntre(0, 10));
		}
		//			if (ent instanceof Slime){
		//				Slime sk = (Slime) ent;
		//				ItemStack i = null;
		//				Boolean fet = false;
		//				if (Utils.Possibilitat(40) && fet == false){
		//					
		//					sk.setSize(10);
		//					sk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 6));
		//					sk.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 0));
		//					fet = true;
		//				}
		//				
		//			}
		//}
	}
	@EventHandler
	public void onDmg(EntityDamageEvent evt) {
		if (!(evt.getEntity() instanceof LivingEntity)){return;}
		LivingEntity damaged = (LivingEntity) evt.getEntity();
		DamageCause c = evt.getCause();
		if (damaged instanceof Skeleton && !(c == DamageCause.ENTITY_ATTACK)){
			Skeleton sk = (Skeleton) damaged;
			if (KingSkeletonBossUtils.isBoss(sk)){
				evt.setCancelled(true);
				//sk.getWorld().playSound(sk.getEyeLocation(), Sound., arg2, arg3);
			}
		}
	}


	@EventHandler
	public void onEntDmg(EntityDamageByEntityEvent evt) {

		if (!(evt.getEntity() instanceof LivingEntity)){return;}
		LivingEntity damaged = (LivingEntity) evt.getEntity();
		if (!(evt.getDamager() instanceof LivingEntity)){return;}
		LivingEntity damager = (LivingEntity) evt.getDamager();
		if (damager instanceof Skeleton){
			Skeleton sk = (Skeleton) damager;
			String customName = sk.getCustomName();
			if (customName != null){
				if (customName.equalsIgnoreCase("Boss")){
					//On boss hit
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 0));
				}
			}

		}
		if (damager instanceof Zombie){
			Zombie sk = (Zombie) damager;
			ItemStack i = null;
			Boolean fet = false;
			if (fet == false){
				i = sk.getEquipment().getItemInHand();
				if (i != null){
					if (i.getType() == Material.STICK){
						damaged.setVelocity(new Vector(0,Utils.NombreEntre(1, 2) * 0.8,0));
						damaged.getWorld().playSound(damaged.getLocation(), Sound.MAGMACUBE_JUMP, 4F, 0.9F);
					}else{

					}
				}
				if (Utils.Possibilitat(30)){
					if (sk.getHealth() != sk.getMaxHealth() && i == null){
						damaged.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * Utils.NombreEntre(1, 5), 1));

					}
				}
				fet = true;
			}
		}
		if (damager instanceof Enderman){
			Enderman sk = (Enderman) damager;
			ItemStack i = sk.getEquipment().getItemInHand();
			Boolean fet = false;
			if (fet == false){
				if (damaged instanceof Player){
					//Bukkit.broadcastMessage("Useless to");
					Player player = (Player) damaged;
					PlayerInventory inv = player.getInventory();
					int percent = Utils.NombreEntre(5, 20);
					Material t = i.getType();
					if (t == Material.GLASS){

						ArrayList<ItemStack> arr = Utils.getInventoryPercent(inv, percent);
						evt.setDamage(0.1);
						//inv.removeItem((ItemStack[]) arr.toArray());
						for (ItemStack st : arr){
							if (st == null){continue;}
							if (st.getType() == Material.AIR){continue;}
							if (st.getAmount() == 0){continue;}
							Vector v = damaged.getLocation().getDirection();//.multiply(-1.5);
							v.add(Vector.getRandom().multiply(0.2));
							Item it = damaged.getWorld().dropItem(damaged.getLocation().clone().add(v), st);
							it.setVelocity(v);
							//it.setPickupDelay(20 * 2);
							inv.removeItem(st);
							Bukkit.broadcastMessage(Integer.toString(st.getAmount()));
						}

					}

					fet = true;
				}
			}
		}
		if (damager instanceof Player){
			Player sk2 = (Player) damager;
			if (damaged instanceof Zombie){
				Zombie z = (Zombie) damaged;
				if (Utils.Possibilitat(70)){
					if (z.getHealth() != z.getMaxHealth() && sk2.getItemInHand() == null){
						damaged.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * Utils.NombreEntre(1, 8), 1));
					}
				}
			}
			if (damaged instanceof Skeleton){
				Skeleton sk = (Skeleton) damaged;
				KingSkeletonBossUtils.onDamage(evt, sk);
			}
		}
		if (damaged instanceof Skeleton && !(damager instanceof Player)){
			Skeleton sk = (Skeleton) damaged;
			if (KingSkeletonBossUtils.isBoss(sk)){
				evt.setCancelled(true);
			}
		}

		if (MoonUtils.IsInMoon(damaged)){
			//			if (evt.getCause() == DamageCause.FALL){
			//				evt.setDamage(evt.getDamage() / 6);
			//				if (evt.getDamage() < 2){
			//					evt.setCancelled(true);
			//				}
			//			}
			if (evt.getCause() == DamageCause.ENTITY_EXPLOSION){
				evt.setDamage(evt.getDamage() / 4);
			}
			//damaged.setVelocity(damaged.getVelocity().multiply(-3));
		}
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent evt) {
		LivingEntity killed = evt.getEntity();
		Player killer =  killed.getKiller();
		evt.setDroppedExp((int) (evt.getDroppedExp() + Math.ceil((evt.getDroppedExp() * 0.5))));
		if (Utils.Possibilitat(1)){
			if (Utils.Possibilitat(1)){
				evt.getDrops().add(new ItemStack(SpecialItemsUtils.getRandomSpecialItem(1)));
			}
		}
		if (killed instanceof Creeper){
			Creeper sk = (Creeper) killed;
			if (sk.isPowered()){
				evt.getDrops().add(new ItemStack(Material.IRON_INGOT));
			}
			if(Utils.Possibilitat(6)){
				evt.getDrops().add(new ItemStack(Material.TNT));
			}
			if(Utils.Possibilitat(3)){
				evt.getDrops().add(new ItemStack(Material.EMERALD));
			}
		}
		if (killed instanceof Skeleton){
			Skeleton sk = (Skeleton) killed;
			if (KingSkeletonBossUtils.isBoss(sk)){
				List<Entity> ents = sk.getNearbyEntities(20, 10, 20);
				for(Entity e : ents){
					if(e instanceof LivingEntity){
						LivingEntity livingEntity = (LivingEntity) e;
						String customName = livingEntity.getCustomName();
						if (customName != null){
							if (customName.equals("Minion")){
								livingEntity.setHealth(0);
							}
						}
					}
				}
				KingSkeletonBossUtils.dropLoot(sk.getLocation());
			}
			ItemStack it = new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
			if (Utils.Possibilitat(15) || evt.getDrops().contains(it)){
				if (sk.getSkeletonType() == SkeletonType.WITHER){

					evt.getDrops().add(it);
					evt.setDroppedExp((int) (evt.getDroppedExp()* 4));
				}
			}
			if (Utils.Possibilitat(4)){
				evt.getDrops().add(new ItemStack(Material.IRON_INGOT, Utils.NombreEntre(1, 4)));
			}
		}
		if (killed instanceof Villager){
			Villager sk = (Villager) killed;
			evt.setDroppedExp((int) (evt.getDroppedExp() * 2));
			if (Utils.Possibilitat(70)){
				evt.getDrops().add(new ItemStack(Material.EMERALD, Utils.NombreEntre(1, 3)));
				evt.getDrops().add(new ItemStack(Material.RAW_BEEF, Utils.NombreEntre(1, 6)));
				evt.getDrops().add(new ItemStack(Material.BONE, Utils.NombreEntre(1, 8)));
			}

		}
		if (killed instanceof Witch){
			Witch sk = (Witch) killed;
			evt.setDroppedExp((int) (evt.getDroppedExp() * 2.5));
			if (Utils.Possibilitat(60)){
				if (Utils.Possibilitat(4)){
					evt.getDrops().add(new ItemStack(SpecialItemsUtils.getRandomSpecialItem(1)));
					if (Utils.Possibilitat(1)){
						evt.getDrops().add(new ItemStack(SpecialItemsUtils.getRandomSpecialItem(2)));
					}
				}
				evt.getDrops().add(new ItemStack(Material.RAW_BEEF, Utils.NombreEntre(1, 6)));
				evt.getDrops().add(new ItemStack(Material.BONE, Utils.NombreEntre(1, 8)));
				
			}

		}
		if (killed instanceof Pig){
			Pig sk = (Pig) killed;
			evt.setDroppedExp((int) (evt.getDroppedExp() * 2));
			sk.getLocation().getBlock().setType(Material.REDSTONE_WIRE);
		}
		if (killed instanceof PigZombie){
			PigZombie sk = (PigZombie) killed;
			evt.setDroppedExp((int) (evt.getDroppedExp() * 4.8));
			evt.getDrops().add(new ItemStack(Material.GOLD_NUGGET, Utils.NombreEntre(1, 4)));
			if(Utils.Possibilitat(10)){
				evt.getDrops().add(new ItemStack(Material.GOLD_INGOT));
			}
		}
		if (killed instanceof EnderDragon){
			EnderDragon sk = (EnderDragon) killed;
			evt.getDrops().add(new ItemStack(Material.DIAMOND, 64));
			evt.getDrops().add(new ItemStack(SpecialItemsUtils.getRandomSpecialItem(3)));
			evt.setDroppedExp((int) (evt.getDroppedExp() * 22));
		}
		if (killed instanceof Silverfish){
			EnderDragon sk = (EnderDragon) killed;
			evt.setDroppedExp((int) (evt.getDroppedExp() + 6));
		}
		if (killed instanceof Skeleton){
			Skeleton sk = (Skeleton) killed;
			ItemStack itemInHand = sk.getEquipment().getItemInHand();
			BowType bowType = BowUtils.getBowType(itemInHand);
			if(bowType != null){
				//Bukkit.broadcastMessage(bowType.name());
				switch(bowType){
				case BOUNCY:
					if (Utils.Possibilitat(60))
						evt.getDrops().add(new ItemStack(Material.SLIME_BALL, 1));
					if (Utils.Possibilitat(5))
						evt.getDrops().add(new ItemStack(Material.MAGMA_CREAM, 1));
					break;
				case ELECTRIC:
					if (Utils.Possibilitat(52))
						evt.getDrops().add(SpecialItemsUtils.getRandomSpecialItem(1));
					if (Utils.Possibilitat(10))
						evt.getDrops().add(new ItemStack(Material.DIAMOND, 1));
					break;
				case ENDER:
					if (Utils.Possibilitat(28))
						evt.getDrops().add(new ItemStack(Material.ENDER_PEARL, 1));
					break;
				case EXPLOSIVE:
					if (Utils.Possibilitat(20))
					evt.getDrops().add(new ItemStack(Material.TNT, Utils.NombreEntre(1, 2)));
					break;
				case ICY:
					if (Utils.Possibilitat(45))
						evt.getDrops().add(new ItemStack(Material.ICE, 1));
					if (Utils.Possibilitat(1))
						evt.getDrops().add(new ItemStack(Material.GOLD_BLOCK, 1));
					break;
				case MAGNETIC:
					if (Utils.Possibilitat(1))
						evt.getDrops().add(new ItemStack(Material.IRON_FENCE, 1));
					break;
				case MULTI:
					if (Utils.Possibilitat(1))
						evt.getDrops().add(new ItemStack(Material.IRON_FENCE, 1));
					if (Utils.Possibilitat(1))
						evt.getDrops().add(new ItemStack(Material.CAKE, 1));
					if (Utils.Possibilitat(8))
						evt.getDrops().add(new ItemStack(Material.DISPENSER, 1));
					if (Utils.Possibilitat(8))
						evt.getDrops().add(new ItemStack(Material.REDSTONE_COMPARATOR, 1));
					if (Utils.Possibilitat(8))
						evt.getDrops().add(new ItemStack(Material.REDSTONE_ORE, 1));
					if (Utils.Possibilitat(12))
						evt.getDrops().add(new ItemStack(Material.POWERED_RAIL, 1));
					break;
				case TORCH:
					evt.getDrops().add(new ItemStack(Material.TORCH, Utils.NombreEntre(1, 8)));
					
					break;
				case WATER:
					if (Utils.Possibilitat(30))
						evt.getDrops().add(new ItemStack(Material.WATER_BUCKET, 1));
					if (Utils.Possibilitat(35))
						evt.getDrops().add(new ItemStack(Material.WATER_LILY, 1));
					break;
				case WITHER:
					if (Utils.Possibilitat(30))
						evt.getDrops().add(new ItemStack(Material.COAL_BLOCK, 1));
					if (Utils.Possibilitat(30))
						evt.getDrops().add(new ItemStack(Material.COAL, 1));
					if (Utils.Possibilitat(30))
						evt.getDrops().add(new ItemStack(Material.COAL_ORE, 1));
					break;
				default:
					break;
				
				}
			}
		}
		if (MoonUtils.IsInMoon(killed)){
			evt.setDroppedExp((int) (evt.getDroppedExp() * 1.6));
			for (ItemStack i : evt.getDrops()){
				if(i.getType() == Material.GLASS){
					evt.getDrops().remove(i);
					evt.getDrops().add(MoonUtils.getSpaceGlass());
					break;
				}
			}
			if (Utils.Possibilitat(1)){
				if (Utils.Possibilitat(1)){
					evt.getDrops().add(new ItemStack(SpecialItemsUtils.getRandomSpecialItem(2)));
				}
				if (Utils.Possibilitat(2)){
					evt.getDrops().add(new ItemStack(SpecialItemsUtils.getRandomSpecialItem(1)));
				}
			}
			if (Utils.Possibilitat(2)){
				if (Utils.Possibilitat(4)){
					evt.getDrops().add(new ItemStack(Material.GOLD_INGOT, Utils.NombreEntre(1, 3)));
				}
				if (Utils.Possibilitat(4)){
					evt.getDrops().add(new ItemStack(Material.IRON_INGOT, Utils.NombreEntre(1, 4)));
				}
				if (Utils.Possibilitat(1)){
					evt.getDrops().add(new ItemStack(Material.DIAMOND, Utils.NombreEntre(1, 2)));
				}
				if (Utils.Possibilitat(5)){
					evt.getDrops().add(new ItemStack(Material.EMERALD, Utils.NombreEntre(1, 5)));
				}
				if (Utils.Possibilitat(3)){
					evt.getDrops().add(Utils.getRandomPotion());
				}
			}
		}
	}
	@EventHandler
	public void onTargetLivingEntity(EntityTargetLivingEntityEvent evt) {
		Entity ent = evt.getEntity();

		//		if (ent instanceof Skeleton){
		//			Skeleton sk = (Skeleton) ent;
		//			if (sk.getCustomName().equalsIgnoreCase("Boss")){
		//				//BossLogic
		//				Zombie z = (Zombie) sk.getWorld().spawnEntity(sk.getLocation().add(sk.getLocation().getDirection().add(new Vector(0, 0.6, 0))), EntityType.ZOMBIE);
		//				evt.setTarget(z);
		//				sk.setTarget(z);
		//			}
		//		}
	}
}
