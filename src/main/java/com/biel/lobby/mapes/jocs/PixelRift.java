/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.DyeColor
 *  org.bukkit.World
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamDeathMatch;
import com.biel.lobby.mapes.JocTeamScoreRace;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.DyeColor;
import org.bukkit.World;

public class PixelRift
extends JocTeamDeathMatch {
    private static final /* synthetic */ int[] lllIlIlI;
    private static final /* synthetic */ String[] lllIIIll;

    @Override
    protected void setCustomGameRules() {
    }

    private static void llIllllIlI() {
        lllIlIlI = new int[7];
        PixelRift.lllIlIlI[0] = 21 ^ 10 ^ (42 ^ 58);
        PixelRift.lllIlIlI[1] = (119 + 140 - 114 + 110 ^ 22 + 44 - -85 + 19) & (137 ^ 156 ^ (218 ^ 154) ^ -" ".length());
        PixelRift.lllIlIlI[2] = " ".length();
        PixelRift.lllIlIlI[3] = "  ".length();
        PixelRift.lllIlIlI[4] = "   ".length();
        PixelRift.lllIlIlI[5] = 92 ^ 17 ^ (225 ^ 168);
        PixelRift.lllIlIlI[6] = 30 + 84 - -19 + 3 ^ 3 + 109 - 100 + 129;
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        PixelRift lIIIIlIIIIllIlI;
        ArrayList<JocEquips.Equip> lIIIIlIIIIllIll = new ArrayList<JocEquips.Equip>();
        "".length();
        lIIIIlIIIIllIll.add(new JocTeamScoreRace.EquipScoreRace(lIIIIlIIIIllIlI, DyeColor.RED, lllIIIll[lllIlIlI[1]]));
        "".length();
        lIIIIlIIIIllIll.add(new JocTeamScoreRace.EquipScoreRace(lIIIIlIIIIllIlI, DyeColor.BLUE, lllIIIll[lllIlIlI[2]]));
        "".length();
        lIIIIlIIIIllIll.add(new JocTeamScoreRace.EquipScoreRace(lIIIIlIIIIllIlI, DyeColor.GREEN, lllIIIll[lllIlIlI[3]]));
        "".length();
        lIIIIlIIIIllIll.add(new JocTeamScoreRace.EquipScoreRace(lIIIIlIIIIllIlI, DyeColor.YELLOW, lllIIIll[lllIlIlI[4]]));
        return lIIIIlIIIIllIll;
    }

    public PixelRift() {
        PixelRift lIIIIlIIIlIIIII;
    }

    private static String llIllIIllI(String lIIIIIlllllIlll, String lIIIIIllllllIll) {
        lIIIIIlllllIlll = new String(Base64.getDecoder().decode(lIIIIIlllllIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIIIIllllllIlI = new StringBuilder();
        char[] lIIIIIllllllIIl = lIIIIIllllllIll.toCharArray();
        int lIIIIIllllllIII = lllIlIlI[1];
        char[] lIIIIIlllllIIlI = lIIIIIlllllIlll.toCharArray();
        int lIIIIIlllllIIIl = lIIIIIlllllIIlI.length;
        int lIIIIIlllllIIII = lllIlIlI[1];
        while (PixelRift.llIlllllII(lIIIIIlllllIIII, lIIIIIlllllIIIl)) {
            char lIIIIIlllllllIl = lIIIIIlllllIIlI[lIIIIIlllllIIII];
            "".length();
            lIIIIIllllllIlI.append((char)(lIIIIIlllllllIl ^ lIIIIIllllllIIl[lIIIIIllllllIII % lIIIIIllllllIIl.length]));
            ++lIIIIIllllllIII;
            ++lIIIIIlllllIIII;
            "".length();
            if ("   ".length() > -" ".length()) continue;
            return null;
        }
        return String.valueOf(lIIIIIllllllIlI);
    }

    @Override
    protected void customJocIniciat() {
        PixelRift lIIIIlIIIIlIlll;
        super.customJocIniciat();
        lIIIIlIIIIlIlll.world.setTime(4500L);
    }

    private static String llIllIlIII(String lIIIIlIIIIIlIlI, String lIIIIlIIIIIlIIl) {
        try {
            SecretKeySpec lIIIIlIIIIIllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIIlIIIIIlIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIIIlIIIIIlllI = Cipher.getInstance("Blowfish");
            lIIIIlIIIIIlllI.init(lllIlIlI[3], lIIIIlIIIIIllll);
            return new String(lIIIIlIIIIIlllI.doFinal(Base64.getDecoder().decode(lIIIIlIIIIIlIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIIlIIIIIllIl) {
            lIIIIlIIIIIllIl.printStackTrace();
            return null;
        }
    }

    static {
        PixelRift.llIllllIlI();
        PixelRift.llIllIlIIl();
    }

    private static boolean llIlllllII(int n, int n2) {
        return n < n2;
    }

    private static void llIllIlIIl() {
        lllIIIll = new String[lllIlIlI[6]];
        PixelRift.lllIIIll[PixelRift.lllIlIlI[1]] = PixelRift.llIllIIllI("LgAABAE0CQ==", "Xerid");
        PixelRift.lllIIIll[PixelRift.lllIlIlI[2]] = PixelRift.llIllIIllI("CyozMg==", "iFRGt");
        PixelRift.lllIIIll[PixelRift.lllIlIlI[3]] = PixelRift.llIllIIllI("HigxEA==", "hMCtm");
        PixelRift.lllIIIll[PixelRift.lllIlIlI[4]] = PixelRift.llIllIIllI("PRgjCg==", "ZjLiL");
        PixelRift.lllIIIll[PixelRift.lllIlIlI[5]] = PixelRift.llIllIlIII("9TjJloup/kP8/00c89t2IA==", "saiOh");
    }

    @Override
    public String getGameName() {
        return lllIIIll[lllIlIlI[5]];
    }

    @Override
    protected int getFinishScore() {
        return lllIlIlI[0];
    }
}

