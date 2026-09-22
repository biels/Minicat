package com.biel.lobby.mapes.jocs.roborampage;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

/** A grounded sneak-jump boost, owned by one match rather than an armor item. */
final class PistonJumpController implements Listener, AutoCloseable {
    static final int COOLDOWN_TICKS = 160;
    static final double VERTICAL_SPEED = 0.48;
    static final double FORWARD_SPEED = 0.34;

    private final World world;
    private final Predicate<Player> activeParticipant;
    private final Map<UUID, JumpState> jumps = new HashMap<>();
    private final Map<UUID, PendingJump> pendingJumps = new HashMap<>();
    private long currentTick;
    private boolean closed;

    private static final class JumpState {
        long readyAt;
        boolean awaitingLanding;
        boolean observedAirborne;
        boolean readinessAnnounced;
        boolean jumpHeld;
    }

    private record PendingJump(Player player, Location origin, PlayerJumpEvent event) {}

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
        if (event.isCancelled() || !validParticipant(player) || !player.isSneaking()) return;
        queueJump(player, event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInput(PlayerInputEvent event) {
        Player player = event.getPlayer();
        if (!validParticipant(player)) return;
        JumpState state = jumps.computeIfAbsent(player.getUniqueId(), ignored -> new JumpState());
        boolean pressedJump = event.getInput().isJump() && !state.jumpHeld;
        state.jumpHeld = event.getInput().isJump();
        if (!pressedJump || !event.getInput().isSneak()) return;
        // Sneaking through a scaffold top does not produce a native ground-jump event.
        Location feet = player.getLocation();
        var support = feet.clone().subtract(0, 0.125, 0).getBlock();
        if (support.getType() == Material.SCAFFOLDING
                && Math.abs(feet.getY() - (support.getY() + 1)) <= 0.125) queueJump(player, null);
    }

    private void queueJump(Player player, PlayerJumpEvent event) {
        JumpState state = jumps.computeIfAbsent(player.getUniqueId(), ignored -> new JumpState());
        if (player.isFlying() || player.isGliding() || player.isInsideVehicle()
                || state.awaitingLanding || currentTick < state.readyAt) return;
        pendingJumps.putIfAbsent(player.getUniqueId(), new PendingJump(player, player.getLocation(), event));
    }

    private void launch(PendingJump pending) {
        Player player = pending.player();
        if (!validParticipant(player) || player.isFlying() || player.isGliding() || player.isInsideVehicle()
                || (pending.event() != null && pending.event().isCancelled())
                || player.getLocation().distanceSquared(pending.origin()) > 2.25) return;
        JumpState state = jumps.get(player.getUniqueId());
        // Apply after Paper's jumpFromGround(), which would overwrite velocity inside the event.
        double yaw = Math.toRadians(player.getLocation().getYaw());
        double upwardSpeed = pending.event() == null ? 0.54 : VERTICAL_SPEED;
        player.setVelocity(new Vector(-Math.sin(yaw) * FORWARD_SPEED, upwardSpeed,
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
        for (PendingJump pending : new ArrayList<>(pendingJumps.values())) launch(pending);
        pendingJumps.clear();
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
        pendingJumps.remove(player.getUniqueId());
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
        pendingJumps.clear();
    }
}
