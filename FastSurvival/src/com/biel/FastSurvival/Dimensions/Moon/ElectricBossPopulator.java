package com.biel.FastSurvival.Dimensions.Moon;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box.Filler;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.biel.FastSurvival.Bows.BowRecipeGenerator;
import com.biel.FastSurvival.Bows.BowUtils;
import com.biel.FastSurvival.SpecialItems.SpecialItemsUtils;
import com.biel.FastSurvival.Utils.Cuboid;
import com.biel.FastSurvival.Utils.Utils;

public class ElectricBossPopulator extends BlockPopulator{
	private static final int BOSS_CHANCE = 2; // Out of 200
	private static final int FLAG_HEIGHT = 3;
	public enum LevelType{SKELETON, ZOMBIE, MAGIC, SPIDER, ICY, SILVERFISH, ABYSS, BONUS}

	public void populate(World world, Random random, Chunk source) {
		if (random.nextInt(125) <= BOSS_CHANCE) {

			int BLOCKS_PER_LEVEL = 5;
			int MAX_LEVELS = Utils.NombreEntre(7, 9);
			int LEVELS = MAX_LEVELS;
			int TOP_LEVEL_RADIUS = 7;
			int HEIGHT = BLOCKS_PER_LEVEL * LEVELS;
			int centerX = (source.getX() << 4) + random.nextInt(16);
			int centerZ = (source.getZ() << 4) + random.nextInt(16);
			int centerY = world.getHighestBlockYAt(centerX, centerZ) - HEIGHT;
			if (centerY <= 0){centerY = 1;} 
			
			Location center = new Location(world, centerX, centerY, centerZ);
			Block bCenter = center.getBlock();
			//No sobreposar
			if (bCenter.getType() != Material.STAINED_CLAY){return;}
			
			BlockFace direction = null;
			Block top = null;
			int dir = random.nextInt(4);

			if (dir == 0) {
				direction = BlockFace.NORTH;
			} else if (dir == 1) {
				direction = BlockFace.EAST;
			} else if (dir == 2) {
				direction = BlockFace.SOUTH;
			} else {
				direction = BlockFace.WEST;
			}

			for (int y = centerY; y < centerY + FLAG_HEIGHT; y++) {
				top = world.getBlockAt(centerX, y, centerZ);
				top.setType(Material.FENCE);
			}
			Cuboid c = Utils.getCuboidAround(top.getLocation(), 10, 1, 10);
			LevelType lastLevelType = null;
			Location prLoc = top.getLocation().clone();
			while (LEVELS > 0){
				int CURRENT_LEVEL_RADIUS = TOP_LEVEL_RADIUS + LEVELS;
				LevelType t = getLevelType();
				ArrayList<Block> empty = Utils.getCylBlocks(prLoc, CURRENT_LEVEL_RADIUS, BLOCKS_PER_LEVEL, true);
				for(Block b : empty){
					b.setType(Material.AIR);
				}
				ArrayList<Block> floor = Utils.getCylBlocks(prLoc, CURRENT_LEVEL_RADIUS, 1, true);
				boolean lastLevel = LEVELS == MAX_LEVELS;
				boolean topLevel = LEVELS == 0;
				int sponges = Utils.NombreEntre(1, 3);
				for(Block b : floor){
					b.setType(Material.WOOD);
					if(lastLevel){
						b.setType(Material.BEDROCK);
						if (sponges > 0){
							Block sb = b.getRelative(BlockFace.UP);
							sb.setType(Material.SPONGE);
							sponges--;
						}
					}
				}
				ArrayList<Block> walls = Utils.getOuterCylBlocks(prLoc, CURRENT_LEVEL_RADIUS + 1, BLOCKS_PER_LEVEL, false);
				for(Block b : walls){
					b.setType(Material.BEDROCK);
				}

				if(lastLevel){
					while (t == LevelType.BONUS || t == LevelType.ICY){
						t = getLevelType();
					}
				}
				if(topLevel){
					while (t == LevelType.BONUS){
						t = getLevelType();
					}
				}
				while (t == lastLevelType){
					t = getLevelType();
				}
				lastLevelType = t;
				//Decor
				Location aLoc = prLoc.clone();
				aLoc.add(0, 1, 0);
				//spawner.setDelay(20 * 3);
				EntityType st = EntityType.SKELETON;
				EntityType st2 = EntityType.SKELETON;
				int prob = 0;


				ArrayList<Block> internalZone =  Utils.getCylBlocks(aLoc.clone(), CURRENT_LEVEL_RADIUS, BLOCKS_PER_LEVEL - 1, true);


				ArrayList<Block> bonusZone =  Utils.getOuterCylBlocks(aLoc.clone(), CURRENT_LEVEL_RADIUS, 1, false);
				int chests = Utils.NombreEntre(2, 3);


				//Chests
				while (chests > 0){
					Block b = bonusZone.get(Utils.NombreEntre(0, bonusZone.size() - 1));
					b.setType(Material.CHEST);
					Utils.fillChestRandomly(b, getItemsForLevel(t));
					chests--;
				}
				//Level random specific
				switch(t){
				case ABYSS:
					st = EntityType.BLAZE;
					st2 = EntityType.MAGMA_CUBE;
					prob = 25;
					for (Block b : internalZone){
						if (Utils.Possibilitat(20)){
							if (b.isEmpty() && !b.getRelative(BlockFace.DOWN).isEmpty()){b.setType(Material.NETHERRACK);};
						}
					}
					break;
				case BONUS:
					st = EntityType.PIG_ZOMBIE;
					st2 = EntityType.ZOMBIE;
					prob = 20;
					for (Block b : bonusZone){
						if (Utils.Possibilitat(2)){
							Material topmat = Material.BOOKSHELF;
							if (Utils.Possibilitat(40)){topmat = Material.WORKBENCH;}
							if (b.isEmpty()){b.setType(Material.BOOKSHELF);b.getLocation().add(0, 1, 0).getBlock().setType(topmat);
							}

						}
						if (Utils.Possibilitat(10)){
							if (b.isEmpty()){b.setType(Material.WORKBENCH);}
						}
						if (Utils.Possibilitat(10)){
							if (b.isEmpty()){b.setType(Material.REDSTONE_LAMP_OFF);}
						}
					}
					for (Block b : internalZone){
						if (Utils.Possibilitat(20)){
							if (b.isEmpty() && !b.getRelative(BlockFace.DOWN).isEmpty()){b.setType(Material.GOLD_PLATE);};
						}
					}
					for (Block b : internalZone){
						if (Utils.Possibilitat(20)){
							if (b.isEmpty() && !b.getRelative(BlockFace.DOWN).isEmpty()){b.setType(Material.IRON_PLATE);};
						}
					}
					break;
				case ICY:
					st = EntityType.SKELETON;
					for (Block b : internalZone){
						if (Utils.Possibilitat(25)){
							if (b.isEmpty()){b.setType(Material.PACKED_ICE);};
						}
					}
					//						for (Block b : internalZone){
					//							if (Utils.Possibilitat(10)){
					//								if (b.isEmpty()){b.setType(Material.ICE);};
					//							}
					//						}
					break;

				case MAGIC:
					st = EntityType.WITCH;
					st2 = EntityType.ZOMBIE;
					prob = 5;
					for (Block b : bonusZone){
						if (Utils.Possibilitat(20)){
							Material topmat = Material.BOOKSHELF;
							if (Utils.Possibilitat(40)){topmat = Material.FLOWER_POT;}
							if (b.isEmpty()){b.setType(Material.BOOKSHELF);b.getLocation().add(0, 1, 0).getBlock().setType(topmat);
							}

						}
					}
					for (Block b : bonusZone){
						if (Utils.Possibilitat(8)){
							if (b.isEmpty()){
								b.setType(Material.BREWING_STAND);
								BrewingStand bw = (BrewingStand) b.getState();
								ItemStack ing = null;
								ing = Utils.getBrewingItems().get(Utils.NombreEntre(0, Utils.getBrewingItems().size() - 1));
								if (ing != null){
									bw.getInventory().setIngredient(ing);
								}
								int sl = 2;
								while (sl >= 0){
									if (Utils.Possibilitat(38)){continue;}
									bw.getInventory().setItem(sl, Utils.getRandomPotion());
									sl--;
								}

							}
						}

					}
					for (Block b : internalZone){
						if (Utils.Possibilitat(4)){
							if (b.isEmpty()){b.setType(Material.CAULDRON);};
						}
					}
					break;
				case SILVERFISH:
					st = EntityType.SILVERFISH;
					for (Block b : internalZone){
						if (Utils.Possibilitat(12)){
							if (b.isEmpty()){b.setType(Material.COBBLESTONE);};
						}
					}
					break;
				case SKELETON:
					st = EntityType.SKELETON;
					for (Block b : bonusZone){
						if (Utils.Possibilitat(20)){
							if (b.isEmpty()){b.setType(Material.SOIL);b.getLocation().add(0, 1, 0).getBlock().setType(Material.CROPS);};
						}
					}
					for (Block b : internalZone){
						if (Utils.Possibilitat(20)){
							if (b.isEmpty() && b.getRelative(BlockFace.DOWN).getType() == Material.WOOD){b.setType(Material.FENCE);};
						}
						if (Utils.Possibilitat(1)){
							if (b.isEmpty() && !b.getRelative(BlockFace.DOWN).isEmpty()){b.setType(Material.FENCE_GATE);};
						}
					}
					break;
				case SPIDER:
					st = EntityType.SPIDER;
					st2 = EntityType.CAVE_SPIDER;
					prob = 60;


					for (Block b : internalZone){
						if (Utils.Possibilitat(15)){
							if (b.isEmpty()){b.setType(Material.WEB);};
						}
					}
					break;
				case ZOMBIE:
					st = EntityType.ZOMBIE;
					for (Block b : bonusZone){
						
						if (Utils.Possibilitat(5)){
							if (b.isEmpty()){b.setType(Material.WORKBENCH);}
						}
						if (Utils.Possibilitat(2)){
							if (b.isEmpty()){b.setType(Material.REDSTONE_LAMP_OFF);}
						}
					}
					for (Block b : internalZone){
						if (Utils.Possibilitat(15)){
							if (b.isEmpty()){b.setType(Material.IRON_FENCE);};
						}
					}
					break;
				default:
					break;

				}
				ArrayList<Location> spLocs = new ArrayList<Location>();
				while (true){
					if (Utils.Possibilitat(18)){
						Location onesLoc = aLoc.clone();
						spLocs.add(onesLoc);
						break;
					}
					if (Utils.Possibilitat(32)){
						Location onesLoc = aLoc.clone();
						if (Utils.Possibilitat(50)){
							spLocs.add(onesLoc.getBlock().getRelative(BlockFace.NORTH).getLocation());
							spLocs.add(onesLoc.getBlock().getRelative(BlockFace.SOUTH).getLocation());
						}else{
							spLocs.add(onesLoc.getBlock().getRelative(BlockFace.EAST).getLocation());
							spLocs.add(onesLoc.getBlock().getRelative(BlockFace.WEST).getLocation());
						}

						break;
					}
					if (Utils.Possibilitat(30)){
						Location onesLoc = aLoc.clone();
						spLocs.add(onesLoc.getBlock().getRelative(BlockFace.NORTH_WEST).getLocation());
						spLocs.add(onesLoc.getBlock().getRelative(BlockFace.NORTH_EAST).getLocation());
						spLocs.add(onesLoc.getBlock().getRelative(BlockFace.SOUTH_WEST).getLocation());
						spLocs.add(onesLoc.getBlock().getRelative(BlockFace.SOUTH_EAST).getLocation());
						break;
					}

				}

				for (Location l : spLocs){


					Block b = l.getBlock();
					b.setType(Material.MOB_SPAWNER);
					CreatureSpawner spawner = (CreatureSpawner) b.getState();
					spawner.setSpawnedType(st);
					if (Utils.Possibilitat(prob)){
						spawner.setSpawnedType(st2);
					}
					spawner.update();
					if (t == LevelType.ICY || Utils.Possibilitat(10)){
						ArrayList<BlockFace> faces = new ArrayList<BlockFace>();
						faces.add(BlockFace.NORTH);
						faces.add(BlockFace.SOUTH);
						faces.add(BlockFace.EAST);
						faces.add(BlockFace.WEST);
						faces.add(BlockFace.UP);
						for (BlockFace f : faces){
							Block relB = b.getRelative(f);
							relB.setType(Material.WOOD);
						}
					}
					if (lastLevel){
						Block sb = b.getRelative(BlockFace.UP);
						sb.setType(Material.SPONGE);
					}
				}
				

				//END
				LEVELS--;
				prLoc = prLoc.clone().add(0, BLOCKS_PER_LEVEL, 0);
			}

		}

	}
	public ArrayList<ItemStack> getItemsForLevel(LevelType t) {
		ArrayList<ItemStack> i = new ArrayList<ItemStack>();
		switch(t){
		case ABYSS:
			if (Utils.Possibilitat(36)){i.add(new ItemStack(Material.COAL, Utils.NombreEntre(8,  10)));}
			if (Utils.Possibilitat(36)){i.add(new ItemStack(Material.BLAZE_ROD, Utils.NombreEntre(1,  3)));}
			if (Utils.Possibilitat(36)){i.add(new ItemStack(Material.GOLD_NUGGET, Utils.NombreEntre(8,  16)));}
			if (Utils.Possibilitat(6)){i.add(new ItemStack(Material.MAGMA_CREAM, Utils.NombreEntre(1,  2)));}
			if (Utils.Possibilitat(36)){i.add(new ItemStack(Material.GOLD_INGOT, Utils.NombreEntre(4,  16)));}
			break;
		case BONUS:
			if (Utils.Possibilitat(1)){i.add(SpecialItemsUtils.getRandomSpecialItem(2));}
			if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.DIAMOND, Utils.NombreEntre(1,  3)));}
			if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.IRON_INGOT, Utils.NombreEntre(1,  3)));}
			if (Utils.Possibilitat(20)){i.add(new ItemStack(Material.ENDER_PEARL));}
			if (Utils.Possibilitat(100)){i.add(new ItemStack(Material.LEVER));}
			if (Utils.Possibilitat(26)){i.add(new ItemStack(Material.GOLD_NUGGET));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.DIAMOND_HELMET));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.GOLD_CHESTPLATE));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.IRON_CHESTPLATE));}
			if (Utils.Possibilitat(38)){i.add(new ItemStack(Material.GOLDEN_APPLE));}
			if (Utils.Possibilitat(65)){i.add(new ItemStack(Material.POTATO, Utils.NombreEntre(8,  32)));}
			if (Utils.Possibilitat(36)){i.add(new ItemStack(Material.COAL, Utils.NombreEntre(8,  10)));}
			if (Utils.Possibilitat(8)){i.add(new ItemStack(Material.IRON_AXE));}
			if (Utils.Possibilitat(2)){i.add(new ItemStack(Material.DIAMOND_AXE));}
			if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.SPONGE));}
			if (Utils.Possibilitat(22)){i.add(new ItemStack(Material.GOLDEN_APPLE));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.BAKED_POTATO, Utils.NombreEntre(1,  8)));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.CHAINMAIL_BOOTS));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.GOLDEN_APPLE, Utils.NombreEntre(1,  8)));}
			break;
		case ICY:
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.PACKED_ICE, Utils.NombreEntre(1,  6)));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.BUCKET));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.COAL));}
			if (Utils.Possibilitat(25)){i.add(new ItemStack(Material.SNOW_BALL, Utils.NombreEntre(1,  16)));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.SNOW_BLOCK, Utils.NombreEntre(1,  12)));}
			if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.DIAMOND, Utils.NombreEntre(1,  2)));}
			break;
		case MAGIC:
			if (Utils.Possibilitat(2)){i.add(SpecialItemsUtils.getRandomSpecialItem(2));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.GLASS, Utils.NombreEntre(1,  4)));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.GHAST_TEAR, Utils.NombreEntre(1,  2)));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.CAULDRON_ITEM));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.SPIDER_EYE, Utils.NombreEntre(1,  2)));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.GLOWSTONE_DUST, Utils.NombreEntre(1,  9)));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.BLAZE_POWDER, Utils.NombreEntre(1,  2)));}
			if (Utils.Possibilitat(2)){i.add(new ItemStack(Material.GOLDEN_APPLE, Utils.NombreEntre(1,  3)));}
			break;
		case SILVERFISH:
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.STONE_SWORD));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.IRON_BOOTS));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.EGG, Utils.NombreEntre(1,  4)));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.WOOD_SWORD));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.GLASS, Utils.NombreEntre(1,  4)));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.MUSHROOM_SOUP, Utils.NombreEntre(1,  4)));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.COBBLESTONE, Utils.NombreEntre(1,  15)));}
			if (Utils.Possibilitat(24)){i.add(new ItemStack(Material.BOAT));}
			break;
		case SKELETON:
			if (Utils.Possibilitat(100)){i.add(new ItemStack(Material.ARROW, Utils.NombreEntre(1,  6)));}
			if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.ARROW, Utils.NombreEntre(2,  8)));}
			if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.STRING, Utils.NombreEntre(1,  15)));}
			if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.BONE, Utils.NombreEntre(1,  15)));}
			if (Utils.Possibilitat(40)){i.add(new ItemStack(Material.BREAD, Utils.NombreEntre(1,  5)));}
			if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.WHEAT, Utils.NombreEntre(1,  5)));}
			if (Utils.Possibilitat(20)){i.add(new ItemStack(Material.BOW));}
			if (Utils.Possibilitat(38)){i.add(BowRecipeGenerator.getBow(BowUtils.BowType.values()[Utils.NombreEntre(0, BowUtils.BowType.values().length - 1)]));}
			break;
		case SPIDER:
			if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.WEB, Utils.NombreEntre(1,  9)));}
			if (Utils.Possibilitat(62)){i.add(new ItemStack(Material.WEB, Utils.NombreEntre(1,  4)));}
			if (Utils.Possibilitat(100)){i.add(new ItemStack(Material.STRING, Utils.NombreEntre(1,  26)));}
			if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.SPIDER_EYE, Utils.NombreEntre(1,  4)));}
			if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.FERMENTED_SPIDER_EYE, Utils.NombreEntre(1,  4)));}

			break;
		case ZOMBIE:
			if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.ROTTEN_FLESH, Utils.NombreEntre(1,  25)));}
			if (Utils.Possibilitat(60)){i.add(new ItemStack(Material.ROTTEN_FLESH, Utils.NombreEntre(1,  25)));}
			if (Utils.Possibilitat(80)){i.add(new ItemStack(Material.ROTTEN_FLESH, Utils.NombreEntre(1,  15)));}
			if (Utils.Possibilitat(80)){i.add(new ItemStack(Material.ROTTEN_FLESH, Utils.NombreEntre(1,  18)));}
			if (Utils.Possibilitat(70)){i.add(new ItemStack(Material.CARROT, Utils.NombreEntre(1,  3)));}
			if (Utils.Possibilitat(48)){i.add(new ItemStack(Material.POISONOUS_POTATO, Utils.NombreEntre(1,  30)));}
			break;
		default:
			break;

		}
		//A tots
		if (Utils.Possibilitat(2)){i.add(new ItemStack(Material.IRON_AXE));}
		if (Utils.Possibilitat(2)){i.add(new ItemStack(Material.IRON_BOOTS));}
		if (Utils.Possibilitat(2)){i.add(new ItemStack(Material.IRON_SWORD));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.DIAMOND_ORE));}
		if (Utils.Possibilitat(2)){i.add(SpecialItemsUtils.getRandomSpecialItem(2));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.POTATO));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.BREAD, Utils.NombreEntre(1,  3)));}
		if (Utils.Possibilitat(5)){i.add(new ItemStack(Material.DIAMOND, Utils.NombreEntre(1,  2)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.COAL_BLOCK));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.COAL_ORE, Utils.NombreEntre(1,  45)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.IRON_ORE, Utils.NombreEntre(1,  10)));}
		if (Utils.Possibilitat(6)){i.add(new ItemStack(Material.EMERALD, Utils.NombreEntre(1,  8)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.ANVIL));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.CHEST, Utils.NombreEntre(1,  2)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.BED, Utils.NombreEntre(1,  3)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.REDSTONE_ORE, Utils.NombreEntre(1,  12)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.NAME_TAG, Utils.NombreEntre(1,  4)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.APPLE, Utils.NombreEntre(1,  4)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.TNT, Utils.NombreEntre(1,  2)));}
		if (Utils.Possibilitat(2)){i.add(new ItemStack(Material.EXP_BOTTLE, Utils.NombreEntre(1,  10)));}
		if (Utils.Possibilitat(2)){i.add(new ItemStack(Material.SLIME_BALL, Utils.NombreEntre(1,  10)));}
		if (Utils.Possibilitat(1)){i.add(new ItemStack(Material.DIAMOND_AXE));}
		if (Utils.Possibilitat(10)){i.add(new ItemStack(Material.IRON_INGOT, Utils.NombreEntre(1,  4)));}
		return i;
	}
	public LevelType getRandomLevelType(){
		return LevelType.values()[Utils.NombreEntre(0, LevelType.values().length - 1)];
	}
	public LevelType getLevelType(){
		LevelType t = getRandomLevelType();
		if(t == LevelType.BONUS){if (Utils.Possibilitat(30)) {t = getRandomLevelType();}}
		if(t == LevelType.MAGIC){if (Utils.Possibilitat(20)) {t = getRandomLevelType();}}
		return t;
	}
}
