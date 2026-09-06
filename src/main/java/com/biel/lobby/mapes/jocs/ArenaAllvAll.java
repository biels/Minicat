package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.Utils;

public class ArenaAllvAll extends JocScoreRace {

	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		return 2 + getPlayers().size();
	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "ArenaAllvAll";
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		items.add(new ItemStack(Material.WOODEN_SWORD, 1));
		items.add(new ItemStack(Material.BOW, 1));
		items.add(Utils.createPotion(PotionType.HARMING, 1, true));
		items.add(Utils.createPotion(PotionType.SLOWNESS, 2, true));
		items.add(new ItemStack(Material.ARROW, 5));
		items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
		items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		items.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
		return items;
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
		// TODO Auto-generated method stub
		return 1;
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
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		incrementScore(killer);
		if (getSpree(killer) == 1){
			killer.getInventory().addItem(Utils.createPotion(PotionType.SWIFTNESS, 1, true));
		}
		if (getSpree(killer) == 2){
			killer.getInventory().addItem(Utils.createPotion(PotionType.POISON, 1, true));
			getPlayerInfo(killer).addAdditionalSkill();
		}
		if (getSpree(killer) == 3){
			killer.getInventory().addItem(Utils.createPotion(PotionType.REGENERATION, 1, true));
		}
		if (getSpree(killer) >= 4){
			killer.getInventory().addItem(Utils.createPotion(Utils.getRandomPotionType(), 1, true));
		}
		evt.getDrops().clear();
		evt.setDeathMessage(killer.getName() + " ha matat a " + killed.getName() + " [+1]");
		updateScoreBoards();
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
			boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if(ranged)damager.getInventory().addItem(new ItemStack(Material.ARROW, 1));
	}
	@Override
	protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerRespawnAfterTick(evt, p);
		teleportToRandomSpawn(p);
	}
}
