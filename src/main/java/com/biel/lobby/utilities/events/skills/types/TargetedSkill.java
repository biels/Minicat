package com.biel.lobby.utilities.events.skills.types;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public abstract class TargetedSkill extends CastableSkill {

	public TargetedSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}
	public enum TargetingMode{}
	protected LivingEntity getTargets(){
		return null;

	}

}
