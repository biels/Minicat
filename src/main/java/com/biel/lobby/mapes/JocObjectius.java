package com.biel.lobby.mapes;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.Arena4.Arena4Equip;
import com.biel.lobby.utilities.ColorConverter;
import com.biel.lobby.utilities.ScoreBoardUpdater;

public abstract class JocObjectius extends JocEquips {

	void ObjectiuCompletat(){

	}

	//	@Override
	//	public EquipObjectius obtenirEquip(Player ply) {
	//		// TODO Auto-generated method stub
	//		return (EquipObjectius) super.obtenirEquip(ply);
	//	}
	//
	//	@Override
	//	public EquipObjectius obtenirEquip(int id) {
	//		// TODO Auto-generated method stub
	//		return (EquipObjectius) super.obtenirEquip(id);
	//	}

	@Override
	public void initTeams() {
		super.initTeams();
		for (Equip e : Equips){
			EquipObjectius eq = (EquipObjectius) e;
			eq.setObjectius(getDesiredObjectivesTeam(eq));
		}
	}
	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockBreak(evt, blk);
		for (Equip e : Equips){
			EquipObjectius eq = (EquipObjectius) e;
			Objectiu closerObjective = getCloserObjective(blk.getLocation(), eq.getObjectius());
			closerObjective.onBlockBreak(evt, blk);
		}
	}
	@Override
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
		// TODO Auto-generated method stub
		super.onBlockPlace(evt, blk);
		for (Equip e : Equips){
			EquipObjectius eq = (EquipObjectius) e;
			Objectiu closerObjective = getCloserObjective(blk.getLocation(), eq.getObjectius());
			closerObjective.onBlockPlace(evt, blk);
		}
	}
	public Objectiu getCloserObjective(Location l, ArrayList<Objectiu> objectius) {
		Objectiu closerO = objectius.get(0);
		for (Objectiu o : objectius){
			if(closerO.getLocation().distanceSquared(l) > o.getLocation().distanceSquared(l)){
				closerO = o;
			}				
		}
		return closerO;
	}
	protected abstract ArrayList<Objectiu> getDesiredObjectivesTeam(EquipObjectius e);

	public ArrayList<Objectiu> obtenirObjectiusPly(Player ply){
		return ((EquipObjectius) obtenirEquipEnemic(ply)).getObjectius();
	}
	public Equip obtenirEquipObjectiu(Objectiu obj){
		for (Equip e : Equips){
			EquipObjectius eq = (EquipObjectius) e;
			if (eq.getObjectius().contains(obj)){
				return e;
			}
		}
		Bukkit.broadcastMessage("Objectiu sense equip -- null");
		return null;
	}

	public Boolean everyoneAlive(){
		for (Equip e : Equips){
			EquipObjectius eq = (EquipObjectius) e;
			if (eq.checkIntegrity() == false){
				return false;
			}
		}
		return true;
	}
	public void comprovarGuanyador(){
		for (Equip e : Equips){
			EquipObjectius eq = (EquipObjectius) e;
			if (eq.checkIntegrity() == false){
				EquipObjectius eqe = (EquipObjectius) obtenirEquipEnemic(e);
				sendGlobalMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "L'equip " + eqe.getAdjectiu() + " ha guanyat!");
				winGame(eqe);
			}
		}
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		super.updateScoreBoard(ply);
		if (JocIniciat && !JocFinalitzat){
			ArrayList<String> list = new ArrayList<String>();
			Equips.stream().map(e -> (EquipObjectius) e).sorted((e2, e1) -> Integer.compare(e1.getCompletedObjectives().size(), e2.getCompletedObjectives().size())).forEach(eq -> {
				list.add(eq.getChatColor() + "Equip " + eq.getAdjectiu());
				for (Objectiu obj : eq.getObjectius()){
					list.add(obj.getScoreboardStatusLine());
				}
			});
			ScoreBoardUpdater.setScoreBoard(ply, "Estadístiques", list, null);
		}
		if (JocFinalitzat){
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<Integer> values = new ArrayList<Integer>();
			for (Equip e : Equips){
				EquipObjectius eq = (EquipObjectius) e;	
				EquipObjectius eqEnemic = (EquipObjectius) obtenirEquipEnemic(e);	
				for (Player p : eq.getPlayers()){
					list.add(eq.getChatColor() + p.getName());
					values.add((int) Math.round(eqEnemic.getObjectiveRatio(p) * 100));
				}
			}
			ScoreBoardUpdater.setScoreBoard(ply, "Objectius (%)", list, values);
		}

	}

	protected int getObjectiveProtectionRadius(){
		int c = 2;
		String pName = "ObjectiveProtectionRadius";
		if (pMapaActual().ExisteixPropietat(pName)){
			c = pMapaActual().ObtenirPropietatInt(pName);	
		}else{
			pMapaActual().EstablirPropietat(pName, c);
		}
		return c;
	}

	@Override
	public double getGameProgressETA() {
		double totalObj = Equips.stream().map(e -> (EquipObjectius)e).map(e -> e.Objectius.size()).mapToInt(i->i).sum();
		double objCompletionRatio = Equips.stream().map(e -> (EquipObjectius)e).map(e -> e.getCompletedObjectives().size()).mapToInt(i->i).sum() / totalObj;
		Supplier<Stream<EquipObjectius>> sortedSt = () -> Equips.stream().map(e -> (EquipObjectius)e).sorted((e2, e1) -> Integer.compare(e1.getCompletedObjectives().size(), e2.getCompletedObjectives().size()));
		double advantageRatio = (sortedSt.get().findFirst().get().getCompletedObjectives().size() - sortedSt.get().skip(1).map(e -> e.getCompletedObjectives().size()).mapToInt(i->i).average().orElse(0)) / Math.max(sortedSt.get().findFirst().get().getObjectius().size() - 1, 1);
		//System.out.println(Math.round(super.getGameProgressETA() * 100) + ", " + Math.round(objCompletionRatio * 100) + ", " + Math.round(advantageRatio * 100));
		return super.getGameProgressETA() * 0.45 + objCompletionRatio * 0.5 + advantageRatio * 0.1; //EXCESS 5%
	}

	public class EquipObjectius extends Equip{
		ArrayList<Objectiu> Objectius =  new ArrayList<Objectiu>();
		public EquipObjectius(DyeColor color, String adj) {
			super(color, adj);
			//initObjectives();
			// TODO Auto-generated constructor stub
		}
		void initObjectives(){
			Objectius = getDesiredObjectivesTeam(this);
		}
		public ArrayList<Objectiu> getObjectius() {
			return Objectius;
		}

		public void setObjectius(ArrayList<Objectiu> objectius) {
			Objectius = objectius;
		}
		public  ArrayList<Objectiu> getAliveObjectives(){
			ArrayList<Objectiu> objs = new ArrayList<Objectiu>();
			for (Objectiu obj : Objectius){
				if (obj.isCompleted() == false){
					objs.add(obj);
				}
			}
			return objs;
		}
		public  ArrayList<Objectiu> getCompletedObjectives(){
			ArrayList<Objectiu> objs = new ArrayList<Objectiu>();
			for (Objectiu obj : Objectius){
				if (obj.isCompleted() == true){
					objs.add(obj);
				}
			}
			return objs;
		}
		public  ArrayList<Objectiu> getCompletedObjectives(Player ply){
			ArrayList<Objectiu> objs = new ArrayList<Objectiu>();
			for (Objectiu obj : getCompletedObjectives()){
				if (obj.getCompleter() == ply){
					objs.add(obj);
				}
			}
			return objs;
		}
		public Double getObjectiveRatio(Player ply){
			Double completed = (double) getCompletedObjectives(ply).size();
			Double total = (double) Objectius.size();
			return completed / total;
		}
		public Double getIntegrity(){
			Double alive = (double) getAliveObjectives().size();
			Double total = (double) Objectius.size();
			//Bukkit.broadcastMessage("Alive: " + getAdjectiu() + Double.toString(alive));
			//Bukkit.broadcastMessage("Total: " + getAdjectiu() + Double.toString(total));
			return alive / total;
		}
		public Boolean checkIntegrity(){
			//Bukkit.broadcastMessage(getAdjectiu() + Double.toString(getIntegrity()));
			return (getIntegrity() > 0);
		}



	}
	public class Objectiu{
		String nom;
		Boolean completed = false;
		String verb = "completat";
		Location location;
		Player completer;
		Object info;
		public Objectiu(String nom, Location l, Object info) {
			super();
			this.nom = nom;
			this.location = l;
			this.info = info;
		}
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public Boolean isCompleted() {
			return completed;
		}
		public void setCompleted(Boolean completed) {
			this.completed = completed;
		}
		public Location getLocation() {
			return location;
		}
		public void setLocation(Location location) {
			this.location = location;
		}
		public Player getCompleter() {
			return completer;
		}
		public boolean canBeCompleted(Player p){
			if(obtenirEquip(p).getId() == obtenirEquipObjectiu(this).getId()){return false;}
			return true;
		}
		public void setCompleter(Player completer) {
			this.completer = completer;
		}
		public Object getInfo() {
			return info;
		}
		public void setInfo(Object info) {
			this.info = info;
		}
		public void anunciarCompletat(Player ply){
			sendGlobalMessage(obtenirEquip(ply).getChatColor() + ply.getName() + ChatColor.WHITE + " ha "+ verb +" l'objectiu " + ChatColor.STRIKETHROUGH + "" + obtenirEquipObjectiu(this).getChatColor() + getNom());
		}
		public void misssatgeError(Player ply){
			sendGlobalMessage(ChatColor.GRAY + "No pots completar aquest objectiu.");
		}
		public void complete(Player completer){
			if (!JocFinalitzat && !isCompleted() && canBeCompleted(completer)){
				setCompleter(completer);
				setCompleted(true);
				getPlayerInfo(completer).setObjectivesCompleted(getPlayerInfo(completer).getObjectivesCompleted() + 1);
				anunciarCompletat(completer);
				playCompletionEffect();
				updateScoreBoards();
				comprovarGuanyador();
			}else{
				misssatgeError(completer);
			}
		}
		public void playCompletionEffect(){
			completer.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 0);
			completer.getWorld().playSound(location, Sound.ENTITY_WITHER_DEATH, 750F, 1.5F);
		}
		public String getScoreboardStatusLine(){
			if (isCompleted()){
				return(ChatColor.STRIKETHROUGH + "" + getNom());
			}else{
				return(ChatColor.YELLOW + "" + getNom());
			}
		}
		protected void onBlockBreak(BlockBreakEvent evt, Block blk) {

		}

		protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {

		}

	}
	public class ObjectiuBlockChange extends Objectiu{
		Material m;
		byte data;
		public ObjectiuBlockChange(String nom, Location l, Material m, byte data) {
			super(nom, l, data);
			this.m = m;
			this.data = data;
		}
		public boolean isInsideProtectionRadius(Location l){
			double dist = l.distance(location);
			//if (dist == 0){return false;}
			//Bukkit.broadcastMessage(Double.toString(dist));
			return dist <= getObjectiveProtectionRadius();
		}

		@Override
		protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
			// TODO Auto-generated method stub
			super.onBlockBreak(evt, blk);
			if(isInsideProtectionRadius(blk.getLocation())){evt.setCancelled(true);
			sendPlayerMessage(evt.getPlayer(), ChatColor.RED + "No pots destrossar el monument");}
		}
		@Override
		protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
			super.onBlockPlace(evt, blk);	
			if(isInsideProtectionRadius(blk.getLocation())){
				Boolean completed = onChange(evt, evt.getPlayer(), blk); 
				if(completed == true){return;}				
				if(!evt.isCancelled())sendPlayerMessage(evt.getPlayer(), ChatColor.RED + "No pots modificar el monument");
				evt.setCancelled(true);
			}
			
		}
		@SuppressWarnings("deprecation")
		public Boolean onChange(BlockPlaceEvent evt, Player p, Block blk){
			if(canBeCompleted(p)){				
				boolean sameType = m == blk.getType();				
				boolean sameData = data == blk.getData();				
				boolean sameLocation = blk.getLocation().equals(location);				
				if(sameType && sameData && sameLocation){					
					complete(p);
					//location.getBlock().setType(Material.GLASS);
					return true;
				}else{
					if(sameLocation){
						sendPlayerMessage(p, "Introdueix la llana corresponent");
						evt.setCancelled(true);
					}
					return false;
				}
			}else{
				return false;
			}
		}
	}
	public class ObjectiuWoolPlace extends ObjectiuBlockChange{
		@SuppressWarnings("deprecation")
		DyeColor color;
		public ObjectiuWoolPlace(String nom, Location l, DyeColor color) {
			super(nom, l, Material.WOOL, color.getWoolData());	
			this.color = color;
		}
		@Override
		public String getScoreboardStatusLine() {
			// TODO Auto-generated method stub
			String checkbox = (" " + ColorConverter.dyeToChat(color) + (isCompleted() ? '\u2588' : '\u2591'));
			return checkbox + " " + ChatColor.WHITE + getNom();
		}
		
	}
	public class ObjectiuBlockBreak extends Objectiu{
		public ObjectiuBlockBreak(String nom, Location l) {
			super(nom, l, null);
			this.verb = "destruït";
		}
		@Override
		protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
			super.onBlockBreak(evt, blk);
			Player p = evt.getPlayer();

			if(blk.getLocation().equals(location)){
				if(canBeCompleted(p)){
					complete(p);
					//location.getBlock().setType(Material.LAVA);
				}else{
					evt.setCancelled(true);
				}
			}

		}
	}


}
