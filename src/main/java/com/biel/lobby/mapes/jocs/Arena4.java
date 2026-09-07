package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.lobby.Com;
import com.biel.lobby.mapes.JocTeamScoreRace;
import com.biel.lobby.utilities.Catalan;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.HologramFacade;
import com.biel.lobby.utilities.PaperMessages;
import com.biel.lobby.utilities.Utils;

/**
 * Four teams in a ring. Each team hunts the next one (its prey), is hunted by the
 * previous one (its predator) and has no quarrel with the rest. Prey takes more damage
 * and pays more points; the predator takes less.
 *
 * The economy is the 2013 map's, run by the plugin instead of vanilla: every team has a
 * dye colour. Its font (the map's spawner cages) trickles every colour but the prey's
 * while a teammate is home, and every kill pays dye of the victim's colour, so the
 * prey's dye only ever comes from the hunt. The Arma-man sells swords for the prey's
 * dye; the Arcoiris-man sells the bow for your own dye, arrows for the prey's, the
 * helmet for a neutral team's and the leggings for the predator's. Whoever kills you
 * takes what you were carrying. Design: minicat-repo docs/games/arena4-design.md.
 */
public class Arena4 extends JocTeamScoreRace {
	public enum Relació { ALIAT, PRESA, DEPREDADOR, NEUTRAL }
	/** Whose dye a price is paid in, relative to the buyer's team. */
	public enum Moneda { PRÒPIA, PRESA, DEPREDADOR, NEUTRAL, QUALSEVOL }

	private static final double DAMAGE_PREY_DEFAULT = 1.5;
	private static final double DAMAGE_NEUTRAL_DEFAULT = 1.0;
	private static final double DAMAGE_PREDATOR_DEFAULT = 0.5;
	private static final int POINTS_PREY_DEFAULT = 3;
	private static final int POINTS_NEUTRAL_DEFAULT = 1;
	private static final int POINTS_PREDATOR_DEFAULT = 1;
	private static final int FINISH_SCORE_DEFAULT = 20;
	private static final int DYE_PER_PREY_KILL = 3;
	private static final int DYE_PER_OTHER_KILL = 1;
	private static final int PREY_KILLS_FOR_EXTRA_SKILL = 3;
	/** With two teams the other one would be prey and predator at once; the ring needs three. */
	private static final int MIN_POPULATED_TEAMS = 3;
	/** The font drops one item this often while a teammate stands within FONT_HOME_RADIUS of it. */
	private static final int FONT_INTERVAL_SECONDS = 30;
	/** One drop in this many is the map's cooked porkchop instead of dye. */
	private static final int FONT_FOOD_EVERY = 4;
	/** The 2013 cages woke up with a player within 16 blocks; the tips are 25 blocks from them. */
	private static final double FONT_HOME_RADIUS = 16;
	private static final int FONT_MAX_DYE_LYING_AROUND = 6;
	private static final int HOLOGRAM_REFRESH_SECONDS = 5;
	private static final int SHOPKEEPER_ADOPTION_DELAY_TICKS = 40;
	/** A map's shopkeeper posted higher than this above its base is brought down to the base's level. */
	private static final int SHOPKEEPER_MAX_HEIGHT_ABOVE_BASE = 2;
	private static final int SHOPKEEPER_LANDING_RADIUS = 5;
	/** Chunks kept loaded beyond the box the bases span, so the shops and cages of every quarter are in it. */
	private static final int CHUNK_MARGIN = 2;
	/** A fallback shopkeeper stands this far from its base toward the middle, or further if that spot is not on the ground. */
	private static final double FALLBACK_SHOP_DISTANCE = 3;
	private static final double FALLBACK_SHOP_MAX_DISTANCE = 12;
	/** Ground more than this far below the base is a pit or a canyon, not a place to trade. */
	private static final int FALLBACK_SHOP_MAX_DROP = 3;
	private static final int KIT_ARROWS_WITH_BOW = 4;
	private static final int ARROWS_PER_PURCHASE = 16;

	private double danyPresa = DAMAGE_PREY_DEFAULT;
	private double danyNeutral = DAMAGE_NEUTRAL_DEFAULT;
	private double danyDepredador = DAMAGE_PREDATOR_DEFAULT;
	private int puntsPresa = POINTS_PREY_DEFAULT;
	private int puntsNeutral = POINTS_NEUTRAL_DEFAULT;
	private int puntsDepredador = POINTS_PREDATOR_DEFAULT;
	private int puntuacióFinal = FINISH_SCORE_DEFAULT;
	/** Team ids in hunting order; each hunts the next. From the map's HuntCycle, else id order. */
	private List<Integer> ordreDeCacera = new ArrayList<>();
	private final Map<UUID, Botiga> botigues = new HashMap<>();
	private final Map<UUID, HologramFacade.Handle> rètols = new HashMap<>();
	private final Map<Equip, List<Location>> fonts = new HashMap<>();

	private enum Botiguer {
		ARMA_MAN("Arma-man", Mercaderia.ESPASA_PEDRA, Mercaderia.ESPASA_FERRO, Mercaderia.ESPASA_DIAMANT),
		ARCOIRIS_MAN("Arcoiris-man", Mercaderia.ARC, Mercaderia.FLETXES, Mercaderia.CASC_FERRO, Mercaderia.CAMES_MALLA,
				Mercaderia.POMA_DAURADA, Mercaderia.POCIÓ_VELOCITAT, Mercaderia.SALT_DE_CAÇADOR);

		final String nom;
		final List<Mercaderia> mercaderies;
		Botiguer(String nom, Mercaderia... mercaderies) {
			this.nom = nom;
			this.mercaderies = List.of(mercaderies);
		}
	}

	private enum Mercaderia {
		ESPASA_PEDRA(Material.STONE_SWORD, "Espasa de pedra", Moneda.PRESA, 2, true),
		ESPASA_FERRO(Material.IRON_SWORD, "Espasa de ferro", Moneda.PRESA, 6, true),
		ESPASA_DIAMANT(Material.DIAMOND_SWORD, "Espasa de diamant", Moneda.PRESA, 15, true),
		ARC(Material.BOW, "Arc", Moneda.PRÒPIA, 2, true),
		FLETXES(Material.ARROW, ARROWS_PER_PURCHASE + " fletxes", Moneda.PRESA, 1, false),
		CASC_FERRO(Material.IRON_HELMET, "Casc de ferro", Moneda.NEUTRAL, 3, true),
		CAMES_MALLA(Material.CHAINMAIL_LEGGINGS, "Calces de malla", Moneda.DEPREDADOR, 3, true),
		POMA_DAURADA(Material.GOLDEN_APPLE, "Poma daurada", Moneda.QUALSEVOL, 1, false),
		POCIÓ_VELOCITAT(Material.SPLASH_POTION, "Poció de velocitat", Moneda.QUALSEVOL, 1, false),
		SALT_DE_CAÇADOR(Material.SNOWBALL, "Salt de caçador", Moneda.PRESA, 2, false);

		final Material icona;
		final String nom;
		final Moneda moneda;
		final int preu;
		final boolean permanent;
		Mercaderia(Material icona, String nom, Moneda moneda, int preu, boolean permanent) {
			this.icona = icona;
			this.nom = nom;
			this.moneda = moneda;
			this.preu = preu;
			this.permanent = permanent;
		}
	}

	private record Botiga(Botiguer botiguer, Equip equip) {}

	@Override
	public String getGameName() {
		return "Arena 4";
	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		equips.add(new EquipCaça(DyeColor.RED, "vermell")); // Id 0
		equips.add(new EquipCaça(DyeColor.BLUE, "blau")); // Id 1
		equips.add(new EquipCaça(DyeColor.GREEN, "verd")); // Id 2
		equips.add(new EquipCaça(DyeColor.YELLOW, "groc")); // Id 3
		return equips;
	}

	@Override
	protected void setCustomGameRules() {
	}

	@Override
	protected int getFinishScore() {
		return puntuacióFinal;
	}

	@Override
	protected int getBaseSkillUnlockerAmount() {
		return 1;
	}

	@Override
	protected boolean isRecallEnabled() {
		return true;
	}

	@Override
	public PlayerInfo getPlayerInfo(Player p) {
		return getPlayerInfo(p, Arena4PlayerInfo.class);
	}

	private Arena4PlayerInfo infoCaçador(Player p) {
		return getPlayerInfo(p, Arena4PlayerInfo.class);
	}

	@Override
	protected boolean canStartGame() {
		if (!super.canStartGame()) return false;
		if (equipsPoblats().size() < MIN_POPULATED_TEAMS) {
			sendGlobalMessage(ChatColor.RED + "Arena 4 necessita almenys " + MIN_POPULATED_TEAMS
					+ " equips amb jugadors: amb dos, l'altre equip seria presa i depredador alhora.");
			return false;
		}
		return true;
	}

	@Override
	protected void customJocIniciat() {
		super.customJocIniciat();
		carregarAjustos();
		carregarZonaDeJoc();
		prendreElMapa();
		// Entities arrive a moment after their chunks; by then the teams are at their bases.
		scheduleGameplayTask(this::adoptarBotiguers, SHOPKEEPER_ADOPTION_DELAY_TICKS);
		for (Player p : getPlayers()) {
			anunciarCacera(p);
		}
	}

	@Override
	protected ArrayList<String> getGameInfo(Player p) {
		ArrayList<String> info = new ArrayList<>();
		info.add("Cada equip caça el següent del cercle i fuig de l'anterior.");
		info.add("La presa rep x" + danyPresa + " de mal i val " + puntsPresa + " punts; el depredador rep x"
				+ danyDepredador + " i val " + puntsDepredador + "; la resta x" + danyNeutral + " i " + puntsNeutral + ".");
		info.add("Cada mort resta 1 punt a l'equip de la víctima. Guanya el primer equip a " + puntuacióFinal + ".");
		info.add("Matar dóna tint del color de la víctima (x" + DYE_PER_PREY_KILL + " si és la presa); la font de la base degota tint de tots els colors menys el de la presa mentre hi ha algú a casa. Qui et mata s'emporta el que duies.");
		info.add("L'Arma-man ven espases per tint de la presa; l'Arcoiris-man ven arc, fletxes, armadura i pocions per tints de colors concrets.");
		return info;
	}

	private void carregarAjustos() {
		GestorPropietats propietats = pMapaActual();
		danyPresa = llegirDouble(propietats, "DamagePrey", DAMAGE_PREY_DEFAULT);
		danyNeutral = llegirDouble(propietats, "DamageNeutral", DAMAGE_NEUTRAL_DEFAULT);
		danyDepredador = llegirDouble(propietats, "DamagePredator", DAMAGE_PREDATOR_DEFAULT);
		puntsPresa = llegirInt(propietats, "PointsPrey", POINTS_PREY_DEFAULT);
		puntsNeutral = llegirInt(propietats, "PointsNeutral", POINTS_NEUTRAL_DEFAULT);
		puntsDepredador = llegirInt(propietats, "PointsPredator", POINTS_PREDATOR_DEFAULT);
		puntuacióFinal = llegirInt(propietats, "FinishScore", FINISH_SCORE_DEFAULT);
		ordreDeCacera = new ArrayList<>();
		if (propietats.ExisteixPropietat("HuntCycle")) {
			for (String id : propietats.ObtenirLlista("HuntCycle")) {
				int equip = Integer.parseInt(id.trim());
				if (equip >= 0 && equip < Equips.size() && !ordreDeCacera.contains(equip)) ordreDeCacera.add(equip);
			}
		}
		for (int id = 0; id < Equips.size(); id++) {
			if (!ordreDeCacera.contains(id)) ordreDeCacera.add(id);
		}
	}

	private static double llegirDouble(GestorPropietats propietats, String nom, double perDefecte) {
		return propietats.ExisteixPropietat(nom) ? propietats.ObtenirPropietatDouble(nom) : perDefecte;
	}

	private static int llegirInt(GestorPropietats propietats, String nom, int perDefecte) {
		return propietats.ExisteixPropietat(nom) ? propietats.ObtenirPropietatInt(nom) : perDefecte;
	}

	// ---- The ring -------------------------------------------------------------

	/** Teams with a player in the arena, in hunting order. A team that empties drops out of the ring. */
	private List<Equip> equipsPoblats() {
		List<Integer> ordre = ordreDeCacera.isEmpty() ? idsPerDefecte() : ordreDeCacera;
		return ordre.stream().map(this::obtenirEquip).filter(this::téJugadorsALArena).collect(Collectors.toList());
	}

	private List<Integer> idsPerDefecte() {
		List<Integer> ids = new ArrayList<>();
		for (int id = 0; id < Equips.size(); id++) ids.add(id);
		return ids;
	}

	/** By seat, not presence: a team whose only member dropped keeps its place in the ring until the grace runs out. */
	private boolean téJugadorsALArena(Equip e) {
		return e.getPlayerNames().stream().anyMatch(name -> {
			Seat seat = seatOf(name);
			return seat != null && seat.getRole() == Seat.Role.PLAYER && seat.getState() != Seat.State.VACANT;
		});
	}

	public Equip presa(Equip e) {
		return veíDelCercle(e, 1);
	}

	public Equip depredador(Equip e) {
		return veíDelCercle(e, -1);
	}

	private Equip veíDelCercle(Equip e, int pas) {
		List<Equip> cercle = equipsPoblats();
		int posició = cercle.indexOf(e);
		if (posició < 0 || cercle.size() < 2) return null;
		int mida = cercle.size();
		return cercle.get(((posició + pas) % mida + mida) % mida);
	}

	public Relació relació(Equip atacant, Equip víctima) {
		if (atacant == víctima) return Relació.ALIAT;
		if (presa(atacant) == víctima) return Relació.PRESA;
		if (depredador(atacant) == víctima) return Relació.DEPREDADOR;
		return Relació.NEUTRAL;
	}

	private double multiplicadorDeMal(Relació r) {
		switch (r) {
		case PRESA: return danyPresa;
		case DEPREDADOR: return danyDepredador;
		case NEUTRAL: return danyNeutral;
		default: return 0;
		}
	}

	private int puntsPerCacera(Relació r) {
		switch (r) {
		case PRESA: return puntsPresa;
		case DEPREDADOR: return puntsDepredador;
		case NEUTRAL: return puntsNeutral;
		default: return 0;
		}
	}

	private static String etiqueta(Relació r) {
		switch (r) {
		case PRESA: return "presa";
		case DEPREDADOR: return "depredador";
		case NEUTRAL: return "neutral";
		default: return "aliat";
		}
	}

	private static String nomEquip(Equip e) {
		return e == null ? ChatColor.GRAY + "ningú" : e.getAdjectiuColored();
	}

	private void anunciarCacera(Player p) {
		Equip e = obtenirEquip(p);
		if (e == null) return;
		Equip presa = presa(e);
		Equip depredador = depredador(e);
		PaperMessages.showTitle(p, 10, 70, 20, ChatColor.GOLD + "Caça " + nomEquip(presa),
				ChatColor.GRAY + "Fuig de " + nomEquip(depredador));
		sendPlayerMessage(p, ChatColor.GOLD + "La teva presa és l'equip " + nomEquip(presa) + ChatColor.GOLD
				+ "; el teu depredador és l'equip " + nomEquip(depredador) + ChatColor.GOLD + ".");
	}

	// ---- Combat --------------------------------------------------------------

	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if (evt.isCancelled()) return;
		Equip atacant = obtenirEquip(damager);
		Equip víctima = obtenirEquip(damaged);
		if (atacant == null || víctima == null || atacant == víctima) return;
		evt.setDamage(evt.getDamage() * multiplicadorDeMal(relació(atacant, víctima)));
	}

	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		super.onPlayerDeath(evt, killed);
		Arena4PlayerInfo info = infoCaçador(killed);
		info.setDeaths(info.getDeaths() + 1);
	}

	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		super.onPlayerDeathByPlayer(evt, killed, killer);
		if (killed == killer) return;
		EquipCaça equipCaçador = (EquipCaça) obtenirEquip(killer);
		EquipCaça equipVíctima = (EquipCaça) obtenirEquip(killed);
		if (equipCaçador == null || equipVíctima == null || equipCaçador == equipVíctima) return;
		Relació r = relació(equipCaçador, equipVíctima);
		int punts = puntsPerCacera(r);
		int tints = r == Relació.PRESA ? DYE_PER_PREY_KILL : DYE_PER_OTHER_KILL;
		killer.getInventory().addItem(tint(equipVíctima, tints));
		killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1.4F);
		Arena4PlayerInfo info = infoCaçador(killer);
		info.setKills(info.getKills() + 1);
		if (r == Relació.PRESA) {
			info.presesCaçades++;
			if (info.presesCaçades == PREY_KILLS_FOR_EXTRA_SKILL) info.addAdditionalSkill();
		}
		evt.setDeathMessage(equipCaçador.getChatColor() + killer.getName() + ChatColor.GRAY + " (+" + punts + ", "
				+ etiqueta(r) + ") ha caçat " + equipVíctima.getChatColor() + killed.getName() + ChatColor.GRAY + " (-1)");
		// The penalty first: the winner check runs inside every score change and must see the final state.
		equipVíctima.incrementScore(-1);
		equipCaçador.incrementScore(punts);
	}

	// ---- Kit ------------------------------------------------------------------

	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		Equip e = obtenirEquip(ply);
		Arena4PlayerInfo info = infoCaçador(ply);
		ArrayList<ItemStack> items = new ArrayList<>();
		items.add(espasa(info));
		if (e != null) {
			items.add(info.téCascDeFerro ? new ItemStack(Material.IRON_HELMET) : Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
			items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e));
			items.add(info.téCalcesDeMalla ? new ItemStack(Material.CHAINMAIL_LEGGINGS) : Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));
			items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e));
		}
		if (info.téArc) {
			items.add(arc());
			items.add(new ItemStack(Material.ARROW, KIT_ARROWS_WITH_BOW));
		}
		items.add(new ItemStack(Material.COOKED_BEEF, 4));
		return items;
	}

	private static ItemStack espasa(Arena4PlayerInfo info) {
		switch (info.nivellEspasa) {
		case 0: return new ItemStack(Material.WOODEN_SWORD);
		case 1: return new ItemStack(Material.STONE_SWORD);
		case 2: return new ItemStack(Material.IRON_SWORD);
		default: return new ItemStack(Material.DIAMOND_SWORD);
		}
	}

	private static ItemStack arc() {
		ItemStack arc = new ItemStack(Material.BOW);
		arc.addUnsafeEnchantment(Enchantment.UNBREAKING, 10);
		return arc;
	}

	private void reemplaçarEspasa(Player ply, Arena4PlayerInfo info) {
		PlayerInventory inventari = ply.getInventory();
		for (int slot = 0; slot < inventari.getSize(); slot++) {
			ItemStack item = inventari.getItem(slot);
			if (item != null && item.getType().name().endsWith("_SWORD")) inventari.setItem(slot, null);
		}
		inventari.addItem(espasa(info));
	}

	// ---- Dye, the currency -----------------------------------------------------

	private static Material materialDeTint(Equip e) {
		return Material.valueOf(e.getColor().name() + "_DYE");
	}

	private static ItemStack tint(Equip equip, int quantitat) {
		return Utils.setItemNameAndLore(new ItemStack(materialDeTint(equip), quantitat),
				equip.getChatColor() + "Tint " + equip.getAdjectiu(), ChatColor.GRAY + "Els botiguers el canvien per equipament");
	}

	private Equip equipDelTint(ItemStack item) {
		if (item == null) return null;
		for (Equip e : Equips) {
			if (materialDeTint(e) == item.getType()) return e;
		}
		return null;
	}

	/** The dye colours a buyer may pay a price in. A ring without a neutral team takes any foreign colour for NEUTRAL prices. */
	private Set<Equip> equipsQuePaguen(Equip comprador, Moneda moneda) {
		Set<Equip> acceptats = new HashSet<>();
		switch (moneda) {
		case PRÒPIA:
			acceptats.add(comprador);
			break;
		case PRESA:
			if (presa(comprador) != null) acceptats.add(presa(comprador));
			break;
		case DEPREDADOR:
			if (depredador(comprador) != null) acceptats.add(depredador(comprador));
			break;
		case NEUTRAL:
			for (Equip e : equipsPoblats()) {
				if (relació(comprador, e) == Relació.NEUTRAL) acceptats.add(e);
			}
			if (acceptats.isEmpty()) {
				for (Equip e : Equips) if (e != comprador) acceptats.add(e);
			}
			break;
		case QUALSEVOL:
			acceptats.addAll(Equips);
			break;
		}
		return acceptats;
	}

	private String nomDeLaMoneda(Equip comprador, Moneda moneda) {
		switch (moneda) {
		case PRÒPIA: return "tint propi";
		case PRESA: return "tint de la presa";
		case DEPREDADOR: return "tint del depredador";
		case NEUTRAL: return "tint d'un equip neutral";
		default: return "tint de qualsevol color";
		}
	}

	private int comptarTint(Player p, Set<Equip> equips) {
		int total = 0;
		for (ItemStack item : p.getInventory().getContents()) {
			if (equips.contains(equipDelTint(item))) total += item.getAmount();
		}
		return total;
	}

	private boolean gastarTint(Player p, Set<Equip> equips, int preu) {
		if (comptarTint(p, equips) < preu) return false;
		int pendent = preu;
		PlayerInventory inventari = p.getInventory();
		for (int slot = 0; slot < inventari.getSize() && pendent > 0; slot++) {
			ItemStack item = inventari.getItem(slot);
			if (!equips.contains(equipDelTint(item))) continue;
			int agafats = Math.min(item.getAmount(), pendent);
			pendent -= agafats;
			if (agafats == item.getAmount()) inventari.setItem(slot, null);
			else item.setAmount(item.getAmount() - agafats);
		}
		p.updateInventory();
		return true;
	}

	// ---- Taking over the map's machinery ---------------------------------------

	/**
	 * Keeps the whole arena loaded for the match: the box the bases span plus a margin.
	 * Without a ticket a quarter nobody is standing in unloads with its shopkeepers and
	 * cages, and the villagers would not even be there to adopt.
	 */
	private void carregarZonaDeJoc() {
		int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;
		for (Equip e : Equips) {
			Location base = e.getTeamSpawnLocation();
			minX = Math.min(minX, base.getBlockX() >> 4);
			maxX = Math.max(maxX, base.getBlockX() >> 4);
			minZ = Math.min(minZ, base.getBlockZ() >> 4);
			maxZ = Math.max(maxZ, base.getBlockZ() >> 4);
		}
		for (int cx = minX - CHUNK_MARGIN; cx <= maxX + CHUNK_MARGIN; cx++) {
			for (int cz = minZ - CHUNK_MARGIN; cz <= maxZ + CHUNK_MARGIN; cz++) {
				world.getChunkAt(cx, cz).addPluginChunkTicket(Com.getPlugin());
			}
		}
	}

	/**
	 * The map's spawner cages become the teams' dye fonts and its command blocks (the
	 * vanilla inventory reset) become wall. A map without cages gets one font per base.
	 */
	private void prendreElMapa() {
		fonts.clear();
		for (Chunk chunk : world.getLoadedChunks()) {
			for (BlockState estat : chunk.getTileEntities()) {
				if (estat instanceof CreatureSpawner gàbia) {
					gàbia.setSpawnedType(null);
					gàbia.setRequiredPlayerRange(0);
					gàbia.update(true, false);
					Equip propietari = equipMésProper(estat.getLocation());
					if (propietari != null) fonts.computeIfAbsent(propietari, k -> new ArrayList<>()).add(estat.getLocation());
				} else if (estat instanceof CommandBlock) {
					estat.getBlock().setType(Material.STONE_BRICKS);
				}
			}
		}
		for (Equip e : equipsPoblats()) {
			fonts.computeIfAbsent(e, k -> new ArrayList<>(List.of(e.getTeamSpawnLocation().getBlock().getRelative(0, -1, 0).getLocation())));
		}
	}

	private Equip equipMésProper(Location lloc) {
		Equip mésProper = null;
		double millor = Double.MAX_VALUE;
		for (Equip e : Equips) {
			double distància = e.getTeamSpawnLocation().distanceSquared(lloc);
			if (distància < millor) {
				millor = distància;
				mésProper = e;
			}
		}
		return mésProper;
	}

	/**
	 * The map's named villagers keep their posts and become the plugin's shops; the ones
	 * belonging to an empty team leave. Populated teams missing a shopkeeper get one
	 * beside their base.
	 */
	private void adoptarBotiguers() {
		if (!JocEnMarxa()) return;
		Set<Botiga> cobertes = new HashSet<>();
		for (Villager vilatà : world.getEntitiesByClass(Villager.class)) {
			if (botigues.containsKey(vilatà.getUniqueId())) continue;
			Botiguer botiguer = botiguerPelNom(vilatà);
			if (botiguer == null) continue;
			Equip propietari = equipMésProper(vilatà.getLocation());
			if (propietari == null || !téJugadorsALArena(propietari) || cobertes.contains(new Botiga(botiguer, propietari))) {
				vilatà.remove();
				continue;
			}
			aterrar(vilatà, propietari);
			obrirBotiga(vilatà, new Botiga(botiguer, propietari));
			cobertes.add(new Botiga(botiguer, propietari));
		}
		for (Equip e : equipsPoblats()) {
			for (Botiguer botiguer : Botiguer.values()) {
				if (cobertes.contains(new Botiga(botiguer, e))) continue;
				Villager vilatà = world.spawn(ubicacióDeReserva(e, botiguer), Villager.class);
				obrirBotiga(vilatà, new Botiga(botiguer, e));
			}
		}
		refrescarRètols();
	}

	/**
	 * A shopkeeper posted above its base level comes down beside its post. The 2013
	 * map's Arma-man stands on a booth seven blocks up a solid pillar, which a player
	 * can neither reach nor trade with from below; the nearest standing spot at the
	 * base's height around the pillar is where it does business now.
	 */
	private void aterrar(Villager vilatà, Equip propietari) {
		Location post = vilatà.getLocation();
		int nivell = propietari.getTeamSpawnLocation().getBlockY();
		if (post.getBlockY() <= nivell + SHOPKEEPER_MAX_HEIGHT_ABOVE_BASE) return;
		Location millor = null;
		double millorDistància = Double.MAX_VALUE;
		for (int dx = -SHOPKEEPER_LANDING_RADIUS; dx <= SHOPKEEPER_LANDING_RADIUS; dx++) {
			for (int dz = -SHOPKEEPER_LANDING_RADIUS; dz <= SHOPKEEPER_LANDING_RADIUS; dz++) {
				Location peus = new Location(world, post.getBlockX() + dx + 0.5, nivell, post.getBlockZ() + dz + 0.5);
				if (!peus.getBlock().isPassable() || !peus.getBlock().getRelative(0, 1, 0).isPassable()
						|| peus.getBlock().getRelative(0, -1, 0).isPassable()) continue;
				double distància = dx * dx + dz * dz;
				if (distància < millorDistància) {
					millorDistància = distància;
					millor = peus;
				}
			}
		}
		if (millor != null) vilatà.teleport(millor);
	}

	private static Botiguer botiguerPelNom(Villager vilatà) {
		String nom = vilatà.getCustomName();
		if (nom == null) return null;
		String net = ChatColor.stripColor(nom).toLowerCase();
		if (net.contains("arcoiris")) return Botiguer.ARCOIRIS_MAN;
		if (net.contains("arma")) return Botiguer.ARMA_MAN;
		return null;
	}

	private void obrirBotiga(Villager vilatà, Botiga botiga) {
		vilatà.setAI(false);
		vilatà.setInvulnerable(true);
		vilatà.setSilent(true);
		vilatà.setCollidable(false);
		vilatà.setRemoveWhenFarAway(false);
		vilatà.setPersistent(true);
		vilatà.setRecipes(Collections.emptyList());
		vilatà.setProfession(botiga.botiguer() == Botiguer.ARMA_MAN ? Villager.Profession.WEAPONSMITH : Villager.Profession.CLERIC);
		vilatà.customName(PaperMessages.legacy(ChatColor.GOLD + botiga.botiguer().nom + " " + botiga.equip().getAdjectiuColored()));
		vilatà.setCustomNameVisible(true);
		botigues.put(vilatà.getUniqueId(), botiga);
		rètols.put(vilatà.getUniqueId(), HologramFacade.create(vilatà.getLocation().clone().add(0, 2.9, 0)));
	}

	/**
	 * Beside the base, toward the middle of the map, on ground a player can walk to: the
	 * first spot from FALLBACK_SHOP_DISTANCE outward whose surface is no higher than the
	 * base. A base inside a tower (PixelRift's corners, roofed at y 101 around a floor at
	 * y 96) would otherwise put its shopkeepers on the roof; this walks past the wall and
	 * puts them on the plateau outside. The two shopkeepers stand apart.
	 */
	private Location ubicacióDeReserva(Equip e, Botiguer botiguer) {
		Location base = e.getTeamSpawnLocation();
		Vector capAlMig = getHalfwayMiddle().toVector().subtract(base.toVector()).setY(0);
		if (capAlMig.lengthSquared() > 0) capAlMig.normalize();
		Vector costat = new Vector(-capAlMig.getZ(), 0, capAlMig.getX()).multiply(botiguer == Botiguer.ARMA_MAN ? -1.5 : 1.5);
		for (double distància = FALLBACK_SHOP_DISTANCE; distància <= FALLBACK_SHOP_MAX_DISTANCE; distància += 1) {
			Location lloc = base.clone().add(capAlMig.clone().multiply(distància)).add(costat);
			int terra = world.getHighestBlockYAt(lloc);
			// Neither on a roof above the base nor down a canyon beside it.
			if (terra > base.getBlockY() || terra < base.getBlockY() - FALLBACK_SHOP_MAX_DROP) continue;
			return new Location(world, lloc.getBlockX() + 0.5, terra + 1, lloc.getBlockZ() + 0.5);
		}
		// No walkable ground toward the middle: right beside the base, which is standing ground by definition.
		Location alCostat = base.clone().add(costat);
		return new Location(world, alCostat.getBlockX() + 0.5, base.getY(), alCostat.getBlockZ() + 0.5);
	}

	private void refrescarRètols() {
		for (Map.Entry<UUID, HologramFacade.Handle> rètol : rètols.entrySet()) {
			Botiga botiga = botigues.get(rètol.getKey());
			if (botiga == null) continue;
			Equip e = botiga.equip();
			String títol = ChatColor.GOLD + "" + ChatColor.BOLD + botiga.botiguer().nom + " " + e.getAdjectiuColored();
			if (botiga.botiguer() == Botiguer.ARMA_MAN) {
				rètol.getValue().setLines(títol, ChatColor.WHITE + "Caça: " + nomEquip(presa(e)), ChatColor.WHITE + "Fuig de: " + nomEquip(depredador(e)));
			} else {
				rètol.getValue().setLines(títol, ChatColor.WHITE + "Arc, fletxes, armadura i pocions", ChatColor.GRAY + "Cada cosa es paga amb un color");
			}
		}
	}

	/**
	 * Every FONT_INTERVAL_SECONDS, each home team's font drops one item, up to a small
	 * pile: dye of any colour in the ring except the prey's, as the map's cages did, or
	 * now and then the cages' porkchop. The prey's colour is earned only by hunting.
	 */
	private void degotarFonts() {
		for (Map.Entry<Equip, List<Location>> font : fonts.entrySet()) {
			Equip e = font.getKey();
			if (!téJugadorsALArena(e)) continue;
			List<Location> gàbies = font.getValue();
			boolean algúACasa = e.getPlayers().stream().filter(p -> p.getWorld() == world)
					.anyMatch(p -> gàbies.stream().anyMatch(g -> g.distanceSquared(p.getLocation()) <= FONT_HOME_RADIUS * FONT_HOME_RADIUS));
			if (!algúACasa) continue;
			Location gàbia = Utils.getRandomListItem(gàbies);
			Location sortida = gàbia.clone().add(0.5, 1.2, 0.5);
			long gotesAlTerra = world.getNearbyEntities(sortida, 4, 3, 4).stream()
					.filter(ent -> ent instanceof Item item && (equipDelTint(item.getItemStack()) != null || item.getItemStack().getType() == Material.COOKED_PORKCHOP)).count();
			if (gotesAlTerra >= FONT_MAX_DYE_LYING_AROUND) continue;
			List<Equip> colors = equipsPoblats().stream().filter(altre -> altre != presa(e)).collect(Collectors.toList());
			if (colors.isEmpty()) colors = List.of(e);
			ItemStack gotaItem = Utils.NombreEntre(1, FONT_FOOD_EVERY) == 1 ? new ItemStack(Material.COOKED_PORKCHOP) : tint(Utils.getRandomListItem(colors), 1);
			Item gota = world.dropItem(sortida, gotaItem);
			gota.setVelocity(new Vector(0, 0.1, 0));
			world.playSound(sortida, Sound.BLOCK_BREWING_STAND_BREW, 0.6F, 1.6F);
		}
	}

	@Override
	public void heartbeat() {
		super.heartbeat();
		if (!JocEnMarxa()) return;
		if (getHeartbeatCount() % HOLOGRAM_REFRESH_SECONDS == 0) refrescarRètols();
		if (getHeartbeatCount() % FONT_INTERVAL_SECONDS == 0) degotarFonts();
	}

	@Override
	public void clearExternals() {
		for (UUID id : botigues.keySet()) {
			Entity vilatà = Bukkit.getEntity(id);
			if (vilatà != null) vilatà.remove();
		}
		botigues.clear();
		rètols.values().forEach(HologramFacade.Handle::delete);
		rètols.clear();
		fonts.clear();
		if (world != null) world.removePluginChunkTickets(Com.getPlugin());
		super.clearExternals();
	}

	@Override
	protected void onEntityDamage(EntityDamageEvent evt, Entity e) {
		super.onEntityDamage(evt, e);
		if (botigues.containsKey(e.getUniqueId())) evt.setCancelled(true);
	}

	@Override
	protected void onPlayerInteractEntity(PlayerInteractEntityEvent evt, Player p) {
		super.onPlayerInteractEntity(evt, p);
		Botiga botiga = botigues.get(evt.getRightClicked().getUniqueId());
		if (botiga == null) return;
		evt.setCancelled(true);
		if (evt.getHand() != EquipmentSlot.HAND || !JocEnMarxa()) return;
		if (obtenirEquip(p) != botiga.equip()) {
			sendPlayerMessage(p, ChatColor.RED + "Aquest " + botiga.botiguer().nom + " només ven a l'equip " + botiga.equip().getAdjectiuColored() + ChatColor.RED + ".");
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			return;
		}
		obrirMenú(p, botiga);
	}

	private void obrirMenú(Player p, Botiga botiga) {
		Arena4PlayerInfo info = infoCaçador(p);
		Equip comprador = botiga.equip();
		List<Mercaderia> mercaderies = botiga.botiguer().mercaderies;
		IconMenu menu = new IconMenu(ChatColor.GOLD + botiga.botiguer().nom, 9, event -> {
			int posició = event.getPosition();
			if (posició < mercaderies.size()) comprar(event.getPlayer(), mercaderies.get(posició));
			// The shop stays open with fresh prices and counts; opening the new menu closes this one.
			event.setWillClose(false);
			if (JocEnMarxa() && event.getPlayer().getWorld() == world) obrirMenú(event.getPlayer(), botiga);
		});
		for (Mercaderia m : mercaderies) {
			ArrayList<String> descripció = new ArrayList<>();
			descripció.add(ChatColor.WHITE + "Preu: " + ChatColor.AQUA + m.preu + " " + nomDeLaMoneda(comprador, m.moneda));
			descripció.add(ChatColor.GRAY + "Tens " + comptarTint(p, equipsQuePaguen(comprador, m.moneda)) + " d'aquest color");
			descripció.add(ChatColor.GRAY + (m.permanent ? "Es conserva en reaparèixer" : "Consumible"));
			if (m.permanent && jaEnTé(info, m)) descripció.add(ChatColor.GREEN + "Ja el tens");
			menu.setOption(mercaderies.indexOf(m), new ItemStack(m.icona), ChatColor.YELLOW + m.nom, descripció);
		}
		menu.setOption(8, tint(comprador, 1), ChatColor.AQUA + "Els teus tints",
				ChatColor.WHITE + "Propi: " + comptarTint(p, equipsQuePaguen(comprador, Moneda.PRÒPIA)),
				ChatColor.WHITE + "Presa: " + comptarTint(p, equipsQuePaguen(comprador, Moneda.PRESA)),
				ChatColor.WHITE + "Depredador: " + comptarTint(p, equipsQuePaguen(comprador, Moneda.DEPREDADOR)),
				ChatColor.WHITE + "Neutral: " + comptarTint(p, equipsQuePaguen(comprador, Moneda.NEUTRAL)));
		menu.open(p);
	}

	private static boolean jaEnTé(Arena4PlayerInfo info, Mercaderia m) {
		switch (m) {
		case ESPASA_PEDRA: return info.nivellEspasa >= 1;
		case ESPASA_FERRO: return info.nivellEspasa >= 2;
		case ESPASA_DIAMANT: return info.nivellEspasa >= 3;
		case ARC: return info.téArc;
		case CASC_FERRO: return info.téCascDeFerro;
		case CAMES_MALLA: return info.téCalcesDeMalla;
		default: return false;
		}
	}

	private void comprar(Player p, Mercaderia m) {
		Equip comprador = obtenirEquip(p);
		Arena4PlayerInfo info = infoCaçador(p);
		if (comprador == null) return;
		if (m.permanent && jaEnTé(info, m)) {
			sendPlayerMessage(p, ChatColor.YELLOW + "Ja tens " + m.nom.toLowerCase() + " o millor.");
			return;
		}
		Set<Equip> equips = equipsQuePaguen(comprador, m.moneda);
		if (!gastarTint(p, equips, m.preu)) {
			sendPlayerMessage(p, ChatColor.RED + "Et falten " + (m.preu - comptarTint(p, equips)) + " " + Catalan.de(nomDeLaMoneda(comprador, m.moneda))
					+ " per a " + m.nom.toLowerCase() + ".");
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			return;
		}
		lliurar(p, info, m);
		sendPlayerMessage(p, ChatColor.GREEN + "Has comprat " + m.nom.toLowerCase() + ".");
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
	}

	private void lliurar(Player p, Arena4PlayerInfo info, Mercaderia m) {
		switch (m) {
		case ESPASA_PEDRA:
			info.nivellEspasa = 1;
			reemplaçarEspasa(p, info);
			break;
		case ESPASA_FERRO:
			info.nivellEspasa = 2;
			reemplaçarEspasa(p, info);
			break;
		case ESPASA_DIAMANT:
			info.nivellEspasa = 3;
			reemplaçarEspasa(p, info);
			break;
		case ARC:
			info.téArc = true;
			p.getInventory().addItem(arc(), new ItemStack(Material.ARROW, KIT_ARROWS_WITH_BOW));
			break;
		case FLETXES:
			p.getInventory().addItem(new ItemStack(Material.ARROW, ARROWS_PER_PURCHASE));
			break;
		case CASC_FERRO:
			info.téCascDeFerro = true;
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			break;
		case CAMES_MALLA:
			info.téCalcesDeMalla = true;
			p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			break;
		case POMA_DAURADA:
			p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
			break;
		case POCIÓ_VELOCITAT:
			p.getInventory().addItem(Utils.createPotion(PotionType.SWIFTNESS, 1, true));
			break;
		case SALT_DE_CAÇADOR:
			p.getInventory().addItem(getSnowLauncher(1));
			break;
		}
	}

	/** A team in the ring. Its score never drops below zero. */
	public class EquipCaça extends EquipScoreRace {
		public EquipCaça(DyeColor color, String adjectiu) {
			super(color, adjectiu);
		}

		@Override
		public void setScore(int score) {
			super.setScore(Math.max(0, score));
		}
	}

	/** What a hunter keeps across deaths: the upgrades bought and the prey kills toward the extra skill. */
	public class Arena4PlayerInfo extends PlayerInfo {
		int nivellEspasa = 0;
		boolean téArc = false;
		boolean téCascDeFerro = false;
		boolean téCalcesDeMalla = false;
		int presesCaçades = 0;
	}
}
