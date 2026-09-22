package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/** Shared by Compactor throws and replacements for Ghast fireballs with full-block, terrain-safe iron projectiles. */
final class MetalProjectileController implements AutoCloseable {
    private static final double PROJECTILE_SPEED = 0.9;
    private static final double RAW_PROJECTILE_DAMAGE = 16.0;
    private static final int MAX_PROJECTILES = 32;
    private static final int LIFETIME_TICKS = 100;

    private final World world;
    private final Predicate<Player> activeParticipant;
    private long ticks;
    private final Map<UUID, MetalProjectile> projectiles = new LinkedHashMap<>();

    MetalProjectileController(World world, Predicate<Player> activeParticipant) {
        this.world = world;
        this.activeParticipant = activeParticipant;
    }

    void launch(Ghast ghast, Player target) {
        launch(ghast, target.getEyeLocation().add(target.getVelocity().multiply(3)));
    }

    void launch(Mob shooter, Location targetLocation) {
        if (projectiles.size() >= MAX_PROJECTILES) remove(projectiles.keySet().iterator().next());
        Location origin = shooter.getEyeLocation();
        if (shooter instanceof Ghast) origin.add(shooter.getLocation().getDirection().multiply(1.5));
        Vector direction = targetLocation.toVector().subtract(origin.toVector());
        if (direction.lengthSquared() < 0.01) return;

        Snowball carrier = world.spawn(origin, Snowball.class, snowball -> {
            snowball.setShooter(shooter);
            // The item is a safe fallback if a client does not render the full-size display.
            snowball.setItem(new ItemStack(Material.IRON_BLOCK));
            snowball.setInvisible(true);
            snowball.setVelocity(direction.normalize().multiply(PROJECTILE_SPEED).add(new Vector(0, 0.08, 0)));
        });
        BlockDisplay metalBlock = world.spawn(displayLocation(origin), BlockDisplay.class, display -> {
            display.setBlock(Material.IRON_BLOCK.createBlockData());
            display.setPersistent(false);
            display.setViewRange(48);
            display.setShadowRadius(0.8F);
            display.setShadowStrength(0.7F);
            display.setTeleportDuration(1);
        });
        projectiles.put(carrier.getUniqueId(), new MetalProjectile(shooter.getUniqueId(), metalBlock.getUniqueId(), ticks + LIFETIME_TICKS));
        world.playSound(origin, Sound.ENTITY_IRON_GOLEM_ATTACK, 0.9F, 0.65F);
    }

    void tick() {
        ticks++;
        for (UUID projectileId : new ArrayList<>(projectiles.keySet())) {
            MetalProjectile state = projectiles.get(projectileId);
            Entity carrierEntity = Bukkit.getEntity(projectileId);
            Entity displayEntity = Bukkit.getEntity(state.displayId());
            if (ticks >= state.expiresAt() || !(carrierEntity instanceof Snowball carrier) || !carrier.isValid()
                    || !(displayEntity instanceof BlockDisplay display) || !display.isValid()) {
                remove(projectileId);
                continue;
            }
            display.teleport(displayLocation(carrier.getLocation()));
        }
    }

    boolean handleHit(ProjectileHitEvent event, Projectile projectile) {
        MetalProjectile state = projectiles.get(projectile.getUniqueId());
        if (state == null) return false;
        event.setCancelled(true);
        Location impact = projectile.getLocation();
        Entity hitEntity = event.getHitEntity();
        Entity shooter = Bukkit.getEntity(state.shooterId());
        if (hitEntity instanceof Player player && activeParticipant.test(player)
                && shooter instanceof Mob && shooter.isValid() && !shooter.isDead()) {
            player.damage(RAW_PROJECTILE_DAMAGE, projectile);
        }
        world.spawnParticle(Particle.BLOCK, impact, 28, 0.45, 0.45, 0.45,
                Material.IRON_BLOCK.createBlockData());
        world.playSound(impact, Sound.BLOCK_ANVIL_LAND, 1.0F, 0.75F);
        remove(projectile.getUniqueId());
        return true;
    }

    void clear() {
        for (UUID projectileId : new ArrayList<>(projectiles.keySet())) remove(projectileId);
    }

    private void remove(UUID projectileId) {
        MetalProjectile state = projectiles.remove(projectileId);
        Entity projectile = Bukkit.getEntity(projectileId);
        if (projectile != null && projectile.isValid()) projectile.remove();
        if (state == null) return;
        Entity display = Bukkit.getEntity(state.displayId());
        if (display != null && display.isValid()) display.remove();
    }

    private static Location displayLocation(Location carrierLocation) {
        return carrierLocation.clone().subtract(0.5, 0.5, 0.5);
    }

    @Override
    public void close() {
        clear();
    }

    private record MetalProjectile(UUID shooterId, UUID displayId, long expiresAt) {}
}
