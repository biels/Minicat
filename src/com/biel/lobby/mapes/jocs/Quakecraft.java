package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.CustomEntityFirework;
import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.FireworkEffectPlayer;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.PlayerData;

public class Quakecraft extends JocScoreRace {
	FireworkEffectPlayer fPlayer = new FireworkEffectPlayer();
	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		int n = 10 + (getPlayers().size() * 2);
		if (n > 25){n = 25;}
		return n;
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean getResetPlayerOnRespawn() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Quakecraft";
	}
	@Override
	protected void customJocIniciat() {
		setBlockBreakPlace(false);
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.WOOD_HOE, 1));
		return items;
	}
	@Override
	public Boolean getGiveStartingItemsRespawn() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void donarEfectesInicials(Player ply) {
		// TODO Auto-generated method stub
		super.donarEfectesInicials(ply);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1, true), true);

	}
	@Override
	protected void teletransportarTothom() {
		for (Player d : getPlayers()) {  // d gets successively each value in ar.
			teleportToRandomSpawn(d);					
		} 
	}

	

	
	
	int maxT = 1800;
	int getMaxT(Player ply){
		int t = 2000 - (getSpree(ply) * 150);
		if (t < 450){
			t = 450;
		}
		return t;
	}
	ItemStack getRailgun(Player ply){
		int t = getMaxT(ply);
		String l = Integer.toString(t) + "ms CD";
		if(t >= 1800){return Utils.setItemNameAndLore(new ItemStack(Material.WOOD_HOE), "Escopeta de fira", l);}
		if(t >= 1600){return Utils.setItemNameAndLore(new ItemStack(Material.STONE_HOE), ChatColor.YELLOW + "Llança-pilotes", l);}
		if(t >= 1400){return Utils.setItemNameAndLore(new ItemStack(Material.IRON_HOE), ChatColor.YELLOW + "Llançador de focs artificials", l);}
		if(t >= 1200){return Utils.setItemNameAndLore(new ItemStack(Material.GOLD_HOE), ChatColor.YELLOW + "Railgun experimental", l);}
		if(t >= 1000){return Utils.setItemNameAndLore(new ItemStack(Material.DIAMOND_HOE), ChatColor.AQUA + "Railgun", l);}
		if(t > 550){return Utils.setItemNameAndLore(new ItemStack(Material.DIAMOND_HOE),  ChatColor.AQUA + "Ultimate Railgun", l);}
		if(t <= 550){return Utils.setItemNameAndLore(new ItemStack(Material.DIAMOND_HOE), ChatColor.AQUA +"SUPER Railgun", l);}
		return new ItemStack(Material.WOOD);
	}
	void updateRailgun(Player ply){
		PlayerInventory i = ply.getInventory();
		ItemStack railgun = getRailgun(ply);
		if (i.getItemInHand().getType() != Material.DIAMOND_HOE && railgun.getType() == Material.DIAMOND_HOE) {
			sendGlobalMessage(ChatColor.AQUA + ply.getName() + " ha aconseguit la RAILGUN");
		}
		i.clear();
		i.addItem(railgun);
	}
	HashMap<String, Long> pReloadRocket = new HashMap<String, Long>();
	@Override
	protected void onPlayerInteract(PlayerInteractEvent e, Player p) {
		updateBar(p);
		if (e.getPlayer().getItemInHand().getType().name().endsWith("_HOE")
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e
				.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			Vector direction = p.getLocation().getDirection();
			if (pReloadRocket.get(e.getPlayer().getName()) != null) {
				long tTransc = System.currentTimeMillis()
						- (pReloadRocket.get(e.getPlayer().getName()));

				//updateBar(p, tTransc, maxT);

				if (tTransc >= getMaxT(p)) {
					p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1, 1);
					//						Main.nmsAccess.spawnFirework(p);
					Arrow arrow = p.getWorld().spawn(p.getEyeLocation().add(direction),
							Arrow.class);
					arrow.setVelocity(direction
							.multiply(4));
					arrow.setShooter(p);
					pReloadRocket.remove(p.getName());
					pReloadRocket.put(p.getName(),
							System.currentTimeMillis());
					//railgunHitPlace(p, arrow);
				}else{
					p.playSound(p.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 1, 1);

				}
			}

			else {
				Arrow arrow = p.getWorld().spawn(p.getEyeLocation(),
						Arrow.class);
				arrow.setVelocity(direction
						.multiply(4));
				arrow.setShooter(p);
				//					Main.nmsAccess.spawnFirework(p);
				pReloadRocket.put(p.getName(), System.currentTimeMillis());
			}

		}

	}

	private void updateBar(Player p) {
		if (!pReloadRocket.containsKey(p.getName())){
			return;
		}
		long tTransc = System.currentTimeMillis()
				- (pReloadRocket.get(p.getName()));
		Float ratio = (float) ((float)tTransc / (float)getMaxT(p));
		if (ratio > 1){ratio = 1F;}
		//Bukkit.broadcastMessage(Float.toString(ratio));
		//p.setExp(0);
		p.setExp(ratio);
		//p.setLevel(Math.round(ratio * 100));
	}

	void railgunHitPlace(Player shooter, Projectile proj){
		if (proj instanceof Arrow) {
			List<Entity> eList = getAffectedEntities(proj, 8, getSpree(shooter) * 0.12);

			int s = 0;
			for (Entity cEnt : eList){
				if (cEnt instanceof Player){
					//sendGlobalMessage(((Player) cEnt).getName());
					s++;
				}
			}
			Integer.toString(s);
			if (eList.contains(shooter)){s--;}
			if(s <= 0){
				playRailgunEffect(shooter, proj.getLocation(), false, false);
			}
			for (Entity pl : eList) {

				if (pl instanceof Player) {

					Player target = (Player) pl;
					Location l = proj.getLocation();

					getWorld().playEffect(l, Effect.SMOKE, 5);
					if (shooter == null){continue;}
					if (target.getEntityId() != shooter.getEntityId()) {
						try {
							//getWorld().createExplosion(l, 0, false);
						} catch (Exception e1) {

						}
						playRailgunEffect(shooter, l, true, true);
						//							Score score = ScoreBoardManager.getObjective()
						//									.getScore(shooter);
						//							int scorepoint = score.getScore();
						//							score.setScore(scorepoint + 1);
						//							instance.checkPoint();
						incrementScore(shooter);
						updateRailgun(shooter);
						addFrag(shooter, target, "Rocket Launcher");
						//Bukkit.broadcastMessage("Tocat!");
					}

					//proj.remove();
				}

			}
			int bonus = s - 2;
			String strB = ChatColor.WHITE + " (" + ChatColor.GOLD + "+" + Integer.toString(bonus) + ChatColor.WHITE + ")";
			if (s == 2){
				sendGlobalMessage(ChatColor.RED + "Doble-kill!");
			}
			if (s == 3){
				sendGlobalMessage(ChatColor.LIGHT_PURPLE + "TRIPLE-KILL!" + strB);			
			}
			if (s == 4){
				sendGlobalMessage(ChatColor.BLUE + "QUADRA-KILL!" + strB);
			}
			if (s == 5){
				sendGlobalMessage(ChatColor.BOLD + "PENTA-KILL!" + strB);
			}
			if (s == 6){
				sendGlobalMessage("HEXA-KILL!" + strB);
			}
			if (s > 6){
				sendGlobalMessage("SUPER-MEGA-MULTI-KILL(" + Integer.toString(s) + ")"  + strB);
			}
			if (bonus > 0){
				incrementScore(shooter, bonus);
			}
		}

	}
	private Color getColorRailgun(Player ply){
		Material t = getRailgun(ply).getType();
		if(t == Material.WOOD_HOE){return Color.RED;}
		if(t == Material.STONE_HOE){return Color.GREEN;}
		if(t == Material.IRON_HOE){return Color.SILVER;}
		if(t == Material.GOLD_HOE){return Color.YELLOW;}
		if(t == Material.DIAMOND_HOE){return Color.AQUA;}
		return Color.BLACK;
	}
	private void playRailgunEffect(Player p, Location l, boolean hit, boolean special) {
		Builder b = FireworkEffect.builder();
		b.withColor(getColorRailgun(p));
		//b.withTrail();		
		if (!hit){
			b.with(Type.BALL);
		}else{
			b.with(Type.BALL_LARGE);
		}
		if (special) {
			b.with(Type.STAR);
		}
		try {
			FireworkEffect r = b.build();
			Player pArr[] = new Player[getPlayers().size()];
			getPlayers().toArray(pArr);
			CustomEntityFirework.spawn(l, r, pArr);
//			fPlayer.playFirework(getWorld(), l, r);
//			fPlayer.playFirework(getWorld(), l, r);
//			fPlayer.playFirework(getWorld(), l, r);
			//fPlayer.playFirework(getWorld(), l, r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt,
			Entity damaged, Entity damager) {
		// TODO Auto-generated method stub
		super.onEntityDamageByEntity(evt, damaged, damager);
		if (damager instanceof Projectile){
			Projectile proj = (Projectile) damager;
			ProjectileSource source = proj.getShooter();
			if (source instanceof Player){
				//railgunHitPlace((Player)source, proj);
			}

		}
	}

	@Override
	protected void onProjectileHit(ProjectileHitEvent e, Projectile proj) {
		//e.getEntity().getLocation().getBlock().setType(Material.GOLD_BLOCK);
		if (e.getEntity() instanceof Arrow) {

			if (e.getEntity().getShooter() instanceof Player) {
				Player shooter = (Player) ((Arrow) e.getEntity())
						.getShooter();
				railgunHitPlace(shooter, proj);
				proj.remove();
			}
		}
	}
	private List<Entity> getAffectedEntities(Entity e, int depth, Double bRange) {
		int origEntId = e.getEntityId();
		List<Entity> nearbyEntities = e.getNearbyEntities(2.8 + bRange, 3.25 + bRange, 2.8 + bRange);
		ArrayList<Entity> toAdd = new ArrayList<Entity>();
		for (Entity ent : nearbyEntities){
			if (depth > 0){
				if (ent.getEntityId() != origEntId && !ent.isDead() && ent.isValid()){
					if(ent instanceof Player){
						Player p = (Player) ent;
						PlayerInfo i = getPlayerInfo(p);
						//getWorld().playEffect(p.getEyeLocation(), Effect.CLOUD, 0);
						if(i.isImmune() || i.isAFK() || isSpectator(p))continue;
					}
					toAdd.add(ent);						
					toAdd.addAll(getAffectedEntities(ent, depth - 1, bRange));
				}

			}	
		}
		nearbyEntities.addAll(toAdd);
		return Utils.removeDuplicates(nearbyEntities);
	}

	public void addFrag(Player p, Player target, String weapon) {
		if(!getPlayers().contains(target))return;
		target.setHealth(0);
		String verb = "rebentat";
		if(p.getName().contains("amiguet") && Utils.Possibilitat(100 / new PlayerData(p).getRank()))verb = "greixetat";
		Bukkit.getServer()
		.broadcastMessage(
				p.getName() + " ha " + verb + " a "
						+ target.getName());
		//"§7[§cQuake§7]: "
		for (Player pl : getPlayers()) {
			pl.playSound(pl.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1, 1);
		}
	}
	@Override
	protected void onEntityDamage(EntityDamageEvent evt, Entity e) {
		// TODO Auto-generated method stub
		super.onEntityDamage(evt, e);
		if (evt.getCause() != DamageCause.CUSTOM){
			evt.setCancelled(true);
		}
	}
	@Override
	protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerRespawnAfterTick(evt, p);
		teleportToRandomSpawn(p);
		updateRailgun(p);

	}
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		// TODO Auto-generated method stub
		super.onPlayerDeath(evt, killed);
		evt.setDeathMessage("");
		evt.getDrops().clear();
	}
	@Override
	protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerMove(evt, p);
		updateBar(p);

	}
	@Override
	protected void onPlayerMoveDistributed(PlayerMoveEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerMoveDistributed(evt, p);
		if (JocIniciat){
			for (Player ply : getPlayers()){
				updateBar(ply);
			}
		}
	}
}
