package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.text.MessageFormat;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class DiamondCoreSkill extends InherentSkill {

	public DiamondCoreSkill(Player ply) {
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
		return "Nucli de diamant";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.DIAMOND;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		Double percentage = Math.round(getProtectionRatio() * 100 * 10) / 10D;
		Double percentage2 = Math.round(getProtectionRatioForPlayer() * 100 * 10) / 10D;
		String percent = ChatColor.GREEN + "" + percentage + ChatColor.WHITE + "%";
		String percent2 = ChatColor.GREEN + "" + percentage2 + ChatColor.WHITE + "%";
		return MessageFormat.format("Redueix el mal rebut de qualsevol font en un {0} + {1} per cada enemic proper", percent, percent2);
	}

	private static double getProtectionRatio() {
		// TODO Auto-generated method stub
		return 0.1;
	}
	private static double getProtectionRatioForPlayer() {
		// TODO Auto-generated method stub
		return 0.082;
	}
	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		if(p != getPlayer())return;
		Joc j = getGame();
		int n = j.getPlayers().stream().mapToInt(ply -> (j.areEnemies(getPlayer(), ply)? 1 : 0)).sum();
		for (int i = 0; i < n; i++) {
			getWorld().playEffect(getPlayer().getEyeLocation().add(0, -0.14 * n, 0), Effect.HAPPY_VILLAGER, n);			
		}
		
		evt.setDamage(evt.getDamage() * (1 - (getProtectionRatio() + getProtectionRatioForPlayer() * n)));
	}

}
