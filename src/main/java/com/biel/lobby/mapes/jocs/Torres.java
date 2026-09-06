package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameRules;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.Turret;
import com.biel.lobby.utilities.Turret.TipusMillora;
import com.biel.lobby.utilities.PaperMessages;
import com.biel.lobby.utilities.Utils;

public class Torres extends JocEquips {
	private static final double GENERAL_DAMAGE_MULTIPLIER = 0.6;
	/** Respawn stun: this many seconds at the start, one more per minute played, capped. A lost fight has to cost a push. */
	private static final int RESPAWN_STUN_BASE_SECONDS = 5;
	private static final int RESPAWN_STUN_MAX_SECONDS = 20;
	/** Siege phase: from this second, turrets lose their shields and wear down until only players defend. Map property SiegeStart overrides. */
	private static final int SIEGE_START_DEFAULT_SECONDS = 8 * 60;
	private static final int SIEGE_DECAY_INTERVAL_SECONDS = 30;
	private static final int SIEGE_DECAY_HP = 5;
	/** Kit tiers by point total: kills give 3, beacons 10. One tier every two kills keeps the kit moving in a 15 minute match. */
	private static final int KIT_TIER_IRON_HELMET = 6;
	private static final int KIT_TIER_DIAMOND_CHESTPLATE = 12;
	private static final int KIT_TIER_DIAMOND_BOOTS = 18;
	private static final int KIT_TIER_DIAMOND_SWORD = 24;
	private static final int KIT_TIER_TURRET = 30;
	/** Bonus pickups reappear this fast. Resistance II lasts 38 s, so the protection pot must not respawn faster than the buff wears off. */
	private static final int BONUS_RESPAWN_TICKS = 40 * 20;
	private static final int PROTECTION_BONUS_RESPAWN_TICKS = 90 * 20;
	boolean debug = false;
	private boolean siegeStarted = false;
	private int secondsUntilSiegeDecay = SIEGE_DECAY_INTERVAL_SECONDS;
	public ArrayList<Turret> Turrets = new ArrayList<>();
	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		equips.add(new Equip(DyeColor.RED, "vermell")); //Id 0
		equips.add(new Equip(DyeColor.BLUE, "blau")); //Id 1
		return equips;
	}


	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub
		world.setGameRule(GameRules.BLOCK_DROPS, false);
	}
	@Override
	protected void donarEfectesInicials(Player ply) {
		// TODO Auto-generated method stub
		super.donarEfectesInicials(ply);
		int stunSeconds = Math.min(RESPAWN_STUN_MAX_SECONDS, RESPAWN_STUN_BASE_SECONDS + segonsTranscorreguts() / 60);
		int d = 20 * stunSeconds;
		ply.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 4 * 20 + d, 4, false), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, d + 5 * 20, 2, false), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, d , 50, false), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, d, 5, false), true);
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Torres";
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		Equip e = obtenirEquip(ply);
		PlayerInfo i = getPlayerInfo(ply);
		int v = i.getValue();
		items.add(new ItemStack((v >= KIT_TIER_DIAMOND_SWORD ? Material.DIAMOND_SWORD : Material.IRON_SWORD), 1));
		if(v >= KIT_TIER_TURRET)items.add(getTurretItem());
		items.add((v >= KIT_TIER_DIAMOND_CHESTPLATE ? new ItemStack(Material.DIAMOND_CHESTPLATE, 1) : Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e)));
		items.add((v >= KIT_TIER_IRON_HELMET ? new ItemStack(Material.IRON_HELMET, 1) : Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e)));
		items.add((v >= KIT_TIER_DIAMOND_BOOTS ? new ItemStack(Material.DIAMOND_BOOTS, 1) : Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e)));

		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		ItemStack arc = new ItemStack(Material.BOW, 1); // A stack of diamonds
		arc.addEnchantment(Enchantment.PUNCH, 1);
		arc.addEnchantment(Enchantment.INFINITY, 1);
		arc.addUnsafeEnchantment(Enchantment.UNBREAKING, 10);
		ItemStack itemstack = new ItemStack(Material.ENCHANTED_BOOK, 1); // A stack of diamonds
		ItemMeta meta = itemstack.getItemMeta();
		meta.setDisplayName(ChatColor.BLUE + "Control remot");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.WHITE + "Obre l'inventari de millores");
		meta.setLore(lore);
		itemstack.setItemMeta(meta);
		items.add(itemstack);
		items.add(arc);
		items.add(new ItemStack(Material.ARROW, 1));
		items.add(new ItemStack(Material.FLINT_AND_STEEL, 1));
		return items;
	}
	@Override
	protected boolean isRecallEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	protected void comprovarGuanyador(){
		for(Equip e : Equips){
			boolean alive = false;
			for(Location inh : getInhibitors(e)){
				if(inh.getBlock().getType() == Material.BEACON)alive = true;
			}
			if(!alive)winGame(obtenirEquipEnemic(e));
		}
	}
	boolean getTNTEnabled(){
		boolean b = true;
		if(pMapaActual().ExisteixPropietat("TNTEnabled")){
			b = pMapaActual().ObtenirPropietat("TNTEnabled").equalsIgnoreCase("true");
		}
		return b;
	}

	int getSiegeStartSeconds(){
		if(pMapaActual().ExisteixPropietat("SiegeStart")){
			return pMapaActual().ObtenirPropietatInt("SiegeStart");
		}
		return SIEGE_START_DEFAULT_SECONDS;
	}
	public ArrayList<Location> getInhibitors(Equip e) {
		return pMapaActual().ObtenirLocations("inhibitors" + e.getId(), getWorld());
	}
	public enum TurretPreset{OUTER, INNER, INHIBITOR, NEXUS, LASER}
	public TurretPreset getTurretPreset(int id){
		String[] presets = pMapaActual().ObtenirLlista("Presets");
		if(id < presets.length){
			TurretPreset parsed = TurretPreset.valueOf(presets[id]);
			if(parsed != null)return parsed;
		}
		return TurretPreset.OUTER;
	}
	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		super.customJocIniciat();

		int eq = 0;
		while(eq <= 1){
			String eqStr =  Integer.toString(eq);

			//EnderCrystal cry = (EnderCrystal) world.spawnEntity(pMapaActual().ObtenirLocation("crystal" + eqStr, world).subtract(new Vector(0.5,0,0.5)), EntityType.END_CRYSTAL);
			//cry.setMetadata("Equip", new FixedMetadataValue(Com.getPlugin(), eq));
			int tId = 0;
			ArrayList<Location> obtenirLocations = pMapaActual().ObtenirLocations("torres" + eqStr, world);
			while(tId < obtenirLocations.size()){
				TurretPreset preset = getTurretPreset(tId);
				Turret turr = Turret.createTurret(Com.getPlugin(), obtenirLocations.get(tId), null, this, obtenirEquip(eq), false, false);
				
				turr.Build();
				turr.xp = 200;
				turr.hasInventory = false;
				if (preset == TurretPreset.INHIBITOR || preset == TurretPreset.NEXUS){ //0, 2
					turr.maxHpEscut = 10 + (preset == TurretPreset.NEXUS ? 30 : 0);
					turr.getByTipus(TipusMillora.RESISTÈNCIA).lvl = 1;
					turr.tempsEscut = 8;
					turr.VelAtac = 10;
					turr.resetArmorCD();
				}
				if (preset == TurretPreset.LASER){ //0, 2
					turr.getByTipus(TipusMillora.FOC).lvl = 1;
					turr.hp = 900000;
					turr.permanent = true;
					turr.distAtac = turr.distAtac + 5;
					turr.Atac = 12;
				}

//				if (tId == 1){
//					turr.xp = 250;
//					turr.distAtac = turr.distAtac + 3;
//					turr.hp = turr.hp + 10;
//				}
//				if (tId == 3 || tId == 4){
//					turr.Atac = turr.Atac + 4;
//					turr.VelAtac = turr.VelAtac - 1;
//				}
//				if (tId == 5 || tId == 6){
//					turr.Atac = turr.Atac + 1;
//					turr.VelAtac = turr.VelAtac - 5;
//				}
				turr.distAtac = turr.distAtac + 4;
				turr.Attack();

				tId = tId + 1;
			}

			eq = eq + 1;
		}
		GenerarBonus("BonusDmg", TipusBonus.DAMAGE);
		GenerarBonus("BonusVel", TipusBonus.SPEED);
		GenerarBonus("BonusHeal", TipusBonus.HEAL);
		GenerarBonus("BonusProtect", TipusBonus.PROTECTION);
		GenerarBonus("BonusJump", TipusBonus.JUMP);
		for (Player p : getPlayers()){
			Turret turr = Turret.createTurret(Com.getPlugin(), p.getLocation(), p, this, obtenirEquip(p), false, true);
			turr.xp = 20;
		}
		sendGlobalMessage(ChatColor.GOLD + "El setge començarà al minut " + (getSiegeStartSeconds() / 60)
				+ ChatColor.GRAY + ": les torres perdran l'escut i es desgastaran fins caure.");
		//Bukkit.broadcastMessage("Millora les torres de la base. La batalla començarà d'aquí a 20 segons.");
		//		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		//          public void run() {
		//          	//TeletransportarTothom();
		//          	Bukkit.broadcastMessage("La batalla ha començat!");
		//          }
		//      }, 22 * 20);

	}
	public enum TipusBonus {HEAL, SPEED, DAMAGE, PROTECTION, JUMP};
	public void GenerarBonus(String pArr, TipusBonus tipus){
		ArrayList<Location> bonus = pMapaActual().ObtenirLocations(pArr ,world);
		int respawnTicks = tipus == TipusBonus.PROTECTION ? PROTECTION_BONUS_RESPAWN_TICKS : BONUS_RESPAWN_TICKS;
		for(Location loc : bonus){
			new Bonus(Com.getPlugin(), tipus, loc, 0, respawnTicks);
		}
	}

	//-- EVENT CHANNELING
	@Override
	public void heartbeat() {
		// TODO Auto-generated method stub
		super.heartbeat();
		ArrayList<Turret> toRemove = new ArrayList<>();
		for(Turret t : Turrets){
			if(t.destroyed){
				toRemove.add(t);
			}
		}
		Turrets.removeAll(toRemove);
		if(JocIniciat)tickSiege();
	}
	/** Runs once a second after the start. Announces the siege and then wears every standing turret down on a fixed cadence. */
	private void tickSiege(){
		if(!siegeStarted){
			if(segonsTranscorreguts() < getSiegeStartSeconds())return;
			siegeStarted = true;
			for(Player p : getPlayers()){
				PaperMessages.showTitle(p, 10, 60, 20, ChatColor.RED + "" + ChatColor.BOLD + "SETGE",
						ChatColor.GRAY + "Les torres perden l'escut i es desgasten");
			}
			world.playSound(getHalfwayMiddle(), Sound.ENTITY_WITHER_SPAWN, 20F, 0.8F);
			secondsUntilSiegeDecay = 1; // Shields drop with the announcement; wear follows on the cadence
		}
		secondsUntilSiegeDecay--;
		if(secondsUntilSiegeDecay > 0)return;
		secondsUntilSiegeDecay = SIEGE_DECAY_INTERVAL_SECONDS;
		for(Turret t : new ArrayList<>(Turrets)){
			if(!t.built || t.permanent)continue;
			t.disableShield();
			t.decay(SIEGE_DECAY_HP);
		}
	}
	@Override
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt,
			Entity damaged, Entity damager) {
		// TODO Auto-generated method stub
		super.onEntityDamageByEntity(evt, damaged, damager);
		Turret shootingTurret = Turret.fromArrow(this, damager);
		if (shootingTurret != null) {
			// A turret's arrow deals its Atac stat, not the vanilla arrow damage that ignored every upgrade.
			evt.setDamage(shootingTurret.Atac * GENERAL_DAMAGE_MULTIPLIER);
			int magnetism = shootingTurret.getByTipus(TipusMillora.MAGNETISME).lvl;
			if (magnetism > 0 && damaged instanceof LivingEntity pulled) {
				pulled.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * magnetism, 0), true);
				Vector towardTurret = shootingTurret.getLocation().add(0.5, 0, 0.5).toVector().subtract(pulled.getLocation().toVector()).setY(0);
				if (towardTurret.lengthSquared() > 0) {
					Vector pull = towardTurret.normalize().multiply(0.35 * magnetism).setY(0.25);
					scheduleGameplayTask(() -> pulled.setVelocity(pull), 1); // After the hit's own knockback
				}
			}
		}

		if (evt.getEntityType() == EntityType.END_CRYSTAL){

			final EnderCrystal cry = (EnderCrystal) evt.getEntity();
			int preequip = 0;
			List<MetadataValue> values = cry.getMetadata("Equip");  
			for(MetadataValue value : values){
				if(value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())){
					preequip = value.asInt();

				}
			}
			final int equip = preequip;
			if (evt.getDamager() instanceof Player){
				Player ply = (Player) evt.getDamager();


				if (obtenirEquip(equip).getPlayers().contains(ply) == true){
					evt.setCancelled(true);

				}
			}


			if (evt.getDamager() instanceof Arrow){
				Arrow arrow = (Arrow)evt.getDamager();
				LivingEntity shot = (LivingEntity) arrow.getShooter();
				//shot.damage(15);
				evt.setCancelled(true);

			}

			if (evt.isCancelled() == false){
				for (Location loc : pMapaActual().ObtenirLocations("inhibitors" + Integer.toString(equip), world)){
					if (loc.getBlock().getType() == Material.BEACON){
						evt.setCancelled(true);
						if (evt.getDamager() instanceof Player){
							Player ply = (Player) evt.getDamager();
							ply.sendMessage("Destrueix tots els punts de control primer!");
						}

					}
				}
			}

			if (evt.isCancelled() == false){
				ArrayList<Player> online = getPlayers();
				ArrayList<Location> allLocs = Utils.getLocationsCircle(cry.getLocation(), 2.2, 10);
				ArrayList<Location> locs = new ArrayList<>();
				for (Location loc : allLocs){
					if (loc.getBlock().getType() == Material.AIR){
						locs.add(loc);
					}
				}
				for (Player ply : online){
					Location locRandom = locs.get(Utils.NombreEntre(0, locs.size() - 1));
					Location locFinal = Utils.lookAt(locRandom, cry.getLocation());
					ply.teleport(locFinal);
					if(obtenirEquip(ply).getId() == equip){
						ply.setGameMode(GameMode.SURVIVAL);
					}else{
						ply.setGameMode(GameMode.CREATIVE);
					}
				}
				world.playSound(cry.getLocation(), Sound.AMBIENT_CAVE, 30F, 3F);
				scheduleGameplayTask(() -> {
                    for(Player p : plugin.getServer().getOnlinePlayers()){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 1, false), true);
                    }
                }, 1 * 20);
				scheduleGameplayTask(() -> {
                    for(Player p : plugin.getServer().getOnlinePlayers()){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4 * 20, 3, false), true);
                    }
                }, 25);
				scheduleGameplayTask(() -> {
                    for(Player p : plugin.getServer().getOnlinePlayers()){
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * 20, 6, false), true);
                    }
                }, 30);
				scheduleGameplayTask(() -> {
                    for(Player p : plugin.getServer().getOnlinePlayers()){
                        if(obtenirEquip(p).getId() == equip){
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "Has perdut!");
                        }else{
                            p.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "Has guanyat!");
                        }
                        world.createExplosion(cry.getLocation(), 18F);
                        cry.remove();

                    }
                }, 35);

			}

			evt.setCancelled(true);

		}

	}
	void givePoints(Player ply, int amount){
		PlayerInfo i = getPlayerInfo(ply);
		int newVal = i.getValue() + amount;
		//GUtils.testPointUpDown(i.getValue(), i.getValue(), newVal);
		i.setValue(newVal);
		ply.setLevel(i.getValue());
	}
	@Override
	protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerInteract(evt, p);
		ItemStack stack = evt.getItem();
		Inventory inv = p.getInventory();
		final Player plyr = evt.getPlayer();
		if(stack != null){
			if (stack.getType() == Material.ENCHANTED_BOOK){
				Turret turr = Turret.getAdmin(this, plyr);
				if (turr == null){
					sendGlobalMessage("Torre: null");
				}
				if (turr != null) {
					turr.openOrRefreshInventory(plyr);
				}
			}
			if (stack.getType() == Material.DIAMOND_AXE){
				final Vector dir = plyr.getLocation().getDirection();
				int i1 = 0;
				int shoots = 5;
				int temps = 5;
				while (i1 < shoots){
					scheduleGameplayTask(() -> {
                        int i = -3;
                        while (i <= 3){
                            float angle = plyr.getLocation().getYaw() + (7 * i) + 90;
                            double toRadians = Math.PI / 180;
                            //Location locSpawn = plyr.getLocation().add(0,1,0);
                            Location spawnpoint = plyr.getLocation().add(0,1.05,0).add(new Location(world,Math.cos(angle * toRadians), 0, Math.sin(angle * toRadians)));

                            Vector dir2 = spawnpoint.toVector().subtract(plyr.getLocation().add(0,1,0).toVector()).normalize().multiply(0.5);
                            Arrow arrow = (Arrow)world.spawnEntity(spawnpoint, EntityType.ARROW);
                            //Bukkit.broadcastMessage(Float.toString(plyr.getLocation().getYaw()));
                            arrow.setShooter(plyr);
                            arrow.setFireTicks(200);
                            arrow.setVelocity(dir2.multiply(8));
                            i= i + 1;
                            world.playSound(spawnpoint, Sound.BLOCK_GLASS_BREAK, 1, 1F);
                        }
                    },temps * i1);
					i1 = i1 + 1;
					stack.setDurability((short) (stack.getDurability() + 35));
				}

				if (stack.getDurability() >= stack.getType().getMaxDurability()){
					inv.removeItem(stack);
				}
				evt.setCancelled(true);

			}
		}

		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK){
			Location clickBlock = evt.getClickedBlock().getLocation().add(new Location(world,0,1,0));
			Block block = evt.getClickedBlock();

			if (stack != null) {
				if (stack.getType() == Material.ARROW) {
					if (stack.getEnchantments().size() >= 1
							|| debug == true) {
						if (block.getPistonMoveReaction() == PistonMoveReaction.BREAK) {
							block.setType(Material.AIR);
							block = block.getLocation().clone()
									.add(new Vector(0, -1, 0)).getBlock();
						}
						Turret turr = Turret.createTurret(plugin,
								clickBlock, plyr, this, obtenirEquip(plyr),
								false, false);
						Turret admin = Turret.getAdmin(this, p);
						if (admin != null) {
							admin.updateChildStats();
							turr.linkCreador = true;
						}
						turr.setHp((int) (15 * getBalancingMultiplier(plyr)) + (admin != null ? admin.getUpgradeHpBonus() : 0));
						turr.Build();
						turr.Attack();
						if (turr.built && debug == false) {
							consumeTurretItem(plyr, evt.getHand(), stack);
						}
					}
				}
			}
			if (block.getType() == Material.BEACON){
				block.setType(Material.LAVA);
				world.playSound(getHalfwayMiddle(), Sound.ENTITY_ENDER_DRAGON_HURT, 150F, 1.1F);
				sendGlobalMessage("Enllaç d'energia destruït!");
				givePoints(p, 10);
				comprovarGuanyador();
			}			
		}	
	}
	private void consumeTurretItem(Player player, EquipmentSlot hand, ItemStack usedItem) {
		if (usedItem.getAmount() > 1) {
			usedItem.setAmount(usedItem.getAmount() - 1);
			return;
		}
		if (hand == EquipmentSlot.OFF_HAND) {
			player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
		} else {
			player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
		}
	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		// TODO Auto-generated method stub
		super.onPlayerDeath(evt, killed);
		Player killer = killed.getKiller();
		if (killer == null){
			Double distance = -1.0;

			for (Player p : getPlayers()){
				if (areEnemies(killed, p)){
					Double calcdist = p.getLocation().distance(killed.getLocation());
					if (distance < 0 || distance > calcdist){
						distance = calcdist;
						killer = p;
					}
				}

			}
		}
		int percentatge = getTNTEnabled() ? 64: 100;
		ItemStack arc = (GUtils.Possibilitat(percentatge) ? getTurretItem() : new ItemStack(Material.TNT, 1));
		if (killer != null) {
			if (obtenirEquip(killer) != obtenirEquip(killed)) {
				killer.getInventory().addItem(arc);
				givePoints(killer, 3);
			}
		}    		

	}


	public ItemStack getTurretItem() {
		ItemStack arc = new ItemStack(Material.ARROW, 1); // A stack of diamonds
		arc.addUnsafeEnchantment(Enchantment.INFINITY, 1);
		ItemMeta meta = arc.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Torre de defensa");
		arc.setItemMeta(meta);
		return arc;
	}
	@Override
	protected void onEntityDamage(EntityDamageEvent evt, Entity e) {
		// TODO Auto-generated method stub
		super.onEntityDamage(evt, e);

		if (evt.getEntityType() == EntityType.END_CRYSTAL){
			//if (evt.getCause() != DamageCause.ENTITY_ATTACK){
				evt.setCancelled(true);
			//}

		}
		evt.setDamage(evt.getDamage() * GENERAL_DAMAGE_MULTIPLIER);
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if (!evt.isCancelled() && Turret.fromArrow(this, evt.getDamager()) == null) {
			// Torres historically reduced every damage source to 60%. Restore
			// normal damage specifically for player-versus-player combat. A turret's
			// arrow carries its creator as shooter but is not player combat.
			evt.setDamage(evt.getDamage() / GENERAL_DAMAGE_MULTIPLIER);
		}
	}
	@Override
	protected void onPlayerFish(PlayerFishEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerFish(evt, p);
		Player player = evt.getPlayer();
//sendGlobalMessage("GA");
		Entity caught = evt.getCaught();
		if (player.getItemInHand().getType() == Material.FISHING_ROD) {
			//    			player.sendMessage(ChatColor.RED + "GET OVER HERE!");
			//    			caught.teleport(player.getLocation());
			Location loc1 = player.getLocation().clone();    			
			if (caught == null){
				ArrayList<Player> nearbyPlayers = Utils.getNearbyPlayers(evt.getHook(), 3);
				if(nearbyPlayers.size() > 0){
					caught = nearbyPlayers.get(0);
				}
			}
			if (caught != null){ 
				if(caught instanceof LivingEntity){
					LivingEntity ecaught = (LivingEntity) caught;
					Location loc2 = caught.getLocation().clone();
					player.teleport(loc2);
					caught.teleport(loc1);

					ecaught.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 5, 2));
					world.playEffect(ecaught.getEyeLocation(), Effect.POTION_BREAK, 0);
				}
			}else{
			}
//			world.playEffect(evt.getHook().getLocation(), Effect.VILLAGER_THUNDERCLOUD, 0);
		}


	}
	@Override
	protected void onEntityExplode(EntityExplodeEvent evt, Entity e) {
		// TODO Auto-generated method stub
		super.onEntityExplode(evt, e);

		if (evt.getEntityType() == EntityType.END_CRYSTAL){
			EnderCrystal cry = (EnderCrystal) evt.getEntity();
			evt.setYield(0F);
			JocFinalitzat();
		}
		if (evt.getEntityType() == EntityType.TNT){
			for (Iterator<Block> iterator = evt.blockList().iterator(); iterator.hasNext();) {
				Block b = iterator.next();
				Material t = b.getType();
				if(t == Material.SHORT_GRASS || t == Material.DIRT || t == Material.GRAVEL)iterator.remove();
			}
			getPlayers().stream().filter(p -> p.getLocation().distance(e.getLocation()) < 12).forEach(p -> {p.damage(10); p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1 * 20, 1));});
		}

	}
	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockBreak(evt, blk);
		if(blk.getType() == Material.TNT){
			evt.setCancelled(false);
			evt.getPlayer().damage(6F);
		}
		
	}
	@Override
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockPlace(evt, blk);
		boolean close = Equips.stream().anyMatch(e -> getInhibitors(e).stream().anyMatch(l -> blk.getLocation().distance(l) < 36));
		if(blk.getType() == Material.TNT && !close)evt.setCancelled(false);
		if(!evt.isCancelled())givePoints(evt.getPlayer(), 1);
	}
	//-- FI EVENT CHANNELING

	//-- DEFINICIÓ TORRE--
	public class Bonus implements Listener{
		private final lobby plugin;
		Boolean predeterminat;
		final TipusBonus tipus;
		final int força;
		final int temps;
		final Location loc;
		//public enum TipusBonus {HEAL, SPEED, DAMAGE, PROTECTION};
		int entityId;
		public Bonus(lobby plugin, TipusBonus tipus, Location loc, int força, int temps) {
			predeterminat = true;
			this.tipus = tipus;
			this.força = força;
			this.temps = temps;
			this.loc = loc;
			this.plugin = plugin;
			plugin.getServer().getPluginManager().registerEvents(this, Com.getPlugin());
			Initialize();
		}
		public Bonus(lobby plugin, TipusBonus tipus, Location loc, ItemStack item, int temps) {
			predeterminat = false;
			this.tipus = tipus;
			this.força = 0;
			this.temps = temps;
			this.loc = loc;
			this.plugin = plugin;
			plugin.getServer().getPluginManager().registerEvents(this, Com.getPlugin());
			Initialize();
		}
		public void Initialize(){
			loc.getBlock().setType(Material.FLOWER_POT); 

			replaceItem();
		}
		void setEffect(Player plyr){
			switch(tipus){
			case DAMAGE:
				plyr.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 34 * 20, 0, false), true);
				//Bukkit.broadcastMessage(plyr.getName() + " té més dany!");
				break;
			case HEAL:
				Utils.healDamageable(plyr, plyr.getMaxHealth() * 0.6);
				plyr.setFoodLevel(20);
				plyr.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2 * 20, 1, false), true);
				break;
			case SPEED:
				plyr.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 2, false), true);
				break;
			case PROTECTION:
				if (força == 3){
					plyr.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 70 * 20, 3, false), true);
				}else{
					plyr.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 38 * 20, 1, false), true);
				}

				break;
			case JUMP:
				plyr.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 20 * 6, 4, false), true);
				break;
			default:
				break;


			}
		}
		@EventHandler(priority = EventPriority.HIGH)
		public void onPickup(PlayerPickupItemEvent evt) {
			if(evt.getItem().getEntityId() == entityId){
				setEffect(evt.getPlayer());

				evt.setCancelled(true);
				evt.getItem().remove();
				//Replace item
				replaceItem();
			}

		}
		@EventHandler(priority = EventPriority.HIGH)
		public void onDespawn(ItemDespawnEvent evt) {
			if (evt.getEntity().getEntityId() == entityId){
				evt.setCancelled(true);
			}
		}
		void replaceItem(){
			scheduleGameplayTask(() -> {
                ItemStack item = new ItemStack(getItem());
                Item dropitem = world.dropItem(loc.clone().add(new Vector(0.5,1.1,0.5)), item);
                dropitem.setVelocity(new Vector(0,0,0));
                entityId = dropitem.getEntityId();
            }, temps);
		}
		Material getItem(){
			switch(tipus){
			case DAMAGE:
				return(Material.IRON_SWORD);
			case HEAL:
				return(Material.EMERALD);
			case SPEED:
				return(Material.FEATHER);
			case PROTECTION:
				if (força == 3){
					return(Material.DIAMOND_CHESTPLATE);
				}
				return(Material.GOLDEN_CHESTPLATE);
			case JUMP:
				if (força >= 3){
					return(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
				}
				return(Material.OAK_PRESSURE_PLATE);
			default:
				return(Material.COBBLESTONE);

			}
		}
	}

}
