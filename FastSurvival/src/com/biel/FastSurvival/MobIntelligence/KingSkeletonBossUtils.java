package com.biel.FastSurvival.MobIntelligence;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.biel.FastSurvival.Bows.BowRecipeGenerator;
import com.biel.FastSurvival.Bows.BowUtils;
import com.biel.FastSurvival.SpecialItems.SpecialItemsUtils;
import com.biel.FastSurvival.Utils.Utils;

public class KingSkeletonBossUtils {
	/**
	 * @param l
	 */
	public static void spawnBoss(Location l) {
		Skeleton sk = (Skeleton)l.getWorld().spawnEntity(l, EntityType.SKELETON);
		sk.setMaxHealth(200);
		sk.setHealth(200);
		sk.setCanPickupItems(false);
		sk.setCustomName("King skeleton");
		sk.setRemoveWhenFarAway(false);
		EntityEquipment eq = sk.getEquipment();
		eq.setItemInHand(new ItemStack(Material.ENDER_PEARL));
		updateEquipment(sk);
	}
	public static void updateEquipment(Skeleton sk){
		EntityEquipment eq = sk.getEquipment();
		
		eq.setHelmet(new ItemStack(Material.GOLD_HELMET));
		Color c = Color.BLUE;
		if (sk.getHealth() <= 100){
			c = Color.ORANGE;
		}
		if (sk.getHealth() <= 60){
			c = Color.YELLOW;
		}
		if (sk.getHealth() <= 30){
			c = Color.RED;
		}
		eq.setChestplate(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, c));
		eq.setLeggings(Utils.createColoredArmor(Material.LEATHER_LEGGINGS, c));
		eq.setBoots(Utils.createColoredArmor(Material.LEATHER_BOOTS, c));
	}
	public static void onDamage(EntityDamageByEntityEvent evt, Skeleton sk){
		World w = evt.getEntity().getWorld();
		if (isBoss(sk)){
			//On boss hit
			updateEquipment(sk);
			//Spell cycle
			ItemStack it = sk.getEquipment().getItemInHand();
			ItemStack nextit = null;
			if (it.isSimilar(new ItemStack(Material.ENDER_PEARL))){
				nextit = new ItemStack(Material.EYE_OF_ENDER);
				w.playSound(sk.getEyeLocation(), Sound.ZOMBIE_WOOD, 2, (float) 1.1);
				teleportToEscape(sk);
			}
			if (it.isSimilar(new ItemStack(Material.EYE_OF_ENDER))){
				nextit = getSpellItemStack(getNextSpell());
				w.playSound(sk.getEyeLocation(), Sound.ZOMBIE_WOOD, (float) 2.5, (float) 1.55);
				//return;
			}
			if (nextit == null){ //Spell
				w.playSound(sk.getEyeLocation(), Sound.ZOMBIE_WOODBREAK, (float) 2.5, (float) 1.2);
				//Teleport
				
				//DoSpell and back to ender pearl
				SpellType t = getTypeFromStack(it);
				Location c = sk.getLocation().clone().add(0, -1, 0); 
				int bonus = 0;
				
				if (sk.getHealth() < 100){
					bonus += 1;
				}
				if (sk.getHealth() < 30){
					bonus += 2;
				}
				if (sk.getHealth() < 20){
					bonus += 1;
				}
				switch (t){
				case BLAZE:
					spawnMobsOnTree(c, EntityType.BLAZE, 4 + bonus);
					break;
				case BONUS:
					spawnMobsOnTree(c, EntityType.THROWN_EXP_BOTTLE, 7 + bonus);
					break;
				case MAGMA_CUBE:
					spawnMobsOnTree(c, EntityType.MAGMA_CUBE, 3 + bonus);
					break;
				case MINEFIELD:
					Location l = sk.getTarget().getLocation().add(Vector.getRandom().normalize().multiply(0.2));
					sk.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 3F + (bonus / 8) , true, false);
					break;
				case SKELETON:
					spawnMobsOnTree(c, EntityType.SKELETON, 5 + bonus);
					break;
				case SLIME:
					spawnMobsOnTree(c, EntityType.SLIME, 4 + bonus);
					break;
				case WITCH:
					spawnMobsOnTree(c, EntityType.WITCH, 2 + bonus);
					break;
				case WITHER:
					spawnMobsOnTree(c, EntityType.SPIDER, 4 + bonus);
					break;
				case ZOMBIE:
					spawnMobsOnTree(c, EntityType.ZOMBIE, 7 + bonus);
					break;
				default:
					break;

				}
				nextit = new ItemStack(Material.ENDER_PEARL);
			}

			//SetnextIt
			sk.getEquipment().setItemInHand(nextit);
			//Teleport
			
			//Apply slowness
			sk.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 12, 4));
		}
	}
	public static void teleportToEscape(Skeleton sk){
		Location c = sk.getLocation().clone().add(0, -1, 0); 
		ArrayList<Location> locs = getValidCircleLocations(c, Utils.NombreEntre(10, 12) + 0.4, 1);
		for(Location l : locs){
			sk.teleport(l.add(0, 1, 0));
		}
	}
	/**
	 * @param sk
	 * @return
	 */
	public static boolean isBoss(Skeleton sk) {
		if (sk == null) {return false;}
		String customName = sk.getCustomName();
		if (customName == null) {return false;}
		return customName.equalsIgnoreCase("King skeleton");
	}

	public enum SpellType{BLAZE, ZOMBIE, SKELETON, WITCH, WITHER, SLIME, MAGMA_CUBE, BONUS, MINEFIELD}
	public static SpellType getNextSpell(){
		SpellType result = SpellType.values()[Utils.NombreEntre(0, SpellType.values().length - 1)];
		if (Utils.Possibilitat(2)){return SpellType.MINEFIELD;}
		if (Utils.Possibilitat(2)){return SpellType.BLAZE;}
		if (Utils.Possibilitat(2)){return SpellType.ZOMBIE;}
		return result;
	}
	@SuppressWarnings("deprecation")
	public static ItemStack getSpellItemStack(SpellType type){
		switch(type){
		case BLAZE:
			return new ItemStack(Material.BLAZE_POWDER);
		case BONUS:
			return new ItemStack(Material.EXP_BOTTLE);
		case MAGMA_CUBE:
			return new ItemStack(Material.MAGMA_CREAM);
		case MINEFIELD:
			return new ItemStack(Material.TNT);
		case SKELETON:
			return new ItemStack(Material.SKULL_ITEM, 1, (short) 0, (byte) 0);
		case SLIME:
			return new ItemStack(Material.SLIME_BALL);
		case WITCH:
			return new ItemStack(Material.BREWING_STAND_ITEM);
		case WITHER:
			return new ItemStack(Material.SKULL_ITEM, 1, (short) 0, (byte) 1);
		case ZOMBIE:
			return new ItemStack(Material.SKULL_ITEM, 1, (short) 0, (byte) 2);
		default:
			return new ItemStack(Material.BEDROCK);

		}
	}
	public static SpellType getTypeFromStack(ItemStack stack){
		for (SpellType t : SpellType.values()){
			if (getSpellItemStack(t).isSimilar(stack)){
				return t;
			}
		}
		Bukkit.broadcastMessage("Habilitat no v�lida.");
		return null;
	}
	public static ArrayList<Location> getValidCircleLocations(Location center, Double radius, int num){
		ArrayList<Location> finalLocs = new ArrayList<Location>();
		ArrayList<Location> locs = Utils.getLocationsCircle(center, radius, 15);
	    //center.getBlock().setType(Material.GOLD_BLOCK);
		while (locs.size() == 0){
	    	locs.add(center);
		}
	    int i = 0;
		while (finalLocs.size() < num){
			Location randomLoc = locs.get(Utils.NombreEntre(0, locs.size() - 1));
			Block block = randomLoc.getBlock();
			Material type = block.getType();
			if ((type == Material.LEAVES || type == Material.REDSTONE_LAMP_ON) && block.getRelative(BlockFace.UP).isEmpty()){
				finalLocs.add(randomLoc);
			}
			//Bukkit.broadcastMessage("msg: " + Integer.toString(num) + " - - " + Integer.toString(locs.size()) + ": "  + Integer.toString(finalLocs.size()));
			i++;
			if (i > num * 4){
				finalLocs.add(center);
			}
		}
		return finalLocs;
	}
	public static void spawnMobsOnTree(Location center, EntityType t, int num){
		ArrayList<Location> locs = getValidCircleLocations(center, Utils.NombreEntre(6, 11) + 0.4, num);
		for(Location l : locs){
			World world = center.getWorld();
			Entity e = world.spawnEntity(l.clone().add(0, 1, 0), t);
			if (e instanceof LivingEntity){
				LivingEntity ent = (LivingEntity) e;
				ent.setCustomName("Minion");
				if (ent instanceof Skeleton){
					ent.getEquipment().setItemInHand(new ItemStack(Material.BOW));
				}
			}
			
		}
	}
	public static void dropLoot(Location center){
		ArrayList<ItemStack> d = new ArrayList<ItemStack>();
		d.add(Utils.getRandomPotion());
		d.add(BowRecipeGenerator.getRandomBow(false));
		d.add(new ItemStack(Material.ICE, 10));
		d.add(new ItemStack(Material.TNT, 25));
		d.add(new ItemStack(Material.ENDER_PEARL, Utils.NombreEntre(8, 16)));
		d.add(SpecialItemsUtils.getRandomSpecialItem(2));
		ItemStack itemStack3 = new ItemStack(Material.GOLD_HELMET);
		itemStack3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
		itemStack3.addUnsafeEnchantment(Enchantment.DURABILITY, 9);
		d.add(itemStack3);
		center.getBlock().setType(Material.GOLD_BLOCK);
		Location cl = center.add(0, 1, 0);
		Block b = cl.getBlock();
		b.setType(Material.CHEST);
		Utils.fillChestRandomly(b, d);
	}

}
