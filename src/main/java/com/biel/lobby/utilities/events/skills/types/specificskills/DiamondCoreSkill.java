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
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class DiamondCoreSkill
extends InherentSkill {
    private static final /* synthetic */ int[] lIllIIlIl;
    private static final /* synthetic */ String[] lIlIllIII;

    private static boolean lIlIIIIIIlI(int n) {
        return n != 0;
    }

    private static String lIIlllIlllI(String lIlIllIlIlIIIl, String lIlIllIlIlIIII) {
        lIlIllIlIlIIIl = new String(Base64.getDecoder().decode(lIlIllIlIlIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlIllIlIIllll = new StringBuilder();
        char[] lIlIllIlIIlllI = lIlIllIlIlIIII.toCharArray();
        int lIlIllIlIIllIl = lIllIIlIl[0];
        char[] lIlIllIlIIIlll = lIlIllIlIlIIIl.toCharArray();
        int lIlIllIlIIIllI = lIlIllIlIIIlll.length;
        int lIlIllIlIIIlIl = lIllIIlIl[0];
        while (DiamondCoreSkill.lIlIIIIIIIl(lIlIllIlIIIlIl, lIlIllIlIIIllI)) {
            char lIlIllIlIlIIlI = lIlIllIlIIIlll[lIlIllIlIIIlIl];
            "".length();
            lIlIllIlIIllll.append((char)(lIlIllIlIlIIlI ^ lIlIllIlIIlllI[lIlIllIlIIllIl % lIlIllIlIIlllI.length]));
            ++lIlIllIlIIllIl;
            ++lIlIllIlIIIlIl;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(lIlIllIlIIllll);
    }

    private static double getProtectionRatioForPlayer() {
        return 0.082;
    }

    private static boolean lIlIIIIIIII(Object object, Object object2) {
        return object != object2;
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    @Override
    public String getName() {
        return lIlIllIII[lIllIIlIl[0]];
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND;
    }

    private static String lIIllIlIIIl(String lIlIllIIlIllll, String lIlIllIIlIlllI) {
        try {
            SecretKeySpec lIlIllIIllIIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIllIIlIlllI.getBytes(StandardCharsets.UTF_8)), lIllIIlIl[7]), "DES");
            Cipher lIlIllIIllIIIl = Cipher.getInstance("DES");
            lIlIllIIllIIIl.init(lIllIIlIl[2], lIlIllIIllIIlI);
            return new String(lIlIllIIllIIIl.doFinal(Base64.getDecoder().decode(lIlIllIIlIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIllIIllIIII) {
            lIlIllIIllIIII.printStackTrace();
            return null;
        }
    }

    private static double getProtectionRatio() {
        return 0.1;
    }

    private static void lIIllllIllI() {
        lIlIllIII = new String[lIllIIlIl[6]];
        DiamondCoreSkill.lIlIllIII[DiamondCoreSkill.lIllIIlIl[0]] = DiamondCoreSkill.lIIllIlIIIl("ECh0ZmG3oKNUejCHCWGZlHYufrCmMLIq", "zENoE");
        DiamondCoreSkill.lIlIllIII[DiamondCoreSkill.lIllIIlIl[1]] = DiamondCoreSkill.lIIlllIlllI("", "puLyR");
        DiamondCoreSkill.lIlIllIII[DiamondCoreSkill.lIllIIlIl[2]] = DiamondCoreSkill.lIIllllIIII("XbXyWfHo+gw=", "zbPva");
        DiamondCoreSkill.lIlIllIII[DiamondCoreSkill.lIllIIlIl[3]] = DiamondCoreSkill.lIIlllIlllI("", "amAhm");
        DiamondCoreSkill.lIlIllIII[DiamondCoreSkill.lIllIIlIl[4]] = DiamondCoreSkill.lIIlllIlllI("SQ==", "lHqAo");
        DiamondCoreSkill.lIlIllIII[DiamondCoreSkill.lIllIIlIl[5]] = DiamondCoreSkill.lIIlllIlllI("KBYJBRYTC00VH1oeDBxTCBYPBQdaFwhQAg8SAQMWDBwBUBUVHRlQFhRTGB5TAUMQUFhaCFwNUwoWH1AQGxcMUBYUFgAZEFoDHx8DHwE=", "zsmps");
    }

    private static void lIIlllllllI() {
        lIllIIlIl = new int[8];
        DiamondCoreSkill.lIllIIlIl[0] = (204 ^ 158) & ~(146 ^ 192);
        DiamondCoreSkill.lIllIIlIl[1] = " ".length();
        DiamondCoreSkill.lIllIIlIl[2] = "  ".length();
        DiamondCoreSkill.lIllIIlIl[3] = "   ".length();
        DiamondCoreSkill.lIllIIlIl[4] = 28 + 118 - 17 + 27 ^ 83 + 2 - -50 + 17;
        DiamondCoreSkill.lIllIIlIl[5] = 134 + 8 - 85 + 101 ^ 13 + 145 - 80 + 77;
        DiamondCoreSkill.lIllIIlIl[6] = 114 ^ 20 ^ (233 ^ 137);
        DiamondCoreSkill.lIllIIlIl[7] = 250 ^ 151 ^ (57 ^ 92);
    }

    protected void onPlayerDamage(EntityDamageEvent lIlIlllIIllllI, Player lIlIlllIIlIlII) {
        DiamondCoreSkill lIlIlllIIlIllI;
        super.onPlayerDamage(lIlIlllIIllllI, lIlIlllIIlIlII);
        if (DiamondCoreSkill.lIlIIIIIIII((Object)lIlIlllIIlIlII, (Object)lIlIlllIIlIllI.getPlayer())) {
            return;
        }
        Joc lIlIlllIIllIIl = lIlIlllIIlIllI.getGame();
        int lIlIlllIIlIlll = lIlIlllIIllIIl.getPlayers().stream().mapToInt(lIlIlllIIIIIII -> {
            DiamondCoreSkill lIlIlllIIIIlII;
            int n;
            if (DiamondCoreSkill.lIlIIIIIIlI((int)lIlIlllIIllIIl.areEnemies(lIlIlllIIIIlII.getPlayer(), (Player)lIlIlllIIIIIII).booleanValue())) {
                n = lIllIIlIl[1];
                "".length();
                if (null != null) {
                    return (64 + 1 - -95 + 0 ^ 178 + 60 - 54 + 1) & (236 ^ 149 ^ (242 ^ 146) ^ -" ".length());
                }
            } else {
                n = lIllIIlIl[0];
            }
            return n;
        }).sum();
        int lIlIlllIlIIIlI = lIllIIlIl[0];
        while (DiamondCoreSkill.lIlIIIIIIIl(lIlIlllIlIIIlI, lIlIlllIIlIlll)) {
            lIlIlllIIlIllI.getWorld().playEffect(lIlIlllIIlIllI.getPlayer().getEyeLocation().add(0.0, -0.14 * (double)lIlIlllIIlIlll, 0.0), Effect.HAPPY_VILLAGER, lIlIlllIIlIlll);
            ++lIlIlllIlIIIlI;
            "".length();
            if (null == null) continue;
            return;
        }
        lIlIlllIIllllI.setDamage(lIlIlllIIllllI.getDamage() * (1.0 - (DiamondCoreSkill.getProtectionRatio() + DiamondCoreSkill.getProtectionRatioForPlayer() * (double)lIlIlllIIlIlll)));
    }

    static {
        DiamondCoreSkill.lIIlllllllI();
        DiamondCoreSkill.lIIllllIllI();
    }

    private static boolean lIlIIIIIIIl(int n, int n2) {
        return n < n2;
    }

    @Override
    public String getDescription() {
        Double lIlIllllIllllI = (double)Math.round(DiamondCoreSkill.getProtectionRatio() * 100.0 * 10.0) / 10.0;
        Double lIlIllllIlllIl = (double)Math.round(DiamondCoreSkill.getProtectionRatioForPlayer() * 100.0 * 10.0) / 10.0;
        String lIlIllllIllIll = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIlIllIII[lIllIIlIl[1]]).append(lIlIllllIllllI).append((Object)ChatColor.WHITE).append(lIlIllIII[lIllIIlIl[2]]));
        String lIlIllllIllIIl = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIlIllIII[lIllIIlIl[3]]).append(lIlIllllIlllIl).append((Object)ChatColor.WHITE).append(lIlIllIII[lIllIIlIl[4]]));
        Object[] arrobject = new Object[lIllIIlIl[2]];
        arrobject[DiamondCoreSkill.lIllIIlIl[0]] = lIlIllllIllIll;
        arrobject[DiamondCoreSkill.lIllIIlIl[1]] = lIlIllllIllIIl;
        return MessageFormat.format(lIlIllIII[lIllIIlIl[5]], arrobject);
    }

    private static String lIIllllIIII(String lIlIllIIllllII, String lIlIllIIlllIIl) {
        try {
            SecretKeySpec lIlIllIIllllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIllIIlllIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIllIIlllllI = Cipher.getInstance("Blowfish");
            lIlIllIIlllllI.init(lIllIIlIl[2], lIlIllIIllllll);
            return new String(lIlIllIIlllllI.doFinal(Base64.getDecoder().decode(lIlIllIIllllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIllIIllllIl) {
            lIlIllIIllllIl.printStackTrace();
            return null;
        }
    }

    public DiamondCoreSkill(Player lIllIIIIIIlIII) {
        DiamondCoreSkill lIllIIIIIIIlll;
        super(lIllIIIIIIlIII);
    }
}

