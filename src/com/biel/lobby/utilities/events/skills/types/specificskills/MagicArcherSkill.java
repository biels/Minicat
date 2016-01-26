package com.biel.lobby.utilities.events.skills.types.specificskills;

import java.util.Collections;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.utilities.events.skills.types.ItemAttatchedModeSkill;
import com.biel.lobby.utilities.events.skills.types.ItemAttatchedStackModeSkill;

public class MagicArcherSkill extends ItemAttatchedStackModeSkill {

	public MagicArcherSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}
	public enum Mode {ACCELERATOR, ENCHANCER, REPULSOR, DRAINER};
	@Override
	public void registerModes() {
		registerMode(new StackSkillMode(Mode.ACCELERATOR.ordinal(), "Accelerador", "Et dona l'efecte de velocitat", ChatColor.DARK_BLUE, 3));
		registerMode(new StackSkillMode(Mode.ENCHANCER.ordinal(), "Potenciador", "Inflingeix mal addicional", ChatColor.DARK_RED, 3));
		registerMode(new StackSkillMode(Mode.REPULSOR.ordinal(), "Repulsor", "Empenta els enemics", ChatColor.YELLOW, 4));
		registerMode(new StackSkillMode(Mode.DRAINER.ordinal(), "Drenador", "Aplica l'efecte de wither a l'enemic i drena vida", ChatColor.BLACK, 5));
	}

	@Override
	public boolean matchesItem(ItemStack i) {
		// TODO Auto-generated method stub
		return i != null && i.getType() == Material.BOW;
	}

	@Override
	public double getCDSeconds() { // UNUSED
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Arquer màgic";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Aquesta habilitat s'aplica als arcs. Utilitza shift + m_wheel per canviar entre modes amb l'arc a la ma. Els modes: " + getModeList();
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.FIREWORK;
	}
	//Class overriding, important to keep uniquely identified to the SkillTrayEffect
	@Override
	protected Class<? extends ItemAttatchedModeSkillTrayEffect> getTrayEffectClass() {
		// TODO Auto-generated method stub
		return MagicArcherTrayEffect.class;
	}
	public class MagicArcherTrayEffect extends ItemAttatchedStackModeSkillTrayEffect{

		public MagicArcherTrayEffect(Player ply) {
			super(ply);
			// TODO Auto-generated constructor stub
			setMaxValue(getSelectedMode().getMaxStacks());
			setValue(0);
		}
		@Override
		protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
				boolean ranged) {
			// TODO Auto-generated method stub
			super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
			if(damager == getPlayer() && ranged && getGame().areEnemies(damager, damaged)){
				int increment = getValue() == 0 ? 1 : 2;
				setValue(getValue() + increment);
			}
		}
		//Shoot bow
	}
}
