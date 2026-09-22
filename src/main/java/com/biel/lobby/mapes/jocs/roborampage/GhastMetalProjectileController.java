package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/** Replaces Ghast fireballs with full-block, terrain-safe iron projectiles. */
final class GhastMetalProjectileController implements AutoCloseable {
    private static final double PROJECTILE_SPEED = 0.9;
    private static final double RAW_PROJECTILE_DAMAGE = 16.0;
    private static final double FALLBACK_PROJECTILE_DAMAGE = 4.0;

    private final World world;
    private final Map<UUID, MetalProjectile> projectiles = new LinkedHashMap<>();

    GhastMetalProjectileController(World world) {
        this.world = world;
    }

    void launch(Ghast ghast, Player target) {
        Location origin = ghast.getEyeLocation().add(ghast.getLocation().getDirection().multiply(1.5));
        Location targetLocation = target.getEyeLocation().add(target.getVelocity().multiply(3));
        Vector direction = targetLocation.toVector().subtract(origin.toVector());
        if (direction.lengthSquared() < 0.01) return;

        Snowball carrier = world.spawn(origin, Snowball.class, snowball -> {
            snowball.setShooter(ghast);
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
        projectiles.put(carrier.getUniqueId(), new MetalProjectile(ghast.getUniqueId(), metalBlock.getUniqueId()));
        world.playSound(origin, Sound.ENTITY_IRON_GOLEM_ATTACK, 0.9F, 0.65F);
    }

    void tick() {
        for (UUID projectileId : new ArrayList<>(projectiles.keySet())) {
            MetalProjectile state = projectiles.get(projectileId);
            Entity carrierEntity = Bukkit.getEntity(projectileId);
            Entity displayEntity = Bukkit.getEntity(state.displayId());
            if (!(carrierEntity instanceof Snowball carrier) || !carrier.isValid()
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
        if (hitEntity instanceof Player player) {
            if (shooter instanceof Ghast ghast && ghast.isValid()) {
                player.damage(RAW_PROJECTILE_DAMAGE, ghast);
            } else {
                player.damage(FALLBACK_PROJECTILE_DAMAGE);
            }
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

    private record MetalProjectile(UUID shooterId, UUID displayId) {}
}
