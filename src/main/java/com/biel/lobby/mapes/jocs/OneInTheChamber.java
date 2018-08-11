package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Random;

import com.biel.BielAPI.Utils.GUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.Utils;

public class OneInTheChamber extends JocScoreRace {

	@Override
	protected int getFinishScore() {
		return Math.min(25, 10 + getPlayers().size() * 2);
	}

	@Override
	public String getGameName() {
		return "OneInTheChamber";
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
		ArrayList<Location> locs = pMapaActual().ObtenirLocations("arena", world);

		if(locs.size() == 0) {
			return new Location(world, -2343, 62, 647);
		}

		loc = new Location(world, Utils.NombreEntre(locs.get(0).getBlockX(), locs.get(1).getBlockX()), locs.get(0).getY() + 1, Utils.NombreEntre(locs.get(0).getBlockZ(), locs.get(1).getBlockZ()));
		return loc;
	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply){
		ArrayList<ItemStack> items = new ArrayList<>();
		items.add(new ItemStack(Material.IRON_SWORD));
		items.add(new ItemStack(Material.BOW));
		items.add(new ItemStack(Material.ARROW));
        Color color1 = getDeterministicColorForPlayer(ply, false);
        Color color2 = getDeterministicColorForPlayer(ply, true);
        items.add(GUtils.createColoredArmor(Material.LEATHER_HELMET, color2));
        items.add(GUtils.createColoredArmor(Material.LEATHER_CHESTPLATE, color1));
        items.add(GUtils.createColoredArmor(Material.LEATHER_LEGGINGS, color2));
        items.add(GUtils.createColoredArmor(Material.LEATHER_BOOTS, color1));
        @SuppressWarnings("deprecation")
		Potion p1 = new Potion(PotionType.INSTANT_DAMAGE);
		p1.setSplash(true);
		items.add(p1.toItemStack(1));
		return items;
	}

	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {

		super.onPlayerDeathByPlayer(evt, killed, killer);
		incrementScore(killer);
		ItemStack Arrow = new ItemStack(Material.ARROW);
		killer.getInventory().addItem(Arrow);
	}

	// Instant kill potion I guess
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {

		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if (ranged) evt.setDamage(100);

	}

	@Override
	protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
		super.onPlayerRespawnAfterTick(evt, p);
		teleportToRandomSpawn(p);
	}
}
