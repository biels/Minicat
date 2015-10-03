package com.biel.FastSurvival.Dimensions.Moon;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class ClayColorPopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random rand, Chunk chunk){
		int chunkX = chunk.getX();
		int chunkZ = chunk.getZ();
		// This will give us the highest block that we would need to check, in this particular setup
		final int MAX_Y = 1 + (60 + 3);

		for(int x = 0; x < 16; x++){
			for(int z = 0; z < 16; z++){
				for(int y = 0; y < MAX_Y; y++){
					int blockX = (chunkX * 16) + x;
					int blockZ = (chunkZ * 16) + z;

					Block block = world.getBlockAt(blockX, y, blockZ);
					if(block.getType() == Material.STAINED_CLAY){
						block.setData((byte) ((y < 16) ? 9 : 0));
					}
				}
			}
		}
	}
}