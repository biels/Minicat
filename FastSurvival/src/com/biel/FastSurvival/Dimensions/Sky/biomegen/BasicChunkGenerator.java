package com.biel.FastSurvival.Dimensions.Sky.biomegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import com.biel.FastSurvival.Utils.Utils;

public class BasicChunkGenerator extends ChunkGenerator {

	void setBlock(int x, int y, int z, short[][] chunk, Material material) {
		if (y < 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0) { 
			if (chunk[y >> 4] == null)
				chunk[y >> 4] = new short[16 * 16 * 16];
			chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (short) material.getId();
		}
	}

	short getBlock(int x, int y, int z, short[][] chunk) {
		if (y < 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0) { 
			if (chunk[y >> 4] == null)
				return 0;
			return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
		}
		else return 0;
	}

	@Override
	public short[][] generateExtBlockSections(World world, Random rand, int chunkX,
			int chunkZ, BiomeGrid biomeGrid) {
		Biomes.setWorld(world);
		
		short[][] chunk = new short[world.getMaxHeight() / 16][];
		BiomeGenerator biomeGenerator = new BiomeGenerator(world);
		
		for (int x=0; x<16; x++) {
			
			for (int z=0; z<16; z++) {
				if (z % 4 != 0){continue;}
				int realX = x + chunkX * 16;
	 			int realZ = z + chunkZ * 16;
				
				//We get the 3 closest biome's to the temperature and rainfall at this block
				HashMap<Biomes, Double> biomes = biomeGenerator.getBiomes(realX, realZ);
				//And tell bukkit (who tells the client) what the biggest biome here is
				biomeGrid.setBiome(x, z, getDominantBiome(biomes));
				
				// for illustrative purposes, we colour the biomes differently
				Material material = getBiomeMaterial(biomeGrid.getBiome(x, z)); 

				// To make it more maintainable, we've abstracted finding height
				// and density values
				int bottomHeight = getHeight(realX, realZ, biomes);

				// This has been lowered to 10 to avoid massive performance issues.
				// We take (10 vertical blocks * 3 (closest) biomes * 16 columns)
				// noise values per chunk! In the next tutorial, I will show you how
				// to use interpolation to "guess" the values between, so that you
				// can avoid expensive calls to the noise generator
				int maxHeight = bottomHeight + 10;
				double threshold = 0.3;
				if (true){
					for (int y=0; y<maxHeight; y++) {
						if (true){
							if (y > bottomHeight) {
								double density = getDensity(realX, y, realZ, biomes);

								if (density > threshold) setBlock(x,y,z,chunk,material);



							} else {
								if(Utils.Possibilitat(15)){
									setBlock(x,y,z,chunk,Material.ICE);
								}
								
							}
						}
					}

					setBlock(x,bottomHeight,z,chunk,getBiomeMaterial(biomeGrid.getBiome(x, z)));
					setBlock(x,bottomHeight - 1,z,chunk,material);
					setBlock(x,bottomHeight - 2,z,chunk,material);

					for (int y=bottomHeight + 1; y>bottomHeight && y < maxHeight; y++ ) {
						int thisblock = getBlock(x, y, z, chunk);
						int blockabove = getBlock(x, y+1, z, chunk);

						if(thisblock != Material.AIR.getId() && blockabove == Material.AIR.getId()) {
							setBlock(x, y, z, chunk, material);
							if(getBlock(x, y-1, z, chunk) != Material.AIR.getId())
								setBlock(x, y-1, z, chunk, material);
							if(getBlock(x, y-2, z, chunk) != Material.AIR.getId())
								setBlock(x, y-2, z, chunk, material);
						}
					}
				}
			}
		}
		return chunk;
	}

	//This would normaly be in an enum, but it's only so you can see biome lines
	private Material getBiomeMaterial(Biome biome) {
		switch (biome) {
		case DESERT: return Material.SANDSTONE;
		case OCEAN: return Material.ICE;
		case PLAINS: return Material.DIRT;
		case SWAMPLAND: return Material.MYCEL;
		case EXTREME_HILLS: return Material.STONE;
		default: return Material.SNOW_BLOCK;
		}
	}

	//We get the closest biome to send to the client (using the biomegrid)
	private Biome getDominantBiome(HashMap<Biomes, Double> biomes) {
		double maxNoiz = 0.0;
		Biomes maxBiome = null;
		
		for (Biomes biome : biomes.keySet()) {
			if (biomes.get(biome) >= maxNoiz) {
				maxNoiz = biomes.get(biome);
				maxBiome = biome;
			}
		}
		return maxBiome.biome;
	}

	private double getDensity(int x, int y, int z, HashMap<Biomes, Double> biomes) {
		double noise = 0.0;
		for (Biomes biome : biomes.keySet()) {
			double weight = biomes.get(biome);
			noise += biome.generator.get3dNoise(x, y, z)*weight;
		}
		return noise;
	}

	private int getHeight(int x, int z, HashMap<Biomes, Double> biomes) {
		double noise = 0.0;
		for (Biomes biome : biomes.keySet()) {
			double weight = biomes.get(biome);
			noise += biome.generator.get2dNoise(x, z)*weight;
		}
		return (int) (noise + 64);
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		ArrayList<BlockPopulator> pops = new ArrayList<BlockPopulator>();
		//Add Block populators here
		return pops;
	}
}
