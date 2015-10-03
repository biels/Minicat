package com.biel.BielAPI.events;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PlayerWorldEventBus extends WorldEventBus {
	String playerName;
	public PlayerWorldEventBus(Player ply) {
		super(ply);
		if (ply != null) {
			this.playerName = ply.getName();
		}
	}

	protected Player getPlayer() {
		if(playerName == null)return null;
		return Bukkit.getPlayer(playerName);
	}

	protected void setPlayer(Player player) {
		this.playerName = player.getName();
	}
	protected boolean getPlayerSpecificEventFiltering(){
		return true;
	}
	@Override
	protected Boolean verifyEvent(Event evt) {
		Boolean worldVerifyEvent = super.verifyEvent(evt);
		if (getPlayerSpecificEventFiltering() && worldVerifyEvent) {
			//Bukkit.broadcastMessage("----");
			worldVerifyEvent = verifyReflection(evt, 6);
		}
		return worldVerifyEvent;
	}
	protected boolean verifyReflection(Object o, int depth){
		if (depth <= 0)return false;
		Class<? extends Object> cls = o.getClass();
		//if(cls.getName().contains("Move"))return false; // temp
		//if(cls.getName().contains("BlockPhysics"))return false; // temp
		//if(cls.getName().contains("Interact"))return false; // temp
		//Bukkit.broadcastMessage(cls.getName());
		for(Method m : cls.getMethods()){
			//if(m.getReturnType().equals(OfflinePlayer.class))continue; //Loop
			if(!m.getName().startsWith("get"))continue;
			if(Entity.class.isAssignableFrom(m.getReturnType())|| Event.class.isAssignableFrom(m.getReturnType())){
				//Bukkit.broadcastMessage(depth + ": " + m.getName() + " " + m.getReturnType().getSimpleName());
				try {
					Object newO = m.invoke(o);
					if (newO != null) {
						//Bukkit.broadcastMessage("GOT: " + newO.toString());
						if (newO instanceof Player) {
							Player p = (Player) newO;
							if (p == getPlayer())
								return true;
							if(m.getName().equals("getPlayer"))continue;
						} 	
						//Bukkit.broadcastMessage("CADENA -><-");
						return verifyReflection(newO, depth - 1);

					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace(); <- D
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace(); <- D
				}
				//if(m.getName().contains("Killer") || m.getName().contains("Shooter")){

				//}
			}
			//Bukkit.broadcastMessage(cls.getSimpleName() + ": " + m.getName());
		}
		return false;
	}
}
