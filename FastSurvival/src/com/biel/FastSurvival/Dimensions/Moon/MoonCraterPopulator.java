package com.biel.FastSurvival.Dimensions.Moon;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import com.biel.FastSurvival.Utils.Utils;

public class MoonCraterPopulator extends BlockPopulator {
    private static final int CRATER_CHANCE = 24; // Out of 100 (45)
    private static final int MIN_CRATER_SIZE = 3;
    private static final int SMALL_CRATER_SIZE = 8;
    private static final int BIG_CRATER_SIZE = 16;
    private static final int BIG_CRATER_CHANCE = 10; // Out of 100

    public void populate(World world, Random random, Chunk source) {
        if (random.nextInt(100) <= CRATER_CHANCE) {
            int centerX = (source.getX() << 4) + random.nextInt(16);
            int centerZ = (source.getZ() << 4) + random.nextInt(16);
            int centerY = world.getHighestBlockYAt(centerX, centerZ);
            Vector center = new BlockVector(centerX, centerY, centerZ);
            Location lcenter = new Location(world, centerX, centerY, centerZ);
            Block bCenter = center.toLocation(world).getBlock().getRelative(BlockFace.DOWN);
    		//No sobreposar
    		if (bCenter.getType() != Material.STAINED_CLAY){return;}

            //--
            int radius = 0;

            if (random.nextInt(100) <= BIG_CRATER_CHANCE) {
                radius = random.nextInt(BIG_CRATER_SIZE - MIN_CRATER_SIZE + 1) + MIN_CRATER_SIZE;
            } else {
                radius = random.nextInt(SMALL_CRATER_SIZE - MIN_CRATER_SIZE + 1) + MIN_CRATER_SIZE;
            }

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Vector position = center.clone().add(new Vector(x, y, z));

                        if (center.distance(position) <= radius + 0.5) {
                            world.getBlockAt(position.toLocation(world)).setType(Material.AIR);
                        }
                    }
                }
            }
            Material m = Material.STONE;
            int c = 0;
            if (Utils.Possibilitat(65)){m = Material.DIAMOND_ORE; c = 97;}
            if (Utils.Possibilitat(35)){m = Material.IRON_ORE; c = 33;}
            if (Utils.Possibilitat(25)){m = Material.GOLD_ORE; c = 38;}
            if (Utils.Possibilitat(25)){m = Material.REDSTONE_ORE; c = 55;}
            if (Utils.Possibilitat(14)){m = Material.DIAMOND_ORE; c = 76;}
            if (Utils.Possibilitat(25)){m = Material.EMERALD_ORE; c = 68;}
            if (Utils.Possibilitat(5)){m = Material.COAL_ORE; c = 45;}
            if (Utils.Possibilitat(3)){m = Material.GLASS; c = 75;}
            if (Utils.Possibilitat(3)){m = Material.COBBLESTONE; c = 75;}
            if (Utils.Possibilitat(3)){m = Material.NETHERRACK; c = 70;}
            if (Utils.Possibilitat(4)){m = Material.LAVA; c = 60;}
            if (Utils.Possibilitat(3)){m = Material.SOUL_SAND; c = 0;}
            if (Utils.Possibilitat(4)){m = Material.SANDSTONE; c = 0;}
            if (Utils.Possibilitat(1)){m = Material.GLOWSTONE; c = 0;}
            if (Utils.Possibilitat(2)){m = Material.WATER; c = 55;}
            if (Utils.Possibilitat(4)){m = Material.PACKED_ICE; c = 0;}
            if (Utils.Possibilitat(5)){m = Material.OBSIDIAN; c = 0;}
            	
            
            center.setY(world.getHighestBlockYAt(centerX, centerZ) + radius / 10);
            radius = radius/3; 
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Vector position = center.clone().add(new Vector(x, y, z));
                        Material mf = m;
                        if (Utils.Possibilitat(c)){mf = Material.STONE;}
                        if (center.distance(position) <= radius + 0.5) {
                        	world.getBlockAt(position.toLocation(world)).setType(mf);
                        }
                    }
                }
            }
        }
    }
}