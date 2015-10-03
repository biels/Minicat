package com.biel.BielAPI.events;

import java.text.MessageFormat;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Spigot;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.inventory.InventoryHolder;

public class WorldEventBus extends EventBus {
	private UUID worldUUID;
	
	public WorldEventBus() {
		super();
	}
	public WorldEventBus(World world) {
		super();
		if(world != null){			
			this.worldUUID = world.getUID();
		}
	}
	public WorldEventBus(Player ply) {
		super();
		if(ply != null){
			this.worldUUID = ply.getWorld().getUID();
		}
	}

	protected World getWorld() {
		if(worldUUID == null)return null;
		return Bukkit.getWorld(worldUUID);
	}
	
	protected void setWorld(World world) {
		if(world == null){
			worldUUID = null;
			return;
		}
		this.worldUUID = world.getUID();
	}
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return getWorld() != null;
	}
	@Override
	protected Boolean verifyEvent(Event evt) {
		if (evt instanceof WorldEvent){
			WorldEvent e = (WorldEvent)evt;
			return e.getWorld() == getWorld();
		}
		if (evt instanceof EntityEvent){
			EntityEvent e = (EntityEvent)evt;
			return e.getEntity().getWorld() == getWorld();
		}
		if (evt instanceof PlayerEvent){
			PlayerEvent e = (PlayerEvent)evt;
			return e.getPlayer().getWorld() == getWorld();
		}
		if (evt instanceof BlockEvent){
			BlockEvent e = (BlockEvent)evt;
			return e.getBlock().getWorld() == getWorld();
		}
		if (evt instanceof InventoryEvent){
			InventoryEvent e = (InventoryEvent)evt;
			InventoryHolder holder = e.getInventory().getHolder();
			if (holder instanceof Entity) {
				return ((Entity) holder).getWorld() == getWorld();
			}
		}
		System.out.println("Event no verificat! @ WorldEventBus");
		return true;
	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		World w = getWorld();
//		if (w == null){return "WorldEventBus, W: null";}
//		return "WorldEventBus, W: " + w.getName();
//	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (getWorld() == null)return super.toString() + " w: null";
		return MessageFormat.format("{0}[World =]", super.toString(), getWorld().getName());
	}
}
