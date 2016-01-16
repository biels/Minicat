package com.biel.lobby.utilities.events.statuseffects;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

public class AuraStatusEffect extends StatusEffect {
	ArrayList<Item> hItems = new ArrayList<Item>();
	private AuraInfo info;
	public AuraStatusEffect(Player ply, AuraInfo info) {
		super(ply);
		this.info = info;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public AuraInfo getInfo() {
		return info;
	}

	public void setInfo(AuraInfo info) {
		this.info = info;
	}

	private void spawnItems(){
		
		getLocations().stream().forEach(l -> {
			Item item = getWorld().dropItem(l, info.getItemStack());
			item.setPickupDelay(Integer.MAX_VALUE);
			hItems.add(item);
		});
	}
	private void despawnItems(){
		hItems.stream().forEach(i -> {i.remove();});
		hItems.clear();
	}
	private void reallocateItems(boolean hard){
		if(hard){
			despawnItems();
			spawnItems();			
		}else{
			if(hItems.size() != info.getN()){spawnItems();return;}
			ArrayList<Location> locations = getLocations();
			for (int i = 0; i < info.getN(); i++) {
				hItems.get(i).teleport(locations.get(i));
			}
		}
		
	}

	private ArrayList<Location> getLocations() {
		return getLocationsCircle(getPlayer().getLocation(), info.getRadius(), info.getN(), getTicksLived() * Math.PI * 2 * info.getSpeed());
	}
	public static ArrayList<Location> getLocationsCircle(Location center, double radius, int n, double offset){
		ArrayList<Location> locs = new ArrayList<Location>();
		World world = center.getWorld();

		for(int i = 0; i < n; i++){
			double currAngle = (2 * Math.PI / n) * i + offset;// + (offset % (Math.PI * 2));			
			Location spawnpoint = center.clone().add(new Location(world, Math.cos(currAngle) * radius, 0, Math.sin(currAngle) * radius));
			locs.add(spawnpoint);
		}
		return locs;
	}
	@Override
	public void clearExternals() {
		// TODO Auto-generated method stub
		super.clearExternals();
		despawnItems();
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		reallocateItems(false);
	}
	@Override
	protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerMove(evt, p);
		reallocateItems(true);
	}
	@Override
	protected void onPlayerTeleport(PlayerTeleportEvent evt, Player p, Location from, Location to, TeleportCause c) {
		// TODO Auto-generated method stub
		super.onPlayerTeleport(evt, p, from, to, c);
		reallocateItems(true);
	}
}
