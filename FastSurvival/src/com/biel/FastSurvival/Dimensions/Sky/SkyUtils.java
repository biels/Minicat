package com.biel.FastSurvival.Dimensions.Sky;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.bergerkiller.bukkit.common.Task;
import com.bergerkiller.bukkit.common.utils.CommonUtil;
import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Bows.BowUtils.BowType;
import com.biel.FastSurvival.Dimensions.Sky.biomegen.BasicChunkGenerator;
import com.biel.FastSurvival.Utils.Cuboid;
import com.biel.FastSurvival.Utils.Utils;

public class SkyUtils {
	
	public static World getSky(){
		return  Bukkit.getWorld("Sky");//Bukkit.getWorld("Sky");
	}
	public static World getEarth(){
		return  Bukkit.getWorlds().get(0);//Bukkit.getWorld("Sky");
	}
	public static void loadSky(){
		
		WorldCreator wc = new WorldCreator("Sky");
		wc.type(WorldType.NORMAL);
		wc.environment(Environment.NORMAL);
		
		wc.generator(new BasicChunkGenerator());
		Bukkit.getServer().createWorld(wc);
	}
	public static Boolean IsSky(World w){
		if (getSky() == null){return false;}
		return (w.equals(getSky()));
	}
	public static Boolean IsEarth(World w){
		if (getEarth() == null){return false;}
		return (w.equals(getEarth()));
	}
	public static Boolean IsInSky(Entity e){
		if (e == null){return false;}
		return IsSky(e.getWorld());
	}
	public static ItemStack getSkyFeather(){
		return Utils.setItemNameAndLore(new ItemStack(Material.GLASS), ChatColor.AQUA + "Bombolla de vidre", ChatColor.WHITE + "Permet respirar a l'espai");
	}
	public static void SkyFeatherRecipe(){
		ShapedRecipe r = new ShapedRecipe(getSkyFeather());
		r.shape("GGG", "G G", "   ");
		r.setIngredient('G', Material.GLASS);
		Bukkit.getServer().addRecipe(r);
	}
	public static void portalActivateToSky(ArrayList<Player> p, Block stoneButton){
		loadSkyIfNecessary();
		copyPortalToSky(stoneButton.getLocation());
		deletePortal(stoneButton.getLocation());
		for (Player pl : p){
			teleportPlayerToSky(pl);
			
		}
		
	}
	public static void portalActivateToEarth(ArrayList<Player> p, Block stoneButton){
		copyPortalToEarth(stoneButton.getLocation());
		deletePortal(stoneButton.getLocation());
		for (Player pl : p){
			teleportPlayerToEarth(pl);
		}
		
	}
	public static void makePlayerKnockUp(Player p){
		if (p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){return;}
		int delay = 2;
		final int speed = 1;
		final int maxHeight = 200;
		final String plyStr = p.getName();
		final Vector il = p.getLocation().toVector();
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60 * 20, 3), true);

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		final int taskid =  scheduler.scheduleSyncRepeatingTask(FastSurvival.getPlugin(), new Runnable() {
			@Override
			public void run() {

				Player ply = Bukkit.getPlayer(plyStr);
				if (ply == null){return;}
				if (ply.isDead() == true ){return;}
				if (!ply.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){return;}

				Location nextloc = ply.getLocation().clone().add(0, speed, 0);
				nextloc.setPitch(ply.getLocation().getPitch());
				nextloc.setYaw(ply.getLocation().getYaw());
				Cuboid c = new Cuboid(nextloc, il.toLocation(nextloc.getWorld()));

				if (ply.getLocation().getY() < maxHeight){
					ply.teleport(nextloc);	
					for (Block blk : c.getBlocks()){
						blk.setType(Material.WATER);
					}
				}else{
					for (Block blk : c.getBlocks()){
						if (blk.getType() == Material.WATER){
							blk.setType(Material.AIR);
						}
					}
					ply.sendMessage("Ja hi ets!");
					ply.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					///cancel
					BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
					scheduler.scheduleSyncDelayedTask(FastSurvival.getPlugin(), new Runnable() {
						@Override
						public void run() {
							Player ply2 = Bukkit.getPlayer(plyStr);
							MetadataValue metadataType = Utils.getMetadata(ply2, "KnockTask");
							if(metadataType == null){return;}
							int task = metadataType.asInt();

							if (task != 0){
								Bukkit.getServer().getScheduler().cancelTask(task);
								Bukkit.broadcastMessage("Cancelled!");
							}
						}
					}, 1L);
					///


					SkyUtils.teleportPlayerToSky(ply);
				}



			}
		}, 1L, delay);
		p.setMetadata("KnockTask", new FixedMetadataValue(FastSurvival.getPlugin(), taskid));
	}
	public static void teleportPlayerToSky(Player p){
		loadSkyIfNecessary();
		Location SkyLocation = getSkyLocation(p.getLocation(), 1);
		int highestBlockYAt = getSky().getHighestBlockYAt(SkyLocation.getBlockX(), SkyLocation.getBlockZ());
		SkyLocation.setY(highestBlockYAt + 1);
		p.teleport(SkyLocation);
		//p.setBedSpawnLocation(SkyLocation, true);
	}
	public static void teleportPlayerToEarth(Player p){
		Location SkyLocation = getEarthLocation(p.getLocation(),1);
		int highestBlockYAt = getEarth().getHighestBlockYAt(SkyLocation.getBlockX(), SkyLocation.getBlockZ());
		SkyLocation.setY(highestBlockYAt + 1);
		p.teleport(SkyLocation);
		//p.setBedSpawnLocation(SkyLocation, true);
	}
	public static void loadSkyIfNecessary() {
		if (getSky() == null){loadSky();}
	}
	public static ArrayList<Material> getSkyPortalMaterials(){
		ArrayList<Material> m = new ArrayList<Material>();
		m.add(Material.WOOD_BUTTON);
		m.add(Material.IRON_BLOCK);
		m.add(Material.IRON_FENCE);
		m.add(Material.DIAMOND_BLOCK);
		m.add(Material.GLASS);
		m.add(Material.GLOWSTONE);
		m.add(Material.IRON_PLATE);
		m.add(Material.NETHER_FENCE);
		m.add(Material.FURNACE);
		m.add(Material.REDSTONE_TORCH_ON);
		m.add(Material.REDSTONE_TORCH_OFF);
		m.add(Material.REDSTONE_BLOCK);
		m.add(Material.STONE_BUTTON);
		
		return m;
	}
	public static ArrayList<Block> detectSkyPortalBlocks(Location l){
		ArrayList<Block> blks = new ArrayList<Block>();
		Cuboid detected = Utils.getCuboidAround(l, 5, 6, 5);
		List<Block> blocks = detected.getBlocks();
		
		for(Block b : blocks){
			Material t = b.getType();
			if(getSkyPortalMaterials().contains(t)){blks.add(b);}
		}
		return blks;
		
	}
	public static int getButtonElevation(Location l){
		ArrayList<Block> blks = detectSkyPortalBlocks(l);
		int lowestY = 250;
		for(Block b : blks){
			if (b.getY() < lowestY){
				lowestY = b.getY();
			}
		}
		return l.getBlockY() - lowestY;
	}
	public static Location getSkyLocation(Location overworldLocation, int offset){
//		int highestBlockYAt = getSky().getHighestBlockYAt(overworldLocation.getBlockX() + offset, overworldLocation.getBlockZ());
		
		return new Location(getSky(), overworldLocation.getBlockX(), overworldLocation.getY(), overworldLocation.getBlockZ());
	}
	public static Location getEarthLocation(Location SkyLocation, int offset){
		int highestBlockYAt = getEarth().getHighestBlockYAt(SkyLocation.getBlockX(), SkyLocation.getBlockZ());
		
		return new Location(getEarth(), SkyLocation.getBlockX(), highestBlockYAt + offset, SkyLocation.getBlockZ());
	}
	public static Location getHighestYLoc(Location loc){
		int highestBlockYAt = loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ());
		Location newLoc = loc.clone();
		newLoc.setY(highestBlockYAt);
		return newLoc;
	}
//	public static Block getSkyBlock(Block overworldBlock){
//		
//	}
	@SuppressWarnings("deprecation")
	public static void copyPortalToSky(Location owBLoc){
		ArrayList<Block> portalBlocks = detectSkyPortalBlocks(owBLoc);
		Location SkyLocation = getHighestYLoc(getSkyLocation(owBLoc, 0));
		SkyLocation.add(0, getButtonElevation(owBLoc), 0);
		for(Block b : portalBlocks){
			Vector relV = Utils.CrearVector(owBLoc, b.getLocation());
			Location rel = SkyLocation.clone().add(relV);
			Block nb = rel.getBlock();
			nb.setType(b.getType());
			nb.setData(b.getData());
		}
		
	}
	@SuppressWarnings("deprecation")
	public static void copyPortalToEarth(Location mBLoc){
		ArrayList<Block> portalBlocks = detectSkyPortalBlocks(mBLoc);
		Location earthLocation = getHighestYLoc(getEarthLocation(mBLoc, 0));
		earthLocation.add(0, getButtonElevation(mBLoc), 0);
		for(Block b : portalBlocks){
			Vector relV = Utils.CrearVector(mBLoc, b.getLocation());
			Location rel = earthLocation.clone().add(relV);
			Block nb = rel.getBlock();
			nb.setType(b.getType());
			Bukkit.broadcastMessage(b.getType().name());
			nb.setData(b.getData());
		}
		
	}
	public static void deletePortal(Location owBLoc){
		ArrayList<Block> portalBlocks = detectSkyPortalBlocks(owBLoc);
		Location l = owBLoc.clone();
		for(Block b : portalBlocks){
			Vector relV = Utils.CrearVector(owBLoc, b.getLocation());
			Location rel = l.clone().add(relV);
			Block nb = rel.getBlock();
			nb.setType(Material.AIR);
			
		}
	}
}
