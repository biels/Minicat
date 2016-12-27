package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;

import org.bukkit.DyeColor;

import com.biel.lobby.mapes.JocTeamDeathMatch;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocTeamScoreRace.EquipScoreRace;

public class PixelRift extends JocTeamDeathMatch {

	@Override
	protected int getFinishScore() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		equips.add(new EquipScoreRace(DyeColor.RED, "vermell")); //Id 0
		equips.add(new EquipScoreRace(DyeColor.BLUE, "blau")); //Id 1
		equips.add(new EquipScoreRace(DyeColor.GREEN, "verd")); //Id 2
		equips.add(new EquipScoreRace(DyeColor.YELLOW, "groc")); //Id 3
		return equips;
	}
	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		super.customJocIniciat();
		world.setTime(4500);
		
	}
	@Override
	protected void setCustomGameRules() {
		
	}

	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "PixelRift";
	}

}
