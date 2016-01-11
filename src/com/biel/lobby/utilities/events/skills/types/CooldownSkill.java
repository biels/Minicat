package com.biel.lobby.utilities.events.skills.types;

import org.bukkit.entity.Player;

import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.events.skills.Skill;
import com.biel.lobby.utilities.events.skills.StatusEffect;
import com.biel.lobby.utilities.events.skills.StatusEffectCD;

public abstract class CooldownSkill extends Skill {
	private int cdRemainingTicks = 0; 
	public CooldownSkill(Player ply) {
		super(ply);
		getAssociatedCDEffect(); //Initialize SE
		// TODO Auto-generated constructor stub
	}
	public abstract double getCDSeconds();
	public void resetCooldown(){
		cdRemainingTicks = (int) (Math.round(getCDSeconds() * 20));
	}
	/**
	 * Skips the entire cooldown
	 */
	public void skipCooldown(){
		cdRemainingTicks = 0;
	}
	/** Skips part of the cooldown
	 * @param m The multiplier used to skip part of the cooldown. To skip half of the remaining cooldown, set to 0.5
	 */
	public void skipCooldown(double m){
		cdRemainingTicks = (int) (cdRemainingTicks * m);
	}
	public boolean isCDAvaliable(){
		return cdRemainingTicks == 0;
	}
	private void doCDTick(){
		if(cdRemainingTicks == 0)return;
		if(cdRemainingTicks > 0)cdRemainingTicks -= getTickSpacing();
		if(cdRemainingTicks < 0)cdRemainingTicks = 0;
	}
	protected boolean tryUseCD(){
		boolean cdAvaliable = isCDAvaliable();
		if(cdAvaliable)resetCooldown();
		return cdAvaliable;
	}
	protected StatusEffectCD getAssociatedCDEffect() {
		if(getPlayer() == null)return null;
		PlayerInfo i = getPlayerInfo(getPlayer());
		if(i.hasStatusEffect(getName())){
			return i.getStatusEffect(getName());
		}else{
			return new StatusEffectCD(getPlayer(), this);
		}
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		doCDTick();
	}
}