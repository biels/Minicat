/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Chest
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.Potion
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.potion.PotionType
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.BlockIterator
 *  org.bukkit.util.BlockVector
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities;

import com.biel.lobby.Com;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.ColorConverter;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Matrix;
import com.biel.lobby.utilities.Vec;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public class Utils {
    private static final /* synthetic */ String[] llIIlI;
    private static final /* synthetic */ int[] lIIlIII;

    public static MetadataValue getMetadata(Metadatable llIlllIlIIlIlll, String llIlllIlIIlIllI) {
        List llIlllIlIIllIII = llIlllIlIIlIlll.getMetadata(llIlllIlIIlIllI);
        Iterator llIlllIlIIlIIll = llIlllIlIIllIII.iterator();
        while (Utils.llllIlIl((int)llIlllIlIIlIIll.hasNext())) {
            MetadataValue llIlllIlIIllIll = (MetadataValue)llIlllIlIIlIIll.next();
            if (Utils.llllIlll((Object)llIlllIlIIllIll.getOwningPlugin(), (Object)Com.getPlugin())) {
                return llIlllIlIIllIll;
            }
            "".length();
            if (((167 ^ 132 ^ (58 ^ 122)) & (40 ^ 91 ^ (119 ^ 103) ^ -" ".length())) <= 0) continue;
            return null;
        }
        return null;
    }

    public static Cuboid getSquareCuboid(Location llIllIlIlIlllIl, Double llIllIlIlIllllI) {
        return new Cuboid(llIllIlIlIlllIl.clone().subtract(llIllIlIlIllllI.doubleValue(), llIllIlIlIllllI.doubleValue(), llIllIlIlIllllI.doubleValue()), llIllIlIlIlllIl.clone().add(llIllIlIlIllllI.doubleValue(), llIllIlIlIllllI.doubleValue(), llIllIlIlIllllI.doubleValue()));
    }

    public static void donarItemsPlayer(Player llIllllllllIllI, ArrayList<ItemStack> llIllllllllIIlI) {
        Iterator<ItemStack> llIllllllllIIII = llIllllllllIIlI.iterator();
        while (Utils.llllIlIl((int)llIllllllllIIII.hasNext())) {
            ItemStack llIlllllllllIII = llIllllllllIIII.next();
            Utils.giveItemStack(llIlllllllllIII, llIllllllllIllI);
            "".length();
            if ("  ".length() >= 0) continue;
            return;
        }
        llIllllllllIllI.updateInventory();
    }

    private static boolean llllIlII(int n) {
        return n == 0;
    }

    public static ArrayList<LivingEntity> getNearbyEnemies(Location llIllIIllIIlIll, double llIllIIllIIlIlI) {
        ArrayList<LivingEntity> llIllIIllIIlIIl = new ArrayList<LivingEntity>();
        Iterator llIllIIllIIIlIl = llIllIIllIIlIll.getWorld().getEntitiesByClass(LivingEntity.class).iterator();
        while (Utils.llllIlIl((int)llIllIIllIIIlIl.hasNext())) {
            Entity llIllIIllIIllII = (Entity)llIllIIllIIIlIl.next();
            if (Utils.llllIlII(llIllIIllIIllII instanceof LivingEntity)) {
                "".length();
                if (-" ".length() < "   ".length()) break;
                return null;
            }
            LivingEntity llIllIIllIIllIl = (LivingEntity)llIllIIllIIllII;
            if (Utils.llllllIl(Utils.lIIIIllll(llIllIIllIIllIl.getLocation().distance(llIllIIllIIlIll), llIllIIllIIlIlI))) {
                "".length();
                llIllIIllIIlIIl.add(llIllIIllIIllIl);
            }
            "".length();
            if (-"  ".length() < 0) continue;
            return null;
        }
        return llIllIIllIIlIIl;
    }

    public static void healDamageable(Damageable llIlllIllIIIIll, Double llIlllIllIIIllI) {
        Double llIlllIllIIIlIl = llIlllIllIIIIll.getHealth() + llIlllIllIIIllI;
        if (Utils.lIIIIIIII(Utils.lllllllI(llIlllIllIIIlIl, 0.0))) {
            llIlllIllIIIlIl = 0.0;
        }
        if (Utils.lIIIIIIIl(Utils.llllllll(llIlllIllIIIlIl, llIlllIllIIIIll.getMaxHealth()))) {
            llIlllIllIIIlIl = llIlllIllIIIIll.getMaxHealth();
        }
        llIlllIllIIIIll.setHealth(llIlllIllIIIlIl.doubleValue());
    }

    public static ItemStack getRandomPotion() {
        Potion llIlIlllIIllIll = new Potion(Utils.getRandomPotionType());
        if (Utils.llllIlIl((int)Utils.Possibilitat(lIIlIII[13]))) {
            llIlIlllIIllIll.setSplash(lIIlIII[1]);
        }
        return llIlIlllIIllIll.toItemStack(lIIlIII[1]);
    }

    private static int lIIlIIIIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean lIIIIIIIl(int n) {
        return n > 0;
    }

    public static void clearPlayers(ArrayList<Player> llIllllIlIlIllI) {
        Iterator<Player> llIllllIlIlIIlI = llIllllIlIlIllI.iterator();
        while (Utils.llllIlIl((int)llIllllIlIlIIlI.hasNext())) {
            Player llIllllIlIllIII = llIllllIlIlIIlI.next();
            Utils.clearPlayer(llIllllIlIllIII);
            "".length();
            if (("   ".length() & ("   ".length() ^ -" ".length())) >= 0) continue;
            return;
        }
    }

    private static int llllllll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public static ArrayList<LivingEntity> ordrerEnitiesByProximity(Location llIllIlIlIlIIIl, ArrayList<LivingEntity> llIllIlIlIIlIll) {
        ArrayList<LivingEntity> llIllIlIlIIllll = new ArrayList<LivingEntity>();
        while (Utils.lIIIIIIIl(llIllIlIlIIlIll.size())) {
            LivingEntity llIllIlIlIlIIlI = Utils.getNearestEntity(llIllIlIlIlIIIl, llIllIlIlIIlIll);
            "".length();
            llIllIlIlIIllll.add(llIllIlIlIlIIlI);
            "".length();
            llIllIlIlIIlIll.remove((Object)llIllIlIlIlIIlI);
            "".length();
            if (" ".length() != 0) continue;
            return null;
        }
        return llIllIlIlIIllll;
    }

    public static void clearEffects(Player llIllllIIlllIII) {
        Iterator llIllllIIllIlII = llIllllIIlllIII.getActivePotionEffects().iterator();
        while (Utils.llllIlIl((int)llIllllIIllIlII.hasNext())) {
            PotionEffect llIllllIIlllIIl = (PotionEffect)llIllllIIllIlII.next();
            llIllllIIlllIII.removePotionEffect(llIllllIIlllIIl.getType());
            "".length();
            if ("  ".length() >= 0) continue;
            return;
        }
    }

    public static Cuboid getCuboidAround(Location llIllIIIIllIlll, int llIllIIIIllIllI) {
        return Utils.getCuboidAround(llIllIIIIllIlll, llIllIIIIllIllI, llIllIIIIllIllI, llIllIIIIllIllI);
    }

    public static ArrayList<Player> getNearbyPlayers(Location llIllIIlIlIlIII, double llIllIIlIlIIlll) {
        ArrayList<Player> llIllIIlIlIIllI = new ArrayList<Player>();
        Iterator llIllIIlIlIIIlI = llIllIIlIlIlIII.getWorld().getPlayers().iterator();
        while (Utils.llllIlIl((int)llIllIIlIlIIIlI.hasNext())) {
            Player llIllIIlIlIlIIl = (Player)llIllIIlIlIIIlI.next();
            Location llIllIIlIlIlIlI = llIllIIlIlIlIIl.getLocation();
            if (Utils.llllllIl(Utils.lIIIlIIII(llIllIIlIlIlIlI.distance(llIllIIlIlIlIII), llIllIIlIlIIlll))) {
                "".length();
                llIllIIlIlIIllI.add(llIllIIlIlIlIIl);
            }
            "".length();
            if ((181 ^ 147 ^ (59 ^ 24)) > 0) continue;
            return null;
        }
        return llIllIIlIlIIllI;
    }

    public static Boolean containsPlayerByName(ArrayList<Player> llIllIlllllIIll, String llIllIlllllIIlI) {
        Iterator<Player> llIllIllllIllll = llIllIlllllIIll.iterator();
        while (Utils.llllIlIl((int)llIllIllllIllll.hasNext())) {
            Player llIllIlllllIlII = llIllIllllIllll.next();
            if (Utils.llllIlIl((int)llIllIlllllIlII.getName().equalsIgnoreCase(llIllIlllllIIlI))) {
                return lIIlIII[1];
            }
            "".length();
            if (-"  ".length() <= 0) continue;
            return null;
        }
        return lIIlIII[0];
    }

    private static int lllllllI(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public static ArrayList<Integer> getIntArrayFromStringArray(ArrayList<String> llIlIlIllIlllII) {
        ArrayList<Integer> llIlIlIllIllIll = new ArrayList<Integer>();
        Iterator<String> llIlIlIllIllIII = llIlIlIllIlllII.iterator();
        while (Utils.llllIlIl((int)llIlIlIllIllIII.hasNext())) {
            String llIlIlIllIlllIl = llIlIlIllIllIII.next();
            "".length();
            llIlIlIllIllIll.add(Integer.parseInt(llIlIlIllIlllIl));
            "".length();
            if ("  ".length() >= 0) continue;
            return null;
        }
        return llIlIlIllIllIll;
    }

    public static boolean Possibilitat(double llIlllIlllIlIlI, double llIlllIlllIlIIl) {
        boolean bl;
        double llIlllIlllIlIII = Utils.NombreEntre(0.0, llIlllIlllIlIIl);
        if (Utils.llllllIl(Utils.llllllII(llIlllIlllIlIII, llIlllIlllIlIlI))) {
            bl = lIIlIII[1];
            "".length();
            if ("   ".length() != "   ".length()) {
                return (boolean)((176 ^ 136) & ~(105 ^ 81));
            }
        } else {
            bl = lIIlIII[0];
        }
        return bl;
    }

    public static void drawVector(Location llIlIlIlIIlIlII, Vector llIlIlIlIIlIIll, Material llIlIlIlIIlIllI) {
        BlockIterator llIlIlIlIIlIlIl = new BlockIterator(llIlIlIlIIlIlII.getWorld(), llIlIlIlIIlIlII.toVector(), llIlIlIlIIlIIll, 0.0, (int)Math.ceil(llIlIlIlIIlIIll.length()));
        while (Utils.llllIlIl((int)llIlIlIlIIlIlIl.hasNext())) {
            Material material;
            if (Utils.llllIlIl((int)llIlIlIlIIlIlIl.hasNext())) {
                material = llIlIlIlIIlIllI;
                "".length();
                if (-(117 ^ 96 ^ (25 ^ 8)) > 0) {
                    return;
                }
            } else {
                material = Material.DIAMOND_BLOCK;
            }
            llIlIlIlIIlIlIl.next().setType(material);
            "".length();
            if (-(120 + 100 - 189 + 160 ^ 116 + 7 - -20 + 44) <= 0) continue;
            return;
        }
    }

    public static ArrayList<Block> getOuterCylBlocks(Location llIlIllllIlllll, int llIlIllllIllllI, int llIlIllllIlllIl, Boolean llIlIlllllIIIlI) {
        ArrayList<Block> llIlIlllllIIIIl = Utils.getCylBlocks(llIlIllllIlllll, llIlIllllIllllI, llIlIllllIlllIl, lIIlIII[1]);
        int llIlIlllllIIIII = llIlIllllIllllI - lIIlIII[1];
        if (Utils.lIIIlIlll(llIlIlllllIIIII)) {
            "".length();
            llIlIlllllIIIIl.removeAll(Utils.getCylBlocks(llIlIllllIlllll, llIlIlllllIIIII, llIlIllllIlllIl, lIIlIII[1]));
        }
        return llIlIlllllIIIIl;
    }

    private static boolean lIIIllIll(int n, int n2) {
        return n > n2;
    }

    public static Boolean trySpendItem(ItemStack llIlllllllIIllI, Player llIlllllllIIlIl) {
        if (Utils.llllIlll((Object)llIlllllllIIlIl.getGameMode(), (Object)GameMode.SURVIVAL)) {
            PlayerInventory llIlllllllIIlll = llIlllllllIIlIl.getInventory();
            ItemStack[] arritemStack = new ItemStack[lIIlIII[1]];
            arritemStack[Utils.lIIlIII[0]] = llIlllllllIIllI;
            if (Utils.llllIlII((int)llIlllllllIIlll.removeItem(arritemStack).isEmpty())) {
                return lIIlIII[0];
            }
            llIlllllllIIlIl.updateInventory();
        }
        return lIIlIII[1];
    }

    public static void establirItem(Player llIllllllIlIlIl, Material llIllllllIlIIll, int llIllllllIlIIlI, int llIllllllIIIllI) {
        PlayerInventory llIllllllIIllll = llIllllllIlIlIl.getInventory();
        ItemStack llIllllllIIllIl = new ItemStack(llIllllllIlIIll, llIllllllIIIllI);
        llIllllllIIllll.setItem(llIllllllIlIIlI, llIllllllIIllIl);
    }

    public static boolean Possibilitat(int llIlllIllIlllIl) {
        return Utils.Possibilitat(llIlllIllIlllIl, lIIlIII[10]);
    }

    public static Vector getProjection(Vector llIlIlIIllllIII, Vector llIlIlIIlllIlll) {
        double llIlIlIIlllIllI = llIlIlIIllllIII.dot(llIlIlIIlllIlll) / llIlIlIIlllIlll.length();
        return llIlIlIIlllIlll.normalize().multiply(llIlIlIIlllIllI);
    }

    public static ArrayList<LivingEntity> getNearbyEnemies(LivingEntity llIllIlIIIlIlII, double llIllIlIIIlIIll, boolean llIllIlIIIIllll) {
        return Utils.getNearbyEnemies(llIllIlIIIlIlII, llIllIlIIIlIlII.getLocation(), llIllIlIIIlIIll, llIllIlIIIIllll);
    }

    private static int lIIIlIIII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public static void setMetadata(Metadatable llIlllIlIllIIlI, String llIlllIlIllIIIl, Object llIlllIlIllIIII) {
        llIlllIlIllIIlI.setMetadata(llIlllIlIllIIIl, (MetadataValue)new FixedMetadataValue((Plugin)Com.getPlugin(), llIlllIlIllIIII));
    }

    public static PotionType getRandomPotionType() {
        return Utils.getRandomArrayItem(PotionType.values());
    }

    static {
        Utils.llllIIll();
        Utils.lIlIlllI();
    }

    public static void clearPlayer(Player llIllllIlIIlIII) {
        llIllllIlIIlIII.getInventory().clear();
        Utils.clearEffects(llIllllIlIIlIII);
        Utils.clearArmor(llIllllIlIIlIII);
        llIllllIlIIlIII.setGameMode(GameMode.SURVIVAL);
        llIllllIlIIlIII.setMaxHealth(20.0);
        llIllllIlIIlIII.setHealth(llIllllIlIIlIII.getMaxHealth());
        llIllllIlIIlIII.setFireTicks(lIIlIII[0]);
        llIllllIlIIlIII.setExp(0.0f);
        llIllllIlIIlIII.setLevel(lIIlIII[0]);
        llIllllIlIIlIII.setFoodLevel(lIIlIII[9]);
        llIllllIlIIlIII.setExhaustion(20.0f);
        llIllllIlIIlIII.setFallDistance(0.0f);
    }

    private static int lIIlIIIII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public static ArrayList<LivingEntity> getNearbyEnemies(LivingEntity llIllIIlllIlIIl, Location llIllIIlllIlIII, double llIllIIlllIllll, boolean llIllIIlllIlllI) {
        ArrayList<LivingEntity> llIllIIlllIllII = new ArrayList<LivingEntity>();
        World llIllIIlllIlIll = llIllIIlllIlIIl.getWorld();
        Iterator llIllIIlllIIIII = llIllIIlllIlIll.getEntitiesByClass(LivingEntity.class).iterator();
        while (Utils.llllIlIl((int)llIllIIlllIIIII.hasNext())) {
            Entity llIllIIllllIIlI = (Entity)llIllIIlllIIIII.next();
            if (Utils.llllIlII(llIllIIllllIIlI instanceof LivingEntity)) {
                "".length();
                if ("  ".length() >= 0) break;
                return null;
            }
            LivingEntity llIllIIllllIIll = (LivingEntity)llIllIIllllIIlI;
            if (Utils.llllllIl(Utils.lIIIIllII(llIllIIllllIIll.getLocation().distance(llIllIIlllIlIII), llIllIIlllIllll))) {
                if (Utils.llllIlIl((int)llIllIIlllIlllI) && Utils.llllIlII((int)llIllIIllllIIll.hasLineOfSight((Entity)llIllIIlllIlIIl))) {
                    "".length();
                    if (((166 ^ 145) & ~(12 ^ 59)) < "  ".length()) continue;
                    return null;
                }
                if (Utils.llllIlIl((int)llIllIIllllIIll.equals((Object)llIllIIlllIlIIl))) {
                    "".length();
                    if (((62 ^ 121) & ~(219 ^ 156)) <= 0) continue;
                    return null;
                }
                "".length();
                llIllIIlllIllII.add(llIllIIllllIIll);
            }
            "".length();
            if ((52 ^ 48) <= (62 ^ 58)) continue;
            return null;
        }
        return llIllIIlllIllII;
    }

    public static Location readHumanReadableLocation(String llIlIlIllllIlll) {
        ArrayList<String> llIlIlIllllIIII = Utils.readHumanReadableList(llIlIlIllllIlll);
        World llIlIlIllllIIIl = Bukkit.getWorld((String)llIlIlIllllIIII.get(lIIlIII[0]));
        int llIlIlIllllIllI = Integer.parseInt(llIlIlIllllIIII.get(lIIlIII[1]));
        int llIlIlIllllIlII = Integer.parseInt(llIlIlIllllIIII.get(lIIlIII[2]));
        int llIlIlIllllIIll = Integer.parseInt(llIlIlIllllIIII.get(lIIlIII[4]));
        if (Utils.lllllIll((Object)llIlIlIllllIIIl)) {
            return null;
        }
        return new Location(llIlIlIllllIIIl, (double)llIlIlIllllIllI, (double)llIlIlIllllIlII, (double)llIlIlIllllIIll);
    }

    public static ItemStack createColoredArmor(Material llIllllIIIlIllI, Color llIllllIIIlllII) {
        ItemStack llIllllIIIllIll = new ItemStack(llIllllIIIlIllI);
        ItemMeta llIllllIIIllIIl = llIllllIIIllIll.getItemMeta();
        LeatherArmorMeta llIllllIIIllIII = (LeatherArmorMeta)llIllllIIIllIIl;
        llIllllIIIllIII.setColor(llIllllIIIlllII);
        "".length();
        llIllllIIIllIll.setItemMeta((ItemMeta)llIllllIIIllIII);
        return llIllllIIIllIll;
    }

    private static boolean lllllIIl(int n, int n2) {
        return n != n2;
    }

    public static void EstablirArmadura(Player llIllllIlllIIll, ItemStack llIllllIllIlllI, int llIllllIllIllIl) {
        ItemStack[] llIllllIlllIIII = llIllllIlllIIll.getInventory().getArmorContents();
        llIllllIlllIIII[llIllllIllIllIl] = llIllllIllIlllI;
        llIllllIlllIIll.getInventory().setArmorContents(llIllllIlllIIII);
    }

    public static void donarItem(Player lllIIIIIIIllIlI, Material lllIIIIIIIllIIl, int lllIIIIIIIllIII) {
        Utils.donarItem(lllIIIIIIIllIlI, lllIIIIIIIllIIl, lllIIIIIIIllIII, llIIlI[lIIlIII[1]]);
    }

    public static Cuboid getCuboidAround(Location llIllIIIlIIlllI, int llIllIIIlIIllIl, int llIllIIIlIlIIlI, int llIllIIIlIlIIII) {
        return new Cuboid(llIllIIIlIIlllI.getWorld(), llIllIIIlIIlllI.getBlockX() - llIllIIIlIIllIl, llIllIIIlIIlllI.getBlockY() - llIllIIIlIlIIlI, llIllIIIlIIlllI.getBlockZ() - llIllIIIlIlIIII, llIllIIIlIIlllI.getBlockX() + llIllIIIlIIllIl, llIllIIIlIIlllI.getBlockY() + llIllIIIlIlIIlI, llIllIIIlIIlllI.getBlockZ() + llIllIIIlIlIIII);
    }

    public static ArrayList<Player> getNearbyPlayers(Entity llIllIIlIIlllII, double llIllIIlIIllIII) {
        ArrayList<Player> llIllIIlIIllIlI = Utils.getNearbyPlayers(llIllIIlIIlllII.getLocation(), llIllIIlIIllIII);
        if (Utils.llllIlIl(llIllIIlIIlllII instanceof Player)) {
            "".length();
            llIllIIlIIllIlI.remove((Object)llIllIIlIIlllII);
        }
        return llIllIIlIIllIlI;
    }

    private static int lIIIlIlIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static int lIIIIlIIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean lIIIIIIll(int n, int n2) {
        return n < n2;
    }

    private static int lIIIllllI(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static void llllIIll() {
        lIIlIII = new int[27];
        Utils.lIIlIII[0] = (16 + 122 - 40 + 87 ^ 84 + 90 - 121 + 102) & (235 ^ 131 ^ (141 ^ 199) ^ -" ".length());
        Utils.lIIlIII[1] = " ".length();
        Utils.lIIlIII[2] = "  ".length();
        Utils.lIIlIII[3] = -" ".length();
        Utils.lIIlIII[4] = "   ".length();
        Utils.lIIlIII[5] = 84 ^ 56 ^ (100 ^ 12);
        Utils.lIIlIII[6] = 189 ^ 184;
        Utils.lIIlIII[7] = 171 ^ 173;
        Utils.lIIlIII[8] = 128 ^ 135;
        Utils.lIIlIII[9] = 171 ^ 191;
        Utils.lIIlIII[10] = 54 ^ 82;
        Utils.lIIlIII[11] = 73 ^ 65;
        Utils.lIIlIII[12] = -11909 & 12268;
        Utils.lIIlIII[13] = 64 ^ 119;
        Utils.lIIlIII[14] = 97 ^ 40 ^ (55 ^ 119);
        Utils.lIIlIII[15] = 104 ^ 78 ^ (162 ^ 142);
        Utils.lIIlIII[16] = 63 ^ 52;
        Utils.lIIlIII[17] = 97 ^ 52 ^ (80 ^ 9);
        Utils.lIIlIII[18] = 70 + 37 - 58 + 84 ^ 52 + 108 - 90 + 66;
        Utils.lIIlIII[19] = 132 ^ 138;
        Utils.lIIlIII[20] = 171 ^ 164;
        Utils.lIIlIII[21] = 190 + 45 - 143 + 99 ^ 56 + 127 - 103 + 95;
        Utils.lIIlIII[22] = 27 ^ 10;
        Utils.lIIlIII[23] = 160 ^ 178;
        Utils.lIIlIII[24] = 77 ^ 11 ^ (201 ^ 156);
        Utils.lIIlIII[25] = 149 ^ 128;
        Utils.lIIlIII[26] = 60 ^ 75 ^ (13 ^ 108);
    }

    public static String writeHumanReadableLocation(Location llIlIllIIlIIlll, boolean llIlIllIIlIIIlI) {
        ArrayList<String> llIlIllIIlIIlIl = new ArrayList<String>();
        ArrayList<String> llIlIllIIlIIlII = new ArrayList<String>();
        if (Utils.llllIlIl((int)llIlIllIIlIIIlI)) {
            "".length();
            llIlIllIIlIIlIl.add(llIIlI[lIIlIII[23]]);
            "".length();
            llIlIllIIlIIlII.add(llIlIllIIlIIlll.getWorld().getName());
        }
        "".length();
        llIlIllIIlIIlIl.add(llIIlI[lIIlIII[24]]);
        "".length();
        llIlIllIIlIIlII.add(Integer.toString(llIlIllIIlIIlll.getBlockX()));
        "".length();
        llIlIllIIlIIlIl.add(llIIlI[lIIlIII[9]]);
        "".length();
        llIlIllIIlIIlII.add(Integer.toString(llIlIllIIlIIlll.getBlockY()));
        "".length();
        llIlIllIIlIIlIl.add(llIIlI[lIIlIII[25]]);
        "".length();
        llIlIllIIlIIlII.add(Integer.toString(llIlIllIIlIIlll.getBlockZ()));
        return Utils.writeHumanReadableList(llIlIllIIlIIlIl, llIlIllIIlIIlII);
    }

    ArrayList<String> freadHumanReadableList(String llIlIlllIIIIlll) {
        int llIlIlllIIIIllI = lIIlIII[0];
        ArrayList<String> llIlIlllIIIIlII = new ArrayList<String>();
        while (Utils.lIIIIIIll(llIlIlllIIIIlII.size(), lIIlIII[4])) {
            int llIlIlllIIIllII;
            int llIlIlllIIIlIll;
            System.out.println(llIlIlllIIIIllI);
            if (Utils.lIIIllIll(llIlIlllIIIIllI, llIlIlllIIIIlll.length())) {
                llIlIlllIIIIllI = llIlIlllIIIIlll.length() - lIIlIII[1];
            }
            if (Utils.lIIIIIIII(llIlIlllIIIIllI)) {
                llIlIlllIIIIllI = lIIlIII[0];
            }
            if (Utils.lIIIlllII(llIlIlllIIIlIll = llIlIlllIIIIlll.indexOf(llIIlI[lIIlIII[15]], llIlIlllIIIllII = llIlIlllIIIIlll.indexOf(llIIlI[lIIlIII[14]], llIlIlllIIIIllI) + lIIlIII[2]), lIIlIII[3])) {
                llIlIlllIIIlIll = llIlIlllIIIIlll.length();
            }
            System.out.println(String.valueOf(new StringBuilder().append(llIIlI[lIIlIII[16]]).append(llIlIlllIIIllII)));
            System.out.println(String.valueOf(new StringBuilder().append(llIIlI[lIIlIII[17]]).append(llIlIlllIIIlIll)));
            "".length();
            llIlIlllIIIIlII.add(llIlIlllIIIIlll.substring(llIlIlllIIIllII, llIlIlllIIIlIll));
            llIlIlllIIIIllI = llIlIlllIIIlIll;
            "".length();
            if ("  ".length() > 0) continue;
            return null;
        }
        return llIlIlllIIIIlII;
    }

    public static BlockVector getCollisionSurfaceNormalVector(Location llIlIlIIlllllll, Block llIlIlIIllllllI) {
        Block llIlIlIlIIIIIIl = llIlIlIIlllllll.getBlock();
        BlockFace llIlIlIlIIIIIII = llIlIlIIllllllI.getFace(llIlIlIlIIIIIIl);
        return new BlockVector(llIlIlIlIIIIIII.getModX(), llIlIlIlIIIIIII.getModY(), llIlIlIlIIIIIII.getModZ());
    }

    private static int llllllII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public static Vector rotateVectorCC(Vector llIlIlIIIllIIll, Vector llIlIlIIIlllllI, double llIlIlIIIllIIII) {
        double llIlIlIIIllllII = llIlIlIIIllIIll.getX();
        double llIlIlIIIlllIll = llIlIlIIIllIIll.getY();
        double llIlIlIIIlllIlI = llIlIlIIIllIIll.getZ();
        double llIlIlIIIlllIIl = llIlIlIIIlllllI.getX();
        double llIlIlIIIlllIII = llIlIlIIIlllllI.getY();
        double llIlIlIIIllIlll = llIlIlIIIlllllI.getZ();
        double llIlIlIIIllIllI = llIlIlIIIlllIIl * (llIlIlIIIlllIIl * llIlIlIIIllllII + llIlIlIIIlllIII * llIlIlIIIlllIll + llIlIlIIIllIlll * llIlIlIIIlllIlI) * (1.0 - Math.cos(llIlIlIIIllIIII)) + llIlIlIIIllllII * Math.cos(llIlIlIIIllIIII) + (-llIlIlIIIllIlll * llIlIlIIIlllIll + llIlIlIIIlllIII * llIlIlIIIlllIlI) * Math.sin(llIlIlIIIllIIII);
        double llIlIlIIIllIlIl = llIlIlIIIlllIII * (llIlIlIIIlllIIl * llIlIlIIIllllII + llIlIlIIIlllIII * llIlIlIIIlllIll + llIlIlIIIllIlll * llIlIlIIIlllIlI) * (1.0 - Math.cos(llIlIlIIIllIIII)) + llIlIlIIIlllIll * Math.cos(llIlIlIIIllIIII) + (llIlIlIIIllIlll * llIlIlIIIllllII - llIlIlIIIlllIIl * llIlIlIIIlllIlI) * Math.sin(llIlIlIIIllIIII);
        double llIlIlIIIllIlII = llIlIlIIIllIlll * (llIlIlIIIlllIIl * llIlIlIIIllllII + llIlIlIIIlllIII * llIlIlIIIlllIll + llIlIlIIIllIlll * llIlIlIIIlllIlI) * (1.0 - Math.cos(llIlIlIIIllIIII)) + llIlIlIIIlllIlI * Math.cos(llIlIlIIIllIIII) + (-llIlIlIIIlllIII * llIlIlIIIllllII + llIlIlIIIlllIIl * llIlIlIIIlllIll) * Math.sin(llIlIlIIIllIIII);
        return new Vector(llIlIlIIIllIllI, llIlIlIIIllIlIl, llIlIlIIIllIlII);
    }

    public static void donarItem(Player lllIIIIIIIIlIIl, Material lllIIIIIIIIIllI) {
        Utils.donarItem(lllIIIIIIIIlIIl, lllIIIIIIIIIllI, lIIlIII[1], llIIlI[lIIlIII[2]]);
    }

    public static void clearArmor(Player llIllllIIlIllIl) {
        llIllllIIlIllIl.getInventory().setHelmet(null);
        llIllllIIlIllIl.getInventory().setChestplate(null);
        llIllllIIlIllIl.getInventory().setLeggings(null);
        llIllllIIlIllIl.getInventory().setBoots(null);
    }

    public static double pointToLineDistance(Vector llIlIlIlIIIllIl, Vector llIlIlIlIIIlIIl, Vector llIlIlIlIIIlIll) {
        return llIlIlIlIIIlIll.subtract(llIlIlIlIIIllIl).crossProduct(llIlIlIlIIIlIll.subtract(llIlIlIlIIIlIIl)).length() / llIlIlIlIIIlIIl.subtract(llIlIlIlIIIllIl).length();
    }

    private static boolean lIIIIIIII(int n) {
        return n < 0;
    }

    private static String lIIllIII(String llIlIIlllllIIll, String llIlIIlllllIIlI) {
        try {
            SecretKeySpec llIlIIlllllIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlIIlllllIIlI.getBytes(StandardCharsets.UTF_8)), lIIlIII[11]), "DES");
            Cipher llIlIIlllllIlIl = Cipher.getInstance("DES");
            llIlIIlllllIlIl.init(lIIlIII[2], llIlIIlllllIllI);
            return new String(llIlIIlllllIlIl.doFinal(Base64.getDecoder().decode(llIlIIlllllIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIIlllllIlII) {
            llIlIIlllllIlII.printStackTrace();
            return null;
        }
    }

    private static void rotateZ(Vector llIlIlIIlIlllIl, double llIlIlIIlIlllII) {
        "".length();
        llIlIlIIlIlllIl.normalize();
        double llIlIlIIlIllllI = llIlIlIIlIlllIl.getX();
        "".length();
        llIlIlIIlIlllIl.setX((float)(llIlIlIIlIlllIl.getX() * Math.cos(llIlIlIIlIlllII) - llIlIlIIlIlllIl.getY() * Math.sin(llIlIlIIlIlllII)));
        "".length();
        llIlIlIIlIlllIl.setY((float)(llIlIlIIlIllllI * Math.sin(llIlIlIIlIlllII) + llIlIlIIlIlllIl.getY() * Math.cos(llIlIlIIlIlllII)));
    }

    public static ArrayList<Vector> geometricMedianReduce(ArrayList<Vector> llIlIlIIIIlllII, int llIlIlIIIIllIll) {
        ArrayList<Vector> arrayList;
        ArrayList<Vector> llIlIlIIIIllIlI = new ArrayList<Vector>();
        Iterator<Vector> llIlIlIIIIlIllI = llIlIlIIIIlllII.iterator();
        while (Utils.llllIlIl((int)llIlIlIIIIlIllI.hasNext())) {
            Vector llIlIlIIIIlllIl = llIlIlIIIIlIllI.next();
            Iterator<Vector> llIlIlIIIIlIlII = llIlIlIIIIlllII.iterator();
            while (Utils.llllIlIl((int)llIlIlIIIIlIlII.hasNext())) {
                Vector llIlIlIIIIllllI = llIlIlIIIIlIlII.next();
                Vector llIlIlIIIIlllll = Utils.CrearVector(llIlIlIIIIlllIl, llIlIlIIIIllllI).multiply(0.5);
                if (Utils.llllIlII((int)llIlIlIIIIllIlI.contains((Object)llIlIlIIIIlllll))) {
                    "".length();
                    llIlIlIIIIllIlI.add(llIlIlIIIIlllll);
                }
                "".length();
                if ("   ".length() != "  ".length()) continue;
                return null;
            }
            "".length();
            if ("   ".length() != -" ".length()) continue;
            return null;
        }
        if (Utils.llllllIl(llIlIlIIIIllIll)) {
            arrayList = llIlIlIIIIllIlI;
            "".length();
            if ("  ".length() > "   ".length()) {
                return null;
            }
        } else {
            arrayList = Utils.geometricMedianReduce(llIlIlIIIIllIlI, llIlIlIIIIllIll - lIIlIII[1]);
        }
        return arrayList;
    }

    private static boolean llllIlIl(int n) {
        return n != 0;
    }

    public static ArrayList<BlockFace> getAdjacentFaces(Block llIlllIIIIllIIl, ArrayList<BlockFace> llIlllIIIIllIII, int llIlllIIIIllIll) {
        ArrayList<BlockFace> llIlllIIIIllIlI = new ArrayList<BlockFace>();
        Iterator<BlockFace> llIlllIIIIlIllI = llIlllIIIIllIII.iterator();
        while (Utils.llllIlIl((int)llIlllIIIIlIllI.hasNext())) {
            BlockFace llIlllIIIIllllI = llIlllIIIIlIllI.next();
            Block llIlllIIIIlllll = llIlllIIIIllIIl.getRelative(llIlllIIIIllllI);
            if (Utils.llllIlll((Object)llIlllIIIIlllll.getType(), (Object)Material.LOG)) {
                "".length();
                llIlllIIIIllIlI.add(llIlllIIIIllllI);
            }
            "".length();
            if (" ".length() != "  ".length()) continue;
            return null;
        }
        return llIlllIIIIllIlI;
    }

    public static Vector geometricMedianAvg(ArrayList<Vector> llIlIlIIIIIIIlI, int llIlIlIIIIIIIll) {
        return Utils.averageVector(Utils.geometricMedianReduce(llIlIlIIIIIIIlI, llIlIlIIIIIIIll));
    }

    public static Vector averageVector(ArrayList<Vector> llIlIlIIIIIllII) {
        Vector llIlIlIIIIIlIll = new Vector(lIIlIII[0], lIIlIII[0], lIIlIII[0]);
        Iterator<Vector> llIlIlIIIIIlIII = llIlIlIIIIIllII.iterator();
        while (Utils.llllIlIl((int)llIlIlIIIIIlIII.hasNext())) {
            Vector llIlIlIIIIIllIl = llIlIlIIIIIlIII.next();
            "".length();
            llIlIlIIIIIlIll.add(llIlIlIIIIIllIl);
            "".length();
            if (-"  ".length() < 0) continue;
            return null;
        }
        return llIlIlIIIIIlIll.multiply(1.0 / (double)llIlIlIIIIIllII.size());
    }

    public static ArrayList<BlockFace> getAdjacentFaces(Block llIlllIIIlllIll, ArrayList<BlockFace> llIlllIIIlllIII) {
        return Utils.getAdjacentFaces(llIlllIIIlllIll, llIlllIIIlllIII, lIIlIII[1]);
    }

    private static boolean lIIIIIllI(int n, int n2) {
        return n <= n2;
    }

    private static boolean lllllIll(Object object) {
        return object == null;
    }

    public static ArrayList<Block> getCylBlocks(Location llIllIIIIIIlIll, int llIllIIIIIIlIlI, int llIllIIIIIIlIIl, Boolean llIllIIIIIIlIII) {
        ArrayList<Block> llIllIIIIIIIlll = new ArrayList<Block>();
        int llIllIIIIIIIllI = lIIlIII[0];
        while (Utils.lIIIIIIll(llIllIIIIIIIllI, llIllIIIIIIlIIl)) {
            Location llIllIIIIIIllIl = llIllIIIIIIlIll.clone().add(new Vector(lIIlIII[0], llIllIIIIIIIllI, lIIlIII[0]));
            Cuboid llIllIIIIIIllII = Utils.getCuboidAround(llIllIIIIIIllIl, Math.round(llIllIIIIIIlIlI), lIIlIII[0], Math.round(llIllIIIIIIlIlI));
            Iterator<Block> llIlIlllllllIlI = llIllIIIIIIllII.getBlocks().iterator();
            while (Utils.llllIlIl((int)llIlIlllllllIlI.hasNext())) {
                Block llIllIIIIIIlllI = llIlIlllllllIlI.next();
                double llIllIIIIIlIIlI = llIllIIIIIIlllI.getLocation().distance(llIllIIIIIIllIl);
                Boolean llIllIIIIIlIIII = lIIlIII[1];
                if (Utils.llllIlIl((int)llIllIIIIIIlIII.booleanValue())) {
                    boolean bl;
                    if (Utils.llllllIl(Utils.lIIIlIlIl(llIllIIIIIlIIlI, llIllIIIIIIlIlI))) {
                        bl = lIIlIII[1];
                        "".length();
                        if (" ".length() == ((131 ^ 168) & ~(118 ^ 93))) {
                            return null;
                        }
                    } else {
                        bl = lIIlIII[0];
                    }
                    llIllIIIIIlIIII = bl;
                }
                if (Utils.llllIlII((int)llIllIIIIIIlIII.booleanValue())) {
                    boolean bl;
                    if (Utils.llllIlII(Utils.lIIIlIllI(llIllIIIIIlIIlI, llIllIIIIIIlIlI))) {
                        bl = lIIlIII[1];
                        "".length();
                        if (null != null) {
                            return null;
                        }
                    } else {
                        bl = lIIlIII[0];
                    }
                    llIllIIIIIlIIII = bl;
                }
                if (Utils.llllIlIl((int)llIllIIIIIlIIII.booleanValue())) {
                    "".length();
                    llIllIIIIIIIlll.add(llIllIIIIIIlllI);
                }
                "".length();
                if (null == null) continue;
                return null;
            }
            ++llIllIIIIIIIllI;
            "".length();
            if (" ".length() < "   ".length()) continue;
            return null;
        }
        return llIllIIIIIIIlll;
    }

    private static void lIlIlllI() {
        llIIlI = new String[lIIlIII[26]];
        Utils.llIIlI[Utils.lIIlIII[0]] = Utils.lIIlIllI("", "YduYs");
        Utils.llIIlI[Utils.lIIlIII[1]] = Utils.lIIlIlll("FbMbiiPSCLc=", "RVGut");
        Utils.llIIlI[Utils.lIIlIII[2]] = Utils.lIIllIII("OQ2Sfu/yBf0=", "YCGiE");
        Utils.llIIlI[Utils.lIIlIII[4]] = Utils.lIIllIII("G0mhFTWaiK4=", "dDKGW");
        Utils.llIIlI[Utils.lIIlIII[5]] = Utils.lIIllIII("ssskzyHD8hYpJGqkpFbYFg==", "BMcHk");
        Utils.llIIlI[Utils.lIIlIII[6]] = Utils.lIIllIII("gPhzDvuRWxc=", "Qgovc");
        Utils.llIIlI[Utils.lIIlIII[7]] = Utils.lIIlIlll("wIIovdj0cXuBA6hLymqwzg==", "kjvYt");
        Utils.llIIlI[Utils.lIIlIII[8]] = Utils.lIIlIlll("4pqsyg/WkOs=", "WBdqV");
        Utils.llIIlI[Utils.lIIlIII[11]] = Utils.lIIllIII("VJsf15AXcoxbh/Vw48gwqf/DAwJnOJI+", "kFCmk");
        Utils.llIIlI[Utils.lIIlIII[14]] = Utils.lIIllIII("AEtax+1HIlY=", "zbTRZ");
        Utils.llIIlI[Utils.lIIlIII[15]] = Utils.lIIlIlll("mxe0Uur0M9Y=", "bImqZ");
        Utils.llIIlI[Utils.lIIlIII[16]] = Utils.lIIlIlll("s5cDCucGCto=", "YfqHD");
        Utils.llIIlI[Utils.lIIlIII[17]] = Utils.lIIllIII("rmkk6iqWAMo=", "aXBlP");
        Utils.llIIlI[Utils.lIIlIII[18]] = Utils.lIIlIllI("Xg==", "rGMuh");
        Utils.llIIlI[Utils.lIIlIII[19]] = Utils.lIIlIlll("FmUhR/LNYMU=", "UIBSz");
        Utils.llIIlI[Utils.lIIlIII[20]] = Utils.lIIllIII("rmsksYAOSSI=", "dVbpv");
        Utils.llIIlI[Utils.lIIlIII[21]] = Utils.lIIllIII("ZmzPTwYa3go=", "eXUTY");
        Utils.llIIlI[Utils.lIIlIII[22]] = Utils.lIIllIII("zKa/12YaUVU=", "BoKiS");
        Utils.llIIlI[Utils.lIIlIII[23]] = Utils.lIIlIlll("NvVSx3xy7DY=", "HJCxf");
        Utils.llIIlI[Utils.lIIlIII[24]] = Utils.lIIllIII("762jbKwhk24=", "ZNiIu");
        Utils.llIIlI[Utils.lIIlIII[9]] = Utils.lIIllIII("RxxlwLl5Uu4=", "dPfMg");
        Utils.llIIlI[Utils.lIIlIII[25]] = Utils.lIIllIII("GyoND13gHq4=", "jmxnR");
    }

    private static int lIIIlIllI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean lIIIlllII(int n, int n2) {
        return n == n2;
    }

    private static boolean lllllIII(Object object) {
        return object != null;
    }

    public static BlockFace getBlocksSharedFace(Block llIlIlllIlIlIlI, Block llIlIlllIlIlIIl) {
        BlockFace[] llIlIlllIlIIllI = BlockFace.values();
        int llIlIlllIlIIlIl = llIlIlllIlIIllI.length;
        int llIlIlllIlIIlII = lIIlIII[0];
        while (Utils.lIIIIIIll(llIlIlllIlIIlII, llIlIlllIlIIlIl)) {
            BlockFace llIlIlllIlIlIll = llIlIlllIlIIllI[llIlIlllIlIIlII];
            if (Utils.llllIlIl((int)llIlIlllIlIlIlI.getRelative(llIlIlllIlIlIll).equals((Object)llIlIlllIlIlIIl))) {
                return llIlIlllIlIlIll;
            }
            ++llIlIlllIlIIlII;
            "".length();
            if (((189 ^ 166) & ~(185 ^ 162)) <= 0) continue;
            return null;
        }
        return null;
    }

    private static boolean llllllIl(int n) {
        return n <= 0;
    }

    public static void donarItem(Player lllIIIIIlIIIIII, Material lllIIIIIIllllll, int lllIIIIIlIIIlIl, String lllIIIIIlIIIlII) {
        PlayerInventory lllIIIIIlIIIIll = lllIIIIIlIIIIII.getInventory();
        ItemStack lllIIIIIlIIIIlI = new ItemStack(lllIIIIIIllllll, lllIIIIIlIIIlIl);
        if (Utils.llllIlII((int)lllIIIIIlIIIlII.isEmpty())) {
            ItemMeta lllIIIIIlIIlIII = lllIIIIIlIIIIlI.getItemMeta();
            lllIIIIIlIIlIII.setDisplayName(lllIIIIIlIIIlII);
            "".length();
            lllIIIIIlIIIIlI.setItemMeta(lllIIIIIlIIlIII);
        }
        ItemStack[] arritemStack = new ItemStack[lIIlIII[1]];
        arritemStack[Utils.lIIlIII[0]] = lllIIIIIlIIIIlI;
        "".length();
        lllIIIIIlIIIIll.addItem(arritemStack);
    }

    public static Cuboid getCuboidCenteredOnBase(Location llIllIIIlIIIIlI, int llIllIIIlIIIIIl, int llIllIIIlIIIIII, int llIllIIIIllllll) {
        return new Cuboid(llIllIIIlIIIIlI.getWorld(), llIllIIIlIIIIlI.getBlockX() - llIllIIIlIIIIIl, llIllIIIlIIIIlI.getBlockY(), llIllIIIlIIIIlI.getBlockZ() - llIllIIIIllllll, llIllIIIlIIIIlI.getBlockX() + llIllIIIlIIIIIl, llIllIIIlIIIIlI.getBlockY() + llIllIIIlIIIIII, llIllIIIlIIIIlI.getBlockZ() + llIllIIIIllllll);
    }

    public Utils() {
        Utils lllIIIIlIIlIIll;
    }

    public static Vector CrearVector(Vector llIlllIIIIIlIll, Vector llIlllIIIIIlIlI) {
        return llIlllIIIIIlIlI.subtract(llIlllIIIIIlIll);
    }

    public static double distanceXZ(Location llIlIlIlIlllIII, Location llIlIlIlIllIIlI) {
        double llIlIlIlIllIllI = llIlIlIlIlllIII.getX() - llIlIlIlIllIIlI.getX();
        double llIlIlIlIllIlIl = llIlIlIlIlllIII.getY() - llIlIlIlIllIIlI.getY();
        double llIlIlIlIllIlII = llIlIlIlIlllIII.getZ() - llIlIlIlIllIIlI.getZ();
        return Math.sqrt(llIlIlIlIllIllI * llIlIlIlIllIllI + llIlIlIlIllIlII * llIlIlIlIllIlII);
    }

    public static Vector CrearVector(Location llIlllIIIIlIIIl, Location llIlllIIIIlIIII) {
        return llIlllIIIIlIIII.toVector().subtract(llIlllIIIIlIIIl.toVector());
    }

    public static boolean isInSphere(Location llIllIlIllIllIl, Double llIllIlIllIllII, Block llIllIlIllIlIII) {
        boolean bl;
        if (Utils.llllllIl(Utils.lIIIIlIIl(llIllIlIllIlIII.getLocation().distance(llIllIlIllIllIl), llIllIlIllIllII))) {
            bl = lIIlIII[1];
            "".length();
            if (((171 + 102 - 167 + 71 ^ 76 + 8 - -8 + 38) & (13 + 112 - -20 + 0 ^ 128 + 150 - 179 + 63 ^ -" ".length())) > "   ".length()) {
                return (boolean)((50 + 62 - -16 + 17 ^ 159 + 127 - 248 + 157) & (158 + 111 - 95 + 25 ^ 27 + 79 - -28 + 15 ^ -" ".length()));
            }
        } else {
            bl = lIIlIII[0];
        }
        return bl;
    }

    private static int lIIIIllII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static int lIIIlllll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static String lIIlIlll(String llIlIIllllIIllI, String llIlIIllllIIlIl) {
        try {
            SecretKeySpec llIlIIllllIlIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIIllllIIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIlIIllllIlIII = Cipher.getInstance("Blowfish");
            llIlIIllllIlIII.init(lIIlIII[2], llIlIIllllIlIIl);
            return new String(llIlIIllllIlIII.doFinal(Base64.getDecoder().decode(llIlIIllllIIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIIllllIIlll) {
            llIlIIllllIIlll.printStackTrace();
            return null;
        }
    }

    private static String lIIlIllI(String llIlIIlllIlIllI, String llIlIIlllIlIlIl) {
        llIlIIlllIlIllI = new String(Base64.getDecoder().decode(llIlIIlllIlIllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIlIIlllIlIlII = new StringBuilder();
        char[] llIlIIlllIlIIll = llIlIIlllIlIlIl.toCharArray();
        int llIlIIlllIlIIlI = lIIlIII[0];
        char[] llIlIIlllIIllII = llIlIIlllIlIllI.toCharArray();
        int llIlIIlllIIlIll = llIlIIlllIIllII.length;
        int llIlIIlllIIlIlI = lIIlIII[0];
        while (Utils.lIIIIIIll(llIlIIlllIIlIlI, llIlIIlllIIlIll)) {
            char llIlIIlllIlIlll = llIlIIlllIIllII[llIlIIlllIIlIlI];
            "".length();
            llIlIIlllIlIlII.append((char)(llIlIIlllIlIlll ^ llIlIIlllIlIIll[llIlIIlllIlIIlI % llIlIIlllIlIIll.length]));
            ++llIlIIlllIlIIlI;
            ++llIlIIlllIIlIlI;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(llIlIIlllIlIlII);
    }

    public static Location readHumanReadableLocation(String llIlIllIIIllIIl, World llIlIllIIIIlllI) {
        if (Utils.lllllIll((Object)llIlIllIIIIlllI)) {
            return null;
        }
        ArrayList<String> llIlIllIIIlIIIl = Utils.readHumanReadableList(llIlIllIIIllIIl);
        int llIlIllIIIlIllI = Integer.parseInt(llIlIllIIIlIIIl.get(lIIlIII[0]));
        int llIlIllIIIlIlII = Integer.parseInt(llIlIllIIIlIIIl.get(lIIlIII[1]));
        int llIlIllIIIlIIll = Integer.parseInt(llIlIllIIIlIIIl.get(lIIlIII[2]));
        return new Location(llIlIllIIIIlllI, (double)llIlIllIIIlIllI, (double)llIlIllIIIlIlII, (double)llIlIllIIIlIIll);
    }

    public static Entity getEntityFromUUID(UUID llIllIIIllIIlII) {
        Iterator llIllIIIllIIIlI = Bukkit.getWorlds().iterator();
        while (Utils.llllIlIl((int)llIllIIIllIIIlI.hasNext())) {
            World llIllIIIllIIllI = (World)llIllIIIllIIIlI.next();
            Entity llIllIIIllIlIII = Utils.getEntityFromUUID(llIllIIIllIIlII, llIllIIIllIIllI);
            if (Utils.lllllIII((Object)llIllIIIllIlIII)) {
                return llIllIIIllIlIII;
            }
            "".length();
            if ("   ".length() <= "   ".length()) continue;
            return null;
        }
        return null;
    }

    public static void BreakBlockLater(Block llIlllIIllIIIIl, int llIlllIIllIIIII, boolean llIlllIIlIllllI) {
        "".length();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)Com.getPlugin(), () -> {
            if (Utils.llllIlIl((int)llIlllIIlIllllI)) {
                "".length();
                llIlllIIllIIIIl.breakNaturally();
                "".length();
                if (((108 ^ 85) & ~(140 ^ 181)) != 0) {
                    return;
                }
            } else {
                llIlllIIllIIIIl.setType(Material.AIR);
            }
        }, (long)llIlllIIllIIIII);
    }

    public static void donarItem(Player lllIIIIIIlIlIll, Material lllIIIIIIlIIlIl, String lllIIIIIIlIIlII) {
        Utils.donarItem(lllIIIIIIlIlIll, lllIIIIIIlIIlIl, lIIlIII[1], lllIIIIIIlIIlII);
    }

    private static void rotateY(Vector llIlIlIIlIlIIII, double llIlIlIIlIIllll) {
        "".length();
        llIlIlIIlIlIIII.normalize();
        double llIlIlIIlIlIIll = llIlIlIIlIlIIII.getZ();
        double llIlIlIIlIlIIlI = Math.sin(llIlIlIIlIIllll);
        double llIlIlIIlIlIIIl = Math.cos(llIlIlIIlIIllll);
        "".length();
        llIlIlIIlIlIIII.setZ((float)(llIlIlIIlIlIIll * llIlIlIIlIlIIIl - llIlIlIIlIlIIII.getX() * llIlIlIIlIlIIlI));
        "".length();
        llIlIlIIlIlIIII.setX((float)(llIlIlIIlIlIIll * llIlIlIIlIlIIlI + llIlIlIIlIlIIII.getX() * llIlIlIIlIlIIIl));
    }

    public static ArrayList<Location> getLocationsCircle(Location llIllIllIlllIII, Double llIllIllIllllll, int llIllIllIllIllI, int llIllIllIllllIl, int llIllIllIllllII) {
        ArrayList<Location> llIllIllIlllIll = new ArrayList<Location>();
        World llIllIllIlllIlI = llIllIllIlllIII.getWorld();
        int llIllIllIlllIIl = llIllIllIllllIl;
        while (Utils.lIIIIIllI(llIllIllIlllIIl, llIllIllIllllII)) {
            float llIllIlllIIIIll = llIllIllIlllIIl;
            double llIllIlllIIIIlI = 0.017453292519943295;
            Location llIllIlllIIIIIl = llIllIllIlllIII.clone().add(new Location(llIllIllIlllIlI, Math.cos((double)llIllIlllIIIIll * llIllIlllIIIIlI) * llIllIllIllllll, 0.0, Math.sin((double)llIllIlllIIIIll * llIllIlllIIIIlI) * llIllIllIllllll));
            "".length();
            llIllIllIlllIll.add(llIllIlllIIIIIl);
            llIllIllIlllIIl += llIllIllIllIllI;
            "".length();
            if (" ".length() >= -" ".length()) continue;
            return null;
        }
        return llIllIllIlllIll;
    }

    public static int getArmorSlot(ItemStack llIlllllIlIIllI) {
        int llIlllllIlIIlIl = lIIlIII[3];
        String llIlllllIlIIlII = llIlllllIlIIllI.getType().name();
        if (Utils.llllIlIl((int)llIlllllIlIIlII.contains(llIIlI[lIIlIII[4]]))) {
            llIlllllIlIIlIl = lIIlIII[4];
        }
        if (!Utils.llllIlII((int)llIlllllIlIIlII.contains(llIIlI[lIIlIII[5]])) || Utils.llllIlIl((int)llIlllllIlIIlII.contains(llIIlI[lIIlIII[6]]))) {
            llIlllllIlIIlIl = lIIlIII[2];
        }
        if (Utils.llllIlIl((int)llIlllllIlIIlII.contains(llIIlI[lIIlIII[7]]))) {
            llIlllllIlIIlIl = lIIlIII[1];
        }
        if (Utils.llllIlIl((int)llIlllllIlIIlII.contains(llIIlI[lIIlIII[8]]))) {
            llIlllllIlIIlIl = lIIlIII[0];
        }
        return llIlllllIlIIlIl;
    }

    public static <T> T getRandomListItem(List<T> llIlIlllIIllllI) {
        Collections.shuffle(llIlIlllIIllllI);
        return llIlIlllIIllllI.get(lIIlIII[0]);
    }

    public static void establirItem(Player llIlllllIlllIlI, Material llIlllllIlllIIl, int llIlllllIlllIll) {
        Utils.establirItem(llIlllllIlllIlI, llIlllllIlllIIl, llIlllllIlllIll, lIIlIII[1]);
    }

    private static boolean lIIIllIIl(Object object, Object object2) {
        return object != object2;
    }

    public static ArrayList<Location> getLocationsCircle(Location llIllIllllIIllI, Double llIllIllllIIIII, int llIllIllllIIIlI) {
        return Utils.getLocationsCircle(llIllIllllIIllI, llIllIllllIIIII, llIllIllllIIIlI, lIIlIII[0], lIIlIII[12]);
    }

    public static <T> T getRandomArrayItem(T[] llIlIlllIlIIIIl) {
        return llIlIlllIlIIIIl[Utils.NombreEntre(lIIlIII[0], llIlIlllIlIIIIl.length - lIIlIII[1])];
    }

    public static /* varargs */ ItemStack setItemLore(ItemStack lllIIIIIlIllIll, String ... lllIIIIIlIllIIl) {
        return Utils.setItemNameAndLore(lllIIIIIlIllIll, llIIlI[lIIlIII[0]], lllIIIIIlIllIIl);
    }

    public static void fillChestRandomly(Block llIlIlllIlllIlI, ArrayList<ItemStack> llIlIlllIlllIIl) {
        if (Utils.lllllIll((Object)llIlIlllIlllIlI)) {
            return;
        }
        if (Utils.lIIIllIIl((Object)llIlIlllIlllIlI.getType(), (Object)Material.CHEST)) {
            return;
        }
        Chest llIlIlllIlllIll = (Chest)llIlIlllIlllIlI.getState();
        Iterator<ItemStack> llIlIlllIllIlll = llIlIlllIlllIIl.iterator();
        while (Utils.llllIlIl((int)llIlIlllIllIlll.hasNext())) {
            ItemStack llIlIllllIIIIII = llIlIlllIllIlll.next();
            int llIlIllllIIIIIl = Utils.NombreEntre(lIIlIII[0], llIlIlllIlllIll.getInventory().getSize() - lIIlIII[1]);
            llIlIlllIlllIll.getInventory().setItem(llIlIllllIIIIIl, llIlIllllIIIIII);
            "".length();
            if (-" ".length() <= " ".length()) continue;
            return;
        }
    }

    public static GestorPropietats getpMapaFromWorld(World llIlllIIlIIlIlI) {
        return new GestorPropietats(String.valueOf(new StringBuilder().append(llIlllIIlIIlIlI.getWorldFolder().getAbsolutePath()).append(llIIlI[lIIlIII[11]])));
    }

    public static String writeHumanReadableList(ArrayList<String> llIlIllIIllIlIl, ArrayList<String> llIlIllIIllIIIl) {
        String llIlIllIIllIIll = llIIlI[lIIlIII[20]];
        Iterator<String> llIlIllIIlIllll = llIlIllIIllIlIl.iterator();
        while (Utils.llllIlIl((int)llIlIllIIlIllll.hasNext())) {
            String llIlIllIIllIllI = llIlIllIIlIllll.next();
            int llIlIllIIlllIII = llIlIllIIllIlIl.indexOf(llIlIllIIllIllI);
            String llIlIllIIllIlll = llIlIllIIllIIIl.get(llIlIllIIlllIII);
            if (Utils.llllIlIl(llIlIllIIlllIII)) {
                llIlIllIIllIIll = String.valueOf(new StringBuilder().append(llIlIllIIllIIll).append(llIIlI[lIIlIII[21]]));
            }
            llIlIllIIllIIll = String.valueOf(new StringBuilder().append(llIlIllIIllIIll).append(llIlIllIIllIllI).append(llIIlI[lIIlIII[22]]).append(llIlIllIIllIlll));
            "".length();
            if (-" ".length() < ((168 ^ 148 ^ (117 ^ 71)) & (77 + 129 - 74 + 71 ^ 136 + 158 - 273 + 176 ^ -" ".length()))) continue;
            return null;
        }
        return llIlIllIIllIIll;
    }

    public static ArrayList<ItemStack> getInventoryPercent(Inventory llIlllIIlllIIll, float llIlllIIlllIlIl) {
        ArrayList<ItemStack> llIlllIIlllIlII = new ArrayList<ItemStack>();
        ItemStack[] llIlllIIlllIIII = llIlllIIlllIIll.getContents();
        int llIlllIIllIllll = llIlllIIlllIIII.length;
        int llIlllIIllIlllI = lIIlIII[0];
        while (Utils.lIIIIIIll(llIlllIIllIlllI, llIlllIIllIllll)) {
            ItemStack llIlllIIlllIlll = llIlllIIlllIIII[llIlllIIllIlllI];
            if (Utils.lllllIll((Object)llIlllIIlllIlll)) {
                "".length();
                if (((52 ^ 121 ^ (14 ^ 116)) & (108 + 34 - -7 + 4 ^ 33 + 63 - -71 + 7 ^ -" ".length())) < ((119 + 40 - 34 + 18 ^ 122 + 20 - 100 + 135) & (60 ^ 127 ^ (104 ^ 21) ^ -" ".length()))) {
                    return null;
                }
            } else if (Utils.llllIlIl((int)Utils.Possibilitat((int)llIlllIIlllIlIl))) {
                Material llIlllIIllllIlI = llIlllIIlllIlll.getType();
                int llIlllIIllllIIl = (int)Math.floor((float)llIlllIIlllIlll.getAmount() * (llIlllIIlllIlIl / 100.0f) * 2.0f);
                ItemStack llIlllIIllllIII = llIlllIIlllIlll.clone();
                llIlllIIllllIII.setAmount(llIlllIIllllIIl);
                "".length();
                llIlllIIlllIlII.add(llIlllIIllllIII);
            }
            ++llIlllIIllIlllI;
            "".length();
            if (((125 ^ 52) & ~(17 ^ 88)) == 0) continue;
            return null;
        }
        return llIlllIIlllIlII;
    }

    public static int NombreEntre(int llIllllIIIIIlIl, int llIlllIllllllll) {
        return llIllllIIIIIlIl + (int)(Math.random() * (double)(llIlllIllllllll - llIllllIIIIIlIl + lIIlIII[1]));
    }

    public static boolean isArmor(ItemStack llIlllllIIIIlIl) {
        boolean bl;
        int llIlllllIIIIllI = Utils.getArmorSlot(llIlllllIIIIlIl);
        if (Utils.lllllIIl(llIlllllIIIIllI, lIIlIII[3])) {
            bl = lIIlIII[1];
            "".length();
            if (" ".length() < ((127 ^ 71) & ~(34 ^ 26))) {
                return (boolean)((26 ^ 92) & ~(117 ^ 51) & ~((95 ^ 118) & ~(178 ^ 155)));
            }
        } else {
            bl = lIIlIII[0];
        }
        return bl;
    }

    public static Entity getEntityFromUUID(UUID llIllIIlIIIIIlI, World llIllIIlIIIIIIl) {
        Iterator llIllIIIllllllI = llIllIIlIIIIIIl.getEntities().iterator();
        while (Utils.llllIlIl((int)llIllIIIllllllI.hasNext())) {
            Entity llIllIIlIIIIIll = (Entity)llIllIIIllllllI.next();
            if (Utils.llllIlll(llIllIIlIIIIIll.getUniqueId(), llIllIIlIIIIIlI)) {
                return llIllIIlIIIIIll;
            }
            "".length();
            if ("  ".length() > " ".length()) continue;
            return null;
        }
        return null;
    }

    public static ItemStack createColoredTeamArmor(Material llIllllIIlIlIII, JocEquips.Equip llIllllIIlIIlll) {
        Color llIllllIIlIIllI;
        if (Utils.lllllIll(llIllllIIlIIlll)) {
            Color llIllllIIlIlIIl = Color.WHITE;
            "".length();
            if ("   ".length() < 0) {
                return null;
            }
        } else {
            llIllllIIlIIllI = ColorConverter.dyeToColor(llIllllIIlIIlll.getColor());
        }
        return Utils.createColoredArmor(llIllllIIlIlIII, llIllllIIlIIllI);
    }

    public static ArrayList<BlockFace> getFacesVert() {
        ArrayList<BlockFace> llIlllIIIllllll = new ArrayList<BlockFace>();
        "".length();
        llIlllIIIllllll.add(BlockFace.UP);
        "".length();
        llIlllIIIllllll.add(BlockFace.DOWN);
        return llIlllIIIllllll;
    }

    public static ArrayList<Location> getSphereLocations(Location llIllIllIIIIIIl, Double llIllIllIIIIllI, boolean llIllIlIlllllll) {
        ArrayList<Location> llIllIllIIIIlII = new ArrayList<Location>();
        World llIllIllIIIIIll = llIllIllIIIIIIl.getWorld();
        Cuboid llIllIllIIIIIlI = Utils.getSquareCuboid(llIllIllIIIIIIl, llIllIllIIIIllI);
        Iterator<Block> llIllIlIllllIll = llIllIllIIIIIlI.getBlocks().iterator();
        while (Utils.llllIlIl((int)llIllIlIllllIll.hasNext())) {
            Block llIllIllIIIlIIl = llIllIlIllllIll.next();
            if (Utils.llllIlIl((int)Utils.isInSphere(llIllIllIIIIIIl, llIllIllIIIIllI, llIllIllIIIlIIl))) {
                if (Utils.llllIlIl((int)llIllIlIlllllll) && Utils.llllIlIl((int)Utils.isInSphere(llIllIllIIIIIIl, llIllIllIIIIllI - 1.0, llIllIllIIIlIIl))) {
                    "".length();
                    if (((112 ^ 64) & ~(172 ^ 156)) >= ((110 ^ 80) & ~(155 ^ 165))) continue;
                    return null;
                }
                "".length();
                llIllIllIIIIlII.add(llIllIllIIIlIIl.getLocation());
            }
            "".length();
            if (-"   ".length() <= 0) continue;
            return null;
        }
        return llIllIllIIIIlII;
    }

    private static boolean llllIlll(Object object, Object object2) {
        return object == object2;
    }

    public static ItemStack getItemInHand(LivingEntity llIlllllIIllIII) {
        if (Utils.llllIlIl(llIlllllIIllIII instanceof Player)) {
            return ((Player)llIlllllIIllIII).getItemInHand();
        }
        EntityEquipment llIlllllIIllIIl = llIlllllIIllIII.getEquipment();
        if (Utils.lllllIII((Object)llIlllllIIllIIl)) {
            return llIlllllIIllIIl.getItemInHand();
        }
        return null;
    }

    public static LivingEntity getNearestEntity(Location llIllIlIIllIIll, ArrayList<LivingEntity> llIllIlIIlIlIll) {
        if (Utils.llllIlII(llIllIlIIlIlIll.size())) {
            return null;
        }
        LivingEntity llIllIlIIllIIII = llIllIlIIlIlIll.get(lIIlIII[0]);
        double llIllIlIIlIlllI = llIllIlIIllIIII.getLocation().distance(llIllIlIIllIIll);
        Iterator<LivingEntity> llIllIlIIlIIllI = llIllIlIIlIlIll.iterator();
        while (Utils.llllIlIl((int)llIllIlIIlIIllI.hasNext())) {
            LivingEntity llIllIlIIllIlII = llIllIlIIlIIllI.next();
            double llIllIlIIllIlIl = llIllIlIIllIlII.getLocation().distance(llIllIlIIllIIll);
            if (Utils.lIIIIIIII(Utils.lIIIIlIlI(llIllIlIIllIlIl, llIllIlIIlIlllI))) {
                llIllIlIIlIlllI = llIllIlIIllIlIl;
                llIllIlIIllIIII = llIllIlIIllIlII;
            }
            "".length();
            if (-" ".length() < " ".length()) continue;
            return null;
        }
        return llIllIlIIllIIII;
    }

    public static ItemStack setItemName(ItemStack lllIIIIIllIlIll, String lllIIIIIllIIlIl) {
        return Utils.setItemNameAndLore(lllIIIIIllIlIll, lllIIIIIllIIlIl, new String[lIIlIII[0]]);
    }

    private static int lIIIIlIlI(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean lIIIlIlll(int n) {
        return n >= 0;
    }

    public static boolean testPointUpDown(Float llIlIlIllIIIlll, double llIlIlIllIIIllI, double llIlIlIllIIIIlI) {
        boolean bl;
        if (Utils.lIIIIIIII(Utils.lIIIllllI(llIlIlIllIIIllI, llIlIlIllIIIlll.floatValue())) && !Utils.lIIIIIIII(Utils.lIIIlllll(llIlIlIllIIIIlI, llIlIlIllIIIlll.floatValue())) || Utils.lIIIlIlll(Utils.lIIIlllll(llIlIlIllIIIllI, llIlIlIllIIIlll.floatValue())) && Utils.lIIIIIIII(Utils.lIIIllllI(llIlIlIllIIIIlI, llIlIlIllIIIlll.floatValue()))) {
            bl = lIIlIII[1];
            "".length();
            if ("  ".length() < "  ".length()) {
                return (boolean)((152 ^ 142 ^ (80 ^ 37)) & (209 ^ 199 ^ (50 ^ 71) ^ -" ".length()));
            }
        } else {
            bl = lIIlIII[0];
        }
        return bl;
    }

    public static Location lookAt(Location llIlIlIlIlIlIII, Location llIlIlIlIlIIlll) {
        llIlIlIlIlIlIII = llIlIlIlIlIlIII.clone();
        double llIlIlIlIlIIllI = llIlIlIlIlIIlll.getX() - llIlIlIlIlIlIII.getX();
        double llIlIlIlIlIIlIl = llIlIlIlIlIIlll.getY() - llIlIlIlIlIlIII.getY();
        double llIlIlIlIlIIlII = llIlIlIlIlIIlll.getZ() - llIlIlIlIlIlIII.getZ();
        if (Utils.llllIlIl(Utils.lIIlIIIII(llIlIlIlIlIIllI, 0.0))) {
            if (Utils.lIIIIIIII(Utils.lIIlIIIIl(llIlIlIlIlIIllI, 0.0))) {
                llIlIlIlIlIlIII.setYaw(4.712389f);
                "".length();
                if (null != null) {
                    return null;
                }
            } else {
                llIlIlIlIlIlIII.setYaw(1.5707964f);
            }
            llIlIlIlIlIlIII.setYaw(llIlIlIlIlIlIII.getYaw() - (float)Math.atan(llIlIlIlIlIIlII / llIlIlIlIlIIllI));
            "".length();
            if ((153 ^ 157) < "   ".length()) {
                return null;
            }
        } else if (Utils.lIIIIIIII(Utils.lIIlIIIIl(llIlIlIlIlIIlII, 0.0))) {
            llIlIlIlIlIlIII.setYaw(3.1415927f);
        }
        double llIlIlIlIlIIIll = Math.sqrt(Math.pow(llIlIlIlIlIIllI, 2.0) + Math.pow(llIlIlIlIlIIlII, 2.0));
        llIlIlIlIlIlIII.setPitch((float)(-Math.atan(llIlIlIlIlIIlIl / llIlIlIlIlIIIll)));
        llIlIlIlIlIlIII.setYaw(-llIlIlIlIlIlIII.getYaw() * 180.0f / 3.1415927f);
        llIlIlIlIlIlIII.setPitch(llIlIlIlIlIlIII.getPitch() * 180.0f / 3.1415927f);
        return llIlIlIlIlIlIII;
    }

    public static ArrayList<String> readHumanReadableList(String llIlIllIlIlIlII) {
        String[] llIlIllIlIlIlIl;
        ArrayList<String> llIlIllIlIlIlll = new ArrayList<String>();
        String[] llIlIllIlIlIIIl = llIlIllIlIlIlIl = llIlIllIlIlIlII.split(llIIlI[lIIlIII[18]]);
        int llIlIllIlIlIIII = llIlIllIlIlIIIl.length;
        int llIlIllIlIIllll = lIIlIII[0];
        while (Utils.lIIIIIIll(llIlIllIlIIllll, llIlIllIlIlIIII)) {
            String llIlIllIlIllIll = llIlIllIlIlIIIl[llIlIllIlIIllll];
            String[] llIlIllIlIlllIl = llIlIllIlIllIll.split(llIIlI[lIIlIII[19]]);
            "".length();
            llIlIllIlIlIlll.add(llIlIllIlIlllIl[lIIlIII[1]].substring(lIIlIII[1]));
            ++llIlIllIlIIllll;
            "".length();
            if ("   ".length() != 0) continue;
            return null;
        }
        return llIlIllIlIlIlll;
    }

    public static <T> List<T> removeDuplicates(List<T> llIlIlIllIlIIIl) {
        return Arrays.asList(new LinkedHashSet<T>(llIlIlIllIlIIIl).toArray());
    }

    public static /* varargs */ ItemStack setItemNameAndLore(ItemStack lllIIIIIllllllI, String lllIIIIIlllllIl, String ... lllIIIIIllllIll) {
        ItemMeta lllIIIIIllllIlI = lllIIIIIllllllI.getItemMeta();
        if (Utils.llllIlII((int)lllIIIIIlllllIl.isEmpty())) {
            lllIIIIIllllIlI.setDisplayName(lllIIIIIlllllIl);
        }
        lllIIIIIllllIlI.setLore(Arrays.asList(lllIIIIIllllIll));
        "".length();
        lllIIIIIllllllI.setItemMeta(lllIIIIIllllIlI);
        return lllIIIIIllllllI;
    }

    public static boolean Possibilitat(int llIlllIlllIIIlI, int llIlllIllIlllll) {
        return Utils.Possibilitat((double)llIlllIlllIIIlI, (double)llIlllIllIlllll);
    }

    public static ArrayList<ItemStack> getBrewingItems() {
        ArrayList<ItemStack> llIlIlllIIllIII = new ArrayList<ItemStack>();
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.NETHER_WARTS));
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.GLOWSTONE));
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.REDSTONE));
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.SPIDER_EYE));
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.MAGMA_CREAM));
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.BLAZE_POWDER));
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.SUGAR));
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.GHAST_TEAR));
        "".length();
        llIlIlllIIllIII.add(new ItemStack(Material.GOLDEN_CARROT));
        return llIlIlllIIllIII;
    }

    public static ArrayList<BlockFace> getFacesNSEW() {
        ArrayList<BlockFace> llIlllIIlIIIIlI = new ArrayList<BlockFace>();
        "".length();
        llIlllIIlIIIIlI.add(BlockFace.NORTH);
        "".length();
        llIlllIIlIIIIlI.add(BlockFace.SOUTH);
        "".length();
        llIlllIIlIIIIlI.add(BlockFace.EAST);
        "".length();
        llIlllIIlIIIIlI.add(BlockFace.WEST);
        return llIlllIIlIIIIlI;
    }

    private static int lIIIIllll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public static void giveItemStack(ItemStack llIllllIlllllll, Player llIllllIllllIlI) {
        int llIllllIlllllIl = Utils.isArmor(llIllllIlllllll);
        int llIllllIlllllII = Utils.getArmorSlot(llIllllIlllllll);
        if (Utils.llllIlIl(llIllllIlllllIl)) {
            Utils.EstablirArmadura(llIllllIllllIlI, llIllllIlllllll, llIllllIlllllII);
            "".length();
            if ("  ".length() >= (227 ^ 175 ^ (84 ^ 28))) {
                return;
            }
        } else {
            ItemStack[] arritemStack = new ItemStack[lIIlIII[1]];
            arritemStack[Utils.lIIlIII[0]] = llIllllIlllllll;
            "".length();
            llIllllIllllIlI.getInventory().addItem(arritemStack);
        }
    }

    public static Vector rotateVector(Vector llIlIlIIllIlIII, Vector llIlIlIIllIIlll, double llIlIlIIllIIllI) {
        Vec llIlIlIIllIlIlI = new Vec(llIlIlIIllIIlll);
        Matrix llIlIlIIllIlIIl = new Matrix(llIlIlIIllIlIlI, llIlIlIIllIIllI);
        return new Vec(llIlIlIIllIlIII).rotate(llIlIlIIllIlIIl).getBukkitVector();
    }

    public static double NombreEntre(double llIlllIlllIllll, double llIlllIlllIlllI) {
        return llIlllIlllIllll + Math.random() * (llIlllIlllIlllI - llIlllIlllIllll + 1.0);
    }
}

