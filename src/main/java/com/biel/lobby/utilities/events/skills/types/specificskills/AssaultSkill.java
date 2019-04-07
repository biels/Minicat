/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

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
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class AssaultSkill
extends InherentSkill {
    private static final /* synthetic */ int[] lIllIlIll;
    private static final /* synthetic */ String[] lIllIlIII;

    public AssaultSkill(Player lIIlIIlIllIIlI) {
        AssaultSkill lIIlIIlIllIIll;
        super(lIIlIIlIllIIlI);
    }

    private static boolean lIlIIllllll(Object object, Object object2) {
        return object != object2;
    }

    @Override
    public String getDescription() {
        String lIIlIIlIlIlIlI = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIllIlIII[lIllIlIll[1]]).append(AssaultSkill.getModifier()).append((Object)ChatColor.WHITE));
        Double lIIlIIlIlIlIIl = (double)Math.round(AssaultSkill.getProtectionRatio() * 100.0 * 10.0) / 10.0;
        String lIIlIIlIlIlIII = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIllIlIII[lIllIlIll[2]]).append(lIIlIIlIlIlIIl).append((Object)ChatColor.WHITE).append(lIllIlIII[lIllIlIll[3]]));
        return String.valueOf(new StringBuilder().append(lIllIlIII[lIllIlIll[4]]).append(lIIlIIlIlIlIlI).append(lIllIlIII[lIllIlIll[5]]).append(lIIlIIlIlIlIII));
    }

    private static boolean lIlIlIIIIII(Object object, Object object2) {
        return object == object2;
    }

    protected void onPlayerDamage(EntityDamageEvent lIIlIIlIIlIlII, Player lIIlIIlIIlIlll) {
        AssaultSkill lIIlIIlIIllIIl;
        super.onPlayerDamage(lIIlIIlIIlIlII, lIIlIIlIIlIlll);
        EntityDamageEvent.DamageCause lIIlIIlIIlIllI = lIIlIIlIIlIlII.getCause();
        if (AssaultSkill.lIlIIllllll((Object)lIIlIIlIIlIlll, (Object)lIIlIIlIIllIIl.getPlayer())) {
            return;
        }
        if (AssaultSkill.lIlIlIIIIII((Object)lIIlIIlIIlIllI, (Object)EntityDamageEvent.DamageCause.FALL)) {
            ArrayList<LivingEntity> lIIlIIlIIllIll = Utils.getNearbyEnemies((LivingEntity)lIIlIIlIIlIlll, AssaultSkill.getModifier(), lIllIlIll[0]);
            Boolean lIIlIIlIIllIlI = lIllIlIll[0];
            Iterator<LivingEntity> lIIlIIlIIIllll = lIIlIIlIIllIll.iterator();
            while (AssaultSkill.lIlIlIIIIIl((int)lIIlIIlIIIllll.hasNext())) {
                LivingEntity lIIlIIlIIlllII = lIIlIIlIIIllll.next();
                if (AssaultSkill.lIlIlIIIIIl(lIIlIIlIIlllII instanceof Player) && AssaultSkill.lIlIlIIIIIl((int)lIIlIIlIIllIIl.getGame().areAllies((Player)lIIlIIlIIlllII, lIIlIIlIIllIIl.getPlayer()).booleanValue())) {
                    "".length();
                    if ("   ".length() > -" ".length()) continue;
                    return;
                }
                lIIlIIlIIlllII.damage(lIIlIIlIIlIlII.getDamage() * (1.0 + AssaultSkill.getProtectionRatio()), (Entity)lIIlIIlIIlIlll);
                lIIlIIlIIlllII.setVelocity(new Vector(0.0, 1.25, 0.0));
                lIIlIIlIIlIlll.getWorld().playEffect(lIIlIIlIIlllII.getLocation().add(0.5, 0.0, 0.5), Effect.SMOKE, lIllIlIll[4]);
                lIIlIIlIIllIIl.getWorld().playEffect(lIIlIIlIIlIlll.getLocation().add(0.2, 0.3, 0.2), Effect.MAGIC_CRIT, lIllIlIll[0]);
                lIIlIIlIIllIIl.getWorld().playEffect(lIIlIIlIIlIlll.getLocation().add(0.2, 0.3, 0.2), Effect.MAGIC_CRIT, lIllIlIll[0]);
                lIIlIIlIIllIlI = lIllIlIll[1];
                "".length();
                if ("   ".length() != "  ".length()) continue;
                return;
            }
            if (AssaultSkill.lIlIlIIIIIl((int)lIIlIIlIIllIlI.booleanValue())) {
                lIIlIIlIIlIlII.setCancelled(lIllIlIll[1]);
            }
        }
    }

    private static Double getProtectionRatio() {
        return 0.185;
    }

    @Override
    public String getName() {
        return lIllIlIII[lIllIlIll[0]];
    }

    static {
        AssaultSkill.lIlIIlllllI();
        AssaultSkill.lIlIIIlIlIl();
    }

    private static void lIlIIIlIlIl() {
        lIllIlIII = new String[lIllIlIll[6]];
        AssaultSkill.lIllIlIII[AssaultSkill.lIllIlIll[0]] = AssaultSkill.lIlIIIIllll("h81EdafrrMU=", "NeBxI");
        AssaultSkill.lIllIlIII[AssaultSkill.lIllIlIll[1]] = AssaultSkill.lIlIIIlIIlI("XwCu0ZM2b5w=", "SPTGV");
        AssaultSkill.lIllIlIII[AssaultSkill.lIllIlIll[2]] = AssaultSkill.lIlIIIlIIlI("ud5PPs1S298=", "SzZtu");
        AssaultSkill.lIllIlIII[AssaultSkill.lIllIlIll[3]] = AssaultSkill.lIlIIIIllll("ua1hSTGvmnY=", "lGsHX");
        AssaultSkill.lIllIlIII[AssaultSkill.lIllIlIll[4]] = AssaultSkill.lIlIIIlIIlI("9EC/YkmITGorK4eFQwi+LMkOJRMEnSPIPb5FH9nsY8Hc/JIWyLDhgIL8BfmWv6DEsakHmKhNIQM=", "qLdjY");
        AssaultSkill.lIllIlIII[AssaultSkill.lIllIlIll[5]] = AssaultSkill.lIlIIIlIIlI("uppqpmT/4qoBxF3tm2azPfvHkuam4IaGhKp3EpwoC9Y=", "LcsCe");
    }

    private static void lIlIIlllllI() {
        lIllIlIll = new int[8];
        AssaultSkill.lIllIlIll[0] = (55 ^ 108 ^ (82 ^ 62)) & (123 ^ 7 ^ (94 ^ 21) ^ -" ".length());
        AssaultSkill.lIllIlIll[1] = " ".length();
        AssaultSkill.lIllIlIll[2] = "  ".length();
        AssaultSkill.lIllIlIll[3] = "   ".length();
        AssaultSkill.lIllIlIll[4] = 28 + 131 - 24 + 49 ^ 112 + 128 - 140 + 88;
        AssaultSkill.lIllIlIll[5] = 116 ^ 113;
        AssaultSkill.lIllIlIll[6] = 72 ^ 78;
        AssaultSkill.lIllIlIll[7] = 140 ^ 132;
    }

    private static double getModifier() {
        return 7.0;
    }

    private static String lIlIIIIllll(String lIIlIIIllllIIl, String lIIlIIIlllIllI) {
        try {
            SecretKeySpec lIIlIIIlllllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlIIIlllIllI.getBytes(StandardCharsets.UTF_8)), lIllIlIll[7]), "DES");
            Cipher lIIlIIIllllIll = Cipher.getInstance("DES");
            lIIlIIIllllIll.init(lIllIlIll[2], lIIlIIIlllllII);
            return new String(lIIlIIIllllIll.doFinal(Base64.getDecoder().decode(lIIlIIIllllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIIIllllIlI) {
            lIIlIIIllllIlI.printStackTrace();
            return null;
        }
    }

    private static String lIlIIIlIIlI(String lIIlIIlIIIIlII, String lIIlIIlIIIIlIl) {
        try {
            SecretKeySpec lIIlIIlIIIlIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIlIIlIIIIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIlIIlIIIlIII = Cipher.getInstance("Blowfish");
            lIIlIIlIIIlIII.init(lIllIlIll[2], lIIlIIlIIIlIIl);
            return new String(lIIlIIlIIIlIII.doFinal(Base64.getDecoder().decode(lIIlIIlIIIIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIlIIlIIIIlll) {
            lIIlIIlIIIIlll.printStackTrace();
            return null;
        }
    }

    @Override
    public double getCDSeconds() {
        return 8.0;
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_BOOTS;
    }

    private static boolean lIlIlIIIIIl(int n) {
        return n != 0;
    }
}

