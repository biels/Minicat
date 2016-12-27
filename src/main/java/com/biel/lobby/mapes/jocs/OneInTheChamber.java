package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.Utils;

public class OneInTheChamber extends JocScoreRace {

	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		return Math.min(25, 10 + getPlayers().size() * 2);
	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
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
		loc = new Location(world, Utils.NombreEntre(locs.get(0).getBlockX(), locs.get(1).getBlockX()), locs.get(0).getY() + 1, Utils.NombreEntre(locs.get(0).getBlockZ(), locs.get(1).getBlockZ()));
		return loc;
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply){
		ArrayList<ItemStack> items = new ArrayList<>();
		items.add(new ItemStack(Material.IRON_SWORD));
		items.add(new ItemStack(Material.BOW));
		items.add(new ItemStack(Material.ARROW));
		@SuppressWarnings("deprecation")
		Potion p1 = new Potion(PotionType.INSTANT_DAMAGE);
		p1.setSplash(true);
		items.add(p1.toItemStack(1));
		return items;
	}
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		super.onPlayerDeathByPlayer(evt, killed, killer);
		incrementScore(killer);
		ItemStack Arrow = new ItemStack(Material.ARROW);
		getStartingItems(killed);
		killer.getInventory().addItem(Arrow);
	}
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
			boolean ranged){
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if(ranged)damaged.damage(1000);
	}

}