package com.biel.lobby.mapes;

import org.bukkit.entity.Player;

public abstract class JocCooperatiu extends Joc {

	@Override
	protected void setCustomGameRules() {
		

	}
	@Override
	public Boolean areAllies(Player ply, Player ply2) {
		// TODO Auto-generated method stub
		return true;
	}

}
