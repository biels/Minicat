/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.DyeColor
 */
package com.biel.lobby.utilities;

import com.google.common.collect.Maps;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

public abstract class ColorConverter {
    private static final /* synthetic */ String[] lIIllllII;
    private static /* synthetic */ Map<ChatColor, String> chatHexMap;
    private static /* synthetic */ Map<ChatColor, String> chatRaw;
    private static /* synthetic */ Map<DyeColor, String> dyeHexMap;
    private static final /* synthetic */ int[] lIllIIIll;
    private static /* synthetic */ Map<DyeColor, ChatColor> dyeChatMap;

    private static String lIIIlllIIIl(String lIlIllIIIIIIll, String lIlIllIIIIIlll) {
        lIlIllIIIIIIll = new String(Base64.getDecoder().decode(lIlIllIIIIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlIllIIIIIllI = new StringBuilder();
        char[] lIlIllIIIIIlIl = lIlIllIIIIIlll.toCharArray();
        int lIlIllIIIIIlII = lIllIIIll[0];
        char[] lIlIlIlllllllI = lIlIllIIIIIIll.toCharArray();
        int lIlIlIllllllIl = lIlIlIlllllllI.length;
        int lIlIlIllllllII = lIllIIIll[0];
        while (ColorConverter.lIIllllIIlI(lIlIlIllllllII, lIlIlIllllllIl)) {
            char lIlIllIIIIlIIl = lIlIlIlllllllI[lIlIlIllllllII];
            "".length();
            lIlIllIIIIIllI.append((char)(lIlIllIIIIlIIl ^ lIlIllIIIIIlIl[lIlIllIIIIIlII % lIlIllIIIIIlIl.length]));
            ++lIlIllIIIIIlII;
            ++lIlIlIllllllII;
            "".length();
            if (-(44 ^ 88 ^ (101 ^ 20)) < 0) continue;
            return null;
        }
        return String.valueOf(lIlIllIIIIIllI);
    }

    static {
        ColorConverter.lIIlllIllIl();
        ColorConverter.lIIlIlllIlI();
        dyeChatMap = Maps.newHashMap();
        "".length();
        dyeChatMap.put(DyeColor.BLACK, ChatColor.DARK_GRAY);
        "".length();
        dyeChatMap.put(DyeColor.BLUE, ChatColor.DARK_BLUE);
        "".length();
        dyeChatMap.put(DyeColor.BROWN, ChatColor.GOLD);
        "".length();
        dyeChatMap.put(DyeColor.CYAN, ChatColor.AQUA);
        "".length();
        dyeChatMap.put(DyeColor.GRAY, ChatColor.GRAY);
        "".length();
        dyeChatMap.put(DyeColor.GREEN, ChatColor.DARK_GREEN);
        "".length();
        dyeChatMap.put(DyeColor.LIGHT_BLUE, ChatColor.BLUE);
        "".length();
        dyeChatMap.put(DyeColor.LIME, ChatColor.GREEN);
        "".length();
        dyeChatMap.put(DyeColor.MAGENTA, ChatColor.LIGHT_PURPLE);
        "".length();
        dyeChatMap.put(DyeColor.ORANGE, ChatColor.GOLD);
        "".length();
        dyeChatMap.put(DyeColor.PINK, ChatColor.LIGHT_PURPLE);
        "".length();
        dyeChatMap.put(DyeColor.PURPLE, ChatColor.DARK_PURPLE);
        "".length();
        dyeChatMap.put(DyeColor.RED, ChatColor.DARK_RED);
        "".length();
        dyeChatMap.put(DyeColor.SILVER, ChatColor.GRAY);
        "".length();
        dyeChatMap.put(DyeColor.WHITE, ChatColor.WHITE);
        "".length();
        dyeChatMap.put(DyeColor.YELLOW, ChatColor.YELLOW);
        chatHexMap = Maps.newHashMap();
        "".length();
        chatHexMap.put(ChatColor.BLACK, lIIllllII[lIllIIIll[15]]);
        "".length();
        chatHexMap.put(ChatColor.DARK_BLUE, lIIllllII[lIllIIIll[16]]);
        "".length();
        chatHexMap.put(ChatColor.DARK_GREEN, lIIllllII[lIllIIIll[17]]);
        "".length();
        chatHexMap.put(ChatColor.DARK_AQUA, lIIllllII[lIllIIIll[7]]);
        "".length();
        chatHexMap.put(ChatColor.DARK_RED, lIIllllII[lIllIIIll[18]]);
        "".length();
        chatHexMap.put(ChatColor.DARK_PURPLE, lIIllllII[lIllIIIll[19]]);
        "".length();
        chatHexMap.put(ChatColor.GOLD, lIIllllII[lIllIIIll[20]]);
        "".length();
        chatHexMap.put(ChatColor.GRAY, lIIllllII[lIllIIIll[21]]);
        "".length();
        chatHexMap.put(ChatColor.DARK_GRAY, lIIllllII[lIllIIIll[22]]);
        "".length();
        chatHexMap.put(ChatColor.BLUE, lIIllllII[lIllIIIll[23]]);
        "".length();
        chatHexMap.put(ChatColor.GREEN, lIIllllII[lIllIIIll[24]]);
        "".length();
        chatHexMap.put(ChatColor.AQUA, lIIllllII[lIllIIIll[25]]);
        "".length();
        chatHexMap.put(ChatColor.RED, lIIllllII[lIllIIIll[26]]);
        "".length();
        chatHexMap.put(ChatColor.LIGHT_PURPLE, lIIllllII[lIllIIIll[27]]);
        "".length();
        chatHexMap.put(ChatColor.YELLOW, lIIllllII[lIllIIIll[28]]);
        "".length();
        chatHexMap.put(ChatColor.WHITE, lIIllllII[lIllIIIll[29]]);
        chatRaw = Maps.newHashMap();
        "".length();
        chatRaw.put(ChatColor.BLACK, lIIllllII[lIllIIIll[30]]);
        "".length();
        chatRaw.put(ChatColor.DARK_BLUE, lIIllllII[lIllIIIll[31]]);
        "".length();
        chatRaw.put(ChatColor.DARK_GREEN, lIIllllII[lIllIIIll[32]]);
        "".length();
        chatRaw.put(ChatColor.DARK_AQUA, lIIllllII[lIllIIIll[33]]);
        "".length();
        chatRaw.put(ChatColor.DARK_RED, lIIllllII[lIllIIIll[34]]);
        "".length();
        chatRaw.put(ChatColor.DARK_PURPLE, lIIllllII[lIllIIIll[35]]);
        "".length();
        chatRaw.put(ChatColor.GOLD, lIIllllII[lIllIIIll[36]]);
        "".length();
        chatRaw.put(ChatColor.GRAY, lIIllllII[lIllIIIll[37]]);
        "".length();
        chatRaw.put(ChatColor.DARK_GRAY, lIIllllII[lIllIIIll[38]]);
        "".length();
        chatRaw.put(ChatColor.BLUE, lIIllllII[lIllIIIll[39]]);
        "".length();
        chatRaw.put(ChatColor.GREEN, lIIllllII[lIllIIIll[40]]);
        "".length();
        chatRaw.put(ChatColor.AQUA, lIIllllII[lIllIIIll[41]]);
        "".length();
        chatRaw.put(ChatColor.RED, lIIllllII[lIllIIIll[42]]);
        "".length();
        chatRaw.put(ChatColor.LIGHT_PURPLE, lIIllllII[lIllIIIll[43]]);
        "".length();
        chatRaw.put(ChatColor.YELLOW, lIIllllII[lIllIIIll[44]]);
        "".length();
        chatRaw.put(ChatColor.WHITE, lIIllllII[lIllIIIll[45]]);
        dyeHexMap = Maps.newHashMap();
        "".length();
        dyeHexMap.put(DyeColor.BLACK, lIIllllII[lIllIIIll[46]]);
        "".length();
        dyeHexMap.put(DyeColor.BLUE, lIIllllII[lIllIIIll[47]]);
        "".length();
        dyeHexMap.put(DyeColor.BROWN, lIIllllII[lIllIIIll[48]]);
        "".length();
        dyeHexMap.put(DyeColor.CYAN, lIIllllII[lIllIIIll[49]]);
        "".length();
        dyeHexMap.put(DyeColor.GRAY, lIIllllII[lIllIIIll[50]]);
        "".length();
        dyeHexMap.put(DyeColor.GREEN, lIIllllII[lIllIIIll[51]]);
        "".length();
        dyeHexMap.put(DyeColor.LIGHT_BLUE, lIIllllII[lIllIIIll[52]]);
        "".length();
        dyeHexMap.put(DyeColor.LIME, lIIllllII[lIllIIIll[53]]);
        "".length();
        dyeHexMap.put(DyeColor.MAGENTA, lIIllllII[lIllIIIll[54]]);
        "".length();
        dyeHexMap.put(DyeColor.ORANGE, lIIllllII[lIllIIIll[55]]);
        "".length();
        dyeHexMap.put(DyeColor.PINK, lIIllllII[lIllIIIll[56]]);
        "".length();
        dyeHexMap.put(DyeColor.PURPLE, lIIllllII[lIllIIIll[57]]);
        "".length();
        dyeHexMap.put(DyeColor.RED, lIIllllII[lIllIIIll[58]]);
        "".length();
        dyeHexMap.put(DyeColor.SILVER, lIIllllII[lIllIIIll[59]]);
        "".length();
        dyeHexMap.put(DyeColor.WHITE, lIIllllII[lIllIIIll[60]]);
        "".length();
        dyeHexMap.put(DyeColor.YELLOW, lIIllllII[lIllIIIll[61]]);
    }

    private static void lIIlllIllIl() {
        lIllIIIll = new int[63];
        ColorConverter.lIllIIIll[0] = (96 ^ 0) & ~(30 ^ 126);
        ColorConverter.lIllIIIll[1] = " ".length();
        ColorConverter.lIllIIIll[2] = "  ".length();
        ColorConverter.lIllIIIll[3] = "   ".length();
        ColorConverter.lIllIIIll[4] = 105 + 87 - 177 + 115 ^ 109 + 69 - 44 + 0;
        ColorConverter.lIllIIIll[5] = 184 ^ 189;
        ColorConverter.lIllIIIll[6] = 16 ^ 124 ^ (5 ^ 111);
        ColorConverter.lIllIIIll[7] = 185 ^ 169;
        ColorConverter.lIllIIIll[8] = 88 + 104 - 191 + 150 ^ 117 + 136 - 152 + 58;
        ColorConverter.lIllIIIll[9] = -1 & 16777215;
        ColorConverter.lIllIIIll[10] = 90 ^ 93;
        ColorConverter.lIllIIIll[11] = 90 ^ 83;
        ColorConverter.lIllIIIll[12] = 205 ^ 199;
        ColorConverter.lIllIIIll[13] = 141 + 133 - 268 + 167 ^ 39 + 7 - -4 + 116;
        ColorConverter.lIllIIIll[14] = 80 ^ 52 ^ (102 ^ 14);
        ColorConverter.lIllIIIll[15] = 60 ^ 13 ^ (129 ^ 189);
        ColorConverter.lIllIIIll[16] = "   ".length() ^ (9 ^ 4);
        ColorConverter.lIllIIIll[17] = 156 + 173 - 188 + 33 ^ 27 + 68 - 57 + 123;
        ColorConverter.lIllIIIll[18] = 17 ^ 60 ^ (98 ^ 94);
        ColorConverter.lIllIIIll[19] = 111 ^ 125;
        ColorConverter.lIllIIIll[20] = 123 ^ 104;
        ColorConverter.lIllIIIll[21] = 89 ^ 77;
        ColorConverter.lIllIIIll[22] = 28 ^ 37 ^ (6 ^ 42);
        ColorConverter.lIllIIIll[23] = 155 ^ 199 ^ (57 ^ 115);
        ColorConverter.lIllIIIll[24] = 107 ^ 73 ^ (15 ^ 58);
        ColorConverter.lIllIIIll[25] = 140 ^ 148;
        ColorConverter.lIllIIIll[26] = 9 ^ 16;
        ColorConverter.lIllIIIll[27] = 7 ^ 29;
        ColorConverter.lIllIIIll[28] = 12 ^ 1 ^ (159 ^ 137);
        ColorConverter.lIllIIIll[29] = 155 ^ 135;
        ColorConverter.lIllIIIll[30] = 134 ^ 155;
        ColorConverter.lIllIIIll[31] = 104 + 43 - -22 + 12 ^ 14 + 67 - 60 + 150;
        ColorConverter.lIllIIIll[32] = 47 ^ 48;
        ColorConverter.lIllIIIll[33] = 102 ^ 70;
        ColorConverter.lIllIIIll[34] = 145 + 83 - 81 + 9 ^ 150 + 88 - 197 + 148;
        ColorConverter.lIllIIIll[35] = 167 ^ 133;
        ColorConverter.lIllIIIll[36] = 132 ^ 195 ^ (32 ^ 68);
        ColorConverter.lIllIIIll[37] = 117 ^ 81;
        ColorConverter.lIllIIIll[38] = 23 + 25 - 7 + 94 ^ 118 + 90 - 61 + 15;
        ColorConverter.lIllIIIll[39] = 55 ^ 17;
        ColorConverter.lIllIIIll[40] = 27 ^ 119 ^ (21 ^ 94);
        ColorConverter.lIllIIIll[41] = 34 ^ 10;
        ColorConverter.lIllIIIll[42] = 25 ^ 66 ^ (116 ^ 6);
        ColorConverter.lIllIIIll[43] = 23 + 96 - 7 + 75 ^ 120 + 43 - 102 + 84;
        ColorConverter.lIllIIIll[44] = 134 ^ 173;
        ColorConverter.lIllIIIll[45] = 88 ^ 71 ^ (98 ^ 81);
        ColorConverter.lIllIIIll[46] = 65 ^ 94 ^ (140 ^ 190);
        ColorConverter.lIllIIIll[47] = 51 + 64 - 42 + 62 ^ 38 + 42 - 8 + 97;
        ColorConverter.lIllIIIll[48] = 92 ^ 120 ^ (191 ^ 180);
        ColorConverter.lIllIIIll[49] = 15 ^ 63;
        ColorConverter.lIllIIIll[50] = 77 ^ 124;
        ColorConverter.lIllIIIll[51] = 78 ^ 97 ^ (160 ^ 189);
        ColorConverter.lIllIIIll[52] = 178 ^ 129;
        ColorConverter.lIllIIIll[53] = 17 ^ 37;
        ColorConverter.lIllIIIll[54] = 65 ^ 116;
        ColorConverter.lIllIIIll[55] = 175 ^ 137 ^ (101 ^ 117);
        ColorConverter.lIllIIIll[56] = 69 ^ 114;
        ColorConverter.lIllIIIll[57] = 142 ^ 182;
        ColorConverter.lIllIIIll[58] = 53 + 98 - 122 + 115 ^ 166 + 77 - 140 + 66;
        ColorConverter.lIllIIIll[59] = 151 ^ 156 ^ (149 ^ 164);
        ColorConverter.lIllIIIll[60] = 110 ^ 31 ^ (142 ^ 196);
        ColorConverter.lIllIIIll[61] = 37 + 14 - -79 + 10 ^ 161 + 26 - 58 + 47;
        ColorConverter.lIllIIIll[62] = 102 ^ 91;
    }

    public static String dyeToHex(DyeColor lIllIIIIIIIllI) {
        if (ColorConverter.lIIlllIllll((int)dyeHexMap.containsKey((Object)lIllIIIIIIIllI))) {
            return dyeHexMap.get((Object)lIllIIIIIIIllI);
        }
        return lIIllllII[lIllIIIll[2]];
    }

    public static String generateColorTable() {
        StringBuilder lIlIllIllIlIIl = new StringBuilder();
        "".length();
        lIlIllIllIlIIl.append(lIIllllII[lIllIIIll[10]]);
        Iterator<Map.Entry<ChatColor, String>> lIlIllIllIIllI = chatHexMap.entrySet().iterator();
        while (ColorConverter.lIIlllIllll((int)lIlIllIllIIllI.hasNext())) {
            Map.Entry<ChatColor, String> lIlIllIllIllII = lIlIllIllIIllI.next();
            Object[] arrobject = new Object[lIllIIIll[2]];
            arrobject[ColorConverter.lIllIIIll[0]] = lIlIllIllIllII.getKey().name();
            arrobject[ColorConverter.lIllIIIll[1]] = lIlIllIllIllII.getValue();
            "".length();
            lIlIllIllIlIIl.append(String.format(lIIllllII[lIllIIIll[8]], arrobject));
            "".length();
            if ("  ".length() > " ".length()) continue;
            return null;
        }
        "".length();
        lIlIllIllIlIIl.append(lIIllllII[lIllIIIll[11]]);
        "".length();
        lIlIllIllIlIIl.append(lIIllllII[lIllIIIll[12]]);
        lIlIllIllIIllI = dyeHexMap.entrySet().iterator();
        while (ColorConverter.lIIlllIllll((int)lIlIllIllIIllI.hasNext())) {
            Map.Entry<ChatColor, String> lIlIllIllIlIlI = lIlIllIllIIllI.next();
            Object[] arrobject = new Object[lIllIIIll[2]];
            arrobject[ColorConverter.lIllIIIll[0]] = ((DyeColor)lIlIllIllIlIlI.getKey()).name();
            arrobject[ColorConverter.lIllIIIll[1]] = lIlIllIllIlIlI.getValue();
            "".length();
            lIlIllIllIlIIl.append(String.format(lIIllllII[lIllIIIll[13]], arrobject));
            "".length();
            if (-(93 ^ 70 ^ (63 ^ 32)) <= 0) continue;
            return null;
        }
        "".length();
        lIlIllIllIlIIl.append(lIIllllII[lIllIIIll[14]]);
        return String.valueOf(lIlIllIllIlIIl);
    }

    private static boolean lIIllllIIlI(int n, int n2) {
        return n < n2;
    }

    public static String chatToHex(ChatColor lIllIIIIIIllll) {
        if (ColorConverter.lIIlllIllll((int)chatHexMap.containsKey((Object)lIllIIIIIIllll))) {
            return chatHexMap.get((Object)lIllIIIIIIllll);
        }
        return lIIllllII[lIllIIIll[1]];
    }

    public ColorConverter() {
        ColorConverter lIllIIIIIlIlll;
    }

    public static Color dyeToColor(DyeColor lIlIllIllIIIII) {
        return ColorConverter.hexToColor(ColorConverter.chatToHex(ColorConverter.dyeToChat(lIlIllIllIIIII)));
    }

    private static void lIIlIlllIlI() {
        lIIllllII = new String[lIllIIIll[62]];
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[0]] = ColorConverter.lIIIlllIIIl("QyE=", "eBicF");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[1]] = ColorConverter.lIIIlllIIIl("YXZGQQ==", "BFvqD");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[2]] = ColorConverter.lIIIlllIIIl("TGh4VQ==", "oXHeD");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[3]] = ColorConverter.lIIIlllIIIl("bQ==", "NGhUO");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[4]] = ColorConverter.lIIIlllIIIl("Cw==", "spXax");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[5]] = ColorConverter.lIIIlllIIlI("zMkGc/7sSo0=", "faOKU");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[6]] = ColorConverter.lIIIlllIIlI("jmAs2Ju2p44=", "HNPCa");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[10]] = ColorConverter.lIIIlllIIlI("vbDg7KsHo0ZejnjRsluqPtrRUl2WkUYvr4T22EFXLNLqdFVxZbXCt4T68oytlCZkO/RO8IpkJbs=", "VnklZ");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[8]] = ColorConverter.lIIIllllIIl("nI3Q2auVDXosDbUEOS58i2VcbYEkAqqICPMk5wwUET34tO9kJ+t+f1sGr/M/qOBd59IGHKe5Bp8Vbkg3EPlEXH+xaYWh/ibnRbUhUCMNYQivvQi+hsSfsw==", "VNtrd");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[11]] = ColorConverter.lIIIlllIIlI("R834gvVjMaMeOCY/eJtTyg==", "bfXUs");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[12]] = ColorConverter.lIIIlllIIlI("UM2VwJzyAGZ6PLVCCvKVdErBRDcfmD6DBCxRQ+tZZBDzVMMmPiFywOK7wLY74gIlmqEGhMTYJJg=", "xCVPE");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[13]] = ColorConverter.lIIIlllIIlI("l56Qu63UHWOPGvYh0PqKLfl0JEM1b3CTVnMb1gSdWZc0maCAm+0hQb6Q6NrE8IAtZoVnPDrhILGS9pQfdVVxpMieE6tVogect1/OUeU+Ocwz8Urxh8xvdA==", "FMmmn");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[14]] = ColorConverter.lIIIllllIIl("nwHIAbuuzPEQZ1Yke0VW8g==", "zLRdL");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[15]] = ColorConverter.lIIIllllIIl("P2SEnFr+LHU=", "PmnYk");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[16]] = ColorConverter.lIIIlllIIlI("ayKBCJjS/FI=", "tjFzB");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[17]] = ColorConverter.lIIIlllIIlI("fbmuaFHZduc=", "EIAuH");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[7]] = ColorConverter.lIIIlllIIlI("oLdGiGYlzHg=", "ivirP");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[18]] = ColorConverter.lIIIlllIIlI("rXM2uoBFbJ8=", "SiUoz");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[19]] = ColorConverter.lIIIllllIIl("KcYnWxXXch8=", "FVyrH");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[20]] = ColorConverter.lIIIllllIIl("hvKczTvu3kM=", "punld");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[21]] = ColorConverter.lIIIlllIIIl("UWhLTw==", "rQrvB");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[22]] = ColorConverter.lIIIlllIIIl("Z1lcVA==", "Dliaq");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[23]] = ColorConverter.lIIIlllIIlI("lQ0WHIxJdSw=", "qhyJP");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[24]] = ColorConverter.lIIIlllIIIl("eVQ7dA==", "ZaXAT");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[25]] = ColorConverter.lIIIlllIIlI("C2rr++tQ1DY=", "hPZGJ");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[26]] = ColorConverter.lIIIlllIIIl("YQpyeA==", "BlGMC");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[27]] = ColorConverter.lIIIlllIIIl("ZjxTIg==", "EZfDs");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[28]] = ColorConverter.lIIIlllIIlI("+tjCLqkDknc=", "lqOLA");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[29]] = ColorConverter.lIIIlllIIIl("QDMlGw==", "cRDzU");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[30]] = ColorConverter.lIIIlllIIIl("TXg=", "kHpWB");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[31]] = ColorConverter.lIIIlllIIIl("U0E=", "upEPU");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[32]] = ColorConverter.lIIIllllIIl("6uQT2u0GNX8=", "bpWcV");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[33]] = ColorConverter.lIIIlllIIIl("UnY=", "tEbAl");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[34]] = ColorConverter.lIIIlllIIIl("YX8=", "GKVga");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[35]] = ColorConverter.lIIIlllIIlI("ra+K9cg4xJY=", "gsxbB");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[36]] = ColorConverter.lIIIlllIIlI("UijCfxwV4nc=", "nYtIg");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[37]] = ColorConverter.lIIIllllIIl("VTIT0RxsLJs=", "QXdtf");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[38]] = ColorConverter.lIIIllllIIl("tryqbr7OK2Q=", "RHTGH");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[39]] = ColorConverter.lIIIllllIIl("oew5EZnSaXc=", "BvksT");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[40]] = ColorConverter.lIIIlllIIIl("bAg=", "JiXmX");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[41]] = ColorConverter.lIIIllllIIl("+EJjHNmyHDI=", "Gttjm");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[42]] = ColorConverter.lIIIlllIIIl("ayU=", "MFZKA");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[43]] = ColorConverter.lIIIlllIIIl("aSw=", "OHvvI");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[44]] = ColorConverter.lIIIlllIIIl("Zw8=", "AjTyw");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[45]] = ColorConverter.lIIIllllIIl("dFJxKio8psY=", "FIpFe");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[46]] = ColorConverter.lIIIlllIIlI("ls1HxOHTi9o=", "gkFXA");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[47]] = ColorConverter.lIIIlllIIlI("VXlmHkbEQag=", "xADbg");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[48]] = ColorConverter.lIIIllllIIl("zETjMXsJDo4=", "NIOPh");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[49]] = ColorConverter.lIIIlllIIIl("SnNDZ0ZQcA==", "iAuPw");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[50]] = ColorConverter.lIIIllllIIl("EULqhS3xx04=", "WgYAl");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[51]] = ColorConverter.lIIIlllIIlI("1J2weqM+Rlc=", "AXtyn");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[52]] = ColorConverter.lIIIlllIIIl("ZkRXSEIhQA==", "Erdpu");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[53]] = ColorConverter.lIIIlllIIIl("T1djIRFeAQ==", "ldZCp");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[54]] = ColorConverter.lIIIlllIIIl("egQRWU06Xw==", "Yftmt");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[55]] = ColorConverter.lIIIllllIIl("2sEJdwolHUY=", "BwzNm");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[56]] = ColorConverter.lIIIlllIIlI("wS1wu55NFZ0=", "ZPhrp");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[57]] = ColorConverter.lIIIllllIIl("SmqeOH6jTX4=", "WSMRC");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[58]] = ColorConverter.lIIIlllIIlI("Sh71l/NMb1g=", "uSAQr");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[59]] = ColorConverter.lIIIlllIIIl("WxF4FlQZRw==", "xpHwc");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[60]] = ColorConverter.lIIIlllIIIl("ZSNeFU4ndg==", "FBjtz");
        ColorConverter.lIIllllII[ColorConverter.lIllIIIll[61]] = ColorConverter.lIIIlllIIIl("djRrFW9kNA==", "UWYwZ");
    }

    public static String chatToRaw(ChatColor lIllIIIIIlIIlI) {
        if (ColorConverter.lIIlllIllll((int)chatRaw.containsKey((Object)lIllIIIIIlIIlI))) {
            return chatRaw.get((Object)lIllIIIIIlIIlI);
        }
        return lIIllllII[lIllIIIll[0]];
    }

    private static String lIIIlllIIlI(String lIlIlIllllIIIl, String lIlIlIllllIIlI) {
        try {
            SecretKeySpec lIlIlIllllIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIlIllllIIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIlIllllIlIl = Cipher.getInstance("Blowfish");
            lIlIlIllllIlIl.init(lIllIIIll[2], lIlIlIllllIllI);
            return new String(lIlIlIllllIlIl.doFinal(Base64.getDecoder().decode(lIlIlIllllIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIlIllllIlII) {
            lIlIlIllllIlII.printStackTrace();
            return null;
        }
    }

    public static ChatColor dyeToChat(DyeColor lIllIIIIIlIlII) {
        if (ColorConverter.lIIlllIllll((int)dyeChatMap.containsKey((Object)lIllIIIIIlIlII))) {
            return dyeChatMap.get((Object)lIllIIIIIlIlII);
        }
        return ChatColor.MAGIC;
    }

    public static Color rgbToColor(String lIlIlllIlIIlII) {
        String[] lIlIlllIlIIIll = lIlIlllIlIIlII.split(lIIllllII[lIllIIIll[6]]);
        if (ColorConverter.lIIllllIIlI(lIlIlllIlIIIll.length, lIllIIIll[3])) {
            return null;
        }
        int lIlIlllIlIIIIl = lIllIIIll[0];
        int lIlIlllIIlllll = lIllIIIll[0];
        while (ColorConverter.lIIllllIIlI(lIlIlllIIlllll, lIllIIIll[3])) {
            lIlIlllIlIIIIl |= Integer.parseInt(lIlIlllIlIIIll[lIlIlllIIlllll]) << lIlIlllIIlllll * lIllIIIll[8];
            ++lIlIlllIIlllll;
            "".length();
            if (null == null) continue;
            return null;
        }
        return Color.fromBGR((int)(lIlIlllIlIIIIl & lIllIIIll[9]));
    }

    private static boolean lIIlllIllll(int n) {
        return n != 0;
    }

    private static String lIIIllllIIl(String lIlIllIIIlIllI, String lIlIllIIIlIlIl) {
        try {
            SecretKeySpec lIlIllIIIllIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIllIIIlIlIl.getBytes(StandardCharsets.UTF_8)), lIllIIIll[8]), "DES");
            Cipher lIlIllIIIllIlI = Cipher.getInstance("DES");
            lIlIllIIIllIlI.init(lIllIIIll[2], lIlIllIIIllIll);
            return new String(lIlIllIIIllIlI.doFinal(Base64.getDecoder().decode(lIlIllIIIlIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIllIIIllIIl) {
            lIlIllIIIllIIl.printStackTrace();
            return null;
        }
    }

    public static Color hexToColor(String lIlIllllIIIIIl) {
        if (ColorConverter.lIIlllIllll((int)lIlIllllIIIIIl.startsWith(lIIllllII[lIllIIIll[3]]))) {
            lIlIllllIIIIIl = lIlIllllIIIIIl.substring(lIllIIIll[1]);
        }
        if (ColorConverter.lIIlllIllll((int)lIlIllllIIIIIl.contains(lIIllllII[lIllIIIll[4]]))) {
            lIlIllllIIIIIl = lIlIllllIIIIIl.substring(lIlIllllIIIIIl.indexOf(lIIllllII[lIllIIIll[5]]));
        }
        if (ColorConverter.lIIllllIIIl(lIlIllllIIIIIl.length(), lIllIIIll[6]) && ColorConverter.lIIllllIIIl(lIlIllllIIIIIl.length(), lIllIIIll[3])) {
            return null;
        }
        int lIlIllllIIIlII = lIlIllllIIIIIl.length() / lIllIIIll[3];
        int lIlIllllIIIIll = lIllIIIll[1] << (lIllIIIll[2] - lIlIllllIIIlII) * lIllIIIll[4];
        int lIlIllllIIIIlI = lIllIIIll[0];
        int lIlIllllIIIlll = lIllIIIll[0];
        int lIlIllllIIIllI = lIllIIIll[0];
        while (ColorConverter.lIIllllIIlI(lIlIllllIIIllI, lIlIllllIIIIIl.length())) {
            lIlIllllIIIIlI |= lIlIllllIIIIll * Integer.parseInt(lIlIllllIIIIIl.substring(lIlIllllIIIllI, lIlIllllIIIllI + lIlIllllIIIlII), lIllIIIll[7]) << lIlIllllIIIlll * lIllIIIll[8];
            ++lIlIllllIIIlll;
            lIlIllllIIIllI += lIlIllllIIIlII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return Color.fromBGR((int)(lIlIllllIIIIlI & lIllIIIll[9]));
    }

    private static boolean lIIllllIIIl(int n, int n2) {
        return n != n2;
    }
}

