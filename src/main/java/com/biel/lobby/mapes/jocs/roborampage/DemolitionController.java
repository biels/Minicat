package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Consumer;

import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;
import com.biel.lobby.mapes.jocs.roborampage.utils.DemolitionRules;

/** Match-owned TNT: native robot blast damage, explicit safe launch, protected terrain. */
final class DemolitionController implements Listener, AutoCloseable {
    private static final int MAX_DEPLOYED_CHARGES = 16;
    private static final int FUSE_TICKS = 80;
    private static final int CHAIN_FUSE_TICKS = 20;

    private final World world;
    private final Location battleCenter;
    private final int arenaRadius;
    private final IntSupplier targetHeight;
    private final Predicate<Player> activePlayer;
    private final Predicate<Entity> trackedRobot;
    private final ScaffoldController scaffolds;
    private final DemolitionRules blastRules = new DemolitionRules();
    private final Set<Block> placedCharges = new HashSet<>();
    private final Map<UUID, TNTPrimed> primedCharges = new HashMap<>();
    private final Set<UUID> starterRecipients = new HashSet<>();
    private Consumer<Runnable> scheduleLaunch;
    private boolean closed;

    DemolitionController(World world, Location battleCenter, int arenaRadius,
            IntSupplier targetHeight, Predicate<Player> activePlayer, ScaffoldController scaffolds,
            Predicate<Entity> trackedRobot) {
        this(world, battleCenter, arenaRadius, targetHeight, activePlayer, scaffolds, trackedRobot, Runnable::run);
    }

    DemolitionController(World world, Location battleCenter, int arenaRadius,
            IntSupplier targetHeight, Predicate<Player> activePlayer, ScaffoldController scaffolds,
            Predicate<Entity> trackedRobot, Consumer<Runnable> scheduleLaunch) {
        this.world = world;
        this.battleCenter = battleCenter.clone();
        this.arenaRadius = arenaRadius;
        this.targetHeight = targetHeight;
        this.activePlayer = activePlayer;
        this.scaffolds = scaffolds;
        this.trackedRobot = trackedRobot;
        this.scheduleLaunch = scheduleLaunch;
    }

    void register(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        // Apply once native explosion processing and this tick's movement have finished.
        scheduleLaunch = launch -> plugin.getServer().getScheduler().runTask(plugin, launch);
    }

    ItemStack createIgniter() {
        ItemStack igniter = new ItemStack(Material.FLINT_AND_STEEL);
        var meta = igniter.getItemMeta();
        meta.setUnbreakable(true);
        igniter.setItemMeta(meta);
        return igniter;
    }

    void giveInitialCharges(Player player) {
        if (starterRecipients.add(player.getUniqueId())) {
            player.getInventory().addItem(new ItemStack(Material.TNT, 2));
        }
    }

    void handlePlacement(BlockPlaceEvent event, Block block) {
        if (block.getType() != Material.TNT) return;
        event.setCancelled(true);
        if (!activePlayer.test(event.getPlayer()) || !withinBuildArea(block)
                || !event.getBlockReplacedState().getType().isAir()) return;
        pruneMissingCharges();
        if (placedCharges.size() + primedCharges.size() >= MAX_DEPLOYED_CHARGES) {
            Messages.send(event.getPlayer(), MessageKey.ROBO_RAMPAGE_TNT_LIMIT);
            return;
        }
        placedCharges.add(block);
        event.setCancelled(false);
    }

    void handleBreak(BlockBreakEvent event, Block block) {
        if (!activePlayer.test(event.getPlayer()) || !placedCharges.remove(block)) return;
        // Normal survival breaking returns exactly the block that was placed.
        event.setCancelled(false);
    }

    boolean handleInteraction(PlayerInteractEvent event, Player player) {
        if (event.getMaterial() != Material.FLINT_AND_STEEL) return false;
        // The igniter only lights tracked charges; never arena blocks or loose fire.
        event.setCancelled(true);
        Block block = event.getClickedBlock();
        if (activePlayer.test(player) && event.getAction() == Action.RIGHT_CLICK_BLOCK
                && block != null && placedCharges.contains(block)) {
            prime(block, player, FUSE_TICKS);
        }
        return true;
    }

    @EventHandler
    public void preventAutomaticIgnition(TNTPrimeEvent event) {
        // Redstone/fire/projectiles must not bypass ownership or double-spawn TNT.
        if (event.getBlock().getWorld() == world) event.setCancelled(true);
    }

    boolean owns(Entity entity) { return primedCharges.containsKey(entity.getUniqueId()); }

    boolean mayDamage(Entity explosion, Entity target, boolean targetIsRobot) {
        return owns(explosion) && !(target instanceof Player) && targetIsRobot;
    }

    double adjustedRobotDamage(double nativeDamage) {
        return nativeDamage * DemolitionRules.ROBOT_DAMAGE_MULTIPLIER;
    }

    boolean consumeBlastFallProtection(Player player) {
        return activePlayer.test(player)
                && blastRules.consumeFallProtection(player.getUniqueId(), world.getGameTime());
    }

    void forgetPlayer(UUID playerId) { blastRules.forgetPlayer(playerId); }

    void handleExplosion(EntityExplodeEvent event) {
        if (!owns(event.getEntity())) return;
        TNTPrimed charge = primedCharges.get(event.getEntity().getUniqueId());
        List<Block> affectedBlocks = new ArrayList<>(event.blockList());
        // Preserve authoritative scrap, original terrain, items and all other blocks.
        event.blockList().clear();
        event.setYield(0);
        if (event.isCancelled()) return;
        for (Block block : affectedBlocks) {
            if (placedCharges.contains(block)) prime(block, charge.getSource(), CHAIN_FUSE_TICKS);
        }
        queueBlastLaunch(event);
        scaffolds.destroyInBlast(affectedBlocks);
    }

    private void queueBlastLaunch(EntityExplodeEvent blast) {
        Location origin = blast.getLocation();
        double radius = DemolitionRules.BLAST_RADIUS;
        for (Entity target : world.getNearbyEntities(origin, radius, radius, radius)) {
            if (!mayLaunch(target)) continue;
            double exposure = exposure(origin, target);
            double speed = DemolitionRules.launchSpeed(origin.distance(target.getLocation()), exposure);
            if (speed == 0) continue;
            scheduleLaunch.accept(() -> {
                if (blast.isCancelled() || !mayLaunch(target)) return;
                Vector velocity = target.getVelocity();
                // Replace vertical knockback instead of adding to native/repeated blast impulses.
                target.setVelocity(velocity.setY(speed));
                target.setFallDistance(0);
                if (target instanceof Player player) {
                    blastRules.protectLanding(player.getUniqueId(), world.getGameTime());
                }
            });
        }
    }

    private boolean mayLaunch(Entity target) {
        if (closed || !target.isValid() || target.isDead() || target.getWorld() != world) return false;
        if (target instanceof Player player) {
            return player.isOnline() && player.getGameMode() != GameMode.SPECTATOR && activePlayer.test(player);
        }
        return trackedRobot.test(target);
    }

    private double exposure(Location origin, Entity target) {
        var bounds = target.getBoundingBox();
        int visibleSamples = 0;
        double[] heights = {bounds.getMinY() + 0.1, bounds.getCenterY(), bounds.getMaxY() - 0.1};
        for (double height : heights) {
            Vector direction = new Vector(bounds.getCenterX() - origin.getX(),
                    height - origin.getY(), bounds.getCenterZ() - origin.getZ());
            double distance = direction.length();
            if (distance <= 0.01 || world.rayTraceBlocks(origin, direction.normalize(),
                    Math.max(0, distance - 0.05), FluidCollisionMode.NEVER, true) == null) visibleSamples++;
        }
        return visibleSamples / 3.0;
    }

    private void prime(Block block, Entity source, int fuseTicks) {
        if (!placedCharges.remove(block) || block.getType() != Material.TNT) return;
        Location origin = block.getLocation().add(0.5, 0, 0.5);
        block.setType(Material.AIR, false);
        TNTPrimed charge = world.spawn(origin, TNTPrimed.class, entity -> {
            entity.setSource(source);
            entity.setFuseTicks(fuseTicks);
            entity.setYield(DemolitionRules.BLAST_POWER);
            entity.setIsIncendiary(false);
            entity.setPersistent(false);
            entity.addScoreboardTag("robo_rampage_tnt");
        });
        primedCharges.put(charge.getUniqueId(), charge);
        world.playSound(origin, Sound.ENTITY_TNT_PRIMED, 1, 1);
    }

    void pruneMissingCharges() {
        placedCharges.removeIf(block -> block.getType() != Material.TNT);
        primedCharges.values().removeIf(charge -> !charge.isValid());
        blastRules.prune(world.getGameTime());
    }

    private boolean withinBuildArea(Block block) {
        return block.getWorld() == world
                && Math.abs(block.getX() - battleCenter.getBlockX()) <= arenaRadius
                && Math.abs(block.getZ() - battleCenter.getBlockZ()) <= arenaRadius
                && block.getY() >= battleCenter.getBlockY()
                && block.getY() <= battleCenter.getBlockY() + targetHeight.getAsInt() + 8;
    }

    @Override
    public void close() {
        closed = true;
        HandlerList.unregisterAll(this);
        for (Block block : placedCharges) {
            if (block.getType() == Material.TNT) block.setType(Material.AIR, false);
        }
        primedCharges.values().forEach(Entity::remove);
        placedCharges.clear();
        primedCharges.clear();
        starterRecipients.clear();
        blastRules.clear();
    }
}
