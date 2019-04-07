/*
 * Decompiled with CFR 0.139.
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocScoreRace;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TempleQuest
extends JocScoreRace {
    private static final /* synthetic */ String[] llIIIll;
    private static final /* synthetic */ int[] llIllIl;

    public TempleQuest() {
        TempleQuest llIIlIIIlIIlIll;
    }

    private static void lIlllIlll() {
        llIllIl = new int[3];
        TempleQuest.llIllIl[0] = (42 ^ 32) & ~(70 ^ 76);
        TempleQuest.llIllIl[1] = " ".length();
        TempleQuest.llIllIl[2] = "  ".length();
    }

    private static void lIllIIIIl() {
        llIIIll = new String[llIllIl[1]];
        TempleQuest.llIIIll[TempleQuest.llIllIl[0]] = TempleQuest.lIlIllllI("OBs45af36okkMCyxKQfcKg==", "NYOWG");
    }

    @Override
    public String getGameName() {
        return llIIIll[llIllIl[0]];
    }

    @Override
    protected int getFinishScore() {
        return llIllIl[0];
    }

    static {
        TempleQuest.lIlllIlll();
        TempleQuest.lIllIIIIl();
    }

    private static String lIlIllllI(String llIIlIIIlIIIIIl, String llIIlIIIlIIIIII) {
        try {
            SecretKeySpec llIIlIIIlIIIlII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIlIIIlIIIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIlIIIlIIIIll = Cipher.getInstance("Blowfish");
            llIIlIIIlIIIIll.init(llIllIl[2], llIIlIIIlIIIlII);
            return new String(llIIlIIIlIIIIll.doFinal(Base64.getDecoder().decode(llIIlIIIlIIIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIlIIIlIIIIlI) {
            llIIlIIIlIIIIlI.printStackTrace();
            return null;
        }
    }
}

