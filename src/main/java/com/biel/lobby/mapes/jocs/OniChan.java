/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.Joc;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OniChan
extends Joc {
    private static final /* synthetic */ int[] lIIIIlIll;
    private static final /* synthetic */ String[] lIIIIlIlI;

    @Override
    protected void customJocFinalitzat() {
    }

    private static void llllIlIllI() {
        lIIIIlIll = new int[2];
        OniChan.lIIIIlIll[0] = (1 ^ 54) & ~(19 ^ 36);
        OniChan.lIIIIlIll[1] = " ".length();
    }

    @Override
    protected void customJocIniciat() {
    }

    @Override
    protected void setCustomGameRules() {
    }

    public OniChan() {
        OniChan llllIlIlIIIIlI;
    }

    @Override
    protected void teletransportarTothom() {
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llllIlIIlllllI) {
        return null;
    }

    private static String llllIlIlII(String llllIlIIlIlIlI, String llllIlIIlIlllI) {
        llllIlIIlIlIlI = new String(Base64.getDecoder().decode(llllIlIIlIlIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llllIlIIlIllIl = new StringBuilder();
        char[] llllIlIIlIllII = llllIlIIlIlllI.toCharArray();
        int llllIlIIlIlIll = lIIIIlIll[0];
        char[] llllIlIIlIIlIl = llllIlIIlIlIlI.toCharArray();
        int llllIlIIlIIlII = llllIlIIlIIlIl.length;
        int llllIlIIlIIIll = lIIIIlIll[0];
        while (OniChan.llllIlIlll(llllIlIIlIIIll, llllIlIIlIIlII)) {
            char llllIlIIllIIII = llllIlIIlIIlIl[llllIlIIlIIIll];
            "".length();
            llllIlIIlIllIl.append((char)(llllIlIIllIIII ^ llllIlIIlIllII[llllIlIIlIlIll % llllIlIIlIllII.length]));
            ++llllIlIIlIlIll;
            ++llllIlIIlIIIll;
            "".length();
            if (" ".length() != 0) continue;
            return null;
        }
        return String.valueOf(llllIlIIlIllIl);
    }

    static {
        OniChan.llllIlIllI();
        OniChan.llllIlIlIl();
    }

    private static boolean llllIlIlll(int n, int n2) {
        return n < n2;
    }

    @Override
    public String getGameName() {
        return lIIIIlIlI[lIIIIlIll[0]];
    }

    private static void llllIlIlIl() {
        lIIIIlIlI = new String[lIIIIlIll[1]];
        OniChan.lIIIIlIlI[OniChan.lIIIIlIll[0]] = OniChan.llllIlIlII("OTs9FRkXOw==", "vUTVq");
    }
}

