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

public class AdvancedAssaultEquipmentItem extends RawAssaultEquipmentItem{
	@Override
	public int getClassID() {
		// TODO Auto-generated method stub
		return 14;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Advanced assault equipment";
	}
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return Material.DIAMOND_BOOTS;
	}
	@Override
	public int getTier() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public void initializeData(SpecialItemData d) {
		// TODO Auto-generated method stub
		super.initializeData(d);
		d.setModifier(8D + (Utils.NombreEntre(1, 10) / 2D));
	}
	@Override
	public Double getDamageMultiplier(SpecialItemData d) {
		// TODO Auto-generated method stub
		return 0.3D + ((d.getModifier() / 100) / 6);
	}
	
}
