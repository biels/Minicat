package com.biel.lobby.mapes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public abstract class JocTeamDeathMatch extends JocTeamScoreRace{

	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		EquipScoreRace eKiller = (EquipScoreRace) obtenirEquip(killer);
		int value = 1;
		eKiller.incrementScore(value);
		//evt.setDeathMessage(evt.getDeathMessage() + ChatColor.GREEN + "[+" + Integer.toString(value) + "]");
	}


}
