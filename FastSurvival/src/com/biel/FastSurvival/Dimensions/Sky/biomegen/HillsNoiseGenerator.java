package com.biel.FastSurvival.Dimensions.Sky.biomegen;

import org.bukkit.World;

public class HillsNoiseGenerator extends BiomeNoiseGenerator {

	@Override
	public void setWorld(World world) {
		super.setWorld(world);
		this.generator.setScale(1.0/64.0);
		this.generator.setYScale(1.0/72.0);
		this.magnitude = 14.0;
	}

}
