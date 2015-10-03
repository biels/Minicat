package com.biel.FastSurvival;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.*;

import com.bergerkiller.bukkit.common.Task;
import com.biel.FastSurvival.Bows.BowRecipeGenerator;
import com.biel.FastSurvival.Bows.CustomBowsListener;
import com.biel.FastSurvival.BuilderWand.BuilderWandListener;
import com.biel.FastSurvival.BuilderWand.BuilderWandUtils;
import com.biel.FastSurvival.Dimensions.Moon.MoonChunkGenerator;
import com.biel.FastSurvival.Dimensions.Moon.MoonListener;
import com.biel.FastSurvival.Dimensions.Moon.MoonUtils;
import com.biel.FastSurvival.Dimensions.Moon.TeleporterListener;
import com.biel.FastSurvival.Dimensions.Sky.KnockUpListener;
import com.biel.FastSurvival.Dimensions.Sky.SkyListener;
import com.biel.FastSurvival.Dimensions.Sky.SkyUtils;
import com.biel.FastSurvival.MobIntelligence.MobListener;
import com.biel.FastSurvival.Recall.RecallListener;
import com.biel.FastSurvival.Recall.RecallUtils;
import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemsUtils;
import com.biel.FastSurvival.Turrets.OldTurret;
import com.biel.FastSurvival.Turrets.TurretListener;
import com.biel.FastSurvival.Turrets.TurretLogic;
import com.biel.FastSurvival.Turrets.TurretUtils;
import com.biel.FastSurvival.Utils.GestorPropietats;
import com.biel.FastSurvival.Utils.MazeGenerator;
import com.biel.FastSurvival.Utils.Metrics;
import com.biel.FastSurvival.Utils.Utils;

public final class FastSurvival extends JavaPlugin {
	public GestorPropietats pTemp = new GestorPropietats("pTemp.txt");
	public void onEnable(){

		getLogger().info("onEnable has been invoked!");
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
			System.out.println("Metrics fail :-(");
		}
		//
		if (!FastSurvival.getPlugin().getDataFolder().exists()){FastSurvival.getPlugin().getDataFolder().mkdirs();}

		getServer().getPluginManager().registerEvents(new EventListener(), this);
		getServer().getPluginManager().registerEvents(new CustomBowsListener(), this);
		getServer().getPluginManager().registerEvents(new MobListener(), this);
		getServer().getPluginManager().registerEvents(new TeleporterListener(), this);
		getServer().getPluginManager().registerEvents(new MoonListener(), this);
		getServer().getPluginManager().registerEvents(new KnockUpListener(), this);
		getServer().getPluginManager().registerEvents(new SkyListener(), this);
		getServer().getPluginManager().registerEvents(new RecallListener(), this);
		getServer().getPluginManager().registerEvents(new BuilderWandListener(), this);
		getServer().getPluginManager().registerEvents(new TurretListener(), this);
		SpecialItemsUtils.registerItemListeners();
		//
		Task myTask = new Task(getPlugin()) {
			@Override
			public void run() {
				MoonUtils.loadMoon();
				SkyUtils.loadSky();
				Bukkit.broadcastMessage("Addtitional worlds loaded!");
				TurretUtils.startTurretLogicTask();
				Bukkit.broadcastMessage("Turret logic started!");
			}
		}.start(1);

		//getServer().getPluginManager().registerEvents(new MobTracker(), this);
		BowRecipeGenerator.addBowRecipes();
		ToolRecipeGenerator.addToolRecipes();
		RecallUtils.addRecallRecipe();
		MoonUtils.spaceGlassRecipe();
		BuilderWandUtils.addWandRecipe();
		TurretUtils.addRecipes();
		
		//MoonUtils.loadMoon();
		//Turret.loadInstances();
	}

	public void onDisable(){
		getLogger().info("onDisable has been invoked!");
		//Turret.saveInstances();

	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("save")){ // If the player typed /basic then do the following...
			if (sender instanceof Player){
				Player p = (Player) sender;
				//Turret.saveInstances();
				getLogger().info("Turrets saved!");

			}
			return true;
		} //If this has happened the function will return true. 
		if(cmd.getName().equalsIgnoreCase("load")){ // If the player typed /basic then do the following...
			if (sender instanceof Player){
				Player p = (Player) sender;
				//Turret.loadInstances();
				getLogger().info("Turrets loaded!");

			}
			return true;
		} //If this has happened the function will return true. 
		if(cmd.getName().equalsIgnoreCase("moon")){ // If the player typed /basic then do the following...
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage("Has d'estar en creatiu per per això! Construeix un teletransportador per fer-ho legalment en supervivència!");return true;}
				if (MoonUtils.IsInMoon(p)){
					MoonUtils.teleportPlayerToEarth(p);
					getLogger().info("Teleported to earth!");
					return true;
				}
				MoonUtils.teleportPlayerToMoon(p);
				getLogger().info("Teleported to moon!");

			}
			return true;
		} //If this has happened the function will return true. 
		if(cmd.getName().equalsIgnoreCase("sky")){ // If the player typed /basic then do the following...
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage("Has d'estar en creatiu per per això! Construeix un corrent d'aigua (Knock Up) per fer-ho legalment en supervivència!");return true;}
				if (SkyUtils.IsInSky(p)){
					SkyUtils.teleportPlayerToEarth(p);
					getLogger().info("Teleported to earth!");
					return true;
				}
				SkyUtils.teleportPlayerToSky(p);
				getLogger().info("Teleported to Sky!");

			}
			return true;
		} //If this has happened the function will return true. 
		if(cmd.getName().equalsIgnoreCase("e")){
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage("Has d'estar en creatiu per per això!");return true;}
				Inventory i = p.getInventory();
				ItemStack itemStacke = new ItemStack(Material.BOW);
				itemStacke.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
				i.addItem(itemStacke);
				ItemStack itemStack3 = new ItemStack(Material.DIAMOND_SWORD);
				itemStack3.addEnchantment(Enchantment.DAMAGE_ALL, 2);
				i.addItem(itemStack3);

				i.addItem(new ItemStack(Material.DIAMOND_AXE));
				ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
				itemStack.addEnchantment(Enchantment.DIG_SPEED, 2);
				i.addItem(itemStack);
				i.addItem(new ItemStack(Material.GOLD_ORE, 64));
				i.addItem(new ItemStack(Material.GOLD_ORE, 64));
				i.addItem(new ItemStack(Material.COOKED_BEEF, 64));
				p.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				p.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				p.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				p.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
				i.addItem(new ItemStack(Material.ARROW, 64));

			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("t")){
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage("Has d'estar en creatiu per fer això!");return true;}
				Inventory i = p.getInventory();
				int count = 0;
				while (count < 2){
					i.addItem(TurretUtils.createNewItemStack(Utils.NombreEntre(1, 3)));
					count++;
				}
				
				

			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("moonmats")){
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage("Has d'estar en creatiu per fer això!");return true;}
				Inventory i = p.getInventory();
				i.addItem(new ItemStack(Material.IRON_BLOCK, 8));
				i.addItem(new ItemStack(Material.IRON_FENCE, 4));
				i.addItem(new ItemStack(Material.WOOD_BUTTON, 2));
				i.addItem(new ItemStack(Material.STONE_BUTTON, 1));


			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("recall")){
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage("Has d'estar en creatiu per fer això!");return true;}
				Inventory i = p.getInventory();
				i.addItem(RecallUtils.getRecallItem(null));


			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("wand")){
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage("Has d'estar en creatiu per fer això!");return true;}
				Inventory i = p.getInventory();
				i.addItem(BuilderWandUtils.getWandItem());


			}
			return true;
		}

		if(cmd.getName().equalsIgnoreCase("it")){
			if (sender instanceof Player){
				Player p = (Player) sender;
				
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage("Has d'estar en creatiu per fer això!");return true;}
				ArrayList<SpecialItem> registeredSpecialItems = SpecialItemsUtils.getRegisteredSpecialItems();
				Inventory i;
				if(args.length == 0){
					int rows = 1;
					while(rows * 9 < registeredSpecialItems.size()){
						rows++;
					}
					i = Bukkit.createInventory(p, 9 * rows, ChatColor.RED + "Special items - Creative mode");
				}else{
					i = p.getInventory();
				}
				Boolean fet = false;
//				if(args.length == 1){
//					try {
//						int parseInt = Integer.parseInt(args[1]);
//						if(parseInt <= 3 && parseInt >= 1){
//							int rows = 1;
//							while(rows * 9 < registeredSpecialItems.size()){
//								rows++;
//							}
//							i = Bukkit.createInventory(p, 9 * rows, ChatColor.RED + "Tier "+ parseInt +" Special items - Creative mode");
//
//							int count = 0;
//							while (count <= rows * 9){
//								i.addItem(SpecialItemsUtils.getRandomSpecialItem(parseInt));
//							}
//							fet = true;
//						}
//					} catch (NumberFormatException e) {
//					} catch (IllegalArgumentException e) {
//					}
//				}
				if (fet == false){
					for (SpecialItem s : registeredSpecialItems){
						i.addItem(s.createNewItemStack());
					}
				}
				
				if(args.length == 0 || fet == true){
					p.openInventory(i);
				}

				//p.setGameMode(GameMode.SURVIVAL);
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("maze")){ // If the player typed /basic then do the following...
			if (sender instanceof Player){
				Player p = (Player) sender;
				int x = args.length >= 1 ? (Integer.parseInt(args[0])) : 8;
				int y = args.length == 2 ? (Integer.parseInt(args[0])) : 8;
				MazeGenerator maze = new MazeGenerator(x, y);
				maze.display(p.getLocation());
			}

		}
		if(cmd.getName().equalsIgnoreCase("b")){ // If the player typed /basic then do the following...
			if (sender instanceof Player){
				Player p = (Player) sender;
				if (!(p.getGameMode() == GameMode.CREATIVE)){p.sendMessage(Utils.L("MUST_BE_IN_CREATIVE"));return true;}
				Inventory i = p.getInventory();
				Iterator<Recipe> itr = Bukkit.recipeIterator();
				while(itr.hasNext()) {
					Recipe element = itr.next();
					if(element.getResult().getType() == Material.BOW && element.getResult().getItemMeta().hasLore()){
						i.addItem(element.getResult());
					}
				}
				i.addItem(new ItemStack(Material.ARROW, 64));

			}
			return true;
		} //If this has happened the function will return true. 
		// If this hasn't happened the a value of false will be returned.
		return false; 
	}
	//	@Override
	//	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
	//		// TODO Auto-generated method stub
	//		ChunkGenerator c = super.getDefaultWorldGenerator(worldName, id);
	//		
	//		return c;
	//	}
	static public World getWorld(){
		return Bukkit.getWorlds().get(0);
	}
	static public GestorPropietats Config(){
		File f = new File(FastSurvival.getPlugin().getDataFolder().getAbsolutePath() + ((String)  File.separator) + "Config.txt");
		if (!f.exists()){
			//Default settings
			GestorPropietats g = new GestorPropietats(f.getAbsolutePath());
			g.EstablirPropietat("LanguageFileName", "CAT");
		}

		return new GestorPropietats(f.getAbsolutePath());
	}
	static public FastSurvival getPlugin() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("FastSurvival");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof FastSurvival)) {
			return null; // Maybe you want throw an exception instead
		}

		return (FastSurvival) plugin;
	}
	//	@Override
	//    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
	//        return new MoonChunkGenerator();
	//    }
}
