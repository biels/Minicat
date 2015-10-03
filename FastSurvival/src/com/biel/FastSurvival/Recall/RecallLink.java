package com.biel.FastSurvival.Recall;

import org.bukkit.Location;

public class RecallLink{
	Location linkLocation;
	int castTime;
	int idleTime;
	public Location getLinkLocation() {
		return linkLocation;
	}
	public void setLinkLocation(Location linkLocation) {
		this.linkLocation = linkLocation;
	}
	public int getCastTime() {
		return castTime;
	}
	public void setCastTime(int castTime) {
		this.castTime = castTime;
	}
	public int getIdleTime() {
		return idleTime;
	}
	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}
}