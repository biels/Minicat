/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.Color
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Damageable
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocScoreRace;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PilotaSplash
extends JocScoreRace {
    private static final /* synthetic */ String[] lIIllllll;
    private static final /* synthetic */ int[] lIlIIlIIl;

    @Override
    protected int getFinishScore() {
        PilotaSplash llIIIIllllllIl;
        return lIlIIlIIl[0] + llIIIIllllllIl.getPlayers().size() * lIlIIlIIl[1];
    }

    private static int lIIlIlIllII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    public void clearExternals() {
        PilotaSplash llIIIIIIllllII;
        super.clearExternals();
    }

    private static String lIIIlllllII(String llIIIIIIIlIlIl, String llIIIIIIIlIlII) {
        try {
            SecretKeySpec llIIIIIIIllIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIIIIIlIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIIIIIIlIlll = Cipher.getInstance("Blowfish");
            llIIIIIIIlIlll.init(lIlIIlIIl[1], llIIIIIIIllIII);
            return new String(llIIIIIIIlIlll.doFinal(Base64.getDecoder().decode(llIIIIIIIlIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIIIIIlIllI) {
            llIIIIIIIlIllI.printStackTrace();
            return null;
        }
    }

    @Override
    public void JocIniciat() {
        PilotaSplash llIIIIlllllIIl;
        super.JocIniciat();
        llIIIIlllllIIl.applyDisguises();
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent llIIIIIllIIIll, Player llIIIIIllIIIIl, Player llIIIIIllIIlIl) {
        PilotaSplash llIIIIIllIlIIl;
        super.onPlayerDeathByPlayer(llIIIIIllIIIll, llIIIIIllIIIIl, llIIIIIllIIlIl);
        llIIIIIllIlIIl.incrementScore(llIIIIIllIIlIl);
        GUtils.healDamageable((Damageable)llIIIIIllIIlIl, (Double)3.0);
    }

    private static void lIIlIlIlIlI() {
        lIlIIlIIl = new int[9];
        PilotaSplash.lIlIIlIIl[0] = 231 ^ 191 ^ (98 ^ 50);
        PilotaSplash.lIlIIlIIl[1] = "  ".length();
        PilotaSplash.lIlIIlIIl[2] = (100 ^ 57 ^ (60 ^ 91)) & (226 ^ 143 ^ (69 ^ 18) ^ -" ".length());
        PilotaSplash.lIlIIlIIl[3] = -1 & Integer.MAX_VALUE;
        PilotaSplash.lIlIIlIIl[4] = 26 ^ 30;
        PilotaSplash.lIlIIlIIl[5] = " ".length();
        PilotaSplash.lIlIIlIIl[6] = "   ".length();
        PilotaSplash.lIlIIlIIl[7] = -" ".length();
        PilotaSplash.lIlIIlIIl[8] = 247 ^ 175 ^ (29 ^ 64);
    }

    protected void onPlayerInteract(PlayerInteractEvent llIIIIIlllIlII, Player llIIIIIlllIllI) {
        PilotaSplash llIIIIIllllIIl;
        super.onPlayerInteract(llIIIIIlllIlII, llIIIIIlllIllI);
        if (PilotaSplash.lIIlIlIlIll((int)llIIIIIlllIllI.getItemInHand().equals((Object)llIIIIIllllIIl.getMagnusLauncherItem()))) {
            llIIIIIlllIllI.setVelocity(llIIIIIlllIllI.getLocation().getDirection().multiply(1.15).add(new Vector(0.0, 0.25, 0.0)));
            llIIIIIlllIllI.playSound(llIIIIIlllIllI.getEyeLocation(), Sound.BLOCK_SAND_STEP, 1.0f, 0.9f);
        }
    }

    @Override
    protected void teletransportarTothom() {
        PilotaSplash llIIIIllIIlIlI;
        Iterator<Player> llIIIIllIIlIII = llIIIIllIIlIlI.getPlayers().iterator();
        while (PilotaSplash.lIIlIlIlIll((int)llIIIIllIIlIII.hasNext())) {
            Player llIIIIllIIlIll = llIIIIllIIlIII.next();
            llIIIIllIIlIlI.teleportToRandomSpawn(llIIIIllIIlIll);
            "".length();
            if (-"  ".length() < 0) continue;
            return;
        }
    }

    private static boolean lIIlIlIllIl(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected void donarEfectesInicials(Player llIIIIllllIIll) {
        PilotaSplash llIIIIllllIlII;
        super.donarEfectesInicials(llIIIIllllIIll);
        "".length();
        llIIIIllllIIll.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, lIlIIlIIl[3], lIlIIlIIl[4], lIlIIlIIl[5]), lIlIIlIIl[5]);
    }

    private static boolean lIIlIllIIII(int n) {
        return n < 0;
    }

    private static String lIIIllllllI(String llIIIIIIlIIIII, String llIIIIIIIlllll) {
        try {
            SecretKeySpec llIIIIIIlIIlIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIIIIIlllll.getBytes(StandardCharsets.UTF_8)), lIlIIlIIl[0]), "DES");
            Cipher llIIIIIIlIIlII = Cipher.getInstance("DES");
            llIIIIIIlIIlII.init(lIlIIlIIl[1], llIIIIIIlIIlIl);
            return new String(llIIIIIIlIIlII.doFinal(Base64.getDecoder().decode(llIIIIIIlIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIIIIlIIIll) {
            llIIIIIIlIIIll.printStackTrace();
            return null;
        }
    }

    static {
        PilotaSplash.lIIlIlIlIlI();
        PilotaSplash.lIIlIIIllIl();
    }

    void applyDisguises() {
        PilotaSplash llIIIIIlIIIIIl;
        Iterator<Player> llIIIIIlIIIIII = llIIIIIlIIIIIl.getPlayers().iterator();
        while (PilotaSplash.lIIlIlIlIll((int)llIIIIIlIIIIII.hasNext())) {
            Player llIIIIIIllllll = llIIIIIlIIIIII.next();
            "".length();
            if ("  ".length() >= 0) continue;
            return;
        }
    }

    public void giveMagnusIfNecessary(Player llIIIIlIIIlIIl) {
        PilotaSplash llIIIIlIIIlIlI;
        if (PilotaSplash.lIIlIllIIlI((int)llIIIIlIIIlIIl.getInventory().contains(llIIIIlIIIlIlI.getMagnusLauncherItem()))) {
            ItemStack[] arritemStack = new ItemStack[lIlIIlIIl[5]];
            arritemStack[PilotaSplash.lIlIIlIIl[2]] = llIIIIlIIIlIlI.getMagnusLauncherItem();
            "".length();
            llIIIIlIIIlIIl.getInventory().addItem(arritemStack);
        }
    }

    @Override
    public String getGameName() {
        return lIIllllll[lIlIIlIIl[2]];
    }

    private static boolean lIIlIllIIlI(int n) {
        return n == 0;
    }

    public ItemStack getMagnusLauncherItem() {
        ItemStack llIIIIllIlIIIl = new ItemStack(Material.QUARTZ, lIlIIlIIl[5]);
        llIIIIllIlIIIl.addUnsafeEnchantment(Enchantment.KNOCKBACK, lIlIIlIIl[6]);
        String[] arrstring = new String[lIlIIlIIl[5]];
        arrstring[PilotaSplash.lIlIIlIIl[2]] = lIIllllll[lIlIIlIIl[4]];
        return GUtils.setItemNameAndLore((ItemStack)llIIIIllIlIIIl, (String)lIIllllll[lIlIIlIIl[6]], (String[])arrstring);
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llIIIIlllIlIll) {
        PilotaSplash llIIIIlllIIllI;
        ArrayList<ItemStack> llIIIIlllIlIlI = new ArrayList<ItemStack>();
        ItemStack llIIIIlllIlIIl = new ItemStack(Material.SLIME_BALL, lIlIIlIIl[5]);
        llIIIIlllIlIIl.addUnsafeEnchantment(Enchantment.KNOCKBACK, lIlIIlIIl[1]);
        String[] arrstring = new String[lIlIIlIIl[5]];
        arrstring[PilotaSplash.lIlIIlIIl[2]] = lIIllllll[lIlIIlIIl[1]];
        "".length();
        llIIIIlllIlIlI.add(GUtils.setItemNameAndLore((ItemStack)llIIIIlllIlIIl, (String)lIIllllll[lIlIIlIIl[5]], (String[])arrstring));
        Color llIIIIlllIlIII = llIIIIlllIIllI.getDeterministicColorForPlayer(llIIIIlllIlIll, lIlIIlIIl[2]);
        Color llIIIIlllIIlll = llIIIIlllIIllI.getDeterministicColorForPlayer(llIIIIlllIlIll, lIlIIlIIl[5]);
        "".length();
        llIIIIlllIlIlI.add(GUtils.createColoredArmor((Material)Material.LEATHER_HELMET, (Color)Color.GREEN));
        "".length();
        llIIIIlllIlIlI.add(GUtils.createColoredArmor((Material)Material.LEATHER_CHESTPLATE, (Color)llIIIIlllIlIII));
        "".length();
        llIIIIlllIlIlI.add(GUtils.createColoredArmor((Material)Material.LEATHER_LEGGINGS, (Color)llIIIIlllIIlll));
        "".length();
        llIIIIlllIlIlI.add(GUtils.createColoredArmor((Material)Material.LEATHER_BOOTS, (Color)llIIIIlllIlIII));
        return llIIIIlllIlIlI;
    }

    @Override
    protected int getBaseSkillUnlockerAmount() {
        return lIlIIlIIl[2];
    }

    private static boolean lIIlIlIlllI(int n) {
        return n > 0;
    }

    @Override
    public void clearExternals(Player llIIIIIIlllIII) {
        PilotaSplash llIIIIIIllIlll;
        super.clearExternals(llIIIIIIlllIII);
    }

    private static int lIIlIlIllll(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    @Override
    protected void setSpree(Player llIIIIIlIIlIII, int llIIIIIlIIlIlI) {
        PilotaSplash llIIIIIlIIlIIl;
        super.setSpree(llIIIIIlIIlIII, llIIIIIlIIlIlI);
    }

    public void removeMagnusIfNecessary(Player llIIIIlIIIIIIl) {
        PilotaSplash llIIIIlIIIIlII;
        if (PilotaSplash.lIIlIlIlIll((int)llIIIIlIIIIIIl.getInventory().contains(llIIIIlIIIIlII.getMagnusLauncherItem()))) {
            ItemStack[] arritemStack = new ItemStack[lIlIIlIIl[5]];
            arritemStack[PilotaSplash.lIlIIlIIl[2]] = llIIIIlIIIIlII.getMagnusLauncherItem();
            "".length();
            llIIIIlIIIIIIl.getInventory().removeItem(arritemStack);
        }
    }

    private static boolean lIIlIlIlIll(int n) {
        return n != 0;
    }

    @Override
    protected void onPlayerMove(PlayerMoveEvent llIIIIlIIlIIlI, Player llIIIIlIIIlllI) {
        PilotaSplash llIIIIlIIlIIll;
        super.onPlayerMove(llIIIIlIIlIIlI, llIIIIlIIIlllI);
        if (PilotaSplash.lIIlIllIIII(PilotaSplash.lIIlIlIllll(llIIIIlIIlIIlI.getTo().getY(), llIIIIlIIlIIll.getMinimumHeight()))) {
            llIIIIlIIlIIll.teleportToRandomSpawn(llIIIIlIIIlllI);
            Player llIIIIlIIlIlII = llIIIIlIIlIIll.getPlayerInfo(llIIIIlIIIlllI).getLastDamager();
            if (PilotaSplash.lIIlIllIIIl((Object)llIIIIlIIlIlII)) {
                llIIIIlIIlIIll.incrementScore(llIIIIlIIlIlII);
                llIIIIlIIlIIll.resetSpree(llIIIIlIIIlllI);
                llIIIIlIIlIlII.playSound(llIIIIlIIIlllI.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1.0f, 1.5f);
                llIIIIlIIlIlII.playEffect(llIIIIlIIIlllI.getEyeLocation(), Effect.POTION_SWIRL, lIlIIlIIl[6]);
            }
        }
        if (PilotaSplash.lIIlIllIIII(PilotaSplash.lIIlIlIllll(llIIIIlIIIlllI.getVelocity().getY(), -0.19)) && PilotaSplash.lIIlIllIIII(PilotaSplash.lIIlIlIllll(llIIIIlIIIlllI.getVelocity().normalize().angle(new Vector(lIlIIlIIl[2], lIlIIlIIl[7], lIlIIlIIl[2])), 0.6283185307179586))) {
            llIIIIlIIlIIll.giveMagnusIfNecessary(llIIIIlIIIlllI);
            "".length();
            if ("  ".length() > "  ".length()) {
                return;
            }
        } else {
            llIIIIlIIlIIll.removeMagnusIfNecessary(llIIIIlIIIlllI);
        }
    }

    private static void lIIlIIIllIl() {
        lIIllllll = new String[lIlIIlIIl[8]];
        PilotaSplash.lIIllllll[PilotaSplash.lIlIIlIIl[2]] = PilotaSplash.lIIIlllllII("CnerEzw8i6Wz0zCg5ouxZQ==", "pKDju");
        PilotaSplash.lIIllllll[PilotaSplash.lIlIIlIIl[5]] = PilotaSplash.lIIIllllllI("brJ2KGdc8VLhLj7/H/CXnA==", "FhDKm");
        PilotaSplash.lIIllllll[PilotaSplash.lIlIIlIIl[1]] = PilotaSplash.lIIIlllllII("ZewHIaG/qKz8cd82L8NEAg==", "dRZuU");
        PilotaSplash.lIIllllll[PilotaSplash.lIlIIlIIl[6]] = PilotaSplash.lIIIlllllII("m0qNvYHlX+EZnWG3Z8jnSQ==", "qdmcT");
        PilotaSplash.lIIllllll[PilotaSplash.lIlIIlIIl[4]] = PilotaSplash.lIIIllllllI("C9lxl7QzYGbj6G27zWJxlg==", "RAQpX");
    }

    private static boolean lIIlIllIIIl(Object object) {
        return object != null;
    }

    public PilotaSplash() {
        PilotaSplash llIIIlIIIIIIII;
    }

    @Override
    protected void onPlayerDamage(EntityDamageEvent llIIIIlIllIlIl, Player llIIIIlIllIlII) {
        PilotaSplash llIIIIlIllIIll;
        super.onPlayerDamage(llIIIIlIllIlIl, llIIIIlIllIlII);
        if (PilotaSplash.lIIlIlIllIl((Object)llIIIIlIllIlIl.getCause(), (Object)EntityDamageEvent.DamageCause.FALL)) {
            Sound sound;
            llIIIIlIllIlIl.setCancelled(lIlIIlIIl[2]);
            GUtils.getNearbyEnemies((LivingEntity)llIIIIlIllIlII, (double)(3.0 + (double)llIIIIlIllIIll.getSpree(llIIIIlIllIlII) * 0.2), (boolean)lIlIIlIIl[2]).forEach(llIIIIIIlIlllI -> {
                PilotaSplash llIIIIIIllIIIl;
                llIIIIIIlIlllI.damage(llIIIIlIllIlIl.getDamage() * 0.45 + 5.5 + (double)llIIIIIIllIIIl.getSpree(llIIIIlIllIlII) * 0.3);
            });
            if (PilotaSplash.lIIlIlIlllI(PilotaSplash.lIIlIlIllII(llIIIIlIllIlIl.getDamage(), 4.0))) {
                sound = Sound.BLOCK_SLIME_HIT;
                "".length();
                if (((11 ^ 28) & ~(189 ^ 170)) > (141 ^ 137)) {
                    return;
                }
            } else {
                sound = Sound.BLOCK_SLIME_STEP;
            }
            llIIIIlIllIIll.getWorld().playSound(llIIIIlIllIlII.getLocation(), sound, 1.0f, 1.0f);
            GUtils.healDamageable((Damageable)llIIIIlIllIlII, (Double)(llIIIIlIllIlIl.getDamage() * 0.25));
            llIIIIlIllIlIl.setDamage(0.5);
        }
    }

    @Override
    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent llIIIIIlIllIIl, Player llIIIIIlIlIlll) {
        PilotaSplash llIIIIIlIllIlI;
        super.onPlayerRespawnAfterTick(llIIIIIlIllIIl, llIIIIIlIlIlll);
        llIIIIIlIllIlI.teleportToRandomSpawn(llIIIIIlIlIlll);
    }
}

