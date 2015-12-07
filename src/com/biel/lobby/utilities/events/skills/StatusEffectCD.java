package com.biel.lobby.utilities.events.skills;

import org.bukkit.entity.Player;

import com.biel.lobby.utilities.events.skills.types.CooldownSkill;

public class StatusEffectCD extends StatusEffect {
	CooldownSkill s;
	public StatusEffectCD(Player ply, CooldownSkill s) {
		super(ply);
		this.s = s;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return s.getName();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return s.getDescription();
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		if(s.isCDAvaliable()){
			setRemainingTicks(-1);
			setType(StatusEffectType.BUFF);
		}else {
			setType(StatusEffectType.SKILL_TRAY);
			setRemainingTicks((int) (s.getCDSeconds() * 20));
		}
	}
}
