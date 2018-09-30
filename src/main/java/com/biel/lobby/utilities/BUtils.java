package com.biel.lobby.utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BUtils {
	@SuppressWarnings("deprecation")
	// TODO: Fix this function
	/*public static void fillBlocks(List<Block> blks, ArrayList<Material> ms, ArrayList<Byte> datas, ArrayList<Integer> chances){
		for(Block b : blks){
			Material m = null;
			if (chances == null){m = ms.get(0);}else{
				while(m == null){
					for(Material mat : ms){
						if (Utils.Possibilitat(chances.get(ms.indexOf(mat)), 1000)){
							m = mat;
						}
					}
				}
			}
			b.setType(m);
			if (datas != null){
				b.setData(datas.get(ms.indexOf(m)));
			}

		}
	}
	public static void fillBlocks(List<Block> blks, ArrayList<Material> ms, ArrayList<Integer> chances){
		fillBlocks(blks, ms, null, chances);
	}*/
	/*public static void fillBlocks(List<Block> blks, Material m, byte data){
		ArrayList<Material> ms = new ArrayList<>();

		ms.add(m);
		if (data != 0){
			ArrayList<Byte> ds = new ArrayList<>();
			ds.add(data);
			fillBlocks(blks, ms, ds, null);
		}else{
			fillBlocks(blks, ms, null, null);
		}
	}
	public static void fillBlocks(List<Block> blks, Material m){
		fillBlocks(blks, m, (byte)0);
	}*/
	public static ArrayList<Block> locListToBlock(List<Location> locs) {
		ArrayList<Block> blks = new ArrayList<>();
		for(Location l : locs){
			blks.add(l.getBlock());
		}
		return blks;
	}

	// This can possibly be changed using block tags
	public static boolean isBedBlock(Block block)  {

		Material type = block.getType();
		return type.equals(Material.WHITE_BED) || type.equals(Material.BLACK_BED) || type.equals(Material.BLUE_BED) || type.equals(Material.BROWN_BED) ||
				type.equals(Material.BROWN_BED) || type.equals(Material.CYAN_BED) || type.equals(Material.GRAY_BED)  || type.equals(Material.GREEN_BED) ||
				type.equals(Material.LIGHT_BLUE_BED) || type.equals(Material.LIGHT_GRAY_BED) || type.equals(Material.LIME_BED) || type.equals(Material.MAGENTA_BED) ||
				type.equals(Material.ORANGE_BED) || type.equals(Material.PINK_BED) || type.equals(Material.PURPLE_BED) || type.equals(Material.RED_BED)  || type.equals(Material.YELLOW_BED);
	}

	public static Material dyeToWool(DyeColor color) {

		// TODO: Pretty sure there's better ways to do this
		if(color.equals(DyeColor.BLACK)) return Material.BLACK_WOOL;
		if(color.equals(DyeColor.BLUE)) return Material.BLUE_WOOL;
		if(color.equals(DyeColor.BROWN)) return Material.BROWN_WOOL;
		if(color.equals(DyeColor.CYAN)) return Material.CYAN_WOOL;
		if(color.equals(DyeColor.GRAY)) return Material.GRAY_WOOL;
		if(color.equals(DyeColor.GREEN)) return Material.GREEN_WOOL;
		if(color.equals(DyeColor.LIGHT_BLUE)) return Material.LIGHT_BLUE_WOOL;
		if(color.equals(DyeColor.LIGHT_GRAY)) return Material.LIGHT_GRAY_WOOL;
		if(color.equals(DyeColor.LIME)) return Material.LIME_WOOL;
		if(color.equals(DyeColor.MAGENTA)) return Material.MAGENTA_WOOL;
		if(color.equals(DyeColor.ORANGE)) return Material.ORANGE_WOOL;
		if(color.equals(DyeColor.PINK)) return Material.PINK_WOOL;
		if(color.equals(DyeColor.PURPLE)) return Material.PURPLE_WOOL;
		if(color.equals(DyeColor.RED)) return Material.RED_WOOL;
		if(color.equals(DyeColor.WHITE)) return Material.WHITE_WOOL;
		if(color.equals(DyeColor.YELLOW)) return Material.YELLOW_WOOL;


		return Material.WHITE_WOOL;
	}

	public static boolean isGlassBlock(Block block)  {

		Material type = block.getType();
		return type.equals(Material.GLASS) || type.equals(Material.GLASS_PANE) ||
				type.equals(Material.BLACK_STAINED_GLASS) || type.equals(Material.BLACK_STAINED_GLASS_PANE) ||
				type.equals(Material.BLUE_STAINED_GLASS) || type.equals(Material.BLUE_STAINED_GLASS_PANE) ||
				type.equals(Material.BROWN_STAINED_GLASS) || type.equals(Material.BROWN_STAINED_GLASS_PANE) ||
				type.equals(Material.CYAN_STAINED_GLASS) || type.equals(Material.CYAN_STAINED_GLASS_PANE) ||
				type.equals(Material.GRAY_STAINED_GLASS) || type.equals(Material.GRAY_STAINED_GLASS_PANE) ||
				type.equals(Material.GREEN_STAINED_GLASS) || type.equals(Material.GREEN_STAINED_GLASS_PANE) ||
				type.equals(Material.LIGHT_BLUE_STAINED_GLASS) || type.equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE) ||
				type.equals(Material.LIGHT_GRAY_STAINED_GLASS) || type.equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE) ||
				type.equals(Material.LIME_STAINED_GLASS) || type.equals(Material.LIME_STAINED_GLASS_PANE) ||
				type.equals(Material.MAGENTA_STAINED_GLASS) || type.equals(Material.MAGENTA_STAINED_GLASS_PANE) ||
				type.equals(Material.ORANGE_STAINED_GLASS) || type.equals(Material.ORANGE_STAINED_GLASS_PANE) ||
				type.equals(Material.PINK_STAINED_GLASS) || type.equals(Material.PINK_STAINED_GLASS_PANE) ||
				type.equals(Material.PURPLE_STAINED_GLASS) || type.equals(Material.PURPLE_STAINED_GLASS_PANE) ||
				type.equals(Material.RED_STAINED_GLASS) || type.equals(Material.RED_STAINED_GLASS_PANE) ||
				type.equals(Material.WHITE_STAINED_GLASS) || type.equals(Material.WHITE_STAINED_GLASS_PANE) ||
				type.equals(Material.YELLOW_STAINED_GLASS) || type.equals(Material.YELLOW_STAINED_GLASS_PANE);
	}

}
