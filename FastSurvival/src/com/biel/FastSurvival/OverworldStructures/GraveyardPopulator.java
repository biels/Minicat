package com.biel.FastSurvival.OverworldStructures;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;

import com.biel.FastSurvival.Dimensions.Moon.ElectricBossPopulator.LevelType;
import com.biel.FastSurvival.SpecialItems.SpecialItemsUtils;
import com.biel.FastSurvival.Utils.Utils;

public class GraveyardPopulator extends BlockPopulator {
	private static final int GRAVEYARD_CHANCE = 2; // Out of 150 (1.25%)
	@Override
	public void populate(World world, Random random, Chunk source) {
		if (!(random.nextInt(164) <= GRAVEYARD_CHANCE)) {return;}
		int centerX = (source.getX() << 4) + random.nextInt(16);
		int centerZ = (source.getZ() << 4) + random.nextInt(16);
		int centerY = world.getHighestBlockYAt(centerX, centerZ);
		Location center = new Location(world, centerX, centerY, centerZ);
		
		Material tc = center.getBlock().getRelative(BlockFace.DOWN).getType();
		if (tc == Material.LEAVES || tc == Material.LEAVES_2 || tc == Material.WATER || tc == Material.WOOD || tc == Material.LAVA || tc == Material.LOG){
			return;
		}
		
		ArrayList<BlockFace> faces = new ArrayList<BlockFace>();
		Boolean dir;
		if (Utils.Possibilitat(50)){
			dir = false;
			faces.add(BlockFace.NORTH);
			faces.add(BlockFace.SOUTH);
		}else{
			dir = true;
			faces.add(BlockFace.EAST);
			faces.add(BlockFace.WEST);
		}
		boolean yesno = false;
		int numb = Utils.NombreEntre(5, 16);
		int step = -1 * numb;
		while (step <= numb){
			yesno = !yesno;
			
			Location l;
			if (!dir){
				l = center.clone().add(step, 0, 0);
			}else{
				l = center.clone().add(0, 0, step);
			}
			for (BlockFace f : faces){
				int intStep = 0;
				while (intStep <= 4){
					Block b = l.clone().getBlock().getRelative(f, intStep);
					if (!l.clone().getBlock().getRelative(f, 4).getRelative(BlockFace.DOWN).getType().isSolid()){break;}
					if (intStep == 0 && Utils.Possibilitat(95)){
						if (!b.getRelative(BlockFace.DOWN, 2).getType().isSolid()){break;}
						b.getRelative(BlockFace.DOWN).setType(Material.COBBLESTONE);
						
					}
					
					if (!yesno || Utils.Possibilitat(8)){break;}
					//1 as is
					if (intStep == 2 || intStep == 3){
						if (!b.getRelative(BlockFace.DOWN).getType().isSolid()){break;}
						b.setType(Material.STEP);
						b.setData((byte) 0);
						if (Utils.Possibilitat(85)){
							
							if (Utils.Possibilitat(48)){
								//Chest
								Block bl = b.getRelative(BlockFace.DOWN, 1);
								bl.setType(Material.CHEST);
								Utils.fillChestRandomly(bl, getItemsForLevel());
							}else{
								//Spawner
								Block bl = b.getRelative(BlockFace.DOWN, Utils.NombreEntre(1, 4));
								bl.setType(Material.MOB_SPAWNER);
								CreatureSpawner spawner = (CreatureSpawner) bl.getState();
								spawner.setSpawnedType(EntityType.ZOMBIE);
								if (Utils.Possibilitat(15)){
									spawner.setSpawnedType(EntityType.SKELETON);
								}
								spawner.update();
							}
						}
					}
					if (intStep == 4){
						Block bl = b;//.getRelative(BlockFace.UP);
						bl.setType(Material.COBBLESTONE);
						if (Utils.Possibilitat(1)){bl.setType(Material.COAL_BLOCK);}
						if (Utils.Possibilitat(1)){bl.setType(Material.GOLD_BLOCK);}
						if (Utils.Possibilitat(1)){bl.setType(Material.QUARTZ_BLOCK);}
						if (Utils.Possibilitat(1)){bl.setType(Material.WOOD);}
						if (Utils.Possibilitat(1)){bl.setType(Material.WOOD_STAIRS);}
						if (Utils.Possibilitat(1)){bl.setType(Material.COBBLESTONE_STAIRS);}


					}
					intStep++;
				}
			}
			step++;
		}
				
	}
	public ArrayList<ItemStack> getItemsForLevel() {
		ArrayList<ItemStack> i = new ArrayList<ItemStack>();
		if (Utils.Possibilitat(3)){new ItemStack(SpecialItemsUtils.getRandomSpecialItem(1));}
		if (Utils.Possibilitat(10)){i.add(new ItemStack(Material.ROTTEN_FLESH, Utils.NombreEntre(1,  25)));}
		if (Utils.Possibilitat(10)){i.add(new ItemStack(Material.ROTTEN_FLESH, Utils.NombreEntre(1,  25)));}
		if (Utils.Possibilitat(10)){i.add(new ItemStack(Material.ROTTEN_FLESH, Utils.NombreEntre(1,  15)));}
		if (Utils.Possibilitat(10)){i.add(new ItemStack(Material.ROTTEN_FLESH, Utils.NombreEntre(1,  18)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.CARROT, Utils.NombreEntre(1,  3)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.DIAMOND_HELMET, 1));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.IRON_SWORD, Utils.NombreEntre(1,  3)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.IRON_SPADE, Utils.NombreEntre(1,  3)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.DIAMOND, Utils.NombreEntre(1,  2)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.REDSTONE, Utils.NombreEntre(1,  2)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.WHEAT, Utils.NombreEntre(1,  20)));}
		if (Utils.Possibilitat(3)){i.add(new ItemStack(Material.ENDER_PEARL, Utils.NombreEntre(1,  4)));}
		if (Utils.Possibilitat(2)){i.add(new ItemStack(Material.WEB, Utils.NombreEntre(1,  3)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.WEB, Utils.NombreEntre(1,  6)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.GOLD_INGOT, Utils.NombreEntre(1,  4)));}
		if (Utils.Possibilitat(85)){i.add(new ItemStack(Material.BONE, Utils.NombreEntre(1,  8)));}
		if (Utils.Possibilitat(15)){i.add(new ItemStack(Material.BONE, Utils.NombreEntre(1,  6)));}
		if (Utils.Possibilitat(15)){i.add(new ItemStack(Material.EXP_BOTTLE, Utils.NombreEntre(1,  6)));}
		if (Utils.Possibilitat(15)){i.add(Utils.getRandomPotion());}
		if (Utils.Possibilitat(15)){i.add(Utils.setItemName(new ItemStack(Material.GOLD_NUGGET, Utils.NombreEntre(1,  2)), "Dent d'or" + ChatColor.GOLD));}
		if (Utils.Possibilitat(38)){i.add(new ItemStack(Material.POISONOUS_POTATO, Utils.NombreEntre(1,  30)));}
		return i;
	}
}
