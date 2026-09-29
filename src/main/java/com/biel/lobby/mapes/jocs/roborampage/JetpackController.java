package com.biel.lobby.mapes.jocs.roborampage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.biel.lobby.localization.MessageArgument;
import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;
import com.biel.lobby.mapes.jocs.roborampage.utils.JetpackRules;
import com.biel.lobby.utilities.Utils;

/** A short, rechargeable escape tool. Flight is velocity only; fuel belongs to the match. */
final class JetpackController implements Listener, AutoCloseable {
    private final World world;
    private final Predicate<Player> activeParticipant;
    private final NamespacedKey jetpackKey;
    private final Map<UUID, JetpackRules.State> states = new HashMap<>();
    private long currentTick;
    private boolean closed;

    JetpackController(Plugin plugin, World world, Predicate<Player> activeParticipant) {
        this.world = world;
        this.activeParticipant = activeParticipant;
        this.jetpackKey = new NamespacedKey(plugin, "robo_rampage_jetpack");
    }

    void register(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    ItemStack createStartingJetpack(Player player) {
        states.computeIfAbsent(player.getUniqueId(), ignored -> new JetpackRules.State());
        ItemStack jetpack = Utils.setItemNameAndLore(new ItemStack(Material.FEATHER),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_JETPACK_NAME),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_JETPACK_LORE_THRUST),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_JETPACK_LORE_RECHARGE));
        ItemMeta meta = jetpack.getItemMeta();
        meta.getPersistentDataContainer().set(jetpackKey, PersistentDataType.BYTE, (byte) 1);
        meta.setMaxStackSize(1);
        meta.setEnchantmentGlintOverride(true);
        jetpack.setItemMeta(meta);
        return jetpack;
    }

    boolean isJetpack(ItemStack item) {
        return item != null && item.getType() == Material.FEATHER && item.hasItemMeta()
                && item.getItemMeta().getPersistentDataContainer().has(jetpackKey, PersistentDataType.BYTE);
    }

    boolean handleInteraction(PlayerInteractEvent event, Player player) {
        if (event.getHand() != EquipmentSlot.HAND
                || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
                || !isJetpack(event.getItem())) return false;
        event.setCancelled(true);
        if (!validParticipant(player)) return true;
        JetpackRules.State state = states.computeIfAbsent(player.getUniqueId(), ignored -> new JetpackRules.State());
        boolean firing = state.toggle();
        player.playSound(player.getLocation(), firing ? Sound.BLOCK_FIRE_EXTINGUISH : Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF,
                0.6F, firing ? 1.8F : 1.2F);
        player.swingMainHand();
        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent event) {
        if (!closed && event.getPlayer().getWorld() == world && isJetpack(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    void tick() {
        if (closed) return;
        currentTick++;
        Map<UUID, Player> presentPlayers = new HashMap<>();
        for (Player player : world.getPlayers()) presentPlayers.put(player.getUniqueId(), player);
        for (var entry : states.entrySet()) {
            Player player = presentPlayers.get(entry.getKey());
            boolean eligible = validParticipant(player);
            JetpackRules.State state = entry.getValue();
            boolean thrust = state.tick(currentTick, eligible,
                    eligible && isJetpack(player.getInventory().getItemInMainHand()),
                    eligible && physicallySupported(player));
            if (!eligible) continue;
            if (thrust && player.getLocation().getY() < world.getMaxHeight() - 2) {
                double yaw = Math.toRadians(player.getLocation().getYaw());
                player.setVelocity(new Vector(-Math.sin(yaw) * JetpackRules.FORWARD_SPEED,
                        JetpackRules.VERTICAL_SPEED, Math.cos(yaw) * JetpackRules.FORWARD_SPEED));
                // A bounded, one-use shield handles the landing after this powered ascent.
                player.setFallDistance(0);
                if (currentTick % 3 == 0) {
                    world.spawnParticle(Particle.CLOUD, player.getLocation(), 3, 0.15, 0.05, 0.15, 0.025);
                }
            }
            if (currentTick % 10 == 0 && isJetpack(player.getInventory().getItemInMainHand())) showCharge(player, state);
        }
    }

    void onRespawn(Player player) {
        JetpackRules.State state = states.get(player.getUniqueId());
        if (state != null) state.resetFlight();
    }

    boolean protectsFall(Player player) {
        JetpackRules.State state = states.get(player.getUniqueId());
        return validParticipant(player) && state != null && state.consumeFallProtection(currentTick);
    }

    private boolean physicallySupported(Player player) {
        if (!player.isOnGround()) return false;
        Material support = player.getLocation().subtract(0, 0.15, 0).getBlock().getType();
        return support.isSolid() || support == Material.SCAFFOLDING;
    }

    private boolean validParticipant(Player player) {
        return !closed && player != null && player.isOnline() && !player.isDead()
                && player.getWorld() == world && activeParticipant.test(player)
                && (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
                && !player.isFlying() && !player.isGliding() && !player.isInsideVehicle();
    }

    private void showCharge(Player player, JetpackRules.State state) {
        player.sendActionBar(Messages.component(player,
                state.firing() ? MessageKey.ROBO_RAMPAGE_JETPACK_CHARGE_ACTIVE : MessageKey.ROBO_RAMPAGE_JETPACK_CHARGE_READY,
                MessageArgument.number("charge", state.chargePercent()), MessageArgument.number("maximum", 100)));
    }

    @Override
    public void close() {
        if (closed) return;
        closed = true;
        HandlerList.unregisterAll(this);
        states.clear();
        for (Player player : world.getPlayers()) {
            PlayerInventory inventory = player.getInventory();
            for (int slot = 0; slot < inventory.getSize(); slot++) {
                if (isJetpack(inventory.getItem(slot))) inventory.setItem(slot, null);
            }
        }
        for (Item item : world.getEntitiesByClass(Item.class)) {
            if (isJetpack(item.getItemStack())) item.remove();
        }
    }
}
