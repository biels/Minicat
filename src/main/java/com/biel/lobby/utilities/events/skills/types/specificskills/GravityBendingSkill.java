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
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.Utils;
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
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class GravityBendingSkill
extends InherentSkill {
    private static final /* synthetic */ int[] lIlllll;
    private static final /* synthetic */ String[] lIllIIl;

    private static boolean lIlIllIIl(int n) {
        return n != 0;
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    private static void lIlIlIlll() {
        lIlllll = new int[9];
        GravityBendingSkill.lIlllll[0] = (47 + 83 - 4 + 23 ^ 30 + 41 - -45 + 30) & (107 + 82 - 112 + 62 ^ 74 + 33 - 12 + 45 ^ -" ".length());
        GravityBendingSkill.lIlllll[1] = " ".length();
        GravityBendingSkill.lIlllll[2] = "  ".length();
        GravityBendingSkill.lIlllll[3] = "   ".length();
        GravityBendingSkill.lIlllll[4] = 195 ^ 199;
        GravityBendingSkill.lIlllll[5] = 149 ^ 198 ^ (60 ^ 106);
        GravityBendingSkill.lIlllll[6] = 186 ^ 182;
        GravityBendingSkill.lIlllll[7] = 69 ^ 67;
        GravityBendingSkill.lIlllll[8] = 177 ^ 185;
    }

    public GravityBendingSkill(Player llIIllIIllIIIlI) {
        GravityBendingSkill llIIllIIllIIlIl;
        super(llIIllIIllIIIlI);
    }

    @Override
    public Material getMaterial() {
        return Material.GOLD_BOOTS;
    }

    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent llIIllIIlIIIIII, Player llIIllIIlIIIlII, Player llIIllIIlIIIIll, boolean llIIllIIIllllIl) {
        GravityBendingSkill llIIllIIlIIIllI;
        super.onPlayerDamageByPlayer(llIIllIIlIIIIII, llIIllIIlIIIlII, llIIllIIlIIIIll, llIIllIIIllllIl);
        if (GravityBendingSkill.lIlIllIII((Object)llIIllIIlIIIIll, (Object)llIIllIIlIIIllI.getPlayer())) {
            GravityStatusEffect llIIllIIlIIIlll;
            Joc.PlayerInfo llIIllIIlIIlIII = llIIllIIlIIIllI.getGame().getPlayerInfo(llIIllIIlIIIlII);
            if (GravityBendingSkill.lIlIllIIl((int)llIIllIIlIIlIII.hasStatusEffect(GravityStatusEffect.class))) {
                GravityStatusEffect llIIllIIlIIlIIl = llIIllIIlIIlIII.getStatusEffect(GravityStatusEffect.class);
                "".length();
                if (-"  ".length() > 0) {
                    return;
                }
            } else {
                llIIllIIlIIIlll = llIIllIIlIIIllI.new GravityStatusEffect(llIIllIIlIIIlII);
                llIIllIIlIIlIII.addStatusEffect(llIIllIIlIIIlll);
            }
            llIIllIIlIIIlll.setOwnerPlayer(llIIllIIlIIIIll);
            llIIllIIlIIIlll.setRemainingTicks((int)(20.0 * llIIllIIlIIIllI.getModifier()));
        }
    }

    private static String lIlIIIlII(String llIIllIIIIlIlII, String llIIllIIIIlIlIl) {
        try {
            SecretKeySpec llIIllIIIIllIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIllIIIIlIlIl.getBytes(StandardCharsets.UTF_8)), lIlllll[8]), "DES");
            Cipher llIIllIIIIllIII = Cipher.getInstance("DES");
            llIIllIIIIllIII.init(lIlllll[2], llIIllIIIIllIIl);
            return new String(llIIllIIIIllIII.doFinal(Base64.getDecoder().decode(llIIllIIIIlIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIllIIIIlIlll) {
            llIIllIIIIlIlll.printStackTrace();
            return null;
        }
    }

    @Override
    public String getName() {
        return lIllIIl[lIlllll[0]];
    }

    public double getModifier() {
        return 12.0;
    }

    private static boolean lIlIllIlI(int n, int n2) {
        return n < n2;
    }

    private static String lIlIIIIll(String llIIllIIIlIIllI, String llIIllIIIlIlIlI) {
        llIIllIIIlIIllI = new String(Base64.getDecoder().decode(llIIllIIIlIIllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIllIIIlIlIIl = new StringBuilder();
        char[] llIIllIIIlIlIII = llIIllIIIlIlIlI.toCharArray();
        int llIIllIIIlIIlll = lIlllll[0];
        char[] llIIllIIIlIIIIl = llIIllIIIlIIllI.toCharArray();
        int llIIllIIIlIIIII = llIIllIIIlIIIIl.length;
        int llIIllIIIIlllll = lIlllll[0];
        while (GravityBendingSkill.lIlIllIlI(llIIllIIIIlllll, llIIllIIIlIIIII)) {
            char llIIllIIIlIllII = llIIllIIIlIIIIl[llIIllIIIIlllll];
            "".length();
            llIIllIIIlIlIIl.append((char)(llIIllIIIlIllII ^ llIIllIIIlIlIII[llIIllIIIlIIlll % llIIllIIIlIlIII.length]));
            ++llIIllIIIlIIlll;
            ++llIIllIIIIlllll;
            "".length();
            if (-" ".length() != "   ".length()) continue;
            return null;
        }
        return String.valueOf(llIIllIIIlIlIIl);
    }

    public void playEffect(Player llIIllIIIllIlll) {
        int llIIllIIIlllIIl = lIlllll[0];
        while (GravityBendingSkill.lIlIllIlI(llIIllIIIlllIIl, lIlllll[6])) {
            ++llIIllIIIlllIIl;
            "".length();
            if ("  ".length() != ((55 ^ 102) & ~(95 ^ 14))) continue;
            return;
        }
    }

    public double getProtectionRatio() {
        return 0.12;
    }

    private static void lIlIIIlIl() {
        lIllIIl = new String[lIlllll[7]];
        GravityBendingSkill.lIllIIl[GravityBendingSkill.lIlllll[0]] = GravityBendingSkill.lIlIIIIll("ICgpEwoMK2cDHUMrJkcfESYxAgwCMw==", "cGGgx");
        GravityBendingSkill.lIllIIl[GravityBendingSkill.lIlllll[1]] = GravityBendingSkill.lIlIIIlII("cfpb2YtZHk0=", "xoRwr");
        GravityBendingSkill.lIllIIl[GravityBendingSkill.lIlllll[2]] = GravityBendingSkill.lIlIIIIll("", "dkxrq");
        GravityBendingSkill.lIllIIl[GravityBendingSkill.lIlllll[3]] = GravityBendingSkill.lIlIIIIll("Uw==", "vYccT");
        GravityBendingSkill.lIllIIl[GravityBendingSkill.lIlllll[4]] = GravityBendingSkill.lIlIIIIll("BT0ZAxchOBQOBjVoHQtFOSkURwExaBsGDDM9HAZFJT0dRxcxKh0JRTEkC0cAOi0VDgYnaBkTBDcpDBRFMSZYEgt0IRYTACY+GQtFMC1Y", "THxge");
        GravityBendingSkill.lIllIIl[GravityBendingSkill.lIlllll[5]] = GravityBendingSkill.lIlIIIIll("J3tENx02NgoxUiA2CjETdCENIRN0NAsoUjk2CGUXLCMWJFIyNgcs", "TWdEr");
    }

    private static boolean lIlIllIII(Object object, Object object2) {
        return object == object2;
    }

    @Override
    public String getDescription() {
        GravityBendingSkill llIIllIIlIlIlII;
        String llIIllIIlIlIlll = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIllIIl[lIlllll[1]]).append(llIIllIIlIlIlII.getModifier()).append((Object)ChatColor.WHITE));
        Double llIIllIIlIlIllI = (double)Math.round(llIIllIIlIlIlII.getProtectionRatio() * 100.0 * 10.0) / 10.0;
        String llIIllIIlIlIlIl = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIllIIl[lIlllll[2]]).append(llIIllIIlIlIllI).append((Object)ChatColor.WHITE).append(lIllIIl[lIlllll[3]]));
        return String.valueOf(new StringBuilder().append(lIllIIl[lIlllll[4]]).append(llIIllIIlIlIlll).append(lIllIIl[lIlllll[5]]));
    }

    static {
        GravityBendingSkill.lIlIlIlll();
        GravityBendingSkill.lIlIIIlIl();
    }

    public class GravityStatusEffect
    extends StatusEffect {
        private static final /* synthetic */ String[] llllIIll;
        private static final /* synthetic */ int[] llllIlIl;

        @Override
        public String getDescription() {
            return llllIIll[llllIlIl[1]];
        }

        private static void lllIlIIIIl() {
            llllIIll = new String[llllIlIl[6]];
            GravityStatusEffect.llllIIll[GravityStatusEffect.llllIlIl[0]] = GravityStatusEffect.lllIIllIIl("KQgEHDYaGxFKK10=", "nzejS");
            GravityStatusEffect.llllIIll[GravityStatusEffect.llllIlIl[1]] = GravityStatusEffect.lllIIllIlI("H07OOqIKOKz4pG2NKWwohwyKrImZDaIm2gprYzPaC6w=", "ZVbwp");
            GravityStatusEffect.llllIIll[GravityStatusEffect.llllIlIl[5]] = GravityStatusEffect.lllIIlllll("AEhAAQSaevOcijZWkSZ2K7BSpPQ/sBUz", "kNPCZ");
        }

        public GravityStatusEffect(Player lIIIIIIlIIIIlIl) {
            GravityStatusEffect lIIIIIIlIIIIlII;
            super(lIIIIIIlIIIIlIl);
            lIIIIIIlIIIIlII.setType(StatusEffect.StatusEffectType.DEBUFF);
        }

        static {
            GravityStatusEffect.lllIlIIIlI();
            GravityStatusEffect.lllIlIIIIl();
        }

        private static String lllIIllIIl(String lIIIIIIIllIIIll, String lIIIIIIIllIIIlI) {
            lIIIIIIIllIIIll = new String(Base64.getDecoder().decode(lIIIIIIIllIIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIIIIIIllIIIIl = new StringBuilder();
            char[] lIIIIIIIllIIIII = lIIIIIIIllIIIlI.toCharArray();
            int lIIIIIIIlIlllll = llllIlIl[0];
            char[] lIIIIIIIlIllIIl = lIIIIIIIllIIIll.toCharArray();
            int lIIIIIIIlIllIII = lIIIIIIIlIllIIl.length;
            int lIIIIIIIlIlIlll = llllIlIl[0];
            while (GravityStatusEffect.lllIlIIlIl(lIIIIIIIlIlIlll, lIIIIIIIlIllIII)) {
                char lIIIIIIIllIIlII = lIIIIIIIlIllIIl[lIIIIIIIlIlIlll];
                "".length();
                lIIIIIIIllIIIIl.append((char)(lIIIIIIIllIIlII ^ lIIIIIIIllIIIII[lIIIIIIIlIlllll % lIIIIIIIllIIIII.length]));
                ++lIIIIIIIlIlllll;
                ++lIIIIIIIlIlIlll;
                "".length();
                if (null == null) continue;
                return null;
            }
            return String.valueOf(lIIIIIIIllIIIIl);
        }

        protected void onPlayerDamage(EntityDamageEvent lIIIIIIIlllIlIl, Player lIIIIIIIlllIIIl) {
            GravityStatusEffect lIIIIIIIlllIllI;
            super.onPlayerDamage(lIIIIIIIlllIlIl, lIIIIIIIlllIIIl);
            if (GravityStatusEffect.lllIlIIIll((Object)lIIIIIIIlllIlIl.getCause(), (Object)EntityDamageEvent.DamageCause.FALL) && GravityStatusEffect.lllIlIIIll((Object)lIIIIIIIlllIIIl, (Object)lIIIIIIIlllIllI.getPlayer())) {
                double lIIIIIIIllllIIl = lIIIIIIIlllIlIl.getDamage() + 1.0;
                lIIIIIIIlllIIIl.damage(lIIIIIIIllllIIl * 3.0, (Entity)lIIIIIIIlllIllI.getOwnerPlayer());
                Utils.healDamageable((Damageable)lIIIIIIIlllIllI.getOwnerPlayer(), lIIIIIIIllllIIl * 1.1);
                lIIIIIIIlllIllI.getWorld().playEffect(lIIIIIIIlllIllI.getOwnerPlayer().getEyeLocation().add(0.0, -0.8, 0.0), Effect.HAPPY_VILLAGER, llllIlIl[0]);
                Location lIIIIIIIllllIII = lIIIIIIIlllIIIl.getLocation();
                int lIIIIIIIlllIlll = Utils.NombreEntre(llllIlIl[2], llllIlIl[3]);
                lIIIIIIIlllIllI.getWorld().playSound(lIIIIIIIllllIII, Sound.ENTITY_PLAYER_SMALL_FALL, 3.0f, 1.0f);
                while (GravityStatusEffect.lllIlIIlII(lIIIIIIIlllIlll)) {
                    lIIIIIIIlllIllI.getWorld().playEffect(lIIIIIIIllllIII, Effect.SMOKE, Utils.NombreEntre(llllIlIl[0], llllIlIl[4]));
                    lIIIIIIIlllIlll -= llllIlIl[1];
                    "".length();
                    if (null == null) continue;
                    return;
                }
                lIIIIIIIlllIllI.sendEffectMessage(llllIIll[llllIlIl[5]]);
            }
        }

        @Override
        public String getName() {
            return llllIIll[llllIlIl[0]];
        }

        private static boolean lllIlIIlIl(int n, int n2) {
            return n < n2;
        }

        private static String lllIIllIlI(String lIIIIIIIlIIllII, String lIIIIIIIlIIlIll) {
            try {
                SecretKeySpec lIIIIIIIlIlIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIIIIIlIIlIll.getBytes(StandardCharsets.UTF_8)), llllIlIl[4]), "DES");
                Cipher lIIIIIIIlIlIIII = Cipher.getInstance("DES");
                lIIIIIIIlIlIIII.init(llllIlIl[5], lIIIIIIIlIlIIIl);
                return new String(lIIIIIIIlIlIIII.doFinal(Base64.getDecoder().decode(lIIIIIIIlIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIIIIIlIIllll) {
                lIIIIIIIlIIllll.printStackTrace();
                return null;
            }
        }

        private static boolean lllIlIIlII(int n) {
            return n >= 0;
        }

        private static String lllIIlllll(String lIIIIIIIIllllll, String lIIIIIIIIlllllI) {
            try {
                SecretKeySpec lIIIIIIIlIIIlII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIIIIIIlllllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIIIIIIlIIIIll = Cipher.getInstance("Blowfish");
                lIIIIIIIlIIIIll.init(llllIlIl[5], lIIIIIIIlIIIlII);
                return new String(lIIIIIIIlIIIIll.doFinal(Base64.getDecoder().decode(lIIIIIIIIllllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIIIIIlIIIIlI) {
                lIIIIIIIlIIIIlI.printStackTrace();
                return null;
            }
        }

        private static boolean lllIlIIIll(Object object, Object object2) {
            return object == object2;
        }

        private static void lllIlIIIlI() {
            llllIlIl = new int[7];
            GravityStatusEffect.llllIlIl[0] = (10 ^ 26 ^ (120 ^ 102)) & (41 + 136 - 157 + 127 ^ 83 + 63 - -8 + 3 ^ -" ".length());
            GravityStatusEffect.llllIlIl[1] = " ".length();
            GravityStatusEffect.llllIlIl[2] = 26 ^ 31;
            GravityStatusEffect.llllIlIl[3] = 75 ^ 47 ^ (120 ^ 22);
            GravityStatusEffect.llllIlIl[4] = 49 ^ 57;
            GravityStatusEffect.llllIlIl[5] = "  ".length();
            GravityStatusEffect.llllIlIl[6] = "   ".length();
        }
    }

}

