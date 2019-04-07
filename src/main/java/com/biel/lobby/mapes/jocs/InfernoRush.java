/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocLastStanding;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InfernoRush
extends JocLastStanding {
    private static final /* synthetic */ String[] lllIIlll;
    private static final /* synthetic */ int[] lllllIlI;

    @Override
    protected void setCustomGameRules() {
    }

    @Override
    protected void teletransportarTothom() {
    }

    private static void lllIlIllIl() {
        lllllIlI = new int[2];
        InfernoRush.lllllIlI[0] = (11 ^ 75) & ~(109 ^ 45);
        InfernoRush.lllllIlI[1] = " ".length();
    }

    @Override
    public String getGameName() {
        return lllIIlll[lllllIlI[0]];
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llllllIllllllI) {
        return null;
    }

    private static void llIlllIllI() {
        lllIIlll = new String[lllllIlI[1]];
        InfernoRush.lllIIlll[InfernoRush.lllllIlI[0]] = InfernoRush.llIlllIlIl("IjcoCTEFNhwZMAM=", "kYNlC");
    }

    private static String llIlllIlIl(String llllllIlllIIIl, String llllllIlllIIII) {
        llllllIlllIIIl = new String(Base64.getDecoder().decode(llllllIlllIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llllllIllIllll = new StringBuilder();
        char[] llllllIllIlllI = llllllIlllIIII.toCharArray();
        int llllllIllIllIl = lllllIlI[0];
        char[] llllllIllIIlll = llllllIlllIIIl.toCharArray();
        int llllllIllIIllI = llllllIllIIlll.length;
        int llllllIllIIlIl = lllllIlI[0];
        while (InfernoRush.lllIlIlllI(llllllIllIIlIl, llllllIllIIllI)) {
            char llllllIlllIIlI = llllllIllIIlll[llllllIllIIlIl];
            "".length();
            llllllIllIllll.append((char)(llllllIlllIIlI ^ llllllIllIlllI[llllllIllIllIl % llllllIllIlllI.length]));
            ++llllllIllIllIl;
            ++llllllIllIIlIl;
            "".length();
            if (" ".length() < "   ".length()) continue;
            return null;
        }
        return String.valueOf(llllllIllIllll);
    }

    static {
        InfernoRush.lllIlIllIl();
        InfernoRush.llIlllIllI();
    }

    public InfernoRush() {
        InfernoRush lllllllIIIIIlI;
    }

    private static boolean lllIlIlllI(int n, int n2) {
        return n < n2;
    }
}

