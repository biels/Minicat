/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocObjectius;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BoletumDTC
extends JocObjectius {
    private static final /* synthetic */ int[] lIIIlIll;
    private static final /* synthetic */ String[] lIIIlIII;

    public BoletumDTC() {
        BoletumDTC lIllIllIllIlIll;
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        BoletumDTC lIllIllIlIlllII;
        ArrayList<JocEquips.Equip> lIllIllIlIllIll = new ArrayList<JocEquips.Equip>();
        "".length();
        lIllIllIlIllIll.add(lIllIllIlIlllII.new JocObjectius.EquipObjectius(DyeColor.ORANGE, lIIIlIII[lIIIlIll[2]]));
        "".length();
        lIllIllIlIllIll.add(lIllIllIlIlllII.new JocObjectius.EquipObjectius(DyeColor.GREEN, lIIIlIII[lIIIlIll[3]]));
        return lIllIllIlIllIll;
    }

    private static boolean lllllIlIl(int n, int n2) {
        return n < n2;
    }

    @Override
    protected ArrayList<JocObjectius.Objectiu> getDesiredObjectivesTeam(JocObjectius.EquipObjectius lIllIllIllIIIIl) {
        BoletumDTC lIllIllIllIIIlI;
        ArrayList<JocObjectius.Objectiu> lIllIllIllIIlII = new ArrayList<JocObjectius.Objectiu>();
        ArrayList<Location> lIllIllIllIIIll = lIllIllIllIIIlI.pMapaActual().ObtenirLocations(String.valueOf(new StringBuilder().append(lIIIlIII[lIIIlIll[0]]).append(Integer.toString(lIllIllIllIIIIl.getId() + lIIIlIll[1]))), lIllIllIllIIIlI.getWorld());
        "".length();
        lIllIllIllIIlII.add(lIllIllIllIIIlI.new JocObjectius.ObjectiuBlockBreak(String.valueOf(new StringBuilder().append(lIIIlIII[lIIIlIll[1]]).append(lIllIllIllIIIIl.getAdjectiu())), lIllIllIllIIIll.get(lIIIlIll[0])));
        return lIllIllIllIIlII;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIllIllIlIIIllI) {
        BoletumDTC lIllIllIlIIllII;
        ArrayList<ItemStack> lIllIllIlIIlIlI = new ArrayList<ItemStack>();
        JocEquips.Equip lIllIllIlIIlIIl = lIllIllIlIIllII.obtenirEquip(lIllIllIlIIIllI);
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.STONE_SWORD, lIIIlIll[1]));
        ItemStack lIllIllIlIIlIII = new ItemStack(Material.BOW, lIIIlIll[1]);
        lIllIllIlIIlIII.addUnsafeEnchantment(Enchantment.DURABILITY, lIIIlIll[4]);
        lIllIllIlIIlIII.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, lIIIlIll[1]);
        "".length();
        lIllIllIlIIlIlI.add(lIllIllIlIIlIII);
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.DIAMOND_PICKAXE, lIIIlIll[1]));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.IRON_AXE));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.GLASS, lIIIlIll[5]));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.WOOD, lIIIlIll[6]));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.WOOD, lIIIlIll[6]));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.CHAINMAIL_HELMET, lIIIlIll[1]));
        "".length();
        lIllIllIlIIlIlI.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, lIllIllIlIIlIIl));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.IRON_BOOTS, lIIIlIll[1]));
        "".length();
        lIllIllIlIIlIlI.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, lIllIllIlIIlIIl));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.LADDER, lIIIlIll[7]));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.GOLDEN_APPLE, lIIIlIll[2]));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.ARROW, lIIIlIll[1]));
        "".length();
        lIllIllIlIIlIlI.add(new ItemStack(Material.WEB, lIIIlIll[1]));
        return lIllIllIlIIlIlI;
    }

    private static boolean lllllIlII(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected void setCustomGameRules() {
    }

    static {
        BoletumDTC.lllllIIll();
        BoletumDTC.llllIlllI();
    }

    private static String llllIIlII(String lIllIllIIlIlIIl, String lIllIllIIlIllIl) {
        lIllIllIIlIlIIl = new String(Base64.getDecoder().decode(lIllIllIIlIlIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIllIllIIlIllII = new StringBuilder();
        char[] lIllIllIIlIlIll = lIllIllIIlIllIl.toCharArray();
        int lIllIllIIlIlIlI = lIIIlIll[0];
        char[] lIllIllIIlIIlII = lIllIllIIlIlIIl.toCharArray();
        int lIllIllIIlIIIll = lIllIllIIlIIlII.length;
        int lIllIllIIlIIIlI = lIIIlIll[0];
        while (BoletumDTC.lllllIlIl(lIllIllIIlIIIlI, lIllIllIIlIIIll)) {
            char lIllIllIIlIllll = lIllIllIIlIIlII[lIllIllIIlIIIlI];
            "".length();
            lIllIllIIlIllII.append((char)(lIllIllIIlIllll ^ lIllIllIIlIlIll[lIllIllIIlIlIlI % lIllIllIIlIlIll.length]));
            ++lIllIllIIlIlIlI;
            ++lIllIllIIlIIIlI;
            "".length();
            if (-" ".length() < ((7 ^ 86) & ~(113 ^ 32))) continue;
            return null;
        }
        return String.valueOf(lIllIllIIlIllII);
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent lIllIllIIlllIlI, Block lIllIllIIllllII) {
        BoletumDTC lIllIllIIlllllI;
        super.onBlockPlace(lIllIllIIlllIlI, lIllIllIIllllII);
        if (BoletumDTC.lllllIlII((Object)lIllIllIIllllII.getType(), (Object)Material.OBSIDIAN)) {
            lIllIllIIlllIlI.setCancelled(lIIIlIll[1]);
            lIllIllIIlllIlI.getPlayer().damage(15.0);
        }
    }

    @Override
    protected void customJocIniciat() {
        BoletumDTC lIllIllIlIlIlII;
        super.customJocIniciat();
        lIllIllIlIlIlII.setBlockBreakPlace(lIIIlIll[1]);
        lIllIllIlIlIlII.setGiveStartingItemsRespawn(lIIIlIll[1]);
    }

    @Override
    public String getGameName() {
        return lIIIlIII[lIIIlIll[8]];
    }

    @Override
    protected void customJocFinalitzat() {
        BoletumDTC lIllIllIlIlIllI;
        super.customJocFinalitzat();
        lIllIllIlIlIllI.setBlockBreakPlace(lIIIlIll[0]);
    }

    private static void llllIlllI() {
        lIIIlIII = new String[lIIIlIll[9]];
        BoletumDTC.lIIIlIII[BoletumDTC.lIIIlIll[0]] = BoletumDTC.llllIIlII("AigxIgE=", "AGCGr");
        BoletumDTC.lIIIlIII[BoletumDTC.lIIIlIll[1]] = BoletumDTC.llllIIlIl("j0UZotrRsRo=", "qLzpT");
        BoletumDTC.lIIIlIII[BoletumDTC.lIIIlIll[2]] = BoletumDTC.llllIIlIl("N/5db7b2w2o=", "NpHZC");
        BoletumDTC.lIIIlIII[BoletumDTC.lIIIlIll[3]] = BoletumDTC.llllIIlIl("Pp0bnhk5oBE=", "aWTpq");
        BoletumDTC.lIIIlIII[BoletumDTC.lIIIlIll[8]] = BoletumDTC.llllIIlIl("NqSOl0+qt2LSK9cKJCPNKg==", "WqctO");
    }

    private static String llllIIlIl(String lIllIllIIIlIlll, String lIllIllIIIllIII) {
        try {
            SecretKeySpec lIllIllIIIlllII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIllIIIllIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIllIllIIIllIll = Cipher.getInstance("Blowfish");
            lIllIllIIIllIll.init(lIIIlIll[2], lIllIllIIIlllII);
            return new String(lIllIllIIIllIll.doFinal(Base64.getDecoder().decode(lIllIllIIIlIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIllIIIllIlI) {
            lIllIllIIIllIlI.printStackTrace();
            return null;
        }
    }

    private static void lllllIIll() {
        lIIIlIll = new int[10];
        BoletumDTC.lIIIlIll[0] = (56 ^ 18) & ~(117 ^ 95);
        BoletumDTC.lIIIlIll[1] = " ".length();
        BoletumDTC.lIIIlIll[2] = "  ".length();
        BoletumDTC.lIIIlIll[3] = "   ".length();
        BoletumDTC.lIIIlIll[4] = 38 ^ 44;
        BoletumDTC.lIIIlIll[5] = 66 ^ 98;
        BoletumDTC.lIIIlIll[6] = 236 ^ 130 ^ (119 ^ 89);
        BoletumDTC.lIIIlIll[7] = 62 ^ 54;
        BoletumDTC.lIIIlIll[8] = 86 + 98 - 85 + 96 ^ 47 + 48 - 38 + 142;
        BoletumDTC.lIIIlIll[9] = 155 ^ 130 ^ (170 ^ 182);
    }
}

