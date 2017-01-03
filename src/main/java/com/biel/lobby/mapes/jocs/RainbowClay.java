package com.biel.lobby.mapes.jocs;

import java.util.ArrayList;


import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.JocEquips.Equip;
import com.biel.lobby.mapes.JocObjectius;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.Utils;

public class RainbowClay extends JocObjectius {
  public RainbowClay() {
    super();

  }
  @Override
  public String getGameName() {
    return "RainbowClay";
  }
  @Override
  protected ArrayList<Equip> getDesiredTeams() {
    ArrayList<Equip> equips = new ArrayList<>();
    equips.add(new EquipObjectius(DyeColor.RED, "vermell")); //Id 0
    equips.add(new EquipObjectius(DyeColor.BLUE, "blau")); //Id 1
    return equips;
  }
  @Override
  protected ArrayList<Objectiu> getDesiredObjectivesTeam(EquipObjectius e) {
    ArrayList<Objectiu> objectius = new ArrayList<>();
    //Bukkit.broadcastMessage("Cores" + Integer.toString(e.getId() + 1));
    ArrayList<Location> coreLocs = pMapaActual().ObtenirLocations("Cores" + Integer.toString(e.getId() + 1), getWorld());
    objectius.add(new ObjectiuBlockBreak("Core " + e.getAdjectiu() + " L", coreLocs.get(0)));
    objectius.add(new ObjectiuBlockBreak("Core " + e.getAdjectiu() + " R", coreLocs.get(1)));
    if (e.getId() == 0){
      //objectius.add(new Objectiu("Llana vermella" , pMapaActual().ObtenirLocation("WRed", getWorld()), DyeColor.RED));
      objectius.add(new ObjectiuWoolPlace("Llana vermella", pMapaActual().ObtenirLocation("WRed", getWorld()), DyeColor.RED));
      objectius.add(new ObjectiuWoolPlace("Llana magenta" , pMapaActual().ObtenirLocation("WMagenta", getWorld()), DyeColor.MAGENTA));
    }else{
      objectius.add(new ObjectiuWoolPlace("Llana verda" , pMapaActual().ObtenirLocation("WGreen", getWorld()), DyeColor.LIME));
      objectius.add(new ObjectiuWoolPlace("Llana blava" , pMapaActual().ObtenirLocation("WBlue", getWorld()), DyeColor.BLUE));
    }
    return objectius;
  }
  @Override
  protected void setCustomGameRules() {
    // TODO Auto-generated method stub

  }
  
  @Override
  protected void customJocFinalitzat() {
    super.customJocFinalitzat();
    setBlockBreakPlace(false);
  }

  @Override
  protected void customJocIniciat() {
    super.customJocIniciat();
    setBlockBreakPlace(true);
    setGiveStartingItemsRespawn(true);
  }
  @Override
  public boolean giveSnowLauncherOnKill() {
    // TODO Auto-generated method stub
    return true;
  }
  @Override
  protected void onPlayerDeathByPlayer(PlayerDeathEvent evt, Player killed, Player killer) {
  
    super.onPlayerDeathByPlayer(evt, killed, killer);
    double distance = killed.getLocation().distance(killer.getLocation());
    
    Player ent = evt.getEntity();
    DamageCause dc = ent.getLastDamageCause().getCause();;
    		
    		
    if (dc == DamageCause.PROJECTILE) {
        
        Equip killedTeam = obtenirEquip(killed);
        Equip killerTeam = obtenirEquip(killer);
        evt.setDeathMessage(
          killedTeam.getChatColor() + "" + killed.getName() + ChatColor.GRAY + " ha estat assassinat per " 
          + killerTeam.getChatColor() + "" + killer.getName() + ChatColor.GRAY + " desde " 
          + ChatColor.GOLD + Long.toString(Math.round(distance)) + ChatColor.GRAY + " blocs"
        );
      }

    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player ply) {
      
      ArrayList<ItemStack> items = new ArrayList<>();
      
      Equip equip = obtenirEquip(ply);
      
      items.add(new ItemStack(Material.IRON_SWORD, 1));
      ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
      
      ItemStack arc = new ItemStack(Material.BOW, 1); 
      arc.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
      items.add(arc);
      
      double balancingMultiplier = getBalancingMultiplier(equip);
      
      int launchers = (int) (8 * balancingMultiplier);
      if(launchers > 64)launchers = 64;
      if(launchers < 0) launchers = 8;
      items.add(getSnowLauncher(launchers));  
      
      if(balancingMultiplier > 1) pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, (balancingMultiplier > 1.20 ? 2 : 1));
      items.add(pickaxe);
      
      items.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, equip));
      items.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, equip));
      items.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, equip));
      items.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, equip));
      
      int arrows = (int) (50 * balancingMultiplier);
      if(arrows > 64){arrows = 64;}
      items.add(new ItemStack(Material.ARROW, arrows));
      
      int ladders = (int) (50 * balancingMultiplier);
      if(ladders > 64){ladders = 64;}
      items.add(new ItemStack(Material.LADDER, ladders));
      
      int block_amount = (int) (45 * balancingMultiplier);
      if(block_amount > 64){block_amount = 64;}
      if (obtenirEquip(ply).getId() == 0){
        items.add(new ItemStack(Material.STAINED_CLAY, block_amount, (short) 14));
      }else{
        items.add(new ItemStack(Material.STAINED_CLAY, block_amount, (short) 10));
      }
      
      items.add(new ItemStack(Material.WEB, 1));
      
      return items;
    }
    
    @Override
    protected void donarEfectesInicials(Player ply) {
      // TODO Auto-generated method stub
      super.donarEfectesInicials(ply);
      
      double m = getBalancingMultiplier(obtenirEquip(ply));
      ply.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (int) (30 * 10 * (m - 0.5)), 5, true), true);
      ply.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 1, true), true);
      ply.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 30, 0, true), true);
      ply.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 3, true), true);
      ply.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int) (20 * 19 * m), 1, true), true);
    }
    
    @Override
    protected void onBlockPlace(BlockPlaceEvent evt, Block blk) {

      super.onBlockPlace(evt, blk);
      
      Player ply = evt.getPlayer();
      
      // Cuboid centre = pMapaActual().ObtenirCuboid("RegC", getWorld());
      
//      if(centre.contains(blk)){
//        
//        evt.setCancelled(true);
//        
//      }
      if (blk.getType() == Material.OBSIDIAN){
        evt.setCancelled(true);
        evt.getPlayer().getInventory().getItemInHand().setAmount(0);

      }
    }
    
    @Override
    protected void onBlockBreak(BlockBreakEvent evt, Block blk) {

      // Do the super thingy
      super.onBlockBreak(evt, blk);
      
      // Get player and it's team
      Player ply = evt.getPlayer();
      Equip equip = obtenirEquip(ply);
      
  	  // Get map centre cuboid
      Cuboid centre = pMapaActual().ObtenirCuboid("RegC", getWorld());
      
      // Get wools cuboid
      Cuboid llana1 = pMapaActual().ObtenirCuboid("RegT10", getWorld());
      Cuboid llana2 = pMapaActual().ObtenirCuboid("RegT11", getWorld());
      Cuboid llana3 = pMapaActual().ObtenirCuboid("RegT20", getWorld());
      Cuboid llana4 = pMapaActual().ObtenirCuboid("RegT21", getWorld());
      
      if(llana1.contains(blk) || llana2.contains(blk) || llana3.contains(blk) || llana4.contains(blk)) {
        
        if(blk.getType() == Material.WEB) {
          // Permetre destruir teranyines per accedir a la llana
        } else {
          evt.setCancelled(true);
        }
        
        return;
      }
      
      if(centre.contains(blk) && blk.getType() == Material.CHEST){
        evt.setCancelled(true);
        // Send actionbar message
        ply.playSound(ply.getLocation(), Sound.BLOCK_CHEST_LOCKED, 100, 0);
        ply.sendMessage("\n" + ChatColor.RED + ChatColor.ITALIC + "    No pots destruir els cofres del centre del mapa\n ");
        return;
      }
      
      if (blk.getType() == Material.STAINED_CLAY && blk.getData() == 11 ) {
        // Send actionbar message
        // ply.sendMessage("\n" + ChatColor.RED + ChatColor.ITALIC + "    Aquest Ã©s un bloc protegit. No el pots destruir\n ");
        evt.setCancelled(true);
        return;
      }  
      
      // Prevent chest destroy
      if (blk.getType() == Material.CHEST) { 
        
        // Check if the chest is on a base
        
        String reg = "base1Area"; // Vermell ha destruit, check for blue
        
        if (equip.getId() == 1) reg = "base0Area"; // Blau ha destruir, check for red
              
        Cuboid base = pMapaActual().ObtenirCuboid(reg, getWorld());
        
        if(base.contains(blk)) {
          
          ply.playSound(ply.getLocation(), Sound.BLOCK_CHEST_LOCKED, 100, 0);
          ply.sendMessage("\n" + ChatColor.RED + ChatColor.ITALIC + "    No pots destruir els cofres enemics\n ");
          evt.setCancelled(true);
          
        }
        return;

      }  
      
    }
    
    @Override
    protected void onPlayerInteract(PlayerInteractEvent evt, Player p) {
      
      // Do the super thingy
      super.onPlayerInteract(evt, p);
      
      if (evt.getAction() != Action.RIGHT_CLICK_BLOCK || evt.getClickedBlock().getType() != Material.CHEST) {
        // Action is not a right click on a chest
        return;
      }
      
      Player ply = evt.getPlayer();
      Equip equip = obtenirEquip(ply);
      Block blk = evt.getClickedBlock();
          
      // Check if the chest is on a base
      
      String reg = "base1Area"; // Vermell ha destruit, check for blue
      
      if (equip.getId() == 1) reg = "base0Area"; // Blau ha destruir, check for red
      
      Cuboid base = pMapaActual().ObtenirCuboid(reg, getWorld());
      
      if(base.contains(blk)) {
        
        // TO DO: Add sound to the player
        ply.playSound(ply.getLocation(), Sound.BLOCK_CHEST_LOCKED, 100, 0);
        ply.sendMessage("\n" + ChatColor.RED + ChatColor.ITALIC + "    No pots interactuar amb els cofres enemics\n ");
        evt.setCancelled(true);
         
      }
      return;
    }

    @Override
    protected void onPlayerMove(PlayerMoveEvent evt, Player P) {
      // TODO Auto-generated method stub
      super.onPlayerMove(evt, P);
      Player ply = evt.getPlayer();
      if(isSpectator(ply))return;
      if (JocIniciat){
        Player plyr =  evt.getPlayer();

        Location to = evt.getTo();
        Location from = evt.getFrom();

        int equip = obtenirEquip(ply).getId() + 1;
        if (ply.getLocation().getY() < 90){
          ply.damage(10000);
          
          DamageCause cause = DamageCause.VOID;
          Player target = ply;
          EntityDamageEvent event = new EntityDamageEvent(target, cause, target.getHealth());
          target.setLastDamageCause(event);
          
          
        }
      //Torres escuts
        int e = 1;
        while(e <= 2){
          int i = 0;
          while(i <= 1){
            Cuboid cub = pMapaActual().ObtenirCuboid("RegT" + Integer.toString(e) + Integer.toString(i), getWorld()); 
            Location center = cub.getCenter();
            if(cub.contains(to.getBlock())){
              if(e == equip){
                Vector vec = Utils.CrearVector(center, from).normalize().add(new Vector(0, 1, 0));
                getWorld().playSound(to, Sound.ENTITY_IRONGOLEM_HURT, 1F, 2.2F);
                getWorld().playEffect(to, Effect.MOBSPAWNER_FLAMES, 3);
                getWorld().playEffect(to.clone().add(new Vector(0, 1, 0)), Effect.MOBSPAWNER_FLAMES, 3);
                if(cub.contains(from.getBlock()) && plyr.getVelocity().length() >= 1){
                  plyr.teleport(from.add(vec));
                  //Bukkit.broadcastMessage("ha entrat");
                }else{
                  plyr.setVelocity(vec);
                }
                //evt.setCancelled(true);

              }
            }
            i = i + 1;
          }
          e = e + 1;
        }
        //SECURE NO-FALL
        //
        boolean isNoFallActive = false;

        ItemStack itemInHand = ply.getItemInHand();
        if(itemInHand.hasItemMeta()){
          ItemMeta itemMeta = itemInHand.getItemMeta();
          if (itemMeta.hasDisplayName()){
            if (itemMeta.getDisplayName().equals(getBridgeToolName())){
              isNoFallActive = true;
            }
          }
        }

        if(isNoFallActive){
          Vector v = Utils.CrearVector(evt.getFrom(), evt.getTo());
          v.multiply(1.45D);
          v.setY(0);
          Block bDown = evt.getTo().add(v).getBlock().getRelative(BlockFace.DOWN);
          if (bDown.isEmpty() && bDown.getRelative(BlockFace.DOWN).isEmpty()){
            ItemStack placeableItemStack = getPlaceableItemStack(ply);
            if (placeableItemStack != null){
              bDown.setType(placeableItemStack.getType());
              bDown.setData(placeableItemStack.getData().getData());
              ItemStack sampleIt = new ItemStack(placeableItemStack);
              sampleIt.setAmount(1);
              ply.getInventory().removeItem(sampleIt);
              itemInHand.setDurability((short) (itemInHand.getDurability() + 3));
            }
          }
        }
      }

    }
    
    protected void onBlockHitByProjectile(ProjectileHitEvent evt, Block b, Projectile proj) {
      // TODO Auto-generated method stub
      super.onBlockHitByProjectile(evt, b, proj);
      //sendGlobalMessage("Pilotassa");
      Material t = b.getType();
      if(t == Material.GLASS || t == Material.STAINED_GLASS || t == Material.STAINED_GLASS_PANE || t == Material.THIN_GLASS || t == Material.GLOWSTONE){
        b.setType(Material.AIR);
        getWorld().playSound(b.getLocation(), Sound.BLOCK_GLASS_BREAK, 15F, 1.2F);
        proj.remove();
        for(BlockFace f : BlockFace.values()){
          if(GUtils.Possibilitat(58))continue;
          Block relative = b.getRelative(f);
          t = relative.getType();
          if(t == Material.GLASS || t == Material.STAINED_GLASS || t == Material.STAINED_GLASS_PANE || t == Material.THIN_GLASS || t == Material.GLOWSTONE){
            relative.setType(Material.AIR);
          }
        }
      }
    }

    private static String getBridgeToolName() {
      return ChatColor.YELLOW + "Aixada del constructor";
    }
    public ItemStack getPlaceableItemStack(Player ply) {
      for (ItemStack i : ply.getInventory()){
        if(i.getType().isSolid() && i.getType() != Material.WOOL){return i;}
      }
      return null;
    }

  }
