/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.FireworkEffect
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Firework
 *  org.bukkit.inventory.meta.FireworkMeta
 */
package com.biel.lobby.utilities;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkEffectPlayer {
    private /* synthetic */ Method firework_getHandle;
    private static final /* synthetic */ String[] llIlIIll;
    private static final /* synthetic */ int[] llIlIlll;
    private /* synthetic */ Method nms_world_broadcastEntityEffect;
    private /* synthetic */ Method world_getHandle;

    private static boolean llIIllIlll(Object object) {
        return object == null;
    }

    public void playFirework(World lIIIllIIIlllIII, Location lIIIllIIIlIllll, FireworkEffect lIIIllIIIlIlllI) throws Exception {
        FireworkEffectPlayer lIIIllIIIllIIIl;
        Firework lIIIllIIIllIlIl = (Firework)lIIIllIIIlllIII.spawn(lIIIllIIIlIllll, Firework.class);
        Object lIIIllIIIllIlII = null;
        Object lIIIllIIIllIIll = null;
        if (FireworkEffectPlayer.llIIllIlll(lIIIllIIIllIIIl.world_getHandle)) {
            lIIIllIIIllIIIl.world_getHandle = FireworkEffectPlayer.getMethod(lIIIllIIIlllIII.getClass(), llIlIIll[llIlIlll[0]]);
            lIIIllIIIllIIIl.firework_getHandle = FireworkEffectPlayer.getMethod(lIIIllIIIllIlIl.getClass(), llIlIIll[llIlIlll[1]]);
        }
        lIIIllIIIllIlII = lIIIllIIIllIIIl.world_getHandle.invoke((Object)lIIIllIIIlllIII, null);
        lIIIllIIIllIIll = lIIIllIIIllIIIl.firework_getHandle.invoke((Object)lIIIllIIIllIlIl, null);
        if (FireworkEffectPlayer.llIIllIlll(lIIIllIIIllIIIl.nms_world_broadcastEntityEffect)) {
            lIIIllIIIllIIIl.nms_world_broadcastEntityEffect = FireworkEffectPlayer.getMethod(lIIIllIIIllIlII.getClass(), llIlIIll[llIlIlll[2]]);
        }
        FireworkMeta lIIIllIIIllIIlI = lIIIllIIIllIlIl.getFireworkMeta();
        lIIIllIIIllIIlI.clearEffects();
        lIIIllIIIllIIlI.setPower(llIlIlll[1]);
        lIIIllIIIllIIlI.addEffect(lIIIllIIIlIlllI);
        lIIIllIIIllIlIl.setFireworkMeta(lIIIllIIIllIIlI);
        Object[] arrobject = new Object[llIlIlll[2]];
        arrobject[FireworkEffectPlayer.llIlIlll[0]] = lIIIllIIIllIIll;
        arrobject[FireworkEffectPlayer.llIlIlll[1]] = llIlIlll[3];
        "".length();
        lIIIllIIIllIIIl.nms_world_broadcastEntityEffect.invoke(lIIIllIIIllIlII, arrobject);
        lIIIllIIIllIlIl.remove();
    }

    public FireworkEffectPlayer() {
        FireworkEffectPlayer lIIIllIIlIIIIll;
        lIIIllIIlIIIIll.world_getHandle = null;
        lIIIllIIlIIIIll.nms_world_broadcastEntityEffect = null;
        lIIIllIIlIIIIll.firework_getHandle = null;
    }

    private static boolean llIIlllIIl(int n) {
        return n != 0;
    }

    private static void llIIllIIll() {
        llIlIIll = new String[llIlIlll[4]];
        FireworkEffectPlayer.llIlIIll[FireworkEffectPlayer.llIlIlll[0]] = FireworkEffectPlayer.llIIlIlllI("ACglAwwJKT0u", "gMQKm");
        FireworkEffectPlayer.llIlIIll[FireworkEffectPlayer.llIlIlll[1]] = FireworkEffectPlayer.llIIllIIIl("+ZEc53yBrSOpCBfEJzdDTw==", "QWlby");
        FireworkEffectPlayer.llIlIIll[FireworkEffectPlayer.llIlIlll[2]] = FireworkEffectPlayer.llIIllIIIl("fuiTP8YvFmlz5dkEqkQ/uxV5E7xo7p52", "nrpZK");
    }

    private static String llIIlIlllI(String lIIIllIIIIIlIll, String lIIIllIIIIIlIlI) {
        lIIIllIIIIIlIll = new String(Base64.getDecoder().decode(lIIIllIIIIIlIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIIllIIIIIlllI = new StringBuilder();
        char[] lIIIllIIIIIllIl = lIIIllIIIIIlIlI.toCharArray();
        int lIIIllIIIIIllII = llIlIlll[0];
        char[] lIIIllIIIIIIllI = lIIIllIIIIIlIll.toCharArray();
        int lIIIllIIIIIIlIl = lIIIllIIIIIIllI.length;
        int lIIIllIIIIIIlII = llIlIlll[0];
        while (FireworkEffectPlayer.llIIlllIII(lIIIllIIIIIIlII, lIIIllIIIIIIlIl)) {
            char lIIIllIIIIlIIIl = lIIIllIIIIIIllI[lIIIllIIIIIIlII];
            "".length();
            lIIIllIIIIIlllI.append((char)(lIIIllIIIIlIIIl ^ lIIIllIIIIIllIl[lIIIllIIIIIllII % lIIIllIIIIIllIl.length]));
            ++lIIIllIIIIIllII;
            ++lIIIllIIIIIIlII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(lIIIllIIIIIlllI);
    }

    static {
        FireworkEffectPlayer.llIIllIllI();
        FireworkEffectPlayer.llIIllIIll();
    }

    private static String llIIllIIIl(String lIIIlIllllllIIl, String lIIIlIllllllIlI) {
        try {
            SecretKeySpec lIIIlIllllllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIlIllllllIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIIlIlllllllIl = Cipher.getInstance("Blowfish");
            lIIIlIlllllllIl.init(llIlIlll[2], lIIIlIllllllllI);
            return new String(lIIIlIlllllllIl.doFinal(Base64.getDecoder().decode(lIIIlIllllllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIlIlllllllII) {
            lIIIlIlllllllII.printStackTrace();
            return null;
        }
    }

    private static void llIIllIllI() {
        llIlIlll = new int[5];
        FireworkEffectPlayer.llIlIlll[0] = (222 + 213 - 268 + 61 ^ 4 + 116 - 110 + 172) & (212 ^ 197 ^ (120 ^ 59) ^ -" ".length());
        FireworkEffectPlayer.llIlIlll[1] = " ".length();
        FireworkEffectPlayer.llIlIlll[2] = "  ".length();
        FireworkEffectPlayer.llIlIlll[3] = 114 + 126 - 214 + 159 ^ 162 + 160 - 189 + 35;
        FireworkEffectPlayer.llIlIlll[4] = "   ".length();
    }

    private static Method getMethod(Class<?> lIIIllIIIlIIIII, String lIIIllIIIIlllll) {
        Method[] lIIIllIIIIllllI = lIIIllIIIlIIIII.getMethods();
        int lIIIllIIIIlllIl = lIIIllIIIIllllI.length;
        int lIIIllIIIIlllII = llIlIlll[0];
        while (FireworkEffectPlayer.llIIlllIII(lIIIllIIIIlllII, lIIIllIIIIlllIl)) {
            Method lIIIllIIIlIIIll = lIIIllIIIIllllI[lIIIllIIIIlllII];
            if (FireworkEffectPlayer.llIIlllIIl((int)lIIIllIIIlIIIll.getName().equals(lIIIllIIIIlllll))) {
                return lIIIllIIIlIIIll;
            }
            ++lIIIllIIIIlllII;
            "".length();
            if ((88 + 148 - 117 + 66 ^ 142 + 126 - 243 + 164) != " ".length()) continue;
            return null;
        }
        return null;
    }

    private static boolean llIIlllIII(int n, int n2) {
        return n < n2;
    }
}

