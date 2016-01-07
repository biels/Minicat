package com.biel.lobby.utilities.events.skills.types.specificskills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class SpeedyArcher extends InherentSkill {

	public SpeedyArcher(Player ply) {
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
		return Material.FEATHER;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Arquer veloç";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Otorga uns instants de velocitat a l'encertar una fletxa a un jugador enemic";
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
			boolean ranged) {
		// TODO Auto-generated method stub
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if(ranged && damager == getPlayer()){
			int ticks = (int) (22 * 1 + 5 * Math.round(evt.getDamage()));
			damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, ticks, 2));
		}
	}
}
