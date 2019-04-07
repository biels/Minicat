/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.util.Vector
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
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

public class VampireSkill
extends InherentSkill {
    private static final /* synthetic */ String[] lIIIllllI;
    private static final /* synthetic */ int[] lIIlIlIlI;

    @Override
    public String getName() {
        return lIIIllllI[lIIlIlIlI[0]];
    }

    protected void onPlayerDeathByPlayer(PlayerDeathEvent llIllIIlIllIll, Player llIllIIlIlIlIl, Player llIllIIlIllIIl) {
        int n;
        VampireSkill llIllIIlIlllII;
        super.onPlayerDeathByPlayer(llIllIIlIllIll, llIllIIlIlIlIl, llIllIIlIllIIl);
        if (!VampireSkill.lIIIlIIllII(VampireSkill.lIIIlIIlIll(llIllIIlIllIIl.getHealth(), 0.2)) || VampireSkill.lIIIlIIllIl((int)llIllIIlIllIIl.isDead())) {
            return;
        }
        if (VampireSkill.lIIIlIIlllI(VampireSkill.lIIIlIIlIll(llIllIIlIlIlIl.getLocation().distance(llIllIIlIllIIl.getLocation()), 8.0))) {
            n = lIIlIlIlI[1];
            "".length();
            if ("  ".length() == (47 ^ 4 ^ (79 ^ 96))) {
                return;
            }
        } else {
            int llIllIIlIllIII;
            n = llIllIIlIllIII = lIIlIlIlI[0];
        }
        if (VampireSkill.lIIIlIIllIl((int)llIllIIlIllIIl.getName().equals(llIllIIlIlllII.getPlayer().getName()))) {
            Utils.healDamageable((Damageable)llIllIIlIlllII.getPlayer(), llIllIIlIllIIl.getMaxHealth() * VampireSkill.getProtectionRatio());
            llIllIIlIlllII.getWorld().playSound(llIllIIlIlllII.getPlayer().getEyeLocation(), Sound.ENTITY_GENERIC_DRINK, 1.0f, 1.0f);
            llIllIIlIlllII.getWorld().playSound(llIllIIlIlllII.getPlayer().getEyeLocation(), Sound.ENTITY_WITCH_DRINK, 2.0f, 0.8f);
            llIllIIlIlllII.getWorld().playSound(llIllIIlIlllII.getPlayer().getEyeLocation(), Sound.ENTITY_GENERIC_EAT, 1.0f, 1.8f);
            Vector llIllIIlIllllI = Utils.CrearVector(llIllIIlIllIIl.getLocation(), llIllIIlIlIlIl.getLocation()).multiply(0.5);
            double llIllIIlIlllIl = 7.0;
            int llIllIIlIlllll = lIIlIlIlI[0];
            while (VampireSkill.lIIIlIIlllI(VampireSkill.lIIIlIIlIll(llIllIIlIlllll, llIllIIlIlllIl))) {
                llIllIIlIlllII.getWorld().playEffect(llIllIIlIlllII.getPlayer().getLocation().add(llIllIIlIllllI.clone().multiply((double)llIllIIlIlllll / llIllIIlIlllIl)), Effect.HEART, lIIlIlIlI[0]);
                ++llIllIIlIlllll;
                "".length();
                if (-" ".length() == -" ".length()) continue;
                return;
            }
        }
    }

    private static String lIIIIlIlIll(String llIllIIlIIlIII, String llIllIIlIIIlll) {
        try {
            SecretKeySpec llIllIIlIIlIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIllIIlIIIlll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIllIIlIIlIlI = Cipher.getInstance("Blowfish");
            llIllIIlIIlIlI.init(lIIlIlIlI[2], llIllIIlIIlIll);
            return new String(llIllIIlIIlIlI.doFinal(Base64.getDecoder().decode(llIllIIlIIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIIlIIlIIl) {
            llIllIIlIIlIIl.printStackTrace();
            return null;
        }
    }

    private static void lIIIIlllllI() {
        lIIlIlIlI = new int[7];
        VampireSkill.lIIlIlIlI[0] = (87 ^ 10 ^ " ".length()) & (66 + 53 - 54 + 164 ^ 47 + 102 - 146 + 182 ^ -" ".length());
        VampireSkill.lIIlIlIlI[1] = " ".length();
        VampireSkill.lIIlIlIlI[2] = "  ".length();
        VampireSkill.lIIlIlIlI[3] = "   ".length();
        VampireSkill.lIIlIlIlI[4] = 23 ^ 0 ^ (215 ^ 196);
        VampireSkill.lIIlIlIlI[5] = 184 ^ 189;
        VampireSkill.lIIlIlIlI[6] = 63 ^ 55;
    }

    private static double getProtectionRatio() {
        return 0.3;
    }

    private static String lIIIIlIlllI(String llIllIIIlIlIll, String llIllIIIlIIlIl) {
        llIllIIIlIlIll = new String(Base64.getDecoder().decode(llIllIIIlIlIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIllIIIlIlIIl = new StringBuilder();
        char[] llIllIIIlIlIII = llIllIIIlIIlIl.toCharArray();
        int llIllIIIlIIlll = lIIlIlIlI[0];
        char[] llIllIIIlIIIIl = llIllIIIlIlIll.toCharArray();
        int llIllIIIlIIIII = llIllIIIlIIIIl.length;
        int llIllIIIIlllll = lIIlIlIlI[0];
        while (VampireSkill.lIIIlIIllll(llIllIIIIlllll, llIllIIIlIIIII)) {
            char llIllIIIlIllII = llIllIIIlIIIIl[llIllIIIIlllll];
            "".length();
            llIllIIIlIlIIl.append((char)(llIllIIIlIllII ^ llIllIIIlIlIII[llIllIIIlIIlll % llIllIIIlIlIII.length]));
            ++llIllIIIlIIlll;
            ++llIllIIIIlllll;
            "".length();
            if ((138 ^ 142) != 0) continue;
            return null;
        }
        return String.valueOf(llIllIIIlIlIIl);
    }

    private static String lIIIIlIllII(String llIllIIIlllIIl, String llIllIIIlllIII) {
        try {
            SecretKeySpec llIllIIIlllllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIllIIIlllIII.getBytes(StandardCharsets.UTF_8)), lIIlIlIlI[6]), "DES");
            Cipher llIllIIIllllIl = Cipher.getInstance("DES");
            llIllIIIllllIl.init(lIIlIlIlI[2], llIllIIIlllllI);
            return new String(llIllIIIllllIl.doFinal(Base64.getDecoder().decode(llIllIIIlllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIllIIIllllII) {
            llIllIIIllllII.printStackTrace();
            return null;
        }
    }

    static {
        VampireSkill.lIIIIlllllI();
        VampireSkill.lIIIIlIllll();
    }

    public VampireSkill(Player llIllIIlllIIlI) {
        VampireSkill llIllIIlllIIll;
        super(llIllIIlllIIlI);
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    @Override
    public Material getMaterial() {
        return Material.REDSTONE;
    }

    private static void lIIIIlIllll() {
        lIIIllllI = new String[lIIlIlIlI[5]];
        VampireSkill.lIIIllllI[VampireSkill.lIIlIlIlI[0]] = VampireSkill.lIIIIlIlIll("jn22rfhC5fg=", "xTwnD");
        VampireSkill.lIIIllllI[VampireSkill.lIIlIlIlI[1]] = VampireSkill.lIIIIlIllII("UiZezKj8Ajk=", "Vutkb");
        VampireSkill.lIIIllllI[VampireSkill.lIIlIlIlI[2]] = VampireSkill.lIIIIlIllII("SocQ2mQeiK4=", "JwZrq");
        VampireSkill.lIIIllllI[VampireSkill.lIIlIlIlI[3]] = VampireSkill.lIIIIlIlIll("Z7zB4klPIKvio+k0E7vcBg==", "UZNEZ");
        VampireSkill.lIIIllllI[VampireSkill.lIIlIlIlI[4]] = VampireSkill.lIIIIlIlllI("YQAOWT4gRB0QNiBEBsKZKigJClkjNAUFWT8gEA4KcjQKSxw8JAkCGg==", "AdkyR");
    }

    private static boolean lIIIlIIllIl(int n) {
        return n != 0;
    }

    private static boolean lIIIlIIlllI(int n) {
        return n < 0;
    }

    private static boolean lIIIlIIllII(int n) {
        return n >= 0;
    }

    @Override
    public String getDescription() {
        Double llIllIIllIllIl = (double)Math.round(VampireSkill.getProtectionRatio() * 100.0 * 10.0) / 10.0;
        String llIllIIllIllII = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIIllllI[lIIlIlIlI[1]]).append(llIllIIllIllIl).append((Object)ChatColor.WHITE).append(lIIIllllI[lIIlIlIlI[2]]));
        return String.valueOf(new StringBuilder().append(lIIIllllI[lIIlIlIlI[3]]).append(llIllIIllIllII).append(lIIIllllI[lIIlIlIlI[4]]));
    }

    private static int lIIIlIIlIll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean lIIIlIIllll(int n, int n2) {
        return n < n2;
    }
}

