package com.biel.FastSurvival.Bows;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import com.avaje.ebean.ExampleExpression;
import com.biel.FastSurvival.Bows.BowUtils.BowType;
import com.biel.FastSurvival.Utils.Utils;

public class BowRecipeGenerator {
	public static void addBowRecipes(){
		enderRecipe();
		magneticRecipe();
		explosiveRecipe();
		torchRecipe();
		bouncyRecipe();
		icyRecipe();
		waterRecipe();
		witherRecipe();
		multiRecipe();
		electricRecipe();
	}
	static void enderRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.DARK_GREEN + Utils.L("BOW_NAME_TELEPORT"), ChatColor.WHITE + Utils.L("BOW_DESC_TELEPORT"), getID(BowType.ENDER)));
		r.shape("IEI", "EBE", "GRG");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE);
        r.setIngredient('G', Material.GOLD_INGOT);
        r.setIngredient('I', Material.IRON_INGOT);
        r.setIngredient('E', Material.ENDER_PEARL);
        Bukkit.getServer().addRecipe(r);
	}
	static void magneticRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.DARK_BLUE + Utils.L("BOW_NAME_MAGNETIC"), ChatColor.WHITE + Utils.L("BOW_DESC_MAGNETIC"), getID(BowType.MAGNETIC)));
		r.shape(" C ", "RBR", "RIR");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE);
        r.setIngredient('C', Material.COMPASS);
        r.setIngredient('I', Material.IRON_BLOCK);
        Bukkit.getServer().addRecipe(r);
	}
	static void explosiveRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.DARK_RED + Utils.L("BOW_NAME_EXPLOSIVE"), ChatColor.WHITE + Utils.L("BOW_DESC_EXPLOSIVE"), getID(BowType.EXPLOSIVE)));
		r.shape(" T ", "IBI", "XRX");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE);
        r.setIngredient('X', Material.TNT);
        r.setIngredient('I', Material.IRON_INGOT);
        r.setIngredient('T', Material.REDSTONE_TORCH_ON);
        Bukkit.getServer().addRecipe(r);
	}
	static void torchRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.DARK_GREEN + Utils.L("BOW_NAME_ILLUMINATOR"), ChatColor.WHITE + Utils.L("BOW_DESC_ILLUMINATOR"), getID(BowType.TORCH)));
		r.shape(" T ", "CBC", "SGS");
		r.setIngredient('B', Material.BOW);
		r.setIngredient('C', Material.COAL_BLOCK);
        r.setIngredient('S', Material.STICK);
        r.setIngredient('T', Material.TORCH);
        r.setIngredient('G', Material.THIN_GLASS);
        Bukkit.getServer().addRecipe(r);
	}
	static void bouncyRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.GREEN + Utils.L("BOW_NAME_BOUNCY"), ChatColor.WHITE + Utils.L("BOW_DESC_BOUNCY"), getID(BowType.BOUNCY)));
		r.shape(" S ", "SBS", "RGR");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE);
        r.setIngredient('G', Material.GOLD_INGOT);
        r.setIngredient('S', Material.SLIME_BALL);
        Bukkit.getServer().addRecipe(r);
	}
	static void icyRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.AQUA + Utils.L("BOW_NAME_ICY"), ChatColor.WHITE + Utils.L("BOW_DESC_ICY"), getID(BowType.ICY)));
		r.shape("GWG", "RBR", "IDI");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE);
        r.setIngredient('D', Material.DIAMOND);
        r.setIngredient('G', Material.GOLD_INGOT);
        r.setIngredient('I', Material.ICE);
        r.setIngredient('W', Material.WATCH);
        Bukkit.getServer().addRecipe(r);
	}
	static void witherRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.BLACK + Utils.L("BOW_NAME_WITHER"), ChatColor.WHITE + Utils.L("BOW_DESC_WITHER"), getID(BowType.WITHER)));
		r.shape(" S ", "RBR", "WGW");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE_BLOCK);
        r.setIngredient('W', Material.STONE_SWORD);
        r.setIngredient('G', Material.GOLD_INGOT);
        r.setIngredient('S', Material.SKULL_ITEM);
        Bukkit.getServer().addRecipe(r);
	}
	static void waterRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.BLUE + Utils.L("BOW_NAME_WATER"), ChatColor.WHITE + Utils.L("BOW_DESC_WATER"), getID(BowType.WATER)));
		r.shape(" W ", "LBL", "RVR");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE);
        r.setIngredient('V', Material.IRON_BARDING);
        r.setIngredient('W', Material.RAW_FISH);
        r.setIngredient('L', Material.LAPIS_BLOCK);
        Bukkit.getServer().addRecipe(r);
	}
	static void multiRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.DARK_PURPLE + Utils.L("BOW_NAME_MULTITARGET"), ChatColor.WHITE + Utils.L("BOW_DESC_MULTITARGET"), getID(BowType.MULTI)));
		r.shape("AAA", "DBD", "RVR");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE);
        r.setIngredient('V', Material.IRON_BARDING);
        r.setIngredient('A', Material.ARROW);
        r.setIngredient('D', Material.DISPENSER);
        Bukkit.getServer().addRecipe(r);
	}
	static void electricRecipe(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.BOW), ChatColor.DARK_BLUE + Utils.L("BOW_NAME_ELECTRIC"), ChatColor.WHITE + Utils.L("BOW_DESC_ELECTRIC"), getID(BowType.ELECTRIC)));
		r.shape(" D ", "VBV", "RLR");
		r.setIngredient('B', Material.BOW);
        r.setIngredient('R', Material.REDSTONE);
        r.setIngredient('V', Material.IRON_BARDING);
        r.setIngredient('D', Material.DIAMOND);
        r.setIngredient('L', Material.DIAMOND_BLOCK);
        Bukkit.getServer().addRecipe(r);
	}
	static String getID(BowType t){
		return Integer.toString(t.ordinal());
	}
	public static ArrayList<ItemStack> getAllBows(){
		ArrayList<ItemStack> arr = new ArrayList<ItemStack>();
		Iterator<Recipe> itr = Bukkit.recipeIterator();
		while(itr.hasNext()) {
			Recipe element = itr.next();
			if(element.getResult().getType() == Material.BOW && element.getResult().getItemMeta().hasLore()){
				arr.add(element.getResult());
			}
		}
		return arr;
	}
	public static ItemStack getRandomBow(Boolean electric){
		int id = Utils.NombreEntre(0, BowUtils.BowType.values().length - 1);
		
		BowType t = BowUtils.BowType.values()[id];
		if (!electric && t == BowType.ELECTRIC){return getRandomBow(false);}
		ItemStack i = BowRecipeGenerator.getBow(t);
		return i;
	}
	public static ItemStack getBow(BowType t){
		for (ItemStack i : getAllBows()){
			if (BowUtils.getBowType(i) == t){
				return i;
			}
		}
		return null;
	}
}
