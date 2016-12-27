package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocObjectius;
import com.biel.lobby.mapes.jocs.Arena4.Arena4Equip;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.Skill;
import com.biel.lobby.utilities.events.skills.types.specificskills.CalciumSourceSkill;

public class RainbowClay extends JocObjectius {
	public RainbowClay() {
		super();

	}
	@Override
	public String getGameName() {
		return "RainbowClay";
	}
	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<Equip>();
		equips.add(new EquipObjectius(DyeColor.RED, "vermell")); //Id 0
		equips.add(new EquipObjectius(DyeColor.BLUE, "blau")); //Id 1
		return equips;
	}
	@Override
	protected ArrayList<Objectiu> getDesiredObjectivesTeam(EquipObjectius e) {
		ArrayList<Objectiu> objectius = new ArrayList<Objectiu>();
		//Bukkit.broadcastMessage("Cores" + Integer.toString(e.getId() + 1));
		ArrayList<Location> coreLocs = pMapaActual().ObtenirLocations("Cores" + Integer.toString(e.getId() + 1), getWorld());
		objectius.add(new ObjectiuBlockBreak("Core " + e.getAdjectiu() + " L", coreLocs.get(0)));
		objectius.add(new ObjectiuBlockBreak("Core " + e.getAdjectiu() + " R", coreLocs.get(1)));
		if (e.getId() == 0){
			//objectius.add(new Objectiu("Llana vermella" , pMapaActual().ObtenirLocation("WRed", getWorld()), DyeColor.RED));
			objectius.add(new ObjectiuWoolPlace("Llana vermella", pMapaActual().ObtenirLocation("WRed", getWorld()), DyeColor.RED));
			objectius.add(new ObjectiuWoolPlace("Llana magenta" , pMapaActual().ObtenirLocation("WMagenta", getWorld()), DyeColor.MAGENTA));
		}else{
			objectius.add(new ObjectiuWoolPlace("Llana verda" , pMapaActual().ObtenirLocation("WGreen", getWorld()), DyeColor.LIME));
			objectius.add(new ObjectiuWoolPlace("Llana blava" , pMapaActual().ObtenirLocation("WBlue", getWorld()), DyeColor.BLUE));
		}
		return objectius;
	}
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void customJocFinalitzat() {
		super.customJocFinalitzat();
		setBlockBreakPlace(false);
	}

	@Override
	protected void customJocIniciat() {
		super.customJocIniciat();
		setBlockBreakPlace(true);
		setGiveStartingItemsRespawn(true);
		ProgTask();
	}
	@Override
	public boolean giveSnowLauncherOnKill() {
		// TODO Auto-generated method stub
		return true;
	}
//	@Override
//	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
//		// TODO Auto-generated method stub
//		super.onPlayerDeath(evt, killed);
//		ArrayList<ItemStack> startingItems = getStartingItems(killed);
//		evt.getDrops().removeAll(startingItems);
//		ArrayList<ItemStack> rem = new ArrayList<ItemStack>();
//		for(ItemStack i : evt.getDrops()){
//			Material t = i.getType();
//			boolean cname = false; 
//			if(i.hasItemMeta()){
//				cname = i.getItemMeta().hasDisplayName();
//			}
//			for(ItemStack starti : startingItems){
//				if (i.getType() == starti.getType()){
//					ItemStack remi = i.clone();
//					remi.setAmount(starti.getAmount());
//					rem.add(remi);
//				}
//			}
//			//			boolean valid = (t == Material.WOOL || t == Material.TNT || t == Material.DIAMOND || cname);
//			//			if(valid){
//			//				rem.add(i);
//			//			}
//		}
//		evt.getDrops().removeAll(rem);
//		//		evt.getDrops().clear();
//		//		//evt.getDrops().add(new ItemStack(Material.DIAMOND));
//		//		evt.getDrops().addAll(rem);
//
//	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);

//		double distance = killed.getLocation().distance(killer.getLocation());
//		boolean lluny = distance > 50;
//		if (lluny){
//			evt.setDeathMessage(killed.getName() + " ha estat assassinat per " + killer.getName() + " desde "+ Long.toString(Math.round(distance)) +" blocs");
//		}
//		if (!lluny && !isPlayerLookingAtAnother(killed, killer)){
//			evt.setDeathMessage(killed.getName() + " ha estat assassinat per l'esquena per " + killer.getName());
//
//		}

	}

	public static boolean isPlayerLookingAtAnother(Player p1, Player p2)
	{
		boolean isLooking = false;
		Location loc = lookAt(p1.getLocation(), p2.getLocation());
		Location loc1 = p1.getLocation();
		int yaw1 = (int)Math.floor(loc.getYaw());
		int yaw2 = (int)Math.floor(loc1.getYaw());
		int pitch1 = (int)Math.floor(loc.getPitch());
		int pitch2 = (int)Math.floor(loc1.getPitch());
		if(yaw1 == yaw2 && pitch1 == pitch2)
		{
			isLooking = true;
		}
		return isLooking;
	}
	public static Location lookAt(Location loc, Location lookat)
	{

		loc = loc.clone();

		double dx = lookat.getX() - loc.getX();
		double dy = lookat.getY() - loc.getY();
		double dz = lookat.getZ() - loc.getZ();

		if(dx != 0)
		{
			if(dx < 0)
			{
				loc.setYaw((float)(1.5 * Math.PI));
			}
			else
			{
				loc.setYaw((float)(0.5 * Math.PI));
			}
			loc.setYaw(loc.getYaw() - (float)Math.atan(dz / dx));
		}
		else if(dz < 0)
		{
			loc.setYaw((float)Math.PI);
		}

		double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

		loc.setPitch((float)-Math.atan(dy / dxz));

		loc.setYaw(-loc.getYaw() * 180f / (float)Math.PI);
		loc.setPitch(loc.getPitch() * 180f / (float)Math.PI);

		return loc;
	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		Equip e = obtenirEquip(ply);
		items.add(new ItemStack(Material.IRON_SWORD, 1));
		ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		double balancingMultiplier = getBalancingMultiplier(e);
		if(balancingMultiplier > 1)pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, (balancingMultiplier > 1.20 ? 2 : 1));
		items.add(pickaxe);
		int launchers = (int) (8 * balancingMultiplier);
		if(launchers > 64)launchers = 64;
		items.add(getSnowLauncher(launchers));
		//items.add(new ItemStack(Material.FLINT_AND_STEEL));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		ItemStack arc = new ItemStack(Material.BOW, 1); // A stack of diamonds
		arc.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		items.add(arc);
		int arrows = (int) (50 * balancingMultiplier);
		if(arrows > 64){arrows = 64;}
		items.add(new ItemStack(Material.ARROW, arrows));
		int ladders = (int) (50 * balancingMultiplier);
		if(ladders > 64){ladders = 64;}
		items.add(new ItemStack(Material.LADDER, ladders));
		int block_amount = (int) (45 * balancingMultiplier);
		if(block_amount > 64){block_amount = 64;}
		if (obtenirEquip(ply).getId() == 0){
			items.add(new ItemStack(Material.STAINED_CLAY, block_amount, (short) 14));
		}else{
			items.add(new ItemStack(Material.STAINED_CLAY, block_amount, (short) 10));
		}
		items.add(new ItemStack(Material.WEB, 1));
		return items;
	}
	@Override
	protected void donarEfectesInicials(Player ply) {
		// TODO Auto-generated method stub
		super.donarEfectesInicials(ply);
		double m = getBalancingMultiplier(obtenirEquip(ply));
		ply.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (int) (30 * 10 * (m - 0.5)), 5, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 1, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 30, 0, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 3, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int) (20 * 19 * m), 1, true), true);
	}
	@Override
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockPlace(evt, blk);
		Player ply = evt.getPlayer();
		//Bukkit.broadcastMessage(Boolean.toString(evt.isCancelled()));
		Cuboid cub = pMapaActual().ObtenirCuboid("RegC", getWorld());
		if(cub.contains(blk)){
			evt.setCancelled(true);
		}
		if (blk.getType() == Material.OBSIDIAN){
			evt.setCancelled(true);
			evt.getPlayer().damage(15);
		}
		//		if (blk.getType() == Material.WOOL){
		//			
		//			for (Objectiu obj : obtenirObjectiusPly(ply)){
		//				Bukkit.broadcastMessage(Double.toString(obj.getLocation().distance(blk.getLocation())));
		//				if (obj.getLocation().distance(blk.getLocation()) < 2){
		//					Wool wool = new Wool(DyeColor.getByWoolData(blk.getData()));
		//	    			Location blkLoc = blk.getLocation();
		//	    			if(wool.getColor().getWoolData() == ((DyeColor) obj.getInfo()).getWoolData()){
		//	    				obj.complete(ply);
		//	    			}else{
		//	    				evt.setCancelled(true);
		//	    			}
		//					
		//				}
		//				
		//			}
		//		}
	}
	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockBreak(evt, blk);
		Player ply = evt.getPlayer();

		Cuboid cub = pMapaActual().ObtenirCuboid("RegC", getWorld());
		if(cub.contains(blk)){
			evt.setCancelled(true);
		}
		if (blk.getType() == Material.STAINED_CLAY && blk.getData() == 11 ) { 
			evt.setCancelled(true);
		}
	}

	@Override
	protected void onPlayerMove(PlayerMoveEvent evt, Player P) {
		// TODO Auto-generated method stub
		super.onPlayerMove(evt, P);
		Player ply = evt.getPlayer();
		if(isSpectator(ply))return;
		if (JocIniciat){
			Player plyr =  evt.getPlayer();

			Location to = evt.getTo();
			Location from = evt.getFrom();

			int equip = obtenirEquip(ply).getId() + 1;
			if (ply.getLocation().getY() < 102){
				ply.setFireTicks(5000);
			}
			if (ply.getLocation().getY() < 60){
				ply.damage(10000);
			}

			//Torres escuts
			int e = 1;
			while(e <= 2){
				int i = 0;
				while(i <= 1){
					Cuboid cub = pMapaActual().ObtenirCuboid("RegT" + Integer.toString(e) + Integer.toString(i), getWorld()); 
					Location center = cub.getCenter();
					if(cub.contains(to.getBlock())){
						if(e == equip){
							Vector vec = Utils.CrearVector(center, from).normalize().add(new Vector(0, 1, 0));
							getWorld().playSound(to, Sound.ENTITY_IRONGOLEM_HURT, 1F, 2.2F);
							getWorld().playEffect(to, Effect.MOBSPAWNER_FLAMES, 3);
							getWorld().playEffect(to.clone().add(new Vector(0, 1, 0)), Effect.MOBSPAWNER_FLAMES, 3);
							if(cub.contains(from.getBlock()) && plyr.getVelocity().length() >= 1){
								plyr.teleport(from.add(vec));
								//Bukkit.broadcastMessage("ha entrat");
							}else{
								plyr.setVelocity(vec);
							}
							//evt.setCancelled(true);

						}
					}
					i = i + 1;
				}
				e = e + 1;
			}
			//SECURE NO-FALL
			//
			boolean isNoFallActive = false;

			ItemStack itemInHand = ply.getItemInHand();
			if(itemInHand.hasItemMeta()){
				ItemMeta itemMeta = itemInHand.getItemMeta();
				if (itemMeta.hasDisplayName()){
					if (itemMeta.getDisplayName().equals(getBridgeToolName())){
						isNoFallActive = true;
					}
				}
			}

			if(isNoFallActive){
				Vector v = Utils.CrearVector(evt.getFrom(), evt.getTo());
				v.multiply(1.45D);
				v.setY(0);
				Block bDown = evt.getTo().add(v).getBlock().getRelative(BlockFace.DOWN);
				if (bDown.isEmpty() && bDown.getRelative(BlockFace.DOWN).isEmpty()){
					ItemStack placeableItemStack = getPlaceableItemStack(ply);
					if (placeableItemStack != null){
						bDown.setType(placeableItemStack.getType());
						bDown.setData(placeableItemStack.getData().getData());
						ItemStack sampleIt = new ItemStack(placeableItemStack);
						sampleIt.setAmount(1);
						ply.getInventory().removeItem(sampleIt);
						itemInHand.setDurability((short) (itemInHand.getDurability() + 3));
					}
				}
			}
		}

	}
	@Override
	protected void onBlockHitByProjectile(ProjectileHitEvent evt, Block b, Projectile proj) {
		// TODO Auto-generated method stub
		super.onBlockHitByProjectile(evt, b, proj);
		//sendGlobalMessage("Pilotassa");
		Material t = b.getType();
		if(t == Material.GLASS || t == Material.STAINED_GLASS || t == Material.STAINED_GLASS_PANE || t == Material.THIN_GLASS || t == Material.GLOWSTONE){
			b.setType(Material.AIR);
			getWorld().playSound(b.getLocation(), Sound.BLOCK_GLASS_BREAK, 15F, 1.2F);
			proj.remove();
			for(BlockFace f : BlockFace.values()){
				if(GUtils.Possibilitat(58))continue;
				Block relative = b.getRelative(f);
				t = relative.getType();
				if(t == Material.GLASS || t == Material.STAINED_GLASS || t == Material.STAINED_GLASS_PANE || t == Material.THIN_GLASS || t == Material.GLOWSTONE){
					relative.setType(Material.AIR);
				}
			}
		}
	}
	@Override
	protected void onCreatureSpawn(CreatureSpawnEvent evt) {
		// TODO Auto-generated method stub
		super.onCreatureSpawn(evt);
		if(evt.getEntityType() == EntityType.GHAST){
			evt.getEntity().setMaxHealth(350);
		}
	}
	private static String getBridgeToolName() {
		return ChatColor.YELLOW + "Aixada del constructor";
	}
	public ItemStack getPlaceableItemStack(Player ply){
		for (ItemStack i : ply.getInventory()){
			if(i.getType().isSolid()){return i;}
		}
		return null;
	}
	
	public void ProgTask(){
		int tId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if (getWorld() == null){return;}
				Cuboid cub = pMapaActual().ObtenirCuboid("RegC", getWorld());

				for (Block bl : cub){
					if (bl.getType() == Material.CHEST){
						OmplirCofre(bl);
					}
				}
			}
		}, 20 * 8, 2 * 20 * 10); // *12
		handleTask(tId);
	}
	
	public void OmplirCofre(Block blk){
		Chest chest = (Chest) blk.getState();
		Inventory inv = chest.getInventory();
		inv.clear();
		//inv.clear();
		int maxN = Utils.NombreEntre(4, 9);
		int n = 0;

		while(n < maxN){
			if(Utils.Possibilitat(4)){
				inv.addItem(new ItemStack(Material.DIAMOND, 1));
			}
			if(Utils.Possibilitat(8)){
				inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
			}
			if(Utils.Possibilitat(2)){
				inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));
			}
			if(Utils.Possibilitat(12)){
				inv.addItem(new ItemStack(Material.IRON_INGOT, 1));
			}
			if(Utils.Possibilitat(5)){
				inv.addItem(new ItemStack(Material.GOLD_INGOT, 1));
			}
			if(Utils.Possibilitat(3)){
				inv.addItem(new ItemStack(Material.ENDER_PEARL, 1));
			}
			if(Utils.Possibilitat(4)){
				Material hoe = Material.WOOD_HOE;
				if(Utils.Possibilitat(35)){hoe = Material.GOLD_HOE;}
				if(Utils.Possibilitat(15)){hoe = Material.STONE_HOE;}
				if(Utils.Possibilitat(10)){hoe = Material.IRON_HOE;}
				if(Utils.Possibilitat(8)){hoe = Material.DIAMOND_HOE;}
				
				inv.addItem(Utils.setItemNameAndLore(new ItemStack(hoe, 1), getBridgeToolName(), "Col·loca automàticament els blocs formant un passadís"));
			}
//			if(Utils.Possibilitat(2)){
//				inv.addItem(new ItemStack(Material.TNT, 1));
//			}
			if(Utils.Possibilitat(3)){		
				ItemStack item = new ItemStack(Material.GOLD_LEGGINGS, 1);
				item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Utils.NombreEntre(1, 5));
				inv.addItem(item);
			}
			if(Utils.Possibilitat(3)){		
				ItemStack item = new ItemStack(Material.GOLD_CHESTPLATE, 1);
				item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Utils.NombreEntre(1, 5));
				item.addUnsafeEnchantment(Enchantment.THORNS, Utils.NombreEntre(1, 3));
				inv.addItem(item);
			}
			if(Utils.Possibilitat(3)){		
				ItemStack item = new ItemStack(Material.GOLD_BOOTS, 1);
				item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Utils.NombreEntre(1, 5));
				item.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, Utils.NombreEntre(1, 8));
				inv.addItem(item);
			}
			if(Utils.Possibilitat(3)){		
				ItemStack item = new ItemStack(Material.IRON_HELMET, 1);
				item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Utils.NombreEntre(1, 7));
				inv.addItem(item);
			}
			//Pocions
			if(Utils.Possibilitat(5)){		
				ItemStack item = new ItemStack(Material.POTION, Utils.NombreEntre(1, 4));
				Potion pot = new Potion(1); //The constructor calls for an (int name), but I'm not sure what that is... I tried 1 and it works fine.
				pot.setType(PotionType.INSTANT_HEAL);        				
				pot.setLevel(Utils.NombreEntre(1, 2));
				pot.setSplash(true);
				pot.apply(item);
				inv.addItem(item);
			}
			if(Utils.Possibilitat(4)){		
				ItemStack item = new ItemStack(Material.POTION, 1);
				Potion pot = new Potion(1); //The constructor calls for an (int name), but I'm not sure what that is... I tried 1 and it works fine.
				pot.setType(PotionType.POISON);        				
				pot.setLevel(Utils.NombreEntre(1, 2));
				pot.setSplash(true);
				pot.apply(item);
				inv.addItem(item);
			}
			if(Utils.Possibilitat(3)){		
				ItemStack item = new ItemStack(Material.POTION, 1);
				Potion pot = new Potion(1); //The constructor calls for an (int name), but I'm not sure what that is... I tried 1 and it works fine.
				pot.setType(PotionType.INSTANT_DAMAGE);        				
				pot.setLevel(2);
				pot.setSplash(true);
				pot.apply(item);
				inv.addItem(item);
			}
			if(Utils.Possibilitat(3)){		
				ItemStack item = new ItemStack(Material.POTION, 1);
				Potion pot = new Potion(1); //The constructor calls for an (int name), but I'm not sure what that is... I tried 1 and it works fine.
				pot.setType(PotionType.STRENGTH);        				
				pot.setLevel(Utils.NombreEntre(1, 2));
				pot.setSplash(true);
				pot.apply(item);
				inv.addItem(item);
			}
			n = 0;
			for(ItemStack i : inv.getContents()){
				if (i != null){
					n++;
				}
			}
		}
	}





}
