package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.lobby.mapes.JocCooperatiu;
import com.biel.lobby.utilities.Utils;

public class KingSkeletonChallenge extends JocCooperatiu {

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		ItemStack itemStack3 = new ItemStack(Material.DIAMOND_SWORD);
		itemStack3.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		items.add(itemStack3);
		items.add(new ItemStack(Material.DIAMOND_HELMET));
		items.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		items.add(new ItemStack(Material.DIAMOND_LEGGINGS));
		items.add(new ItemStack(Material.GOLDEN_APPLE, 4));
		items.add(Utils.getRandomPotion());
		items.add(Utils.getRandomPotion());
		return items;
	}

	@Override
	protected void teletransportarTothom() {
		for (Player d : getPlayers()) {  // d gets successively each value in ar.
			ArrayList<Location> locs = pMapaActual().ObtenirLocations("arena", world);
			Location loc = new Location(world, Utils.NombreEntre(locs.get(0).getBlockX(), locs.get(1).getBlockX()), locs.get(0).getY() + 1, Utils.NombreEntre(locs.get(0).getBlockZ(), locs.get(1).getBlockZ()));
			d.teleport(loc);					
		} 
	}
	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		setBlockBreakPlace(false);
		spawnBoss(getBossSpawnLoc());
		sendGlobalMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "El King Skeleton ha entrat a la batalla!");
		world.setTime(15000);
	}

	private Location getBossSpawnLoc() {
		return pMapaActual().ObtenirLocation("boss", world);
	}

	@Override
	protected void customJocFinalitzat() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "KingSkeletonChallenge";
	}
	
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
		
		eq.setHelmet(new ItemStack(Material.GOLDEN_HELMET));
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
	public void onDamage(EntityDamageByEntityEvent evt, Skeleton sk){
		World w = evt.getEntity().getWorld();
		if (isBoss(sk)){
			//On boss hit
			updateEquipment(sk);
			//Spell cycle
			ItemStack it = sk.getEquipment().getItemInHand();
			ItemStack nextit = null;
			if (it.isSimilar(new ItemStack(Material.ENDER_PEARL))){
				nextit = new ItemStack(Material.ENDER_EYE);
				w.playSound(sk.getEyeLocation(), Sound.BLOCK_WOOD_STEP, 2, (float) 1.1);
				teleportToEscape(sk);
			}
			if (it.isSimilar(new ItemStack(Material.ENDER_EYE))){
				nextit = getSpellItemStack(getNextSpell());
				w.playSound(sk.getEyeLocation(), Sound.BLOCK_WOOD_BREAK, (float) 2.5, (float) 1.55);
				//return;
			}
			if (nextit == null){ //Spell
				w.playSound(sk.getEyeLocation(), Sound.BLOCK_ANVIL_STEP, (float) 2.5, (float) 1.2);
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
	public void teleportToEscape(Skeleton sk){
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
	public boolean isBoss(Skeleton sk) {
		if (sk == null) {return false;}
		String customName = sk.getCustomName();
		if (customName == null) {return false;}
		return customName.equalsIgnoreCase("King skeleton");
	}

	public enum SpellType{BLAZE, ZOMBIE, SKELETON, WITCH, WITHER, SLIME, MAGMA_CUBE, BONUS, MINEFIELD}
	public SpellType getNextSpell(){
		SpellType result = SpellType.values()[Utils.NombreEntre(0, SpellType.values().length - 1)];
		if (Utils.Possibilitat(2)){return SpellType.MINEFIELD;}
		if (Utils.Possibilitat(2)){return SpellType.BLAZE;}
		if (Utils.Possibilitat(2)){return SpellType.ZOMBIE;}
		return result;
	}
	@SuppressWarnings("deprecation")
	public ItemStack getSpellItemStack(SpellType type){
		switch(type){
		case BLAZE:
			return new ItemStack(Material.BLAZE_POWDER);
		case BONUS:
			return new ItemStack(Material.EXPERIENCE_BOTTLE);
		case MAGMA_CUBE:
			return new ItemStack(Material.MAGMA_CREAM);
		case MINEFIELD:
			return new ItemStack(Material.TNT);
		case SKELETON:
			return new ItemStack(Material.SKELETON_SKULL, 1, (short) 0, (byte) 0);
		case SLIME:
			return new ItemStack(Material.SLIME_BALL);
		case WITCH:
			return new ItemStack(Material.BREWING_STAND);
		case WITHER:
			return new ItemStack(Material.SKELETON_SKULL, 1, (short) 0, (byte) 1);
		case ZOMBIE:
			return new ItemStack(Material.SKELETON_SKULL, 1, (short) 0, (byte) 2);
		default:
			return new ItemStack(Material.BEDROCK);

		}
	}
	public SpellType getTypeFromStack(ItemStack stack){
		for (SpellType t : SpellType.values()){
			if (getSpellItemStack(t).isSimilar(stack)){
				return t;
			}
		}
		//sen("Habilitat no vàlida.");
		return null;
	}
	public ArrayList<Location> getValidCircleLocations(Location center, Double radius, int num){
		ArrayList<Location> finalLocs = new ArrayList<>();
		ArrayList<Location> locs = Utils.getLocationsCircle(center, radius, 15);
	    //center.getBlock().setType(Material.GOLD_BLOCK);
		locs.add(getBossSpawnLoc());
		while (locs.size() == 0){
	    	locs.add(center);
		}
	    int i = 0;
		while (finalLocs.size() < num){
			Location randomLoc = locs.get(Utils.NombreEntre(0, locs.size() - 1));
			Block block = randomLoc.getBlock();
			Material type = block.getType();
			if ((type == Material.GRASS || type == Material.REDSTONE_LAMP) && block.getRelative(BlockFace.UP).isEmpty()){
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
	public void spawnMobsOnTree(Location center, EntityType t, int num){
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
	public void dropLoot(Location center){
		ArrayList<ItemStack> d = new ArrayList<>();
		d.add(Utils.getRandomPotion());
		//d.add(BowRecipeGenerator.getRandomBow(false));
		d.add(new ItemStack(Material.ICE, 10));
		d.add(new ItemStack(Material.TNT, 25));
		d.add(new ItemStack(Material.ENDER_PEARL, Utils.NombreEntre(8, 16)));
		//d.add(SpecialItemsUtils.getRandomSpecialItem(2));
		ItemStack itemStack3 = new ItemStack(Material.GOLDEN_HELMET);
		itemStack3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 9);
		itemStack3.addUnsafeEnchantment(Enchantment.DURABILITY, 9);
		d.add(itemStack3);
		center.getBlock().setType(Material.GOLD_BLOCK);
		Location cl = center.add(0, 1, 0);
		Block b = cl.getBlock();
		b.setType(Material.CHEST);
		Utils.fillChestRandomly(b, d);
		sendGlobalMessage("Heu guanyat!!!");
		JocFinalitzat();
	}
	@Override
	protected void onEntityDeath(EntityDeathEvent evt, Entity e) {
		// TODO Auto-generated method stub
		super.onEntityDeath(evt, e);
		if (e instanceof Skeleton){
			Skeleton sk = (Skeleton) e;
			if (isBoss(sk)){
				List<Entity> ents = sk.getNearbyEntities(20, 10, 20);
				for(Entity ent : ents){
					if(ent instanceof LivingEntity){
						LivingEntity livingEntity = (LivingEntity) ent;
						String customName = livingEntity.getCustomName();
						if (customName != null){
							if (customName.equals("Minion")){
								livingEntity.setHealth(0);
							}
						}
					}
				}
				dropLoot(sk.getLocation());
			}
		}
	}
	@Override
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt,
			Entity damaged, Entity damager) {
		// TODO Auto-generated method stub
		super.onEntityDamageByEntity(evt, damaged, damager);
		if(damaged instanceof Skeleton){
			onDamage(evt, (Skeleton) damaged);
		}
	}
}
