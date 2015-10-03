package com.biel.FastSurvival.Turrets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Turrets.OldTurret.TipusMillora;
import com.biel.FastSurvival.Utils.Utils;

public class TurretUtils {
	public static void addRecipes(){
		addRecipe1();
		addRecipe2();
		addRecipe3();
	}
	static void addRecipe1(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.ARROW), "Crafted turret", "1"));
		r.shape(" T ", " I ", "EIE");
		r.setIngredient('I', Material.IRON_BLOCK);
		r.setIngredient('T', Material.REDSTONE_TORCH_ON);
		r.setIngredient('E', Material.REDSTONE_BLOCK);
		Bukkit.getServer().addRecipe(r);

	}
	static void addRecipe2(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.ARROW), "Crafted turret", "2"));
		r.shape(" T ", " I ", "EDE");
		r.setIngredient('I', Material.IRON_BLOCK);
		r.setIngredient('D', Material.DIAMOND_BLOCK);
		r.setIngredient('T', Material.REDSTONE_TORCH_ON);
		r.setIngredient('E', Material.REDSTONE_BLOCK);
		Bukkit.getServer().addRecipe(r);

	}
	static void addRecipe3(){
		ShapedRecipe r = new ShapedRecipe(Utils.setItemNameAndLore(new ItemStack(Material.ARROW), "Crafted turret", "3"));
		r.shape(" T ", " D ", "EDE");
		r.setIngredient('D', Material.DIAMOND_BLOCK);
		r.setIngredient('T', Material.GHAST_TEAR);
		r.setIngredient('E', Material.TNT);
		Bukkit.getServer().addRecipe(r);

	}
	public static void startTurretLogicTask(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FastSurvival.getPlugin(), new Runnable(){
			public void run() {
				TurretLogic.doAllLogic();
			}

		}, 5, 30); //1 tick = 1.5s
	}
	public static void initializeData(TurretData d){
		d.setActive(true);
		d.setEnabled(true);
		d.setBuilt(false);
		d.setTicks(0);
		d.setHealth(80);
		d.setLocation(new Location(FastSurvival.getWorld(), 0, 0, 0));
	}
	//public ItemStack 
	public static ItemStack createNewItemStack(int tier){
		int newiId = TurretData.getNextiId();
		//Bukkit.broadcastMessage(Integer.toString(newiId));
		TurretData data = new TurretData(newiId);
		initializeData(data);
		data.setTier(tier);
		return recreateItemStack(newiId);
	}
	@SuppressWarnings("deprecation")
	public static ItemStack recreateItemStack(int iId){
		TurretData d = new TurretData(iId);
		ItemStack i = Utils.setItemNameAndLore(new ItemStack(Material.ARROW, 1), getTierChatColor(d.getTier()) + "Torre de defensa", getLoreArr(d));
		//ItemStack i = new ItemStack(getMaterial());
		i.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, d.getTier());
		return i;//"[Line1]", "[Line2]", "Info{Current/Max}");
	}	
	private static String[] getLoreArr(TurretData d){
		ArrayList<String> arr = new ArrayList<String>(); //getDescLines(d);
		//		String valueLine = getValueLine(d);
		//		if (valueLine != null){
		//			arr.add(valueLine);
		//		}
		arr.add("T" + Integer.toString(d.iId));
		//To array
		String[] arrayResult = arr.toArray(new String[arr.size() - 1]);
		return arrayResult;
	}
	public static TurretData getItemStackData(ItemStack i){
		if (isTurret(i)){
			TurretData data = new TurretData(getiId(i));
			return data;
		}
		return null;
	}
	public static Boolean isTurret(ItemStack i){
		return hasiId(i);
	}
	private static int getiId(ItemStack i){
		if(i == null){return -1;}
		if(!i.hasItemMeta()){return -1;}
		ItemMeta meta = i.getItemMeta();
		if(!meta.hasLore()){return -1;}
		List<String> l = meta.getLore();
		if(l.size() == 0){return -1;}
		try {
			int id = l.size() - 1;
			String string2 = l.get(id);
			if (string2.startsWith("T")){
				String string = string2.substring(1);
				int iId = Integer.parseInt(string);
				if (iId > 0){
					return iId;
				}
			}
			return -1;
		} catch (Exception e) {
			return -1;
		}
	}
	private static boolean hasiId(ItemStack i){
		return getiId(i) != -1;
	}
	public static ChatColor getTierChatColor(int tier){
		switch (tier) {
		case 1:
			return ChatColor.YELLOW;
		case 2:
			return ChatColor.AQUA;
		case 3:
			return ChatColor.DARK_PURPLE;
		default:
			return ChatColor.WHITE;
		}
	}
	public static void dropTurret(TurretData d, Location l){
		l.getWorld().dropItemNaturally(l, recreateItemStack(d.iId));
	}
	//FILTERS
	public static ArrayList<TurretData> getActiveTurrets() {
		ArrayList<TurretData> r = new ArrayList<TurretData>();
		for(TurretData d : TurretData.getAllTurrets()){
			if(d.getActive()){
				r.add(d);
			}
		}
		return r;
	}
	public static ArrayList<TurretData> getNearbyTurrets(Location l, Double dist){
		ArrayList<TurretData> r = new ArrayList<TurretData>();
		for(TurretData d : getActiveTurrets()){
			try {
				if (d.getLocation().getWorld().equals(l.getWorld())){
					if(d.getLocation().distance(l) <= dist){
						r.add(d);
					}
				}
			} catch (Exception e) {}
		}
		return r;
	}
	public static TurretData getTurretAt(Location l){
		for(TurretData d : getNearbyTurrets(l, 12D)){
			Turret t = new Turret(d);
			if(t.ContainsTurretBlock(l)){
				return d;
			}
		}
		return null;
	}
	public static ArrayList<TurretData> getOwnedTurrets(Player p){
		ArrayList<TurretData> r = new ArrayList<TurretData>();
		for(TurretData d : getActiveTurrets()){
			if(d.getOwner().equalsIgnoreCase(p.getName())){
				r.add(d);
			}
		}
		return r;
	}
	//-------
	public static TurretData getShooterTurret(Projectile p){
		MetadataValue meta = Utils.getMetadata(p, "Tower");
		if (meta != null){
			return new TurretData(meta.asInt());
		}
		return null;
	}

}
