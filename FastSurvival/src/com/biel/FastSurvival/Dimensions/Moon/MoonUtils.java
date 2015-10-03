package com.biel.FastSurvival.Dimensions.Moon;

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
import org.bukkit.util.Vector;

import com.biel.FastSurvival.Bows.BowUtils.BowType;
import com.biel.FastSurvival.Utils.Cuboid;
import com.biel.FastSurvival.Utils.Utils;

public class MoonUtils {

	public static World getMoon(){
		return  Bukkit.getWorld("Moon");//Bukkit.getWorld("Moon");
	}
	public static World getEarth(){
		return  Bukkit.getWorlds().get(0);//Bukkit.getWorld("Moon");
	}
	public static void loadMoon(){

		WorldCreator wc = new WorldCreator("Moon");
		wc.type(WorldType.NORMAL);
		wc.environment(Environment.NORMAL);

		wc.generator(new MoonChunkGenerator());
		Bukkit.getServer().createWorld(wc);

	}
	public static Boolean IsMoon(World w){
		if (getMoon() == null){return false;}
		return (w.equals(getMoon()));
	}
	public static Boolean IsEarth(World w){
		if (getEarth() == null){return false;}
		return (w.equals(getEarth()));
	}
	public static Boolean IsInMoon(Entity e){
		if (e == null){return false;}
		return IsMoon(e.getWorld());
	}
	public static ItemStack getSpaceGlass(){
		return Utils.setItemNameAndLore(new ItemStack(Material.GLASS), ChatColor.AQUA + "Bombolla de vidre", ChatColor.WHITE + "Permet respirar a l'espai");
	}
	public static void spaceGlassRecipe(){
		ShapedRecipe r = new ShapedRecipe(getSpaceGlass());
		r.shape("GGG", "G G", "   ");
		r.setIngredient('G', Material.GLASS);
		Bukkit.getServer().addRecipe(r);
	}
	public static void portalActivateToMoon(ArrayList<Player> p, Block stoneButton){
		loadMoonIfNecessary();
		copyPortalToMoon(stoneButton.getLocation());
		deletePortal(stoneButton.getLocation());
		for (Player pl : p){
			teleportPlayerToMoon(pl);

		}

	}
	public static void portalActivateToEarth(ArrayList<Player> p, Block stoneButton){
		copyPortalToEarth(stoneButton.getLocation());
		deletePortal(stoneButton.getLocation());
		for (Player pl : p){
			teleportPlayerToEarth(pl);
		}

	}
	public static void teleportPlayerToMoon(Player p){
		loadMoonIfNecessary();
		Location moonLocation = getMoonLocation(p.getLocation(), 1);
		int highestBlockYAt = getMoon().getHighestBlockYAt(moonLocation.getBlockX(), moonLocation.getBlockZ());
		moonLocation.setY(highestBlockYAt + 1);
		p.teleport(moonLocation);
		//p.setBedSpawnLocation(moonLocation, true);
	}
	public static void teleportPlayerToEarth(Player p){
		Location moonLocation = getEarthLocation(p.getLocation(),1);
		int highestBlockYAt = getEarth().getHighestBlockYAt(moonLocation.getBlockX(), moonLocation.getBlockZ());
		moonLocation.setY(highestBlockYAt + 1);
		p.teleport(moonLocation);
		//p.setBedSpawnLocation(moonLocation, true);
	}
	public static void loadMoonIfNecessary() {
		if (getMoon() == null){loadMoon();}
	}
	public static ArrayList<Material> getMoonPortalMaterials(){
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
	public static ArrayList<Block> detectMoonPortalBlocks(Location l){
		ArrayList<Block> blks = new ArrayList<Block>();
		Cuboid detected = Utils.getCuboidAround(l, 5, 6, 5);
		List<Block> blocks = detected.getBlocks();

		for(Block b : blocks){
			Material t = b.getType();
			if(getMoonPortalMaterials().contains(t)){blks.add(b);}
		}
		return blks;

	}
	public static int getButtonElevation(Location l){
		ArrayList<Block> blks = detectMoonPortalBlocks(l);
		int lowestY = 250;
		for(Block b : blks){
			if (b.getY() < lowestY){
				lowestY = b.getY();
			}
		}
		return l.getBlockY() - lowestY;
	}
	public static Location getMoonLocation(Location overworldLocation, int offset){
		//		int highestBlockYAt = getMoon().getHighestBlockYAt(overworldLocation.getBlockX() + offset, overworldLocation.getBlockZ());

		return new Location(getMoon(), overworldLocation.getBlockX(), overworldLocation.getY(), overworldLocation.getBlockZ());
	}
	public static Location getEarthLocation(Location moonLocation, int offset){
		int highestBlockYAt = getEarth().getHighestBlockYAt(moonLocation.getBlockX(), moonLocation.getBlockZ());

		return new Location(getEarth(), moonLocation.getBlockX(), highestBlockYAt + offset, moonLocation.getBlockZ());
	}
	public static Location getHighestYLoc(Location loc){
		int highestBlockYAt = loc.getWorld().getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ());
		Location newLoc = loc.clone();
		newLoc.setY(highestBlockYAt);
		return newLoc;
	}
	//	public static Block getMoonBlock(Block overworldBlock){
	//		
	//	}
	@SuppressWarnings("deprecation")
	public static void copyPortalToMoon(Location owBLoc){
		ArrayList<Block> portalBlocks = detectMoonPortalBlocks(owBLoc);
		Location moonLocation = getHighestYLoc(getMoonLocation(owBLoc, 0));
		moonLocation.add(0, getButtonElevation(owBLoc), 0);
		setPortalCopyBlocks(owBLoc, portalBlocks, moonLocation);

	}
	public static void copyPortalToEarth(Location mBLoc){
		ArrayList<Block> portalBlocks = detectMoonPortalBlocks(mBLoc);
		Location earthLocation = getHighestYLoc(getEarthLocation(mBLoc, 0));
		earthLocation.add(0, getButtonElevation(mBLoc), 0);
		setPortalCopyBlocks(mBLoc, portalBlocks, earthLocation);

	}
	@SuppressWarnings("deprecation")
	public static void setPortalCopyBlocks(Location mBLoc,
			ArrayList<Block> portalBlocks, Location earthLocation) {
		for(Block b : portalBlocks){
			Vector relV = Utils.CrearVector(mBLoc, b.getLocation());
			Location rel = earthLocation.clone().add(relV);
			Block nb = rel.getBlock();
			Material type = nb.getType();
			if (type.isSolid()){
				nb.setType(b.getType());
				if (type == Material.COAL_BLOCK && Utils.Possibilitat(70)){
					nb.setType(Material.GLASS);
				}
				if (type == Material.REDSTONE_BLOCK && Utils.Possibilitat(30)){
					nb.setType(Material.GLASS);
				}
				nb.setData(b.getData());
			}
		}
		for(Block b : portalBlocks){
			Vector relV = Utils.CrearVector(mBLoc, b.getLocation());
			Location rel = earthLocation.clone().add(relV);
			Block nb = rel.getBlock();
			if (!nb.getType().isSolid()){
				nb.setType(b.getType());
				nb.setData(b.getData());
			}
		}
	}
	public static void deletePortal(Location owBLoc){
		ArrayList<Block> portalBlocks = detectMoonPortalBlocks(owBLoc);
		Location l = owBLoc.clone();
		//TRANSPARENT BLOCKS
		for(Block b : portalBlocks){
			Vector relV = Utils.CrearVector(owBLoc, b.getLocation());
			Location rel = l.clone().add(relV);
			Block nb = rel.getBlock();
			if (!nb.getType().isSolid()){
				nb.setType(Material.AIR);
			}			
		}
		//SOLID BLOCKS
		for(Block b : portalBlocks){
			Vector relV = Utils.CrearVector(owBLoc, b.getLocation());
			Location rel = l.clone().add(relV);
			Block nb = rel.getBlock();
			if (nb.getType().isSolid()){
				nb.setType(Material.AIR);
			}			
		}
	}
}
