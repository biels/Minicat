package com.biel.BielAPI.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EventUtils {
	public static boolean interactsWithAny(Object evt, List<Player> players, int depth){
		if (depth <= 0)return false;
		Class<? extends Object> cls = evt.getClass();
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
					Object newO = m.invoke(evt);
					if (newO != null) {
						//Bukkit.broadcastMessage("GOT: " + newO.toString());
						if (newO instanceof Player) {
							Player p = (Player) newO;
							if (players.contains(p))
								return true;
							if(m.getName().equals("getPlayer"))continue;
						} 	
						//Bukkit.broadcastMessage("CADENA -><-");
						return interactsWithAny(newO, players, depth - 1);

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
