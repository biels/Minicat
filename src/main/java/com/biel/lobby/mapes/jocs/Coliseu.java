/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Event
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocExtern;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.event.Event;

public class Coliseu
extends JocExtern {
    private static final /* synthetic */ String[] llIlllI;
    private static final /* synthetic */ int[] llIllll;

    static {
        Coliseu.lIllllIll();
        Coliseu.lIllllIlI();
    }

    @Override
    protected synchronized void gameEvent(Event llIIIlllIlIlIlI) {
    }

    private static String lIllllIIl(String llIIIlllIlIIIlI, String llIIIlllIIlllll) {
        try {
            SecretKeySpec llIIIlllIlIIlIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIlllIIlllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIIlllIlIIlII = Cipher.getInstance("Blowfish");
            llIIIlllIlIIlII.init(llIllll[2], llIIIlllIlIIlIl);
            return new String(llIIIlllIlIIlII.doFinal(Base64.getDecoder().decode(llIIIlllIlIIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIlllIlIIIll) {
            llIIIlllIlIIIll.printStackTrace();
            return null;
        }
    }

    private static void lIllllIlI() {
        llIlllI = new String[llIllll[1]];
        Coliseu.llIlllI[Coliseu.llIllll[0]] = Coliseu.lIllllIIl("TyTA5Dp9h5s=", "cxjMh");
    }

    private static void lIllllIll() {
        llIllll = new int[3];
        Coliseu.llIllll[0] = (119 ^ 39) & ~(238 ^ 190);
        Coliseu.llIllll[1] = " ".length();
        Coliseu.llIllll[2] = "  ".length();
    }

    @Override
    public String getGameName() {
        return llIlllI[llIllll[0]];
    }

    public Coliseu() {
        Coliseu llIIIlllIlIlllI;
    }
}

