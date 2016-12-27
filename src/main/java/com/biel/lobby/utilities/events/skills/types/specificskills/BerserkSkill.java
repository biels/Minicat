package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.text.MessageFormat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class BerserkSkill extends InherentSkill {

	public BerserkSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.GOLD_AXE;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Lluitador boig";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		Double percentage = Math.round(getRatio() * 100 * 10) / 10D;
		String percent = ChatColor.GREEN + "" + percentage + ChatColor.WHITE + "%";
		return MessageFormat.format("Incrementa l''atac en un {0} per cada punt de vida que et falti", percent);
	}

	private static double getRatio() {
		// TODO Auto-generated method stub
		return 0.023;
	}
	@Override
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt, Entity damaged, Entity damager) {
		// TODO Auto-generated method stub
		super.onEntityDamageByEntity(evt, damaged, damager);
		if(evt.isCancelled())return;
		if(damager == getPlayer()){
			Damageable ply = (Damageable) damager;
			double missingHealth = ply.getMaxHealth() - ply.getHealth();
			evt.setDamage(evt.getDamage() * (1 + getRatio() * missingHealth));
		}
	}
}
