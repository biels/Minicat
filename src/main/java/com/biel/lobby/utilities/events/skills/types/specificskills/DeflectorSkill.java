/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities.events.skills.types.specificskills;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.AuraInfo;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DeflectorSkill
extends InherentSkill {
    private static final /* synthetic */ int[] lIIlIIll;
    private static final /* synthetic */ String[] lIIlIIIl;
    private /* synthetic */ int stacks;

    private static void lIIIIllIII() {
        lIIlIIIl = new String[lIIlIIll[8]];
        DeflectorSkill.lIIlIIIl[DeflectorSkill.lIIlIIll[0]] = DeflectorSkill.lIIIIlIIIl("Thw3CzcTAjR7bQ7KaXq5dg==", "zEtkL");
        DeflectorSkill.lIIlIIIl[DeflectorSkill.lIIlIIll[1]] = DeflectorSkill.lIIIIlIIIl("MM6OuAxrWvc=", "UROPA");
        DeflectorSkill.lIIlIIIl[DeflectorSkill.lIIlIIll[2]] = DeflectorSkill.lIIIIlIllI("VzSDb+8nePs=", "mhpBe");
        DeflectorSkill.lIIlIIIl[DeflectorSkill.lIIlIIll[3]] = DeflectorSkill.lIIIIlIllI("V0FLmNuY+Ck=", "MNOJT");
        DeflectorSkill.lIIlIIIl[DeflectorSkill.lIIlIIll[4]] = DeflectorSkill.lIIIIlIlll("PCwuAh4AKHoYAk4oLgwPTih6AUsLJz8ABQ1pOQwID2k=", "nIZml");
        DeflectorSkill.lIIlIIIl[DeflectorSkill.lIIlIIll[5]] = DeflectorSkill.lIIIIlIlll("NFcoIzErHi8nIiYZPWMtKFc8IA==", "GwINA");
        DeflectorSkill.lIIlIIIl[DeflectorSkill.lIIlIIll[6]] = DeflectorSkill.lIIIIlIIIl("TFH+Jqm4j/IFq03D5gcGWw==", "poNSi");
        DeflectorSkill.lIIlIIIl[DeflectorSkill.lIIlIIll[7]] = DeflectorSkill.lIIIIlIllI("bhj29MMXxuy/iin0JqjdXy8Yw+C/Q1uGzCtYR0TyN4wrV/8n20mRcoOBt9+QyXSN/PqANYwQBxAgF7En5EqQT0vTCywjql2H", "EhNuC");
    }

    @Override
    public void onCDUse() {
        DeflectorSkill lIllIIIIlIlllIl;
        super.onCDUse();
        lIllIIIIlIlllIl.getPlayerInfo().removeAura(lIllIIIIlIlllIl.getName());
    }

    protected void onPlayerDeathByPlayer(PlayerDeathEvent lIllIIIlIIIIIll, Player lIllIIIlIIIIIlI, Player lIllIIIlIIIIIIl) {
        DeflectorSkill lIllIIIlIIIIIII;
        super.onPlayerDeathByPlayer(lIllIIIlIIIIIll, lIllIIIlIIIIIlI, lIllIIIlIIIIIIl);
        if (DeflectorSkill.lIIIIllllI((Object)lIllIIIlIIIIIIl, (Object)lIllIIIlIIIIIII.getPlayer())) {
            lIllIIIlIIIIIII.skipCooldown();
        }
    }

    private static boolean lIIIlIIIII(int n) {
        return n != 0;
    }

    private static boolean lIIIIlllll(int n) {
        return n == 0;
    }

    @Override
    public double getCDSeconds() {
        return 35.0;
    }

    public double getDmgMultiplierBlk() {
        return 2.35;
    }

    public DeflectorSkill(Player lIllIIIlIlIIllI) {
        DeflectorSkill lIllIIIlIlIlIIl;
        super(lIllIIIlIlIIllI);
        lIllIIIlIlIlIIl.stacks = lIIlIIll[0];
        if (DeflectorSkill.lIIIIlllII((Object)lIllIIIlIlIIllI)) {
            // empty if block
        }
    }

    private static String lIIIIlIlll(String lIllIIIIlIIIlIl, String lIllIIIIIllllll) {
        lIllIIIIlIIIlIl = new String(Base64.getDecoder().decode(lIllIIIIlIIIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIllIIIIlIIIIll = new StringBuilder();
        char[] lIllIIIIlIIIIlI = lIllIIIIIllllll.toCharArray();
        int lIllIIIIlIIIIIl = lIIlIIll[0];
        char[] lIllIIIIIlllIll = lIllIIIIlIIIlIl.toCharArray();
        int lIllIIIIIlllIlI = lIllIIIIIlllIll.length;
        int lIllIIIIIlllIIl = lIIlIIll[0];
        while (DeflectorSkill.lIIIlIIIIl(lIllIIIIIlllIIl, lIllIIIIIlllIlI)) {
            char lIllIIIIlIIIllI = lIllIIIIIlllIll[lIllIIIIIlllIIl];
            "".length();
            lIllIIIIlIIIIll.append((char)(lIllIIIIlIIIllI ^ lIllIIIIlIIIIlI[lIllIIIIlIIIIIl % lIllIIIIlIIIIlI.length]));
            ++lIllIIIIlIIIIIl;
            ++lIllIIIIIlllIIl;
            "".length();
            if ("   ".length() >= "  ".length()) continue;
            return null;
        }
        return String.valueOf(lIllIIIIlIIIIll);
    }

    @Override
    public boolean usingAssociatedCDEffect() {
        return lIIlIIll[1];
    }

    static {
        DeflectorSkill.lIIIIllIll();
        DeflectorSkill.lIIIIllIII();
    }

    @Override
    public void tick() {
        DeflectorSkill lIllIIIlIIIlIIl;
        super.tick();
    }

    private static String lIIIIlIIIl(String lIllIIIIIlIlllI, String lIllIIIIIlIllll) {
        try {
            SecretKeySpec lIllIIIIIllIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIIIIIlIllll.getBytes(StandardCharsets.UTF_8)), lIIlIIll[8]), "DES");
            Cipher lIllIIIIIllIIlI = Cipher.getInstance("DES");
            lIllIIIIIllIIlI.init(lIIlIIll[2], lIllIIIIIllIIll);
            return new String(lIllIIIIIllIIlI.doFinal(Base64.getDecoder().decode(lIllIIIIIlIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIIIIIllIIIl) {
            lIllIIIIIllIIIl.printStackTrace();
            return null;
        }
    }

    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIllIIIIlllIIll, Player lIllIIIIllIlIlI, Player lIllIIIIlllIIIl, boolean lIllIIIIlllIIII) {
        double d;
        DeflectorSkill lIllIIIIllIllII;
        super.onPlayerDamageByPlayer(lIllIIIIlllIIll, lIllIIIIllIlIlI, lIllIIIIlllIIIl, lIllIIIIlllIIII);
        if (DeflectorSkill.lIIIIlllll((int)lIllIIIIllIllII.getPlayer().equals((Object)lIllIIIIllIlIlI))) {
            return;
        }
        if (DeflectorSkill.lIIIIlllll((int)lIllIIIIllIllII.tryUseCD())) {
            return;
        }
        if (DeflectorSkill.lIIIlIIIII((int)lIllIIIIlllIIIl.equals((Object)lIllIIIIllIlIlI))) {
            return;
        }
        lIllIIIIlllIIll.setCancelled(lIIlIIll[1]);
        int lIllIIIIllIllll = lIllIIIIllIllII.getPlayer().isBlocking();
        if (DeflectorSkill.lIIIlIIIII(lIllIIIIllIllll)) {
            d = lIllIIIIllIllII.getDmgMultiplierBlk();
            "".length();
            if (-" ".length() >= "   ".length()) {
                return;
            }
        } else {
            d = lIllIIIIllIllII.getDmgMultiplier();
        }
        lIllIIIIlllIIIl.damage(lIllIIIIlllIIll.getDamage() * d, (Entity)lIllIIIIllIlIlI);
        Vector lIllIIIIllIlllI = lIllIIIIllIlIlI.getLocation().toVector().subtract(lIllIIIIlllIIIl.getLocation().toVector());
        Vector lIllIIIIllIllIl = lIllIIIIllIlllI.normalize().multiply(-1.35).add(new Vector(0.0, 0.42, 0.0));
        lIllIIIIlllIIIl.setVelocity(lIllIIIIllIllIl);
        lIllIIIIllIllII.getWorld().playSound(lIllIIIIlllIIIl.getLocation(), Sound.BLOCK_METAL_HIT, 1.2f, 0.8f);
        lIllIIIIllIllII.getWorld().playEffect(lIllIIIIllIlIlI.getEyeLocation(), Effect.FIREWORKS_SPARK, (int)DyeColor.BLUE.getDyeData());
        lIllIIIIllIllII.getWorld().playEffect(lIllIIIIlllIIIl.getEyeLocation(), Effect.FIREWORKS_SPARK, (int)DyeColor.RED.getDyeData());
        if (DeflectorSkill.lIIIlIIIII(lIllIIIIllIllll)) {
            lIllIIIIllIllII.getWorld().playEffect(lIllIIIIlllIIIl.getEyeLocation(), Effect.MAGIC_CRIT, (int)DyeColor.RED.getDyeData());
        }
        if (DeflectorSkill.lIIIlIIIII(lIllIIIIllIllll)) {
            lIllIIIIllIllII.getWorld().playEffect(lIllIIIIllIlIlI.getEyeLocation(), Effect.MAGIC_CRIT, (int)DyeColor.RED.getDyeData());
        }
    }

    protected boolean getPlayerSpecificEventFiltering() {
        return lIIlIIll[0];
    }

    private static boolean lIIIIlllII(Object object) {
        return object == null;
    }

    public double getDmgMultiplier() {
        return 1.2;
    }

    @Override
    public String getName() {
        return lIIlIIIl[lIIlIIll[0]];
    }

    @Override
    public void onCDAvaliable() {
        DeflectorSkill lIllIIIIllIIIIl;
        super.onCDAvaliable();
        lIllIIIIllIIIIl.getPlayerInfo().addAura(new AuraInfo(lIllIIIIllIIIIl.getName(), lIIlIIll[4], lIllIIIIllIIIIl.getItemStack()));
    }

    private static String lIIIIlIllI(String lIllIIIIlIlIlIl, String lIllIIIIlIlIlII) {
        try {
            SecretKeySpec lIllIIIIlIllIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIIIIlIlIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIllIIIIlIlIlll = Cipher.getInstance("Blowfish");
            lIllIIIIlIlIlll.init(lIIlIIll[2], lIllIIIIlIllIII);
            return new String(lIllIIIIlIlIlll.doFinal(Base64.getDecoder().decode(lIllIIIIlIlIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIIIIlIlIllI) {
            lIllIIIIlIlIllI.printStackTrace();
            return null;
        }
    }

    private static boolean lIIIlIIIIl(int n, int n2) {
        return n < n2;
    }

    private static void lIIIIllIll() {
        lIIlIIll = new int[9];
        DeflectorSkill.lIIlIIll[0] = (202 ^ 144) & ~(108 ^ 54);
        DeflectorSkill.lIIlIIll[1] = " ".length();
        DeflectorSkill.lIIlIIll[2] = "  ".length();
        DeflectorSkill.lIIlIIll[3] = "   ".length();
        DeflectorSkill.lIIlIIll[4] = 110 ^ 65 ^ (99 ^ 72);
        DeflectorSkill.lIIlIIll[5] = 50 ^ 55;
        DeflectorSkill.lIIlIIll[6] = 9 ^ 15;
        DeflectorSkill.lIIlIIll[7] = 65 + 75 - 92 + 96 ^ 82 + 54 - 79 + 94;
        DeflectorSkill.lIIlIIll[8] = 87 ^ 95;
    }

    private void setStacks(int lIllIIIlIIIlllI) {
        lIllIIIlIIIllll.stacks = lIllIIIlIIIlllI;
    }

    private static boolean lIIIIllllI(Object object, Object object2) {
        return object == object2;
    }

    @Override
    public String getDescription() {
        DeflectorSkill lIllIIIlIIlllII;
        String lIllIIIlIIllIll = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIlIIIl[lIIlIIll[1]]).append(lIllIIIlIIlllII.getDmgMultiplier() * 100.0).append((Object)ChatColor.WHITE));
        String lIllIIIlIIllIlI = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIlIIIl[lIIlIIll[2]]).append(lIllIIIlIIlllII.getDmgMultiplierBlk() * 100.0).append((Object)ChatColor.WHITE));
        String lIllIIIlIIllIIl = String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIIlIIIl[lIIlIIll[3]]).append(lIllIIIlIIlllII.getCDSeconds()).append((Object)ChatColor.WHITE));
        return String.valueOf(new StringBuilder().append(lIIlIIIl[lIIlIIll[4]]).append(lIllIIIlIIllIIl).append(lIIlIIIl[lIIlIIll[5]]).append(lIllIIIlIIllIll).append(lIIlIIIl[lIIlIIll[6]]).append(lIllIIIlIIllIlI).append(lIIlIIIl[lIIlIIll[7]]));
    }

    private int getStacks() {
        DeflectorSkill lIllIIIlIIlIIlI;
        return lIllIIIlIIlIIlI.stacks;
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_TRAPDOOR;
    }
}

