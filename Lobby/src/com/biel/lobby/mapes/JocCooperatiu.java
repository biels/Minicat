package com.biel.lobby.mapes;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
