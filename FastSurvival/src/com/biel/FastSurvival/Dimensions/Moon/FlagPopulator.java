package com.biel.FastSurvival.Dimensions.Moon;

import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;

import com.biel.FastSurvival.Utils.Utils;

public class FlagPopulator extends BlockPopulator {
    private static final int FLAG_CHANCE = 8; // Out of 200
    private static final int FLAG_HEIGHT = 3;

    public void populate(World world, Random random, Chunk source) {
        if (random.nextInt(200) <= FLAG_CHANCE) {
            int centerX = (source.getX() << 4) + random.nextInt(16);
            int centerZ = (source.getZ() << 4) + random.nextInt(16);
            int centerY = world.getHighestBlockYAt(centerX, centerZ);
            BlockFace direction = null;
            Block top = null;
            int dir = random.nextInt(4);

            if (dir == 0) {
                direction = BlockFace.NORTH;
            } else if (dir == 1) {
                direction = BlockFace.EAST;
            } else if (dir == 2) {
                direction = BlockFace.SOUTH;
            } else {
                direction = BlockFace.WEST;
            }

            for (int y = centerY; y < centerY + FLAG_HEIGHT; y++) {
                top = world.getBlockAt(centerX, y, centerZ);
                top.setType(Material.FENCE);
            }

            Block signBlock = top.getRelative(direction);

            if (Utils.Possibilitat(30)){
            	if (Utils.Possibilitat(20)){
            		signBlock.setType(Material.WALL_SIGN);
            		BlockState state = signBlock.getState();
            		if (state instanceof Sign) {
            			Sign sign = (Sign)state;
            			org.bukkit.material.Sign data = (org.bukkit.material.Sign)state.getData();

            			data.setFacingDirection(direction);
            			String name = "Tu";
            			List<Player> players = world.getPlayers();
            			if (players.size() != 0){
            				name = players.get(Utils.NombreEntre(0, players.size()-1)).getName();
            			}
            			sign.setLine(0, name);
            			sign.setLine(1, "has arribat");
            			sign.setLine(2, "a la");
            			sign.setLine(3, "lluna :D");
            			sign.update(true);
            		}
            	}else{
            		signBlock.setType(Material.REDSTONE_LAMP_OFF);
            	}

            }else{
            	signBlock.setType(Material.MOB_SPAWNER);
            	if(signBlock.getState() instanceof CreatureSpawner){
            		CreatureSpawner spawner = (CreatureSpawner) signBlock.getState();
                	//spawner.setDelay(20 * 3);

                	spawner.setSpawnedType(EntityType.ZOMBIE);;
                	spawner.update();
            	}else{
            		signBlock.setType(Material.REDSTONE_LAMP_OFF);
            	}
            	
            }
        }
    }
}