/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CorinthianHelmetSkill
extends InherentSkill {
    private static final /* synthetic */ int[] lIlllIII;
    private static final /* synthetic */ String[] lIlIlIlI;

    private static String lIlIIlIIll(String lIIllIlIlllllIl, String lIIllIlIllllIlI) {
        try {
            SecretKeySpec lIIllIllIIIIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIllIlIllllIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIllIlIlllllll = Cipher.getInstance("Blowfish");
            lIIllIlIlllllll.init(lIlllIII[2], lIIllIllIIIIIII);
            return new String(lIIllIlIlllllll.doFinal(Base64.getDecoder().decode(lIIllIlIlllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIllIlIllllllI) {
            lIIllIlIllllllI.printStackTrace();
            return null;
        }
    }

    private static boolean lIllIIlllI(int n) {
        return n != 0;
    }

    public CorinthianHelmetSkill(Player lIIllIllIIlllIl) {
        CorinthianHelmetSkill lIIllIllIIllllI;
        super(lIIllIllIIlllIl);
    }

    private static void lIllIIllIl() {
        lIlllIII = new int[5];
        CorinthianHelmetSkill.lIlllIII[0] = (82 ^ 22 ^ (3 ^ 79)) & (23 + 167 - 138 + 139 ^ 20 + 156 - 44 + 51 ^ -" ".length());
        CorinthianHelmetSkill.lIlllIII[1] = " ".length();
        CorinthianHelmetSkill.lIlllIII[2] = "  ".length();
        CorinthianHelmetSkill.lIlllIII[3] = "   ".length();
        CorinthianHelmetSkill.lIlllIII[4] = 45 + 69 - -18 + 22 ^ 116 + 44 - 50 + 48;
    }

    private static double getDmgMultiplier() {
        return 0.5;
    }

    private static String lIlIIlIIlI(String lIIllIlIllIllIl, String lIIllIlIllIIlll) {
        lIIllIlIllIllIl = new String(Base64.getDecoder().decode(lIIllIlIllIllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIllIlIllIlIll = new StringBuilder();
        char[] lIIllIlIllIlIlI = lIIllIlIllIIlll.toCharArray();
        int lIIllIlIllIlIIl = lIlllIII[0];
        char[] lIIllIlIllIIIll = lIIllIlIllIllIl.toCharArray();
        int lIIllIlIllIIIlI = lIIllIlIllIIIll.length;
        int lIIllIlIllIIIIl = lIlllIII[0];
        while (CorinthianHelmetSkill.lIllIIllll(lIIllIlIllIIIIl, lIIllIlIllIIIlI)) {
            char lIIllIlIllIlllI = lIIllIlIllIIIll[lIIllIlIllIIIIl];
            "".length();
            lIIllIlIllIlIll.append((char)(lIIllIlIllIlllI ^ lIIllIlIllIlIlI[lIIllIlIllIlIIl % lIIllIlIllIlIlI.length]));
            ++lIIllIlIllIlIIl;
            ++lIIllIlIllIIIIl;
            "".length();
            if ((177 ^ 193 ^ (68 ^ 48)) != 0) continue;
            return null;
        }
        return String.valueOf(lIIllIlIllIlIll);
    }

    private static boolean lIllIIllll(int n, int n2) {
        return n < n2;
    }

    @Override
    public String getName() {
        return lIlIlIlI[lIlllIII[0]];
    }

    @Override
    public String getDescription() {
        String lIIllIllIIlIlIl = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIlIlIlI[lIlllIII[1]]).append(CorinthianHelmetSkill.getDmgMultiplier() * 100.0).append((Object)ChatColor.WHITE));
        return String.valueOf(new StringBuilder().append(lIlIlIlI[lIlllIII[2]]).append(lIIllIllIIlIlIl).append(lIlIlIlI[lIlllIII[3]]));
    }

    static {
        CorinthianHelmetSkill.lIllIIllIl();
        CorinthianHelmetSkill.lIlIlIIlll();
    }

    @Override
    public Material getMaterial() {
        return Material.GOLD_HELMET;
    }

    private static void lIlIlIIlll() {
        lIlIlIlI = new String[lIlllIII[4]];
        CorinthianHelmetSkill.lIlIlIlI[CorinthianHelmetSkill.lIlllIII[0]] = CorinthianHelmetSkill.lIlIIlIIlI("CBM9LnUoHTwkOz8b", "KrNMU");
        CorinthianHelmetSkill.lIlIlIlI[CorinthianHelmetSkill.lIlllIII[1]] = CorinthianHelmetSkill.lIlIIlIIll("ypxJef4wUJA=", "HyVcH");
        CorinthianHelmetSkill.lIlIlIlI[CorinthianHelmetSkill.lIlllIII[2]] = CorinthianHelmetSkill.lIlIIlIIll("Ck9HEEy8Gro=", "OKexJ");
        CorinthianHelmetSkill.lIlIlIlI[CorinthianHelmetSkill.lIlllIII[3]] = CorinthianHelmetSkill.lIlIIlIIlI("SjwLD3UYNAgWIUo1D0MzBjQeGzAZ", "jQjcU");
    }

    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIIllIllIIIlIII, Player lIIllIllIIIllII, Player lIIllIllIIIIllI, boolean lIIllIllIIIlIlI) {
        CorinthianHelmetSkill lIIllIllIIIlIIl;
        super.onPlayerDamageByPlayer(lIIllIllIIIlIII, lIIllIllIIIllII, lIIllIllIIIIllI, lIIllIllIIIlIlI);
        if (CorinthianHelmetSkill.lIllIIlllI((int)lIIllIllIIIlIlI) && CorinthianHelmetSkill.lIllIIlllI((int)lIIllIllIIIlIIl.getGame().areEnemies(lIIllIllIIIIllI, lIIllIllIIIllII).booleanValue())) {
            lIIllIllIIIlIII.setDamage(lIIllIllIIIlIII.getDamage() * (1.0 - CorinthianHelmetSkill.getDmgMultiplier()));
        }
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }
}

