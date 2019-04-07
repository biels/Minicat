/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocCooperatiu;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class KingSkeletonChallenge
extends JocCooperatiu {
    private static final /* synthetic */ String[] lIlIllll;
    private static final /* synthetic */ int[] lIlllIIl;

    public static void spawnBoss(Location lIIlIllIlIlIlll) {
        Skeleton lIIlIllIlIlIllI = (Skeleton)lIIlIllIlIlIlll.getWorld().spawnEntity(lIIlIllIlIlIlll, EntityType.SKELETON);
        lIIlIllIlIlIllI.setMaxHealth(200.0);
        lIIlIllIlIlIllI.setHealth(200.0);
        lIIlIllIlIlIllI.setCanPickupItems(lIlllIIl[2]);
        lIIlIllIlIlIllI.setCustomName(lIlIllll[lIlllIIl[5]]);
        lIIlIllIlIlIllI.setRemoveWhenFarAway(lIlllIIl[2]);
        EntityEquipment lIIlIllIlIlIlIl = lIIlIllIlIlIllI.getEquipment();
        lIIlIllIlIlIlIl.setItemInHand(new ItemStack(Material.ENDER_PEARL));
        KingSkeletonChallenge.updateEquipment(lIIlIllIlIlIllI);
    }

    public KingSkeletonChallenge() {
        KingSkeletonChallenge lIIlIllIllllIIl;
    }

    public void onDamage(EntityDamageByEntityEvent lIIlIllIIllIIll, Skeleton lIIlIllIIllIllI) {
        KingSkeletonChallenge lIIlIllIIlllIII;
        World lIIlIllIIllIlIl = lIIlIllIIllIIll.getEntity().getWorld();
        if (KingSkeletonChallenge.lIllIlIIlI((int)lIIlIllIIlllIII.isBoss(lIIlIllIIllIllI))) {
            KingSkeletonChallenge.updateEquipment(lIIlIllIIllIllI);
            ItemStack lIIlIllIIlllIlI = lIIlIllIIllIllI.getEquipment().getItemInHand();
            ItemStack lIIlIllIIlllIIl = null;
            if (KingSkeletonChallenge.lIllIlIIlI((int)lIIlIllIIlllIlI.isSimilar(new ItemStack(Material.ENDER_PEARL)))) {
                lIIlIllIIlllIIl = new ItemStack(Material.EYE_OF_ENDER);
                lIIlIllIIllIlIl.playSound(lIIlIllIIllIllI.getEyeLocation(), Sound.BLOCK_WOOD_STEP, 2.0f, 1.1f);
                lIIlIllIIlllIII.teleportToEscape(lIIlIllIIllIllI);
            }
            if (KingSkeletonChallenge.lIllIlIIlI((int)lIIlIllIIlllIlI.isSimilar(new ItemStack(Material.EYE_OF_ENDER)))) {
                lIIlIllIIlllIIl = lIIlIllIIlllIII.getSpellItemStack(lIIlIllIIlllIII.getNextSpell());
                lIIlIllIIllIlIl.playSound(lIIlIllIIllIllI.getEyeLocation(), Sound.BLOCK_WOOD_BREAK, 2.5f, 1.55f);
            }
            if (KingSkeletonChallenge.lIllIllIll((Object)lIIlIllIIlllIIl)) {
                lIIlIllIIllIlIl.playSound(lIIlIllIIllIllI.getEyeLocation(), Sound.BLOCK_ANVIL_STEP, 2.5f, 1.2f);
                SpellType lIIlIllIIllllIl = lIIlIllIIlllIII.getTypeFromStack(lIIlIllIIlllIlI);
                Location lIIlIllIIllllII = lIIlIllIIllIllI.getLocation().clone().add(0.0, -1.0, 0.0);
                int lIIlIllIIlllIll = lIlllIIl[2];
                if (KingSkeletonChallenge.lIllIlllIl(KingSkeletonChallenge.lIllIllIII(lIIlIllIIllIllI.getHealth(), 100.0))) {
                    ++lIIlIllIIlllIll;
                }
                if (KingSkeletonChallenge.lIllIlllIl(KingSkeletonChallenge.lIllIllIII(lIIlIllIIllIllI.getHealth(), 30.0))) {
                    lIIlIllIIlllIll += 2;
                }
                if (KingSkeletonChallenge.lIllIlllIl(KingSkeletonChallenge.lIllIllIII(lIIlIllIIllIllI.getHealth(), 20.0))) {
                    ++lIIlIllIIlllIll;
                }
                switch (1.$SwitchMap$com$biel$lobby$mapes$jocs$KingSkeletonChallenge$SpellType[lIIlIllIIllllIl.ordinal()]) {
                    case 1: {
                        lIIlIllIIlllIII.spawnMobsOnTree(lIIlIllIIllllII, EntityType.BLAZE, lIlllIIl[1] + lIIlIllIIlllIll);
                        "".length();
                        if ("  ".length() > " ".length()) break;
                        return;
                    }
                    case 2: {
                        lIIlIllIIlllIII.spawnMobsOnTree(lIIlIllIIllllII, EntityType.THROWN_EXP_BOTTLE, lIlllIIl[6] + lIIlIllIIlllIll);
                        "".length();
                        if (null == null) break;
                        return;
                    }
                    case 3: {
                        lIIlIllIIlllIII.spawnMobsOnTree(lIIlIllIIllllII, EntityType.MAGMA_CUBE, lIlllIIl[4] + lIIlIllIIlllIll);
                        "".length();
                        if ((65 ^ 91 ^ (70 ^ 89)) != 0) break;
                        return;
                    }
                    case 4: {
                        Location lIIlIllIIlllllI = lIIlIllIIllIllI.getTarget().getLocation().add(Vector.getRandom().normalize().multiply(0.2));
                        "".length();
                        lIIlIllIIllIllI.getWorld().createExplosion(lIIlIllIIlllllI.getX(), lIIlIllIIlllllI.getY(), lIIlIllIIlllllI.getZ(), 3.0f + (float)(lIIlIllIIlllIll / lIlllIIl[7]), lIlllIIl[3], lIlllIIl[2]);
                        "".length();
                        if (-" ".length() <= -" ".length()) break;
                        return;
                    }
                    case 5: {
                        lIIlIllIIlllIII.spawnMobsOnTree(lIIlIllIIllllII, EntityType.SKELETON, lIlllIIl[5] + lIIlIllIIlllIll);
                        "".length();
                        if (-" ".length() == -" ".length()) break;
                        return;
                    }
                    case 6: {
                        lIIlIllIIlllIII.spawnMobsOnTree(lIIlIllIIllllII, EntityType.SLIME, lIlllIIl[1] + lIIlIllIIlllIll);
                        "".length();
                        if ("  ".length() >= 0) break;
                        return;
                    }
                    case 7: {
                        lIIlIllIIlllIII.spawnMobsOnTree(lIIlIllIIllllII, EntityType.WITCH, lIlllIIl[0] + lIIlIllIIlllIll);
                        "".length();
                        if (" ".length() == " ".length()) break;
                        return;
                    }
                    case 8: {
                        lIIlIllIIlllIII.spawnMobsOnTree(lIIlIllIIllllII, EntityType.SPIDER, lIlllIIl[1] + lIIlIllIIlllIll);
                        "".length();
                        if (((178 ^ 139) & ~(120 ^ 65)) >= ((242 ^ 169) & ~(83 ^ 8))) break;
                        return;
                    }
                    case 9: {
                        lIIlIllIIlllIII.spawnMobsOnTree(lIIlIllIIllllII, EntityType.ZOMBIE, lIlllIIl[6] + lIIlIllIIlllIll);
                        "".length();
                        if ((4 ^ 114 ^ (41 ^ 91)) >= 0) break;
                        return;
                    }
                }
                lIIlIllIIlllIIl = new ItemStack(Material.ENDER_PEARL);
            }
            lIIlIllIIllIllI.getEquipment().setItemInHand(lIIlIllIIlllIIl);
            "".length();
            lIIlIllIIllIllI.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, lIlllIIl[8], lIlllIIl[1]));
        }
    }

    private static void lIlIllIlII() {
        lIlIllll = new String[lIlllIIl[9]];
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[2]] = KingSkeletonChallenge.lIlIllIIIl("CXMBgLjbhAQ=", "JrkBe");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[3]] = KingSkeletonChallenge.lIlIllIIlI("", "GxMjg");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[0]] = KingSkeletonChallenge.lIlIllIIlI("JA1yIzsPBnI7OQQNNxw9D0E6CXIEDyYaMxVBM0g+AEEwCSYADT4Jcw==", "aaRhR");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[4]] = KingSkeletonChallenge.lIlIllIIIl("qZ8HjsMvzYE=", "cESLe");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[1]] = KingSkeletonChallenge.lIlIllIIll("g9S+pyDR0N55CkbYBOxv02aREVivBtZZ", "AfMeG");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[5]] = KingSkeletonChallenge.lIlIllIIlI("OiQiP1QCJik0EQUiIg==", "qMLXt");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[11]] = KingSkeletonChallenge.lIlIllIIIl("sedrJLF9xfP6poCW+0T4jQ==", "pGZTa");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[6]] = KingSkeletonChallenge.lIlIllIIll("SNoLKZi738g=", "HbYFv");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[7]] = KingSkeletonChallenge.lIlIllIIIl("GJC/M++ZSMOFb2mGteazAA==", "efoUv");
        KingSkeletonChallenge.lIlIllll[KingSkeletonChallenge.lIlllIIl[16]] = KingSkeletonChallenge.lIlIllIIlI("CDwkOSEr", "EUJPN");
    }

    public ItemStack getSpellItemStack(SpellType lIIlIllIIIIllII) {
        switch (1.$SwitchMap$com$biel$lobby$mapes$jocs$KingSkeletonChallenge$SpellType[lIIlIllIIIIllII.ordinal()]) {
            case 1: {
                return new ItemStack(Material.BLAZE_POWDER);
            }
            case 2: {
                return new ItemStack(Material.EXP_BOTTLE);
            }
            case 3: {
                return new ItemStack(Material.MAGMA_CREAM);
            }
            case 4: {
                return new ItemStack(Material.TNT);
            }
            case 5: {
                return new ItemStack(Material.SKULL_ITEM, lIlllIIl[3], lIlllIIl[2], Byte.valueOf(lIlllIIl[2]));
            }
            case 6: {
                return new ItemStack(Material.SLIME_BALL);
            }
            case 7: {
                return new ItemStack(Material.BREWING_STAND_ITEM);
            }
            case 8: {
                return new ItemStack(Material.SKULL_ITEM, lIlllIIl[3], lIlllIIl[2], Byte.valueOf(lIlllIIl[3]));
            }
            case 9: {
                return new ItemStack(Material.SKULL_ITEM, lIlllIIl[3], lIlllIIl[2], Byte.valueOf(lIlllIIl[0]));
            }
        }
        return new ItemStack(Material.BEDROCK);
    }

    @Override
    public String getGameName() {
        return lIlIllll[lIlllIIl[1]];
    }

    private static boolean lIlllIIIll(int n) {
        return n == 0;
    }

    private static void lIllIlIIII() {
        lIlllIIl = new int[17];
        KingSkeletonChallenge.lIlllIIl[0] = "  ".length();
        KingSkeletonChallenge.lIlllIIl[1] = 170 ^ 174;
        KingSkeletonChallenge.lIlllIIl[2] = (240 ^ 179 ^ (127 ^ 5)) & (152 ^ 142 ^ (172 ^ 131) ^ -" ".length());
        KingSkeletonChallenge.lIlllIIl[3] = " ".length();
        KingSkeletonChallenge.lIlllIIl[4] = "   ".length();
        KingSkeletonChallenge.lIlllIIl[5] = 60 + 122 - 179 + 137 ^ 51 + 120 - 100 + 66;
        KingSkeletonChallenge.lIlllIIl[6] = 150 ^ 145;
        KingSkeletonChallenge.lIlllIIl[7] = 32 + 14 - -140 + 4 ^ 110 + 22 - -18 + 32;
        KingSkeletonChallenge.lIlllIIl[8] = 188 + 101 - 96 + 47;
        KingSkeletonChallenge.lIlllIIl[9] = 30 ^ 20;
        KingSkeletonChallenge.lIlllIIl[10] = 15 + 31 - -51 + 56 ^ 103 + 28 - 41 + 59;
        KingSkeletonChallenge.lIlllIIl[11] = 16 ^ 70 ^ (216 ^ 136);
        KingSkeletonChallenge.lIlllIIl[12] = 73 + 37 - -43 + 53 ^ 165 + 35 - 154 + 147;
        KingSkeletonChallenge.lIlllIIl[13] = 126 ^ 117;
        KingSkeletonChallenge.lIlllIIl[14] = 17 + 129 - 43 + 33 ^ 135 + 29 - 74 + 55;
        KingSkeletonChallenge.lIlllIIl[15] = 156 ^ 131 ^ (108 ^ 99);
        KingSkeletonChallenge.lIlllIIl[16] = 160 ^ 169;
    }

    private static boolean lIlllllIlI(Object object, Object object2) {
        return object != object2;
    }

    private static int lIllIllIII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean lIllllllIl(int n, int n2) {
        return n > n2;
    }

    public SpellType getTypeFromStack(ItemStack lIIlIllIIIIIIII) {
        SpellType[] lIIlIlIllllllll = SpellType.values();
        int lIIlIlIlllllllI = lIIlIlIllllllll.length;
        int lIIlIlIllllllIl = lIlllIIl[2];
        while (KingSkeletonChallenge.lIlllIIIlI(lIIlIlIllllllIl, lIIlIlIlllllllI)) {
            KingSkeletonChallenge lIIlIllIIIIIIIl;
            SpellType lIIlIllIIIIIlII = lIIlIlIllllllll[lIIlIlIllllllIl];
            if (KingSkeletonChallenge.lIllIlIIlI((int)lIIlIllIIIIIIIl.getSpellItemStack(lIIlIllIIIIIlII).isSimilar(lIIlIllIIIIIIII))) {
                return lIIlIllIIIIIlII;
            }
            ++lIIlIlIllllllIl;
            "".length();
            if (null == null) continue;
            return null;
        }
        return null;
    }

    private static boolean lIlllllIll(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected void customJocIniciat() {
        KingSkeletonChallenge lIIlIllIllIIIII;
        lIIlIllIllIIIII.setBlockBreakPlace(lIlllIIl[2]);
        KingSkeletonChallenge.spawnBoss(lIIlIllIllIIIII.getBossSpawnLoc());
        lIIlIllIllIIIII.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIlIllll[lIlllIIl[3]]).append((Object)ChatColor.BOLD).append(lIlIllll[lIlllIIl[0]])));
        lIIlIllIllIIIII.world.setTime(15000L);
    }

    private static String lIlIllIIll(String lIIlIlIIllIIlll, String lIIlIlIIllIlIII) {
        try {
            SecretKeySpec lIIlIlIIllIllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlIlIIllIlIII.getBytes(StandardCharsets.UTF_8)), lIlllIIl[7]), "DES");
            Cipher lIIlIlIIllIlIll = Cipher.getInstance("DES");
            lIIlIlIIllIlIll.init(lIlllIIl[0], lIIlIlIIllIllII);
            return new String(lIIlIlIIllIlIll.doFinal(Base64.getDecoder().decode(lIIlIlIIllIIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIlIIllIlIlI) {
            lIIlIlIIllIlIlI.printStackTrace();
            return null;
        }
    }

    private static boolean lIllIlllIl(int n) {
        return n < 0;
    }

    public ArrayList<Location> getValidCircleLocations(Location lIIlIlIlllIIllI, Double lIIlIlIlllIIlIl, int lIIlIlIlllIlIll) {
        KingSkeletonChallenge lIIlIlIlllIIlll;
        ArrayList<Location> lIIlIlIlllIlIlI = new ArrayList<Location>();
        ArrayList<Location> lIIlIlIlllIlIIl = Utils.getLocationsCircle(lIIlIlIlllIIllI, lIIlIlIlllIIlIl, lIlllIIl[12]);
        "".length();
        lIIlIlIlllIlIIl.add(lIIlIlIlllIIlll.getBossSpawnLoc());
        while (KingSkeletonChallenge.lIlllIIIll(lIIlIlIlllIlIIl.size())) {
            "".length();
            lIIlIlIlllIlIIl.add(lIIlIlIlllIIllI);
            "".length();
            if (-"   ".length() < 0) continue;
            return null;
        }
        int lIIlIlIlllIlIII = lIlllIIl[2];
        while (KingSkeletonChallenge.lIlllIIIlI(lIIlIlIlllIlIlI.size(), lIIlIlIlllIlIll)) {
            Location lIIlIlIllllIIIl = lIIlIlIlllIlIIl.get(Utils.NombreEntre(lIlllIIl[2], lIIlIlIlllIlIIl.size() - lIlllIIl[3]));
            Block lIIlIlIllllIIII = lIIlIlIllllIIIl.getBlock();
            Material lIIlIlIlllIllll = lIIlIlIllllIIII.getType();
            if ((!KingSkeletonChallenge.lIlllllIlI((Object)lIIlIlIlllIllll, (Object)Material.GRASS) || KingSkeletonChallenge.lIlllllIll((Object)lIIlIlIlllIllll, (Object)Material.REDSTONE_LAMP_ON)) && KingSkeletonChallenge.lIllIlIIlI((int)lIIlIlIllllIIII.getRelative(BlockFace.UP).isEmpty())) {
                "".length();
                lIIlIlIlllIlIlI.add(lIIlIlIllllIIIl);
            }
            if (KingSkeletonChallenge.lIllllllIl(++lIIlIlIlllIlIII, lIIlIlIlllIlIll * lIlllIIl[1])) {
                "".length();
                lIIlIlIlllIlIlI.add(lIIlIlIlllIIllI);
            }
            "".length();
            if ("   ".length() < (7 + 83 - -39 + 60 ^ 184 + 64 - 239 + 176)) continue;
            return null;
        }
        return lIIlIlIlllIlIlI;
    }

    private Location getBossSpawnLoc() {
        KingSkeletonChallenge lIIlIllIlIllllI;
        return lIIlIllIlIllllI.pMapaActual().ObtenirLocation(lIlIllll[lIlllIIl[4]], lIIlIllIlIllllI.world);
    }

    public static void updateEquipment(Skeleton lIIlIllIlIIlllI) {
        EntityEquipment lIIlIllIlIIllIl = lIIlIllIlIIlllI.getEquipment();
        lIIlIllIlIIllIl.setHelmet(new ItemStack(Material.GOLD_HELMET));
        Color lIIlIllIlIIllII = Color.BLUE;
        if (KingSkeletonChallenge.lIllIlIlll(KingSkeletonChallenge.lIllIlIllI(lIIlIllIlIIlllI.getHealth(), 100.0))) {
            lIIlIllIlIIllII = Color.ORANGE;
        }
        if (KingSkeletonChallenge.lIllIlIlll(KingSkeletonChallenge.lIllIlIllI(lIIlIllIlIIlllI.getHealth(), 60.0))) {
            lIIlIllIlIIllII = Color.YELLOW;
        }
        if (KingSkeletonChallenge.lIllIlIlll(KingSkeletonChallenge.lIllIlIllI(lIIlIllIlIIlllI.getHealth(), 30.0))) {
            lIIlIllIlIIllII = Color.RED;
        }
        lIIlIllIlIIllIl.setChestplate(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, lIIlIllIlIIllII));
        lIIlIllIlIIllIl.setLeggings(Utils.createColoredArmor(Material.LEATHER_LEGGINGS, lIIlIllIlIIllII));
        lIIlIllIlIIllIl.setBoots(Utils.createColoredArmor(Material.LEATHER_BOOTS, lIIlIllIlIIllII));
    }

    private static boolean lIllIlIIlI(int n) {
        return n != 0;
    }

    public SpellType getNextSpell() {
        SpellType lIIlIllIIIlIIII = SpellType.values()[Utils.NombreEntre(lIlllIIl[2], SpellType.values().length - lIlllIIl[3])];
        if (KingSkeletonChallenge.lIllIlIIlI((int)Utils.Possibilitat(lIlllIIl[0]))) {
            return SpellType.MINEFIELD;
        }
        if (KingSkeletonChallenge.lIllIlIIlI((int)Utils.Possibilitat(lIlllIIl[0]))) {
            return SpellType.BLAZE;
        }
        if (KingSkeletonChallenge.lIllIlIIlI((int)Utils.Possibilitat(lIlllIIl[0]))) {
            return SpellType.ZOMBIE;
        }
        return lIIlIllIIIlIIII;
    }

    protected void onEntityDamageByEntity(EntityDamageByEntityEvent lIIlIlIlIIIlIll, Entity lIIlIlIlIIIlIlI, Entity lIIlIlIlIIIllIl) {
        KingSkeletonChallenge lIIlIlIlIIIllII;
        super.onEntityDamageByEntity(lIIlIlIlIIIlIll, lIIlIlIlIIIlIlI, lIIlIlIlIIIllIl);
        if (KingSkeletonChallenge.lIllIlIIlI(lIIlIlIlIIIlIlI instanceof Skeleton)) {
            lIIlIlIlIIIllII.onDamage(lIIlIlIlIIIlIll, (Skeleton)lIIlIlIlIIIlIlI);
        }
    }

    @Override
    protected void customJocFinalitzat() {
    }

    protected void onEntityDeath(EntityDeathEvent lIIlIlIlIIlllII, Entity lIIlIlIlIIllIll) {
        KingSkeletonChallenge lIIlIlIlIlIIIII;
        Skeleton lIIlIlIlIlIIIIl;
        super.onEntityDeath(lIIlIlIlIIlllII, lIIlIlIlIIllIll);
        if (KingSkeletonChallenge.lIllIlIIlI(lIIlIlIlIIllIll instanceof Skeleton) && KingSkeletonChallenge.lIllIlIIlI((int)lIIlIlIlIlIIIII.isBoss(lIIlIlIlIlIIIIl = (Skeleton)lIIlIlIlIIllIll))) {
            List lIIlIlIlIlIIIlI = lIIlIlIlIlIIIIl.getNearbyEntities(20.0, 10.0, 20.0);
            Iterator lIIlIlIlIIllIII = lIIlIlIlIlIIIlI.iterator();
            while (KingSkeletonChallenge.lIllIlIIlI((int)lIIlIlIlIIllIII.hasNext())) {
                LivingEntity lIIlIlIlIlIIlIl;
                String lIIlIlIlIlIIlII;
                Entity lIIlIlIlIlIIIll = (Entity)lIIlIlIlIIllIII.next();
                if (KingSkeletonChallenge.lIllIlIIlI(lIIlIlIlIlIIIll instanceof LivingEntity) && KingSkeletonChallenge.lIllllllll(lIIlIlIlIlIIlII = (lIIlIlIlIlIIlIl = (LivingEntity)lIIlIlIlIlIIIll).getCustomName()) && KingSkeletonChallenge.lIllIlIIlI((int)lIIlIlIlIlIIlII.equals(lIlIllll[lIlllIIl[16]]))) {
                    lIIlIlIlIlIIlIl.setHealth(0.0);
                }
                "".length();
                if (null == null) continue;
                return;
            }
            lIIlIlIlIlIIIII.dropLoot(lIIlIlIlIlIIIIl.getLocation());
        }
    }

    public void spawnMobsOnTree(Location lIIlIlIllIIlIIl, EntityType lIIlIlIllIIllIl, int lIIlIlIllIIIlll) {
        KingSkeletonChallenge lIIlIlIllIIllll;
        ArrayList<Location> lIIlIlIllIIlIll = lIIlIlIllIIllll.getValidCircleLocations(lIIlIlIllIIlIIl, (double)Utils.NombreEntre(lIlllIIl[11], lIlllIIl[13]) + 0.4, lIIlIlIllIIIlll);
        Iterator<Location> lIIlIlIllIIIlIl = lIIlIlIllIIlIll.iterator();
        while (KingSkeletonChallenge.lIllIlIIlI((int)lIIlIlIllIIIlIl.hasNext())) {
            Location lIIlIlIllIlIIII = lIIlIlIllIIIlIl.next();
            World lIIlIlIllIlIIlI = lIIlIlIllIIlIIl.getWorld();
            Entity lIIlIlIllIlIIIl = lIIlIlIllIlIIlI.spawnEntity(lIIlIlIllIlIIII.clone().add(0.0, 1.0, 0.0), lIIlIlIllIIllIl);
            if (KingSkeletonChallenge.lIllIlIIlI(lIIlIlIllIlIIIl instanceof LivingEntity)) {
                LivingEntity lIIlIlIllIlIIll = (LivingEntity)lIIlIlIllIlIIIl;
                lIIlIlIllIlIIll.setCustomName(lIlIllll[lIlllIIl[6]]);
                if (KingSkeletonChallenge.lIllIlIIlI(lIIlIlIllIlIIll instanceof Skeleton)) {
                    lIIlIlIllIlIIll.getEquipment().setItemInHand(new ItemStack(Material.BOW));
                }
            }
            "".length();
            if ((("  ".length() ^ (112 ^ 67)) & (70 + 6 - -165 + 3 ^ 34 + 160 - 137 + 140 ^ -" ".length())) <= (144 + 40 - 55 + 53 ^ 9 + 26 - -24 + 119)) continue;
            return;
        }
    }

    private static String lIlIllIIlI(String lIIlIlIIllllllI, String lIIlIlIIllllIII) {
        lIIlIlIIllllllI = new String(Base64.getDecoder().decode(lIIlIlIIllllllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIlIlIIlllllII = new StringBuilder();
        char[] lIIlIlIIllllIll = lIIlIlIIllllIII.toCharArray();
        int lIIlIlIIllllIlI = lIlllIIl[2];
        char[] lIIlIlIIlllIlII = lIIlIlIIllllllI.toCharArray();
        int lIIlIlIIlllIIll = lIIlIlIIlllIlII.length;
        int lIIlIlIIlllIIlI = lIlllIIl[2];
        while (KingSkeletonChallenge.lIlllIIIlI(lIIlIlIIlllIIlI, lIIlIlIIlllIIll)) {
            char lIIlIlIIlllllll = lIIlIlIIlllIlII[lIIlIlIIlllIIlI];
            "".length();
            lIIlIlIIlllllII.append((char)(lIIlIlIIlllllll ^ lIIlIlIIllllIll[lIIlIlIIllllIlI % lIIlIlIIllllIll.length]));
            ++lIIlIlIIllllIlI;
            ++lIIlIlIIlllIIlI;
            "".length();
            if ((41 ^ 88 ^ (82 ^ 38)) != 0) continue;
            return null;
        }
        return String.valueOf(lIIlIlIIlllllII);
    }

    public boolean isBoss(Skeleton lIIlIllIIIlIlII) {
        if (KingSkeletonChallenge.lIllIllIll((Object)lIIlIllIIIlIlII)) {
            return lIlllIIl[2];
        }
        String lIIlIllIIIlIlIl = lIIlIllIIIlIlII.getCustomName();
        if (KingSkeletonChallenge.lIllIllIll(lIIlIllIIIlIlIl)) {
            return lIlllIIl[2];
        }
        return lIIlIllIIIlIlIl.equalsIgnoreCase(lIlIllll[lIlllIIl[11]]);
    }

    public void dropLoot(Location lIIlIlIlIllIIll) {
        KingSkeletonChallenge lIIlIlIlIlllIlI;
        ArrayList<ItemStack> lIIlIlIlIlllIII = new ArrayList<ItemStack>();
        "".length();
        lIIlIlIlIlllIII.add(Utils.getRandomPotion());
        "".length();
        lIIlIlIlIlllIII.add(new ItemStack(Material.ICE, lIlllIIl[9]));
        "".length();
        lIIlIlIlIlllIII.add(new ItemStack(Material.TNT, lIlllIIl[14]));
        "".length();
        lIIlIlIlIlllIII.add(new ItemStack(Material.ENDER_PEARL, Utils.NombreEntre(lIlllIIl[7], lIlllIIl[15])));
        ItemStack lIIlIlIlIllIlll = new ItemStack(Material.GOLD_HELMET);
        lIIlIlIlIllIlll.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lIlllIIl[16]);
        lIIlIlIlIllIlll.addUnsafeEnchantment(Enchantment.DURABILITY, lIlllIIl[16]);
        "".length();
        lIIlIlIlIlllIII.add(lIIlIlIlIllIlll);
        lIIlIlIlIllIIll.getBlock().setType(Material.GOLD_BLOCK);
        Location lIIlIlIlIllIllI = lIIlIlIlIllIIll.add(0.0, 1.0, 0.0);
        Block lIIlIlIlIllIlIl = lIIlIlIlIllIllI.getBlock();
        lIIlIlIlIllIlIl.setType(Material.CHEST);
        Utils.fillChestRandomly(lIIlIlIlIllIlIl, lIIlIlIlIlllIII);
        lIIlIlIlIlllIlI.sendGlobalMessage(lIlIllll[lIlllIIl[7]]);
        lIIlIlIlIlllIlI.JocFinalitzat();
    }

    private static int lIllIlIllI(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    @Override
    protected void teletransportarTothom() {
        KingSkeletonChallenge lIIlIllIllIlIII;
        Iterator<Player> lIIlIllIllIIllI = lIIlIllIllIlIII.getPlayers().iterator();
        while (KingSkeletonChallenge.lIllIlIIlI((int)lIIlIllIllIIllI.hasNext())) {
            Player lIIlIllIllIlIIl = lIIlIllIllIIllI.next();
            ArrayList<Location> lIIlIllIllIlIll = lIIlIllIllIlIII.pMapaActual().ObtenirLocations(lIlIllll[lIlllIIl[2]], lIIlIllIllIlIII.world);
            Location lIIlIllIllIlIlI = new Location(lIIlIllIllIlIII.world, (double)Utils.NombreEntre(lIIlIllIllIlIll.get(lIlllIIl[2]).getBlockX(), lIIlIllIllIlIll.get(lIlllIIl[3]).getBlockX()), lIIlIllIllIlIll.get(lIlllIIl[2]).getY() + 1.0, (double)Utils.NombreEntre(lIIlIllIllIlIll.get(lIlllIIl[2]).getBlockZ(), lIIlIllIllIlIll.get(lIlllIIl[3]).getBlockZ()));
            "".length();
            lIIlIllIllIlIIl.teleport(lIIlIllIllIlIlI);
            "".length();
            if (-(56 + 88 - 112 + 106 ^ 100 + 9 - 36 + 70) < 0) continue;
            return;
        }
    }

    private static String lIlIllIIIl(String lIIlIlIIlIlllII, String lIIlIlIIlIllIIl) {
        try {
            SecretKeySpec lIIlIlIIlIlllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIlIlIIlIllIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIlIlIIlIllllI = Cipher.getInstance("Blowfish");
            lIIlIlIIlIllllI.init(lIlllIIl[0], lIIlIlIIlIlllll);
            return new String(lIIlIlIIlIllllI.doFinal(Base64.getDecoder().decode(lIIlIlIIlIlllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIlIIlIlllIl) {
            lIIlIlIIlIlllIl.printStackTrace();
            return null;
        }
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIIlIllIlllIlIl) {
        ArrayList<ItemStack> lIIlIllIlllIlII = new ArrayList<ItemStack>();
        ItemStack lIIlIllIlllIIll = new ItemStack(Material.DIAMOND_SWORD);
        lIIlIllIlllIIll.addEnchantment(Enchantment.DAMAGE_ALL, lIlllIIl[0]);
        "".length();
        lIIlIllIlllIlII.add(lIIlIllIlllIIll);
        "".length();
        lIIlIllIlllIlII.add(new ItemStack(Material.DIAMOND_HELMET));
        "".length();
        lIIlIllIlllIlII.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
        "".length();
        lIIlIllIlllIlII.add(new ItemStack(Material.DIAMOND_LEGGINGS));
        "".length();
        lIIlIllIlllIlII.add(new ItemStack(Material.GOLDEN_APPLE, lIlllIIl[1]));
        "".length();
        lIIlIllIlllIlII.add(Utils.getRandomPotion());
        "".length();
        lIIlIllIlllIlII.add(Utils.getRandomPotion());
        return lIIlIllIlllIlII;
    }

    private static boolean lIllIllIll(Object object) {
        return object == null;
    }

    private static boolean lIllllllll(Object object) {
        return object != null;
    }

    private static boolean lIllIlIlll(int n) {
        return n <= 0;
    }

    public void teleportToEscape(Skeleton lIIlIllIIIllllI) {
        KingSkeletonChallenge lIIlIllIIlIIIll;
        Location lIIlIllIIlIIIIl = lIIlIllIIIllllI.getLocation().clone().add(0.0, -1.0, 0.0);
        ArrayList<Location> lIIlIllIIlIIIII = lIIlIllIIlIIIll.getValidCircleLocations(lIIlIllIIlIIIIl, (double)Utils.NombreEntre(lIlllIIl[9], lIlllIIl[10]) + 0.4, lIlllIIl[3]);
        Iterator<Location> lIIlIllIIIllIll = lIIlIllIIlIIIII.iterator();
        while (KingSkeletonChallenge.lIllIlIIlI((int)lIIlIllIIIllIll.hasNext())) {
            Location lIIlIllIIlIIlII = lIIlIllIIIllIll.next();
            "".length();
            lIIlIllIIIllllI.teleport(lIIlIllIIlIIlII.add(0.0, 1.0, 0.0));
            "".length();
            if (((219 ^ 190 ^ (123 ^ 33)) & (77 ^ 22 ^ (96 ^ 4) ^ -" ".length())) == 0) continue;
            return;
        }
    }

    private static boolean lIlllIIIlI(int n, int n2) {
        return n < n2;
    }

    static {
        KingSkeletonChallenge.lIllIlIIII();
        KingSkeletonChallenge.lIlIllIlII();
    }

    public static final class SpellType
    extends Enum<SpellType> {
        public static final /* synthetic */ /* enum */ SpellType WITCH;
        public static final /* synthetic */ /* enum */ SpellType MINEFIELD;
        public static final /* synthetic */ /* enum */ SpellType MAGMA_CUBE;
        private static final /* synthetic */ int[] lIIIlIIII;
        public static final /* synthetic */ /* enum */ SpellType ZOMBIE;
        public static final /* synthetic */ /* enum */ SpellType SLIME;
        public static final /* synthetic */ /* enum */ SpellType BONUS;
        public static final /* synthetic */ /* enum */ SpellType SKELETON;
        private static final /* synthetic */ SpellType[] $VALUES;
        public static final /* synthetic */ /* enum */ SpellType BLAZE;
        private static final /* synthetic */ String[] lllIllIl;
        public static final /* synthetic */ /* enum */ SpellType WITHER;

        private SpellType() {
            SpellType llllIIIIIllIII;
        }

        private static String lllIIIIIIl(String lllIlllllllIII, String lllIllllllIlll) {
            lllIlllllllIII = new String(Base64.getDecoder().decode(lllIlllllllIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lllIlllllllIll = new StringBuilder();
            char[] lllIlllllllIlI = lllIllllllIlll.toCharArray();
            int lllIlllllllIIl = lIIIlIIII[0];
            char[] lllIllllllIIll = lllIlllllllIII.toCharArray();
            int lllIllllllIIlI = lllIllllllIIll.length;
            int lllIllllllIIIl = lIIIlIIII[0];
            while (SpellType.lllllIlIIl(lllIllllllIIIl, lllIllllllIIlI)) {
                char lllIlllllllllI = lllIllllllIIll[lllIllllllIIIl];
                "".length();
                lllIlllllllIll.append((char)(lllIlllllllllI ^ lllIlllllllIlI[lllIlllllllIIl % lllIlllllllIlI.length]));
                ++lllIlllllllIIl;
                ++lllIllllllIIIl;
                "".length();
                if ("   ".length() >= "  ".length()) continue;
                return null;
            }
            return String.valueOf(lllIlllllllIll);
        }

        private static void lllIIIIlII() {
            lllIllIl = new String[lIIIlIIII[9]];
            SpellType.lllIllIl[SpellType.lIIIlIIII[0]] = SpellType.llIlllllll("Fjj382jHfEQ=", "eWFZX");
            SpellType.lllIllIl[SpellType.lIIIlIIII[1]] = SpellType.lllIIIIIIl("DjkGEyIR", "TvKQk");
            SpellType.lllIllIl[SpellType.lIIIlIIII[2]] = SpellType.lllIIIIIIl("PD8PIh87OwQ=", "otJnZ");
            SpellType.lllIllIl[SpellType.lIIIlIIII[3]] = SpellType.lllIIIIIIl("PwEZBCA=", "hHMGh");
            SpellType.lllIllIl[SpellType.lIIIlIIII[4]] = SpellType.llIlllllll("NlWP0awL4d4=", "OTyev");
            SpellType.lllIllIl[SpellType.lIIIlIIII[5]] = SpellType.lllIIIIIlI("fEZg/FnoH7Q=", "ScnGd");
            SpellType.lllIllIl[SpellType.lIIIlIIII[6]] = SpellType.lllIIIIIlI("pPl+tb0o+cg3cS6EquNkig==", "adjzp");
            SpellType.lllIllIl[SpellType.lIIIlIIII[7]] = SpellType.lllIIIIIlI("ptm3kkmnVtI=", "WleFB");
            SpellType.lllIllIl[SpellType.lIIIlIIII[8]] = SpellType.llIlllllll("QuYPSBwAfcVsaP22Wuf+Gg==", "SpCxz");
        }

        private static String llIlllllll(String llllIIIIIIllIl, String llllIIIIIIlIlI) {
            try {
                SecretKeySpec llllIIIIIlIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIIIIIIlIlI.getBytes(StandardCharsets.UTF_8)), lIIIlIIII[8]), "DES");
                Cipher llllIIIIIIllll = Cipher.getInstance("DES");
                llllIIIIIIllll.init(lIIIlIIII[2], llllIIIIIlIIII);
                return new String(llllIIIIIIllll.doFinal(Base64.getDecoder().decode(llllIIIIIIllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIIIIIIlllI) {
                llllIIIIIIlllI.printStackTrace();
                return null;
            }
        }

        public static SpellType valueOf(String llllIIIIIlllII) {
            return Enum.valueOf(SpellType.class, llllIIIIIlllII);
        }

        private static String lllIIIIIlI(String lllIlllllIlIII, String lllIlllllIIlll) {
            try {
                SecretKeySpec lllIlllllIlIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIlllllIIlll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lllIlllllIlIlI = Cipher.getInstance("Blowfish");
                lllIlllllIlIlI.init(lIIIlIIII[2], lllIlllllIlIll);
                return new String(lllIlllllIlIlI.doFinal(Base64.getDecoder().decode(lllIlllllIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lllIlllllIlIIl) {
                lllIlllllIlIIl.printStackTrace();
                return null;
            }
        }

        private static boolean lllllIlIIl(int n, int n2) {
            return n < n2;
        }

        static {
            SpellType.lllllIIllI();
            SpellType.lllIIIIlII();
            BLAZE = new SpellType();
            ZOMBIE = new SpellType();
            SKELETON = new SpellType();
            WITCH = new SpellType();
            WITHER = new SpellType();
            SLIME = new SpellType();
            MAGMA_CUBE = new SpellType();
            BONUS = new SpellType();
            MINEFIELD = new SpellType();
            SpellType[] arrspellType = new SpellType[lIIIlIIII[9]];
            arrspellType[SpellType.lIIIlIIII[0]] = BLAZE;
            arrspellType[SpellType.lIIIlIIII[1]] = ZOMBIE;
            arrspellType[SpellType.lIIIlIIII[2]] = SKELETON;
            arrspellType[SpellType.lIIIlIIII[3]] = WITCH;
            arrspellType[SpellType.lIIIlIIII[4]] = WITHER;
            arrspellType[SpellType.lIIIlIIII[5]] = SLIME;
            arrspellType[SpellType.lIIIlIIII[6]] = MAGMA_CUBE;
            arrspellType[SpellType.lIIIlIIII[7]] = BONUS;
            arrspellType[SpellType.lIIIlIIII[8]] = MINEFIELD;
            $VALUES = arrspellType;
        }

        public static SpellType[] values() {
            return (SpellType[])$VALUES.clone();
        }

        private static void lllllIIllI() {
            lIIIlIIII = new int[10];
            SpellType.lIIIlIIII[0] = (144 ^ 142 ^ (91 ^ 18)) & (25 ^ 126 ^ (145 ^ 161) ^ -" ".length());
            SpellType.lIIIlIIII[1] = " ".length();
            SpellType.lIIIlIIII[2] = "  ".length();
            SpellType.lIIIlIIII[3] = "   ".length();
            SpellType.lIIIlIIII[4] = 46 + 84 - 63 + 101 ^ 15 + 39 - 17 + 135;
            SpellType.lIIIlIIII[5] = 102 ^ 99;
            SpellType.lIIIlIIII[6] = 9 + 55 - -107 + 28 ^ 175 + 88 - 207 + 137;
            SpellType.lIIIlIIII[7] = 161 ^ 166;
            SpellType.lIIIlIIII[8] = 231 ^ 143 ^ (55 ^ 87);
            SpellType.lIIIlIIII[9] = 29 ^ 20;
        }
    }

}

