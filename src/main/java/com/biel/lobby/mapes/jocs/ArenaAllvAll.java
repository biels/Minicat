package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

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

public class ArenaAllvAll extends JocScoreRace {

	@Override
	protected int getFinishScore() {
		return 2 + getPlayers().size();
	}

	@Override
	public String getGameName() {
		return "ArenaAllvAll";
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		items.add(new ItemStack(Material.WOODEN_SWORD, 1));
		items.add(new ItemStack(Material.BOW, 1));
		Potion p1 = new Potion(PotionType.INSTANT_DAMAGE);
		p1.setSplash(true);
		items.add(p1.toItemStack(1));
		Potion p2 = new Potion(PotionType.SLOWNESS);
		p2.setSplash(true);
		items.add(p2.toItemStack(2));
		items.add(new ItemStack(Material.ARROW, 5));
		items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
		items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
		items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		items.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
		return items;
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
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
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {

		super.onPlayerDeathByPlayer(evt, killed, killer);
		incrementScore(killer);
		if (getSpree(killer) == 1){
			Potion p1 = new Potion(PotionType.SPEED);
			p1.setSplash(true);
			killer.getInventory().addItem(p1.toItemStack(1));
		}
		if (getSpree(killer) == 2){
			Potion p1 = new Potion(PotionType.POISON);
			p1.setSplash(true);
			killer.getInventory().addItem(p1.toItemStack(1));
			getPlayerInfo(killer).addAdditionalSkill();
		}
		if (getSpree(killer) == 3){
			Potion p1 = new Potion(PotionType.REGEN);
			p1.setSplash(true);
			killer.getInventory().addItem(p1.toItemStack(1));
		}
		if (getSpree(killer) >= 4){
			Potion p1 = new Potion(Utils.getRandomPotionType());
			p1.setSplash(true);
			killer.getInventory().addItem(p1.toItemStack(1));
		}
		evt.getDrops().clear();
		evt.setDeathMessage(killer.getName() + " ha matat a " + killed.getName() + " [+1]");
		updateScoreBoards();

	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if(ranged)damager.getInventory().addItem(new ItemStack(Material.ARROW, 1));
	}
	@Override
	protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
		super.onPlayerRespawnAfterTick(evt, p);
		teleportToRandomSpawn(p);
	}
}
