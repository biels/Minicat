package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;
import java.util.Collections;

import com.biel.BielAPI.Utils.GUtils;
import org.bukkit.Effect;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.Utils;
import org.bukkit.util.Vector;

public class BatallaAerea extends JocScoreRace {

    @Override
    protected int getFinishScore() {
        // TODO Auto-generated method stub
        return 2 + getPlayers().size();
    }

    @Override
    public String getGameName() {
        // TODO Auto-generated method stub
        return "Batalla Aerea";
    }
    @Override
    protected ArrayList<ItemStack> getStartingItems(Player ply) {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.IRON_SWORD, 1));
        ItemStack bow =new ItemStack(Material.BOW, 1);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        items.add(bow);
        Potion p1 = new Potion(PotionType.INSTANT_DAMAGE);
        p1.setSplash(true);
        items.add(p1.toItemStack(1));
        Potion p2 = new Potion(PotionType.SLOWNESS);
        p2.setSplash(true);
        items.add(p2.toItemStack(2));
        items.add(new ItemStack(Material.ARROW, 1));
        items.add(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        ply.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
        ply.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
        items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        items.add(new ItemStack(Material.CHAINMAIL_BOOTS, 1));

        return items;
    }
    @Override
    protected int getBaseSkillUnlockerAmount() {
        // TODO Auto-generated method stub
        return 1;
    }
    @Override
    protected void teletransportarTothom() {
        for (Player d : getPlayers()) {  // d gets successively each value in ar.
            teleportToRandomSpawn(d);
        }
    }

    protected void teleportToRandomSpawn(Player d) {
        Location loc;
        loc = getRandomSpawnLoc();
        d.teleport(loc);
    }
    private Location getRandomSpawnLoc() {
        ArrayList<Location> loc = pMapaActual().ObtenirLocations("arena", world);
        Collections.shuffle(loc);
        Location l = loc.get(0);
        l.add(0, 2, 0);
        return l;
    }


    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed,
                                         Player killer) {
        //
        super.onPlayerDeathByPlayer(evt, killed, killer);
        incrementScore(killer);
        Potion p1 = new Potion(PotionType.REGEN);
        killer.getInventory().addItem(p1.toItemStack(1));
        killer.getInventory().addItem(getMagnusLauncherItem());
        evt.getDrops().clear();
        evt.setDeathMessage(killer.getName() + " ha matat a " + killed.getName() + " [+1]");
        updateScoreBoards();
    }

    @Override
    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent evt, Player damaged, Player damager,
                                          boolean ranged) {
        // TODO Auto-generated method stub
        super.onPlayerDamageByPlayer(evt, damaged, damager, ranged);
        if(ranged)damager.getInventory().addItem(new ItemStack(Material.ARROW, 1));
    }
    @Override
    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent evt, Player p) {
        // TODO Auto-generated method stub
        super.onPlayerRespawnAfterTick(evt, p);
        teleportToRandomSpawn(p);
    }
    public double getMinimumHeight(){
        double r = 10.0;
        if(pMapaActual().ExisteixPropietat("MinHeight")){
            r = pMapaActual().ObtenirPropietatInt("MinHeight");
        }
        return r;
    }
    public ItemStack getMagnusLauncherItem(){
        ItemStack item = new ItemStack(Material.FIREWORK, 1);

        return item;
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType() == Material.FIREWORK) {
            event.setCancelled(true);
            event.getItemDrop().getItemStack().setType(Material.AIR);
        }
    }
    @Override
    protected void onPlayerMove(PlayerMoveEvent evt, Player p) {
        // TODO Auto-generated method stub
        super.onPlayerMove(evt, p);

        if(evt.getTo().getY() < getMinimumHeight()){
            teleportToRandomSpawn(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30, 128, true), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 128, true), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 128, true), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 30, 128, true), true);
            // Aixó dona els items inicials als jugadors

        }

        if(p.getInventory().contains(getMagnusLauncherItem(), 1)){

        }else if(p.getInventory().containsAtLeast(getMagnusLauncherItem(), 2)){
            p.getInventory().remove(getMagnusLauncherItem());


            }
            else{p.getInventory().addItem(getMagnusLauncherItem());}
        }
    }




