/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.Color
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.Event
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.player.PlayerEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocLastStanding;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Spleef
extends JocLastStanding {
    private static final /* synthetic */ String[] lIIIIl;
    public /* synthetic */ Material BREAK_TYPE;
    private static final /* synthetic */ int[] lIIIlI;

    private static String lIllIll(String llllIIIlllllllI, String llllIIIllllllIl) {
        try {
            SecretKeySpec llllIIlIIIIIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIIIllllllIl.getBytes(StandardCharsets.UTF_8)), lIIIlI[3]), "DES");
            Cipher llllIIlIIIIIIII = Cipher.getInstance("DES");
            llllIIlIIIIIIII.init(lIIIlI[7], llllIIlIIIIIIIl);
            return new String(llllIIlIIIIIIII.doFinal(Base64.getDecoder().decode(llllIIIlllllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllIIIllllllll) {
            llllIIIllllllll.printStackTrace();
            return null;
        }
    }

    private static boolean lllIllI(int n) {
        return n < 0;
    }

    static {
        Spleef.llIIIII();
        Spleef.lIlllII();
    }

    private void arrowAoE(Location llllIIlIllIIIlI, ProjectileHitEvent llllIIlIllIIlIl, LivingEntity llllIIlIllIIlII) {
        if (Spleef.llIlIll((Object)llllIIlIllIIlIl.getEntityType(), (Object)EntityType.ARROW)) {
            int llllIIlIllIlIII = lIIIlI[7];
            while (Spleef.lllIlll(llllIIlIllIlIII)) {
                llllIIlIllIIIlI.setY(llllIIlIllIIIlI.getY() - 1.0);
                int llllIIlIllIlIlI = lIIIlI[8];
                int llllIIlIllIlIIl = lIIIlI[8];
                while (Spleef.llllIII(llllIIlIllIlIlI, lIIIlI[1])) {
                    while (Spleef.llllIII(llllIIlIllIlIIl, lIIIlI[1])) {
                        Spleef llllIIlIllIIlll;
                        Location llllIIlIllIlIll = llllIIlIllIIIlI.clone();
                        llllIIlIllIlIll.setZ(llllIIlIllIlIll.getZ() + (double)llllIIlIllIlIIl);
                        llllIIlIllIlIll.setX(llllIIlIllIlIll.getX() + (double)llllIIlIllIlIlI);
                        if (!Spleef.llIlIII((Object)llllIIlIllIlIll.getBlock().getType(), (Object)Material.SNOW_BLOCK) || Spleef.llIlIll((Object)llllIIlIllIlIll.getBlock().getType(), (Object)Material.AIR)) {
                            llllIIlIllIlIll.getBlock().setType(llllIIlIllIIlll.BREAK_TYPE);
                            ItemStack llllIIlIllIlllI = new ItemStack(Material.DIAMOND, lIIIlI[1]);
                            "".length();
                            llllIIlIllIIlll.world.dropItem(llllIIlIllIlIll, llllIIlIllIlllI);
                            "".length();
                            if (-"  ".length() > 0) {
                                return;
                            }
                        } else {
                            Material llllIIlIllIllIl = Material.REDSTONE;
                            if (Spleef.llIIIll((int)Utils.Possibilitat(lIIIlI[3]))) {
                                llllIIlIllIllIl = Material.ANVIL;
                            }
                            if (Spleef.llIIIll((int)Utils.Possibilitat(lIIIlI[9]))) {
                                llllIIlIllIllIl = Material.BLAZE_POWDER;
                            }
                            if (Spleef.llIIIll((int)Utils.Possibilitat(lIIIlI[9]))) {
                                llllIIlIllIllIl = Material.DRAGON_EGG;
                            }
                            if (Spleef.llIIIll((int)Utils.Possibilitat(lIIIlI[9]))) {
                                llllIIlIllIllIl = Material.BEDROCK;
                            }
                            ItemStack llllIIlIllIllII = new ItemStack(llllIIlIllIllIl, lIIIlI[1]);
                            "".length();
                            llllIIlIllIIlll.world.dropItem(llllIIlIllIlIll, llllIIlIllIllII);
                        }
                        llllIIlIllIlIIl += lIIIlI[1];
                        "".length();
                        if (((209 ^ 177) & ~(49 ^ 81)) >= -" ".length()) continue;
                        return;
                    }
                    llllIIlIllIlIlI += lIIIlI[1];
                    llllIIlIllIlIIl = lIIIlI[8];
                    "".length();
                    if ("  ".length() > ((39 ^ 41) & ~(90 ^ 84))) continue;
                    return;
                }
                llllIIlIllIlIII -= lIIIlI[1];
                "".length();
                if ((70 ^ 66) > 0) continue;
                return;
            }
        }
    }

    private static boolean llIlIII(Object object, Object object2) {
        return object != object2;
    }

    private static boolean llllllI(int n, int n2) {
        return n < n2;
    }

    @Override
    protected int getBaseSkillUnlockerAmount() {
        return lIIIlI[0];
    }

    private static boolean lllllIl(int n, int n2) {
        return n > n2;
    }

    private void efectesPales(Player llllIIlIIIllIIl, PlayerInteractEvent llllIIlIIIllIII, ItemStack llllIIlIIIlIlll, Inventory llllIIlIIIlIllI) {
        Spleef llllIIlIIIllIlI;
        if (Spleef.llllIlI((Object)llllIIlIIIlIlll)) {
            return;
        }
        Material llllIIlIIIlllII = llllIIlIIIllIIl.getItemInHand().getType();
        if (!Spleef.llIlIII((Object)llllIIlIIIlllII, (Object)Material.LAPIS_BLOCK) || !Spleef.llIlIII((Object)llllIIlIIIlllII, (Object)Material.REDSTONE_BLOCK) || !Spleef.llIlIII((Object)llllIIlIIIlllII, (Object)Material.GOLD_BLOCK) || Spleef.llIlIll((Object)llllIIlIIIlllII, (Object)Material.IRON_BLOCK)) {
            if (Spleef.llIlIll((Object)llllIIlIIIllIII.getAction(), (Object)Action.RIGHT_CLICK_AIR)) {
                Byte llllIIlIlIIIlIl = lIIIlI[0];
                FallingBlock llllIIlIlIIIlII = llllIIlIIIllIlI.world.spawnFallingBlock(llllIIlIIIllIIl.getLocation(), llllIIlIIIlllII, llllIIlIlIIIlIl.byteValue());
                llllIIlIlIIIlII.setVelocity(llllIIlIIIllIIl.getLocation().getDirection().multiply(lIIIlI[7]));
                ItemStack[] arritemStack = new ItemStack[lIIIlI[1]];
                arritemStack[Spleef.lIIIlI[0]] = new ItemStack(llllIIlIIIlllII, lIIIlI[1]);
                "".length();
                llllIIlIIIllIIl.getInventory().removeItem(arritemStack);
                "".length();
                if ((75 ^ 78) == 0) {
                    return;
                }
            } else {
                llllIIlIIIllIII.setCancelled(lIIIlI[1]);
            }
        }
        if (Spleef.llIlIll((Object)llllIIlIIIlllII, (Object)Material.TNT)) {
            if (Spleef.llIlIll((Object)llllIIlIIIllIII.getAction(), (Object)Action.RIGHT_CLICK_AIR)) {
                Byte llllIIlIlIIIIll = lIIIlI[0];
                TNTPrimed llllIIlIlIIIIlI = (TNTPrimed)llllIIlIIIllIIl.getWorld().spawn(llllIIlIIIllIIl.getEyeLocation(), TNTPrimed.class);
                llllIIlIlIIIIlI.setVelocity(llllIIlIIIllIIl.getLocation().getDirection().multiply(1.32f));
                llllIIlIlIIIIlI.setFuseTicks(lIIIlI[10]);
                ItemStack[] arritemStack = new ItemStack[lIIIlI[1]];
                arritemStack[Spleef.lIIIlI[0]] = new ItemStack(Material.TNT, lIIIlI[1]);
                "".length();
                llllIIlIIIllIIl.getInventory().removeItem(arritemStack);
                "".length();
                if (((62 ^ 55 ^ (42 ^ 66)) & (192 ^ 160 ^ " ".length() ^ -" ".length())) != 0) {
                    return;
                }
            } else {
                llllIIlIIIllIII.setCancelled(lIIIlI[1]);
            }
        }
        Boolean llllIIlIIIllIll = lIIIlI[0];
        if (Spleef.llIlIll((Object)llllIIlIIIllIII.getAction(), (Object)Action.RIGHT_CLICK_BLOCK)) {
            Location llllIIlIIlIIIlI = llllIIlIIIllIII.getClickedBlock().getLocation();
            if (!(Spleef.llIlIII((Object)llllIIlIIIlIlll.getType(), (Object)Material.WOOD_HOE) && Spleef.llIlIII((Object)llllIIlIIIlIlll.getType(), (Object)Material.STONE_HOE) && Spleef.llIlIII((Object)llllIIlIIIlIlll.getType(), (Object)Material.IRON_HOE) && Spleef.llIlIII((Object)llllIIlIIIlIlll.getType(), (Object)Material.GOLD_HOE) && !Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.DIAMOND_HOE))) {
                Location llllIIlIIlIIlIl = llllIIlIIIllIIl.getLocation();
                llllIIlIIlIIlIl.setY(llllIIlIIlIIlIl.getY() - 1.0);
                int llllIIlIIlIIlII = lIIIlI[0];
                Boolean llllIIlIIlIIIll = null;
                if (Spleef.llllIll(llllIIlIIlIIIlI.getBlockX(), llllIIlIIlIIlIl.getBlockX())) {
                    llllIIlIIlIIIll = lIIIlI[0];
                }
                if (Spleef.llllIll(llllIIlIIlIIIlI.getBlockZ(), llllIIlIIlIIlIl.getBlockZ())) {
                    llllIIlIIlIIIll = lIIIlI[1];
                }
                if (Spleef.lllllII(llllIIlIIlIIIll)) {
                    Boolean llllIIlIIlIIllI = null;
                    if (Spleef.llllIll((int)llllIIlIIlIIIll.booleanValue(), lIIIlI[1])) {
                        boolean bl;
                        if (Spleef.lllllIl(llllIIlIIlIIIlI.getBlockX(), llllIIlIIlIIlIl.getBlockX())) {
                            bl = lIIIlI[1];
                            "".length();
                            if ((37 + 48 - 76 + 120 ^ 112 + 118 - 149 + 52) <= 0) {
                                return;
                            }
                        } else {
                            bl = lIIIlI[0];
                        }
                        llllIIlIIlIIllI = bl;
                    }
                    if (Spleef.llIlIIl((int)llllIIlIIlIIIll.booleanValue())) {
                        boolean bl;
                        if (Spleef.lllllIl(llllIIlIIlIIIlI.getBlockZ(), llllIIlIIlIIlIl.getBlockZ())) {
                            bl = lIIIlI[1];
                            "".length();
                            if (((117 ^ 22) & ~(67 ^ 32)) != 0) {
                                return;
                            }
                        } else {
                            bl = lIIIlI[0];
                        }
                        llllIIlIIlIIllI = bl;
                    }
                    if (Spleef.lllllII(llllIIlIIlIIllI)) {
                        int llllIIlIIlIlIII = lIIIlI[1];
                        if (Spleef.llIlIIl((int)llllIIlIIlIIllI.booleanValue())) {
                            llllIIlIIlIlIII = lIIIlI[8];
                        }
                        Location llllIIlIIlIIlll = llllIIlIIlIIIlI.clone();
                        if (!Spleef.llIlIII((Object)llllIIlIIIlIlll.getType(), (Object)Material.WOOD_HOE) || Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.STONE_HOE)) {
                            int llllIIlIIlllllI = lIIIlI[0];
                            Boolean llllIIlIIllllIl = lIIIlI[0];
                            while (Spleef.llIlIll((Object)llllIIlIIlIIlll.getBlock().getType(), (Object)Material.SNOW_BLOCK)) {
                                if (Spleef.llIlIIl((int)llllIIlIIlIIlll.equals((Object)llllIIlIIlIIlIl))) {
                                    if (Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.WOOD_HOE)) {
                                        boolean bl;
                                        llllIIlIIIllIll = lIIIlI[1];
                                        if (Spleef.llllIll((int)llllIIlIIllllIl.booleanValue(), lIIIlI[1])) {
                                            Location llllIIlIlIIIIIl = llllIIlIIlIIlll.clone();
                                            if (Spleef.llIlIIl((int)llllIIlIIlIIIll.booleanValue())) {
                                                llllIIlIlIIIIIl.setX(llllIIlIIlIIlll.getX() + 1.0);
                                                llllIIlIlIIIIIl.getBlock().setType(Material.SAND);
                                                llllIIlIlIIIIIl.setX(llllIIlIIlIIlll.getX() - 1.0);
                                                llllIIlIlIIIIIl.getBlock().setType(Material.SAND);
                                                "".length();
                                                if (((82 ^ 77 ^ (0 ^ 18)) & (149 ^ 177 ^ (9 ^ 32) ^ -" ".length())) == "   ".length()) {
                                                    return;
                                                }
                                            } else {
                                                llllIIlIlIIIIIl.setZ(llllIIlIIlIIlll.getZ() + 1.0);
                                                llllIIlIlIIIIIl.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                                llllIIlIlIIIIIl.setZ(llllIIlIIlIIlll.getZ() - 1.0);
                                                llllIIlIlIIIIIl.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                            }
                                            "".length();
                                            if (((155 ^ 137) & ~(103 ^ 117)) != 0) {
                                                return;
                                            }
                                        } else {
                                            llllIIlIIlIIlll.getBlock().setType(Material.SAND);
                                        }
                                        if (Spleef.llIlIIl((int)llllIIlIIllllIl.booleanValue())) {
                                            bl = lIIIlI[1];
                                            "".length();
                                            if ((19 ^ 22) == 0) {
                                                return;
                                            }
                                        } else {
                                            bl = lIIIlI[0];
                                        }
                                        llllIIlIIllllIl = bl;
                                    }
                                    if (Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.STONE_HOE)) {
                                        llllIIlIIIllIll = lIIIlI[1];
                                        llllIIlIIlIIlll.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                        if (Spleef.llIIIll((int)Utils.Possibilitat(lIIIlI[11]))) {
                                            int llllIIlIlIIIIII = lIIIlI[1];
                                            if (Spleef.llIIIll((int)Utils.Possibilitat(lIIIlI[12]))) {
                                                llllIIlIlIIIIII = lIIIlI[8];
                                            }
                                            Location llllIIlIIllllll = llllIIlIIlIIlll.clone();
                                            if (Spleef.llIlIIl((int)llllIIlIIlIIIll.booleanValue())) {
                                                llllIIlIIllllll.setX(llllIIlIIlIIlll.getX() + (double)llllIIlIlIIIIII);
                                                "".length();
                                                if (null != null) {
                                                    return;
                                                }
                                            } else {
                                                llllIIlIIllllll.setZ(llllIIlIIlIIlll.getZ() + (double)llllIIlIlIIIIII);
                                            }
                                            llllIIlIIllllll.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                            ItemStack llllIIlIIIIlIII = new ItemStack(Material.STONE, lIIIlI[1]);
                                        }
                                    }
                                }
                                if (Spleef.llllIll((int)llllIIlIIlIIIll.booleanValue(), lIIIlI[1])) {
                                    llllIIlIIlIIlll.setX(llllIIlIIlIIlll.getX() + (double)llllIIlIIlIlIII);
                                    "".length();
                                    if ((165 ^ 161) < 0) {
                                        return;
                                    }
                                } else {
                                    llllIIlIIlIIlll.setZ(llllIIlIIlIIlll.getZ() + (double)llllIIlIIlIlIII);
                                }
                                llllIIlIIlllllI += lIIIlI[1];
                                "".length();
                                if (((58 ^ 83 ^ (21 ^ 115)) & (108 + 95 - 116 + 72 ^ 56 + 53 - 50 + 85 ^ -" ".length())) < "   ".length()) continue;
                                return;
                            }
                        }
                        if (!Spleef.llIlIII((Object)llllIIlIIIlIlll.getType(), (Object)Material.IRON_HOE) || !Spleef.llIlIII((Object)llllIIlIIIlIlll.getType(), (Object)Material.GOLD_HOE) || Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.DIAMOND_HOE)) {
                            int llllIIlIIlIlIll = lIIIlI[0];
                            Boolean llllIIlIIlIlIlI = lIIIlI[1];
                            int llllIIlIIlIlIIl = lIIIlI[0];
                            while (!Spleef.llIlIII((Object)llllIIlIIlIIlll.getBlock().getType(), (Object)Material.SNOW_BLOCK) || !Spleef.llIlIII((Object)llllIIlIIlIIlll.getBlock().getType(), (Object)Material.AIR) || Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.DIAMOND_HOE)) {
                                if (Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.IRON_HOE)) {
                                    llllIIlIIIllIll = lIIIlI[1];
                                    if (Spleef.llllIII(llllIIlIIlIlIll, lIIIlI[3])) {
                                        boolean bl;
                                        if (Spleef.llllIll((int)llllIIlIIlIlIlI.booleanValue(), lIIIlI[1])) {
                                            Location llllIIlIIlllIlI = llllIIlIIlIIlll.clone();
                                            Location llllIIlIIlllIIl = llllIIlIIlIIlll.clone();
                                            Utils.BreakBlockLater(llllIIlIIlIIlll.getBlock(), lIIIlI[1] * llllIIlIIlIlIll / lIIIlI[7], lIIIlI[1]);
                                            if (Spleef.llIlIIl((int)llllIIlIIlIIIll.booleanValue())) {
                                                int llllIIlIIllllII = lIIIlI[0];
                                                while (Spleef.llllllI(llllIIlIIllllII, llllIIlIIlIlIIl)) {
                                                    llllIIlIIlllIlI.setX(llllIIlIIlllIlI.getX() + 1.0);
                                                    Utils.BreakBlockLater(llllIIlIIlllIlI.getBlock(), lIIIlI[1] * llllIIlIIlIlIll / lIIIlI[7], lIIIlI[1]);
                                                    llllIIlIIlllIIl.setX(llllIIlIIlllIIl.getX() - 1.0);
                                                    Utils.BreakBlockLater(llllIIlIIlllIIl.getBlock(), lIIIlI[1] * llllIIlIIlIlIll / lIIIlI[7], lIIIlI[1]);
                                                    llllIIlIIllllII += lIIIlI[1];
                                                    "".length();
                                                    if ((88 ^ 112 ^ (191 ^ 147)) != "   ".length()) continue;
                                                    return;
                                                }
                                                "".length();
                                                if ("   ".length() <= 0) {
                                                    return;
                                                }
                                            } else {
                                                int llllIIlIIlllIll = lIIIlI[0];
                                                while (Spleef.llllllI(llllIIlIIlllIll, llllIIlIIlIlIIl)) {
                                                    llllIIlIIlllIlI.setZ(llllIIlIIlllIlI.getZ() + 1.0);
                                                    Utils.BreakBlockLater(llllIIlIIlllIlI.getBlock(), lIIIlI[1] * llllIIlIIlIlIll / lIIIlI[7], lIIIlI[1]);
                                                    llllIIlIIlllIIl.setZ(llllIIlIIlllIIl.getZ() - 1.0);
                                                    Utils.BreakBlockLater(llllIIlIIlllIIl.getBlock(), lIIIlI[1] * llllIIlIIlIlIll / lIIIlI[7], lIIIlI[1]);
                                                    llllIIlIIlllIll += lIIIlI[1];
                                                    "".length();
                                                    if (((166 ^ 154) & ~(81 ^ 109)) == 0) continue;
                                                    return;
                                                }
                                            }
                                            llllIIlIIlIlIIl += lIIIlI[1];
                                        }
                                        if (Spleef.llIlIIl((int)llllIIlIIlIlIlI.booleanValue())) {
                                            bl = lIIIlI[1];
                                            "".length();
                                            if (null != null) {
                                                return;
                                            }
                                        } else {
                                            bl = lIIIlI[0];
                                        }
                                        llllIIlIIlIlIlI = bl;
                                    }
                                    if (Spleef.lllllIl(llllIIlIIlIlIll, lIIIlI[13])) {
                                        if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[9])) {
                                            llllIIlIIlIlIIl = lIIIlI[14];
                                        }
                                        if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[15])) {
                                            llllIIlIIlIlIIl = lIIIlI[14];
                                        }
                                        if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[16])) {
                                            llllIIlIIlIlIIl = lIIIlI[17];
                                        }
                                        if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[18])) {
                                            llllIIlIIlIlIIl = lIIIlI[1];
                                        }
                                        if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[19])) {
                                            "".length();
                                            if ("  ".length() > " ".length()) break;
                                            return;
                                        }
                                        Location llllIIlIIllIllI = llllIIlIIlIIlll.clone();
                                        Location llllIIlIIllIlIl = llllIIlIIlIIlll.clone();
                                        Utils.BreakBlockLater(llllIIlIIlIIlll.getBlock(), lIIIlI[13], lIIIlI[1]);
                                        if (Spleef.llIlIIl((int)llllIIlIIlIIIll.booleanValue())) {
                                            int llllIIlIIlllIII = lIIIlI[0];
                                            while (Spleef.llllllI(llllIIlIIlllIII, llllIIlIIlIlIIl)) {
                                                llllIIlIIllIllI.setX(llllIIlIIllIllI.getX() + 1.0);
                                                Utils.BreakBlockLater(llllIIlIIllIllI.getBlock(), lIIIlI[13], lIIIlI[1]);
                                                llllIIlIIllIlIl.setX(llllIIlIIllIlIl.getX() - 1.0);
                                                Utils.BreakBlockLater(llllIIlIIllIlIl.getBlock(), lIIIlI[13], lIIIlI[1]);
                                                llllIIlIIlllIII += lIIIlI[1];
                                                "".length();
                                                if (" ".length() <= (150 ^ 143 ^ (110 ^ 115))) continue;
                                                return;
                                            }
                                            "".length();
                                            if (null != null) {
                                                return;
                                            }
                                        } else {
                                            int llllIIlIIllIlll = lIIIlI[0];
                                            while (Spleef.llllllI(llllIIlIIllIlll, llllIIlIIlIlIIl)) {
                                                llllIIlIIllIllI.setZ(llllIIlIIllIllI.getZ() + 1.0);
                                                Utils.BreakBlockLater(llllIIlIIllIllI.getBlock(), lIIIlI[13], lIIIlI[1]);
                                                llllIIlIIllIlIl.setZ(llllIIlIIllIlIl.getZ() - 1.0);
                                                Utils.BreakBlockLater(llllIIlIIllIlIl.getBlock(), lIIIlI[13], lIIIlI[1]);
                                                llllIIlIIllIlll += lIIIlI[1];
                                                "".length();
                                                if ((64 ^ 90 ^ (56 ^ 39)) > 0) continue;
                                                return;
                                            }
                                        }
                                    }
                                }
                                if (Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.GOLD_HOE)) {
                                    llllIIlIIIllIll = lIIIlI[1];
                                    if (Spleef.llIlIIl(llllIIlIIlIlIll)) {
                                        llllIIlIIlIlIIl = lIIIlI[0];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[1])) {
                                        llllIIlIIlIlIIl = lIIIlI[1];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[7])) {
                                        llllIIlIIlIlIIl = lIIIlI[0];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[17])) {
                                        llllIIlIIlIlIIl = lIIIlI[0];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[14])) {
                                        llllIIlIIlIlIIl = lIIIlI[1];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[20])) {
                                        llllIIlIIlIlIIl = lIIIlI[7];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[21])) {
                                        llllIIlIIlIlIIl = lIIIlI[1];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[22])) {
                                        llllIIlIIlIlIIl = lIIIlI[0];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[3])) {
                                        llllIIlIIlIlIIl = lIIIlI[1];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[13])) {
                                        llllIIlIIlIlIIl = lIIIlI[7];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[9])) {
                                        llllIIlIIlIlIIl = lIIIlI[17];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[15])) {
                                        llllIIlIIlIlIIl = lIIIlI[17];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[16])) {
                                        llllIIlIIlIlIIl = lIIIlI[17];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[18])) {
                                        llllIIlIIlIlIIl = lIIIlI[7];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[19])) {
                                        llllIIlIIlIlIIl = lIIIlI[1];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[23])) {
                                        llllIIlIIlIlIIl = lIIIlI[0];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[24])) {
                                        llllIIlIIlIlIIl = lIIIlI[14];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[25])) {
                                        "".length();
                                        if (-(9 + 43 - 39 + 138 ^ 13 + 34 - 43 + 142) < 0) break;
                                        return;
                                    }
                                    Location llllIIlIIllIIlI = llllIIlIIlIIlll.clone();
                                    Location llllIIlIIllIIIl = llllIIlIIlIIlll.clone();
                                    int llllIIlIIllIIII = lIIIlI[7] * llllIIlIIlIlIll;
                                    if (Spleef.lllllll(llllIIlIIlIlIll, lIIIlI[24])) {
                                        llllIIlIIllIIII += 20;
                                    }
                                    Utils.BreakBlockLater(llllIIlIIlIIlll.getBlock(), llllIIlIIllIIII, lIIIlI[1]);
                                    if (Spleef.llIlIIl((int)llllIIlIIlIIIll.booleanValue())) {
                                        int llllIIlIIllIlII = lIIIlI[0];
                                        while (Spleef.llllllI(llllIIlIIllIlII, llllIIlIIlIlIIl)) {
                                            llllIIlIIllIIlI.setX(llllIIlIIllIIlI.getX() + 1.0);
                                            Utils.BreakBlockLater(llllIIlIIllIIlI.getBlock(), llllIIlIIllIIII, lIIIlI[1]);
                                            llllIIlIIllIIIl.setX(llllIIlIIllIIIl.getX() - 1.0);
                                            Utils.BreakBlockLater(llllIIlIIllIIIl.getBlock(), llllIIlIIllIIII, lIIIlI[1]);
                                            llllIIlIIllIlII += lIIIlI[1];
                                            "".length();
                                            if ("  ".length() > 0) continue;
                                            return;
                                        }
                                        "".length();
                                        if (null != null) {
                                            return;
                                        }
                                    } else {
                                        int llllIIlIIllIIll = lIIIlI[0];
                                        while (Spleef.llllllI(llllIIlIIllIIll, llllIIlIIlIlIIl)) {
                                            llllIIlIIllIIlI.setZ(llllIIlIIllIIlI.getZ() + 1.0);
                                            Utils.BreakBlockLater(llllIIlIIllIIlI.getBlock(), llllIIlIIllIIII, lIIIlI[1]);
                                            llllIIlIIllIIIl.setZ(llllIIlIIllIIIl.getZ() - 1.0);
                                            Utils.BreakBlockLater(llllIIlIIllIIIl.getBlock(), llllIIlIIllIIII, lIIIlI[1]);
                                            llllIIlIIllIIll += lIIIlI[1];
                                            "".length();
                                            if (((215 ^ 192 ^ (108 ^ 124)) & (20 + 103 - 87 + 94 ^ 41 + 48 - -32 + 12 ^ -" ".length())) <= (112 + 136 - 103 + 4 ^ 1 + 54 - -36 + 54)) continue;
                                            return;
                                        }
                                    }
                                }
                                if (Spleef.llIlIll((Object)llllIIlIIIlIlll.getType(), (Object)Material.DIAMOND_HOE)) {
                                    llllIIlIIIllIll = lIIIlI[1];
                                    if (Spleef.llIlIIl(llllIIlIIlIlIll)) {
                                        llllIIlIIlIlIIl = lIIIlI[0];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[1])) {
                                        llllIIlIIlIlIIl = lIIIlI[0];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[7])) {
                                        llllIIlIIlIlIIl = lIIIlI[7];
                                    }
                                    if (Spleef.lllllll(llllIIlIIlIlIll, lIIIlI[17]) && Spleef.llllIII(llllIIlIIlIlIll, lIIIlI[9])) {
                                        llllIIlIIlIlIIl = lIIIlI[1];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[15])) {
                                        llllIIlIIlIlIIl = lIIIlI[0];
                                    }
                                    if (Spleef.llllIll(llllIIlIIlIlIll, lIIIlI[16])) {
                                        "".length();
                                        if (-" ".length() < 0) break;
                                        return;
                                    }
                                    Location llllIIlIIlIllIl = llllIIlIIlIIlll.clone();
                                    Location llllIIlIIlIllII = llllIIlIIlIIlll.clone();
                                    llllIIlIIlIIlll.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                    if (Spleef.llIlIIl((int)llllIIlIIlIIIll.booleanValue())) {
                                        int llllIIlIIlIllll = lIIIlI[0];
                                        while (Spleef.llllllI(llllIIlIIlIllll, llllIIlIIlIlIIl)) {
                                            llllIIlIIlIllIl.setX(llllIIlIIlIllIl.getX() + 1.0);
                                            llllIIlIIlIllIl.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                            llllIIlIIlIllII.setX(llllIIlIIlIllII.getX() - 1.0);
                                            llllIIlIIlIllII.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                            llllIIlIIlIllll += lIIIlI[1];
                                            "".length();
                                            if (null == null) continue;
                                            return;
                                        }
                                        "".length();
                                        if (((99 ^ 76 ^ (71 ^ 46)) & (122 ^ 79 ^ (88 ^ 43) ^ -" ".length())) != 0) {
                                            return;
                                        }
                                    } else {
                                        int llllIIlIIlIlllI = lIIIlI[0];
                                        while (Spleef.llllllI(llllIIlIIlIlllI, llllIIlIIlIlIIl)) {
                                            llllIIlIIlIllIl.setZ(llllIIlIIlIllIl.getZ() + 1.0);
                                            llllIIlIIlIllIl.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                            llllIIlIIlIllII.setZ(llllIIlIIlIllII.getZ() - 1.0);
                                            llllIIlIIlIllII.getBlock().setType(llllIIlIIIllIlI.BREAK_TYPE);
                                            llllIIlIIlIlllI += lIIIlI[1];
                                            "".length();
                                            if (" ".length() != 0) continue;
                                            return;
                                        }
                                    }
                                }
                                if (Spleef.llllIll((int)llllIIlIIlIIIll.booleanValue(), lIIIlI[1])) {
                                    llllIIlIIlIIlll.setX(llllIIlIIlIIlll.getX() + (double)llllIIlIIlIlIII);
                                    "".length();
                                    if (((131 ^ 194 ^ (15 ^ 107)) & (112 ^ 70 ^ (77 ^ 94) ^ -" ".length())) != (("  ".length() ^ (186 ^ 150)) & (64 + 170 - 101 + 56 ^ 40 + 8 - -91 + 8 ^ -" ".length()))) {
                                        return;
                                    }
                                } else {
                                    llllIIlIIlIIlll.setZ(llllIIlIIlIIlll.getZ() + (double)llllIIlIIlIlIII);
                                }
                                llllIIlIIlIlIll += lIIIlI[1];
                                "".length();
                                if ((152 ^ 157) != 0) continue;
                                return;
                            }
                        }
                    }
                }
                if (Spleef.llllIll((int)llllIIlIIIllIll.booleanValue(), lIIIlI[1]) && Spleef.llIlIII((Object)llllIIlIIIllIIl.getGameMode(), (Object)GameMode.CREATIVE)) {
                    ItemStack[] arritemStack = new ItemStack[lIIIlI[1]];
                    arritemStack[Spleef.lIIIlI[0]] = new ItemStack(llllIIlIIIlIlll.getType());
                    "".length();
                    llllIIlIIIlIllI.removeItem(arritemStack);
                }
            }
        }
    }

    private static boolean llllIll(int n, int n2) {
        return n == n2;
    }

    private static boolean llllIII(int n, int n2) {
        return n <= n2;
    }

    private static boolean lllllll(int n, int n2) {
        return n >= n2;
    }

    @Override
    protected synchronized void gameEvent(Event llllIIlIllllllI) {
        Spleef llllIIllIIIIIIl;
        super.gameEvent(llllIIlIllllllI);
        if (Spleef.llIIIll(llllIIlIllllllI instanceof BlockEvent)) {
            Block llllIIllIIlIlII = ((BlockEvent)llllIIlIllllllI).getBlock();
            Location llllIIllIIlIIll = llllIIllIIlIlII.getLocation();
            if (Spleef.llIIIll(llllIIlIllllllI instanceof BlockBreakEvent)) {
                BlockBreakEvent llllIIllIIlIlIl = (BlockBreakEvent)llllIIlIllllllI;
                if (Spleef.llIlIII((Object)llllIIllIIlIlII.getType(), (Object)Material.SNOW_BLOCK)) {
                    llllIIllIIlIlIl.setCancelled(lIIIlI[1]);
                }
                if (Spleef.llIlIIl((int)llllIIllIIlIlIl.isCancelled())) {
                    // empty if block
                }
            }
        }
        if (Spleef.llIIIll(llllIIlIllllllI instanceof EntityEvent)) {
            Object llllIIllIIIllll;
            Entity llllIIllIIIlIlI = ((EntityEvent)llllIIlIllllllI).getEntity();
            Location llllIIllIIIlIIl = llllIIllIIIlIlI.getLocation();
            if (Spleef.llIIIll(llllIIlIllllllI instanceof EntityDamageByEntityEvent)) {
                EntityDamageByEntityEvent llllIIllIIlIIlI = (EntityDamageByEntityEvent)llllIIlIllllllI;
                llllIIllIIlIIlI.setCancelled(lIIIlI[1]);
            }
            if (Spleef.llIIIll(llllIIlIllllllI instanceof ProjectileHitEvent)) {
                ProjectileHitEvent llllIIllIIlIIIl = (ProjectileHitEvent)llllIIlIllllllI;
                Projectile llllIIllIIlIIII = (Projectile)llllIIllIIIlIlI;
                llllIIllIIIllll = (LivingEntity)llllIIllIIlIIII.getShooter();
                if (Spleef.llIIIll(llllIIllIIlIIII instanceof Arrow)) {
                    llllIIllIIIIIIl.arrowAoE(llllIIllIIIlIIl, llllIIllIIlIIIl, (LivingEntity)llllIIllIIIllll);
                }
            }
            if (Spleef.llIIIll(llllIIlIllllllI instanceof ExplosionPrimeEvent)) {
                ExplosionPrimeEvent llllIIllIIIlllI = (ExplosionPrimeEvent)llllIIlIllllllI;
                llllIIllIIIlllI.setRadius(6.0f);
            }
            if (Spleef.llIIIll(llllIIlIllllllI instanceof EntityExplodeEvent)) {
                EntityExplodeEvent llllIIllIIIlIll = (EntityExplodeEvent)llllIIlIllllllI;
                llllIIllIIIlIll.setYield(8.0f);
                if (Spleef.llIlIll((Object)llllIIllIIIlIlI.getType(), (Object)EntityType.PRIMED_TNT)) {
                    ArrayList<Block> llllIIllIIIllII = new ArrayList<Block>();
                    llllIIllIIIllll = llllIIllIIIlIll.blockList().iterator();
                    while (Spleef.llIIIll((int)llllIIllIIIllll.hasNext())) {
                        Block llllIIllIIIllIl = (Block)llllIIllIIIllll.next();
                        if (Spleef.llIlIII((Object)llllIIllIIIllIl.getType(), (Object)Material.SNOW_BLOCK)) {
                            "".length();
                            llllIIllIIIllII.add(llllIIllIIIllIl);
                        }
                        "".length();
                        if ("   ".length() >= " ".length()) continue;
                        return;
                    }
                    "".length();
                    llllIIllIIIlIll.blockList().removeAll(llllIIllIIIllII);
                }
            }
        }
        if (Spleef.llIIIll(llllIIlIllllllI instanceof PlayerEvent)) {
            Player llllIIllIIIIIlI = ((PlayerEvent)llllIIlIllllllI).getPlayer();
            if (Spleef.llIIIll(llllIIlIllllllI instanceof PlayerInteractEvent)) {
                PlayerInteractEvent llllIIllIIIlIII = (PlayerInteractEvent)llllIIlIllllllI;
                ItemStack llllIIllIIIIlll = llllIIllIIIlIII.getItem();
                PlayerInventory llllIIllIIIIllI = llllIIllIIIIIlI.getInventory();
                llllIIllIIIIIIl.efectesPales(llllIIllIIIIIlI, llllIIllIIIlIII, llllIIllIIIIlll, (Inventory)llllIIllIIIIllI);
            }
            if (Spleef.llIIIll(llllIIlIllllllI instanceof PlayerPickupItemEvent)) {
                PlayerPickupItemEvent llllIIllIIIIlIl = (PlayerPickupItemEvent)llllIIlIllllllI;
                llllIIllIIIIlIl.setCancelled(lIIIlI[1]);
            }
            if (Spleef.llIIIll(llllIIlIllllllI instanceof PlayerMoveEvent)) {
                PlayerMoveEvent llllIIllIIIIIll = (PlayerMoveEvent)llllIIlIllllllI;
                if (Spleef.lllIllI(llllIIllIIIIIlI.getLocation().getBlockY()) && Spleef.llIIIll((int)llllIIllIIIIIIl.JocIniciat.booleanValue())) {
                    llllIIllIIIIIlI.getInventory().clear();
                    llllIIllIIIIIlI.setHealth(20.0);
                    llllIIllIIIIIlI.setGameMode(GameMode.SPECTATOR);
                    llllIIllIIIIIIl.removeIfAlive(llllIIllIIIIIlI);
                    if (Spleef.llIIIll((int)llllIIllIIIIIIl.anyoneAlive().booleanValue())) {
                        Player llllIIllIIIIlII = llllIIllIIIIIIl.getRandomAlivePlayer();
                        "".length();
                        llllIIllIIIIIlI.teleport(llllIIllIIIIlII.getLocation().add(0.0, 2.0, 0.0));
                    }
                }
            }
        }
    }

    private static void lIlllII() {
        lIIIIl = new String[lIIIlI[7]];
        Spleef.lIIIIl[Spleef.lIIIlI[0]] = Spleef.lIllIll("d0Um6Xw/7eY=", "CccEh");
        Spleef.lIIIIl[Spleef.lIIIlI[1]] = Spleef.lIllIll("tkE4vekpats=", "ITCIW");
    }

    private static void llIIIII() {
        lIIIlI = new int[26];
        Spleef.lIIIlI[0] = (82 ^ 66 ^ (190 ^ 138)) & (210 ^ 137 ^ 32 + 36 - 41 + 100 ^ -" ".length());
        Spleef.lIIIlI[1] = " ".length();
        Spleef.lIIIlI[2] = -24635 & 32634;
        Spleef.lIIIlI[3] = 101 ^ 80 ^ (255 ^ 194);
        Spleef.lIIIlI[4] = -15976 & 16375;
        Spleef.lIIIlI[5] = 176 + 29 - 48 + 87 ^ 146 + 36 - 89 + 68;
        Spleef.lIIIlI[6] = -2082 & 2401;
        Spleef.lIIIlI[7] = "  ".length();
        Spleef.lIIIlI[8] = -" ".length();
        Spleef.lIIIlI[9] = 44 + 125 - 51 + 35 ^ 140 + 78 - 92 + 21;
        Spleef.lIIIlI[10] = 86 ^ 11 ^ (124 ^ 9);
        Spleef.lIIIlI[11] = 102 ^ 120;
        Spleef.lIIIlI[12] = 90 ^ 127 ^ (163 ^ 180);
        Spleef.lIIIlI[13] = 40 ^ 123 ^ (58 ^ 96);
        Spleef.lIIIlI[14] = 42 ^ 46;
        Spleef.lIIIlI[15] = 117 ^ 99 ^ (40 ^ 53);
        Spleef.lIIIlI[16] = 27 ^ 23;
        Spleef.lIIIlI[17] = "   ".length();
        Spleef.lIIIlI[18] = 106 ^ 103;
        Spleef.lIIIlI[19] = 172 ^ 162;
        Spleef.lIIIlI[20] = 86 ^ 83;
        Spleef.lIIIlI[21] = 251 ^ 171 ^ (2 ^ 84);
        Spleef.lIIIlI[22] = 27 ^ 37 ^ (10 ^ 51);
        Spleef.lIIIlI[23] = 86 ^ 76 ^ (57 ^ 44);
        Spleef.lIIIlI[24] = 28 ^ 12;
        Spleef.lIIIlI[25] = 188 ^ 135 ^ (12 ^ 38);
    }

    @Override
    public void customJocIniciat() {
        Spleef llllIIllIllllll;
        super.customJocIniciat();
        llllIIllIllllll.setBlockBreakPlace(lIIIlI[1]);
        llllIIllIllllll.initAlivePlayers();
        BukkitTask llllIIlllIIIIII = llllIIllIllllll.new DonarPales().runTaskLater((Plugin)llllIIllIllllll.plugin, 80L);
        Iterator<Player> llllIIllIllllIl = llllIIllIllllll.getPlayers().iterator();
        while (Spleef.llIIIll((int)llllIIllIllllIl.hasNext())) {
            Player llllIIlllIIIIlI = llllIIllIllllIl.next();
            "".length();
            llllIIlllIIIIlI.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, lIIIlI[2], lIIIlI[3], lIIIlI[1]), lIIIlI[1]);
            "".length();
            llllIIlllIIIIlI.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, lIIIlI[4], lIIIlI[5], lIIIlI[1]), lIIIlI[1]);
            "".length();
            llllIIlllIIIIlI.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIIIlI[6], lIIIlI[7], lIIIlI[1]), lIIIlI[1]);
            "".length();
            if (null == null) continue;
            return;
        }
    }

    @Override
    public String getGameName() {
        return lIIIIl[lIIIlI[0]];
    }

    public Spleef() {
        Spleef llllIIlllIIlIll;
        llllIIlllIIlIll.BREAK_TYPE = Material.AIR;
    }

    private static boolean lllllII(Object object) {
        return object != null;
    }

    private static boolean lllIlll(int n) {
        return n > 0;
    }

    private static boolean llIlIIl(int n) {
        return n == 0;
    }

    void Pales() {
    }

    private static boolean llIlIll(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llllIIllIllIIII) {
        Spleef llllIIllIllIllI;
        ArrayList<ItemStack> llllIIllIllIlII = new ArrayList<ItemStack>();
        Color llllIIllIllIIll = llllIIllIllIllI.getDeterministicColorForPlayer(llllIIllIllIIII, lIIIlI[0]);
        Color llllIIllIllIIlI = llllIIllIllIllI.getDeterministicColorForPlayer(llllIIllIllIIII, lIIIlI[1]);
        "".length();
        llllIIllIllIlII.add(GUtils.createColoredArmor((Material)Material.LEATHER_HELMET, (Color)llllIIllIllIIlI));
        "".length();
        llllIIllIllIlII.add(GUtils.createColoredArmor((Material)Material.LEATHER_CHESTPLATE, (Color)llllIIllIllIIll));
        "".length();
        llllIIllIllIlII.add(GUtils.createColoredArmor((Material)Material.LEATHER_LEGGINGS, (Color)llllIIllIllIIlI));
        "".length();
        llllIIllIllIlII.add(GUtils.createColoredArmor((Material)Material.LEATHER_BOOTS, (Color)llllIIllIllIIll));
        return llllIIllIllIlII;
    }

    @Override
    protected void setCustomGameRules() {
    }

    @Override
    protected void teletransportarTothom() {
        Spleef llllIIllIlIIIll;
        Iterator<Player> llllIIllIlIIIlI = llllIIllIlIIIll.getPlayers().iterator();
        while (Spleef.llIIIll((int)llllIIllIlIIIlI.hasNext())) {
            Player llllIIllIlIIlIl = llllIIllIlIIIlI.next();
            ArrayList<Location> llllIIllIlIIlll = llllIIllIlIIIll.pMapaActual().ObtenirLocations(lIIIIl[lIIIlI[1]], llllIIllIlIIIll.world);
            Location llllIIllIlIIllI = new Location(llllIIllIlIIIll.world, (double)Utils.NombreEntre(llllIIllIlIIlll.get(lIIIlI[0]).getBlockX(), llllIIllIlIIlll.get(lIIIlI[1]).getBlockX()), llllIIllIlIIlll.get(lIIIlI[0]).getY() + 1.0, (double)Utils.NombreEntre(llllIIllIlIIlll.get(lIIIlI[0]).getBlockZ(), llllIIllIlIIlll.get(lIIIlI[1]).getBlockZ()));
            "".length();
            llllIIllIlIIlIl.teleport(llllIIllIlIIllI);
            "".length();
            if ("   ".length() > " ".length()) continue;
            return;
        }
    }

    private static boolean llIIIll(int n) {
        return n != 0;
    }

    private static boolean llllIlI(Object object) {
        return object == null;
    }

    public class DonarPales
    extends BukkitRunnable {
        public /* synthetic */ Boolean Hoe;
        /* synthetic */ TimerTask hourlyTask;
        public /* synthetic */ int vegades;
        private static final /* synthetic */ int[] llIlIII;
        /* synthetic */ Timer timer;

        static {
            DonarPales.lIllIllll();
        }

        private static void lIllIllll() {
            llIlIII = new int[2];
            DonarPales.llIlIII[0] = (61 ^ 109 ^ (105 ^ 120)) & (85 ^ 116 ^ (9 ^ 105) ^ -" ".length());
            DonarPales.llIlIII[1] = " ".length();
        }

        public void run() {
            DonarPales llIIlIlIIIllIIl;
            llIIlIlIIIllIIl.timer.schedule(llIIlIlIIIllIIl.hourlyTask, 0L, 5058L);
        }

        public DonarPales() {
            DonarPales llIIlIlIIIlllIl;
            llIIlIlIIIlllIl.vegades = llIlIII[0];
            llIIlIlIIIlllIl.Hoe = llIlIII[1];
            llIIlIlIIIlllIl.timer = new Timer();
            llIIlIlIIIlllIl.hourlyTask = new TimerTask(){
                private static final /* synthetic */ String[] lIIlllllI;
                private static final /* synthetic */ int[] lIlIIIIII;

                private static void lIIIlllllll() {
                    lIlIIIIII = new int[17];
                    1.lIlIIIIII[0] = (11 ^ 55 ^ (255 ^ 148)) & (123 ^ 81 ^ (63 ^ 66) ^ -" ".length());
                    1.lIlIIIIII[1] = " ".length();
                    1.lIlIIIIII[2] = 110 ^ 104;
                    1.lIlIIIIII[3] = 148 ^ 158;
                    1.lIlIIIIII[4] = "   ".length();
                    1.lIlIIIIII[5] = 127 ^ 115;
                    1.lIlIIIIII[6] = "  ".length();
                    1.lIlIIIIII[7] = 12 ^ 8;
                    1.lIlIIIIII[8] = 179 ^ 182;
                    1.lIlIIIIII[9] = 14 ^ 56 ^ (153 ^ 168);
                    1.lIlIIIIII[10] = 61 + 54 - 51 + 63 ^ (42 ^ 93);
                    1.lIlIIIIII[11] = 27 ^ 18;
                    1.lIlIIIIII[12] = 57 + 38 - -12 + 61 ^ 82 + 8 - 64 + 137;
                    1.lIlIIIIII[13] = 102 ^ 107;
                    1.lIlIIIIII[14] = 49 ^ 35 ^ (79 ^ 111);
                    1.lIlIIIIII[15] = 56 + 69 - -42 + 37 ^ 48 + 115 - 147 + 120;
                    1.lIlIIIIII[16] = 108 ^ 45;
                }

                static {
                    1.lIIIlllllll();
                    1.lIIIlllllIl();
                }

                private static String lIIIllllIll(String llIIlIlIlIlIII, String llIIlIlIlIlIIl) {
                    try {
                        SecretKeySpec llIIlIlIlIllIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIlIlIlIlIIl.getBytes(StandardCharsets.UTF_8)), lIlIIIIII[10]), "DES");
                        Cipher llIIlIlIlIllII = Cipher.getInstance("DES");
                        llIIlIlIlIllII.init(lIlIIIIII[6], llIIlIlIlIllIl);
                        return new String(llIIlIlIlIllII.doFinal(Base64.getDecoder().decode(llIIlIlIlIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                    }
                    catch (Exception llIIlIlIlIlIll) {
                        llIIlIlIlIlIll.printStackTrace();
                        return null;
                    }
                }

                private static boolean lIIlIIIIIII(int n) {
                    return n != 0;
                }

                private static boolean lIIlIIIIIlI(int n, int n2) {
                    return n == n2;
                }

                public Material pala(int llIIlIlIllIlIl) {
                    Material llIIlIlIllIlII = Material.WOOD_SPADE;
                    if (1.lIIlIIIIIIl(llIIlIlIllIlIl)) {
                        llIIlIlIllIlII = Material.WOOD_SPADE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[1])) {
                        llIIlIlIllIlII = Material.WOOD_HOE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[6])) {
                        llIIlIlIllIlII = Material.STONE_SPADE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[4])) {
                        llIIlIlIllIlII = Material.STONE_HOE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[7])) {
                        llIIlIlIllIlII = Material.IRON_SPADE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[8])) {
                        llIIlIlIllIlII = Material.IRON_HOE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[2])) {
                        llIIlIlIllIlII = Material.TNT;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[9])) {
                        llIIlIlIllIlII = Material.GOLD_HOE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[10])) {
                        llIIlIlIllIlII = Material.DIAMOND_SPADE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[11])) {
                        llIIlIlIllIlII = Material.DIAMOND_HOE;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[3])) {
                        llIIlIlIllIlII = Material.BOW;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[12])) {
                        llIIlIlIllIlII = Material.TNT;
                    }
                    if (1.lIIlIIIIIlI(llIIlIlIllIlIl, lIlIIIIII[5])) {
                        llIIlIlIllIlII = Material.ARROW;
                    }
                    if (1.lIIlIIIlIlI(llIIlIlIllIlIl, lIlIIIIII[13])) {
                        if (1.lIIlIIIIIII((int)Utils.Possibilitat(lIlIIIIII[14]))) {
                            llIIlIlIllIlII = Material.DIAMOND_HOE;
                            if (1.lIIlIIIIIII((int)Utils.Possibilitat(lIlIIIIII[15]))) {
                                llIIlIlIllIlII = Material.ARROW;
                                "".length();
                                if ("  ".length() == " ".length()) {
                                    return null;
                                }
                            }
                        } else {
                            llIIlIlIllIlII = Material.IRON_HOE;
                            if (1.lIIlIIIIIII((int)Utils.Possibilitat(lIlIIIIII[16]))) {
                                llIIlIlIllIlII = Material.TNT;
                            }
                        }
                    }
                    return llIIlIlIllIlII;
                }

                @Override
                public void run() {
                    1 llIIlIllIIIIII;
                    ArrayList<Player> llIIlIllIIIIIl = llIIlIllIIIIII.Spleef.this.getPlayers();
                    Iterator llIIlIlIlllllI = llIIlIllIIIIIl.iterator();
                    while (1.lIIlIIIIIII((int)llIIlIlIlllllI.hasNext())) {
                        boolean bl;
                        Player llIIlIllIIIIll = (Player)llIIlIlIlllllI.next();
                        Material llIIlIllIIIllI = llIIlIllIIIIII.pala(llIIlIllIIIIII.DonarPales.this.vegades);
                        if (1.lIIlIIIIIIl((int)llIIlIllIIIllI.name().contains(lIIlllllI[lIlIIIIII[0]]))) {
                            bl = lIlIIIIII[1];
                            "".length();
                            if ("   ".length() > "   ".length()) {
                                return;
                            }
                        } else {
                            bl = lIlIIIIII[0];
                        }
                        llIIlIllIIIIII.DonarPales.this.Hoe = bl;
                        PlayerInventory llIIlIllIIIlIl = llIIlIllIIIIll.getInventory();
                        ItemStack llIIlIllIIIlII = new ItemStack(llIIlIllIIIllI, lIlIIIIII[1]);
                        if (1.lIIlIIIIIlI(llIIlIllIIIIII.DonarPales.this.vegades, lIlIIIIII[2])) {
                            // empty if block
                        }
                        if (1.lIIlIIIIIlI(llIIlIllIIIIII.DonarPales.this.vegades, lIlIIIIII[3])) {
                            llIIlIllIIIlII.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, lIlIIIIII[1]);
                            ItemStack llIIlIllIIlIII = new ItemStack(Material.ARROW, lIlIIIIII[4]);
                            ItemStack[] arritemStack = new ItemStack[lIlIIIIII[1]];
                            arritemStack[1.lIlIIIIII[0]] = llIIlIllIIlIII;
                            "".length();
                            llIIlIllIIIlIl.addItem(arritemStack);
                        }
                        if (1.lIIlIIIIIlI(llIIlIllIIIIII.DonarPales.this.vegades, lIlIIIIII[5])) {
                            ItemStack llIIlIllIIIlll = new ItemStack(Material.ARROW, lIlIIIIII[6]);
                            ItemStack[] arritemStack = new ItemStack[lIlIIIIII[1]];
                            arritemStack[1.lIlIIIIII[0]] = llIIlIllIIIlll;
                            "".length();
                            llIIlIllIIIlIl.addItem(arritemStack);
                        }
                        if (1.lIIlIIIIIII((int)llIIlIllIIIIII.DonarPales.this.Hoe.booleanValue())) {
                            ItemStack[] arritemStack = new ItemStack[lIlIIIIII[1]];
                            arritemStack[1.lIlIIIIII[0]] = llIIlIllIIIlII;
                            "".length();
                            llIIlIllIIIlIl.addItem(arritemStack);
                            "".length();
                            if (-" ".length() == ((49 ^ 95 ^ (26 ^ 76)) & (29 + 151 - 140 + 141 ^ 73 + 28 - 33 + 73 ^ -" ".length()))) {
                                return;
                            }
                        } else {
                            llIIlIllIIIlIl.setItem(lIlIIIIII[0], llIIlIllIIIlII);
                        }
                        "".length();
                        if (((17 ^ 26) & ~(52 ^ 63)) == 0) continue;
                        return;
                    }
                    if (1.lIIlIIIIlIl(llIIlIllIIIIII.DonarPales.this.vegades, lIlIIIIII[3])) {
                        // empty if block
                    }
                    llIIlIllIIIIII.DonarPales.this.vegades += lIlIIIIII[1];
                }

                private static boolean lIIlIIIlIlI(int n, int n2) {
                    return n >= n2;
                }

                private static boolean lIIlIIIIIIl(int n) {
                    return n == 0;
                }

                private static void lIIIlllllIl() {
                    lIIlllllI = new String[lIlIIIIII[1]];
                    1.lIIlllllI[1.lIlIIIIII[0]] = 1.lIIIllllIll("w7x2NquzJC8=", "jVAnq");
                }

                private static boolean lIIlIIIIlIl(int n, int n2) {
                    return n != n2;
                }
                {
                    1 llIIlIlllIlIIl;
                }
            };
        }

    }

}

