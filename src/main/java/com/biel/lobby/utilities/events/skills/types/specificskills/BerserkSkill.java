/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BerserkSkill
extends InherentSkill {
    private static final /* synthetic */ String[] llllIIl;
    private static final /* synthetic */ int[] lIIIIlII;

    public BerserkSkill(Player lIlllIlIlIllIlI) {
        BerserkSkill lIlllIlIlIlllIl;
        super(lIlllIlIlIllIlI);
    }

    @Override
    public String getName() {
        return llllIIl[lIIIIlII[0]];
    }

    private static String llIlIIIII(String lIlllIlIIlIllIl, String lIlllIlIIlIllII) {
        try {
            SecretKeySpec lIlllIlIIllIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlllIlIIlIllII.getBytes(StandardCharsets.UTF_8)), lIIIIlII[5]), "DES");
            Cipher lIlllIlIIlIllll = Cipher.getInstance("DES");
            lIlllIlIIlIllll.init(lIIIIlII[2], lIlllIlIIllIIII);
            return new String(lIlllIlIIlIllll.doFinal(Base64.getDecoder().decode(lIlllIlIIlIllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllIlIIlIlllI) {
            lIlllIlIIlIlllI.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDescription() {
        Double lIlllIlIlIlIIII = (double)Math.round(BerserkSkill.getRatio() * 100.0 * 10.0) / 10.0;
        String lIlllIlIlIIllll = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llllIIl[lIIIIlII[1]]).append(lIlllIlIlIlIIII).append((Object)ChatColor.WHITE).append(llllIIl[lIIIIlII[2]]));
        Object[] arrobject = new Object[lIIIIlII[1]];
        arrobject[BerserkSkill.lIIIIlII[0]] = lIlllIlIlIIllll;
        return MessageFormat.format(llllIIl[lIIIIlII[3]], arrobject);
    }

    @Override
    public Material getMaterial() {
        return Material.GOLD_AXE;
    }

    private static void lllIllIIl() {
        lIIIIlII = new int[6];
        BerserkSkill.lIIIIlII[0] = (57 ^ 68 ^ (109 ^ 12)) & (161 + 80 - 127 + 77 ^ 93 + 16 - 58 + 112 ^ -" ".length());
        BerserkSkill.lIIIIlII[1] = " ".length();
        BerserkSkill.lIIIIlII[2] = "  ".length();
        BerserkSkill.lIIIIlII[3] = "   ".length();
        BerserkSkill.lIIIIlII[4] = 36 ^ 32;
        BerserkSkill.lIIIIlII[5] = 236 ^ 171 ^ (120 ^ 55);
    }

    private static void llIlIIlll() {
        llllIIl = new String[lIIIIlII[4]];
        BerserkSkill.llllIIl[BerserkSkill.lIIIIlII[0]] = BerserkSkill.llIlIIIII("0Sz4lBcAot4wBXJKAWRhcw==", "UMhpq");
        BerserkSkill.llllIIl[BerserkSkill.lIIIIlII[1]] = BerserkSkill.llIlIIIII("nwIBdArIBHY=", "NZoWV");
        BerserkSkill.llllIIl[BerserkSkill.lIIIIlII[2]] = BerserkSkill.llIlIIIII("rQv+GwWDz0s=", "vebHj");
        BerserkSkill.llllIIl[BerserkSkill.lIIIIlII[3]] = BerserkSkill.llIlIIIlI("9pyg+NXXh61gHfdwyOQu9MOPQ5oW7b7gyFU4MJk6QpFEwCPL6cozpuBZRZHqiWxifQc6VESgnWN0wm4DSr59jg==", "bdqyF");
    }

    private static String llIlIIIlI(String lIlllIlIIIlIIIl, String lIlllIlIIIlIIII) {
        try {
            SecretKeySpec lIlllIlIIIllIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlllIlIIIlIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlllIlIIIlIllI = Cipher.getInstance("Blowfish");
            lIlllIlIIIlIllI.init(lIIIIlII[2], lIlllIlIIIllIII);
            return new String(lIlllIlIIIlIllI.doFinal(Base64.getDecoder().decode(lIlllIlIIIlIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllIlIIIlIlII) {
            lIlllIlIIIlIlII.printStackTrace();
            return null;
        }
    }

    static {
        BerserkSkill.lllIllIIl();
        BerserkSkill.llIlIIlll();
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    private static boolean lllIlllIl(int n) {
        return n != 0;
    }

    private static boolean lllIllllI(Object object, Object object2) {
        return object == object2;
    }

    protected void onEntityDamageByEntity(EntityDamageByEntityEvent lIlllIlIIllllIl, Entity lIlllIlIIllllII, Entity lIlllIlIIllIlll) {
        BerserkSkill lIlllIlIIlllllI;
        super.onEntityDamageByEntity(lIlllIlIIllllIl, lIlllIlIIllllII, lIlllIlIIllIlll);
        if (BerserkSkill.lllIlllIl((int)lIlllIlIIllllIl.isCancelled())) {
            return;
        }
        if (BerserkSkill.lllIllllI((Object)lIlllIlIIllIlll, (Object)lIlllIlIIlllllI.getPlayer())) {
            Damageable lIlllIlIlIIIIII = (Damageable)lIlllIlIIllIlll;
            double lIlllIlIIllllll = lIlllIlIlIIIIII.getMaxHealth() - lIlllIlIlIIIIII.getHealth();
            lIlllIlIIllllIl.setDamage(lIlllIlIIllllIl.getDamage() * (1.0 + BerserkSkill.getRatio() * lIlllIlIIllllll));
        }
    }

    private static double getRatio() {
        return 0.023;
    }
}

