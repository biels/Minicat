package com.biel.lobby.mapes.jocs.obsidiandefenders;

import com.biel.lobby.mapes.jocs.obsidiandefenders.utils.LauncherTrajectory;
import com.biel.lobby.mapes.jocs.obsidiandefenders.utils.TeamUpgrades;
import com.biel.lobby.mapes.jocs.obsidiandefenders.utils.Watchtowers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import com.biel.lobby.utilities.PaperMessages;

final class LauncherController implements Listener {
    private final World world;
    private final Plugin plugin;
    private final Predicate<Player> participant;
    private final ToIntFunction<Player> teamOf;
    private final Watchtowers upgrades;
    private final Map<Block, Watchtowers.Tower> buttons = new HashMap<>();
    private final Map<Block, BlockData> originalBlocks = new LinkedHashMap<>();
    private final Map<Block, BlockData> installedBlocks = new HashMap<>();
    private final Map<UUID, Occupant> occupants = new HashMap<>();
    private final Map<UUID, Flight> flights = new HashMap<>();
    private long tick;

    private record Occupant(int tower, long since) {}
    private static final class Flight {
        final Watchtowers.Tower tower;
        final Location start;
        final long started;
        boolean propelled;
        final Watchtowers.FallProtection protection;
        Flight(Watchtowers.Tower tower, Location start, long started) {
            this.tower = tower; this.start = start; this.started = started;
            protection = new Watchtowers.FallProtection(started);
        }
    }

    LauncherController(World world, Plugin plugin, Predicate<Player> participant,
            ToIntFunction<Player> teamOf, TeamUpgrades teamUpgrades) {
        this.world = world; this.plugin = plugin; this.participant = participant;
        this.teamOf = teamOf;
        this.upgrades = new Watchtowers(teamUpgrades);
        for (var tower : Watchtowers.TOWERS) for (var position : tower.buttons()) buttons.put(block(position), tower);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private Block block(Watchtowers.Position position) {
        return world.getBlockAt(position.x(), position.y(), position.z());
    }

    private boolean eligible(Player player) {
        return player != null && player.isOnline() && !player.isDead() && player.getWorld() == world
                && !player.isFlying() && !player.isGliding() && !player.isInsideVehicle() && participant.test(player);
    }

    boolean isPlate(Block block) {
        return Watchtowers.TOWERS.stream().filter(tower -> upgrades.unlocked(tower.team()))
                .flatMap(tower -> tower.plates().stream()).anyMatch(position -> block(position).equals(block));
    }

    boolean interact(Player player, Block clicked) {
        var tower = buttons.get(clicked);
        if (tower == null) return false;
        if (eligible(player) && upgrades.unlocked(tower.team())) launch(player, tower);
        return true;
    }

    boolean installAndPay(int team, BooleanSupplier pay) {
        Map<Block, BlockData> transaction = new LinkedHashMap<>();
        boolean committed = false;
        try {
            if (!install(team, transaction) || !pay.getAsBoolean()) return false;
            transaction.forEach(originalBlocks::putIfAbsent);
            transaction.keySet().forEach(block -> installedBlocks.put(block, block.getBlockData().clone()));
            committed = true;
            return true;
        } finally {
            if (!committed) transaction.forEach((block, data) -> block.setBlockData(data, false));
        }
    }

    private boolean install(int team, Map<Block, BlockData> transaction) {
        Map<Block, BlockData> desired = new LinkedHashMap<>();
        for (var tower : Watchtowers.TOWERS) {
            if (tower.team() != team) continue;
            for (var position : tower.plates()) {
                Block plate = block(position);
                if (plate.getRelative(BlockFace.DOWN).getType() != Material.JUNGLE_PLANKS
                        || !plate.getRelative(BlockFace.UP).isPassable()) return false;
                desired.put(plate, Material.HEAVY_WEIGHTED_PRESSURE_PLATE.createBlockData());
            }
            for (var position : tower.buttons()) {
                Block button = block(position);
                Block support = button.getRelative(tower.direction(), 0, 0);
                if (!support.getType().isSolid()) return false;
                Switch data = (Switch) Material.STONE_BUTTON.createBlockData();
                data.setAttachedFace(FaceAttachable.AttachedFace.WALL);
                data.setFacing(tower.direction() == 1 ? BlockFace.WEST : BlockFace.EAST);
                desired.put(button, data);
            }
        }
        for (var entry : desired.entrySet()) {
            if (!entry.getKey().getType().isAir() && entry.getKey().getType() != entry.getValue().getMaterial()) return false;
        }
        for (var entry : desired.entrySet()) {
            transaction.put(entry.getKey(), entry.getKey().getBlockData().clone());
            entry.getKey().setBlockData(entry.getValue(), false);
        }
        return true;
    }

    private void sampleOccupants() {
        Map<UUID, Occupant> current = new HashMap<>();
        for (Player player : world.getPlayers()) {
            if (!eligible(player) || flights.containsKey(player.getUniqueId()) || !player.isOnGround()) continue;
            for (var tower : Watchtowers.TOWERS) {
                if (!upgrades.unlocked(tower.team()) || !tower.onPlates(player.getLocation().toVector())) continue;
                if (block(new Watchtowers.Position(player.getLocation().getBlockX(), 52,
                        player.getLocation().getBlockZ())).getType() != Material.HEAVY_WEIGHTED_PRESSURE_PLATE) continue;
                Occupant previous = occupants.get(player.getUniqueId());
                current.put(player.getUniqueId(), previous != null && previous.tower == tower.id()
                        ? previous : new Occupant(tower.id(), tick));
            }
        }
        occupants.clear();
        occupants.putAll(current);
    }

    private void launch(Player activator, Watchtowers.Tower tower) {
        sampleOccupants();
        List<Watchtowers.Waiting> waiting = new ArrayList<>();
        occupants.forEach((id, occupant) -> {
            if (occupant.tower == tower.id()) waiting.add(new Watchtowers.Waiting(id, occupant.since));
        });
        UUID selected = Watchtowers.passenger(activator.getUniqueId(), waiting);
        if (selected == null) { message(activator, "No hi ha ningú sobre les plaques"); return; }
        Player passenger = Bukkit.getPlayer(selected);
        if (!eligible(passenger)) return;
        if (!upgrades.fire(tower.id(), tick)) {
            message(activator, "Recarregant: " + upgrades.reloadSeconds(tower.id(), tick) + " s");
            return;
        }
        flights.put(selected, new Flight(tower, passenger.getLocation(), tick));
        occupants.remove(selected);
        passenger.setFallDistance(0);
        passenger.setVelocity(new Vector(0, 0.5, 0));
        sound(tower, Sound.BLOCK_PISTON_EXTEND, 1);
    }

    void tick() {
        tick++;
        sampleOccupants();
        for (var entry : new ArrayList<>(flights.entrySet())) {
            Player player = Bukkit.getPlayer(entry.getKey());
            Flight flight = entry.getValue();
            if (!eligible(player) || flight.protection.expired(tick) || player.isInWater()) {
                forget(entry.getKey());
                continue;
            }
            if (!flight.propelled) {
                Location at = player.getLocation();
                if (at.distanceSquared(flight.start) > 9 || tick - flight.started > 8) { forget(entry.getKey()); continue; }
                if (!player.isOnGround() && at.getY() >= flight.start.getY() + 0.3) {
                    Vector impulse = LauncherTrajectory.find(flight.tower, at.toVector(),
                            LauncherTrajectory.terrain(world));
                    if (impulse != null) player.setVelocity(impulse);
                    else message(player, "Trajecte bloquejat: torna-ho a provar");
                    flight.propelled = true;
                    sound(flight.tower, Sound.BLOCK_PISTON_CONTRACT, 0.7f);
                }
            } else if (player.isOnGround()) {
                flight.protection.land(tick);
            }
        }
        if (tick % 20 == 0) occupants.forEach((id, occupant) -> {
            Player player = Bukkit.getPlayer(id);
            int seconds = upgrades.reloadSeconds(occupant.tower, tick);
            if (player != null) message(player, seconds == 0 ? "Llançador preparat" : "Recarregant: " + seconds + " s");
        });
    }

    private void sound(Watchtowers.Tower tower, Sound sound, float volume) {
        Location source = new Location(world, tower.rearX() + 0.5, 52.5, tower.middleZ() + 0.5);
        for (Player listener : world.getPlayers()) if (listener.getLocation().distanceSquared(source) <= 24 * 24)
            listener.playSound(source, sound, volume, 1);
    }

    private static void message(Player player, String text) {
        PaperMessages.sendActionBar(player, ChatColor.GOLD + text, 30);
    }

    void forget(UUID player) { flights.remove(player); occupants.remove(player); }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && event.getCause() == EntityDamageEvent.DamageCause.FALL
                && flights.containsKey(player.getUniqueId()) && eligible(player)) {
            event.setCancelled(true);
            player.setFallDistance(0);
            forget(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) { forget(event.getPlayer().getUniqueId()); }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCombat(EntityDamageByEntityEvent event) {
        Flight flight = flights.get(event.getEntity().getUniqueId());
        if (flight != null) flight.propelled = true; // Never overwrite an intervening combat knockback.
    }
    @EventHandler public void onQuit(PlayerQuitEvent event) { forget(event.getPlayer().getUniqueId()); }
    @EventHandler public void onDeath(PlayerDeathEvent event) { forget(event.getEntity().getUniqueId()); }

    void close() {
        HandlerList.unregisterAll(this);
        flights.clear();
        occupants.clear();
        originalBlocks.forEach((block, original) -> {
            if (block.getBlockData().equals(installedBlocks.get(block))) block.setBlockData(original, false);
        });
        originalBlocks.clear();
        installedBlocks.clear();
    }
}
