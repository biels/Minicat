package com.biel.lobby.utilities.events.statuseffects;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class AuraRendererStatusEffect extends StatusEffect {
	ArrayList<Item> hItems = new ArrayList<>();
	public AuraRendererStatusEffect(Player ply) {
		super(ply);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "AuraRenderer";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	private void spawnItems(AuraInfo info){ //Spawn items for every aura
		for(AuraInfo a : getAurasa()){
			getLocations(a).stream().forEach(l -> {
				Item item = getWorld().dropItem(l, info.getItemStack());
				item.setPickupDelay(Integer.MAX_VALUE);
				hItems.add(item);
			});		
		}
	}
	private void despawnItems(){ //Despawn all at once
		hItems.stream().forEach(Entity::remove);
		hItems.clear();
	}
	private void reallocateItems(boolean hard){ //
		if(hard){
			despawnItems();
			for(AuraInfo a : getAurasa())spawnItems(a);	
		}else{
//			for(AuraInfo a : getPlayerInfo().getAuras()){
//				//if(hItems.size() != info.getN()){spawnItems(a);continue;}
//				ArrayList<Location> locations = getLocations(a);
//				for (int i = 0; i < a.getN(); i++) {
//					hItems.get(i).teleport(locations.get(i));
//				}				
//			}
		}
	}
	private double getDefaultRadius(AuraInfo info){
		int i = getAurasa().indexOf(info);
		int s = getAurasa().size();
		double SPACING = 0.2;
		double INTERNAL = 1.2;
		double SHRINK_EXP = 0.5;
		double SHRINK_LINEAR = 0.1;
		return INTERNAL; //+ SPACING * i - Math.pow(s, SHRINK_EXP) * SHRINK_LINEAR;
	}

	private ArrayList<AuraInfo> getAurasa() {
		return getPlayerInfo().getAuras();
	}
	private ArrayList<Location> getLocations(AuraInfo info) {
		return getLocationsCircle(getPlayer().getLocation(), (info.getRadius() > 0 ? info.getRadius() : getDefaultRadius(info)), info.getN(), getTicksLived() * Math.PI * 2 * (info.getSpeed() / (20 * 360)));
	}
	public static ArrayList<Location> getLocationsCircle(Location center, double radius, int n, double offset){
		ArrayList<Location> locs = new ArrayList<>();
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
		reallocateItems(true);
	}
	@Override
	protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerMove(evt, p);
		//reallocateItems(true);
	}
	@Override
	protected void onPlayerTeleport(PlayerTeleportEvent evt, Player p, Location from, Location to, TeleportCause c) {
		// TODO Auto-generated method stub
		super.onPlayerTeleport(evt, p, from, to, c);
		//reallocateItems(true);
	}
}
