package com.biel.lobby.utilities.events.skills.types;

import org.bukkit.entity.Player;

import com.biel.lobby.mapes.Joc.PlayerInfo;
import com.biel.lobby.utilities.events.skills.Skill;
import com.biel.lobby.utilities.events.statuseffects.CDStatusEffect;

public abstract class CooldownSkill extends Skill {
	private int cdRemainingTicks = 0; 
	public CooldownSkill(Player ply) {
		super(ply);
		//getAssociatedCDEffect(); //Initialize SE
		if(ply != null)resetCooldown();
		// TODO Auto-generated constructor stub
	}
	public abstract double getCDSeconds();
	public boolean usingAssociatedCDEffect(){
		return false;
	}
	public void resetCooldown(){
		setCdRemainingTicks((int) (Math.round(getCDSeconds() * 20)));
		if(usingAssociatedCDEffect())getAssociatedCDEffect();
		onCDUse();
	}
	/**
	 * Skips the entire cooldown
	 */
	public void skipCooldown(){
		setCdRemainingTicks(0);
	}
	/** Skips part of the cooldown
	 * @param m The multiplier used to skip part of the cooldown. To skip half of the remaining cooldown, set to 0.5
	 */
	public void skipCooldown(double m){
		setCdRemainingTicks((int) (cdRemainingTicks * m));
	}
	public int getCDRemainigTicks(){
		return cdRemainingTicks;
	}
	public double getCDRemainigSeconds(){
		return cdRemainingTicks / (double) getTickSpacing();
	}
	public boolean isCDAvaliable(){
		return cdRemainingTicks == 0;
	}
	
	protected void setCdRemainingTicks(int cdRemainingTicks) {
		if(cdRemainingTicks == 0){
			onCDAvaliable();
		}
		this.cdRemainingTicks = cdRemainingTicks;
	}
	private void doCDTick(){
		if(cdRemainingTicks == 0)return;
		if(cdRemainingTicks > 0)setCdRemainingTicks(cdRemainingTicks - 1);
		if(cdRemainingTicks < 0)setCdRemainingTicks(0);
		//sendGlobalMessage("cdRemainingTicks: " + cdRemainingTicks);
	}
	protected boolean tryUseCD(){
		boolean cdAvaliable = isCDAvaliable();
		if(cdAvaliable)resetCooldown();
		return cdAvaliable;
	}
	protected void applyAssociatedCDEffect(){
		PlayerInfo i = getPlayerInfo(getPlayer());
		if(!i.hasStatusEffect(getName()))i.addStatusEffect(new CDStatusEffect(getPlayer(), this));
	}
	protected CDStatusEffect getAssociatedCDEffect() {
		if(getPlayer() == null)return null;
		PlayerInfo i = getPlayerInfo(getPlayer());
		applyAssociatedCDEffect();
		return i.getStatusEffect(getName());
	}
	/**
	 * Gets called when cd is up for use
	 */
	public void onCDAvaliable(){
		
	}
	
	/**
	 * Gets called when the cooldown is used up
	 */
	public void onCDUse(){
		
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		doCDTick();
	}
}
