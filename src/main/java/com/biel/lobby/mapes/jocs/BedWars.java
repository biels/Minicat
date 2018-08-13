package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.Regions.Cuboid;
import com.biel.lobby.mapes.JocEquipsLastStanding;
import com.biel.lobby.mapes.JocObjectius;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import com.connorlinfoot.bountifulapi.BountifulAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BedWars extends JocEquipsLastStanding {

    /*
        General
     */

    @Override
    protected void customJocIniciat() {
        super.customJocIniciat();
        Equips.stream()
                .map(e -> (EquipBedWars)e)
                .forEach(EquipBedWars::detectBed);
        setBlockBreakPlace(true);
        setGiveStartingItemsRespawn(true);
    }

    @Override
    protected ArrayList<Equip> getDesiredTeams() {

        ArrayList<Equip> equips = new ArrayList<>();

            equips.add(new EquipBedWars(DyeColor.RED, "vermell"));
            equips.add(new EquipBedWars(DyeColor.BLUE, "blau"));

        return equips;

    }

    @Override
    protected void setCustomGameRules() {

    }

    @Override
    public String getGameName() {
        return "BedWars";
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player ply) {

        ArrayList<ItemStack> items = new ArrayList<>();

        Equip e = obtenirEquip(ply);

            // Armor
            items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e));
            items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
            items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e));
            items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, e));

            // Sword
            ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
            sword.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 5);
            items.add(sword);

            // Bow
            ItemStack bow = new ItemStack(Material.BOW, 1);
            bow.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            items.add(bow);

            // Balancing multiplier
            double balancingMultiplier = getBalancingMultiplier(e);

            // Pickaxe
            ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
            if (balancingMultiplier > 1) pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, (balancingMultiplier > 1.20 ? 2 : 1));
            items.add(pickaxe);

            // Blocks
            int block_amount = (int) (45 * balancingMultiplier);
            if (block_amount > 64) block_amount = 64;

            if (obtenirEquip(ply).getId() == 0) items.add(new ItemStack(Material.STAINED_CLAY, block_amount, (short) 14));
            else items.add(new ItemStack(Material.STAINED_CLAY, block_amount, (short) 10));

            // Arrows
            int arrows = (int) (50 * balancingMultiplier);
            if (arrows > 64) arrows = 64;
            items.add(new ItemStack(Material.ARROW, arrows));

        return items;

    }

    /*
        Game specific functions
     */

    @Override
    public EquipBedWars obtenirEquip(Player ply) {
        return obtenirEquip(ply, EquipBedWars.class);
    }


    @Override
    protected void updateScoreBoard(Player ply) {

        super.updateScoreBoard(ply);

        if(!JocIniciat) return;

        ArrayList<String> list = new ArrayList<>();

        list.add(ChatColor.RED + "");

        for(Equip equip : Equips) {

            EquipBedWars equipBedWars = (EquipBedWars) equip;

            String teamSidebar = equipBedWars.getChatColor() + "" + ChatColor.BOLD + "" + equipBedWars.getAdjectiu().toUpperCase().charAt(0) + "" + ChatColor.RESET + "" + ChatColor.GRAY + " Equip " + equipBedWars.getAdjectiu() + ": ";

            if(equipBedWars.isBedAlive()) list.add(teamSidebar + ChatColor.BOLD + "" + ChatColor.GREEN + "✓");
            else if (equipBedWars.getPlayers().size() > 0) list.add(teamSidebar + ChatColor.YELLOW + equipBedWars.getPlayers().size());
            else list.add(teamSidebar + ChatColor.BOLD + "" + ChatColor.RED + "✗");

        }

        list.add(ChatColor.GREEN + "");

        ScoreBoardUpdater.setScoreBoard(ply, ChatColor.BOLD + "" + ChatColor.RED + "Bed Wars", list, null);

    }

    @Override
    protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
        super.onPlayerMove(evt, p);

        Player ply = evt.getPlayer();

        if (isSpectator(ply)) return;
        if (!JocIniciat) return;

        if (ply.getLocation().getY() < 80) ply.damage(10000);

    }

    @Override
    protected void onBlockHitByProjectile(ProjectileHitEvent evt, Block b, Projectile proj) {

        super.onBlockHitByProjectile(evt, b, proj);

        Material t = b.getType();
        if (
                t == Material.GLASS ||
                t == Material.STAINED_GLASS ||
                t == Material.STAINED_GLASS_PANE ||
                t == Material.THIN_GLASS
        ) {

            b.setType(Material.AIR);
            getWorld().playSound(b.getLocation(), Sound.BLOCK_GLASS_BREAK, 15F, 1.2F);
            proj.remove();

            for (BlockFace f : BlockFace.values()) {

                if (GUtils.Possibilitat(58)) continue;
                Block relative = b.getRelative(f);
                t = relative.getType();

                if (
                        t == Material.GLASS ||
                        t == Material.STAINED_GLASS ||
                        t == Material.STAINED_GLASS_PANE ||
                        t == Material.THIN_GLASS
                ) {
                    relative.setType(Material.AIR);
                }

            }
        }
    }

    @Override
    protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {

        super.onPlayerDeath(evt, killed);
        Bukkit.broadcastMessage("isBedAlive: " + obtenirEquip(killed).isBedAlive());
        if(!obtenirEquip(killed).isBedAlive()) removeAlive(killed);
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent evt, Block blk) {

        super.onBlockBreak(evt, blk);

        // Check for bed
        if(evt.getBlock().getType() == Material.BED || evt.getBlock().getType() == Material.BED_BLOCK) {

            evt.setDropItems(false);
            Player ply = evt.getPlayer();

            // S'han de tenir en compte que un llit es un bloc doble i per tant s'han de comprovar els blocs alrededor
            if(
                obtenirEquip(ply).getBedLocation().equals(evt.getBlock().getLocation().add(1, 0, 0)) ||
                obtenirEquip(ply).getBedLocation().equals(evt.getBlock().getLocation().add(-1, 0, 0)) ||
                obtenirEquip(ply).getBedLocation().equals(evt.getBlock().getLocation().add(0, 0, 1)) ||
                obtenirEquip(ply).getBedLocation().equals(evt.getBlock().getLocation().add(0, 0, -1)) ||
                obtenirEquip(ply).getBedLocation().equals(evt.getBlock().getLocation())
            ) {

                evt.setCancelled(true);
                ply.playSound(ply.getLocation(), Sound.ENTITY_VILLAGER_NO, 100, 1);
                String msg = ChatColor.RED + "" + ChatColor.ITALIC + "No pots destruir el teu propi llit";
                BountifulAPI.sendActionBar(ply, msg, 150);

            } else {

                sendGlobalSound(Sound.ENTITY_ENDERDRAGON_GROWL, 100, 1);
                String text = obtenirEquip(ply).getChatColor() == ChatColor.RED
                        ? ChatColor.GREEN + "S'ha destruit el llit " + ChatColor.BLUE + "blau"
                        : ChatColor.GREEN + "S'ha destruit el llit " + ChatColor.RED + "vermell";

                for (Player p : getPlayers()) {
                    BountifulAPI.sendTitle(p, 10, 20, 10, "", text);
                }

            }

            updateScoreBoards();

        }

        // Check if block has been placed by a player or was on the map by default

    }

    public class EquipBedWars extends Equip {

        private Location bedLocation;
        EquipBedWars(DyeColor color, String adj) {
            super(color, adj);
        }

        void detectBed() {

            int radiusFromSpawn = 8;
            Cuboid cuboid = GUtils.getCuboidAround(this.getTeamSpawnLocation(), radiusFromSpawn);
            Optional<Block> optionalBed = cuboid.getBlocks().stream()
                    .filter(block -> block.getType().equals(Material.BED_BLOCK))
                    .findAny();

            if(optionalBed.isPresent()) {

                bedLocation = optionalBed.get().getLocation();
                getWorld().playEffect(bedLocation, Effect.MOBSPAWNER_FLAMES, 0);

            } else {
                Bukkit.broadcastMessage("[Error] No s'ha pogut trobar el llit per a l'equip " + this.getAdjectiuColored());
            }
        }

        Location getBedLocation() {
            return bedLocation;
        }

        boolean isBedAlive() { return bedLocation != null && (bedLocation.getBlock().getType() == Material.BED_BLOCK); }

    }
}
