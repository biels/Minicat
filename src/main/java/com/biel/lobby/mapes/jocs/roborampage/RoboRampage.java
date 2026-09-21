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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.LiveDeathReward;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.PowerUp;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.RobotCounts;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules.RobotType;
import com.biel.lobby.mapes.jocs.roborampage.utils.ScrapMaterial;
import com.biel.lobby.utilities.ScoreBoardUpdater;

/** Faithful, bounded reconstruction of the October 2015 cooperative scrap climb. */
public class RoboRampage extends JocCooperatiu {
    private static final int DEFAULT_TARGET_HEIGHT = 32;
    private static final int ARENA_RADIUS = 7;
    private static final int SPAWN_ATTEMPTS = 32;
    private static final int POST_GAME_TICKS = 20 * 10;

    private final Map<UUID, RobotState> robots = new LinkedHashMap<>();
    private final Set<UUID> criticalKillCandidates = new HashSet<>();
    private final RandomGenerator random = ThreadLocalRandom.current();
    private JunkDropPolicy junkDropPolicy;
    private ScrapDropController scrapDrops;
    private TaserController tasers;
    private int displayedScrapHeight;
    private boolean climbFinished;

    private record RobotState(RobotType type, HelmetVariant helmet) {}

    private record SpawnRequest(RobotType type, Location location) {}

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player player) {
        ArrayList<ItemStack> startingItems = new ArrayList<>(List.of(new ItemStack(Material.DIAMOND_SWORD)));
        if (tasers != null) startingItems.add(tasers.createStartingTaser(player));
        return startingItems;
    }

    @Override
    protected boolean canBeDropped(ItemStack item, Player player) {
        return (tasers == null || !tasers.isStartingTaser(item)) && super.canBeDropped(item, player);
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
        junkDropPolicy = new JunkDropPolicy();
        scrapDrops = new ScrapDropController(world, battleCenter(), random);
        tasers = new TaserController(Com.getPlugin(), world, random, this::liveRobotMobs, this::isActiveTaserUser);
        scheduleGameplayRepeatingTask(scrapDrops::tick, 1, 1);
        scheduleGameplayRepeatingTask(tasers::tick, 1, 1);
        scheduleGameplayRepeatingTask(this::runDirector, 1, 40);
        scheduleGameplayRepeatingTask(this::sampleProgress, 20, 20);
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
            Com.getPlugin().getLogger().info("Robo Rampage Tasers created=" + tasers.createdTaserCount()
                    + "; next drop progress=" + tasers.dropProgress() + "/" + tasers.nextDropThreshold());
            tasers.close();
        }
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
                Messages.legacy(player, MessageKey.ROBO_RAMPAGE_INFO_GOAL,
                        MessageArgument.number("height", targetHeight()))));
    }

    @Override
    protected void updateScoreBoard(Player player) {
        super.updateScoreBoard(player);
        if (!JocIniciat) return;
        ScoreBoardUpdater.setTranslatedScoreBoard(player, "Robo Rampage", List.of(
                Messages.scoreboardMarker(MessageKey.ROBO_RAMPAGE_SCORE_SCRAP),
                Messages.scoreboardMarker(MessageKey.ROBO_RAMPAGE_SCORE_TARGET)), List.of(
                displayedScrapHeight, targetHeight()));
    }

    private void sampleProgress() {
        if (!JocEnMarxa() || scrapDrops == null) return;
        displayedScrapHeight = scrapDrops.settledHeight();
        if (junkDropPolicy.sample(
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
        keepBlazesReachable();
        int playerCount = Math.max(1, getPlayers().size());
        RobotCounts counts = robotCounts();
        RoboRampageRules.chooseSpawn(
                RoboRampageRules.liveSpawnLimits(scrapDrops.settledHeight(), playerCount), counts, random)
                .flatMap(type -> findSpawnLocation().map(location -> new SpawnRequest(type, location)))
                .ifPresent(request -> spawnRobot(request.type(), request.location()));
    }

    private Optional<Location> findSpawnLocation() {
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
        }
    }

    private void equipGroundRobot(Mob robot, HelmetVariant helmet) {
        prepareMob(robot);
        EntityEquipment equipment = robot.getEquipment();
        equipment.setBoots(new ItemStack(Material.IRON_BOOTS));
        equipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        equipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
        equipment.setHelmet(new ItemStack(helmetMaterial(helmet)));
        equipment.setBootsDropChance(0);
        equipment.setLeggingsDropChance(0);
        equipment.setChestplateDropChance(0);
        equipment.setItemInMainHandDropChance(0);
        equipment.setHelmetDropChance(0);
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
        for (RobotState state : robots.values()) {
            switch (state.type()) {
                case ZOMBIE -> zombies++;
                case SKELETON -> skeletons++;
                case BLAZE -> blazes++;
            }
        }
        return new RobotCounts(zombies, skeletons, blazes);
    }

    private void removeMissingRobots() {
        robots.keySet().removeIf(robotId -> {
            Entity entity = Bukkit.getEntity(robotId);
            return entity == null || !entity.isValid() || entity.isDead() || entity.getWorld() != world;
        });
    }

    private void keepBlazesReachable() {
        double ceiling = battleCenter().getY() + scrapDrops.settledHeight() + 6;
        for (Map.Entry<UUID, RobotState> entry : robots.entrySet()) {
            if (entry.getValue().type() != RobotType.BLAZE) continue;
            Entity entity = Bukkit.getEntity(entry.getKey());
            if (!(entity instanceof Blaze blaze) || blaze.getLocation().getY() <= ceiling) continue;
            Vector velocity = blaze.getVelocity();
            blaze.setVelocity(new Vector(velocity.getX() * 0.4, -0.35, velocity.getZ() * 0.4));
            nearestPlayer(blaze).ifPresent(blaze::setTarget);
        }
    }

    @Override
    protected void onEntityDeath(EntityDeathEvent event, Entity entity) {
        super.onEntityDeath(event, entity);
        RobotState state = robots.remove(entity.getUniqueId());
        if (state == null || scrapDrops == null) return;
        event.getDrops().clear();
        event.setDroppedExp(0);
        Player killer = event.getEntity().getKiller();
        boolean criticalKill = criticalKillCandidates.remove(entity.getUniqueId());
        LiveDeathReward reward = state.type() == RobotType.BLAZE
                ? RoboRampageRules.liveBlazeReward()
                : RoboRampageRules.liveGroundRobotReward(state.helmet());
        int blockCount = reward.rollBlockCount(random, criticalKill);
        enqueueScrap(entity.getLocation(), reward.scrapMaterial());
        for (int block = 1; block < blockCount; block++) {
            enqueueScrap(entity.getLocation().clone().add(
                    random.nextDouble(-0.75, 0.75), 0, random.nextDouble(-0.75, 0.75)), reward.scrapMaterial());
        }
        if (killer != null) applyReward(killer, reward);
        if (tasers != null) tasers.recordRobotDeath(entity.getLocation(), state.type(), getPlayers().size());
    }

    private void enqueueScrap(Location location, ScrapMaterial material) {
        if (!scrapDrops.dropRobotScrap(location, material)) {
            Com.getPlugin().getLogger().warning(
                    "Robo Rampage scrap queue is full; placement backpressure exceeded");
        }
    }

    private void applyReward(Player killer, LiveDeathReward reward) {
        if (reward.powerUp() == PowerUp.STRENGTH) {
            killer.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH,
                    RoboRampageRules.POWER_UP_TICKS, RoboRampageRules.POWER_UP_AMPLIFIER));
        } else if (reward.powerUp() == PowerUp.SPEED) {
            killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                    RoboRampageRules.POWER_UP_TICKS, RoboRampageRules.POWER_UP_AMPLIFIER));
        }
        if (reward.extinguishKiller()) killer.setFireTicks(0);
    }

    @Override
    protected void onEntityDamageByEntity(
            EntityDamageByEntityEvent event, Entity damaged, Entity damager) {
        super.onEntityDamageByEntity(event, damaged, damager);
        Entity source = damageSource(damager);
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
            event.setDamage(event.getDamage() * RoboRampageRules.PLAYER_DAMAGE_MULTIPLIER);
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
        if (!(projectile instanceof Fireball) || tasers == null) return;
        ProjectileSource shooter = projectile.getShooter();
        if (shooter instanceof Entity entity && tasers.isEnergized(entity.getUniqueId())) event.setCancelled(true);
    }

    @Override
    protected void onPlayerInteract(PlayerInteractEvent event, Player player) {
        super.onPlayerInteract(event, player);
        if (tasers != null) tasers.handleInteraction(event, player);
    }

    private Entity damageSource(Entity damager) {
        if (!(damager instanceof Projectile projectile)) return damager;
        ProjectileSource shooter = projectile.getShooter();
        return shooter instanceof Entity entity ? entity : damager;
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

    private boolean isActiveTaserUser(Player player) {
        return JocEnMarxa() && getPlayers().contains(player) && !isSpectator(player);
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
