package com.biel.FastSurvival.Dimensions.Sky.biomegen;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CloudPopulator extends BlockPopulator {

	@SuppressWarnings("deprecation")
	@Override
    public void populate(World world, Random random, Chunk chunk) {
        if (random.nextInt(16) >= 1) { //18
            return;
        }
        
//      Bukkit.broadcastMessage("Populated");
        int sizeX = 16;
        int sizeY = 12;
        int sizeZ = 16;
        int spawnerChance = 400; // Spawners are generated at a chance of 12/400
        Material matWalls = Material.NETHERRACK;
        Material matFloor = Material.SOUL_SAND;
        Material matDecor = Material.WEB;

        //Editing is over
        int lengthH = sizeX / 2;
        int heightH = sizeY / 2;
        int widthH = sizeZ / 2;
        int centerX = chunk.getX() * 16 + 8;
        int centerY = world.getHighestBlockYAt(chunk.getX() * 16 + 8, chunk.getZ() * 16 + 8) / 2;
        if(centerY<5)
        	return;
        int centerZ = chunk.getZ() * 16 + 8;
        int minX = centerX - lengthH;
        int maxX = centerX + lengthH;
        int minY = centerY - heightH;
        int maxY = centerY + heightH;
        int minZ = centerZ - widthH;
        int maxZ = centerZ + widthH;
        Boolean chained = random.nextBoolean();
        int MaxLength = 3 + random.nextInt(4);
       // Bukkit.broadcastMessage(Integer.toString(centerY));
        //
        int offX = random.nextInt(3);
        Location l1 = new Location(world, centerX  + offX, 60, centerZ);
        int ycomp = world.getHighestBlockYAt(l1);
        Biome b = l1.getBlock().getBiome();
        Boolean snow = false;
		if (!(b == Biome.PLAINS || b == Biome.FOREST || b == Biome.JUNGLE || b == Biome.SAVANNA || b == Biome.DESERT || b == Biome.BEACH || b == Biome.TAIGA) || b == Biome.ICE_PLAINS || b == Biome.SUNFLOWER_PLAINS){
        	return;
        }
		if (b == Biome.COLD_TAIGA || b == Biome.ICE_PLAINS){
			snow = true;
		}
		Location lp = new Location(world, centerX  + offX, ycomp, centerZ);
		Material type = lp.getBlock().getRelative(BlockFace.DOWN).getType();
		if (type.isTransparent() || !type.isSolid() || type == Material.LEAVES || type == Material.LEAVES_2){
			return;
		}
		Location lpf = new Location(world, centerX  + offX, ycomp, centerZ + MaxLength);
		Material typef = lpf.getBlock().getRelative(BlockFace.DOWN).getType();
		if (typef.isTransparent() || !typef.isSolid()){
			return;
		}
        for (int i = 0; i <= MaxLength; i++) {
        	chained = !chained;
        	Location l = new Location(world, centerX  + offX, ycomp, centerZ + i);
        	Block blk = l.getBlock();
        	ArrayList<BlockFace> faces = new ArrayList<BlockFace>();
        	faces.add(BlockFace.UP);
        	faces.add(BlockFace.EAST);
        	faces.add(BlockFace.WEST);
        	faces.add(BlockFace.SELF);
        	for (BlockFace f : faces){
        		Block relative = blk.getRelative(f);
        		relative.setData((byte) 8);
				relative.setType(Material.LOG);
        		//blk.getRelative(f, 2).setType(Material.LOG);
				if (chained){
					for (BlockFace f2 : faces){
						Block relative2 = relative.getRelative(f2);
						if (relative2.getType() == Material.AIR || relative2.getType() == Material.GRASS){
							relative2.setType(Material.RAILS);
						}
						
					}
	        	}else{
	        		for (BlockFace f2 : faces){
						Block relative2 = relative.getRelative(f2);
						if (relative2.getType() == Material.AIR){
							if (random.nextInt(34) <= 1) {
			        			relative2.setType(Material.TORCH);
			        		}else{
			        			if(snow){
			        				relative2.setType(Material.SNOW);
			        			}
			        					
			        		}
//							if (random.nextInt(70) <= 1) {
//								Slime sl = (Slime) world.spawnEntity(relative2.getLocation(), EntityType.SLIME);
//								sl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
//								sl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 8));
//								sl.setSize(2);
//							}
						}
						
					}
	        		
	        	}
        	}
        	
        }
               
        

      
    }

    private Material pickDecor(Random random, Material decor, Material wall) {
        return (random.nextInt(5) == 0) ? decor : wall;
    }

}
