/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Effect
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Builder
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.FireworkMeta
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.FireworkEffectPlayer;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.data.PlayerData;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class Quakecraft
extends JocScoreRace {
    private static final /* synthetic */ int[] llIIIlIl;
    /* synthetic */ int maxT;
    private static final /* synthetic */ String[] lIllIIII;
    /* synthetic */ FireworkEffectPlayer fPlayer;
    /* synthetic */ HashMap<String, Long> pReloadRocket;

    private static int llIIIlllII(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private void updateBar(Player lIIlIIIllIIIllI) {
        Quakecraft lIIlIIIllIIIlll;
        if (Quakecraft.llIIIlIllI((int)lIIlIIIllIIIlll.pReloadRocket.containsKey(lIIlIIIllIIIllI.getName()))) {
            return;
        }
        long lIIlIIIllIIIlIl = System.currentTimeMillis() - lIIlIIIllIIIlll.pReloadRocket.get(lIIlIIIllIIIllI.getName());
        Float lIIlIIIllIIIlII = Float.valueOf((float)lIIlIIIllIIIlIl / (float)lIIlIIIllIIIlll.getMaxT(lIIlIIIllIIIllI));
        if (Quakecraft.llIIIlIlll(Quakecraft.llIIIlIlIl(lIIlIIIllIIIlII.floatValue(), 1.0f))) {
            lIIlIIIllIIIlII = Float.valueOf(1.0f);
        }
        lIIlIIIllIIIllI.setExp(lIIlIIIllIIIlII.floatValue());
    }

    private static boolean llIIIllIlI(int n, int n2) {
        return n != n2;
    }

    @Override
    protected int getBaseSkillUnlockerAmount() {
        return llIIIlIl[2];
    }

    protected void onEntityDamage(EntityDamageEvent lIIlIIIIIlIIllI, Entity lIIlIIIIIlIIlIl) {
        Quakecraft lIIlIIIIIlIIlll;
        super.onEntityDamage(lIIlIIIIIlIIllI, lIIlIIIIIlIIlIl);
        if (Quakecraft.llIIIIllll((Object)lIIlIIIIIlIIllI.getCause(), (Object)EntityDamageEvent.DamageCause.CUSTOM)) {
            lIIlIIIIIlIIllI.setCancelled(llIIIlIl[3]);
        }
    }

    private static String lIlIllIllI(String lIIIllllllIIlII, String lIIIllllllIIIll) {
        try {
            SecretKeySpec lIIIllllllIIlll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIllllllIIIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIIllllllIIllI = Cipher.getInstance("Blowfish");
            lIIIllllllIIllI.init(llIIIlIl[5], lIIIllllllIIlll);
            return new String(lIIIllllllIIllI.doFinal(Base64.getDecoder().decode(lIIIllllllIIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIllllllIIlIl) {
            lIIIllllllIIlIl.printStackTrace();
            return null;
        }
    }

    private static boolean llIIIlIIII(Object object, Object object2) {
        return object == object2;
    }

    @Override
    public boolean getResetPlayerOnRespawn() {
        return llIIIlIl[3];
    }

    private Color getColorRailgun(Player lIIlIIIlIIllIll) {
        Quakecraft lIIlIIIlIIlllII;
        Material lIIlIIIlIIlllIl = lIIlIIIlIIlllII.getRailgun(lIIlIIIlIIllIll).getType();
        if (Quakecraft.llIIIlIIII((Object)lIIlIIIlIIlllIl, (Object)Material.WOOD_HOE)) {
            return Color.RED;
        }
        if (Quakecraft.llIIIlIIII((Object)lIIlIIIlIIlllIl, (Object)Material.STONE_HOE)) {
            return Color.GREEN;
        }
        if (Quakecraft.llIIIlIIII((Object)lIIlIIIlIIlllIl, (Object)Material.IRON_HOE)) {
            return Color.SILVER;
        }
        if (Quakecraft.llIIIlIIII((Object)lIIlIIIlIIlllIl, (Object)Material.GOLD_HOE)) {
            return Color.YELLOW;
        }
        if (Quakecraft.llIIIlIIII((Object)lIIlIIIlIIlllIl, (Object)Material.DIAMOND_HOE)) {
            return Color.AQUA;
        }
        return Color.BLACK;
    }

    protected void onPlayerInteract(PlayerInteractEvent lIIlIIIllIlIIll, Player lIIlIIIllIIllll) {
        Quakecraft lIIlIIIllIlIIIl;
        lIIlIIIllIlIIIl.updateBar(lIIlIIIllIIllll);
        if (Quakecraft.llIIIIlIlI((int)lIIlIIIllIlIIll.getPlayer().getItemInHand().getType().name().endsWith(lIllIIII[llIIIlIl[21]])) && (!Quakecraft.llIIIIllll((Object)lIIlIIIllIlIIll.getAction(), (Object)Action.RIGHT_CLICK_AIR) || Quakecraft.llIIIlIIII((Object)lIIlIIIllIlIIll.getAction(), (Object)Action.RIGHT_CLICK_BLOCK))) {
            Vector lIIlIIIllIlIlIl = lIIlIIIllIIllll.getLocation().getDirection();
            if (Quakecraft.llIIIlIIll(lIIlIIIllIlIIIl.pReloadRocket.get(lIIlIIIllIlIIll.getPlayer().getName()))) {
                long lIIlIIIllIlIlll = System.currentTimeMillis() - lIIlIIIllIlIIIl.pReloadRocket.get(lIIlIIIllIlIIll.getPlayer().getName());
                if (Quakecraft.llIIIlIlII(Quakecraft.llIIIlIIlI(lIIlIIIllIlIlll, lIIlIIIllIlIIIl.getMaxT(lIIlIIIllIIllll)))) {
                    lIIlIIIllIIllll.playSound(lIIlIIIllIIllll.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0f, 1.0f);
                    Arrow lIIlIIIllIllIII = (Arrow)lIIlIIIllIIllll.getWorld().spawn(lIIlIIIllIIllll.getEyeLocation().add(lIIlIIIllIlIlIl), Arrow.class);
                    lIIlIIIllIllIII.setVelocity(lIIlIIIllIlIlIl.multiply(llIIIlIl[12]));
                    lIIlIIIllIllIII.setShooter((ProjectileSource)lIIlIIIllIIllll);
                    "".length();
                    lIIlIIIllIlIIIl.pReloadRocket.remove(lIIlIIIllIIllll.getName());
                    "".length();
                    lIIlIIIllIlIIIl.pReloadRocket.put(lIIlIIIllIIllll.getName(), System.currentTimeMillis());
                    "".length();
                    if ("   ".length() != "   ".length()) {
                        return;
                    }
                } else {
                    lIIlIIIllIIllll.playSound(lIIlIIIllIIllll.getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0f, 1.0f);
                }
                "".length();
                if (-(103 + 133 - 194 + 116 ^ 71 + 24 - 24 + 83) > 0) {
                    return;
                }
            } else {
                Arrow lIIlIIIllIlIllI = (Arrow)lIIlIIIllIIllll.getWorld().spawn(lIIlIIIllIIllll.getEyeLocation(), Arrow.class);
                lIIlIIIllIlIllI.setVelocity(lIIlIIIllIlIlIl.multiply(llIIIlIl[12]));
                lIIlIIIllIlIllI.setShooter((ProjectileSource)lIIlIIIllIIllll);
                "".length();
                lIIlIIIllIlIIIl.pReloadRocket.put(lIIlIIIllIIllll.getName(), System.currentTimeMillis());
            }
        }
    }

    private static String lIlIlllIII(String lIIIlllllllIIIl, String lIIIllllllIlllI) {
        try {
            SecretKeySpec lIIIlllllllIlII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIllllllIlllI.getBytes(StandardCharsets.UTF_8)), llIIIlIl[19]), "DES");
            Cipher lIIIlllllllIIll = Cipher.getInstance("DES");
            lIIIlllllllIIll.init(llIIIlIl[5], lIIIlllllllIlII);
            return new String(lIIIlllllllIIll.doFinal(Base64.getDecoder().decode(lIIIlllllllIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIlllllllIIlI) {
            lIIIlllllllIIlI.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPlayerMove(PlayerMoveEvent lIIlIIIIIIIlIll, Player lIIlIIIIIIIlIlI) {
        Quakecraft lIIlIIIIIIIllII;
        super.onPlayerMove(lIIlIIIIIIIlIll, lIIlIIIIIIIlIlI);
        lIIlIIIIIIIllII.updateBar(lIIlIIIIIIIlIlI);
        if (Quakecraft.llIIIlllIl(Quakecraft.llIIIlllII(lIIlIIIIIIIlIll.getTo().getY(), 0.0))) {
            lIIlIIIIIIIlIlI.setHealth(0.0);
        }
    }

    static {
        Quakecraft.llIIIIIlll();
        Quakecraft.lIllIIIIII();
    }

    private static boolean llIIIllIIl(Object object) {
        return object == null;
    }

    private static boolean llIIIIllll(Object object, Object object2) {
        return object != object2;
    }

    private static boolean llIIIIllIl(int n, int n2) {
        return n >= n2;
    }

    public void addFrag(Player lIIlIIIIIllIlII, Player lIIlIIIIIllIIll, String lIIlIIIIIllIIlI) {
        Quakecraft lIIlIIIIIllIlIl;
        if (Quakecraft.llIIIlIllI((int)lIIlIIIIIllIlIl.getPlayers().contains((Object)lIIlIIIIIllIIll))) {
            return;
        }
        lIIlIIIIIllIIll.setHealth(0.0);
        String lIIlIIIIIllIIIl = lIllIIII[llIIIlIl[33]];
        if (Quakecraft.llIIIIlIlI((int)lIIlIIIIIllIlII.getName().contains(lIllIIII[llIIIlIl[34]])) && Quakecraft.llIIIIlIlI((int)Utils.Possibilitat(llIIIlIl[35] / new PlayerData(lIIlIIIIIllIlII).getRank()))) {
            lIIlIIIIIllIIIl = lIllIIII[llIIIlIl[36]];
        }
        "".length();
        Bukkit.getServer().broadcastMessage(String.valueOf(new StringBuilder().append(lIIlIIIIIllIlII.getName()).append(lIllIIII[llIIIlIl[1]]).append(lIIlIIIIIllIIIl).append(lIllIIII[llIIIlIl[37]]).append(lIIlIIIIIllIIll.getName())));
        Iterator<Player> lIIlIIIIIlIllII = lIIlIIIIIllIlIl.getPlayers().iterator();
        while (Quakecraft.llIIIIlIlI((int)lIIlIIIIIlIllII.hasNext())) {
            Player lIIlIIIIIllIllI = lIIlIIIIIlIllII.next();
            lIIlIIIIIllIllI.playSound(lIIlIIIIIllIllI.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1.0f, 1.0f);
            "".length();
            if (-" ".length() <= 0) continue;
            return;
        }
    }

    private static boolean llIIIIlllI(int n, int n2) {
        return n <= n2;
    }

    int getMaxT(Player lIIlIIIlllllIll) {
        Quakecraft lIIlIIIllllllII;
        int lIIlIIIllllllIl = llIIIlIl[6] - lIIlIIIllllllII.getSpree(lIIlIIIlllllIll) * llIIIlIl[7];
        if (Quakecraft.llIIIIllII(lIIlIIIllllllIl, llIIIlIl[8])) {
            lIIlIIIllllllIl = llIIIlIl[8];
        }
        return lIIlIIIllllllIl;
    }

    private static int llIIIlIlIl(float f, float f2) {
        return (int)(f FCMPL f2);
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIIlIIlIIIlIlII) {
        ArrayList<ItemStack> lIIlIIlIIIlIIll = new ArrayList<ItemStack>();
        "".length();
        lIIlIIlIIIlIIll.add(new ItemStack(Material.WOOD_HOE, llIIIlIl[3]));
        return lIIlIIlIIIlIIll;
    }

    private static boolean llIIIIlIlI(int n) {
        return n != 0;
    }

    ItemStack getRailgun(Player lIIlIIIllllIIll) {
        Quakecraft lIIlIIIlllIllll;
        int lIIlIIIllllIIlI = lIIlIIIlllIllll.getMaxT(lIIlIIIllllIIll);
        String lIIlIIIllllIIIl = String.valueOf(new StringBuilder().append(Integer.toString(lIIlIIIllllIIlI)).append(lIllIIII[llIIIlIl[3]]));
        if (Quakecraft.llIIIIllIl(lIIlIIIllllIIlI, llIIIlIl[0])) {
            String[] arrstring = new String[llIIIlIl[3]];
            arrstring[Quakecraft.llIIIlIl[2]] = lIIlIIIllllIIIl;
            return Utils.setItemNameAndLore(new ItemStack(Material.WOOD_HOE), lIllIIII[llIIIlIl[5]], arrstring);
        }
        if (Quakecraft.llIIIIllIl(lIIlIIIllllIIlI, llIIIlIl[9])) {
            String[] arrstring = new String[llIIIlIl[3]];
            arrstring[Quakecraft.llIIIlIl[2]] = lIIlIIIllllIIIl;
            return Utils.setItemNameAndLore(new ItemStack(Material.STONE_HOE), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIllIIII[llIIIlIl[10]])), arrstring);
        }
        if (Quakecraft.llIIIIllIl(lIIlIIIllllIIlI, llIIIlIl[11])) {
            String[] arrstring = new String[llIIIlIl[3]];
            arrstring[Quakecraft.llIIIlIl[2]] = lIIlIIIllllIIIl;
            return Utils.setItemNameAndLore(new ItemStack(Material.IRON_HOE), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIllIIII[llIIIlIl[12]])), arrstring);
        }
        if (Quakecraft.llIIIIllIl(lIIlIIIllllIIlI, llIIIlIl[13])) {
            String[] arrstring = new String[llIIIlIl[3]];
            arrstring[Quakecraft.llIIIlIl[2]] = lIIlIIIllllIIIl;
            return Utils.setItemNameAndLore(new ItemStack(Material.GOLD_HOE), String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lIllIIII[llIIIlIl[14]])), arrstring);
        }
        ItemStack lIIlIIIllllIIII = new ItemStack(Material.DIAMOND_HOE);
        if (Quakecraft.llIIIIllIl(lIIlIIIllllIIlI, llIIIlIl[15])) {
            String[] arrstring = new String[llIIIlIl[3]];
            arrstring[Quakecraft.llIIIlIl[2]] = lIIlIIIllllIIIl;
            return Utils.setItemNameAndLore(lIIlIIIllllIIII, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIllIIII[llIIIlIl[16]])), arrstring);
        }
        if (Quakecraft.llIIIIlIII(lIIlIIIllllIIlI, llIIIlIl[17])) {
            String[] arrstring = new String[llIIIlIl[3]];
            arrstring[Quakecraft.llIIIlIl[2]] = lIIlIIIllllIIIl;
            return Utils.setItemNameAndLore(lIIlIIIllllIIII, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIllIIII[llIIIlIl[18]])), arrstring);
        }
        lIIlIIIllllIIII.addEnchantment(Enchantment.ARROW_DAMAGE, llIIIlIl[12]);
        if (Quakecraft.llIIIIlllI(lIIlIIIllllIIlI, llIIIlIl[17])) {
            String[] arrstring = new String[llIIIlIl[3]];
            arrstring[Quakecraft.llIIIlIl[2]] = lIIlIIIllllIIIl;
            return Utils.setItemNameAndLore(lIIlIIIllllIIII, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIllIIII[llIIIlIl[19]])), arrstring);
        }
        return new ItemStack(Material.WOOD);
    }

    private static boolean llIIIIllII(int n, int n2) {
        return n < n2;
    }

    @Override
    protected void onPlayerMoveDistributed(PlayerMoveEvent lIIIlllllllllII, Player lIIIllllllllIll) {
        Quakecraft lIIIlllllllllIl;
        super.onPlayerMoveDistributed(lIIIlllllllllII, lIIIllllllllIll);
        if (Quakecraft.llIIIIlIlI((int)lIIIlllllllllIl.JocIniciat.booleanValue())) {
            Iterator<Player> lIIIllllllllIlI = lIIIlllllllllIl.getPlayers().iterator();
            while (Quakecraft.llIIIIlIlI((int)lIIIllllllllIlI.hasNext())) {
                Player lIIlIIIIIIIIIIl = lIIIllllllllIlI.next();
                lIIIlllllllllIl.updateBar(lIIlIIIIIIIIIIl);
                "".length();
                if (" ".length() >= ((236 ^ 190 ^ (24 ^ 21)) & (172 + 135 - 243 + 164 ^ 25 + 131 - 134 + 165 ^ -" ".length()))) continue;
                return;
            }
        }
    }

    void railgunHitPlace(Player lIIlIIIlIlIlIlI, Projectile lIIlIIIlIlIlIIl) {
        if (Quakecraft.llIIIIlIlI(lIIlIIIlIlIlIIl instanceof Arrow)) {
            Quakecraft lIIlIIIlIlIlIll;
            List<Entity> lIIlIIIlIllIIlI = lIIlIIIlIlIlIll.getAffectedEntities((Entity)lIIlIIIlIlIlIIl, llIIIlIl[19], (double)lIIlIIIlIlIlIll.getSpree(lIIlIIIlIlIlIlI) * 0.12);
            int lIIlIIIlIllIIIl = llIIIlIl[2];
            Iterator<Entity> lIIlIIIlIlIIllI = lIIlIIIlIllIIlI.iterator();
            while (Quakecraft.llIIIIlIlI((int)lIIlIIIlIlIIllI.hasNext())) {
                Entity lIIlIIIlIllIllI = lIIlIIIlIlIIllI.next();
                if (Quakecraft.llIIIIlIlI(lIIlIIIlIllIllI instanceof Player)) {
                    ++lIIlIIIlIllIIIl;
                }
                "".length();
                if (null == null) continue;
                return;
            }
            "".length();
            Integer.toString(lIIlIIIlIllIIIl);
            if (Quakecraft.llIIIIlIlI((int)lIIlIIIlIllIIlI.contains((Object)lIIlIIIlIlIlIlI))) {
                --lIIlIIIlIllIIIl;
            }
            if (Quakecraft.llIIIllIII(lIIlIIIlIllIIIl)) {
                lIIlIIIlIlIlIll.playRailgunEffect(lIIlIIIlIlIlIlI, lIIlIIIlIlIlIIl.getLocation(), llIIIlIl[2], llIIIlIl[2]);
            }
            lIIlIIIlIlIIllI = lIIlIIIlIllIIlI.iterator();
            while (Quakecraft.llIIIIlIlI((int)lIIlIIIlIlIIllI.hasNext())) {
                Entity lIIlIIIlIllIIll = lIIlIIIlIlIIllI.next();
                if (Quakecraft.llIIIIlIlI(lIIlIIIlIllIIll instanceof Player)) {
                    Player lIIlIIIlIllIlIl = (Player)lIIlIIIlIllIIll;
                    Location lIIlIIIlIllIlII = lIIlIIIlIlIlIIl.getLocation();
                    lIIlIIIlIlIlIll.getWorld().playEffect(lIIlIIIlIllIlII, Effect.SMOKE, llIIIlIl[14]);
                    if (Quakecraft.llIIIllIIl((Object)lIIlIIIlIlIlIlI)) {
                        "".length();
                        if (-"  ".length() < 0) continue;
                        return;
                    }
                    if (Quakecraft.llIIIllIlI(lIIlIIIlIllIlIl.getEntityId(), lIIlIIIlIlIlIlI.getEntityId())) {
                        lIIlIIIlIlIlIll.playRailgunEffect(lIIlIIIlIlIlIlI, lIIlIIIlIllIlII, llIIIlIl[3], llIIIlIl[3]);
                        lIIlIIIlIlIlIll.incrementScore(lIIlIIIlIlIlIlI);
                        lIIlIIIlIlIlIll.updateRailgun(lIIlIIIlIlIlIlI);
                        lIIlIIIlIlIlIll.addFrag(lIIlIIIlIlIlIlI, lIIlIIIlIllIlIl, lIllIIII[llIIIlIl[22]]);
                    }
                }
                "".length();
                if ((96 ^ 3 ^ (94 ^ 57)) > ((72 ^ 126 ^ (148 ^ 167)) & ((34 ^ 106) & ~(222 ^ 150) ^ (94 ^ 91) ^ -" ".length()))) continue;
                return;
            }
            int lIIlIIIlIllIIII = lIIlIIIlIllIIIl - llIIIlIl[5];
            String lIIlIIIlIlIllll = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIllIIII[llIIIlIl[23]]).append((Object)ChatColor.GOLD).append(lIllIIII[llIIIlIl[24]]).append(Integer.toString(lIIlIIIlIllIIII)).append((Object)ChatColor.WHITE).append(lIllIIII[llIIIlIl[25]]));
            if (Quakecraft.llIIIllIll(lIIlIIIlIllIIIl, llIIIlIl[5])) {
                lIIlIIIlIlIlIll.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIllIIII[llIIIlIl[26]])));
            }
            if (Quakecraft.llIIIllIll(lIIlIIIlIllIIIl, llIIIlIl[10])) {
                lIIlIIIlIlIlIll.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.LIGHT_PURPLE).append(lIllIIII[llIIIlIl[27]]).append(lIIlIIIlIlIllll)));
            }
            if (Quakecraft.llIIIllIll(lIIlIIIlIllIIIl, llIIIlIl[12])) {
                lIIlIIIlIlIlIll.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIllIIII[llIIIlIl[28]]).append(lIIlIIIlIlIllll)));
            }
            if (Quakecraft.llIIIllIll(lIIlIIIlIllIIIl, llIIIlIl[14])) {
                lIIlIIIlIlIlIll.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.BOLD).append(lIllIIII[llIIIlIl[29]]).append(lIIlIIIlIlIllll)));
            }
            if (Quakecraft.llIIIllIll(lIIlIIIlIllIIIl, llIIIlIl[16])) {
                lIIlIIIlIlIlIll.sendGlobalMessage(String.valueOf(new StringBuilder().append(lIllIIII[llIIIlIl[30]]).append(lIIlIIIlIlIllll)));
            }
            if (Quakecraft.llIIIIlIII(lIIlIIIlIllIIIl, llIIIlIl[16])) {
                lIIlIIIlIlIlIll.sendGlobalMessage(String.valueOf(new StringBuilder().append(lIllIIII[llIIIlIl[31]]).append(Integer.toString(lIIlIIIlIllIIIl)).append(lIllIIII[llIIIlIl[32]]).append(lIIlIIIlIlIllll)));
            }
            if (Quakecraft.llIIIlIlll(lIIlIIIlIllIIII)) {
                lIIlIIIlIlIlIll.incrementScore(lIIlIIIlIlIlIlI, lIIlIIIlIllIIII);
            }
        }
    }

    protected void onEntityDamageByEntity(EntityDamageByEntityEvent lIIlIIIIllIllIl, Entity lIIlIIIIlllIIII, Entity lIIlIIIIllIllll) {
        Projectile lIIlIIIIlllIlII;
        Quakecraft lIIlIIIIllIlllI;
        ProjectileSource lIIlIIIIlllIIll;
        super.onEntityDamageByEntity(lIIlIIIIllIllIl, lIIlIIIIlllIIII, lIIlIIIIllIllll);
        if (!Quakecraft.llIIIIlIlI(lIIlIIIIllIllll instanceof Projectile) || Quakecraft.llIIIIlIlI((lIIlIIIIlllIIll = (lIIlIIIIlllIlII = (Projectile)lIIlIIIIllIllll).getShooter()) instanceof Player)) {
            // empty if block
        }
    }

    private static boolean llIIIllIll(int n, int n2) {
        return n == n2;
    }

    void updateRailgun(Player lIIlIIIlllIIlIl) {
        Quakecraft lIIlIIIlllIIllI;
        PlayerInventory lIIlIIIlllIIlII = lIIlIIIlllIIlIl.getInventory();
        ItemStack lIIlIIIlllIIIll = lIIlIIIlllIIllI.getRailgun(lIIlIIIlllIIlIl);
        if (Quakecraft.llIIIIllll((Object)lIIlIIIlllIIlII.getItemInHand().getType(), (Object)Material.DIAMOND_HOE) && Quakecraft.llIIIlIIII((Object)lIIlIIIlllIIIll.getType(), (Object)Material.DIAMOND_HOE)) {
            lIIlIIIlllIIllI.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(lIIlIIIlllIIlIl.getName()).append(lIllIIII[llIIIlIl[20]])));
        }
        lIIlIIIlllIIlII.clear();
        ItemStack[] arritemStack = new ItemStack[llIIIlIl[3]];
        arritemStack[Quakecraft.llIIIlIl[2]] = lIIlIIIlllIIIll;
        "".length();
        lIIlIIIlllIIlII.addItem(arritemStack);
    }

    private static boolean llIIIIlIII(int n, int n2) {
        return n > n2;
    }

    private static void lIllIIIIII() {
        lIllIIII = new String[llIIIlIl[39]];
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[2]] = Quakecraft.lIlIllIllI("tUEKWL1lVJz0EFOZtkVYcQ==", "Jypbs");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[3]] = Quakecraft.lIlIllIlll("AyNpJQY=", "nPIfB");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[5]] = Quakecraft.lIlIllIlll("NAIuBRYUBSxKAhRRKwMUEA==", "qqMjf");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[10]] = Quakecraft.lIlIlllIII("I+Zt4xR4QvUMBFA13BOEXdbGFHMHtYXn", "JkHIW");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[12]] = Quakecraft.lIlIllIlll("Lg85I+++sAMHNz9tBgZ4KyIBEHgsPxYKPiQuCwI0Pg==", "bcXMM");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[14]] = Quakecraft.lIlIllIlll("OQUoGwIeCmESHRsBMx4IDgo1Fgk=", "kdAwe");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[16]] = Quakecraft.lIlIllIlll("PwUtHAEYCg==", "mdDpf");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[18]] = Quakecraft.lIlIllIllI("60jv5nBObnTpHLhIV1/gMHcSqXEJmn5X", "zHvYf");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[19]] = Quakecraft.lIlIlllIII("G1Tpind2BMNFuBiixxsjJQ==", "zoikY");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[20]] = Quakecraft.lIlIlllIII("VxUuVFkIe72kGL2R8+ia0CTQq7Xb9WcGgMFLemc7HjI=", "TCCyz");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[21]] = Quakecraft.lIlIllIlll("JwoZJg==", "xBVcP");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[22]] = Quakecraft.lIlIllIlll("Ows5LQkdRBYnGQcHMiMe", "idZFl");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[23]] = Quakecraft.lIlIllIlll("WE0=", "xeDxx");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[24]] = Quakecraft.lIlIlllIII("3l9KCbhsVJA=", "ADxYI");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[25]] = Quakecraft.lIlIllIlll("Yw==", "JFOJU");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[26]] = Quakecraft.lIlIllIllI("+zqvBJHvOglrJ8qsjkDDkw==", "mWBmL");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[27]] = Quakecraft.lIlIllIllI("yxycHtoZ0daEqA0Sb+HzeA==", "ACsLK");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[28]] = Quakecraft.lIlIlllIII("XeAY6Genm4rWAlOIsS/wRg==", "QNEwP");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[29]] = Quakecraft.lIlIllIlll("OR0dBDBEExocPUg=", "iXSPq");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[30]] = Quakecraft.lIlIlllIII("FBSNJ0/G3vQwF10ZsgPjHQ==", "cWPIl");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[31]] = Quakecraft.lIlIlllIII("UtLwJPmP6pibwJ0KJ310GA9wc+BH6dQe", "XbepG");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[32]] = Quakecraft.lIlIllIlll("bQ==", "DEOna");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[33]] = Quakecraft.lIlIlllIII("+cOc172UIYbZoPzS6zXPMw==", "XQgQz");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[34]] = Quakecraft.lIlIlllIII("afUo1CJVsVo=", "tFXVw");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[36]] = Quakecraft.lIlIllIllI("wV/54819JTE5m5XMi7Ue5Q==", "oZoTV");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[1]] = Quakecraft.lIlIllIllI("0Z4nRQ//VLs=", "KtUkA");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[37]] = Quakecraft.lIlIllIlll("eTdy", "YVRvQ");
        Quakecraft.lIllIIII[Quakecraft.llIIIlIl[38]] = Quakecraft.lIlIllIllI("337lGLNwSlA=", "LjoKS");
    }

    @Override
    protected void teletransportarTothom() {
        Quakecraft lIIlIIlIIIIIllI;
        Iterator<Player> lIIlIIlIIIIIlII = lIIlIIlIIIIIllI.getPlayers().iterator();
        while (Quakecraft.llIIIIlIlI((int)lIIlIIlIIIIIlII.hasNext())) {
            Player lIIlIIlIIIIIlll = lIIlIIlIIIIIlII.next();
            lIIlIIlIIIIIllI.teleportToRandomSpawn(lIIlIIlIIIIIlll);
            "".length();
            if (-" ".length() < 0) continue;
            return;
        }
    }

    private static boolean llIIIlIlll(int n) {
        return n > 0;
    }

    private static int llIIIlIIlI(long l, long l2) {
        return (int)(l LCMP l2);
    }

    @Override
    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent lIIlIIIIIIllIlI, Player lIIlIIIIIIlllII) {
        Quakecraft lIIlIIIIIIllllI;
        super.onPlayerRespawnAfterTick(lIIlIIIIIIllIlI, lIIlIIIIIIlllII);
        lIIlIIIIIIllllI.teleportToRandomSpawn(lIIlIIIIIIlllII);
        lIIlIIIIIIllllI.updateRailgun(lIIlIIIIIIlllII);
    }

    private static boolean llIIIlIllI(int n) {
        return n == 0;
    }

    private List<Entity> getAffectedEntities(Entity lIIlIIIIlIIllIl, int lIIlIIIIlIIllII, Double lIIlIIIIlIIIlII) {
        int lIIlIIIIlIIlIlI = lIIlIIIIlIIllIl.getEntityId();
        List lIIlIIIIlIIlIIl = lIIlIIIIlIIllIl.getNearbyEntities(2.8 + lIIlIIIIlIIIlII, 3.25 + lIIlIIIIlIIIlII, 2.8 + lIIlIIIIlIIIlII);
        ArrayList<Entity> lIIlIIIIlIIlIII = new ArrayList<Entity>();
        Iterator lIIlIIIIlIIIIII = lIIlIIIIlIIlIIl.iterator();
        while (Quakecraft.llIIIIlIlI((int)lIIlIIIIlIIIIII.hasNext())) {
            Entity lIIlIIIIlIIllll = (Entity)lIIlIIIIlIIIIII.next();
            if (Quakecraft.llIIIlIlll(lIIlIIIIlIIllII) && Quakecraft.llIIIllIlI(lIIlIIIIlIIllll.getEntityId(), lIIlIIIIlIIlIlI) && Quakecraft.llIIIlIllI((int)lIIlIIIIlIIllll.isDead()) && Quakecraft.llIIIIlIlI((int)lIIlIIIIlIIllll.isValid())) {
                Quakecraft lIIlIIIIlIIlllI;
                if (Quakecraft.llIIIIlIlI(lIIlIIIIlIIllll instanceof Player)) {
                    Player lIIlIIIIlIlIIIl = (Player)lIIlIIIIlIIllll;
                    Joc.PlayerInfo lIIlIIIIlIlIIII = lIIlIIIIlIIlllI.getPlayerInfo(lIIlIIIIlIlIIIl);
                    if (!Quakecraft.llIIIlIllI((int)lIIlIIIIlIlIIII.isImmune()) || !Quakecraft.llIIIlIllI((int)lIIlIIIIlIlIIII.isAFK())) continue;
                    if (Quakecraft.llIIIIlIlI((int)lIIlIIIIlIIlllI.isSpectator(lIIlIIIIlIlIIIl).booleanValue())) {
                        "".length();
                        if (null == null) continue;
                        return null;
                    }
                }
                "".length();
                lIIlIIIIlIIlIII.add(lIIlIIIIlIIllll);
                "".length();
                lIIlIIIIlIIlIII.addAll(lIIlIIIIlIIlllI.getAffectedEntities(lIIlIIIIlIIllll, lIIlIIIIlIIllII - llIIIlIl[3], lIIlIIIIlIIIlII));
            }
            "".length();
            if ("  ".length() >= ((181 ^ 132) & ~(150 ^ 167))) continue;
            return null;
        }
        "".length();
        lIIlIIIIlIIlIIl.addAll(lIIlIIIIlIIlIII);
        return Utils.removeDuplicates(lIIlIIIIlIIlIIl);
    }

    private static String lIlIllIlll(String lIIIlllllIlIlII, String lIIIlllllIlIIll) {
        lIIIlllllIlIlII = new String(Base64.getDecoder().decode(lIIIlllllIlIlII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIIlllllIlIIlI = new StringBuilder();
        char[] lIIIlllllIlIIIl = lIIIlllllIlIIll.toCharArray();
        int lIIIlllllIlIIII = llIIIlIl[2];
        char[] lIIIlllllIIlIlI = lIIIlllllIlIlII.toCharArray();
        int lIIIlllllIIlIIl = lIIIlllllIIlIlI.length;
        int lIIIlllllIIlIII = llIIIlIl[2];
        while (Quakecraft.llIIIIllII(lIIIlllllIIlIII, lIIIlllllIIlIIl)) {
            char lIIIlllllIlIlIl = lIIIlllllIIlIlI[lIIIlllllIIlIII];
            "".length();
            lIIIlllllIlIIlI.append((char)(lIIIlllllIlIlIl ^ lIIIlllllIlIIIl[lIIIlllllIlIIII % lIIIlllllIlIIIl.length]));
            ++lIIIlllllIlIIII;
            ++lIIIlllllIIlIII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(lIIIlllllIlIIlI);
    }

    private static boolean llIIIlIlII(int n) {
        return n >= 0;
    }

    private void playRailgunEffect(Player lIIlIIIlIIIlIIl, Location lIIlIIIlIIIlIII, boolean lIIlIIIlIIIIlll, boolean lIIlIIIlIIIIIII) {
        Quakecraft lIIlIIIlIIIIlII;
        FireworkEffect.Builder lIIlIIIlIIIIlIl = FireworkEffect.builder();
        "".length();
        lIIlIIIlIIIIlIl.withColor(lIIlIIIlIIIIlII.getColorRailgun(lIIlIIIlIIIlIIl));
        if (Quakecraft.llIIIlIllI((int)lIIlIIIlIIIIlll)) {
            "".length();
            lIIlIIIlIIIIlIl.with(FireworkEffect.Type.BALL);
            "".length();
            if (null != null) {
                return;
            }
        } else {
            "".length();
            lIIlIIIlIIIIlIl.with(FireworkEffect.Type.BALL_LARGE);
        }
        if (Quakecraft.llIIIIlIlI((int)lIIlIIIlIIIIIII)) {
            "".length();
            lIIlIIIlIIIIlIl.with(FireworkEffect.Type.STAR);
        }
        try {
            FireworkEffect lIIlIIIlIIIllll = lIIlIIIlIIIIlIl.build();
            Player[] lIIlIIIlIIIlllI = new Player[lIIlIIIlIIIIlII.getPlayers().size()];
            "".length();
            lIIlIIIlIIIIlII.getPlayers().toArray((T[])lIIlIIIlIIIlllI);
            Firework lIIlIIIlIIIllIl = (Firework)lIIlIIIlIIIIlII.getWorld().spawnEntity(lIIlIIIlIIIlIII, EntityType.FIREWORK);
            FireworkMeta lIIlIIIlIIIllII = lIIlIIIlIIIllIl.getFireworkMeta();
            lIIlIIIlIIIllII.addEffect(lIIlIIIlIIIllll);
            lIIlIIIlIIIllII.setPower(llIIIlIl[3]);
            lIIlIIIlIIIllIl.setFireworkMeta(lIIlIIIlIIIllII);
            lIIlIIIlIIIllIl.detonate();
            "".length();
        }
        catch (Exception lIIlIIIlIIIlIll) {
            lIIlIIIlIIIlIll.printStackTrace();
        }
        if ((195 ^ 199) <= "  ".length()) {
            return;
        }
    }

    private static boolean llIIIlllIl(int n) {
        return n < 0;
    }

    @Override
    protected void onPlayerDeath(PlayerDeathEvent lIIlIIIIIIlIIIl, Player lIIlIIIIIIlIIll) {
        Quakecraft lIIlIIIIIIlIIlI;
        super.onPlayerDeath(lIIlIIIIIIlIIIl, lIIlIIIIIIlIIll);
        lIIlIIIIIIlIIIl.setDeathMessage(lIllIIII[llIIIlIl[38]]);
        lIIlIIIIIIlIIIl.getDrops().clear();
    }

    private static boolean llIIIllIII(int n) {
        return n <= 0;
    }

    @Override
    protected void customJocIniciat() {
        Quakecraft lIIlIIlIIIllIll;
        lIIlIIlIIIllIll.setBlockBreakPlace(llIIIlIl[2]);
    }

    @Override
    public Boolean getGiveStartingItemsRespawn() {
        return llIIIlIl[2];
    }

    @Override
    protected void onProjectileHit(ProjectileHitEvent lIIlIIIIllIIIlI, Projectile lIIlIIIIlIllllI) {
        if (Quakecraft.llIIIIlIlI(lIIlIIIIllIIIlI.getEntity() instanceof Arrow) && Quakecraft.llIIIIlIlI(lIIlIIIIllIIIlI.getEntity().getShooter() instanceof Player)) {
            Quakecraft lIIlIIIIllIIIll;
            Player lIIlIIIIllIIlII = (Player)lIIlIIIIllIIIlI.getEntity().getShooter();
            lIIlIIIIllIIIll.railgunHitPlace(lIIlIIIIllIIlII, lIIlIIIIlIllllI);
            lIIlIIIIlIllllI.remove();
        }
    }

    private static boolean llIIIlIIll(Object object) {
        return object != null;
    }

    private static void llIIIIIlll() {
        llIIIlIl = new int[40];
        Quakecraft.llIIIlIl[0] = -4180 & 5979;
        Quakecraft.llIIIlIl[1] = 100 + 43 - 98 + 90 ^ 53 + 28 - 55 + 132;
        Quakecraft.llIIIlIl[2] = (238 ^ 188) & ~(60 ^ 110);
        Quakecraft.llIIIlIl[3] = " ".length();
        Quakecraft.llIIIlIl[4] = -" ".length() & (-1 & Integer.MAX_VALUE);
        Quakecraft.llIIIlIl[5] = "  ".length();
        Quakecraft.llIIIlIl[6] = -12303 & 14302;
        Quakecraft.llIIIlIl[7] = 23 + 118 - 99 + 108;
        Quakecraft.llIIIlIl[8] = -(-1473 & 18430) & (-12289 & 29695);
        Quakecraft.llIIIlIl[9] = -(13 ^ 43) & (-20753 & 22389);
        Quakecraft.llIIIlIl[10] = "   ".length();
        Quakecraft.llIIIlIl[11] = -10245 & 11644;
        Quakecraft.llIIIlIl[12] = 67 ^ 68 ^ "   ".length();
        Quakecraft.llIIIlIl[13] = -(-21505 & 32262) & (-16459 & 28415);
        Quakecraft.llIIIlIl[14] = 58 ^ 87 ^ (59 ^ 83);
        Quakecraft.llIIIlIl[15] = -20502 & 21501;
        Quakecraft.llIIIlIl[16] = 73 ^ 79;
        Quakecraft.llIIIlIl[17] = -19530 & 20079;
        Quakecraft.llIIIlIl[18] = 121 ^ 126;
        Quakecraft.llIIIlIl[19] = 205 ^ 182 ^ (38 ^ 85);
        Quakecraft.llIIIlIl[20] = 135 ^ 142;
        Quakecraft.llIIIlIl[21] = 110 + 29 - 11 + 20 ^ 70 + 16 - 5 + 77;
        Quakecraft.llIIIlIl[22] = 132 ^ 143;
        Quakecraft.llIIIlIl[23] = 66 ^ 78;
        Quakecraft.llIIIlIl[24] = 117 ^ 22 ^ (46 ^ 64);
        Quakecraft.llIIIlIl[25] = 41 ^ 39;
        Quakecraft.llIIIlIl[26] = 46 ^ 26 ^ (127 ^ 68);
        Quakecraft.llIIIlIl[27] = 82 ^ 66;
        Quakecraft.llIIIlIl[28] = 76 ^ 93;
        Quakecraft.llIIIlIl[29] = 90 ^ 18 ^ (123 ^ 33);
        Quakecraft.llIIIlIl[30] = 119 + 149 - 146 + 67 ^ 101 + 171 - 143 + 45;
        Quakecraft.llIIIlIl[31] = 52 ^ 32;
        Quakecraft.llIIIlIl[32] = 134 ^ 147;
        Quakecraft.llIIIlIl[33] = 17 ^ 7;
        Quakecraft.llIIIlIl[34] = 213 ^ 194;
        Quakecraft.llIIIlIl[35] = 91 ^ 96 ^ (83 ^ 12);
        Quakecraft.llIIIlIl[36] = 73 + 129 - 144 + 97 ^ 52 + 39 - -36 + 4;
        Quakecraft.llIIIlIl[37] = 74 ^ 94 ^ (41 ^ 39);
        Quakecraft.llIIIlIl[38] = 180 ^ 129 ^ (183 ^ 153);
        Quakecraft.llIIIlIl[39] = 102 ^ 122;
    }

    @Override
    public String getGameName() {
        return lIllIIII[llIIIlIl[2]];
    }

    @Override
    protected void donarEfectesInicials(Player lIIlIIlIIIIllIl) {
        Quakecraft lIIlIIlIIIIllII;
        super.donarEfectesInicials(lIIlIIlIIIIllIl);
        "".length();
        lIIlIIlIIIIllIl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, llIIIlIl[4], llIIIlIl[5], llIIIlIl[3]), llIIIlIl[3]);
        "".length();
        lIIlIIlIIIIllIl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, llIIIlIl[4], llIIIlIl[3], llIIIlIl[3]), llIIIlIl[3]);
    }

    public Quakecraft() {
        Quakecraft lIIlIIlIIllIlll;
        lIIlIIlIIllIlll.fPlayer = new FireworkEffectPlayer();
        lIIlIIlIIllIlll.maxT = llIIIlIl[0];
        lIIlIIlIIllIlll.pReloadRocket = new HashMap();
    }

    @Override
    protected int getFinishScore() {
        Quakecraft lIIlIIlIIllIIIl;
        int lIIlIIlIIllIIII = (int)(5.0 + (double)lIIlIIlIIllIIIl.getPlayers().size() * 2.5);
        if (Quakecraft.llIIIIlIII(lIIlIIlIIllIIII, llIIIlIl[1])) {
            lIIlIIlIIllIIII = llIIIlIl[1];
        }
        return lIIlIIlIIllIIII;
    }
}

