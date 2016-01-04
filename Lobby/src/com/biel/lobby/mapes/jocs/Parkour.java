package com.biel.lobby.mapes.jocs;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TooManyListenersException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.TemporalType;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Stairs;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.Pair;
import com.biel.BielAPI.Utils.Title;
import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.BielAPI.events.WorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocScoreCombo;
import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.mapes.jocs.Parkour.ParkourProvider.ParkourBubble;
import com.biel.lobby.mapes.jocs.Parkour.ParkourProvider.ParkourBubble.Checkpoint;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.Utils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class Parkour extends JocScoreCombo{

	ArrayList<ParkourStream> streams = new ArrayList<ParkourStream>();
	ParkourProvider provider = new ParkourProvider();
	int playerCount = 0;
	int mapLength = 35;
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
	public Vector getBackward(){
		return getForward().multiply(-1);
	}
	public Vector getUp(){
		return new Vector(0, 1, 0);
	}
	public Vector getRight(){
		return getForward().crossProduct(getUp());
	}
	public Vector getLeft(){
		return getRight().multiply(-1);
	}
	public Vector getCenterize(){
		return new Vector(0.5, 0.5, 0.5);
	}
	public Vector getBackRightLeftRandom(){
		switch(Utils.NombreEntre(1, 3)){
		case 1:
			return getBackward();
		case 2:
			return getRight();
		case 3:
			return getLeft();
		}
		return getBackward();
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
		private Location startLocation;
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
		public class BubbleHandler{ // Currentblock = provider !!! - Check other bubbles and interpolate (lag/no detection) - Store position history (as vector)
			int providerBubbleIndex;
			ZonedDateTime firstContactTime;
			ZonedDateTime leaveTime;
			ArrayList<Vector> positions = new ArrayList<Vector>();
			ArrayList<CheckpointHandler> checkpointHandlers = new ArrayList<CheckpointHandler>();
			Score score;
			Hologram h;
			public BubbleHandler(int providerBubbleIndex) {
				super();
				this.providerBubbleIndex = providerBubbleIndex;
				fetchCheckpoints();
			}
			public ParkourBubble getBubble(){
				return provider.getBubble(providerBubbleIndex);
			}
			public void fetchCheckpoints(){
				int i = 0;
				for(Checkpoint c : getBubble().getCheckpoints()){
					checkpointHandlers.add(new CheckpointHandler(i++));
					//sendGlobalMessage("Checkpoint handler created " + i);
				}
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
					//teleportToEndingSpawn(getPlayer());
					getPlayer().setGameMode(GameMode.SPECTATOR);
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
				handleLocationCheckIn(getAvgPosition().toLocation(getWorld()));

			}
			public void handleLocationCheckIn(Location l){
				Player p = getPlayer();
				if(!getPlayers().contains(p))return;
				//Somewhere call advance
				//sendGlobalMessage("providerBubbleIndex: " + providerBubbleIndex);
				//sendGlobalMessage("targetBubbleIndex: " + targetBubbleIndex);
				//getWorld().playEffect(l, Effect.FLAME, 4);

				checkpointHandlers.forEach(ch -> ch.handleCpLocationCheckIn(l));

				if(l.getY() < getBubble().getLowestSurfaceY(startLocation) - 1){ // IMPORTANT UPFACTOR
					registerFail(p);
					return;
				}
				//boolean isAboveBubble = getBubble().getSurfaceBlockList(startLocation).stream().mapToDouble(b -> b.getLocation().add(0.5, 0, 0.5).distance(l)).min().getAsDouble() < 1.3;

				if(firstContactTime != null){
					Duration span = Duration.between(firstContactTime, ZonedDateTime.now());
					long ms = span.toMillis();
					if(ms > 3000 * getBubble().getMultiplier()){
						registerFail(p);
						setLeaveTime();						
					}
				}
			}
			protected void advanceBasedOnTime() {
				if(firstContactTime == null){advance(Score.N100);return;}
				if(leaveTime == null)setLeaveTime();
				Duration span = Duration.between(firstContactTime, leaveTime);
				Score s = Score.N50;
				long ms = span.toMillis();
				//p.sendMessage("ms: " + ms);
				double m = getBubble().getMultiplier();
				if(ms < 1200 * m)s = Score.N100;
				if(ms < 800 * m)s = Score.N200;
				if(ms < 400 * m)s = Score.N300;
				if(ms < 250 * m)s = Score.C300;
				advance(s);
			}
			private void setLeaveTime() {
				leaveTime = ZonedDateTime.now();
			}
			private void setFirstContactTime() {
				firstContactTime = ZonedDateTime.now();
			}
			public void registerFail(Player p) {
				p.teleport(getBubble().getFailTeleportPoint(startLocation));
				advance(Score.FAIL);
			}
			//CHECKPOINT HANDLER
			public class CheckpointHandler{
				int checkpointIndex;
				boolean completed;
				boolean wasPlayerInsideRange = false;
				Hologram ho;
				public CheckpointHandler(int checkpointIndex) {
					super();
					this.checkpointIndex = checkpointIndex;
					updateHologram();
				}
				Checkpoint getCheckpoint(){
					return getBubble().getCheckpoints().get(checkpointIndex);
				}
				public Vector getAbsoluteCheckpointPosition(){
					return getBubble().getEntryPoint().add(startLocation.toVector()).add(getCheckpoint().position);
				}
				boolean isFirst(){
					return checkpointIndex == 0;
				}
				boolean isLast(){
					return checkpointIndex == getBubble().getCheckpoints().size() - 1;
				}
				boolean isAlone(){
					return isFirst() && isLast();
				}
				double getRelativePositionRatio(){
					return ((double)checkpointIndex) / (getBubble().getCheckpoints().size() - 1);
				}
				void onEnter(){
					if(isFirst())setFirstContactTime();
					if(isLast())advanceBasedOnTime();
					if(!isAlone())tryComplete();
				}
				void onLeave(){
					tryComplete();

				}
				void tryComplete(){
					if(!completed)complete();
				}
				void complete(){
					//Completion code
					setLeaveTime();
					completed = true;
					updateHologram();
					incrementCombo(getPlayer(), 0.1);
					playCompletionSound();
				}
				void playCompletionSound(){
					getPlayer().playSound(getPlayer().getEyeLocation(), Sound.SLIME_WALK, 1F, (float) (0.5 + (1.5 * getRelativePositionRatio())));
				}
				public void handleCpLocationCheckIn(Location l){ //Raise onEnter and onLeave events
					Player p = getPlayer();
					if(!getPlayers().contains(p))return;
					//Somewhere call advance
					//getWorld().playEffect(l, Effect.FLAME, 4);
					boolean isPlayerInsideRange = getAbsoluteCheckpointPosition().add(new Vector(0, 1.2, 0)).distance(p.getLocation().toVector()) <= getCheckpoint().radius;

					if(!wasPlayerInsideRange && isPlayerInsideRange){
						onEnter();
					}
					if(wasPlayerInsideRange && !isPlayerInsideRange){
						onLeave();
					}
					wasPlayerInsideRange = isPlayerInsideRange;
				}
				private Location getHologramLocation() {
					return getAbsoluteCheckpointPosition().toLocation(getWorld()).add(0.5, 1.8, 0.5);
				}
				public void createHolgram(){
					if (ho != null){return;}
					//getHologramLocation().getBlock().setType(Material.GOLD_BLOCK);
					ho = HologramsAPI.createHologram(Com.getPlugin(), getHologramLocation());
					////sendGlobalMessage("Hologram created + "  + getHologramDisplayText());
				}
				public void updateHologram(){
					if (ho == null){createHolgram();}
					ho.clearLines();
					ho.appendTextLine(MessageFormat.format("{0}", getHologramDisplayText(), isTargeted() ? "[" : "", isTargeted() ? "]" : ""));					
				}
				String getHologramDisplayText(){
					if(completed)return ChatColor.GOLD + "+ x0.1";
					if(isAlone())return ChatColor.DARK_GREEN + "" +'\u2B07' + ChatColor.DARK_RED + "" +'\u2B06';
					if(isFirst())return ChatColor.DARK_GREEN + "" +'\u2B07';
					if(isLast())return ChatColor.DARK_RED + "" +'\u2B06';
					return ChatColor.GREEN + "+";
				}
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
			ParkourBubble b;
			try {
				b = getRandomBubbleType().getConstructor(ParkourProvider.class).newInstance(this);			
				b.generate();
				if(bubbles.size() > 0){b.setEntryPoint(bubbles.get(bubbles.size() - 1).getAbsoluteExitPoint().add(getRandomBubbleSpacing()));}else{b.setEntryPoint(getForward().multiply(4));}
				bubbles.add(b);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		Class<? extends ParkourBubble> getRandomBubbleType(){
			List<Pair<Class<? extends ParkourBubble>, Double>> registeredBubbleTypes = getRegisteredBubbleTypes();	
			Collections.shuffle(registeredBubbleTypes);
			return registeredBubbleTypes.get(0).getFirst();
			//			int c = 0;
			//			for(Pair<Class<? extends ParkourBubble>, Double> t : registeredBubbleTypes){
			//				if(Utils.Possibilitat(t.getSecond(), 100 + 10 * registeredBubbleTypes.size()))return t.getFirst();
			//				if(c > 50)return registeredBubbleTypes.get(0).getFirst();
			//				c++;
			//			}
			//			return null;
		}
		List<Pair<Class<? extends ParkourBubble>, Double>> getRegisteredBubbleTypes(){
			List<Pair<Class<? extends ParkourBubble>, Double>> r = new ArrayList<Pair<Class<? extends ParkourBubble>, Double>>();
			r.add(new Pair<Class<? extends ParkourBubble>, Double>(SingleBlockBubble.class, 10D));
			r.add(new Pair<Class<? extends ParkourBubble>, Double>(ZigZagBubble.class, 10D));
			r.add(new Pair<Class<? extends ParkourBubble>, Double>(CrossBlockTowerBubble.class, 10D));

			return r;
		}
		public Vector getRandomBubbleSpacing(){
			Vector vert = new Vector(0, 0, 0);
			if(Utils.Possibilitat(10))vert.setY(-1);
			if(Utils.Possibilitat(8))vert.setY(-2);
			if(Utils.Possibilitat(18))vert.setY(1);
			Vector hor = new Vector(0, 1, 0).crossProduct(getForward()).normalize();
			if(Utils.Possibilitat(50))hor.multiply(-1);
			if(Utils.Possibilitat(10))hor.multiply(2);
			if(Utils.Possibilitat(70))hor.multiply(0);
			Vector forward = getForward().multiply(Utils.NombreEntre(3,  4));			
			return vert.add(hor).add(forward);

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

		public abstract class ParkourBubble{ //Single island on sky
			private Vector entryPoint; //Absolute - ISSUES
			ArrayList<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
			ArrayList<Vector> blocks = new ArrayList<Vector>(); //Index matches with material 
			ArrayList<Material> materials = new ArrayList<Material>(); //Index matches with block 
			Function<? super Vector, Material> materialGetter = v -> materials.get(blocks.indexOf(v));

			public ParkourBubble() {
				super();
				// TODO Auto-generated constructor stub
			}
			public abstract void generate();
			public abstract double getMultiplier();
			public Vector getEntryPoint() {
				return entryPoint.clone();
			}
			/**
			 * Sets the center relative to the stream starting point
			 * @param center Absolute center within the stream
			 */
			public void setEntryPoint(Vector center) {
				this.entryPoint = center;
			}
			public Vector getAbsoluteExitPoint(){
				return getEntryPoint().add(checkpoints.get(checkpoints.size() - 1).position);
			}
			public ArrayList<Checkpoint> getCheckpoints() {
				return checkpoints;
			}

			public void buildAt(Location streamStartLocation) {
				Predicate<? super Vector> filterSolid = v -> materialGetter.apply(v).isSolid();
				Consumer<? super Vector> placeAction = v ->  streamStartLocation.clone().add(getEntryPoint()).add(v).getBlock().setType(materialGetter.apply(v));
				blocks.stream().filter(filterSolid).forEach(placeAction); //Solid blocks go first
				blocks.stream().filter(filterSolid.negate()).forEach(placeAction);
			}
			public Location getFailTeleportPoint(Location streamStartLocation){
				Location l = streamStartLocation.clone().add(getAbsoluteExitPoint()).add(0.5, 1, 0.5);
				l.setPitch(0);
				l.setYaw(270);
				return l;
			}
			public List<Block> getBlockList(Location streamStartLocation){
				return blocks.stream().map(v -> streamStartLocation.clone().add(getEntryPoint()).add(v).getBlock()).collect(Collectors.toList());
			}
			public List<Block> getSurfaceBlockList(Location streamStartLocation){
				List<Block> blockList = getBlockList(streamStartLocation);
				//blockList.stream().forEach(b -> b.setType(Material.GOLD_BLOCK)); //  ////.filter(b -> b.getType().isSolid())
				//streamStartLocation.clone().add(entryPoint).getBlock().setType(Material.DIAMOND_BLOCK);
				return blockList.stream().map(b -> b.getRelative(BlockFace.UP)).filter(b -> !blockList.contains(b)).collect(Collectors.toList());
			}
			/**
			 * @param streamStartLocation
			 * @return The lowest Y coordinate
			 */
			public int getLowestSurfaceY(Location streamStartLocation){
				List<Block> surfaceBlockList = getSurfaceBlockList(streamStartLocation);
				return surfaceBlockList.stream().map(Block::getY).mapToInt(i -> i).min().getAsInt();
			}
			public class Checkpoint{
				Vector position;
				double radius;
				public Checkpoint(Vector position, double radius) {
					super();
					this.position = position;
					this.radius = radius;
				}
				public Checkpoint(Vector position) {
					super();
					this.position = position;
					this.radius = 1.2D;
				}
			}
		}
		public class SingleBlockBubble extends ParkourBubble{
			//Single block with a torch
			@Override
			public void generate() {
				blocks.add(new Vector(0, 0, 0));materials.add(Material.QUARTZ_BLOCK);		
				//blocks.add(new Vector(0, 1, 0));materials.add(Material.TORCH);
				checkpoints.add(new Checkpoint(new Vector(0, 0, 0)));
				checkpoints.add(new Checkpoint(new Vector(0, 0, 0)));
			}

			@Override
			public double getMultiplier() {
				// TODO Auto-generated method stub
				return 1.18;
			}
		}
		public class ZigZagBubble extends ParkourBubble{
			//Single block with a torch
			int n = Utils.NombreEntre(3, 7);
			@Override
			public void generate() {
				for (int i = 0; i < n; i++) {
					blocks.add(getForward().multiply(2 * i));materials.add(Material.QUARTZ_BLOCK);
					blocks.add(getForward().multiply(2 * i + 1).add(getRight().multiply(2)));materials.add(Material.QUARTZ_BLOCK);
					if(i < n - 1){
						blocks.add(getForward().multiply(2 * i + 1).add(getUp()));materials.add(Material.FENCE);
						blocks.add(getForward().multiply(2 * i + 2).add(getRight().multiply(2)).add(getUp()));materials.add(Material.FENCE);
					}
				}
				blocks.stream().filter(b -> materialGetter.apply(b) == Material.QUARTZ_BLOCK).forEach(b -> checkpoints.add(new Checkpoint(b, 0.9)));
			}

			@Override
			public double getMultiplier() {
				// TODO Auto-generated method stub
				return 5.38 * n  + 1;
			}
		}
		public class CrossBlockTowerBubble extends ParkourBubble{
			int n = Utils.NombreEntre(3, 7);
			@Override
			public void generate() {
				Vector c = getForward();
				Vector d = getBackRightLeftRandom();
				for (int i = 0; i < n; i++) {
					Vector lc = c.clone().add(getUp().multiply(i));
					blocks.add(lc);materials.add(Material.QUARTZ_BLOCK);
					Vector dlc = lc.clone().add(d);
					blocks.add(dlc);materials.add(Material.QUARTZ_BLOCK);
					checkpoints.add(new Checkpoint(dlc));
					d = GUtils.rotateVector(d, getUp(), Math.PI/2.0);
				}

			}

			@Override
			public double getMultiplier() {
				// TODO Auto-generated method stub
				return 2.5 * n + 2;
			}

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
