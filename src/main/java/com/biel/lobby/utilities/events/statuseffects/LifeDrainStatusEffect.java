/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities.events.statuseffects;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class LifeDrainStatusEffect
extends StatusEffect {
    private static final /* synthetic */ String[] llIlIll;
    private static final /* synthetic */ int[] lllIlIl;

    protected void onPlayerDamage(EntityDamageEvent lIllllIIIllllll, Player lIllllIIIlllllI) {
        LifeDrainStatusEffect lIllllIIIllllIl;
        super.onPlayerDamage(lIllllIIIllllll, lIllllIIIlllllI);
        if (LifeDrainStatusEffect.llIIllIIl((Object)lIllllIIIllllll.getCause(), (Object)EntityDamageEvent.DamageCause.WITHER)) {
            GUtils.healDamageable((Damageable)lIllllIIIllllIl.getOwnerPlayer(), (Double)(lIllllIIIllllll.getDamage() * 1.5));
            Vector lIllllIIlIIIIlI = Utils.CrearVector(lIllllIIIllllIl.getOwnerPlayer().getLocation(), lIllllIIIllllIl.getPlayer().getLocation()).multiply(0.5);
            double lIllllIIlIIIIIl = 7.0;
            int lIllllIIlIIIIll = lllIlIl[0];
            while (LifeDrainStatusEffect.llIIllIlI(LifeDrainStatusEffect.llIIllIII(lIllllIIlIIIIll, lIllllIIlIIIIIl))) {
                lIllllIIIllllIl.getWorld().playEffect(lIllllIIIllllIl.getPlayer().getEyeLocation().add(0.0, -0.8, 0.0).add(lIllllIIlIIIIlI.clone().multiply((double)lIllllIIlIIIIll / lIllllIIlIIIIIl)), Effect.HEART, lllIlIl[0]);
                ++lIllllIIlIIIIll;
                "".length();
                if ((56 ^ 60) >= 0) continue;
                return;
            }
        }
    }

    private static boolean llIIlIlll(int n) {
        return n == 0;
    }

    private static void llIIlIlIl() {
        lllIlIl = new int[4];
        LifeDrainStatusEffect.lllIlIl[0] = (201 ^ 158) & ~(32 ^ 119);
        LifeDrainStatusEffect.lllIlIl[1] = " ".length();
        LifeDrainStatusEffect.lllIlIl[2] = 254 ^ 137 ^ (248 ^ 138);
        LifeDrainStatusEffect.lllIlIl[3] = "  ".length();
    }

    private static boolean llIIllIIl(Object object, Object object2) {
        return object == object2;
    }

    public LifeDrainStatusEffect(Player lIllllIIlIlIlII) {
        LifeDrainStatusEffect lIllllIIlIlIlll;
        super(lIllllIIlIlIlII);
        lIllllIIlIlIlll.setType(StatusEffect.StatusEffectType.DEBUFF);
    }

    private static void lIlllIIll() {
        llIlIll = new String[lllIlIl[3]];
        LifeDrainStatusEffect.llIlIll[LifeDrainStatusEffect.lllIlIl[0]] = LifeDrainStatusEffect.lIlllIIlI("BzgVDisi", "PQafN");
        LifeDrainStatusEffect.llIlIll[LifeDrainStatusEffect.lllIlIl[1]] = LifeDrainStatusEffect.lIlllIIlI("BBg5Axw2DyoIBihKLgQLMUo5TQN3DzYIAjkJ", "PjXmo");
    }

    private static boolean llIIllIll(int n, int n2) {
        return n < n2;
    }

    private static boolean llIIllIlI(int n) {
        return n < 0;
    }

    @Override
    public String getDescription() {
        return llIlIll[lllIlIl[1]];
    }

    private static int llIIllIII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static boolean llIIlIllI(int n) {
        return n != 0;
    }

    static {
        LifeDrainStatusEffect.llIIlIlIl();
        LifeDrainStatusEffect.lIlllIIll();
    }

    @Override
    public String getName() {
        return llIlIll[lllIlIl[0]];
    }

    private static String lIlllIIlI(String lIllllIIIlIlIII, String lIllllIIIlIIlll) {
        lIllllIIIlIlIII = new String(Base64.getDecoder().decode(lIllllIIIlIlIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIllllIIIlIlIll = new StringBuilder();
        char[] lIllllIIIlIlIlI = lIllllIIIlIIlll.toCharArray();
        int lIllllIIIlIlIIl = lllIlIl[0];
        char[] lIllllIIIlIIIll = lIllllIIIlIlIII.toCharArray();
        int lIllllIIIlIIIlI = lIllllIIIlIIIll.length;
        int lIllllIIIlIIIIl = lllIlIl[0];
        while (LifeDrainStatusEffect.llIIllIll(lIllllIIIlIIIIl, lIllllIIIlIIIlI)) {
            char lIllllIIIlIlllI = lIllllIIIlIIIll[lIllllIIIlIIIIl];
            "".length();
            lIllllIIIlIlIll.append((char)(lIllllIIIlIlllI ^ lIllllIIIlIlIlI[lIllllIIIlIlIIl % lIllllIIIlIlIlI.length]));
            ++lIllllIIIlIlIIl;
            ++lIllllIIIlIIIIl;
            "".length();
            if ("  ".length() >= 0) continue;
            return null;
        }
        return String.valueOf(lIllllIIIlIlIll);
    }

    @Override
    public void tick() {
        LifeDrainStatusEffect lIllllIIlIIlIlI;
        super.tick();
        if (LifeDrainStatusEffect.llIIlIllI((int)lIllllIIlIIlIlI.isNthTick(lllIlIl[2])) && LifeDrainStatusEffect.llIIlIlll((int)lIllllIIlIIlIlI.getPlayer().hasPotionEffect(PotionEffectType.WITHER))) {
            "".length();
            lIllllIIlIIlIlI.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, lIllllIIlIIlIlI.getRemainingTicks(), lllIlIl[1]));
        }
    }
}

