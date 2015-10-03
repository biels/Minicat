package com.biel.FastSurvival.Recall;

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

import com.bergerkiller.bukkit.common.Task;
import com.biel.FastSurvival.FastSurvival;
import com.biel.FastSurvival.Bows.BowUtils.BowType;
import com.biel.FastSurvival.Dimensions.Sky.SkyUtils;
import com.biel.FastSurvival.Utils.Cuboid;
import com.biel.FastSurvival.Utils.Utils;

public class RecallUtils {

	public static void addRecallRecipe(){
		ShapedRecipe r = new ShapedRecipe(getRecallItem(null));
		r.shape("ERE", "RWR", "ERE");
		r.setIngredient('W', Material.WATCH);
		r.setIngredient('R', Material.REDSTONE_BLOCK);
		r.setIngredient('E', Material.ENDER_PEARL);
		Bukkit.getServer().addRecipe(r);

	}
	public static Boolean isValidRecallItem(ItemStack s){
		if (!(s.getType() == Material.WATCH)){return false;}
		if (!s.hasItemMeta()){return false;}
		ItemMeta itemMeta = s.getItemMeta();
		if (!itemMeta.hasDisplayName()){return false;}
		if (!itemMeta.hasLore()){return false;}
		return true;
	}
	//Exemple - Material.WATCH
	/* Recall - Linked
	 * X: 605, Y: 72, Z: 562
	 * PiloWorld
	 * Cast: 5s, Idle: 12s
	 */
	/* Recall - Unlinked
	 * Right click to link
	 */
	public static ItemStack getRecallItem(RecallLink link){
		ItemStack itemStack = new ItemStack(Material.WATCH);
		//itemStack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		String name = "Recall" + " - ";
		String line2 = ChatColor.YELLOW + "";
		String line3 = ChatColor.YELLOW + "";
		String line4 = ChatColor.WHITE + "";
		if (link == null){
			name = name + ChatColor.RED + "Unlinked";
			line2 = line2 + "Right click to link";

			return Utils.setItemNameAndLore(itemStack, name, line2);
		}else{
			name = name + ChatColor.GREEN + "Linked";
			Location l = link.getLinkLocation();
			line2 = line2 + Utils.writeHumanReadableLocation(l, false);
			ArrayList<String> c = new ArrayList<String>();
			ArrayList<String> v = new ArrayList<String>();
			c.add("Cast");v.add(Integer.toString(link.getCastTime()));
			c.add("Idle");v.add(Integer.toString(link.getIdleTime()));
			line3 = line3 + l.getWorld().getName();
			line4 = line4 + Utils.writeHumanReadableList(c, v);

			return Utils.setItemNameAndLore(itemStack, name, line2, line3, line4);
		}

	}
	public static RecallLink getRecallLink(Player p, ItemStack stack){
		if (stack.getType() != Material.WATCH || !stack.hasItemMeta()){
			return null;
		}
		//Extract
		ItemMeta meta = stack.getItemMeta();
		if (!meta.hasDisplayName() || !meta.hasLore()){return null;}
		String name = meta.getDisplayName();
		String line2 = meta.getLore().get(0);
		String line3 = meta.getLore().get(1);
		String line4 = meta.getLore().get(2);
		//Parse
		RecallLink link = new RecallLink();
		World world = Bukkit.getWorld(line3.substring((ChatColor.YELLOW + "").length()));
		Location loc = Utils.readHumanReadableLocation(line2, world);
		//Bukkit.broadcastMessage(Utils.writeHumanReadableLocation(loc, true));
		if (loc == null){p.sendMessage(line3.substring((ChatColor.YELLOW + "").length()));}
		link.setLinkLocation(loc);
		int cast, idle;
		ArrayList<String> l4parts = Utils.readHumanReadableList(line4);
		cast = Integer.parseInt(l4parts.get(0));
		idle = Integer.parseInt(l4parts.get(1));
		link.setCastTime(cast);
		link.setIdleTime(idle);
		//Return
		return link;

	}
	//X: 605, Y: 72, Z: 562 
	Location locationFromMiddle(World w, String text){
		int lastIndex = 0;
		ArrayList<String> parts = new ArrayList<String>();
		while(parts.size() < 3){
			System.out.println(lastIndex);
			if (lastIndex > text.length()){
				lastIndex = text.length() - 1;
			}
			if (lastIndex < 0){
				lastIndex = 0;
			}
			int beginIndex = text.indexOf(":", lastIndex) + 2;
			int endIndex =  text.indexOf(",", beginIndex);
			if (endIndex == -1){
				endIndex = text.length();
			}
			System.out.println("Begin: " + beginIndex);
			System.out.println("End: " + endIndex);

			parts.add(text.substring(beginIndex, endIndex));
			lastIndex = endIndex;
		}
		//Return a location
		int x, y, z;
		x = Integer.parseInt(parts.get(0));
		y = Integer.parseInt(parts.get(1));
		z = Integer.parseInt(parts.get(2));
		Location loc = new Location(w, x, y, z);
		return loc;
	}
	public static void playerLinkItemInHand(Player p, Location l){
		//Link - Replace item in hand
		//RecallLink current = getRecallLink(p, p.getItemInHand());
		RecallLink newLink = createLinkFromLocation(l);
		if(newLink != null){
			p.setItemInHand(getRecallItem(newLink));
			p.sendMessage(ChatColor.GREEN + "Enllaçat correctament. " + ChatColor.YELLOW + Utils.writeHumanReadableLocation(newLink.getLinkLocation(), true));
		}else{
			p.sendMessage("Nomès pots enllaçar sobre blocs de maragda.");
		}
	}
	public static void playerRecallClickItemInHand(Player p){
		//Link - Replace item in hand
		//RecallLink current = getRecallLink(p, p.getItemInHand());
		ItemStack i = p.getItemInHand();
		//Reecall link
		RecallLink link = getRecallLink(p, i);
		if (link == null){
			p.sendMessage("Enllaç no vàlid");
			return;
		}
		Location linkLocation = link.getLinkLocation();
		if (linkLocation == null){
			p.sendMessage("Posició de destí inexistent");
			return;
		}
		Location loc = linkLocation.clone().add(0.5,1,0.5);
		//loc.setPitch(90);
		//p.teleport(loc);
		startRecallTeleport(p, link);
		//p.sendMessage("Transportat!");
	}
	public static RecallLink createLinkFromLocation(Location l){
		Block blk = l.getBlock();
		if (blk.getType() != Material.EMERALD_BLOCK){return null;}
		RecallLink link = new RecallLink();
		link.setLinkLocation(l);
		link.setCastTime(5);
		link.setIdleTime(9);
		return link;
	}
	public static void drawRecallEffect(Player p, Double prog){
		World w = p.getWorld();
		//SmokeCircle
		ArrayList<Location> locs = Utils.getLocationsCircle(p.getLocation().clone().add(0, -1, 0), 1.2, 3);
		if (prog >= 0.5D){
			locs.addAll(Utils.getLocationsCircle(p.getLocation().clone().add(0, 0, 0), 0.8, 4));
		}
		if (prog >= 0.75D){
			locs.add(p.getEyeLocation());
		}
		for (Location l : locs){
			if (Utils.Possibilitat(80)){continue;}
			//w.playEffect(l, Effect.SMOKE, Utils.NombreEntre(1, 8));
			Item it = w.dropItem(l, new ItemStack(Material.DIAMOND_BLOCK));
			it.setPickupDelay(200 * 600);
		}
		
	}
	public static void spawnRecallAnimItems(Location center, int waves){
		World w = center.getWorld();

		int wave = 1;
		while (wave <= waves){
			ArrayList<Location> locs = Utils.getLocationsCircle(center.clone().add(0, 1.1, 0), 1.25 * wave, 10);
			for (Location l : locs){
				//if (Utils.Possibilitat(80)){continue;}
				//w.playEffect(l, Effect.SMOKE, Utils.NombreEntre(1, 8));
				ItemStack itemStack = Utils.setItemNameAndLore(new ItemStack(Material.DIAMOND_BLOCK), "RecallAnimItem", Integer.toString(wave), UUID.randomUUID() + "");

				Item it = w.dropItem(l, itemStack);
				it.setVelocity(new Vector(0, 0, 0));
				it.setPickupDelay(200 * 600);
				it.setTicksLived((int) Math.round(20 * 60 * 4.8));

			}
			wave++;
		}
	}
	public static void magnetRecallAnimItems(Player p, ArrayList<Item> recallItems, int total){
		if (recallItems == null){return;}
		for(Item it: recallItems){
			if (it != null){
				if (it.isValid()){
					Vector vec = Utils.CrearVector(it.getLocation(), p.getEyeLocation());
					vec.multiply(3.0 / total);
					vec.setY(0);
					Location oldL = it.getLocation().clone();

					it.teleport(oldL.add(vec));
				}
			}
		}

	}
	public static void shotRecallItemsRing(Player p, ArrayList<Item> recallItems, int ring){
		for (Item it : recallItems){
			String strID = it.getItemStack().getItemMeta().getLore().get(0);
			int ringID = Integer.parseInt(strID);//strID.substring(strID.length() - 2, 1);
			Vector vec = Utils.CrearVector(it.getLocation(), p.getEyeLocation());
			vec.multiply(0.02);
			vec.setY(0);
			Location oldL = it.getLocation().clone();

			it.teleport(oldL.add(vec));

		}
	}
	public static void deleteRecallAnimItems(Player p,  ArrayList<Item> recallItems){
		if (recallItems == null){return;}
		for(Item it: recallItems){
			if (it != null){
				if (it.isValid()){
					it.remove();
				}
			}
			
		}
	}
	public static boolean isRecallAnimItem(ItemStack i){
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
	public static ArrayList<Item> getRecallanimItems(Player p){
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
	public static Boolean checkRecallArea(Location l){
		int d = 2;
		Cuboid flat = Utils.getCuboidAround(l.clone().add(0, -1, 0), d, 0, d);
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
	public static Boolean checkDestArea(Location dLoc){
		Block b = dLoc.getBlock();
		if (b.getType() != Material.EMERALD_BLOCK){return false;}
		Block relative = b.getRelative(BlockFace.UP);
		if (isValidSolidBlock(relative)){return false;}
		if (isValidSolidBlock(relative.getRelative(BlockFace.UP))){return false;}
		return true;
	}
	/**
	 * @param blk
	 * @return
	 */
	public static boolean isValidSolidBlock(Block blk) {
		if(blk.isLiquid() || !blk.getType().isBlock() || blk.isEmpty() || !blk.getType().isSolid()){
			return false;
		}
		return true;
	}
	public static void startRecallTeleport(Player p, final RecallLink link){
		if (isInRecall(p)){return;}
		//PRE-CHECK
		if(!checkRecallArea(p.getLocation())){
			p.sendMessage("Posició no vàlida per fer recall. Torna-ho a intentar en un lloc plà.");
			return;
		}
		if (!isWithinSameWorld(p, link)){
			p.sendMessage("No pots fer recall entre móns.");
			return;
		}
		if (!checkDestArea(link.getLinkLocation())){
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
		final String plyloc = Utils.writeHumanReadableLocation(p.getLocation(), false);
		double distance = p.getLocation().distance(link.getLinkLocation());
		int preTotal = (int) Math.round(20 * (6 + distance * 0.01));
		if (preTotal > 8 * 20){preTotal = 8 * 20;}
		final int total = preTotal; //Ticks
		final int taskid =  scheduler.scheduleSyncRepeatingTask(FastSurvival.getPlugin(), new Runnable() {
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
				boolean moved = !plyloc.equalsIgnoreCase(Utils.writeHumanReadableLocation(ply.getLocation(), false));
				boolean damaged = ply.getHealth() != ply.getMaxHealth();
				
				
				if (damaged){
					ply.sendMessage("Has de tenir la vida al màxim per poder fer recall!");
					cancelled = true;
				}
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
					Location loc = link.getLinkLocation().clone().add(0.5,1,0.5);
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
		p.setMetadata("RecallTask", new FixedMetadataValue(FastSurvival.getPlugin(), taskid));

	}
	/**
	 * @param p
	 * @param link
	 * @return
	 */
	public static boolean isWithinSameWorld(Player p, final RecallLink link) {
		return link.getLinkLocation().getWorld().getName().equalsIgnoreCase(p.getLocation().getWorld().getName());
	}
	public static void cancelRecallTask(final String plyStr) {
		///cancel
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(FastSurvival.getPlugin(), new Runnable() {
			@Override
			public void run() {
				Player ply2 = Bukkit.getPlayer(plyStr);
				MetadataValue metadataType = Utils.getMetadata(ply2, "RecallTask");
				if(metadataType == null){return;}
				int task = metadataType.asInt();

				if (task != 0){
					Bukkit.getServer().getScheduler().cancelTask(task);
					//Bukkit.broadcastMessage("Cancelled!");
				}
			}
		}, 1L);
	}
	public static Boolean isInRecall(Player p){
		//Metadata
		MetadataValue m = Utils.getMetadata(p, "isInRecall");
		if (m == null){return false;}
		return m.asBoolean();
	}
	public static void setInRecall(Player p, Boolean value){
		Utils.setMetadata(p, "isInRecall", value);
	}
	public static void cancelPossibleRecall(Player p){
		if (isInRecall(p)){
			setInRecall(p, false);
		}
	}
	public static void setRecallProgress(Player p, Double prog){
		p.setMetadata("RecallProgress", new FixedMetadataValue(FastSurvival.getPlugin(), prog));
	}
	public static Double getRecallProgress(Player p){
		MetadataValue m = Utils.getMetadata(p, "RecallProgress");
		return m.asDouble();
	}
	
}
