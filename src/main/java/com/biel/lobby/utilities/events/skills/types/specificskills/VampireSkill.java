package com.biel.lobby.utilities.events.skills.types.specificskills;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class VampireSkill extends InherentSkill{

	public VampireSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Vampir";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		Double percentage = Math.round(getProtectionRatio() * 100 * 10) / 10D;
		String percent = ChatColor.GREEN + "" + percentage + ChatColor.WHITE + "%";
		return "Recuperes el " + percent + " de la vida màxima quan mates un enemic";
	}
	private static double getProtectionRatio() {
		// TODO Auto-generated method stub
		return 0.30;
	}

	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.REDSTONE;
	}
	@Override
	public double getCDSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		//sendGlobalMessage("death");
		if(killer.getHealth() < 0.2 || killer.isDead())return;
		boolean b = killed.getLocation().distance(killer.getLocation()) < 8.0;
		//sendGlobalMessage("D: " + killer.getName() + " " + getPlayer().getName());
		if (killer.getName().equals(getPlayer().getName())){ //b
			//sendGlobalMessage("Vampir effect");
			Utils.healDamageable(getPlayer(), killer.getMaxHealth() * getProtectionRatio());
			//getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) (20 * 2.8), 3));
			getWorld().playSound(getPlayer().getEyeLocation(), Sound.ENTITY_GENERIC_DRINK, 1F, 1F);
			getWorld().playSound(getPlayer().getEyeLocation(), Sound.ENTITY_WITCH_DRINK, 2.0F, 0.8F);
			getWorld().playSound(getPlayer().getEyeLocation(), Sound.ENTITY_GENERIC_EAT, 1.0F, 1.8F);
			Vector v = Utils.CrearVector(killer.getLocation(), killed.getLocation()).multiply(0.5D);
			double max = 7;
			for (int i = 0; i < max; i++) {
//				getWorld().playEffect(getPlayer().getLocation().add(v.clone().multiply(i/max)),
//						Effect.HEART, 0);
			}
		}
	}
}
