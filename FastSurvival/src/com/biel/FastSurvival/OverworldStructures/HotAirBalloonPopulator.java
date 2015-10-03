package com.biel.FastSurvival.OverworldStructures;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.util.Vector;

import com.biel.FastSurvival.Bows.BowRecipeGenerator;
import com.biel.FastSurvival.Bows.BowUtils;
import com.biel.FastSurvival.SpecialItems.SpecialItemsUtils;
import com.biel.FastSurvival.Utils.BUtils;
import com.biel.FastSurvival.Utils.Cuboid;
import com.biel.FastSurvival.Utils.Utils;

public class HotAirBalloonPopulator extends BlockPopulator{
	private static final int BALLOON_CHANCE = 1; // Out of 1000 (0.12%)
	@Override
	public void populate(World world, Random random, Chunk source) {
		if (!(random.nextInt(800) <= BALLOON_CHANCE)) {return;}
		int centerX = (source.getX() << 4) + random.nextInt(16);
		int centerZ = (source.getZ() << 4) + random.nextInt(16);
		int centerY = world.getHighestBlockYAt(centerX, centerZ);
		Location center = new Location(world, centerX, centerY + 20 + Utils.NombreEntre(1, 20), centerZ);
		generateBalloon(center);
		generateDownChest(center);
		generateEngine(center);
	}
	public void generateDownChest(Location center){
		Cuboid botC = new Cuboid(center.clone().add(-1 * 1, 0, -1 * 1),center.clone().add(1, 0, 1));
		BUtils.fillBlocks(botC.getBlocks(), Material.WOOD);
		center.getBlock().setType(Material.WORKBENCH);
		
		//Chest
		ArrayList<BlockFace> Edgefaces = new ArrayList<BlockFace>();
		Edgefaces.add(BlockFace.NORTH_EAST);
		Edgefaces.add(BlockFace.NORTH_WEST);
		Edgefaces.add(BlockFace.SOUTH_EAST);
		Edgefaces.add(BlockFace.SOUTH_WEST);
		
		Block cbl = center.getBlock().getRelative(BlockFace.UP).getRelative(Edgefaces.get(Utils.NombreEntre(0, Edgefaces.size() - 1)));
		cbl.setType(Material.CHEST);
		Utils.fillChestRandomly(cbl, getItemsForLevel());
		//---
		ArrayList<BlockFace> faces = new ArrayList<BlockFace>();
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.EAST);
		faces.add(BlockFace.WEST);
		ArrayList<BlockFace> facesNS = new ArrayList<BlockFace>();
		facesNS.add(BlockFace.NORTH);
		facesNS.add(BlockFace.SOUTH);
		ArrayList<BlockFace> facesWE = new ArrayList<BlockFace>();
		facesWE.add(BlockFace.EAST);
		facesWE.add(BlockFace.WEST);
		for (BlockFace f : faces){
			ArrayList<Block> woodBlocks = new ArrayList<Block>();
			ArrayList<Block> fenceBlocks = new ArrayList<Block>();
			Block sBlock = center.getBlock().getRelative(f, 2).getRelative(BlockFace.UP);
			woodBlocks.add(sBlock);
			fenceBlocks.add(sBlock.getRelative(BlockFace.UP));
			//Det. Blocs
			ArrayList<BlockFace> faces2 = new ArrayList<BlockFace>();
			if (facesNS.contains(f)){
				faces2.addAll(facesWE);
			}
			if (facesWE.contains(f)){
				faces2.addAll(facesNS);
			}
			//Omplir
			for (BlockFace f2 : faces2){
				Block bl = sBlock;
				woodBlocks.add(bl.getRelative(f2));
				fenceBlocks.add(bl.getRelative(f2, 2));
				fenceBlocks.add(bl.getRelative(f2).getRelative(BlockFace.UP));
				fenceBlocks.add(bl.getRelative(f2, 2).getRelative(BlockFace.UP));
				fenceBlocks.add(bl.getRelative(f2, 2).getRelative(BlockFace.UP, 2));
				fenceBlocks.add(bl.getRelative(f2, 2).getRelative(BlockFace.UP, 3));
			}
			BUtils.fillBlocks(woodBlocks, Material.WOOD);
			BUtils.fillBlocks(fenceBlocks, Material.FENCE);
		}
		
	}
	public void generateBalloon(Location center){
		ArrayList<Location> sphBlocks = Utils.getSphereLocations(center.clone().add(0, 10, 0), 6D, true);
//		ArrayList<Location> c1 = new ArrayList<Location>();
//		ArrayList<Location> c2 = new ArrayList<Location>();
//		Vector rnd = Vector.getRandom().normalize().multiply(6);
//		for (Location l : sphBlocks){
//			int x = l.getBlockX();
//			int z = l.getBlockZ();
//			Vector plain = Utils.CrearVector(center, new Location(center.getWorld(), x, 0, z)).multiply(6);
//			//--
//			double h = ((-1 * x * rnd.getBlockX()) - (z * rnd.getZ()) + rnd.length() * plain.length() * Math.cos(rnd.angle(plain))) / rnd.getBlockY();
//			Bukkit.broadcastMessage(Double.toString(h));
//			h = 
//			//--
//			if (l.getBlockY() < h){
//				c1.add(l);
//			}else{
//				c2.add(l);
//			}
//		}
		DyeColor c = null;
		while (c == null){
			if (Utils.Possibilitat(8)){c = DyeColor.RED;}
			if (Utils.Possibilitat(4)){c = DyeColor.YELLOW;}
			if (Utils.Possibilitat(4)){c = DyeColor.WHITE;}
			if (Utils.Possibilitat(3)){c = DyeColor.ORANGE;}
			if (Utils.Possibilitat(1)){c = DyeColor.BLACK;}
			if (Utils.Possibilitat(2)){c = DyeColor.BLUE;}
			if (Utils.Possibilitat(1)){c = DyeColor.GREEN;}
			if (Utils.Possibilitat(1)){c = DyeColor.GRAY;}
		}
		BUtils.fillBlocks(BUtils.locListToBlock(sphBlocks), Material.WOOL, c.getData());
		//BUtils.fillBlocks(BUtils.locListToBlock(c2), Material.WOOL, DyeColor.BLACK.getData());
	}
	public void generateEngine(Location center){
		Location CLoc = center.clone().add(0, 5, 0);
		//Clean wool
		Cuboid botC = new Cuboid(CLoc.clone().add(-1 * 1, -1, -1 * 1),CLoc.clone().add(1, 1, 1));
		BUtils.fillBlocks(botC.getBlocks(), Material.AIR);
		//--
		Block Cblk = CLoc.getBlock();
		Cblk.setType(Material.REDSTONE_BLOCK);
		Cblk.getRelative(BlockFace.UP).setType(Material.REDSTONE_LAMP_ON);
		ArrayList<BlockFace> faces = new ArrayList<BlockFace>();
		faces.add(BlockFace.NORTH);
		faces.add(BlockFace.SOUTH);
		faces.add(BlockFace.EAST);
		faces.add(BlockFace.WEST);
		for(BlockFace f : faces){
			Block bl = Cblk.getRelative(f);
			bl.setType(Material.FENCE);
		}
	}
	public ArrayList<ItemStack> getItemsForLevel() {
		ArrayList<ItemStack> i = new ArrayList<ItemStack>();

		if (Utils.Possibilitat(80)){i.add(new ItemStack(Material.BREAD, Utils.NombreEntre(1,  25)));}
		if (Utils.Possibilitat(80)){i.add(new ItemStack(Material.RAW_BEEF, Utils.NombreEntre(1,  25)));}
		if (Utils.Possibilitat(80)){i.add(new ItemStack(Material.REDSTONE_BLOCK, Utils.NombreEntre(1,  5)));}
		if (Utils.Possibilitat(80)){i.add(new ItemStack(Material.REDSTONE_LAMP_OFF, Utils.NombreEntre(1,  5)));}
		if (Utils.Possibilitat(10)){i.add(new ItemStack(Material.BAKED_POTATO, Utils.NombreEntre(1,  18)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.CARROT, Utils.NombreEntre(1,  3)));}
		if (Utils.Possibilitat(9)){new ItemStack(SpecialItemsUtils.getRandomSpecialItem(1));}
		if (Utils.Possibilitat(60)){
			
			if (Utils.Possibilitat(50)){
				//Càrrega
				if (Utils.Possibilitat(85)){
					if (Utils.Possibilitat(75)){i.add(new ItemStack(Material.DIAMOND, Utils.NombreEntre(1,  2)));}
					if (Utils.Possibilitat(45)){i.add(new ItemStack(Material.DIAMOND, Utils.NombreEntre(1,  5)));}
					if (Utils.Possibilitat(65)){i.add(new ItemStack(Material.QUARTZ, Utils.NombreEntre(1,  23)));}
					if (Utils.Possibilitat(65)){i.add(new ItemStack(Material.QUARTZ_BLOCK, Utils.NombreEntre(1,  5)));}
					if (Utils.Possibilitat(54)){i.add(new ItemStack(Material.DIAMOND_BLOCK, Utils.NombreEntre(1,  2)));}
					if (Utils.Possibilitat(75)){i.add(new ItemStack(Material.GOLDEN_APPLE, Utils.NombreEntre(1,  8)));}
					if (Utils.Possibilitat(65)){i.add(new ItemStack(Material.GOLD_BLOCK, Utils.NombreEntre(1,  8)));}
					if (Utils.Possibilitat(45)){i.add(new ItemStack(Material.REDSTONE, Utils.NombreEntre(1,  40)));}
					if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.WHEAT, Utils.NombreEntre(1,  64)));}
					if (Utils.Possibilitat(8)){i.add(new ItemStack(Material.ENDER_PEARL, Utils.NombreEntre(1,  4)));}
					if (Utils.Possibilitat(8)){i.add(new ItemStack(Material.EYE_OF_ENDER, Utils.NombreEntre(1,  2)));}
					if (Utils.Possibilitat(8)){i.add(new ItemStack(Material.OBSIDIAN, Utils.NombreEntre(1,  4)));}
					if (Utils.Possibilitat(95)){i.add(new ItemStack(Material.GOLD_INGOT, Utils.NombreEntre(1,  20)));}
					if (Utils.Possibilitat(70)){i.add(new ItemStack(Material.IRON_INGOT, Utils.NombreEntre(1,  20)));}
					if (Utils.Possibilitat(85)){i.add(new ItemStack(Material.BONE, Utils.NombreEntre(1,  8)));}
					if (Utils.Possibilitat(15)){i.add(new ItemStack(Material.BONE, Utils.NombreEntre(1,  6)));}
					if (Utils.Possibilitat(35)){i.add(new ItemStack(Material.EXP_BOTTLE, Utils.NombreEntre(1,  6)));}
					
				}else{
					int count = Utils.NombreEntre(10, 23);
					while(count >= 0){
						i.add(Utils.setItemName(new ItemStack(Material.GOLD_NUGGET, Utils.NombreEntre(1,  64)), "Moneda d'or" + ChatColor.GOLD));
						
						count--;
					}
					
				}
				
				
			}else{
				//Guerra
				if (Utils.Possibilitat(85)){
					if (Utils.Possibilitat(68)){i.add(BowRecipeGenerator.getRandomBow(false));}
					if (Utils.Possibilitat(8)){i.add(BowRecipeGenerator.getRandomBow(false));}
					if (Utils.Possibilitat(4)){i.add(BowRecipeGenerator.getRandomBow(false));}
					if (Utils.Possibilitat(50)){i.add(new ItemStack(Material.DIAMOND_HELMET, 1));}
					if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.DIAMOND_SWORD, 1));}
					if (Utils.Possibilitat(30)){i.add(new ItemStack(Material.WOOD_SWORD, 1));}
					if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.DIAMOND_HELMET, 1));}
					if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.IRON_HELMET, 1));}
					if (Utils.Possibilitat(30)){i.add(new ItemStack(Material.IRON_SWORD, 1));}
					if (Utils.Possibilitat(30)){i.add(new ItemStack(Material.IRON_SPADE, 1));}
					if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.ARROW, 1));}
					if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.FLINT_AND_STEEL, 1));}
					if (Utils.Possibilitat(30)){i.add(new ItemStack(Material.IRON_DOOR, 1));}
					if (Utils.Possibilitat(20)){i.add(new ItemStack(Material.IRON_CHESTPLATE, 1));}
					if (Utils.Possibilitat(30)){i.add(new ItemStack(Material.SNOW_BALL, 16));}
					if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.SNOW_BALL, 16));}
					if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.TORCH, 18));}
					if (Utils.Possibilitat(50)){i.add(new ItemStack(Material.TNT, Utils.NombreEntre(1,  6)));}
					if (Utils.Possibilitat(25)){i.add(new ItemStack(Material.TNT, Utils.NombreEntre(1,  6)));}
					if (Utils.Possibilitat(14)){i.add(new ItemStack(Material.TNT, Utils.NombreEntre(1,  6)));}
					if (Utils.Possibilitat(65)){i.add(Utils.getRandomPotion());}
					if (Utils.Possibilitat(38)){i.add(Utils.getRandomPotion());}
					if (Utils.Possibilitat(35)){i.add(Utils.getRandomPotion());}
					if (Utils.Possibilitat(1)){new ItemStack(SpecialItemsUtils.getRandomSpecialItem(3));}
				}else{
					int count = Utils.NombreEntre(10, 20);
					while(count >= 0){
						i.add(new ItemStack(Material.EXP_BOTTLE, Utils.NombreEntre(1,  8)));
						count--;
					}
				}
				
			}
			
		}

		return i;
	}
	
}
