package com.biel.FastSurvival.Dimensions.Moon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Stairs;
import org.bukkit.material.Vine;
import org.bukkit.util.Vector;

import com.bergerkiller.bukkit.common.utils.MaterialUtil;
import com.biel.FastSurvival.MobIntelligence.KingSkeletonBossUtils;
import com.biel.FastSurvival.Utils.BUtils;
import com.biel.FastSurvival.Utils.Cuboid;
import com.biel.FastSurvival.Utils.Utils;
import com.biel.FastSurvival.Utils.Cuboid.CuboidDirection;

public class MoonMagicTreePopulator extends BlockPopulator {
	private static final int TREE_CHANCE = 1; // Out of 100 (45)
	@SuppressWarnings("deprecation")
	@Override
	public void populate(World world, Random random, Chunk source) {
		if (!(random.nextInt(495) <= TREE_CHANCE)) {return;}
		int centerX = (source.getX() << 4) + random.nextInt(16);
		int centerZ = (source.getZ() << 4) + random.nextInt(16);
		int centerY = world.getHighestBlockYAt(centerX, centerZ);
		Location center = new Location(world, centerX, centerY, centerZ);
		Block bCenter = center.getBlock().getRelative(BlockFace.DOWN);
		//No sobreposar
		if (bCenter.getType() != Material.STAINED_CLAY){Bukkit.broadcastMessage("Material: " + bCenter.getType().name());return;}
		int radius = 2; //Utils.NombreEntre(2, 5);
		Cuboid sc = generateCenterCuboid(center, 41, radius);
		for (Block b: sc.getBlocks()){
			b.setType(Material.LOG);
			b.setData((byte) 2, false);
			//			if (Utils.Possibilitat(1)){
			//				b.setType(Material.WOOD);
			//			}
		}
		GenerateLowerRoots(center.clone().add(0, Utils.NombreEntre(3, 4), 0));
		generateStair(center.clone(), 41, radius + 1, 1, Utils.NombreEntre(1, 3));
		int rad = Utils.NombreEntre(14, 18);
		GenerateTopTree(center.add(0, 40, 0), 8, rad);
		SpawnUltraZombie(center.clone().add(0, 5, 0));
		//generateStair(center.clone(), 40, radius + 6, 1, Utils.NombreEntre(1, 3));
		center.add(0, -6, 0);
		GenerateRoots(center, 0.62, rad + 0.8);

	}
	public Cuboid generateCenterCuboid(Location center, int height, int width){
		Cuboid c = new Cuboid(center.clone().add(-1 * width, 0, -1 * width),center.clone().add(width, height, width));
		c.expand(CuboidDirection.Up, height - 1);
		return c;
	}
	@SuppressWarnings("deprecation")
	public void generateStair(Location center, int MaxHeight, int radius, int amp, int UpFact){
		int height = 0;
		int upFactRemaining = UpFact; 
		int forwardCount = 0; //Max 4
		Location l = center.clone().add(-1 * (radius), 0, radius);
		l.setDirection(Utils.CrearVector(l, l.clone().add(0, 0, -1)));
		while (height <= MaxHeight){
			Location elevLoc = l.clone().add(0, height, 0);
			//elevLoc.getBlock().setType(Material.DIAMOND_BLOCK);
			//Laterals
			Block last = null;
			Vector vOut = new Vector(l.getDirection().getZ(), l.getDirection().getY(), -1 * l.getDirection().getX());
			int RemAmp = amp + 1;
			while (RemAmp >= 0){
				Location lBlock = elevLoc.clone().add(vOut.clone().multiply(RemAmp));
				Block blk = lBlock.getBlock();
				if (RemAmp != 2){
					if (!Utils.Possibilitat(1)){
						blk.setType(Material.LOG);
						blk.setData((byte) 2, false);
					}
				}else{
					if (Utils.Possibilitat(100)){
						blk.setType(Material.VINE);
						Vine v = new Vine(blk.getData());
						
//						ArrayList<BlockFace> adjf = Utils.getAdjacentFaces(blk, Utils.getFacesNSEW());
						BlockFace f = Utils.getFacesNSEW().get(Utils.NombreEntre(0, 3));
//						int size = adjf.size();
						
//						if (size > 0){
//							Bukkit.broadcastMessage(Integer.toString(size));
//							
//						
////							for(BlockFace fa : adjf){
////								Block b = blk.getRelative(fa);
////								b.setType(Material.DIAMOND_BLOCK);
////							}
//						}else{
//							Bukkit.broadcastMessage("Defaulted");
//							//blk.setType(Material.GOLD_BLOCK);
//						}
						//v.putOnFace(f);  --IMP
						blk.setData(v.getData());
					}
					//					if (Utils.Possibilitat(10)){
					//						Block blk = lBlock.getBlock();
					//						blk.setType(Material.WALL_SIGN);
					//						Sign s = (Sign) blk.getState();
					//						s.setLine(1, "---");
					//						BlockFace f = BlockFace.SOUTH;
					//						if (last != null){
					//							f = Utils.getBlocksSharedFace(last, blk);
					//						}
					//						
					//						blk.setData();
					//					}

				}
				last = lBlock.getBlock();
				RemAmp--;
			}
			//----
			if (forwardCount >= radius * 2){
				forwardCount = 0;
				l.setDirection(new Vector(-1 * l.getDirection().getZ(), l.getDirection().getY(), l.getDirection().getX()));
			}else{
				l = l.clone().add(l.getDirection());
				forwardCount++;
				if (upFactRemaining <= 0){
					upFactRemaining = UpFact;
					height++;
				}else{
					upFactRemaining--;
				}
			}


		}
	}
	public void GenerateTopTree(Location center, int height, int radius){
		ArrayList<Block> blks = new ArrayList<Block>();
		blks.addAll(Utils.getCylBlocks(center.clone().add(0, height, 0), radius -1, 1, true));

		blks.addAll(Utils.getOuterCylBlocks(center.clone().add(0, 2, 0), radius, height, false));
		blks.addAll(Utils.getCylBlocks(center.clone().add(0, -1, 0), radius -1, 3, true));
		for(Block b : blks){
			b.setType(Material.LEAVES);
		}
		for(Block b : Utils.getOuterCylBlocks(center.clone().add(0, 2, 0), radius + 1, height, false)){
			//if (Utils.Possibilitat(12)){b.setType(Material.GOLD_BLOCK);}
		}
		//Lamps
		Location lampCenterLoc = center.clone().add(0, 1, 0);
		List<Block> iblks = BUtils.locListToBlock(Utils.getLocationsCircle(lampCenterLoc, radius - 3.8, 20));
		iblks.add(lampCenterLoc.getBlock());
		for(Block b : iblks){
			b.getRelative(BlockFace.DOWN).setType(Material.REDSTONE_BLOCK);
			b.setType(Material.REDSTONE_LAMP_ON);
			b.getRelative(BlockFace.UP, 7).setType(Material.AIR);
		}
	}
	public void GenerateUpperRoots(Location center){
		GenerateRoots(center, 0.55, 30.5);
	}
	public void GenerateLowerRoots(Location center){
		GenerateRoots(center, -0.98, 32.5);
	}
	public void GenerateRoots(Location center, Double upfact, Double maxDist){
		ArrayList<Location> dls = Utils.getLocationsCircle(center, 4.2, 20);
		for (Location b : dls){
			//			Location dl = d;
			if (Utils.Possibilitat(35)){
				GenerateRootStep(center, center, Utils.CrearVector(center, b), upfact, maxDist);
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void GenerateRootStep(Location center, Location start, Vector dir, Double upfact, Double maxDist){
		Location l = start.clone();
		while(center.distance(l) < maxDist){
			//Check
			if (l.getBlockY() - center.getY() > 4){return;}
			//Set
			Block blk = l.getBlock();
			blk.setType(Material.LOG);
			blk.setData((byte) 2, false);
			//Advance or lateral
			Vector ndir = dir.clone().normalize();
			Vector random = Vector.getRandom().subtract(Vector.getRandom());
			Vector nrand = random.normalize();
			Vector n = ndir.multiply(2).add(nrand).add(new Vector(0, upfact, 0));
			n.normalize();
			l.add(n);
			if (Utils.Possibilitat(5)){
				Vector bdir;
				if (Utils.Possibilitat(5)){
					bdir = new Vector(-1 * n.getZ(), 0, n.getX());
				}else{
					bdir = new Vector(n.getZ(), 0, -1 * n.getX());
				}
				bdir.add(dir);
				bdir.normalize();
				GenerateRootStep(center, l, bdir, upfact, maxDist);
			}
		}
	}
	public void SpawnUltraZombie(Location l){
		KingSkeletonBossUtils.spawnBoss(l);
	}


}
