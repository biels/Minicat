package com.biel.lobby.mapes.jocs.obsidiandefenders;

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
import org.bukkit.event.EventPriority;
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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.jocs.obsidiandefenders.utils.GoldScore;
import com.biel.lobby.mapes.jocs.obsidiandefenders.utils.Interactions;
import com.biel.lobby.mapes.jocs.obsidiandefenders.utils.TeamUpgrades;
import com.biel.lobby.mapes.jocs.obsidiandefenders.ObsidianDefenders.Ability.AbilityType;
import com.biel.lobby.guide.GameGuide;
import com.biel.lobby.minions.Lane;
import com.biel.lobby.minions.LaneMinion;
import com.biel.lobby.minions.LaneMinionKind;
import com.biel.lobby.minions.Minion;
import com.biel.lobby.minions.SkeletonArcherMinion;
import com.biel.lobby.minions.SnowmanKind;
import com.biel.lobby.minions.SnowmanMinion;
import com.biel.lobby.utilities.InventoryTidy;
import com.biel.lobby.utilities.HologramFacade;
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
	private static final Vector DIAMOND_PICKAXE_2013 = new Vector(661, 42, -1398);
	private static final long CHEST_CYCLE_TICKS = 32 * 20;
	/** The first chest cycle waits this long, so no prize is announced before anyone has left the spawn (Biel, 2026-09-08). */
	private static final long FIRST_CHEST_CYCLE_TICKS = 15 * 20;
	private static final double RECALL_SECONDS = 3;
	/** Karen's speed potion lasts this long. */
	private static final int SPEED_POTION_TICKS = 3 * 60 * 20;
	/** The objective stays on a boss bar at the top of the screen this long after the start, draining, then goes (Biel, 2026-09-08: what a first-timer must know). */
	private static final int OBJECTIVE_BAR_SECONDS = 60;
	/** The recall clock's slot, which the inventory tidy never touches. */
	private static final int RECALL_SLOT = 8;
	private static final int MAX_OPEN_CHESTS = 8;
	private static final long FIRST_PICKAXE_TICKS = 3 * 60 * 20;
	private static final long PICKAXE_PERIOD_TICKS = 2 * 60 * 20;
	/** The Guardian wakes a minute in (Biel, 2026-09-07 night: "give the players a chance to get situated, buy things"); it was 5 s. */
	private static final long INITIAL_GUARDIAN_TICKS = 60 * 20;
	/** Every player starts with this much gold, so the first purchase happens in the first minute. */
	private static final int INITIAL_GOLD = 12;
	/**
	 * The nether star (Biel, 2026-09-07 night: epic for newbies, counterable for tryhards):
	 * its arrival in a chest is announced with a beam so both teams run for it; a right
	 * click charges it for two seconds, glowing and audible to the whole map, and then it
	 * strikes the enemies within its radius. It used to reach the whole map at once.
	 */
	private static final double NETHER_STAR_RADIUS = 30;
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
	 * kill raises a wither skeleton at the killer's base, owned by the killer, marching the
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
	private static final int GOLD_PER_GUARDIAN = 22;
	/** The iron golem is El Guardià: named, lit by an aura and documented in game (docs/games/obsidian-defenders/guardian-golem-design.md). */
	private static final String GUARDIAN_NAME = "El Guardià";
	private static final String GUARDIAN_TITLE = "El Guardià de l'obsidiana";
	/** Glacial cyan, deliberately neither team's colour: the blue team is navy. */
	private static final Color GUARDIAN_AURA_COLOR = Color.fromRGB(90, 200, 255);
	private static final Color ENRAGED_AURA_COLOR = Color.fromRGB(220, 245, 255);
	private static final double GUARDIAN_AURA_RADIUS = 1.2;
	private static final long GUARDIAN_AURA_PERIOD_TICKS = 5;
	private static final double ENRAGED_HEALTH_FRACTION = 0.3;
	/** Players this close to the Guardian see its boss bar: the hut and the canal in front of it, not the deck above. */
	private static final double GUARDIAN_BAR_DISTANCE = 12;
	/** The scoreboard countdown to the Guardian's return moves in steps of this many seconds, so the sidebar does not tick. */
	private static final int GUARDIAN_COUNTDOWN_STEP_SECONDS = 15;
	/** The beam over the lair starts on the deck block above the Golem property (nine blocks up on the 2013 map) and rises this far. */
	private static final int GUARDIAN_BEAM_BASE = 9;
	private static final int GUARDIAN_BEAM_HEIGHT = 10;
	private static final int GUARDIAN_BUZZ_INTERVAL_SECONDS = 4;
	/**
	 * How far from a team's spawn the base's TNT is looked for when the match starts.
	 * On the 2013 map each core is a cluster of about 23 TNT blocks 20 to 27 blocks
	 * behind the spawn at y 39 (x 589-597 for the red base, 729-737 for the blue one);
	 * the small five-block TNT crosses 45 blocks out are traps, not cores.
	 */
	private static final int CORE_RADIUS = 30;
	private static final int CORE_HEIGHT = 8;
	/** A primed TNT counts as a base core while it is this close to one of the TNT blocks found at start. */
	private static final double CORE_TOLERANCE = 3;
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
	private static final int LAST_HIT_CREDIT_SECONDS = 95;
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
	/**
	 * Prizes, the star and the enchanted snowball (Biel, 2026-09-08: "no two things at once,
	 * a way to reduce noise"): at most one per chest cycle, rolled once for the whole map, and
	 * a kind lands only while none of that kind is still lying unclaimed (a standing ball does
	 * not stop a star); the star gets this share of the rolls.
	 */
	private static final int PRIZE_CYCLE_CHANCE = 33;
	private static final int PRIZE_STAR_SHARE = 60;
	private static final String PICKAXE_FIREWORK_TAG = "minicat-pickaxe-burst";
	private record ShopPortal(Location entrance, Location arrival, BlockData originalBlock, HologramFacade.Handle label) {}
	private final Map<Integer, ShopPortal> shopPortals = new HashMap<>();
	private final Map<UUID, Integer> portalCooldownUntil = new HashMap<>();
	private record FlyingLoot(Item item, UUID collector) {}
	private final List<FlyingLoot> flyingChestLoot = new ArrayList<>();
	/** The team whose player last felled the Guardian throws magma snowmen until the other team fells it. */
	private Equip guardianSlayerTeam;
	/** Victim → game second until which snowballs neither charge nor spend a cage on them. */
	private final Map<UUID, Integer> iceCageGraceUntil = new HashMap<>();
	private boolean suddenDeath;
	/** Team id → kills by that team this match; the shop sign shows them and the catch-up rule reads them. */
	private final Map<Integer, Integer> killsByTeam = new HashMap<>();
	/** Team id → the sign by that team's shop that shows both teams' kills. */
	private final Map<Integer, Block> killsSigns = new HashMap<>();
	private Block lookoutSign;
	private BlockState originalLookoutBlock;
	private List<String> lastLookoutLines = List.of();
	private final GoldScore goldScore = new GoldScore();
	/** Player → the task that will discharge the star they are charging. */
	private final Map<UUID, Integer> starChargeTasks = new HashMap<>();
	/** Player → deaths to a wither skeleton this match. */
	private final Map<UUID, Integer> witherDeaths = new HashMap<>();
	/** Players who have bought their one quartz this match. */
	private final Set<UUID> quartzBuyers = new HashSet<>();
	/** Prizes already announced this match: the first landing of each also says what it does and where it is. */
	private final Set<GameItem> prizesAnnounced = new HashSet<>();
	/** Prize kind → the chest where one of that kind still lies unclaimed. */
	private final Map<GameItem, Block> prizeChests = new HashMap<>();

	/** The game's words, written once in guides/obsidian-defenders.md: start lines, tooltips, hints and the book; the numbers come from here. */
	private static final GameGuide GUIDE = GameGuide.of("Obsidian Defenders").withValues(guideValues());

	private static Map<String, String> guideValues() {
		Map<String, String> v = new HashMap<>();
		v.put("PIC_PRIMER_MIN", String.valueOf(FIRST_PICKAXE_TICKS / 20 / 60));
		v.put("PIC_PERIODE_MIN", String.valueOf(PICKAXE_PERIOD_TICKS / 20 / 60));
		v.put("MORT_BASE_S", String.valueOf(RESPAWN_WAIT_BASE_SECONDS));
		v.put("MORT_CADA_MIN", String.valueOf(RESPAWN_WAIT_MINUTES_PER_EXTRA_SECOND));
		v.put("MORT_MAX_S", String.valueOf(RESPAWN_WAIT_MAX_SECONDS));
		v.put("RECALL_S", String.valueOf((int) RECALL_SECONDS));
		v.put("CAPTURA_S", String.valueOf(CAPTURE_SECONDS));
		v.put("CAPTURA_OR", String.valueOf(GOLD_PER_CAPTURE));
		v.put("PUNT_QUADRE_S", String.valueOf(SECONDS_PER_POINT_SQUARE));
		v.put("PONT_QUADRES", String.valueOf(MAX_BRIDGE_CHARGE));
		v.put("PONT_S", String.valueOf(BRIDGE_DEPLOYED_TICKS / 20));
		v.put("GUARDIA_MIN", String.valueOf(INITIAL_GUARDIAN_TICKS / 20 / 60));
		v.put("GUARDIA_OR", String.valueOf(GOLD_PER_GUARDIAN));
		v.put("OR_INICIAL", String.valueOf(INITIAL_GOLD));
		v.put("COFRES_S", String.valueOf(CHEST_CYCLE_TICKS / 20));
		v.put("BLOC_OR_PASSIU", String.valueOf(GOLD_BLOCK_PASSIVE_GOLD));
		v.put("REMUNTADA_KILLS", String.valueOf(CATCH_UP_KILL_GAP));
		v.put("REMUNTADA_OR", String.valueOf(CATCH_UP_GOLD));
		v.put("MAX_NINOTS", String.valueOf(MAX_SNOWMEN_PER_PLAYER));
		v.put("GEL_S", String.valueOf(ICE_CAGE_TICKS / 20));
		v.put("GEL_COPS_ARMAR", String.valueOf(SNOWMAN_HITS_TO_ARM_CAGE));
		v.put("FOC_S", String.valueOf(SNOWBALL_FIRE_TICKS / 20));
		v.put("PREMI_PERCENT", String.valueOf(PRIZE_CYCLE_CHANCE));
		v.put("ESTRELLA_RADI", String.valueOf((int) NETHER_STAR_RADIUS));
		v.put("ESTRELLA_CARREGA_S", String.valueOf(NETHER_STAR_CHARGE_TICKS / 20));
		v.put("MORT_SOBTADA_MIN", String.valueOf(SUDDEN_DEATH_SECOND / 60));
		for (Merchandise merchandise : Merchandise.values()) v.put("PREU_" + merchandise.guideKey(), String.valueOf(merchandise.price));
		for (EnchantOffer offer : EnchantOffer.values()) v.put("ENCANT_" + offer.guideKey(), String.valueOf(offer.price(1)));
		return v;
	}

	boolean debug = false;
	/** Team id → block positions of the TNT that is that team's base core. */
	private final Map<Integer, Set<Vector>> coresByTeam = new HashMap<>();
	/** Detonator plate → the team whose vault it blows: the map's own, or one laid in a breach. */
	private final Map<Block, Integer> teamByDetonator = new HashMap<>();
	/** The team whose base has gone up; set once, so nothing blows twice. */
	private Equip explodedBase;
	private final Map<UUID, Integer> lastHitSecondByPlayer = new HashMap<>();
	private UUID currentGuardianId;
	private BossBar guardianBossBar;
	private BossBar objectiveBar;
	private int objectiveBarTask = -1;
	private int guardianAuraTask = -1;
	private int auraSteps = 0;
	private int guardianPresenceSeconds = 0;
	private boolean guardianEnraged = false;
	private boolean guardianAnnounced = false;
	/** Game second at which the Guardian (re)appears; read only while it is dead. */
	private int guardianReturnsAtSecond = 0;
	private String lastGuardianCountdownShown = "";
	private boolean firstPickaxeAnnounced = false;
	/** Team id → bridge charge, 0..MAX_BRIDGE_CHARGE. */
	private final Map<Integer, Integer> bridgeCharge = new HashMap<>();
	/** Team id → the sign above that team's bridge button, rewritten as its charge bar. */
	private final Map<Integer, Block> bridgeSigns = new HashMap<>();
	/** Bridge button block → team id that owns it. */
	private final Map<Block, Integer> bridgeButtons = new HashMap<>();
	private LauncherController watchtowerLaunchers;
	private TeamUpgrades teamUpgrades;
	private UpgradeController upgradeSigns;
	private record CombatCredit(UUID ownerId, Minion minion, int second) {}
	private final Map<UUID, CombatCredit> combatCredits = new HashMap<>();
	/** Team id → the plank columns of the bridge over that team's moat, deploy order. */
	private final Map<Integer, List<List<Block>>> bridgesByMoat = new HashMap<>();
	private final Set<Integer> deployedBridges = new HashSet<>();
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
			return Integer.signum(block.getX() - center().getBlockX());
		}

		int sideOfTeam(int team) {
			Block plate = plateByTeam.get(team);
			return plate == null ? 0 : sideOf(plate);
		}

		Integer teamOfPlate(Block block) {
			for (Map.Entry<Integer, Block> plate : plateByTeam.entrySet()) if (plate.getValue().equals(block)) return plate.getKey();
			return null;
		}

		Location center() {
			Location sum = null;
			for (Block plate : plateByTeam.values()) sum = sum == null ? plate.getLocation() : sum.add(plate.getLocation());
			return sum.multiply(1.0 / plateByTeam.size());
		}
	}

	private static final int MAX_BRIDGE_CHARGE = 6;
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
	private static final long BRIDGE_TICKS_PER_COLUMN = 10;
	private static final long BRIDGE_DEPLOYED_TICKS = 45 * 20;
	private static final int BRIDGE_HEIGHT = 37;
	/** A block over the middle of each moat's gap on the 2013 map, red then blue; a PontMoat<n> property overrides it. */
	private static final Vector[] BRIDGE_MOAT_2013 = { new Vector(644, 37, -1426), new Vector(682, 37, -1374) };
	private static final String FULL_SQUARE = "\u25A0";
	private static final String EMPTY_SQUARE = "\u25A1";
	/** The control room is 50 blocks from the spawn on the 2013 map, the farthest booth 46. */
	private static final int SIGN_RADIUS = 60;

	/** The three booths of each base, from the spawn doors outward, and what each sells. */
	private enum Booth {
		GERRY("weapons", "Gerry", "armes", Merchandise.ARROWS, Merchandise.FIREWORK_STARS, Merchandise.IRON_SWORD, Merchandise.BOW, Merchandise.IRON_PICKAXE, Merchandise.DIAMOND_SWORD),
		SEON("armors", "Seon", "armadures", Merchandise.IRON_CHESTPLATE, Merchandise.DIAMOND_LEGGINGS),
		KAREN("potions", "Karen", "altres coses", Merchandise.GOLD_BLOCK, Merchandise.SNOWBALL, Merchandise.QUARTZ, Merchandise.ENDER_PEARL, Merchandise.GOLDEN_APPLE, Merchandise.SPEED_POTION, Merchandise.EMERALD);

		final String originalSignText;
		final String name;
		final String profession;
		final List<Merchandise> merchandise;
		Booth(String originalSignText, String name, String profession, Merchandise... merchandise) {
			this.originalSignText = originalSignText;
			this.name = name;
			this.profession = profession;
			this.merchandise = List.of(merchandise);
		}
	}

	/** What the booths sell. Prices in gold nuggets; an ingot pays for ten. */
	private enum Merchandise {
		ARROWS(Material.ARROW, 8, 3, "8 fletxes", null),
		FIREWORK_STARS(Material.FIREWORK_STAR, 2, 10, "2 estrelles de foc", null),
		IRON_SWORD(Material.IRON_SWORD, 1, 10, "Espasa de ferro", null),
		BOW(Material.BOW, 1, 12, "Arc", null),
		IRON_PICKAXE(Material.IRON_PICKAXE, 1, 12, "Pic de ferro", "+30 dany al golem"),
		IRON_CHESTPLATE(Material.IRON_CHESTPLATE, 1, 18, "Pitral de ferro", null),
		GOLD_BLOCK(Material.GOLD_BLOCK, 1, 20, "Bloc d'or", "+" + GOLD_BLOCK_PASSIVE_GOLD + " or cada " + (CHEST_CYCLE_TICKS / 20) + " s: es paga sol en 3,5 min"),
		SNOWBALL(Material.SNOWBALL, 1, 6, "Bola de neu", null),
		QUARTZ(Material.QUARTZ, 1, 15, "Quars", "Mentre el portis, els teus nous ninots disparen un 50 % més ràpid"),
		ENDER_PEARL(Material.ENDER_PEARL, 1, 15, "Perla d'Ender", null),
		GOLDEN_APPLE(Material.GOLDEN_APPLE, 1, 10, "Poma daurada", null),
		SPEED_POTION(Material.POTION, 1, 12, "Poció de velocitat", null),
		EMERALD(Material.EMERALD, 1, 8, "Maragda", null),
		DIAMOND_LEGGINGS(Material.DIAMOND_LEGGINGS, 1, 30, "Calces de diamant", null),
		DIAMOND_SWORD(Material.DIAMOND_SWORD, 1, 40, "Espasa de diamant", null);

		final Material material;
		final int amount;
		final int price;
		final String name;
		final String description;
		/** The stack handed over: the speed potion is three minutes of Speed I, the rest the bare item. */
		ItemStack stack() {
			ItemStack item = new ItemStack(material, amount);
			if (this == SPEED_POTION && item.getItemMeta() instanceof PotionMeta meta) {
				meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, SPEED_POTION_TICKS, 0), true);
				meta.setColor(Color.fromRGB(120, 200, 255));
				item.setItemMeta(meta);
			}
			return item;
		}
		Merchandise(Material material, int amount, int price, String name, String description) {
			this.material = material;
			this.amount = amount;
			this.price = price;
			this.name = name;
			this.description = description;
		}

		String guideKey() {
			return switch (this) {
				case ARROWS -> "FLETXES";
				case FIREWORK_STARS -> "ESTRELLES";
				case IRON_SWORD -> "ESPASA_FERRO";
				case BOW -> "ARC";
				case IRON_PICKAXE -> "PIC_FERRO";
				case IRON_CHESTPLATE -> "PITRAL_FERRO";
				case GOLD_BLOCK -> "BLOC_OR";
				case SNOWBALL -> "BOLA_DE_NEU";
				case QUARTZ -> "QUARS";
				case ENDER_PEARL -> "PERLA_D_ENDER";
				case GOLDEN_APPLE -> "POMA_DAURADA";
				case SPEED_POTION -> "POCIO_DE_VELOCITAT";
				case EMERALD -> "MARAGDA";
				case DIAMOND_LEGGINGS -> "CALCES_DIAMANT";
				case DIAMOND_SWORD -> "ESPASA_DIAMANT";
			};
		}
	}

	/**
	 * The items the game hands out, named and explained on their tooltip from one place:
	 * chest loot, booth purchases, the pickaxes and the star an arrow needs. The tooltip
	 * is the quietest channel there is: read only when hovered, never repeated.
	 */
	private enum GameItem {
		NETHER_STAR(Material.NETHER_STAR, "Estrella infernal"),
		FIREWORK_STAR(Material.FIREWORK_STAR, "Estrella de foc"),
		MAGMA_CREAM(Material.MAGMA_CREAM, "Crema de magma"),
		EMERALD(Material.EMERALD, "Maragda"),
		SNOWBALL(Material.SNOWBALL, "Bola de neu"),
		ENCHANTED_SNOWBALL(Material.SNOWBALL, true, "Bola de neu encantada"),
		ENDER_PEARL(Material.ENDER_PEARL, "Perla d'Ender"),
		GOLDEN_APPLE(Material.GOLDEN_APPLE, "Poma daurada"),
		SPEED_POTION(Material.POTION, "Poció de velocitat"),
		GOLDEN_SWORD(Material.GOLDEN_SWORD, "Espasa d'or"),
		DIAMOND_PICKAXE(Material.DIAMOND_PICKAXE, "Pic de diamant"),
		GOLDEN_PICKAXE(Material.GOLDEN_PICKAXE, "Pic d'or");

		final Material material;
		/** Shown with the enchantment glint; the glint is what tells it from the plain item of the same material. */
		final boolean glinting;
		final String name;
		GameItem(Material material, String name) {
			this(material, false, name);
		}

		GameItem(Material material, boolean glinting, String name) {
			this.material = material;
			this.glinting = glinting;
			this.name = name;
		}

		String guideKey() {
			return switch (this) {
				case NETHER_STAR -> "ESTRELLA_DEL_NETHER";
				case FIREWORK_STAR -> "ESTRELLA_DE_FOC";
				case MAGMA_CREAM -> "CREMA_DE_MAGMA";
				case EMERALD -> "MARAGDA";
				case SNOWBALL -> "BOLA_DE_NEU";
				case ENCHANTED_SNOWBALL -> "BOLA_DE_NEU_ENCANTADA";
				case ENDER_PEARL -> "PERLA_D_ENDER";
				case GOLDEN_APPLE -> "POMA_DAURADA";
				case SPEED_POTION -> "POCIO_DE_VELOCITAT";
				case GOLDEN_SWORD -> "ESPASA_D_OR";
				case DIAMOND_PICKAXE -> "PIC_DE_DIAMANT";
				case GOLDEN_PICKAXE -> "PIC_D_OR";
			};
		}

		/** The tooltip, from the guide's stable {@code objecte NAME} section. */
		List<String> lore() {
			return GUIDE.lines("objecte " + guideKey());
		}

		static GameItem from(Material material) {
			return from(material, false);
		}

		static GameItem from(ItemStack item) {
			if (item == null) return null;
			boolean glinting = item.hasItemMeta() && item.getItemMeta().hasEnchantmentGlintOverride() && item.getItemMeta().getEnchantmentGlintOverride();
			return from(item.getType(), glinting);
		}

		static GameItem from(Material material, boolean glinting) {
			for (GameItem o : values()) if (o.material == material && o.glinting == glinting) return o;
			return null;
		}

		/** A fresh stack of one, named. */
		ItemStack newItem() {
			return describe(new ItemStack(material), this);
		}

		/** Names and explains the item when the game has words for it; returns the same stack. */
		static ItemStack describe(ItemStack item) {
			return describe(item, from(item));
		}

		static ItemStack describe(ItemStack item, GameItem gameItem) {
			if (gameItem == null) return item;
			ItemMeta meta = item.getItemMeta();
			if (gameItem.glinting) meta.setEnchantmentGlintOverride(true);
			meta.displayName(Component.text(gameItem.name, NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
			List<Component> lore = new ArrayList<>();
			for (String line : gameItem.lore()) lore.add(Component.text(line, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
			meta.lore(lore);
			item.setItemMeta(meta);
			return item;
		}
	}

	/** Spends one of the stack in hand. Named items do not match a bare ItemStack, so the stack itself is shrunk. */
	private static void consumeOne(ItemStack stack) {
		stack.setAmount(stack.getAmount() - 1);
	}

	private static void removeOne(Inventory inv, Material material) {
		for (ItemStack item : inv.getContents()) {
			if (item == null || item.getType() != material) continue;
			item.setAmount(item.getAmount() - 1);
			return;
		}
	}

	private record TeamBooth(Booth booth, Equip team) {}
	private final Map<UUID, TeamBooth> shopkeepers = new HashMap<>();

	//---------- The enchanting tables ----------

	/** Where an enchanting table stands: in a base, or on the canopy altars at the top of the jungle. */
	private enum EnchantingSite {
		BASE(ChatColor.GOLD + "Taula d'encantar"),
		CANOPY(ChatColor.LIGHT_PURPLE + "Altar de la capçada");

		final String title;
		EnchantingSite(String title) {
			this.title = title;
		}
	}

	/**
	 * What the tables sell, paid in gold, no lapis and no experience. A base table offers
	 * the simple line up to its base level; the canopy altars, a long climb away from the
	 * fight, offer every line up to its canopy level. Each purchase is the next level of
	 * what the item already has, at the line's price times that level.
	 */
	private enum EnchantOffer {
		KNOCKBACK("Retrocés", Enchantment.KNOCKBACK, "L'espasa empeny l'enemic en colpejar-lo", 1, 2, 10),
		PUNCH("Empenta", Enchantment.PUNCH, "Les fletxes empenyen l'enemic", 1, 2, 10),
		FLAME("Flama", Enchantment.FLAME, "Les fletxes encenen l'enemic", 1, 1, 15),
		EFFICIENCY("Eficiència", Enchantment.EFFICIENCY, "Trenca l'obsidiana més de pressa", 1, 3, 15),
		SHARPNESS("Esmolat", Enchantment.SHARPNESS, "Més dany amb l'espasa", 0, 3, 20),
		POWER("Potència", Enchantment.POWER, "Més dany amb l'arc", 0, 3, 20),
		FIRE_ASPECT("Aspecte de foc", Enchantment.FIRE_ASPECT, "L'espasa encén l'enemic", 0, 1, 25),
		PROTECTION("Protecció", Enchantment.PROTECTION, "Menys dany rebut", 0, 2, 20);

		final String name;
		final Enchantment enchantment;
		final String description;
		final int maxBaseLevel;
		final int maxCanopyLevel;
		final int pricePerLevel;
		EnchantOffer(String name, Enchantment enchantment, String description, int maxBaseLevel, int maxCanopyLevel, int pricePerLevel) {
			this.name = name;
			this.enchantment = enchantment;
			this.description = description;
			this.maxBaseLevel = maxBaseLevel;
			this.maxCanopyLevel = maxCanopyLevel;
			this.pricePerLevel = pricePerLevel;
		}

		int maxLevel(EnchantingSite enchantingSite) {
			return enchantingSite == EnchantingSite.BASE ? maxBaseLevel : maxCanopyLevel;
		}

		/** The level this table would put on the item, or 0 when the item cannot take it or has all this table gives. */
		int nextLevel(EnchantingSite enchantingSite, ItemStack item) {
			if (item == null || item.getType() == Material.AIR || !enchantment.canEnchantItem(item)) return 0;
			int next = item.getEnchantmentLevel(enchantment) + 1;
			return next <= maxLevel(enchantingSite) ? next : 0;
		}

		int price(int level) {
			return pricePerLevel * level;
		}

		String guideKey() {
			return switch (this) {
				case KNOCKBACK -> "RETROCÉS";
				case PUNCH -> "EMPENTA";
				case FLAME -> "FLAMA";
				case EFFICIENCY -> "EFICIÈNCIA";
				case SHARPNESS -> "ESMOLAT";
				case POWER -> "POTÈNCIA";
				case FIRE_ASPECT -> "ASPECTE_DE_FOC";
				case PROTECTION -> "PROTECCIÓ";
			};
		}
	}

	/** An enchanting table of the map and, for the ones in a base, the team whose base it is. */
	private record EnchantingTableRegistration(EnchantingSite enchantingSite, Integer team) {}
	private final Map<Block, EnchantingTableRegistration> enchantingTables = new HashMap<>();
	/** The sign the plugin stands on each table → the table; a click on the sign is a click on the table. */
	private final Map<Block, Block> tableSigns = new HashMap<>();
	/** Player → the one-shot hints already shown: a line above the hotbar the first time they come near a thing. */
	private final Map<UUID, Set<String>> hintsShown = new HashMap<>();
	private static final double HINT_DISTANCE = 4;
	private static final long HINT_TICKS = 80;
	/** A base's tables stand at the base's level within the sign radius; anything higher is the canopy. */
	private static final int BASE_TABLE_HEIGHT = 3;
	private static final String[] ROMAN_LEVELS = { "", "I", "II", "III", "IV", "V" };

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
		registerCores();
		registerControlsAndBooths();
		registerEnchantingTables();
		registerBridges();
		registerControlPointsAndLamps();
		scheduleGameplayTask(this::verifyRegistrations, REGISTRATION_CHECK_TICKS);
		Bukkit.getPluginManager().registerEvents(worldListener, plugin);
		teamUpgrades = new TeamUpgrades();
		watchtowerLaunchers = new LauncherController(world, plugin,
				player -> JocEnMarxa() && getPlayers().contains(player) && !isSpectator(player)
						&& player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR,
				player -> obtenirEquip(player) == null ? -1 : obtenirEquip(player).getId(),
				teamUpgrades);
		upgradeSigns = new UpgradeController(world, teamUpgrades, watchtowerLaunchers,
				player -> JocEnMarxa() && getPlayers().contains(player) && !isSpectator(player)
						&& player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR,
				player -> obtenirEquip(player) == null ? -1 : obtenirEquip(player).getId(),
				this::spendGold, this::updateScoreBoard);
		scheduleGameplayRepeatingTask(() -> {
			if (watchtowerLaunchers != null) watchtowerLaunchers.tick();
			if (upgradeSigns != null) upgradeSigns.tick();
		}, 1, 1);
		createShopPortals();
		scheduleGameplayRepeatingTask(this::tickShopPortals, 2, 2);
		scheduleGameplayRepeatingTask(this::tickChestLoot, 1, 1);
		showObjective();
		emptyDispensers();
		scheduleGameplayRepeatingTask(this::cycleChests, FIRST_CHEST_CYCLE_TICKS, CHEST_CYCLE_TICKS);
		scheduleGameplayRepeatingTask(this::tickControlPoints, 20, 20);
		scheduleGameplayRepeatingTask(this::tickDetonators, 20, DETONATOR_TICK_PERIOD);
		scheduleGameplayRepeatingTask(this::tickHints, 30, 20);
		scheduleGameplayRepeatingTask(this::spawnDiamondPickaxe, FIRST_PICKAXE_TICKS, PICKAXE_PERIOD_TICKS);
		guardianReturnsAtSecond = (int) (INITIAL_GUARDIAN_TICKS / 20);
		guardianSlayerTeam = null;
		scheduleGameplayTask(this::spawnGuardian, INITIAL_GUARDIAN_TICKS);
		suddenDeath = false;
		killsByTeam.clear();
		goldScore.clear();
		createLookoutSign();
		scheduleGameplayRepeatingTask(this::updateLookoutSign, 20, 20);
		witherDeaths.clear();
		quartzBuyers.clear();
		prizesAnnounced.clear();
		prizeChests.clear();
		scheduleGameplayTask(this::warnSuddenDeath, (SUDDEN_DEATH_SECOND - SUDDEN_DEATH_WARNING_SECONDS) * 20L);
		scheduleGameplayTask(this::startSuddenDeath, SUDDEN_DEATH_SECOND * 20L);
		scheduleGameplayRepeatingTask(this::trackGuardianPresence, 20, 20);
	}

	@Override
	protected ArrayList<Equip> getDesiredTeams() {
		ArrayList<Equip> teams = new ArrayList<>();
		teams.add(new Equip(DyeColor.RED, "vermell")); //Id 0
		teams.add(new Equip(DyeColor.BLUE, "blau")); //Id 1
		return teams;
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
		items.add(new ItemStack(Material.COOKED_BEEF, 8));
		return items;
	}

	@Override
	protected void donarEfectesInicials(Player ply) {
		ply.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 25 * 20, 2, false), true);
		setMaxHealth(ply, FULL_MAX_HEALTH);
		giveGold(ply, INITIAL_GOLD);
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
		// Three lines; the rest is taught where it happens: item tooltips, signs, the one-shot hints, and the book (/info).
		return new ArrayList<>(GUIDE.lines("inici"));
	}

	//---------- Base cores and the win ----------

	/** Finds each team's TNT around its spawn. A base without TNT cannot be lost, and says so. */
	private void registerCores() {
		coresByTeam.clear();
		for (Equip e : Equips) {
			Location base = e.getTeamSpawnLocation();
			Set<Vector> cores = new HashSet<>();
			double minimumDistance = Double.MAX_VALUE;
			for (int x = -CORE_RADIUS; x <= CORE_RADIUS; x++) {
				for (int y = -CORE_HEIGHT; y <= CORE_HEIGHT; y++) {
					for (int z = -CORE_RADIUS; z <= CORE_RADIUS; z++) {
						Block b = base.getBlock().getRelative(x, y, z);
						if (b.getType() != Material.TNT) continue;
						cores.add(b.getLocation().toVector());
						minimumDistance = Math.min(minimumDistance, b.getLocation().distance(base));
					}
				}
			}
			coresByTeam.put(e.getId(), cores);
			if (cores.isEmpty()) {
				plugin.getLogger().warning(getGameName() + " " + getMapName() + ": no TNT within " + CORE_RADIUS + " blocks of base" + e.getId() + "; that base cannot be blown up");
				sendGlobalMessage(ChatColor.RED + "No s'ha trobat TNT a la base " + e.getAdjectiuColored() + ChatColor.RED + ": aquesta base no pot explotar.");
			} else {
				plugin.getLogger().info(getGameName() + " " + getMapName() + ": base" + e.getId() + " has " + cores.size() + " TNT blocks, the nearest " + Math.round(minimumDistance) + " blocks from the spawn");
			}
			layMapDetonator(e);
		}
	}

	private Equip teamOfCore(Location explosion) {
		Vector point = explosion.toVector();
		for (Equip e : Equips) {
			for (Vector core : coresByTeam.getOrDefault(e.getId(), Set.of())) {
				if (core.clone().add(new Vector(0.5, 0.5, 0.5)).distance(point) <= CORE_TOLERANCE) return e;
			}
		}
		return null;
	}

	/** The middle of a team's TNT, at the height of the blocks' centres; null for a base without TNT. */
	private Location coreCentre(Equip e) {
		Set<Vector> core = coresByTeam.getOrDefault(e.getId(), Set.of());
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
			for (Vector tnt : coresByTeam.getOrDefault(e.getId(), Set.of())) {
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
		Equip exploded = teamOfCore(evt.getEntity().getLocation());
		if (exploded == null) return;
		blowUpBase(exploded, null);
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
		for (Vector position : coresByTeam.getOrDefault(vaultOf.getId(), Set.of())) {
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
	private void cycleChests() {
		if (!JocEnMarxa()) return;
		ArrayList<Location> points = pMapaActual().ObtenirLocations("cofres", world);
		for (Location point : points) closeChest(point.getBlock());
		if (!points.isEmpty()) {
			Set<Block> opened = new HashSet<>();
			int passes = 0;
			while (opened.size() < MAX_OPEN_CHESTS && passes++ < 200) {
				for (Location point : points) {
					if (opened.size() >= MAX_OPEN_CHESTS) break;
					Block b = point.getBlock();
					if (opened.contains(b) || !Utils.Possibilitat(10)) continue;
					openChest(b);
					opened.add(b);
				}
			}
			rollPrize(new ArrayList<>(opened));
		}
		for (Player p : getPlayers()) {
			givePassiveGold(p);
			wearEquipment(p);
		}
		if (++chestCycles % CYCLES_PER_DEFAULT_SQUARE == 0) for (Equip e : Equips) chargeBridge(e, 1);
	}

	private void closeChest(Block b) {
		if (b.getState() instanceof Chest chest) chest.getInventory().clear();
		BlockData leafData = Material.JUNGLE_LEAVES.createBlockData();
		if (leafData instanceof Leaves leaves) leaves.setPersistent(true);
		b.setBlockData(leafData);
	}

	private void openChest(Block b) {
		b.setType(Material.CHEST);
		if (!(b.getState() instanceof Chest chest)) return;
		Inventory inv = chest.getInventory();
		inv.clear();
		for (ItemStack loot : chestLoot()) {
			int slot = Utils.NombreEntre(0, inv.getSize() - 1);
			if (inv.getItem(slot) == null) inv.setItem(slot, loot); else inv.addItem(loot);
		}
	}

	/** One roll per cycle for the whole map, and none while a prize still lies unclaimed: the star or the enchanted ball into one of the chests just opened. */
	private void rollPrize(List<Block> opened) {
		if (opened.isEmpty() || !Utils.Possibilitat(PRIZE_CYCLE_CHANCE)) return;
		boolean star = Utils.Possibilitat(PRIZE_STAR_SHARE);
		GameItem prize = star ? GameItem.NETHER_STAR : GameItem.ENCHANTED_SNOWBALL;
		if (prizeStanding(prize)) return;
		Block chest = opened.get(Utils.NombreEntre(0, opened.size() - 1));
		if (!(chest.getState() instanceof Chest open)) return;
		open.getInventory().addItem(prize.newItem());
		prizeChests.put(prize, chest);
		if (star) announceLoot(chest, prize, Particle.FLAME, Color.fromRGB(255, 60, 30), ChatColor.RED + "Una estrella infernal" + ChatColor.WHITE + " ha aparegut a la jungla!");
		else announceLoot(chest, prize, Particle.END_ROD, Color.fromRGB(120, 220, 255), ChatColor.AQUA + "Una bola de neu encantada" + ChatColor.WHITE + " ha aparegut a la jungla!");
	}

	private boolean prizeStanding(GameItem prize) {
		Block chest = prizeChests.get(prize);
		return chest != null && chest.getState() instanceof Chest open && holdsPrize(open.getInventory(), prize);
	}

	/**
	 * Randomise where and when, never the outcome (Biel, 2026-09-07 night): a prize in a
	 * chest is announced to everyone and a beam stands over the chest until the prize is
	 * taken or the chest closes, so both teams can run for it.
	 */
	private void announceLoot(Block chest, GameItem prize, Particle beam, Color colour, String announcement) {
		sendGlobalMessage(announcement);
		// The first time a prize lands in a match, one more line: what it does and where to look (Biel, 2026-09-08).
		if (prizesAnnounced.add(prize)) for (String line : GUIDE.lines("anunci " + prize.guideKey())) sendGlobalMessage(ChatColor.GRAY + line);
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

	private static boolean holdsPrize(Inventory inv, GameItem prize) {
		for (ItemStack item : inv.getContents()) if (item != null && GameItem.from(item) == prize) return true;
		return false;
	}

	/** The 2013 loot table, one roll per line. */
	private ArrayList<ItemStack> chestLoot() {
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
		if (Utils.Possibilitat(8)) loot.add(new ItemStack(Material.EXPERIENCE_BOTTLE, Utils.NombreEntre(1, 3)));
		if (Utils.Possibilitat(5)) loot.add(new ItemStack(Material.ENDER_PEARL));
		if (Utils.Possibilitat(8)) loot.add(new ItemStack(Material.ARROW, 4));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.GOLDEN_SWORD));
		if (Utils.Possibilitat(6)) loot.add(new ItemStack(Material.IRON_SWORD));
		// No anvil on the map and the tables sell enchantments, so no books (JoniMega, 2026-09-07): nuggets instead.
		if (Utils.Possibilitat(15)) loot.add(new ItemStack(Material.GOLD_NUGGET, 3));
		loot.replaceAll(GameItem::describe);
		return loot;
	}

	/** One nugget per cycle, more for holding a gold block or the gold pickaxe, and a bonus for both. */
	private void givePassiveGold(Player p) {
		int gold = 1;
		Inventory inv = p.getInventory();
		if (inv.contains(Material.GOLD_BLOCK)) {
			sendPlayerMessage(p, ChatColor.GRAY + "Bloc d'or --> +" + GOLD_BLOCK_PASSIVE_GOLD + " Or passiu");
			gold += GOLD_BLOCK_PASSIVE_GOLD;
		}
		Equip team = obtenirEquip(p);
		if (team != null && killsBehind(team) >= CATCH_UP_KILL_GAP) {
			sendPlayerMessage(p, ChatColor.GRAY + "Remuntada --> +" + CATCH_UP_GOLD + " Or passiu");
			gold += CATCH_UP_GOLD;
		}
		if (inv.contains(Material.GOLDEN_PICKAXE)) {
			sendPlayerMessage(p, ChatColor.GRAY + "Pic d'or --> +3 Or passiu");
			gold += 3;
		}
		if (gold >= 5) {
			int extra = Utils.NombreEntre(1, 4);
			gold += extra;
			sendPlayerMessage(p, ChatColor.GRAY + "Combinació --> +" + extra + " Or passiu");
		}
		giveGold(p, gold);
	}

	private void wearEquipment(Player p) {
		for (ItemStack item : p.getInventory().getContents()) wearDown(item);
		for (ItemStack item : p.getInventory().getArmorContents()) wearDown(item);
	}

	private void wearDown(ItemStack item) {
		if (item == null) return;
		int wearAmount = wearPerCycle(item);
		if (wearAmount == 0) return;
		if (item.getItemMeta() instanceof Damageable meta) {
			meta.setDamage(meta.getDamage() + wearAmount);
			item.setItemMeta(meta);
		}
	}

	/** Wear per chest cycle. An untouched or enchanted item does not wear, as in 2013. */
	private int wearPerCycle(ItemStack item) {
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

	private Location diamondPickaxePoint() {
		if (pMapaActual().ExisteixPropietat("PicDiamant")) {
			return pMapaActual().ObtenirLocation("PicDiamant", world).add(0.5, 1, 0.5);
		}
		return DIAMOND_PICKAXE_2013.toLocation(world).add(0.5, 0, 0.5);
	}

	/** A pickaxe good for exactly one block of obsidian, dropped in the middle of the map. */
	private void spawnDiamondPickaxe() {
		if (!JocEnMarxa()) return;
		ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
		if (pickaxe.getItemMeta() instanceof Damageable meta) {
			meta.setDamage(Material.DIAMOND_PICKAXE.getMaxDurability() - 1);
			pickaxe.setItemMeta(meta);
		}
		Location spawn = diamondPickaxePoint();
		clearPickaxeSpawn(spawn);
		world.dropItem(spawn, GameItem.describe(pickaxe), item -> {
			item.setVelocity(new Vector());
			item.setPickupDelay(Interactions.PICKAXE_PICKUP_DELAY);
		});
		Firework burst = world.spawn(spawn, Firework.class, firework -> {
			firework.addScoreboardTag(PICKAXE_FIREWORK_TAG);
			FireworkMeta meta = firework.getFireworkMeta();
			meta.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL)
					.withColor(Color.AQUA).trail(false).flicker(false).build());
			meta.setPower(0);
			firework.setFireworkMeta(meta);
			firework.setVelocity(new Vector());
			firework.setGravity(false);
		});
		burst.detonate();
		if (!firstPickaxeAnnounced) {
			sendGlobalMessage(ChatColor.AQUA + "Ha aparegut el primer pic de diamant!");
			firstPickaxeAnnounced = true;
		}
	}

	private void clearPickaxeSpawn(Location center) {
		for (Player player : getPlayers()) {
			if (player.isDead() || isSpectator(player) || player.getWorld() != world) continue;
			Vector push = Interactions.outwardPush(player.getLocation().toVector(), center.toVector(), player.getLocation().getDirection());
			if (push.lengthSquared() == 0) continue;
			// Keep the shove on walkable ground and away from walls and the island's drops.
			Vector horizontal = push.clone().setY(0).normalize();
			double clearDistance = 0;
			for (double distance = 0.25; distance <= 2.5; distance += 0.25) {
				Location step = player.getLocation().add(horizontal.clone().multiply(distance));
				if (!safeStandingSpot(step)) break;
				clearDistance = distance;
			}
			if (clearDistance < 0.5) continue;
			push.setX(push.getX() * Math.min(1, clearDistance / 2.5));
			push.setZ(push.getZ() * Math.min(1, clearDistance / 2.5));
			player.setVelocity(push);
		}
	}

	private boolean safeStandingSpot(Location location) {
		for (double dx : new double[]{-0.3, 0.3}) for (double dz : new double[]{-0.3, 0.3}) {
			Location corner = location.clone().add(dx, 0, dz);
			if (!corner.getBlock().isPassable() || !corner.clone().add(0, 1, 0).getBlock().isPassable()) return false;
			Block support = corner.clone().add(0, -0.1, 0).getBlock();
			if (!support.getType().isSolid() || support.isLiquid()) return false;
		}
		return true;
	}

	@Override
	protected void onPlayerPickupItem(PlayerPickupItemEvent evt, Player p) {
		super.onPlayerPickupItem(evt, p);
		Item item = evt.getItem();
		if (item.getItemStack().getType() == Material.GOLD_NUGGET || item.getItemStack().getType() == Material.GOLD_INGOT) refreshGoldSoon(p);
		tidySoon(p);
		GameItem gameItem = GameItem.from(item.getItemStack());
		if (gameItem == GameItem.SNOWBALL) hint(p, "bola");
		if (gameItem == GameItem.ENCHANTED_SNOWBALL) hint(p, "superninot");
		if (gameItem == GameItem.NETHER_STAR) hint(p, "estrella");
		if (item.getItemStack().getType() != Material.DIAMOND_PICKAXE) return;
		if (item.getLocation().distance(diamondPickaxePoint()) < 1.5) {
			int gold = 3;
			giveGold(p, gold);
			sendPlayerMessage(p, "Has agafat el pic de diamant" + "(" + ChatColor.GOLD + "+" + gold + ChatColor.WHITE + ")");
		}
	}

	//---------- The Guardian: the iron golem (ApareixerGolem, 2013) with its aura ----------

	private void spawnGuardian() {
		if (!JocEnMarxa() || !pMapaActual().ExisteixPropietat("Golem")) return;
		Location point = guardianSpawnPoint();
		IronGolem golem = world.spawn(point, IronGolem.class);
		golem.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400 * 20, 1, true), true);
		golem.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 400 * 20, 1, true), true);
		golem.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400 * 20, 1, true), true);
		golem.setRemoveWhenFarAway(false);
		golem.setPersistent(true);
		golem.customName(PaperMessages.legacy(ChatColor.AQUA + GUARDIAN_NAME));
		golem.setCustomNameVisible(true);
		currentGuardianId = golem.getUniqueId();
		guardianEnraged = false;
		guardianBossBar().progress(1F);
		world.playSound(point, Sound.BLOCK_BEACON_ACTIVATE, 2F, 1F);
		world.playSound(point, Sound.ENTITY_IRON_GOLEM_REPAIR, 1.5F, 0.8F);
		for (Player p : getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.6F, 1F);
		if (!guardianAnnounced) {
			guardianAnnounced = true;
			sendGlobalMessage(ChatColor.AQUA + GUARDIAN_NAME + ChatColor.WHITE + " s'ha despertat sota el mig: " + ChatColor.GOLD + GOLD_PER_GUARDIAN + " d'or" + ChatColor.WHITE + " i 3 min de Resistència i Velocitat per a qui el mati.");
		} else {
			sendGlobalMessage(ChatColor.AQUA + GUARDIAN_NAME + ChatColor.WHITE + " s'ha despertat.");
		}
		if (guardianAuraTask != -1) Bukkit.getScheduler().cancelTask(guardianAuraTask);
		guardianAuraTask = scheduleGameplayRepeatingTask(this::renderGuardianAura, 0, GUARDIAN_AURA_PERIOD_TICKS);
		updateScoreBoards();
	}

	private Location guardianMapPoint() {
		return pMapaActual().ObtenirLocation("Golem", world);
	}

	/** Beside the hut's chest, which stays as loot for whoever gets there first: the doorway side, else above the chest. */
	private Location guardianSpawnPoint() {
		Block chest = guardianMapPoint().getBlock().getRelative(BlockFace.UP);
		Block sideBlock = chest.getRelative(BlockFace.SOUTH);
		Block feetLocation = sideBlock.isPassable() && sideBlock.getRelative(BlockFace.UP).isPassable() ? sideBlock : chest.getRelative(BlockFace.UP);
		return feetLocation.getLocation().add(0.5, 0, 0.5);
	}

	private boolean isGuardian(Entity e) {
		return e instanceof IronGolem && currentGuardianId != null && currentGuardianId.equals(e.getUniqueId());
	}

	private boolean killedByGuardian(Player deadPlayer) {
		return deadPlayer.getLastDamageCause() instanceof EntityDamageByEntityEvent damageEvent && isGuardian(damageEvent.getDamager());
	}

	private IronGolem liveGuardian() {
		if (currentGuardianId == null) return null;
		Entity e = Bukkit.getEntity(currentGuardianId);
		return e instanceof IronGolem golem && !golem.isDead() ? golem : null;
	}

	private BossBar guardianBossBar() {
		if (guardianBossBar == null) {
			guardianBossBar = BossBar.bossBar(PaperMessages.legacy(ChatColor.AQUA + GUARDIAN_NAME), 1F, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_10);
		}
		return guardianBossBar;
	}

	/**
	 * Every five ticks while the Guardian lives: a slowly turning ring of dust and soul
	 * flames at its feet, a mote rising from its chest, and the beam over the lair that
	 * tells the surface it is up. Particles only; no block is touched.
	 */
	private void renderGuardianAura() {
		IronGolem golem = liveGuardian();
		if (golem == null) return;
		auraSteps++;
		Location feetLocation = golem.getLocation();
		Color auraColor = guardianEnraged ? ENRAGED_AURA_COLOR : GUARDIAN_AURA_COLOR;
		Particle.DustOptions dust = new Particle.DustOptions(auraColor, 1.1F);
		double rotation = auraSteps * 0.15;
		auraRing(feetLocation, auraColor, GUARDIAN_AURA_RADIUS, rotation, guardianEnraged ? 4 : 2);
		if (auraSteps % 2 == 0) world.spawnParticle(Particle.END_ROD, feetLocation.clone().add(0, 1.4, 0), 0, 0, 1, 0, 0.03);
		Location beamLocation = guardianMapPoint().add(0.5, GUARDIAN_BEAM_BASE, 0.5);
		for (int i = 0; i < 3; i++) world.spawnParticle(Particle.END_ROD, beamLocation.clone().add(0, Math.random() * GUARDIAN_BEAM_HEIGHT, 0), 1, 0, 0, 0, 0);
		if (auraSteps % 2 == 0) {
			for (int i = 0; i < 8; i++) {
				double angle = rotation + i * Math.PI / 4;
				world.spawnParticle(Particle.DUST, beamLocation.clone().add(0.8 * Math.cos(angle), 0.1, 0.8 * Math.sin(angle)), 1, 0, 0, 0, 0, dust);
			}
		}
	}

	/**
	 * Every second for the whole match. While the Guardian lives: who sees its boss bar,
	 * the hum on the canal, and the enrage tint under 30 % health. While it is dead: the
	 * scoreboard countdown to its return.
	 */
	private void trackGuardianPresence() {
		IronGolem golem = liveGuardian();
		if (golem == null) {
			// The sidebar is refreshed only when the coarse countdown changes.
			if (guardianReturnsAtSecond > segonsTranscorreguts() && !guardianStatus().equals(lastGuardianCountdownShown)) updateScoreBoards();
			return;
		}
		BossBar bar = guardianBossBar();
		double healthFraction = golem.getHealth() / golem.getAttribute(Attribute.MAX_HEALTH).getValue();
		bar.progress((float) Math.max(0, Math.min(1, healthFraction)));
		for (Player p : getPlayers()) {
			if (p.getLocation().distance(golem.getLocation()) <= GUARDIAN_BAR_DISTANCE) p.showBossBar(bar);
			else p.hideBossBar(bar);
		}
		if (!guardianEnraged && healthFraction < ENRAGED_HEALTH_FRACTION) {
			guardianEnraged = true;
			world.playSound(golem.getLocation(), Sound.ENTITY_IRON_GOLEM_REPAIR, 1.5F, 0.6F);
		}
		if (++guardianPresenceSeconds % GUARDIAN_BUZZ_INTERVAL_SECONDS == 0) world.playSound(golem.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 0.7F, 1F);
	}

	/** A player who leaves the match (/l, a teleport out) must not carry the Guardian's bar to the lobby. */
	@Override
	protected void customLeave(Player ply, List<String> attatchments) {
		removeOwnedArchers(ply);
		combatCredits.remove(ply.getUniqueId());
		if (watchtowerLaunchers != null) watchtowerLaunchers.forget(ply.getUniqueId());
		goldScore.removePlayer(ply.getUniqueId());
		super.customLeave(ply, attatchments);
		if (guardianBossBar != null) ply.hideBossBar(guardianBossBar);
		if (objectiveBar != null) ply.hideBossBar(objectiveBar);
		cancelStarCharge(ply);
		setMaxHealth(ply, FULL_MAX_HEALTH);
		Block shown = shownPressedPlate.remove(ply.getUniqueId());
		if (shown != null) ply.sendBlockChange(shown.getLocation(), shown.getBlockData());
	}
	/** A lost connection mid-charge: the charge task must not fire on a player who is gone; the star drops at their feet as a death would. */
	@Override
	protected void onSeatDropped(Player ply) {
		removeOwnedArchers(ply);
		if (watchtowerLaunchers != null) watchtowerLaunchers.forget(ply.getUniqueId());
		super.onSeatDropped(ply);
		updateGoldBalance(ply);
		cancelStarCharge(ply);
	}
	/** Away past the grace: only what is keyed by them here needs forgetting; the bar re-shows itself each second to whoever is present. */
	@Override
	protected void onSeatAbandoned(Seat seat, List<String> attatchments) {
		goldScore.removePlayer(seat.getUuid());
		super.onSeatAbandoned(seat, attatchments);
		shownPressedPlate.remove(seat.getUuid());
	}

	private void stopGuardianAura() {
		if (guardianAuraTask != -1) {
			Bukkit.getScheduler().cancelTask(guardianAuraTask);
			guardianAuraTask = -1;
		}
		if (guardianBossBar != null && world != null) for (Player p : world.getPlayers()) p.hideBossBar(guardianBossBar);
	}

	/** The scoreboard's word for the Guardian: alive, or the time until it returns. */
	private String guardianStatus() {
		if (liveGuardian() != null) return "viu";
		int seconds = guardianReturnsAtSecond - segonsTranscorreguts();
		if (seconds <= 0) return "arriba";
		int step = GUARDIAN_COUNTDOWN_STEP_SECONDS;
		seconds = (seconds + step - 1) / step * step;
		return String.format("%d:%02d", seconds / 60, seconds % 60);
	}

	@Override
	protected void onEntityDeath(EntityDeathEvent evt, Entity e) {
		super.onEntityDeath(evt, e);
		if (!isGuardian(e)) return;
		currentGuardianId = null;
		stopGuardianAura();
		Location pit = e.getLocation().add(0, 1.3, 0);
		world.playSound(pit, Sound.BLOCK_BEACON_DEACTIVATE, 2F, 1F);
		world.spawnParticle(Particle.DUST, pit, 60, 0.6, 0.8, 0.6, 0, new Particle.DustOptions(GUARDIAN_AURA_COLOR, 1.4F));
		world.spawnParticle(Particle.END_ROD, pit, 20, 0.3, 0.5, 0.3, 0.1);
		for (Player p : getPlayers()) p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_DEATH, 0.5F, 1F);
		Player p = ((IronGolem) e).getKiller();
		if (p != null) {
			giveGold(p, GOLD_PER_GUARDIAN);
			p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 3 * 60 * 20, 0, false), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30 * 20, 1, false), true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3 * 60 * 20, 1, false), true);
			PaperMessages.sendActionBar(p, ChatColor.AQUA + "Benedicció del Guardià", 60);
			sendGlobalMessage(p.getName() + " ha matat " + ChatColor.AQUA + GUARDIAN_NAME + ChatColor.WHITE + ", el golem de ferro (" + ChatColor.GOLD + "+" + GOLD_PER_GUARDIAN + ChatColor.WHITE + ")");
			Equip slayers = obtenirEquip(p);
			if (slayers != null && slayers != guardianSlayerTeam) {
				guardianSlayerTeam = slayers;
				sendGlobalMessage(slayers.getChatColor() + "Els ninots de l'equip " + slayers.getAdjectiu() + ChatColor.WHITE + " ara són de magma i cremen, fins que l'altre equip mati el Guardià.");
			}
		}
		int minutes = getPlayers().size() >= 5 ? 2 : 3;
		guardianReturnsAtSecond = segonsTranscorreguts() + minutes * 60;
		sendGlobalMessage(ChatColor.AQUA + GUARDIAN_NAME + ChatColor.WHITE + " tornarà d'aquí a " + minutes + " min.");
		scheduleGameplayTask(this::spawnGuardian, minutes * 60L * 20L);
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
		lastHitSecondByPlayer.put(damaged.getUniqueId(), segonsTranscorreguts());
		if (damager.getLocation().getBlockY() >= 50 && !ranged) {
			evt.setDamage(evt.getDamage() * 1.6 + Utils.NombreEntre(1, 11));
		}
		if (damager.getLocation().getBlockY() >= 45) {
			evt.setDamage(evt.getDamage() + Utils.NombreEntre(1, 5));
		}
		evt.setDamage(evt.getDamage() * 0.8);
		if (Ability.hasAbility(plugin, this, damager, AbilityType.PERFECT_ARCHER) && !evt.isCancelled() && ranged) {
			int hitCount = pPlayer(damager).ObtenirPropietatInt("PerfectBowHitCount");
			if (hitCount >= 3) {
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
				if (hitCount == 5) {
					damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				}
			}
			updateScoreBoard(damager);
		}
		if (Ability.hasAbility(plugin, this, damager, AbilityType.FROST_ARCHER) && !evt.isCancelled() && ranged) {
			int hitCount = pPlayer(damager).ObtenirPropietatInt("StrongBowHitCount");
			if (hitCount >= 6) {
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
				if (hitCount == 5) {
					damager.playSound(damager.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				}
			}
			updateScoreBoard(damager);
		}
		if (Ability.hasAbility(plugin, this, damager, AbilityType.SWORDSMAN) && !evt.isCancelled()) {
			int hitCount = pPlayer(damager).ObtenirPropietatInt("StrongHitCount");
			if (hitCount >= 5) {
				evt.setDamage(evt.getDamage() * 1.5);
				Vector rawDir = damaged.getLocation().toVector().subtract(damager.getLocation().toVector());
				Vector dir = rawDir.normalize().multiply(2).add(new Vector(0, 0.3, 0));
				damaged.setVelocity(dir);
				pPlayer(damager).EstablirPropietat("StrongHitCount", 1);
				damaged.playSound(damager.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, 0.3F);
			} else {
				pPlayer(damager).IncrementarPropietat("StrongHitCount");
				if (hitCount == 5) {
					damaged.playSound(damager.getLocation(), Sound.ENTITY_HORSE_LAND, 1, 0.3F);
				}
			}
			updateScoreBoard(damager);
		}
		if (Ability.hasAbility(plugin, this, damaged, AbilityType.RESISTANCE)) {
			double damageMultiplier = 0.9;
			damageMultiplier = damageMultiplier - (Utils.getNearbyPlayers(damaged, 10).size() * 0.08);
			if (damageMultiplier <= 0.1) { damageMultiplier = 0.1; }
			double finalDamage = evt.getDamage() * 0.85;
			evt.setDamage(finalDamage);
			if (debug) {
				Bukkit.broadcastMessage("Mal reduït: " + Double.toString(evt.getDamage() - finalDamage) + " - " + damageMultiplier * 100 + "%");
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
			} else if (Ability.hasAbility(plugin, this, damager, AbilityType.DESTROYER)) {
				int deaths = pTemp().ObtenirPropietatInt(damager.getName() + "Morts");
				meta.setDamage(meta.getDamage() + 5 + deaths);
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
			if (Ability.hasAbility(plugin, this, player, AbilityType.IMPACT_PROTECTION)) {
				evt.setDamage(evt.getDamage() / 2);
			}
		}
	}

	//---------- The map's signs: booths and the bridge button ----------

	/**
	 * Reads the signs around each base once. A booth sign hands its villager (the map's
	 * own, adopted in place, or a new one when the booth is empty) to the team; the
	 * "Deploy enemy's bridge" sign becomes that team's charge bar and its button the
	 * control. The former sewer controls are registered separately as base upgrades.
	 */
	private void registerControlsAndBooths() {
		for (Equip e : Equips) {
			Location base = e.getTeamSpawnLocation();
			for (Sign sign : signsAround(base, SIGN_RADIUS)) {
				String text = String.join(" ", sign.getSide(Side.FRONT).getLines()).toLowerCase();
				Block block = sign.getBlock();
				if (text.contains("bridge")) {
					bridgeSigns.put(e.getId(), block);
					Block button = adjacentButton(block);
					if (button != null) bridgeButtons.put(button, e.getId());
					writeSign(block, bridgeLabel(e, "Pont enemic"), bridgeBar(e), "", "");
				} else if (text.contains("sewers")) {
					// The launcher controller owns this sign, including registration retries.
				} else {
					boolean boothFound = false;
					for (Booth booth : Booth.values()) {
						if (!text.contains(booth.originalSignText)) continue;
						boothFound = true;
						boothSigns.add(block);
						writeSign(block, ChatColor.GOLD + booth.name, ChatColor.GRAY + booth.profession, "", "");
						Villager shopkeeper = boothVillager(block.getLocation());
						configureShopkeeper(shopkeeper, new TeamBooth(booth, e));
					}
					if (!boothFound) unusedSigns.add(sign);
				}
			}
			if (!bridgeSigns.containsKey(e.getId())) {
				plugin.getLogger().info(getGameName() + " " + getMapName() + ": no bridge sign within " + SIGN_RADIUS + " blocks of base" + e.getId() + " yet; the check after the start scans again");
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
		@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
		public void onCreditedDamage(EntityDamageByEntityEvent event) {
			if (!JocEnMarxa() || event.getEntity().getWorld() != world
					|| !(event.getEntity() instanceof Player victim) || !getPlayers().contains(victim)
					|| isSpectator(victim) || event.getFinalDamage() <= 0) return;
			Minion minion = attackingMinion(event.getDamager());
			Entity source = event.getDamager();
			if (source instanceof Projectile projectile && projectile.getShooter() instanceof Entity shooter) source = shooter;
			Player owner = minion != null ? minion.owner() : source instanceof Player player ? player : null;
			if (owner == null || !getPlayers().contains(owner) || areAllies(owner, victim)) return;
			combatCredits.put(victim.getUniqueId(), new CombatCredit(owner.getUniqueId(), minion, segonsTranscorreguts()));
			lastHitSecondByPlayer.put(victim.getUniqueId(), segonsTranscorreguts());
			getPlayerInfo(victim).setLastDamager(owner);
			if (minion != null) {
				PlayerInfo info = getPlayerInfo(owner);
				info.setDamageDealt(info.getDamageDealt() + event.getDamage());
			}
		}

		@EventHandler(priority = EventPriority.HIGHEST)
		public void onArrivalFireworkDamage(EntityDamageByEntityEvent evt) {
			if (evt.getDamager() instanceof Firework firework && firework.getScoreboardTags().contains(PICKAXE_FIREWORK_TAG)) evt.setCancelled(true);
		}

		@EventHandler
		public void onEntityInteract(EntityInteractEvent evt) {
			if (world == null || evt.getBlock().getWorld() != world || minionOf(evt.getEntity()) == null) return;
			if (Tag.PRESSURE_PLATES.isTagged(evt.getBlock().getType())) evt.setCancelled(true);
		}

		@EventHandler
		public void onInventoryClick(InventoryClickEvent evt) {
			if (evt.getWhoClicked() instanceof Player p) refreshGoldSoon(p);
		}

		@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
		public void onGoldDrop(org.bukkit.event.player.PlayerDropItemEvent evt) {
			refreshGoldSoon(evt.getPlayer());
		}

		@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
		public void onInventoryDrag(org.bukkit.event.inventory.InventoryDragEvent evt) {
			if (evt.getWhoClicked() instanceof Player p) refreshGoldSoon(p);
		}

		@EventHandler
		public void onInventoryClose(InventoryCloseEvent evt) {
			if (!(evt.getPlayer() instanceof Player p)) return;
			if (!JocEnMarxa() || p.getWorld() != world || p.isDead() || isSpectator(p)) return;
			if (evt.getReason() == InventoryCloseEvent.Reason.PLAYER
					&& evt.getInventory().getHolder() instanceof Chest chest && isJungleChest(chest.getBlock())) {
				emptyChestInto(chest, p);
			}
			refreshGoldSoon(p);
		}
	};

	private boolean isJungleChest(Block block) {
		if (world == null || block.getWorld() != world) return false;
		for (Location spot : pMapaActual().ObtenirLocations("cofres", world)) if (spot.getBlock().equals(block)) return true;
		return false;
	}

	/** Claim the chest before creating entities, so simultaneous viewers cannot collect it twice. */
	private void emptyChestInto(Chest chest, Player p) {
		if (chest.getBlock().getType() != Material.CHEST || p.getLocation().distanceSquared(chest.getLocation()) > 64) return;
		Inventory inv = chest.getInventory();
		List<ItemStack> loot = new ArrayList<>();
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (item == null || item.getType() == Material.AIR) continue;
			loot.add(item.clone());
		}
		inv.clear();
		Location origin = chest.getLocation().add(0.5, 1.05, 0.5);
		for (ItemStack stack : loot) {
			Item item = world.dropItem(origin, stack, drop -> {
				drop.setOwner(p.getUniqueId());
				drop.setCanMobPickup(false);
				drop.setPickupDelay(0);
				drop.setGravity(false);
			drop.setVelocity(Interactions.lootVelocity(origin.toVector(), p.getLocation().add(0, 0.5, 0).toVector()));
			});
			flyingChestLoot.add(new FlyingLoot(item, p.getUniqueId()));
		}
		// Inventory-close handlers finish before changing the block under any other viewer.
		Block block = chest.getBlock();
		scheduleGameplayTask(() -> {
			if (JocEnMarxa() && block.getState() instanceof Chest current && current.getInventory().isEmpty()) closeChest(block);
		}, 1);
	}

	private void tickChestLoot() {
		var iterator = flyingChestLoot.iterator();
		while (iterator.hasNext()) {
			FlyingLoot flight = iterator.next();
			Item item = flight.item();
			if (!item.isValid()) { iterator.remove(); continue; }
			Player collector = Bukkit.getPlayer(flight.collector());
			if (!JocEnMarxa() || collector == null || collector.getWorld() != world || collector.isDead() || isSpectator(collector)) {
				releaseChestLoot(item);
				iterator.remove();
				continue;
			}
			Location target = collector.getLocation().add(0, 0.4, 0);
			Vector toward = target.toVector().subtract(item.getLocation().toVector());
			if (toward.lengthSquared() < 0.36 || item.getTicksLived() >= 20) {
				item.teleport(target);
				releaseChestLoot(item);
				iterator.remove();
			} else {
				item.setVelocity(Interactions.lootVelocity(item.getLocation().toVector(), target.toVector()));
			}
		}
	}

	private void releaseChestLoot(Item item) {
		item.setGravity(true);
		item.setVelocity(new Vector());
		item.setOwner(null);
		item.setCanMobPickup(true);
	}

	private void createShopPortals() {
		for (Equip team : Equips) {
			int id = team.getId();
			Interactions.PortalPosition approved = Interactions.portal(id);
			Location entrance = portalLocation("ShopPortal" + id, approved.entrance());
			Location arrival = portalLocation("ShopArrival" + id, approved.arrival());
			arrival.setYaw(approved.arrivalYaw());
			if (pMapaActual().ExisteixPropietat("ShopArrivalYaw" + id)) arrival.setYaw(pMapaActual().ObtenirPropietatInt("ShopArrivalYaw" + id));
			if (!safeStandingSpot(entrance) || !safeStandingSpot(arrival)) {
				plugin.getLogger().warning("Shop portal " + id + " not created: entrance or arrival is obstructed");
				continue;
			}
			BlockData previous = entrance.getBlock().getBlockData().clone();
			entrance.getBlock().setType(Material.OAK_PRESSURE_PLATE, false);
			HologramFacade.Handle label = HologramFacade.create(entrance.clone().add(0, 2.5, 0));
			label.setLines(team.getChatColor() + "Shop");
			shopPortals.put(id, new ShopPortal(entrance, arrival, previous, label));
			plugin.getLogger().info("Shop portal " + id + ": " + entrance.toVector() + " -> " + arrival.toVector() + ", radius=2");
		}
	}

	private Location portalLocation(String property, Vector fallback) {
		return pMapaActual().ExisteixPropietat(property)
				? pMapaActual().ObtenirLocation(property, world).add(0.5, 0, 0.5) : fallback.toLocation(world);
	}

	private void tickShopPortals() {
		if (!JocEnMarxa()) return;
		for (Player player : getPlayers()) {
			if (player.isDead() || isSpectator(player) || player.getWorld() != world) continue;
			Equip team = obtenirEquip(player);
			ShopPortal portal = team == null ? null : shopPortals.get(team.getId());
			if (portal == null || Bukkit.getCurrentTick() < portalCooldownUntil.getOrDefault(player.getUniqueId(), 0)) continue;
			if (!Interactions.inPortal(player.getLocation().toVector(), portal.entrance().toVector())) continue;
			if (!safeStandingSpot(portal.arrival())) continue;
			if (player.teleport(portal.arrival())) {
				player.setVelocity(new Vector());
				player.setFallDistance(0);
				portalCooldownUntil.put(player.getUniqueId(), Bukkit.getCurrentTick() + 40);
				player.playSound(portal.arrival(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.4F, 1.2F);
			}
		}
	}

	private void refreshGoldSoon(Player p) {
		if (world == null || p.getWorld() != world || !JocEnMarxa()) return;
		scheduleGameplayTask(() -> { if (p.isOnline()) { consolidateGold(p); updateScoreBoard(p); } }, 1);
	}

	/**
	 * Three seconds after the start, what the scans should have found is checked once
	 * more: the instance recreated at 23:12 on 2026-09-07, 35 s after one of the same name
	 * was deleted, found no bridge sign and no lamps for base0 while base1 was whole.
	 */
	private void verifyRegistrations() {
		if (!JocEnMarxa()) return;
		List<String> missing = missingRegistrations();
		if (missing.isEmpty()) return;
		if (missing.stream().anyMatch(m -> m.startsWith("bridge sign"))) registerControlsAndBooths();
		registerControlPointsAndLamps();
		List<String> still = missingRegistrations();
		// The first scan of a freshly created instance misses base0's sign about one time in three and the rescan always finds it (logs of 2026-09-08); only a second miss is worth a warning.
		if (still.isEmpty()) plugin.getLogger().info(getGameName() + " " + getMapName() + ": " + missing + " found on the second scan");
		else plugin.getLogger().warning(getGameName() + " " + getMapName() + ": " + still + " still missing after the second scan; the map or its property file needs a look");
	}

	private List<String> missingRegistrations() {
		List<String> missing = new ArrayList<>();
		for (Equip e : Equips) if (!bridgeSigns.containsKey(e.getId())) missing.add("bridge sign of base" + e.getId());
		if (controlPoints.isEmpty()) missing.add("control points");
		return missing;
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
			Vector center = new Vector();
			for (Block b : booths) center.add(b.getLocation().toVector());
			center.multiply(1.0 / booths.size());
			Sign nearest = null;
			double best = Double.MAX_VALUE;
			for (Sign sign : unused) {
				double d = sign.getLocation().toVector().distanceSquared(center);
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
		for (Block sign : killsSigns.values()) if (sign.getState() instanceof Sign) writeSign(sign, lines);
		updateLookoutSign();
	}

	private void createLookoutSign() {
		Location floor = pMapaActual().ExisteixPropietat("Lookout")
				? pMapaActual().ObtenirLocation("Lookout", world) : Interactions.lookoutFloor().toLocation(world);
		Location standing = floor.clone().add(0.5, 1, 0.5);
		Block display = standing.getBlock();
		if (!safeStandingSpot(standing) || !display.getType().isAir()) {
			plugin.getLogger().warning("Lookout sign not created: canopy position is obstructed at " + floor.toVector());
			return;
		}
		originalLookoutBlock = display.getState();
		org.bukkit.block.data.type.Sign signData = (org.bukkit.block.data.type.Sign) Material.OAK_SIGN.createBlockData();
		signData.setRotation(BlockFace.EAST);
		display.setBlockData(signData, false);
		lookoutSign = display;
		updateLookoutSign();
		plugin.getLogger().info("Lookout sign at " + display.getLocation().toVector() + ": red vs blue, held plus spent gold (0.01k nuggets) and team kills");
	}

	private void updateLookoutSign() {
		if (!JocEnMarxa()) return;
		for (Seat seat : getSeats()) {
			if (seat.getRole() != Seat.Role.PLAYER || (!seat.isOccupied() && !seat.isDropped())) {
				goldScore.removePlayer(seat.getUuid());
			} else if (seat.isOccupied() && seat.getPlayer() != null) {
				updateGoldBalance(seat.getPlayer());
			}
		}
		if (lookoutSign == null || Equips.size() != 2) return;
		String[] lines = Interactions.lookoutLines(goldScore.total(0), goldScore.total(1),
				killsByTeam.getOrDefault(0, 0), killsByTeam.getOrDefault(1, 0));
		List<String> next = List.of(lines);
		if (!next.equals(lastLookoutLines)) {
			if (lookoutSign.getState() instanceof Sign sign) {
				for (Side side : Side.values()) {
					for (int line = 0; line < 4; line++) sign.getSide(side).line(line, PaperMessages.legacy(lines[line]));
					sign.getSide(side).setGlowingText(true);
				}
				sign.setWaxed(true);
				if (sign.update(false, false)) lastLookoutLines = next;
			}
		}
	}

	/** How many kills this team trails the best other team by; zero when level or ahead. */
	private int killsBehind(Equip team) {
		int best = 0;
		for (Equip other : Equips) if (other != team) best = Math.max(best, killsByTeam.getOrDefault(other.getId(), 0));
		return Math.max(0, best - killsByTeam.getOrDefault(team.getId(), 0));
	}

	private List<Sign> signsAround(Location center, int radius) {
		List<Sign> signs = new ArrayList<>();
		int cx = center.getBlockX() >> 4, cz = center.getBlockZ() >> 4, chunks = (radius >> 4) + 1;
		for (int dx = -chunks; dx <= chunks; dx++) {
			for (int dz = -chunks; dz <= chunks; dz++) {
				for (BlockState status : world.getChunkAt(cx + dx, cz + dz).getTileEntities()) {
					if (status instanceof Sign sign && sign.getLocation().distance(center) <= radius) signs.add(sign);
				}
			}
		}
		return signs;
	}

	private static Block adjacentButton(Block sign) {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				for (int dz = -1; dz <= 1; dz++) {
					Block b = sign.getRelative(dx, dy, dz);
					if (Tag.BUTTONS.isTagged(b.getType())) return b;
				}
			}
		}
		return null;
	}

	private static void writeSign(Block block, String... lines) {
		if (!(block.getState() instanceof Sign sign)) return;
		for (int i = 0; i < 4 && i < lines.length; i++) sign.getSide(Side.FRONT).line(i, PaperMessages.legacy(lines[i]));
		sign.update(true, false);
	}

	/** The map's villager nearest the booth sign, or a new one on the nearest standing spot. */
	private Villager boothVillager(Location sign) {
		Villager nearest = null;
		double bestDistance = 8;
		for (Villager v : world.getEntitiesByClass(Villager.class)) {
			if (shopkeepers.containsKey(v.getUniqueId())) continue;
			double d = v.getLocation().distance(sign);
			if (d < bestDistance) { bestDistance = d; nearest = v; }
		}
		if (nearest != null) return nearest;
		Location feetLocation = null;
		for (int dx = -2; dx <= 2 && feetLocation == null; dx++) {
			for (int dz = -2; dz <= 2 && feetLocation == null; dz++) {
				Block b = sign.getBlock().getRelative(dx, 0, dz);
				if (b.isPassable() && b.getRelative(0, 1, 0).isPassable() && !b.getRelative(0, -1, 0).isPassable() && !(b.getState() instanceof Sign)) {
					feetLocation = b.getLocation().add(0.5, 0, 0.5);
				}
			}
		}
		return world.spawn(feetLocation != null ? feetLocation : sign.clone().add(0.5, 0, 0.5), Villager.class);
	}

	private void configureShopkeeper(Villager shopkeeper, TeamBooth booth) {
		shopkeeper.setAI(false);
		shopkeeper.setInvulnerable(true);
		shopkeeper.setSilent(true);
		shopkeeper.setCollidable(false);
		shopkeeper.setRemoveWhenFarAway(false);
		shopkeeper.setPersistent(true);
		shopkeeper.setRecipes(java.util.Collections.emptyList());
		shopkeeper.setProfession(switch (booth.booth()) {
			case GERRY -> Villager.Profession.WEAPONSMITH;
			case SEON -> Villager.Profession.ARMORER;
			case KAREN -> Villager.Profession.CLERIC;
		});
		shopkeeper.customName(PaperMessages.legacy(ChatColor.GOLD + booth.booth().name));
		shopkeeper.setCustomNameVisible(true);
		shopkeepers.put(shopkeeper.getUniqueId(), booth);
	}

	@Override
	public void clearExternals() {
		if (upgradeSigns != null) { upgradeSigns.close(); upgradeSigns = null; }
		teamUpgrades = null;
		combatCredits.clear();
		lastHitSecondByPlayer.clear();
		if (watchtowerLaunchers != null) { watchtowerLaunchers.close(); watchtowerLaunchers = null; }
		HandlerList.unregisterAll(worldListener);
		if (originalLookoutBlock != null) { originalLookoutBlock.update(true, false); originalLookoutBlock = null; }
		lookoutSign = null;
		goldScore.clear();
		lastLookoutLines = List.of();
		for (FlyingLoot flight : flyingChestLoot) if (flight.item().isValid()) releaseChestLoot(flight.item());
		flyingChestLoot.clear();
		for (ShopPortal portal : shopPortals.values()) {
			portal.label().delete();
			portal.entrance().getBlock().setBlockData(portal.originalBlock(), false);
		}
		shopPortals.clear();
		portalCooldownUntil.clear();
		hideObjective();
		stopGuardianAura();
		for (UUID id : shopkeepers.keySet()) {
			Entity shopkeeper = Bukkit.getEntity(id);
			if (shopkeeper != null) shopkeeper.remove();
		}
		shopkeepers.clear();
		super.clearExternals();
	}

	@Override
	protected void onEntityDamage(EntityDamageEvent evt, Entity e) {
		super.onEntityDamage(evt, e);
		if (shopkeepers.containsKey(e.getUniqueId())) evt.setCancelled(true);
	}

	@Override
	protected void onPlayerInteractEntity(PlayerInteractEntityEvent evt, Player p) {
		super.onPlayerInteractEntity(evt, p);
		TeamBooth booth = shopkeepers.get(evt.getRightClicked().getUniqueId());
		if (booth == null) return;
		evt.setCancelled(true);
		if (evt.getHand() != EquipmentSlot.HAND || !JocEnMarxa()) return;
		if (obtenirEquip(p) != booth.team()) {
			rejectEnemyElement(p);
			return;
		}
		openBoothMenu(p, booth.booth());
	}

	/** An enemy's booth villager or bridge button: the villager's "no" and a grey line saying why. */
	private static void rejectEnemyElement(Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
		p.sendMessage(ChatColor.GRAY + "No pots interactuar amb elements de l'equip enemic");
	}

	private void openBoothMenu(Player p, Booth booth) {
		List<Merchandise> merchandise = booth.merchandise;
		IconMenu menu = new IconMenu(ChatColor.GOLD + booth.name, 9, event -> {
			int position = event.getPosition();
			if (position < merchandise.size()) purchase(event.getPlayer(), merchandise.get(position));
			event.setWillClose(false);
		});
		for (Merchandise m : merchandise) {
			ArrayList<String> info = new ArrayList<>();
			GameItem gameItem = GameItem.from(m.material);
			if (gameItem != null) for (String line : gameItem.lore()) info.add(ChatColor.GRAY + line);
			else if (m.description != null) info.add(ChatColor.GRAY + m.description);
			info.add(ChatColor.WHITE + "Preu: " + ChatColor.GOLD + m.price + " or");
			boolean soldOut = m == Merchandise.QUARTZ && hasQuartz(p);
			if (soldOut) info.add(ChatColor.RED + "Ja el tens: només un per persona");
			if (m == Merchandise.ARROWS && !hasBow(p)) { soldOut = true; info.add(ChatColor.RED + "Primer compra un arc"); }
			menu.setOption(merchandise.indexOf(m), m.stack(), (soldOut ? ChatColor.DARK_GRAY : ChatColor.YELLOW) + m.name, info);
		}
		menu.open(p);
	}

	private void purchase(Player p, Merchandise m) {
		if (!JocEnMarxa()) return;
		if (m == Merchandise.QUARTZ && hasQuartz(p)) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			PaperMessages.sendActionBar(p, ChatColor.RED + "Només un quars per persona", 60);
			return;
		}
		if (m == Merchandise.ARROWS && !hasBow(p)) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			PaperMessages.sendActionBar(p, ChatColor.RED + "Primer compra un arc", 60);
			return;
		}
		int shortfall = m.price - availableGold(p);
		if (shortfall > 0 || !spendGold(p, m.price)) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			PaperMessages.sendActionBar(p, ChatColor.RED + "Et falten " + Math.max(1, shortfall) + " or", 60);
			return;
		}
		if (m == Merchandise.QUARTZ) quartzBuyers.add(p.getUniqueId());
		giveOrDrop(p, GameItem.describe(m.stack()));
		tidySoon(p);
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1F, 1F);
		updateScoreBoard(p);
	}

	private int availableGold(Player p) {
		Inventory inv = p.getInventory();
		int nuggets = 0;
		for (ItemStack item : inv.getContents()) {
			if (item == null) continue;
			if (item.getType() == Material.GOLD_NUGGET) nuggets += item.getAmount();
			if (item.getType() == Material.GOLD_INGOT) nuggets += 10 * item.getAmount();
		}
		return nuggets;
	}

	/** Arrows without a bow are gold thrown away (Biel, 2026-09-08): Gerry sells them only to a player who carries one. */
	private static boolean hasBow(Player p) {
		return p.getInventory().contains(Material.BOW);
	}

	/** Quartz is one per person for the match (Biel, 2026-09-07 night): bought once, or already carried. */
	private boolean hasQuartz(Player p) {
		return quartzBuyers.contains(p.getUniqueId()) || p.getInventory().contains(Material.QUARTZ);
	}

	/** Puts the stack in the inventory; what does not fit falls at the player's feet instead of vanishing. Armour is worn by the tidy when it is better. */
	private void giveOrDrop(Player p, ItemStack stack) {
		for (ItemStack leftover : p.getInventory().addItem(stack).values()) world.dropItemNaturally(p.getLocation(), leftover);
	}

	/** The inventory tidies itself the tick after something arrives: a purchase, a chest, a pickup, a respawn (design in GAMES.md, "Inventory tidy"). */
	private void tidySoon(Player p) {
		scheduleGameplayTask(() -> { if (p.isOnline() && JocEnMarxa()) InventoryTidy.tidy(p, RECALL_SLOT); }, 1);
	}

	private boolean spendGold(Player p, int price) {
		consolidateGold(p);
		int before = availableGold(p);
		if (price <= 0 || before < price) return false;
		p.getInventory().removeItem(new ItemStack(Material.GOLD_NUGGET, price));
		int paid = before - availableGold(p);
		if (paid != price) {
			if (paid > 0) giveOrDrop(p, new ItemStack(Material.GOLD_NUGGET, paid));
			updateGoldBalance(p);
			return false;
		}
		Equip team = obtenirEquip(p);
		if (team != null) goldScore.recordPurchase(p.getUniqueId(), team.getId(), heldGold(p), paid);
		return true;
	}

	/**
	 * Finds the map's enchanting tables between the bases and a little beyond them. A table
	 * at a base's level within the sign radius belongs to that base; the rest are the
	 * canopy altars, which anyone may use.
	 */
	private void registerEnchantingTables() {
		enchantingTables.clear();
		tableSigns.clear();
		if (Equips.size() < 2) return;
		Location a = Equips.get(0).getTeamSpawnLocation(), b = Equips.get(1).getTeamSpawnLocation();
		int minX = Math.min(a.getBlockX(), b.getBlockX()) - SIGN_RADIUS, maxX = Math.max(a.getBlockX(), b.getBlockX()) + SIGN_RADIUS;
		int midZ = (a.getBlockZ() + b.getBlockZ()) / 2;
		for (Block table : blocksBetween(minX, midZ - SIGN_RADIUS, maxX, midZ + SIGN_RADIUS, m -> m == Material.ENCHANTING_TABLE)) {
			EnchantingTableRegistration registration = new EnchantingTableRegistration(EnchantingSite.CANOPY, null);
			for (Equip e : Equips) {
				Location base = e.getTeamSpawnLocation();
				boolean atBaseLevel = Math.abs(table.getY() - base.getBlockY()) <= BASE_TABLE_HEIGHT;
				boolean insideBase = Math.abs(table.getX() - base.getBlockX()) <= SIGN_RADIUS && Math.abs(table.getZ() - base.getBlockZ()) <= SIGN_RADIUS;
				if (atBaseLevel && insideBase) registration = new EnchantingTableRegistration(EnchantingSite.BASE, e.getId());
			}
			enchantingTables.put(table, registration);
			placeTableSign(table, registration.enchantingSite());
		}
		long canopyTableCount = enchantingTables.values().stream().filter(t -> t.enchantingSite() == EnchantingSite.CANOPY).count();
		plugin.getLogger().info(getGameName() + " " + getMapName() + ": " + (enchantingTables.size() - canopyTableCount) + " base enchanting tables and " + canopyTableCount + " canopy altars");
	}

	/** A waxed sign standing on the table, turned to its open side, saying what it is in three words. */
	private void placeTableSign(Block table, EnchantingSite enchantingSite) {
		Block signBlock = table.getRelative(BlockFace.UP);
		if (!signBlock.getType().isAir()) return;
		BlockFace openFace = null;
		for (BlockFace face : List.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)) {
			Block sideBlock = table.getRelative(face);
			if (sideBlock.isPassable() && sideBlock.getRelative(BlockFace.UP).isPassable()) { openFace = face; break; }
		}
		if (openFace == null) return;
		signBlock.setType(Material.OAK_SIGN, false);
		if (signBlock.getBlockData() instanceof Rotatable rotatable) {
			rotatable.setRotation(openFace);
			signBlock.setBlockData(rotatable, false);
		}
		if (enchantingSite == EnchantingSite.BASE) writeSign(signBlock, ChatColor.GOLD + "Taula", ChatColor.GOLD + "d'encantar", ChatColor.GRAY + "paga amb or", "");
		else writeSign(signBlock, ChatColor.LIGHT_PURPLE + "Altar de la", ChatColor.LIGHT_PURPLE + "capçada", ChatColor.GRAY + "encanteris", ChatColor.GRAY + "forts");
		if (signBlock.getState() instanceof Sign sign) {
			sign.setWaxed(true);
			sign.update(true, false);
		}
		tableSigns.put(signBlock, table);
	}

	/** Once per player per match, a line above the hotbar the first time they stand near a table, a plate or the bridge button. */
	private void tickHints() {
		if (!JocEnMarxa()) return;
		for (Player p : getPlayers()) {
			Location at = p.getLocation();
			for (Map.Entry<Block, EnchantingTableRegistration> table : enchantingTables.entrySet()) {
				if (!isNear(at, table.getKey())) continue;
				if (table.getValue().enchantingSite() == EnchantingSite.BASE) hint(p, "taula");
				else hint(p, "altar");
			}
			for (ControlPoint point : controlPoints) {
				for (Block plate : point.plateByTeam.values()) if (isNear(at, plate)) hint(p, "placa");
			}
			for (Block button : bridgeButtons.keySet()) if (isNear(at, button)) hint(p, "botó");
			for (Block detonator : teamByDetonator.keySet()) if (isNear(at, detonator)) hint(p, "detonador");
			for (UUID shopkeeper : shopkeepers.keySet()) {
				Entity villager = Bukkit.getEntity(shopkeeper);
				if (villager != null && villager.getWorld() == world && villager.getLocation().distance(at) <= HINT_DISTANCE) hint(p, "paradista");
			}
			for (Location chestSpot : pMapaActual().ObtenirLocations("cofres", world)) {
				if (chestSpot.getBlock().getType() == Material.CHEST && isNear(at, chestSpot.getBlock())) hint(p, "cofre");
			}
			Equip team = obtenirEquip(p);
			if (team == null) continue;
			for (Equip enemy : Equips) {
				if (enemy == team) continue;
				for (Vector core : coresByTeam.getOrDefault(enemy.getId(), Set.of())) {
					if (core.toLocation(world).add(0.5, 0.5, 0.5).distance(at) > VAULT_HINT_DISTANCE) continue;
					hint(p, "cambra");
					break;
				}
			}
		}
	}

	private static boolean isNear(Location at, Block block) {
		return block.getLocation().add(0.5, 0.5, 0.5).distance(at) <= HINT_DISTANCE;
	}

	private void hint(Player p, String key) {
		if (!hintsShown.computeIfAbsent(p.getUniqueId(), id -> new HashSet<>()).add(key)) return;
		PaperMessages.sendActionBar(p, ChatColor.YELLOW + GUIDE.line("pista " + key), HINT_TICKS);
	}

	/** A click on a table: never the vanilla screen; the team's tables refuse the enemy. */
	private void openEnchantingTable(Player p, EnchantingTableRegistration table) {
		if (!JocEnMarxa()) return;
		Equip team = obtenirEquip(p);
		if (team == null) return;
		if (table.team() != null && table.team() != team.getId()) {
			rejectEnemyElement(p);
			return;
		}
		EnchantingSite enchantingSite = table.enchantingSite();
		ItemStack item = p.getInventory().getItemInMainHand();
		List<EnchantOffer> offers = new ArrayList<>();
		for (EnchantOffer enchantment : EnchantOffer.values()) if (enchantment.nextLevel(enchantingSite, item) > 0) offers.add(enchantment);
		if (offers.isEmpty()) {
			playEnchantRefused(p);
			boolean enchantableItem = item == null || item.getType() == Material.AIR || Arrays.stream(EnchantOffer.values()).noneMatch(e -> e.enchantment.canEnchantItem(item));
			p.sendMessage(ChatColor.GRAY + (enchantableItem
					? "Agafa a la mà el que vols encantar: espasa, arc, pic o armadura."
					: enchantingSite == EnchantingSite.BASE ? "Per millorar més, puja als altars de la capçada." : "L'altar ja ha donat tot el que pot a això."));
			return;
		}
		IconMenu menu = new IconMenu(enchantingSite.title, 9, event -> {
			int position = event.getPosition();
			if (position < offers.size()) enchant(event.getPlayer(), enchantingSite, offers.get(position));
			event.setWillClose(true);
		});
		for (EnchantOffer enchantment : offers) {
			int level = enchantment.nextLevel(enchantingSite, item);
			ArrayList<String> info = new ArrayList<>();
			info.add(ChatColor.GRAY + enchantment.description);
			info.add(ChatColor.WHITE + "Preu: " + ChatColor.GOLD + enchantment.price(level) + " or");
			menu.setOption(offers.indexOf(enchantment), new ItemStack(Material.ENCHANTED_BOOK), ChatColor.YELLOW + enchantment.name + " " + ROMAN_LEVELS[level], info);
		}
		menu.open(p);
	}

	/** A table is not a villager: a refused enchantment fizzles, a hiss and a low note, instead of the villager's grunt. */
	private static void playEnchantRefused(Player p) {
		p.playSound(p.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.8F, 0.7F);
		p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.6F, 0.5F);
	}

	/** The item is read again at the click: the hand may have changed while the menu was open. */
	private void enchant(Player p, EnchantingSite enchantingSite, EnchantOffer enchantment) {
		ItemStack item = p.getInventory().getItemInMainHand();
		int level = enchantment.nextLevel(enchantingSite, item);
		int shortfall = level == 0 ? 0 : enchantment.price(level) - availableGold(p);
		if (!JocEnMarxa() || level == 0 || shortfall > 0 || !spendGold(p, enchantment.price(level))) {
			playEnchantRefused(p);
			if (shortfall > 0) PaperMessages.sendActionBar(p, ChatColor.RED + "Et falten " + shortfall + " or", 60);
			return;
		}
		item.addUnsafeEnchantment(enchantment.enchantment, level);
		p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1F, 1F);
		// An altar purchase is heard, softly, by the whole map: a strong player is up on the canopy.
		if (enchantingSite == EnchantingSite.CANOPY) for (Player v : world.getPlayers()) if (v != p) v.playSound(v.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.4F, 1F);
		p.sendMessage(ChatColor.LIGHT_PURPLE + enchantment.name + " " + ROMAN_LEVELS[level] + ChatColor.WHITE + " (" + ChatColor.GOLD + "-" + enchantment.price(level) + " or" + ChatColor.WHITE + ")");
		updateScoreBoard(p);
	}

	//---------- The bridge ----------

	/**
	 * Finds the plank columns each moat's bridge is missing: from the seed block over
	 * the gap, along x in both directions while the deck level is air over water, three
	 * wide. Ordered from the far bank toward the base the moat protects, which is the
	 * order the attackers want it built in.
	 */
	private void registerBridges() {
		for (Equip e : Equips) {
			Location seed = pMapaActual().ExisteixPropietat("PontMoat" + e.getId())
					? pMapaActual().ObtenirLocation("PontMoat" + e.getId(), world)
					: BRIDGE_MOAT_2013[Math.min(e.getId(), BRIDGE_MOAT_2013.length - 1)].toLocation(world);
			// Each of the three rows is walked on its own: the 2013 decks are not square,
			// one row's planks start a block further out than its neighbours'.
			Map<Integer, List<Block>> columnsByX = new java.util.TreeMap<>();
			for (int dz = -1; dz <= 1; dz++) {
				for (int direction : new int[] { -1, 1 }) {
					for (int step = direction == 1 ? 1 : 0; step < 20; step++) {
						int x = seed.getBlockX() + direction * step;
						Block deck = world.getBlockAt(x, BRIDGE_HEIGHT, seed.getBlockZ() + dz);
						if (!isEmptyOverWater(deck)) break;
						columnsByX.computeIfAbsent(x, k -> new ArrayList<>()).add(deck);
					}
				}
			}
			List<List<Block>> columns = new ArrayList<>(columnsByX.values());
			Location base = e.getTeamSpawnLocation();
			columns.sort((a, b) -> Double.compare(b.get(0).getLocation().distance(base), a.get(0).getLocation().distance(base)));
			bridgesByMoat.put(e.getId(), columns);
			bridgeCharge.put(e.getId(), 0);
			if (columns.isEmpty()) {
				plugin.getLogger().warning(getGameName() + " " + getMapName() + ": no bridge gap found over the moat of base" + e.getId() + " around " + seed.toVector());
			} else {
				plugin.getLogger().info(getGameName() + " " + getMapName() + ": the moat of base" + e.getId() + " needs " + columns.size() + " bridge columns");
			}
		}
	}

	private static boolean isEmptyOverWater(Block deck) {
		if (!deck.isPassable() || deck.isLiquid()) return false;
		for (int dy = 1; dy <= 3; dy++) {
			Block blockBelow = deck.getRelative(0, -dy, 0);
			if (blockBelow.getType() == Material.WATER) return true;
			if (!blockBelow.isPassable()) return false;
		}
		return false;
	}

	private void chargeBridge(Equip e, int amount) {
		if (e == null || !JocEnMarxa()) return;
		int previousCharge = bridgeCharge.getOrDefault(e.getId(), 0);
		int newCharge = Math.min(MAX_BRIDGE_CHARGE, previousCharge + amount);
		if (newCharge == previousCharge) return;
		bridgeCharge.put(e.getId(), newCharge);
		if (newCharge == MAX_BRIDGE_CHARGE) {
			sendTeamMessage(e, ChatColor.AQUA + "Pont enemic a punt " + ChatColor.GRAY + "(activar des de la base)");
			for (Player p : e.getPlayers()) p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1.4F);
		}
		updateBridgeChargeDisplay(e);
	}

	private boolean bridgeCharged(Equip e) {
		return bridgeCharge.getOrDefault(e.getId(), 0) >= MAX_BRIDGE_CHARGE;
	}

	/** White squares fill quietly; the bar and its label turn aqua only when the bridge is ready. */
	/** True from the button press until the last plank of this team's bridge over the enemy moat is back. */
	private boolean bridgeDeployedBy(Equip e) {
		Equip enemy = obtenirEquipEnemic(e);
		return enemy != null && deployedBridges.contains(enemy.getId());
	}

	private String bridgeBar(Equip e) {
		if (bridgeDeployedBy(e)) return ChatColor.AQUA + "desplegat";
		int charge = bridgeCharge.getOrDefault(e.getId(), 0);
		ChatColor completedPrefix = bridgeCharged(e) ? ChatColor.AQUA : ChatColor.WHITE;
		return completedPrefix + FULL_SQUARE.repeat(charge) + ChatColor.DARK_GRAY + EMPTY_SQUARE.repeat(MAX_BRIDGE_CHARGE - charge);
	}

	private String bridgeLabel(Equip e, String text) {
		return (bridgeCharged(e) || bridgeDeployedBy(e) ? ChatColor.AQUA : ChatColor.GRAY) + text;
	}

	private void updateBridgeChargeDisplay(Equip e) {
		Block sign = bridgeSigns.get(e.getId());
		if (sign != null) writeSign(sign, bridgeLabel(e, "Pont enemic"), bridgeBar(e), "", "");
		lightLamps(e);
		for (Player p : e.getPlayers()) updateScoreBoard(p);
	}

	private void pressBridgeButton(Player p, Equip e) {
		if (!JocEnMarxa()) return;
		if (obtenirEquip(p) != e) {
			rejectEnemyElement(p);
			return;
		}
		Equip enemy = obtenirEquipEnemic(e);
		if (!bridgeCharged(e) || deployedBridges.contains(enemy.getId())) {
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1F, 1F);
			return;
		}
		bridgeCharge.put(e.getId(), 0);
		updateBridgeChargeDisplay(e);
		deployBridge(enemy, e);
	}

	/** Builds the bridge over the enemy's moat one column at a time, keeps it, then takes it back the same way. */
	private void deployBridge(Equip moatOf, Equip attacker) {
		List<List<Block>> columns = bridgesByMoat.getOrDefault(moatOf.getId(), List.of());
		if (columns.isEmpty()) return;
		deployedBridges.add(moatOf.getId());
		updateBridgeChargeDisplay(attacker);
		sendGlobalMessage(attacker.getChatColor() + "L'equip " + attacker.getAdjectiu() + ChatColor.WHITE + " desplega el pont sobre el fossat " + moatOf.getAdjectiuColored() + ChatColor.WHITE + ".");
		sendGlobalSound(Sound.BLOCK_PISTON_EXTEND, 1F, 0.6F);
		for (int i = 0; i < columns.size(); i++) {
			List<Block> column = columns.get(i);
			scheduleGameplayTask(() -> setBridgeColumn(column, true), BRIDGE_TICKS_PER_COLUMN * (i + 1));
		}
		long totalDeploymentTicks = BRIDGE_TICKS_PER_COLUMN * columns.size();
		for (int i = 0; i < columns.size(); i++) {
			List<Block> column = columns.get(columns.size() - 1 - i);
			scheduleGameplayTask(() -> setBridgeColumn(column, false), totalDeploymentTicks + BRIDGE_DEPLOYED_TICKS + BRIDGE_TICKS_PER_COLUMN * (i + 1));
		}
		scheduleGameplayTask(() -> {
			deployedBridges.remove(moatOf.getId());
			updateBridgeChargeDisplay(attacker);
		}, totalDeploymentTicks + BRIDGE_DEPLOYED_TICKS + BRIDGE_TICKS_PER_COLUMN * (columns.size() + 1));
	}

	private void setBridgeColumn(List<Block> column, boolean place) {
		if (!JocEnMarxa()) return;
		for (Block b : column) b.setType(place ? Material.OAK_PLANKS : Material.AIR);
		Location soundLocation = column.get(0).getLocation();
		world.playSound(soundLocation, place ? Sound.BLOCK_PISTON_EXTEND : Sound.BLOCK_PISTON_CONTRACT, 1F, 1F);
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
				if (candidate.center().distance(plate.getLocation()) <= CONTROL_POINT_RADIUS) point = candidate;
			}
			if (point == null) {
				point = new ControlPoint();
				controlPoints.add(point);
			}
			point.plateByTeam.put(team.getId(), plate);
		}
		for (ControlPoint point : controlPoints) {
			Location center = point.center();
			int cy = center.getBlockY();
			for (Block lamp : blocksBetween(center.getBlockX() - CONTROL_POINT_LAMP_REACH, center.getBlockZ() - CONTROL_POINT_LAMP_REACH, center.getBlockX() + CONTROL_POINT_LAMP_REACH, center.getBlockZ() + CONTROL_POINT_LAMP_REACH, m -> m == Material.REDSTONE_LAMP)) {
				if (lamp.getY() < cy - 2 || lamp.getY() > cy + CONTROL_POINT_TOWER_HEIGHT) continue;
				int side = point.sideOf(lamp);
				if (lamp.getY() > cy + CONTROL_POINT_ROOM_HEIGHT && side != 0) {
					for (Equip e : Equips) if (point.sideOfTeam(e.getId()) == side) towerLampsByTeam.computeIfAbsent(e.getId(), id -> new ArrayList<>()).add(lamp);
				} else if (side == 0 || Math.abs(lamp.getX() - center.getBlockX()) <= 1) {
					point.wallLamps.add(lamp);
				} else {
					point.lamps.add(lamp);
				}
			}
			point.wallLamps.sort((l1, l2) -> Integer.compare(l1.getX(), l2.getX()));
			showControlPoint(point);
			plugin.getLogger().info(getGameName() + " " + getMapName() + ": control point at " + center.toVector() + " with plates " + point.plateByTeam.keySet() + ", " + point.wallLamps.size() + " wall lamps and " + point.lamps.size() + " side lamps");
		}
		for (List<Block> tower : towerLampsByTeam.values()) tower.sort(BOTTOM_ROW_FIRST);
		for (Equip e : Equips) {
			Block sign = bridgeSigns.get(e.getId());
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
				if (refusedOnPlate.add(id)) rejectEnemyElement(p);
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
				chargeBridge(e, 1);
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
				world.playSound(point.center(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.5F, 1.0F + 0.25F * point.progress);
			}
			return;
		}
		if (standing.size() > 1) return;
		boolean challenging = point.chargingTeam != null && (point.owner == null || !point.owner.equals(point.chargingTeam));
		if (!challenging) return;
		point.progress--;
		// The same note stepping back down as the challenge fades.
		world.playSound(point.center(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.35F, 0.9F + 0.25F * Math.max(0, point.progress));
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
		giveGold(captor, GOLD_PER_CAPTURE);
		chargeBridge(team, SQUARES_PER_CAPTURE);
		sendGlobalMessage(team.getChatColor() + captor.getName() + ChatColor.GRAY + " ha capturat el punt de control (" + pointsHeldBy(team) + "/" + controlPoints.size() + ") " + ChatColor.WHITE + "(" + ChatColor.GOLD + "+" + GOLD_PER_CAPTURE + ChatColor.WHITE + ")");
		// In the room a level-up chime; the capturing team hears a bell wherever they are, the team that lost it a low bass.
		world.playSound(point.center(), Sound.ENTITY_PLAYER_LEVELUP, 0.6F, 1.5F);
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
		int charge = bridgeCharge.getOrDefault(e.getId(), 0);
		lightBar(lampsByTeam.getOrDefault(e.getId(), List.of()), charge);
		lightBar(towerLampsByTeam.getOrDefault(e.getId(), List.of()), charge);
	}

	private static void lightBar(List<Block> lamps, int charge) {
		if (lamps.isEmpty()) return;
		int lit = charge * lamps.size() / MAX_BRIDGE_CHARGE;
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
			for (Block block : blocksBetween(base.getBlockX() - SIGN_RADIUS, base.getBlockZ() - SIGN_RADIUS, base.getBlockX() + SIGN_RADIUS, base.getBlockZ() + SIGN_RADIUS, m -> m == Material.DISPENSER || m == Material.DROPPER)) {
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

	public void giveGold(Player plyr, int gold) {
		if (gold <= 0) return;
		giveOrDrop(plyr, new ItemStack(Material.GOLD_NUGGET, gold));
		pPlayer(plyr).IncrementarPropietat("Or", gold);
		consolidateGold(plyr);
		updateScoreBoard(plyr);
	}
	public void giveGoldToTeam(ArrayList<Player> team, int gold, Boolean divide) {
		if (divide) { gold = (int) Math.ceil(gold / team.size()); }
		for (Player p : team) {
			giveGold(p, gold);
		}
	}
	public void giveGoldToTeam(ArrayList<Player> team, int gold, Boolean divide, String text, Boolean broadcast) {
		if (divide) { gold = (int) Math.ceil(gold / team.size()); }
		for (Player p : team) {
			giveGold(p, gold, text, broadcast);
		}
	}
	public void giveGoldToAll(int gold) {
		for (Player p : getPlayers()) {
			giveGold(p, gold);
		}
	}
	public void giveGoldToAll(int gold, String text, Boolean broadcast) {
		for (Player p : getPlayers()) {
			giveGold(p, gold, text, broadcast);
		}
	}
	public void giveGold(Player plyr, int gold, String text, Boolean broadcast) {
		giveGold(plyr, gold);
		String message = text + ChatColor.WHITE + "(" + ChatColor.GOLD + "+" + gold + ChatColor.WHITE + ")";
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
	public void consolidateGold(Player p) {
		Inventory inv = p.getInventory();
		int ingots = 0;
		for (ItemStack d : inv.getContents()) if (d != null && d.getType() == Material.GOLD_INGOT) ingots += d.getAmount();
		if (ingots > 0) {
			inv.remove(Material.GOLD_INGOT);
			giveOrDrop(p, new ItemStack(Material.GOLD_NUGGET, ingots * 10));
		}
		updateGoldBalance(p);
	}

	private void updateGoldBalance(Player player) {
		if (!JocEnMarxa()) return;
		Seat seat = seatOf(player);
		Equip team = obtenirEquip(player);
		if (player.getWorld() != world || team == null || seat == null || seat.getRole() != Seat.Role.PLAYER
				|| (!seat.isOccupied() && !seat.isDropped())) {
			goldScore.removePlayer(player.getUniqueId());
			return;
		}
		goldScore.updateBalance(player.getUniqueId(), team.getId(), heldGold(player));
	}

	private long heldGold(Player player) {
		ItemStack cursor = player.getItemOnCursor();
		long cursorGold = cursor.getType() == Material.GOLD_NUGGET ? cursor.getAmount()
				: cursor.getType() == Material.GOLD_INGOT ? 10L * cursor.getAmount() : 0;
		return availableGold(player) + cursorGold;
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
		removeOwnedArchers(killed);
		updateGoldBalance(killed);
		super.onPlayerDeath(evt, killed);
		cancelStarCharge(killed);
		boolean exploded = false;
		Player player = killed;
		Location location = player.getLocation();
		CombatCredit credit = combatCredits.remove(player.getUniqueId());
		Integer lastHitSecond = lastHitSecondByPlayer.remove(player.getUniqueId());
		boolean recentCredit = credit != null && segonsTranscorreguts() - credit.second() <= LAST_HIT_CREDIT_SECONDS;
		Minion minionKiller = recentCredit ? credit.minion() : minionThatKilled(player);
		Player killer = recentCredit && minionKiller != null ? Bukkit.getPlayer(credit.ownerId()) : player.getKiller();
		if (killer == null) {
			Long milliseconds = Calendar.getInstance().getTimeInMillis();
			Long previousMilliseconds = Long.parseLong(pTemp().ObtenirPropietat("Explo"));
			if (milliseconds - previousMilliseconds <= 1000 * 2) {
				String attackerName = pTemp().ObtenirPropietat("ExploPlayer");
				Player kill = plugin.getServer().getPlayer(attackerName);
				if (kill != null) {
					killer = kill;
					exploded = true;
				}
			}
		}
		if (killer == null) {
			Player lastDamager = getPlayerInfo(player).getLastDamager();
			Integer hitSecond = lastHitSecond;
			if (lastDamager != null && hitSecond != null && segonsTranscorreguts() - hitSecond <= LAST_HIT_CREDIT_SECONDS) {
				killer = lastDamager;
			}
		}
		if (killer == null || killer == player) {
			evt.setDeathMessage(killedByGuardian(player)
					? ChatColor.AQUA + GUARDIAN_TITLE + ChatColor.WHITE + " ha matat a " + player.getName()
					: player.getName() + " s'ha mort tot sol");
			pTemp().EstablirPropietat(player.getName() + "Morts", "0");
			pPlayer(player).IncrementarPropietat("Morts");
			updateScoreBoards();
			return;
		}
		int killerDeaths = pTemp().ObtenirPropietatInt(killer.getName() + "Morts");
		int victimDeaths = pTemp().ObtenirPropietatInt(player.getName() + "Morts");
		int gold = 5 + (victimDeaths * 1);
		if (victimDeaths > 4) {
			gold = gold + 2;
		}
		if (victimDeaths > 6) {
			gold = gold + 5;
		}
		if (victimDeaths > 10) {
			gold = gold + 5;
		}
		if (exploded) {
			gold = gold + 1;
		}
		// The carried-item bounties count before the caps, so the ceiling is the 25 and 30 they name.
		if (player.getInventory().contains(Material.DIAMOND_PICKAXE)) {
			gold = gold + 1;
		}
		if (player.getInventory().contains(Material.GOLD_BLOCK)) {
			gold = gold + 1;
		}
		if (gold >= 25) {
			gold = 25;
		}
		boolean goldenPickaxeKill = minionKiller == null && killer.getInventory().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE;
		if (goldenPickaxeKill) {
			gold = gold * 3;
			if (gold >= 30) {
				gold = 30;
			}
		}
		if (areAllies(player, killer)) {
			gold = 0;
		} else {
			if (!exploded) killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 3));
			chargeBridge(obtenirEquip(killer), SQUARES_PER_KILL);
		}
		Inventory inventory = killer.getInventory();
		giveGold(killer, gold);

		evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + "(" + ChatColor.GOLD + "+" + gold + ChatColor.WHITE + ")");
		if (exploded) {
			evt.setDeathMessage(killer.getName() + " ha fet explotar a " + player.getName() + "(" + ChatColor.GOLD + "+" + gold + ChatColor.WHITE + ")");
			pTemp().IncrementarPropietat(killer.getName() + "MortsExplotats");
		}
		if (goldenPickaxeKill) {
			evt.setDeathMessage(killer.getName() + " ha matat amb el pic d'or a " + player.getName() + "(" + ChatColor.GOLD + "+" + gold + ChatColor.WHITE + ")(" + ChatColor.GOLD + "x3" + ChatColor.WHITE + ")");
		}
		if (minionKiller instanceof SnowmanMinion snowman) {
			evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + " amb un " + snowman.noun() + " de " + snowman.kind().label + " (" + ChatColor.GOLD + "+" + gold + ChatColor.WHITE + ")");
		} else if (minionKiller instanceof LaneMinion skeleton) {
			int deaths = witherDeaths.merge(player.getUniqueId(), 1, Integer::sum);
			evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + " amb un " + skeleton.kind().label().toLowerCase() + " (" + ChatColor.GOLD + "+" + gold + ChatColor.WHITE + ")" + ChatColor.DARK_RED + " -1 cor màxim (" + deaths + ")");
		}
		if (Ability.hasAbility(plugin, this, player, AbilityType.CREEPER)) {
			float explosionPower = 0.8F + (victimDeaths / 2);
			world.createExplosion(location.getX(), location.getY(), location.getZ(), explosionPower, false, false);
		}
		pTemp().EstablirPropietat(killer.getName() + "Morts", Integer.toString(killerDeaths + 1));
		pTemp().EstablirPropietat(player.getName() + "Morts", "0");
		if (gold != 0) {
			pPlayer(killer).IncrementarPropietat("Assassinats");
			Equip killerTeam = obtenirEquip(killer);
			if (killerTeam != null) {
				killsByTeam.merge(killerTeam.getId(), 1, Integer::sum);
				writeKillsSigns();
			}
			if (suddenDeath) raiseWitherSkeleton(killer);
			raiseSkeletonArcher(killer);
		}
		pPlayer(player).IncrementarPropietat("Morts");
		if (player.getInventory().contains(Material.DIAMOND_PICKAXE)) {
			player.getInventory().remove(Material.DIAMOND_PICKAXE);
			evt.setDeathMessage(killer.getName() + " ha matat a " + player.getName() + " que tenia pic de diamant!(" + ChatColor.GOLD + "+" + gold + ChatColor.WHITE + ")");
			killer.sendMessage(ChatColor.GOLD + "+3 Or passiu! (pic d'or)");
			killer.sendMessage(ChatColor.GOLD + "Matar un enemic amb el pic d'or et dona x3 or");
			inventory.addItem(GameItem.describe(new ItemStack(Material.GOLDEN_PICKAXE, 1)));
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
		if (upgradeSigns != null && evt.getClickedBlock() != null && evt.getAction() == Action.RIGHT_CLICK_BLOCK
				&& evt.getHand() == EquipmentSlot.HAND && upgradeSigns.interact(plyr, evt.getClickedBlock())) {
			evt.setCancelled(true);
			return;
		}
		if (watchtowerLaunchers != null && evt.getClickedBlock() != null) {
			if (evt.getAction() == Action.PHYSICAL && watchtowerLaunchers.isPlate(evt.getClickedBlock())) {
				evt.setCancelled(true);
				return;
			}
			if (evt.getAction() == Action.RIGHT_CLICK_BLOCK && evt.getHand() == EquipmentSlot.HAND
					&& watchtowerLaunchers.interact(plyr, evt.getClickedBlock())) {
				evt.setCancelled(true);
				return;
			}
		}
		if (evt.getAction() == Action.PHYSICAL && evt.getClickedBlock() != null
				&& shopPortals.values().stream().anyMatch(portal -> portal.entrance().getBlock().equals(evt.getClickedBlock()))) {
			evt.setCancelled(true);
			return;
		}
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
			Block clicked = evt.getClickedBlock();
			if (clicked.getType() == Material.DISPENSER || clicked.getType() == Material.DROPPER) {
				// The 2013 currency dispensers: never fired, never opened.
				evt.setCancelled(true);
				return;
			}
			if (clicked.getType() == Material.ENCHANTING_TABLE || tableSigns.containsKey(clicked)) {
				evt.setCancelled(true);
				EnchantingTableRegistration table = enchantingTables.get(tableSigns.getOrDefault(clicked, clicked));
				if (table != null && evt.getHand() == EquipmentSlot.HAND) openEnchantingTable(plyr, table);
				return;
			}
			if (Tag.BUTTONS.isTagged(clicked.getType()) || clicked.getType() == Material.LEVER) {
				// The map's 2013 redstone stays dead: the plugin does what the signs promise.
				evt.setCancelled(true);
				Integer buttonTeamId = bridgeButtons.get(clicked);
				if (buttonTeamId != null && evt.getHand() == EquipmentSlot.HAND) pressBridgeButton(plyr, obtenirEquip(buttonTeamId));
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
				consumeOne(stack);
			}
		}
		if (stack.getType() == Material.MAGMA_CREAM) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.setFireTicks(3 * 20);
			}
			plyr.sendMessage("Has cremat a l'equip enemic.");
			consumeOne(stack);
		}
		if (stack.getType() == Material.STRING) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 15 * 20, 2, false), true);
			}
			plyr.sendMessage("Has alentit a l'equip enemic un 40% durant 15 segons.");
			consumeOne(stack);
		}
		if (stack.getType() == Material.SPIDER_EYE) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 8 * 20, 1, false), true);
			}
			plyr.sendMessage("Has enverinat a l'equip enemic durant 8 segons.");
			consumeOne(stack);
		}
		if (stack.getType() == Material.PAPER) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 25 * 20, 5, false), true);
			}
			plyr.sendMessage("Has esverat a l'equip enemic durant 25 segons.");
			consumeOne(stack);
		}
		if (stack.getType() == Material.COCOA_BEANS) {
			for (Player p : obtenirEquipEnemic(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6 * 20, 100, false), true);
			}
			plyr.sendMessage("Tots a l'aigua!");
			consumeOne(stack);
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
			consumeOne(stack);
		}
		if (stack.getType() == Material.SUGAR) {
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 2, false), true);
			}
			plyr.sendMessage("Has augmentat la velocitat del teu equip durant 15 segons.");
			consumeOne(stack);
		}
		if (stack.getType() == Material.GLASS) {
			for (Player p : obtenirEquip(plyr).getPlayers()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 15 * 20, 1, false), true);
			}
			plyr.sendMessage("El teu equip és invisible durant 15 segons.");
			consumeOne(stack);
		}
		if (stack.getType() == Material.BLAZE_POWDER) {
			Player targetPlayer = null;
			for (Entity e : plyr.getNearbyEntities(25, 40, 25)) {
				if (e instanceof Player p && areEnemies(p, plyr)) {
					if (targetPlayer == null) {
						targetPlayer = p;
					} else if (plyr.getLocation().distance(p.getLocation()) < plyr.getLocation().distance(targetPlayer.getLocation()) && p.getFireTicks() == 0) {
						targetPlayer = p;
					}
				}
			}
			if (targetPlayer != null) {
				int kills = pTemp().ObtenirPropietatInt(plyr.getName() + "Morts");
				targetPlayer.setFireTicks((5 + kills + (plyr.getLevel() / 2)) * 20);
				consumeOne(stack);
				pTemp().EstablirPropietat("LastIgnitePlayerVictim", targetPlayer.getName());
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
		boolean hero = evt.getEntity() instanceof Snowball ball && GameItem.from(ball.getItem()) == GameItem.ENCHANTED_SNOWBALL;
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
		Block center = around.getBlock();
		for (int ring = 0; ring <= STANDING_SPOT_SEARCH_RADIUS; ring++) {
			for (int dy = 0; dy <= STANDING_SPOT_SEARCH_HEIGHT; dy++) {
				for (int sign : dy == 0 ? new int[] {1} : new int[] {1, -1}) {
					for (int dx = -ring; dx <= ring; dx++) {
						for (int dz = -ring; dz <= ring; dz++) {
							if (Math.max(Math.abs(dx), Math.abs(dz)) != ring) continue;
							Block candidate = center.getRelative(dx, dy * sign, dz);
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
	 * Snowballs deal one heart of damage. An armed cage traps the victim and resets the
	 * snowman's head; other hits shove, add fire for magma snowmen, and charge the cage.
	 * Recently freed victims cannot charge or trigger another cage during their grace.
	 * JocEquips credits the owner; this hook records when the hit occurred.
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
		lastHitSecondByPlayer.put(victim.getUniqueId(), now);
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
		return attackingMinion(cause.getDamager());
	}

	private void removeOwnedArchers(Player owner) {
		for (Minion minion : minionsOf(owner)) if (minion instanceof SkeletonArcherMinion) discharge(minion);
		removeMinionShots(owner, SkeletonArcherMinion.class);
	}

	private void raiseSkeletonArcher(Player owner) {
		Equip team = obtenirEquip(owner);
		if (team == null || teamUpgrades == null || !JocEnMarxa() || owner.isDead()
				|| !owner.isOnline() || isSpectator(owner) || owner.getGameMode() == GameMode.SPECTATOR
				|| !teamUpgrades.has(team.getId(), TeamUpgrades.Upgrade.ARCHERS)) return;
		long livingArchers = minionsOf(team).stream().filter(minion -> minion instanceof SkeletonArcherMinion && minion.isAlive()).count();
		if (livingArchers >= SkeletonArcherMinion.TEAM_CAP) return;
		Lane lane = snowmanLane(team);
		Location spot = standingSpotNear(team.getTeamSpawnLocation());
		if (spot == null) spot = team.getTeamSpawnLocation();
		enlist(new SkeletonArcherMinion(this, team, owner, lane,
				teamUpgrades.has(team.getId(), TeamUpgrades.Upgrade.ARMOR)), spot);
		world.spawnParticle(Particle.SOUL, spot.clone().add(0, 1, 0), 15, 0.3, 0.5, 0.3, 0.02);
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
		GameItem.describe(star);
	}

	/** Dying, or leaving, while charging: the charge is lost and the star falls where the player stood. */
	private void cancelStarCharge(Player plyr) {
		Integer task = starChargeTasks.remove(plyr.getUniqueId());
		if (task == null) return;
		Bukkit.getScheduler().cancelTask(task);
		plyr.setGlowing(false);
		for (ItemStack item : plyr.getInventory().getContents()) {
			if (item == null || item.getType() != Material.NETHER_STAR) continue;
			consumeOne(item);
			world.dropItemNaturally(plyr.getLocation(), GameItem.NETHER_STAR.newItem());
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
		sendGlobalMessage(ChatColor.DARK_RED + "Mort sobtada: " + ChatColor.WHITE + "cada baixa aixeca un esquelet wither.");
		updateScoreBoards();
	}

	/** Three seconds at the start, one more every two minutes, twelve at most: at minute fifteen the dead wait long enough for a breach. */
	@Override
	protected int respawnWaitSeconds(Player p) {
		return Math.min(RESPAWN_WAIT_MAX_SECONDS, RESPAWN_WAIT_BASE_SECONDS + segonsTranscorreguts() / 60 / RESPAWN_WAIT_MINUTES_PER_EXTRA_SECOND);
	}

	@Override
	protected void onRespawnWaitOver(Player p) {
		tidySoon(p);
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
	private void raiseWitherSkeleton(Player killer) {
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
		lastHitSecondByPlayer.put(victim.getUniqueId(), segonsTranscorreguts());
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
		boolean spawnProtected = false;
		for (Equip e : Equips) {
			if (loc.distance(e.getTeamSpawnLocation()) <= 5) spawnProtected = true;
		}
		if (player.getLocation().getBlockY() >= 49 && entity.getTicksLived() > 5 && !spawnProtected) {
			Inventory inv = player.getInventory();
			if (inv.contains(Material.FIREWORK_STAR)) {
				pTemp().EstablirPropietat("Explo", Long.toString(Calendar.getInstance().getTimeInMillis()));
				pTemp().EstablirPropietat("ExploPlayer", player.getName());
				removeOne(inv, Material.FIREWORK_STAR);
				int explosionDeaths = pTemp().ObtenirPropietatInt(player.getName() + "MortsExplotats");
				int deaths = pTemp().ObtenirPropietatInt(player.getName() + "Morts");

				float explosivePower = 3.25F;
				explosivePower = explosivePower + (0.12F * explosionDeaths);
				explosivePower = explosivePower + (0.28F * deaths);
				float mult = ((float) player.getHealth()) / ((float) player.getMaxHealth());
				explosivePower = explosivePower * mult;
				if (inv.contains(Material.NETHER_STAR)) {
					explosivePower = explosivePower + 1F;
					explosivePower = explosivePower + (explosivePower * 1.012F);
				}
				if (inv.contains(Material.GOLDEN_SWORD)) {
					explosivePower = explosivePower + (explosivePower * 0.20F);
				}
				if (Ability.hasAbility(plugin, this, player, AbilityType.PYROTECHNIC)) {
					explosivePower = explosivePower + (explosivePower * 0.35F);
				}
				world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), explosivePower, false, false);
				if (!pTemp().ObtenirPropietat("ForçaExplo").equals(Float.toString(explosivePower))) {
					player.sendMessage("Força de les fletxes explosives: " + Float.toString(explosivePower));
					pTemp().EstablirPropietat("ForçaExplo", Float.toString(explosivePower));
				}
				world.dropItem(loc, new ItemStack(Material.GOLD_NUGGET, 1)).setVelocity(new Vector(0, 0, 0));
				//Automal
				double hp = player.getHealth();
				if (hp == 20) {
					player.sendMessage(ChatColor.GRAY + "Les fletxes explosives et costen 1 cor.");
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
			list.add(ChatColor.GOLD + "Or: " + availableGold(ply));
			if (pMapaActual().ExisteixPropietat("Golem")) {
				// Aqua only while it lives; grey and coarse while it is dead, so the line does not pull the eye.
				String status = guardianStatus();
				lastGuardianCountdownShown = status;
				list.add((liveGuardian() != null ? ChatColor.AQUA : ChatColor.GRAY) + "Guardià: " + status);
			}
			Equip team = obtenirEquip(ply);
			if (team != null) list.add(bridgeLabel(team, "Pont: ") + bridgeBar(team));
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
			RESISTANCE,
			COMMANDER,
			SWORDSMAN,
			ENHANCED_REGENERATION,
			ASSAULT,
			CREEPER,
			PERFECT_ARCHER,
			FROST_ARCHER,
			IMPACT_PROTECTION,
			PYROTECHNIC,
			STRONG_SKELETON,
			GRAVITY_CONTROL,
			DESTROYER,
			RANDOM;

			static AbilityType fromStoredName(String storedName) {
				try {
					return valueOf(storedName);
				} catch (IllegalArgumentException ignored) {
					return switch (storedName) {
						case "RESISTENCIA" -> RESISTANCE;
						case "COMANDANT" -> COMMANDER;
						case "ESPADATXI" -> SWORDSMAN;
						case "REGENERACIO_AUGMENTADA" -> ENHANCED_REGENERATION;
						case "ASSALT" -> ASSAULT;
						case "ARQUER_PERFECTE" -> PERFECT_ARCHER;
						case "ARQUER_DE_GEL" -> FROST_ARCHER;
						case "PROTECCIÓ_IMPACTE" -> IMPACT_PROTECTION;
						case "PIROTÈCNIC" -> PYROTECHNIC;
						case "ESQUELET_FORT" -> STRONG_SKELETON;
						case "CONTROL_GRAVETAT" -> GRAVITY_CONTROL;
						case "DESTRUCTOR" -> DESTROYER;
						default -> throw ignored;
					};
				}
			}
		}
		static ItemStack icon(lobby plugin, ObsidianDefenders j, Player plyr, AbilityType ability) {
			Material mat = Material.OAK_PLANKS;
			String title = "<Nom>";
			String description = "<Descripció>";
			String secondaryDescription = "<Descripció2>";
			boolean available = false;
			//-----------
			switch (ability) {
			case SWORDSMAN:
				mat = Material.IRON_SWORD;
				title = "Espadatxí";
				description = "Augmenta l'atac cada 6 cops d'espasa";
				secondaryDescription = "i els enemics volen pels aires";
				available = true;
				break;
			case ENHANCED_REGENERATION:
				mat = Material.OAK_SAPLING;
				title = "Regeneració augmentada";
				description = "x3 Regeneració passiva x2 cost de menjar";
				available = true;
				break;
			case RESISTANCE:
				mat = Material.DIAMOND;
				title = "Resistència";
				description = "Redueix el mal d'enemics un";
				secondaryDescription = "10% + 8% per enemic proper (8 blocs)";
				available = true;
				break;
			case PERFECT_ARCHER:
				mat = Material.BOW;
				title = "Arquer perfecte";
				description = "Cada 10 fletxes encertades,";
				secondaryDescription = "la fletxa rebota (8 blocs)";
				available = true;
				break;
			case ASSAULT:
				mat = Material.LEATHER_BOOTS;
				title = "Assalt";
				description = "El mal per caiguda es transfereix";
				secondaryDescription = "als enemics propers (7 blocs)";
				available = true;
				break;
			case STRONG_SKELETON:
				mat = Material.BONE;
				title = "Esquelet fort";
				description = "- 3 mal per caiguda";
				available = true;
				break;
			case PYROTECHNIC:
				mat = Material.FIREWORK_ROCKET;
				title = "Pirotècnic";
				description = "+ 35 % força explosions";
				available = true;
				break;
			case IMPACT_PROTECTION:
				mat = Material.IRON_CHESTPLATE;
				title = "Protecció d'impacte";
				description = "-50% mal rebut del golem de ferro";
				available = true;
				break;
			case GRAVITY_CONTROL:
				mat = Material.GOLDEN_BOOTS;
				title = "Control de la gravetat";
				description = "Duplica el mal per caiguda dels";
				secondaryDescription = "enemics atacats recentment (10s)";
				available = true;
				break;
			case RANDOM:
				mat = Material.BEDROCK;
				title = "Inmortalitat";
				description = "Ets inmortal i guanyes";
				secondaryDescription = "la partida en 5s ;) jaja";
				available = false;
				break;
			case FROST_ARCHER:
				mat = Material.ICE;
				title = "Arquer de gel";
				description = "Congela l'enemic que encertis";
				secondaryDescription = "cada 7 fletxes";
				available = true;
				break;

			case DESTROYER:
				mat = Material.DIAMOND_AXE;
				title = "Destructor";
				description = "Dismnueix la durabilitat de les";
				secondaryDescription = "armadures de l'enemic (5 + morts)";
				available = true;
				break;
			case COMMANDER:
				mat = Material.COMMAND_BLOCK;
				title = "Comandant";
				description = "Augmenta el mal dels aliats propers un";
				secondaryDescription = "12% i comença amb items addicionals";
				available = false;
				break;
			case CREEPER:
				mat = Material.TNT;
				title = "Creeper";
				description = "Explotes al morir(0.8F). La força augmenta";
				secondaryDescription = "0.5F per cada enemic que hagis matat.";
				available = true;
				break;
			default:
				break;

			}
			//-----------
			ItemStack item = new ItemStack(mat);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + title);
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.WHITE + description);
			if (!secondaryDescription.equals("<Descripció2>")) {
				lore.add(ChatColor.WHITE + secondaryDescription);
			}
			if (hasAbility(plugin, j, plyr, ability)) {
				lore.add(ChatColor.YELLOW + "Seleccionat!");
				item.setAmount(2);
			}
			if (!available) {
				lore.add(ChatColor.DARK_RED + "No funciona");
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;

		}
		public static void openSelectionInventory(lobby plugin, ObsidianDefenders j, Player plyr) {
			Inventory inv = Bukkit.getServer().createInventory(plyr, 9 * 2, "Selecciona habilitat");
			int i = 0;
			for (AbilityType ability : AbilityType.values()) {
				inv.setItem(i, icon(plugin, j, plyr, ability));
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
				lore.add(AbilityType.fromStoredName(j.pPlayer(plyr).ObtenirPropietat("Habilitat1")));
				lore.add(AbilityType.fromStoredName(j.pPlayer(plyr).ObtenirPropietat("Habilitat2")));
			} catch (Exception e) {
				randomAbilities(plugin, j, plyr);
				return getPlayerAbilityTypes(plugin, j, plyr);
			}
			return lore;
		}
		public static void randomAbilities(lobby plugin, ObsidianDefenders j, Player plyr) {
			int i = 1;
			while (i <= 2) {
				boolean done = false;
				while (done == false) {
					for (AbilityType ab : AbilityType.values()) {
						if (Utils.Possibilitat(10)) {
							setAbility(plugin, j, plyr, ab, i);
							done = true;
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
