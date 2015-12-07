package com.biel.BielAPI.events;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import org.bukkit.event.Event;

public class EventBusManager {
	ArrayList<EventBus> buses = new ArrayList<EventBus>();
	ArrayList<EventBus> toAdd = new ArrayList<EventBus>();
	ArrayList<EventBus> toRemove = new ArrayList<EventBus>();
	public synchronized void recieveEvent(Event evt) {
		unregisterInvalidBuses();
		buses.addAll(toAdd);
		toAdd.clear();
		buses.removeAll(toRemove);
		toRemove.clear();
		EventBus lastEventBus = null;
		try {
			for(EventBus bus: buses){
				bus.recieveEvent(evt);
				lastEventBus = bus;
			}
		} catch (ConcurrentModificationException e) {			
			if (lastEventBus != null) {
				System.out.println("ConcurrentModificationException:");
				System.out.println("Caused by: " + lastEventBus.toString() + " Event: " + evt.getEventName());
			}
		}
	}
	public synchronized void registerEventBus(EventBus bus){
		if(buses.contains(bus)){
			System.out.println("El canal d'eseveniments ja existeix @ " + bus.toString());
		}else{
			//System.out.println("Afegit el canal d'esdeveniments a la cua " + bus.getClass().getName());
			toAdd.add(bus);
		}
	}
	public synchronized void unregisterEventBus(EventBus bus){
		if(buses.contains(bus)){
			toRemove.add(bus);
			//System.out.println("Esborrat el canal d'esdeveniments " + bus.getClass().getName());
		}else{
			System.out.println("El canal d'eseveniments ja no existia");
		}
	}
	public synchronized void unregisterInvalidBuses(){
		ArrayList<EventBus> toremove = new ArrayList<EventBus>();
		for(EventBus bus : buses){
			if(!bus.isValid() || bus.isDestroyed()){
				toremove.add(bus);
			}
		}
		for (EventBus eventBus : toremove) {
			unregisterEventBus(eventBus);			
		}
	}
	public String getStats(){
		return  MessageFormat.format("AB: {0}, TA:{1}, TR:{2}", buses.size(), toAdd.size(), toRemove.size());
	}
}
