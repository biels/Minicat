package com.biel.lobby.mapes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.biel.lobby.Com;
import com.biel.lobby.utilities.BUtils;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public abstract class JocTeamDominion extends JocEquips {
	public ArrayList<ControlPoint> ControlPoints = new ArrayList<ControlPoint>();
	public enum GameGoalType{CaptureAll, LifeDraining, ScoreRace}
	@Override
	protected void customJocIniciat() {
		// TODO Auto-generated method stub
		super.customJocIniciat();
		initControlPoints();
	}

	protected void initControlPoints(){
		ControlPoints = getDesiredControlPoints();
		for (ControlPoint p : ControlPoints){
			p.init();
		}
	}
	protected abstract ArrayList<ControlPoint> getDesiredControlPoints();

	protected GameGoalType getGameGoal(){
		return GameGoalType.CaptureAll;
	}
	protected void processControlPoints(){
		for (ControlPoint p : ControlPoints){
			p.processStandingPlayers();
			p.incrementTeamScore();
			p.updateHologram();
		}
	}
	protected Equip getWinnerTeam(){ //
		if(ControlPoints.size() == 0){return null;}
		if (getGameGoal() == GameGoalType.CaptureAll) {
			Equip ownerTeam = ControlPoints.get(0).getOwnerTeam();
			if(ownerTeam == null){return null;}
			int winner = ownerTeam.getId();
			boolean allCaptured = true;
			for (ControlPoint p : ControlPoints) {
				if (!(p.getOwnerTeam().getId() == winner && p.isCaptured())) {
					return null;
				}
			}
			return obtenirEquip(winner);
		}
		if (getGameGoal() == GameGoalType.ScoreRace) {
			for(Equip e : Equips){
				EquipDominion ed = (EquipDominion)e;
				if(ed.hasWon()){
					return e;
				}
			}
		}
		return null;
	}
	protected void comprovarGuanyador(){
		Equip winnerTeam = getWinnerTeam();
		if (winnerTeam != null){
			sendGlobalMessage(ChatColor.GRAY + "L'equip " + winnerTeam.getChatColor() + winnerTeam.getAdjectiu() + ChatColor.GRAY + " ha guanyat la partida!");
			//teamWinAction(winnerTeam);
			winGame(winnerTeam);
		}
	}
	@Override
	public void heartbeat() {
		// TODO Auto-generated method stub
		super.heartbeat();
		if(JocIniciat){
			processControlPoints();
			updateScoreBoards();
			if(getHeartbeatCount() % 2 == 0)comprovarGuanyador();
		}	
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		if (JocIniciat){
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<Integer> values = new ArrayList<Integer>();
			list.add("[Màxim]");
			values.add(Math.round(getMaxHealth()));
			for(Equip e : Equips){
				try {
					list.add(e.getDisplayName());
					EquipDominion eq = (EquipDominion)e;
					values.add(Math.round(eq.getHealth()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}
			for (ControlPoint p : ControlPoints){			
				String finalString = p.getDisplayName();

				if (finalString.length() > 16){
					finalString = finalString.substring(0, 16);
				}
				list.add("+ " + finalString);
				values.add(Math.round(p.getPercent()));
			}

			ScoreBoardUpdater.setScoreBoard(getPlayers(), "Dominion" + ChatColor.RED + " %", list, values);
		}
	}
	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
			Player killer) {
		// TODO Auto-generated method stub
		super.onPlayerDeathByPlayer(evt, killed, killer);
		if(getGameGoal() == GameGoalType.ScoreRace){
			EquipDominion ekr = (EquipDominion) obtenirEquip(killer);
			EquipDominion ekd = (EquipDominion) obtenirEquip(killed);
			if(ekr != null && ekd != null){
				if(ekr != ekd){
					int basePunish = 14 - getPlayers().size();
					if(basePunish < 0)basePunish = 0;
					int punishPoints = (int) Math.ceil(basePunish * Math.sqrt(ekd.getPercent() / 100) * getBalancingMultiplier(ekr));					
					ekd.setHealth(ekd.getHealth() - punishPoints);
					if(punishPoints != 0)evt.setDeathMessage(evt.getDeathMessage() + ChatColor.GOLD + " [-" + Integer.toString(punishPoints) + "]");
				}
			}
		}
	}
	public float getMaxHealth(){
		return 500F + ControlPoints.size() * 150F;
	}
	protected class EquipDominion extends Equip{
		Float health = 0F;
		public EquipDominion(DyeColor color, String adj) {
			super(color, adj);
			// TODO Auto-generated constructor stub
		}
		public Float getHealth() {
			return health;
		}
		public void setHealth(Float health) {
			float[] array = {10F, 25F, 50F, 75F, 90F, 95F};
			for (int i = 0; i < array.length; i++) {
				broadcastPoint(array[i], getPercent(), getPercent(health));
			}
			this.health = health;
			if(this.health < 0)this.health = 0F;
		}
		public void broadcastPoint(Float value, Float oldValue, Float newValue){
			if(Utils.testPointUpDown(value, oldValue, newValue)){
				sendGlobalMessage(ChatColor.GRAY + "L'equip " + getAdjectiuColored() + ChatColor.GRAY + " té " + Integer.toString(Math.round(health)) + "/" + Integer.toString(Math.round(getMaxHealth())) + " punts (" + Integer.toString(Math.round(getPercent())) + "%)");
			}
		}
		public boolean getIntegrity(){
			return health > 0;
		}
		public float getMaxHealth(){
			return 500F + ControlPoints.size() * 150F;
		}

		public boolean hasWon(){
			return health >= getMaxHealth();
		}
		public float getPercent(Float health){
			return (health / getMaxHealth()) * 100;
		}
		public float getPercent(){
			return getPercent(this.health);
		}
	}
	protected abstract class ControlPoint{
		String name;
		float percent = 0;
		float captureRateMultiplier = 1F;
		int ownerTeam = -1;
		int dominatingTeamID = -1;
		int dominationPower = 0;
		Hologram h;
		boolean captured = false;
		int tendency = 0;
		float basePointReward = 3F;
		public ControlPoint(String name) {
			super();
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public boolean isCaptured(){
			return captured;
		}
		public float getPercent() {
			return percent;
		}
		public void setPercent(int percent) {
			this.percent = percent;
		}
		public float getCaptureRateMultiplier() {
			return captureRateMultiplier;
		}
		public void setCaptureRateMultiplier(float captureRateMultiplier) {
			this.captureRateMultiplier = captureRateMultiplier;
		}
		public float getBasePointReward() {
			return basePointReward;
		}
		public void setBasePointReward(float basePointReward) {
			this.basePointReward = basePointReward;
		}
		public void setOwnerTeam(int ownerTeam) {
			this.ownerTeam = ownerTeam;
		}
		public void init(){
			createHolgram();
		}
		public void createHolgram(){
			if (h != null){return;}
			h = HologramsAPI.createHologram(Com.getPlugin(), getHologramLocation());
		}
		public void updateHologram(){
			if (h == null){return;}
			h.clearLines();
			h.appendTextLine(ChatColor.BOLD + getDisplayName());
			if (percent != 100) {
				h.appendTextLine(getProgressBar());
			}
			h.appendTextLine(ChatColor.AQUA + Integer.toString(Math.round(percent)) + "%");
			if(captured){
				h.appendTextLine(ChatColor.GOLD + "+" + Float.toString(Math.round(getPointReward() * 10) / 10F) + " punts/s");
			}
		}
		private float getPointReward() {
			if (getOwnerTeam() == null){return basePointReward;}
			return (float) (basePointReward * getBalancingMultiplier(getOwnerTeam()));
		}
		public String getProgressBar(){
			Character c = '\u2B1B';
			int n = 10;
			int colorPoint = Math.round(percent / n);
			Equip ownerTeam = getOwnerTeam();
			String r = "";
			ChatColor tcolor;
			if(ownerTeam != null){			
				tcolor = ownerTeam.getChatColor();
				r += tcolor; 
			}
			for (int i = 0; i < n; i++) {
				if(i == colorPoint){r += ChatColor.GRAY;}
				r += c;
			}
			return r;
		}
		public abstract Location getHologramLocation();
		public abstract ArrayList<Player> getStandingPlayers();
		public Equip getOwnerTeam(){
			if(ownerTeam == -1){return null;}
			return obtenirEquip(ownerTeam);
		}
		public Equip getDominatingTeam(){
			if(dominatingTeamID == -1){return null;}
			return obtenirEquip(dominatingTeamID);
		}
		public String getDisplayName(){
			Equip ownerTeam = getOwnerTeam();
			ChatColor color =  ChatColor.GRAY;
			String ending = getCapturingNameModifier();
			if (ownerTeam != null && captured) {color = ownerTeam.getChatColor();}
			return color + name + ending;
		}
		protected String getCapturingNameModifier(){
			if (tendency != 0 && percent != 100){
				Equip ownerTeam = getOwnerTeam();
				EquipDominion dominatingTeam = (EquipDominion) getDominatingTeam();				
				boolean up = true;
				up = tendency == 2 ? true : false;
				Character c = (up ? '\u25B2' : '\u25BC');
				ChatColor chatColor = dominatingTeam.getChatColor();
				String multDisplay = "";
				if(dominationPower > 1){
					multDisplay = Integer.toString(dominationPower);
				}
				return chatColor + "[" + multDisplay + c + "]";
			}
			return "";
		}
		protected void onCapture(int teamID){
			captured = true;
			playCaptureEffect();
			sendActionMessage("capturat");
		}
		protected void onNeutralize(){
			captured = false;
			playNeutralizeEffect();
			sendGlobalMessage("El punt de control " + ChatColor.YELLOW + getName() + ChatColor.GRAY + " ha estat neutralitzat");
		}
		protected void onPercentChange(){
			renderEnvironment();
		}
		protected abstract void renderEnvironment();
		public void sendActionMessage(String action) {
			sendGlobalMessage("L'equip " + getOwnerTeam().getAdjectiuColored() + ChatColor.GRAY + " ha " + action + " " + ChatColor.YELLOW + getName().toLowerCase());
		}
		public void incrementTeamScore(){
			if (getGameGoal() == GameGoalType.ScoreRace) {
				if (ownerTeam != -1 && captured) {
					EquipDominion t = (EquipDominion) getOwnerTeam();
					t.setHealth(t.getHealth() + getPointReward());
					playIncrementScoreEffect();
				}
			}
		}
		public abstract void playIncrementScoreEffect();
		public abstract void playCaptureEffect();
		public abstract void playNeutralizeEffect();
		public void processStandingPlayers(){
			ArrayList<ArrayList<Player>> orderedPlayerGroups = getOrderedPlayerGroups();
			updateLocalVariables(orderedPlayerGroups);			
			double incrementStep = Math.sqrt(dominationPower) * captureRateMultiplier;
			double regenertationPower = 0.5D * captureRateMultiplier;
			float newpercent = percent;
			if(captured && (dominatingTeamID == ownerTeam || dominationPower == 0)){newpercent += regenertationPower;}
			if(!captured && dominationPower == 0){newpercent -= regenertationPower;}
			//if(dominationPower > 0){
			if(ownerTeam == -1){ownerTeam = dominatingTeamID;}
			if(dominatingTeamID != -1){
				if(dominatingTeamID == ownerTeam){					
					if (newpercent >= 100 && !captured) {
						onCapture(dominatingTeamID);
					}
					if (newpercent < 100) {					
						newpercent += incrementStep;
						tendency = 2;
					}
				}else{
					if (newpercent > 0) {
						newpercent -= incrementStep;
						tendency = 1;
					}
					if (percent <= 0){
						ownerTeam = dominatingTeamID;
					}
					if (newpercent == 0 && captured) {onNeutralize();}
				}

			}else{tendency = 0;}
			if(newpercent > 100){newpercent = 100;}
			if(newpercent < 0){newpercent = 0;}
			if (percent != newpercent){percent = newpercent; onPercentChange();}
			//}
		}
		private void updateLocalVariables(
				ArrayList<ArrayList<Player>> orderedPlayerGroups) {
			dominatingTeamID = getDominatingTeamID(orderedPlayerGroups);
			dominationPower = getDominationPower(orderedPlayerGroups);
		}
		public int getDominatingTeamID(ArrayList<ArrayList<Player>> orderedPlayerGroups){
			if(orderedPlayerGroups.size() > 0){
				ArrayList<Player> group = orderedPlayerGroups.get(0);
				if(group.size() > 0){
					Player player = group.get(0);
					return obtenirEquip(player).getId();
				}
			}
			return -1;
		}
		public int getDominationPower(ArrayList<ArrayList<Player>> orderedPlayerGroups){
			int domiatingTeamPower = 0;
			int everyoneElsePower = 0;
			if(orderedPlayerGroups.size() > 0){
				ArrayList<Player> group = orderedPlayerGroups.get(0);
				domiatingTeamPower = group.size();
			}
			if(orderedPlayerGroups.size() > 1){				
				for(ArrayList<Player> group : orderedPlayerGroups){
					if(orderedPlayerGroups.indexOf(group) == 0){continue;}
					everyoneElsePower += group.size();
				}
			}
			int result = domiatingTeamPower - everyoneElsePower;
			return result;
		}
		public ArrayList<ArrayList<Player>> getOrderedPlayerGroups(){
			ArrayList<ArrayList<Player>> playerGroups = getPlayerGroups();
			Collections.sort(playerGroups, new Comparator<ArrayList<Player>>(){
				@Override
				public int compare(ArrayList<Player> arg0,
						ArrayList<Player> arg1) {
					return Integer.compare(arg1.size(), arg0.size());
				}
			});
			return playerGroups;
		}
		public ArrayList<ArrayList<Player>> getPlayerGroups(){			
			ArrayList<Player> standingPlayers = getStandingPlayers();
			ArrayList<ArrayList<Player>> playerGroups = new ArrayList<ArrayList<Player>>();
			for (Equip e : Equips){
				@SuppressWarnings("unchecked")
				ArrayList<Player> presentTeamPlayers = (ArrayList<Player>) standingPlayers.clone();
				presentTeamPlayers.retainAll(e.getPlayers());
				playerGroups.add(presentTeamPlayers);
			}

			return playerGroups;
		}
	}
	public abstract class PhysicalControlPoint extends ControlPoint{
		public ControlPointRenderer renderer;
		public PhysicalControlPoint(String name, ControlPointRenderer renderer) {
			super(name);
			this.renderer = renderer;
		}
		@Override
		protected void renderEnvironment() {
			// TODO Auto-generated method stub
			renderer.renderEnvironment(this);
		}
		public abstract Location getCenter();
		public abstract double getEquivalentRadius();
		public abstract ArrayList<Player> getStandingPlayers();
		public abstract ArrayList<Block> getEnvironmentBlocks();
	}
	public class RadialControlPoint extends PhysicalControlPoint {
		Location center;
		Double radius;

		public RadialControlPoint(String name, ControlPointRenderer renderer,
				Location center, Double radius) {
			super(name, renderer);
			this.center = center;
			this.radius = radius;
		}
		@Override
		public Location getCenter() {
			return center.clone();
		}

		public void setCenter(Location center) {
			this.center = center;
		}

		public Double getRadius() {
			return radius;
		}

		public void setRadius(Double radius) {
			this.radius = radius;
		}

		@Override
		public ArrayList<Player> getStandingPlayers() {
			return Utils.getNearbyPlayers(center, radius);
		}
		@Override
		public ArrayList<Block> getEnvironmentBlocks() {
			//return BUtils.locListToBlock(Utils.getSphereLocations(center.getBlock().getLocation(), radius, false));
			return Utils.getCylBlocks(getCenter().getBlock().getLocation().add(0, -8, 0), Integer.valueOf((int) Math.round(radius)), (int)(5 * radius), true);
		}

		@Override
		public double getEquivalentRadius() {
			return getRadius();
		}
		@Override
		public void playIncrementScoreEffect() {
			center.getWorld().playEffect(getCenter().add(0, 1, 0), Effect.MOBSPAWNER_FLAMES, 0);

		}
		@Override
		public void playCaptureEffect() {
			for(Location l : Utils.getLocationsCircle(getCenter().add(0, 1, 0), radius / 2, 5)){
				center.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			}	
			center.getWorld().playSound(getCenter(), Sound.PISTON_EXTEND, 1F, 1F);
		}
		@Override
		public void playNeutralizeEffect() {
			for(Location l : Utils.getLocationsCircle(getCenter().add(0, 1, 0), radius / 2, 5)){
				center.getWorld().playEffect(l, Effect.SMOKE, 4);
			}	
			center.getWorld().playSound(getCenter(), Sound.PISTON_RETRACT, 1F, 1F);
		}
		@Override
		public Location getHologramLocation() {
			// TODO Auto-generated method stub
			return getCenter().add(-1, 3, -1);
		}

	}
	//RENDERITZADORS DEL TERRENY
	public abstract class ControlPointRenderer{
		public abstract void renderEnvironment(PhysicalControlPoint p);

	}
	public abstract class DyeColorControlPointRenderer extends ControlPointRenderer{
		@SuppressWarnings("deprecation")
		@Override
		public void renderEnvironment(PhysicalControlPoint p) {
			// TODO Auto-generated method stub
			for (Block b : p.getEnvironmentBlocks()){
				if(b.getType() == Material.WOOL || b.getType() == Material.STAINED_CLAY || b.getType() == Material.STAINED_GLASS || b.getType() == Material.STAINED_GLASS_PANE){
					b.setData(getDyeColorForBlock(p, b));
				}
			}
		}
		public abstract byte getDyeColorForBlock(PhysicalControlPoint p, Block b);
	}
	public class PieControlPointRenderer extends DyeColorControlPointRenderer{
		@Override
		public byte getDyeColorForBlock(PhysicalControlPoint p, Block b) {
			// TODO Auto-generated method stub
			Slice sliceForBlock = getSliceForBlock(p, b);
			if (sliceForBlock != null) {
				return sliceForBlock.color;
			}
			return 0;
		}
		@SuppressWarnings("deprecation")
		public ArrayList<Slice> getSlices(PhysicalControlPoint p){
			ArrayList<Slice> slices = new ArrayList<Slice>();
			Equip ownerTeam = p.getOwnerTeam();
			DyeColor color =  DyeColor.WHITE;
			if (ownerTeam != null) {color = ownerTeam.getColor();}
			slices.add(new Slice(p.getPercent(), color.getWoolData()));
			slices.add(new Slice(100 - p.getPercent(), DyeColor.WHITE.getWoolData()));
			return slices;
		}
		public Slice getSliceForBlock(PhysicalControlPoint p, Block b){
			ArrayList<Slice> slices = getSlices(p);
			double total = 0.0D;
			for (Slice s : slices) {
				total += s.value;
			}
			double startAngle = 0.0D;
			int x = b.getX();
			int z = b.getZ();
			double sqrt = Math.sqrt((z*z + x*x) + 1);
			double cosine = x/sqrt;
			double currentAngle = Math.acos(cosine);
			double curAngle = startAngle;
			for (Slice s : slices) {
				double arcAngle =  ((s.value / total) * 2 * Math.PI);
				double endAngle = curAngle + arcAngle;
				if(currentAngle >= curAngle && currentAngle <= endAngle){
					return s;
				}
				curAngle += arcAngle;
			}
			return null;
		}
		class Slice {
			double value;
			byte color;

			public Slice(double value, byte color) {
				this.value = value;
				this.color = color;
			}
		}

	}
	public class BubbleControlPointRenderer extends DyeColorControlPointRenderer{
		byte neutralColor = 0;
		@Override
		public byte getDyeColorForBlock(PhysicalControlPoint p, Block b) {
			float multiplier = p.getPercent() / 100.0F;			
			DyeColor color =  DyeColor.CYAN;
			double intRadius = p.getEquivalentRadius() * Math.sqrt(multiplier);
			Location center = p.getCenter().getBlock().getLocation();
			Location l = b.getLocation();
			double distance = Utils.distanceXZ(center, l);
			if(distance < intRadius){
				Equip ownerTeam = p.getOwnerTeam();
				if (ownerTeam != null) {color = ownerTeam.getColor();}
			}
			return color.getWoolData();
		}
		
	}
}
