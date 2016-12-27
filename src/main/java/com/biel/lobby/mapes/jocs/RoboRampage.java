package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.lobby.mapes.JocCooperatiu;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.ObsidianDefenders.Ability;
import com.biel.lobby.mapes.jocs.ObsidianDefenders.Ability.AbilityType;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Cuboid.CuboidDirection;
import com.biel.lobby.utilities.Utils;

public class RoboRampage extends JocCooperatiu {
	int tid;
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.DIAMOND_SWORD, 1));
		return items;
	}

	@Override
	protected void teletransportarTothom() {
		// TODO Auto-generated method stub
		for (Player d : getPlayers()) {  // d gets successively each value in ar.
			d.teleport(getCenterOfBattle());					
		} 
	}

	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		updateScoreBoards();
		ProgTask();
	}
@Override
protected void setCustomGameRules() {
	// TODO Auto-generated method stub
	super.setCustomGameRules();
}
	@Override
	protected void customJocFinalitzat() {
		// TODO Auto-generated method stub
		plugin.getServer().getScheduler().cancelTask(tid);
	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "RoboRampage";
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		// TODO Auto-generated method stub
		super.updateScoreBoard(ply);
		if (JocIniciat){
			ArrayList<String> list = new ArrayList<String>();
			list.add(ChatColor.GREEN + "Ferralla:");
			list.add(ChatColor.GREEN + Integer.toString(getMaxScrapHeight()) + " m");
			//list.add(ChatColor.GOLD + "Or: " + pPlayer(ply).ObtenirPropietatInt("Or"));

			ScoreBoardUpdater.setScoreBoard(getViewers(), "Estadístiques", list, null); //Puntuació
		}
	}
	public Cuboid getBattleCuboid(boolean volumetric){
		int r = 7;
		int h = 1;
		if (volumetric){
			h = 255;
		}
		Location obtenirLocation = getCenterOfBattle();
		obtenirLocation.setY(0);
		Cuboid cub = Utils.getCuboidCenteredOnBase(obtenirLocation, r, h, r); 
		return cub;
	}

	private Location getCenterOfBattle() {
		return pMapaActual().ObtenirLocation("BCenter", world);
	}

	public int getMaxScrapHeight(){
		int max = 0;
		for (Block b : getBattleCuboid(false)){
			int newmax = world.getHighestBlockYAt(b.getLocation()) - 1;
			if (newmax > max){
				max = newmax;
			}
		}
		return max;
	}
	public Location getRandomLocation(){
		int cycles = 0;
		while(cycles < 20){
			cycles++;
			for (Block blk : getBattleCuboid(false)){
				if (Utils.Possibilitat(cycles) && Utils.Possibilitat(10)){
					return world.getHighestBlockAt(blk.getLocation()).getLocation();
				}
			}
		}
		return null;
	}
	public void spawnZombie(){
		Location spawnLoc = getRandomLocation().add(new Vector(0.5, 4, 0.5));
		Zombie z = (Zombie) world.spawnEntity(spawnLoc, EntityType.ZOMBIE);
		//z.setVelocity(Vector.getRandom());
		z.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		z.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		z.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		z.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
		ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		if (Utils.Possibilitat(15)){
			helmet = new ItemStack(Material.IRON_BLOCK);
		}
		if (Utils.Possibilitat(10)){
			helmet = new ItemStack(Material.REDSTONE_BLOCK);
		}
		z.getEquipment().setHelmet(helmet);
	}
	public void spawnSkeleton(){
		Location spawnLoc = getRandomLocation().add(new Vector(0.5, 4, 0.5));
		Skeleton z = (Skeleton) world.spawnEntity(spawnLoc, EntityType.SKELETON);
		//z.setVelocity(Vector.getRandom());
		z.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		z.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		z.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		z.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
		ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		if (Utils.Possibilitat(15)){
			helmet = new ItemStack(Material.IRON_BLOCK);
		}
		if (Utils.Possibilitat(12)){
			helmet = new ItemStack(Material.REDSTONE_BLOCK);
		}
		if (Utils.Possibilitat(14)){
			helmet = new ItemStack(Material.LAPIS_BLOCK);
		}
		z.getEquipment().setHelmet(helmet);
	}
	public void spawnBlaze(){
		Location spawnLoc = getRandomLocation().add(new Vector(0.5, 5, 0.5));
		Blaze z = (Blaze) world.spawnEntity(spawnLoc, EntityType.BLAZE);
		//z.setVelocity(Vector.getRandom());
		
	}
	public void spawnEnemy(){
		while (true){
			if (world.getEntitiesByClass(Zombie.class).size() < (getMaxScrapHeight() / 2) + 2 && Utils.Possibilitat(20)){
				spawnZombie();
				return;
			}
			if (world.getEntitiesByClass(Skeleton.class).size() < (getMaxScrapHeight() / 2) && Utils.Possibilitat(20)){
				spawnSkeleton();
				return;
			}
			if (world.getEntitiesByClass(Blaze.class).size() < (getMaxScrapHeight() / 3) && Utils.Possibilitat(20)){
				spawnBlaze();
				return;
			}
		}
		
	}
	public void ProgTask(){
		tid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				spawnEnemy();
			}
		}, 1, 2 * 20); // *12
	}
	@Override
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
		super.onBlockPlace(evt, blk);
		updateScoreBoards();
	}
	@Override
	protected void onEntityDeath(EntityDeathEvent evt, Entity e) {
		// TODO Auto-generated method stub
		super.onEntityDeath(evt, e);
		Player killer = evt.getEntity().getKiller();
		if(evt.getEntityType() == EntityType.ZOMBIE || evt.getEntityType() == EntityType.SKELETON){
			//Bukkit.broadcastMessage("ZombDeath: " + evt.);
			Zombie z = (Zombie) evt.getEntity();
			Block highestBlockAt = world.getHighestBlockAt(z.getLocation().getBlock().getLocation());
			highestBlockAt.setType(Material.IRON_BLOCK);
			Material htype = z.getEquipment().getHelmet().getType();
			
			if (htype == Material.REDSTONE_BLOCK){
				killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 20, 1));
			}
			if (htype == Material.LAPIS_BLOCK){
				killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 1));
			}
			if (htype == Material.CHEST){
				
			}
			if (htype == Material.IRON_BLOCK){
				Byte blockData = 0x0;
				FallingBlock tnt = world.spawnFallingBlock(z.getEyeLocation().add(0, 0.35, 0), Material.IRON_BLOCK, blockData);
				tnt.setVelocity(Utils.CrearVector(killer.getLocation(), z.getEyeLocation()));
				tnt.setDropItem(false);
			}
		}
		if(evt.getEntityType() == EntityType.BLAZE){
			Blaze b = (Blaze) evt.getEntity();
			Byte blockData = 0x0;
			FallingBlock tnt = world.spawnFallingBlock(b.getLocation().getBlock().getLocation(), Material.GOLD_BLOCK, blockData);
			tnt.setVelocity(new Vector(0, 1, 0));
			tnt.setDropItem(false);
			killer.setFireTicks(0);
			
		}
		updateScoreBoards();
	}
	@Override
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt,
			Entity damaged, Entity damager) {
		// TODO Auto-generated method stub
		super.onEntityDamageByEntity(evt, damaged, damager);
		if (damaged instanceof Player){
			if(damager instanceof Player){
				evt.setCancelled(true);
			}
			evt.setDamage(evt.getDamage() / 4);
		}else{
			evt.setDamage(evt.getDamage() * 2.65);
		}
	}
}
