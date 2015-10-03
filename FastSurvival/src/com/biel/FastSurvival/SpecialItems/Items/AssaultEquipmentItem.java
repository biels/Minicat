package com.biel.FastSurvival.SpecialItems.Items;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.biel.FastSurvival.SpecialItems.SpecialItem;
import com.biel.FastSurvival.SpecialItems.SpecialItemData;
import com.biel.FastSurvival.Utils.Utils;

public class AssaultEquipmentItem extends RawAssaultEquipmentItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 13;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Assault equipment";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.GOLD_BOOTS;
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setModifier(5D + Utils.NombreEntre(1, 2));
	}
	
	
}
