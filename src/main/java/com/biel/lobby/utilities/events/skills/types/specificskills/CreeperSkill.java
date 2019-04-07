/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CreeperSkill
extends InherentSkill {
    private static final /* synthetic */ int[] llIllIIl;
    private static final /* synthetic */ String[] llIIIIIl;

    private static boolean llIlIIIIIl(int n, int n2) {
        return n < n2;
    }

    public CreeperSkill(Player lIIIlIllllIlIlI) {
        CreeperSkill lIIIlIllllIlIll;
        super(lIIIlIllllIlIlI);
    }

    private static boolean llIlIIIIII(int n) {
        return n > 0;
    }

    private static String lIllllIIII(String lIIIlIllIlIIIll, String lIIIlIllIlIIIlI) {
        try {
            SecretKeySpec lIIIlIllIlIIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIlIllIlIIIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIIlIllIlIIlIl = Cipher.getInstance("Blowfish");
            lIIIlIllIlIIlIl.init(llIllIIl[8], lIIIlIllIlIIllI);
            return new String(lIIIlIllIlIIlIl.doFinal(Base64.getDecoder().decode(lIIIlIllIlIIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIlIllIlIIlII) {
            lIIIlIllIlIIlII.printStackTrace();
            return null;
        }
    }

    @Override
    public String getName() {
        return llIIIIIl[llIllIIl[1]];
    }

    public double getProtectionRatio() {
        return 0.12;
    }

    public double getModifier() {
        return 2.25;
    }

    public void playEffect(Player lIIIlIlllIIIIIl) {
        int lIIIlIlllIIIlIl = llIllIIl[1];
        while (CreeperSkill.llIlIIIIIl(lIIIlIlllIIIlIl, llIllIIl[5])) {
            CreeperSkill lIIIlIlllIIIlII;
            lIIIlIlllIIIlII.getWorld().playEffect(lIIIlIlllIIIIIl.getEyeLocation().subtract(0.0, 0.9, 0.0), Effect.SNOWBALL_BREAK, Utils.NombreEntre(llIllIIl[1], llIllIIl[6]));
            ++lIIIlIlllIIIlIl;
            "".length();
            if ("  ".length() > " ".length()) continue;
            return;
        }
    }

    private static void llIIllllIl() {
        llIllIIl = new int[9];
        CreeperSkill.llIllIIl[0] = 122 ^ 126;
        CreeperSkill.llIllIIl[1] = (78 + 44 - 70 + 112 ^ 31 + 93 - 6 + 40) & (206 + 123 - 154 + 76 ^ 70 + 100 - 61 + 84 ^ -" ".length());
        CreeperSkill.llIllIIl[2] = " ".length();
        CreeperSkill.llIllIIl[3] = 3 + 149 - -10 + 13 ^ 128 + 67 - 139 + 109;
        CreeperSkill.llIllIIl[4] = 12 + 158 - 104 + 96 ^ 45 + 100 - 63 + 88;
        CreeperSkill.llIllIIl[5] = 22 ^ 26;
        CreeperSkill.llIllIIl[6] = 171 ^ 172;
        CreeperSkill.llIllIIl[7] = 98 ^ 30 ^ (89 ^ 13);
        CreeperSkill.llIllIIl[8] = "  ".length();
    }

    private static int llIIlllllI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean llIIllllll(Object object, Object object2) {
        return object != object2;
    }

    @Override
    public String getDescription() {
        return llIIIIIl[llIllIIl[2]];
    }

    private static void lIllllIllI() {
        llIIIIIl = new String[llIllIIl[8]];
        CreeperSkill.llIIIIIl[CreeperSkill.llIllIIl[1]] = CreeperSkill.lIllllIIII("gbWFxL1ln+I=", "JHOPW");
        CreeperSkill.llIIIIIl[CreeperSkill.llIllIIl[2]] = CreeperSkill.lIllllIIlI("xHRhIEoUcyVsSUy3St67nYzb3ZMqQ7xzUiamMwMS46SyEpjiwnWXEptm04/iTsdV", "AqrvJ");
    }

    @Override
    public double getCDSeconds() {
        return 0.0;
    }

    private static String lIllllIIlI(String lIIIlIllIIlIllI, String lIIIlIllIIlIlIl) {
        try {
            SecretKeySpec lIIIlIllIIllIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIlIllIIlIlIl.getBytes(StandardCharsets.UTF_8)), llIllIIl[4]), "DES");
            Cipher lIIIlIllIIllIII = Cipher.getInstance("DES");
            lIIIlIllIIllIII.init(llIllIIl[8], lIIIlIllIIllIIl);
            return new String(lIIIlIllIIllIII.doFinal(Base64.getDecoder().decode(lIIIlIllIIlIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIlIllIIlIlll) {
            lIIIlIllIIlIlll.printStackTrace();
            return null;
        }
    }

    @Override
    public Byte getData() {
        return llIllIIl[0];
    }

    static {
        CreeperSkill.llIIllllIl();
        CreeperSkill.lIllllIllI();
    }

    protected void onPlayerDeath(PlayerDeathEvent lIIIlIlllIIllll, Player lIIIlIlllIlIllI) {
        CreeperSkill lIIIlIlllIllIII;
        super.onPlayerDeath(lIIIlIlllIIllll, lIIIlIlllIlIllI);
        if (CreeperSkill.llIIllllll((Object)lIIIlIlllIlIllI, (Object)lIIIlIlllIllIII.getPlayer())) {
            return;
        }
        if (CreeperSkill.llIlIIIIII(CreeperSkill.llIIlllllI(lIIIlIlllIllIII.getPlayer().getHealth(), 0.0))) {
            return;
        }
        int lIIIlIlllIlIlIl = llIllIIl[3];
        Location lIIIlIlllIlIlII = lIIIlIlllIlIllI.getEyeLocation();
        ArrayList lIIIlIlllIlIIll = GUtils.getNearbyPlayers((Location)lIIIlIlllIlIlII, (double)lIIIlIlllIlIlIl);
        lIIIlIlllIllIII.getWorld().playEffect(lIIIlIlllIlIlII, Effect.EXPLOSION_HUGE, llIllIIl[4]);
        lIIIlIlllIllIII.getWorld().playSound(lIIIlIlllIlIlII, Sound.ENTITY_GENERIC_EXPLODE, 0.8f, 1.0f);
        lIIIlIlllIllIII.getWorld().playSound(lIIIlIlllIlIlII, Sound.ENTITY_CREEPER_PRIMED, 0.8f, 1.0f);
        Consumer<Player> lIIIlIlllIlIIlI = lIIIlIllIlIlllI -> lIIIlIllIlIlllI.damage(2.0 + 6.5 / lIIIlIlllIlIlII.distance(lIIIlIllIlIlllI.getEyeLocation()), (Entity)lIIIlIlllIlIllI);
        Predicate<Player> lIIIlIlllIlIIIl = lIIIlIllIllIlII -> {
            CreeperSkill lIIIlIllIllIllI;
            return lIIIlIllIllIllI.getGame().areEnemies(lIIIlIlllIlIllI, (Player)lIIIlIllIllIlII);
        };
        lIIIlIlllIlIIll.stream().filter(lIIIlIlllIlIIIl).forEach(lIIIlIlllIlIIlI.andThen(lIIIlIllIlllllI -> ((Entity)lIIIlIllIlllllI).setFireTicks(llIllIIl[7])));
    }

    @Override
    public Material getMaterial() {
        return Material.SKULL_ITEM;
    }
}

