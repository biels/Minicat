package com.biel.lobby.utilities.events.skills.types;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.biel.BielAPI.Utils.ItemButton;
import com.biel.lobby.lobby;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.Skill;

public abstract class CastableSkill extends CooldownSkill {

	public CastableSkill(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	public void cast(){ //SKILL CAST!

	}
	public ItemButton getItemButton(Player ply){
		ItemButton button = new ItemButton(getItemStack(), ply, new ItemButton.OptionClickEventHandler() {
			@Override
			public void onOptionClick(ItemButton.OptionClickEvent event) {
				//Trigger skill

			}
		});
		return button;
	}

}
