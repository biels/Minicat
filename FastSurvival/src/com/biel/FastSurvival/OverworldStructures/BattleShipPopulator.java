package com.biel.FastSurvival.OverworldStructures;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.biel.FastSurvival.Utils.Utils;

public class BattleShipPopulator extends BlockPopulator{
	private static final int BALLOON_CHANCE = 1; // Out of 100 (45)
	@Override
	public void populate(World world, Random random, Chunk source) {
		if (!(random.nextInt(100) <= BALLOON_CHANCE)) {return;}
		int centerX = (source.getX() << 4) + random.nextInt(16);
		int centerZ = (source.getZ() << 4) + random.nextInt(16);
		int centerY = world.getHighestBlockYAt(centerX, centerZ);
		Location center = new Location(world, centerX, centerY + 20 + Utils.NombreEntre(1, 20), centerZ);
		
	}
}
