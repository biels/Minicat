package com.biel.lobby.utilities.events.skills.types;

import org.bukkit.entity.Player;

import com.biel.BielAPI.Utils.ItemButton;

public abstract class CastableSkill extends CooldownSkill {

	public CastableSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	public void cast(){ //SKILL CAST!

	}
	public ItemButton getItemButton(Player ply){
		ItemButton button = new ItemButton(getItemStack(), ply, event -> {
            //Trigger skill

        });
		return button;
	}

}
