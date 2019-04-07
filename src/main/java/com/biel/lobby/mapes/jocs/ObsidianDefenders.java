/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.SkullType
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.IronGolem
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class ObsidianDefenders
extends JocEquips {
    private static final /* synthetic */ int[] lIllIllIl;
    /* synthetic */ boolean debug;
    private static final /* synthetic */ String[] lIlIlIllI;

    private static String lIIllllllll(String lIIIIIlIIlIIIl, String lIIIIIlIIlIIII) {
        try {
            SecretKeySpec lIIIIIlIIlIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIIIlIIlIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIIIIlIIlIlIl = Cipher.getInstance("Blowfish");
            lIIIIIlIIlIlIl.init(lIllIllIl[2], lIIIIIlIIlIllI);
            return new String(lIIIIIlIIlIlIl.doFinal(Base64.getDecoder().decode(lIIIIIlIIlIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIIIlIIlIlII) {
            lIIIIIlIIlIlII.printStackTrace();
            return null;
        }
    }

    protected void onEntityDamageByEntity(EntityDamageByEntityEvent lIIIIllllllllI, Entity lIIIIllllllIII, Entity lIIIIlllllllII) {
        ObsidianDefenders lIIIIlllllllll;
        super.onEntityDamageByEntity(lIIIIllllllllI, lIIIIllllllIII, lIIIIlllllllII);
        Double lIIIIllllllIll = lIIIIllllllllI.getDamage();
        if (ObsidianDefenders.lIlIlIIlIIl(lIIIIllllllllI.getEntity() instanceof Player) && (!ObsidianDefenders.lIlIlIIlIlI(lIIIIllllllllI.getDamager() instanceof Player) || ObsidianDefenders.lIlIlIIlIIl(lIIIIllllllllI.getDamager() instanceof Arrow))) {
            Player lIIIlIIIIIIlII = (Player)lIIIIllllllIII;
            Player lIIIlIIIIIIIll = null;
            int lIIIlIIIIIIIlI = lIllIllIl[0];
            if (ObsidianDefenders.lIlIlIIlIIl(lIIIIllllllllI.getDamager() instanceof Player)) {
                lIIIlIIIIIIIll = (Player)lIIIIllllllllI.getDamager();
            }
            if (ObsidianDefenders.lIlIlIIlIIl(lIIIIllllllllI.getDamager() instanceof Arrow)) {
                lIIIlIIIIIIIll = (Player)((Arrow)lIIIIllllllllI.getDamager()).getShooter();
                lIIIlIIIIIIIlI = lIllIllIl[1];
            }
            if (ObsidianDefenders.lIlIlIIlIlI((int)lIIIIlllllllll.JocIniciat.booleanValue())) {
                lIIIIllllllllI.setCancelled(lIllIllIl[1]);
            }
            if (ObsidianDefenders.lIlIlIIlIll(ObsidianDefenders.lIlIlIIIlll(lIIIlIIIIIIlII.getLocation().distance(lIIIIlllllllll.pMapaActual().ObtenirLocation(String.valueOf(new StringBuilder().append(lIlIlIllI[lIllIllIl[4]]).append(Integer.toString(lIIIIlllllllll.obtenirEquip(lIIIlIIIIIIlII).getId()))), lIIIIlllllllll.world)), 7.0))) {
                lIIIIllllllllI.setCancelled(lIllIllIl[1]);
                if (ObsidianDefenders.lIlIlIIllII((Object)lIIIlIIIIIIIll)) {
                    lIIIlIIIIIIIll.damage(lIIIIllllllllI.getDamage(), (Entity)lIIIlIIIIIIlII);
                    "".length();
                    Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIIIlIIIIIIIll.getName()).append(lIlIlIllI[lIllIllIl[5]])));
                }
            }
            if (ObsidianDefenders.lIlIlIIllII((Object)lIIIlIIIIIIIll)) {
                if (ObsidianDefenders.lIlIlIIllIl(lIIIlIIIIIIIll.getLocation().getBlockY(), lIllIllIl[6]) && ObsidianDefenders.lIlIlIIlIlI(lIIIlIIIIIIIlI)) {
                    lIIIIllllllllI.setDamage(lIIIIllllllllI.getDamage() * 1.6 + (double)Utils.NombreEntre(lIllIllIl[1], lIllIllIl[7]));
                }
                if (ObsidianDefenders.lIlIlIIllIl(lIIIlIIIIIIIll.getLocation().getBlockY(), lIllIllIl[8])) {
                    lIIIIllllllllI.setDamage(lIIIIllllllllI.getDamage() + (double)Utils.NombreEntre(lIllIllIl[1], lIllIllIl[5]));
                }
            }
            lIIIIllllllllI.setDamage(lIIIIllllllllI.getDamage() * 0.8);
            if (ObsidianDefenders.lIlIlIIllII((Object)lIIIlIIIIIIIll)) {
                Vector lIIIlIIIIIllll;
                Vector lIIIlIIIIIIlll;
                if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIlllllllll.plugin, lIIIIlllllllll, lIIIlIIIIIIIll, Ability.AbilityType.ARQUER_PERFECTE)) && ObsidianDefenders.lIlIlIIlIlI((int)lIIIIllllllllI.isCancelled()) && ObsidianDefenders.lIlIlIIlllI(lIIIlIIIIIIIlI, lIllIllIl[1])) {
                    int lIIIlIIIIlIlIl = lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).ObtenirPropietatInt(lIlIlIllI[lIllIllIl[9]]);
                    if (ObsidianDefenders.lIlIlIIllIl(lIIIlIIIIlIlIl, lIllIllIl[3])) {
                        Vector lIIIlIIIIllIII = Utils.CrearVector(lIIIlIIIIIIIll.getLocation(), lIIIlIIIIIIlII.getLocation());
                        int lIIIlIIIIlIlll = lIllIllIl[10];
                        if (ObsidianDefenders.lIlIlIIllll(lIIIlIIIIlIlll, lIllIllIl[11])) {
                            lIIIlIIIIlIlll = lIllIllIl[11];
                        }
                        ArrayList<Location> lIIIlIIIIlIllI = Utils.getLocationsCircle(lIIIlIIIIIIlII.getLocation(), 1.0, lIllIllIl[10]);
                        Iterator<Location> lIIIIllllIlllI = lIIIlIIIIlIllI.iterator();
                        while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIllllIlllI.hasNext())) {
                            Location lIIIlIIIIllIIl = lIIIIllllIlllI.next();
                            if (ObsidianDefenders.lIlIlIlIIII(ObsidianDefenders.lIlIlIIlIII(lIIIlIIIIllIIl.distance(lIIIlIIIIIIIll.getLocation()), lIIIlIIIIIIlII.getLocation().distance(lIIIlIIIIIIIll.getLocation())))) {
                                Vector lIIIlIIIIllIll = Utils.CrearVector(lIIIlIIIIIIlII.getLocation(), lIIIlIIIIllIIl).normalize();
                                Arrow lIIIlIIIIllIlI = (Arrow)lIIIIlllllllll.world.spawnEntity(lIIIlIIIIllIIl, EntityType.ARROW);
                                lIIIlIIIIllIlI.setShooter((ProjectileSource)lIIIlIIIIIIIll);
                                lIIIlIIIIllIlI.setFireTicks(lIllIllIl[12]);
                                lIIIlIIIIllIlI.setVelocity(lIIIlIIIIllIll.multiply(lIllIllIl[13]));
                            }
                            "".length();
                            if (" ".length() != ((175 ^ 165) & ~(76 ^ 70))) continue;
                            return;
                        }
                        lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).EstablirPropietat(lIlIlIllI[lIllIllIl[14]], lIllIllIl[1]);
                        lIIIlIIIIIIIll.playSound(lIIIlIIIIIIIll.getLocation(), Sound.ENTITY_GENERIC_SWIM, 1.0f, 0.5f);
                        "".length();
                        if ("  ".length() <= ((56 ^ 101) & ~(53 ^ 104))) {
                            return;
                        }
                    } else {
                        lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).IncrementarPropietat(lIlIlIllI[lIllIllIl[13]]);
                        if (ObsidianDefenders.lIlIlIIlllI(lIIIlIIIIlIlIl, lIllIllIl[5])) {
                            lIIIlIIIIIIIll.playSound(lIIIlIIIIIIIll.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        }
                    }
                    lIIIIlllllllll.updateScoreBoard(lIIIlIIIIIIIll);
                }
                if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIlllllllll.plugin, lIIIIlllllllll, lIIIlIIIIIIIll, Ability.AbilityType.ARQUER_DE_GEL)) && ObsidianDefenders.lIlIlIIlIlI((int)lIIIIllllllllI.isCancelled()) && ObsidianDefenders.lIlIlIIlllI(lIIIlIIIIIIIlI, lIllIllIl[1])) {
                    int lIIIlIIIIlIIII = lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).ObtenirPropietatInt(lIlIlIllI[lIllIllIl[15]]);
                    if (ObsidianDefenders.lIlIlIIllIl(lIIIlIIIIlIIII, lIllIllIl[9])) {
                        ArrayList<BlockFace> lIIIlIIIIlIIlI = new ArrayList<BlockFace>();
                        "".length();
                        lIIIlIIIIlIIlI.add(BlockFace.NORTH);
                        "".length();
                        lIIIlIIIIlIIlI.add(BlockFace.SOUTH);
                        "".length();
                        lIIIlIIIIlIIlI.add(BlockFace.WEST);
                        "".length();
                        lIIIlIIIIlIIlI.add(BlockFace.EAST);
                        Iterator lIIIlIIIIlIlll = lIIIlIIIIlIIlI.iterator();
                        while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIlIIIIlIlll.hasNext())) {
                            BlockFace lIIIlIIIIlIIll = (BlockFace)lIIIlIIIIlIlll.next();
                            Block lIIIlIIIIlIlII = lIIIlIIIIIIlII.getLocation().getBlock().getRelative(lIIIlIIIIlIIll);
                            if (ObsidianDefenders.lIlIlIlIIIl((Object)lIIIlIIIIlIlII.getType(), (Object)Material.AIR)) {
                                "".length();
                                if (-" ".length() == -" ".length()) continue;
                                return;
                            }
                            lIIIlIIIIlIlII.setType(Material.ICE);
                            Utils.BreakBlockLater(lIIIlIIIIlIlII, lIllIllIl[16], lIllIllIl[0]);
                            "".length();
                            if (null == null) continue;
                            return;
                        }
                        "".length();
                        lIIIlIIIIIIlII.teleport(lIIIlIIIIIIlII.getLocation().getBlock().getLocation().add(new Vector(0.5, 0.0, 0.5)));
                        Block lIIIlIIIIlIIIl = lIIIlIIIIIIlII.getLocation().add(0.0, 2.0, 0.0).getBlock();
                        if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIlIIIIlIIIl.getType(), (Object)Material.AIR)) {
                            lIIIlIIIIlIIIl.setType(Material.GOLD_BLOCK);
                            Utils.BreakBlockLater(lIIIlIIIIlIIIl, lIllIllIl[16], lIllIllIl[0]);
                        }
                        lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).EstablirPropietat(lIlIlIllI[lIllIllIl[11]], lIllIllIl[1]);
                        lIIIlIIIIIIlII.playSound(lIIIlIIIIIIIll.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 0.5f);
                        "".length();
                        if (-" ".length() >= "  ".length()) {
                            return;
                        }
                    } else {
                        lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).IncrementarPropietat(lIlIlIllI[lIllIllIl[7]]);
                        if (ObsidianDefenders.lIlIlIIlllI(lIIIlIIIIlIIII, lIllIllIl[5])) {
                            lIIIlIIIIIIIll.playSound(lIIIlIIIIIIIll.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        }
                    }
                    lIIIIlllllllll.updateScoreBoard(lIIIlIIIIIIIll);
                }
                if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIlllllllll.plugin, lIIIIlllllllll, lIIIlIIIIIIIll, Ability.AbilityType.ESPADATXI)) && ObsidianDefenders.lIlIlIIlIlI((int)lIIIIllllllllI.isCancelled())) {
                    int lIIIlIIIIIllIl = lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).ObtenirPropietatInt(lIlIlIllI[lIllIllIl[17]]);
                    if (ObsidianDefenders.lIlIlIIllIl(lIIIlIIIIIllIl, lIllIllIl[5])) {
                        lIIIIllllllllI.setDamage(lIIIIllllllllI.getDamage() * 1.5);
                        lIIIlIIIIIllll = lIIIlIIIIIIlII.getLocation().toVector().subtract(lIIIlIIIIIIIll.getLocation().toVector());
                        Vector lIIIlIIIIIlllI = lIIIlIIIIIllll.normalize().multiply(lIllIllIl[2]).add(new Vector(0.0, 0.3, 0.0));
                        lIIIlIIIIIIlII.setVelocity(lIIIlIIIIIlllI);
                        lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).EstablirPropietat(lIlIlIllI[lIllIllIl[18]], lIllIllIl[1]);
                        lIIIlIIIIIIlII.playSound(lIIIlIIIIIIIll.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0f, 0.3f);
                        "".length();
                        if ((154 ^ 158) == 0) {
                            return;
                        }
                    } else {
                        lIIIIlllllllll.pPlayer(lIIIlIIIIIIIll).IncrementarPropietat(lIlIlIllI[lIllIllIl[19]]);
                        if (ObsidianDefenders.lIlIlIIlllI(lIIIlIIIIIllIl, lIllIllIl[5])) {
                            lIIIlIIIIIIlII.playSound(lIIIlIIIIIIIll.getLocation(), Sound.ENTITY_HORSE_LAND, 1.0f, 0.3f);
                        }
                    }
                    lIIIIlllllllll.updateScoreBoard(lIIIlIIIIIIIll);
                }
                if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIlllllllll.plugin, lIIIIlllllllll, lIIIlIIIIIIlII, Ability.AbilityType.RESISTENCIA))) {
                    double lIIIlIIIIIllII = 0.9;
                    if (ObsidianDefenders.lIlIlIIlIll(ObsidianDefenders.lIlIlIIIlll(lIIIlIIIIIllII -= (double)Utils.getNearbyPlayers((Entity)lIIIlIIIIIIlII, 10.0).size() * 0.08, 0.1))) {
                        lIIIlIIIIIllII = 0.1;
                    }
                    double lIIIlIIIIIlIll = lIIIIllllllllI.getDamage() * 0.85;
                    lIIIIllllllllI.setDamage(lIIIlIIIIIlIll);
                    if (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlllllllll.debug)) {
                        "".length();
                        Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIlIllI[lIllIllIl[20]]).append(Double.toString(lIIIIllllllllI.getDamage() - lIIIlIIIIIlIll)).append(lIlIlIllI[lIllIllIl[21]]).append(lIIIlIIIIIllII * 100.0).append(lIlIlIllI[lIllIllIl[22]])));
                    }
                }
                if (ObsidianDefenders.lIlIlIIlllI(lIIIIlllllllll.obtenirEquip(lIIIlIIIIIIlII).getId(), lIIIIlllllllll.obtenirEquip(lIIIlIIIIIIIll).getId())) {
                    lIIIIllllllllI.setCancelled(lIllIllIl[1]);
                }
                lIIIlIIIIIllll = lIIIlIIIIIIlll = ((Player)lIIIIllllllllI.getEntity()).getInventory().getArmorContents();
                int lIIIlIIIIIlIll = ((ItemStack[])lIIIlIIIIIllll).length;
                int lIIIlIIIIlIIll = lIllIllIl[0];
                while (ObsidianDefenders.lIlIlIIllll(lIIIlIIIIlIIll, lIIIlIIIIIlIll)) {
                    Vector lIIIlIIIIIlIII = lIIIlIIIIIllll[lIIIlIIIIlIIll];
                    Material lIIIlIIIIIlIIl = lIIIlIIIIIlIII.getType();
                    if (!ObsidianDefenders.lIlIlIlIIIl((Object)lIIIlIIIIIlIIl, (Object)Material.LEATHER_HELMET) || !ObsidianDefenders.lIlIlIlIIIl((Object)lIIIlIIIIIlIIl, (Object)Material.CHAINMAIL_CHESTPLATE) || !ObsidianDefenders.lIlIlIlIIIl((Object)lIIIlIIIIIlIIl, (Object)Material.CHAINMAIL_LEGGINGS) || ObsidianDefenders.lIlIlIlIIlI((Object)lIIIlIIIIIlIIl, (Object)Material.CHAINMAIL_BOOTS)) {
                        lIIIlIIIIIlIII.setDurability(lIllIllIl[0]);
                        "".length();
                        if ((193 ^ 197) > (48 ^ 52)) {
                            return;
                        }
                    } else if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIlllllllll.plugin, lIIIIlllllllll, lIIIlIIIIIIIll, Ability.AbilityType.DESTRUCTOR))) {
                        int lIIIlIIIIIlIlI = Integer.parseInt(lIIIIlllllllll.pTemp().ObtenirPropietat(String.valueOf(new StringBuilder().append(lIIIlIIIIIIIll.getName()).append(lIlIlIllI[lIllIllIl[23]]))));
                        lIIIlIIIIIlIII.setDurability((short)(lIIIlIIIIIlIII.getDurability() + lIllIllIl[5] + lIIIlIIIIIlIlI));
                    }
                    ++lIIIlIIIIlIIll;
                    "".length();
                    if (((106 ^ 70) & ~(179 ^ 159)) == 0) continue;
                    return;
                }
                ((Player)lIIIIllllllllI.getEntity()).getInventory().setArmorContents((ItemStack[])lIIIlIIIIIIlll);
            }
            if (ObsidianDefenders.lIlIlIIlIIl(lIIIIllllllllI.getEntity() instanceof IronGolem) && ObsidianDefenders.lIlIlIIlIIl(lIIIIllllllllI.getDamager() instanceof Player)) {
                IronGolem lIIIlIIIIIIllI = (IronGolem)lIIIIllllllllI.getEntity();
                ItemStack lIIIlIIIIIIlIl = lIIIlIIIIIIIll.getItemInHand();
                if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIlIIIIIIlIl.getType(), (Object)Material.IRON_PICKAXE)) {
                    lIIIIllllllllI.setDamage(30.0);
                    lIIIlIIIIIIlIl.setDurability((short)(lIIIlIIIIIIlIl.getDurability() + lIIIlIIIIIIlIl.getType().getMaxDurability() / lIllIllIl[4]));
                }
            }
        }
        if (ObsidianDefenders.lIlIlIIlIIl(lIIIIllllllllI.getEntity() instanceof Player) && ObsidianDefenders.lIlIlIIlIIl(lIIIIllllllllI.getDamager() instanceof IronGolem)) {
            IronGolem lIIIlIIIIIIIIl = (IronGolem)lIIIIllllllllI.getDamager();
            Player lIIIlIIIIIIIII = (Player)lIIIIllllllllI.getEntity();
            if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIlllllllll.plugin, lIIIIlllllllll, lIIIlIIIIIIIII, Ability.AbilityType.PROTECCI\u00d3_IMPACTE))) {
                lIIIIllllllllI.setDamage(lIIIIllllllllI.getDamage() / 2.0);
            }
        }
        lIIIIllllllllI.setDamage(lIIIIllllllIll.doubleValue());
    }

    public void donarOrATots(int lIIIIllIIlIlll) {
        ObsidianDefenders lIIIIllIIllIII;
        ArrayList<Player> lIIIIllIIlIllI = lIIIIllIIllIII.getPlayers();
        Iterator<Player> lIIIIllIIlIIlI = lIIIIllIIlIllI.iterator();
        while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIllIIlIIlI.hasNext())) {
            Player lIIIIllIIllIIl = lIIIIllIIlIIlI.next();
            lIIIIllIIllIII.donarOr(lIIIIllIIllIIl, lIIIIllIIlIlll);
            "".length();
            if ("  ".length() >= -" ".length()) continue;
            return;
        }
    }

    public void donarOrAEquip(ArrayList<Player> lIIIIllIlIlIll, int lIIIIllIlIIlII, Boolean lIIIIllIlIlIIl, String lIIIIllIlIlIII, Boolean lIIIIllIlIIIIl) {
        if (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIllIlIlIIl.booleanValue())) {
            lIIIIllIlIIlII = (int)Math.ceil(lIIIIllIlIIlII / lIIIIllIlIlIll.size());
        }
        Iterator<Player> lIIIIllIlIIIII = lIIIIllIlIlIll.iterator();
        while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIllIlIIIII.hasNext())) {
            ObsidianDefenders lIIIIllIlIIllI;
            Player lIIIIllIlIllIl = lIIIIllIlIIIII.next();
            lIIIIllIlIIllI.donarOr(lIIIIllIlIllIl, lIIIIllIlIIlII, lIIIIllIlIlIII, lIIIIllIlIIIIl);
            "".length();
            if (((115 ^ 51 ^ (53 ^ 67)) & (131 + 145 - 217 + 91 ^ 95 + 141 - 207 + 131 ^ -" ".length())) <= "   ".length()) continue;
            return;
        }
    }

    @Override
    protected void updateScoreBoard(Player lIIIIIllllIlII) {
        ObsidianDefenders lIIIIIllllIIll;
        super.updateScoreBoard(lIIIIIllllIlII);
        if (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIIllllIIll.JocIniciat.booleanValue())) {
            ArrayList<String> lIIIIIllllIllI = new ArrayList<String>();
            "".length();
            lIIIIIllllIllI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIlIlIllI[lIllIllIl[87]]).append(lIIIIIllllIIll.pPlayer(lIIIIIllllIlII).ObtenirPropietatInt(lIlIlIllI[lIllIllIl[88]]))));
            "".length();
            lIIIIIllllIllI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIlIlIllI[lIllIllIl[16]]).append(lIIIIIllllIIll.pPlayer(lIIIIIllllIlII).ObtenirPropietatInt(lIlIlIllI[lIllIllIl[89]]))));
            "".length();
            lIIIIIllllIllI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[90]]).append(lIIIIIllllIIll.pPlayer(lIIIIIllllIlII).ObtenirPropietatInt(lIlIlIllI[lIllIllIl[91]]))));
            if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIIllllIIll.plugin, lIIIIIllllIIll, lIIIIIllllIlII, Ability.AbilityType.ESPADATXI))) {
                "".length();
                lIIIIIllllIllI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIlIlIllI[lIllIllIl[92]]).append(lIIIIIllllIIll.pPlayer(lIIIIIllllIlII).ObtenirPropietatInt(lIlIlIllI[lIllIllIl[93]]))));
            }
            if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIIllllIIll.plugin, lIIIIIllllIIll, lIIIIIllllIlII, Ability.AbilityType.ARQUER_DE_GEL))) {
                "".length();
                lIIIIIllllIllI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIlIlIllI[lIllIllIl[94]]).append(lIIIIIllllIIll.pPlayer(lIIIIIllllIlII).ObtenirPropietatInt(lIlIlIllI[lIllIllIl[95]]))));
            }
            ScoreBoardUpdater.setScoreBoard(lIIIIIllllIlII, lIlIlIllI[lIllIllIl[96]], lIIIIIllllIllI, null);
        }
    }

    @Override
    protected void customJocFinalitzat() {
    }

    private static String lIIllIIlllI(String lIIIIIlIlIIIll, String lIIIIIlIlIIIlI) {
        lIIIIIlIlIIIll = new String(Base64.getDecoder().decode(lIIIIIlIlIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIIIIlIlIIllI = new StringBuilder();
        char[] lIIIIIlIlIIlIl = lIIIIIlIlIIIlI.toCharArray();
        int lIIIIIlIlIIlII = lIllIllIl[0];
        char[] lIIIIIlIIllllI = lIIIIIlIlIIIll.toCharArray();
        int lIIIIIlIIlllIl = lIIIIIlIIllllI.length;
        int lIIIIIlIIlllII = lIllIllIl[0];
        while (ObsidianDefenders.lIlIlIIllll(lIIIIIlIIlllII, lIIIIIlIIlllIl)) {
            char lIIIIIlIlIlIIl = lIIIIIlIIllllI[lIIIIIlIIlllII];
            "".length();
            lIIIIIlIlIIllI.append((char)(lIIIIIlIlIlIIl ^ lIIIIIlIlIIlIl[lIIIIIlIlIIlII % lIIIIIlIlIIlIl.length]));
            ++lIIIIIlIlIIlII;
            ++lIIIIIlIIlllII;
            "".length();
            if (((38 + 29 - 4 + 97 ^ 83 + 92 - 136 + 139) & (61 ^ 69 ^ (54 ^ 92) ^ -" ".length())) == 0) continue;
            return null;
        }
        return String.valueOf(lIIIIIlIlIIllI);
    }

    private static boolean lIlIlIllIll(int n) {
        return n >= 0;
    }

    private static int lIlIlIlllII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    protected void onPlayerPickupItem(PlayerPickupItemEvent lIIIIlllIllIIl, Player lIIIIlllIllllI) {
        ObsidianDefenders lIIIIllllIIIII;
        super.onPlayerPickupItem(lIIIIlllIllIIl, lIIIIlllIllllI);
        Player lIIIIlllIlllIl = lIIIIlllIllIIl.getPlayer();
        Item lIIIIlllIlllII = lIIIIlllIllIIl.getItem();
        ItemStack lIIIIlllIllIll = lIIIIlllIlllII.getItemStack();
        if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlllIllIll.getType(), (Object)Material.DIAMOND_PICKAXE)) {
            Location lIIIIllllIIIIl = new Location(lIIIIllllIIIII.world, 661.0, 42.0, -1398.0);
            if (ObsidianDefenders.lIlIlIlIlII(ObsidianDefenders.lIlIlIlIIll(lIIIIlllIlllII.getLocation().distance(lIIIIllllIIIIl), 1.0))) {
                int lIIIIllllIIIlI = lIllIllIl[3];
                lIIIIllllIIIII.donarOr(lIIIIlllIlllIl, lIIIIllllIIIlI);
                lIIIIlllIlllIl.sendMessage(String.valueOf(new StringBuilder().append(lIlIlIllI[lIllIllIl[24]]).append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[25]]).append(lIIIIllllIIIlI).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[26]])));
            }
        }
    }

    public void ajuntarOr(Player lIIIIlIIIIIIlI) {
        int lIIIIlIIIIIlII = lIllIllIl[11];
        PlayerInventory lIIIIlIIIIIIll = lIIIIlIIIIIIlI.getInventory();
        ItemStack[] lIIIIIllllllll = lIIIIlIIIIIIll.getContents();
        int lIIIIIlllllllI = lIIIIIllllllll.length;
        int lIIIIIllllllIl = lIllIllIl[0];
        while (ObsidianDefenders.lIlIlIIllll(lIIIIIllllllIl, lIIIIIlllllllI)) {
            ItemStack lIIIIlIIIIIlll = lIIIIIllllllll[lIIIIIllllllIl];
            if (ObsidianDefenders.lIlIlIlIllI((Object)lIIIIlIIIIIlll)) {
                "".length();
                if (-"  ".length() > 0) {
                    return;
                }
            } else if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIIIlll.getType(), (Object)Material.GOLD_NUGGET) && ObsidianDefenders.lIlIlIIllIl(lIIIIlIIIIIlll.getAmount(), lIllIllIl[11])) {
                int lIIIIlIIIIlIIl = lIIIIlIIIIIlll.getAmount() / lIllIllIl[11];
                int lIIIIlIIIIlIII = lIIIIlIIIIlIIl * lIllIllIl[11];
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(Material.GOLD_INGOT, lIIIIlIIIIlIIl);
                "".length();
                lIIIIlIIIIIIll.addItem(arritemStack);
                ItemStack[] arritemStack2 = new ItemStack[lIllIllIl[1]];
                arritemStack2[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(Material.GOLD_NUGGET, lIIIIlIIIIlIII);
                "".length();
                lIIIIlIIIIIIll.removeItem(arritemStack2);
            }
            ++lIIIIIllllllIl;
            "".length();
            if (null == null) continue;
            return;
        }
    }

    @Override
    public String getGameName() {
        return lIlIlIllI[lIllIllIl[0]];
    }

    private static String lIIllIIllll(String lIIIIIlIlllIII, String lIIIIIlIllIlll) {
        try {
            SecretKeySpec lIIIIIlIlllIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIIIlIllIlll.getBytes(StandardCharsets.UTF_8)), lIllIllIl[13]), "DES");
            Cipher lIIIIIlIlllIlI = Cipher.getInstance("DES");
            lIIIIIlIlllIlI.init(lIllIllIl[2], lIIIIIlIlllIll);
            return new String(lIIIIIlIlllIlI.doFinal(Base64.getDecoder().decode(lIIIIIlIlllIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIIIlIlllIIl) {
            lIIIIIlIlllIIl.printStackTrace();
            return null;
        }
    }

    @Override
    protected void customJocIniciat() {
        ObsidianDefenders lIIIlIIlIIIlll;
        lIIIlIIlIIIlll.setBlockBreakPlace(lIllIllIl[0]);
        lIIIlIIlIIIlll.setGiveStartingItemsRespawn(lIllIllIl[0]);
        lIIIlIIlIIIlll.donarItemsInicials();
    }

    private static int lIlIlIlIlIl(long l, long l2) {
        return (int)(l LCMP l2);
    }

    private static void lIlIlIIIlIl() {
        lIlIlIllI = new String[lIllIllIl[107]];
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[0]] = ObsidianDefenders.lIIllIIlllI("CAUwECwuBi1ZDCIBJhcsIhUw", "GgCyH");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[1]] = ObsidianDefenders.lIIllIIlllI("BQ01HTQfBA==", "shGpQ");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[2]] = ObsidianDefenders.lIIllIIllll("k8NGqr7ST1A=", "FvLXq");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[3]] = ObsidianDefenders.lIIllIIllll("RprKc4mpdnDQ96Wy2HslduC/FjZuT3+w", "jbMPD");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[4]] = ObsidianDefenders.lIIllllllll("l0peq8qDQl4=", "wgoaa");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[5]] = ObsidianDefenders.lIIllIIlllI("VlcFAEQfBEsfCw5XChsFGRYZTwVaG0wcFBsABU4=", "zwkod");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[9]] = ObsidianDefenders.lIIllllllll("dxTXQfvVU7reEOKPT1h5XbUU9n6DZbA1", "VHWmB");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[14]] = ObsidianDefenders.lIIllllllll("al9J8I9jEWrNrIJHReI5y2/9UEAth+LW", "WxDBd");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[13]] = ObsidianDefenders.lIIllIIlllI("HAoTNzcvGyM+JQQGFRI9OQEV", "LoaQR");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[15]] = ObsidianDefenders.lIIllIIlllI("Hx8fKiorKQIyDCUfLioxIh8=", "LkmED");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[11]] = ObsidianDefenders.lIIllllllll("Et3a2DvqqSJVcPDv2sgYDaEbzR+XBOCZ", "wSBMo");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[7]] = ObsidianDefenders.lIIllIIlllI("ORE5AiYNJyQaAAMRCAI9BBE=", "jeKmH");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[17]] = ObsidianDefenders.lIIllIIlllI("IC0LOBsUERAjNhwsFyM=", "sYyWu");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[18]] = ObsidianDefenders.lIIllIIlllI("IRcYHDYVKwMHGx0WBAc=", "rcjsX");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[19]] = ObsidianDefenders.lIIllllllll("AliVABRLn8A0De/ioM4Ypw==", "CHSjq");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[20]] = ObsidianDefenders.lIIllllllll("dsMALKCPZE7Y4M5JHd3aTg==", "jukGC");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[21]] = ObsidianDefenders.lIIllIIlllI("WktD", "zfcMw");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[22]] = ObsidianDefenders.lIIllIIllll("Eowk87saaS4=", "lbknS");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[23]] = ObsidianDefenders.lIIllIIllll("oYFrjPpzRqE=", "ZidNh");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[24]] = ObsidianDefenders.lIIllIIlllI("IzAhSTUMMDQIIEs0PkkkAjJyDTFLNTsIOQo/JkE=", "kQRiT");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[25]] = ObsidianDefenders.lIIllIIlllI("XQ==", "vYMrJ");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[26]] = ObsidianDefenders.lIIllIIllll("mE6YCEyY0+A=", "sLRVr");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[27]] = ObsidianDefenders.lIIllllllll("HhFfDmGgJxU=", "nhSSr");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[28]] = ObsidianDefenders.lIIllIIlllI("RQ==", "mZxFQ");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[29]] = ObsidianDefenders.lIIllIIllll("1nNONt2pWdk=", "qaiHW");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[30]] = ObsidianDefenders.lIIllllllll("28TSUo8nzc4=", "aLFTl");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[31]] = ObsidianDefenders.lIIllIIlllI("DB0bDxc=", "Iekcx");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[32]] = ObsidianDefenders.lIIllIIllll("jWdAs0qrt9pIBwO3RTWGew==", "MJFzz");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[33]] = ObsidianDefenders.lIIllllllll("fOllsprsMD37DQ9dr2y0QQ==", "dGXCj");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[34]] = ObsidianDefenders.lIIllIIlllI("Ng0iODATGAUlFR8=", "zlQLx");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[36]] = ObsidianDefenders.lIIllIIllll("5NNGnyXFLHw=", "TiafJ");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[37]] = ObsidianDefenders.lIIllIIllll("ZMlgGKBh61U=", "yhMIY");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[38]] = ObsidianDefenders.lIIllIIlllI("Gjs=", "UIDOx");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[39]] = ObsidianDefenders.lIIllIIlllI("ZxsnZBomBycwVyZT", "GsFDw");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[40]] = ObsidianDefenders.lIIllIIllll("89Sdpkotq+0=", "XXSAC");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[41]] = ObsidianDefenders.lIIllIIllll("8vQ3+uwSENQ=", "PnOyj");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[42]] = ObsidianDefenders.lIIllIIlllI("Yw==", "JSFHy");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[43]] = ObsidianDefenders.lIIllllllll("wzfhpiMQ3lCHqqBaN+USKFNIhKsDFi/4", "SwegZ");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[44]] = ObsidianDefenders.lIIllIIlllI("aQ==", "AfbSn");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[45]] = ObsidianDefenders.lIIllIIlllI("Zg==", "MtSZH");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[10]] = ObsidianDefenders.lIIllIIlllI("XA==", "ukBmS");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[46]] = ObsidianDefenders.lIIllllllll("6otlUjCxL2UCRmReiqlqrQ==", "aoNAe");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[47]] = ObsidianDefenders.lIIllllllll("UybV95EcJDr0Yqd4AqEd4Q==", "QwOAj");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[48]] = ObsidianDefenders.lIIllIIlllI("Vi0NVSIXMQ0BbxcoDlUqGmUcHCxWIUsaPVYkTA==", "vEluO");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[49]] = ObsidianDefenders.lIIllllllll("5LqvltnDwb4=", "BFgtU");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[8]] = ObsidianDefenders.lIIllllllll("X1YauVokIGg=", "uBveE");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[50]] = ObsidianDefenders.lIIllllllll("11+h6766ydU=", "KddeJ");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[51]] = ObsidianDefenders.lIIllIIllll("s78oVrJGw7k=", "swJkl");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[52]] = ObsidianDefenders.lIIllllllll("mfFPfmVHU9c=", "nqxPA");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[53]] = ObsidianDefenders.lIIllIIllll("AiobNAzksZQ=", "Tqbej");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[6]] = ObsidianDefenders.lIIllIIllll("VZ90OHRwYb0=", "Sjvbp");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[54]] = ObsidianDefenders.lIIllIIllll("k5fnU44rDvw=", "LQerU");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[55]] = ObsidianDefenders.lIIllIIlllI("KQMhKREbGTwpFhs=", "hpRHb");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[56]] = ObsidianDefenders.lIIllIIllll("QczCOBu/bbU=", "AyGYA");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[57]] = ObsidianDefenders.lIIllIIlllI("UiQGVAcTOAYAShNs", "rLgtj");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[58]] = ObsidianDefenders.lIIllIIlllI("dhkBK3MiDRonMnYYHS1zMg1UKjo3BRUgJ3dA", "VhtNS");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[59]] = ObsidianDefenders.lIIllllllll("A2b9vx55EAI=", "hwpDr");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[60]] = ObsidianDefenders.lIIllIIlllI("Sw==", "bDHaI");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[61]] = ObsidianDefenders.lIIllIIllll("R4tYQNvC7ZHXRp+fMVFYx7haN1DgTQ2YhcbQpBsBRJE=", "fQucO");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[62]] = ObsidianDefenders.lIIllIIlllI("AwYxJDxuEitlKyACKCwtbgYoJ24rC2U1Jy1HIWIhPEcgMW4qCCskbjZUZSo8", "NgEEN");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[63]] = ObsidianDefenders.lIIllIIlllI("RBZVPxtECB0lDkQRHSNaFwoe", "derWz");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[64]] = ObsidianDefenders.lIIllllllll("kbJJSoW0qxbaAkfUWAOTJmXs0w9I9Pqv", "oUEpv");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[66]] = ObsidianDefenders.lIIllIIlllI("Zy0JWBYzLAQRFz0kHFgWKSQ=", "GEhxc");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[67]] = ObsidianDefenders.lIIllllllll("ssutTxfOiL+JIP7zjFGW4Q==", "yfhwK");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[68]] = ObsidianDefenders.lIIllIIllll("4ag5Xfg/ywA=", "KNfAX");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[69]] = ObsidianDefenders.lIIllIIlllI("VF91GxgLTjRYAxYadRRQHB8gEQdZCzsdGhANew==", "ynUxw");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[70]] = ObsidianDefenders.lIIllIIlllI("JxUlYgEdETsjFk8Vdi5FCgUjKxJPETgnDwYXeA==", "otVBb");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[72]] = ObsidianDefenders.lIIllIIlllI("KSsFdSMNLxghKxVqF3UuRi8HICsRahM7JwwjFXU3D2pCZWdBLgMnIw8+VmR3QTkTMi0POVg=", "aJvUB");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[74]] = ObsidianDefenders.lIIllllllll("1kO7yC9R2fHmmlSVR9kP6G6wu0lv/Nu9MV/xuteRZJKyyQvpA9xsxf8uVF4lDtnh", "Nqgsb");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[76]] = ObsidianDefenders.lIIllIIlllI("KxcjWicQADUIIxdWMVouRBMhDysTVjUUJw4fM1omFgQxFDZDRGVaMQYRPxQxTQ==", "cvPzB");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[79]] = ObsidianDefenders.lIIllIIlllI("BTotOkIwdTVuAzgyLChD", "QUYIb");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[80]] = ObsidianDefenders.lIIllIIllll("ejXqHByiACv/+FvBfBZLeeUMKAdjd4/2Dq1pd3oNFOE=", "sxHXm");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[81]] = ObsidianDefenders.lIIllIIllll("mEYW3O8w5Fy+rHxhC0M9gBq9agU0I3S24tmH82aQkiedME4J07WnrF0MLNX0MZgs5LV7Jho/SgivbsHwwSsy8g==", "RzeUm");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[82]] = ObsidianDefenders.lIIllIIlllI("LwlKMTUfRQ80JQMVSsKsI0oMBDM5GQwIKTVKAR83MQQRSnRlShYPIj8EFkQ=", "jejEP");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[83]] = ObsidianDefenders.lIIllIIllll("ThjIlY09zEU=", "XIziv");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[84]] = ObsidianDefenders.lIIllIIlllI("OS4mGCgSITwYBCUjNBUEBxk8DxUcIg==", "uOUla");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[85]] = ObsidianDefenders.lIIllllllll("UwdnZQlGUYGaERdsrucXfeWxT1grIrcL", "bLpdZ");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[86]] = ObsidianDefenders.lIIllllllll("CuGL5pINtgX2gZB3Pxy2BG1iu27J9nqIt2V3tIax57U=", "dlCYf");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[87]] = ObsidianDefenders.lIIllllllll("Utri17Y806c=", "GklJS");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[88]] = ObsidianDefenders.lIIllIIllll("6IQUkRPDdfe+57QqqXvsDA==", "hKxaJ");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[16]] = ObsidianDefenders.lIIllIIlllI("CAInPRl/TQ==", "EmUIj");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[89]] = ObsidianDefenders.lIIllIIllll("uW0uPiSRJ8k=", "bHWHe");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[90]] = ObsidianDefenders.lIIllIIlllI("GzdAZw==", "TEzGV");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[91]] = ObsidianDefenders.lIIllllllll("9T4lJum6cL8=", "NcUnp");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[92]] = ObsidianDefenders.lIIllIIlllI("HAM5MSE4BDHCvX95", "YpIPE");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[93]] = ObsidianDefenders.lIIllIIlllI("PSQLPzYJGBAkGwElFyQ=", "nPyPX");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[94]] = ObsidianDefenders.lIIllIIllll("YqUBRApaLi1BP3j5XCKI/A==", "Sogew");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[95]] = ObsidianDefenders.lIIllllllll("OBGJPMuFUTI1jZJKIWefhaIMDudaBvXY", "GHEMc");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[96]] = ObsidianDefenders.lIIllIIllll("yK4Pg/zoVJlW3VnuU5Heew==", "hxlhf");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[97]] = ObsidianDefenders.lIIllIIllll("wBDI9Bi3nreqbdvitvNvGg==", "MCUyq");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[98]] = ObsidianDefenders.lIIllIIlllI("UXAyC0QMOSgLEFglNAtEGj82C0QcNXoEAQ1x", "xPZjd");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[99]] = ObsidianDefenders.lIIllIIllll("yRrHXC+OblA=", "BSgWL");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[100]] = ObsidianDefenders.lIIllIIlllI("ITMjMA==", "CRPUc");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[101]] = ObsidianDefenders.lIIllIIllll("eHg1Zdt+aNE=", "HaRWO");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[102]] = ObsidianDefenders.lIIllIIlllI("Cg4yFDwfGiMBNj0=", "OvBxS");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[35]] = ObsidianDefenders.lIIllIIllll("0erbNq45Ut8QnXA+NWZNKQ==", "kaPOy");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[103]] = ObsidianDefenders.lIIllIIlllI("JDwBExI=", "iSsga");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[104]] = ObsidianDefenders.lIIllIIlllI("MBoiwoQkMw0gDyo=", "vuPcE");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[105]] = ObsidianDefenders.lIIllllllll("l1Zj43SyF7SEFTtyol/Ez3jkKhvVCl5Hr7iis9cA8YDXYTUCgfbo/w==", "DFnST");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[106]] = ObsidianDefenders.lIIllllllll("FxGz6JCiSzQ1fyp/gDtcaw==", "dBPrq");
        ObsidianDefenders.lIlIlIllI[ObsidianDefenders.lIllIllIl[78]] = ObsidianDefenders.lIIllIIlllI("Mx0WGAAFFRdIBxsRERAEBFQAEBEbGxYBFxIHRQ0VVwAXDRRXRUULDgVUFQ0TVxEdGA0YBwzCm0ERHQsbQRZUCAEGVxcKGk9XOARIBxgGwoIJQRMRRQQEBFQDBAQDDAAbQRIMFQQOBB0TDRJXAgQaCBZUBAUDVxgESBUSAgRIFx4QBEhJRURFABFXWUVZUUdRSUhQVxwVSFRS", "wteha");
    }

    private static int lIlIlIIlIII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean lIlIlIlIIIl(Object object, Object object2) {
        return object != object2;
    }

    private static boolean lIlIlIllIII(int n, int n2) {
        return n > n2;
    }

    private static int lIlIlIIIlll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public ObsidianDefenders() {
        ObsidianDefenders lIIIlIIlIIlIlI;
        lIIIlIIlIIlIlI.debug = lIllIllIl[0];
    }

    public void donarOrAEquip(ArrayList<Player> lIIIIllIlllIlI, int lIIIIllIlllIIl, Boolean lIIIIllIlllIII) {
        if (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIllIlllIII.booleanValue())) {
            lIIIIllIlllIIl = (int)Math.ceil(lIIIIllIlllIIl / lIIIIllIlllIlI.size());
        }
        Iterator<Player> lIIIIllIllIlll = lIIIIllIlllIlI.iterator();
        while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIllIllIlll.hasNext())) {
            ObsidianDefenders lIIIIllIllllll;
            Player lIIIIlllIIIIII = lIIIIllIllIlll.next();
            lIIIIllIllllll.donarOr(lIIIIlllIIIIII, lIIIIllIlllIIl);
            "".length();
            if (-"   ".length() <= 0) continue;
            return;
        }
    }

    private static boolean lIlIlIlIIII(int n) {
        return n > 0;
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        ObsidianDefenders lIIIlIIlIIIIII;
        ArrayList<JocEquips.Equip> lIIIlIIlIIIIIl = new ArrayList<JocEquips.Equip>();
        "".length();
        lIIIlIIlIIIIIl.add(lIIIlIIlIIIIII.new JocEquips.Equip(DyeColor.RED, lIlIlIllI[lIllIllIl[1]]));
        "".length();
        lIIIlIIlIIIIIl.add(lIIIlIIlIIIIII.new JocEquips.Equip(DyeColor.BLUE, lIlIlIllI[lIllIllIl[2]]));
        return lIIIlIIlIIIIIl;
    }

    private static boolean lIlIlIlIllI(Object object) {
        return object == null;
    }

    protected void onExplosionPrime(ExplosionPrimeEvent lIIIlIIIlIlllI) {
        ObsidianDefenders lIIIlIIIlIllIl;
        super.onExplosionPrime(lIIIlIIIlIlllI);
        "".length();
        Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIlIlIllI[lIllIllIl[3]])));
    }

    private static boolean lIlIlIIlIll(int n) {
        return n <= 0;
    }

    protected void onPlayerInteract(PlayerInteractEvent lIIIIlIIIllIlI, Player lIIIIlIIIllllI) {
        ObsidianDefenders lIIIIlIIIllIll;
        super.onPlayerInteract(lIIIIlIIIllIlI, lIIIIlIIIllllI);
        ItemStack lIIIIlIIIlllIl = lIIIIlIIIllIlI.getItem();
        PlayerInventory lIIIIlIIIlllII = lIIIIlIIIllllI.getInventory();
        if (ObsidianDefenders.lIlIlIIllII((Object)lIIIIlIIIllIlI.getItem())) {
            Object lIIIIlIIlIIlIl;
            Object lIIIIlIIllIIIl;
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.WOOD_SWORD) && ObsidianDefenders.lIlIlIIlIlI((int)lIIIIlIIIllIll.JocIniciat.booleanValue())) {
                Ability.openSelectionInventory(lIIIIlIIIllIll.plugin, lIIIIlIIIllIll, lIIIIlIIIllllI);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.DIAMOND_BLOCK)) {
                lIIIIlIIllIIIl = Bukkit.getServer().createInventory((InventoryHolder)lIIIIlIIIllllI, lIllIllIl[15], lIlIlIllI[lIllIllIl[64]]);
                Iterator<Player> lIIIIlIIIlIlIl = lIIIIlIIIllIll.obtenirEquip(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIIlIlIl.hasNext())) {
                    Player lIIIIlIIllIIlI = lIIIIlIIIlIlIl.next();
                    if (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIlI.getName().equals(lIIIIlIIIllllI.getName()))) {
                        "".length();
                        if (((103 ^ 58) & ~(234 ^ 183)) == ((35 ^ 24) & ~(139 ^ 176))) continue;
                        return;
                    }
                    if (!ObsidianDefenders.lIlIlIIlIlI((int)lIIIIlIIllIIlI.isDead())) continue;
                    if (ObsidianDefenders.lIlIlIIlIlI((int)lIIIIlIIllIIlI.isOnline())) {
                        "".length();
                        if ((88 + 46 - 130 + 163 ^ 48 + 79 - -24 + 12) > "  ".length()) continue;
                        return;
                    }
                    ItemStack lIIIIlIIllIIll = new ItemStack(Material.SKULL_ITEM, lIllIllIl[1], (short)SkullType.PLAYER.ordinal());
                    ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                    arritemStack[ObsidianDefenders.lIllIllIl[0]] = Utils.setItemName(lIIIIlIIllIIll, lIIIIlIIllIIlI.getName());
                    "".length();
                    lIIIIlIIllIIIl.addItem(arritemStack);
                    "".length();
                    if ("  ".length() >= 0) continue;
                    return;
                }
                "".length();
                lIIIIlIIIllllI.openInventory((Inventory)lIIIIlIIllIIIl);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.NETHER_STAR)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquipEnemic(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIllIIII = (Player)lIIIIlIIllIIIl.next();
                    lIIIIlIIllIIII.setHealth(1.0);
                    "".length();
                    lIIIIlIIllIIII.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, lIllIllIl[65], lIllIllIl[4], lIllIllIl[0]), lIllIllIl[1]);
                    "".length();
                    if (null == null) continue;
                    return;
                }
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquip(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIllll = (Player)lIIIIlIIllIIIl.next();
                    "".length();
                    lIIIIlIIlIllll.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIllIllIl[65], lIllIllIl[3], lIllIllIl[0]), lIllIllIl[1]);
                    "".length();
                    if ((50 ^ 54) == (138 ^ 142)) continue;
                    return;
                }
                "".length();
                Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIIlIIIllllI.getName()).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[66]]).append((Object)ChatColor.BOLD).append(lIlIlIllI[lIllIllIl[67]]).append((Object)ChatColor.RESET).append(lIlIlIllI[lIllIllIl[68]])));
                lIIIIlIIIllIlI.getItem().setType(Material.FIREWORK_CHARGE);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.ARROW) && ObsidianDefenders.lIlIlIIllIl(lIIIIlIIIlllIl.getEnchantments().size(), lIllIllIl[1])) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquipEnemic(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIlllI = (Player)lIIIIlIIllIIIl.next();
                    lIIIIlIIlIlllI.setHealth(lIIIIlIIlIlllI.getHealth() - 3.0);
                    "".length();
                    if (((244 + 182 - 255 + 82 ^ 133 + 40 - 56 + 41) & (65 + 23 - 56 + 135 ^ 63 + 5 - 6 + 134 ^ -" ".length())) <= 0) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[69]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.MAGMA_CREAM)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquipEnemic(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIllIl = (Player)lIIIIlIIllIIIl.next();
                    lIIIIlIIlIllIl.setFireTicks(lIllIllIl[63]);
                    "".length();
                    if ((156 ^ 152) >= 0) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[70]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.STRING)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquipEnemic(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIllII = (Player)lIIIIlIIllIIIl.next();
                    "".length();
                    lIIIIlIIlIllII.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, lIllIllIl[71], lIllIllIl[2], lIllIllIl[0]), lIllIllIl[1]);
                    "".length();
                    if (-" ".length() <= -" ".length()) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[72]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIllIlI.getItem().getType(), (Object)Material.SPIDER_EYE)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquipEnemic(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIlIll = (Player)lIIIIlIIllIIIl.next();
                    "".length();
                    lIIIIlIIlIlIll.addPotionEffect(new PotionEffect(PotionEffectType.POISON, lIllIllIl[73], lIllIllIl[1], lIllIllIl[0]), lIllIllIl[1]);
                    "".length();
                    if (-" ".length() <= 0) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[74]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIllIlI.getItem().getType(), (Object)Material.PAPER)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquipEnemic(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIlIlI = (Player)lIIIIlIIllIIIl.next();
                    "".length();
                    lIIIIlIIlIlIlI.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, lIllIllIl[75], lIllIllIl[5], lIllIllIl[0]), lIllIllIl[1]);
                    "".length();
                    if ("   ".length() > "  ".length()) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[76]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIllIlI.getItem().getType(), (Object)Material.COCOA)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquip(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIlIIl = (Player)lIIIIlIIllIIIl.next();
                    "".length();
                    lIIIIlIIlIlIIl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIllIllIl[77], lIllIllIl[78], lIllIllIl[0]), lIllIllIl[1]);
                    "".length();
                    if (((49 ^ 117 ^ (237 ^ 137)) & (132 + 53 - 74 + 43 ^ 66 + 136 - 176 + 160 ^ -" ".length())) <= "  ".length()) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[79]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIllIlI.getItem().getType(), (Object)Material.EMERALD)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquip(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIIlll = (Player)lIIIIlIIllIIIl.next();
                    double lIIIIlIIlIlIII = lIIIIlIIlIIlll.getHealth() + 3.0;
                    if (ObsidianDefenders.lIlIlIllIll(ObsidianDefenders.lIlIlIllIIl(lIIIIlIIlIlIII, 20.0))) {
                        lIIIIlIIlIlIII = 20.0;
                    }
                    lIIIIlIIlIIlll.setHealth(lIIIIlIIlIlIII);
                    "".length();
                    if (((48 ^ 97 ^ (92 ^ 29)) & (97 ^ 8 ^ (81 ^ 40) ^ -" ".length())) == 0) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[80]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIllIlI.getItem().getType(), (Object)Material.SUGAR)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquip(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    Player lIIIIlIIlIIllI = (Player)lIIIIlIIllIIIl.next();
                    "".length();
                    lIIIIlIIlIIllI.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIllIllIl[71], lIllIllIl[2], lIllIllIl[0]), lIllIllIl[1]);
                    "".length();
                    if (" ".length() != (58 ^ 54 ^ (99 ^ 107))) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[81]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.GLASS)) {
                lIIIIlIIllIIIl = lIIIIlIIIllIll.obtenirEquip(lIIIIlIIIllllI).getPlayers().iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIllIIIl.hasNext())) {
                    lIIIIlIIlIIlIl = (Player)lIIIIlIIllIIIl.next();
                    "".length();
                    lIIIIlIIlIIlIl.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, lIllIllIl[71], lIllIllIl[1], lIllIllIl[0]), lIllIllIl[1]);
                    "".length();
                    if (-(74 ^ 78) <= 0) continue;
                    return;
                }
                lIIIIlIIIllllI.sendMessage(lIlIlIllI[lIllIllIl[82]]);
                ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                "".length();
                lIIIIlIIIlllII.removeItem(arritemStack);
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.BLAZE_POWDER)) {
                Player lIIIIlIIlIIIIl = null;
                lIIIIlIIlIIlIl = lIIIIlIIIllllI.getNearbyEntities(25.0, 40.0, 25.0).iterator();
                while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIIlIIlIl.hasNext())) {
                    Player lIIIIlIIlIIlII;
                    Entity lIIIIlIIlIIIll = (Entity)lIIIIlIIlIIlIl.next();
                    if (ObsidianDefenders.lIlIlIIlIIl(lIIIIlIIlIIIll instanceof Player) && ObsidianDefenders.lIlIlIIlIlI((int)lIIIIlIIIllIll.obtenirEquip(lIIIIlIIlIIlII = (Player)lIIIIlIIlIIIll).equals(lIIIIlIIIllIll.obtenirEquip(lIIIIlIIIllllI)))) {
                        if (ObsidianDefenders.lIlIlIlIllI(lIIIIlIIlIIIIl)) {
                            lIIIIlIIlIIIIl = lIIIIlIIlIIlII;
                            "".length();
                            if ((" ".length() & (" ".length() ^ -" ".length())) > ((182 ^ 172 ^ (199 ^ 193)) & (89 + 125 - 165 + 85 ^ 152 + 89 - 149 + 62 ^ -" ".length()))) {
                                return;
                            }
                        } else if (ObsidianDefenders.lIlIlIlIlII(ObsidianDefenders.lIlIlIllIlI(lIIIIlIIIllllI.getLocation().distance(lIIIIlIIlIIlII.getLocation()), lIIIIlIIIllllI.getLocation().distance(lIIIIlIIlIIIIl.getLocation()))) && ObsidianDefenders.lIlIlIIlIlI(lIIIIlIIlIIlII.getFireTicks())) {
                            lIIIIlIIlIIIIl = lIIIIlIIlIIlII;
                        }
                    }
                    "".length();
                    if (-"  ".length() < 0) continue;
                    return;
                }
                if (ObsidianDefenders.lIlIlIIllII(lIIIIlIIlIIIIl)) {
                    int lIIIIlIIlIIIlI = Integer.parseInt(lIIIIlIIIllIll.pTemp().ObtenirPropietat(String.valueOf(new StringBuilder().append(lIIIIlIIIllllI.getName()).append(lIlIlIllI[lIllIllIl[83]]))));
                    lIIIIlIIlIIIIl.setFireTicks((lIllIllIl[5] + lIIIIlIIlIIIlI + lIIIIlIIIllllI.getLevel() / lIllIllIl[2]) * lIllIllIl[25]);
                    ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                    arritemStack[ObsidianDefenders.lIllIllIl[0]] = new ItemStack(lIIIIlIIIlllIl.getType());
                    "".length();
                    lIIIIlIIIlllII.removeItem(arritemStack);
                    lIIIIlIIIllIll.pTemp().EstablirPropietat(lIlIlIllI[lIllIllIl[84]], lIIIIlIIlIIIIl.getName());
                    lIIIIlIIIllIll.pTemp().EstablirPropietat(lIlIlIllI[lIllIllIl[85]], lIIIIlIIIllllI.getName());
                    "".length();
                    if (-(166 ^ 162) > 0) {
                        return;
                    }
                } else {
                    lIIIIlIIIllllI.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIlIlIllI[lIllIllIl[86]])));
                }
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIIIlllIl.getType(), (Object)Material.SLIME_BALL)) {
                // empty if block
            }
        }
    }

    private static boolean lIlIlIlIIlI(Object object, Object object2) {
        return object == object2;
    }

    private static int lIlIlIlIIll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    static {
        ObsidianDefenders.lIlIlIIIllI();
        ObsidianDefenders.lIlIlIIIlIl();
    }

    public void donarOrATots(int lIIIIllIIIIIlI, String lIIIIllIIIIllI, Boolean lIIIIllIIIIIII) {
        ObsidianDefenders lIIIIllIIIIIll;
        ArrayList<Player> lIIIIllIIIIlII = lIIIIllIIIIIll.getPlayers();
        Iterator<Player> lIIIIlIllllllI = lIIIIllIIIIlII.iterator();
        while (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIllllllI.hasNext())) {
            Player lIIIIllIIIlIIl = lIIIIlIllllllI.next();
            lIIIIllIIIIIll.donarOr(lIIIIllIIIlIIl, lIIIIllIIIIIlI, lIIIIllIIIIllI, lIIIIllIIIIIII);
            "".length();
            if (-(118 ^ 115) < 0) continue;
            return;
        }
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent lIIIIlIlIIllll, Player lIIIIlIlIIlllI, Player lIIIIlIlIIllIl) {
        ObsidianDefenders lIIIIlIlIlIIII;
        super.onPlayerDeathByPlayer(lIIIIlIlIIllll, lIIIIlIlIIlllI, lIIIIlIlIIllIl);
        int lIIIIlIlIIllII = lIllIllIl[0];
        Player lIIIIlIlIIlIll = lIIIIlIlIIllll.getEntity();
        Location lIIIIlIlIIlIlI = lIIIIlIlIIlIll.getLocation();
        if (ObsidianDefenders.lIlIlIlIllI((Object)lIIIIlIlIIllIl)) {
            // empty if block
        }
        if (ObsidianDefenders.lIlIlIlIllI((Object)lIIIIlIlIIllIl)) {
            String lIIIIlIlIlllII;
            Long lIIIIlIlIllIll = Calendar.getInstance().getTimeInMillis();
            Long lIIIIlIlIllIlI = Long.parseLong(lIIIIlIlIlIIII.pTemp().ObtenirPropietat(lIlIlIllI[lIllIllIl[31]]));
            if (ObsidianDefenders.lIlIlIIlIll(ObsidianDefenders.lIlIlIlIlIl(lIIIIlIlIllIll - lIIIIlIlIllIlI, 2000L)) && ObsidianDefenders.lIlIlIIlIlI((int)(lIIIIlIlIlllII = lIIIIlIlIlIIII.pTemp().ObtenirPropietat(lIlIlIllI[lIllIllIl[32]])).equals(lIllIllIl[0]))) {
                Player lIIIIlIlIlllIl;
                lIIIIlIlIIllIl = lIIIIlIlIlllIl = lIIIIlIlIlIIII.plugin.getServer().getPlayer(lIIIIlIlIlllII);
                lIIIIlIlIIllII = lIllIllIl[1];
            }
        }
        if (ObsidianDefenders.lIlIlIlIllI((Object)lIIIIlIlIIllIl)) {
            Player lIIIIlIlIllIIl = Bukkit.getPlayer((String)lIIIIlIlIlIIII.pPlayer(lIIIIlIlIIlIll).ObtenirPropietat(lIlIlIllI[lIllIllIl[33]]));
            int lIIIIlIlIllIII = Integer.parseInt(lIIIIlIlIlIIII.pPlayer(lIIIIlIlIIlIll).ObtenirPropietat(lIlIlIllI[lIllIllIl[34]]));
            if (ObsidianDefenders.lIlIlIIllII((Object)lIIIIlIlIllIIl) && ObsidianDefenders.lIlIlIlIlll(lIIIIlIlIlIIII.segonsTranscorreguts() - lIIIIlIlIllIII, lIllIllIl[35])) {
                lIIIIlIlIIllIl = lIIIIlIlIllIIl;
            }
        }
        if (ObsidianDefenders.lIlIlIIllII((Object)lIIIIlIlIIllIl)) {
            int lIIIIlIlIlIlIl = Integer.parseInt(lIIIIlIlIlIIII.pTemp().ObtenirPropietat(String.valueOf(new StringBuilder().append(lIIIIlIlIIllIl.getName()).append(lIlIlIllI[lIllIllIl[36]]))));
            int lIIIIlIlIlIlII = Integer.parseInt(lIIIIlIlIlIIII.pTemp().ObtenirPropietat(String.valueOf(new StringBuilder().append(lIIIIlIlIIlIll.getName()).append(lIlIlIllI[lIllIllIl[37]]))));
            int lIIIIlIlIlIIll = lIllIllIl[5] + lIIIIlIlIlIlII * lIllIllIl[1];
            if (ObsidianDefenders.lIlIlIllIII(lIIIIlIlIlIlII, lIllIllIl[4])) {
                lIIIIlIlIlIIll += lIllIllIl[2];
            }
            if (ObsidianDefenders.lIlIlIllIII(lIIIIlIlIlIlII, lIllIllIl[9])) {
                lIIIIlIlIlIIll += lIllIllIl[5];
            }
            if (ObsidianDefenders.lIlIlIllIII(lIIIIlIlIlIlII, lIllIllIl[11])) {
                lIIIIlIlIlIIll += lIllIllIl[5];
            }
            if (ObsidianDefenders.lIlIlIIlllI(lIIIIlIlIIllII, lIllIllIl[1])) {
                lIIIIlIlIlIIll += lIllIllIl[1];
            }
            if (ObsidianDefenders.lIlIlIIllIl(lIIIIlIlIlIIll, lIllIllIl[30])) {
                lIIIIlIlIlIIll = lIllIllIl[30];
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIlIIllIl.getItemInHand().getType(), (Object)Material.GOLD_PICKAXE) && ObsidianDefenders.lIlIlIIllIl(lIIIIlIlIlIIll *= lIllIllIl[3], lIllIllIl[36])) {
                lIIIIlIlIlIIll = lIllIllIl[36];
            }
            if (ObsidianDefenders.lIlIlIIlllI((int)lIIIIlIlIIlIll.getInventory().contains(Material.DIAMOND_PICKAXE), lIllIllIl[1])) {
                lIIIIlIlIlIIll += lIllIllIl[1];
            }
            if (ObsidianDefenders.lIlIlIIlllI((int)lIIIIlIlIIlIll.getInventory().contains(Material.GOLD_BLOCK), lIllIllIl[1])) {
                lIIIIlIlIlIIll += lIllIllIl[1];
            }
            if (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIlIlIIII.obtenirEquip(lIIIIlIlIIlIll).getPlayers().contains((Object)lIIIIlIlIIllIl))) {
                lIIIIlIlIlIIll = lIllIllIl[0];
                "".length();
                if (-(138 ^ 192 ^ (16 ^ 95)) >= 0) {
                    return;
                }
            } else if (ObsidianDefenders.lIlIlIIlIlI(lIIIIlIlIIllII)) {
                "".length();
                lIIIIlIlIIllIl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, lIllIllIl[10], lIllIllIl[3]));
            }
            PlayerInventory lIIIIlIlIlIIlI = lIIIIlIlIIllIl.getInventory();
            ItemStack lIIIIlIlIlIIIl = new ItemStack(Material.GOLD_NUGGET, lIIIIlIlIlIIll);
            ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
            arritemStack[ObsidianDefenders.lIllIllIl[0]] = lIIIIlIlIlIIIl;
            "".length();
            lIIIIlIlIlIIlI.addItem(arritemStack);
            lIIIIlIlIlIIII.pPlayer(lIIIIlIlIIllIl).IncrementarPropietat(lIlIlIllI[lIllIllIl[38]], lIIIIlIlIlIIll);
            lIIIIlIlIIllll.setDeathMessage(String.valueOf(new StringBuilder().append(lIIIIlIlIIllIl.getName()).append(lIlIlIllI[lIllIllIl[39]]).append(lIIIIlIlIIlIll.getName()).append(lIlIlIllI[lIllIllIl[40]]).append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[41]]).append(lIIIIlIlIlIIll).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[42]])));
            if (ObsidianDefenders.lIlIlIIlllI(lIIIIlIlIIllII, lIllIllIl[1])) {
                lIIIIlIlIIllll.setDeathMessage(String.valueOf(new StringBuilder().append(lIIIIlIlIIllIl.getName()).append(lIlIlIllI[lIllIllIl[43]]).append(lIIIIlIlIIlIll.getName()).append(lIlIlIllI[lIllIllIl[44]]).append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[45]]).append(lIIIIlIlIlIIll).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[10]])));
                lIIIIlIlIlIIII.pTemp().EstablirPropietat(String.valueOf(new StringBuilder().append(lIIIIlIlIIllIl.getName()).append(lIlIlIllI[lIllIllIl[46]])), Integer.toString(Integer.parseInt(lIIIIlIlIlIIII.pTemp().ObtenirPropietat(String.valueOf(new StringBuilder().append(lIIIIlIlIIllIl.getName()).append(lIlIlIllI[lIllIllIl[47]])))) + lIllIllIl[1]));
            }
            if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIlIlIIllIl.getItemInHand().getType(), (Object)Material.GOLD_PICKAXE)) {
                lIIIIlIlIIllll.setDeathMessage(String.valueOf(new StringBuilder().append(lIIIIlIlIIllIl.getName()).append(lIlIlIllI[lIllIllIl[48]]).append(lIIIIlIlIIlIll.getName()).append(lIlIlIllI[lIllIllIl[49]]).append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[8]]).append(lIIIIlIlIlIIll).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[50]]).append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[51]]).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[52]])));
            }
            if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIlIlIlIIII.plugin, lIIIIlIlIlIIII, lIIIIlIlIIlIll, Ability.AbilityType.CREEPER))) {
                float lIIIIlIlIlIlll = 0.8f + (float)(lIIIIlIlIlIlII / lIllIllIl[2]);
                "".length();
                lIIIIlIlIlIIII.world.createExplosion(lIIIIlIlIIlIlI.getX(), lIIIIlIlIIlIlI.getY(), lIIIIlIlIIlIlI.getZ(), lIIIIlIlIlIlll, lIllIllIl[0], lIllIllIl[0]);
            }
            lIIIIlIlIlIIII.pTemp().EstablirPropietat(String.valueOf(new StringBuilder().append(lIIIIlIlIIllIl.getName()).append(lIlIlIllI[lIllIllIl[53]])), Integer.toString(lIIIIlIlIlIlIl + lIllIllIl[1]));
            lIIIIlIlIlIIII.pTemp().EstablirPropietat(String.valueOf(new StringBuilder().append(lIIIIlIlIIlIll.getName()).append(lIlIlIllI[lIllIllIl[6]])), lIlIlIllI[lIllIllIl[54]]);
            if (ObsidianDefenders.lIlIlIIlIIl(lIIIIlIlIlIIll)) {
                lIIIIlIlIlIIII.pPlayer(lIIIIlIlIIllIl).IncrementarPropietat(lIlIlIllI[lIllIllIl[55]]);
                lIIIIlIlIlIIII.pPlayer(lIIIIlIlIIlIll).IncrementarPropietat(lIlIlIllI[lIllIllIl[56]]);
            }
            if (ObsidianDefenders.lIlIlIIlllI((int)lIIIIlIlIIlIll.getInventory().contains(Material.DIAMOND_PICKAXE), lIllIllIl[1])) {
                lIIIIlIlIIlIll.getInventory().remove(Material.DIAMOND_PICKAXE);
                ItemStack lIIIIlIlIlIllI = new ItemStack(Material.GOLD_PICKAXE, lIllIllIl[1]);
                lIIIIlIlIIllll.setDeathMessage(String.valueOf(new StringBuilder().append(lIIIIlIlIIllIl.getName()).append(lIlIlIllI[lIllIllIl[57]]).append(lIIIIlIlIIlIll.getName()).append(lIlIlIllI[lIllIllIl[58]]).append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[59]]).append(lIIIIlIlIlIIll).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[60]])));
                lIIIIlIlIIllIl.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[61]])));
                lIIIIlIlIIllIl.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[62]])));
                ItemStack[] arritemStack2 = new ItemStack[lIllIllIl[1]];
                arritemStack2[ObsidianDefenders.lIllIllIl[0]] = lIIIIlIlIlIllI;
                "".length();
                lIIIIlIlIlIIlI.addItem(arritemStack2);
            }
            if (ObsidianDefenders.lIlIlIIlllI((int)lIIIIlIlIIlIll.getInventory().contains(Material.GOLD_PICKAXE), lIllIllIl[1])) {
                lIIIIlIlIIlIll.getInventory().remove(Material.GOLD_PICKAXE);
            }
            lIIIIlIlIlIIII.updateScoreBoards();
            "".length();
            if ((226 ^ 197 ^ (172 ^ 142)) == 0) {
                return;
            }
        } else {
            lIIIIlIlIIllll.setDeathMessage(String.valueOf(new StringBuilder().append(lIIIIlIlIIlIll.getName()).append(lIlIlIllI[lIllIllIl[63]])));
        }
    }

    private static int lIlIlIllIIl(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean lIlIlIIllIl(int n, int n2) {
        return n >= n2;
    }

    private static boolean lIlIlIIllll(int n, int n2) {
        return n < n2;
    }

    @Override
    protected void onProjectileHit(ProjectileHitEvent lIIIIIllIIlllI, Projectile lIIIIIllIIllIl) {
        ObsidianDefenders lIIIIIllIIllll;
        super.onProjectileHit(lIIIIIllIIlllI, lIIIIIllIIllIl);
        LivingEntity lIIIIIllIlIIlI = (LivingEntity)lIIIIIllIIllIl.getShooter();
        Projectile lIIIIIllIlIIIl = lIIIIIllIIlllI.getEntity();
        Location lIIIIIllIlIIII = lIIIIIllIlIIIl.getLocation();
        if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIIllIIlllI.getEntityType(), (Object)EntityType.SNOWBALL) && ObsidianDefenders.lIlIlIIlIIl(lIIIIIllIlIIlI instanceof Player)) {
            Player lIIIIIlllIIIII = (Player)lIIIIIllIlIIlI;
            "".length();
            Bukkit.broadcastMessage((String)String.valueOf(new StringBuilder().append(lIlIlIllI[lIllIllIl[97]]).append(lIIIIIlllIIIII.getName()).append(lIlIlIllI[lIllIllIl[98]])));
        }
        if (ObsidianDefenders.lIlIlIlIIlI((Object)lIIIIIllIIlllI.getEntityType(), (Object)EntityType.ARROW) && ObsidianDefenders.lIlIlIIlIIl(lIIIIIllIlIIlI instanceof Player)) {
            Player lIIIIIllIlIlll = (Player)lIIIIIllIlIIlI;
            int lIIIIIllIlIllI = lIllIllIl[0];
            if (!ObsidianDefenders.lIlIlIlIIII(ObsidianDefenders.lIlIlIlllII(lIIIIIllIlIIIl.getLocation().distance(lIIIIIllIIllll.pMapaActual().ObtenirLocation(String.valueOf(new StringBuilder().append(lIlIlIllI[lIllIllIl[99]]).append(Integer.toString(lIllIllIl[0]))), lIIIIIllIIllll.world)), 5.0)) || ObsidianDefenders.lIlIlIIlIll(ObsidianDefenders.lIlIlIlllII(lIIIIIllIlIIIl.getLocation().distance(lIIIIIllIIllll.pMapaActual().ObtenirLocation(String.valueOf(new StringBuilder().append(lIlIlIllI[lIllIllIl[100]]).append(Integer.toString(lIllIllIl[1]))), lIIIIIllIIllll.world)), 5.0))) {
                lIIIIIllIlIllI = lIllIllIl[1];
            }
            if (ObsidianDefenders.lIlIlIIllIl(lIIIIIllIlIlll.getLocation().getBlockY(), lIllIllIl[53]) && ObsidianDefenders.lIlIlIllIII(lIIIIIllIlIIIl.getTicksLived(), lIllIllIl[5]) && ObsidianDefenders.lIlIlIIlIlI(lIIIIIllIlIllI)) {
                PlayerInventory lIIIIIllIllIII = lIIIIIllIlIlll.getInventory();
                if (ObsidianDefenders.lIlIlIIlllI((int)lIIIIIllIllIII.contains(Material.FIREWORK_CHARGE), lIllIllIl[1])) {
                    lIIIIIllIIllll.pTemp().EstablirPropietat(lIlIlIllI[lIllIllIl[101]], Long.toString(Calendar.getInstance().getTimeInMillis()));
                    lIIIIIllIIllll.pTemp().EstablirPropietat(lIlIlIllI[lIllIllIl[102]], lIIIIIllIlIlll.getName());
                    ItemStack lIIIIIllIlllll = new ItemStack(Material.FIREWORK_CHARGE, lIllIllIl[1]);
                    ItemStack[] arritemStack = new ItemStack[lIllIllIl[1]];
                    arritemStack[ObsidianDefenders.lIllIllIl[0]] = lIIIIIllIlllll;
                    "".length();
                    lIIIIIllIllIII.removeItem(arritemStack);
                    int lIIIIIllIllllI = Integer.parseInt(lIIIIIllIIllll.pTemp().ObtenirPropietat(String.valueOf(new StringBuilder().append(lIIIIIllIlIlll.getName()).append(lIlIlIllI[lIllIllIl[35]]))));
                    int lIIIIIllIlllIl = Integer.parseInt(lIIIIIllIIllll.pTemp().ObtenirPropietat(String.valueOf(new StringBuilder().append(lIIIIIllIlIlll.getName()).append(lIlIlIllI[lIllIllIl[103]]))));
                    float lIIIIIllIlllII = 3.25f;
                    lIIIIIllIlllII += 0.12f * (float)lIIIIIllIllllI;
                    lIIIIIllIlllII += 0.28f * (float)lIIIIIllIlllIl;
                    float lIIIIIllIllIll = (float)lIIIIIllIlIlll.getHealth() / (float)lIIIIIllIlIlll.getMaxHealth();
                    lIIIIIllIlllII *= lIIIIIllIllIll;
                    if (ObsidianDefenders.lIlIlIIlllI((int)lIIIIIllIllIII.contains(Material.NETHER_STAR), lIllIllIl[1])) {
                        lIIIIIllIlllII += 1.0f;
                        lIIIIIllIlllII += lIIIIIllIlllII * 1.012f;
                    }
                    if (ObsidianDefenders.lIlIlIIlllI((int)lIIIIIllIllIII.contains(Material.GOLD_SWORD), lIllIllIl[1])) {
                        lIIIIIllIlllII += lIIIIIllIlllII * 0.2f;
                    }
                    if (ObsidianDefenders.lIlIlIIlIIl((int)Ability.hasAbility(lIIIIIllIIllll.plugin, lIIIIIllIIllll, lIIIIIllIlIlll, Ability.AbilityType.PIROT\u00c8CNIC))) {
                        lIIIIIllIlllII += lIIIIIllIlllII * 0.35f;
                    }
                    "".length();
                    lIIIIIllIIllll.world.createExplosion(lIIIIIllIlIIII.getX(), lIIIIIllIlIIII.getY(), lIIIIIllIlIIII.getZ(), lIIIIIllIlllII, lIllIllIl[0], lIllIllIl[0]);
                    if (ObsidianDefenders.lIlIlIIlIlI((int)lIIIIIllIIllll.pTemp().ObtenirPropietat(lIlIlIllI[lIllIllIl[104]]).equals(Float.toString(lIIIIIllIlllII)))) {
                        lIIIIIllIlIlll.sendMessage(String.valueOf(new StringBuilder().append(lIlIlIllI[lIllIllIl[105]]).append(Float.toString(lIIIIIllIlllII))));
                        lIIIIIllIIllll.pTemp().EstablirPropietat(lIlIlIllI[lIllIllIl[106]], Float.toString(lIIIIIllIlllII));
                    }
                    ItemStack lIIIIIllIllIlI = new ItemStack(Material.GOLD_NUGGET, lIllIllIl[1]);
                    lIIIIIllIIllll.world.dropItem(lIIIIIllIlIIII, lIIIIIllIllIlI).setVelocity(new Vector(lIllIllIl[0], lIllIllIl[0], lIllIllIl[0]));
                    double lIIIIIllIllIIl = lIIIIIllIlIlll.getHealth();
                    if (ObsidianDefenders.lIlIlIIlIlI(ObsidianDefenders.lIlIlIlllIl(lIIIIIllIllIIl, 20.0))) {
                        lIIIIIllIlIlll.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIlIlIllI[lIllIllIl[78]])));
                    }
                    if (ObsidianDefenders.lIlIlIIlIll(ObsidianDefenders.lIlIlIlllII(lIIIIIllIllIIl -= 2.0, 0.0))) {
                        lIIIIIllIllIIl = 1.0;
                    }
                    lIIIIIllIlIlll.setHealth(lIIIIIllIllIIl);
                }
                lIIIIIllIlIIIl.remove();
            }
        }
    }

    private static int lIlIlIllIlI(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean lIlIlIIlIIl(int n) {
        return n != 0;
    }

    private static boolean lIlIlIIllII(Object object) {
        return object != null;
    }

    private static boolean lIlIlIlIlll(int n, int n2) {
        return n <= n2;
    }

    public void donarOr(Player lIIIIlIllIllll, int lIIIIlIllIlllI, String lIIIIlIllIllIl, Boolean lIIIIlIlllIIlI) {
        ObsidianDefenders lIIIIlIlllIIII;
        lIIIIlIlllIIII.donarOr(lIIIIlIllIllll, lIIIIlIllIlllI);
        String lIIIIlIlllIIIl = String.valueOf(new StringBuilder().append(lIIIIlIllIllIl).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[28]]).append((Object)ChatColor.GOLD).append(lIlIlIllI[lIllIllIl[29]]).append(lIIIIlIllIlllI).append((Object)ChatColor.WHITE).append(lIlIlIllI[lIllIllIl[30]]));
        if (ObsidianDefenders.lIlIlIIlIIl((int)lIIIIlIlllIIlI.booleanValue())) {
            "".length();
            Bukkit.broadcastMessage((String)lIIIIlIlllIIIl);
            "".length();
            if (-" ".length() > ((1 ^ 99) & ~(115 ^ 17))) {
                return;
            }
        } else {
            lIIIIlIllIllll.sendMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(lIIIIlIlllIIIl)));
        }
    }

    private static void lIlIlIIIllI() {
        lIllIllIl = new int[108];
        ObsidianDefenders.lIllIllIl[0] = (157 ^ 140 ^ (17 ^ 91)) & (167 + 229 - 282 + 121 ^ 128 + 82 - 144 + 110 ^ -" ".length());
        ObsidianDefenders.lIllIllIl[1] = " ".length();
        ObsidianDefenders.lIllIllIl[2] = "  ".length();
        ObsidianDefenders.lIllIllIl[3] = "   ".length();
        ObsidianDefenders.lIllIllIl[4] = 95 ^ 91;
        ObsidianDefenders.lIllIllIl[5] = 83 ^ 86;
        ObsidianDefenders.lIllIllIl[6] = 143 + 83 - 212 + 142 ^ 37 + 22 - -94 + 21;
        ObsidianDefenders.lIllIllIl[7] = 223 ^ 181 ^ (230 ^ 135);
        ObsidianDefenders.lIllIllIl[8] = (163 ^ 159) & ~(75 ^ 119) ^ (152 ^ 181);
        ObsidianDefenders.lIllIllIl[9] = 52 + 59 - 74 + 147 ^ 31 + 47 - 72 + 184;
        ObsidianDefenders.lIllIllIl[10] = 95 ^ 77 ^ (148 ^ 174);
        ObsidianDefenders.lIllIllIl[11] = 198 ^ 173 ^ (112 ^ 17);
        ObsidianDefenders.lIllIllIl[12] = (90 ^ 93) + (22 ^ 14) - "   ".length() + (56 + 5 - -11 + 100);
        ObsidianDefenders.lIllIllIl[13] = 92 ^ 84;
        ObsidianDefenders.lIllIllIl[14] = 168 ^ 175;
        ObsidianDefenders.lIllIllIl[15] = 59 ^ 75 ^ (4 ^ 125);
        ObsidianDefenders.lIllIllIl[16] = 250 ^ 170;
        ObsidianDefenders.lIllIllIl[17] = 174 ^ 162;
        ObsidianDefenders.lIllIllIl[18] = 46 ^ 22 ^ (117 ^ 64);
        ObsidianDefenders.lIllIllIl[19] = 93 ^ 83;
        ObsidianDefenders.lIllIllIl[20] = 190 ^ 139 ^ (14 ^ 52);
        ObsidianDefenders.lIllIllIl[21] = 140 ^ 156;
        ObsidianDefenders.lIllIllIl[22] = 100 ^ 117;
        ObsidianDefenders.lIllIllIl[23] = 8 ^ 26;
        ObsidianDefenders.lIllIllIl[24] = 122 + 75 - 138 + 73 ^ 143 + 58 - 192 + 142;
        ObsidianDefenders.lIllIllIl[25] = 146 + 66 - 149 + 87 ^ 50 + 90 - 11 + 1;
        ObsidianDefenders.lIllIllIl[26] = 132 ^ 145;
        ObsidianDefenders.lIllIllIl[27] = 55 + 123 - 46 + 81 ^ 91 + 54 - 83 + 133;
        ObsidianDefenders.lIllIllIl[28] = 28 ^ 122 ^ (211 ^ 162);
        ObsidianDefenders.lIllIllIl[29] = 120 ^ 96;
        ObsidianDefenders.lIllIllIl[30] = 15 ^ 90 ^ (50 ^ 126);
        ObsidianDefenders.lIllIllIl[31] = 49 ^ 43;
        ObsidianDefenders.lIllIllIl[32] = 71 + 141 - 195 + 128 ^ 0 + 0 - -74 + 64;
        ObsidianDefenders.lIllIllIl[33] = 147 ^ 187 ^ (112 ^ 68);
        ObsidianDefenders.lIllIllIl[34] = 151 + 8 - 134 + 165 ^ 129 + 152 - 118 + 0;
        ObsidianDefenders.lIllIllIl[35] = 110 ^ 49;
        ObsidianDefenders.lIllIllIl[36] = 174 ^ 178 ^ "  ".length();
        ObsidianDefenders.lIllIllIl[37] = 44 ^ 51;
        ObsidianDefenders.lIllIllIl[38] = 123 ^ 9 ^ (29 ^ 79);
        ObsidianDefenders.lIllIllIl[39] = 61 + 44 - 41 + 78 ^ 149 + 155 - 213 + 84;
        ObsidianDefenders.lIllIllIl[40] = 76 + 36 - 69 + 100 ^ 86 + 147 - 68 + 8;
        ObsidianDefenders.lIllIllIl[41] = 201 ^ 192 ^ (237 ^ 199);
        ObsidianDefenders.lIllIllIl[42] = 135 ^ 163;
        ObsidianDefenders.lIllIllIl[43] = 35 ^ 6;
        ObsidianDefenders.lIllIllIl[44] = 194 + 136 - 118 + 17 ^ 77 + 27 - 98 + 189;
        ObsidianDefenders.lIllIllIl[45] = 168 + 140 - 160 + 43 ^ 68 + 88 - 135 + 131;
        ObsidianDefenders.lIllIllIl[46] = "  ".length() ^ (160 ^ 139);
        ObsidianDefenders.lIllIllIl[47] = 41 ^ 0 ^ "   ".length();
        ObsidianDefenders.lIllIllIl[48] = "   ".length() ^ (166 ^ 142);
        ObsidianDefenders.lIllIllIl[49] = 82 ^ 95 ^ (16 ^ 49);
        ObsidianDefenders.lIllIllIl[50] = 5 ^ 43;
        ObsidianDefenders.lIllIllIl[51] = 178 ^ 157;
        ObsidianDefenders.lIllIllIl[52] = 60 ^ 12;
        ObsidianDefenders.lIllIllIl[53] = 11 + 16 - 16 + 139 ^ 5 + 73 - -70 + 19;
        ObsidianDefenders.lIllIllIl[54] = 121 ^ 74;
        ObsidianDefenders.lIllIllIl[55] = 20 + 17 - -53 + 98 ^ 41 + 83 - -5 + 7;
        ObsidianDefenders.lIllIllIl[56] = 159 ^ 170;
        ObsidianDefenders.lIllIllIl[57] = 2 ^ 117 ^ (215 ^ 150);
        ObsidianDefenders.lIllIllIl[58] = 124 + 31 - 78 + 94 ^ 63 + 130 - 170 + 133;
        ObsidianDefenders.lIllIllIl[59] = 76 ^ 116;
        ObsidianDefenders.lIllIllIl[60] = 99 ^ 49 ^ (173 ^ 198);
        ObsidianDefenders.lIllIllIl[61] = 69 ^ 127;
        ObsidianDefenders.lIllIllIl[62] = 207 ^ 157 ^ (40 ^ 65);
        ObsidianDefenders.lIllIllIl[63] = 96 ^ 114 ^ (132 ^ 170);
        ObsidianDefenders.lIllIllIl[64] = 63 ^ 2;
        ObsidianDefenders.lIllIllIl[65] = -(-2433 & 11247) & (-23553 & 32766);
        ObsidianDefenders.lIllIllIl[66] = 47 + 68 - 106 + 129 ^ 150 + 34 - 121 + 117;
        ObsidianDefenders.lIllIllIl[67] = 124 + 63 - 123 + 92 ^ 135 + 143 - 136 + 21;
        ObsidianDefenders.lIllIllIl[68] = 15 ^ 106 ^ (141 ^ 168);
        ObsidianDefenders.lIllIllIl[69] = 85 + 48 - 0 + 2 ^ 93 + 5 - -57 + 43;
        ObsidianDefenders.lIllIllIl[70] = 142 ^ 186 ^ (107 ^ 29);
        ObsidianDefenders.lIllIllIl[71] = -15955 & 16254;
        ObsidianDefenders.lIllIllIl[72] = 133 ^ 198;
        ObsidianDefenders.lIllIllIl[73] = (96 ^ 94) + (57 ^ 31) - (229 ^ 187) + (93 + 97 - 51 + 15);
        ObsidianDefenders.lIllIllIl[74] = 70 ^ 2;
        ObsidianDefenders.lIllIllIl[75] = -10755 & 11254;
        ObsidianDefenders.lIllIllIl[76] = 77 ^ 73 ^ (231 ^ 166);
        ObsidianDefenders.lIllIllIl[77] = 87 + 83 - 99 + 119 ^ 116 + 131 - 137 + 88;
        ObsidianDefenders.lIllIllIl[78] = 59 ^ 95;
        ObsidianDefenders.lIllIllIl[79] = 76 ^ 10;
        ObsidianDefenders.lIllIllIl[80] = 51 ^ 116;
        ObsidianDefenders.lIllIllIl[81] = 175 ^ 194 ^ (166 ^ 131);
        ObsidianDefenders.lIllIllIl[82] = 136 ^ 193;
        ObsidianDefenders.lIllIllIl[83] = 57 ^ 93 ^ (137 ^ 167);
        ObsidianDefenders.lIllIllIl[84] = 41 ^ 93 ^ (166 ^ 153);
        ObsidianDefenders.lIllIllIl[85] = 72 + 39 - -10 + 20 ^ 94 + 116 - 198 + 181;
        ObsidianDefenders.lIllIllIl[86] = 70 ^ 11;
        ObsidianDefenders.lIllIllIl[87] = 13 ^ 67;
        ObsidianDefenders.lIllIllIl[88] = 116 ^ 59;
        ObsidianDefenders.lIllIllIl[89] = 13 ^ 54 ^ (57 ^ 83);
        ObsidianDefenders.lIllIllIl[90] = 195 ^ 170 ^ (88 ^ 99);
        ObsidianDefenders.lIllIllIl[91] = 47 ^ 124;
        ObsidianDefenders.lIllIllIl[92] = 194 ^ 172 ^ (153 ^ 163);
        ObsidianDefenders.lIllIllIl[93] = 86 + 14 - 34 + 139 ^ 116 + 65 - 172 + 143;
        ObsidianDefenders.lIllIllIl[94] = 13 ^ 91;
        ObsidianDefenders.lIllIllIl[95] = 212 ^ 131;
        ObsidianDefenders.lIllIllIl[96] = 102 ^ 62;
        ObsidianDefenders.lIllIllIl[97] = 44 ^ 117;
        ObsidianDefenders.lIllIllIl[98] = 252 ^ 166;
        ObsidianDefenders.lIllIllIl[99] = 88 ^ 112 ^ (204 ^ 191);
        ObsidianDefenders.lIllIllIl[100] = 49 ^ 109;
        ObsidianDefenders.lIllIllIl[101] = 3 ^ 94;
        ObsidianDefenders.lIllIllIl[102] = 109 + 88 - 38 + 84 ^ 104 + 135 - 213 + 147;
        ObsidianDefenders.lIllIllIl[103] = 231 ^ 135;
        ObsidianDefenders.lIllIllIl[104] = 101 + 230 - 207 + 107 ^ 53 + 67 - 102 + 116;
        ObsidianDefenders.lIllIllIl[105] = 46 ^ 76;
        ObsidianDefenders.lIllIllIl[106] = 205 ^ 183 ^ (170 ^ 179);
        ObsidianDefenders.lIllIllIl[107] = 49 ^ 125 ^ (173 ^ 132);
    }

    private static int lIlIlIlllIl(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean lIlIlIlIlII(int n) {
        return n < 0;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIIIlIIIlllIIl) {
        ObsidianDefenders lIIIlIIIlllIlI;
        ArrayList<ItemStack> lIIIlIIIlllIII = new ArrayList<ItemStack>();
        JocEquips.Equip lIIIlIIIllIlll = lIIIlIIIlllIlI.obtenirEquip(lIIIlIIIlllIIl);
        "".length();
        lIIIlIIIlllIII.add(new ItemStack(Material.WOOD_SWORD, lIllIllIl[1]));
        "".length();
        lIIIlIIIlllIII.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, lIIIlIIIllIlll));
        "".length();
        lIIIlIIIlllIII.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, lIIIlIIIllIlll));
        "".length();
        lIIIlIIIlllIII.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, lIIIlIIIllIlll));
        "".length();
        lIIIlIIIlllIII.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, lIIIlIIIllIlll));
        "".length();
        lIIIlIIIlllIII.add(new ItemStack(Material.ARROW, lIllIllIl[1]));
        return lIIIlIIIlllIII;
    }

    @Override
    protected void setCustomGameRules() {
    }

    public void donarOr(Player lIIIIlllIIllIl, int lIIIIlllIIlIII) {
        ObsidianDefenders lIIIIlllIIlIlI;
        ItemStack lIIIIlllIIlIll = new ItemStack(Material.GOLD_NUGGET, lIIIIlllIIlIII);
        Utils.giveItemStack(lIIIIlllIIlIll, lIIIIlllIIllIl);
        lIIIIlllIIlIlI.pPlayer(lIIIIlllIIllIl).IncrementarPropietat(lIlIlIllI[lIllIllIl[27]], lIIIIlllIIlIII);
        lIIIIlllIIlIlI.ajuntarOr(lIIIIlllIIllIl);
        lIIIIlllIIlIlI.updateScoreBoard(lIIIIlllIIllIl);
    }

    private static boolean lIlIlIIlllI(int n, int n2) {
        return n == n2;
    }

    private static boolean lIlIlIIlIlI(int n) {
        return n == 0;
    }

    public static class Ability {
        private static final /* synthetic */ int[] lIlIIIIl;
        private static final /* synthetic */ String[] lIIllIll;

        public static void openSelectionInventory(lobby lIlIIlIIlIIllII, ObsidianDefenders lIlIIlIIlIlIIIl, Player lIlIIlIIlIIlIlI) {
            World lIlIIlIIlIIllll = (World)Bukkit.getServer().getWorlds().get(lIlIIIIl[0]);
            Inventory lIlIIlIIlIIlllI = Bukkit.getServer().createInventory((InventoryHolder)lIlIIlIIlIIlIlI, lIlIIIIl[18], lIIllIll[lIlIIIIl[44]]);
            int lIlIIlIIlIIllIl = lIlIIIIl[0];
            AbilityType[] lIlIIlIIlIIIllI = AbilityType.values();
            int lIlIIlIIlIIIlIl = lIlIIlIIlIIIllI.length;
            int lIlIIlIIlIIIlII = lIlIIIIl[0];
            while (Ability.lIlIIIlIII(lIlIIlIIlIIIlII, lIlIIlIIlIIIlIl)) {
                AbilityType lIlIIlIIlIlIIll = lIlIIlIIlIIIllI[lIlIIlIIlIIIlII];
                lIlIIlIIlIIlllI.setItem(lIlIIlIIlIIllIl, Ability.Icona(lIlIIlIIlIIllII, lIlIIlIIlIlIIIl, lIlIIlIIlIIlIlI, lIlIIlIIlIlIIll));
                ++lIlIIlIIlIIllIl;
                ++lIlIIlIIlIIIlII;
                "".length();
                if (null == null) continue;
                return;
            }
            "".length();
            lIlIIlIIlIIlIlI.openInventory(lIlIIlIIlIIlllI);
        }

        public static boolean hasAbility(lobby lIlIIlIIIlllIlI, ObsidianDefenders lIlIIlIIIllllIl, Player lIlIIlIIIllllII, AbilityType lIlIIlIIIlllIll) {
            return Ability.getPlayerAbilityTypes(lIlIIlIIIlllIlI, lIlIIlIIIllllIl, lIlIIlIIIllllII).contains((Object)lIlIIlIIIlllIll);
        }

        public static void setAbility(lobby lIlIIIlllllllll, ObsidianDefenders lIlIIIllllllllI, Player lIlIIIllllllIIl, AbilityType lIlIIIlllllllII, int lIlIIIlllllIlll) {
            lIlIIIllllllllI.pPlayer(lIlIIIllllllIIl).EstablirPropietat(String.valueOf(new StringBuilder().append(lIIllIll[lIlIIIIl[47]]).append(Integer.toString(lIlIIIlllllIlll))), lIlIIIlllllllII.name());
        }

        private static void lIIlllIllI() {
            lIIllIll = new String[lIlIIIIl[49]];
            Ability.lIIllIll[Ability.lIlIIIIl[0]] = Ability.lIIlIIllIl("V3gd4YwYPqo=", "tgfzl");
            Ability.lIIllIll[Ability.lIlIIIIl[1]] = Ability.lIIlIIlllI("UxYBEhAdOxQCGsKcbA==", "oRdas");
            Ability.lIIllIll[Ability.lIlIIIIl[2]] = Ability.lIIlIIllIl("Ppk7We8rKF6YhvzlGc3Teg==", "BKFZn");
            Ability.lIIllIll[Ability.lIlIIIIl[3]] = Ability.lIIlIIlllI("NzQzCT0TMzvChQ==", "rGChY");
            Ability.lIIllIll[Ability.lIlIIIIl[4]] = Ability.lIIlIIlllI("IAMFIBYPAgNtH0YXFiwQQRUDKRJBQEIuHBEFQilUBAUSLAAA", "avbMs");
            Ability.lIIllIll[Ability.lIlIIIIl[5]] = Ability.lIIlIIllll("qsg/vWm/k4lbJsvxzBjoBnZmWuJ+cZVAssG5tygcmDk=", "owavv");
            Ability.lIIllIll[Ability.lIlIIIIl[6]] = Ability.lIIlIIlllI("CAs0Hwk/HDIZDsKpTjIPADcLPQ4GPg8=", "ZnSzg");
            Ability.lIIllIll[Ability.lIlIIIIl[7]] = Ability.lIIlIIllIl("7w3mEQqTmu7RUKs6J7cirNc7iNuWREzfM1W0pUFDm5wNH8TVBzjYWomA+IxDScjL", "iDKnK");
            Ability.lIIllIll[Ability.lIlIIIIl[8]] = Ability.lIIlIIllll("sFrdTuZ2vjwcrtQUANTg7A==", "uuysS");
            Ability.lIIllIll[Ability.lIlIIIIl[9]] = Ability.lIIlIIllIl("q3Xq1jKd4aza+MJOzMS9GhagEugHwQeoOUMVkwvePTQ=", "UorkZ");
            Ability.lIIllIll[Ability.lIlIIIIl[10]] = Ability.lIIlIIlllI("VVxfb1JEVF9vCQEeWioXAQETLFkUHhU/HBZMUndZBgAVLApN", "dlzOy");
            Ability.lIIllIll[Ability.lIlIIIIl[11]] = Ability.lIIlIIlllI("MwI4ASAAUDkRNxQVKgAg", "rpItE");
            Ability.lIIllIll[Ability.lIlIIIIl[12]] = Ability.lIIlIIllll("YwpCAF6O0OyyI/m6AVeDFlYuVEE/hDaZJWk7t5+XgKk=", "cOjOf");
            Ability.lIIllIll[Ability.lIlIIIIl[13]] = Ability.lIIlIIlllI("PChWNzg1PQ4wdCIsFD4gMWleaXQyJRkyJ3k=", "PIvQT");
            Ability.lIIllIll[Ability.lIlIIIIl[14]] = Ability.lIIlIIllIl("xOa06xFJ5MQ=", "bUmix");
            Ability.lIIllIll[Ability.lIlIIIIl[15]] = Ability.lIIlIIlllI("HAZLGgY1ShsSFXkJCh4ALA4KVwIqSh8FBjcZDRIVPAMT", "Yjkwg");
            Ability.lIIllIll[Ability.lIlIIIIl[16]] = Ability.lIIlIIllll("kET5XIBpTiAFXLi+mcTHMKVR0lxCCFD43WvcYWhJT+E=", "YgnSm");
            Ability.lIIllIll[Ability.lIlIIIIl[17]] = Ability.lIIlIIllIl("UAEOjYbkHphaissI6wmkfw==", "cLgwN");
            Ability.lIIllIll[Ability.lIlIIIIl[18]] = Ability.lIIlIIllIl("xD/ebcSh9Q7mD4h3HwN/TBu9+gej/r8v", "uHwcB");
            Ability.lIIllIll[Ability.lIlIIIIl[19]] = Ability.lIIlIIllll("gcH+XVtU1y1pVZ+T9lZ94w==", "KnZap");
            Ability.lIIllIll[Ability.lIlIIIIl[20]] = Ability.lIIlIIllll("0OObwF/kB7x+yDMbfuHWbppXEzcUPV+QOk4WxtETff4=", "FFHKq");
            Ability.lIIllIll[Ability.lIlIIIIl[21]] = Ability.lIIlIIllll("1ohsGNy23lSz9TjHX7cExiqBgeMS3y2F", "wJjWd");
            Ability.lIIllIll[Ability.lIlIIIIl[22]] = Ability.lIIlIIllIl("TyuZILzIcS00hSkGfuHdvvIinVfL3cPZ0COfBqySA7TGMbNE4O3VVg==", "piQKJ");
            Ability.lIIllIll[Ability.lIlIIIIl[23]] = Ability.lIIlIIllll("FfHWDe2Fs/NnkfjoCPbjgAilBHU5bBgK", "vSLYQ");
            Ability.lIIllIll[Ability.lIlIIIIl[24]] = Ability.lIIlIIllIl("M88GvQo8HJypQ7aOja6O6aaubRtbbh/Ch8k0JSru3wE=", "umHzW");
            Ability.lIIllIll[Ability.lIlIIIIl[25]] = Ability.lIIlIIlllI("BjkcHzMAJFkTLgI0GAYpQyUcET8NIxQXNBd3UUNqEH4=", "cWyrZ");
            Ability.lIIllIll[Ability.lIlIIIIl[26]] = Ability.lIIlIIlllI("HyYjDj4iKSIIODc8", "VHNaL");
            Ability.lIIllIll[Ability.lIlIIIIl[27]] = Ability.lIIlIIlllI("NjUYaRkdLAQ7BBItSyBQFDQKJwkWMg==", "sAkIp");
            Ability.lIIllIll[Ability.lIlIIIIl[28]] = Ability.lIIlIIllIl("eweVzZegc4hmlEbDsaYUydxi7rJtuwIfLfYdR44O24Q=", "rXKgf");
            Ability.lIIllIll[Ability.lIlIIIIl[29]] = Ability.lIIlIIllIl("QDFTs/3Aem3ip58SWQf3gw==", "MXZRW");
            Ability.lIIllIll[Ability.lIlIIIIl[30]] = Ability.lIIlIIllIl("UlnJcA+nAjjkfEufaGk3exWdCt1zWrDkAIwIUjAHkoQ=", "gaXEu");
            Ability.lIIllIll[Ability.lIlIIIIl[31]] = Ability.lIIlIIlllI("AgYtD2JWRy8CJxUfLB0=", "agInB");
            Ability.lIIllIll[Ability.lIlIIIIl[32]] = Ability.lIIlIIllIl("SUQ6ZLzeycUFfgL9r3nPLQ==", "CmNCW");
            Ability.lIIllIll[Ability.lIlIIIIl[33]] = Ability.lIIlIIllll("7F+Q2qkcEKDw7te/LhTvjuPzCSrnT5/ZWRwVnrMtO18=", "XWCjZ");
            Ability.lIIllIll[Ability.lIlIIIIl[34]] = Ability.lIIlIIlllI("ICgYLSY0KBA/YiU/VSBlJDQQISsiel15Ymp6GCMwNSlc", "AZuLB");
            Ability.lIIllIll[Ability.lIlIIIIl[35]] = Ability.lIIlIIllll("V057/yHsiXGJReXnznLqBw==", "jKmLx");
            Ability.lIIllIll[Ability.lIlIIIIl[36]] = Ability.lIIlIIlllI("FRYLJxw6Fw1qHDhDASsVdAcJJgp0AgAjGCAQTDoLOxMJOAp0FgI=", "TclJy");
            Ability.lIIllIll[Ability.lIlIIIIl[37]] = Ability.lIIlIIlllI("ZEdKdAt1FgA5BzvCkg50AzgXTz0WMBgcdAMxEQY3CzobDjgR", "UuoTb");
            Ability.lIIllIll[Ability.lIlIIIIl[38]] = Ability.lIIlIIllIl("g+9ri1pBYds=", "YonGO");
            Ability.lIIllIll[Ability.lIlIIIIl[39]] = Ability.lIIlIIlllI("HR0jCgEsACBGDzRFPgkcMRd7VkBgI3pIThQEcwABKsKCMkYPLQI+AwAsBA==", "XeSfn");
            Ability.lIIllIll[Ability.lIlIIIIl[40]] = Ability.lIIlIIlllI("Q0J7JGEDCTxCIhIIL0IkHQkjCyJTHTsHYRsNKQsyUwEvFiAHQg==", "slNbA");
            Ability.lIIllIll[Ability.lIlIIIIl[41]] = Ability.lIIlIIlllI("STAEORMHHREpGcKGRl8=", "utaJp");
            Ability.lIIllIll[Ability.lIlIIIIl[42]] = Ability.lIIlIIllll("eVY70JuDbT0KGK6mF+lQ0Q==", "SXtxq");
            Ability.lIIllIll[Ability.lIlIIIIl[43]] = Ability.lIIlIIlllI("HSFzHw89LToWFDI=", "SNSyz");
            Ability.lIIllIll[Ability.lIlIIIIl[44]] = Ability.lIIlIIllll("xgZJZh8tB8kEzpdOFRdNAweU7icnU/0z", "knVxq");
            Ability.lIIllIll[Ability.lIlIIIIl[45]] = Ability.lIIlIIlllI("BgIYOgQnFxsnWQ==", "NczSh");
            Ability.lIIllIll[Ability.lIlIIIIl[46]] = Ability.lIIlIIlllI("ATcqIzUgIik+aw==", "IVHJY");
            Ability.lIIllIll[Ability.lIlIIIIl[47]] = Ability.lIIlIIlllI("PCYFMycdMwYu", "tGgZK");
            Ability.lIIllIll[Ability.lIlIIIIl[48]] = Ability.lIIlIIllll("LuBbq2m6r0KgAjZ9dRWQ9A==", "KBmjv");
        }

        private static String lIIlIIllll(String lIlIIIlllIllIIl, String lIlIIIlllIllIlI) {
            try {
                SecretKeySpec lIlIIIlllIllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIIIlllIllIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIlIIIlllIlllIl = Cipher.getInstance("Blowfish");
                lIlIIIlllIlllIl.init(lIlIIIIl[2], lIlIIIlllIllllI);
                return new String(lIlIIIlllIlllIl.doFinal(Base64.getDecoder().decode(lIlIIIlllIllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIIIlllIlllII) {
                lIlIIIlllIlllII.printStackTrace();
                return null;
            }
        }

        static ItemStack Icona(lobby lIlIIlIIllIlIIl, ObsidianDefenders lIlIIlIIlllIlII, Player lIlIIlIIllIIlll, AbilityType lIlIIlIIlllIIlI) {
            Material lIlIIlIIlllIIIl = Material.WOOD;
            String lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[0]];
            String lIlIIlIIllIllll = lIIllIll[lIlIIIIl[1]];
            String lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[2]];
            int lIlIIlIIllIllIl = lIlIIIIl[0];
            switch (1.$SwitchMap$com$biel$lobby$mapes$jocs$ObsidianDefenders$Ability$AbilityType[lIlIIlIIlllIIlI.ordinal()]) {
                case 1: {
                    lIlIIlIIlllIIIl = Material.IRON_SWORD;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[3]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[4]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[5]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if (" ".length() == " ".length()) break;
                    return null;
                }
                case 2: {
                    lIlIIlIIlllIIIl = Material.SAPLING;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[6]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[7]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if (((166 + 200 - 296 + 139 ^ 63 + 28 - -13 + 40) & (54 ^ 87 ^ (8 ^ 40) ^ -" ".length())) != (249 ^ 191 ^ (65 ^ 3))) break;
                    return null;
                }
                case 3: {
                    lIlIIlIIlllIIIl = Material.DIAMOND;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[8]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[9]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[10]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if (((7 ^ 79) & ~(13 ^ 69)) < (107 ^ 111)) break;
                    return null;
                }
                case 4: {
                    lIlIIlIIlllIIIl = Material.BOW;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[11]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[12]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[13]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if ((53 ^ 49) >= 0) break;
                    return null;
                }
                case 5: {
                    lIlIIlIIlllIIIl = Material.LEATHER_BOOTS;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[14]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[15]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[16]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if (-" ".length() < (180 + 68 - 73 + 13 ^ 133 + 167 - 217 + 101)) break;
                    return null;
                }
                case 6: {
                    lIlIIlIIlllIIIl = Material.BONE;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[17]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[18]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if ((92 ^ 88) > "   ".length()) break;
                    return null;
                }
                case 7: {
                    lIlIIlIIlllIIIl = Material.FIREWORK;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[19]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[20]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if (-" ".length() <= "   ".length()) break;
                    return null;
                }
                case 8: {
                    lIlIIlIIlllIIIl = Material.IRON_CHESTPLATE;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[21]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[22]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if (" ".length() < "  ".length()) break;
                    return null;
                }
                case 9: {
                    lIlIIlIIlllIIIl = Material.GOLD_BOOTS;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[23]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[24]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[25]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if (((33 ^ 115) & ~(15 ^ 93)) <= 0) break;
                    return null;
                }
                case 10: {
                    lIlIIlIIlllIIIl = Material.BEDROCK;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[26]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[27]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[28]];
                    lIlIIlIIllIllIl = lIlIIIIl[0];
                    "".length();
                    if ("   ".length() >= 0) break;
                    return null;
                }
                case 11: {
                    lIlIIlIIlllIIIl = Material.ICE;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[29]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[30]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[31]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if (null == null) break;
                    return null;
                }
                case 12: {
                    lIlIIlIIlllIIIl = Material.DIAMOND_AXE;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[32]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[33]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[34]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if ("  ".length() <= "  ".length()) break;
                    return null;
                }
                case 13: {
                    lIlIIlIIlllIIIl = Material.COMMAND;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[35]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[36]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[37]];
                    lIlIIlIIllIllIl = lIlIIIIl[0];
                    "".length();
                    if (-(1 ^ 110 ^ (22 ^ 124)) < 0) break;
                    return null;
                }
                case 14: {
                    lIlIIlIIlllIIIl = Material.TNT;
                    lIlIIlIIlllIIII = lIIllIll[lIlIIIIl[38]];
                    lIlIIlIIllIllll = lIIllIll[lIlIIIIl[39]];
                    lIlIIlIIllIlllI = lIIllIll[lIlIIIIl[40]];
                    lIlIIlIIllIllIl = lIlIIIIl[1];
                    "".length();
                    if ("   ".length() > -" ".length()) break;
                    return null;
                }
            }
            ItemStack lIlIIlIIllIllII = new ItemStack(lIlIIlIIlllIIIl);
            ItemMeta lIlIIlIIllIlIll = lIlIIlIIllIllII.getItemMeta();
            lIlIIlIIllIlIll.setDisplayName(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIlIIlIIlllIIII)));
            ArrayList<String> lIlIIlIIllIlIlI = new ArrayList<String>();
            "".length();
            lIlIIlIIllIlIlI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIlIIlIIllIllll)));
            if (Ability.lIlIIIIllI((int)lIlIIlIIllIlllI.equals(lIIllIll[lIlIIIIl[41]]))) {
                "".length();
                lIlIIlIIllIlIlI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIlIIlIIllIlllI)));
            }
            if (Ability.lIlIIIIlll((int)Ability.hasAbility(lIlIIlIIllIlIIl, lIlIIlIIlllIlII, lIlIIlIIllIIlll, lIlIIlIIlllIIlI))) {
                "".length();
                lIlIIlIIllIlIlI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIllIll[lIlIIIIl[42]])));
                lIlIIlIIllIllII.setAmount(lIlIIIIl[2]);
            }
            if (Ability.lIlIIIIllI(lIlIIlIIllIllIl)) {
                "".length();
                lIlIIlIIllIlIlI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_RED).append(lIIllIll[lIlIIIIl[43]])));
            }
            lIlIIlIIllIlIll.setLore(lIlIIlIIllIlIlI);
            "".length();
            lIlIIlIIllIllII.setItemMeta(lIlIIlIIllIlIll);
            return lIlIIlIIllIllII;
        }

        private static boolean lIlIIIIllI(int n) {
            return n == 0;
        }

        public static void randomAbilities(lobby lIlIIlIIIIIllII, ObsidianDefenders lIlIIlIIIIlIIII, Player lIlIIlIIIIIllll) {
            int lIlIIlIIIIIllIl = lIlIIIIl[1];
            while (Ability.lIlIIIlIIl(lIlIIlIIIIIllIl, lIlIIIIl[2])) {
                int lIlIIlIIIIlIlII = lIlIIIIl[0];
                while (Ability.lIlIIIIllI(lIlIIlIIIIlIlII)) {
                    AbilityType[] lIlIIlIIIIIIlll = AbilityType.values();
                    int lIlIIlIIIIIIllI = lIlIIlIIIIIIlll.length;
                    int lIlIIlIIIIIIlIl = lIlIIIIl[0];
                    while (Ability.lIlIIIlIII(lIlIIlIIIIIIlIl, lIlIIlIIIIIIllI)) {
                        AbilityType lIlIIlIIIIlIlIl = lIlIIlIIIIIIlll[lIlIIlIIIIIIlIl];
                        if (Ability.lIlIIIIlll((int)Utils.Possibilitat(lIlIIIIl[10]))) {
                            Ability.setAbility(lIlIIlIIIIIllII, lIlIIlIIIIlIIII, lIlIIlIIIIIllll, lIlIIlIIIIlIlIl, lIlIIlIIIIIllIl);
                            lIlIIlIIIIlIlII = lIlIIIIl[1];
                        }
                        ++lIlIIlIIIIIIlIl;
                        "".length();
                        if (" ".length() != "  ".length()) continue;
                        return;
                    }
                    "".length();
                    if (-" ".length() < " ".length()) continue;
                    return;
                }
                ++lIlIIlIIIIIllIl;
                "".length();
                if ("  ".length() == "  ".length()) continue;
                return;
            }
        }

        private static String lIIlIIlllI(String lIlIIIllIlllIIl, String lIlIIIllIllllIl) {
            lIlIIIllIlllIIl = new String(Base64.getDecoder().decode(lIlIIIllIlllIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIlIIIllIllllII = new StringBuilder();
            char[] lIlIIIllIlllIll = lIlIIIllIllllIl.toCharArray();
            int lIlIIIllIlllIlI = lIlIIIIl[0];
            char[] lIlIIIllIllIlII = lIlIIIllIlllIIl.toCharArray();
            int lIlIIIllIllIIll = lIlIIIllIllIlII.length;
            int lIlIIIllIllIIlI = lIlIIIIl[0];
            while (Ability.lIlIIIlIII(lIlIIIllIllIIlI, lIlIIIllIllIIll)) {
                char lIlIIIllIllllll = lIlIIIllIllIlII[lIlIIIllIllIIlI];
                "".length();
                lIlIIIllIllllII.append((char)(lIlIIIllIllllll ^ lIlIIIllIlllIll[lIlIIIllIlllIlI % lIlIIIllIlllIll.length]));
                ++lIlIIIllIlllIlI;
                ++lIlIIIllIllIIlI;
                "".length();
                if (-(38 ^ 20 ^ (129 ^ 183)) < 0) continue;
                return null;
            }
            return String.valueOf(lIlIIIllIllllII);
        }

        static ArrayList<AbilityType> getPlayerAbilityTypes(lobby lIlIIlIIIlIlIII, ObsidianDefenders lIlIIlIIIlIIlll, Player lIlIIlIIIlIlIll) {
            ArrayList<AbilityType> lIlIIlIIIlIlIlI;
            lIlIIlIIIlIlIlI = new ArrayList<AbilityType>();
            try {
                "".length();
                lIlIIlIIIlIlIlI.add(AbilityType.valueOf(lIlIIlIIIlIIlll.pPlayer(lIlIIlIIIlIlIll).ObtenirPropietat(lIIllIll[lIlIIIIl[45]])));
                "".length();
                lIlIIlIIIlIlIlI.add(AbilityType.valueOf(lIlIIlIIIlIIlll.pPlayer(lIlIIlIIIlIlIll).ObtenirPropietat(lIIllIll[lIlIIIIl[46]])));
                "".length();
            }
            catch (Exception lIlIIlIIIllIIII) {
                Ability.randomAbilities(lIlIIlIIIlIlIII, lIlIIlIIIlIIlll, lIlIIlIIIlIlIll);
                return Ability.getPlayerAbilityTypes(lIlIIlIIIlIlIII, lIlIIlIIIlIIlll, lIlIIlIIIlIlIll);
            }
            if (((147 ^ 129 ^ (108 ^ 31)) & (74 ^ 50 ^ (87 ^ 78) ^ -" ".length())) != 0) {
                return null;
            }
            return lIlIIlIIIlIlIlI;
        }

        private static void lIIlllllIl() {
            lIlIIIIl = new int[50];
            Ability.lIlIIIIl[0] = (124 ^ 116 ^ (34 ^ 10)) & (125 + 119 - 158 + 48 ^ 149 + 130 - 138 + 25 ^ -" ".length());
            Ability.lIlIIIIl[1] = " ".length();
            Ability.lIlIIIIl[2] = "  ".length();
            Ability.lIlIIIIl[3] = "   ".length();
            Ability.lIlIIIIl[4] = 130 ^ 134;
            Ability.lIlIIIIl[5] = 106 ^ 111;
            Ability.lIlIIIIl[6] = 248 ^ 146 ^ (78 ^ 34);
            Ability.lIlIIIIl[7] = 119 ^ 112;
            Ability.lIlIIIIl[8] = 127 ^ 119;
            Ability.lIlIIIIl[9] = 105 ^ 96;
            Ability.lIlIIIIl[10] = 121 ^ 44 ^ (117 ^ 42);
            Ability.lIlIIIIl[11] = 101 ^ 28 ^ (251 ^ 137);
            Ability.lIlIIIIl[12] = 116 + 133 - 202 + 119 ^ 76 + 2 - -35 + 57;
            Ability.lIlIIIIl[13] = 89 + 30 - 97 + 152 ^ 48 + 113 - 36 + 38;
            Ability.lIlIIIIl[14] = 99 + 89 - 155 + 110 ^ 91 + 76 - 59 + 21;
            Ability.lIlIIIIl[15] = 71 ^ 72;
            Ability.lIlIIIIl[16] = 231 ^ 181 ^ (235 ^ 169);
            Ability.lIlIIIIl[17] = 36 ^ 53;
            Ability.lIlIIIIl[18] = 39 + 67 - -18 + 42 ^ 153 + 78 - 219 + 168;
            Ability.lIlIIIIl[19] = 185 ^ 193 ^ (236 ^ 135);
            Ability.lIlIIIIl[20] = 113 ^ 101;
            Ability.lIlIIIIl[21] = 88 ^ 77;
            Ability.lIlIIIIl[22] = 185 ^ 175;
            Ability.lIlIIIIl[23] = 43 ^ 60;
            Ability.lIlIIIIl[24] = 74 ^ 80 ^ "  ".length();
            Ability.lIlIIIIl[25] = 181 ^ 169 ^ (41 ^ 44);
            Ability.lIlIIIIl[26] = 35 ^ 57;
            Ability.lIlIIIIl[27] = 16 ^ 11;
            Ability.lIlIIIIl[28] = 59 ^ 103 ^ (232 ^ 168);
            Ability.lIlIIIIl[29] = 223 ^ 194;
            Ability.lIlIIIIl[30] = 216 ^ 141 ^ (82 ^ 25);
            Ability.lIlIIIIl[31] = 42 + 44 - 59 + 107 ^ 143 + 71 - 151 + 90;
            Ability.lIlIIIIl[32] = 13 ^ 45;
            Ability.lIlIIIIl[33] = 0 ^ 33;
            Ability.lIlIIIIl[34] = 71 + 53 - 84 + 102 ^ 31 + 147 - 42 + 36;
            Ability.lIlIIIIl[35] = 119 ^ 84;
            Ability.lIlIIIIl[36] = 76 ^ 104;
            Ability.lIlIIIIl[37] = 235 ^ 179 ^ (198 ^ 187);
            Ability.lIlIIIIl[38] = 169 ^ 138 ^ (142 ^ 139);
            Ability.lIlIIIIl[39] = 66 ^ 101;
            Ability.lIlIIIIl[40] = 130 ^ 170;
            Ability.lIlIIIIl[41] = 33 ^ 8;
            Ability.lIlIIIIl[42] = 75 + 51 - 54 + 55 ^ (194 ^ 151);
            Ability.lIlIIIIl[43] = 13 ^ 118 ^ (22 ^ 70);
            Ability.lIlIIIIl[44] = 79 ^ 67 ^ (130 ^ 162);
            Ability.lIlIIIIl[45] = 37 ^ 8;
            Ability.lIlIIIIl[46] = 46 ^ 21 ^ (9 ^ 28);
            Ability.lIlIIIIl[47] = 83 ^ 124;
            Ability.lIlIIIIl[48] = 12 ^ 43 ^ (214 ^ 193);
            Ability.lIlIIIIl[49] = 78 + 55 - 28 + 37 ^ 19 + 130 - -21 + 21;
        }

        private static boolean lIlIIIlIII(int n, int n2) {
            return n < n2;
        }

        public Ability() {
            Ability lIlIIlIlIIIIIll;
        }

        static {
            Ability.lIIlllllIl();
            Ability.lIIlllIllI();
        }

        private static boolean lIlIIIIlll(int n) {
            return n != 0;
        }

        private static boolean lIlIIIlIIl(int n, int n2) {
            return n <= n2;
        }

        public static void giveSelectors(lobby lIlIIIllllIlIlI, Player lIlIIIllllIlIIl) {
            int lIlIIIllllIlIII = lIlIIIIl[1];
            while (Ability.lIlIIIlIIl(lIlIIIllllIlIII, lIlIIIIl[2])) {
                ItemStack lIlIIIllllIllIl = new ItemStack(Material.WOOD_SWORD);
                ItemMeta lIlIIIllllIllII = lIlIIIllllIllIl.getItemMeta();
                lIlIIIllllIllII.setDisplayName(String.valueOf(new StringBuilder().append(lIIllIll[lIlIIIIl[48]]).append(Integer.toString(lIlIIIllllIlIII))));
                ArrayList<String> lIlIIIllllIlIll = new ArrayList<String>();
                "".length();
                lIlIIIllllIlIll.add(Integer.toString(lIlIIIllllIlIII));
                lIlIIIllllIllII.setLore(lIlIIIllllIlIll);
                "".length();
                lIlIIIllllIllIl.setItemMeta(lIlIIIllllIllII);
                lIlIIIllllIlIIl.getInventory().setItem(lIlIIIIl[4] + lIlIIIllllIlIII, lIlIIIllllIllIl);
                ++lIlIIIllllIlIII;
                "".length();
                if ("  ".length() >= ((156 ^ 131) & ~(17 ^ 14))) continue;
                return;
            }
        }

        private static String lIIlIIllIl(String lIlIIIlllIIllII, String lIlIIIlllIIllIl) {
            try {
                SecretKeySpec lIlIIIlllIlIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIIIlllIIllIl.getBytes(StandardCharsets.UTF_8)), lIlIIIIl[8]), "DES");
                Cipher lIlIIIlllIlIIII = Cipher.getInstance("DES");
                lIlIIIlllIlIIII.init(lIlIIIIl[2], lIlIIIlllIlIIIl);
                return new String(lIlIIIlllIlIIII.doFinal(Base64.getDecoder().decode(lIlIIIlllIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIlIIIlllIIllll) {
                lIlIIIlllIIllll.printStackTrace();
                return null;
            }
        }

        public static final class AbilityType
        extends Enum<AbilityType> {
            public static final /* synthetic */ /* enum */ AbilityType PIROT\u00c8CNIC;
            public static final /* synthetic */ /* enum */ AbilityType REGENERACIO_AUGMENTADA;
            public static final /* synthetic */ /* enum */ AbilityType PROTECCI\u00d3_IMPACTE;
            public static final /* synthetic */ /* enum */ AbilityType DESTRUCTOR;
            private static final /* synthetic */ int[] lIIllIIII;
            public static final /* synthetic */ /* enum */ AbilityType CREEPER;
            public static final /* synthetic */ /* enum */ AbilityType ESQUELET_FORT;
            public static final /* synthetic */ /* enum */ AbilityType ESPADATXI;
            public static final /* synthetic */ /* enum */ AbilityType RANDOM;
            private static final /* synthetic */ String[] lIIlIIlIl;
            public static final /* synthetic */ /* enum */ AbilityType ASSALT;
            private static final /* synthetic */ AbilityType[] $VALUES;
            public static final /* synthetic */ /* enum */ AbilityType ARQUER_PERFECTE;
            public static final /* synthetic */ /* enum */ AbilityType ARQUER_DE_GEL;
            public static final /* synthetic */ /* enum */ AbilityType CONTROL_GRAVETAT;
            public static final /* synthetic */ /* enum */ AbilityType RESISTENCIA;
            public static final /* synthetic */ /* enum */ AbilityType COMANDANT;

            private AbilityType() {
                AbilityType llIlIlIIlIIIIl;
            }

            static {
                AbilityType.lIIIlIllIll();
                AbilityType.lIIIlIIIIIl();
                RESISTENCIA = new AbilityType();
                COMANDANT = new AbilityType();
                ESPADATXI = new AbilityType();
                REGENERACIO_AUGMENTADA = new AbilityType();
                ASSALT = new AbilityType();
                CREEPER = new AbilityType();
                ARQUER_PERFECTE = new AbilityType();
                ARQUER_DE_GEL = new AbilityType();
                PROTECCI\u00d3_IMPACTE = new AbilityType();
                PIROT\u00c8CNIC = new AbilityType();
                ESQUELET_FORT = new AbilityType();
                CONTROL_GRAVETAT = new AbilityType();
                DESTRUCTOR = new AbilityType();
                RANDOM = new AbilityType();
                AbilityType[] arrabilityType = new AbilityType[lIIllIIII[14]];
                arrabilityType[AbilityType.lIIllIIII[0]] = RESISTENCIA;
                arrabilityType[AbilityType.lIIllIIII[1]] = COMANDANT;
                arrabilityType[AbilityType.lIIllIIII[2]] = ESPADATXI;
                arrabilityType[AbilityType.lIIllIIII[3]] = REGENERACIO_AUGMENTADA;
                arrabilityType[AbilityType.lIIllIIII[4]] = ASSALT;
                arrabilityType[AbilityType.lIIllIIII[5]] = CREEPER;
                arrabilityType[AbilityType.lIIllIIII[6]] = ARQUER_PERFECTE;
                arrabilityType[AbilityType.lIIllIIII[7]] = ARQUER_DE_GEL;
                arrabilityType[AbilityType.lIIllIIII[8]] = PROTECCI\u00d3_IMPACTE;
                arrabilityType[AbilityType.lIIllIIII[9]] = PIROT\u00c8CNIC;
                arrabilityType[AbilityType.lIIllIIII[10]] = ESQUELET_FORT;
                arrabilityType[AbilityType.lIIllIIII[11]] = CONTROL_GRAVETAT;
                arrabilityType[AbilityType.lIIllIIII[12]] = DESTRUCTOR;
                arrabilityType[AbilityType.lIIllIIII[13]] = RANDOM;
                $VALUES = arrabilityType;
            }

            private static String lIIIIlllIIl(String llIlIIlllIllll, String llIlIIlllIlllI) {
                try {
                    SecretKeySpec llIlIIllllIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlIIlllIlllI.getBytes(StandardCharsets.UTF_8)), lIIllIIII[8]), "DES");
                    Cipher llIlIIllllIIll = Cipher.getInstance("DES");
                    llIlIIllllIIll.init(lIIllIIII[2], llIlIIllllIlII);
                    return new String(llIlIIllllIIll.doFinal(Base64.getDecoder().decode(llIlIIlllIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception llIlIIllllIIlI) {
                    llIlIIllllIIlI.printStackTrace();
                    return null;
                }
            }

            private static boolean lIIIlIlllII(int n, int n2) {
                return n < n2;
            }

            public static AbilityType valueOf(String llIlIlIIlIIlIl) {
                return Enum.valueOf(AbilityType.class, llIlIlIIlIIlIl);
            }

            private static void lIIIlIIIIIl() {
                lIIlIIlIl = new String[lIIllIIII[14]];
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[0]] = AbilityType.lIIIIlllIII("cBQpbvtEA7gf6MZ7ddG17Q==", "gVZwT");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[1]] = AbilityType.lIIIIlllIIl("NkAE45L5Q2QFhrmkhedQQA==", "lhweo");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[2]] = AbilityType.lIIIIlllIII("UZtUYInUx2RxZuzETFYzXg==", "rRPuR");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[3]] = AbilityType.lIIIIlllIII("m5ULQ9YCXDPBYiIc48s4b/xxopliZZ6S", "fnnkr");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[4]] = AbilityType.lIIIIlllIII("L+AWZSqCTxM=", "IrxxU");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[5]] = AbilityType.lIIIIlllIll("ETUwNSEXNQ==", "Rgupq");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[6]] = AbilityType.lIIIIlllIII("v+WS6nyBHi54083HZFeHJg==", "HkQjG");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[7]] = AbilityType.lIIIIlllIll("MxsBEQ8gFhQBFTUMHA==", "rIPDJ");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[8]] = AbilityType.lIIIIlllIIl("NRSYc4mX9FlaoEnxlBL5MOXQgjjH737i", "fCHRj");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[9]] = AbilityType.lIIIIlllIll("AD0+JDvCmDciIiw=", "Ptlko");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[10]] = AbilityType.lIIIIlllIll("LgY+HBMnEDsWECQHOw==", "kUoIV");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[11]] = AbilityType.lIIIIlllIII("u8Gf3GX0nyyem7qA7IZmycAILee0yUUE", "jZweN");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[12]] = AbilityType.lIIIIlllIll("MCMHLTkhJQA2OQ==", "tfTyk");
                AbilityType.lIIlIIlIl[AbilityType.lIIllIIII[13]] = AbilityType.lIIIIlllIIl("vTdIQu3Q3I0=", "prMxY");
            }

            private static void lIIIlIllIll() {
                lIIllIIII = new int[15];
                AbilityType.lIIllIIII[0] = (76 ^ 117) & ~(150 ^ 175);
                AbilityType.lIIllIIII[1] = " ".length();
                AbilityType.lIIllIIII[2] = "  ".length();
                AbilityType.lIIllIIII[3] = "   ".length();
                AbilityType.lIIllIIII[4] = 117 ^ 93 ^ (178 ^ 158);
                AbilityType.lIIllIIII[5] = 92 ^ 89;
                AbilityType.lIIllIIII[6] = 244 ^ 193 ^ (16 ^ 35);
                AbilityType.lIIllIIII[7] = 190 ^ 185;
                AbilityType.lIIllIIII[8] = 116 ^ 124;
                AbilityType.lIIllIIII[9] = 136 ^ 129;
                AbilityType.lIIllIIII[10] = 30 ^ 20;
                AbilityType.lIIllIIII[11] = 112 ^ 91 ^ (91 ^ 123);
                AbilityType.lIIllIIII[12] = 21 ^ 41 ^ (82 ^ 98);
                AbilityType.lIIllIIII[13] = 125 ^ 112;
                AbilityType.lIIllIIII[14] = (11 ^ 17) & ~(54 ^ 44) ^ (36 ^ 42);
            }

            private static String lIIIIlllIll(String llIlIlIIIIlllI, String llIlIlIIIlIIlI) {
                llIlIlIIIIlllI = new String(Base64.getDecoder().decode(llIlIlIIIIlllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                StringBuilder llIlIlIIIlIIIl = new StringBuilder();
                char[] llIlIlIIIlIIII = llIlIlIIIlIIlI.toCharArray();
                int llIlIlIIIIllll = lIIllIIII[0];
                char[] llIlIlIIIIlIIl = llIlIlIIIIlllI.toCharArray();
                int llIlIlIIIIlIII = llIlIlIIIIlIIl.length;
                int llIlIlIIIIIlll = lIIllIIII[0];
                while (AbilityType.lIIIlIlllII(llIlIlIIIIIlll, llIlIlIIIIlIII)) {
                    char llIlIlIIIlIlII = llIlIlIIIIlIIl[llIlIlIIIIIlll];
                    "".length();
                    llIlIlIIIlIIIl.append((char)(llIlIlIIIlIlII ^ llIlIlIIIlIIII[llIlIlIIIIllll % llIlIlIIIlIIII.length]));
                    ++llIlIlIIIIllll;
                    ++llIlIlIIIIIlll;
                    "".length();
                    if (((36 ^ 33 ^ (169 ^ 141)) & (112 + 39 - 76 + 55 ^ 43 + 113 - 94 + 101 ^ -" ".length())) >= 0) continue;
                    return null;
                }
                return String.valueOf(llIlIlIIIlIIIl);
            }

            private static String lIIIIlllIII(String llIlIIllllllII, String llIlIIlllllIll) {
                try {
                    SecretKeySpec llIlIlIIIIIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIIlllllIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                    Cipher llIlIlIIIIIIII = Cipher.getInstance("Blowfish");
                    llIlIlIIIIIIII.init(lIIllIIII[2], llIlIlIIIIIIIl);
                    return new String(llIlIlIIIIIIII.doFinal(Base64.getDecoder().decode(llIlIIllllllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception llIlIIllllllll) {
                    llIlIIllllllll.printStackTrace();
                    return null;
                }
            }

            public static AbilityType[] values() {
                return (AbilityType[])$VALUES.clone();
            }
        }

    }

}

