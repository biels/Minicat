package com.biel.lobby.mapes.jocs.roborampage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

/** A grounded sneak-jump boost, owned by one match rather than an armor item. */
final class PistonJumpController implements Listener, AutoCloseable {
    static final int COOLDOWN_TICKS = 160;
    static final double VERTICAL_SPEED = 0.58;
    static final double FORWARD_SPEED = 0.34;

    private final World world;
    private final Predicate<Player> activeParticipant;
    private final Map<UUID, JumpState> jumps = new HashMap<>();
    private long currentTick;
    private boolean closed;

    private static final class JumpState {
        long readyAt;
        boolean awaitingLanding;
        boolean observedAirborne;
        boolean readinessAnnounced;
    }

    PistonJumpController(World world, Predicate<Player> activeParticipant) {
        this.world = world;
        this.activeParticipant = activeParticipant;
    }

    void register(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled() || !validParticipant(player) || !player.isSneaking()
                || player.isFlying() || player.isGliding() || player.isInsideVehicle()) return;
        JumpState state = jumps.computeIfAbsent(player.getUniqueId(), ignored -> new JumpState());
        if (state.awaitingLanding || currentTick < state.readyAt) return;

        // Paper emits this event for an actual ground jump. Looking up never increases lift.
        double yaw = Math.toRadians(player.getLocation().getYaw());
        player.setVelocity(new Vector(-Math.sin(yaw) * FORWARD_SPEED, VERTICAL_SPEED,
                Math.cos(yaw) * FORWARD_SPEED));
        state.readyAt = currentTick + COOLDOWN_TICKS;
        state.awaitingLanding = true;
        state.observedAirborne = false;
        state.readinessAnnounced = false;
        world.playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, 0.8F, 1.4F);
        world.spawnParticle(Particle.CLOUD, player.getLocation(), 12, 0.2, 0.05, 0.2, 0.035);
    }

    void tick() {
        if (closed) return;
        currentTick++;
        for (Player player : world.getPlayers()) {
            if (!validParticipant(player)) continue;
            JumpState state = jumps.computeIfAbsent(player.getUniqueId(), ignored -> new JumpState());
            if (state.awaitingLanding) {
                if (!player.isOnGround()) state.observedAirborne = true;
                else if (state.observedAirborne) state.awaitingLanding = false;
            }
            if (!state.awaitingLanding && currentTick >= state.readyAt
                    && !state.readinessAnnounced && player.isOnGround()) {
                state.readinessAnnounced = true;
                Location feet = player.getLocation();
                player.playSound(feet, Sound.BLOCK_PISTON_CONTRACT, 0.5F, 1.8F);
                player.spawnParticle(Particle.ELECTRIC_SPARK, feet, 8, 0.25, 0.1, 0.25, 0.015);
            }
        }
    }

    void onRespawn(Player player) {
        JumpState state = jumps.get(player.getUniqueId());
        if (state == null) return;
        state.awaitingLanding = false;
        state.observedAirborne = false;
        // Death cannot manufacture a fresh cooldown.
        state.readinessAnnounced = false;
    }

    private boolean validParticipant(Player player) {
        return !closed && player.isOnline() && !player.isDead()
                && player.getWorld() == world && activeParticipant.test(player);
    }

    @Override
    public void close() {
        closed = true;
        HandlerList.unregisterAll(this);
        jumps.clear();
    }
}
