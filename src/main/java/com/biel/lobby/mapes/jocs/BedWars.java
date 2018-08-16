package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.Regions.Cuboid;
import com.biel.lobby.mapes.JocEquipsLastStanding;
import com.biel.lobby.utilities.BUtils;
import com.biel.lobby.utilities.Utils;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Optional;

public class BedWars extends JocEquipsLastStanding {
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
        items.add(new ItemStack(Material.WOODEN_SWORD, 1));
        items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, e));
        items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, e));
        items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, e));
        items.add(new ItemStack(Material.ARROW, 1));

        return items;
    }

    @Override
    public EquipBedWars obtenirEquip(Player ply) {
        return obtenirEquip(ply, EquipBedWars.class);
    }
    public class EquipBedWars extends Equip{
        private org.bukkit.Location bedLocation;
        public EquipBedWars(DyeColor color, String adj) {
            super(color, adj);
        }
        public void detectBed(){
            int radiusFromSpawn = 8;
            Cuboid cuboid = GUtils.getCuboidAround(this.getTeamSpawnLocation(), radiusFromSpawn);
            Optional<Block> optionalBed = cuboid.getBlocks().stream()
                    .filter(BUtils::isBedBlock)
                    .findAny();
            if(optionalBed.isPresent()){
                bedLocation = optionalBed.get().getLocation();
                getWorld().playEffect(bedLocation, Effect.MOBSPAWNER_FLAMES, 0);
            } else {
                sendMessage("[Error] No s'ha pogut trobar el llit per a l'equip " + this.getAdjectiuColored());
            }
        }
        public Location getBedLocation() {
            return bedLocation;
        }
        public boolean isBedAlive(){
            return BUtils.isBedBlock(bedLocation.getBlock());
        }
    }

    @Override
    protected void onPlayerDeath(PlayerDeathEvent evt, Player killed) {
        super.onPlayerDeath(evt, killed);
        if(!obtenirEquip(killed).isBedAlive())
            removeAlive(killed);
    }
}
