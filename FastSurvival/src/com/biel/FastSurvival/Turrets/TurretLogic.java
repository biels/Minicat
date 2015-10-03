package com.biel.FastSurvival.Turrets;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class TurretLogic {
	public static void doAllLogic(){
		for(TurretData d : TurretUtils.getActiveTurrets()){
			Turret t = new Turret(d);
			t.doTurretLogic();
		}
	}
	
	public enum AttackGroups{ALL, ENEMY_PLAYERS, ENEMY_MOBS, FRIENDLY_MOBS}
	public enum Upgrades{DAMAGE, ATTACK_SPEED, INCREASE_MAX_HP, SHIELD, FIRE, MAGNETIC}
}
