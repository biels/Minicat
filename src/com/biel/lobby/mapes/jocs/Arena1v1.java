package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamDeathMatch;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocObjectius.EquipObjectius;
import com.biel.lobby.utilities.ScoreBoardUpdater;

public class Arena1v1 extends JocTeamDeathMatch {

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<Equip>();
		equips.add(new Equip(DyeColor.RED, "vermell")); //Id 0
		equips.add(new Equip(DyeColor.BLUE, "blau")); //Id 1
		return equips;
	}

	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(Material.WOOD_SWORD, 1));
		return items;
	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Arena 1v1";
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		PlayerInfo playerInfo = getPlayerInfo(killer);
		playerInfo.setValue(playerInfo.getValue() + 1);
		sendGlobalMessage(killer.getName() + " kills: " + playerInfo.getValue());
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		// TODO Auto-generated method stub
		if (JocIniciat){
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<Integer> values = new ArrayList<Integer>();
			int max = 20;
			int best = 0;
			for (Equip e : Equips){
				list.add(e.getChatColor() + "Equip " + e.getAdjectiu());
				values.add(e.getPlayers().size());
			}
			ScoreBoardUpdater.setScoreBoard(getPlayers(), "Taula [" + Integer.toString(best) + "/" + Integer.toString(max) + "]", list, values);
		}
	}

	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		return 5;
	}
	
}
