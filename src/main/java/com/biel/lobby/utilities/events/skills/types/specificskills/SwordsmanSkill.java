/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class SwordsmanSkill
extends InherentSkill {
    private static final /* synthetic */ String[] lllIII;
    private /* synthetic */ int stacks;
    private static final /* synthetic */ int[] lllllI;

    private static String lIlIllIl(String lllIIlIlIllIIll, String lllIIlIlIllIIlI) {
        try {
            SecretKeySpec lllIIlIlIllIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIIlIlIllIIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllIIlIlIllIlIl = Cipher.getInstance("Blowfish");
            lllIIlIlIllIlIl.init(lllllI[2], lllIIlIlIllIllI);
            return new String(lllIIlIlIllIlIl.doFinal(Base64.getDecoder().decode(lllIIlIlIllIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIlIllIlII) {
            lllIIlIlIllIlII.printStackTrace();
            return null;
        }
    }

    private static boolean llIlIIlI(int n) {
        return n >= 0;
    }

    private int getStacks() {
        SwordsmanSkill lllIIlIlllIllIl;
        return lllIIlIlllIllIl.stacks;
    }

    private static boolean llIlIIIl(Object object, Object object2) {
        return object != object2;
    }

    private static boolean llIlIlIl(int n) {
        return n != 0;
    }

    private static int llIIlIII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_SWORD;
    }

    static {
        SwordsmanSkill.llIIIlll();
        SwordsmanSkill.lIlIllll();
    }

    @Override
    public String getName() {
        return lllIII[lllllI[0]];
    }

    public void copFort(Player lllIIlIllIIIllI, Player lllIIlIllIIIlIl) {
    }

    public double getDmgMultiplier() {
        return 1.5;
    }

    private static int getMaxStacks() {
        return lllllI[4];
    }

    private static boolean llIllIlI(int n, int n2) {
        return n < n2;
    }

    private void setStacks(int lllIIlIlllIIlll) {
        lllIIlIlllIlIlI.stacks = lllIIlIlllIIlll;
    }

    @Override
    public String getDescription() {
        SwordsmanSkill lllIIlIllllIIll;
        String lllIIlIllllIIlI = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lllIII[lllllI[1]]).append(lllIIlIllllIIll.getDmgMultiplier() * 100.0).append((Object)ChatColor.WHITE));
        return String.valueOf(new StringBuilder().append(lllIII[lllllI[2]]).append(lllIIlIllllIIlI).append(lllIII[lllllI[3]]));
    }

    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lllIIlIllIlIIIl, Player lllIIlIllIlIIII, Player lllIIlIllIIllll, boolean lllIIlIllIlIllI) {
        SwordsmanSkill lllIIlIllIllIlI;
        super.onPlayerDamageByPlayer(lllIIlIllIlIIIl, lllIIlIllIlIIII, lllIIlIllIIllll, lllIIlIllIlIllI);
        Player lllIIlIllIlIlIl = lllIIlIllIllIlI.getPlayer();
        if (SwordsmanSkill.llIlIIIl((Object)lllIIlIllIIllll, (Object)lllIIlIllIlIlIl)) {
            return;
        }
        SwordsmanStatusEffect lllIIlIllIlIlII = lllIIlIllIllIlI.getAssociatedEffect();
        int lllIIlIllIlIIll = lllllI[5];
        lllIIlIllIlIlII.setRemainingTicks(lllIIlIllIlIIll);
        if (SwordsmanSkill.llIlIIlI(SwordsmanSkill.llIIlIII(lllIIlIllIlIlII.getValue(), lllIIlIllIlIlII.getMaxValue()))) {
            if (SwordsmanSkill.llIlIIll((int)lllIIlIllIlIllI)) {
                lllIIlIllIlIIIl.setDamage(lllIIlIllIlIIIl.getDamage() * lllIIlIllIllIlI.getDmgMultiplier());
                Vector lllIIlIllIlllII = lllIIlIllIlIIII.getLocation().toVector().subtract(lllIIlIllIIllll.getLocation().toVector());
                Vector lllIIlIllIllIll = lllIIlIllIlllII.normalize().multiply(1.98).add(new Vector(0.0, 0.52, 0.0));
                lllIIlIllIlIIII.setVelocity(lllIIlIllIllIll);
                lllIIlIllIlIIII.playSound(lllIIlIllIIllll.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0f, 0.3f);
                lllIIlIllIllIlI.copFort(lllIIlIllIlIIII, lllIIlIllIIllll);
                lllIIlIllIlIlII.setValue(0.0);
                lllIIlIllIlIlII.setModal(lllllI[0]);
                lllIIlIllIlIlII.setRemainingTicks(lllllI[0]);
                lllIIlIllIlIlII.setModal(lllllI[0]);
                "".length();
                if (null != null) {
                    return;
                }
            }
        } else {
            lllIIlIllIlIlII.setValue(lllIIlIllIlIlII.getValue() + 1.0);
            if (SwordsmanSkill.llIlIIlI(SwordsmanSkill.llIIlIII(lllIIlIllIlIlII.getValue(), lllIIlIllIlIlII.getMaxValue()))) {
                lllIIlIllIIllll.playSound(lllIIlIllIIllll.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                lllIIlIllIlIlII.setModal(lllllI[1]);
                lllIIlIllIlIlII.setModalRemainingTicks(lllllI[6]);
            }
        }
    }

    private static String lIlIllII(String lllIIlIlIlIIIll, String lllIIlIlIlIIIlI) {
        lllIIlIlIlIIIll = new String(Base64.getDecoder().decode(lllIIlIlIlIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllIIlIlIlIIIIl = new StringBuilder();
        char[] lllIIlIlIlIIIII = lllIIlIlIlIIIlI.toCharArray();
        int lllIIlIlIIlllll = lllllI[0];
        char[] lllIIlIlIIllIIl = lllIIlIlIlIIIll.toCharArray();
        int lllIIlIlIIllIII = lllIIlIlIIllIIl.length;
        int lllIIlIlIIlIlll = lllllI[0];
        while (SwordsmanSkill.llIllIlI(lllIIlIlIIlIlll, lllIIlIlIIllIII)) {
            char lllIIlIlIlIIlII = lllIIlIlIIllIIl[lllIIlIlIIlIlll];
            "".length();
            lllIIlIlIlIIIIl.append((char)(lllIIlIlIlIIlII ^ lllIIlIlIlIIIII[lllIIlIlIIlllll % lllIIlIlIlIIIII.length]));
            ++lllIIlIlIIlllll;
            ++lllIIlIlIIlIlll;
            "".length();
            if ("  ".length() <= (186 ^ 131 ^ (14 ^ 51))) continue;
            return null;
        }
        return String.valueOf(lllIIlIlIlIIIIl);
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    private static boolean llIlIIll(int n) {
        return n == 0;
    }

    private static void lIlIllll() {
        lllIII = new String[lllllI[7]];
        SwordsmanSkill.lllIII[SwordsmanSkill.lllllI[0]] = SwordsmanSkill.lIlIlIll("ggHun8auhl8kYvCAso9N7w==", "wOvGN");
        SwordsmanSkill.lllIII[SwordsmanSkill.lllllI[1]] = SwordsmanSkill.lIlIllII("", "OQzKz");
        SwordsmanSkill.lllIII[SwordsmanSkill.lllllI[2]] = SwordsmanSkill.lIlIllIl("+xA4MEuT4uFo1n2qD0dP28krHM4wJ4x3bUzcsVNBZ2JXcPmprENf8z+wzHf6VzbImbFM92L1JGA=", "sIakG");
        SwordsmanSkill.lllIII[SwordsmanSkill.lllllI[3]] = SwordsmanSkill.lIlIllII("a0VzGRwtGTYdFyAfMh4Gbh8yHRDCp0s/VxcjGzYeBi9LMgAeJwgyFBNuCnMcVSsFNh0bLUVzMQM7DiAEUisNNhMGK0s9Hx/CphhzA1UvGz8ZES9LMhwBbgonERE9SzAfAW4KcxMdPQ==", "NkSpr");
    }

    private static void llIIIlll() {
        lllllI = new int[9];
        SwordsmanSkill.lllllI[0] = (66 ^ 120) & ~(127 ^ 69);
        SwordsmanSkill.lllllI[1] = " ".length();
        SwordsmanSkill.lllllI[2] = "  ".length();
        SwordsmanSkill.lllllI[3] = "   ".length();
        SwordsmanSkill.lllllI[4] = 180 + 54 - 158 + 109 ^ 12 + 89 - -30 + 60;
        SwordsmanSkill.lllllI[5] = 164 + 207 - 267 + 116;
        SwordsmanSkill.lllllI[6] = 50 ^ 98;
        SwordsmanSkill.lllllI[7] = 33 ^ 116 ^ (14 ^ 95);
        SwordsmanSkill.lllllI[8] = 114 ^ 122;
    }

    public SwordsmanStatusEffect getAssociatedEffect() {
        SwordsmanStatusEffect lllIIlIlIllllll;
        SwordsmanSkill lllIIlIllIIIIII;
        Joc.PlayerInfo lllIIlIlIlllllI = lllIIlIllIIIIII.getPlayerInfo(lllIIlIllIIIIII.getPlayer());
        if (SwordsmanSkill.llIlIlIl((int)lllIIlIlIlllllI.hasStatusEffect(SwordsmanStatusEffect.class))) {
            SwordsmanStatusEffect lllIIlIllIIIIIl = lllIIlIlIlllllI.getStatusEffect(SwordsmanStatusEffect.class);
            "".length();
            if ((181 ^ 165 ^ (174 ^ 186)) < ((10 + 115 - 67 + 77 ^ 124 + 108 - 176 + 87) & (171 + 104 - 255 + 159 ^ 147 + 107 - 195 + 128 ^ -" ".length()))) {
                return null;
            }
        } else {
            lllIIlIlIllllll = lllIIlIllIIIIII.new SwordsmanStatusEffect(lllIIlIllIIIIII.getPlayer());
            lllIIlIlIllllll.setValue(1.0);
            lllIIlIlIlllllI.addStatusEffect(lllIIlIlIllllll);
        }
        return lllIIlIlIllllll;
    }

    private static String lIlIlIll(String lllIIlIlIIIllII, String lllIIlIlIIIllIl) {
        try {
            SecretKeySpec lllIIlIlIIlIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIlIlIIIllIl.getBytes(StandardCharsets.UTF_8)), lllllI[8]), "DES");
            Cipher lllIIlIlIIlIIII = Cipher.getInstance("DES");
            lllIIlIlIIlIIII.init(lllllI[2], lllIIlIlIIlIIIl);
            return new String(lllIIlIlIIlIIII.doFinal(Base64.getDecoder().decode(lllIIlIlIIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIlIIIllll) {
            lllIIlIlIIIllll.printStackTrace();
            return null;
        }
    }

    public SwordsmanSkill(Player lllIIllIIlIIlll) {
        SwordsmanSkill lllIIllIIlIIllI;
        super(lllIIllIIlIIlll);
        lllIIllIIlIIllI.stacks = lllllI[0];
    }

    public class SwordsmanStatusEffect
    extends StatusEffect {
        private static final /* synthetic */ int[] llllIll;
        private static final /* synthetic */ String[] llIIllI;

        private static void lIllIllII() {
            llIIllI = new String[llllIll[2]];
            SwordsmanStatusEffect.llIIllI[SwordsmanStatusEffect.llllIll[0]] = SwordsmanStatusEffect.lIllIlIIl("F1lli7JKlAD8xwmwJ4TKDQ==", "WeDmN");
            SwordsmanStatusEffect.llIIllI[SwordsmanStatusEffect.llllIll[1]] = SwordsmanStatusEffect.lIllIlIll("DcKUPDoWKQErO1M+ETxoAyEQKzpTKxIrKwc7FTxoBiBULScDbhUjKlMjFSJoEiodLSEcIBUi", "NtNHs");
        }

        static {
            SwordsmanStatusEffect.llIlIIlIl();
            SwordsmanStatusEffect.lIllIllII();
        }

        private static void llIlIIlIl() {
            llllIll = new int[3];
            SwordsmanStatusEffect.llllIll[0] = (58 + 113 - 66 + 44 ^ 151 + 133 - 233 + 120) & (170 + 159 - 193 + 40 ^ 65 + 2 - 6 + 81 ^ -" ".length());
            SwordsmanStatusEffect.llllIll[1] = " ".length();
            SwordsmanStatusEffect.llllIll[2] = "  ".length();
        }

        @Override
        public String getName() {
            return llIIllI[llllIll[0]];
        }

        @Override
        public String getDescription() {
            return llIIllI[llllIll[1]];
        }

        private static boolean llIlIIllI(int n, int n2) {
            return n < n2;
        }

        private static String lIllIlIIl(String llIIIIllIIlIIlI, String llIIIIllIIlIIIl) {
            try {
                SecretKeySpec llIIIIllIIlIlll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIIllIIlIIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher llIIIIllIIlIllI = Cipher.getInstance("Blowfish");
                llIIIIllIIlIllI.init(llllIll[2], llIIIIllIIlIlll);
                return new String(llIIIIllIIlIllI.doFinal(Base64.getDecoder().decode(llIIIIllIIlIIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIIIIllIIlIlIl) {
                llIIIIllIIlIlIl.printStackTrace();
                return null;
            }
        }

        public SwordsmanStatusEffect(Player llIIIIllIllIllI) {
            SwordsmanStatusEffect llIIIIllIlllIll;
            super(llIIIIllIllIllI);
            llIIIIllIlllIll.setType(StatusEffect.StatusEffectType.SKILL_TRAY);
            llIIIIllIlllIll.setMaxValue(6.0);
        }

        private static String lIllIlIll(String llIIIIllIlIIlII, String llIIIIllIlIlIII) {
            llIIIIllIlIIlII = new String(Base64.getDecoder().decode(llIIIIllIlIIlII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llIIIIllIlIIlll = new StringBuilder();
            char[] llIIIIllIlIIllI = llIIIIllIlIlIII.toCharArray();
            int llIIIIllIlIIlIl = llllIll[0];
            char[] llIIIIllIIlllll = llIIIIllIlIIlII.toCharArray();
            int llIIIIllIIllllI = llIIIIllIIlllll.length;
            int llIIIIllIIlllIl = llllIll[0];
            while (SwordsmanStatusEffect.llIlIIllI(llIIIIllIIlllIl, llIIIIllIIllllI)) {
                char llIIIIllIlIlIlI = llIIIIllIIlllll[llIIIIllIIlllIl];
                "".length();
                llIIIIllIlIIlll.append((char)(llIIIIllIlIlIlI ^ llIIIIllIlIIllI[llIIIIllIlIIlIl % llIIIIllIlIIllI.length]));
                ++llIIIIllIlIIlIl;
                ++llIIIIllIIlllIl;
                "".length();
                if ((176 ^ 180) >= "   ".length()) continue;
                return null;
            }
            return String.valueOf(llIIIIllIlIIlll);
        }
    }

}

