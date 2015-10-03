package com.biel.FastSurvival.Dimensions.Sky.biomegen;

import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.util.noise.PerlinOctaveGenerator;

public class BiomeGenerator {
	
	// Note that simplex noise and perlin noise are very
	// similar (and the generators act the same). Perlin
	// noise is just faster
	//
	// To get rid of the "bands" of biomes, we should use a
	// different type of noise, but to keep the tutorial
	// as simple as possible, we can use this for now. We
	// should use Voronoi (cell) noise for biomes. In the next
	// tutorial, I will cover voronoi noise.
	private final PerlinOctaveGenerator temperatureGen, rainfallGen;
	
	public BiomeGenerator(World world) {
		// I used a scale of 1/300 instead of something like 1/600 or 1/1000
		// to make the biome edges easier to see
		temperatureGen = new PerlinOctaveGenerator(world.getSeed(), 16);
		temperatureGen.setScale(1.0/300.0);
		
		rainfallGen = new PerlinOctaveGenerator(world.getSeed() + 1, 15);
		rainfallGen.setScale(1.0/300.0);
	}

	public HashMap<Biomes, Double> getBiomes(int realX, int realZ) {
		return Biomes.getBiomes(Math.abs(temperatureGen.noise(realX, realZ, 0.5, 0.5)*100.0), Math.abs(rainfallGen.noise(realX, realZ, 0.5, 0.5)*100.0));
	}

}
