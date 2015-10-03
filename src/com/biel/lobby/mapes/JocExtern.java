package com.biel.lobby.mapes;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class JocExtern extends Joc{
	public JocExtern() {
		super();
		JocIniciat();
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		// TODO Auto-generated method stub
		return new ArrayList<ItemStack>();
	}
	
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void teletransportarTothom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void customJocFinalitzat() {
		// TODO Auto-generated method stub
		
	}

	
}
