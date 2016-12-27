package com.biel.lobby.utilities.events.skills.types.specificskills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class CorinthianHelmetSkill extends InherentSkill {

	public CorinthianHelmetSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Casc corinti";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.GOLD_HELMET;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		String modifier1 = ChatColor.GREEN + "" + getDmgMultiplier() * 100 + ChatColor.WHITE;
		return "-" + modifier1 + " mal rebut de fletxes";
	}

	private static double getDmgMultiplier() {
		// TODO Auto-generated method stub
		return 0.5;
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
			boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if(ranged && getGame().areEnemies(damager, damaged)){
			evt.setDamage(evt.getDamage() * (1 - getDmgMultiplier()));
		}
	}
}
