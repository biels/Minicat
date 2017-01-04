package com.biel.lobby.mapes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Wool;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.BielAPI.Utils.RecallUtils;
import com.biel.lobby.Com;
import com.biel.lobby.utilities.ColorConverter;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.PlayerData;
import com.google.common.collect.Lists;

public abstract class JocEquips extends Joc {
	private boolean equipsBloquejats = false;
	public ArrayList<Equip> Equips = new ArrayList<>();
	public enum TeamGenerationMode{DEFAULT, RANDOM, BALANCED, CUSTOM}
	public TeamGenerationMode generationMode = TeamGenerationMode.DEFAULT;
	public JocEquips() {
		super();
	}
	@Override
	public void initialize() {
		super.initialize();
		initTeams();
	}
	public void initTeams(){
		ArrayList<Equip> desiredTeams = getDesiredTeams();
		if(desiredTeams == null)desiredTeams = getDesiredTeamsFromFile();
		Equips = desiredTeams;
	}
	@Override
	protected void customJocFinalitzat() {
		planificarReseteig(20 * 20);
		raisePlayersToSpectatorZone();
	}
	@Override
	protected void customJocIniciat() {
		if(generationMode == TeamGenerationMode.DEFAULT)ferEquipsEquilibrats();
		if(generationMode == TeamGenerationMode.CUSTOM && getPlayersOutOfTeam().size() != 0){
			sendGlobalMessage("Mode selecció d'equips personalitzats, siusplau, seleccioneu els vostres equips abans de començar la partida.");
			anunciarEquips(null);
		}
		anunciarEquips(null);	
		fixarSpawns();
		//establirColorsNoms();
		delayedUpdateHeadColors();
		updateScoreBoards();
		
	}
	public void winGame(Equip e){ //TODO
		if(won)return;
		if(e == null){			
			JocFinalitzat();
			Bukkit.broadcastMessage("La partida a " + getGameName() + " ha finalitzat sense guanyadors");
			return;
		}
		won = true;
		Bukkit.broadcastMessage(ChatColor.GRAY + "L'equip " + e.getAdjectiuColored() + ChatColor.GRAY + " ha guanyat a " + ChatColor.YELLOW + getGameName());
		anunciarEquips(e);
		matchData.registerEnd(e);
		JocFinalitzat();
		updateElo(e.getPlayers());
	}
	@Override
	double getEloM() {
		double m = 1 - Math.sqrt(getAvgNumericDeviation());
		if(getAvgNumericDeviation() > 0.3)m = 0;
		return m;
	}
	public double getAvgNumericDeviation() {
		return Math.abs(getAvgTeamSize() - Math.round(getAvgTeamSize()));
	}
	public double getAvgTeamSize() {
		return Equips.stream().mapToInt(e -> e.getPlayers().size()).average().getAsDouble();
	}
	protected void raisePlayersToSpectatorZone(){
		for(Player p : getPlayers()){
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(getHalfwayMiddle().add(0, 12, 0));
		}
	}
	private void delayedUpdateHeadColors() {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Com.getPlugin(), () -> updateHeadColors(), 20);
	}
	protected void establirColorsNoms(){
		for (Equip e : Equips){
			for (Player p : e.getPlayers()){
				p.setDisplayName(e.getChatColor() + p.getName() + ChatColor.WHITE);
			}
		}
	}
	protected abstract ArrayList<Equip> getDesiredTeams();
	@SuppressWarnings("unchecked")
	protected ArrayList<Equip> getDesiredTeamsFromFile(){
		ArrayList<Equip> teams = new ArrayList<>();
		ArrayList<String> teamInfos = pMapaActual().ObtenirArray("e");
		if(teamInfos.size() == 0)return null;
		for(String s : teamInfos){
			Class<? extends Equip> cls = getCustomTeamClass();
			Constructor<?>[] constructors = cls.getConstructors();
			for (Constructor<?> constructor : constructors) {
				Class<?>[] parameterTypes = constructor.getParameterTypes();
				String[] split = s.split(";");
				if (parameterTypes.length - 1 != split.length) continue;
				ArrayList<Object> initargs = new ArrayList<>();
				initargs.add(this);
				for (int i = 0; i < split.length; i++) {
					String p = split[i];
					Class<?> pType = parameterTypes[i + 1];
					try {
						//initargs.add(pType.cast(p));
						Object arg = null;
						if (pType.isEnum()) {
							@SuppressWarnings({"rawtypes"})
							Class<? extends Enum> eType = (Class<? extends Enum>) pType;
							//eType.
							arg = Enum.valueOf(eType, p);
						}

						if (pType.equals(String.class)) {
							arg = p;
						}
						initargs.add(arg);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
				}
				try {
					constructor.setAccessible(true);
					//teams.add((Equip) constructor.newInstance(this, DyeColor.BLACK, DyeColor.BLUE, "D"));
					teams.add((Equip) constructor.newInstance(initargs.toArray()));
				} catch (InstantiationException | InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return teams;
	}
	@SuppressWarnings("unchecked")
	public Class<? extends Equip> getCustomTeamClass(){
		for(Class<?> c : this.getClass().getDeclaredClasses()){
			//Bukkit.broadcastMessage(c.getSimpleName());
			if(Equip.class.isAssignableFrom(c))return (Class<? extends Equip>) c;
		}
		return Equip.class;
	}
	@Override
	protected void teletransportarTothom(){
		for (Equip e : Equips){
			e.teleportToTeamSpawn();
		}
//		getHalfwayMiddle().getBlock().setType(Material.GOLD_BLOCK);
//		for(Player p : getPlayers()){
//			p.teleport(getHalfwayMiddle().add(0, 30, 0));
//		}
	}
	public void updateHeadColors(){
		for (Equip e : Equips){
			for (Player p : e.getPlayers()){
				Com.setHeadColor(p, ColorConverter.chatToRaw(e.getChatColor()));
			}
		}
	}
	public void updateHeadColor(Player p){
		Equip e = obtenirEquip(p);
		if (e != null) {
			Com.setHeadColor(p, ColorConverter.chatToRaw(e.getChatColor()));
		}
	}
	public void establirEquipJugador(Player ply, Equip eq){
		if (!eq.getPlayers().contains(ply)){
			for (Equip e : Equips){
				if (e == eq){
					e.addPlayer(ply);
				}else{
					e.removePlayer(ply);
				}
			}
			sendGlobalMessage(ply.getName() + " és a l'equip " + eq.getChatColor() + eq.getAdjectiu());
			updateScoreBoards();
			ScoreBoardUpdater.updateTeamScore(this);
			updateHeadColor(ply);
			updateScoreBoards();
		}

	}
	@SuppressWarnings("unchecked")
	public <T extends Equip> T obtenirEquip(Player ply, Class<T> type){
		if (ply == null){System.out.println("Null player");return null;}
		String name = ply.getName();
		for (Equip e : Equips){

			if (Utils.containsPlayerByName(e.getPlayers(), name)){
				return (T) e;
			}

		}
		if(JocIniciat){
			//Bukkit.broadcastMessage("Alerta: " + name + " no �s a cap equip! -- Null");
		}
		return null;
	}
	public Equip obtenirEquip(Player ply){
		return obtenirEquip(ply, Equip.class);
	}
	public Equip obtenirEquip(int id){
		return Equips.get(id);
	}
	public Equip obtenirEquipEnemic(Player ply){
		return obtenirEquipEnemic(ply, Equip.class);
	}
	public <T extends Equip> T obtenirEquipEnemic(Player ply, Class<T> type){
		return obtenirEquipEnemic(obtenirEquip(ply, type));
	}
	@SuppressWarnings("unchecked")
	public <T extends Equip> T obtenirEquipEnemic(T e){
		if (Equips.size() == 2){
			for (Equip eq : Equips){
				if (!eq.equals(e)){
					return (T) eq;
				}
			}
		}
		Bukkit.broadcastMessage("+ de 2 equips, no es decideix l'enemic! -- null");
		return null;

	}
	@Override
	public Boolean areAllies(Player ply, Player ply2){
		return obtenirEquip(ply).getPlayers().contains(ply2);
	}
	@Override
	public Boolean areEnemies(Player ply, Player ply2){
		return !areAllies(ply, ply2);
	}
	@Override
	public ArrayList<Player> getEnemies(Player p) {
		ArrayList<Player> enemies = getViewers();
		enemies.removeAll(obtenirEquip(p).getPlayers());
		return enemies; //Futurs espectadors
	}
	public void fixarSpawn(Player ply){
		if (obtenirEquip(ply) != null){
			ply.setBedSpawnLocation(obtenirEquip(ply).getTeamSpawnLocation(), true);
		}
	}
	public void fixarSpawns(){
		for (Player p : getPlayers()){
			fixarSpawn(p);
		}
	}
	public Location getHalfwayMiddle(){
		ArrayList<Vector> locs = new ArrayList<>();
		for(Equip e : Equips){
			locs.add(e.getTeamSpawnLocation().toVector());
		}
		return Utils.averageVector(locs).toLocation(getWorld());
	}
	public void sendTeamMessage(Equip e, String message){
		if (world == null){return;}
		for (Player p : e.getPlayers()){
			sendPlayerMessage(p, message);			
		}
	}
	@Override
	protected void updateScoreBoard(Player ply) {
		super.updateScoreBoard(ply);
		if (!JocIniciat){
			ArrayList<String> list = new ArrayList<>();
			ArrayList<Integer> values = new ArrayList<>();
			for (Equip e : Equips){
				list.add(e.getChatColor() + "Equip " + e.getAdjectiu());
				values.add(e.getPlayers().size());
			}
			ScoreBoardUpdater.setScoreBoard(ply, "Equips", list, values);
		}
	}
	public void anunciarEquips(Equip winner){
		Boolean first = true;
		String message = "";
		for (Equip e : Equips){
			
			//VS
			String vsMsg = ChatColor.GOLD + "\n    =====[VS]=====";
			if (!first){
				message = message + vsMsg + "\n";
			}
			
			//Team Color
			if(e == winner) message = message + ChatColor.BOLD + " --> ";
			message = message + e.getChatColor();
			if(getBalancingMultiplier(e) != 1) {
				message = message + "\n     <" + getBalancingMultiplier(e) + ">\n";
			}
			for (Player p : e.getPlayers()) {
				message = message + "     >  " + p.getName() + " ";
			}
			if (e.getPlayers().size() == 0) {
				message = message + "     [-]";
			}
			if(e == winner)message = message + ChatColor.BOLD + " <-- ";
			first = false;
		}
		sendGlobalMessage(message);

	}
	public ArrayList<Player> getPlayersOutOfTeam(){
		ArrayList<Player> outPlayers = new ArrayList<>();
		for (Player p : getPlayers()){
			if (obtenirEquip(p) == null){
				outPlayers.add(p);
			}
		}
		return outPlayers;
	}
	public void resetTeams(){
		initTeams();
		sendGlobalMessage(ChatColor.DARK_BLUE + "Els equips han estat resetejats.");
		generationMode = TeamGenerationMode.DEFAULT;
	}
	public Double getTeamSize(){
		return (getPlayers().size() / (double) Equips.size());
	}
	public void ferEquipsAleatoris(boolean reassignar){
		if(Equips.size() == 0)return;
		//if(reassignar){resetTeams();}
		ArrayList<Player> players = new ArrayList<>(getPlayers());
		Collections.shuffle(players);
		int next_team = 0;
		int cycles = 0;
		int max_cycles = Equips.size() * 4;
		for (Player p : players) {
			if(cycles > max_cycles){return;}
			cycles++;
			if(!reassignar && obtenirEquip(p) != null)continue;
			if (next_team > Equips.size() - 1) {
				next_team = 0;
			}
			Equip eq = Equips.get(next_team);
			if(eq.getPlayers().size() >= Math.ceil(getTeamSize()))continue;
			establirEquipJugador(p, eq);
			next_team++;			
		}
		generationMode = TeamGenerationMode.RANDOM;
	}
	public void ferEquipsEquilibrats(){
		int cycles = 10 + getPlayers().size() * 25;
		if(cycles > 500)cycles = 500;
		if(Equips.size() == 0)return;
		ZonedDateTime startingTime = ZonedDateTime.now();
		sendGlobalMessage("Calculant equips equilibrats...");
		double bestVariance = Double.NaN;
		List<List<Player>> bestTeams = null;
		for (int i = 0; i < cycles; i++) {
			ArrayList<Player> players = new ArrayList<>(getPlayers());
			Collections.shuffle(players);
			List<List<Player>> rTeams = Lists.partition(players, (int)(Math.ceil(players.size() / (double)Equips.size())));
			double variance = variance(rTeams.stream().mapToDouble(t -> t.stream().mapToDouble(p -> new PlayerData(p).getElo()).sum()).boxed().collect(Collectors.toList()));
			if(variance < bestVariance || bestTeams == null){
				bestVariance = variance;
				bestTeams = rTeams;
			}
		}
		resetTeams();
		long millis = Duration.between(startingTime, ZonedDateTime.now()).toMillis();
		sendGlobalMessage(MessageFormat.format("Desviació típica: {0}, Temps: {1}ms, Cicles: {2}, Vel:{3}ms/c", Math.sqrt(bestVariance), millis, cycles, Math.round(millis * 100/(double)cycles) / 100D));
		final List<List<Player>> finalTeams = bestTeams;
		if(finalTeams != null)finalTeams.forEach(t -> t.forEach(p -> establirEquipJugador(p, Equips.get(finalTeams.indexOf(t)))));
		sendGlobalMessage("D: " + getAvgNumericDeviation());
		generationMode = TeamGenerationMode.BALANCED;
	}
	public static double variance(List<Double> values){
		double avg = values.stream().mapToDouble(v -> v).average().orElse(0);
		return values.stream().mapToDouble(v -> Math.pow(v-avg,2)).average().orElse(0);
	}
//	public void ferEquipsEquilibrats(){
//		List<Player> eloSortedPlayers = getPlayers().stream()
//				.sorted((Player p1, Player p2) -> Double.compare(new PlayerData(p1).getElo(), new PlayerData(p2).getElo()))
//				.collect(Collectors.toList());
//		int outPlayers = eloSortedPlayers.size() % Equips.size();
//		//1 - Cycle assign
//		List<Player> cleanlyAssignable = eloSortedPlayers.subList(0, eloSortedPlayers.size() - 1 - outPlayers);
//		for(Equip e : Equips){
//			
//		}
//		//2 - Worst player assignment
//		
//	}
	public double getBalancingMultiplier(Equip e){
		if(e == null)return 0.1;
		if(e.getPlayers().size() == 0)return 1;
		return Math.round((((1D / (e.getPlayers().size() / getTeamSize())) + 1D) / 2D) * 100D) / 100D;
	}
	public double getBalancingMultiplier(Player p){
		return getBalancingMultiplier(obtenirEquip(p));
	}
	public boolean isEquipsBloquejats() {
		return equipsBloquejats;
	}
	public void setEquipsBloquejats(boolean equipsBloquejats) {
		this.equipsBloquejats = equipsBloquejats;
		if (isEquipsBloquejats()){
			sendGlobalMessage(ChatColor.RED + "Els equips han estat bloquejats");
		}else{
			sendGlobalMessage(ChatColor.GREEN + "Els equips han estat desbloquejats");
		}
	}
	void openTemSelectionMenu(final Player ply, final Player objPly, String title){
		IconMenu menu = new IconMenu(title, 27, event -> {
            event.setWillClose(true);
            //Obrir mapa
            int pos = event.getPosition();
            if (pos != 26){
                Equip e = Equips.get(pos);
                establirEquipJugador(ply, e);
                if(generationMode != TeamGenerationMode.CUSTOM){
                    generationMode = TeamGenerationMode.CUSTOM;
                    sendGlobalMessage(ChatColor.YELLOW + ply.getName() + " ha seleccionat un equip. Mode selecció d'equips personalitzats, seleccioneu els vostres equips.");
                }
            }else{
                ply.sendMessage("El mode espectador no està disponible");
                event.setWillClose(false);
            }

        });
		for(Equip eq : Equips){
			Wool wool = new Wool(eq.getColor());
			ItemStack stack = wool.toItemStack();
			//stack.setAmount(eq.getPlayers().size());
			menu.setOption(Equips.indexOf(eq), stack, eq.getChatColor() + "Equip " + eq.getAdjectiu());
		}
		//if (AlgunMapaDisponible() == false){
		menu.setOption(26, new ItemStack(Material.GLASS, 1), ChatColor.GREEN + "Espectador", ChatColor.WHITE + "Entra al grup dels espectadors");
		//}

		menu.open(objPly);
	}
	@Override
	protected void donarItemsPreparatiusGenerals(final Player ply) {
		super.donarItemsPreparatiusGenerals(ply);
		PlayerInventory inventory = ply.getInventory();
		ItemButton button = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BOOKSHELF), ChatColor.YELLOW + "Selecciona l'equip"), ply, event -> {
            if(!hasHostPrivilleges(event.getPlayer())){
                event.getPlayer().sendMessage("No pots seleccionar l'equip. " + "Cal que l'administrador de la partida habiliti els equips personalitzats.");
            }
            if ((!isEquipsBloquejats() || ply.isOp())){
                openTemSelectionMenu(ply, ply, "Unir-se a l'equip...");

            }else{
                event.getPlayer().sendMessage("No pots seleccionar l'equip. Equips bloquejats.");
            }
        });
		inventory.setItem(2, button.getItemStack());
		ItemButton button2 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.COMMAND), ChatColor.YELLOW + "Equips equilibrats"), ply, event -> ferEquipsEquilibrats());
		if(hasHostPrivilleges(ply))inventory.setItem(3, button2.getItemStack());
		ItemButton button3 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.SLIME_BALL), ChatColor.YELLOW + "Equips aleatoris + Inici"), ply, event -> {
            if(canBeStartedBy(ply, true)){
                ferEquipsAleatoris(true);
                JocIniciat();
            }
        });
		if(hasHostPrivilleges(ply))inventory.setItem(4, button3.getItemStack());
		ItemButton button4 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.EMERALD_BLOCK), ChatColor.GREEN + "Netejar equips"), ply, event -> resetTeams());
		if(hasHostPrivilleges(ply))inventory.setItem(5, button4.getItemStack());
//		ItemButton button4 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BONE), ChatColor.GREEN + "Establir equip"), ply, new ItemButton.OptionClickEventHandler() {
//			@Override
//			public void onOptionClick(ItemButton.OptionClickEvent event) {
//				final List<Player> AllPlayers = getPlayers();
//				//AllPlayers.remove(ply);
//				IconMenu menu = new IconMenu("Selecciona jugador", 27, new IconMenu.OptionClickEventHandler() {
//					@Override
//					public void onOptionClick(IconMenu.OptionClickEvent event) {
//						//event.getPlayer().sendMessage("You have chosen " + event.getName());
//						event.setWillClose(false);
//						//Obrir mapa
//						int pos = event.getPosition();
//
//						Player pl = AllPlayers.get(pos);
//						openTemSelectionMenu(ply, pl, "Equip (" + pl.getName() + "");
//
//
//
//					}
//				});
//
//				for(Player p : AllPlayers){
//					ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1);
//					//stack.setAmount(eq.getPlayers().size());
//					menu.setOption(AllPlayers.indexOf(p), stack, ChatColor.AQUA + p.getName(),ChatColor.WHITE +  "Desde: lobby");
//				}
//				//if (AlgunMapaDisponible() == false){
//				menu.setOption(26, new ItemStack(Material.SPONGE, 1), ChatColor.YELLOW + "Afegir tots", ChatColor.WHITE + "Afegeix tots els jugadors del lobby a la partida actual");
//				//}
//
//				menu.open(ply);
//			}
//		});
//		inventory.setItem(5, button4.getItemStack());
//		ItemButton button5 = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.IRON_SPADE), ChatColor.RED + "Bloquejar equips"), ply, new ItemButton.OptionClickEventHandler() {
//			@Override
//			public void onOptionClick(ItemButton.OptionClickEvent event) {
//				setEquipsBloquejats(!isEquipsBloquejats());
//			}
//		});
//		inventory.setItem(6, button5.getItemStack());
		ply.updateInventory();
	}
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		Equip e = obtenirEquip(ply);
		items.add(new ItemStack(Material.STONE_SWORD, 1));
		items.add(new ItemStack(Material.IRON_PICKAXE, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
		ItemStack arc = new ItemStack(Material.BOW, 1); // A stack of diamonds
		arc.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		items.add(arc);
		items.add(new ItemStack(Material.ARROW, 64));
		items.add(new ItemStack(Material.LOG, 64));
		items.add(new ItemStack(Material.GRILLED_PORK, 32));
		return items;
	}
	@Override
	public void giveFixedPlaceItems(Player ply) {
		// TODO Auto-generated method stub
		super.giveFixedPlaceItems(ply);
		if(isRecallEnabled() && getPlayerInfo(ply).getUnselectedSkillAmount() == 0)obtenirEquip(ply).giveRecallButton(ply);
	}
	protected boolean isRecallEnabled(){
		return false;
	}
	@Override
	protected void onPlayerInteractEntity(PlayerInteractEntityEvent evt,
			Player p) {
		super.onPlayerInteractEntity(evt, p);
		if (!JocIniciat){
			if (evt.getRightClicked() instanceof Player){
				Player ply = evt.getPlayer();
				Player clicked = (Player) evt.getRightClicked();
				if (ply.getItemInHand().getType() == Material.BONE){
					openTemSelectionMenu(clicked, ply, "Equip (" + clicked.getName() + ")");
				}
			}
		}
	}
	public Boolean getFriendlyFireEnabled(){
		return false;
	}
	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt,
			Player damaged, Player damager, boolean ranged) {
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if (!getFriendlyFireEnabled()){
			Boolean same = areInSameTeam(damaged, damager);
			if (same){evt.setCancelled(true);}
		}
		if(isUndeBaseProtection(damaged.getLocation().getBlock())){
			//damager.damage(evt.getDamage(), damaged);
			Utils.healDamageable(damaged, evt.getDamage());
			evt.setDamage(0);
			//damager.setVelocity(new Vector(0, GUtils.NombreEntre(0, 4), 0));
			//sendPlayerMessage(damager, ChatColor.GRAY + "Atac a la base no perm�s. El mal retorna a l'emisor, bon viatge.");
		}
	}
	private boolean areInSameTeam(Player p1, Player p2) {
		return obtenirEquip(p1).equals(obtenirEquip(p2));
	}
	public class Equip{
		ArrayList<String> Players = new ArrayList<>();
		DyeColor color = DyeColor.GRAY;
		String adjectiu = "";
		public Equip(DyeColor color, String adj) {
			super();
			this.color = color;
			this.adjectiu = adj;
		}
		public DyeColor getColor() {
			return color;
		}
		public ChatColor getChatColor() {
			return ColorConverter.dyeToChat(color);
		}
		public void setColor(DyeColor color) {
			this.color = color;
		}
		public String getAdjectiu() {
			return adjectiu;
		}
		public String getAdjectiuColored() {
			return getChatColor() + adjectiu;
		}
		public String getDisplayName(){
			if(getPlayers().size() == 1)return getChatColor() + getPlayers().get(0).getName();
			return getChatColor() + "Equip " + getAdjectiu();
		}
		public void setAdjectiu(String adjectiu) {
			this.adjectiu = adjectiu;
		}
		public int getId(){
			return Equips.indexOf(this);
		}
		public ArrayList<Player> getPlayers (){
			ArrayList<Player> returnPlayers = new ArrayList<>();
			for (String ply : Players){
				Player newplayer = Bukkit.getPlayer(ply);
				if(newplayer != null){
					returnPlayers.add(newplayer);
				}

			}
			return returnPlayers;
		}
		void addPlayer(Player ply){
			Players.add(ply.getName());
		}
		void removePlayer(Player ply){
			for (String str : Players){
				String plystr = ply.getName();
				if(str.equals(plystr)){
					Players.remove(str);
				}
			}
		}
		//-------
		public Location getTeamSpawnLocation(){
			return pMapaActual().ObtenirLocation("base" + getId(), getWorld());
		}
		public void teleportToTeamSpawn(){
			for (Player p : getPlayers()){
				teleportToTeamSpawn(p);
			}
		}
		public void teleportToTeamSpawn(Player ply){
			ply.teleport(getTeamSpawnLocation());
		}
		public void giveRecallButton(Player ply){
			ItemStack dBlk = new ItemStack(Material.DIAMOND_BLOCK);
			dBlk.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
			ItemButton button = new ItemButton(Utils.setItemNameAndLore(dBlk, ChatColor.GREEN + "Recall",  ChatColor.WHITE + "Torna el jugador a la base."), ply, event -> RecallUtils.startRecallTeleport(event.getPlayer(), getTeamSpawnLocation()));
			PlayerInventory inventory = ply.getInventory();
			inventory.setItem(8, button.getItemStack());
		}
		public void sendMessage(String text){
			sendTeamMessage(this, text);
		}
	}
	//	CAMPS DE FORÇA -- SUPER-FORCEFIELD
	boolean isForcefieldEnabled(){
		return pMapaActual().ExisteixPropietat("ProtectionRadius");
	}
	enum ForcefieldType{VERTICAL, HORIZONTALX, HORIZONTALZ, RADIAL}
	ForcefieldType getForcefieldType(){
		ForcefieldType r = ForcefieldType.VERTICAL;
		try {
			if(pMapaActual().ExisteixPropietat("ForcefieldType")){
				r = ForcefieldType.valueOf(pMapaActual().ObtenirPropietat("ForcefieldType"));
			}
		} catch (Exception e) {
			sendGlobalMessage("Mala config en ForcefieldType, se usa VERTICAL");
		}
		return r;
	}
	int getProtectionRadius(){
		int r = 10;
		if(pMapaActual().ExisteixPropietat("ProtectionRadius")){
			r = pMapaActual().ObtenirPropietatInt("ProtectionRadius");
		}
		return r;
	}
	ArrayList<Location> getTeamForcefields(Equip e){
		return pMapaActual().ObtenirLocations("LForcefieldTeam" + Integer.toString(e.getId()), world);
	}
	Location normalizeForcefield(Location original, Player p){
		Location l = original.clone();
		if (getForcefieldType() == ForcefieldType.RADIAL){return l;}
		if (getForcefieldType() == ForcefieldType.VERTICAL){l.setY(p.getLocation().getY());return l;}
		if (getForcefieldType() == ForcefieldType.HORIZONTALX){l.setY(p.getLocation().getX());return l;}
		if (getForcefieldType() == ForcefieldType.HORIZONTALZ){l.setY(p.getLocation().getX());return l;}
		return l;
	}
	void keepAwayFromForcefields(Player p){
		//Sempre que estigui activat...
		if (!isForcefieldEnabled()){return;}
		//Mirar si est� en algun forcefield enemic
		int radius = getProtectionRadius();
		ArrayList<Location> enemyForcefields = getTeamForcefields(obtenirEquipEnemic(p));
		for (Location forcefieldOrigin : enemyForcefields){
			Location normalizedForcefield = normalizeForcefield(forcefieldOrigin, p);
			if(p.getLocation().distance(normalizedForcefield) <= radius){
				//Get out !!
				forcefieldKickOff(p, normalizedForcefield);
			}
		}
	}
	void forcefieldKickOff(Player p, Location normalizedForcefield){
		Location ploc = p.getLocation();
		Vector vec = Utils.CrearVector(normalizedForcefield, ploc).normalize().add(new Vector(0, 1, 0)).normalize();
		getWorld().playSound(ploc, Sound.ENTITY_IRONGOLEM_HURT, 1F, 2.2F);
		getWorld().playEffect(ploc, Effect.MOBSPAWNER_FLAMES, 3);
		getWorld().playEffect(ploc.clone().add(new Vector(0, 1, 0)), Effect.MOBSPAWNER_FLAMES, 3);
		//Kick
		Location jumpPoint = ploc.clone().add(vec);
		p.teleport(jumpPoint);
		p.setVelocity(vec);
		//Msg
		sendPlayerMessage(p, ChatColor.RED + "No pots capturar el teu propi objectiu");
	}
	int getBaseProtectionRadius(){
		int r = 3;
		if(pMapaActual().ExisteixPropietat("BaseProtectionRadius")){
			r = pMapaActual().ObtenirPropietatInt("BaseProtectionRadius");
		}
		return r;
	}
	boolean isUndeBaseProtection(Block blk){
		for(Equip e : Equips){
			if (blk.getLocation().distance(e.getTeamSpawnLocation()) < getBaseProtectionRadius()){
				return true;
			}
		}
		return false;
	}


	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		super.onBlockBreak(evt, blk);
		if (isUndeBaseProtection(blk))evt.setCancelled(true);
	}
	@Override
	protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {
		super.onBlockPlace(evt, blk);
		if (isUndeBaseProtection(blk))evt.setCancelled(true);
	}
	@Override
	protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
		super.onPlayerMove(evt, p);
		if(evt.isCancelled())return;
		keepAwayFromForcefields(p); //(Si existeixen)
		Location bedSpawnLocation = p.getBedSpawnLocation();
		if (bedSpawnLocation != null) {
			double d = p.getLocation().distance(bedSpawnLocation);
			if (d < 30) {
				if (d > 6
						&& segonsTranscorreguts() < 8
						&& s.getSkillsForPlayer(p).size() < getBaseSkillUnlockerAmount()) {
					sendPlayerMessage(
							p,
							ChatColor.GOLD
									+ "Assegura't de desbloquejar totes les habilitats (slots 7 i 8)");
					p.teleport(bedSpawnLocation);
				}
			}
		}
	}
	protected void onPlayerDeathByPlayer(org.bukkit.event.entity.PlayerDeathEvent evt, Player killed, Player killer) {
		Equip eqKilled = obtenirEquip(killed);
		Equip eqKiller = obtenirEquip(killer);
		if(eqKilled != null && eqKiller != null){
			evt.setDeathMessage(eqKilled.getChatColor() + killed.getName() + ChatColor.GRAY + " ha estat assassinat per " + eqKiller.getChatColor() + killer.getName());
		}
		//TODO add random death causes
	}

}
