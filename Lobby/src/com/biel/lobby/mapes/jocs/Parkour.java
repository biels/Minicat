package com.biel.lobby.mapes.jocs;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.TemporalType;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.Title;
import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.BielAPI.events.WorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocScoreCombo;
import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.mapes.jocs.Parkour.ParkourProvider.ParkourBubble;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.Utils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class Parkour extends JocScoreCombo{

	ArrayList<ParkourStream> streams = new ArrayList<ParkourStream>();
	ParkourProvider provider = new ParkourProvider();
	int playerCount = 0;
	int mapLength = 100;
	@Override
	public String getGameName() {
		// TODO Auto-generated method stub
		return "Parkour";
	}
	@Override
	protected int getBaseSkillUnlockerAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	protected void setCustomGameRules() {
		// TODO Auto-generated method stub

	}
	public Vector getForward(){
		return new Vector(1, 0, 0);
	}
	public Vector getCenterize(){
		return new Vector(0.5, 0.5, 0.5);
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void customJoin(Player ply) {
		// TODO Auto-generated method stub
		super.customJoin(ply);
		//updateStartingPlatforms();
	}
	@Override
	protected void teletransportarTothom() {
		//Generate & TP
		generateStreams();

	}
	public void generateStreams() {
		getPlayers().forEach(p -> streams.add(new ParkourStream(p, p.getLocation().getBlock().getLocation().add(0, -1, 0))));
	}
	protected void updateStartingPlatforms(){
		ArrayList<Player> players = getPlayers();
		if(playerCount == players.size())return;
		Vector mv = new Vector(0, 1, 0).crossProduct(getForward());
		int maxIndex = players.size() - 1;
		mv.multiply(maxIndex * 8);
		Location fCenter = world.getSpawnLocation().add(getForward().clone().multiply(7));
		Cuboid cuboid = new Cuboid(fCenter.clone().add(mv).add(getForward().clone().multiply(-1)), fCenter.clone().add(mv.multiply(-1)).add(getForward().clone().multiply(1)).add(0, -2, 0));
		cuboid.getBlocks().forEach(b -> {if(!b.isEmpty())b.breakNaturally();});
		for(Player p : players){
			int index = players.indexOf(p);
			Vector v = mv.clone();
			Location l = fCenter.clone().add(v.clone().multiply(index / (double) (maxIndex == 0 ? 1 : maxIndex))).add(v.clone().multiply(-0.5));
			//streams.add(new ParkourStream(p, l));
			l.getBlock().getRelative(BlockFace.DOWN).setType(Material.COAL_BLOCK);

			p.teleport(l);
			//streams.add(new ParkourStream(p, l));
		}
		playerCount = players.size();
	}
	protected void teleportToEndingSpawn(Player p){
		p.teleport(pMapaActual().ObtenirLocation("endSpawn", world));
	}
	public void comprovarFinish(){
		//boolean allFinished = streams.stream().mapToInt(ParkourStream::getTargetBubbleIndex).min().getAsInt() > 100;
		boolean allFinished = getPlayers().stream().map(p -> getPlayerInfo(p).isInGame()).allMatch(b -> b == false);
		if(allFinished)comprovarGuanyador();
	}
	@Override
	public void heartbeat() {
		// TODO Auto-generated method stub
		super.heartbeat();
		if(!JocIniciat){
			updateStartingPlatforms();
		}
		if(JocIniciat){
			streams.forEach(s -> s.tick());
			comprovarFinish();
		}
	}
	@Override
	public void ultraHeartbeat() {
		// TODO Auto-generated method stub
		super.ultraHeartbeat();
		streams.forEach(s -> s.ultraTick());
	}
	@Override
	protected void customJocIniciat() {


	}

	@Override
	protected void customJocFinalitzat() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerMove(evt, p);
		if(!JocIniciat){
			if(!evt.getFrom().getBlock().equals(evt.getTo().getBlock()))evt.setCancelled(true);
		}
	}
	@Override
	protected void onPlayerDamage(EntityDamageEvent evt, Player p) {
		// TODO Auto-generated method stub
		super.onPlayerDamage(evt, p);
		evt.setCancelled(true);
	}
	public class ParkourStream extends PlayerWorldEventBus { //One for each player
		Location startLocation;
		ArrayList<BubbleHandler> handlers = new ArrayList<BubbleHandler>();
		int targetBubbleIndex = 0;
		int playerPosition = 0;
		public ParkourStream(Player ply, Location startLocation) {
			super(ply);
			this.startLocation = startLocation;
			checkBufferBuildStreaming();
		}
		public Player getPlayer(){
			return super.getPlayer();
		}
		public int getTargetBubbleIndex() {
			return targetBubbleIndex;
		}
		//		public void bufferBubbles(){
		//			//Add new generated bubbles as handlers
		//			while(handlers.size() < provider.bubbles.size()){
		//				handlers.add(new BubbleHandler(handlers.size())); // implicit index+1
		//			}			
		//		}
		public void checkBufferBuildStreaming(){
			for (int i = 0; i < 3; i++) {
				int index = targetBubbleIndex + i;
				if(index < handlers.size())continue;
				BubbleHandler bubbleHandler = new BubbleHandler(index);
				bubbleHandler.build();
				handlers.add(bubbleHandler);
			}			
		}
		public BubbleHandler getTargetBubbleHandler(){
			return handlers.get(targetBubbleIndex);
		}
		protected void tick(){
			getPlayer().setExp((targetBubbleIndex /(float) mapLength));			
		}
		protected void ultraTick(){
			getTargetBubbleHandler().handleTick();
		}
		@Override
		protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
			// TODO Auto-generated method stub
			super.onPlayerMove(evt, p);

			checkBufferBuildStreaming();
		}
		public class BubbleHandler{ // Currentblock = povider !!! - Check other bubbles and interpolate (lag/no detection) - Store position history (as vector)
			int providerBubbleIndex;
			ZonedDateTime firstContactTime;
			ZonedDateTime leaveTime;
			ArrayList<Vector> positions = new ArrayList<Vector>();
			Score score;
			Hologram h;
			public BubbleHandler(int providerBubbleIndex) {
				super();
				this.providerBubbleIndex = providerBubbleIndex;
			}
			public ParkourBubble getBubble(){
				return provider.getBubble(providerBubbleIndex);
			}
			public boolean isTargeted(){
				return handlers.indexOf(this) == targetBubbleIndex;
			}
			public boolean isCompleted(){
				return score != null;
			}
			private Location getHologramLocation() {
				// TODO Auto-generated method stub
				return getBubble().getFailTeleportPoint(startLocation).add(0, 0.8, 0);
			}
			public void createHolgram(){
				if (h != null){return;}
				h = HologramsAPI.createHologram(Com.getPlugin(), getHologramLocation());
			}
			public void updateHologram(){
				if (h == null){return;}
				h.clearLines();
				h.appendTextLine((score == null ? (isTargeted() ? ChatColor.GREEN : ChatColor.YELLOW) + "" + ChatColor.BOLD + "+" : score.getFormattedString()));
			}
			public void build(){
				getBubble().buildAt(startLocation);
				createHolgram();
			}
			public void showScore (Score score){
				Title title = new Title(score.getFormattedString(), ChatColor.DARK_AQUA + "x" + getCombo(getPlayer()) , 1,4,2);
				//title.setTitleColor(ChatColor.RED);
				//title.setSubtitleColor(ChatColor.GREEN);
				title.setTimingsToTicks(); // IMPORTANT

				title.send(getPlayer());
			}
			public void advance(Score score){
				ParkourPlayerInfo i = getPlayerInfo(getPlayer());
				if(!i.isInGame()){teleportToEndingSpawn(getPlayer()); return;}
				if(targetBubbleIndex > mapLength && i.isInGame()){
					sendGlobalMessage(getPlayer().getName() + " ha arribat a la meta!");
					teleportToEndingSpawn(getPlayer());
					i.setInGame(false);
					return;
				}
				if(isCompleted()){sendGlobalMessage("S'ha intentat completar una bombolla que ja estava completada.");return;}
				this.score = score;
				incrementScore(getPlayer(), score);
				//getPlayer().sendMessage(score.getFormattedString());
				showScore(score);
				updateHologram();
				targetBubbleIndex++;
				checkBufferBuildStreaming();
			}
			public void handlePlayerMoveEvent(PlayerMoveEvent evt, Player p){
				updateHologram();
			}
			public Vector getAvgPosition(){
				Supplier<Stream<Vector>> streamSupplier =
					    () -> positions.stream()
					    .skip(Math.max(0, positions.size() - 5));
				if(streamSupplier.get().count() == 0)return getPlayer().getLocation().toVector();
				return new Vector(
						streamSupplier.get().mapToDouble(Vector::getX).average().getAsDouble(),
						streamSupplier.get().mapToDouble(Vector::getY).average().getAsDouble(),
						streamSupplier.get().mapToDouble(Vector::getZ).average().getAsDouble());
			}
			public void handleTick(){ //Check sides (dist 1.3 from center)
				Location location = getPlayer().getLocation();
				positions.add(location.toVector());
				//handleLocationCheckIn(getAvgPosition().toLocation(world));
				handleLocationCheckIn(location);
				
			}
			public void handleLocationCheckIn(Location l){
				Player p = getPlayer();
				if(!getPlayers().contains(p))return;
				//Somewhere call advance
				//sendGlobalMessage("providerBubbleIndex: " + providerBubbleIndex);
				//sendGlobalMessage("targetBubbleIndex: " + targetBubbleIndex);
				//getWorld().playEffect(l, Effect.FLAME, 4);
				if(l.getY() < getBubble().getLowestSurfaceY(startLocation) - 1){ // IMPORTANT UPFACTOR
					registerFail(p);
					return;
				}
				boolean isAboveBubble = getBubble().getSurfaceBlockList(startLocation).stream().mapToDouble(b -> b.getLocation().add(0.5, 0, 0.5).distance(l)).min().getAsDouble() < 1.3;
						//.contains(l.getBlock());
				boolean isInGroundContact = isAboveBubble; //&& ((LivingEntity)p).isOnGround();
				if(firstContactTime == null && isInGroundContact){
					//Register contact start
					firstContactTime = ZonedDateTime.now();
				}
				if(firstContactTime != null && !isAboveBubble){
					//RegisterLeave
					leaveTime = ZonedDateTime.now();
					Duration span = Duration.between(firstContactTime, leaveTime);
					Score s = Score.N50;
					long ms = span.toMillis();
					//p.sendMessage("ms: " + ms);
					double m = 1.18;
					if(ms < 1200 * m)s = Score.N100;
					if(ms < 800 * m)s = Score.N200;
					if(ms < 400 * m)s = Score.N300;
					if(ms < 250 * m)s = Score.C300;
					advance(s);
				}
				if(firstContactTime != null && isAboveBubble){
					Duration span = Duration.between(firstContactTime, ZonedDateTime.now());
					long ms = span.toMillis();
					if(ms > 3000){
						registerFail(p);
						leaveTime = ZonedDateTime.now();						
					}
				}
			}
			public void registerFail(Player p) {
				p.teleport(getBubble().getFailTeleportPoint(startLocation));
				advance(Score.FAIL);
			}

		}
	}
	public class ParkourProvider{ //Single instnace
		//		ParkourModule currentModule;
		ArrayList<ParkourBubble> bubbles = new ArrayList<ParkourBubble>();
		public ParkourBubble getBubble(int index){
			if(bubbles.size() > index){
				return bubbles.get(index);
			}else{
				while(!(bubbles.size() > index))generateNextBubble();
				return getBubble(index);
			}
		}
		public void generateNextBubble(){
			ParkourBubble b = new ParkourBubble();
			
			Vector vert = new Vector(0, 0, 0);
			if(Utils.Possibilitat(10))vert.setY(-1);
			if(Utils.Possibilitat(8))vert.setY(-2);
			if(Utils.Possibilitat(18))vert.setY(1);
			Vector hor = new Vector(0, 1, 0).crossProduct(getForward()).normalize();
			if(Utils.Possibilitat(50))hor.multiply(-1);
			if(Utils.Possibilitat(10))hor.multiply(2);
			if(Utils.Possibilitat(70))hor.multiply(0);
			if(bubbles.size() > 0){b.setCenter(bubbles.get(bubbles.size() - 1).getCenter().add(getForward().multiply(Utils.NombreEntre(3, 4))).add(vert).add(hor));}else{b.setCenter(getForward().multiply(4));}
			b.generate();
			bubbles.add(b);
		}
		//		public class ParkourModule{ //Set of bubbles
		//			ArrayList<ParkourBubble> bubbles = new ArrayList<ParkourBubble>();
		//			Vector startPoint;
		//			public ParkourBubble getNextBubble(){
		//				return null;				
		//			}
		//			public void generateBubbles(){
		//				
		//			}
		//		}

		public class ParkourBubble{ //Single island on sky
			Vector center; //Absolute - ISSUES
			ArrayList<Vector> blocks = new ArrayList<Vector>();
			public void generate(){
				blocks.add(new Vector(0, 0, 0));
			}
			public Vector getCenter() {
				return center.clone();
			}
			public void setCenter(Vector center) {
				this.center = center;
			}
			public void buildAt(Location streamStartLocation){
				for(Vector v : blocks){
					streamStartLocation.clone().add(center).add(v).getBlock().setType(Material.QUARTZ_BLOCK);
				}
			}
			public Location getFailTeleportPoint(Location streamStartLocation){
				Location l = streamStartLocation.clone().add(center).add(0.5, 1, 0.5);
				l.setPitch(0);
				l.setYaw(270);
				return l;
			}
			public List<Block> getBlockList(Location streamStartLocation){
				return blocks.stream().map(v -> streamStartLocation.clone().add(center).add(v).getBlock()).collect(Collectors.toList());
			}
			public List<Block> getSurfaceBlockList(Location streamStartLocation){
				List<Block> blockList = getBlockList(streamStartLocation);
				return blockList.stream().map(b -> b.getRelative(BlockFace.UP)).filter(b -> !blockList.contains(b)).collect(Collectors.toList());
			}
			public int getLowestSurfaceY(Location streamStartLocation){
				List<Block> surfaceBlockList = getSurfaceBlockList(streamStartLocation);
				return surfaceBlockList.stream().map(Block::getY).mapToInt(i -> i).min().getAsInt();
			}
		}
		public class BasicBubble extends ParkourBubble{

		}
	}
	@Override
	public ParkourPlayerInfo getPlayerInfo(Player p) {
		return getPlayerInfo(p, ParkourPlayerInfo.class);		
	}
	public class ParkourPlayerInfo extends JocScoreComboPlayerInfo{
		public ParkourStream getStream(){
			return streams.stream().filter(s -> s.getPlayer().equals(getPlayer())).findFirst().get();
		}
	}
}
