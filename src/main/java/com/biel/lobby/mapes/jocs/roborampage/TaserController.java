package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Item;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.biel.lobby.localization.MessageArgument;
import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;
import com.biel.lobby.mapes.jocs.roborampage.utils.ElectricNetwork;
import com.biel.lobby.mapes.jocs.roborampage.utils.ElectricNetwork.Edge;
import com.biel.lobby.mapes.jocs.roborampage.utils.ElectricNetwork.Point;
import com.biel.lobby.mapes.jocs.roborampage.utils.TaserRules;
import com.biel.lobby.utilities.Utils;

/** Owns Taser items, charge, automatic electrical networks and attributed pulse damage. */
final class TaserController implements AutoCloseable {
    private static final int BEAM_RENDER_INTERVAL_TICKS = 3;
    private static final int SLOWNESS_DURATION_TICKS = 15;
    private static final int SLOWNESS_AMPLIFIER = 4;
    private static final double PARTICLE_SPACING = 0.45;

    private final World world;
    private final Supplier<? extends Collection<Mob>> liveRobots;
    private final Predicate<Player> activeParticipant;
    private final NamespacedKey taserIdKey;
    private final NamespacedKey startingTaserKey;
    private final NamespacedKey chargeKey;
    private final NamespacedKey levelKey;
    private final NamespacedKey lastDischargeTickKey;
    private final GuardianBeamRenderer guardianBeams;
    private final Set<UUID> activePlayers = new LinkedHashSet<>();
    private final Set<UUID> energizedRobotIds = new HashSet<>();
    private final Set<UUID> applyingDamageToRobotIds = new HashSet<>();
    private final Map<UUID, Long> lastPulseByPlayer = new HashMap<>();
    private final Map<UUID, Long> lastDamageByRobot = new HashMap<>();
    private long currentTick;

    TaserController(
            Plugin plugin,
            World world,
            Supplier<? extends Collection<Mob>> liveRobots,
            Predicate<Player> activeParticipant) {
        this.world = world;
        this.liveRobots = liveRobots;
        this.activeParticipant = activeParticipant;
        this.taserIdKey = new NamespacedKey(plugin, "robo_rampage_taser_id");
        this.startingTaserKey = new NamespacedKey(plugin, "robo_rampage_starting_taser");
        this.chargeKey = new NamespacedKey(plugin, "robo_rampage_taser_charge");
        this.levelKey = new NamespacedKey(plugin, "robo_rampage_taser_level");
        this.lastDischargeTickKey = new NamespacedKey(plugin, "robo_rampage_taser_last_discharge");
        this.guardianBeams = new GuardianBeamRenderer(world);
    }

    void tick() {
        currentTick++;
        energizedRobotIds.clear();
        guardianBeams.beginFrame();
        Map<UUID, Mob> robotsById = liveRobotMap();
        for (UUID playerId : new ArrayList<>(activePlayers)) {
            Player player = Bukkit.getPlayer(playerId);
            if (!validOperator(player)) {
                stop(playerId);
                continue;
            }
            ItemStack taser = player.getInventory().getItemInMainHand();
            if (!isTaser(taser)) {
                stop(playerId);
                continue;
            }
            int charge = charge(taser);
            if (charge <= 0) {
                stop(playerId);
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.7F, 0.7F);
                continue;
            }
            NetworkSnapshot network = buildNetwork(player, charge, level(taser), robotsById);
            if (network.edges().isEmpty()) continue;

            setCharge(taser, charge - TaserRules.CHARGE_DRAIN_PER_TICK);
            setLastDischargeTick(taser, currentTick);
            player.getInventory().setItemInMainHand(taser);
            for (Edge edge : network.edges()) energizedRobotIds.add(edge.targetId());

            renderGuardianBeams(player, network);
            if (currentTick % BEAM_RENDER_INTERVAL_TICKS == 0) renderElectricSparks(network);
            long lastPulse = lastPulseByPlayer.getOrDefault(playerId, Long.MIN_VALUE / 2);
            if (currentTick - lastPulse >= TaserRules.PULSE_INTERVAL_TICKS) {
                pulse(player, network);
                lastPulseByPlayer.put(playerId, currentTick);
            }
        }
        guardianBeams.finishFrame();

        for (Player player : world.getPlayers()) {
            if (!activeParticipant.test(player)) continue;
            rechargeCarriedTasers(player);
            if (currentTick % 10 == 0) showCharge(player);
        }
        lastDamageByRobot.entrySet().removeIf(entry -> currentTick - entry.getValue() > 40);
    }

    boolean handleInteraction(PlayerInteractEvent event, Player player) {
        if (event.getHand() != EquipmentSlot.HAND
                || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
                || !isTaser(event.getItem())) {
            return false;
        }
        event.setCancelled(true);
        if (!validOperator(player)) return true;

        UUID playerId = player.getUniqueId();
        if (activePlayers.remove(playerId)) {
            lastPulseByPlayer.remove(playerId);
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.8F, 1.2F);
        } else if (charge(event.getItem()) > 0) {
            activePlayers.add(playerId);
            lastPulseByPlayer.put(playerId, currentTick - TaserRules.PULSE_INTERVAL_TICKS);
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.8F, 1.6F);
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF, 0.7F, 0.7F);
        }
        player.swingMainHand();
        showCharge(player);
        return true;
    }

    boolean isApplyingDamageTo(UUID robotId) { return applyingDamageToRobotIds.contains(robotId); }

    boolean isEnergized(UUID robotId) { return energizedRobotIds.contains(robotId); }

    boolean isVisualBeamEntity(UUID entityId) { return guardianBeams.isHelper(entityId); }

    ItemStack createStartingTaser(Player player) {
        String taserId = "starter:" + player.getUniqueId();
        return createTaser(taserId, true);
    }

    boolean canUpgrade(Player player) {
        return carriedTaser(player).map(carried -> level(carried.item())).orElse(TaserRules.MAXIMUM_LEVEL)
                < TaserRules.MAXIMUM_LEVEL;
    }

    boolean upgradeCarriedTaser(Player player) {
        var carriedTaser = carriedTaser(player);
        if (carriedTaser.isEmpty()) return false;
        ItemStack taser = carriedTaser.get().item();
        int upgradedLevel = Math.min(TaserRules.MAXIMUM_LEVEL, level(taser) + 1);
        if (upgradedLevel == level(taser)) {
            Messages.send(player, MessageKey.ROBO_RAMPAGE_TASER_UPGRADE_MAXIMUM);
            return false;
        }
        ItemMeta meta = taser.getItemMeta();
        meta.getPersistentDataContainer().set(levelKey, PersistentDataType.INTEGER, upgradedLevel);
        meta.getPersistentDataContainer().set(
                chargeKey, PersistentDataType.INTEGER, TaserRules.maximumCharge(upgradedLevel));
        taser.setItemMeta(meta);
        player.getInventory().setItem(carriedTaser.get().slot(), taser);
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.0F, 1.6F);
        Messages.send(player, MessageKey.ROBO_RAMPAGE_TASER_UPGRADED,
                MessageArgument.number("level", upgradedLevel));
        return true;
    }

    boolean isStartingTaser(ItemStack item) {
        return isTaser(item) && item.getItemMeta().getPersistentDataContainer()
                .has(startingTaserKey, PersistentDataType.BYTE);
    }

    private ItemStack createTaser(String taserId, boolean startingTaser) {
        ItemStack taser = Utils.setItemNameAndLore(
                new ItemStack(Material.LIGHTNING_ROD),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_TASER_NAME),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_TASER_LORE_NETWORK),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_TASER_LORE_CHARGE));
        ItemMeta meta = taser.getItemMeta();
        meta.getPersistentDataContainer().set(taserIdKey, PersistentDataType.STRING, taserId);
        if (startingTaser) {
            meta.getPersistentDataContainer().set(startingTaserKey, PersistentDataType.BYTE, (byte) 1);
        }
        meta.getPersistentDataContainer().set(levelKey, PersistentDataType.INTEGER, 1);
        meta.getPersistentDataContainer().set(
                chargeKey, PersistentDataType.INTEGER, TaserRules.maximumCharge(1));
        meta.getPersistentDataContainer().set(lastDischargeTickKey, PersistentDataType.LONG, Long.MIN_VALUE / 2);
        meta.setEnchantmentGlintOverride(true);
        meta.setMaxStackSize(1);
        meta.setUnbreakable(true);
        taser.setItemMeta(meta);
        return taser;
    }

    private NetworkSnapshot buildNetwork(
            Player player, int charge, int level, Map<UUID, Mob> robotsById) {
        Location sourceLocation = player.getEyeLocation();
        Point source = point(sourceLocation);
        List<ElectricNetwork.Target> targets = robotsById.values().stream()
                .map(robot -> new ElectricNetwork.Target(robot.getUniqueId(), point(targetLocation(robot))))
                .toList();
        List<Edge> edges = ElectricNetwork.build(
                source,
                targets,
                TaserRules.maximumTargets(charge, level),
                target -> TaserRules.sourceRange(robotsById.get(target.id()) instanceof Ghast),
                TaserRules.JUMP_RANGE,
                this::hasLineOfSight);
        return new NetworkSnapshot(sourceLocation, edges, robotsById);
    }

    private void renderGuardianBeams(Player operator, NetworkSnapshot network) {
        UUID operatorId = operator.getUniqueId();
        for (Edge edge : network.edges()) {
            Location source = edge.parentTargetId()
                    .map(network.robotsById()::get)
                    .map(this::targetLocation)
                    .orElse(network.source());
            Mob target = network.robotsById().get(edge.targetId());
            if (target == null) continue;
            guardianBeams.show(
                    operatorId,
                    edge.parentTargetId().orElse(operatorId),
                    edge.targetId(),
                    source,
                    target);
        }
    }

    private void renderElectricSparks(NetworkSnapshot network) {
        for (Edge edge : network.edges()) {
            Location from = edge.parentTargetId()
                    .map(network.robotsById()::get)
                    .map(this::targetLocation)
                    .orElse(network.source());
            Mob target = network.robotsById().get(edge.targetId());
            if (target != null) renderBeam(from, targetLocation(target));
        }
    }

    private void renderBeam(Location from, Location to) {
        Vector difference = to.toVector().subtract(from.toVector());
        double distance = difference.length();
        if (distance <= 0.01) return;
        Vector step = difference.normalize().multiply(PARTICLE_SPACING);
        Location particleLocation = from.clone();
        int particleCount = Math.max(1, (int) Math.ceil(distance / PARTICLE_SPACING));
        for (int particle = 0; particle <= particleCount; particle++) {
            world.spawnParticle(Particle.ELECTRIC_SPARK, particleLocation, 1, 0.025, 0.025, 0.025, 0.01);
            particleLocation.add(step);
        }
    }

    private void pulse(Player player, NetworkSnapshot network) {
        DamageSource damageSource = DamageSource.builder(DamageType.MAGIC)
                .withCausingEntity(player)
                .withDirectEntity(player)
                .withDamageLocation(player.getLocation())
                .build();
        for (UUID robotId : network.edges().stream().map(Edge::targetId).distinct().toList()) {
            Mob robot = network.robotsById().get(robotId);
            if (robot == null || !robot.isValid() || robot.isDead()) continue;
            robot.addPotionEffect(new PotionEffect(
                    PotionEffectType.SLOWNESS, SLOWNESS_DURATION_TICKS, SLOWNESS_AMPLIFIER, false, false, true));
            if (currentTick - lastDamageByRobot.getOrDefault(robotId, Long.MIN_VALUE / 2)
                    < TaserRules.PULSE_INTERVAL_TICKS) continue;
            lastDamageByRobot.put(robotId, currentTick);
            applyingDamageToRobotIds.add(robotId);
            try {
                robot.setNoDamageTicks(0);
                robot.damage(TaserRules.DAMAGE_PER_PULSE, damageSource);
            } finally {
                applyingDamageToRobotIds.remove(robotId);
            }
            if (robot.isValid() && !robot.isDead()) {
                world.spawnParticle(Particle.ELECTRIC_SPARK, targetLocation(robot), 10, 0.25, 0.4, 0.25, 0.09);
            }
        }
        world.playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.35F, 1.8F);
    }

    private void rechargeCarriedTasers(Player player) {
        PlayerInventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (!isTaser(item) || charge(item) >= TaserRules.maximumCharge(level(item))
                    || currentTick - lastDischargeTick(item) < TaserRules.RECHARGE_DELAY_TICKS
                    || currentTick % TaserRules.rechargeIntervalTicks(level(item)) != 0) continue;
            setCharge(item, charge(item) + 1);
            inventory.setItem(slot, item);
        }
    }

    private void showCharge(Player player) {
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (!isTaser(heldItem)) return;
        MessageKey key = activePlayers.contains(player.getUniqueId())
                ? MessageKey.ROBO_RAMPAGE_TASER_CHARGE_ACTIVE
                : MessageKey.ROBO_RAMPAGE_TASER_CHARGE_READY;
        player.sendActionBar(Messages.component(
                player, key,
                MessageArgument.number("level", level(heldItem)),
                MessageArgument.number("charge", charge(heldItem)),
                MessageArgument.number("maximum", TaserRules.maximumCharge(level(heldItem)))));
    }

    private Map<UUID, Mob> liveRobotMap() {
        Map<UUID, Mob> result = new HashMap<>();
        for (Mob robot : liveRobots.get()) {
            if (robot != null && robot.isValid() && !robot.isDead() && robot.getWorld() == world) {
                result.put(robot.getUniqueId(), robot);
            }
        }
        return Map.copyOf(result);
    }

    private boolean validOperator(Player player) {
        return player != null && player.isOnline() && !player.isDead()
                && player.getWorld() == world && activeParticipant.test(player);
    }

    private boolean hasLineOfSight(Point from, Point to) {
        Vector direction = new Vector(to.x() - from.x(), to.y() - from.y(), to.z() - from.z());
        double distance = direction.length();
        if (distance <= 0.01) return true;
        return world.rayTraceBlocks(
                location(from), direction.normalize(), Math.max(0, distance - 0.2),
                FluidCollisionMode.NEVER, true) == null;
    }

    private Location targetLocation(Mob robot) {
        return robot.getLocation().add(0, robot.getHeight() * 0.6, 0);
    }

    private Location location(Point point) { return new Location(world, point.x(), point.y(), point.z()); }

    private static Point point(Location location) {
        return new Point(location.getX(), location.getY(), location.getZ());
    }

    private boolean isTaser(ItemStack item) {
        if (item == null || item.getType() != Material.LIGHTNING_ROD || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(taserIdKey, PersistentDataType.STRING);
    }

    private int charge(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer()
                .getOrDefault(chargeKey, PersistentDataType.INTEGER, 0);
    }

    private int level(ItemStack item) {
        return TaserRules.normalizedLevel(item.getItemMeta().getPersistentDataContainer()
                .getOrDefault(levelKey, PersistentDataType.INTEGER, 1));
    }

    private java.util.Optional<CarriedTaser> carriedTaser(Player player) {
        PlayerInventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack item = inventory.getItem(slot);
            if (isTaser(item)) return java.util.Optional.of(new CarriedTaser(slot, item));
        }
        return java.util.Optional.empty();
    }

    private long lastDischargeTick(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer()
                .getOrDefault(lastDischargeTickKey, PersistentDataType.LONG, Long.MIN_VALUE / 2);
    }

    private void setCharge(ItemStack item, int charge) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(
                chargeKey, PersistentDataType.INTEGER,
                Math.max(0, Math.min(TaserRules.maximumCharge(level(item)), charge)));
        item.setItemMeta(meta);
    }

    private void setLastDischargeTick(ItemStack item, long tick) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(lastDischargeTickKey, PersistentDataType.LONG, tick);
        item.setItemMeta(meta);
    }

    private void stop(UUID playerId) {
        activePlayers.remove(playerId);
        lastPulseByPlayer.remove(playerId);
    }

    @Override
    public void close() {
        guardianBeams.close();
        activePlayers.clear();
        energizedRobotIds.clear();
        applyingDamageToRobotIds.clear();
        for (Player player : world.getPlayers()) {
            PlayerInventory inventory = player.getInventory();
            for (int slot = 0; slot < inventory.getSize(); slot++) {
                if (isTaser(inventory.getItem(slot))) inventory.setItem(slot, null);
            }
        }
        for (Item item : world.getEntitiesByClass(Item.class)) {
            if (isTaser(item.getItemStack())) item.remove();
        }
    }

    private record NetworkSnapshot(Location source, List<Edge> edges, Map<UUID, Mob> robotsById) {}

    private record CarriedTaser(int slot, ItemStack item) {}
}
