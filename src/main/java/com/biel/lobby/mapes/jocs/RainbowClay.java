/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  com.connorlinfoot.bountifulapi.BountifulAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Chest
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.PotionMeta
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocObjectius;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import com.connorlinfoot.bountifulapi.BountifulAPI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class RainbowClay
extends JocObjectius {
    private static final /* synthetic */ String[] lIIlIIl;
    private static final /* synthetic */ int[] llllIII;

    public List<ItemStack> getPotionChestItems() {
        ArrayList<ItemStack> lIlllIIlIIllIll = new ArrayList<ItemStack>();
        ItemStack lIlllIIlIIllIlI = new ItemStack(Material.SPLASH_POTION, llllIII[1]);
        PotionMeta lIlllIIlIIllIIl = (PotionMeta)lIlllIIlIIllIlI.getItemMeta();
        switch (Utils.NombreEntre(llllIII[0], llllIII[7])) {
            case 0: {
                "".length();
                lIlllIIlIIllIIl.addCustomEffect(new PotionEffect(PotionEffectType.POISON, llllIII[54], llllIII[3]), llllIII[1]);
                "".length();
                if ("   ".length() == "   ".length()) break;
                return null;
            }
            case 1: {
                "".length();
                lIlllIIlIIllIIl.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, llllIII[55], llllIII[1]), llllIII[1]);
                "".length();
                if (null == null) break;
                return null;
            }
            case 2: {
                "".length();
                lIlllIIlIIllIIl.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, llllIII[19], llllIII[1]), llllIII[1]);
                "".length();
                if (-"   ".length() < 0) break;
                return null;
            }
            case 3: {
                "".length();
                lIlllIIlIIllIIl.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, llllIII[56], llllIII[2]), llllIII[1]);
                "".length();
                if (-(39 ^ 76 ^ (192 ^ 175)) < 0) break;
                return null;
            }
            case 4: {
                "".length();
                lIlllIIlIIllIIl.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, llllIII[45], llllIII[3]), llllIII[1]);
                "".length();
                if (-" ".length() != ((188 + 57 - 124 + 103 ^ 159 + 142 - 151 + 11) & (22 ^ 45 ^ (225 ^ 155) ^ -" ".length()))) break;
                return null;
            }
            case 5: {
                "".length();
                lIlllIIlIIllIIl.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, llllIII[57], llllIII[2]), llllIII[1]);
                "".length();
                if (" ".length() >= 0) break;
                return null;
            }
            case 6: {
                "".length();
                lIlllIIlIIllIIl.addCustomEffect(new PotionEffect(PotionEffectType.WEAKNESS, llllIII[58], llllIII[2]), llllIII[1]);
                "".length();
                if (-" ".length() <= "   ".length()) break;
                return null;
            }
            case 7: {
                "".length();
                lIlllIIlIIllIIl.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, llllIII[54], llllIII[8]), llllIII[1]);
                "".length();
                if (null == null) break;
                return null;
            }
        }
        switch (Utils.NombreEntre(llllIII[0], llllIII[5])) {
            case 0: {
                lIlllIIlIIllIIl.setColor(Color.AQUA);
                "".length();
                if (((107 ^ 111 ^ (80 ^ 27)) & (121 ^ 102 ^ (36 ^ 116) ^ -" ".length())) <= "   ".length()) break;
                return null;
            }
            case 1: {
                lIlllIIlIIllIIl.setColor(Color.BLUE);
                "".length();
                if (-" ".length() <= "   ".length()) break;
                return null;
            }
            case 2: {
                lIlllIIlIIllIIl.setColor(Color.GREEN);
                "".length();
                if (((138 ^ 145) & ~(85 ^ 78)) == 0) break;
                return null;
            }
            case 3: {
                lIlllIIlIIllIIl.setColor(Color.PURPLE);
                "".length();
                if (" ".length() <= " ".length()) break;
                return null;
            }
            case 4: {
                lIlllIIlIIllIIl.setColor(Color.RED);
                "".length();
                if ("  ".length() != 0) break;
                return null;
            }
            case 5: {
                lIlllIIlIIllIIl.setColor(Color.WHITE);
                "".length();
                if ("   ".length() != 0) break;
                return null;
            }
        }
        lIlllIIlIIllIIl.setDisplayName(lIIlIIl[llllIII[59]]);
        "".length();
        lIlllIIlIIllIlI.setItemMeta((ItemMeta)lIlllIIlIIllIIl);
        "".length();
        lIlllIIlIIllIll.add(lIlllIIlIIllIlI);
        return lIlllIIlIIllIll;
    }

    private static boolean llIllIIll(Object object, Object object2) {
        return object != object2;
    }

    @Override
    protected void onPlayerDeath(PlayerDeathEvent lIllllIlllllIII, Player lIllllIlllllIll) {
        RainbowClay lIllllIlllllIIl;
        super.onPlayerDeath(lIllllIlllllIII, lIllllIlllllIll);
        JocEquips.Equip lIllllIlllllIlI = lIllllIlllllIIl.obtenirEquip(lIllllIlllllIll);
        lIllllIlllllIII.setDeathMessage(String.valueOf(new StringBuilder().append((Object)lIllllIlllllIlI.getChatColor()).append(lIllllIlllllIll.getName()).append((Object)ChatColor.GRAY).append(lIIlIIl[llllIII[16]])));
    }

    private static boolean llIlIlIIl(int n) {
        return n == 0;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIllllIllIlIlll) {
        RainbowClay lIllllIllIIllIl;
        ArrayList<ItemStack> lIllllIllIlIllI = new ArrayList<ItemStack>();
        JocEquips.Equip lIllllIllIlIlIl = lIllllIllIIllIl.obtenirEquip(lIllllIllIlIlll);
        "".length();
        lIllllIllIlIllI.add(new ItemStack(Material.IRON_SWORD, llllIII[1]));
        ItemStack lIllllIllIlIlII = new ItemStack(Material.BOW, llllIII[1]);
        lIllllIllIlIlII.addUnsafeEnchantment(Enchantment.DURABILITY, llllIII[10]);
        "".length();
        lIllllIllIlIllI.add(lIllllIllIlIlII);
        double lIllllIllIlIIll = lIllllIllIIllIl.getBalancingMultiplier(lIllllIllIlIlIl);
        int lIllllIllIlIIlI = (int)(8.0 * lIllllIllIlIIll);
        if (RainbowClay.llIlIlIll(lIllllIllIlIIlI, llllIII[17])) {
            lIllllIllIlIIlI = llllIII[17];
        }
        "".length();
        lIllllIllIlIllI.add(lIllllIllIIllIl.getSnowLauncher(lIllllIllIlIIlI));
        "".length();
        lIllllIllIlIllI.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, lIllllIllIlIlIl));
        "".length();
        lIllllIllIlIllI.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, lIllllIllIlIlIl));
        "".length();
        lIllllIllIlIllI.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, lIllllIllIlIlIl));
        "".length();
        lIllllIllIlIllI.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, lIllllIllIlIlIl));
        ItemStack lIllllIllIlIIIl = new ItemStack(Material.DIAMOND_PICKAXE, llllIII[1]);
        if (RainbowClay.llIlIllII(RainbowClay.llIlIlIlI(lIllllIllIlIIll, 1.0))) {
            int n;
            if (RainbowClay.llIlIllII(RainbowClay.llIlIlIlI(lIllllIllIlIIll, 1.2))) {
                n = llllIII[2];
                "".length();
                if (" ".length() == ((24 + 42 - -120 + 60 ^ 142 + 69 - 35 + 0) & (97 ^ 1 ^ (143 ^ 169) ^ -" ".length()))) {
                    return null;
                }
            } else {
                n = llllIII[1];
            }
            lIllllIllIlIIIl.addUnsafeEnchantment(Enchantment.DIG_SPEED, n);
        }
        "".length();
        lIllllIllIlIllI.add(lIllllIllIlIIIl);
        int lIllllIllIlIIII = (int)(50.0 * lIllllIllIlIIll);
        if (RainbowClay.llIlIlIll(lIllllIllIlIIII, llllIII[17])) {
            lIllllIllIlIIII = llllIII[17];
        }
        "".length();
        lIllllIllIlIllI.add(new ItemStack(Material.ARROW, lIllllIllIlIIII));
        int lIllllIllIIllll = (int)(50.0 * lIllllIllIlIIll);
        if (RainbowClay.llIlIlIll(lIllllIllIIllll, llllIII[17])) {
            lIllllIllIIllll = llllIII[17];
        }
        "".length();
        lIllllIllIlIllI.add(new ItemStack(Material.LADDER, lIllllIllIIllll));
        int lIllllIllIIlllI = (int)(45.0 * lIllllIllIlIIll);
        if (RainbowClay.llIlIlIll(lIllllIllIIlllI, llllIII[17])) {
            lIllllIllIIlllI = llllIII[17];
        }
        if (RainbowClay.llIlIlIIl(lIllllIllIIllIl.obtenirEquip(lIllllIllIlIlll).getId())) {
            "".length();
            lIllllIllIlIllI.add(new ItemStack(Material.STAINED_CLAY, lIllllIllIIlllI, llllIII[14]));
            "".length();
            if ("  ".length() < 0) {
                return null;
            }
        } else {
            "".length();
            lIllllIllIlIllI.add(new ItemStack(Material.STAINED_CLAY, lIllllIllIIlllI, llllIII[10]));
        }
        "".length();
        lIllllIllIlIllI.add(new ItemStack(Material.WEB, llllIII[1]));
        return lIllllIllIlIllI;
    }

    private static String getBridgeToolName() {
        return String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIIlIIl[llllIII[51]]));
    }

    @Override
    protected void onPlayerMove(PlayerMoveEvent lIlllIlllllIllI, Player lIlllIlllllIIIl) {
        RainbowClay lIlllIlllllIIll;
        super.onPlayerMove(lIlllIlllllIllI, lIlllIlllllIIIl);
        Player lIlllIlllllIlII = lIlllIlllllIllI.getPlayer();
        if (RainbowClay.llIllIIIl((int)lIlllIlllllIIll.isSpectator(lIlllIlllllIlII).booleanValue())) {
            return;
        }
        if (RainbowClay.llIllIIIl((int)lIlllIlllllIIll.JocIniciat.booleanValue())) {
            ItemMeta lIllllIIIIIIIll;
            Player lIlllIllllllllI = lIlllIlllllIllI.getPlayer();
            Location lIlllIlllllllIl = lIlllIlllllIllI.getTo();
            Location lIlllIlllllllII = lIlllIlllllIllI.getFrom();
            int lIlllIllllllIll = lIlllIlllllIIll.obtenirEquip(lIlllIlllllIlII).getId() + llllIII[1];
            if (RainbowClay.llIlllIlI(RainbowClay.llIlllIII(lIlllIlllllIlII.getLocation().getY(), 80.0))) {
                lIlllIlllllIlII.damage(10000.0);
            }
            int lIlllIllllllIlI = llllIII[1];
            while (RainbowClay.llIllllII(lIlllIllllllIlI, llllIII[2])) {
                int lIllllIIIIIIlII = llllIII[0];
                while (RainbowClay.llIllllII(lIllllIIIIIIlII, llllIII[1])) {
                    Cuboid lIllllIIIIIIllI = lIlllIlllllIIll.pMapaActual().ObtenirCuboid(String.valueOf(new StringBuilder().append(lIIlIIl[llllIII[49]]).append(Integer.toString(lIlllIllllllIlI)).append(Integer.toString(lIllllIIIIIIlII))), lIlllIlllllIIll.getWorld());
                    Location lIllllIIIIIIlIl = lIllllIIIIIIllI.getCenter();
                    if (RainbowClay.llIllIIIl((int)lIllllIIIIIIllI.contains(lIlllIlllllllIl.getBlock())) && RainbowClay.llIllIllI(lIlllIllllllIlI, lIlllIllllllIll)) {
                        Vector lIllllIIIIIIlll = Utils.CrearVector(lIllllIIIIIIlIl, lIlllIlllllllII).normalize().add(new Vector(llllIII[0], llllIII[1], llllIII[0]));
                        lIlllIlllllIIll.getWorld().playSound(lIlllIlllllllIl, Sound.ENTITY_IRONGOLEM_HURT, 1.0f, 2.2f);
                        lIlllIlllllIIll.getWorld().playEffect(lIlllIlllllllIl, Effect.MOBSPAWNER_FLAMES, llllIII[3]);
                        lIlllIlllllIIll.getWorld().playEffect(lIlllIlllllllIl.clone().add(new Vector(llllIII[0], llllIII[1], llllIII[0])), Effect.MOBSPAWNER_FLAMES, llllIII[3]);
                        if (RainbowClay.llIllIIIl((int)lIllllIIIIIIllI.contains(lIlllIlllllllII.getBlock())) && RainbowClay.llIlllllI(RainbowClay.llIlllIIl(lIlllIllllllllI.getVelocity().length(), 1.0))) {
                            "".length();
                            lIlllIllllllllI.teleport(lIlllIlllllllII.add(lIllllIIIIIIlll));
                            "".length();
                            if ("  ".length() < " ".length()) {
                                return;
                            }
                        } else {
                            lIlllIllllllllI.setVelocity(lIllllIIIIIIlll);
                        }
                    }
                    lIllllIIIIIIlII += llllIII[1];
                    "".length();
                    if (-(81 ^ 107 ^ (123 ^ 69)) < 0) continue;
                    return;
                }
                lIlllIllllllIlI += llllIII[1];
                "".length();
                if (" ".length() != 0) continue;
                return;
            }
            int lIlllIllllllIIl = llllIII[0];
            ItemStack lIlllIllllllIII = lIlllIlllllIlII.getItemInHand();
            if (RainbowClay.llIllIIIl((int)lIlllIllllllIII.hasItemMeta()) && RainbowClay.llIllIIIl((int)(lIllllIIIIIIIll = lIlllIllllllIII.getItemMeta()).hasDisplayName()) && RainbowClay.llIllIIIl((int)lIllllIIIIIIIll.getDisplayName().equals(RainbowClay.getBridgeToolName()))) {
                lIlllIllllllIIl = llllIII[1];
            }
            if (RainbowClay.llIllIIIl(lIlllIllllllIIl)) {
                ItemStack lIllllIIIIIIIIl;
                Vector lIllllIIIIIIIII = Utils.CrearVector(lIlllIlllllIllI.getFrom(), lIlllIlllllIllI.getTo());
                "".length();
                lIllllIIIIIIIII.multiply(1.45);
                "".length();
                lIllllIIIIIIIII.setY(llllIII[0]);
                Block lIlllIlllllllll = lIlllIlllllIllI.getTo().add(lIllllIIIIIIIII).getBlock().getRelative(BlockFace.DOWN);
                if (RainbowClay.llIllIIIl((int)lIlllIlllllllll.isEmpty()) && RainbowClay.llIllIIIl((int)lIlllIlllllllll.getRelative(BlockFace.DOWN).isEmpty()) && RainbowClay.lllIIIIIl((Object)(lIllllIIIIIIIIl = lIlllIlllllIIll.getPlaceableItemStack(lIlllIlllllIlII)))) {
                    lIlllIlllllllll.setType(lIllllIIIIIIIIl.getType());
                    lIlllIlllllllll.setData(lIllllIIIIIIIIl.getData().getData());
                    ItemStack lIllllIIIIIIIlI = new ItemStack(lIllllIIIIIIIIl);
                    lIllllIIIIIIIlI.setAmount(llllIII[1]);
                    ItemStack[] arritemStack = new ItemStack[llllIII[1]];
                    arritemStack[RainbowClay.llllIII[0]] = lIllllIIIIIIIlI;
                    "".length();
                    lIlllIlllllIlII.getInventory().removeItem(arritemStack);
                    lIlllIllllllIII.setDurability((short)(lIlllIllllllIII.getDurability() + llllIII[3]));
                }
            }
        }
    }

    private static boolean llIllIllI(int n, int n2) {
        return n == n2;
    }

    private static String lIIIlllIl(String lIlllIIIlIIlIlI, String lIlllIIIlIIlIIl) {
        lIlllIIIlIIlIlI = new String(Base64.getDecoder().decode(lIlllIIIlIIlIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlllIIIlIIlIII = new StringBuilder();
        char[] lIlllIIIlIIIlll = lIlllIIIlIIlIIl.toCharArray();
        int lIlllIIIlIIIllI = llllIII[0];
        char[] lIlllIIIlIIIIII = lIlllIIIlIIlIlI.toCharArray();
        int lIlllIIIIllllll = lIlllIIIlIIIIII.length;
        int lIlllIIIIlllllI = llllIII[0];
        while (RainbowClay.lllIIIIlI(lIlllIIIIlllllI, lIlllIIIIllllll)) {
            char lIlllIIIlIIlIll = lIlllIIIlIIIIII[lIlllIIIIlllllI];
            "".length();
            lIlllIIIlIIlIII.append((char)(lIlllIIIlIIlIll ^ lIlllIIIlIIIlll[lIlllIIIlIIIllI % lIlllIIIlIIIlll.length]));
            ++lIlllIIIlIIIllI;
            ++lIlllIIIIlllllI;
            "".length();
            if ("   ".length() >= 0) continue;
            return null;
        }
        return String.valueOf(lIlllIIIlIIlIII);
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent lIllllIlIIIIIII, Block lIllllIIlllllll) {
        RainbowClay lIllllIlIIIlIll;
        super.onBlockBreak(lIllllIlIIIIIII, lIllllIIlllllll);
        Player lIllllIlIIIlIII = lIllllIlIIIIIII.getPlayer();
        JocEquips.Equip lIllllIlIIIIlll = lIllllIlIIIlIll.obtenirEquip(lIllllIlIIIlIII);
        Cuboid lIllllIlIIIIllI = lIllllIlIIIlIll.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[30]], lIllllIlIIIlIll.getWorld());
        Cuboid lIllllIlIIIIlIl = lIllllIlIIIlIll.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[31]], lIllllIlIIIlIll.getWorld());
        Cuboid lIllllIlIIIIlII = lIllllIlIIIlIll.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[32]], lIllllIlIIIlIll.getWorld());
        Cuboid lIllllIlIIIIIll = lIllllIlIIIlIll.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[33]], lIllllIlIIIlIll.getWorld());
        Cuboid lIllllIlIIIIIlI = lIllllIlIIIlIll.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[34]], lIllllIlIIIlIll.getWorld());
        if (!(RainbowClay.llIlIlIIl((int)lIllllIlIIIIlIl.contains(lIllllIIlllllll)) && RainbowClay.llIlIlIIl((int)lIllllIlIIIIlII.contains(lIllllIIlllllll)) && RainbowClay.llIlIlIIl((int)lIllllIlIIIIIll.contains(lIllllIIlllllll)) && !RainbowClay.llIllIIIl((int)lIllllIlIIIIIlI.contains(lIllllIIlllllll)) || !RainbowClay.llIllIIll((Object)lIllllIIlllllll.getType(), (Object)Material.WEB))) {
            lIllllIlIIIIIII.setCancelled(llllIII[1]);
            String lIllllIlIIlIIII = String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIlIIl[llllIII[35]]).append((Object)ChatColor.ITALIC).append(lIIlIIl[llllIII[36]]));
            BountifulAPI.sendActionBar((Player)lIllllIlIIIlIII, (String)lIllllIlIIlIIII, (int)llllIII[27]);
        }
        if (RainbowClay.llIllIIIl((int)lIllllIlIIIIllI.contains(lIllllIIlllllll)) && RainbowClay.llIlIllll((Object)lIllllIIlllllll.getType(), (Object)Material.CHEST)) {
            lIllllIlIIIIIII.setCancelled(llllIII[1]);
            String lIllllIlIIIllll = String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIlIIl[llllIII[37]]).append((Object)ChatColor.ITALIC).append(lIIlIIl[llllIII[38]]));
            BountifulAPI.sendActionBar((Player)lIllllIlIIIlIII, (String)lIllllIlIIIllll, (int)llllIII[27]);
        }
        if (RainbowClay.llIlIllll((Object)lIllllIIlllllll.getType(), (Object)Material.STAINED_CLAY) && RainbowClay.llIllIllI(lIllllIIlllllll.getData(), llllIII[11])) {
            lIllllIlIIIIIII.setCancelled(llllIII[1]);
        }
        if (RainbowClay.llIlIllll((Object)lIllllIIlllllll.getType(), (Object)Material.CHEST)) {
            Cuboid lIllllIlIIIllII;
            String lIllllIlIIIllIl = lIIlIIl[llllIII[39]];
            if (RainbowClay.llIllIllI(lIllllIlIIIIlll.getId(), llllIII[1])) {
                lIllllIlIIIllIl = lIIlIIl[llllIII[40]];
                "".length();
                if ((136 ^ 140) <= ((13 ^ 28) & ~(108 ^ 125))) {
                    return;
                }
            } else {
                lIllllIlIIIllIl = lIIlIIl[llllIII[41]];
            }
            if (RainbowClay.llIllIIIl((int)(lIllllIlIIIllII = lIllllIlIIIlIll.pMapaActual().ObtenirCuboid(lIllllIlIIIllIl, lIllllIlIIIlIll.getWorld())).contains(lIllllIIlllllll))) {
                lIllllIlIIIlIII.playSound(lIllllIlIIIlIII.getLocation(), Sound.BLOCK_CHEST_LOCKED, 100.0f, 0.0f);
                lIllllIlIIIIIII.setCancelled(llllIII[1]);
                String lIllllIlIIIlllI = String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIlIIl[llllIII[42]]).append((Object)ChatColor.ITALIC).append(lIIlIIl[llllIII[43]]));
                BountifulAPI.sendActionBar((Player)lIllllIlIIIlIII, (String)lIllllIlIIIlllI, (int)llllIII[27]);
            }
        }
    }

    private static boolean llIllllII(int n, int n2) {
        return n <= n2;
    }

    @Override
    protected ArrayList<JocObjectius.Objectiu> getDesiredObjectivesTeam(JocObjectius.EquipObjectius lIlllllIIIIllII) {
        RainbowClay lIlllllIIIlIIIl;
        ArrayList<JocObjectius.Objectiu> lIlllllIIIIllll = new ArrayList<JocObjectius.Objectiu>();
        ArrayList<Location> lIlllllIIIIlllI = lIlllllIIIlIIIl.pMapaActual().ObtenirLocations(String.valueOf(new StringBuilder().append(lIIlIIl[llllIII[3]]).append(Integer.toString(lIlllllIIIIllII.getId() + llllIII[1]))), lIlllllIIIlIIIl.getWorld());
        "".length();
        lIlllllIIIIllll.add(lIlllllIIIlIIIl.new JocObjectius.ObjectiuBlockBreak(String.valueOf(new StringBuilder().append(lIIlIIl[llllIII[4]]).append(lIlllllIIIIllII.getAdjectiu()).append(lIIlIIl[llllIII[5]])), lIlllllIIIIlllI.get(llllIII[0])));
        "".length();
        lIlllllIIIIllll.add(lIlllllIIIlIIIl.new JocObjectius.ObjectiuBlockBreak(String.valueOf(new StringBuilder().append(lIIlIIl[llllIII[6]]).append(lIlllllIIIIllII.getAdjectiu()).append(lIIlIIl[llllIII[7]])), lIlllllIIIIlllI.get(llllIII[1])));
        if (RainbowClay.llIlIlIIl(lIlllllIIIIllII.getId())) {
            "".length();
            lIlllllIIIIllll.add(lIlllllIIIlIIIl.new JocObjectius.ObjectiuWoolPlace(lIIlIIl[llllIII[8]], lIlllllIIIlIIIl.pMapaActual().ObtenirLocation(lIIlIIl[llllIII[9]], lIlllllIIIlIIIl.getWorld()), DyeColor.RED));
            "".length();
            lIlllllIIIIllll.add(lIlllllIIIlIIIl.new JocObjectius.ObjectiuWoolPlace(lIIlIIl[llllIII[10]], lIlllllIIIlIIIl.pMapaActual().ObtenirLocation(lIIlIIl[llllIII[11]], lIlllllIIIlIIIl.getWorld()), DyeColor.MAGENTA));
            "".length();
            if (-"   ".length() > 0) {
                return null;
            }
        } else {
            "".length();
            lIlllllIIIIllll.add(lIlllllIIIlIIIl.new JocObjectius.ObjectiuWoolPlace(lIIlIIl[llllIII[12]], lIlllllIIIlIIIl.pMapaActual().ObtenirLocation(lIIlIIl[llllIII[13]], lIlllllIIIlIIIl.getWorld()), DyeColor.LIME));
            "".length();
            lIlllllIIIIllll.add(lIlllllIIIlIIIl.new JocObjectius.ObjectiuWoolPlace(lIIlIIl[llllIII[14]], lIlllllIIIlIIIl.pMapaActual().ObtenirLocation(lIIlIIl[llllIII[15]], lIlllllIIIlIIIl.getWorld()), DyeColor.BLUE));
        }
        return lIlllllIIIIllll;
    }

    @Override
    public void heartbeat() {
        RainbowClay lIlllIllIlIlllI;
        super.heartbeat();
        if (RainbowClay.llIlIlIIl(RainbowClay.lllIIIlII(lIlllIllIlIlllI.getHeartbeatCount() % 30L, 0L)) && RainbowClay.lllIIIIIl((Object)lIlllIllIlIlllI.getWorld())) {
            Cuboid lIlllIllIlIllll = lIlllIllIlIlllI.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[52]], lIlllIllIlIlllI.getWorld());
            lIlllIllIlIllll.getBlocks().stream().filter(lIlllIIIllIIlIl -> {
                boolean bl;
                if (RainbowClay.llIlIllll((Object)lIlllIIIllIIlIl.getType(), (Object)Material.CHEST)) {
                    bl = llllIII[1];
                    "".length();
                    if (" ".length() > "   ".length()) {
                        return (boolean)((124 ^ 5 ^ (85 ^ 28)) & (39 + 125 - 26 + 39 ^ 47 + 67 - 17 + 32 ^ -" ".length()));
                    }
                } else {
                    bl = llllIII[0];
                }
                return bl;
            }).forEach(lIlllIIIllIlIII -> {
                RainbowClay lIlllIIIllIlIIl;
                lIlllIIIllIlIIl.fillChest((Chest)lIlllIIIllIlIII.getState(), lIlllIIIllIlIIl.getMiddleChestItems(), llllIII[0]);
            });
            lIlllIllIlIllll = lIlllIllIlIlllI.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[53]], lIlllIllIlIlllI.getWorld());
            lIlllIllIlIllll.getBlocks().stream().filter(lIlllIIIllIlllI -> {
                boolean bl;
                if (RainbowClay.llIlIllll((Object)lIlllIIIllIlllI.getType(), (Object)Material.CHEST)) {
                    bl = llllIII[1];
                    "".length();
                    if ("   ".length() == "  ".length()) {
                        return (boolean)((55 ^ 21) & ~(29 ^ 63));
                    }
                } else {
                    bl = llllIII[0];
                }
                return bl;
            }).forEach(lIlllIIIlllIIll -> {
                RainbowClay lIlllIIIlllIIlI;
                lIlllIIIlllIIlI.fillChest((Chest)lIlllIIIlllIIll.getState(), lIlllIIIlllIIlI.getPotionChestItems(), llllIII[1]);
            });
        }
    }

    public RainbowClay() {
        RainbowClay lIlllllIIIlllIl;
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent lIllllIlllIlllI, Player lIllllIlllIIlll, Player lIllllIlllIIllI) {
        RainbowClay lIllllIlllIllll;
        super.onPlayerDeathByPlayer(lIllllIlllIlllI, lIllllIlllIIlll, lIllllIlllIIllI);
        double lIllllIlllIlIll = lIllllIlllIIlll.getLocation().distance(lIllllIlllIIllI.getLocation());
        EntityDamageEvent.DamageCause lIllllIlllIlIlI = lIllllIlllIIlll.getLastDamageCause().getCause();
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent lIllllIlIlIllIl, Block lIllllIlIlIllII) {
        RainbowClay lIllllIlIlIIllI;
        super.onBlockPlace(lIllllIlIlIllIl, lIllllIlIlIllII);
        Player lIllllIlIlIlIll = lIllllIlIlIllIl.getPlayer();
        Cuboid lIllllIlIlIlIlI = lIllllIlIlIIllI.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[21]], lIllllIlIlIIllI.getWorld());
        Cuboid lIllllIlIlIlIIl = lIllllIlIlIIllI.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[22]], lIllllIlIlIIllI.getWorld());
        Cuboid lIllllIlIlIlIII = lIllllIlIlIIllI.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[23]], lIllllIlIlIIllI.getWorld());
        Cuboid lIllllIlIlIIlll = lIllllIlIlIIllI.pMapaActual().ObtenirCuboid(lIIlIIl[llllIII[24]], lIllllIlIlIIllI.getWorld());
        if (RainbowClay.llIlIllll((Object)lIllllIlIlIllII.getType(), (Object)Material.OBSIDIAN)) {
            String lIllllIlIllIIII = String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIlIIl[llllIII[25]]).append((Object)ChatColor.ITALIC).append(lIIlIIl[llllIII[26]]));
            BountifulAPI.sendActionBar((Player)lIllllIlIlIlIll, (String)lIllllIlIllIIII, (int)llllIII[27]);
            lIllllIlIlIllIl.setCancelled(llllIII[1]);
            return;
        }
        if (!(RainbowClay.llIlIlIIl((int)lIllllIlIlIlIlI.contains(lIllllIlIlIllII)) && RainbowClay.llIlIlIIl((int)lIllllIlIlIlIIl.contains(lIllllIlIlIllII)) && RainbowClay.llIlIlIIl((int)lIllllIlIlIlIII.contains(lIllllIlIlIllII)) && !RainbowClay.llIllIIIl((int)lIllllIlIlIIlll.contains(lIllllIlIlIllII)) || !RainbowClay.llIllIIll((Object)lIllllIlIlIllII.getType(), (Object)Material.WEB))) {
            lIllllIlIlIllIl.setCancelled(llllIII[1]);
            String lIllllIlIlIllll = String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIlIIl[llllIII[28]]).append((Object)ChatColor.ITALIC).append(lIIlIIl[llllIII[29]]));
            BountifulAPI.sendActionBar((Player)lIllllIlIlIlIll, (String)lIllllIlIlIllll, (int)llllIII[27]);
        }
    }

    private static String llllIllI(String lIlllIIIlIllIlI, String lIlllIIIlIlIlll) {
        try {
            SecretKeySpec lIlllIIIlIlllIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlllIIIlIlIlll.getBytes(StandardCharsets.UTF_8)), llllIII[8]), "DES");
            Cipher lIlllIIIlIlllII = Cipher.getInstance("DES");
            lIlllIIIlIlllII.init(llllIII[2], lIlllIIIlIlllIl);
            return new String(lIlllIIIlIlllII.doFinal(Base64.getDecoder().decode(lIlllIIIlIllIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllIIIlIllIll) {
            lIlllIIIlIllIll.printStackTrace();
            return null;
        }
    }

    static {
        RainbowClay.llIlIlIII();
        RainbowClay.llIIIIIlI();
    }

    private static boolean lllIIIIlI(int n, int n2) {
        return n < n2;
    }

    @Override
    protected void customJocIniciat() {
        RainbowClay lIlllllIIIIIlII;
        super.customJocIniciat();
        lIlllllIIIIIlII.setBlockBreakPlace(llllIII[1]);
        lIlllllIIIIIlII.setGiveStartingItemsRespawn(llllIII[1]);
    }

    @Override
    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIlllIlllIIIIll, Player lIlllIlllIIIIlI, Player lIlllIlllIIIIIl, boolean lIlllIlllIIIIII) {
        RainbowClay lIlllIlllIIIlII;
        super.onPlayerDamageByPlayer(lIlllIlllIIIIll, lIlllIlllIIIIlI, lIlllIlllIIIIIl, lIlllIlllIIIIII);
        if (RainbowClay.llIllIIIl((int)lIlllIlllIIIIII) && RainbowClay.llIllIIIl((int)lIlllIlllIIIIlI.isSneaking())) {
            lIlllIlllIIIIlI.setVelocity(new Vector());
            "".length();
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)lIlllIlllIIIlII.plugin, () -> lIlllIlllIIIIlI.setVelocity(new Vector()), 1L);
        }
    }

    @Override
    public String getGameName() {
        return lIIlIIl[llllIII[0]];
    }

    private static boolean lllIIIIIl(Object object) {
        return object != null;
    }

    @Override
    protected void donarEfectesInicials(Player lIllllIlIlllIll) {
        RainbowClay lIllllIlIllllII;
        super.donarEfectesInicials(lIllllIlIlllIll);
        double lIllllIlIllllIl = lIllllIlIllllII.getBalancingMultiplier(lIllllIlIllllII.obtenirEquip(lIllllIlIlllIll));
        "".length();
        lIllllIlIlllIll.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (int)(300.0 * (lIllllIlIllllIl - 0.5)), llllIII[5], llllIII[1]), llllIII[1]);
        "".length();
        lIllllIlIlllIll.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, llllIII[18], llllIII[1], llllIII[1]), llllIII[1]);
        "".length();
        lIllllIlIlllIll.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, llllIII[19], llllIII[0], llllIII[1]), llllIII[1]);
        "".length();
        lIllllIlIlllIll.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, llllIII[20], llllIII[3], llllIII[1]), llllIII[1]);
        "".length();
        lIllllIlIlllIll.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int)(380.0 * lIllllIlIllllIl), llllIII[1], llllIII[1]), llllIII[1]);
    }

    private static boolean llIlIlIll(int n, int n2) {
        return n > n2;
    }

    protected void onPlayerInteract(PlayerInteractEvent lIllllIIllIIIIl, Player lIllllIIllIIIII) {
        Cuboid lIllllIIllIIIll;
        RainbowClay lIllllIIllIlIlI;
        super.onPlayerInteract(lIllllIIllIIIIl, lIllllIIllIIIII);
        if (!RainbowClay.llIlIllll((Object)lIllllIIllIIIIl.getAction(), (Object)Action.RIGHT_CLICK_BLOCK) || RainbowClay.llIllIIll((Object)lIllllIIllIIIIl.getClickedBlock().getType(), (Object)Material.CHEST)) {
            return;
        }
        Player lIllllIIllIIlll = lIllllIIllIIIIl.getPlayer();
        JocEquips.Equip lIllllIIllIIllI = lIllllIIllIlIlI.obtenirEquip(lIllllIIllIIlll);
        Block lIllllIIllIIlIl = lIllllIIllIIIIl.getClickedBlock();
        String lIllllIIllIIlII = lIIlIIl[llllIII[44]];
        if (RainbowClay.llIllIllI(lIllllIIllIIllI.getId(), llllIII[1])) {
            lIllllIIllIIlII = lIIlIIl[llllIII[45]];
            "".length();
            if (((29 ^ 26) & ~(181 ^ 178)) > "   ".length()) {
                return;
            }
        } else {
            lIllllIIllIIlII = lIIlIIl[llllIII[46]];
        }
        if (RainbowClay.llIllIIIl((int)(lIllllIIllIIIll = lIllllIIllIlIlI.pMapaActual().ObtenirCuboid(lIllllIIllIIlII, lIllllIIllIlIlI.getWorld())).contains(lIllllIIllIIlIl))) {
            String lIllllIIllIlIll = String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIIlIIl[llllIII[47]]).append((Object)ChatColor.ITALIC).append(lIIlIIl[llllIII[48]]));
            BountifulAPI.sendActionBar((Player)lIllllIIllIIlll, (String)lIllllIIllIlIll, (int)llllIII[27]);
            lIllllIIllIIlll.playSound(lIllllIIllIIlll.getLocation(), Sound.BLOCK_CHEST_LOCKED, 100.0f, 0.0f);
            lIllllIIllIIIIl.setCancelled(llllIII[1]);
        }
    }

    private static String lllllIlI(String lIlllIIIIllIIll, String lIlllIIIIllIlII) {
        try {
            SecretKeySpec lIlllIIIIlllIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlllIIIIllIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlllIIIIllIlll = Cipher.getInstance("Blowfish");
            lIlllIIIIllIlll.init(llllIII[2], lIlllIIIIlllIII);
            return new String(lIlllIIIIllIlll.doFinal(Base64.getDecoder().decode(lIlllIIIIllIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllIIIIllIllI) {
            lIlllIIIIllIllI.printStackTrace();
            return null;
        }
    }

    private static int llIlllIII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    protected void onBlockHitByProjectile(ProjectileHitEvent lIlllIlllIlIlll, Block lIlllIlllIlIllI, Projectile lIlllIlllIlIlIl) {
        RainbowClay lIlllIlllIllIII;
        super.onBlockHitByProjectile(lIlllIlllIlIlll, lIlllIlllIlIllI, lIlllIlllIlIlIl);
        Material lIlllIlllIlIlII = lIlllIlllIlIllI.getType();
        if (!(RainbowClay.llIllIIll((Object)lIlllIlllIlIlII, (Object)Material.GLASS) && RainbowClay.llIllIIll((Object)lIlllIlllIlIlII, (Object)Material.STAINED_GLASS) && RainbowClay.llIllIIll((Object)lIlllIlllIlIlII, (Object)Material.STAINED_GLASS_PANE) && RainbowClay.llIllIIll((Object)lIlllIlllIlIlII, (Object)Material.THIN_GLASS) && !RainbowClay.llIlIllll((Object)lIlllIlllIlIlII, (Object)Material.GLOWSTONE))) {
            lIlllIlllIlIllI.setType(Material.AIR);
            lIlllIlllIllIII.getWorld().playSound(lIlllIlllIlIllI.getLocation(), Sound.BLOCK_GLASS_BREAK, 15.0f, 1.2f);
            lIlllIlllIlIlIl.remove();
            BlockFace[] lIlllIlllIIlllI = BlockFace.values();
            int lIlllIlllIIllIl = lIlllIlllIIlllI.length;
            int lIlllIlllIIllII = llllIII[0];
            while (RainbowClay.lllIIIIlI(lIlllIlllIIllII, lIlllIlllIIllIl)) {
                BlockFace lIlllIlllIllIIl = lIlllIlllIIlllI[lIlllIlllIIllII];
                if (RainbowClay.llIllIIIl((int)GUtils.Possibilitat((int)llllIII[50]))) {
                    "".length();
                    if ((22 ^ 18) < (95 ^ 91)) {
                        return;
                    }
                } else {
                    Block lIlllIlllIllIlI = lIlllIlllIlIllI.getRelative(lIlllIlllIllIIl);
                    lIlllIlllIlIlII = lIlllIlllIllIlI.getType();
                    if (!(RainbowClay.llIllIIll((Object)lIlllIlllIlIlII, (Object)Material.GLASS) && RainbowClay.llIllIIll((Object)lIlllIlllIlIlII, (Object)Material.STAINED_GLASS) && RainbowClay.llIllIIll((Object)lIlllIlllIlIlII, (Object)Material.STAINED_GLASS_PANE) && RainbowClay.llIllIIll((Object)lIlllIlllIlIlII, (Object)Material.THIN_GLASS) && !RainbowClay.llIlIllll((Object)lIlllIlllIlIlII, (Object)Material.GLOWSTONE))) {
                        lIlllIlllIllIlI.setType(Material.AIR);
                    }
                }
                ++lIlllIlllIIllII;
                "".length();
                if (((93 ^ 119) & ~(150 ^ 188)) < " ".length()) continue;
                return;
            }
        }
    }

    private static int lllIIIlII(long l, long l2) {
        return (int)(l LCMP l2);
    }

    private static boolean llIlllIlI(int n) {
        return n < 0;
    }

    private static boolean llIllIIIl(int n) {
        return n != 0;
    }

    @Override
    protected void setCustomGameRules() {
    }

    private static boolean llIlllllI(int n) {
        return n >= 0;
    }

    public List<ItemStack> getMiddleChestItems() {
        ArrayList<ItemStack> lIlllIIlIIIIlIl = new ArrayList<ItemStack>();
        int lIlllIIlIIIIlII = Utils.NombreEntre(llllIII[4], llllIII[9]);
        int lIlllIIlIIIIIll = llllIII[0];
        while (RainbowClay.lllIIIIlI(lIlllIIlIIIIIll, lIlllIIlIIIIlII)) {
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[5]))) {
                "".length();
                lIlllIIlIIIIlIl.add(new ItemStack(Material.ENDER_PEARL, llllIII[1]));
                ++lIlllIIlIIIIIll;
            }
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[2]))) {
                ItemStack lIlllIIlIIIllll = new ItemStack(Material.GOLD_LEGGINGS, llllIII[1]);
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllll.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllll.addUnsafeEnchantment(Enchantment.THORNS, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllll.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                "".length();
                lIlllIIlIIIIlIl.add(lIlllIIlIIIllll);
                ++lIlllIIlIIIIIll;
            }
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[2]))) {
                ItemStack lIlllIIlIIIlllI = new ItemStack(Material.GOLD_CHESTPLATE, llllIII[1]);
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIlllI.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIlllI.addUnsafeEnchantment(Enchantment.THORNS, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIlllI.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                "".length();
                lIlllIIlIIIIlIl.add(lIlllIIlIIIlllI);
                ++lIlllIIlIIIIIll;
            }
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[2]))) {
                ItemStack lIlllIIlIIIllIl = new ItemStack(Material.GOLD_BOOTS, llllIII[1]);
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllIl.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllIl.addUnsafeEnchantment(Enchantment.THORNS, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllIl.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                "".length();
                lIlllIIlIIIIlIl.add(lIlllIIlIIIllIl);
                ++lIlllIIlIIIIIll;
            }
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[2]))) {
                ItemStack lIlllIIlIIIllII = new ItemStack(Material.GOLD_HELMET, llllIII[1]);
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllII.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllII.addUnsafeEnchantment(Enchantment.THORNS, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[15]))) {
                    lIlllIIlIIIllII.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                "".length();
                lIlllIIlIIIIlIl.add(lIlllIIlIIIllII);
                ++lIlllIIlIIIIIll;
            }
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[1]))) {
                ItemStack lIlllIIlIIIlIll = new ItemStack(Material.GOLD_AXE, llllIII[1]);
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[35]))) {
                    lIlllIIlIIIlIll.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, Utils.NombreEntre(llllIII[1], llllIII[5]));
                }
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[1]))) {
                    lIlllIIlIIIlIll.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, llllIII[1]);
                }
                "".length();
                lIlllIIlIIIIlIl.add(lIlllIIlIIIlIll);
                ++lIlllIIlIIIIIll;
            }
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[10]))) {
                "".length();
                lIlllIIlIIIIlIl.add(new ItemStack(Material.GOLDEN_APPLE, llllIII[1]));
                ++lIlllIIlIIIIIll;
            }
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[30]))) {
                ItemStack lIlllIIlIIIlIlI = new ItemStack(Material.IRON_INGOT, llllIII[1]);
                if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[1])) && RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[1]))) {
                    lIlllIIlIIIlIlI.addUnsafeEnchantment(Enchantment.KNOCKBACK, llllIII[1]);
                }
                "".length();
                lIlllIIlIIIIlIl.add(lIlllIIlIIIlIlI);
                ++lIlllIIlIIIIIll;
            }
            if (RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[3]))) {
                "".length();
                lIlllIIlIIIIlIl.add(new ItemStack(Material.GLASS, llllIII[16]));
                ++lIlllIIlIIIIIll;
            }
            if (!RainbowClay.llIllIIIl((int)Utils.Possibilitat(llllIII[5]))) continue;
            ItemStack lIlllIIlIIIlIIl = new ItemStack(Material.POTION, Utils.NombreEntre(llllIII[1], llllIII[4]));
            PotionMeta lIlllIIlIIIlIII = (PotionMeta)lIlllIIlIIIlIIl.getItemMeta();
            switch (Utils.NombreEntre(llllIII[1], llllIII[9])) {
                case 1: {
                    "".length();
                    lIlllIIlIIIlIII.addCustomEffect(new PotionEffect(PotionEffectType.POISON, llllIII[54], llllIII[1]), llllIII[1]);
                    "".length();
                    if (-" ".length() <= (77 ^ 73)) break;
                    return null;
                }
                case 2: {
                    "".length();
                    lIlllIIlIIIlIII.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, llllIII[60], llllIII[4]), llllIII[1]);
                    "".length();
                    if (((29 ^ 49) & ~(47 ^ 3)) == 0) break;
                    return null;
                }
                case 3: {
                    "".length();
                    lIlllIIlIIIlIII.addCustomEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, llllIII[60], llllIII[2]), llllIII[1]);
                    "".length();
                    if ("  ".length() < (68 ^ 64)) break;
                    return null;
                }
                case 4: {
                    "".length();
                    lIlllIIlIIIlIII.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, llllIII[61], llllIII[1]), llllIII[1]);
                    "".length();
                    if (" ".length() >= 0) break;
                    return null;
                }
                case 5: {
                    "".length();
                    lIlllIIlIIIlIII.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, llllIII[62], llllIII[5]), llllIII[1]);
                    "".length();
                    if (null == null) break;
                    return null;
                }
                case 6: {
                    "".length();
                    lIlllIIlIIIlIII.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, llllIII[19], llllIII[1]), llllIII[1]);
                    "".length();
                    if (null == null) break;
                    return null;
                }
                case 8: {
                    "".length();
                    lIlllIIlIIIlIII.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, llllIII[62], llllIII[1]), llllIII[1]);
                    "".length();
                    if ("   ".length() != "  ".length()) break;
                    return null;
                }
                case 9: {
                    "".length();
                    lIlllIIlIIIlIII.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, llllIII[19], llllIII[1]), llllIII[1]);
                    "".length();
                    if (-"  ".length() <= 0) break;
                    return null;
                }
            }
            ItemFlag[] arritemFlag = new ItemFlag[llllIII[1]];
            arritemFlag[RainbowClay.llllIII[0]] = ItemFlag.HIDE_POTION_EFFECTS;
            lIlllIIlIIIlIII.addItemFlags(arritemFlag);
            lIlllIIlIIIlIII.setDisplayName(lIIlIIl[llllIII[63]]);
            lIlllIIlIIIlIII.setColor(Color.YELLOW);
            ArrayList<String> lIlllIIlIIIIlll = new ArrayList<String>();
            "".length();
            lIlllIIlIIIIlll.add(lIIlIIl[llllIII[64]]);
            "".length();
            lIlllIIlIIIIlll.add(lIIlIIl[llllIII[65]]);
            lIlllIIlIIIlIII.setLore(lIlllIIlIIIIlll);
            "".length();
            lIlllIIlIIIlIIl.setItemMeta((ItemMeta)lIlllIIlIIIlIII);
            "".length();
            lIlllIIlIIIIlIl.add(lIlllIIlIIIlIIl);
            ++lIlllIIlIIIIIll;
            "".length();
            if (" ".length() >= -" ".length()) continue;
            return null;
        }
        return lIlllIIlIIIIlIl;
    }

    private static boolean llIlIllll(Object object, Object object2) {
        return object == object2;
    }

    private static void llIIIIIlI() {
        lIIlIIl = new String[llllIII[66]];
        RainbowClay.lIIlIIl[RainbowClay.llllIII[0]] = RainbowClay.llllIllI("q1q9A40a7U+I59CISS1i3Q==", "etmUN");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[1]] = RainbowClay.lllllIlI("WICh8sECLFg=", "OIsdg");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[2]] = RainbowClay.lIIIlllIl("Gw4XBA==", "ybvqI");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[3]] = RainbowClay.lllllIlI("6ekFOaXtSkA=", "RYigT");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[4]] = RainbowClay.llllIllI("KL/0jlq/360=", "rZxPE");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[5]] = RainbowClay.lIIIlllIl("QjQ=", "bxbLU");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[6]] = RainbowClay.lIIIlllIl("LwobDXA=", "leihP");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[7]] = RainbowClay.llllIllI("6ZJAo/7o3dw=", "kGieJ");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[8]] = RainbowClay.lllllIlI("FpwiDP4w0AP3UbqYj0029g==", "KuVzl");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[9]] = RainbowClay.lIIIlllIl("PAMJLg==", "kQlJq");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[10]] = RainbowClay.lIIIlllIl("IgsHJAROCgctAAATBw==", "ngfJe");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[11]] = RainbowClay.lIIIlllIl("GB0RKxMhJBE=", "OPpLv");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[12]] = RainbowClay.lllllIlI("QX0Z2Ybz9cuJ6eblekOfVg==", "gCxNE");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[13]] = RainbowClay.lllllIlI("EksDsTIlujI=", "AObHr");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[14]] = RainbowClay.lllllIlI("yvt7TItcUQx5E9026DZ+XA==", "EZVSi");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[15]] = RainbowClay.lIIIlllIl("Igc6PDc=", "uEVIR");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[16]] = RainbowClay.lIIIlllIl("RRI4WiQKCC1U", "ezYzI");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[21]] = RainbowClay.lIIIlllIl("FSgqGkt3", "GMMNz");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[22]] = RainbowClay.llllIllI("CHAb62EB6uU=", "ABCbL");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[23]] = RainbowClay.llllIllI("FaxIHjCC8cI=", "qhUMu");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[24]] = RainbowClay.llllIllI("yAoVvqPzVDU=", "URPDg");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[25]] = RainbowClay.lllllIlI("tHcKll4+F/A=", "QeCta");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[26]] = RainbowClay.llllIllI("fmB7mL5aMEyEurSfGMxQeJDlFY6HLznBwRQMT29WUVs=", "Twnxm");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[28]] = RainbowClay.lIIIlllIl("", "qoxfN");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[29]] = RainbowClay.llllIllI("L2vCDuqyC1pmHboBLCiXsMDxzbEiOBHfWfHtfGs+umY=", "ENOlz");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[30]] = RainbowClay.llllIllI("hvyFuhRFrtc=", "OaVQG");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[31]] = RainbowClay.llllIllI("S9kL57clbDM=", "HHgWh");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[32]] = RainbowClay.llllIllI("Rl9qe6IuB28=", "zrwXD");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[33]] = RainbowClay.lllllIlI("8eza7pdwGmc=", "LBjUL");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[34]] = RainbowClay.llllIllI("aoFMVNuuAd0=", "qDvNe");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[35]] = RainbowClay.llllIllI("VT80isyadas=", "RAHwd");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[36]] = RainbowClay.llllIllI("+Ty9kxxAmVYRc/TUjdQ1NzLZrdaHfPCwBy7460iRtTs=", "rnAvK");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[37]] = RainbowClay.lIIIlllIl("", "meMkc");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[38]] = RainbowClay.lIIIlllIl("PyNwMgMFP3AmCQI4IjcFA2w1Lh9RLz8kHhQ/cCYJHWwzJwIFPjU=", "qLPBl");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[39]] = RainbowClay.llllIllI("kaYbtDFFuvJj3Hnx3HANmw==", "Iefdj");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[40]] = RainbowClay.llllIllI("NYZm5+G3Gm7XRqI3II8YqQ==", "nYhui");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[41]] = RainbowClay.lIIIlllIl("BRs4AFkmCC4E", "gzKeh");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[42]] = RainbowClay.llllIllI("gr5oPkYKY+g=", "SXvIL");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[43]] = RainbowClay.lIIIlllIl("CiZGFgwwOkYCBjc9FBMKNmkDChBkKgkAESE6RgMNISQPBRA=", "DIffc");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[44]] = RainbowClay.llllIllI("5xsWllujBlQERPU+1mWD0w==", "PjSjL");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[45]] = RainbowClay.lllllIlI("PJWFpOGFaG7EmCilZk/Nzw==", "EbWVZ");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[46]] = RainbowClay.llllIllI("+/FGr0TBm66LCnMgQOXxHA==", "hYDPa");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[47]] = RainbowClay.lIIIlllIl("", "AgbHB");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[48]] = RainbowClay.lllllIlI("TyTdEk4yhQ4qHDB5wkVsQ9e2Q43dqPNRFYfGUKaKbOI/bnHSj5Cz+xWazugauV5s", "tWCoE");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[49]] = RainbowClay.llllIllI("j4JLC+U54Ag=", "nNLeW");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[51]] = RainbowClay.lIIIlllIl("KwIcAwsLSwAHA0oICwwcHhkRARsFGQ==", "jkdbo");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[52]] = RainbowClay.llllIllI("kH/Fk4MY8YY=", "OkKET");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[53]] = RainbowClay.llllIllI("H3nvt/d882s=", "nKZnv");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[59]] = RainbowClay.lIIIlllIl("ES4UB8KBYSBXChsyNcKXABEoIA==", "AAwnr");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[63]] = RainbowClay.lllllIlI("YHnrUsnL0WBc4VU2EFbvKg==", "VzKyv");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[64]] = RainbowClay.lllllIlI("uDUQSjNKGHmczgeucagQ8CT6rFOj/jNE", "KbkEq");
        RainbowClay.lIIlIIl[RainbowClay.llllIII[65]] = RainbowClay.lllllIlI("3/0OUFJhm3AuO5F/UW+cJg==", "MpJCX");
    }

    public void fillChest(Chest lIlllIllIlIIIlI, List<ItemStack> lIlllIllIlIIIIl, boolean lIlllIllIlIIIII) {
        Inventory lIlllIllIlIIIll = lIlllIllIlIIIlI.getInventory();
        if (RainbowClay.llIlIlIIl((int)lIlllIllIlIIIII)) {
            lIlllIllIlIIIll.clear();
        }
        if (RainbowClay.llIlIlIll(lIlllIllIlIIIll.getMaxStackSize(), lIlllIllIlIIIll.getStorageContents().length + lIlllIllIlIIIIl.size())) {
            lIlllIllIlIIIIl.forEach(lIlllIIIllllIIl -> {
                ItemStack[] arritemStack = new ItemStack[llllIII[1]];
                arritemStack[RainbowClay.llllIII[0]] = lIlllIIIllllIIl;
                "".length();
                lIlllIllIlIIIll.addItem(arritemStack);
            });
        }
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        RainbowClay lIlllllIIIllIIl;
        ArrayList<JocEquips.Equip> lIlllllIIIllIII = new ArrayList<JocEquips.Equip>();
        "".length();
        lIlllllIIIllIII.add(lIlllllIIIllIIl.new JocObjectius.EquipObjectius(DyeColor.RED, lIIlIIl[llllIII[1]]));
        "".length();
        lIlllllIIIllIII.add(lIlllllIIIllIIl.new JocObjectius.EquipObjectius(DyeColor.BLUE, lIIlIIl[llllIII[2]]));
        return lIlllllIIIllIII;
    }

    private static int llIlIlIlI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean llIlIllII(int n) {
        return n > 0;
    }

    private static void llIlIlIII() {
        llllIII = new int[67];
        RainbowClay.llllIII[0] = (214 ^ 155) & ~(203 ^ 134);
        RainbowClay.llllIII[1] = " ".length();
        RainbowClay.llllIII[2] = "  ".length();
        RainbowClay.llllIII[3] = "   ".length();
        RainbowClay.llllIII[4] = 171 ^ 175;
        RainbowClay.llllIII[5] = 109 ^ 84 ^ (123 ^ 71);
        RainbowClay.llllIII[6] = 16 ^ 22;
        RainbowClay.llllIII[7] = 200 ^ 159 ^ (63 ^ 111);
        RainbowClay.llllIII[8] = 88 + 87 - 18 + 0 ^ 50 + 0 - -24 + 75;
        RainbowClay.llllIII[9] = 63 ^ 54;
        RainbowClay.llllIII[10] = 17 ^ 123 ^ (84 ^ 52);
        RainbowClay.llllIII[11] = 76 ^ 71;
        RainbowClay.llllIII[12] = 62 ^ 111 ^ (36 ^ 121);
        RainbowClay.llllIII[13] = 81 ^ 92;
        RainbowClay.llllIII[14] = 126 + 94 - 41 + 21 ^ 189 + 101 - 192 + 100;
        RainbowClay.llllIII[15] = 75 + 32 - -13 + 57 ^ 20 + 108 - 39 + 101;
        RainbowClay.llllIII[16] = 28 ^ 8 ^ (124 ^ 120);
        RainbowClay.llllIII[17] = 220 ^ 156;
        RainbowClay.llllIII[18] = 126 ^ 66;
        RainbowClay.llllIII[19] = -(-30341 & 32391) & (-20742 & 23391);
        RainbowClay.llllIII[20] = 145 ^ 161 ^ (248 ^ 172);
        RainbowClay.llllIII[21] = 174 ^ 191;
        RainbowClay.llllIII[22] = 215 ^ 197;
        RainbowClay.llllIII[23] = 126 ^ 77 ^ (54 ^ 22);
        RainbowClay.llllIII[24] = 21 + 110 - 55 + 100 ^ 89 + 108 - 38 + 5;
        RainbowClay.llllIII[25] = 211 ^ 198;
        RainbowClay.llllIII[26] = 26 ^ 72 ^ (87 ^ 19);
        RainbowClay.llllIII[27] = 6 + 141 - 92 + 95;
        RainbowClay.llllIII[28] = 101 + 67 - 113 + 113 ^ 95 + 0 - -60 + 36;
        RainbowClay.llllIII[29] = 134 ^ 158;
        RainbowClay.llllIII[30] = 228 ^ 196 ^ (82 ^ 107);
        RainbowClay.llllIII[31] = 218 ^ 192;
        RainbowClay.llllIII[32] = 241 ^ 141 ^ (77 ^ 42);
        RainbowClay.llllIII[33] = 93 + 102 - 49 + 2 ^ 51 + 5 - -1 + 79;
        RainbowClay.llllIII[34] = 71 + 88 - 37 + 23 ^ 15 + 63 - -22 + 40;
        RainbowClay.llllIII[35] = 89 ^ 101 ^ (179 ^ 145);
        RainbowClay.llllIII[36] = 79 ^ 29 ^ (247 ^ 186);
        RainbowClay.llllIII[37] = 106 ^ 74;
        RainbowClay.llllIII[38] = 157 ^ 188;
        RainbowClay.llllIII[39] = 11 ^ 46 ^ (22 ^ 17);
        RainbowClay.llllIII[40] = 160 ^ 131;
        RainbowClay.llllIII[41] = 100 ^ 64;
        RainbowClay.llllIII[42] = 33 ^ 4;
        RainbowClay.llllIII[43] = 34 ^ 90 ^ (80 ^ 14);
        RainbowClay.llllIII[44] = 173 ^ 188 ^ (70 ^ 112);
        RainbowClay.llllIII[45] = 97 ^ 73;
        RainbowClay.llllIII[46] = 232 ^ 193;
        RainbowClay.llllIII[47] = 89 ^ 50 ^ (121 ^ 56);
        RainbowClay.llllIII[48] = 172 ^ 135;
        RainbowClay.llllIII[49] = 65 ^ 100 ^ (205 ^ 196);
        RainbowClay.llllIII[50] = 249 ^ 195;
        RainbowClay.llllIII[51] = 0 ^ 45;
        RainbowClay.llllIII[52] = 96 ^ 78;
        RainbowClay.llllIII[53] = 232 ^ 199;
        RainbowClay.llllIII[54] = 154 + 18 - 20 + 66 ^ 106 + 36 - 54 + 50;
        RainbowClay.llllIII[55] = (89 ^ 110) + (240 ^ 162) - (215 ^ 157) + (1 ^ 96);
        RainbowClay.llllIII[56] = 182 + 30 - -31 + 6 ^ 3 + 114 - 90 + 102;
        RainbowClay.llllIII[57] = -(-2313 & 11725) & (-16420 & 26111);
        RainbowClay.llllIII[58] = -(-16519 & 30447) & (-4 & 14331);
        RainbowClay.llllIII[59] = 112 ^ 64;
        RainbowClay.llllIII[60] = -6161 & 6960;
        RainbowClay.llllIII[61] = -4936 & 6135;
        RainbowClay.llllIII[62] = -8748 & 9067;
        RainbowClay.llllIII[63] = 112 ^ 65;
        RainbowClay.llllIII[64] = 113 ^ 67;
        RainbowClay.llllIII[65] = 162 ^ 145;
        RainbowClay.llllIII[66] = 0 + 3 - -24 + 108 ^ 124 + 101 - 182 + 136;
    }

    public ItemStack getPlaceableItemStack(Player lIlllIllIllIlII) {
        ListIterator lIlllIllIllIIll = lIlllIllIllIlII.getInventory().iterator();
        while (RainbowClay.llIllIIIl((int)lIlllIllIllIIll.hasNext())) {
            ItemStack lIlllIllIllIlll = (ItemStack)lIlllIllIllIIll.next();
            if (RainbowClay.llIllIIIl((int)lIlllIllIllIlll.getType().isSolid())) {
                return lIlllIllIllIlll;
            }
            "".length();
            if (-(93 ^ 42 ^ (51 ^ 65)) < 0) continue;
            return null;
        }
        return null;
    }

    @Override
    protected void customJocFinalitzat() {
        RainbowClay lIlllllIIIIIllI;
        super.customJocFinalitzat();
        lIlllllIIIIIllI.setBlockBreakPlace(llllIII[0]);
    }

    private static int llIlllIIl(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    public boolean giveSnowLauncherOnKill() {
        return llllIII[1];
    }
}

