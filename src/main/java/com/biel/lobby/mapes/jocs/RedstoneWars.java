/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

public class RedstoneWars
extends JocEquips {
    private static final /* synthetic */ int[] lIIIllIII;
    private static final /* synthetic */ String[] lIIIlIlll;
    /* synthetic */ int chargeCount;

    public void placeBonusOnTheMiddle() {
        RedstoneWars lllIIlllIIlIII;
        Location lllIIlllIIIlll = lllIIlllIIlIII.getHalfwayMiddle();
        int lllIIlllIIIllI = lllIIlllIIlIII.getWorld().getHighestBlockYAt(lllIIlllIIIlll);
        lllIIlllIIIlll.setY((double)(lllIIlllIIIllI + lIIIllIII[8]));
        FallingBlock lllIIlllIIIlIl = lllIIlllIIlIII.world.spawnFallingBlock(lllIIlllIIIlll, Material.GOLD_BLOCK, lIIIllIII[0]);
    }

    protected void onExplosionPrime(ExplosionPrimeEvent lllIIllIIlllII) {
        RedstoneWars lllIIllIIlllIl;
        super.onExplosionPrime(lllIIllIIlllII);
        Location lllIIllIIlllll = lllIIllIIlllII.getEntity().getLocation();
        JocEquips.Equip lllIIllIIllllI = (JocEquips.Equip)lllIIllIIlllIl.Equips.stream().reduce(lllIIllIIlllIl.Equips.get(lIIIllIII[0]), (lllIIllIIIlIll, lllIIllIIIlIlI) -> {
            JocEquips.Equip equip;
            if (RedstoneWars.lIIIIIIllll(RedstoneWars.lIIIIIIlllI(lllIIllIIIlIll.getTeamSpawnLocation().distance(lllIIllIIlllll), lllIIllIIIlIlI.getTeamSpawnLocation().distance(lllIIllIIlllll)))) {
                equip = lllIIllIIIlIll;
                "".length();
                if (-" ".length() > "  ".length()) {
                    return null;
                }
            } else {
                equip = lllIIllIIIlIlI;
            }
            return equip;
        });
        lllIIllIIlllIl.winGame(lllIIllIIllllI);
    }

    void compactRedstone(Player lllIIllIlIlIlI) {
        PlayerInventory lllIIllIlIlIIl = lllIIllIlIlIlI.getInventory();
        if (RedstoneWars.lIIIIIIllII((int)lllIIllIlIlIIl.contains(Material.REDSTONE, lIIIllIII[10]))) {
            ItemStack[] arritemStack = new ItemStack[lIIIllIII[1]];
            arritemStack[RedstoneWars.lIIIllIII[0]] = new ItemStack(Material.REDSTONE, lIIIllIII[10]);
            "".length();
            lllIIllIlIlIIl.removeItem(arritemStack);
            ItemStack[] arritemStack2 = new ItemStack[lIIIllIII[1]];
            arritemStack2[RedstoneWars.lIIIllIII[0]] = new ItemStack(Material.REDSTONE_BLOCK, lIIIllIII[1]);
            "".length();
            lllIIllIlIlIIl.addItem(arritemStack2);
            lllIIllIlIlIlI.playSound(lllIIllIlIlIlI.getEyeLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.1f);
            lllIIllIlIlIlI.playSound(lllIIllIlIlIlI.getEyeLocation(), Sound.BLOCK_WOOD_HIT, 1.0f, 1.1f);
        }
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        RedstoneWars lllIlIIIIlIlll;
        ArrayList<JocEquips.Equip> lllIlIIIIllIII = new ArrayList<JocEquips.Equip>();
        "".length();
        lllIlIIIIllIII.add(lllIlIIIIlIlll.new JocEquips.Equip(DyeColor.RED, lIIIlIlll[lIIIllIII[0]]));
        "".length();
        lllIlIIIIllIII.add(lllIlIIIIlIlll.new JocEquips.Equip(DyeColor.BLUE, lIIIlIlll[lIIIllIII[1]]));
        return lllIlIIIIllIII;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lllIlIIIIIlIlI) {
        RedstoneWars lllIlIIIIIlIll;
        ArrayList<ItemStack> lllIlIIIIIlllI = new ArrayList<ItemStack>();
        JocEquips.Equip lllIlIIIIIllIl = lllIlIIIIIlIll.obtenirEquip(lllIlIIIIIlIlI);
        "".length();
        lllIlIIIIIlllI.add(new ItemStack(Material.IRON_SWORD, lIIIllIII[1]));
        "".length();
        lllIlIIIIIlllI.add(new ItemStack(Material.IRON_PICKAXE, lIIIllIII[1]));
        "".length();
        lllIlIIIIIlllI.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, lllIlIIIIIllIl));
        "".length();
        lllIlIIIIIlllI.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, lllIlIIIIIllIl));
        "".length();
        lllIlIIIIIlllI.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, lllIlIIIIIllIl));
        "".length();
        lllIlIIIIIlllI.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, lllIlIIIIIllIl));
        ItemStack lllIlIIIIIllII = new ItemStack(Material.BOW, lIIIllIII[1]);
        lllIlIIIIIllII.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, lIIIllIII[2]);
        lllIlIIIIIllII.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, lIIIllIII[1]);
        lllIlIIIIIllII.addUnsafeEnchantment(Enchantment.KNOCKBACK, lIIIllIII[3]);
        "".length();
        lllIlIIIIIlllI.add(lllIlIIIIIllII);
        "".length();
        lllIlIIIIIlllI.add(new ItemStack(Material.ARROW, lIIIllIII[1]));
        return lllIlIIIIIlllI;
    }

    private static boolean lIIIIIIllll(int n) {
        return n > 0;
    }

    @Override
    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lllIIlllllIIII, Player lllIIllllIlIlI, Player lllIIllllIlllI, boolean lllIIllllIllIl) {
        RedstoneWars lllIIlllllIIIl;
        super.onPlayerDamageByPlayer(lllIIlllllIIII, lllIIllllIlIlI, lllIIllllIlllI, lllIIllllIllIl);
    }

    protected void onPlayerPickupItem(PlayerPickupItemEvent lllIIlllIllIII, Player lllIIlllIlIlll) {
        RedstoneWars lllIIlllIlllIl;
        super.onPlayerPickupItem(lllIIlllIllIII, lllIIlllIlIlll);
        Item lllIIlllIllIlI = lllIIlllIllIII.getItem();
        if (RedstoneWars.lIIIIIIlIll((Object)lllIIlllIllIlI.getItemStack().getType(), (Object)Material.REDSTONE)) {
            lllIIlllIlIlll.playSound(lllIIlllIlIlll.getEyeLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.2f);
            lllIIlllIlllIl.compactRedstone(lllIIlllIlIlll);
        }
    }

    private static String lIIIIIIIlIl(String lllIIlIIllIlIl, String lllIIlIIlIllll) {
        lllIIlIIllIlIl = new String(Base64.getDecoder().decode(lllIIlIIllIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllIIlIIllIIll = new StringBuilder();
        char[] lllIIlIIllIIlI = lllIIlIIlIllll.toCharArray();
        int lllIIlIIllIIIl = lIIIllIII[0];
        char[] lllIIlIIlIlIll = lllIIlIIllIlIl.toCharArray();
        int lllIIlIIlIlIlI = lllIIlIIlIlIll.length;
        int lllIIlIIlIlIIl = lIIIllIII[0];
        while (RedstoneWars.lIIIIIlIIII(lllIIlIIlIlIIl, lllIIlIIlIlIlI)) {
            char lllIIlIIllIllI = lllIIlIIlIlIll[lllIIlIIlIlIIl];
            "".length();
            lllIIlIIllIIll.append((char)(lllIIlIIllIllI ^ lllIIlIIllIIlI[lllIIlIIllIIIl % lllIIlIIllIIlI.length]));
            ++lllIIlIIllIIIl;
            ++lllIIlIIlIlIIl;
            "".length();
            if (-" ".length() <= 0) continue;
            return null;
        }
        return String.valueOf(lllIIlIIllIIll);
    }

    private static int lIIIIIIlllI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent lllIIllIlIllll, Block lllIIllIlIlllI) {
        RedstoneWars lllIIllIllIIll;
        super.onBlockPlace(lllIIllIlIllll, lllIIllIlIlllI);
        if (RedstoneWars.lIIIIIIlIll((Object)lllIIllIlIlllI.getType(), (Object)Material.REDSTONE_BLOCK)) {
            boolean bl;
            if (RedstoneWars.lIIIIIIllIl((int)Utils.getFacesVert().stream().allMatch(lllIIllIIIIIll -> GUtils.isValidSolidBlock((Block)lllIIllIlIlllI.getRelative(lllIIllIIIIIll))))) {
                bl = lIIIllIII[1];
                "".length();
                if ((139 ^ 141 ^ "   ".length()) == 0) {
                    return;
                }
            } else {
                bl = lIIIllIII[0];
            }
            lllIIllIlIllll.setCancelled(bl);
        }
    }

    static {
        RedstoneWars.lIIIIIIlIlI();
        RedstoneWars.lIIIIIIlIII();
    }

    private static void lIIIIIIlIII() {
        lIIIlIlll = new String[lIIIllIII[3]];
        RedstoneWars.lIIIlIlll[RedstoneWars.lIIIllIII[0]] = RedstoneWars.lIIIIIIIlIl("GQYcAyoDDw==", "ocnnO");
        RedstoneWars.lIIIlIlll[RedstoneWars.lIIIllIII[1]] = RedstoneWars.lIIIIIIIllI("3UZiuozM0L8=", "AHrNY");
        RedstoneWars.lIIIlIlll[RedstoneWars.lIIIllIII[7]] = RedstoneWars.lIIIIIIIlll("Qh0G9evYKzveIxqrKz++kGTqbf4lzfa3mk/2WanFhoI=", "uUihP");
        RedstoneWars.lIIIlIlll[RedstoneWars.lIIIllIII[11]] = RedstoneWars.lIIIIIIIlIl("MDcOEhgNPA82DRAh", "bRjal");
    }

    public RedstoneWars() {
        RedstoneWars lllIlIIIIlllIl;
        lllIlIIIIlllIl.chargeCount = lIIIllIII[0];
    }

    private static String lIIIIIIIllI(String lllIIlIlIIllII, String lllIIlIlIIllIl) {
        try {
            SecretKeySpec lllIIlIlIlIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIIlIlIIllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllIIlIlIlIIII = Cipher.getInstance("Blowfish");
            lllIIlIlIlIIII.init(lIIIllIII[7], lllIIlIlIlIIIl);
            return new String(lllIIlIlIlIIII.doFinal(Base64.getDecoder().decode(lllIIlIlIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIlIIllll) {
            lllIIlIlIIllll.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getBaseSkillUnlockerAmount() {
        RedstoneWars lllIIllllIIllI;
        return (int)(1.0 + Math.floor((double)lllIIllllIIllI.segonsTranscorreguts() / 200.0));
    }

    @Override
    public boolean giveSnowLauncherOnKill() {
        return lIIIllIII[1];
    }

    @Override
    public String getGameName() {
        return lIIIlIlll[lIIIllIII[11]];
    }

    private static boolean lIIIIIlIIII(int n, int n2) {
        return n < n2;
    }

    protected void onEntityExplode(EntityExplodeEvent lllIIllIIlIlIl, Entity lllIIllIIlIIIl) {
        RedstoneWars lllIIllIIlIllI;
        super.onEntityExplode(lllIIllIIlIlIl, lllIIllIIlIIIl);
        lllIIllIIlIlIl.setYield((float)((double)lllIIllIIlIlIl.getYield() * 1.2));
    }

    private static boolean lIIIIIIllIl(int n) {
        return n == 0;
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent lllIIllllllIlI, Player lllIIllllllIIl, Player lllIIllllllIII) {
        RedstoneWars lllIIllllllIll;
        super.onPlayerDeathByPlayer(lllIIllllllIlI, lllIIllllllIIl, lllIIllllllIII);
        Block lllIIlllllllII = lllIIllllllIIl.getLocation().getBlock();
        if (RedstoneWars.lIIIIIIlIll((Object)lllIIlllllllII.getType(), (Object)Material.AIR)) {
            lllIIlllllllII.setType(Material.REDSTONE_ORE);
        }
        GUtils.getLocationsCircle((Location)lllIIllllllIIl.getEyeLocation(), (Double)1.1, (int)lIIIllIII[4]).forEach(lllIIlIllIlllI -> {
            RedstoneWars lllIIlIllIllII;
            FallingBlock lllIIlIllIllIl = lllIIlIllIllII.world.spawnFallingBlock(lllIIlIllIlllI, Material.REDSTONE_ORE, lIIIllIII[0]);
            lllIIlIllIllIl.setVelocity(Utils.CrearVector(lllIIllllllIII.getLocation(), lllIIlIllIlllI).normalize().add(new Vector(0.0, 0.35, 0.0).multiply(0.7)));
            lllIIlIllIllIl.setDropItem(lIIIllIII[0]);
        });
    }

    private static String lIIIIIIIlll(String lllIIlIlIlllIl, String lllIIlIlIllIII) {
        try {
            SecretKeySpec lllIIlIllIIIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIlIlIllIII.getBytes(StandardCharsets.UTF_8)), lIIIllIII[12]), "DES");
            Cipher lllIIlIllIIIII = Cipher.getInstance("DES");
            lllIIlIllIIIII.init(lIIIllIII[7], lllIIlIllIIIlI);
            return new String(lllIIlIllIIIII.doFinal(Base64.getDecoder().decode(lllIIlIlIlllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIlIlllll) {
            lllIIlIlIlllll.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean getBlockBreakPlace() {
        return lIIIllIII[0];
    }

    @Override
    public void heartbeat() {
        RedstoneWars lllIIllllIIIll;
        super.heartbeat();
        lllIIllllIIIll.getPlayers().forEach(lllIIllllIIIll::compactRedstone);
        if (RedstoneWars.lIIIIIIllII((int)lllIIllllIIIll.JocIniciat.booleanValue()) && RedstoneWars.lIIIIIIllIl(lllIIllllIIIll.segonsTranscorreguts() % lIIIllIII[5])) {
            lllIIllllIIIll.placeBonusOnTheMiddle();
        }
        if (RedstoneWars.lIIIIIIllII((int)lllIIllllIIIll.JocIniciat.booleanValue()) && RedstoneWars.lIIIIIIllIl(lllIIllllIIIll.segonsTranscorreguts() % lIIIllIII[6])) {
            lllIIllllIIIll.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIlIlll[lIIIllIII[7]])));
        }
    }

    private static boolean lIIIIIIlIll(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent lllIIllIlllIll, Block lllIIllIlllIlI) {
        RedstoneWars lllIIllIllllII;
        super.onBlockBreak(lllIIllIlllIll, lllIIllIlllIlI);
        if (RedstoneWars.lIIIIIIlIll((Object)lllIIllIlllIlI.getType(), (Object)Material.GLOWING_REDSTONE_ORE)) {
            lllIIllIlllIll.setCancelled(lIIIllIII[0]);
        }
        if (RedstoneWars.lIIIIIIlIll((Object)lllIIllIlllIlI.getType(), (Object)Material.GOLD_BLOCK)) {
            GUtils.getLocationsCircle((Location)lllIIllIlllIlI.getLocation(), (Double)1.1, (int)lIIIllIII[9]).forEach(lllIIlIlllIllI -> {
                RedstoneWars lllIIlIlllllII;
                FallingBlock lllIIlIllllIIl = lllIIlIlllllII.world.spawnFallingBlock(lllIIlIlllIllI, Material.REDSTONE_ORE, lIIIllIII[0]);
                lllIIlIllllIIl.setVelocity(Utils.CrearVector(lllIIllIlllIlI.getLocation(), lllIIlIlllIllI).normalize().add(new Vector(0.0, 0.45, 0.0).multiply(0.7)));
                lllIIlIllllIIl.setDropItem(lIIIllIII[0]);
            });
            lllIIllIlllIlI.setType(Material.AIR);
            lllIIllIllllII.getWorld().playSound(lllIIllIlllIlI.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 2.0f, 1.15f);
        }
    }

    private static boolean lIIIIIIllII(int n) {
        return n != 0;
    }

    protected void onInventoryClick(InventoryClickEvent lllIIlllIIlllI, Inventory lllIIlllIlIIII) {
        RedstoneWars lllIIlllIlIIlI;
        super.onInventoryClick(lllIIlllIIlllI, lllIIlllIlIIII);
        lllIIlllIlIIlI.compactRedstone((Player)lllIIlllIIlllI.getWhoClicked());
    }

    private static void lIIIIIIlIlI() {
        lIIIllIII = new int[13];
        RedstoneWars.lIIIllIII[0] = (3 + 159 - 114 + 178 ^ 50 + 100 - 37 + 77) & (188 + 114 - 228 + 147 ^ 49 + 118 - 139 + 101 ^ -" ".length());
        RedstoneWars.lIIIllIII[1] = " ".length();
        RedstoneWars.lIIIllIII[2] = 80 + 116 - 165 + 107 ^ 52 + 14 - -37 + 25;
        RedstoneWars.lIIIllIII[3] = 135 ^ 131;
        RedstoneWars.lIIIllIII[4] = 122 ^ 50;
        RedstoneWars.lIIIllIII[5] = 149 ^ 177 ^ (59 ^ 79);
        RedstoneWars.lIIIllIII[6] = 174 + 77 - 123 + 72;
        RedstoneWars.lIIIllIII[7] = "  ".length();
        RedstoneWars.lIIIllIII[8] = 211 ^ 165 ^ (115 ^ 45);
        RedstoneWars.lIIIllIII[9] = 225 ^ 193;
        RedstoneWars.lIIIllIII[10] = 140 + 70 - 172 + 103 ^ 93 + 12 - 64 + 91;
        RedstoneWars.lIIIllIII[11] = "   ".length();
        RedstoneWars.lIIIllIII[12] = 48 ^ 17 ^ (84 ^ 125);
    }

    @Override
    protected void setCustomGameRules() {
    }
}

