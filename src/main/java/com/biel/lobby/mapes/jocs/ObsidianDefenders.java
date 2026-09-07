package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Powerable;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import java.util.function.Predicate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.Tag;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.ObsidianDefenders.Ability.AbilityType;
import com.biel.lobby.minions.Minion;
import com.biel.lobby.minions.SnowmanMinion;
import com.biel.lobby.utilities.PaperMessages;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;

import io.papermc.paper.registry.RegistryAccess;
import net.kyori.adventure.bossbar.BossBar;
import io.papermc.paper.registry.RegistryKey;

/**
 * The first game the server ever had (March 2013). Two teams, an obsidian-clad TNT base
 * each; the match ends when a base's TNT goes off. Gold nuggets are the economy: kills,
 * the cycling jungle chests, the diamond pickaxe that lands in the middle and the iron
 * golem all pay in gold. The scheduled mechanics are ported from the 2013 plugin
 * (minicat-repo/docs/games/obsidian-defenders/original-2013): ExampleTask is the chest
 * cycle, PicDiamantTask the pickaxe, ApareixerGolem the golem.
 */
public class ObsidianDefenders extends JocEquips {
	/** Where the pickaxe lands when the map has no PicDiamant property: the middle of the jungle, as in 2013. */
	private static final Vector PIC_DIAMANT_2013 = new Vector(661, 42, -1398);
	private static final long CICLE_COFRES_TICKS = 32 * 20;
	private static final int MAX_COFRES_OBERTS = 8;
	private static final long PRIMER_PIC_TICKS = 3 * 60 * 20;
	private static final long PERIODE_PIC_TICKS = 2 * 60 * 20;
	private static final long GOLEM_INICIAL_TICKS = 5 * 20;
	private static final int OR_PER_GOLEM = 22;
	/** The iron golem is El Guardià: named, lit by an aura and documented in game (docs/games/obsidian-defenders/guardian-golem-design.md). */
	private static final String NOM_GUARDIÀ = "El Guardià";
	private static final String TÍTOL_GUARDIÀ = "El Guardià de l'obsidiana";
	/** Glacial cyan, deliberately neither team's colour: the blue team is navy. */
	private static final Color COLOR_AURA_GUARDIÀ = Color.fromRGB(90, 200, 255);
	private static final Color COLOR_AURA_ENFURISMAT = Color.fromRGB(220, 245, 255);
	private static final double RADI_AURA_GUARDIÀ = 1.2;
	private static final long AURA_GUARDIÀ_PERIODE_TICKS = 5;
	private static final double FRACCIÓ_VIDA_ENFURISMAT = 0.3;
	/** Players this close to the Guardian see its boss bar: the hut, the canal, the sewers and the deck above. */
	private static final double DISTÀNCIA_BARRA_GUARDIÀ = 24;
	/** The beam over the lair starts on the deck block above the Golem property (nine blocks up on the 2013 map) and rises this far. */
	private static final int FEIX_GUARDIÀ_BASE = 9;
	private static final int FEIX_GUARDIÀ_ALÇADA = 10;
	private static final int SEGONS_ENTRE_BRUNZITS_GUARDIÀ = 4;
	/**
	 * How far from a team's spawn the base's TNT is looked for when the match starts.
	 * On the 2013 map each core is a cluster of about 23 TNT blocks 20 to 27 blocks
	 * behind the spawn at y 39 (x 589-597 for the red base, 729-737 for the blue one);
	 * the small five-block TNT crosses 45 blocks out are traps, not cores.
	 */
	private static final int RADI_NUCLI = 30;
	private static final int ALÇADA_NUCLI = 8;
	/** A primed TNT counts as a base core while it is this close to one of the TNT blocks found at start. */
	private static final double TOLERÀNCIA_NUCLI = 3;
	/** A death with no killer is credited to the last player who hit the victim within this window. */
	private static final int SEGONS_CREDIT_ÚLTIM_COP = 95;
	/** Snowmen (2013): a thrown snowball becomes a snow golem owned by the thrower; at most this many alive per player. */
	private static final int MAX_SNOWMEN_PER_PLAYER = 3;
	/** Quartz in the thrower's inventory makes the new snowman fire a third faster, as in 2013. */
	private static final int SNOWMAN_QUARTZ_COOLDOWN_TICKS = SnowmanMinion.DEFAULT_COOLDOWN_TICKS * 2 / 3;
	private static final int SNOWBALL_DAMAGE_EARLY = 4;
	private static final int SNOWBALL_DAMAGE_LATE = 1;
	private static final int SNOWBALL_LATE_FROM_MINUTE = 5;

	boolean debug = false;
	/** Team id → block positions of the TNT that is that team's base core. */
	private final Map<Integer, Set<Vector>> nuclisPerEquip = new HashMap<>();
	private final Map<UUID, Integer> segonsÚltimCopRebut = new HashMap<>();
	private UUID golemActual;
	private BossBar barraGuardià;
	private int tascaAuraGuardià = -1;
	private int passosAura = 0;
	private int segonsPresènciaGuardià = 0;
	private boolean guardiàEnfurismat = false;
	private boolean guardiàAnunciat = false;
	/** Game second at which the Guardian (re)appears; read only while it is dead. */
	private int guardiàTornaAlSegon = 0;
	private boolean primerPicAnunciat = false;
	/** Team id → bridge charge, 0..PONT_CÀRREGA_MÀXIMA. */
	private final Map<Integer, Integer> càrregaPont = new HashMap<>();
	/** Team id → the sign above that team's bridge button, rewritten as its charge bar. */
	private final Map<Integer, Block> rètolsPont = new HashMap<>();
	/** Bridge button block → team id that owns it. */
	private final Map<Block, Integer> botonsPont = new HashMap<>();
	/** Team id → the plank columns of the bridge over that team's moat, deploy order. */
	private final Map<Integer, List<List<Block>>> pontsPerMoat = new HashMap<>();
	private final Set<Integer> pontsDesplegats = new HashSet<>();
	private final List<ControlPoint> controlPoints = new ArrayList<>();
	/** Team id → that base's redstone lamps, nearest the spawn first: the bridge bar in light. */
	private final Map<Integer, List<Block>> lampsByTeam = new HashMap<>();
	/** Team id → seconds of captured-point time not yet turned into a square. */
	private final Map<Integer, Integer> pointSecondsByTeam = new HashMap<>();
	/** Player → the plate shown pressed for them; the real block never changes. */
	private final Map<UUID, Block> shownPressedPlate = new HashMap<>();
	private final Set<UUID> refusedOnPlate = new HashSet<>();

	/** A middle room: one plate per team, the lamp beside each plate, and who holds it. */
	private static final class ControlPoint {
		final Map<Integer, Block> plateByTeam = new HashMap<>();
		final Map<Integer, Block> lampByTeam = new HashMap<>();
		Integer owner = null;

		Integer teamOfPlate(Block block) {
			for (Map.Entry<Integer, Block> plate : plateByTeam.entrySet()) if (plate.getValue().equals(block)) return plate.getKey();
			return null;
		}

		Location centre() {
			Location sum = null;
			for (Block plate : plateByTeam.values()) sum = sum == null ? plate.getLocation() : sum.add(plate.getLocation());
			return sum.multiply(1.0 / plateByTeam.size());
		}
	}

	private static final int PONT_CÀRREGA_MÀXIMA = 6;
	/**
	 * The control points (docs/games/obsidian-defenders/middle-plates-design.md): each
	 * middle room holds a red and a blue plate; stepping on your colour's plate captures
	 * the point for your team until the enemy steps on theirs. Every captured point earns
	 * its team a bridge square every SECONDS_PER_POINT_SQUARE seconds; the capture itself
	 * pays the capturer.
	 */
	private static final int SECONDS_PER_POINT_SQUARE = 5;
	private static final int GOLD_PER_CAPTURE = 5;
	/** Plates are looked for between the two spawns, this far either side of the line joining them, at the map's play heights. */
	private static final int PLATE_BAND_HALF_WIDTH = 60;
	private static final int SCAN_MIN_Y = 30;
	private static final int SCAN_MAX_Y = 70;
	/** Plates closer than this belong to the same control point; a point's lamp is within it of its plate. */
	private static final double CONTROL_POINT_RADIUS = 8;
	/** Who is shown a plate pressed: everyone this close to it. */
	private static final double PLATE_VIEW_DISTANCE = 16;
	private static final long PONT_TICKS_PER_COLUMNA = 10;
	private static final long PONT_TICKS_DESPLEGAT = 45 * 20;
	private static final int PONT_ALÇADA = 37;
	/** A block over the middle of each moat's gap on the 2013 map, red then blue; a PontMoat<n> property overrides it. */
	private static final Vector[] PONT_MOAT_2013 = { new Vector(644, 37, -1426), new Vector(682, 37, -1374) };
	private static final String QUADRAT_PLE = "\u25A0";
	private static final String QUADRAT_BUIT = "\u25A1";
	/** The control room is 50 blocks from the spawn on the 2013 map, the farthest booth 46. */
	private static final int RADI_RÈTOLS = 60;

	/** The three booths of each base, from the spawn doors outward, and what each sells. */
	private enum Parada {
		GERRY("weapons", "Gerry", "armes", Mercaderia.FLETXES, Mercaderia.ESTRELLES, Mercaderia.ESPASA_FERRO, Mercaderia.ARC, Mercaderia.PIC_FERRO, Mercaderia.ESPASA_DIAMANT),
		SEON("armors", "Seon", "armadures", Mercaderia.PITRAL_FERRO, Mercaderia.CALCES_DIAMANT),
		KAREN("potions", "Karen", "altres coses", Mercaderia.BLOC_OR, Mercaderia.BOLA_DE_NEU, Mercaderia.QUARS);

		final String rètolOriginal;
		final String nom;
		final String ofici;
		final List<Mercaderia> mercaderies;
		Parada(String rètolOriginal, String nom, String ofici, Mercaderia... mercaderies) {
			this.rètolOriginal = rètolOriginal;
			this.nom = nom;
			this.ofici = ofici;
			this.mercaderies = List.of(mercaderies);
		}
	}

	/** What the booths sell. Prices in gold nuggets; an ingot pays for ten. */
	private enum Mercaderia {
		FLETXES(Material.ARROW, 8, 3, "8 fletxes", null),
		ESTRELLES(Material.FIREWORK_STAR, 2, 10, "2 estrelles de foc", "Permet disparar fletxes explosives des de llocs elevats."),
		ESPASA_FERRO(Material.IRON_SWORD, 1, 10, "Espasa de ferro", null),
		ARC(Material.BOW, 1, 12, "Arc", null),
		PIC_FERRO(Material.IRON_PICKAXE, 1, 12, "Pic de ferro", "+30 dany al golem"),
		PITRAL_FERRO(Material.IRON_CHESTPLATE, 1, 18, "Pitral de ferro", null),
		BLOC_OR(Material.GOLD_BLOCK, 1, 25, "Bloc d'or", "+2 or cada " + (CICLE_COFRES_TICKS / 20) + " segons"),
		BOLA_DE_NEU(Material.SNOWBALL, 1, 6, "Bola de neu", "Llança-la: on caigui apareix un ninot de neu que dispara als enemics (màx. " + MAX_SNOWMEN_PER_PLAYER + ")"),
		QUARS(Material.QUARTZ, 1, 15, "Quars", "Mentre el portis, els teus nous ninots disparen un 50 % més ràpid"),
		CALCES_DIAMANT(Material.DIAMOND_LEGGINGS, 1, 30, "Calces de diamant", null),
		ESPASA_DIAMANT(Material.DIAMOND_SWORD, 1, 40, "Espasa de diamant", null);

		final Material material;
		final int quantitat;
		final int preu;
		final String nom;
		final String descripció;
		Mercaderia(Material material, int quantitat, int preu, String nom, String descripció) {
			this.material = material;
			this.quantitat = quantitat;
			this.preu = preu;
			this.nom = nom;
			this.descripció = descripció;
		}
	}

	private record ParadaDeLEquip(Parada parada, Equip equip) {}
	private final Map<UUID, ParadaDeLEquip> botiguers = new HashMap<>();

	public ObsidianDefenders() {
	}

	@Override
	public String getGameName() {
		return "Obsidian Defenders";
	}

	@Override
	protected void customJocIniciat() {
		super.customJocIniciat();
		setBlockBreakPlace(false);
		setGiveStartingItemsRespawn(false);
		registrarNuclis();
		registrarControlsIParades();
		registrarPonts();
		registerControlPointsAndLamps();
		emptyDispensers();
		scheduleGameplayRepeatingTask(this::cicleCofres, 20, CICLE_COFRES_TICKS);
		scheduleGameplayRepeatingTask(this::tickControlPoints, 20, 20);
		scheduleGameplayRepeatingTask(this::apareixerPicDiamant, PRIMER_PIC_TICKS, PERIODE_PIC_TICKS);
		guardiàTornaAlSegon = (int) (GOLEM_INICIAL_TICKS / 20);
		scheduleGameplayTask(this::apareixerGolem, GOLEM_INICIAL_TICKS);
		scheduleGameplayRepeatingTask(this::presènciaDelGuardià, 20, 20);
	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> equips = new ArrayList<>();
		equips.add(new Equip(DyeColor.RED, "vermell")); //Id 0
		equips.add(new Equip(DyeColor.BLUE, "blau")); //Id 1
		return equips;
	}

	/** The 2013 kit: stone sword, team helmet over chainmail, one arrow and food for the whole match. */
	@Override
	protected ArrayList<ItemStack> getStartingItems(Player ply) {
		ArrayList<ItemStack> items = new ArrayList<>();
		Equip e = obtenirEquip(ply);
		items.add(new ItemStack(Material.STONE_SWORD, 1));
		items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
		items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
		items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		items.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
		items.add(new ItemStack(Material.ARROW, 1));
		items.add(new ItemStack(Material.COOKED_BEEF, 40));
		return items;
	}

	@Override
	protected void donarEfectesInicials(Player ply) {
		ply.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 25 * 20, 2, false), true);
	}

	/** Gold, pickaxes and consumables survive death: the economy is the game. */
	@Override
	public boolean getResetPlayerOnRespawn() {
		return false;
	}

	@Override
	protected void setCustomGameRules() {
		world.setTime(12600);
		world.setGameRule(GameRule.MOB_GRIEFING, false);
		// Snow golems melt in rain; the sky stays clear for the whole match.
		world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		world.setStorm(false);
		world.setThundering(false);
	}

	@Override
	protected ArrayList<String> getGameInfo(Player p) {
		ArrayList<String> info = new ArrayList<>();
		info.add("Fes explotar la TNT de la base enemiga. L'obsidiana es pot trencar.");
		info.add("Els cofres de la jungla canvien de lloc cada 32 s; el pic de diamant cau al mig als 3 min.");
		info.add("L'or paga tot: matar, obrir cofres, matar el Guardià.");
		info.add("El Guardià viu sota el mig: matar-lo dona " + OR_PER_GOLEM + " d'or i 3 min de Resistència i Velocitat.");
		info.add("Una bola de neu llançada fa aparèixer un ninot de neu que dispara als enemics (màxim " + MAX_SNOWMEN_PER_PLAYER + " per jugador).");
		info.add("Trepitja la teva placa als punts de control del mig: cada punt capturat carrega el pont del teu equip.");
		return info;
	}

	//---------- Base cores and the win ----------

	/** Finds each team's TNT around its spawn. A base without TNT cannot be lost, and says so. */
	private void registrarNuclis() {
		nuclisPerEquip.clear();
		for (Equip e : Equips) {
			Location base = e.getTeamSpawnLocation();
			Set<Vector> nuclis = new HashSet<>();
			double distànciaMínima = Double.MAX_VALUE;
			for (int x = -RADI_NUCLI; x <= RADI_NUCLI; x++) {
				for (int y = -ALÇADA_NUCLI; y <= ALÇADA_NUCLI; y++) {
					for (int z = -RADI_NUCLI; z <= RADI_NUCLI; z++) {
						Block b = base.getBlock().getRelative(x, y, z);
						if (b.getType() != Material.TNT) continue;
						nuclis.add(b.getLocation().toVector());
						distànciaMínima = Math.min(distànciaMínima, b.getLocation().distance(base));
					}
				}
			}
			nuclisPerEquip.put(e.getId(), nuclis);
			if (nuclis.isEmpty()) {
				plugin.getLogger().warning(getGameName() + " " + getMapName() + ": no TNT within " + RADI_NUCLI + " blocks of base" + e.getId() + "; that base cannot be blown up");
				sendGlobalMessage(ChatColor.RED + "No s'ha trobat TNT a la base " + e.getAdjectiuColored() + ChatColor.RED + ": aquesta base no pot explotar.");
			} else {
				plugin.getLogger().info(getGameName() + " " + getMapName() + ": base" + e.getId() + " has " + nuclis.size() + " TNT blocks, the nearest " + Math.round(distànciaMínima) + " blocks from the spawn");
			}
		}
	}

	private Equip equipDelNucli(Location explosió) {
		Vector punt = explosió.toVector();
		for (Equip e : Equips) {
			for (Vector nucli : nuclisPerEquip.getOrDefault(e.getId(), Set.of())) {
				if (nucli.clone().add(new Vector(0.5, 0.5, 0.5)).distance(punt) <= TOLERÀNCIA_NUCLI) return e;
			}
		}
		return null;
	}

	@Override
	protected void onExplosionPrime(ExplosionPrimeEvent evt) {
		super.onExplosionPrime(evt);
		if (!JocEnMarxa() || !(evt.getEntity() instanceof TNTPrimed)) return;
		Equip explotat = equipDelNucli(evt.getEntity().getLocation());
		if (explotat == null) return;
		sendGlobalMessage(ChatColor.RED + "La base de l'equip " + explotat.getAdjectiuColored() + ChatColor.RED + " ha explotat!");
		sendGlobalSound(Sound.ENTITY_GENERIC_EXPLODE, 2F, 0.6F);
		winGame(obtenirEquipEnemic(explotat));
	}

	/** Obsidian is the one block anyone may break: it is what shields the TNT. */
	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		super.onBlockBreak(evt, blk);
		Player ply = evt.getPlayer();
		if (blk.getType() != Material.OBSIDIAN || ply.getGameMode() == GameMode.CREATIVE || !JocEnMarxa()) return;
		evt.setCancelled(false);
		sendGlobalMessage(ply.getName() + ChatColor.DARK_PURPLE + " ha trencat un bloc d'obsidiana!");
		world.playSound(blk.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1F, 1F);
	}

	//---------- Jungle chests (ExampleTask, 2013) ----------

	/** Every 32 s all chest spots close, up to eight reopen with fresh loot, and everyone collects passive gold and wear. */
	private void cicleCofres() {
		if (!JocEnMarxa()) return;
		ArrayList<Location> punts = pMapaActual().ObtenirLocations("cofres", world);
		for (Location punt : punts) tancarCofre(punt.getBlock());
		if (!punts.isEmpty()) {
			Set<Block> oberts = new HashSet<>();
			int passades = 0;
			while (oberts.size() < MAX_COFRES_OBERTS && passades++ < 200) {
				for (Location punt : punts) {
					if (oberts.size() >= MAX_COFRES_OBERTS) break;
					Block b = punt.getBlock();
					if (oberts.contains(b) || !Utils.Possibilitat(10)) continue;
					obrirCofre(b);
					oberts.add(b);
				}
			}
		}
		for (Player p : getPlayers()) {
			donarOrPassiu(p);
			desgastarEquipament(p);
		}
	}

	private void tancarCofre(Block b) {
		if (b.getState() instanceof Chest cofre) cofre.getInventory().clear();
		BlockData fulles = Material.JUNGLE_LEAVES.createBlockData();
		if (fulles instanceof Leaves leaves) leaves.setPersistent(true);
		b.setBlockData(fulles);
	}

	private void obrirCofre(Block b) {
		b.setType(Material.CHEST);
		if (!(b.getState() instanceof Chest cofre)) return;
		Inventory inv = cofre.getInventory();
		inv.clear();
		for (ItemStack loot : lootCofre()) {
			int slot = Utils.NombreEntre(0, inv.getSize() - 1);
			if (inv.getItem(slot) == null) inv.setItem(slot, loot); else inv.addItem(loot);
		}
	}

	/** The 2013 loot table, one roll per line. */
	private ArrayList<ItemStack> lootCofre() {
		ArrayList<ItemStack> loot = new ArrayList<>();
		if (Utils.Possibilitat(90)) {
			int piles = Utils.NombreEntre(1, 6);
			for (int i = 0; i < piles; i++) {
				loot.add(new ItemStack(Material.GOLD_NUGGET, Utils.Possibilitat(8) ? Utils.NombreEntre(2, 4) : 1));
			}
		}
		if (Utils.Possibilitat(5)) loot.add(new ItemStack(Material.GOLD_INGOT, Utils.NombreEntre(1, 2)));
		if (Utils.Possibilitat(20)) loot.add(new ItemStack(Material.EMERALD));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.MAGMA_CREAM));
		if (Utils.Possibilitat(14)) loot.add(new ItemStack(Material.SNOWBALL));
		if (Utils.Possibilitat(8)) loot.add(new ItemStack(Material.EXPERIENCE_BOTTLE, Utils.NombreEntre(1, 3)));
		if (Utils.Possibilitat(5)) loot.add(new ItemStack(Material.ENDER_PEARL));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.NETHER_STAR));
		if (Utils.Possibilitat(8)) loot.add(new ItemStack(Material.BOOK));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.GOLDEN_SWORD));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.IRON_SWORD));
		if (Utils.Possibilitat(15)) loot.add(llibreEncantatAleatori());
		return loot;
	}

	private ItemStack llibreEncantatAleatori() {
		Registry<Enchantment> registre = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);
		List<Enchantment> encantaments = registre.stream().toList();
		Enchantment ench = encantaments.get(Utils.NombreEntre(0, encantaments.size() - 1));
		if (Utils.Possibilitat(5)) ench = Enchantment.PROTECTION;
		if (Utils.Possibilitat(5)) ench = Enchantment.SHARPNESS;
		if (Utils.Possibilitat(5)) ench = Enchantment.POWER;
		ItemStack llibre = new ItemStack(Material.ENCHANTED_BOOK);
		if (llibre.getItemMeta() instanceof EnchantmentStorageMeta meta) {
			meta.addStoredEnchant(ench, Utils.NombreEntre(3, 4), true);
			llibre.setItemMeta(meta);
		}
		return llibre;
	}

	/** One nugget per cycle, more for holding a gold block or the gold pickaxe, and a bonus for both. */
	private void donarOrPassiu(Player p) {
		int or = 1;
		Inventory inv = p.getInventory();
		if (inv.contains(Material.GOLD_BLOCK)) {
			sendPlayerMessage(p, ChatColor.GRAY + "Bloc d'or --> +2 Or passiu");
			or += 2;
		}
		if (inv.contains(Material.GOLDEN_PICKAXE)) {
			sendPlayerMessage(p, ChatColor.GRAY + "Pic d'or --> +3 Or passiu");
			or += 3;
		}
		if (or >= 5) {
			int extra = Utils.NombreEntre(1, 4);
			or += extra;
			sendPlayerMessage(p, ChatColor.GRAY + "Combinació --> +" + extra + " Or passiu");
		}
		donarOr(p, or);
	}

	private void desgastarEquipament(Player p) {
		for (ItemStack item : p.getInventory().getContents()) desgastar(item);
		for (ItemStack item : p.getInventory().getArmorContents()) desgastar(item);
	}

	private void desgastar(ItemStack item) {
		if (item == null) return;
		int desgast = desgastPerCicle(item);
		if (desgast == 0) return;
		if (item.getItemMeta() instanceof Damageable meta) {
			meta.setDamage(meta.getDamage() + desgast);
			item.setItemMeta(meta);
		}
	}

	/** Wear per chest cycle. An untouched or enchanted item does not wear, as in 2013. */
	private int desgastPerCicle(ItemStack item) {
		if (!(item.getItemMeta() instanceof Damageable meta) || meta.getDamage() == 0) return 0;
		if (!item.getEnchantments().isEmpty()) return 0;
		return switch (item.getType()) {
			case CHAINMAIL_HELMET, GOLDEN_HELMET, STONE_SWORD -> 1;
			case CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS -> 4;
			case BOW -> 18;
			case IRON_SWORD -> 2;
			case DIAMOND_SWORD -> 46;
			case IRON_CHESTPLATE -> 11;
			case DIAMOND_BOOTS, DIAMOND_LEGGINGS -> 38;
			default -> 0;
		};
	}

	//---------- Diamond pickaxe (PicDiamantTask, 2013) ----------

	private Location puntPicDiamant() {
		if (pMapaActual().ExisteixPropietat("PicDiamant")) {
			return pMapaActual().ObtenirLocation("PicDiamant", world).add(0.5, 1, 0.5);
		}
		return PIC_DIAMANT_2013.toLocation(world).add(0.5, 0, 0.5);
	}

	/** A pickaxe good for exactly one block of obsidian, dropped in the middle of the map. */
	private void apareixerPicDiamant() {
		if (!JocEnMarxa()) return;
		ItemStack pic = new ItemStack(Material.DIAMOND_PICKAXE);
		if (pic.getItemMeta() instanceof Damageable meta) {
			meta.setDamage(Material.DIAMOND_PICKAXE.getMaxDurability() - 1);
			pic.setItemMeta(meta);
		}
		world.dropItem(puntPicDiamant(), pic).setVelocity(new Vector(0, 0, 0));
		if (!primerPicAnunciat) {
			sendGlobalMessage(ChatColor.AQUA + "Ha aparegut el primer pic de diamant!");
			primerPicAnunciat = true;
		}
	}

	@Override
	protected void onPlayerPickupItem(PlayerPickupItemEvent evt, Player p) {
		super.onPlayerPickupItem(evt, p);
		Item item = evt.getItem();
		if (item.getItemStack().getType() != Material.DIAMOND_PICKAXE) return;
		if (item.getLocation().distance(puntPicDiamant()) < 1.5) {
			int or = 3;
			donarOr(p, or);
			sendPlayerMessage(p, "Has agafat el pic de diamant" + "(" + ChatColor.GOLD + "+" + or + ChatColor.WHITE + ")");
		}
	}

	//---------- The Guardian: the iron golem (ApareixerGolem, 2013) with its aura ----------

	private void apareixerGolem() {
		if (!JocEnMarxa() || !pMapaActual().ExisteixPropietat("Golem")) return;
		Location punt = guardianSpawnPoint();
		IronGolem golem = world.spawn(punt, IronGolem.class);
		golem.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400 * 20, 1, true), true);
		golem.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 400 * 20, 1, true), true);
		golem.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400 * 20, 1, true), true);
		golem.setRemoveWhenFarAway(false);
		golem.setPersistent(true);
		golem.customName(PaperMessages.legacy(ChatColor.AQUA + NOM_GUARDIÀ));
		golem.setCustomNameVisible(true);
		golemActual = golem.getUniqueId();
		guardiàEnfurismat = false;
		barraGuardià().progress(1F);
		world.playSound(punt, Sound.BLOCK_BEACON_ACTIVATE, 2F, 1F);
		world.playSound(punt, Sound.ENTITY_IRON_GOLEM_REPAIR, 1.5F, 0.8F);
		for (Player p : getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.6F, 1F);
		if (!guardiàAnunciat) {
			guardiàAnunciat = true;
			sendGlobalMessage(ChatColor.AQUA + NOM_GUARDIÀ + ChatColor.WHITE + " s'ha despertat sota el mig: " + ChatColor.GOLD + OR_PER_GOLEM + " d'or" + ChatColor.WHITE + " i 3 min de Resistència i Velocitat per a qui el mati.");
		} else {
			sendGlobalMessage(ChatColor.AQUA + NOM_GUARDIÀ + ChatColor.WHITE + " s'ha despertat.");
		}
		if (tascaAuraGuardià != -1) Bukkit.getScheduler().cancelTask(tascaAuraGuardià);
		tascaAuraGuardià = scheduleGameplayRepeatingTask(this::auraDelGuardià, 0, AURA_GUARDIÀ_PERIODE_TICKS);
		updateScoreBoards();
	}

	private Location puntDelGuardià() {
		return pMapaActual().ObtenirLocation("Golem", world);
	}

	/** Beside the hut's chest, which stays as loot for whoever gets there first: the doorway side, else above the chest. */
	private Location guardianSpawnPoint() {
		Block cofre = puntDelGuardià().getBlock().getRelative(BlockFace.UP);
		Block costat = cofre.getRelative(BlockFace.SOUTH);
		Block peus = costat.isPassable() && costat.getRelative(BlockFace.UP).isPassable() ? costat : cofre.getRelative(BlockFace.UP);
		return peus.getLocation().add(0.5, 0, 0.5);
	}

	private boolean ésElGolem(Entity e) {
		return e instanceof IronGolem && golemActual != null && golemActual.equals(e.getUniqueId());
	}

	private boolean matatPelGuardià(Player mort) {
		return mort.getLastDamageCause() instanceof EntityDamageByEntityEvent cop && ésElGolem(cop.getDamager());
	}

	private IronGolem guardiàViu() {
		if (golemActual == null) return null;
		Entity e = Bukkit.getEntity(golemActual);
		return e instanceof IronGolem golem && !golem.isDead() ? golem : null;
	}

	private BossBar barraGuardià() {
		if (barraGuardià == null) {
			barraGuardià = BossBar.bossBar(PaperMessages.legacy(ChatColor.AQUA + NOM_GUARDIÀ), 1F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_10);
		}
		return barraGuardià;
	}

	/**
	 * Every five ticks while the Guardian lives: a slowly turning ring of dust and soul
	 * flames at its feet, a mote rising from its chest, and the beam over the lair that
	 * tells the surface it is up. Particles only; no block is touched.
	 */
	private void auraDelGuardià() {
		IronGolem golem = guardiàViu();
		if (golem == null) return;
		passosAura++;
		Location peus = golem.getLocation();
		Particle.DustOptions pols = new Particle.DustOptions(guardiàEnfurismat ? COLOR_AURA_ENFURISMAT : COLOR_AURA_GUARDIÀ, 1.1F);
		double gir = passosAura * 0.15;
		for (int i = 0; i < 12; i++) {
			double angle = gir + i * Math.PI / 6;
			world.spawnParticle(Particle.DUST, peus.clone().add(RADI_AURA_GUARDIÀ * Math.cos(angle), 0.1, RADI_AURA_GUARDIÀ * Math.sin(angle)), 1, 0, 0, 0, 0, pols);
		}
		int flames = guardiàEnfurismat ? 4 : 2;
		for (int i = 0; i < flames; i++) {
			double angle = Math.random() * 2 * Math.PI, radi = Math.random() * RADI_AURA_GUARDIÀ;
			world.spawnParticle(Particle.SOUL_FIRE_FLAME, peus.clone().add(radi * Math.cos(angle), 0.1, radi * Math.sin(angle)), 0, 0, 1, 0, 0.04);
		}
		if (passosAura % 2 == 0) world.spawnParticle(Particle.END_ROD, peus.clone().add(0, 1.4, 0), 0, 0, 1, 0, 0.03);
		Location feix = puntDelGuardià().add(0.5, FEIX_GUARDIÀ_BASE, 0.5);
		for (int i = 0; i < 3; i++) world.spawnParticle(Particle.END_ROD, feix.clone().add(0, Math.random() * FEIX_GUARDIÀ_ALÇADA, 0), 1, 0, 0, 0, 0);
		if (passosAura % 2 == 0) {
			for (int i = 0; i < 8; i++) {
				double angle = gir + i * Math.PI / 4;
				world.spawnParticle(Particle.DUST, feix.clone().add(0.8 * Math.cos(angle), 0.1, 0.8 * Math.sin(angle)), 1, 0, 0, 0, 0, pols);
			}
		}
	}

	/**
	 * Every second for the whole match. While the Guardian lives: who sees its boss bar,
	 * the hum on the canal, and the enrage tint under 30 % health. While it is dead: the
	 * scoreboard countdown to its return.
	 */
	private void presènciaDelGuardià() {
		IronGolem golem = guardiàViu();
		if (golem == null) {
			if (guardiàTornaAlSegon > segonsTranscorreguts()) updateScoreBoards();
			return;
		}
		BossBar barra = barraGuardià();
		double fracció = golem.getHealth() / golem.getAttribute(Attribute.MAX_HEALTH).getValue();
		barra.progress((float) Math.max(0, Math.min(1, fracció)));
		for (Player p : getPlayers()) {
			if (p.getLocation().distance(golem.getLocation()) <= DISTÀNCIA_BARRA_GUARDIÀ) p.showBossBar(barra);
			else p.hideBossBar(barra);
		}
		if (!guardiàEnfurismat && fracció < FRACCIÓ_VIDA_ENFURISMAT) {
			guardiàEnfurismat = true;
			world.playSound(golem.getLocation(), Sound.ENTITY_IRON_GOLEM_REPAIR, 1.5F, 0.6F);
		}
		if (++segonsPresènciaGuardià % SEGONS_ENTRE_BRUNZITS_GUARDIÀ == 0) world.playSound(golem.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 0.7F, 1F);
	}

	private void apagarAuraDelGuardià() {
		if (tascaAuraGuardià != -1) {
			Bukkit.getScheduler().cancelTask(tascaAuraGuardià);
			tascaAuraGuardià = -1;
		}
		if (barraGuardià != null && world != null) for (Player p : world.getPlayers()) p.hideBossBar(barraGuardià);
	}

	/** The scoreboard's word for the Guardian: alive, or the time until it returns. */
	private String estatGuardià() {
		if (guardiàViu() != null) return "viu";
		int segons = guardiàTornaAlSegon - segonsTranscorreguts();
		if (segons <= 0) return "arriba";
		return String.format("%d:%02d", segons / 60, segons % 60);
	}

	@Override
	protected void onEntityDeath(EntityDeathEvent evt, Entity e) {
		super.onEntityDeath(evt, e);
		if (!ésElGolem(e)) return;
		golemActual = null;
		apagarAuraDelGuardià();
		Location pit = e.getLocation().add(0, 1.3, 0);
		world.playSound(pit, Sound.BLOCK_BEACON_DEACTIVATE, 2F, 1F);
		world.spawnParticle(Particle.DUST, pit, 60, 0.6, 0.8, 0.6, 0, new Particle.DustOptions(COLOR_AURA_GUARDIÀ, 1.4F));
		world.spawnParticle(Particle.END_ROD, pit, 20, 0.3, 0.5, 0.3, 0.1);
		for (Player p : getPlayers()) p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_DEATH, 0.5F, 1F);
		Player p = ((IronGolem) e).getKiller();
		if (p != null) {
			donarOr(p, OR_PER_GOLEM);
			p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 3 * 60 * 20, 1, false), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30 * 20, 1, false), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * 60 * 20, 1, false), true);
			PaperMessages.sendActionBar(p, ChatColor.AQUA + "Benedicció del Guardià", 60);
			sendGlobalMessage(p.getName() + " ha matat " + ChatColor.AQUA + NOM_GUARDIÀ + ChatColor.WHITE + ", el golem de ferro (" + ChatColor.GOLD + "+" + OR_PER_GOLEM + ChatColor.WHITE + ")");
		}
		int minuts = getPlayers().size() >= 5 ? 2 : 3;
		guardiàTornaAlSegon = segonsTranscorreguts() + minuts * 60;
		sendGlobalMessage(ChatColor.AQUA + NOM_GUARDIÀ + ChatColor.WHITE + " tornarà d'aquí a " + minuts + " min.");
		scheduleGameplayTask(this::apareixerGolem, minuts * 60L * 20L);
		updateScoreBoards();
	}

	//---------- Combat ----------

	@Override
	protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager, boolean ranged) {
		super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
		if (!JocIniciat) {
			evt.setCancelled(true);
			return;
		}
		segonsÚltimCopRebut.put(damaged.getUniqueId(), segonsTranscorreguts());
		if (damager.getLocation().getBlockY() >= 50 && !ranged) {
			evt.setDamage(evt.getDamage() * 1.6 + Utils.NombreEntre(1, 11));
		}
		if (damager.getLocation().getBlockY() >= 45) {
			evt.setDamage(evt.getDamage() + Utils.NombreEntre(1, 5));
		}
		evt.setDamage(evt.getDamage() * 0.8);
		if (Ability.hasAbility(plugin, this, damager, AbilityType.ARQUER_PERFECTE) && !evt.isCancelled() && ranged) {
			int crg = pPlayer(damager).ObtenirPropietatInt("PerfectBowHitCount");
			if (crg >= 3) {
				ArrayList<Location> locs = Utils.getLocationsCircle(damaged.getLocation(), 1.0, 40);
				for (Location loc : locs) {
					if (loc.distance(damager.getLocation()) > damaged.getLocation().distance(damager.getLocation())) {
						Vector vec2 = Utils.CrearVector(damaged.getLocation(), loc).normalize();
						Arrow arrow = (Arrow) world.spawnEntity(loc, EntityType.ARROW);
						arrow.setShooter(damager);
						arrow.setFireTicks(200);
						arrow.setVelocity(vec2.multiply(8));
					}
				}
				pPlayer(damager).EstablirPropietat("PerfectBowHitCount", 1);
				damager.playSound(damager.getLocation(), Sound.ENTITY_GENERIC_SWIM, 1, 0.5F);
			} else {
				pPlayer(damager).IncrementarPropietat("PerfectBowHitCount");
				if (crg == 5) {
					damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				}
			}
			updateScoreBoard(damager);
		}
		if (Ability.hasAbility(plugin, this, damager, AbilityType.ARQUER_DE_GEL) && !evt.isCancelled() && ranged) {
			int crg = pPlayer(damager).ObtenirPropietatInt("StrongBowHitCount");
			if (crg >= 6) {
				ArrayList<BlockFace> faces = new ArrayList<>();
				faces.add(BlockFace.NORTH);
				faces.add(BlockFace.SOUTH);
				faces.add(BlockFace.WEST);
				faces.add(BlockFace.EAST);
				for (BlockFace face : faces) {
					Block block = damaged.getLocation().getBlock().getRelative(face);
					if (block.getType() != Material.AIR) {
						continue;
					}
					block.setType(Material.ICE);
					scheduleTrackedBlockRemoval(block, 20 * 4, false);
				}
				damaged.teleport(damaged.getLocation().getBlock().getLocation().add(new Vector(0.5, 0, 0.5)));
				Block gblock = damaged.getLocation().add(0, 2, 0).getBlock();
				if (gblock.getType() == Material.AIR) {
					gblock.setType(Material.GOLD_BLOCK);
					scheduleTrackedBlockRemoval(gblock, 20 * 4, false);
				}
				pPlayer(damager).EstablirPropietat("StrongBowHitCount", 1);
				damaged.playSound(damager.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 0.5F);
			} else {
				pPlayer(damager).IncrementarPropietat("StrongBowHitCount");
				if (crg == 5) {
					damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				}
			}
			updateScoreBoard(damager);
		}
		if (Ability.hasAbility(plugin, this, damager, AbilityType.ESPADATXI) && !evt.isCancelled()) {
			int crg = pPlayer(damager).ObtenirPropietatInt("StrongHitCount");
			if (crg >= 5) {
				evt.setDamage(evt.getDamage() * 1.5);
				Vector rawDir = damaged.getLocation().toVector().subtract(damager.getLocation().toVector());
				Vector dir = rawDir.normalize().multiply(2).add(new Vector(0, 0.3, 0));
				damaged.setVelocity(dir);
				pPlayer(damager).EstablirPropietat("StrongHitCount", 1);
				damaged.playSound(damager.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 0.3F);
			} else {
				pPlayer(damager).IncrementarPropietat("StrongHitCount");
				if (crg == 5) {
					damaged.playSound(damager.getLocation(), Sound.ENTITY_HORSE_LAND, 1, 0.3F);
				}
			}
			updateScoreBoard(damager);
		}
		if (Ability.hasAbility(plugin, this, damaged, AbilityType.RESISTENCIA)) {
			double dmgm = 0.9;
			dmgm = dmgm - (Utils.getNearbyPlayers(damaged, 10).size() * 0.08);
			if (dmgm <= 0.1) { dmgm = 0.1; }
			double finaldmg = evt.getDamage() * 0.85;
			evt.setDamage(finaldmg);
			if (debug) {
				Bukkit.broadcastMessage("Mal reduït: " + Double.toString(evt.getDamage() - finaldmg) + " - " + dmgm * 100 + "%");
			}
		}
		// The kit armour never wears from hits; bought armour does, faster against a Destructor.
		ItemStack[] armor = damaged.getInventory().getArmorContents();
		for (ItemStack i : armor) {
			if (i == null || !(i.getItemMeta() instanceof Damageable meta)) continue;
			Material mat = i.getType();
			if (mat == Material.LEATHER_HELMET || mat == Material.CHAINMAIL_CHESTPLATE || mat == Material.CHAINMAIL_LEGGINGS || mat == Material.CHAINMAIL_BOOTS) {
				meta.setDamage(0);
				i.setItemMeta(meta);
			} else if (Ability.hasAbility(plugin, this, damager, AbilityType.DESTRUCTOR)) {
				int morts = pTemp().ObtenirPropietatInt(damager.getName() + "Morts");
				meta.setDamage(meta.getDamage() + 5 + morts);
				i.setItemMeta(meta);
			}
		}
		damaged.getInventory().setArmorContents(armor);
	}

	@Override
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent evt, Entity damaged, Entity damager) {
		super.onEntityDamageByEntity(evt, damaged, damager);
		if (damaged instanceof IronGolem && damager instanceof Player p) {
			ItemStack item = p.getInventory().getItemInMainHand();
			if (item.getType() == Material.IRON_PICKAXE && item.getItemMeta() instanceof Damageable meta) {
				evt.setDamage(30);
				meta.setDamage(meta.getDamage() + (item.getType().getMaxDurability() / 4));
				item.setItemMeta(meta);
			}
		}
		if (damaged instanceof Player player && damager instanceof IronGolem) {
			if (Ability.hasAbility(plugin, this, player, AbilityType.PROTECCIÓ_IMPACTE)) {
				evt.setDamage(evt.getDamage() / 2);
			}
		}
	}

	//---------- The map's signs: booths and the bridge button ----------

	/**
	 * Reads the signs around each base once. A booth sign hands its villager (the map's
	 * own, adopted in place, or a new one when the booth is empty) to the team; the
	 * "Deploy enemy's bridge" sign becomes that team's charge bar and its button the
	 * control; the sewers sign just says the sewers are open, since they always are now.
	 */
	private void registrarControlsIParades() {
		for (Equip e : Equips) {
			Location base = e.getTeamSpawnLocation();
			for (Sign rètol : rètolsAlVoltant(base, RADI_RÈTOLS)) {
				String text = String.join(" ", rètol.getSide(Side.FRONT).getLines()).toLowerCase();
				Block bloc = rètol.getBlock();
				if (text.contains("bridge")) {
					rètolsPont.put(e.getId(), bloc);
					Block botó = botóVeí(bloc);
					if (botó != null) botonsPont.put(botó, e.getId());
					escriureRètol(bloc, etiquetaPont(e, "Pont enemic"), barraPont(e), "", "");
				} else if (text.contains("sewers")) {
					escriureRètol(bloc, ChatColor.DARK_GRAY + "Clavegueres", ChatColor.GRAY + "obertes", "", "");
				} else {
					for (Parada parada : Parada.values()) {
						if (!text.contains(parada.rètolOriginal)) continue;
						escriureRètol(bloc, ChatColor.GOLD + parada.nom, ChatColor.GRAY + parada.ofici, "", "");
						Villager botiguer = villagerDeLaParada(bloc.getLocation());
						adoptarBotiguer(botiguer, new ParadaDeLEquip(parada, e));
					}
				}
			}
			if (!rètolsPont.containsKey(e.getId())) {
				plugin.getLogger().warning(getGameName() + " " + getMapName() + ": no bridge sign within " + RADI_RÈTOLS + " blocks of base" + e.getId());
			}
		}
	}

	private List<Sign> rètolsAlVoltant(Location centre, int radi) {
		List<Sign> rètols = new ArrayList<>();
		int cx = centre.getBlockX() >> 4, cz = centre.getBlockZ() >> 4, chunks = (radi >> 4) + 1;
		for (int dx = -chunks; dx <= chunks; dx++) {
			for (int dz = -chunks; dz <= chunks; dz++) {
				for (BlockState estat : world.getChunkAt(cx + dx, cz + dz).getTileEntities()) {
					if (estat instanceof Sign rètol && rètol.getLocation().distance(centre) <= radi) rètols.add(rètol);
				}
			}
		}
		return rètols;
	}

	private static Block botóVeí(Block rètol) {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					Block b = rètol.getRelative(dx, dy, dz);
					if (Tag.BUTTONS.isTagged(b.getType())) return b;
				}
			}
		}
		return null;
	}

	private static void escriureRètol(Block bloc, String... línies) {
		if (!(bloc.getState() instanceof Sign rètol)) return;
		for (int i = 0; i < 4 && i < línies.length; i++) rètol.getSide(Side.FRONT).line(i, PaperMessages.legacy(línies[i]));
		rètol.update(true, false);
	}

	/** The map's villager nearest the booth sign, or a new one on the nearest standing spot. */
	private Villager villagerDeLaParada(Location rètol) {
		Villager mésProper = null;
		double millor = 8;
		for (Villager v : world.getEntitiesByClass(Villager.class)) {
			if (botiguers.containsKey(v.getUniqueId())) continue;
			double d = v.getLocation().distance(rètol);
			if (d < millor) { millor = d; mésProper = v; }
		}
		if (mésProper != null) return mésProper;
		Location peus = null;
		for (int dx = -2; dx <= 2 && peus == null; dx++) {
			for (int dz = -2; dz <= 2 && peus == null; dz++) {
				Block b = rètol.getBlock().getRelative(dx, 0, dz);
				if (b.isPassable() && b.getRelative(0, 1, 0).isPassable() && !b.getRelative(0, -1, 0).isPassable() && !(b.getState() instanceof Sign)) {
					peus = b.getLocation().add(0.5, 0, 0.5);
				}
			}
		}
		return world.spawn(peus != null ? peus : rètol.clone().add(0.5, 0, 0.5), Villager.class);
	}

	private void adoptarBotiguer(Villager botiguer, ParadaDeLEquip parada) {
		botiguer.setAI(false);
		botiguer.setInvulnerable(true);
		botiguer.setSilent(true);
		botiguer.setCollidable(false);
		botiguer.setRemoveWhenFarAway(false);
		botiguer.setPersistent(true);
		botiguer.setRecipes(java.util.Collections.emptyList());
		botiguer.setProfession(switch (parada.parada()) {
			case GERRY -> Villager.Profession.WEAPONSMITH;
			case SEON -> Villager.Profession.ARMORER;
			case KAREN -> Villager.Profession.CLERIC;
		});
		botiguer.customName(PaperMessages.legacy(ChatColor.GOLD + parada.parada().nom));
		botiguer.setCustomNameVisible(true);
		botiguers.put(botiguer.getUniqueId(), parada);
	}

	@Override
	public void clearExternals() {
		apagarAuraDelGuardià();
		for (UUID id : botiguers.keySet()) {
			Entity botiguer = Bukkit.getEntity(id);
			if (botiguer != null) botiguer.remove();
		}
		botiguers.clear();
		super.clearExternals();
	}

	@Override
	protected void onEntityDamage(EntityDamageEvent evt, Entity e) {
		super.onEntityDamage(evt, e);
		if (botiguers.containsKey(e.getUniqueId())) evt.setCancelled(true);
	}

	@Override
	protected void onPlayerInteractEntity(PlayerInteractEntityEvent evt, Player p) {
		super.onPlayerInteractEntity(evt, p);
		ParadaDeLEquip parada = botiguers.get(evt.getRightClicked().getUniqueId());
		if (parada == null) return;
		evt.setCancelled(true);
		if (evt.getHand() != EquipmentSlot.HAND || !JocEnMarxa()) return;
		if (obtenirEquip(p) != parada.equip()) {
			rebutjarElementEnemic(p);
			return;
		}
		obrirMenúParada(p, parada.parada());
	}

	/** An enemy's booth villager or bridge button: the villager's "no" and a grey line saying why. */
	private static void rebutjarElementEnemic(Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
		p.sendMessage(ChatColor.GRAY + "No pots interactuar amb elements de l'equip enemic");
	}

	private void obrirMenúParada(Player p, Parada parada) {
		List<Mercaderia> mercaderies = parada.mercaderies;
		IconMenu menu = new IconMenu(ChatColor.GOLD + parada.nom, 9, event -> {
			int posició = event.getPosition();
			if (posició < mercaderies.size()) comprar(event.getPlayer(), mercaderies.get(posició));
			event.setWillClose(false);
		});
		for (Mercaderia m : mercaderies) {
			ArrayList<String> info = new ArrayList<>();
			if (m.descripció != null) info.add(ChatColor.GRAY + m.descripció);
			info.add(ChatColor.WHITE + "Preu: " + ChatColor.GOLD + m.preu + " or");
			menu.setOption(mercaderies.indexOf(m), new ItemStack(m.material, m.quantitat), ChatColor.YELLOW + m.nom, info);
		}
		menu.open(p);
	}

	private void comprar(Player p, Mercaderia m) {
		if (!JocEnMarxa() || !gastarOr(p, m.preu)) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			return;
		}
		Utils.giveItemStack(new ItemStack(m.material, m.quantitat), p);
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
		updateScoreBoard(p);
	}

	private int orDisponible(Player p) {
		Inventory inv = p.getInventory();
		int nuggets = 0;
		for (ItemStack item : inv.getContents()) {
			if (item == null) continue;
			if (item.getType() == Material.GOLD_NUGGET) nuggets += item.getAmount();
			if (item.getType() == Material.GOLD_INGOT) nuggets += 10 * item.getAmount();
		}
		return nuggets;
	}

	/** Takes the price in nuggets, breaking ingots when needed and returning the change as nuggets. */
	private boolean gastarOr(Player p, int preu) {
		if (orDisponible(p) < preu) return false;
		Inventory inv = p.getInventory();
		int nuggets = 0;
		for (ItemStack item : inv.getContents()) {
			if (item != null && item.getType() == Material.GOLD_NUGGET) nuggets += item.getAmount();
		}
		int lingots = 0;
		while (nuggets + 10 * lingots < preu) lingots++;
		if (lingots > 0) inv.removeItem(new ItemStack(Material.GOLD_INGOT, lingots));
		int aPagarEnNuggets = preu - 10 * lingots;
		if (aPagarEnNuggets > 0) inv.removeItem(new ItemStack(Material.GOLD_NUGGET, aPagarEnNuggets));
		if (aPagarEnNuggets < 0) inv.addItem(new ItemStack(Material.GOLD_NUGGET, -aPagarEnNuggets));
		return true;
	}

	//---------- The bridge ----------

	/**
	 * Finds the plank columns each moat's bridge is missing: from the seed block over
	 * the gap, along x in both directions while the deck level is air over water, three
	 * wide. Ordered from the far bank toward the base the moat protects, which is the
	 * order the attackers want it built in.
	 */
	private void registrarPonts() {
		for (Equip e : Equips) {
			Location llavor = pMapaActual().ExisteixPropietat("PontMoat" + e.getId())
					? pMapaActual().ObtenirLocation("PontMoat" + e.getId(), world)
					: PONT_MOAT_2013[Math.min(e.getId(), PONT_MOAT_2013.length - 1)].toLocation(world);
			// Each of the three rows is walked on its own: the 2013 decks are not square,
			// one row's planks start a block further out than its neighbours'.
			Map<Integer, List<Block>> columnesPerX = new java.util.TreeMap<>();
			for (int dz = -1; dz <= 1; dz++) {
				for (int direcció : new int[] { -1, 1 }) {
					for (int pas = direcció == 1 ? 1 : 0; pas < 20; pas++) {
						int x = llavor.getBlockX() + direcció * pas;
						Block deck = world.getBlockAt(x, PONT_ALÇADA, llavor.getBlockZ() + dz);
						if (!ésBuitSobreAigua(deck)) break;
						columnesPerX.computeIfAbsent(x, k -> new ArrayList<>()).add(deck);
					}
				}
			}
			List<List<Block>> columnes = new ArrayList<>(columnesPerX.values());
			Location base = e.getTeamSpawnLocation();
			columnes.sort((a, b) -> Double.compare(b.get(0).getLocation().distance(base), a.get(0).getLocation().distance(base)));
			pontsPerMoat.put(e.getId(), columnes);
			càrregaPont.put(e.getId(), 0);
			if (columnes.isEmpty()) {
				plugin.getLogger().warning(getGameName() + " " + getMapName() + ": no bridge gap found over the moat of base" + e.getId() + " around " + llavor.toVector());
			} else {
				plugin.getLogger().info(getGameName() + " " + getMapName() + ": the moat of base" + e.getId() + " needs " + columnes.size() + " bridge columns");
			}
		}
	}

	private static boolean ésBuitSobreAigua(Block deck) {
		if (!deck.isPassable() || deck.isLiquid()) return false;
		for (int dy = 1; dy <= 3; dy++) {
			Block sota = deck.getRelative(0, -dy, 0);
			if (sota.getType() == Material.WATER) return true;
			if (!sota.isPassable()) return false;
		}
		return false;
	}

	private void carregarPont(Equip e, int quantitat) {
		if (e == null || !JocEnMarxa()) return;
		int abans = càrregaPont.getOrDefault(e.getId(), 0);
		int ara = Math.min(PONT_CÀRREGA_MÀXIMA, abans + quantitat);
		if (ara == abans) return;
		càrregaPont.put(e.getId(), ara);
		if (ara == PONT_CÀRREGA_MÀXIMA) {
			sendTeamMessage(e, ChatColor.AQUA + "Pont enemic a punt.");
			for (Player p : e.getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1.4F);
		}
		mostrarCàrregaPont(e);
	}

	private boolean pontCarregat(Equip e) {
		return càrregaPont.getOrDefault(e.getId(), 0) >= PONT_CÀRREGA_MÀXIMA;
	}

	/** White squares fill quietly; the bar and its label turn aqua only when the bridge is ready. */
	private String barraPont(Equip e) {
		int càrrega = càrregaPont.getOrDefault(e.getId(), 0);
		ChatColor plens = pontCarregat(e) ? ChatColor.AQUA : ChatColor.WHITE;
		return plens + QUADRAT_PLE.repeat(càrrega) + ChatColor.DARK_GRAY + QUADRAT_BUIT.repeat(PONT_CÀRREGA_MÀXIMA - càrrega);
	}

	private String etiquetaPont(Equip e, String text) {
		return (pontCarregat(e) ? ChatColor.AQUA : ChatColor.GRAY) + text;
	}

	private void mostrarCàrregaPont(Equip e) {
		Block rètol = rètolsPont.get(e.getId());
		if (rètol != null) escriureRètol(rètol, etiquetaPont(e, "Pont enemic"), barraPont(e), "", "");
		lightLamps(e);
		for (Player p : e.getPlayers()) updateScoreBoard(p);
	}

	private void prémerBotóPont(Player p, Equip e) {
		if (!JocEnMarxa()) return;
		if (obtenirEquip(p) != e) {
			rebutjarElementEnemic(p);
			return;
		}
		Equip enemic = obtenirEquipEnemic(e);
		if (!pontCarregat(e) || pontsDesplegats.contains(enemic.getId())) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			return;
		}
		càrregaPont.put(e.getId(), 0);
		mostrarCàrregaPont(e);
		desplegarPont(enemic, e);
	}

	/** Builds the bridge over the enemy's moat one column at a time, keeps it, then takes it back the same way. */
	private void desplegarPont(Equip moatDe, Equip atacant) {
		List<List<Block>> columnes = pontsPerMoat.getOrDefault(moatDe.getId(), List.of());
		if (columnes.isEmpty()) return;
		pontsDesplegats.add(moatDe.getId());
		sendGlobalMessage(atacant.getChatColor() + "L'equip " + atacant.getAdjectiu() + ChatColor.WHITE + " desplega el pont sobre el fossat " + moatDe.getAdjectiuColored() + ChatColor.WHITE + ".");
		sendGlobalSound(Sound.BLOCK_PISTON_EXTEND, 1F, 0.6F);
		for (int i = 0; i < columnes.size(); i++) {
			List<Block> columna = columnes.get(i);
			scheduleGameplayTask(() -> posarColumnaDePont(columna, true), PONT_TICKS_PER_COLUMNA * (i + 1));
		}
		long totalDesplegament = PONT_TICKS_PER_COLUMNA * columnes.size();
		for (int i = 0; i < columnes.size(); i++) {
			List<Block> columna = columnes.get(columnes.size() - 1 - i);
			scheduleGameplayTask(() -> posarColumnaDePont(columna, false), totalDesplegament + PONT_TICKS_DESPLEGAT + PONT_TICKS_PER_COLUMNA * (i + 1));
		}
		scheduleGameplayTask(() -> pontsDesplegats.remove(moatDe.getId()), totalDesplegament + PONT_TICKS_DESPLEGAT + PONT_TICKS_PER_COLUMNA * (columnes.size() + 1));
	}

	private void posarColumnaDePont(List<Block> columna, boolean posar) {
		if (!JocEnMarxa()) return;
		for (Block b : columna) b.setType(posar ? Material.OAK_PLANKS : Material.AIR);
		Location so = columna.get(0).getLocation();
		world.playSound(so, posar ? Sound.BLOCK_PISTON_EXTEND : Sound.BLOCK_PISTON_CONTRACT, 1F, 1F);
	}

	//---------- The control points, the base lamps and the dispensers ----------

	/**
	 * Finds the coloured plates between the bases, groups them into control points by
	 * proximity, and finds each base's lamp bank. A plate's team is the majority colour
	 * among its eight horizontal neighbours at its level and one below; a plate with no
	 * colour (the keep doors) is not a control point.
	 */
	private void registerControlPointsAndLamps() {
		controlPoints.clear();
		lampsByTeam.clear();
		pointSecondsByTeam.clear();
		if (Equips.size() < 2) return;
		Location a = Equips.get(0).getTeamSpawnLocation(), b = Equips.get(1).getTeamSpawnLocation();
		int minX = Math.min(a.getBlockX(), b.getBlockX()), maxX = Math.max(a.getBlockX(), b.getBlockX());
		int midZ = (a.getBlockZ() + b.getBlockZ()) / 2;
		for (Block plate : blocksBetween(minX, midZ - PLATE_BAND_HALF_WIDTH, maxX, midZ + PLATE_BAND_HALF_WIDTH, m -> Tag.PRESSURE_PLATES.isTagged(m))) {
			Equip team = teamOfColourAround(plate);
			if (team == null) continue;
			ControlPoint point = null;
			for (ControlPoint candidate : controlPoints) {
				if (candidate.centre().distance(plate.getLocation()) <= CONTROL_POINT_RADIUS) point = candidate;
			}
			if (point == null) {
				point = new ControlPoint();
				controlPoints.add(point);
			}
			point.plateByTeam.put(team.getId(), plate);
		}
		for (ControlPoint point : controlPoints) {
			for (Map.Entry<Integer, Block> plate : point.plateByTeam.entrySet()) {
				Block lamp = nearestBlock(plate.getValue().getLocation(), CONTROL_POINT_RADIUS, m -> m == Material.REDSTONE_LAMP);
				if (lamp != null) point.lampByTeam.put(plate.getKey(), lamp);
			}
			showControlPoint(point);
			plugin.getLogger().info(getGameName() + " " + getMapName() + ": control point at " + point.centre().toVector() + " with plates " + point.plateByTeam.keySet() + " and lamps " + point.lampByTeam.keySet());
		}
		for (Equip e : Equips) {
			Location base = e.getTeamSpawnLocation();
			List<Block> lamps = blocksBetween(base.getBlockX() - RADI_RÈTOLS, base.getBlockZ() - RADI_RÈTOLS, base.getBlockX() + RADI_RÈTOLS, base.getBlockZ() + RADI_RÈTOLS, m -> m == Material.REDSTONE_LAMP);
			lamps.removeIf(l -> l.getLocation().distance(base) > RADI_RÈTOLS);
			lamps.sort((l1, l2) -> Double.compare(l1.getLocation().distance(base), l2.getLocation().distance(base)));
			lampsByTeam.put(e.getId(), lamps);
			lightLamps(e);
			plugin.getLogger().info(getGameName() + " " + getMapName() + ": base" + e.getId() + " has " + lamps.size() + " lamps");
		}
		if (controlPoints.isEmpty()) plugin.getLogger().warning(getGameName() + " " + getMapName() + ": no coloured plates between the bases; the bridge cannot be charged");
	}

	/** Every block of a kind in the box, at the map's play heights. */
	private List<Block> blocksBetween(int minX, int minZ, int maxX, int maxZ, Predicate<Material> kind) {
		List<Block> blocks = new ArrayList<>();
		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				for (int y = SCAN_MIN_Y; y <= SCAN_MAX_Y; y++) {
					Block block = world.getBlockAt(x, y, z);
					if (kind.test(block.getType())) blocks.add(block);
				}
			}
		}
		return blocks;
	}

	private Block nearestBlock(Location centre, double radius, Predicate<Material> kind) {
		Block nearest = null;
		int r = (int) Math.ceil(radius);
		for (int dx = -r; dx <= r; dx++) {
			for (int dy = -r; dy <= r; dy++) {
				for (int dz = -r; dz <= r; dz++) {
					Block block = centre.getBlock().getRelative(dx, dy, dz);
					if (!kind.test(block.getType()) || block.getLocation().distance(centre) > radius) continue;
					if (nearest == null || block.getLocation().distance(centre) < nearest.getLocation().distance(centre)) nearest = block;
				}
			}
		}
		return nearest;
	}

	/** The team whose colour (wool, carpet, concrete, terracotta) rings the plate, if one does. */
	private Equip teamOfColourAround(Block plate) {
		Equip best = null;
		int bestCount = 0;
		for (Equip e : Equips) {
			String prefix = e.getColor().name() + "_";
			int count = 0;
			for (int dx = -1; dx <= 1; dx++) {
				for (int dz = -1; dz <= 1; dz++) {
					if (dx == 0 && dz == 0) continue;
					for (int dy = -1; dy <= 0; dy++) {
						if (plate.getRelative(dx, dy, dz).getType().name().startsWith(prefix)) count++;
					}
				}
			}
			if (count > bestCount) {
				best = e;
				bestCount = count;
			}
		}
		return best;
	}

	private ControlPoint controlPointOf(Block plate) {
		for (ControlPoint point : controlPoints) if (point.teamOfPlate(plate) != null) return point;
		return null;
	}

	private int pointsHeldBy(Equip team) {
		int held = 0;
		for (ControlPoint point : controlPoints) if (point.owner != null && point.owner == team.getId()) held++;
		return held;
	}

	/**
	 * Once a second: captures for whoever stands on their colour's plate, the pressed look
	 * for whoever stepped off, and a bridge square per captured point every five seconds.
	 */
	private void tickControlPoints() {
		if (!JocEnMarxa()) return;
		for (Player p : getPlayers()) {
			UUID id = p.getUniqueId();
			Block feet = p.getLocation().getBlock();
			ControlPoint point = controlPointOf(feet);
			Block shown = shownPressedPlate.get(id);
			if (point == null) {
				if (shown != null) showPlateReleased(p, shown);
				refusedOnPlate.remove(id);
				continue;
			}
			if (!feet.equals(shown)) {
				if (shown != null) showPlateReleased(p, shown);
				showPlatePressed(p, feet);
			}
			Equip team = obtenirEquip(p);
			if (team == null || team.getId() != point.teamOfPlate(feet)) {
				if (refusedOnPlate.add(id)) rebutjarElementEnemic(p);
				continue;
			}
			if (point.owner != null && point.owner == team.getId()) continue;
			capture(point, team, p);
		}
		for (Equip e : Equips) {
			int held = pointsHeldBy(e);
			if (held == 0) continue;
			int seconds = pointSecondsByTeam.merge(e.getId(), held, Integer::sum);
			while (seconds >= SECONDS_PER_POINT_SQUARE) {
				seconds -= SECONDS_PER_POINT_SQUARE;
				carregarPont(e, 1);
			}
			pointSecondsByTeam.put(e.getId(), seconds);
		}
	}

	private void capture(ControlPoint point, Equip team, Player captor) {
		point.owner = team.getId();
		showControlPoint(point);
		donarOr(captor, GOLD_PER_CAPTURE);
		sendGlobalMessage(ChatColor.GRAY + captor.getName() + " ha capturat el punt de control (" + team.getChatColor() + pointsHeldBy(team) + ChatColor.GRAY + "/" + controlPoints.size() + ")");
		world.playSound(point.centre(), Sound.BLOCK_BEACON_POWER_SELECT, 1F, 1.2F);
		for (Player p : team.getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.6F, 1.6F);
	}

	/** The lamp beside the owner's plate is lit, the other one dark. */
	private void showControlPoint(ControlPoint point) {
		for (Map.Entry<Integer, Block> lamp : point.lampByTeam.entrySet()) {
			setLit(lamp.getValue(), point.owner != null && point.owner.equals(lamp.getKey()));
		}
	}

	/** Shows the plate pressed to everyone near it and plays the click; the real block stays unpressed. */
	private void showPlatePressed(Player p, Block plate) {
		shownPressedPlate.put(p.getUniqueId(), plate);
		BlockData pressed = plate.getBlockData().clone();
		if (pressed instanceof Powerable powerable) powerable.setPowered(true);
		for (Player viewer : world.getPlayers()) {
			if (viewer.getLocation().distance(plate.getLocation()) <= PLATE_VIEW_DISTANCE) viewer.sendBlockChange(plate.getLocation(), pressed);
		}
		world.playSound(plate.getLocation(), Sound.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, 0.3F, 0.6F);
	}

	private void showPlateReleased(Player p, Block plate) {
		shownPressedPlate.remove(p.getUniqueId());
		for (Player viewer : world.getPlayers()) {
			if (viewer.getLocation().distance(plate.getLocation()) <= PLATE_VIEW_DISTANCE) viewer.sendBlockChange(plate.getLocation(), plate.getBlockData());
		}
		world.playSound(plate.getLocation(), Sound.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, 0.3F, 0.5F);
	}

	/** The base lamps mirror the bar: with six lamps one per square, otherwise proportionally. */
	private void lightLamps(Equip e) {
		List<Block> lamps = lampsByTeam.getOrDefault(e.getId(), List.of());
		if (lamps.isEmpty()) return;
		int lit = càrregaPont.getOrDefault(e.getId(), 0) * lamps.size() / PONT_CÀRREGA_MÀXIMA;
		for (int i = 0; i < lamps.size(); i++) setLit(lamps.get(i), i < lit);
	}

	/** No physics, so the dormant wiring beside a lamp cannot switch it back. */
	private static void setLit(Block lamp, boolean lit) {
		if (!(lamp.getBlockData() instanceof Lightable lightable) || lightable.isLit() == lit) return;
		lightable.setLit(lit);
		lamp.setBlockData(lightable, false);
	}

	/** The 2013 currency dispensers hold gold nobody should reach: emptied at the start, refused on click and never fired. */
	private void emptyDispensers() {
		for (Equip e : Equips) {
			Location base = e.getTeamSpawnLocation();
			for (Block block : blocksBetween(base.getBlockX() - RADI_RÈTOLS, base.getBlockZ() - RADI_RÈTOLS, base.getBlockX() + RADI_RÈTOLS, base.getBlockZ() + RADI_RÈTOLS, m -> m == Material.DISPENSER || m == Material.DROPPER)) {
				if (block.getState() instanceof Container container) container.getInventory().clear();
			}
		}
	}

	@Override
	protected void onInventoryOpen(InventoryOpenEvent evt, Inventory inv) {
		super.onInventoryOpen(evt, inv);
		if (inv.getHolder() instanceof Dispenser || inv.getHolder() instanceof Dropper) evt.setCancelled(true);
	}

	@Override
	protected void onBlockDispense(BlockDispenseEvent evt, Block blk) {
		super.onBlockDispense(evt, blk);
		evt.setCancelled(true);
	}

	//---------- Gold ----------

	public void donarOr(Player plyr, int Or) {
		ItemStack itemstack = new ItemStack(Material.GOLD_NUGGET, Or);
		Utils.giveItemStack(itemstack, plyr);
		pPlayer(plyr).IncrementarPropietat("Or", Or);
		ajuntarOr(plyr);
		updateScoreBoard(plyr);
	}
	public void donarOrAEquip(ArrayList<Player> equip, int Or, Boolean dividir) {
		if (dividir) { Or = (int) Math.ceil(Or / equip.size()); }
		for (Player p : equip) {
			donarOr(p, Or);
		}
	}
	public void donarOrAEquip(ArrayList<Player> equip, int Or, Boolean dividir, String Text, Boolean broadcast) {
		if (dividir) { Or = (int) Math.ceil(Or / equip.size()); }
		for (Player p : equip) {
			donarOr(p, Or, Text, broadcast);
		}
	}
	public void donarOrATots(int Or) {
		for (Player p : getPlayers()) {
			donarOr(p, Or);
		}
	}
	public void donarOrATots(int Or, String Text, Boolean broadcast) {
		for (Player p : getPlayers()) {
			donarOr(p, Or, Text, broadcast);
		}
	}
	public void donarOr(Player plyr, int Or, String Text, Boolean broadcast) {
		donarOr(plyr, Or);
		String message = Text + ChatColor.WHITE + "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")";
		if (broadcast) {
			Bukkit.broadcastMessage(message);
		} else {
			plyr.sendMessage(ChatColor.GRAY + message);
		}
	}
	/** Ten nuggets become an ingot. */
	public void ajuntarOr(Player p) {
		Inventory inv = p.getInventory();
		for (ItemStack d : inv.getContents()) {
			if (d == null) {
				continue;
			}
			if (d.getType() == Material.GOLD_NUGGET) {
				if (d.getAmount() >= 10) {
					int lingots = d.getAmount() / 10;
					int nuggElim = lingots * 10;
					inv.addItem(new ItemStack(Material.GOLD_INGOT, lingots));
					inv.removeItem(new ItemStack(Material.GOLD_NUGGET, nuggElim));
				}
			}
		}
	}

	//---------- Deaths ----------

	/**
	 * Every death is credited here, including the ones with no killer entity: an
	 * explosive-arrow kill inside two seconds of the shot, or the last player to hit
	 * the victim recently. The bus only calls onPlayerDeathByPlayer with a real killer,
	 * so that hook is left empty and the death message is written once, here.
	 */
	@Override
	protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
		super.onPlayerDeath(evt, killed);
		boolean explotat = false;
		Player player = killed;
		Location location = player.getLocation();
		Player killer = player.getKiller();
		if (killer == null) {
			Long milliseconds = Calendar.getInstance().getTimeInMillis();
			Long millisecondsAntics = Long.parseLong(pTemp().ObtenirPropietat("Explo"));
			if (milliseconds - millisecondsAntics <= 1000 * 2) {
				String prop = pTemp().ObtenirPropietat("ExploPlayer");
				Player kill = plugin.getServer().getPlayer(prop);
				if (kill != null) {
					killer = kill;
					explotat = true;
				}
			}
		}
		if (killer == null) {
			Player lastDamager = getPlayerInfo(player).getLastDamager();
			Integer segonsCop = segonsÚltimCopRebut.get(player.getUniqueId());
			if (lastDamager != null && segonsCop != null && segonsTranscorreguts() - segonsCop <= SEGONS_CREDIT_ÚLTIM_COP) {
				killer = lastDamager;
			}
		}
		if (killer == null || killer == player) {
			evt.setDeathMessage(matatPelGuardià(player)
					? ChatColor.AQUA + TÍTOL_GUARDIÀ + ChatColor.WHITE + " ha matat a " + player.getName()
					: player.getName() + " s'ha mort tot sol");
			pTemp().EstablirPropietat(player.getName() + "Morts", "0");
			pPlayer(player).IncrementarPropietat("Morts");
			updateScoreBoards();
			return;
		}
		int mortsKiller = pTemp().ObtenirPropietatInt(killer.getName() + "Morts");
		int mortsMort = pTemp().ObtenirPropietatInt(player.getName() + "Morts");
		int Or = 5 + (mortsMort * 1);
		if (mortsMort > 4) {
			Or = Or + 2;
		}
		if (mortsMort > 6) {
			Or = Or + 5;
		}
		if (mortsMort > 10) {
			Or = Or + 5;
		}
		if (explotat) {
			Or = Or + 1;
		}
		if (Or >= 25) {
			Or = 25;
		}
		boolean picDOr = killer.getInventory().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE;
		if (picDOr) {
			Or = Or * 3;
			if (Or >= 30) {
				Or = 30;
			}
		}
		if (player.getInventory().contains(Material.DIAMOND_PICKAXE)) {
			Or = Or + 1;
		}
		if (player.getInventory().contains(Material.GOLD_BLOCK)) {
			Or = Or + 1;
		}
		if (areAllies(player, killer)) {
			Or = 0;
		} else {
			if (!explotat) killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 3));
		}
		Inventory inventory = killer.getInventory();
		inventory.addItem(new ItemStack(Material.GOLD_NUGGET, Or));
		pPlayer(killer).IncrementarPropietat("Or", Or);
		ajuntarOr(killer);

		evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
		if (explotat) {
			evt.setDeathMessage(killer.getName() + " ha fet explotar a " + player.getName() + "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
			pTemp().IncrementarPropietat(killer.getName() + "MortsExplotats");
		}
		if (picDOr) {
			evt.setDeathMessage(killer.getName() + " ha matat amb el pic d'or a " + player.getName() + "(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")(" + ChatColor.GOLD + "x3" + ChatColor.WHITE + ")");
		}
		if (!explotat && !picDOr && killedBySnowman(player)) {
			evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + " amb un ninot de neu (" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
		}
		if (Ability.hasAbility(plugin, this, player, AbilityType.CREEPER)) {
			float explopower = 0.8F + (mortsMort / 2);
			world.createExplosion(location.getX(), location.getY(), location.getZ(), explopower, false, false);
		}
		pTemp().EstablirPropietat(killer.getName() + "Morts", Integer.toString(mortsKiller + 1));
		pTemp().EstablirPropietat(player.getName() + "Morts", "0");
		if (Or != 0) {
			pPlayer(killer).IncrementarPropietat("Assassinats");
		}
		pPlayer(player).IncrementarPropietat("Morts");
		if (player.getInventory().contains(Material.DIAMOND_PICKAXE)) {
			player.getInventory().remove(Material.DIAMOND_PICKAXE);
			evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + " que tenia pic de diamant!(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
			killer.sendMessage(ChatColor.GOLD + "+3 Or passiu! (pic d'or)");
			killer.sendMessage(ChatColor.GOLD + "Matar un enemic amb el pic d'or et dona x3 or");
			inventory.addItem(new ItemStack(Material.GOLDEN_PICKAXE, 1));
		}
		if (player.getInventory().contains(Material.GOLDEN_PICKAXE)) {
			player.getInventory().remove(Material.GOLDEN_PICKAXE);
		}
		updateScoreBoards();
	}

	@Override
	protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
		// Credited and announced by onPlayerDeath, which also sees the deaths without a killer entity.
	}

	//---------- Consumables ----------

	@Override
	protected void onPlayerInteract(PlayerInteractEvent evt, Player plyr) {
		super.onPlayerInteract(evt, plyr);
		if (evt.getAction() == Action.PHYSICAL && evt.getClickedBlock() != null && controlPointOf(evt.getClickedBlock()) != null) {
			// The plate never depresses, so the 2013 wiring behind it never runs; the press is shown and heard anyway.
			evt.setCancelled(true);
			if (JocEnMarxa() && !evt.getClickedBlock().equals(shownPressedPlate.get(plyr.getUniqueId()))) showPlatePressed(plyr, evt.getClickedBlock());
			return;
		}
		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK && evt.getClickedBlock() != null) {
			Block clicat = evt.getClickedBlock();
			if (clicat.getType() == Material.DISPENSER || clicat.getType() == Material.DROPPER) {
				// The 2013 currency dispensers: never fired, never opened.
				evt.setCancelled(true);
				return;
			}
			if (Tag.BUTTONS.isTagged(clicat.getType()) || clicat.getType() == Material.LEVER) {
				// The map's 2013 redstone stays dead: the plugin does what the signs promise.
				evt.setCancelled(true);
				Integer equipDelBotó = botonsPont.get(clicat);
				if (equipDelBotó != null && evt.getHand() == EquipmentSlot.HAND) prémerBotóPont(plyr, obtenirEquip(equipDelBotó));
				return;
			}
		}
		ItemStack stack = evt.getItem();
		Inventory inv = plyr.getInventory();
		if (stack == null) return;
		if (stack.getType() == Material.WOODEN_SWORD && !JocIniciat) {
			Ability.openSelectionInventory(plugin, this, plyr);
		}
		if (stack.getType() == Material.DIAMOND_BLOCK) {
			Inventory inv1 = Bukkit.getServer().createInventory(plyr, 9, "Teletransportar...");
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				if (p.getName().equals(plyr.getName())) {
					continue;
				}
				if (p.isDead() || !p.isOnline()) {
					continue;
				}
				ItemStack steveItem = new ItemStack(Material.PLAYER_HEAD);
				inv1.addItem(Utils.setItemName(steveItem, p.getName()));
			}
			plyr.openInventory(inv1);
		}
		if (!JocEnMarxa() || obtenirEquip(plyr) == null) return;
		if (stack.getType() == Material.NETHER_STAR) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.setHealth(1);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 20, 4, false), true);
			}
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 3, false), true);
			}
			sendGlobalMessage(ChatColor.GREEN + plyr.getName() + ChatColor.WHITE + " ha utilitzat una" + ChatColor.BOLD + " nether star" + ChatColor.RESET + "!");
			// The star turns into the charge that makes arrows explosive.
			stack.setType(Material.FIREWORK_STAR);
		}
		if (stack.getType() == Material.ARROW) {
			if (stack.getEnchantments().size() >= 1) {
				for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
					p.setHealth(Math.max(0, p.getHealth() - 3));
				}
				plyr.sendMessage("-1 cor a tot l'equip enemic.");
				inv.removeItem(new ItemStack(stack.getType()));
			}
		}
		if (stack.getType() == Material.MAGMA_CREAM) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.setFireTicks(3 * 20);
			}
			plyr.sendMessage("Has cremat a l'equip enemic.");
			inv.removeItem(new ItemStack(stack.getType()));
		}
		if (stack.getType() == Material.STRING) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 15 * 20, 2, false), true);
			}
			plyr.sendMessage("Has alentit a l'equip enemic un 40% durant 15 segons.");
			inv.removeItem(new ItemStack(stack.getType()));
		}
		if (stack.getType() == Material.SPIDER_EYE) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 8 * 20, 1, false), true);
			}
			plyr.sendMessage("Has enverinat a l'equip enemic durant 8 segons.");
			inv.removeItem(new ItemStack(stack.getType()));
		}
		if (stack.getType() == Material.PAPER) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 25 * 20, 5, false), true);
			}
			plyr.sendMessage("Has esverat a l'equip enemic durant 25 segons.");
			inv.removeItem(new ItemStack(stack.getType()));
		}
		if (stack.getType() == Material.COCOA_BEANS) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6 * 20, 100, false), true);
			}
			plyr.sendMessage("Tots a l'aigua!");
			inv.removeItem(new ItemStack(stack.getType()));
		}
		if (stack.getType() == Material.EMERALD) {
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				double newHealth = p.getHealth() + 3;
				if (newHealth >= 20) {
					newHealth = 20;
				}
				p.setHealth(newHealth);
			}
			plyr.sendMessage("Has curat 1 cor al teu equip.");
			inv.removeItem(new ItemStack(stack.getType()));
		}
		if (stack.getType() == Material.SUGAR) {
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2, false), true);
			}
			plyr.sendMessage("Has augmentat la velocitat del teu equip durant 15 segons.");
			inv.removeItem(new ItemStack(stack.getType()));
		}
		if (stack.getType() == Material.GLASS) {
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 1, false), true);
			}
			plyr.sendMessage("El teu equip és invisible durant 15 segons.");
			inv.removeItem(new ItemStack(stack.getType()));
		}
		if (stack.getType() == Material.BLAZE_POWDER) {
			Player pObj = null;
			for (Entity e : plyr.getNearbyEntities(25, 40, 25)) {
				if (e instanceof Player p && areEnemies(p, plyr)) {
					if (pObj == null) {
						pObj = p;
					} else if (plyr.getLocation().distance(p.getLocation()) < plyr.getLocation().distance(pObj.getLocation()) && p.getFireTicks() == 0) {
						pObj = p;
					}
				}
			}
			if (pObj != null) {
				int kills = pTemp().ObtenirPropietatInt(plyr.getName() + "Morts");
				pObj.setFireTicks((5 + kills + (plyr.getLevel() / 2)) * 20);
				inv.removeItem(new ItemStack(stack.getType()));
				pTemp().EstablirPropietat("LastIgnitePlayerVictim", pObj.getName());
				pTemp().EstablirPropietat("LastIgnitePlayerKiller", plyr.getName());
			} else {
				plyr.sendMessage(ChatColor.GRAY + "No hi ha cap enemic a prop!");
			}
		}
	}

	//---------- Snowmen (onHit snowball, 2013) ----------

	/**
	 * A thrown snowball becomes a snow golem owned by the thrower on the nearest spot with
	 * a floor; a fourth one melts the thrower's oldest. Quartz in the thrower's inventory
	 * at this moment makes the new snowman fire faster for the rest of its life.
	 */
	private void throwSnowman(Player thrower, ProjectileHitEvent evt, Location impact) {
		Equip team = obtenirEquip(thrower);
		if (!JocEnMarxa() || team == null) return;
		Location spot = snowmanSpotNear(evt, impact);
		if (spot == null) {
			thrower.sendMessage(ChatColor.GRAY + "La bola de neu s'ha fos.");
			return;
		}
		List<Minion> mine = new ArrayList<>();
		for (Minion minion : minionsOf(thrower)) if (minion instanceof SnowmanMinion) mine.add(minion);
		mine.sort((a, b) -> Integer.compare(a.bornAtSecond(), b.bornAtSecond()));
		while (mine.size() >= MAX_SNOWMEN_PER_PLAYER) {
			Minion oldest = mine.remove(0);
			Location where = oldest.mob() != null ? oldest.mob().getLocation().add(0, 1, 0) : null;
			discharge(oldest);
			if (where != null) {
				world.spawnParticle(Particle.ITEM_SNOWBALL, where, 30, 0.4, 0.6, 0.4, 0.05);
				world.playSound(where, Sound.BLOCK_SNOW_BREAK, 1F, 0.8F);
			}
		}
		int cooldown = thrower.getInventory().contains(Material.QUARTZ) ? SNOWMAN_QUARTZ_COOLDOWN_TICKS : SnowmanMinion.DEFAULT_COOLDOWN_TICKS;
		enlist(new SnowmanMinion(this, team, thrower, cooldown, this::snowballHit), spot);
		world.playSound(spot, Sound.ENTITY_SNOW_GOLEM_AMBIENT, 1F, 1F);
		world.playSound(spot, Sound.BLOCK_SNOW_PLACE, 1F, 1F);
		PaperMessages.sendActionBar(thrower, ChatColor.WHITE + "Ninot de neu " + (mine.size() + 1) + "/" + MAX_SNOWMEN_PER_PLAYER, 60);
	}

	/** The block the snowball stopped against, the impact block and its neighbours above and below, first one a golem can stand in. */
	private static Location snowmanSpotNear(ProjectileHitEvent evt, Location impact) {
		List<Block> candidates = new ArrayList<>();
		if (evt.getHitBlock() != null && evt.getHitBlockFace() != null) candidates.add(evt.getHitBlock().getRelative(evt.getHitBlockFace()));
		Block impactBlock = impact.getBlock();
		candidates.add(impactBlock);
		candidates.add(impactBlock.getRelative(BlockFace.UP));
		candidates.add(impactBlock.getRelative(BlockFace.DOWN));
		candidates.add(impactBlock.getRelative(0, 2, 0));
		for (Block candidate : candidates) {
			if (canStandIn(candidate)) return candidate.getLocation().add(0.5, 0, 0.5);
		}
		return null;
	}

	private static boolean canStandIn(Block feet) {
		Block head = feet.getRelative(BlockFace.UP);
		return feet.isPassable() && !feet.isLiquid() && head.isPassable() && !head.isLiquid() && !feet.getRelative(BlockFace.DOWN).isPassable();
	}

	/**
	 * The 2013 snowball: 4 damage until minute five, then 1, plus half the owner's kill
	 * streak, with a chance of Slowness or Weakness that grows with the streak. The hit
	 * counts as the owner's for kill credit (JocEquips records the last damager; the
	 * second is recorded here).
	 */
	private void snowballHit(SnowmanMinion snowman, EntityDamageByEntityEvent evt, Player victim) {
		int streak = snowman.ownerName() == null ? 0 : pTemp().ObtenirPropietatInt(snowman.ownerName() + "Morts");
		int bonus = streak / 2;
		int damage = (segonsTranscorreguts() / 60 >= SNOWBALL_LATE_FROM_MINUTE ? SNOWBALL_DAMAGE_LATE : SNOWBALL_DAMAGE_EARLY) + bonus;
		evt.setDamage(damage);
		int level = 0;
		if (bonus >= 4 && Utils.Possibilitat(50)) level = 1;
		if (bonus >= 7 && Utils.Possibilitat(70)) level = 2;
		if (Utils.Possibilitat(20)) victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, (2 + bonus) * 20, level, true), true);
		if (Utils.Possibilitat(10)) victim.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, (Utils.NombreEntre(1, 3) + bonus) * 20, level, true), true);
		segonsÚltimCopRebut.put(victim.getUniqueId(), segonsTranscorreguts());
	}

	private boolean killedBySnowman(Player victim) {
		if (!(victim.getLastDamageCause() instanceof EntityDamageByEntityEvent cause)) return false;
		if (!(cause.getDamager() instanceof Snowball ball) || !(ball.getShooter() instanceof Entity shooter)) return false;
		return minionOf(shooter) instanceof SnowmanMinion;
	}

	//---------- Explosive arrows ----------

	@Override
	protected void onProjectileHit(ProjectileHitEvent evt, Projectile proj) {
		super.onProjectileHit(evt, proj);
		if (!(proj.getShooter() instanceof Player player)) return;
		Entity entity = evt.getEntity();
		Location loc = entity.getLocation();
		if (evt.getEntityType() == EntityType.SNOWBALL) {
			throwSnowman(player, evt, loc);
			return;
		}
		if (evt.getEntityType() != EntityType.ARROW) return;
		boolean spawnProt = false;
		for (Equip e : Equips) {
			if (loc.distance(e.getTeamSpawnLocation()) <= 5) spawnProt = true;
		}
		if (player.getLocation().getBlockY() >= 49 && entity.getTicksLived() > 5 && !spawnProt) {
			Inventory inv = player.getInventory();
			if (inv.contains(Material.FIREWORK_STAR)) {
				pTemp().EstablirPropietat("Explo", Long.toString(Calendar.getInstance().getTimeInMillis()));
				pTemp().EstablirPropietat("ExploPlayer", player.getName());
				inv.removeItem(new ItemStack(Material.FIREWORK_STAR, 1));
				int mortsExplotats = pTemp().ObtenirPropietatInt(player.getName() + "MortsExplotats");
				int morts = pTemp().ObtenirPropietatInt(player.getName() + "Morts");

				float explo = 3.25F;
				explo = explo + (0.12F * mortsExplotats);
				explo = explo + (0.28F * morts);
				float mult = ((float) player.getHealth()) / ((float) player.getMaxHealth());
				explo = explo * mult;
				if (inv.contains(Material.NETHER_STAR)) {
					explo = explo + 1F;
					explo = explo + (explo * 1.012F);
				}
				if (inv.contains(Material.GOLDEN_SWORD)) {
					explo = explo + (explo * 0.20F);
				}
				if (Ability.hasAbility(plugin, this, player, AbilityType.PIROTÈCNIC)) {
					explo = explo + (explo * 0.35F);
				}
				world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), explo, false, false);
				if (!pTemp().ObtenirPropietat("ForçaExplo").equals(Float.toString(explo))) {
					player.sendMessage("Força de les fletxes explosives: " + Float.toString(explo));
					pTemp().EstablirPropietat("ForçaExplo", Float.toString(explo));
				}
				world.dropItem(loc, new ItemStack(Material.GOLD_NUGGET, 1)).setVelocity(new Vector(0, 0, 0));
				//Automal
				double hp = player.getHealth();
				if (hp == 20) {
					player.sendMessage(ChatColor.GRAY + "Disparar fletxes explosives et treu 1 cor per explosió fins a mig cor. La força de les fletxes explosives varia amb la teva vida (20 hp - 100%, 1 hp 5%");
				}
				hp = hp - 2;
				if (hp <= 0) {
					hp = 1;
				}
				player.setHealth(hp);
			}
			entity.remove();
		}
	}

	//---------- Scoreboard ----------

	@Override
	protected void updateScoreBoard(Player ply) {
		super.updateScoreBoard(ply);
		if (JocIniciat) {
			ArrayList<String> list = new ArrayList<>();
			list.add(ChatColor.GREEN + "Kills: " + pPlayer(ply).ObtenirPropietatInt("Assassinats"));
			list.add(ChatColor.RED + "Morts: " + pPlayer(ply).ObtenirPropietatInt("Morts"));
			list.add(ChatColor.GOLD + "Or: " + pPlayer(ply).ObtenirPropietatInt("Or"));
			if (pMapaActual().ExisteixPropietat("Golem")) list.add(ChatColor.AQUA + "Guardià: " + estatGuardià());
			Equip equip = obtenirEquip(ply);
			if (equip != null) list.add(etiquetaPont(equip, "Pont: ") + barraPont(equip));
			if (Ability.hasAbility(plugin, this, ply, AbilityType.ESPADATXI)) {
				list.add(ChatColor.BLUE + "Espadatxí: " + pPlayer(ply).ObtenirPropietatInt("StrongHitCount"));
			}
			if (Ability.hasAbility(plugin, this, ply, AbilityType.ARQUER_DE_GEL)) {
				list.add(ChatColor.BLUE + "Arquer de gel: " + pPlayer(ply).ObtenirPropietatInt("StrongBowHitCount"));
			}
			ScoreBoardUpdater.setScoreBoard(ply, "Estadístiques", list, null);
		}
	}

	//---------- Abilities (2015 lobby port; the selection menu is not wired yet) ----------

	public static class Ability {

		public Ability() {
		}
		public enum AbilityType {
			RESISTENCIA,
			COMANDANT,
			ESPADATXI,
			REGENERACIO_AUGMENTADA,
			ASSALT,
			CREEPER,
			ARQUER_PERFECTE,
			ARQUER_DE_GEL,
			PROTECCIÓ_IMPACTE,
			PIROTÈCNIC,
			ESQUELET_FORT,
			CONTROL_GRAVETAT,
			DESTRUCTOR,
			RANDOM
		}
		static ItemStack Icona(lobby plugin, ObsidianDefenders j, Player plyr, AbilityType habilitat) {
			Material mat = Material.OAK_PLANKS;
			String Titol = "<Nom>";
			String Desc = "<Descripció>";
			String Desc2 = "<Descripció2>";
			boolean disp = false;
			//-----------
			switch (habilitat) {
			case ESPADATXI:
				mat = Material.IRON_SWORD;
				Titol = "Espadatxí";
				Desc = "Augmenta l'atac cada 6 cops d'espasa";
				Desc2 = "i els enemics volen pels aires";
				disp = true;
				break;
			case REGENERACIO_AUGMENTADA:
				mat = Material.OAK_SAPLING;
				Titol = "Regeneració augmentada";
				Desc = "x3 Regeneració passiva x2 cost de menjar";
				disp = true;
				break;
			case RESISTENCIA:
				mat = Material.DIAMOND;
				Titol = "Resistència";
				Desc = "Redueix el mal d'enemics un";
				Desc2 = "10% + 8% per enemic proper (8 blocs)";
				disp = true;
				break;
			case ARQUER_PERFECTE:
				mat = Material.BOW;
				Titol = "Arquer perfecte";
				Desc = "Cada 10 fletxes encertades,";
				Desc2 = "la fletxa rebota (8 blocs)";
				disp = true;
				break;
			case ASSALT:
				mat = Material.LEATHER_BOOTS;
				Titol = "Assalt";
				Desc = "El mal per caiguda es transfereix";
				Desc2 = "als enemics propers (7 blocs)";
				disp = true;
				break;
			case ESQUELET_FORT:
				mat = Material.BONE;
				Titol = "Esquelet fort";
				Desc = "- 3 mal per caiguda";
				disp = true;
				break;
			case PIROTÈCNIC:
				mat = Material.FIREWORK_ROCKET;
				Titol = "Pirotècnic";
				Desc = "+ 35 % força explosions";
				disp = true;
				break;
			case PROTECCIÓ_IMPACTE:
				mat = Material.IRON_CHESTPLATE;
				Titol = "Protecció d'impacte";
				Desc = "-50% mal rebut del golem de ferro";
				disp = true;
				break;
			case CONTROL_GRAVETAT:
				mat = Material.GOLDEN_BOOTS;
				Titol = "Control de la gravetat";
				Desc = "Duplica el mal per caiguda dels";
				Desc2 = "enemics atacats recentment (10s)";
				disp = true;
				break;
			case RANDOM:
				mat = Material.BEDROCK;
				Titol = "Inmortalitat";
				Desc = "Ets inmortal i guanyes";
				Desc2 = "la partida en 5s ;) jaja";
				disp = false;
				break;
			case ARQUER_DE_GEL:
				mat = Material.ICE;
				Titol = "Arquer de gel";
				Desc = "Congela l'enemic que encertis";
				Desc2 = "cada 7 fletxes";
				disp = true;
				break;

			case DESTRUCTOR:
				mat = Material.DIAMOND_AXE;
				Titol = "Destructor";
				Desc = "Dismnueix la durabilitat de les";
				Desc2 = "armadures de l'enemic (5 + morts)";
				disp = true;
				break;
			case COMANDANT:
				mat = Material.COMMAND_BLOCK;
				Titol = "Comandant";
				Desc = "Augmenta el mal dels aliats propers un";
				Desc2 = "12% i comença amb items addicionals";
				disp = false;
				break;
			case CREEPER:
				mat = Material.TNT;
				Titol = "Creeper";
				Desc = "Explotes al morir(0.8F). La força augmenta";
				Desc2 = "0.5F per cada enemic que hagis matat.";
				disp = true;
				break;
			default:
				break;

			}
			//-----------
			ItemStack item = new ItemStack(mat);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + Titol);
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.WHITE + Desc);
			if (!Desc2.equals("<Descripció2>")) {
				lore.add(ChatColor.WHITE + Desc2);
			}
			if (hasAbility(plugin, j, plyr, habilitat)) {
				lore.add(ChatColor.YELLOW + "Seleccionat!");
				item.setAmount(2);
			}
			if (!disp) {
				lore.add(ChatColor.DARK_RED + "No funciona");
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;

		}
		public static void openSelectionInventory(lobby plugin, ObsidianDefenders j, Player plyr) {
			Inventory inv = Bukkit.getServer().createInventory(plyr, 9 * 2, "Selecciona habilitat");
			int i = 0;
			for (AbilityType mill : AbilityType.values()) {
				inv.setItem(i, Icona(plugin, j, plyr, mill));
				i++;
			}
			plyr.openInventory(inv);
		}
		public static boolean hasAbility(lobby plugin, ObsidianDefenders j, Player plyr, AbilityType ab) {
			return getPlayerAbilityTypes(plugin, j, plyr).contains(ab);
		}
		static ArrayList<AbilityType> getPlayerAbilityTypes(lobby plugin, ObsidianDefenders j, Player plyr) {
			ArrayList<AbilityType> lore = new ArrayList<>();
			try {
				lore.add(AbilityType.valueOf(j.pPlayer(plyr).ObtenirPropietat("Habilitat1")));
				lore.add(AbilityType.valueOf(j.pPlayer(plyr).ObtenirPropietat("Habilitat2")));
			} catch (Exception e) {
				randomAbilities(plugin, j, plyr);
				return getPlayerAbilityTypes(plugin, j, plyr);
			}
			return lore;
		}
		public static void randomAbilities(lobby plugin, ObsidianDefenders j, Player plyr) {
			int i = 1;
			while (i <= 2) {
				boolean fet = false;
				while (fet == false) {
					for (AbilityType ab : AbilityType.values()) {
						if (Utils.Possibilitat(10)) {
							setAbility(plugin, j, plyr, ab, i);
							fet = true;
						}
					}
				}
				i++;
			}

		}
		public static void setAbility(lobby plugin, ObsidianDefenders j, Player plyr, AbilityType ab, int id) {
			j.pPlayer(plyr).EstablirPropietat("Habilitat" + Integer.toString(id), ab.name());
		}
		public static void giveSelectors(lobby plugin, Player plyr) {
			int i = 1;
			while (i <= 2) {
				ItemStack item = new ItemStack(Material.WOODEN_SWORD);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("Habilitat " + Integer.toString(i));
				ArrayList<String> lore = new ArrayList<>();
				lore.add(Integer.toString(i));
				meta.setLore(lore);
				item.setItemMeta(meta);
				plyr.getInventory().setItem(4 + i, item);
				i++;
			}
		}
	}
}
