/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.ItemButton
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEvent
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEventHandler
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.BookMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.biel.lobby.utilities;

import com.biel.BielAPI.Utils.ItemButton;
import com.biel.lobby.Com;
import com.biel.lobby.GestorMapes;
import com.biel.lobby.lobby;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.PlayerData;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Options {
    private static final /* synthetic */ int[] lIlIIlIlI;
    private static final /* synthetic */ String[] lIlIIIlII;

    public static void giveCommonOptionsMenu(Player lIllllllIlllII) {
        ItemButton.clearButtons((Player)lIllllllIlllII);
        PlayerInventory lIllllllIllllI = lIllllllIlllII.getInventory();
        String[] arrstring = new String[lIlIIlIlI[2]];
        arrstring[Options.lIlIIlIlI[1]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIlIIIlII[lIlIIlIlI[17]]));
        ItemButton lIllllllIlllIl = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BOOK), String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIlIIIlII[lIlIIlIlI[16]])), arrstring), lIllllllIlllII, lIllllllIlIlll -> Com.teleportPlayerToLobby(lIllllllIlllII));
        lIllllllIllllI.setItem(lIlIIlIlI[9], lIllllllIlllIl.getItemStack());
    }

    private static String lIIlIIIlllI(String lIlllllIlIllll, String lIlllllIlIlllI) {
        try {
            SecretKeySpec lIlllllIllIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlllllIlIlllI.getBytes(StandardCharsets.UTF_8)), lIlIIlIlI[9]), "DES");
            Cipher lIlllllIllIIll = Cipher.getInstance("DES");
            lIlllllIllIIll.init(lIlIIlIlI[3], lIlllllIllIlII);
            return new String(lIlllllIllIIll.doFinal(Base64.getDecoder().decode(lIlllllIlIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllllIllIIlI) {
            lIlllllIllIIlI.printStackTrace();
            return null;
        }
    }

    private static void lIIlIllIlIl() {
        lIlIIlIlI = new int[21];
        Options.lIlIIlIlI[0] = 191 ^ 181;
        Options.lIlIIlIlI[1] = (252 ^ 160) & ~(63 ^ 99);
        Options.lIlIIlIlI[2] = " ".length();
        Options.lIlIIlIlI[3] = "  ".length();
        Options.lIlIIlIlI[4] = "   ".length();
        Options.lIlIIlIlI[5] = 140 ^ 136;
        Options.lIlIIlIlI[6] = 71 ^ 66;
        Options.lIlIIlIlI[7] = 33 ^ 39;
        Options.lIlIIlIlI[8] = 253 ^ 160 ^ (6 ^ 92);
        Options.lIlIIlIlI[9] = 96 ^ 104;
        Options.lIlIIlIlI[10] = 93 ^ 18 ^ (28 ^ 90);
        Options.lIlIIlIlI[11] = 78 + 87 - 69 + 64 ^ 134 + 136 - 216 + 117;
        Options.lIlIIlIlI[12] = 61 ^ 68 ^ (113 ^ 4);
        Options.lIlIIlIlI[13] = 46 ^ 35;
        Options.lIlIIlIlI[14] = 92 ^ 6 ^ (223 ^ 139);
        Options.lIlIIlIlI[15] = 135 ^ 136;
        Options.lIlIIlIlI[16] = 172 ^ 188;
        Options.lIlIIlIlI[17] = 183 ^ 166;
        Options.lIlIIlIlI[18] = 120 ^ 106;
        Options.lIlIIlIlI[19] = 44 ^ 63;
        Options.lIlIIlIlI[20] = 11 + 13 - -43 + 91 ^ 27 + 88 - -6 + 17;
    }

    private static String lIIlIIlIllI(String lIlllllIlllllI, String lIlllllIlllIll) {
        try {
            SecretKeySpec lIllllllIIIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlllllIlllIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIllllllIIIIII = Cipher.getInstance("Blowfish");
            lIllllllIIIIII.init(lIlIIlIlI[3], lIllllllIIIIIl);
            return new String(lIllllllIIIIII.doFinal(Base64.getDecoder().decode(lIlllllIlllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllllIllllll) {
            lIlllllIllllll.printStackTrace();
            return null;
        }
    }

    static {
        Options.lIIlIllIlIl();
        Options.lIIlIllIIll();
    }

    private static void lIIlIllIIll() {
        lIlIIIlII = new String[lIlIIlIlI[20]];
        Options.lIlIIIlII[Options.lIlIIlIlI[1]] = Options.lIIlIIIlllI("wRH4I1zLeQLRK5sRoYaQ1wVIIct6ZRx1", "IqLNx");
        Options.lIlIIIlII[Options.lIlIIlIlI[2]] = Options.lIIlIIlIIlI("PSQhFUweYToeGhcoJxEeG2Y3FUwGIz8VGAAnPQMcHTQnXg==", "rFSpl");
        Options.lIlIIIlII[Options.lIlIIlIlI[3]] = Options.lIIlIIlIIlI("MC4qJhgMKGQ=", "bODMq");
        Options.lIlIIIlII[Options.lIlIIlIlI[4]] = Options.lIIlIIlIllI("I9ET+DN/oCY=", "vAuGu");
        Options.lIlIIIlII[Options.lIlIIlIlI[5]] = Options.lIIlIIlIIlI("Yw==", "JsDzJ");
        Options.lIlIIIlII[Options.lIlIIlIlI[6]] = Options.lIIlIIlIllI("3a7E4j+zCKNY3a+qMvDSYYAP8FSx7erTDhB2TfPz7ZXGrdwfBwFGlg==", "unzVB");
        Options.lIlIIIlII[Options.lIlIIlIlI[7]] = Options.lIIlIIIlllI("gQYSiTQf1wU=", "vEzNx");
        Options.lIlIIIlII[Options.lIlIIlIlI[8]] = Options.lIIlIIlIIlI("RGc=", "dDIAW");
        Options.lIlIIIlII[Options.lIlIIlIlI[9]] = Options.lIIlIIIlllI("gr0xO+SK8dSa+YbDUg/ryMhCDhBVXRdA", "WbXvw");
        Options.lIlIIIlII[Options.lIlIIlIlI[10]] = Options.lIIlIIlIIlI("", "JhGTb");
        Options.lIlIIIlII[Options.lIlIIlIlI[0]] = Options.lIIlIIlIllI("lnW8eaP4ihNLonsst3xPUg==", "ZacMq");
        Options.lIlIIIlII[Options.lIlIIlIlI[11]] = Options.lIIlIIIlllI("E5zh4wK5p9RmOWrfe2at9xlN+rrhziGK", "mascy");
        Options.lIlIIIlII[Options.lIlIIlIlI[12]] = Options.lIIlIIlIllI("hBdX9cPzPiTLbOf9PaOinkmgAsDgCsxZjYrZ/6g1OWSuiIb79kOhGs6/hlXCDNap", "UKqvC");
        Options.lIlIIIlII[Options.lIlIIlIlI[13]] = Options.lIIlIIIlllI("GFha5vRyGoo=", "JbaSb");
        Options.lIlIIIlII[Options.lIlIIlIlI[14]] = Options.lIIlIIlIllI("qbX+ybG/IRXVUO7LS00yNA==", "JMscg");
        Options.lIlIIIlII[Options.lIlIIlIlI[15]] = Options.lIIlIIlIllI("EXpLWjMql1GlDDV8JTw5y9wD86ynTnZy3vwZINq+OxXMJeIR9yPEUJj/AwRNAFBNRfcvuPm8fB+1+63BWI5keA9qUJGDjQ4H9p5+Y2Fs+5pbhMbZccwxU8tCxLxUfrrwqkKT9UO+rLSMGYkhhx1pdOtKuWp/SDYQQRU3QxfQSBtNMo1u/0oLoi/YPkxBsP5kn9quQAZTtK1XjhrM2kBh/g==", "nHkLx");
        Options.lIlIIIlII[Options.lIlIIlIlI[16]] = Options.lIIlIIIlllI("TxhlO7MoZMA=", "yWzUz");
        Options.lIlIIIlII[Options.lIlIIlIlI[17]] = Options.lIIlIIlIllI("bI6adfOkjIb0L28P1R+9Vd/OzHRuqRTl", "Bmwjf");
        Options.lIlIIIlII[Options.lIlIIlIlI[18]] = Options.lIIlIIIlllI("RZBxwmyh43U=", "WnKdr");
        Options.lIlIIIlII[Options.lIlIIlIlI[19]] = Options.lIIlIIIlllI("XcEHJx795/Vh+GtY+pyYvw==", "WgdHO");
    }

    private static String lIIlIIlIIlI(String lIlllllIlIIIIl, String lIlllllIlIIIII) {
        lIlllllIlIIIIl = new String(Base64.getDecoder().decode(lIlllllIlIIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlllllIIlllll = new StringBuilder();
        char[] lIlllllIIllllI = lIlllllIlIIIII.toCharArray();
        int lIlllllIIlllIl = lIlIIlIlI[1];
        char[] lIlllllIIlIlll = lIlllllIlIIIIl.toCharArray();
        int lIlllllIIlIllI = lIlllllIIlIlll.length;
        int lIlllllIIlIlIl = lIlIIlIlI[1];
        while (Options.lIIlIllIlll(lIlllllIIlIlIl, lIlllllIIlIllI)) {
            char lIlllllIlIIIlI = lIlllllIIlIlll[lIlllllIIlIlIl];
            "".length();
            lIlllllIIlllll.append((char)(lIlllllIlIIIlI ^ lIlllllIIllllI[lIlllllIIlllIl % lIlllllIIllllI.length]));
            ++lIlllllIIlllIl;
            ++lIlllllIIlIlIl;
            "".length();
            if (-"  ".length() <= 0) continue;
            return null;
        }
        return String.valueOf(lIlllllIIlllll);
    }

    public Options() {
        Options lIllllllllllll;
    }

    private static boolean lIIlIllIllI(int n) {
        return n != 0;
    }

    private static boolean lIIlIllIlll(int n, int n2) {
        return n < n2;
    }

    public static void giveStartingButtons(Player lIllllllllIlII) {
        PlayerData lIllllllllIIll = new PlayerData(lIllllllllIlII);
        ItemButton.clearButtons((Player)lIllllllllIlII);
        PlayerInventory lIllllllllIIlI = lIllllllllIlII.getInventory();
        lIllllllllIIlI.clear();
        ItemStack lIllllllllIIIl = new ItemStack(Material.DIAMOND_BLOCK);
        lIllllllllIIIl.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, lIlIIlIlI[0]);
        String[] arrstring = new String[lIlIIlIlI[2]];
        arrstring[Options.lIlIIlIlI[1]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIlIIIlII[lIlIIlIlI[2]]));
        ItemButton lIllllllllIIII = new ItemButton(Utils.setItemNameAndLore(lIllllllllIIIl, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIlIIIlII[lIlIIlIlI[1]])), arrstring), lIllllllllIlII, lIllllllIIIlll -> lobby.getPlugin().gest.ObrirMenuMapes(Bukkit.getPlayer((String)lIllllllllIlII.getName())));
        lIllllllllIIlI.setItem(lIlIIlIlI[2], lIllllllllIIII.getItemStack());
        String[] arrstring2 = new String[lIlIIlIlI[3]];
        arrstring2[Options.lIlIIlIlI[1]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIlIIIlII[lIlIIlIlI[6]]));
        arrstring2[Options.lIlIIlIlI[2]] = String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_AQUA).append(lIlIIIlII[lIlIIlIlI[7]]).append((Object)ChatColor.WHITE).append(Math.round(lIllllllllIIll.getElo())).append((Object)ChatColor.YELLOW).append(lIlIIIlII[lIlIIlIlI[8]]).append(lIllllllllIIll.getRank()));
        ItemButton lIlllllllIllll = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.GOLD_INGOT), String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIlIIIlII[lIlIIlIlI[3]]).append((Object)ChatColor.YELLOW).append(lIlIIIlII[lIlIIlIlI[4]]).append(lIllllllllIIll.getRank()).append(lIlIIIlII[lIlIIlIlI[5]])), arrstring2), lIllllllllIlII, lIllllllIIlIlI -> Com.displayRanking(lIllllllIIlIlI.getPlayer()));
        lIllllllllIIlI.setItem(lIlIIlIlI[4], lIlllllllIllll.getItemStack());
        String[] arrstring3 = new String[lIlIIlIlI[2]];
        arrstring3[Options.lIlIIlIlI[1]] = lIlIIIlII[lIlIIlIlI[10]];
        ItemButton lIlllllllIlllI = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.BLAZE_POWDER), String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIlIIIlII[lIlIIlIlI[9]])), arrstring3), lIllllllllIlII, lIllllllIIlllI -> Com.getGest().openAllGamesMenu(lIllllllIIlllI.getPlayer()));
        lIllllllllIIlI.setItem(lIlIIlIlI[6], lIlllllllIlllI.getItemStack());
        if (Options.lIIlIllIllI((int)lIllllllllIlII.isOp())) {
            String[] arrstring4 = new String[lIlIIlIlI[3]];
            arrstring4[Options.lIlIIlIlI[1]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIlIIIlII[lIlIIlIlI[11]]));
            arrstring4[Options.lIlIIlIlI[2]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIlIIIlII[lIlIIlIlI[12]]));
            ItemButton lIllllllllIlIl = new ItemButton(Utils.setItemNameAndLore(new ItemStack(Material.LEVER), String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIlIIIlII[lIlIIlIlI[0]])), arrstring4), lIllllllllIlII, lIllllllIlIIlI -> {
                Iterator lIllllllIlIIIl = Bukkit.getOnlinePlayers().iterator();
                while (Options.lIIlIllIllI((int)lIllllllIlIIIl.hasNext())) {
                    Player lIllllllIlIIll = (Player)lIllllllIlIIIl.next();
                    lIllllllIlIIll.kickPlayer(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIlIIIlII[lIlIIlIlI[18]]).append((Object)ChatColor.BOLD).append(lIlIIIlII[lIlIIlIlI[19]])));
                    "".length();
                    if (-"   ".length() <= 0) continue;
                    return;
                }
                Bukkit.getServer().shutdown();
            });
            lIllllllllIIlI.setItem(lIlIIlIlI[5], lIllllllllIlIl.getItemStack());
        }
        ItemStack lIlllllllIllIl = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta lIlllllllIllII = (BookMeta)lIlllllllIllIl.getItemMeta();
        lIlllllllIllII.setAuthor(lIlIIIlII[lIlIIlIlI[13]]);
        "".length();
        lIlllllllIllII.setTitle(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIlIIIlII[lIlIIlIlI[14]])));
        String[] arrstring5 = new String[lIlIIlIlI[2]];
        arrstring5[Options.lIlIIlIlI[1]] = lIlIIIlII[lIlIIlIlI[15]];
        lIlllllllIllII.addPage(arrstring5);
        "".length();
        lIlllllllIllIl.setItemMeta((ItemMeta)lIlllllllIllII);
        lIllllllllIlII.getInventory().setItem(lIlIIlIlI[9], lIlllllllIllIl);
    }
}

