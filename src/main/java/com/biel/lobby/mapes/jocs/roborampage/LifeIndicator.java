package com.biel.lobby.mapes.jocs.roborampage;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import com.biel.lobby.localization.MessageArgument;
import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;
import com.biel.lobby.mapes.jocs.roborampage.utils.TeamLives;
import com.biel.lobby.utilities.Utils;

/** A protected hotbar display; TeamLives remains the only source of life counts. */
final class LifeIndicator implements Listener, AutoCloseable {
    private static final int HOTBAR_SLOT = 8;
    // Red heart skin: https://minecraft-heads.com/custom-heads/head/60535-heart-red
    private static final URI HEART_SKIN = URI.create("https://textures.minecraft.net/texture/"
            + "eb76b4ee988572297cbd874683bee96ae3c55ce94c004e51adc82cee16cd0b0c");
    private final World world;
    private final TeamLives lives;
    private final NamespacedKey indicatorKey;
    private final ItemStack heartTemplate;
    private boolean closed;

    LifeIndicator(Plugin plugin, World world, TeamLives lives) {
        this.world = world;
        this.lives = lives;
        indicatorKey = new NamespacedKey(plugin, "robo_rampage_lives");
        heartTemplate = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) heartTemplate.getItemMeta();
        var profile = Bukkit.createPlayerProfile(UUID.fromString("eb76b4ee-9885-4229-9cbd-874683bee96a"));
        var textures = profile.getTextures();
        try {
            textures.setSkin(HEART_SKIN.toURL());
        } catch (MalformedURLException exception) {
            throw new IllegalStateException("Invalid heart skin URL", exception);
        }
        profile.setTextures(textures);
        meta.setOwnerProfile(profile);
        meta.setMaxStackSize(TeamLives.STARTING_LIVES);
        meta.getPersistentDataContainer().set(indicatorKey, PersistentDataType.BYTE, (byte) 1);
        heartTemplate.setItemMeta(meta);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    void update(Player player) {
        if (!belongsToMatch(player)) return;
        int remainingLives = lives.remaining(player.getUniqueId());
        if (remainingLives == 0) { remove(player); return; }
        PlayerInventory inventory = player.getInventory();
        ItemStack previous = inventory.getItem(HOTBAR_SLOT);
        if (previous != null && !previous.getType().isAir() && !isIndicator(previous)) {
            int emptySlot = inventory.firstEmpty();
            // Never overwrite equipment if an external edit filled the reserved slot.
            if (emptySlot < 0) return;
            inventory.setItem(emptySlot, previous);
        }
        ItemStack hearts = Utils.setItemNameAndLore(heartTemplate.clone(),
                Messages.sharedNumberItemMarker(MessageKey.ROBO_RAMPAGE_LIVES_NAME,
                        MessageArgument.number("lives", remainingLives)),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_LIVES_LORE));
        hearts.setAmount(remainingLives);
        inventory.setItem(HOTBAR_SLOT, hearts);
    }

    boolean isIndicator(ItemStack item) {
        return item != null && item.hasItemMeta()
                && item.getItemMeta().getPersistentDataContainer().has(indicatorKey, PersistentDataType.BYTE);
    }

    void remove(Player player) {
        PlayerInventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (isIndicator(inventory.getItem(slot))) inventory.setItem(slot, null);
        }
    }

    private boolean belongsToMatch(Player player) {
        return !closed && player.getWorld() == world && lives.contains(player.getUniqueId());
    }

    @EventHandler public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player) || !belongsToMatch(player)) return;
        if (isIndicator(event.getCurrentItem()) || isIndicator(event.getCursor())
                || (event.getClickedInventory() instanceof PlayerInventory && event.getSlot() == HOTBAR_SLOT)
                || event.getHotbarButton() == HOTBAR_SLOT) event.setCancelled(true);
    }

    @EventHandler public void onDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player) || !belongsToMatch(player)) return;
        if (isIndicator(event.getOldCursor()) || event.getRawSlots().stream().anyMatch(slot ->
                event.getView().getInventory(slot) instanceof PlayerInventory
                        && event.getView().convertSlot(slot) == HOTBAR_SLOT)) event.setCancelled(true);
    }

    @EventHandler public void onDrop(PlayerDropItemEvent event) {
        if (belongsToMatch(event.getPlayer()) && isIndicator(event.getItemDrop().getItemStack())) event.setCancelled(true);
    }

    @EventHandler public void onSwap(PlayerSwapHandItemsEvent event) {
        if (belongsToMatch(event.getPlayer())
                && (isIndicator(event.getMainHandItem()) || isIndicator(event.getOffHandItem()))) event.setCancelled(true);
    }

    @EventHandler public void onInteract(PlayerInteractEvent event) {
        if (belongsToMatch(event.getPlayer()) && isIndicator(event.getItem())) event.setCancelled(true);
    }

    @Override public void close() {
        if (closed) return;
        closed = true;
        HandlerList.unregisterAll(this);
        world.getPlayers().forEach(this::remove);
    }
}
