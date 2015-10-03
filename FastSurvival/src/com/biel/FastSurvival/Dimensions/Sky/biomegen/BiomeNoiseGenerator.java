package com.biel.FastSurvival.Dimensions.Sky.biomegen;

import org.bukkit.World;
import org.bukkit.util.noise.PerlinOctaveGenerator;

public abstract class BiomeNoiseGenerator {
	
	// The perlin octave generator works the same way as
	// a simplex octave generator (just faster on lower 
	// dimensions i.e.. 2 and 3).
	protected PerlinOctaveGenerator generator;
	double magnitude = 64.0;
	
	// We use a state machine in order to handle multiple worlds (thanks @Icyene)
	public void setWorld(World world) {
		this.generator = new PerlinOctaveGenerator(world, 8);
	}

	//These methods can be overridden later on to deal with more specific biome generation
	public double get2dNoise(double x, double z) {
		return generator.noise(x, z, 0.5, 0.5) * magnitude;
	}
	public double get3dNoise(double x, double y, double z) {
		return generator.noise(x, y, z, 0.5, 0.5);
	}
	
}
