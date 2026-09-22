package com.biel.lobby.mapes.jocs.roborampage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.random.RandomGenerator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.biel.lobby.localization.MessageKey;
import com.biel.lobby.localization.Messages;
import com.biel.lobby.mapes.jocs.roborampage.utils.RoboRampageRules;
import com.biel.lobby.utilities.Utils;

/** Creates one Taser upgrade, one major reward and one scaffold bundle per player between waves. */
final class SupplyDropController implements AutoCloseable {
    private static final int DROP_HEIGHT = 8;
    private static final int BOW_ARROW_COUNT = 16;

    private final World world;
    private final RandomGenerator random;
    private final TaserController tasers;
    private final NamespacedKey taserUpgradeKey;
    private final Set<UUID> unclaimedItemIds = new HashSet<>();

    SupplyDropController(Plugin plugin, World world, RandomGenerator random, TaserController tasers) {
        this.world = world;
        this.random = random;
        this.tasers = tasers;
        this.taserUpgradeKey = new NamespacedKey(plugin, "robo_rampage_taser_upgrade");
    }

    void dropWaveSupplies(Location battleCenter, int waveNumber, List<Player> players) {
        clearUnclaimed();
        Location dropOrigin = centralDropOrigin(battleCenter);
        for (Player player : players) {
            if (!player.isOnline() || player.getWorld() != world) continue;
            dropOwned(dropOrigin, new ItemStack(
                    Material.SCAFFOLDING, RoboRampageRules.scaffoldingPerPlayer(waveNumber)), player);
            dropRewards(dropOrigin, waveNumber, player);
        }
        world.spawnParticle(Particle.END_ROD, dropOrigin, 70, 2.0, 1.5, 2.0, 0.04);
        world.playSound(dropOrigin, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0F, 0.8F);
    }

    boolean handlePickup(PlayerPickupItemEvent event, Player player) {
        Item droppedItem = event.getItem();
        if (!isTaserUpgrade(droppedItem.getItemStack())) return false;
        event.setCancelled(true);
        if (!tasers.upgradeCarriedTaser(player)) return true;
        unclaimedItemIds.remove(droppedItem.getUniqueId());
        droppedItem.remove();
        return true;
    }

    void clearUnclaimed() {
        for (UUID itemId : unclaimedItemIds) {
            Entity item = Bukkit.getEntity(itemId);
            if (item != null && item.isValid()) item.remove();
        }
        unclaimedItemIds.clear();
    }

    private void dropRewards(Location dropOrigin, int waveNumber, Player player) {
        PlayerInventory inventory = player.getInventory();
        boolean carriesBow = inventory.contains(Material.BOW);
        boolean carriesArrow = inventory.contains(Material.ARROW);
        ItemStack armorUpgrade = armorUpgrade(player, waveNumber);
        var supplyPlan = RoboRampageRules.supplyPlan(
                waveNumber,
                carriesBow,
                carriesArrow,
                armorUpgrade != null,
                tasers.canUpgrade(player),
                random);
        if (supplyPlan.taserUpgrade()) dropOwned(dropOrigin, createTaserUpgrade(), player);
        switch (supplyPlan.majorReward()) {
            case ARMOR -> dropOwned(dropOrigin, armorUpgrade, player);
            case BOW -> dropRangedKit(dropOrigin, player, carriesBow);
            case TASER_UPGRADE -> throw new IllegalStateException("Taser upgrade cannot be a major reward");
        }
    }

    private void dropRangedKit(Location dropOrigin, Player player, boolean carriesBow) {
        if (!carriesBow) dropOwned(dropOrigin, new ItemStack(Material.BOW), player);
        dropOwned(dropOrigin, new ItemStack(Material.ARROW, BOW_ARROW_COUNT), player);
    }

    private ItemStack armorUpgrade(Player player, int waveNumber) {
        PlayerInventory inventory = player.getInventory();
        Material[] targetArmor = armorTier(waveNumber);
        ItemStack[] equippedArmor = {
                inventory.getHelmet(), inventory.getChestplate(), inventory.getLeggings(), inventory.getBoots()
        };
        List<Material> upgrades = new ArrayList<>();
        for (int slot = 0; slot < targetArmor.length; slot++) {
            Material equippedMaterial = equippedArmor[slot] == null ? Material.AIR : equippedArmor[slot].getType();
            if (armorTierScore(equippedMaterial) < armorTierScore(targetArmor[slot])) upgrades.add(targetArmor[slot]);
        }
        return upgrades.isEmpty() ? null : new ItemStack(upgrades.get(random.nextInt(upgrades.size())));
    }

    private static Material[] armorTier(int waveNumber) {
        if (waveNumber >= 5) {
            return new Material[] {Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
                    Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS};
        }
        if (waveNumber >= 2) {
            return new Material[] {Material.IRON_HELMET, Material.IRON_CHESTPLATE,
                    Material.IRON_LEGGINGS, Material.IRON_BOOTS};
        }
        return new Material[] {Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
                Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS};
    }

    private static int armorTierScore(Material material) {
        String name = material.name();
        if (name.startsWith("DIAMOND_")) return 3;
        if (name.startsWith("IRON_")) return 2;
        if (name.startsWith("CHAINMAIL_")) return 1;
        return 0;
    }

    private ItemStack createTaserUpgrade() {
        ItemStack upgrade = Utils.setItemNameAndLore(
                new ItemStack(Material.PRISMARINE_CRYSTALS),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_TASER_UPGRADE_NAME),
                Messages.sharedItemMarker(MessageKey.ROBO_RAMPAGE_TASER_UPGRADE_LORE));
        ItemMeta meta = upgrade.getItemMeta();
        meta.getPersistentDataContainer().set(taserUpgradeKey, PersistentDataType.BYTE, (byte) 1);
        meta.setEnchantmentGlintOverride(true);
        upgrade.setItemMeta(meta);
        return upgrade;
    }

    private boolean isTaserUpgrade(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer()
                .has(taserUpgradeKey, PersistentDataType.BYTE);
    }

    private Location centralDropOrigin(Location battleCenter) {
        int highestY = world.getHighestBlockYAt(battleCenter);
        return new Location(world, battleCenter.getBlockX() + 0.5, highestY + DROP_HEIGHT,
                battleCenter.getBlockZ() + 0.5);
    }

    private void dropOwned(Location origin, ItemStack itemStack, Player owner) {
        Location location = origin.clone().add(
                random.nextDouble(-1.75, 1.75), random.nextDouble(0, 1.5), random.nextDouble(-1.75, 1.75));
        Item item = world.dropItem(location, itemStack);
        item.setOwner(owner.getUniqueId());
        item.setThrower(owner.getUniqueId());
        item.setPickupDelay(20);
        item.setCanMobPickup(false);
        item.setUnlimitedLifetime(true);
        item.setWillAge(false);
        item.setGlowing(true);
        item.setVelocity(new Vector(
                random.nextDouble(-0.05, 0.05), -0.12, random.nextDouble(-0.05, 0.05)));
        unclaimedItemIds.add(item.getUniqueId());
    }

    @Override
    public void close() {
        clearUnclaimed();
    }
}
