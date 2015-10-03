package com.biel.BielAPI.Utils;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Com;



public class RecallUtils {

	private static void drawRecallEffect(Player p, Double prog){
		World w = p.getWorld();
		//SmokeCircle
		ArrayList<Location> locs = GUtils.getLocationsCircle(p.getLocation().clone().add(0, -1, 0), 1.2, 3);
		if (prog >= 0.5D){
			locs.addAll(GUtils.getLocationsCircle(p.getLocation().clone().add(0, 0, 0), 0.8, 4));
		}
		if (prog >= 0.75D){
			locs.add(p.getEyeLocation());
		}
		for (Location l : locs){
			if (GUtils.Possibilitat(80)){continue;}
			//w.playEffect(l, Effect.SMOKE, Utils.NombreEntre(1, 8));
			Item it = w.dropItem(l, new ItemStack(Material.DIAMOND_BLOCK));
			it.setPickupDelay(200 * 600);
		}
		
	}
	private static void spawnRecallAnimItems(Location center, int waves){
		World w = center.getWorld();

		int wave = 1;
		while (wave <= waves){
			ArrayList<Location> locs = GUtils.getLocationsCircle(center.clone().add(0, 1.1, 0), 1.25 * wave, 10);
			for (Location l : locs){
				//if (Utils.Possibilitat(80)){continue;}
				//w.playEffect(l, Effect.SMOKE, Utils.NombreEntre(1, 8));
				ItemStack itemStack = GUtils.setItemNameAndLore(new ItemStack(Material.DIAMOND_BLOCK), "RecallAnimItem", Integer.toString(wave), UUID.randomUUID() + "");

				Item it = w.dropItem(l, itemStack);
				it.setVelocity(new Vector(0, 0, 0));
				it.setPickupDelay(200 * 600);
				it.setTicksLived((int) Math.round(20 * 60 * 4.8));

			}
			wave++;
		}
	}
	private static void magnetRecallAnimItems(Player p, ArrayList<Item> recallItems, int total){
		if (recallItems == null){return;}
		for(Item it: recallItems){
			if (it != null){
				if (it.isValid()){
					Vector vec = GUtils.CrearVector(it.getLocation(), p.getEyeLocation());
					vec.multiply(3.0 / total);
					vec.setY(0);
					Location oldL = it.getLocation().clone();

					it.teleport(oldL.add(vec));
				}
			}
		}

	}
	private static void shotRecallItemsRing(Player p, ArrayList<Item> recallItems, int ring){
		for (Item it : recallItems){
			String strID = it.getItemStack().getItemMeta().getLore().get(0);
			int ringID = Integer.parseInt(strID);//strID.substring(strID.length() - 2, 1);
			Vector vec = GUtils.CrearVector(it.getLocation(), p.getEyeLocation());
			vec.multiply(0.02);
			vec.setY(0);
			Location oldL = it.getLocation().clone();

			it.teleport(oldL.add(vec));

		}
	}
	private static void deleteRecallAnimItems(Player p,  ArrayList<Item> recallItems){
		if (recallItems == null){return;}
		for(Item it: recallItems){
			if (it != null){
				if (it.isValid()){
					it.remove();
				}
			}
			
		}
	}
	private static boolean isRecallAnimItem(ItemStack i){
		if (i.getType() != Material.DIAMOND_BLOCK){return false;}
		if (i.hasItemMeta()){
			if (i.getItemMeta().hasDisplayName()){
				if(i.getItemMeta().getDisplayName().equals("RecallAnimItem")){
					return true;
				}
			}
		}
		return false;
	}
	private static ArrayList<Item> getRecallanimItems(Player p){
		ArrayList<Item> items = new ArrayList<Item>();
		for (Entity e : p.getNearbyEntities(14, 20, 14)){
			if (e instanceof Item){
				Item it = (Item)e;
				ItemStack stack = it.getItemStack();
				if (isRecallAnimItem(stack)){
					items.add(it);
				}
			}
		}
		return items;
	}
	private static Boolean checkRecallArea(Location l){
		int d = 2;
		Cuboid flat = GUtils.getCuboidAround(l.clone().add(0, -1, 0), d, 0, d);
		for (Block blk : flat.getBlocks()){
			boolean validSolidBlock = isValidSolidBlock(blk);
//			if (!validSolidBlock){
//				return false;
//			}
			boolean validSolidBlockUP = isValidSolidBlock(blk.getRelative(BlockFace.UP));
//			if (validSolidBlockUP){
//				return false;
//			}
			boolean dist = blk.getLocation().distance(l) < d;
			if ((!validSolidBlock || validSolidBlockUP) && dist){
				return false;
			}
		}
		return true;
	}
	private static Boolean checkDestArea(Location dLoc){
		Block b = dLoc.getBlock();
		//if (b.getType() != Material.EMERALD_BLOCK){return false;}
		Block relative = b.getRelative(BlockFace.UP);
		if (isValidSolidBlock(relative)){return false;}
		if (isValidSolidBlock(relative.getRelative(BlockFace.UP))){return false;}
		return true;
	}
	/**
	 * @param blk
	 * @return
	 */
	private static boolean isValidSolidBlock(Block blk) {
		if(blk.isLiquid() || !blk.getType().isBlock() || blk.isEmpty() || !blk.getType().isSolid()){
			return false;
		}
		return true;
	}
	public static void startRecallTeleport(Player p, final Location l){
		if (isInRecall(p)){return;}
		//PRE-CHECK
		if(!checkRecallArea(p.getLocation())){
			p.sendMessage("Posició no vàlida per fer recall. Torna-ho a intentar en un lloc plà.");
			return;
		}
		if (!checkDestArea(l)){
			p.sendMessage("El punt de destí no és vàlid o está obstruït.");
			return;
		}
		//START
		setInRecall(p, true);
		setRecallProgress(p, 0D);
		spawnRecallAnimItems(p.getLocation(), 3);
		//p.getLocation().setPitch(-90);
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		final String plyStr = p.getName();
		final String plyloc = GUtils.writeHumanReadableLocation(p.getLocation(), false);
		double distance = p.getLocation().distance(l);
		int preTotal = (int) Math.round(20 * (6 + distance * 0.01));
		if (preTotal > 8 * 20){preTotal = 8 * 20;}
		final int total = preTotal; //Ticks
		final int taskid =  scheduler.scheduleSyncRepeatingTask(Com.getPlugin(), new Runnable() {
			@Override
			public void run() {
				//Bukkit.broadcastMessage("Cycle!");
				Player ply = Bukkit.getPlayer(plyStr);
				if (ply == null){return;}
				if (ply.isDead() == true ){return;}
				if (!isInRecall(ply)){return;}
				
				Double recallProgress = getRecallProgress(ply);
				boolean cancelled = false;
				//----ANIMATION-----
				ArrayList<Item> recallItems = getRecallanimItems(ply);
				if (recallItems != null){
					magnetRecallAnimItems(ply, recallItems, total);
				}else{
					cancelled = true;
				}
				//----SOUND----
				float max = 2.6F;
				float pitch = (float) (max * recallProgress);
				float decay = 17.6F;
				double dp = 0.9;
				if (recallProgress > dp){
					pitch = (float) (pitch - (0.8F * (recallProgress - 0.8) * (recallProgress - 0.8)));
					pitch = (float) (max * dp - (decay * (recallProgress - dp)));
				}
				ply.getWorld().playSound(ply.getLocation(), Sound.NOTE_BASS, 3F, pitch);
				//------------------
				boolean moved = !plyloc.equalsIgnoreCase(GUtils.writeHumanReadableLocation(ply.getLocation(), false));
				boolean damaged = ply.getHealth() != ply.getMaxHealth();
				
				
//				if (damaged){
//					ply.sendMessage("Has de tenir la vida al màxim per poder fer recall!");
//					cancelled = true;
//				}
				if (moved){
					ply.sendMessage("No et pots moure durant el recall!");
					cancelled = true;
				}
				if (cancelled){
					//Cancel breking recall
					cancelRecallCompletely(plyStr, ply, recallItems);
					//ply.sendMessage("Recall interromput!");
				}
				if (recallProgress >= 1D ){
					cancelRecallCompletely(plyStr, ply, recallItems);
					Location loc = l.clone().add(0.5,1,0.5);
					loc.setPitch(0);
					ply.teleport(loc);
					//ply.sendMessage("Transportat!");
					
				}
				//Increment
				
				setRecallProgress(ply, recallProgress + (1D/total));


			}

			public void cancelRecallCompletely(final String plyStr, Player ply,	ArrayList<Item> recallItems) {
				deleteRecallAnimItems(ply, recallItems);
				cancelRecallTask(plyStr);
				setInRecall(ply, false);
			}
		}, 1L, 1);
		p.setMetadata("RecallTask", new FixedMetadataValue(Com.getPlugin(), taskid));

	}

	private static void cancelRecallTask(final String plyStr) {
		///cancel
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(Com.getPlugin(), new Runnable() {
			@Override
			public void run() {
				Player ply2 = Bukkit.getPlayer(plyStr);
				MetadataValue metadataType = GUtils.getMetadata(ply2, "RecallTask");
				if(metadataType == null){return;}
				int task = metadataType.asInt();

				if (task != 0){
					Bukkit.getServer().getScheduler().cancelTask(task);
					//Bukkit.broadcastMessage("Cancelled!");
				}
			}
		}, 1L);
	}
	private static Boolean isInRecall(Player p){
		//Metadata
		MetadataValue m = GUtils.getMetadata(p, "isInRecall");
		if (m == null){return false;}
		return m.asBoolean();
	}
	private static void setInRecall(Player p, Boolean value){
		GUtils.setMetadata(p, "isInRecall", value);
	}
	private static void cancelPossibleRecall(Player p){
		if (isInRecall(p)){
			setInRecall(p, false);
		}
	}
	private static void setRecallProgress(Player p, Double prog){
		p.setMetadata("RecallProgress", new FixedMetadataValue(Com.getPlugin(), prog));
	}
	private static Double getRecallProgress(Player p){
		MetadataValue m = GUtils.getMetadata(p, "RecallProgress");
		return m.asDouble();
	}
	
}
