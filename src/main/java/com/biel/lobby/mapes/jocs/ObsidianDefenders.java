package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
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
import org.bukkit.FireworkEffect;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import java.util.function.Predicate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.Tag;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.ObsidianDefenders.Ability.AbilityType;
import com.biel.lobby.guide.GameGuide;
import com.biel.lobby.minions.Lane;
import com.biel.lobby.minions.LaneMinion;
import com.biel.lobby.minions.LaneMinionKind;
import com.biel.lobby.minions.Minion;
import com.biel.lobby.minions.SnowmanKind;
import com.biel.lobby.minions.SnowmanMinion;
import com.biel.lobby.utilities.PaperMessages;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;

import io.papermc.paper.registry.RegistryAccess;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.block.data.Rotatable;
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
	/** The first chest cycle waits this long, so no prize is announced before anyone has left the spawn (Biel, 2026-09-08). */
	private static final long FIRST_CHEST_CYCLE_TICKS = 15 * 20;
	private static final double RECALL_SECONDS = 3;
	/** The objective stays on a boss bar at the top of the screen this long after the start, draining, then goes (Biel, 2026-09-08: what a first-timer must know). */
	private static final int OBJECTIVE_BAR_SECONDS = 60;
	private static final int MAX_COFRES_OBERTS = 8;
	private static final long PRIMER_PIC_TICKS = 3 * 60 * 20;
	private static final long PERIODE_PIC_TICKS = 2 * 60 * 20;
	/** The Guardian wakes a minute in (Biel, 2026-09-07 night: "give the players a chance to get situated, buy things"); it was 5 s. */
	private static final long GOLEM_INICIAL_TICKS = 60 * 20;
	/** Every player starts with this much gold, so the first purchase happens in the first minute. */
	private static final int INITIAL_GOLD = 12;
	/**
	 * The nether star (Biel, 2026-09-07 night: epic for newbies, counterable for tryhards):
	 * its arrival in a chest is announced with a beam so both teams run for it; a right
	 * click charges it for two seconds, glowing and audible to the whole map, and then it
	 * strikes the enemies within its radius. It used to reach the whole map at once.
	 */
	private static final double NETHER_STAR_RADIUS = 30;
	private static final int NETHER_STAR_CHEST_CHANCE = 6;
	private static final long NETHER_STAR_CHARGE_TICKS = 40;
	/** A beam over a chest that holds a star or a hero snowball: this tall, redrawn this often, until the item is taken. */
	private static final int LOOT_BEAM_HEIGHT = 14;
	private static final long LOOT_BEAM_PERIOD_TICKS = 5;
	/** Catch-up: the team behind by this many kills gets this much more passive gold per player per cycle. */
	private static final int CATCH_UP_KILL_GAP = 3;
	private static final int CATCH_UP_GOLD = 1;
	/** The gold block's passive income per cycle; at its price it pays for itself in about three and a half minutes. */
	private static final int GOLD_BLOCK_PASSIVE_GOLD = 3;
	/**
	 * Closure (Biel, 2026-09-07 night: "after a certain minute, kills to the enemy team start
	 * spawning wither skeletons, as minions, one per kill"): from this second on, every
	 * kill raises a wither skeleton beside the victim, owned by the killer, marching the
	 * killer's lane to the enemy base; its kills are the owner's, so each raises another.
	 */
	private static final int SUDDEN_DEATH_SECOND = 15 * 60;
	private static final int SUDDEN_DEATH_WARNING_SECONDS = 60;
	/** The dead wait as spectators (JoniMega, 2026-09-07: "apareixes més ràpid del que puc picar"): this long at the start, growing with the match, capped. */
	private static final int RESPAWN_WAIT_BASE_SECONDS = 3;
	private static final int RESPAWN_WAIT_MINUTES_PER_EXTRA_SECOND = 2;
	private static final int RESPAWN_WAIT_MAX_SECONDS = 12;
	/** The one-shot hint about the breach shows within this distance of an enemy core. */
	private static final double VAULT_HINT_DISTANCE = 7;
	/** The map scans are checked again this long after the start, since a fast recreate once returned base0 without its bridge sign or lamps. */
	private static final long REGISTRATION_CHECK_TICKS = 60;
	/** Each death to a wither skeleton costs this much max health on respawn, down to the floor. */
	private static final double WITHER_DEATH_MAX_HEALTH_LOSS = 2;
	private static final double MIN_MAX_HEALTH = 4;
	private static final double FULL_MAX_HEALTH = 20;
	private static final int WITHER_SKELETON_HEALTH = 40;
	private static final double WITHER_SKELETON_DAMAGE = 8;
	private static final int WITHER_SKELETON_WITHER_TICKS = 10 * 20;
	private static final LaneMinionKind WITHER_SKELETON = new LaneMinionKind("Esquelet wither", WITHER_SKELETON_HEALTH, WITHER_SKELETON_DAMAGE, 2.5, 20, 10, 1.0, at -> {
		WitherSkeleton skeleton = at.getWorld().spawn(at, WitherSkeleton.class);
		skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
		skeleton.getEquipment().setItemInMainHandDropChance(0);
		return skeleton;
	});
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
	/** Players this close to the Guardian see its boss bar: the hut and the canal in front of it, not the deck above. */
	private static final double DISTÀNCIA_BARRA_GUARDIÀ = 12;
	/** The scoreboard countdown to the Guardian's return moves in steps of this many seconds, so the sidebar does not tick. */
	private static final int GUARDIAN_COUNTDOWN_STEP_SECONDS = 15;
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
	/**
	 * The detonator (Biel, 2026-09-07 night: "a pressure plate inside; once someone steps
	 * on it it triggers the explosion"): a gold plate an enemy foot on which blows the base
	 * up. The plugin lays it in every breach of the vault's ring, and at match start where a
	 * {@code Detonador<team>} map property names the block under it. Detected like the
	 * control points: the cancelled physical press and a tick over the players' feet.
	 */
	private static final Material DETONATOR_PLATE = Material.LIGHT_WEIGHTED_PRESSURE_PLATE;
	/** A ring block is obsidian at the TNT's level within this many blocks of a TNT; the back wall is three out. */
	private static final int RING_REACH = 3;
	private static final long DETONATOR_TICK_PERIOD = 5;
	/** The vault's TNT goes off block by block within this many ticks, then the base is ruined blast by blast. */
	private static final int CORE_FUSE_SPREAD_TICKS = 50;
	private static final int RUIN_BLASTS = 16;
	private static final long RUIN_TICKS_BETWEEN_BLASTS = 8;
	private static final int RUIN_RADIUS = 26;
	private static final float RUIN_BLAST_POWER = 5F;
	/** The spectators watch the base go up from this high over its spawn. */
	private static final int RUIN_VIEW_HEIGHT = 14;
	/** A death with no killer is credited to the last player who hit the victim within this window. */
	private static final int SEGONS_CREDIT_ÚLTIM_COP = 95;
	/** Snowmen (2013): a thrown snowball becomes a snow golem owned by the thrower; at most this many alive per player. */
	private static final int MAX_SNOWMEN_PER_PLAYER = 3;
	/** Quartz in the thrower's inventory makes the new snowman fire a third faster, as in 2013. */
	private static final double SNOWMAN_QUARTZ_COOLDOWN_FACTOR = 2.0 / 3;
	/**
	 * Snowballs (Biel, 2026-09-07 night, after playing: "should not slow, a bit more damage,
	 * more knockback, then after a few hits the ice cage"): this much damage and a shove of
	 * this strength per hit, a magma one burns this long on top; every snowman arms its
	 * cage after this many hits on players, and the next hit shuts its victim in ice for
	 * this long.
	 */
	private static final double SNOWBALL_DAMAGE = 2;
	private static final double SNOWBALL_KNOCKBACK = 0.8;
	private static final double SNOWBALL_KNOCKBACK_LIFT = 0.3;
	private static final int SNOWBALL_FIRE_TICKS = 60;
	private static final int SNOWMAN_HITS_TO_ARM_CAGE = 3;
	private static final int ICE_CAGE_TICKS = 60;
	/** After a cage melts, hits on that victim charge no cage and spend none for this long, so three snowmen cannot chain cages on one player. */
	private static final int ICE_CAGE_GRACE_SECONDS = 6;
	/** Chance, per chest opened, of an enchanted snowball, the hero snowman (Biel: "incentives to go to the trees"). */
	private static final int HERO_SNOWBALL_CHEST_CHANCE = 5;
	/** The team whose player last felled the Guardian throws magma snowmen until the other team fells it. */
	private Equip guardianSlayerTeam;
	/** Victim → game second until which snowballs neither charge nor spend a cage on them. */
	private final Map<UUID, Integer> iceCageGraceUntil = new HashMap<>();
	private boolean suddenDeath;
	/** Team id → kills by that team this match; the shop sign shows them and the catch-up rule reads them. */
	private final Map<Integer, Integer> killsByTeam = new HashMap<>();
	/** Team id → the sign by that team's shop that shows both teams' kills. */
	private final Map<Integer, Block> killsSigns = new HashMap<>();
	/** Player → the task that will discharge the star they are charging. */
	private final Map<UUID, Integer> starChargeTasks = new HashMap<>();
	/** Player → deaths to a wither skeleton this match. */
	private final Map<UUID, Integer> witherDeaths = new HashMap<>();
	/** Players who have bought their one quartz this match. */
	private final Set<UUID> quartzBuyers = new HashSet<>();

	/** The game's words, written once in guides/obsidian-defenders.md: start lines, tooltips, hints and the book; the numbers come from here. */
	private static final GameGuide GUIDE = GameGuide.of("Obsidian Defenders").withValues(guideValues());

	private static Map<String, String> guideValues() {
		Map<String, String> v = new HashMap<>();
		v.put("PIC_PRIMER_MIN", String.valueOf(PRIMER_PIC_TICKS / 20 / 60));
		v.put("PIC_PERIODE_MIN", String.valueOf(PERIODE_PIC_TICKS / 20 / 60));
		v.put("MORT_BASE_S", String.valueOf(RESPAWN_WAIT_BASE_SECONDS));
		v.put("MORT_CADA_MIN", String.valueOf(RESPAWN_WAIT_MINUTES_PER_EXTRA_SECOND));
		v.put("MORT_MAX_S", String.valueOf(RESPAWN_WAIT_MAX_SECONDS));
		v.put("RECALL_S", String.valueOf((int) RECALL_SECONDS));
		v.put("CAPTURA_S", String.valueOf(CAPTURE_SECONDS));
		v.put("CAPTURA_OR", String.valueOf(GOLD_PER_CAPTURE));
		v.put("PUNT_QUADRE_S", String.valueOf(SECONDS_PER_POINT_SQUARE));
		v.put("PONT_QUADRES", String.valueOf(PONT_CÀRREGA_MÀXIMA));
		v.put("PONT_S", String.valueOf(PONT_TICKS_DESPLEGAT / 20));
		v.put("GUARDIA_MIN", String.valueOf(GOLEM_INICIAL_TICKS / 20 / 60));
		v.put("GUARDIA_OR", String.valueOf(OR_PER_GOLEM));
		v.put("OR_INICIAL", String.valueOf(INITIAL_GOLD));
		v.put("COFRES_S", String.valueOf(CICLE_COFRES_TICKS / 20));
		v.put("BLOC_OR_PASSIU", String.valueOf(GOLD_BLOCK_PASSIVE_GOLD));
		v.put("REMUNTADA_KILLS", String.valueOf(CATCH_UP_KILL_GAP));
		v.put("REMUNTADA_OR", String.valueOf(CATCH_UP_GOLD));
		v.put("MAX_NINOTS", String.valueOf(MAX_SNOWMEN_PER_PLAYER));
		v.put("GEL_S", String.valueOf(ICE_CAGE_TICKS / 20));
		v.put("GEL_COPS_ARMAR", String.valueOf(SNOWMAN_HITS_TO_ARM_CAGE));
		v.put("FOC_S", String.valueOf(SNOWBALL_FIRE_TICKS / 20));
		v.put("SUPERNINOT_PERCENT", String.valueOf(HERO_SNOWBALL_CHEST_CHANCE));
		v.put("ESTRELLA_RADI", String.valueOf((int) NETHER_STAR_RADIUS));
		v.put("ESTRELLA_CARREGA_S", String.valueOf(NETHER_STAR_CHARGE_TICKS / 20));
		v.put("ESTRELLA_PERCENT", String.valueOf(NETHER_STAR_CHEST_CHANCE));
		v.put("MORT_SOBTADA_MIN", String.valueOf(SUDDEN_DEATH_SECOND / 60));
		for (Mercaderia m : Mercaderia.values()) v.put("PREU_" + m.name(), String.valueOf(m.preu));
		for (Encantament e : Encantament.values()) v.put("ENCANT_" + e.name(), String.valueOf(e.preu(1)));
		return v;
	}

	boolean debug = false;
	/** Team id → block positions of the TNT that is that team's base core. */
	private final Map<Integer, Set<Vector>> nuclisPerEquip = new HashMap<>();
	/** Detonator plate → the team whose vault it blows: the map's own, or one laid in a breach. */
	private final Map<Block, Integer> teamByDetonator = new HashMap<>();
	/** The team whose base has gone up; set once, so nothing blows twice. */
	private Equip explodedBase;
	private final Map<UUID, Integer> segonsÚltimCopRebut = new HashMap<>();
	private UUID golemActual;
	private BossBar barraGuardià;
	private BossBar objectiveBar;
	private int objectiveBarTask = -1;
	private int tascaAuraGuardià = -1;
	private int passosAura = 0;
	private int segonsPresènciaGuardià = 0;
	private boolean guardiàEnfurismat = false;
	private boolean guardiàAnunciat = false;
	/** Game second at which the Guardian (re)appears; read only while it is dead. */
	private int guardiàTornaAlSegon = 0;
	private String lastGuardianCountdownShown = "";
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
	/** Team id → the lamps on that team's half of every middle-room tower, bottom row first: the same bar, seen from the whole map. */
	private final Map<Integer, List<Block>> towerLampsByTeam = new HashMap<>();
	/** Team id → seconds of captured-point time not yet turned into a square. */
	private final Map<Integer, Integer> pointSecondsByTeam = new HashMap<>();
	/** Player → the plate shown pressed for them; the real block never changes. */
	private final Map<UUID, Block> shownPressedPlate = new HashMap<>();
	private final Set<UUID> refusedOnPlate = new HashSet<>();
	private int chestCycles = 0;

	/**
	 * A middle room: one plate per team, its lamps, and who holds it. The room's lamps are
	 * split by the side of the room they sit on: the wall lamp beside a team's plate lights
	 * when that team holds the point, the lamps on the centre line light while anyone does.
	 * The tower on top of the room is not the point's: each half is its team's bridge bar
	 * ({@link #towerLampsByTeam}).
	 */
	private static final class ControlPoint {
		final Map<Integer, Block> plateByTeam = new HashMap<>();
		/** The side lamps at room level: lit on the owner's side. */
		final List<Block> lamps = new ArrayList<>();
		/** The lamps on the centre line at room level, west to east: the capture progress, lit from the charging team's side. */
		final List<Block> wallLamps = new ArrayList<>();
		Integer owner = null;
		/** The team whose progress the wall lamps show; the owner once a capture completes or the challenge fades. */
		Integer chargingTeam = null;
		int progress = 0;

		int sideOf(Block block) {
			return Integer.signum(block.getX() - centre().getBlockX());
		}

		int sideOfTeam(int team) {
			Block plate = plateByTeam.get(team);
			return plate == null ? 0 : sideOf(plate);
		}

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
	private static final int SECONDS_PER_POINT_SQUARE = 20;
	/** A capture is felt on the bar at once. */
	private static final int SQUARES_PER_CAPTURE = 1;
	/** The slow default: a square every this many chest cycles, and one per enemy kill. Biel, 2026-09-07: "the bridge loads too fast, four times slower". */
	private static final int CYCLES_PER_DEFAULT_SQUARE = 2;
	private static final int SQUARES_PER_KILL = 1;
	/** Seconds on your plate to take a point; the wall lamps light one per second from your side. */
	private static final int CAPTURE_SECONDS = 3;
	private static final int GOLD_PER_CAPTURE = 5;
	/** Plates are looked for between the two spawns, this far either side of the line joining them, at the map's play heights. */
	private static final int PLATE_BAND_HALF_WIDTH = 60;
	private static final int SCAN_MIN_Y = 30;
	private static final int SCAN_MAX_Y = 70;
	/** Plates closer than this belong to the same control point. */
	private static final double CONTROL_POINT_RADIUS = 8;
	/** A control point's lamps: within this many blocks horizontally of the room's centre, from the floor up to the tower on top of it. */
	private static final int CONTROL_POINT_LAMP_REACH = 6;
	private static final int CONTROL_POINT_TOWER_HEIGHT = 20;
	/** Lamps higher than this above the plates are the tower, not the room. */
	private static final int CONTROL_POINT_ROOM_HEIGHT = 3;
	/** A base's bridge lamps are the ones around its bridge sign. */
	private static final int BRIDGE_LAMP_REACH = 6;
	/** Lamp banks light as a bar in this order: bottom row first, west to east. */
	private static final Comparator<Block> BOTTOM_ROW_FIRST = (l1, l2) -> l1.getY() != l2.getY() ? Integer.compare(l1.getY(), l2.getY()) : Integer.compare(l1.getX(), l2.getX());
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
		ESTRELLES(Material.FIREWORK_STAR, 2, 10, "2 estrelles de foc", null),
		ESPASA_FERRO(Material.IRON_SWORD, 1, 10, "Espasa de ferro", null),
		ARC(Material.BOW, 1, 12, "Arc", null),
		PIC_FERRO(Material.IRON_PICKAXE, 1, 12, "Pic de ferro", "+30 dany al golem"),
		PITRAL_FERRO(Material.IRON_CHESTPLATE, 1, 18, "Pitral de ferro", null),
		BLOC_OR(Material.GOLD_BLOCK, 1, 20, "Bloc d'or", "+" + GOLD_BLOCK_PASSIVE_GOLD + " or cada " + (CICLE_COFRES_TICKS / 20) + " s: es paga sol en 3,5 min"),
		BOLA_DE_NEU(Material.SNOWBALL, 1, 6, "Bola de neu", null),
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

	/**
	 * The items the game hands out, named and explained on their tooltip from one place:
	 * chest loot, booth purchases, the pickaxes and the star an arrow needs. The tooltip
	 * is the quietest channel there is: read only when hovered, never repeated.
	 */
	private enum Objecte {
		ESTRELLA_DEL_NETHER(Material.NETHER_STAR, "Estrella infernal"),
		ESTRELLA_DE_FOC(Material.FIREWORK_STAR, "Estrella de foc"),
		CREMA_DE_MAGMA(Material.MAGMA_CREAM, "Crema de magma"),
		MARAGDA(Material.EMERALD, "Maragda"),
		BOLA_DE_NEU(Material.SNOWBALL, "Bola de neu"),
		BOLA_DE_NEU_ENCANTADA(Material.SNOWBALL, true, "Bola de neu encantada"),
		PERLA_D_ENDER(Material.ENDER_PEARL, "Perla d'Ender"),
		ESPASA_D_OR(Material.GOLDEN_SWORD, "Espasa d'or"),
		PIC_DE_DIAMANT(Material.DIAMOND_PICKAXE, "Pic de diamant"),
		PIC_D_OR(Material.GOLDEN_PICKAXE, "Pic d'or");

		final Material material;
		/** Shown with the enchantment glint; the glint is what tells it from the plain item of the same material. */
		final boolean brillant;
		final String nom;
		Objecte(Material material, String nom) {
			this(material, false, nom);
		}

		Objecte(Material material, boolean brillant, String nom) {
			this.material = material;
			this.brillant = brillant;
			this.nom = nom;
		}

		/** The tooltip, from the guide's {@code objecte NAME} section. */
		List<String> llegenda() {
			return GUIDE.lines("objecte " + name());
		}

		static Objecte de(Material material) {
			return de(material, false);
		}

		static Objecte de(ItemStack item) {
			if (item == null) return null;
			boolean brillant = item.hasItemMeta() && item.getItemMeta().hasEnchantmentGlintOverride() && item.getItemMeta().getEnchantmentGlintOverride();
			return de(item.getType(), brillant);
		}

		static Objecte de(Material material, boolean brillant) {
			for (Objecte o : values()) if (o.material == material && o.brillant == brillant) return o;
			return null;
		}

		/** A fresh stack of one, named. */
		ItemStack nou() {
			return descriure(new ItemStack(material), this);
		}

		/** Names and explains the item when the game has words for it; returns the same stack. */
		static ItemStack descriure(ItemStack item) {
			return descriure(item, de(item));
		}

		static ItemStack descriure(ItemStack item, Objecte objecte) {
			if (objecte == null) return item;
			ItemMeta meta = item.getItemMeta();
			if (objecte.brillant) meta.setEnchantmentGlintOverride(true);
			meta.displayName(Component.text(objecte.nom, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
			List<Component> lore = new ArrayList<>();
			for (String línia : objecte.llegenda()) lore.add(Component.text(línia, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
			meta.lore(lore);
			item.setItemMeta(meta);
			return item;
		}
	}

	/** Spends one of the stack in hand. Named items do not match a bare ItemStack, so the stack itself is shrunk. */
	private static void consumirUn(ItemStack stack) {
		stack.setAmount(stack.getAmount() - 1);
	}

	private static void treureUn(Inventory inv, Material material) {
		for (ItemStack item : inv.getContents()) {
			if (item == null || item.getType() != material) continue;
			item.setAmount(item.getAmount() - 1);
			return;
		}
	}

	private record ParadaDeLEquip(Parada parada, Equip equip) {}
	private final Map<UUID, ParadaDeLEquip> botiguers = new HashMap<>();

	//---------- The enchanting tables ----------

	/** Where an enchanting table stands: in a base, or on the canopy altars at the top of the jungle. */
	private enum Forja {
		BASE(ChatColor.GOLD + "Taula d'encantar"),
		CAPÇADA(ChatColor.LIGHT_PURPLE + "Altar de la capçada");

		final String títol;
		Forja(String títol) {
			this.títol = títol;
		}
	}

	/**
	 * What the tables sell, paid in gold, no lapis and no experience. A base table offers
	 * the simple line up to its base level; the canopy altars, a long climb away from the
	 * fight, offer every line up to its canopy level. Each purchase is the next level of
	 * what the item already has, at the line's price times that level.
	 */
	private enum Encantament {
		RETROCÉS("Retrocés", Enchantment.KNOCKBACK, "L'espasa empeny l'enemic en colpejar-lo", 1, 2, 10),
		EMPENTA("Empenta", Enchantment.PUNCH, "Les fletxes empenyen l'enemic", 1, 2, 10),
		FLAMA("Flama", Enchantment.FLAME, "Les fletxes encenen l'enemic", 1, 1, 15),
		EFICIÈNCIA("Eficiència", Enchantment.EFFICIENCY, "Trenca l'obsidiana més de pressa", 1, 3, 15),
		ESMOLAT("Esmolat", Enchantment.SHARPNESS, "Més dany amb l'espasa", 0, 3, 20),
		POTÈNCIA("Potència", Enchantment.POWER, "Més dany amb l'arc", 0, 3, 20),
		ASPECTE_DE_FOC("Aspecte de foc", Enchantment.FIRE_ASPECT, "L'espasa encén l'enemic", 0, 1, 25),
		PROTECCIÓ("Protecció", Enchantment.PROTECTION, "Menys dany rebut", 0, 2, 20);

		final String nom;
		final Enchantment encantament;
		final String descripció;
		final int nivellMàximBase;
		final int nivellMàximCapçada;
		final int preuPerNivell;
		Encantament(String nom, Enchantment encantament, String descripció, int nivellMàximBase, int nivellMàximCapçada, int preuPerNivell) {
			this.nom = nom;
			this.encantament = encantament;
			this.descripció = descripció;
			this.nivellMàximBase = nivellMàximBase;
			this.nivellMàximCapçada = nivellMàximCapçada;
			this.preuPerNivell = preuPerNivell;
		}

		int nivellMàxim(Forja forja) {
			return forja == Forja.BASE ? nivellMàximBase : nivellMàximCapçada;
		}

		/** The level this table would put on the item, or 0 when the item cannot take it or has all this table gives. */
		int nivellSegüent(Forja forja, ItemStack item) {
			if (item == null || item.getType() == Material.AIR || !encantament.canEnchantItem(item)) return 0;
			int següent = item.getEnchantmentLevel(encantament) + 1;
			return següent <= nivellMàxim(forja) ? següent : 0;
		}

		int preu(int nivell) {
			return preuPerNivell * nivell;
		}
	}

	/** An enchanting table of the map and, for the ones in a base, the team whose base it is. */
	private record TaulaDEncantar(Forja forja, Integer equip) {}
	private final Map<Block, TaulaDEncantar> taulesDEncantar = new HashMap<>();
	/** The sign the plugin stands on each table → the table; a click on the sign is a click on the table. */
	private final Map<Block, Block> rètolsDeTaula = new HashMap<>();
	/** Player → the one-shot hints already shown: a line above the hotbar the first time they come near a thing. */
	private final Map<UUID, Set<String>> hintsShown = new HashMap<>();
	private static final double HINT_DISTANCE = 4;
	private static final long HINT_TICKS = 80;
	/** A base's tables stand at the base's level within the sign radius; anything higher is the canopy. */
	private static final int ALÇADA_TAULES_DE_BASE = 3;
	private static final String[] NIVELLS_ROMANS = { "", "I", "II", "III", "IV", "V" };

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
		registrarTaulesDEncantar();
		registrarPonts();
		registerControlPointsAndLamps();
		scheduleGameplayTask(this::verifyRegistrations, REGISTRATION_CHECK_TICKS);
		Bukkit.getPluginManager().registerEvents(worldListener, plugin);
		showObjective();
		emptyDispensers();
		scheduleGameplayRepeatingTask(this::cicleCofres, FIRST_CHEST_CYCLE_TICKS, CICLE_COFRES_TICKS);
		scheduleGameplayRepeatingTask(this::tickControlPoints, 20, 20);
		scheduleGameplayRepeatingTask(this::tickDetonators, 20, DETONATOR_TICK_PERIOD);
		scheduleGameplayRepeatingTask(this::tickHints, 30, 20);
		scheduleGameplayRepeatingTask(this::apareixerPicDiamant, PRIMER_PIC_TICKS, PERIODE_PIC_TICKS);
		guardiàTornaAlSegon = (int) (GOLEM_INICIAL_TICKS / 20);
		guardianSlayerTeam = null;
		scheduleGameplayTask(this::apareixerGolem, GOLEM_INICIAL_TICKS);
		suddenDeath = false;
		killsByTeam.clear();
		witherDeaths.clear();
		quartzBuyers.clear();
		scheduleGameplayTask(this::warnSuddenDeath, (SUDDEN_DEATH_SECOND - SUDDEN_DEATH_WARNING_SECONDS) * 20L);
		scheduleGameplayTask(this::startSuddenDeath, SUDDEN_DEATH_SECOND * 20L);
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
		setMaxHealth(ply, FULL_MAX_HEALTH);
		donarOr(ply, INITIAL_GOLD);
	}

	private static void setMaxHealth(Player ply, double maxHealth) {
		ply.getAttribute(Attribute.MAX_HEALTH).setBaseValue(maxHealth);
		if (ply.getHealth() > maxHealth) ply.setHealth(maxHealth);
	}

	/** The recall clock in the last hotbar slot (Biel, 2026-09-08: "a way to go back home"): a three-second channel to the base, cancelled by moving. */
	@Override
	protected boolean isRecallEnabled() {
		return true;
	}

	@Override
	protected double recallSeconds() {
		return RECALL_SECONDS;
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
		// Three lines; the rest is taught where it happens: item tooltips, signs, the one-shot hints, and the book (/guia).
		return new ArrayList<>(GUIDE.lines("inici"));
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
			layMapDetonator(e);
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

	/** The middle of a team's TNT, at the height of the blocks' centres; null for a base without TNT. */
	private Location coreCentre(Equip e) {
		Set<Vector> core = nuclisPerEquip.getOrDefault(e.getId(), Set.of());
		if (core.isEmpty()) return null;
		Vector sum = new Vector();
		for (Vector tnt : core) sum.add(tnt);
		return sum.multiply(1.0 / core.size()).add(new Vector(0.5, 0.5, 0.5)).toLocation(world);
	}

	/**
	 * The vault whose ring the obsidian belongs to: a block at the TNT's level within
	 * {@link #RING_REACH} of one of the team's TNT, else null. On the 2013 map the vault
	 * is a one-block-high cavity under the spawn room, so nobody can stand inside it; the
	 * ring's exposed back wall, walkable on top from the terrain, is the only obsidian an
	 * attacker can reach.
	 */
	private Equip vaultOfRingBlock(Block obsidian) {
		for (Equip e : Equips) {
			for (Vector tnt : nuclisPerEquip.getOrDefault(e.getId(), Set.of())) {
				if (tnt.getBlockY() == obsidian.getY() && Math.abs(tnt.getBlockX() - obsidian.getX()) <= RING_REACH && Math.abs(tnt.getBlockZ() - obsidian.getZ()) <= RING_REACH) return e;
			}
		}
		return null;
	}

	/** A plate laid at match start where the map says ({@code Detonador<team>}, the block under it); without the property the breaches are the detonators. */
	private void layMapDetonator(Equip e) {
		if (!pMapaActual().ExisteixPropietat("Detonador" + e.getId())) return;
		layDetonator(e, pMapaActual().ObtenirLocation("Detonador" + e.getId(), world).getBlock().getRelative(BlockFace.UP));
	}

	/** The gold plate on the cell, over a block of obsidian when the cell hangs over nothing, as the ring's back wall does. */
	private void layDetonator(Equip vaultOf, Block cell) {
		Block support = cell.getRelative(BlockFace.DOWN);
		if (support.isPassable()) support.setType(Material.OBSIDIAN, false);
		cell.setType(DETONATOR_PLATE, false);
		teamByDetonator.put(cell, vaultOf.getId());
		plugin.getLogger().info(getGameName() + " " + getMapName() + ": detonator of base" + vaultOf.getId() + " at " + cell.getLocation().toVector());
	}

	/** A block of the ring goes: a tick later, once it is air, the breach is floored and the detonator laid in it, for the breacher to step down onto. */
	private void breach(Player ply, Block block, Equip vaultOf) {
		Equip team = obtenirEquip(ply);
		sendGlobalMessage((team == null ? "" : team.getChatColor().toString()) + ply.getName() + ChatColor.WHITE + " ha obert una bretxa a la base " + vaultOf.getAdjectiuColored() + ChatColor.WHITE + "!");
		scheduleGameplayTask(() -> {
			if (!JocEnMarxa() || !block.getType().isAir()) return;
			layDetonator(vaultOf, block);
			world.playSound(block.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.6F, 1.6F);
			PaperMessages.sendActionBar(ply, ChatColor.GOLD + "Trepitja el detonador de la bretxa", HINT_TICKS);
		}, 1);
	}

	/** The team whose vault the detonator plate opens, or null for any other block. */
	private Equip teamOfDetonator(Block block) {
		Integer team = teamByDetonator.get(block);
		return team == null ? null : obtenirEquip(team);
	}

	/** Four times a second, the control points' check at a faster pace: an enemy foot on a detonator. */
	private void tickDetonators() {
		if (!JocEnMarxa()) return;
		for (Player p : getPlayers()) stepOnDetonator(p, p.getLocation().getBlock());
	}

	/** A defender on their own detonator is nothing; an enemy on it is the end of the match. */
	private void stepOnDetonator(Player p, Block feet) {
		Equip vaultOf = teamOfDetonator(feet);
		if (vaultOf == null || !JocEnMarxa()) return;
		Equip team = obtenirEquip(p);
		if (team == null || team == vaultOf) return;
		blowUpBase(vaultOf, p);
	}

	/** A primed TNT going off inside a vault (a Flame arrow through a broken block) blows the base the same way. */
	@Override
	protected void onExplosionPrime(ExplosionPrimeEvent evt) {
		super.onExplosionPrime(evt);
		if (!JocEnMarxa() || !(evt.getEntity() instanceof TNTPrimed)) return;
		Equip explotat = equipDelNucli(evt.getEntity().getLocation());
		if (explotat == null) return;
		blowUpBase(explotat, null);
	}

	/**
	 * The base goes up: the announcement and the win at once, then the vault's TNT block by
	 * block, then blasts at random over the whole base that break its blocks, for the
	 * spectators. The win cancels every gameplay task, so the show runs on lifecycle tasks.
	 */
	private void blowUpBase(Equip vaultOf, Player detonator) {
		if (explodedBase != null || !JocEnMarxa()) return;
		explodedBase = vaultOf;
		if (detonator != null) sendGlobalMessage(obtenirEquip(detonator).getChatColor() + detonator.getName() + ChatColor.WHITE + " ha trepitjat el detonador de la base " + vaultOf.getAdjectiuColored() + ChatColor.WHITE + "!");
		sendGlobalMessage(ChatColor.RED + "La base de l'equip " + vaultOf.getAdjectiuColored() + ChatColor.RED + " ha explotat!");
		sendGlobalSound(Sound.ENTITY_GENERIC_EXPLODE, 2F, 0.6F);
		winGame(obtenirEquipEnemic(vaultOf));
		igniteCore(vaultOf);
		Location base = vaultOf.getTeamSpawnLocation();
		for (int i = 0; i < RUIN_BLASTS; i++) {
			long delay = CORE_FUSE_SPREAD_TICKS + RUIN_TICKS_BETWEEN_BLASTS * i;
			handleLifecycleTask(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> ruinBlast(base), delay));
		}
	}

	/** Every TNT block of the vault still standing becomes a primed one with its own short fuse. */
	private void igniteCore(Equip vaultOf) {
		for (Vector position : nuclisPerEquip.getOrDefault(vaultOf.getId(), Set.of())) {
			Block block = position.toLocation(world).getBlock();
			if (block.getType() != Material.TNT) continue;
			block.setType(Material.AIR, false);
			TNTPrimed tnt = world.spawn(block.getLocation().add(0.5, 0, 0.5), TNTPrimed.class);
			tnt.setFuseTicks(10 + Utils.NombreEntre(0, CORE_FUSE_SPREAD_TICKS));
		}
	}

	/** One blast on the surface of the losing base, somewhere within the ruin radius of its spawn; the void around the map is skipped. */
	private void ruinBlast(Location base) {
		if (world == null) return;
		double angle = Math.random() * 2 * Math.PI, radius = Math.sqrt(Math.random()) * RUIN_RADIUS;
		int x = base.getBlockX() + (int) Math.round(radius * Math.cos(angle));
		int z = base.getBlockZ() + (int) Math.round(radius * Math.sin(angle));
		int top = world.getHighestBlockYAt(x, z);
		if (top < SCAN_MIN_Y || top > SCAN_MAX_Y) return;
		world.createExplosion(x + 0.5, top + 0.5, z + 0.5, RUIN_BLAST_POWER, false, true);
	}

	/** Everyone watches the base go up from over its spawn, facing the vault; a match ended any other way ends in the middle as usual. */
	@Override
	protected void raisePlayersToSpectatorZone() {
		Location vault = explodedBase == null ? null : coreCentre(explodedBase);
		if (vault == null) {
			super.raisePlayersToSpectatorZone();
			return;
		}
		Location view = explodedBase.getTeamSpawnLocation().clone().add(0, RUIN_VIEW_HEIGHT, 0);
		view.setDirection(vault.toVector().subtract(view.toVector()));
		for (Player p : getPlayers()) {
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(view);
		}
	}

	/** Obsidian is the one block anyone may break: it is what shields the TNT. A ring block broken is a breach, and the breach is the detonator. */
	@Override
	protected void onBlockBreak(BlockBreakEvent evt, Block blk) {
		super.onBlockBreak(evt, blk);
		Player ply = evt.getPlayer();
		if (blk.getType() != Material.OBSIDIAN || ply.getGameMode() == GameMode.CREATIVE || !JocEnMarxa()) return;
		evt.setCancelled(false);
		// The broken block is the breach, never a block to carry (JoniMega, 2026-09-08: "la obsidiana que he minat ha caigut").
		evt.setDropItems(false);
		world.playSound(blk.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1F, 1F);
		Equip vaultOf = vaultOfRingBlock(blk);
		if (vaultOf != null) breach(ply, blk, vaultOf);
		else sendGlobalMessage(ply.getName() + ChatColor.DARK_PURPLE + " ha trencat un bloc d'obsidiana!");
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
		if (++chestCycles % CYCLES_PER_DEFAULT_SQUARE == 0) for (Equip e : Equips) carregarPont(e, 1);
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
			Objecte objecte = Objecte.de(loot);
			if (objecte == Objecte.ESTRELLA_DEL_NETHER) announceLoot(b, objecte, Particle.FLAME, Color.fromRGB(255, 60, 30), ChatColor.RED + "Una estrella infernal" + ChatColor.WHITE + " ha aparegut a la jungla!");
			if (objecte == Objecte.BOLA_DE_NEU_ENCANTADA) announceLoot(b, objecte, Particle.END_ROD, Color.fromRGB(120, 220, 255), ChatColor.AQUA + "Una bola de neu encantada" + ChatColor.WHITE + " ha aparegut a la jungla!");
		}
	}

	/**
	 * Randomise where and when, never the outcome (Biel, 2026-09-07 night): a prize in a
	 * chest is announced to everyone and a beam stands over the chest until the prize is
	 * taken or the chest closes, so both teams can run for it.
	 */
	private void announceLoot(Block chest, Objecte prize, Particle beam, Color colour, String announcement) {
		sendGlobalMessage(announcement);
		for (Player p : getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_BELL_RESONATE, 0.6F, 1.4F);
		Location base = chest.getLocation().add(0.5, 1, 0.5);
		// A firework in the prize's colour at the landing: seen over the canopy from anywhere on the map.
		Firework firework = world.spawn(base.clone().add(0, 2, 0), Firework.class, fw -> {
			FireworkMeta meta = fw.getFireworkMeta();
			meta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(colour).withFade(Color.WHITE).withFlicker().withTrail().build());
			meta.setPower(1);
			fw.setFireworkMeta(meta);
		});
		scheduleGameplayTask(firework::detonate, 15);
		int[] frame = new int[1], taskId = new int[1];
		taskId[0] = scheduleGameplayRepeatingTask(() -> {
			if (!JocEnMarxa() || !(chest.getState() instanceof Chest open) || !holdsPrize(open.getInventory(), prize)) {
				Bukkit.getScheduler().cancelTask(taskId[0]);
				return;
			}
			frame[0]++;
			// A solid column two particles a block tall, and a helix of the prize's colour turning around it.
			for (int i = 0; i < LOOT_BEAM_HEIGHT * 2; i++) world.spawnParticle(beam, base.clone().add(0, i / 2.0, 0), 1, 0.05, 0.05, 0.05, 0);
			Particle.DustOptions dust = new Particle.DustOptions(colour, 1.6F);
			for (int i = 0; i < LOOT_BEAM_HEIGHT; i++) {
				double angle = frame[0] * 0.3 + i * 0.6;
				world.spawnParticle(Particle.DUST, base.clone().add(0.7 * Math.cos(angle), i, 0.7 * Math.sin(angle)), 1, 0, 0, 0, 0, dust);
			}
		}, 0, LOOT_BEAM_PERIOD_TICKS);
	}

	private static boolean holdsPrize(Inventory inv, Objecte prize) {
		for (ItemStack item : inv.getContents()) if (item != null && Objecte.de(item) == prize) return true;
		return false;
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
		if (Utils.Possibilitat(5)) loot.add(new ItemStack(Material.GOLD_NUGGET, 10 * Utils.NombreEntre(1, 2)));
		if (Utils.Possibilitat(20)) loot.add(new ItemStack(Material.EMERALD));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.MAGMA_CREAM));
		if (Utils.Possibilitat(14)) loot.add(new ItemStack(Material.SNOWBALL));
		if (Utils.Possibilitat(HERO_SNOWBALL_CHEST_CHANCE)) loot.add(Objecte.BOLA_DE_NEU_ENCANTADA.nou());
		if (Utils.Possibilitat(8)) loot.add(new ItemStack(Material.EXPERIENCE_BOTTLE, Utils.NombreEntre(1, 3)));
		if (Utils.Possibilitat(5)) loot.add(new ItemStack(Material.ENDER_PEARL));
		if (Utils.Possibilitat(NETHER_STAR_CHEST_CHANCE)) loot.add(new ItemStack(Material.NETHER_STAR));
		if (Utils.Possibilitat(8)) loot.add(new ItemStack(Material.ARROW, 4));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.GOLDEN_SWORD));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.IRON_SWORD));
		// No anvil on the map and the tables sell enchantments, so no books (JoniMega, 2026-09-07): nuggets instead.
		if (Utils.Possibilitat(15)) loot.add(new ItemStack(Material.GOLD_NUGGET, 3));
		loot.replaceAll(Objecte::descriure);
		return loot;
	}

	/** One nugget per cycle, more for holding a gold block or the gold pickaxe, and a bonus for both. */
	private void donarOrPassiu(Player p) {
		int or = 1;
		Inventory inv = p.getInventory();
		if (inv.contains(Material.GOLD_BLOCK)) {
			sendPlayerMessage(p, ChatColor.GRAY + "Bloc d'or --> +" + GOLD_BLOCK_PASSIVE_GOLD + " Or passiu");
			or += GOLD_BLOCK_PASSIVE_GOLD;
		}
		Equip team = obtenirEquip(p);
		if (team != null && killsBehind(team) >= CATCH_UP_KILL_GAP) {
			sendPlayerMessage(p, ChatColor.GRAY + "Remuntada --> +" + CATCH_UP_GOLD + " Or passiu");
			or += CATCH_UP_GOLD;
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
		world.dropItem(puntPicDiamant(), Objecte.descriure(pic)).setVelocity(new Vector(0, 0, 0));
		if (!primerPicAnunciat) {
			sendGlobalMessage(ChatColor.AQUA + "Ha aparegut el primer pic de diamant!");
			primerPicAnunciat = true;
		}
	}

	@Override
	protected void onPlayerPickupItem(PlayerPickupItemEvent evt, Player p) {
		super.onPlayerPickupItem(evt, p);
		Item item = evt.getItem();
		if (item.getItemStack().getType() == Material.GOLD_NUGGET || item.getItemStack().getType() == Material.GOLD_INGOT) refreshGoldSoon(p);
		Objecte objecte = Objecte.de(item.getItemStack());
		if (objecte == Objecte.BOLA_DE_NEU) hint(p, "bola");
		if (objecte == Objecte.BOLA_DE_NEU_ENCANTADA) hint(p, "superninot");
		if (objecte == Objecte.ESTRELLA_DEL_NETHER) hint(p, "estrella");
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
		Color colorAura = guardiàEnfurismat ? COLOR_AURA_ENFURISMAT : COLOR_AURA_GUARDIÀ;
		Particle.DustOptions pols = new Particle.DustOptions(colorAura, 1.1F);
		double gir = passosAura * 0.15;
		auraRing(peus, colorAura, RADI_AURA_GUARDIÀ, gir, guardiàEnfurismat ? 4 : 2);
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
			// The sidebar is refreshed only when the coarse countdown changes.
			if (guardiàTornaAlSegon > segonsTranscorreguts() && !estatGuardià().equals(lastGuardianCountdownShown)) updateScoreBoards();
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

	/** A player who leaves the match (/l, a teleport out) must not carry the Guardian's bar to the lobby. */
	@Override
	protected void customLeave(Player ply, List<String> attatchments) {
		super.customLeave(ply, attatchments);
		if (barraGuardià != null) ply.hideBossBar(barraGuardià);
		if (objectiveBar != null) ply.hideBossBar(objectiveBar);
		cancelStarCharge(ply);
		setMaxHealth(ply, FULL_MAX_HEALTH);
		Block shown = shownPressedPlate.remove(ply.getUniqueId());
		if (shown != null) ply.sendBlockChange(shown.getLocation(), shown.getBlockData());
	}
	/** A lost connection mid-charge: the charge task must not fire on a player who is gone; the star drops at their feet as a death would. */
	@Override
	protected void onSeatDropped(Player ply) {
		super.onSeatDropped(ply);
		cancelStarCharge(ply);
	}
	/** Away past the grace: only what is keyed by them here needs forgetting; the bar re-shows itself each second to whoever is present. */
	@Override
	protected void onSeatAbandoned(Seat seat, List<String> attatchments) {
		super.onSeatAbandoned(seat, attatchments);
		shownPressedPlate.remove(seat.getUuid());
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
		int step = GUARDIAN_COUNTDOWN_STEP_SECONDS;
		segons = (segons + step - 1) / step * step;
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
			p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 3 * 60 * 20, 0, false), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30 * 20, 1, false), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * 60 * 20, 1, false), true);
			PaperMessages.sendActionBar(p, ChatColor.AQUA + "Benedicció del Guardià", 60);
			sendGlobalMessage(p.getName() + " ha matat " + ChatColor.AQUA + NOM_GUARDIÀ + ChatColor.WHITE + ", el golem de ferro (" + ChatColor.GOLD + "+" + OR_PER_GOLEM + ChatColor.WHITE + ")");
			Equip slayers = obtenirEquip(p);
			if (slayers != null && slayers != guardianSlayerTeam) {
				guardianSlayerTeam = slayers;
				sendGlobalMessage(slayers.getChatColor() + "Els ninots de l'equip " + slayers.getAdjectiu() + ChatColor.WHITE + " ara són de magma i cremen, fins que l'altre equip mati el Guardià.");
			}
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
					boolean booth = false;
					for (Parada parada : Parada.values()) {
						if (!text.contains(parada.rètolOriginal)) continue;
						booth = true;
						boothSigns.add(bloc);
						escriureRètol(bloc, ChatColor.GOLD + parada.nom, ChatColor.GRAY + parada.ofici, "", "");
						Villager botiguer = villagerDeLaParada(bloc.getLocation());
						adoptarBotiguer(botiguer, new ParadaDeLEquip(parada, e));
					}
					if (!booth) unusedSigns.add(rètol);
				}
			}
			if (!rètolsPont.containsKey(e.getId())) {
				plugin.getLogger().warning(getGameName() + " " + getMapName() + ": no bridge sign within " + RADI_RÈTOLS + " blocks of base" + e.getId());
			}
			registerKillsSign(e, boothSigns, unusedSigns);
			boothSigns.clear();
			unusedSigns.clear();
		}
	}

	/**
	 * Events the game's bus does not route: a minion on a plate must not depress it for
	 * real (the 2013 wiring stays dormant for minions as for players), and the sidebar's
	 * gold follows the inventory the moment a chest is clicked or closed (Biel, 2026-09-08:
	 * "picking up gold from a chest should increase the gold immediately").
	 */
	private final Listener worldListener = new Listener() {
		@EventHandler
		public void onEntityInteract(EntityInteractEvent evt) {
			if (world == null || evt.getBlock().getWorld() != world || minionOf(evt.getEntity()) == null) return;
			if (Tag.PRESSURE_PLATES.isTagged(evt.getBlock().getType())) evt.setCancelled(true);
		}

		@EventHandler
		public void onInventoryClick(InventoryClickEvent evt) {
			if (evt.getWhoClicked() instanceof Player p) refreshGoldSoon(p);
		}

		@EventHandler
		public void onInventoryClose(InventoryCloseEvent evt) {
			if (evt.getPlayer() instanceof Player p) refreshGoldSoon(p);
		}
	};

	private void refreshGoldSoon(Player p) {
		if (world == null || p.getWorld() != world || !JocEnMarxa()) return;
		scheduleGameplayTask(() -> { if (p.isOnline()) { ajuntarOr(p); updateScoreBoard(p); } }, 1);
	}

	/**
	 * Three seconds after the start, what the scans should have found is checked once
	 * more: the instance recreated at 23:12 on 2026-09-07, 35 s after one of the same name
	 * was deleted, found no bridge sign and no lamps for base0 while base1 was whole.
	 */
	private void verifyRegistrations() {
		if (!JocEnMarxa()) return;
		List<String> missing = new ArrayList<>();
		for (Equip e : Equips) if (!rètolsPont.containsKey(e.getId())) missing.add("bridge sign of base" + e.getId());
		if (controlPoints.isEmpty()) missing.add("control points");
		if (missing.isEmpty()) return;
		plugin.getLogger().warning(getGameName() + " " + getMapName() + ": " + missing + " missing " + REGISTRATION_CHECK_TICKS / 20 + " s after the start; scanning again");
		if (missing.stream().anyMatch(m -> m.startsWith("bridge sign"))) registrarControlsIParades();
		registerControlPointsAndLamps();
	}

	private final List<Block> boothSigns = new ArrayList<>();
	private final List<Sign> unusedSigns = new ArrayList<>();

	/**
	 * The kills sign (Biel, 2026-09-07 night: "on one of the currently unused signs in the
	 * shop area, so we don't overcrowd the scoreboard"): the block a {@code RètolKills<team>}
	 * map property names, else the unmatched sign nearest the booths. Every unmatched sign
	 * is logged with its text so the property can be set once the right one is known.
	 */
	private void registerKillsSign(Equip e, List<Block> booths, List<Sign> unused) {
		String property = "RètolKills" + e.getId();
		Block chosen = pMapaActual().ExisteixPropietat(property) ? pMapaActual().ObtenirLocation(property, world).getBlock() : null;
		if (chosen == null && !booths.isEmpty() && !unused.isEmpty()) {
			Vector centre = new Vector();
			for (Block b : booths) centre.add(b.getLocation().toVector());
			centre.multiply(1.0 / booths.size());
			Sign nearest = null;
			double best = Double.MAX_VALUE;
			for (Sign sign : unused) {
				double d = sign.getLocation().toVector().distanceSquared(centre);
				if (d < best) { best = d; nearest = sign; }
			}
			chosen = nearest.getBlock();
		}
		for (Sign sign : unused) {
			plugin.getLogger().info(getGameName() + " base" + e.getId() + " unused sign at " + sign.getX() + "," + sign.getY() + "," + sign.getZ() + ": \"" + String.join(" / ", sign.getSide(Side.FRONT).getLines()) + "\"" + (chosen != null && sign.getBlock().equals(chosen) ? " (kills sign)" : ""));
		}
		if (chosen == null) return;
		killsSigns.put(e.getId(), chosen);
		writeKillsSigns();
	}

	private void writeKillsSigns() {
		String[] lines = new String[4];
		lines[0] = ChatColor.GOLD + "Kills";
		int i = 1;
		for (Equip e : Equips) if (i < 4) lines[i++] = e.getChatColor() + e.getAdjectiu() + ": " + killsByTeam.getOrDefault(e.getId(), 0);
		while (i < 4) lines[i++] = "";
		for (Block sign : killsSigns.values()) if (sign.getState() instanceof Sign) escriureRètol(sign, lines);
	}

	/** How many kills this team trails the best other team by; zero when level or ahead. */
	private int killsBehind(Equip team) {
		int best = 0;
		for (Equip other : Equips) if (other != team) best = Math.max(best, killsByTeam.getOrDefault(other.getId(), 0));
		return Math.max(0, best - killsByTeam.getOrDefault(team.getId(), 0));
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
		HandlerList.unregisterAll(worldListener);
		hideObjective();
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
			Objecte objecte = Objecte.de(m.material);
			if (objecte != null) for (String línia : objecte.llegenda()) info.add(ChatColor.GRAY + línia);
			else if (m.descripció != null) info.add(ChatColor.GRAY + m.descripció);
			info.add(ChatColor.WHITE + "Preu: " + ChatColor.GOLD + m.preu + " or");
			boolean soldOut = m == Mercaderia.QUARS && hasQuartz(p);
			if (soldOut) info.add(ChatColor.RED + "Ja el tens: només un per persona");
			if (m == Mercaderia.FLETXES && !hasBow(p)) { soldOut = true; info.add(ChatColor.RED + "Primer compra un arc"); }
			menu.setOption(mercaderies.indexOf(m), new ItemStack(m.material, m.quantitat), (soldOut ? ChatColor.DARK_GRAY : ChatColor.YELLOW) + m.nom, info);
		}
		menu.open(p);
	}

	private void comprar(Player p, Mercaderia m) {
		if (!JocEnMarxa()) return;
		if (m == Mercaderia.QUARS && hasQuartz(p)) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			PaperMessages.sendActionBar(p, ChatColor.RED + "Només un quars per persona", 60);
			return;
		}
		if (m == Mercaderia.FLETXES && !hasBow(p)) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			PaperMessages.sendActionBar(p, ChatColor.RED + "Primer compra un arc", 60);
			return;
		}
		int falta = m.preu - orDisponible(p);
		if (falta > 0 || !gastarOr(p, m.preu)) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			PaperMessages.sendActionBar(p, ChatColor.RED + "Et falten " + Math.max(1, falta) + " or", 60);
			return;
		}
		if (m == Mercaderia.QUARS) quartzBuyers.add(p.getUniqueId());
		giveOrDrop(p, Objecte.descriure(new ItemStack(m.material, m.quantitat)));
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
	/** Arrows without a bow are gold thrown away (Biel, 2026-09-08): Gerry sells them only to a player who carries one. */
	private static boolean hasBow(Player p) {
		return p.getInventory().contains(Material.BOW);
	}

	/** Quartz is one per person for the match (Biel, 2026-09-07 night): bought once, or already carried. */
	private boolean hasQuartz(Player p) {
		return quartzBuyers.contains(p.getUniqueId()) || p.getInventory().contains(Material.QUARTZ);
	}

	/** Puts the stack in the inventory; what does not fit falls at the player's feet instead of vanishing. */
	private void giveOrDrop(Player p, ItemStack stack) {
		if (Utils.isArmor(stack)) {
			Utils.giveItemStack(stack, p);
			return;
		}
		for (ItemStack leftover : p.getInventory().addItem(stack).values()) world.dropItemNaturally(p.getLocation(), leftover);
	}

	private boolean gastarOr(Player p, int preu) {
		ajuntarOr(p);
		if (orDisponible(p) < preu) return false;
		p.getInventory().removeItem(new ItemStack(Material.GOLD_NUGGET, preu));
		return true;
	}

	/**
	 * Finds the map's enchanting tables between the bases and a little beyond them. A table
	 * at a base's level within the sign radius belongs to that base; the rest are the
	 * canopy altars, which anyone may use.
	 */
	private void registrarTaulesDEncantar() {
		taulesDEncantar.clear();
		rètolsDeTaula.clear();
		if (Equips.size() < 2) return;
		Location a = Equips.get(0).getTeamSpawnLocation(), b = Equips.get(1).getTeamSpawnLocation();
		int minX = Math.min(a.getBlockX(), b.getBlockX()) - RADI_RÈTOLS, maxX = Math.max(a.getBlockX(), b.getBlockX()) + RADI_RÈTOLS;
		int midZ = (a.getBlockZ() + b.getBlockZ()) / 2;
		for (Block taula : blocksBetween(minX, midZ - RADI_RÈTOLS, maxX, midZ + RADI_RÈTOLS, m -> m == Material.ENCHANTING_TABLE)) {
			TaulaDEncantar registre = new TaulaDEncantar(Forja.CAPÇADA, null);
			for (Equip e : Equips) {
				Location base = e.getTeamSpawnLocation();
				boolean alNivellDeLaBase = Math.abs(taula.getY() - base.getBlockY()) <= ALÇADA_TAULES_DE_BASE;
				boolean aLaBase = Math.abs(taula.getX() - base.getBlockX()) <= RADI_RÈTOLS && Math.abs(taula.getZ() - base.getBlockZ()) <= RADI_RÈTOLS;
				if (alNivellDeLaBase && aLaBase) registre = new TaulaDEncantar(Forja.BASE, e.getId());
			}
			taulesDEncantar.put(taula, registre);
			posarRètolDeTaula(taula, registre.forja());
		}
		long alsAltars = taulesDEncantar.values().stream().filter(t -> t.forja() == Forja.CAPÇADA).count();
		plugin.getLogger().info(getGameName() + " " + getMapName() + ": " + (taulesDEncantar.size() - alsAltars) + " base enchanting tables and " + alsAltars + " canopy altars");
	}

	/** A waxed sign standing on the table, turned to its open side, saying what it is in three words. */
	private void posarRètolDeTaula(Block taula, Forja forja) {
		Block sobre = taula.getRelative(BlockFace.UP);
		if (!sobre.getType().isAir()) return;
		BlockFace obert = null;
		for (BlockFace cara : List.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)) {
			Block costat = taula.getRelative(cara);
			if (costat.isPassable() && costat.getRelative(BlockFace.UP).isPassable()) { obert = cara; break; }
		}
		if (obert == null) return;
		sobre.setType(Material.OAK_SIGN, false);
		if (sobre.getBlockData() instanceof Rotatable rotatable) {
			rotatable.setRotation(obert);
			sobre.setBlockData(rotatable, false);
		}
		if (forja == Forja.BASE) escriureRètol(sobre, ChatColor.GOLD + "Taula", ChatColor.GOLD + "d'encantar", ChatColor.GRAY + "paga amb or", "");
		else escriureRètol(sobre, ChatColor.LIGHT_PURPLE + "Altar de la", ChatColor.LIGHT_PURPLE + "capçada", ChatColor.GRAY + "encanteris", ChatColor.GRAY + "forts");
		if (sobre.getState() instanceof Sign rètol) {
			rètol.setWaxed(true);
			rètol.update(true, false);
		}
		rètolsDeTaula.put(sobre, taula);
	}

	/** Once per player per match, a line above the hotbar the first time they stand near a table, a plate or the bridge button. */
	private void tickHints() {
		if (!JocEnMarxa()) return;
		for (Player p : getPlayers()) {
			Location at = p.getLocation();
			for (Map.Entry<Block, TaulaDEncantar> taula : taulesDEncantar.entrySet()) {
				if (!aProp(at, taula.getKey())) continue;
				if (taula.getValue().forja() == Forja.BASE) hint(p, "taula");
				else hint(p, "altar");
			}
			for (ControlPoint point : controlPoints) {
				for (Block plate : point.plateByTeam.values()) if (aProp(at, plate)) hint(p, "placa");
			}
			for (Block botó : botonsPont.keySet()) if (aProp(at, botó)) hint(p, "botó");
			for (Block detonator : teamByDetonator.keySet()) if (aProp(at, detonator)) hint(p, "detonador");
			for (UUID shopkeeper : botiguers.keySet()) {
				Entity villager = Bukkit.getEntity(shopkeeper);
				if (villager != null && villager.getWorld() == world && villager.getLocation().distance(at) <= HINT_DISTANCE) hint(p, "paradista");
			}
			for (Location chestSpot : pMapaActual().ObtenirLocations("cofres", world)) {
				if (chestSpot.getBlock().getType() == Material.CHEST && aProp(at, chestSpot.getBlock())) hint(p, "cofre");
			}
			Equip team = obtenirEquip(p);
			if (team == null) continue;
			for (Equip enemy : Equips) {
				if (enemy == team) continue;
				for (Vector core : nuclisPerEquip.getOrDefault(enemy.getId(), Set.of())) {
					if (core.toLocation(world).add(0.5, 0.5, 0.5).distance(at) > VAULT_HINT_DISTANCE) continue;
					hint(p, "cambra");
					break;
				}
			}
		}
	}

	private static boolean aProp(Location at, Block block) {
		return block.getLocation().add(0.5, 0.5, 0.5).distance(at) <= HINT_DISTANCE;
	}

	private void hint(Player p, String clau) {
		if (!hintsShown.computeIfAbsent(p.getUniqueId(), id -> new HashSet<>()).add(clau)) return;
		PaperMessages.sendActionBar(p, ChatColor.YELLOW + GUIDE.line("pista " + clau), HINT_TICKS);
	}

	/** A click on a table: never the vanilla screen; the team's tables refuse the enemy. */
	private void obrirTaulaDEncantar(Player p, TaulaDEncantar taula) {
		if (!JocEnMarxa()) return;
		Equip equip = obtenirEquip(p);
		if (equip == null) return;
		if (taula.equip() != null && taula.equip() != equip.getId()) {
			rebutjarElementEnemic(p);
			return;
		}
		Forja forja = taula.forja();
		ItemStack item = p.getInventory().getItemInMainHand();
		List<Encantament> ofertes = new ArrayList<>();
		for (Encantament encantament : Encantament.values()) if (encantament.nivellSegüent(forja, item) > 0) ofertes.add(encantament);
		if (ofertes.isEmpty()) {
			playEnchantRefused(p);
			boolean resEncantable = item == null || item.getType() == Material.AIR || Arrays.stream(Encantament.values()).noneMatch(e -> e.encantament.canEnchantItem(item));
			p.sendMessage(ChatColor.GRAY + (resEncantable
					? "Agafa a la mà el que vols encantar: espasa, arc, pic o armadura."
					: forja == Forja.BASE ? "La taula de la base no pot encantar més això: puja als altars de la capçada." : "L'altar ja ha donat tot el que pot a això."));
			return;
		}
		IconMenu menu = new IconMenu(forja.títol, 9, event -> {
			int posició = event.getPosition();
			if (posició < ofertes.size()) encantar(event.getPlayer(), forja, ofertes.get(posició));
			event.setWillClose(true);
		});
		for (Encantament encantament : ofertes) {
			int nivell = encantament.nivellSegüent(forja, item);
			ArrayList<String> info = new ArrayList<>();
			info.add(ChatColor.GRAY + encantament.descripció);
			info.add(ChatColor.WHITE + "Preu: " + ChatColor.GOLD + encantament.preu(nivell) + " or");
			menu.setOption(ofertes.indexOf(encantament), new ItemStack(Material.ENCHANTED_BOOK), ChatColor.YELLOW + encantament.nom + " " + NIVELLS_ROMANS[nivell], info);
		}
		menu.open(p);
	}

	/** A table is not a villager: a refused enchantment fizzles, a hiss and a low note, instead of the villager's grunt. */
	private static void playEnchantRefused(Player p) {
		p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.8F, 0.7F);
		p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.6F, 0.5F);
	}

	/** The item is read again at the click: the hand may have changed while the menu was open. */
	private void encantar(Player p, Forja forja, Encantament encantament) {
		ItemStack item = p.getInventory().getItemInMainHand();
		int nivell = encantament.nivellSegüent(forja, item);
		int falta = nivell == 0 ? 0 : encantament.preu(nivell) - orDisponible(p);
		if (!JocEnMarxa() || nivell == 0 || falta > 0 || !gastarOr(p, encantament.preu(nivell))) {
			playEnchantRefused(p);
			if (falta > 0) PaperMessages.sendActionBar(p, ChatColor.RED + "Et falten " + falta + " or", 60);
			return;
		}
		item.addUnsafeEnchantment(encantament.encantament, nivell);
		p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1F, 1F);
		// An altar purchase is heard, softly, by the whole map: a strong player is up on the canopy.
		if (forja == Forja.CAPÇADA) for (Player v : world.getPlayers()) if (v != p) v.playSound(v.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.4F, 1F);
		p.sendMessage(ChatColor.LIGHT_PURPLE + encantament.nom + " " + NIVELLS_ROMANS[nivell] + ChatColor.WHITE + " (" + ChatColor.GOLD + "-" + encantament.preu(nivell) + " or" + ChatColor.WHITE + ")");
		updateScoreBoard(p);
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
			sendTeamMessage(e, ChatColor.AQUA + "Pont enemic a punt " + ChatColor.GRAY + "(activar des de la base)");
			for (Player p : e.getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1.4F);
		}
		mostrarCàrregaPont(e);
	}

	private boolean pontCarregat(Equip e) {
		return càrregaPont.getOrDefault(e.getId(), 0) >= PONT_CÀRREGA_MÀXIMA;
	}

	/** White squares fill quietly; the bar and its label turn aqua only when the bridge is ready. */
	/** True from the button press until the last plank of this team's bridge over the enemy moat is back. */
	private boolean bridgeDeployedBy(Equip e) {
		Equip enemy = obtenirEquipEnemic(e);
		return enemy != null && pontsDesplegats.contains(enemy.getId());
	}

	private String barraPont(Equip e) {
		if (bridgeDeployedBy(e)) return ChatColor.AQUA + "desplegat";
		int càrrega = càrregaPont.getOrDefault(e.getId(), 0);
		ChatColor plens = pontCarregat(e) ? ChatColor.AQUA : ChatColor.WHITE;
		return plens + QUADRAT_PLE.repeat(càrrega) + ChatColor.DARK_GRAY + QUADRAT_BUIT.repeat(PONT_CÀRREGA_MÀXIMA - càrrega);
	}

	private String etiquetaPont(Equip e, String text) {
		return (pontCarregat(e) || bridgeDeployedBy(e) ? ChatColor.AQUA : ChatColor.GRAY) + text;
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
		mostrarCàrregaPont(atacant);
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
		scheduleGameplayTask(() -> {
			pontsDesplegats.remove(moatDe.getId());
			mostrarCàrregaPont(atacant);
		}, totalDesplegament + PONT_TICKS_DESPLEGAT + PONT_TICKS_PER_COLUMNA * (columnes.size() + 1));
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
		towerLampsByTeam.clear();
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
			Location centre = point.centre();
			int cy = centre.getBlockY();
			for (Block lamp : blocksBetween(centre.getBlockX() - CONTROL_POINT_LAMP_REACH, centre.getBlockZ() - CONTROL_POINT_LAMP_REACH, centre.getBlockX() + CONTROL_POINT_LAMP_REACH, centre.getBlockZ() + CONTROL_POINT_LAMP_REACH, m -> m == Material.REDSTONE_LAMP)) {
				if (lamp.getY() < cy - 2 || lamp.getY() > cy + CONTROL_POINT_TOWER_HEIGHT) continue;
				int side = point.sideOf(lamp);
				if (lamp.getY() > cy + CONTROL_POINT_ROOM_HEIGHT && side != 0) {
					for (Equip e : Equips) if (point.sideOfTeam(e.getId()) == side) towerLampsByTeam.computeIfAbsent(e.getId(), id -> new ArrayList<>()).add(lamp);
				} else if (side == 0 || Math.abs(lamp.getX() - centre.getBlockX()) <= 1) {
					point.wallLamps.add(lamp);
				} else {
					point.lamps.add(lamp);
				}
			}
			point.wallLamps.sort((l1, l2) -> Integer.compare(l1.getX(), l2.getX()));
			showControlPoint(point);
			plugin.getLogger().info(getGameName() + " " + getMapName() + ": control point at " + centre.toVector() + " with plates " + point.plateByTeam.keySet() + ", " + point.wallLamps.size() + " wall lamps and " + point.lamps.size() + " side lamps");
		}
		for (List<Block> tower : towerLampsByTeam.values()) tower.sort(BOTTOM_ROW_FIRST);
		for (Equip e : Equips) {
			Block sign = rètolsPont.get(e.getId());
			List<Block> lamps = new ArrayList<>();
			if (sign != null) {
				lamps = blocksBetween(sign.getX() - BRIDGE_LAMP_REACH, sign.getZ() - BRIDGE_LAMP_REACH, sign.getX() + BRIDGE_LAMP_REACH, sign.getZ() + BRIDGE_LAMP_REACH, m -> m == Material.REDSTONE_LAMP);
				lamps.removeIf(l -> l.getLocation().distance(sign.getLocation()) > BRIDGE_LAMP_REACH);
				lamps.sort(BOTTOM_ROW_FIRST);
			}
			lampsByTeam.put(e.getId(), lamps);
			lightLamps(e);
			plugin.getLogger().info(getGameName() + " " + getMapName() + ": base" + e.getId() + " has " + lamps.size() + " bridge lamps");
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

	/** The team whose colour the plate sits on (the block under it), else whose colour rings it. */
	private Equip teamOfColourAround(Block plate) {
		Equip best = null;
		int bestCount = 0;
		for (Equip e : Equips) {
			String prefix = e.getColor().name() + "_";
			if (plate.getRelative(BlockFace.DOWN).getType().name().startsWith(prefix)) return e;
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
	 * Once a second: the pressed look for whoever steps on or off a plate, the capture
	 * channel of every point, and a bridge square per captured point every five seconds.
	 */
	private void tickControlPoints() {
		if (!JocEnMarxa()) return;
		Map<ControlPoint, Map<Integer, Player>> standing = new HashMap<>();
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
			standing.computeIfAbsent(point, k -> new HashMap<>()).putIfAbsent(team.getId(), p);
		}
		for (ControlPoint point : controlPoints) channel(point, standing.getOrDefault(point, Map.of()));
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

	/**
	 * One second of a point's capture channel. One team on its plate charges toward the
	 * capture, a lamp a second from its side; both teams at once freeze it; nobody lets a
	 * challenge fade back a lamp a second until the owner's full triple returns.
	 */
	private void channel(ControlPoint point, Map<Integer, Player> standing) {
		if (standing.size() == 1) {
			Map.Entry<Integer, Player> challenger = standing.entrySet().iterator().next();
			int team = challenger.getKey();
			if (point.owner != null && point.owner == team) {
				point.chargingTeam = team;
				point.progress = CAPTURE_SECONDS;
				return;
			}
			if (point.chargingTeam == null || point.chargingTeam != team) {
				point.chargingTeam = team;
				point.progress = 0;
			}
			point.progress++;
			showControlPoint(point);
			if (point.progress >= CAPTURE_SECONDS) {
				capture(point, obtenirEquip(team), challenger.getValue());
			} else {
				// A soft note climbing with each lamp, heard in the room only.
				world.playSound(point.centre(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.5F, 1.0F + 0.25F * point.progress);
			}
			return;
		}
		if (standing.size() > 1) return;
		boolean challenging = point.chargingTeam != null && (point.owner == null || !point.owner.equals(point.chargingTeam));
		if (!challenging) return;
		point.progress--;
		// The same note stepping back down as the challenge fades.
		world.playSound(point.centre(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.35F, 0.9F + 0.25F * Math.max(0, point.progress));
		if (point.progress <= 0) {
			point.chargingTeam = point.owner;
			point.progress = point.owner == null ? 0 : CAPTURE_SECONDS;
		}
		showControlPoint(point);
	}

	private void capture(ControlPoint point, Equip team, Player captor) {
		Equip previousOwner = point.owner == null ? null : obtenirEquip(point.owner);
		point.owner = team.getId();
		point.chargingTeam = team.getId();
		point.progress = CAPTURE_SECONDS;
		showControlPoint(point);
		donarOr(captor, GOLD_PER_CAPTURE);
		carregarPont(team, SQUARES_PER_CAPTURE);
		sendGlobalMessage(team.getChatColor() + captor.getName() + ChatColor.GRAY + " ha capturat el punt de control (" + pointsHeldBy(team) + "/" + controlPoints.size() + ") " + ChatColor.WHITE + "(" + ChatColor.GOLD + "+" + GOLD_PER_CAPTURE + ChatColor.WHITE + ")");
		// In the room a level-up chime; the capturing team hears a bell wherever they are, the team that lost it a low bass.
		world.playSound(point.centre(), Sound.ENTITY_PLAYER_LEVELUP, 0.6F, 1.5F);
		for (Player p : team.getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 0.5F, 1.2F);
		if (previousOwner != null && previousOwner != team) {
			for (Player p : previousOwner.getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.5F, 0.6F);
		}
	}

	/**
	 * The wall lamps show the channel: {@code progress} of them lit, counted from the
	 * charging team's side of the room. The side lamps and the tower show the owner.
	 */
	private void showControlPoint(ControlPoint point) {
		int n = point.wallLamps.size();
		int lit = point.chargingTeam == null ? 0 : Math.min(n, Math.round((float) point.progress * n / CAPTURE_SECONDS));
		boolean fromEast = point.chargingTeam != null && point.sideOfTeam(point.chargingTeam) > 0;
		for (int i = 0; i < n; i++) {
			int rank = fromEast ? n - 1 - i : i;
			setLit(point.wallLamps.get(i), rank < lit);
		}
		int ownerSide = point.owner == null ? 0 : point.sideOfTeam(point.owner);
		for (Block lamp : point.lamps) {
			int side = point.sideOf(lamp);
			setLit(lamp, point.owner != null && (side == 0 || side == ownerSide));
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

	/** The base lamps and the team's tower halves mirror the bar: with six lamps one per square, otherwise proportionally. */
	private void lightLamps(Equip e) {
		int charge = càrregaPont.getOrDefault(e.getId(), 0);
		lightBar(lampsByTeam.getOrDefault(e.getId(), List.of()), charge);
		lightBar(towerLampsByTeam.getOrDefault(e.getId(), List.of()), charge);
	}

	private static void lightBar(List<Block> lamps, int charge) {
		if (lamps.isEmpty()) return;
		int lit = charge * lamps.size() / PONT_CÀRREGA_MÀXIMA;
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
		giveOrDrop(plyr, new ItemStack(Material.GOLD_NUGGET, Or));
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
	/**
	 * Gold is nuggets and nothing else (Biel, 2026-09-08: "uncraft automatically, the number
	 * on the scoreboard should always match the amount of gold nuggets"): any ingot that gets
	 * into the inventory becomes ten nuggets, stacked to 64. It used to be the other way round.
	 */
	public void ajuntarOr(Player p) {
		Inventory inv = p.getInventory();
		int lingots = 0;
		for (ItemStack d : inv.getContents()) if (d != null && d.getType() == Material.GOLD_INGOT) lingots += d.getAmount();
		if (lingots == 0) return;
		inv.remove(Material.GOLD_INGOT);
		giveOrDrop(p, new ItemStack(Material.GOLD_NUGGET, lingots * 10));
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
		cancelStarCharge(killed);
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
		// The carried-item bounties count before the caps, so the ceiling is the 25 and 30 they name.
		if (player.getInventory().contains(Material.DIAMOND_PICKAXE)) {
			Or = Or + 1;
		}
		if (player.getInventory().contains(Material.GOLD_BLOCK)) {
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
		if (areAllies(player, killer)) {
			Or = 0;
		} else {
			if (!explotat) killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 3));
			carregarPont(obtenirEquip(killer), SQUARES_PER_KILL);
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
		Minion minionKiller = explotat || picDOr ? null : minionThatKilled(player);
		if (minionKiller instanceof SnowmanMinion ninot) {
			evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + " amb un " + ninot.noun() + " de " + ninot.kind().label + " (" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
		} else if (minionKiller instanceof LaneMinion skeleton) {
			int deaths = witherDeaths.merge(player.getUniqueId(), 1, Integer::sum);
			evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + " amb un " + skeleton.kind().label().toLowerCase() + " (" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")" + ChatColor.DARK_RED + " -1 cor màxim (" + deaths + ")");
		}
		if (Ability.hasAbility(plugin, this, player, AbilityType.CREEPER)) {
			float explopower = 0.8F + (mortsMort / 2);
			world.createExplosion(location.getX(), location.getY(), location.getZ(), explopower, false, false);
		}
		pTemp().EstablirPropietat(killer.getName() + "Morts", Integer.toString(mortsKiller + 1));
		pTemp().EstablirPropietat(player.getName() + "Morts", "0");
		if (Or != 0) {
			pPlayer(killer).IncrementarPropietat("Assassinats");
			Equip killerTeam = obtenirEquip(killer);
			if (killerTeam != null) {
				killsByTeam.merge(killerTeam.getId(), 1, Integer::sum);
				writeKillsSigns();
			}
			if (suddenDeath) raiseWitherSkeleton(killer, location);
		}
		pPlayer(player).IncrementarPropietat("Morts");
		if (player.getInventory().contains(Material.DIAMOND_PICKAXE)) {
			player.getInventory().remove(Material.DIAMOND_PICKAXE);
			evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + " que tenia pic de diamant!(" + ChatColor.GOLD + "+" + Or + ChatColor.WHITE + ")");
			killer.sendMessage(ChatColor.GOLD + "+3 Or passiu! (pic d'or)");
			killer.sendMessage(ChatColor.GOLD + "Matar un enemic amb el pic d'or et dona x3 or");
			inventory.addItem(Objecte.descriure(new ItemStack(Material.GOLDEN_PICKAXE, 1)));
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
		if (evt.getAction() == Action.PHYSICAL && evt.getClickedBlock() != null && teamOfDetonator(evt.getClickedBlock()) != null) {
			// The press itself is the trigger, the instant the foot lands; the tick is the fallback.
			evt.setCancelled(true);
			stepOnDetonator(plyr, evt.getClickedBlock());
			return;
		}
		if (evt.getAction() == Action.RIGHT_CLICK_BLOCK && evt.getClickedBlock() != null) {
			Block clicat = evt.getClickedBlock();
			if (clicat.getType() == Material.DISPENSER || clicat.getType() == Material.DROPPER) {
				// The 2013 currency dispensers: never fired, never opened.
				evt.setCancelled(true);
				return;
			}
			if (clicat.getType() == Material.ENCHANTING_TABLE || rètolsDeTaula.containsKey(clicat)) {
				evt.setCancelled(true);
				TaulaDEncantar taula = taulesDEncantar.get(rètolsDeTaula.getOrDefault(clicat, clicat));
				if (taula != null && evt.getHand() == EquipmentSlot.HAND) obrirTaulaDEncantar(plyr, taula);
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
			chargeStar(plyr);
		}
		if (stack.getType() == Material.ARROW) {
			if (stack.getEnchantments().size() >= 1) {
				for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
					p.setHealth(Math.max(0, p.getHealth() - 3));
				}
				plyr.sendMessage("-1 cor a tot l'equip enemic.");
				consumirUn(stack);
			}
		}
		if (stack.getType() == Material.MAGMA_CREAM) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.setFireTicks(3 * 20);
			}
			plyr.sendMessage("Has cremat a l'equip enemic.");
			consumirUn(stack);
		}
		if (stack.getType() == Material.STRING) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 15 * 20, 2, false), true);
			}
			plyr.sendMessage("Has alentit a l'equip enemic un 40% durant 15 segons.");
			consumirUn(stack);
		}
		if (stack.getType() == Material.SPIDER_EYE) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 8 * 20, 1, false), true);
			}
			plyr.sendMessage("Has enverinat a l'equip enemic durant 8 segons.");
			consumirUn(stack);
		}
		if (stack.getType() == Material.PAPER) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 25 * 20, 5, false), true);
			}
			plyr.sendMessage("Has esverat a l'equip enemic durant 25 segons.");
			consumirUn(stack);
		}
		if (stack.getType() == Material.COCOA_BEANS) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6 * 20, 100, false), true);
			}
			plyr.sendMessage("Tots a l'aigua!");
			consumirUn(stack);
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
			consumirUn(stack);
		}
		if (stack.getType() == Material.SUGAR) {
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2, false), true);
			}
			plyr.sendMessage("Has augmentat la velocitat del teu equip durant 15 segons.");
			consumirUn(stack);
		}
		if (stack.getType() == Material.GLASS) {
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 1, false), true);
			}
			plyr.sendMessage("El teu equip és invisible durant 15 segons.");
			consumirUn(stack);
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
				consumirUn(stack);
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
	 * a floor; a fourth one melts the thrower's oldest. Every snowball is the same item
	 * (Biel, 2026-09-07 night: "make them all spawn from the same snowball"): the snowman
	 * is of magma while the thrower's team holds the Guardian's last kill, of neu otherwise;
	 * an enchanted ball, chest loot only, makes it a hero. Quartz in the thrower's
	 * inventory at this moment makes the new snowman fire faster for the rest of its life.
	 */
	private void throwSnowman(Player thrower, ProjectileHitEvent evt, Location impact) {
		Equip team = obtenirEquip(thrower);
		if (!JocEnMarxa() || team == null) return;
		Location spot = snowmanSpotNear(evt, impact, thrower);
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
		SnowmanKind kind = team == guardianSlayerTeam ? SnowmanKind.MAGMA : SnowmanKind.NEU;
		boolean hero = evt.getEntity() instanceof Snowball ball && Objecte.de(ball.getItem()) == Objecte.BOLA_DE_NEU_ENCANTADA;
		int cooldown = thrower.getInventory().contains(Material.QUARTZ) ? (int) Math.round(kind.cooldownTicks * SNOWMAN_QUARTZ_COOLDOWN_FACTOR) : kind.cooldownTicks;
		SnowmanMinion snowman = new SnowmanMinion(this, team, thrower, kind, cooldown, hero, snowmanLane(team), this::snowballHit);
		enlist(snowman, spot);
		world.playSound(spot, Sound.ENTITY_SNOW_GOLEM_AMBIENT, 1F, 1F);
		world.playSound(spot, kind == SnowmanKind.MAGMA ? Sound.BLOCK_FIRE_AMBIENT : Sound.BLOCK_SNOW_PLACE, 1F, 1F);
		if (hero) {
			world.playSound(spot, Sound.ENTITY_PLAYER_LEVELUP, 1F, 1.5F);
			world.spawnParticle(Particle.END_ROD, spot.clone().add(0, 1, 0), 40, 0.4, 0.8, 0.4, 0.08);
		}
		PaperMessages.sendActionBar(thrower, ChatColor.WHITE + (hero ? "Superninot de " : "Ninot de ") + kind.label + " " + (mine.size() + 1) + "/" + MAX_SNOWMEN_PER_PLAYER, 60);
	}

	/**
	 * The team's lane, {@code lane<team>_N} in the map file from {@code _0} upward (laid in
	 * game with {@code /p lane0_0 x,y,z} and so on, read at every throw so a lane laid
	 * mid-match counts), else straight at the enemy spawn.
	 */
	private Lane snowmanLane(Equip team) {
		List<Location> waypoints = new ArrayList<>();
		for (Location waypoint : pMapaActual().ObtenirLocations("lane" + team.getId(), world)) waypoints.add(waypoint.add(0.5, 1, 0.5));
		if (waypoints.isEmpty()) waypoints.add(obtenirEquipEnemic(team).getTeamSpawnLocation());
		return Lane.of(waypoints);
	}

	/**
	 * The block the snowball stopped against, then the impact block and rings around it,
	 * then the thrower's own feet: a thrown ball always makes a snowman (Biel, 2026-09-07
	 * night: "s'ha fos should not happen").
	 */
	private static Location snowmanSpotNear(ProjectileHitEvent evt, Location impact, Player thrower) {
		if (evt.getHitBlock() != null && evt.getHitBlockFace() != null) {
			Block beside = evt.getHitBlock().getRelative(evt.getHitBlockFace());
			if (canStandIn(beside)) return beside.getLocation().add(0.5, 0, 0.5);
		}
		Location spot = standingSpotNear(impact);
		if (spot == null) spot = standingSpotNear(thrower.getLocation());
		return spot != null ? spot : thrower.getLocation();
	}

	private static final int STANDING_SPOT_SEARCH_RADIUS = 4;
	private static final int STANDING_SPOT_SEARCH_HEIGHT = 3;

	/** The nearest block around the location a mob can stand in, searched in growing rings up to the radius and a few blocks up and down; null when none. */
	private static Location standingSpotNear(Location around) {
		Block centre = around.getBlock();
		for (int ring = 0; ring <= STANDING_SPOT_SEARCH_RADIUS; ring++) {
			for (int dy = 0; dy <= STANDING_SPOT_SEARCH_HEIGHT; dy++) {
				for (int sign : dy == 0 ? new int[] {1} : new int[] {1, -1}) {
					for (int dx = -ring; dx <= ring; dx++) {
						for (int dz = -ring; dz <= ring; dz++) {
							if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) continue;
							Block candidate = centre.getRelative(dx, dy * sign, dz);
							if (canStandIn(candidate)) return candidate.getLocation().add(0.5, 0, 0.5);
						}
					}
				}
			}
		}
		return null;
	}

	private static boolean canStandIn(Block feet) {
		Block head = feet.getRelative(BlockFace.UP);
		return feet.isPassable() && !feet.isLiquid() && head.isPassable() && !head.isLiquid() && !feet.getRelative(BlockFace.DOWN).isPassable();
	}

	/**
	 * What a snowman's snowball does to an enemy (Biel, 2026-09-07: "rather than pure
	 * damage, slow them, or an ice cage after a few hits"): half a heart always. A snowman
	 * with its cage armed (ice on its head, three hits landed) spends it: the victim is shut
	 * in ice and the head goes back to the kind's. Otherwise the kind's effect, neu slows
	 * and magma sets on fire, and one more hit toward the cage. A victim just out of a cage
	 * is in grace: the kind's effect applies, but no cage charges or spends on them, so
	 * three snowmen cannot hold one player in a loop of cages. The hit counts as the
	 * owner's for kill credit (JocEquips records the last damager; the second is recorded
	 * here).
	 */
	private void snowballHit(SnowmanMinion snowman, EntityDamageByEntityEvent evt, Player victim) {
		evt.setDamage(SNOWBALL_DAMAGE);
		int now = segonsTranscorreguts();
		boolean inGrace = iceCageGraceUntil.getOrDefault(victim.getUniqueId(), 0) > now;
		if (snowman.cageArmed() && !inGrace) {
			snowman.dischargeCage();
			encaseInIce(victim, ICE_CAGE_TICKS);
			iceCageGraceUntil.put(victim.getUniqueId(), now + ICE_CAGE_TICKS / 20 + ICE_CAGE_GRACE_SECONDS);
			PaperMessages.sendActionBar(victim, ChatColor.AQUA + "Congelat!", 40);
		} else {
			shove(snowman, victim);
			if (snowman.kind() == SnowmanKind.MAGMA) {
				victim.setFireTicks(Math.max(victim.getFireTicks(), SNOWBALL_FIRE_TICKS));
				world.playSound(victim.getLocation(), Sound.ENTITY_GENERIC_BURN, 0.6F, 1.2F);
			}
			if (!inGrace) snowman.chargeCage(SNOWMAN_HITS_TO_ARM_CAGE);
		}
		segonsÚltimCopRebut.put(victim.getUniqueId(), now);
	}

	/** The snowball's own knockback is small; the snowman pushes its victim away from itself, a little upward, on the next tick so it lands after vanilla's. */
	private void shove(SnowmanMinion snowman, Player victim) {
		Mob body = snowman.mob();
		if (body == null) return;
		Vector away = victim.getLocation().toVector().subtract(body.getLocation().toVector()).setY(0);
		if (away.lengthSquared() < 0.01) return;
		Vector push = away.normalize().multiply(SNOWBALL_KNOCKBACK).setY(SNOWBALL_KNOCKBACK_LIFT);
		scheduleGameplayTask(() -> { if (victim.isOnline() && !victim.isDead()) victim.setVelocity(victim.getVelocity().add(push)); }, 1);
	}

	/** The minion that landed the killing hit, by its body or by its projectile, if one did. */
	private Minion minionThatKilled(Player victim) {
		if (!(victim.getLastDamageCause() instanceof EntityDamageByEntityEvent cause)) return null;
		Entity damager = cause.getDamager();
		Minion minion = minionOf(damager);
		if (minion == null && damager instanceof Projectile projectile && projectile.getShooter() instanceof Entity shooter) minion = minionOf(shooter);
		return minion;
	}

	//---------- The first minute: what a newcomer must know, on the screen and not in the chat ----------

	/** A title with the one sentence that wins, and a boss bar with it that drains over the first minute. */
	private void showObjective() {
		String sentence = GUIDE.line("barra objectiu");
		for (Player p : getPlayers()) PaperMessages.showTitle(p, 10, 80, 20, ChatColor.GOLD + getGameName(), ChatColor.WHITE + GUIDE.line("títol inici"));
		objectiveBar = BossBar.bossBar(PaperMessages.legacy(ChatColor.GOLD + sentence), 1F, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS);
		for (Player p : getPlayers()) p.showBossBar(objectiveBar);
		int[] left = {OBJECTIVE_BAR_SECONDS};
		objectiveBarTask = scheduleGameplayRepeatingTask(() -> {
			left[0]--;
			if (left[0] <= 0 || !JocEnMarxa()) {
				hideObjective();
				return;
			}
			objectiveBar.progress(left[0] / (float) OBJECTIVE_BAR_SECONDS);
		}, 20, 20);
	}

	private void hideObjective() {
		if (objectiveBarTask != -1) Bukkit.getScheduler().cancelTask(objectiveBarTask);
		objectiveBarTask = -1;
		if (objectiveBar != null && world != null) for (Player p : world.getPlayers()) p.hideBossBar(objectiveBar);
		objectiveBar = null;
	}

	//---------- The infernal star: a charge everyone hears, then a blast within its radius ----------

	/** The wind-up: the user glows and the whole map hears it; two seconds later the star goes off if the user still lives and holds it. */
	private void chargeStar(Player plyr) {
		if (starChargeTasks.containsKey(plyr.getUniqueId())) return;
		plyr.setGlowing(true);
		for (Player p : getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1F, 1.6F);
		sendGlobalMessage(ChatColor.GREEN + plyr.getName() + ChatColor.WHITE + " carrega una " + ChatColor.RED + "estrella infernal" + ChatColor.WHITE + "!");
		starChargeTasks.put(plyr.getUniqueId(), scheduleGameplayTask(() -> dischargeStar(plyr), NETHER_STAR_CHARGE_TICKS));
	}

	private void dischargeStar(Player plyr) {
		starChargeTasks.remove(plyr.getUniqueId());
		plyr.setGlowing(false);
		if (!JocEnMarxa() || !plyr.isOnline() || plyr.isDead() || obtenirEquip(plyr) == null) return;
		ItemStack star = null;
		for (ItemStack item : plyr.getInventory().getContents()) if (item != null && item.getType() == Material.NETHER_STAR) { star = item; break; }
		if (star == null) return;
		// Within its radius only: a star must not decide fights it was not part of.
		int struck = 0;
		for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
			if (p.getWorld() != plyr.getWorld() || p.getLocation().distance(plyr.getLocation()) > NETHER_STAR_RADIUS) continue;
			p.setHealth(1);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 20, 4, false), true);
			struck++;
		}
		for (Player p : obtenirEquip(plyr).getPlayers()) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 3, false), true);
		}
		world.playSound(plyr.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1.5F, 0.8F);
		world.spawnParticle(Particle.FLAME, plyr.getLocation().add(0, 1, 0), 120, 2, 1, 2, 0.05);
		sendGlobalMessage(ChatColor.GREEN + plyr.getName() + ChatColor.WHITE + " ha fet esclatar l'" + ChatColor.RED + "estrella infernal" + ChatColor.WHITE + " (" + struck + " enemics a prop)!");
		// The star turns into the charge that makes arrows explosive.
		star.setType(Material.FIREWORK_STAR);
		Objecte.descriure(star);
	}

	/** Dying, or leaving, while charging: the charge is lost and the star falls where the player stood. */
	private void cancelStarCharge(Player plyr) {
		Integer task = starChargeTasks.remove(plyr.getUniqueId());
		if (task == null) return;
		Bukkit.getScheduler().cancelTask(task);
		plyr.setGlowing(false);
		for (ItemStack item : plyr.getInventory().getContents()) {
			if (item == null || item.getType() != Material.NETHER_STAR) continue;
			consumirUn(item);
			world.dropItemNaturally(plyr.getLocation(), Objecte.ESTRELLA_DEL_NETHER.nou());
			break;
		}
	}

	//---------- Sudden death: wither skeletons from kills ----------

	private void warnSuddenDeath() {
		if (!JocEnMarxa()) return;
		sendGlobalMessage(ChatColor.DARK_RED + "Mort sobtada d'aquí a " + SUDDEN_DEATH_WARNING_SECONDS / 60 + " min: " + ChatColor.WHITE + "tothom quedarà a 1 cor, i cada kill aixecarà un esquelet wither.");
		for (Player p : getPlayers()) p.playSound(p.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 0.4F, 0.6F);
	}

	/**
	 * The match has run long (Biel, 2026-09-07 night: "leave everyone at 1 hp, as if a global
	 * nether star had been used, and who wins that battle is who spawns wither skeletons"):
	 * everyone drops to 1 hp, and from now on every kill raises a wither skeleton. Announced once.
	 */
	private void startSuddenDeath() {
		if (!JocEnMarxa()) return;
		suddenDeath = true;
		for (Player p : getPlayers()) {
			if (!p.isDead() && !isSpectator(p)) p.setHealth(1);
			PaperMessages.showTitle(p, 10, 70, 20, ChatColor.DARK_RED + "Mort sobtada", ChatColor.GRAY + "Tothom a 1 cor. Cada kill aixeca un esquelet wither");
			p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 0.5F, 1.2F);
		}
		sendGlobalMessage(ChatColor.DARK_RED + "Mort sobtada: " + ChatColor.WHITE + "tothom a 1 cor. A partir d'ara cada kill aixeca un esquelet wither al costat del mort, que marxa cap a la base enemiga i lluita per qui l'ha aixecat; morir per un esquelet costa 1 cor de vida màxima.");
		updateScoreBoards();
	}

	/** Three seconds at the start, one more every two minutes, twelve at most: at minute fifteen the dead wait long enough for a breach. */
	@Override
	protected int respawnWaitSeconds(Player p) {
		return Math.min(RESPAWN_WAIT_MAX_SECONDS, RESPAWN_WAIT_BASE_SECONDS + segonsTranscorreguts() / 60 / RESPAWN_WAIT_MINUTES_PER_EXTRA_SECOND);
	}

	/** A player who died to a wither skeleton comes back with less: one heart of max health per such death, never below two. */
	@Override
	protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
		super.onPlayerRespawnAfterTick(evt, p);
		int deaths = witherDeaths.getOrDefault(p.getUniqueId(), 0);
		double maxHealth = Math.max(MIN_MAX_HEALTH, FULL_MAX_HEALTH - WITHER_DEATH_MAX_HEALTH_LOSS * deaths);
		setMaxHealth(p, maxHealth);
		p.setHealth(maxHealth);
		if (deaths > 0) PaperMessages.sendActionBar(p, ChatColor.DARK_RED + "Un esquelet wither t'ha pres vida màxima: " + (int) (maxHealth / 2) + " cors", 80);
	}

	/**
	 * A wither skeleton rises at the killer's base, at the start of the team's lane (Biel,
	 * 2026-09-08: "només un per cada mort i a la meva base"), owned by the killer, and
	 * walks the whole lane to the enemy base.
	 */
	private void raiseWitherSkeleton(Player killer, Location whereTheVictimFell) {
		Equip team = obtenirEquip(killer);
		if (team == null) return;
		Lane lane = snowmanLane(team);
		Location spot = standingSpotNear(lane.waypoints().get(0));
		if (spot == null) spot = team.getTeamSpawnLocation();
		enlist(new LaneMinion(this, team, killer, WITHER_SKELETON, lane, this::witherSkeletonHit), spot);
		world.playSound(spot, Sound.ENTITY_WITHER_SKELETON_AMBIENT, 1F, 0.8F);
		world.spawnParticle(Particle.SOUL, spot.clone().add(0, 1, 0), 30, 0.3, 0.6, 0.3, 0.03);
		PaperMessages.sendActionBar(killer, ChatColor.DARK_GRAY + "Un esquelet wither s'aixeca a la teva base", 60);
	}

	/** A wither skeleton's blow: its attack damage through armour as vanilla does it, plus Wither I, so the victim cannot heal it away. */
	private void witherSkeletonHit(LaneMinion skeleton, EntityDamageByEntityEvent evt, Player victim) {
		victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, WITHER_SKELETON_WITHER_TICKS, 0, true, true));
		segonsÚltimCopRebut.put(victim.getUniqueId(), segonsTranscorreguts());
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
				treureUn(inv, Material.FIREWORK_STAR);
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
			// The gold you can spend now, not the gold earned so far: the shops count the inventory.
			list.add(ChatColor.GOLD + "Or: " + orDisponible(ply));
			if (pMapaActual().ExisteixPropietat("Golem")) {
				// Aqua only while it lives; grey and coarse while it is dead, so the line does not pull the eye.
				String estat = estatGuardià();
				lastGuardianCountdownShown = estat;
				list.add((guardiàViu() != null ? ChatColor.AQUA : ChatColor.GRAY) + "Guardià: " + estat);
			}
			Equip equip = obtenirEquip(ply);
			if (equip != null) list.add(etiquetaPont(equip, "Pont: ") + barraPont(equip));
			if (suddenDeath) list.add(ChatColor.DARK_RED + "Mort sobtada");
			// The 2015 ability counters are not shown: that layer is dead code until it is rebuilt on the skill pool.
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
