package com.biel.lobby.mapes.jocs.roborampage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.random.RandomGenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/** One armor fragment per surviving robot; presentation runs after damage is applied. */
final class RobotSheddingController implements Listener, AutoCloseable {
    private static final List<EquipmentSlot> ARMOR_SLOTS = List.of(
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
    private final World world;
    private final Predicate<UUID> tracked;
    private final RandomGenerator random;
    private final Consumer<Runnable> defer;
    private final BiPredicate<Location, Vector> emit;
    private final Set<UUID> shedRobots = new HashSet<>();
    private final Set<UUID> pendingRobots = new HashSet<>();
    private boolean closed;

    RobotSheddingController(World world, Predicate<UUID> tracked, RandomGenerator random,
            Consumer<Runnable> defer, BiPredicate<Location, Vector> emit) {
        this.world = world;
        this.tracked = tracked;
        this.random = random;
        this.defer = defer;
        this.emit = emit;
    }

    void register(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        if (closed || event.isCancelled() || !(event.getEntity() instanceof Mob robot)
                || !eligible(robot) || shedRobots.contains(robot.getUniqueId())) return;
        double healthBefore = robot.getHealth();
        double finalDamage = event.getFinalDamage();
        if (finalDamage <= 0 || finalDamage >= healthBefore) return;
        var maximumHealth = robot.getAttribute(Attribute.MAX_HEALTH);
        if (maximumHealth == null || maximumHealth.getValue() <= 0) return;
        double halfHealth = maximumHealth.getValue() / 2;
        boolean criticalIronHead = event instanceof EntityDamageByEntityEvent hit
                && hit.isCritical() && hit.getDamager() instanceof Player
                && robot.getEquipment() != null
                && present(robot.getEquipment().getItem(EquipmentSlot.HEAD))
                && robot.getEquipment().getItem(EquipmentSlot.HEAD).getType() == Material.IRON_BLOCK;
        if (!criticalIronHead && healthBefore - finalDamage > halfHealth) return;
        if (armorSlots(robot.getEquipment()).isEmpty() || !pendingRobots.add(robot.getUniqueId())) return;
        Vector direction = event instanceof EntityDamageByEntityEvent hit
                ? robot.getLocation().toVector().subtract(hit.getDamager().getLocation().toVector())
                : new Vector(random.nextDouble(-1, 1), 0, random.nextDouble(-1, 1));
        // Removing armor during the event would alter the hit being resolved.
        defer.accept(() -> shedAfterDamage(event, robot, healthBefore, halfHealth, criticalIronHead, direction));
    }

    private void shedAfterDamage(EntityDamageEvent event, Mob robot, double healthBefore,
            double halfHealth, boolean criticalIronHead, Vector direction) {
        pendingRobots.remove(robot.getUniqueId());
        if (closed || event.isCancelled() || event.getFinalDamage() <= 0 || !eligible(robot)
                || robot.getHealth() >= healthBefore || shedRobots.contains(robot.getUniqueId())
                || (!criticalIronHead && robot.getHealth() > halfHealth)) return;
        EntityEquipment equipment = robot.getEquipment();
        List<EquipmentSlot> remainingArmor = armorSlots(equipment);
        if (remainingArmor.isEmpty()) return;
        EquipmentSlot slot = criticalIronHead && remainingArmor.contains(EquipmentSlot.HEAD)
                ? EquipmentSlot.HEAD : remainingArmor.get(random.nextInt(remainingArmor.size()));
        Location origin = robot.getLocation().add(0, robot.getHeight() * 0.65, 0);
        if (!emit.test(origin, direction)) return;
        equipment.setItem(slot, null);
        shedRobots.add(robot.getUniqueId());
        world.playSound(origin, Sound.ENTITY_ITEM_BREAK, 0.7F, 0.9F);
        world.spawnParticle(Particle.BLOCK, origin, 12, 0.25, 0.25, 0.25, 0.02,
                Material.IRON_BLOCK.createBlockData());
    }

    private boolean eligible(Mob robot) {
        return robot.isValid() && !robot.isDead() && robot.getWorld() == world
                && robot.getHealth() > 0 && tracked.test(robot.getUniqueId());
    }

    private static List<EquipmentSlot> armorSlots(EntityEquipment equipment) {
        if (equipment == null) return List.of();
        return ARMOR_SLOTS.stream().filter(slot -> present(equipment.getItem(slot))).toList();
    }

    private static boolean present(ItemStack item) {
        return item != null && !item.getType().isAir() && item.getAmount() > 0;
    }

    void forget(UUID robotId) {
        shedRobots.remove(robotId);
        pendingRobots.remove(robotId);
    }

    @Override public void close() {
        closed = true;
        HandlerList.unregisterAll(this);
        shedRobots.clear();
        pendingRobots.clear();
    }
}
