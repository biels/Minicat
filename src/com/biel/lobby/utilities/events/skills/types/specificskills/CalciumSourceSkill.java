package com.biel.lobby.utilities.events.skills.types.specificskills;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;

public class CalciumSourceSkill extends InherentSkill {

	public CalciumSourceSkill(Player ply) {
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
		return Material.MILK_BUCKET;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Esquelet fort";
	}
	public double getModifier(){
		return 2.25D;
	}
	public double getProtectionRatio(){
		return 0.65D;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		String modifier1 = ChatColor.GREEN + "" + getModifier() + ChatColor.WHITE;
		Double percentage = Math.round(getProtectionRatio() * 100 * 10) / 10D;
		String percent = ChatColor.GREEN + "" + percentage + ChatColor.WHITE + "%";
		return "El calci reforça el teu esquelet reduïnt el mal per caiguda en " + modifier1 + " de manera que pots caure des de més amunt sense fer-te mal, redueix el mal per caiguda general en un " + percent + "";
	}
	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		if(evt.getCause() == DamageCause.FALL){
			double m = getProtectionRatio();
			double dmg = evt.getDamage();
			double reduction = dmg * m;
			double result = dmg - reduction;
			evt.setDamage(result);
			evt.setDamage(evt.getDamage() - getModifier());
			//playEffect(p);
			//sendSkillMessage("Reduït: " + Math.round((dmg - evt.getDamage()) * 10) / 10.0D);
		}
	}

	public void playEffect(Player p) {
		for (int i = 0; i < 12; i++) {
			getWorld().playEffect(p.getEyeLocation().subtract(0, 0.9, 0),
					Effect.SNOWBALL_BREAK, Utils.NombreEntre(0, 7));
		}
	}

}
