/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedyArcher
extends InherentSkill {
    private static final /* synthetic */ int[] lIllIllI;
    private static final /* synthetic */ String[] lIlIllIl;

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    @Override
    public Material getMaterial() {
        return Material.FEATHER;
    }

    private static String lIlIlIIlIl(String lIIllIllIlllIII, String lIIllIllIllIlll) {
        lIIllIllIlllIII = new String(Base64.getDecoder().decode(lIIllIllIlllIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIllIllIllIllI = new StringBuilder();
        char[] lIIllIllIllIlIl = lIIllIllIllIlll.toCharArray();
        int lIIllIllIllIlII = lIllIllI[0];
        char[] lIIllIllIlIlllI = lIIllIllIlllIII.toCharArray();
        int lIIllIllIlIllIl = lIIllIllIlIlllI.length;
        int lIIllIllIlIllII = lIllIllI[0];
        while (SpeedyArcher.lIllIIlIIl(lIIllIllIlIllII, lIIllIllIlIllIl)) {
            char lIIllIllIlllIIl = lIIllIllIlIlllI[lIIllIllIlIllII];
            "".length();
            lIIllIllIllIllI.append((char)(lIIllIllIlllIIl ^ lIIllIllIllIlIl[lIIllIllIllIlII % lIIllIllIllIlIl.length]));
            ++lIIllIllIllIlII;
            ++lIIllIllIlIllII;
            "".length();
            if ("   ".length() > ((16 ^ 3) & ~(183 ^ 164))) continue;
            return null;
        }
        return String.valueOf(lIIllIllIllIllI);
    }

    private static boolean lIllIIIlll(int n) {
        return n != 0;
    }

    public SpeedyArcher(Player lIIllIlllIllIll) {
        SpeedyArcher lIIllIlllIlllII;
        super(lIIllIlllIllIll);
    }

    private static boolean lIllIIlIII(Object object, Object object2) {
        return object == object2;
    }

    @Override
    public String getName() {
        return lIlIllIl[lIllIllI[0]];
    }

    private static void lIlIlIIllI() {
        lIlIllIl = new String[lIllIllI[2]];
        SpeedyArcher.lIlIllIl[SpeedyArcher.lIllIllI[0]] = SpeedyArcher.lIlIlIIlIl("MCYmFBMDdCEEGh7Csw==", "qTWav");
        SpeedyArcher.lIlIllIl[SpeedyArcher.lIllIllI[1]] = SpeedyArcher.lIlIlIIlIl("GAcGAR82UxwdC3caBwAMNh0dAFgzFkkFHTscChoMNgdJElg7VAwdGzIBHRIKdwYHElgxHwwHADZTCFMNOVMDBh82FwYBWDIdDB4RNA==", "Wsisx");
    }

    private static void lIllIIIllI() {
        lIllIllI = new int[3];
        SpeedyArcher.lIllIllI[0] = (7 ^ 68 ^ (212 ^ 135)) & (59 ^ 20 ^ (21 ^ 42) ^ -" ".length());
        SpeedyArcher.lIllIllI[1] = " ".length();
        SpeedyArcher.lIllIllI[2] = "  ".length();
    }

    @Override
    public String getDescription() {
        return lIlIllIl[lIllIllI[1]];
    }

    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIIllIlllIIllII, Player lIIllIlllIIIllI, Player lIIllIlllIIlIlI, boolean lIIllIlllIIIlII) {
        SpeedyArcher lIIllIlllIIlIII;
        super.onPlayerDamageByPlayer(lIIllIlllIIllII, lIIllIlllIIIllI, lIIllIlllIIlIlI, lIIllIlllIIIlII);
        if (SpeedyArcher.lIllIIIlll((int)lIIllIlllIIIlII) && SpeedyArcher.lIllIIlIII((Object)lIIllIlllIIlIlI, (Object)lIIllIlllIIlIII.getPlayer())) {
            int lIIllIlllIIlllI = (int)(22L + 5L * Math.round(lIIllIlllIIllII.getDamage()));
            "".length();
            lIIllIlllIIlIlI.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIIllIlllIIlllI, lIllIllI[2]));
        }
    }

    private static boolean lIllIIlIIl(int n, int n2) {
        return n < n2;
    }

    static {
        SpeedyArcher.lIllIIIllI();
        SpeedyArcher.lIlIlIIllI();
    }
}

