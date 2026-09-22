package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import com.biel.lobby.Com;
import com.biel.lobby.localization.MessageArgument;
import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;
import com.biel.lobby.mapes.JocCooperatiu;
import com.biel.lobby.mapes.jocs.roborampage.utils.JunkDropPolicy;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.HelmetVariant;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.GroundRobotProfile;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.LiveDeathReward;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.RobotCounts;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.RobotType;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.WavePhase;
import com.biel.lobby.mapes.jocs.roborampage.utils.ScrapMaterial;
import com.biel.lobby.utilities.ScoreBoardUpdater;

/** Faithful, bounded reconstruction of the October 2015 cooperative scrap climb. */
public class RoboRampage extends JocCooperatiu {
    private static final int DEFAULT_TARGET_HEIGHT = 32;
    private static final int ARENA_RADIUS = 7;
    private static final int SPAWN_ATTEMPTS = 32;
    private static final int POST_GAME_TICKS = 20 * 10;
    private static final int SUPPLY_DURATION_TICKS = 20 * 12;
    private static final int SUPPLY_FIRE_SWEEP_INTERVAL_TICKS = 20;

    private final Map<UUID, RobotState> robots = new LinkedHashMap<>();
    private final Set<UUID> criticalKillCandidates = new HashSet<>();
    private final RandomGenerator random = ThreadLocalRandom.current();
    private JunkDropPolicy junkDropPolicy;
    private ScrapDropController scrapDrops;
    private TaserController tasers;
    private SupplyDropController supplyDrops;
    private ScaffoldController scaffolds;
    private DemolitionController demolition;
    private GhastMetalProjectileController ghastProjectiles;
    private int displayedScrapHeight;
    private int waveNumber;
    private int waveRobotQuota;
    private int robotsSpawnedThisWave;
    private int supplyTicksRemaining;
    private WavePhase wavePhase;
    private boolean climbFinished;

    private record RobotState(RobotType type, HelmetVariant helmet) {}

    private record SpawnRequest(RobotType type, Location location) {}

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player player) {
        ArrayList<ItemStack> startingItems = new ArrayList<>(List.of(new ItemStack(Material.DIAMOND_SWORD)));
        if (tasers != null) startingItems.add(tasers.createStartingTaser(player));
        if (demolition != null) startingItems.add(demolition.createIgniter());
        return startingItems;
    }

    @Override
    protected void donarItemsInicials(Player player) {
        super.donarItemsInicials(player);
        if (demolition != null) demolition.giveInitialCharges(player);
    }

    @Override
    protected boolean canBeDropped(ItemStack item, Player player) {
        return item.getType() != Material.FLINT_AND_STEEL
                && (tasers == null || !tasers.isStartingTaser(item)) && super.canBeDropped(item, player);
    }

    @Override
    protected void teletransportarTothom() {
        Location spawn = scrapDrops.safePlayerSpawn();
        for (Player player : getPlayers()) player.teleport(spawn);
    }

    @Override
    protected void customJocIniciat() {
        robots.clear();
        criticalKillCandidates.clear();
        climbFinished = false;
        displayedScrapHeight = 0;
        waveNumber = 1;
        wavePhase = WavePhase.ASSAULT;
        robotsSpawnedThisWave = 0;
        waveRobotQuota = RoboRampageRules.waveRobotQuota(waveNumber, getPlayers().size());
        junkDropPolicy = new JunkDropPolicy();
        scrapDrops = new ScrapDropController(world, battleCenter(), random);
        tasers = new TaserController(Com.getPlugin(), world, this::liveRobotMobs, this::isActiveWeaponUser);
        supplyDrops = new SupplyDropController(Com.getPlugin(), world, random, tasers);
        scaffolds = new ScaffoldController(
                world, battleCenter(), ARENA_RADIUS, this::targetHeight,
                this::liveRobotMobs, this::scaffoldDamageFor);
        demolition = new DemolitionController(world, battleCenter(), ARENA_RADIUS,
                this::targetHeight, this::isActiveWeaponUser, scaffolds);
        demolition.register(Com.getPlugin());
        ghastProjectiles = new GhastMetalProjectileController(world);
        scheduleGameplayRepeatingTask(scrapDrops::tick, 1, 1);
        scheduleGameplayRepeatingTask(tasers::tick, 1, 1);
        scheduleGameplayRepeatingTask(this::tickSupplyPhase, 1, 1);
        scheduleGameplayRepeatingTask(scaffolds::tickRobotDamage, 20, 20);
        scheduleGameplayRepeatingTask(demolition::pruneMissingCharges, 20, 20);
        scheduleGameplayRepeatingTask(ghastProjectiles::tick, 1, 1);
        scheduleGameplayRepeatingTask(this::keepFlyingRobotsReachable, 1, 1);
        scheduleGameplayRepeatingTask(this::runDirector, 1, 40);
        scheduleGameplayRepeatingTask(this::sampleProgress, 20, 20);
        announceWaveStart();
    }

    @Override
    protected void setCustomGameRules() {
        super.setCustomGameRules();
        setBlockBreakPlace(false);
    }

    @Override
    protected void customJocFinalitzat() {
        if (scrapDrops != null) {
            displayedScrapHeight = scrapDrops.settledHeight();
            Com.getPlugin().getLogger().info("Robo Rampage finished at " + displayedScrapHeight
                    + " m; robot scrap=" + scrapDrops.settledRobotBlocks()
                    + ", junk scrap=" + scrapDrops.settledJunkBlocks()
                    + ", unevenness=" + scrapDrops.unevenness());
            scrapDrops.close();
        }
        if (tasers != null) {
            tasers.close();
        }
        if (supplyDrops != null) supplyDrops.close();
        if (demolition != null) demolition.close();
        if (scaffolds != null) scaffolds.close();
        if (ghastProjectiles != null) ghastProjectiles.close();
        for (UUID robotId : new ArrayList<>(robots.keySet())) {
            Entity entity = Bukkit.getEntity(robotId);
            if (entity != null && entity.isValid()) entity.remove();
        }
        robots.clear();
        criticalKillCandidates.clear();
    }

    @Override
    public String getGameName() { return "RoboRampage"; }

    /** Cooperative reconstruction is deliberately unrated while match storage has one winner column. */
    @Override
    public double getEloBaseK() { return 0; }

    @Override
    public double getGameProgressETA() {
        return Math.min(1, displayedScrapHeight / (double) targetHeight());
    }

    @Override
    protected ArrayList<String> getGameInfo(Player player) {
        return new ArrayList<>(List.of(
                Messages.legacy(player, MessageKey.ROBO_RAMPAGE_INFO_SCRAP),
                Messages.legacy(player, MessageKey.ROBO_RAMPAGE_INFO_JUNK),
                Messages.legacy(player, MessageKey.ROBO_RAMPAGE_INFO_TASER),
                Messages.legacy(player, MessageKey.ROBO_RAMPAGE_INFO_TNT),
                Messages.legacy(player, MessageKey.ROBO_RAMPAGE_INFO_SUPPLIES),
                Messages.legacy(player, MessageKey.ROBO_RAMPAGE_INFO_ENEMIES),
                Messages.legacy(player, MessageKey.ROBO_RAMPAGE_INFO_GOAL,
                        MessageArgument.number("height", targetHeight()))));
    }

    @Override
    protected void updateScoreBoard(Player player) {
        super.updateScoreBoard(player);
        if (!JocIniciat) return;
        ScoreBoardUpdater.setTranslatedScoreBoard(player, "Robo Rampage", List.of(
                Messages.scoreboardMarker(MessageKey.ROBO_RAMPAGE_SCORE_SCRAP),
                Messages.scoreboardMarker(MessageKey.ROBO_RAMPAGE_SCORE_TARGET),
                Messages.scoreboardMarker(MessageKey.ROBO_RAMPAGE_SCORE_WAVE)), List.of(
                displayedScrapHeight, targetHeight(), waveNumber));
    }

    private void sampleProgress() {
        if (!JocEnMarxa() || scrapDrops == null) return;
        displayedScrapHeight = scrapDrops.settledHeight();
        if (wavePhase != WavePhase.SUPPLY && junkDropPolicy.sample(
                scrapDrops.settledBlockCount(), scrapDrops.unevenness(), scrapDrops.hasBulkyDropInFlight())
                && scrapDrops.dropCorrectiveCar()) {
            broadcast(MessageKey.ROBO_RAMPAGE_JUNK_INCOMING);
        }
        updateScoreBoards();
        for (Player player : getPlayers()) checkVictory(player);
    }

    private void runDirector() {
        if (!JocEnMarxa() || scrapDrops == null) return;
        removeMissingRobots();
        if (wavePhase == WavePhase.SUPPLY) return;
        if (wavePhase == WavePhase.CLEANUP) {
            if (robots.isEmpty()) beginSupplyPhase();
            return;
        }
        if (robotsSpawnedThisWave >= waveRobotQuota) {
            wavePhase = WavePhase.CLEANUP;
            if (robots.isEmpty()) beginSupplyPhase();
            return;
        }
        int playerCount = Math.max(1, getPlayers().size());
        RobotCounts counts = robotCounts();
        RoboRampageRules.chooseSpawn(
                RoboRampageRules.liveSpawnLimits(
                        scrapDrops.settledHeight(), playerCount, waveNumber), counts, random)
                .flatMap(type -> findSpawnLocation(type).map(location -> new SpawnRequest(type, location)))
                .ifPresent(request -> {
                    spawnRobot(request.type(), request.location());
                    robotsSpawnedThisWave++;
                });
    }

    private void beginSupplyPhase() {
        wavePhase = WavePhase.SUPPLY;
        supplyTicksRemaining = SUPPLY_DURATION_TICKS;
        extinguishArena();
        ghastProjectiles.clear();
        supplyDrops.dropWaveSupplies(battleCenter(), waveNumber, getPlayers());
        broadcast(MessageKey.ROBO_RAMPAGE_SUPPLY_INCOMING,
                MessageArgument.number("wave", waveNumber),
                MessageArgument.number("seconds", SUPPLY_DURATION_TICKS / 20));
        updateScoreBoards();
    }

    private void tickSupplyPhase() {
        if (!JocEnMarxa() || wavePhase != WavePhase.SUPPLY) return;
        supplyTicksRemaining--;
        for (Player player : getPlayers()) player.setFireTicks(0);
        if (supplyTicksRemaining % SUPPLY_FIRE_SWEEP_INTERVAL_TICKS == 0) extinguishArena();
        if (supplyTicksRemaining > 0) return;
        supplyDrops.clearUnclaimed();
        waveNumber++;
        wavePhase = WavePhase.ASSAULT;
        robotsSpawnedThisWave = 0;
        waveRobotQuota = RoboRampageRules.waveRobotQuota(waveNumber, getPlayers().size());
        announceWaveStart();
        updateScoreBoards();
    }

    private void announceWaveStart() {
        broadcast(MessageKey.ROBO_RAMPAGE_WAVE_START,
                MessageArgument.number("wave", waveNumber),
                MessageArgument.number("robots", waveRobotQuota));
    }

    private void extinguishArena() {
        for (Player player : getPlayers()) player.setFireTicks(0);
        for (Mob robot : liveRobotMobs()) robot.setFireTicks(0);
        for (Fireball fireball : world.getEntitiesByClass(Fireball.class)) fireball.remove();

        Location center = battleCenter();
        int minimumY = Math.max(world.getMinHeight(), center.getBlockY() - 4);
        int maximumY = Math.min(world.getMaxHeight() - 1, center.getBlockY() + targetHeight() + 12);
        for (int x = center.getBlockX() - ARENA_RADIUS; x <= center.getBlockX() + ARENA_RADIUS; x++) {
            for (int z = center.getBlockZ() - ARENA_RADIUS; z <= center.getBlockZ() + ARENA_RADIUS; z++) {
                for (int y = minimumY; y <= maximumY; y++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.FIRE || block.getType() == Material.SOUL_FIRE) {
                        block.setType(Material.AIR, false);
                    }
                }
            }
        }
    }

    private Optional<Location> findSpawnLocation(RobotType type) {
        return type == RobotType.GHAST ? findGhastSpawnLocation() : findGroundSpawnLocation();
    }

    private Optional<Location> findGroundSpawnLocation() {
        Location center = battleCenter();
        for (int attempt = 0; attempt < SPAWN_ATTEMPTS; attempt++) {
            int x = center.getBlockX() + random.nextInt(-ARENA_RADIUS, ARENA_RADIUS + 1);
            int z = center.getBlockZ() + random.nextInt(-ARENA_RADIUS, ARENA_RADIUS + 1);
            Block surface = world.getHighestBlockAt(x, z);
            if (!surface.getRelative(0, 1, 0).isEmpty() || !surface.getRelative(0, 2, 0).isEmpty()) continue;
            Location spawn = surface.getLocation().add(0.5, 4, 0.5);
            boolean tooClose = getPlayers().stream().anyMatch(player ->
                    player.getWorld() == world && player.getLocation().distanceSquared(spawn) < 16);
            if (!tooClose) return Optional.of(spawn);
        }
        return Optional.empty();
    }

    private Optional<Location> findGhastSpawnLocation() {
        Location center = battleCenter();
        for (int attempt = 0; attempt < SPAWN_ATTEMPTS; attempt++) {
            int x = center.getBlockX() + random.nextInt(-3, 4);
            int z = center.getBlockZ() + random.nextInt(-3, 4);
            int y = Math.min(world.getMaxHeight() - 5,
                    world.getHighestBlockYAt(x, z) + 8 + random.nextInt(0, 4));
            boolean obstructed = false;
            for (int offsetX = -2; offsetX <= 2 && !obstructed; offsetX++) {
                for (int offsetY = -2; offsetY <= 2 && !obstructed; offsetY++) {
                    for (int offsetZ = -2; offsetZ <= 2; offsetZ++) {
                        if (!world.getBlockAt(x + offsetX, y + offsetY, z + offsetZ).isEmpty()) {
                            obstructed = true;
                            break;
                        }
                    }
                }
            }
            if (!obstructed) return Optional.of(new Location(world, x + 0.5, y, z + 0.5));
        }
        return Optional.empty();
    }

    private void spawnRobot(RobotType type, Location location) {
        switch (type) {
            case ZOMBIE -> {
                Zombie zombie = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);
                HelmetVariant helmet = RoboRampageRules.randomZombieHelmet(random);
                equipGroundRobot(zombie, helmet);
                rememberRobot(zombie, type, helmet);
            }
            case SKELETON -> {
                Skeleton skeleton = (Skeleton) world.spawnEntity(location, EntityType.SKELETON);
                HelmetVariant helmet = RoboRampageRules.randomSkeletonHelmet(random);
                equipGroundRobot(skeleton, helmet);
                rememberRobot(skeleton, type, helmet);
            }
            case BLAZE -> {
                Blaze blaze = (Blaze) world.spawnEntity(location.clone().add(0, 1, 0), EntityType.BLAZE);
                prepareMob(blaze);
                rememberRobot(blaze, type, HelmetVariant.IRON_HELMET);
            }
            case CUTTER -> {
                Zombie cutter = (Zombie) world.spawnEntity(location, EntityType.ZOMBIE);
                equipCutterRobot(cutter);
                rememberRobot(cutter, type, HelmetVariant.IRON_HELMET);
            }
            case GHAST -> {
                Ghast ghast = (Ghast) world.spawnEntity(location, EntityType.GHAST);
                prepareMob(ghast);
                var maximumHealth = ghast.getAttribute(Attribute.MAX_HEALTH);
                if (maximumHealth != null) {
                    maximumHealth.setBaseValue(30);
                    ghast.setHealth(30);
                }
                rememberRobot(ghast, type, HelmetVariant.IRON_HELMET);
            }
        }
    }

    private void equipGroundRobot(Mob robot, HelmetVariant helmet) {
        prepareMob(robot);
        applyGroundRobotProfile(robot, RoboRampageRules.groundRobotProfile(helmet));
        equipIronRobot(robot, helmetMaterial(helmet), Material.IRON_SWORD);
    }

    private void equipCutterRobot(Mob robot) {
        prepareMob(robot);
        applyGroundRobotProfile(robot, RoboRampageRules.cutterProfile());
        equipIronRobot(robot, Material.IRON_HELMET, Material.STONECUTTER);
    }

    private void equipIronRobot(Mob robot, Material helmetMaterial, Material heldMaterial) {
        EntityEquipment equipment = robot.getEquipment();
        equipment.setBoots(new ItemStack(Material.IRON_BOOTS));
        equipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        equipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        equipment.setItemInMainHand(new ItemStack(heldMaterial));
        equipment.setHelmet(new ItemStack(helmetMaterial));
        equipment.setBootsDropChance(0);
        equipment.setLeggingsDropChance(0);
        equipment.setChestplateDropChance(0);
        equipment.setItemInMainHandDropChance(0);
        equipment.setHelmetDropChance(0);
    }

    private void applyGroundRobotProfile(Mob robot, GroundRobotProfile profile) {
        var maximumHealth = robot.getAttribute(Attribute.MAX_HEALTH);
        if (maximumHealth != null) {
            maximumHealth.setBaseValue(profile.maximumHealth());
            robot.setHealth(profile.maximumHealth());
        }
        var movementSpeed = robot.getAttribute(Attribute.MOVEMENT_SPEED);
        if (movementSpeed != null) movementSpeed.setBaseValue(profile.movementSpeed());
        var knockbackResistance = robot.getAttribute(Attribute.KNOCKBACK_RESISTANCE);
        if (knockbackResistance != null) {
            knockbackResistance.setBaseValue(profile.knockbackResistance());
        }
    }

    private void prepareMob(Mob robot) {
        robot.setCanPickupItems(false);
        robot.setPersistent(true);
        robot.setRemoveWhenFarAway(false);
        nearestPlayer(robot).ifPresent(robot::setTarget);
        var followRange = robot.getAttribute(Attribute.FOLLOW_RANGE);
        if (followRange != null) followRange.setBaseValue(40);
    }

    private Optional<Player> nearestPlayer(Entity entity) {
        return getPlayers().stream()
                .filter(player -> player.getWorld() == world && !player.isDead())
                .min(Comparator.comparingDouble(player -> player.getLocation().distanceSquared(entity.getLocation())));
    }

    private void rememberRobot(Mob robot, RobotType type, HelmetVariant helmet) {
        robots.put(robot.getUniqueId(), new RobotState(type, helmet));
    }

    private RobotCounts robotCounts() {
        int zombies = 0;
        int skeletons = 0;
        int blazes = 0;
        int cutters = 0;
        int ghasts = 0;
        for (RobotState state : robots.values()) {
            switch (state.type()) {
                case ZOMBIE -> zombies++;
                case SKELETON -> skeletons++;
                case BLAZE -> blazes++;
                case CUTTER -> cutters++;
                case GHAST -> ghasts++;
            }
        }
        return new RobotCounts(zombies, skeletons, blazes, cutters, ghasts);
    }

    private int scaffoldDamageFor(Mob robot) {
        RobotState state = robots.get(robot.getUniqueId());
        return state == null ? 1 : RoboRampageRules.scaffoldDamagePerAttack(state.type());
    }

    private void removeMissingRobots() {
        robots.keySet().removeIf(robotId -> {
            Entity entity = Bukkit.getEntity(robotId);
            return entity == null || !entity.isValid() || entity.isDead() || entity.getWorld() != world;
        });
    }

    private void keepFlyingRobotsReachable() {
        double battleCenterY = battleCenter().getY();
        for (Map.Entry<UUID, RobotState> entry : robots.entrySet()) {
            Entity entity = Bukkit.getEntity(entry.getKey());
            RobotType robotType = entry.getValue().type();
            if (!(entity instanceof Mob flyingRobot)
                    || (robotType != RobotType.BLAZE && robotType != RobotType.GHAST)) continue;
            double maximumFlyingHeight = RoboRampageRules.maximumFlyingHeight(
                    battleCenterY, scrapDrops.settledHeight(), robotType);
            if (flyingRobot.getLocation().getY() <= maximumFlyingHeight) continue;
            Location boundedLocation = flyingRobot.getLocation();
            boundedLocation.setY(maximumFlyingHeight);
            flyingRobot.teleport(boundedLocation);
            Vector velocity = flyingRobot.getVelocity();
            flyingRobot.setVelocity(new Vector(velocity.getX() * 0.4, -0.35, velocity.getZ() * 0.4));
            nearestPlayer(flyingRobot).ifPresent(flyingRobot::setTarget);
        }
    }

    @Override
    protected void onEntityDeath(EntityDeathEvent event, Entity entity) {
        super.onEntityDeath(event, entity);
        RobotState state = robots.remove(entity.getUniqueId());
        if (state == null || scrapDrops == null) return;
        event.getDrops().clear();
        event.setDroppedExp(0);
        boolean criticalKill = criticalKillCandidates.remove(entity.getUniqueId());
        LiveDeathReward reward = switch (state.type()) {
            case BLAZE -> RoboRampageRules.liveBlazeReward();
            case GHAST -> RoboRampageRules.liveGhastReward();
            case ZOMBIE, SKELETON, CUTTER -> RoboRampageRules.liveGroundRobotReward(state.helmet());
        };
        int blockCount = reward.rollBlockCount(random, criticalKill);
        enqueueScrap(entity.getLocation(), reward.scrapMaterial());
        for (int block = 1; block < blockCount; block++) {
            enqueueScrap(entity.getLocation().clone().add(
                    random.nextDouble(-0.75, 0.75), 0, random.nextDouble(-0.75, 0.75)), reward.scrapMaterial());
        }
    }

    private void enqueueScrap(Location location, ScrapMaterial material) {
        if (!scrapDrops.dropRobotScrap(location, material)) {
            Com.getPlugin().getLogger().warning(
                    "Robo Rampage scrap queue is full; placement backpressure exceeded");
        }
    }

    @Override
    protected void onEntityDamageByEntity(
            EntityDamageByEntityEvent event, Entity damaged, Entity damager) {
        super.onEntityDamageByEntity(event, damaged, damager);
        if (demolition != null && demolition.owns(damager)) {
            if (!demolition.mayDamage(damager, damaged, robots.containsKey(damaged.getUniqueId()))) {
                event.setCancelled(true);
            }
            // Native blast damage already accounts for distance, exposure and armor.
            criticalKillCandidates.remove(damaged.getUniqueId());
            return;
        }
        Entity source = damageSource(damager);
        if (tasers != null && tasers.isVisualBeamEntity(damager.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (damaged instanceof Player && source instanceof Player) {
            event.setCancelled(true);
            return;
        }
        boolean sourceIsRobot = source != null && robots.containsKey(source.getUniqueId());
        boolean targetIsRobot = robots.containsKey(damaged.getUniqueId());
        if (sourceIsRobot && targetIsRobot) {
            event.setCancelled(true);
            return;
        }
        if (damaged instanceof Player && sourceIsRobot) {
            event.setDamage(event.getDamage() * RoboRampageRules.LIVE_PLAYER_DAMAGE_MULTIPLIER);
        } else if (targetIsRobot && source instanceof Player
                && (tasers == null || !tasers.isApplyingDamageTo(damaged.getUniqueId()))) {
            event.setDamage(event.getDamage() * RoboRampageRules.ROBOT_DAMAGE_MULTIPLIER);
        }
        if (targetIsRobot) {
            ejectIronHeadScrapOnCriticalHit(event, damaged, damager);
            rememberCriticalKillingHit(event, damaged, damager);
        }
    }

    private void ejectIronHeadScrapOnCriticalHit(
            EntityDamageByEntityEvent event, Entity damaged, Entity directDamager) {
        RobotState robot = robots.get(damaged.getUniqueId());
        if (scrapDrops == null || robot == null || robot.helmet() != HelmetVariant.IRON_BLOCK
                || !isDirectPlayerCritical(event, directDamager)) return;
        Player attacker = (Player) directDamager;
        Vector awayFromAttacker = damaged.getLocation().toVector().subtract(attacker.getLocation().toVector());
        Location ejectionOrigin = damaged.getLocation().add(0, damaged.getHeight() * 0.65, 0);
        if (!scrapDrops.ejectRobotScrap(ejectionOrigin, awayFromAttacker, ScrapMaterial.IRON)) {
            Com.getPlugin().getLogger().warning(
                    "Robo Rampage scrap queue is full; critical-hit scrap could not be ejected");
        }
    }

    private void rememberCriticalKillingHit(
            EntityDamageByEntityEvent event, Entity damaged, Entity directDamager) {
        UUID robotId = damaged.getUniqueId();
        boolean lethalDirectCritical = isDirectPlayerCritical(event, directDamager)
                && damaged instanceof LivingEntity robot
                && event.getFinalDamage() >= robot.getHealth();
        if (!lethalDirectCritical) {
            criticalKillCandidates.remove(robotId);
            return;
        }
        criticalKillCandidates.add(robotId);
        scheduleGameplayTask(() -> criticalKillCandidates.remove(robotId), 1);
    }

    private boolean isDirectPlayerCritical(EntityDamageByEntityEvent event, Entity directDamager) {
        return !event.isCancelled() && directDamager instanceof Player && event.isCritical();
    }

    @Override
    protected void onProjectileLaunch(ProjectileLaunchEvent event, Projectile projectile) {
        super.onProjectileLaunch(event, projectile);
        if (!(projectile instanceof Fireball)) return;
        ProjectileSource shooter = projectile.getShooter();
        if (!(shooter instanceof Entity entity)) return;
        if (tasers != null && tasers.isEnergized(entity.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        RobotState robot = robots.get(entity.getUniqueId());
        if (robot == null || robot.type() != RobotType.GHAST || !(entity instanceof Ghast ghast)
                || ghastProjectiles == null) return;
        event.setCancelled(true);
        nearestPlayer(ghast).ifPresent(target -> ghastProjectiles.launch(ghast, target));
    }

    @Override
    protected void onProjectileHit(ProjectileHitEvent event, Projectile projectile) {
        super.onProjectileHit(event, projectile);
        if (ghastProjectiles == null || scrapDrops == null) return;
        Location impactLocation = projectile.getLocation();
        if (ghastProjectiles.handleHit(event, projectile)
                && !scrapDrops.dropImpactScrap(
                        impactLocation, RoboRampageRules.ghastProjectileImpactScrap())) {
            Com.getPlugin().getLogger().warning(
                    "Robo Rampage scrap queue is full; Ghast impact scrap could not be placed");
        }
    }

    @Override
    protected void onPlayerInteract(PlayerInteractEvent event, Player player) {
        super.onPlayerInteract(event, player);
        if (demolition != null && demolition.handleInteraction(event, player)) return;
        if (tasers != null) tasers.handleInteraction(event, player);
    }

    @Override
    protected void onPlayerItemConsume(PlayerItemConsumeEvent event, Player player) {
        super.onPlayerItemConsume(event, player);
        if (event.isCancelled() || event.getHand() != EquipmentSlot.HAND
                || event.getItem().getType() != Material.POTION) return;
        int consumedHotbarSlot = player.getInventory().getHeldItemSlot();
        scheduleGameplayTask(() -> {
            if (player.isOnline() && player.getWorld() == world) {
                HotbarBottleManager.moveBottleToStorage(player, consumedHotbarSlot);
            }
        }, 1);
    }

    @Override
    protected void onPlayerPickupItem(PlayerPickupItemEvent event, Player player) {
        super.onPlayerPickupItem(event, player);
        if (supplyDrops != null) supplyDrops.handlePickup(event, player);
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent event, Block block) {
        super.onBlockPlace(event, block);
        if (demolition != null) demolition.handlePlacement(event, block);
        if (scaffolds != null) scaffolds.handlePlacement(event, block);
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent event, Block block) {
        super.onBlockBreak(event, block);
        if (demolition != null) demolition.handleBreak(event, block);
        if (scaffolds != null) scaffolds.handleBreak(event, block);
    }

    private Entity damageSource(Entity damager) {
        if (!(damager instanceof Projectile projectile)) return damager;
        ProjectileSource shooter = projectile.getShooter();
        return shooter instanceof Entity entity ? entity : damager;
    }

    @Override
    protected void onEntityExplode(EntityExplodeEvent event, Entity entity) {
        super.onEntityExplode(event, entity);
        if (demolition != null) demolition.handleExplosion(event);
    }

    @Override
    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent event, Player player) {
        super.onPlayerRespawnAfterTick(event, player);
        if (scrapDrops != null && JocEnMarxa()) player.teleport(scrapDrops.safePlayerSpawn());
    }

    @Override
    protected void onPlayerMove(PlayerMoveEvent event, Player player) {
        super.onPlayerMove(event, player);
        if (event.getTo() != null && event.getFrom().getBlockY() != event.getTo().getBlockY()) checkVictory(player);
    }

    private void checkVictory(Player player) {
        if (climbFinished || scrapDrops == null || !JocEnMarxa() || player.isDead() || isSpectator(player)
                || !scrapDrops.isStandingOnTargetScrap(player, targetHeight())) return;
        climbFinished = true;
        displayedScrapHeight = scrapDrops.settledHeight();
        broadcast(MessageKey.ROBO_RAMPAGE_VICTORY, MessageArgument.number("height", targetHeight()));
        JocFinalitzat();
        planificarReseteig(POST_GAME_TICKS);
    }

    private void broadcast(MessageKey key, MessageArgument... arguments) {
        for (Player viewer : getViewers()) Messages.send(viewer, key, arguments);
    }

    private int targetHeight() {
        if (!pMapaActual().ExisteixPropietat("TargetHeight")) return DEFAULT_TARGET_HEIGHT;
        return Math.max(8, Math.min(240, pMapaActual().ObtenirPropietatInt("TargetHeight")));
    }

    private Location battleCenter() { return pMapaActual().ObtenirLocation("BCenter", world); }

    private List<Mob> liveRobotMobs() {
        List<Mob> result = new ArrayList<>();
        for (UUID robotId : robots.keySet()) {
            Entity entity = Bukkit.getEntity(robotId);
            if (entity instanceof Mob mob && mob.isValid() && !mob.isDead() && mob.getWorld() == world) {
                result.add(mob);
            }
        }
        return List.copyOf(result);
    }

    private boolean isActiveWeaponUser(Player player) {
        return JocEnMarxa() && player.getWorld() == world && !player.isDead()
                && getPlayers().contains(player) && !isSpectator(player);
    }

    private static Material helmetMaterial(HelmetVariant helmet) {
        return switch (helmet) {
            case IRON_HELMET -> Material.IRON_HELMET;
            case IRON_BLOCK -> Material.IRON_BLOCK;
            case REDSTONE_BLOCK -> Material.REDSTONE_BLOCK;
            case LAPIS_BLOCK -> Material.LAPIS_BLOCK;
        };
    }
}
