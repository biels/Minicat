package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.ObsidianDefenders.Ability.AbilityType;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;

public class ObsidianDefenders extends JocEquips {
	boolean debug = false;
	public ObsidianDefenders() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Obsidian Defenders";
	}

	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		setBlockBreakPlace(false);
		setGiveStartingItemsRespawn(false);
		donarItemsInicials();
	}

	@Override
	protected void customJocFinalitzat() {
		// TODO Auto-generated method stub

	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		equips.add(new Equip(DyeColor.RED, "vermell")); //Id 0
		equips.add(new Equip(DyeColor.BLUE, "blau")); //Id 1
		return equips;
	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		Equip e = obtenirEquip(ply);
		items.add(new ItemStack(Material.WOOD_SWORD, 1));
		//		items.add(new ItemStack(Material.DIAMOND_PICKAXE, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		//		ItemStack arc = new ItemStack(Material.BOW, 1); // A stack of diamonds
		//		arc.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		//		items.add(arc);
		items.add(new ItemStack(Material.ARROW, 1));
		//items.add(new Potion(PotionType.INSTANT_DAMAGE, 1).toItemStack(2));

		return items;
	}

	@Override
	protected void setCustomGameRules() {
		//	world.setGameRuleValue("keepInventory", "true");

	}
	@Override
	protected void onExplosionPrime(ExplosionPrimeEvent evt) {
		// TODO Auto-generated method stub
		super.onExplosionPrime(evt);
		Bukkit.broadcastMessage(ChatColor.RED + " La base ha explotat!");
	}
	@Override
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt,
			Entity pdmged, Entity pdmger) {
		// TODO Auto-generated method stub
		super.onEntityDamageByEntity(evt, pdmged, pdmger);
		Double dmg = evt.getDamage();
		//Bukkit.broadcastMessage(evt.getDamager().getClass().getName());
		if (evt.getEntity() instanceof Player && (evt.getDamager() instanceof Player || evt.getDamager() instanceof Arrow)) {    		
			Player damaged = (Player)pdmged;
			Player damager = null;
			boolean ranged = false;
			if (evt.getDamager() instanceof Player){
				damager = (Player)evt.getDamager();
			}
			if (evt.getDamager() instanceof Arrow){
				damager = (Player)((Arrow)evt.getDamager()).getShooter();
				ranged = true;
			}
			if (JocIniciat == false){
				evt.setCancelled(true);
			}
			if (damaged.getLocation().distance(pMapaActual().ObtenirLocation("base" + Integer.toString(obtenirEquip(damaged).getId()), world)) <= 7){
				evt.setCancelled(true);
				if (damager != null) {
					damager.damage(evt.getDamage(), damaged);			
					Bukkit.broadcastMessage(damager.getName() + ", no es pot atacar a l'spawn!");
				}
			}
			if (damager != null) {
				if (damager.getLocation().getBlockY() >= 50 && ranged == false){
					evt.setDamage(evt.getDamage() * 1.6 + Utils.NombreEntre(1, 11));
				}
				if (damager.getLocation().getBlockY() >= 45){
					evt.setDamage(evt.getDamage() +  Utils.NombreEntre(1, 5));
				}
			}
			evt.setDamage(evt.getDamage() * 0.8);
			//evt.setDamage((double) (evt.getDamage() +  (plyr.getLevel() / 4)));
			//			if (damager.getName().equalsIgnoreCase("biel") == true){
			//				//evt.setDamage((double) (evt.getDamage() * 2));
			//			}
			if (damager != null) {
				if(Ability.hasAbility(plugin, this, damager, AbilityType.ARQUER_PERFECTE) && evt.isCancelled() == false && ranged == true){
					int crg = pPlayer(damager).ObtenirPropietatInt("PerfectBowHitCount");
					if(crg >= 3){
						Vector vec = Utils.CrearVector(damager.getLocation(), damaged.getLocation());
						int sep = 40;
						//jj
						if (sep < 10){sep = 10;}
						ArrayList <Location> locs = Utils.getLocationsCircle(damaged.getLocation(), 1.0, 40);
						for(Location loc : locs){
							if(loc.distance(damager.getLocation()) > damaged.getLocation().distance(damager.getLocation())){
								Vector vec2 = Utils.CrearVector(damaged.getLocation(), loc).normalize();
								Arrow arrow = (Arrow)world.spawnEntity(loc, EntityType.ARROW);
								//Bukkit.broadcastMessage(Float.toString(plyr.getLocation().getYaw()));
								arrow.setShooter(damager);
								//arrow.setItem(item)
								arrow.setFireTicks(200);
								arrow.setVelocity(vec2.multiply(8));
							}
						}
						pPlayer(damager).EstablirPropietat("PerfectBowHitCount", 1);

						damager.playSound(damager.getLocation(), Sound.ENTITY_GENERIC_SWIM, 1, 0.5F);
					}else{
						pPlayer(damager).IncrementarPropietat("PerfectBowHitCount");
						if(crg == 5){
							damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						}

					}
					updateScoreBoard(damager);
				}

				if(Ability.hasAbility(plugin, this, damager, AbilityType.ARQUER_DE_GEL) && evt.isCancelled() == false && ranged == true){
					int crg = pPlayer(damager).ObtenirPropietatInt("StrongBowHitCount");
					if(crg >= 6){
						ArrayList <BlockFace> faces = new ArrayList<>();
						faces.add(BlockFace.NORTH);
						faces.add(BlockFace.SOUTH);
						faces.add(BlockFace.WEST);
						faces.add(BlockFace.EAST);
						for (BlockFace face : faces){

							Block block = damaged.getLocation().getBlock().getRelative(face);
							if (block.getType() != Material.AIR){
								continue;
							}
							block.setType(Material.ICE);
							Utils.BreakBlockLater(block, 20 * 4, false);

						}
						damaged.teleport(damaged.getLocation().getBlock().getLocation().add(new Vector(0.5,0,0.5)));
						Block gblock = damaged.getLocation().add(0, 2, 0).getBlock();
						if (gblock.getType() == Material.AIR){
							gblock.setType(Material.GOLD_BLOCK);
							Utils.BreakBlockLater(gblock, 20 * 4, false);
						}
						pPlayer(damager).EstablirPropietat("StrongBowHitCount", 1);

						damaged.playSound(damager.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 0.5F);
					}else{
						pPlayer(damager).IncrementarPropietat("StrongBowHitCount");
						if(crg == 5){
							damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						}

					}
					updateScoreBoard(damager);

				}
				if(Ability.hasAbility(plugin, this, damager, AbilityType.ESPADATXI) && evt.isCancelled() == false){
					int crg = pPlayer(damager).ObtenirPropietatInt("StrongHitCount");
					if(crg >= 5){
						evt.setDamage(evt.getDamage() * 1.5);
						Vector rawDir = damaged.getLocation().toVector().subtract(damager.getLocation().toVector());
						Vector dir = rawDir.normalize().multiply(2).add(new Vector(0,0.3,0));
						damaged.setVelocity(dir);
						pPlayer(damager).EstablirPropietat("StrongHitCount", 1);
						damaged.playSound(damager.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 0.3F);
					}else{
						pPlayer(damager).IncrementarPropietat("StrongHitCount");
						if(crg == 5){
							damaged.playSound(damager.getLocation(), Sound.ENTITY_HORSE_LAND, 1, 0.3F);
						}
					}
					updateScoreBoard(damager);
				}
				if(Ability.hasAbility(plugin, this, damaged, AbilityType.RESISTENCIA)){
					double dmgm = 0.9;
					dmgm = dmgm - (Utils.getNearbyPlayers(damaged, 10).size() * 0.08);
					if (dmgm <= 0.1){dmgm = 0.1;}
					double finaldmg = evt.getDamage() * 0.85;
					evt.setDamage(finaldmg);
					if(debug){
						Bukkit.broadcastMessage("Mal reduït: " + Double.toString(evt.getDamage() - finaldmg) + " - " + dmgm * 100 +"%");
					}
				}
				if (obtenirEquip(damaged).getId() == obtenirEquip(damager).getId()){
					evt.setCancelled(true);
				}
				//Armor
				ItemStack[] armor = ((Player) evt.getEntity()).getInventory().getArmorContents();
				for(ItemStack i:armor) {
					Material mat = i.getType();
					if (mat == Material.LEATHER_HELMET || mat == Material.CHAINMAIL_CHESTPLATE || mat == Material.CHAINMAIL_LEGGINGS || mat == Material.CHAINMAIL_BOOTS){
						i.setDurability((short) 0);
					}else{
						if(Ability.hasAbility(plugin, this, damager, AbilityType.DESTRUCTOR)){
							int morts = Integer.parseInt(pTemp().ObtenirPropietat(damager.getName() + "Morts"));
							i.setDurability((short) (i.getDurability() + 5 + morts));

						}
					}
				}
				((Player) evt.getEntity()).getInventory().setArmorContents(armor);
			} 
			if (evt.getEntity() instanceof IronGolem && evt.getDamager() instanceof Player) {
				IronGolem golem = (IronGolem)evt.getEntity();				
				@SuppressWarnings("null")
				ItemStack item = damager.getItemInHand();
				if (item.getType() == Material.IRON_PICKAXE){
					evt.setDamage(30);
					//dur
					item.setDurability((short) (item.getDurability() + (item.getType().getMaxDurability() / 4)));

				}

			}
		}
		if (evt.getEntity() instanceof Player && evt.getDamager() instanceof IronGolem) {
			IronGolem golem = (IronGolem)evt.getDamager();
			Player player = (Player)evt.getEntity();
			if(Ability.hasAbility(plugin, this, player, AbilityType.PROTECCIÓ_IMPACTE)){
				evt.setDamage(evt.getDamage() / 2);
			}
		}
		//Bukkit.broadcastMessage(evt.getDamager().getType().getName());

		evt.setDamage(dmg);

	}



	@Override
	protected void onPlayerPickupItem(PlayerPickupItemEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerPickupItem(evt, p);
		Player ply = evt.getPlayer();
		Item item = evt.getItem();
		ItemStack itemStack = item.getItemStack();
		if (itemStack.getType() == Material.DIAMOND_PICKAXE){
			Location loc = new Location(world, 661, 42, -1398);
			if (item.getLocation().distance(loc) < 1){
				int Or = 3;
				donarOr(ply, Or);
				ply.sendMessage("Has agafat el pic de diamant"+ "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
			}
		}
	}
	public void donarOr(Player plyr, int Or){
		ItemStack itemstack = new ItemStack(Material.GOLD_NUGGET, Or); // A stack of diamonds
		Utils.giveItemStack(itemstack, plyr);
		pPlayer(plyr).IncrementarPropietat("Or", Or);
		ajuntarOr(plyr);
		updateScoreBoard(plyr);
	}
	public void donarOrAEquip(ArrayList<Player> equip, int Or, Boolean dividir){
		if (dividir){Or = (int) Math.ceil(Or / equip.size());}
		for (Player p : equip){
			donarOr(p, Or);
		}
	}
	public void donarOrAEquip(ArrayList<Player> equip, int Or, Boolean dividir, String Text, Boolean broadcast){
		if (dividir){Or = (int) Math.ceil(Or / equip.size());}
		for (Player p : equip){
			donarOr(p, Or, Text, broadcast);
		}
	}
	public void donarOrATots(int Or){
		ArrayList<Player> play = getPlayers();
		for (Player p : play){
			donarOr(p, Or);
		}
	}
	public void donarOrATots(int Or, String Text , Boolean broadcast){
		ArrayList<Player> play = getPlayers();
		for (Player p : play){
			donarOr(p, Or, Text, broadcast);
		}
	}
	public void donarOr(Player plyr, int Or, String Text , Boolean broadcast){
		donarOr(plyr, Or);
		String message = Text + ChatColor.WHITE + "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")";
		if (broadcast){
			Bukkit.broadcastMessage( message);
		}else{
			plyr.sendMessage(ChatColor.GRAY + message);
		}

	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		boolean explotat = false;
		Player player = evt.getEntity();
		Location location = player.getLocation();
		//Bukkit.broadcastMessage(killer.getName());  
		//		if (killer == null){
		//			
		//			Player victim = plugin.getServer().getPlayer(plugin.pTemp.ObtenirPropietat("LastIgnitePlayerVictim"));
		//			Player kill = plugin.getServer().getPlayer(plugin.pTemp.ObtenirPropietat("LastIgnitePlayerKiller"));
		//			if (victim.getName().equals(player.getName())){
		//				killer = kill;
		//			}
		//			
		//		}
		if (killer == null){

			//if (milliseconds - millisecondsAntics <= 1000 * 5){    				   			

			//}

		}
		if (killer == null){

			Long milliseconds = Calendar.getInstance().getTimeInMillis();
			Long millisecondsAntics = Long.parseLong(pTemp().ObtenirPropietat("Explo"));
			//Bukkit.broadcastMessage("Diferència: " + Long.toString(milliseconds - millisecondsAntics));    			
			if (milliseconds - millisecondsAntics <= 1000 * 2){
				String prop = pTemp().ObtenirPropietat("ExploPlayer");
				if (prop.equals(0) == false){
					Player kill = plugin.getServer().getPlayer(prop);
					killer = kill;    		
					explotat = true;
				}    				
			}
		}  
		//finalment
		if (killer == null){
			Player lastDamager = Bukkit.getPlayer(pPlayer(player).ObtenirPropietat("LastHitBy"));
			int secs = Integer.parseInt(pPlayer(player).ObtenirPropietat("LastHitTime"));
			if (lastDamager != null){
				if(segonsTranscorreguts() - secs <= 95){
					killer = lastDamager;
				}
			}
		}
		if (killer != null){
			int mortsKiller = Integer.parseInt(pTemp().ObtenirPropietat(killer.getName() + "Morts"));
			int mortsMort = Integer.parseInt(pTemp().ObtenirPropietat(player.getName() + "Morts"));
			int Or = 5 + (mortsMort * 1);
			if (mortsMort > 4){
				Or = Or + 2;
			}
			if (mortsMort > 6){
				Or = Or + 5;
			}
			if (mortsMort > 10){
				Or = Or + 5;
			}    
			if (explotat == true){
				Or = Or + 1;
			}
			if (Or >= 25){
				Or = 25;
			}
			if (killer.getItemInHand().getType() == Material.GOLD_PICKAXE){
				Or = Or * 3;
				if (Or >= 30){
					Or = 30;
				}
			}
			if (player.getInventory().contains(Material.DIAMOND_PICKAXE) == true){
				Or = Or + 1;
			}
			if (player.getInventory().contains(Material.GOLD_BLOCK) == true){
				Or = Or + 1;
			}
			if (obtenirEquip(player).getPlayers().contains(killer)){
				Or = 0;
			} else{
				if(!explotat){
					killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 3));
				}
			}
			Inventory inventory = killer.getInventory();
			ItemStack itemstack = new ItemStack(Material.GOLD_NUGGET, Or); // A stack of diamonds
			// if (inventory.contains(itemstack)) {
			inventory.addItem(itemstack); // Adds a stack of diamonds to the player's inventory
			pPlayer(killer).IncrementarPropietat("Or", Or);

			evt.setDeathMessage(killer.getName()  + " ha matat a " + player.getName()+ "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
			if (explotat == true){
				evt.setDeathMessage(killer.getName()  + " ha fet explotar a " + player.getName()+ "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
				pTemp().EstablirPropietat(killer.getName() + "MortsExplotats", Integer.toString(Integer.parseInt(pTemp().ObtenirPropietat(killer.getName() + "MortsExplotats")) + 1));

			}
			if (killer.getItemInHand().getType() == Material.GOLD_PICKAXE){
				evt.setDeathMessage(killer.getName()  + " ha matat amb el pic d'or a " + player.getName()+ "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")(" + ChatColor.GOLD + "x3" + ChatColor.WHITE + ")");
			}
			if(Ability.hasAbility(plugin, this, player, AbilityType.CREEPER)){
				float explopower = 0.8F + (mortsMort / 2);
				world.createExplosion(location.getX(), location.getY(), location.getZ(), explopower, false, false);
			}
			pTemp().EstablirPropietat(killer.getName() + "Morts", Integer.toString(mortsKiller + 1));
			pTemp().EstablirPropietat(player.getName() + "Morts", "0");
			if(Or != 0){
				pPlayer(killer).IncrementarPropietat("Assassinats");
				pPlayer(player).IncrementarPropietat("Morts");
			}
			if (player.getInventory().contains(Material.DIAMOND_PICKAXE) == true){
				player.getInventory().remove(Material.DIAMOND_PICKAXE);
				ItemStack itemstack3 = new ItemStack(Material.GOLD_PICKAXE, 1); // A stack of diamonds
				evt.setDeathMessage(killer.getName()  + " ha matat a " + player.getName()+ " que tenia pic de diamant!(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
				killer.sendMessage(ChatColor.GOLD + "+2 Or passiu! (pic d'or)");
				killer.sendMessage(ChatColor.GOLD + "Matar un enemic amb el pic d'or et dona x3 or");
				inventory.addItem(itemstack3);
			}
			if (player.getInventory().contains(Material.GOLD_PICKAXE) == true){
				player.getInventory().remove(Material.GOLD_PICKAXE);

			}
			updateScoreBoards();
		} else {
			evt.setDeathMessage(player.getName() + " s'ha mort tot sol");
		}
	}
	@Override
	protected void onPlayerInteract(PlayerInteractEvent evt, Player plyr) {
		// TODO Auto-generated method stub
		super.onPlayerInteract(evt, plyr);
		ItemStack stack = evt.getItem();
		Inventory inv = plyr.getInventory();
		//		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK){
		//			Location LocB1 = plugin.pMapaActual.ObtenirLocation("Botiga0", world);
		//			Location LocB2 = plugin.pMapaActual.ObtenirLocation("Botiga1", world);
		//			if (LocB1.distanceSquared(evt.getClickedBlock().getLocation()) <= 1 || LocB2.distanceSquared(evt.getClickedBlock().getLocation()) <= 1){
		//				VillagerTradeOffer[] offers = GeneradorOfertes.generarOfertes(plugin);
		//				try {
		//					VillagerTrading.openTrade(plyr, offers);
		//				} catch (IOException e) {
		//					e.printStackTrace();
		//				}
		//			}
		//		}
		if (evt.getItem() != null){
			if (stack.getType() == Material.WOOD_SWORD && JocIniciat == false){
				Ability.openSelectionInventory(plugin, this, plyr);
			}
			if (stack.getType() == Material.DIAMOND_BLOCK){

				Inventory inv1 = Bukkit.getServer().createInventory(plyr, 9, "Teletransportar...");

				for (Player p : obtenirEquip(plyr).getPlayers()){
					if (p.getName().equals(plyr.getName())){
						continue;
					}
					if(p.isDead() || !p.isOnline()){
						continue;
					}
					ItemStack steveItem = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
					inv1.addItem(Utils.setItemName(steveItem, p.getName()));
				}




				plyr.openInventory(inv1);
			}
			if (stack.getType() == Material.NETHER_STAR){
				for(Player p : obtenirEquipEnemic(plyr).getPlayers()){
					p.setHealth(1);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 4, false), true);
				}
				for(Player p : obtenirEquip(plyr).getPlayers()){    				
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 3, false), true);
				}
				Bukkit.broadcastMessage(ChatColor.GREEN + plyr.getName() + ChatColor.WHITE + " ha utilitzat una" + ChatColor.BOLD + " nether star" + ChatColor.RESET +"!");
				evt.getItem().setType(Material.FIREWORK_CHARGE);
			}
			if (stack.getType() == Material.ARROW){
				if (stack.getEnchantments().size() >= 1){
					for(Player p : obtenirEquipEnemic(plyr).getPlayers()){
						p.setHealth(p.getHealth() - 3);        				
					}
					plyr.sendMessage("-1 cor a tot l'equip enemic.");
					inv.removeItem(new ItemStack(stack.getType()));
				} 

			}
			if (stack.getType() == Material.MAGMA_CREAM){
				for(Player p : obtenirEquipEnemic(plyr).getPlayers()){
					p.setFireTicks(3 * 20);        				
				}
				plyr.sendMessage("Has cremat a l'equip enemic.");        			
				inv.removeItem(new ItemStack(stack.getType()));
			}
			if (stack.getType() == Material.STRING){
				for(Player p : obtenirEquipEnemic(plyr).getPlayers()){
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15 * 20, 2, false), true);        				
				}
				plyr.sendMessage("Has alentit a l'equip enemic un 40% durant 15 segons.");
				inv.removeItem(new ItemStack(stack.getType()));
			}
			if (evt.getItem().getType() == Material.SPIDER_EYE){
				for(Player p : obtenirEquipEnemic(plyr).getPlayers()){
					p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 8 * 20, 1, false), true);        				
				}
				plyr.sendMessage("Has enverinat a l'equip enemic durant 8 segons.");
				inv.removeItem(new ItemStack(stack.getType()));
			}
			if (evt.getItem().getType() == Material.PAPER){
				for(Player p : obtenirEquipEnemic(plyr).getPlayers()){
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 25 * 20, 5, false), true);        				
				}
				plyr.sendMessage("Has esverat a l'equip enemic durant 25 segons.");
				inv.removeItem(new ItemStack(stack.getType()));
			}
			if (evt.getItem().getType() == Material.COCOA){
				for(Player p : obtenirEquip(plyr).getPlayers()){
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6 * 20, 100, false), true);        				
				}
				plyr.sendMessage("Tots a l'aigua!");
				inv.removeItem(new ItemStack(stack.getType()));
			}
			if (evt.getItem().getType() == Material.EMERALD){
				for(Player p : obtenirEquip(plyr).getPlayers()){
					double newHealth = p.getHealth() + 3;
					if (newHealth >= 20){
						newHealth = 20;
					}
					p.setHealth(newHealth);        				
				}
				plyr.sendMessage("Has curat 1 cor al teu equip.");
				inv.removeItem(new ItemStack(stack.getType()));
			}
			if (evt.getItem().getType() == Material.SUGAR){
				for(Player p : obtenirEquip(plyr).getPlayers()){
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2, false), true);				
				}
				plyr.sendMessage("Has augmentat la velocitat del teu equip durant 15 segons.");

				inv.removeItem(new ItemStack(stack.getType()));

			}
			if (stack.getType() == Material.GLASS){
				for(Player p : obtenirEquip(plyr).getPlayers()){
					p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 1, false), true);				
				}
				plyr.sendMessage("El teu equip és invisible durant 15 segons.");
				inv.removeItem(new ItemStack(stack.getType()));
			}
			if (stack.getType() == Material.BLAZE_POWDER){    				
				Player pObj = null;
				for(Entity e : plyr.getNearbyEntities(25, 40, 25)){
					if (e instanceof Player){
						Player p = (Player)e;
						if (obtenirEquip(p).equals(obtenirEquip(plyr)) == false){
							if (pObj == null){
								pObj = p;
							}else{
								if (plyr.getLocation().distance(p.getLocation()) < plyr.getLocation().distance(pObj.getLocation())){
									if (p.getFireTicks() == 0){
										pObj = p;
									}
								}
							}

						}
					}
				}
				if (pObj != null){
					int kills = Integer.parseInt(pTemp().ObtenirPropietat(plyr.getName() + "Morts"));
					pObj.setFireTicks((5 + kills + (plyr.getLevel() / 2)) * 20); 
					inv.removeItem(new ItemStack(stack.getType()));
					pTemp().EstablirPropietat("LastIgnitePlayerVictim", pObj.getName());
					pTemp().EstablirPropietat("LastIgnitePlayerKiller", plyr.getName());
				}else{
					plyr.sendMessage(ChatColor.GRAY + "No hi ha cap enemic a prop!");
				}
			}
			if (stack.getType() == Material.SLIME_BALL){    
				//				Location base = plugin.pMapaActual.ObtenirLocation("Slime" + plugin.EquipNum(plugin.ObtenirEquip(plyr)) ,world);
				//				base.setY(base.getY() + 2);
				//				//Skeleton skeleton = (Skeleton)world.spawnEntity(base, EntityType.SKELETON);
				//				//ItemStack bow = new ItemStack(Material.BOW);
				//				
				//		      
				//				Zombie slime = (Zombie)world.spawnEntity(base, EntityType.ZOMBIE);		
				//				slime.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400 * 20, 4, false), true);
				//				slime.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400 * 20, 1, false), true);
				//				slime.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400 * 20, 1, false), true);
				//				slime.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 1, false), true);
				//				slime.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 400 * 20, 1, false), true);
				//				
				//				//ControllableMob<Zombie> mob = ControllableMobs.assign(slime);
				//				//mob.getActions()
				//				Bukkit.broadcastMessage(ChatColor.GREEN + plyr.getName() + ChatColor.WHITE + " ha fet apareixer un assasí!");
				//				inv.removeItem(new ItemStack(stack.getType(), 1));
			}
		}    		

	}
	public void ajuntarOr(Player p){
		int preuOr = 10;
		Inventory inv = p.getInventory();    	
		for (ItemStack d : inv.getContents()) {  // d gets successively each value in ar.    		
			if (d == null){
				continue;
			}

			if (d.getType() == Material.GOLD_NUGGET){	    			

				if (d.getAmount() >= 10){    					
					int lingots = d.getAmount()/10;
					int nuggElim = lingots * 10;    					    					
					inv.addItem(new ItemStack(Material.GOLD_INGOT, lingots ));
					//inv.addItem(new ItemStack(Material.GOLD_NUGGET, nugg ));
					inv.removeItem(new ItemStack(Material.GOLD_NUGGET, nuggElim ));
				}

			}
		} 
	}
	public static class Ability {

		public Ability() {
			// TODO Auto-generated constructor stub
		}
		public enum AbilityType{
			RESISTENCIA,
			COMANDANT,
			ESPADATXI,
			REGENERACIO_AUGMENTADA,
			ASSALT,
			CREEPER,
			ARQUER_PERFECTE,
			ARQUER_DE_GEL,
			PROTECCIÓ_IMPACTE,
			PIROTÈCNIC,
			ESQUELET_FORT,
			CONTROL_GRAVETAT,
			DESTRUCTOR,
			RANDOM
		}
		static ItemStack Icona(lobby plugin, ObsidianDefenders j, Player plyr, AbilityType habilitat){
			Material mat = Material.WOOD;
			String Titol = "<Nom>";
			String Desc = "<Descripció>";
			String Desc2 = "<Descripció2>";
			boolean disp = false;
			//-----------
			switch(habilitat){
			case ESPADATXI:
				mat = Material.IRON_SWORD;
				Titol = "Espadatxí";
				Desc = "Augmenta l'atac cada 6 cops d'espasa";
				Desc2 = "i els enemics volen pels aires";
				disp = true;
				break;
			case REGENERACIO_AUGMENTADA:
				mat = Material.SAPLING;
				Titol = "Regeneració augmentada";
				Desc = "x3 Regeneració passiva x2 cost de menjar";
				disp = true;
				break;
			case RESISTENCIA:
				mat = Material.DIAMOND;
				Titol = "Resistència";
				Desc = "Redueix el mal d'enemics un";
				Desc2 ="10% + 8% per enemic proper (8 blocs)";	
				disp = true;
				break;
			case ARQUER_PERFECTE:
				mat = Material.BOW;
				Titol = "Arquer perfecte";
				Desc = "Cada 10 fletxes encertades,";
				Desc2 = "la fletxa rebota (8 blocs)";
				disp = true;
				break;
			case ASSALT:
				mat = Material.LEATHER_BOOTS;
				Titol = "Assalt";
				Desc = "El mal per caiguda es transfereix";
				Desc2 =	 "als enemics propers (7 blocs)";
				disp = true;
				break;
			case ESQUELET_FORT:
				mat = Material.BONE;
				Titol = "Esquelet fort";
				Desc = "- 3 mal per caiguda";
				disp = true;
				break;
			case PIROTÈCNIC:
				mat = Material.FIREWORK;
				Titol = "Pirotècnic";
				Desc = "+ 35 % força explosions";
				disp = true;
				break;
			case PROTECCIÓ_IMPACTE:
				mat = Material.IRON_CHESTPLATE;
				Titol = "Protecció d'impacte";
				Desc = "-50% mal rebut del golem de ferro";
				disp = true;
				break;
			case CONTROL_GRAVETAT:
				mat = Material.GOLD_BOOTS;
				Titol = "Control de la gravetat";
				Desc = "Duplica el mal per caiguda dels";
				Desc2 =	"enemics atacats recentment (10s)";
				disp = true;
				break;
			case RANDOM:
				mat = Material.BEDROCK;
				Titol = "Inmortalitat";
				Desc = "Ets inmortal i guanyes";
				Desc2 =	"la partida en 5s ;) jaja";
				disp = false;
				break;
			case ARQUER_DE_GEL:
				mat = Material.ICE;
				Titol = "Arquer de gel";
				Desc = "Congela l'enemic que encertis";
				Desc2 =	"cada 7 fletxes";
				disp = true;
				break;

			case DESTRUCTOR:
				mat = Material.DIAMOND_AXE;
				Titol = "Destructor";
				Desc = "Dismnueix la durabilitat de les";
				Desc2 =	"armadures de l'enemic (5 + morts)";
				disp = true;
				break;
			case COMANDANT:
				mat = Material.COMMAND;
				Titol = "Comandant";
				Desc = "Augmenta el mal dels aliats propers un";
				Desc2 =	"12% i comença amb items addicionals";
				disp = false;
				break;
			case CREEPER:
				mat = Material.TNT;
				Titol = "Creeper";
				Desc = "Explotes al morir(0.8F). La força augmenta";
				Desc2 =	"0.5F per cada enemic que hagis matat.";
				disp = true;
				break;
			default:
				break;

			}
			//-----------
			ItemStack item = new ItemStack(mat); 
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + Titol);
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.WHITE + Desc);
			if (!Desc2.equals("<Descripció2>")){
				lore.add(ChatColor.WHITE + Desc2);
			}
			if (hasAbility(plugin, j, plyr, habilitat)){
				lore.add(ChatColor.YELLOW + "Seleccionat!");
				item.setAmount(2);
			}
			if (!disp){
				lore.add(ChatColor.DARK_RED + "No funciona");
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;

		}
		public static void openSelectionInventory(lobby plugin, ObsidianDefenders j, Player plyr){
			World world = Bukkit.getServer().getWorlds().get(0);
			Inventory inv = Bukkit.getServer().createInventory(plyr, 9*2, "Selecciona habilitat");
			int i = 0;
			for (AbilityType mill : AbilityType.values()){
				inv.setItem(i, Icona(plugin, j, plyr, mill));
				i++;
			}
			plyr.openInventory(inv);
		}
		public static boolean hasAbility(lobby plugin, ObsidianDefenders j, Player plyr, AbilityType ab){
			return getPlayerAbilityTypes(plugin, j, plyr).contains(ab);
		}
		static ArrayList<AbilityType> getPlayerAbilityTypes(lobby plugin, ObsidianDefenders j, Player plyr){
			ArrayList<AbilityType> lore = new ArrayList<>();
			try {
				lore.add(AbilityType.valueOf(j.pPlayer(plyr).ObtenirPropietat("Habilitat1")));
				lore.add(AbilityType.valueOf(j.pPlayer(plyr).ObtenirPropietat("Habilitat2")));
			} catch (Exception e) {
				randomAbilities(plugin, j, plyr);
				return getPlayerAbilityTypes(plugin, j, plyr);
			}
			return lore;
		}
		public static void randomAbilities(lobby plugin, ObsidianDefenders j, Player plyr){
			int i = 1;
			while(i <= 2){
				boolean fet = false;
				while(fet == false){
					for(AbilityType ab: AbilityType.values()){
						if(Utils.Possibilitat(10)){
							setAbility(plugin, j, plyr, ab, i);
							fet = true;
						}
					}
				}
				i++;
			}

		}
		public static void setAbility(lobby plugin, ObsidianDefenders j, Player plyr, AbilityType ab, int id){
			j.pPlayer(plyr).EstablirPropietat("Habilitat" + Integer.toString(id), ab.name());
		}
		public static void giveSelectors(lobby plugin, Player plyr){
			int i = 1;
			while(i <= 2){
				ItemStack item = new ItemStack(Material.WOOD_SWORD);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("Habilitat " + Integer.toString(i));
				ArrayList<String> lore = new ArrayList<>();
				lore.add(Integer.toString(i));
				meta.setLore(lore);
				item.setItemMeta(meta);
				plyr.getInventory().setItem(4 + i, item);
				i++;
			}
		}
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		// TODO Auto-generated method stub
		super.updateScoreBoard(ply);
		if (JocIniciat){
			ArrayList<String> list = new ArrayList<>();
			list.add(ChatColor.GREEN + "Kills: " + pPlayer(ply).ObtenirPropietatInt("Assassinats"));
			list.add(ChatColor.RED + "Morts: " + pPlayer(ply).ObtenirPropietatInt("Morts"));
			list.add(ChatColor.GOLD + "Or: " + pPlayer(ply).ObtenirPropietatInt("Or"));
			if(Ability.hasAbility(plugin, this, ply, AbilityType.ESPADATXI)){
				list.add(ChatColor.BLUE + "Espadatxí: " + pPlayer(ply).ObtenirPropietatInt("StrongHitCount"));
			}
			if(Ability.hasAbility(plugin, this, ply, AbilityType.ARQUER_DE_GEL)){
				list.add(ChatColor.BLUE + "Arquer de gel: " + pPlayer(ply).ObtenirPropietatInt("StrongBowHitCount"));
			}
			ScoreBoardUpdater.setScoreBoard(ply, "Estadístiques", list, null);
		}
	}
	@Override
	protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
		// TODO Auto-generated method stub
		super.onProjectileHit(evt, proj);
		LivingEntity shooter = (LivingEntity) proj.getShooter();
		Entity entity = evt.getEntity();
		Location loc = entity.getLocation();
		if (evt.getEntityType() == EntityType.SNOWBALL){
			if (shooter instanceof Player) {
				Player ply = (Player) shooter;
				Bukkit.broadcastMessage("Un inutil ("+ ply.getName() +") ha tirat una bola de neu!");
			}

		}

		if (evt.getEntityType() == EntityType.ARROW){
			if (shooter instanceof Player) {
				Player player = (Player)shooter;

				boolean spawnProt = false;
				if ((entity.getLocation().distance((pMapaActual().ObtenirLocation("base" + Integer.toString(0), world))) <= 5) || (entity.getLocation().distance((pMapaActual().ObtenirLocation("base" + Integer.toString(1), world))) <= 5)){
					spawnProt = true;
				}
				if (player.getLocation().getBlockY() >= 49 && entity.getTicksLived() > 5 && !spawnProt){
					Inventory inv = player.getInventory();
					if (inv.contains(Material.FIREWORK_CHARGE) == true){
						pTemp().EstablirPropietat("Explo", Long.toString(Calendar.getInstance().getTimeInMillis()));
						pTemp().EstablirPropietat("ExploPlayer", player.getName());
						ItemStack item = new ItemStack(Material.FIREWORK_CHARGE, 1);
						inv.removeItem(item);
						int mortsExplotats = Integer.parseInt(pTemp().ObtenirPropietat(player.getName() + "MortsExplotats"));
						int morts = Integer.parseInt(pTemp().ObtenirPropietat(player.getName() + "Morts"));

						float explo = 3.25F;
						explo = explo + (0.12F * mortsExplotats);
						explo = explo + (0.28F  * morts);
						float mult = ((float)player.getHealth()) / ((float)player.getMaxHealth());
						explo = explo * mult;
						if (inv.contains(Material.NETHER_STAR) == true){
							explo = explo + 1F;
							explo = explo + (explo * 1.012F);
						}
						if (inv.contains(Material.GOLD_SWORD) == true){
							explo = explo + (explo * 0.20F);
						}
						if(Ability.hasAbility(plugin, this, player, AbilityType.PIROTÈCNIC)){
							explo = explo + (explo * 0.35F);
						}
						world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), explo, false, false);
						if (pTemp().ObtenirPropietat("ForçaExplo").equals(Float.toString(explo)) == false){
							player.sendMessage("Força de les fletxes explosives: " + Float.toString(explo));
							pTemp().EstablirPropietat("ForçaExplo", Float.toString(explo));
						}
						ItemStack itemstack = new ItemStack(Material.GOLD_NUGGET, 1); // A stack of diamonds
						world.dropItem(loc, itemstack).setVelocity(new Vector(0,0,0));
						//Automal
						double hp = player.getHealth();
						if (hp == 20){
							player.sendMessage(ChatColor.GRAY + "Disparar fletxes explosives et treu 1 cor per explosió fins a mig cor. La força de les fletxes explosives varia amb la teva vida (20 hp - 100%, 1 hp 5%");
						}
						hp = hp - 2;
						if (hp <= 0){
							hp = 1;
						}
						player.setHealth(hp);



					}

					entity.remove();
				}
			}

		}


	}


}
