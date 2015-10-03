package com.biel.FastSurvival.Dimensions.Moon;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class MazePopulator extends BlockPopulator {
	private static final int GRAVEYARD_CHANCE = 8; // Out of 100 (45)
	@Override
	public void populate(World world, Random random, Chunk source) {
		if (!(random.nextInt(100) <= GRAVEYARD_CHANCE)) {return;}
		int centerX = (source.getX() << 4) + random.nextInt(16);
		int centerZ = (source.getZ() << 4) + random.nextInt(16);
		int centerY = world.getHighestBlockYAt(centerX, centerZ);
		Location center = new Location(world, centerX, centerY, centerZ);
	}
}
