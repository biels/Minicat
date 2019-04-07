/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class CalciumSourceSkill
extends InherentSkill {
    private static final /* synthetic */ String[] l;
    private static final /* synthetic */ int[] llII;

    private static String lIIl(String lllllIlllllIlIl, String lllllIlllllIlII) {
        try {
            SecretKeySpec lllllIllllllIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllllIlllllIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllllIllllllIIl = Cipher.getInstance("Blowfish");
            lllllIllllllIIl.init(llII[2], lllllIllllllIlI);
            return new String(lllllIllllllIIl.doFinal(Base64.getDecoder().decode(lllllIlllllIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllIllllllIII) {
            lllllIllllllIII.printStackTrace();
            return null;
        }
    }

    private static void lIlII() {
        l = new String[llII[8]];
        CalciumSourceSkill.l[CalciumSourceSkill.llII[0]] = CalciumSourceSkill.l("uYN1EqsgxlzwPcgFAjvnIw==", "WdfLH");
        CalciumSourceSkill.l[CalciumSourceSkill.llII[1]] = CalciumSourceSkill.lIII("", "xRSro");
        CalciumSourceSkill.l[CalciumSourceSkill.llII[2]] = CalciumSourceSkill.lIIl("2o3a0KPxRfY=", "EGSax");
        CalciumSourceSkill.l[CalciumSourceSkill.llII[3]] = CalciumSourceSkill.lIIl("qY7yluCqquc=", "ouxmc");
        CalciumSourceSkill.l[CalciumSourceSkill.llII[4]] = CalciumSourceSkill.lIIl("g2Q1vzMJQnwdKcfke0Qe4kZxlFf/0lXlz+JDAS2zGbRCXZWXwjipjQAN17fgQNho6gguj0gc3GFbashxa52Er+KMFmP3nWK7", "sDHRZ");
        CalciumSourceSkill.l[CalciumSourceSkill.llII[5]] = CalciumSourceSkill.lIIl("MbJpbjUvi+5V8qe5+1E34Xag4kBxuI7uvYJuRIQR0YDcQU4scjJe8hu+bSBKmfD1O0tXSATzPfvxIuA72X5M9Zs3vzZnyRuvcV0UFjQIzdJrA/INe9Y8c6OgvcrJl2tRwSHnqrqun6Y=", "bNgwt");
        CalciumSourceSkill.l[CalciumSourceSkill.llII[6]] = CalciumSourceSkill.lIIl("A8uIuZWwFr0=", "FiyAx");
    }

    private static String l(String llllllIIIIIIIlI, String llllllIIIIIIIll) {
        try {
            SecretKeySpec llllllIIIIIIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllllIIIIIIIll.getBytes(StandardCharsets.UTF_8)), llII[9]), "DES");
            Cipher llllllIIIIIIllI = Cipher.getInstance("DES");
            llllllIIIIIIllI.init(llII[2], llllllIIIIIIlll);
            return new String(llllllIIIIIIllI.doFinal(Base64.getDecoder().decode(llllllIIIIIIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllllIIIIIIlIl) {
            llllllIIIIIIlIl.printStackTrace();
            return null;
        }
    }

    static {
        CalciumSourceSkill.lIIllI();
        CalciumSourceSkill.lIlII();
    }

    private static void lIIllI() {
        llII = new int[10];
        CalciumSourceSkill.llII[0] = (93 ^ 56 ^ (96 ^ 21)) & (68 ^ 24 ^ (201 ^ 133) ^ -" ".length());
        CalciumSourceSkill.llII[1] = " ".length();
        CalciumSourceSkill.llII[2] = "  ".length();
        CalciumSourceSkill.llII[3] = "   ".length();
        CalciumSourceSkill.llII[4] = 164 ^ 160;
        CalciumSourceSkill.llII[5] = 35 ^ 38;
        CalciumSourceSkill.llII[6] = 175 + 47 - 107 + 79 ^ 112 + 34 - 82 + 132;
        CalciumSourceSkill.llII[7] = 4 ^ 8;
        CalciumSourceSkill.llII[8] = 126 ^ 121;
        CalciumSourceSkill.llII[9] = 139 ^ 131;
    }

    @Override
    public Material getMaterial() {
        return Material.MILK_BUCKET;
    }

    public void playEffect(Player llllllIIIlIIlIl) {
        int llllllIIIlIlIIl = llII[0];
        while (CalciumSourceSkill.lIlIlI(llllllIIIlIlIIl, llII[7])) {
            CalciumSourceSkill llllllIIIlIIllI;
            llllllIIIlIIllI.getWorld().playEffect(llllllIIIlIIlIl.getEyeLocation().subtract(0.0, 0.9, 0.0), Effect.SNOWBALL_BREAK, Utils.NombreEntre(llII[0], llII[8]));
            ++llllllIIIlIlIIl;
            "".length();
            if (-" ".length() < ((16 + 34 - 13 + 112 ^ 53 + 1 - 52 + 151) & (122 + 80 - 85 + 33 ^ 69 + 113 - 158 + 130 ^ -" ".length()))) continue;
            return;
        }
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    @Override
    public String getDescription() {
        CalciumSourceSkill llllllIIlIIlIIl;
        String llllllIIlIIlIII = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(l[llII[1]]).append(llllllIIlIIlIIl.getModifier()).append((Object)ChatColor.WHITE));
        Double llllllIIlIIIlll = (double)Math.round(llllllIIlIIlIIl.getProtectionRatio() * 100.0 * 10.0) / 10.0;
        String llllllIIlIIIllI = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(l[llII[2]]).append(llllllIIlIIIlll).append((Object)ChatColor.WHITE).append(l[llII[3]]));
        return String.valueOf(new StringBuilder().append(l[llII[4]]).append(llllllIIlIIlIII).append(l[llII[5]]).append(llllllIIlIIIllI).append(l[llII[6]]));
    }

    public double getModifier() {
        return 2.25;
    }

    public CalciumSourceSkill(Player llllllIIlIlIlIl) {
        CalciumSourceSkill llllllIIlIlIlII;
        super(llllllIIlIlIlIl);
    }

    @Override
    public String getName() {
        return l[llII[0]];
    }

    protected void onPlayerDamage(EntityDamageEvent llllllIIIllIIlI, Player llllllIIIllIIIl) {
        CalciumSourceSkill llllllIIIllIllI;
        super.onPlayerDamage(llllllIIIllIIlI, llllllIIIllIIIl);
        if (CalciumSourceSkill.lIlIIl((Object)llllllIIIllIIlI.getCause(), (Object)EntityDamageEvent.DamageCause.FALL)) {
            double llllllIIIlllIlI = llllllIIIllIllI.getProtectionRatio();
            double llllllIIIlllIIl = llllllIIIllIIlI.getDamage();
            double llllllIIIlllIII = llllllIIIlllIIl * llllllIIIlllIlI;
            double llllllIIIllIlll = llllllIIIlllIIl - llllllIIIlllIII;
            llllllIIIllIIlI.setDamage(llllllIIIllIlll);
            llllllIIIllIIlI.setDamage(llllllIIIllIIlI.getDamage() - llllllIIIllIllI.getModifier());
        }
    }

    private static boolean lIlIlI(int n, int n2) {
        return n < n2;
    }

    private static String lIII(String llllllIIIIllIIl, String llllllIIIIllIII) {
        llllllIIIIllIIl = new String(Base64.getDecoder().decode(llllllIIIIllIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llllllIIIIlIlll = new StringBuilder();
        char[] llllllIIIIlIllI = llllllIIIIllIII.toCharArray();
        int llllllIIIIlIlIl = llII[0];
        char[] llllllIIIIIllll = llllllIIIIllIIl.toCharArray();
        int llllllIIIIIlllI = llllllIIIIIllll.length;
        int llllllIIIIIllIl = llII[0];
        while (CalciumSourceSkill.lIlIlI(llllllIIIIIllIl, llllllIIIIIlllI)) {
            char llllllIIIIllIlI = llllllIIIIIllll[llllllIIIIIllIl];
            "".length();
            llllllIIIIlIlll.append((char)(llllllIIIIllIlI ^ llllllIIIIlIllI[llllllIIIIlIlIl % llllllIIIIlIllI.length]));
            ++llllllIIIIlIlIl;
            ++llllllIIIIIllIl;
            "".length();
            if ("   ".length() <= "   ".length()) continue;
            return null;
        }
        return String.valueOf(llllllIIIIlIlll);
    }

    private static boolean lIlIIl(Object object, Object object2) {
        return object == object2;
    }

    public double getProtectionRatio() {
        return 0.65;
    }
}

