package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Collections;

import com.biel.BielAPI.Utils.GUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.Utils;

public class BaseLunar extends JocScoreRace {

	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		return 8;
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		ItemStack esp = new ItemStack(Material.WOODEN_SWORD, 1); // A stack of diamonds
		esp.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		esp.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		items.add(esp);
		items.add(new ItemStack(Material.WOODEN_SWORD, 1));
		items.add(new ItemStack(Material.BOW, 1));
		Potion p1 = new Potion(PotionType.INSTANT_DAMAGE);
		p1.setSplash(true);
		items.add(p1.toItemStack(1));
		Potion p2 = new Potion(PotionType.INSTANT_HEAL);
		p2.setSplash(true);
		items.add(p2.toItemStack(1));
		items.add(new ItemStack(Material.ARROW, 50));
		items.add(getSnowLauncher(10));
		//Glass
		Color color1 = getDeterministicColorForPlayer(ply, false);;
		items.add(GUtils.createColoredArmor(Material.LEATHER_HELMET, color1));
		items.add(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, Color.WHITE));
		items.add(Utils.createColoredArmor(Material.LEATHER_LEGGINGS, Color.BLUE));
		items.add(Utils.createColoredArmor(Material.LEATHER_BOOTS, Color.WHITE));
		return items;
	}
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "BaseLunar";
	}
	@Override
	public boolean giveSnowLauncherOnKill() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected void donarEfectesInicials(Player ply) {
		// TODO Auto-generated method stub
		super.donarEfectesInicials(ply);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 3, true), true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 3, true), true);

	}
	@Override
	protected void teletransportarTothom() {
		for (Player d : getPlayers()) {  // d gets successively each value in ar.
			teleportToRandomSpawn(d);					
		} 
	}

	protected void teleportToRandomSpawn(Player d) {
		Location loc;
		loc = getRandomSpawnLoc();
		d.teleport(loc);
	}

	private Location getRandomSpawnLoc() {
		Location loc;
		ArrayList<Location> locs = pMapaActual().ObtenirLocations("s", world);
		Collections.shuffle(locs);
		Location l = locs.get(0);
		l.add(0, 2, 0);
		return l;
	}
	@Override
	protected boolean canBeDropped(ItemStack i, Player p) {
		// TODO Auto-generated method stub
		if(i.getType() == Material.ENDER_PEARL)return true;
		if(i.getType() == Material.ARROW)return true;
		return super.canBeDropped(i, p);
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		incrementScore(killer);
		// TODO Auto-generated method stub
		killer.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
		//evt.getDrops().add(new ItemStack(Material.ARROW, 1));
		String action = "matat";
		if(Utils.Possibilitat(20))action = "asssssinat";
		if(Utils.Possibilitat(25)) {
			String[] planets =  {"mercuri", "venus", "la terra", "mart", "júpiter", "saturn", "ura", "neptú", "una altra galàxia", "les estrelles", "una altra lluna"};
			String planet = Utils.getRandomArrayItem(planets);
			action = "enviat a " + planet;
		}
		evt.setDeathMessage(killer.getName() + ChatColor.DARK_AQUA + " ha " + action + " a " + ChatColor.WHITE + killed.getName() + ChatColor.GOLD + " [+1]");
		super.onPlayerDeathByPlayer(evt, killed, killer);
	}
	@Override
	protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerRespawnAfterTick(evt, p);
		p.getEquipment().setHelmet(new ItemStack(Material.GLASS, 1));
	}
	@Override
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt,
			Entity damaged, Entity damager) {
		// TODO Auto-generated method stub
		super.onEntityDamageByEntity(evt, damaged, damager);
		Vector v = Utils.CrearVector(damager.getLocation(), damaged.getLocation());
		v.normalize();
		
		v.multiply(1 + 3 * (evt.getDamage() / 20));
		v.setY(v.getY() / 2.2);
		Vector upV = new Vector(0, 0.25, 0);
		damaged.setVelocity(damaged.getVelocity().add(v.clone().add(upV)));
		damager.setVelocity(damager.getVelocity().add(v.clone().multiply(-1).add(upV)));
	}
	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		if(evt.getCause() == DamageCause.FALL){
			double m = 0.18;
			double dmg = evt.getDamage();
			double reduction = dmg * m;
			double result = dmg - reduction;
			evt.setDamage(result);
			evt.setDamage(evt.getDamage() - 3);
			//playEffect(p);
			//sendSkillMessage("Reduït: " + Math.round((dmg - evt.getDamage()) * 10) / 10.0D);
		}
	}
}
