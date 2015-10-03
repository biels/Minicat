package com.biel.FastSurvival.BuilderWand;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import com.biel.FastSurvival.Utils.Utils;

public class BuilderWandUtils {
	public static void addWandRecipe(){
		recipeWithGold();
		recipeWithIce();
		recipeWithDiamond();
	}
	public static void recipeWithGold() {
		ShapelessRecipe r = new ShapelessRecipe(getWandItem());
		r.addIngredient(Material.GOLD_INGOT);
		r.addIngredient(Material.REDSTONE_TORCH_ON);
		Bukkit.getServer().addRecipe(r);
	}
	public static void recipeWithIce() {
		ShapelessRecipe r = new ShapelessRecipe(getWandItem());
		r.addIngredient(Material.ICE);
		r.addIngredient(Material.REDSTONE_TORCH_ON);
		Bukkit.getServer().addRecipe(r);
	}
	public static void recipeWithDiamond() {
		ShapelessRecipe r = new ShapelessRecipe(getWandItem());
		r.addIngredient(Material.DIAMOND);
		r.addIngredient(Material.REDSTONE_TORCH_ON);
		Bukkit.getServer().addRecipe(r);
	}
	public static ItemStack getWandItem(){
		ItemStack i = Utils.setItemName(new ItemStack(Material.REDSTONE_TORCH_ON), ChatColor.YELLOW + "Builder's wand");
		i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
		return i;
	}
	public static Boolean isWandItem(ItemStack i){
		if (i.getType() != Material.REDSTONE_TORCH_ON){return false;}
		if (i.getEnchantments().size() >= 1){
			return true;
		}
		return false;
	}
	public static void onWandUse(Block cblk, BlockFace face, Player p){
		//		for(Block b : getSelectionBlocks(cblk, face)){
		//			b.setType(Material.GOLD_BLOCK);
		//		}
		if(!cblk.getType().isSolid()){return;}
		if(cblk.getPistonMoveReaction() == PistonMoveReaction.BREAK){return;}

		Long startingTime = System.currentTimeMillis();
		extrudeSelectedBlocks(cblk, face, p, 30);
		Long endingTime = System.currentTimeMillis();
		Long time = endingTime - startingTime;
		long secs = time / 1000;
		//Bukkit.broadcastMessage("Time: " + Long.toString(time) + " ms");
	}
	@SuppressWarnings("deprecation")
	public static Boolean trySetWandBlock(Block orig, Block toSet, Player p){
		if(p.getGameMode() == GameMode.SURVIVAL){
			PlayerInventory inv = p.getInventory();

			if (!inv.removeItem(new ItemStack(orig.getType(), 1, (byte)0, orig.getData())).isEmpty()){
				return false;
			}

			p.updateInventory();
		}
		toSet.setType(orig.getType());
		toSet.setData(orig.getData());
		return true;
	}
	public static void extrudeSelectedBlocks(Block cblk, BlockFace face, Player p, int depth){
		if (depth <= 0){
			//Bukkit.broadcastMessage("Out of depth!");
			return;
		} 

		ArrayList<Block> selectionPlaneValidBlocks = getSelectionPlaneValidBlocks(cblk, face);
		//Bukkit.broadcastMessage(Integer.toString(selectionPlaneValidBlocks.size()));
		//cblk.getRelative(face).setType(Material.GOLD_BLOCK);
		if (!trySetWandBlock(cblk, cblk.getRelative(face), p)){return;}
		for (Block bl : selectionPlaneValidBlocks){
			if (!bl.isEmpty()){continue;}
			//			if (depth == 1){
			//				bl.setType(Material.REDSTONE_BLOCK);
			//			}else{
			//				bl.setType(Material.DIAMOND_BLOCK);
			//			}
			//			

			//double distance = bl.getLocation().distance(cblk.getLocation());
			//if (distance < 5){
			//Bukkit.broadcastMessage("inif");
			Block relative = bl.getRelative(face.getOppositeFace());
			//relative.setType(Material.DIAMOND_BLOCK);
			extrudeSelectedBlocks(relative, face, p, depth - 1);
			//	}
		}

	}
	public static ArrayList<Block> getSelectionBlocks(Block cblk, BlockFace face){
		ArrayList<Block> blks = new ArrayList<Block>();
		for (Block bl : getSelectionPlaneValidBlocks(cblk, face)){
			if (!blks.contains(bl)){blks.add(bl);}
			getBranchSelectionBlocks(blks, cblk, face);
			//			double distance = bl.getLocation().distance(cblk.getLocation());
			//			if (distance < 10){
			//				Bukkit.broadcastMessage("inif");
			//				Block relative = bl.getRelative(face.getOppositeFace());
			//				relative.setType(Material.DIAMOND_BLOCK);
			//				ArrayList<Block> selectionPlaneValidBlocks = getSelectionBlocks(relative, face);
			//				for (Block sb : selectionPlaneValidBlocks){
			//					if (!blks.contains(sb)){blks.add(sb);}
			//				}
			//			}
		}
		return blks;
	}
	public static void getBranchSelectionBlocks(ArrayList<Block> blks, Block cblk, BlockFace face){
		//ArrayList<Block> blks = new ArrayList<Block>();
		for (Block bl : getSelectionPlaneValidBlocks(cblk, face)){
			if (!blks.contains(bl)){blks.add(bl);}

			double distance = bl.getLocation().distance(cblk.getLocation());
			if (distance < 10){
				Block relative = bl.getRelative(face.getOppositeFace());
				getBranchSelectionBlocks(blks, relative, face);
				//				Bukkit.broadcastMessage("inif");

				//				relative.setType(Material.DIAMOND_BLOCK);
				//				ArrayList<Block> selectionPlaneValidBlocks = getBranchSelectionBlocks(blks, relative, face);
				//				for (Block sb : selectionPlaneValidBlocks){
				//					if (!blks.contains(sb)){blks.add(sb);blks.add(sb);}
				//				}
			}
		}
	}
	public static ArrayList<Block> getSelectionPlaneValidBlocks(Block cblk, BlockFace face){
		ArrayList<Block> blks = new ArrayList<Block>();
		for(Block bl : getSelectionPlaneBlocks(cblk, face)){
			Block backRel = bl.getRelative(face.getOppositeFace());
			Block relative = cblk.getRelative(face);
			if (backRel.getType() != cblk.getType() || bl.getType() != relative.getType()){continue;}
			blks.add(bl);
		}
		return blks;
	}
	public static ArrayList<Block> getSelectionPlaneBlocks(Block cblk, BlockFace face){
		ArrayList<Block> blks = new ArrayList<Block>();
		Block rel = cblk.getRelative(face);
		Vector vec = Utils.CrearVector(cblk.getLocation(), rel.getLocation());
		blks.add(rel);
		int i = 0;
		int a = 8;
		while(blks.size() < 9){ //9
			//Bukkit.broadcastMessage(Integer.toString(blks.size()));
			Vector v = getOrthogonalVector(face);

			//v.add(getOrthogonalVector(face).multiply(-1));
			//v.normalize();
			v.multiply(1.9);

			//v.add(new Vector(0.5, 0.5, 0.5));
			if (Utils.Possibilitat(50)){v.multiply(-1);}
			v.multiply(-1);
			//Block b = rel.getRelative(v.getBlockX(), v.getBlockY(), v.getBlockZ());
			Block b = rel.getLocation().add(v).getBlock();
			if (!blks.contains(b) && b.getLocation().distanceSquared(rel.getLocation()) <= 3){ //3
				blks.add(b);
			}
			//Fail
			i++;
			if (i > 500){
				//Bukkit.broadcastMessage("MAX");
				return blks;
			}
		}
		return blks;
	}

	public static Vector getOrthogonalVector(BlockFace f){
		Vector comp = getOrthogonalComps(f);

		Vector rand = BlockVector.getRandom();
		rand.subtract(Vector.getRandom());
		//rand.normalize();
		Vector result = new Vector(comp.getX() * rand.getX(), comp.getY() * rand.getY(), comp.getZ() * rand.getZ());
		//result.normalize();
		return result;
	}
	public static Vector getOrthogonalComps(BlockFace f){
		int x, y, z;
		x = 0; y = 0; z = 0;
		if(f == BlockFace.UP || f == BlockFace.DOWN){
			x = 1; y = 0; z = 1;
		}
		if(f == BlockFace.NORTH || f == BlockFace.SOUTH){
			x = 1; y = 1; z = 0;
		}
		if(f == BlockFace.EAST || f == BlockFace.WEST){
			x = 0; y = 1; z = 1;
		}
		return new Vector(x, y, z);
	}
	public static ArrayList<BlockFace> getOrthogonalFaces(BlockFace f){
		ArrayList<BlockFace> faces = new ArrayList<BlockFace>();
		switch(f){
		case DOWN:
			break;

		default:
			return getOrthogonalFaces(f.getOppositeFace());

		}
		return faces;
	}
}
