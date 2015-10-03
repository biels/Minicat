package com.biel.FastSurvival.Dimensions.Sky.biomegen;

import org.bukkit.World;

public class SwampNoiseGenerator extends BiomeNoiseGenerator {

	@Override
	public void setWorld(World world) {
		super.setWorld(world);
	}
	
	@Override
	public double get2dNoise(double x, double z) {
		return 1;
	}
	
	@Override
	public double get3dNoise(double x, double y, double z) {
		return 0;
	}

}
