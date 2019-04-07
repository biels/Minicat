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
import java.util.Base64;
import org.bukkit.event.Event;

public class TheTowers
extends JocExtern {
    private static final /* synthetic */ String[] lIIIIlIl;
    private static final /* synthetic */ int[] lIIIIlll;

    @Override
    protected synchronized void gameEvent(Event lIlllIIIIIllIlI) {
    }

    private static void llllIIIlI() {
        lIIIIlll = new int[2];
        TheTowers.lIIIIlll[0] = (124 ^ 48) & ~(119 ^ 59);
        TheTowers.lIIIIlll[1] = " ".length();
    }

    private static void lllIllIlI() {
        lIIIIlIl = new String[lIIIIlll[1]];
        TheTowers.lIIIIlIl[TheTowers.lIIIIlll[0]] = TheTowers.lllIlIlll("DiMLQhc1PAsQMA==", "ZKnbC");
    }

    private static boolean llllIIIll(int n, int n2) {
        return n < n2;
    }

    static {
        TheTowers.llllIIIlI();
        TheTowers.lllIllIlI();
    }

    private static String lllIlIlll(String lIlllIIIIIIIIll, String lIllIllllllllII) {
        lIlllIIIIIIIIll = new String(Base64.getDecoder().decode(lIlllIIIIIIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlllIIIIIIIIIl = new StringBuilder();
        char[] lIlllIIIIIIIIII = lIllIllllllllII.toCharArray();
        int lIllIllllllllll = lIIIIlll[0];
        char[] lIllIllllllIlll = lIlllIIIIIIIIll.toCharArray();
        int lIllIllllllIllI = lIllIllllllIlll.length;
        int lIllIllllllIlIl = lIIIIlll[0];
        while (TheTowers.llllIIIll(lIllIllllllIlIl, lIllIllllllIllI)) {
            char lIlllIIIIIIIlII = lIllIllllllIlll[lIllIllllllIlIl];
            "".length();
            lIlllIIIIIIIIIl.append((char)(lIlllIIIIIIIlII ^ lIlllIIIIIIIIII[lIllIllllllllll % lIlllIIIIIIIIII.length]));
            ++lIllIllllllllll;
            ++lIllIllllllIlIl;
            "".length();
            if (-" ".length() <= "   ".length()) continue;
            return null;
        }
        return String.valueOf(lIlllIIIIIIIIIl);
    }

    public TheTowers() {
        TheTowers lIlllIIIIIllllI;
    }

    @Override
    public String getGameName() {
        return lIIIIlIl[lIIIIlll[0]];
    }
}

