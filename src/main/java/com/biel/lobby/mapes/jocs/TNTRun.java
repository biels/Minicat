/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.Com;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.JocLastStanding;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class TNTRun
extends JocLastStanding {
    /* synthetic */ int temps;
    /* synthetic */ ArrayList<Player> immunePlayers;
    /* synthetic */ ArrayList<Player> tntPlayers;
    /* synthetic */ int taskId;
    private static final /* synthetic */ String[] llIIIII;
    /* synthetic */ int round;
    private static final /* synthetic */ int[] lIIIIIII;

    public void ProgTask() {
        TNTRun llIIIIIIIllllIl;
        llIIIIIIIllllIl.taskId = llIIIIIIIllllIl.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)llIIIIIIIllllIl.plugin, () -> {
            TNTRun lIllllllllIIlII;
            lIllllllllIIlII.temps -= lIIIIIII[5];
            if (TNTRun.llIllllIl((int)Utils.Possibilitat(lIIIIIII[5])) && TNTRun.llIllllIl((int)Utils.Possibilitat(lIIIIIII[19]))) {
                lIllllllllIIlII.hipervelocitat();
            }
            lIllllllllIIlII.updateScoreBoards();
            if (TNTRun.lllIIlIlI(lIllllllllIIlII.temps)) {
                lIllllllllIIlII.plugin.getServer().getScheduler().cancelTask(lIllllllllIIlII.taskId);
                lIllllllllIIlII.endRound();
            }
        }, 40L, 10L);
    }

    void passarTNT(Player llIIIIIlIIIllII, Player llIIIIIlIIIIllI) {
        TNTRun llIIIIIlIIIlllI;
        if (TNTRun.llIllllIl((int)llIIIIIlIIIlllI.isImmune(llIIIIIlIIIIllI).booleanValue())) {
            return;
        }
        if (TNTRun.llIllllll((int)llIIIIIlIIIlllI.hasTNT(llIIIIIlIIIllII).booleanValue())) {
            return;
        }
        if (TNTRun.llIllllIl((int)llIIIIIlIIIlllI.hasTNT(llIIIIIlIIIIllI).booleanValue())) {
            return;
        }
        llIIIIIlIIIlllI.treureTNT(llIIIIIlIIIllII);
        llIIIIIlIIIlllI.posarTNT(llIIIIIlIIIIllI);
        llIIIIIlIIIlllI.giveImmunity(llIIIIIlIIIllII, lIIIIIII[2]);
        llIIIIIlIIIlllI.updateEffects(lIIIIIII[1]);
        llIIIIIlIIIlllI.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(llIIIIIlIIIllII.getName()).append((Object)ChatColor.WHITE).append(llIIIII[lIIIIIII[5]]).append((Object)ChatColor.RED).append(llIIIIIlIIIIllI.getName())));
    }

    void endRound() {
        TNTRun llIIIIIIlIIIIII;
        llIIIIIIlIIIIII.explotarJugadors();
        if (TNTRun.lllIIIllI(llIIIIIIlIIIIII.getAlivePlayers().size(), lIIIIIII[5])) {
            llIIIIIIlIIIIII.startRound();
        }
    }

    private static String lIlIlllIl(String lIllllllIIIIlIl, String lIllllllIIIIlII) {
        lIllllllIIIIlIl = new String(Base64.getDecoder().decode(lIllllllIIIIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIllllllIIIlIII = new StringBuilder();
        char[] lIllllllIIIIlll = lIllllllIIIIlII.toCharArray();
        int lIllllllIIIIllI = lIIIIIII[1];
        char[] lIlllllIlllllll = lIllllllIIIIlIl.toCharArray();
        int lIlllllIlllllIl = lIlllllIlllllll.length;
        int lIlllllIlllllII = lIIIIIII[1];
        while (TNTRun.lllIIIlIl(lIlllllIlllllII, lIlllllIlllllIl)) {
            char lIllllllIIIlIll = lIlllllIlllllll[lIlllllIlllllII];
            "".length();
            lIllllllIIIlIII.append((char)(lIllllllIIIlIll ^ lIllllllIIIIlll[lIllllllIIIIllI % lIllllllIIIIlll.length]));
            ++lIllllllIIIIllI;
            ++lIlllllIlllllII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(lIllllllIIIlIII);
    }

    @Override
    protected void customJocIniciat() {
        TNTRun llIIIIIlllIlIll;
        super.customJocIniciat();
        llIIIIIlllIlIll.startRound();
    }

    private static boolean lllIIlIIl(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llIIIIIllllllII) {
        return null;
    }

    @Override
    protected void setCustomGameRules() {
    }

    int getTNTAmount() {
        TNTRun llIIIIIIllIlllI;
        return (int)Math.ceil((double)llIIIIIIllIlllI.getAlivePlayers().size() / 3.0);
    }

    private static boolean lllIIlIlI(int n) {
        return n <= 0;
    }

    private static boolean lllIIIllI(int n, int n2) {
        return n > n2;
    }

    @Override
    protected void teletransportarTothom() {
        TNTRun llIIIIIllllIIIl;
        Iterator<Player> llIIIIIlllIllll = llIIIIIllllIIIl.getPlayers().iterator();
        while (TNTRun.llIllllIl((int)llIIIIIlllIllll.hasNext())) {
            Player llIIIIIllllIIlI = llIIIIIlllIllll.next();
            "".length();
            llIIIIIllllIIlI.teleport(llIIIIIllllIIIl.getWorld().getSpawnLocation());
            "".length();
            if ("   ".length() != (69 ^ 65)) continue;
            return;
        }
    }

    private static String lIlIllIll(String lIllllllIIlllII, String lIllllllIIllIll) {
        try {
            SecretKeySpec lIllllllIlIIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllllllIIllIll.getBytes(StandardCharsets.UTF_8)), lIIIIIII[8]), "DES");
            Cipher lIllllllIlIIIII = Cipher.getInstance("DES");
            lIllllllIlIIIII.init(lIIIIIII[2], lIllllllIlIIIIl);
            return new String(lIllllllIlIIIII.doFinal(Base64.getDecoder().decode(lIllllllIIlllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllllllIIlllll) {
            lIllllllIIlllll.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getBaseSkillUnlockerAmount() {
        return lIIIIIII[1];
    }

    void posarTNT(Player llIIIIIIllllllI) {
        TNTRun llIIIIIIlllllIl;
        llIIIIIIllllllI.getInventory().setHelmet(new ItemStack(Material.TNT));
        llIIIIIIllllllI.getInventory().setItem(lIIIIIII[8], new ItemStack(Material.TNT));
        llIIIIIIllllllI.updateInventory();
        if (TNTRun.llIllllll((int)llIIIIIIlllllIl.tntPlayers.contains((Object)llIIIIIIllllllI))) {
            "".length();
            llIIIIIIlllllIl.tntPlayers.add(llIIIIIIllllllI);
            llIIIIIIllllllI.sendMessage(String.valueOf(new StringBuilder().append(llIIIII[lIIIIIII[2]]).append((Object)ChatColor.DARK_RED).append(llIIIII[lIIIIIII[7]]).append((Object)ChatColor.WHITE).append(llIIIII[lIIIIIII[3]])));
        }
    }

    void explotarJugadors() {
        TNTRun llIIIIIIlIIlllI;
        Iterator<Player> llIIIIIIlIIllII = llIIIIIIlIIlllI.tntPlayers.iterator();
        while (TNTRun.llIllllIl((int)llIIIIIIlIIllII.hasNext())) {
            Player llIIIIIIlIIllll = llIIIIIIlIIllII.next();
            llIIIIIIlIIlllI.removeIfAlive(llIIIIIIlIIllll);
            Com.teleportPlayerToLobby(llIIIIIIlIIllll);
            "".length();
            if ("  ".length() > " ".length()) continue;
            return;
        }
    }

    private static String lIlIlllII(String lIllllllIlllIlI, String lIllllllIlllIIl) {
        try {
            SecretKeySpec lIllllllIllllIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllllllIlllIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIllllllIllllII = Cipher.getInstance("Blowfish");
            lIllllllIllllII.init(lIIIIIII[2], lIllllllIllllIl);
            return new String(lIllllllIllllII.doFinal(Base64.getDecoder().decode(lIllllllIlllIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllllllIlllIll) {
            lIllllllIlllIll.printStackTrace();
            return null;
        }
    }

    public TNTRun() {
        TNTRun llIIIIlIIIIIIII;
        llIIIIlIIIIIIII.tntPlayers = new ArrayList();
        llIIIIlIIIIIIII.immunePlayers = new ArrayList();
        llIIIIlIIIIIIII.temps = lIIIIIII[0];
        llIIIIlIIIIIIII.round = lIIIIIII[1];
        llIIIIlIIIIIIII.taskId = lIIIIIII[1];
    }

    private static void llIlllIll() {
        lIIIIIII = new int[21];
        TNTRun.lIIIIIII[0] = 15 ^ 106 ^ (219 ^ 130);
        TNTRun.lIIIIIII[1] = (147 ^ 135 ^ (110 ^ 100)) & (191 ^ 155 ^ (252 ^ 198) ^ -" ".length());
        TNTRun.lIIIIIII[2] = "  ".length();
        TNTRun.lIIIIIII[3] = 109 ^ 105;
        TNTRun.lIIIIIII[4] = -(-19591 & 19895) & (-74 & 16377);
        TNTRun.lIIIIIII[5] = " ".length();
        TNTRun.lIIIIIII[6] = 23 ^ 78 ^ (24 ^ 85);
        TNTRun.lIIIIIII[7] = "   ".length();
        TNTRun.lIIIIIII[8] = 160 + 169 - 272 + 125 ^ 128 + 0 - 47 + 109;
        TNTRun.lIIIIIII[9] = 108 + 4 - 59 + 96 ^ 61 + 56 - -2 + 25;
        TNTRun.lIIIIIII[10] = 154 + 29 - 49 + 55 ^ 92 + 113 - 200 + 182;
        TNTRun.lIIIIIII[11] = 7 + 30 - -108 + 41 ^ 100 + 5 - -10 + 74;
        TNTRun.lIIIIIII[12] = 47 ^ 2;
        TNTRun.lIIIIIII[13] = "   ".length() ^ (154 ^ 128);
        TNTRun.lIIIIIII[14] = 69 ^ 80 ^ (78 ^ 87);
        TNTRun.lIIIIIII[15] = 77 ^ 68;
        TNTRun.lIIIIIII[16] = 121 ^ 115;
        TNTRun.lIIIIIII[17] = 62 + 158 - 149 + 90 ^ 153 + 92 - 85 + 10;
        TNTRun.lIIIIIII[18] = 115 ^ 87 ^ (238 ^ 199);
        TNTRun.lIIIIIII[19] = 15 ^ 95;
        TNTRun.lIIIIIII[20] = 200 ^ 154 ^ (14 ^ 82);
    }

    Boolean hasTNT(Player llIIIIIlIllIIII) {
        TNTRun llIIIIIlIllIIlI;
        return llIIIIIlIllIIlI.tntPlayers.contains((Object)llIIIIIlIllIIII);
    }

    void dispersarJugadors() {
        TNTRun llIIIIIlIllllII;
        Iterator<Player> llIIIIIlIlllIll = llIIIIIlIllllII.getPlayers().iterator();
        while (TNTRun.llIllllIl((int)llIIIIIlIlllIll.hasNext())) {
            Player llIIIIIlIlllllI = llIIIIIlIlllIll.next();
            Vector llIIIIIlIllllll = Vector.getRandom();
            "".length();
            llIIIIIlIllllll.normalize();
            "".length();
            llIIIIIlIllllll.multiply(Utils.NombreEntre(lIIIIIII[7], lIIIIIII[8]));
            llIIIIIlIlllllI.setVelocity(llIIIIIlIllllll);
            "".length();
            if (" ".length() == " ".length()) continue;
            return;
        }
    }

    static {
        TNTRun.llIlllIll();
        TNTRun.lIllIIIlI();
    }

    void giveImmunity(Player llIIIIIlIlIIIII, int llIIIIIlIIlllll) {
        TNTRun llIIIIIlIIllllI;
        if (TNTRun.llIllllll((int)llIIIIIlIIllllI.isImmune(llIIIIIlIlIIIII).booleanValue())) {
            "".length();
            llIIIIIlIIllllI.immunePlayers.add(llIIIIIlIlIIIII);
            llIIIIIlIlIIIII.getInventory().setHelmet(new ItemStack(Material.QUARTZ_BLOCK));
            llIIIIIlIlIIIII.getInventory().setItem(lIIIIIII[8], new ItemStack(Material.QUARTZ_BLOCK));
            "".length();
            llIIIIIlIIllllI.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)llIIIIIlIIllllI.plugin, () -> {
                TNTRun lIlllllllIlIlIl;
                llIIIIIlIlIIIII.getInventory().setHelmet(null);
                llIIIIIlIlIIIII.getInventory().clear(lIIIIIII[8]);
                "".length();
                lIlllllllIlIlIl.immunePlayers.remove((Object)llIIIIIlIlIIIII);
            }, 40L);
        }
    }

    void startRound() {
        TNTRun llIIIIIIlIIIIlI;
        llIIIIIIlIIIIlI.round += lIIIIIII[5];
        llIIIIIIlIIIIlI.temps = lIIIIIII[0];
        llIIIIIIlIIIIlI.sendGlobalMessage(String.valueOf(new StringBuilder().append(llIIIII[lIIIIIII[9]]).append(Integer.toString(llIIIIIIlIIIIlI.round)).append(llIIIII[lIIIIIII[10]])));
        Utils.clearPlayers(llIIIIIIlIIIIlI.getPlayers());
        llIIIIIIlIIIIlI.teletransportarTothom();
        llIIIIIIlIIIIlI.tntInicial();
        llIIIIIIlIIIIlI.updateEffects(lIIIIIII[1]);
        llIIIIIIlIIIIlI.ProgTask();
    }

    Boolean isImmune(Player llIIIIIlIlIIllI) {
        TNTRun llIIIIIlIlIIlIl;
        return llIIIIIlIlIIlIl.immunePlayers.contains((Object)llIIIIIlIlIIllI);
    }

    @Override
    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent llIIIIIIIIIlIll, Player llIIIIIIIIIlIlI, Player llIIIIIIIIIlIIl, boolean llIIIIIIIIIlIII) {
        TNTRun llIIIIIIIIlIIIl;
        super.onPlayerDamageByPlayer(llIIIIIIIIIlIll, llIIIIIIIIIlIlI, llIIIIIIIIIlIIl, llIIIIIIIIIlIII);
        llIIIIIIIIlIIIl.passarTNT(llIIIIIIIIIlIIl, llIIIIIIIIIlIlI);
    }

    void tntInicial() {
        TNTRun llIIIIIIllIIIIl;
        ArrayList llIIIIIIlIlllll = (ArrayList)llIIIIIIllIIIIl.getAlivePlayers().clone();
        Collections.shuffle(llIIIIIIlIlllll);
        int llIIIIIIlIllllI = llIIIIIIllIIIIl.getTNTAmount();
        int llIIIIIIlIlllIl = lIIIIIII[1];
        while (TNTRun.lllIIIlIl(llIIIIIIlIlllIl, llIIIIIIlIllllI)) {
            llIIIIIIllIIIIl.posarTNT((Player)llIIIIIIlIlllll.get(lIIIIIII[1]));
            ++llIIIIIIlIlllIl;
            "".length();
            if ((134 ^ 136 ^ (191 ^ 181)) > "   ".length()) continue;
            return;
        }
    }

    private static boolean llIllllIl(int n) {
        return n != 0;
    }

    private static boolean lllIIIlIl(int n, int n2) {
        return n < n2;
    }

    @Override
    protected void onPlayerDamage(EntityDamageEvent lIlllllllllIIll, Player lIllllllllIlllI) {
        TNTRun lIlllllllllIIII;
        super.onPlayerDamage(lIlllllllllIIll, lIllllllllIlllI);
        lIlllllllllIIll.setDamage(0.2);
        if (TNTRun.llIllllIl((int)lIlllllllllIIII.JocIniciat.booleanValue())) {
            if (TNTRun.lllIIlIIl((Object)lIlllllllllIIll.getCause(), (Object)EntityDamageEvent.DamageCause.VOID)) {
                lIlllllllllIIll.setDamage(40.0);
            }
            if (TNTRun.lllIIlIIl((Object)lIlllllllllIIll.getCause(), (Object)EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
                lIlllllllllIIII.removeIfAlive(lIllllllllIlllI);
            }
        }
    }

    void treureTNT(Player llIIIIIIlllIIII) {
        TNTRun llIIIIIIlllIIIl;
        llIIIIIIlllIIII.getInventory().setHelmet(null);
        llIIIIIIlllIIII.getInventory().clear(lIIIIIII[8]);
        llIIIIIIlllIIII.updateInventory();
        if (TNTRun.llIllllIl((int)llIIIIIIlllIIIl.tntPlayers.contains((Object)llIIIIIIlllIIII))) {
            "".length();
            llIIIIIIlllIIIl.tntPlayers.remove((Object)llIIIIIIlllIIII);
        }
    }

    public void hipervelocitat() {
        TNTRun llIIIIIIIllIIIl;
        int llIIIIIIIllIIll = Utils.NombreEntre(lIIIIIII[2], lIIIIIII[8]);
        llIIIIIIIllIIIl.updateEffects(lIIIIIII[5]);
        llIIIIIIIllIIIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(llIIIII[lIIIIIII[11]]).append(Integer.toString(llIIIIIIIllIIll)).append(llIIIII[lIIIIIII[8]])));
        "".length();
        llIIIIIIIllIIIl.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)llIIIIIIIllIIIl.plugin, () -> {
            TNTRun lIllllllllIlIll;
            lIllllllllIlIll.updateEffects(lIIIIIII[1]);
        }, 40L);
    }

    private static boolean lllIIlIII(int n, int n2) {
        return n <= n2;
    }

    private static void lIllIIIlI() {
        llIIIII = new String[lIIIIIII[20]];
        TNTRun.llIIIII[TNTRun.lIIIIIII[1]] = TNTRun.lIlIllIll("gq6JfLivz6w=", "jeHRV");
        TNTRun.llIIIII[TNTRun.lIIIIIII[5]] = TNTRun.lIlIlllII("NDPUBqj9AQ6zHRtnWsZXTo+ujqyBL7Fw", "Ccwvd");
        TNTRun.llIIIII[TNTRun.lIIIIIII[2]] = TNTRun.lIlIllIll("uYHNfV4OMGi99knUAlzB4A==", "lYpFF");
        TNTRun.llIIIII[TNTRun.lIIIIIII[7]] = TNTRun.lIlIllIll("c2jwKGvPpQY=", "mLmMF");
        TNTRun.llIIIII[TNTRun.lIIIIIII[3]] = TNTRun.lIlIlllIl("UA==", "qhXLz");
        TNTRun.llIIIII[TNTRun.lIIIIIII[9]] = TNTRun.lIlIlllII("/XbHesX64KU=", "QBVGH");
        TNTRun.llIIIII[TNTRun.lIIIIIII[10]] = TNTRun.lIlIllIll("0jPxqKyIGu1WPI+o5/NJzw==", "MoYXW");
        TNTRun.llIIIII[TNTRun.lIIIIIII[11]] = TNTRun.lIlIllIll("4fUT2FQJ5Ng1G1Ewytqa+1+JB48K8wby", "KJOMe");
        TNTRun.llIIIII[TNTRun.lIIIIIII[8]] = TNTRun.lIlIlllII("07G2ZiJgq+8=", "ClXDd");
        TNTRun.llIIIII[TNTRun.lIIIIIII[15]] = TNTRun.lIlIlllII("GLtdEqJ8k10=", "kjGir");
        TNTRun.llIIIII[TNTRun.lIIIIIII[16]] = TNTRun.lIlIlllIl("", "JtWlZ");
        TNTRun.llIIIII[TNTRun.lIIIIIII[17]] = TNTRun.lIlIlllII("g3OAyNTQNsE=", "HaUeX");
        TNTRun.llIIIII[TNTRun.lIIIIIII[14]] = TNTRun.lIlIlllIl("OwEwKAAeBiRzRA==", "qtWId");
        TNTRun.llIIIII[TNTRun.lIIIIIII[18]] = TNTRun.lIlIlllII("7Pdna59uKQsxT3EiBDK2Vw==", "VuGhU");
    }

    @Override
    protected void updateScoreBoard(Player llIIIIIIIIllIlI) {
        TNTRun llIIIIIIIIllIIl;
        if (TNTRun.llIllllIl((int)llIIIIIIIIllIIl.JocIniciat.booleanValue()) && TNTRun.llIllllll((int)llIIIIIIIIllIIl.JocFinalitzat.booleanValue())) {
            ArrayList<String> llIIIIIIIIlllIl = new ArrayList<String>();
            ChatColor llIIIIIIIIlllII = ChatColor.DARK_GREEN;
            if (TNTRun.lllIIlIII(llIIIIIIIIllIIl.temps, lIIIIIII[12])) {
                llIIIIIIIIlllII = ChatColor.GREEN;
            }
            if (TNTRun.lllIIlIII(llIIIIIIIIllIIl.temps, lIIIIIII[13])) {
                llIIIIIIIIlllII = ChatColor.YELLOW;
            }
            if (TNTRun.lllIIlIII(llIIIIIIIIllIIl.temps, lIIIIIII[14])) {
                llIIIIIIIIlllII = ChatColor.RED;
            }
            if (TNTRun.lllIIlIII(llIIIIIIIIllIIl.temps, lIIIIIII[3])) {
                llIIIIIIIIlllII = ChatColor.DARK_RED;
            }
            "".length();
            llIIIIIIIIlllIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(llIIIII[lIIIIIII[15]]).append((Object)llIIIIIIIIlllII).append(llIIIII[lIIIIIII[16]]).append((Object)ChatColor.BOLD).append(Integer.toString(llIIIIIIIIllIIl.temps))));
            "".length();
            llIIIIIIIIlllIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llIIIII[lIIIIIII[17]]).append((Object)ChatColor.WHITE).append(Integer.toString(llIIIIIIIIllIIl.round))));
            "".length();
            llIIIIIIIIlllIl.add(String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(llIIIII[lIIIIIII[14]]).append((Object)ChatColor.WHITE).append(Integer.toString(llIIIIIIIIllIIl.getAlivePlayers().size()))));
            ScoreBoardUpdater.setScoreBoard(llIIIIIIIIllIIl.getPlayers(), llIIIII[lIIIIIII[18]], llIIIIIIIIlllIl, null);
        }
    }

    private static boolean llIllllll(int n) {
        return n == 0;
    }

    void updateEffects(Boolean llIIIIIllIlIIII) {
        TNTRun llIIIIIllIIllll;
        Iterator<Player> llIIIIIllIIllII = llIIIIIllIIllll.getPlayers().iterator();
        while (TNTRun.llIllllIl((int)llIIIIIllIIllII.hasNext())) {
            Player llIIIIIllIlIlII = llIIIIIllIIllII.next();
            Utils.clearEffects(llIIIIIllIlIlII);
            if (TNTRun.llIllllll((int)llIIIIIllIlIIII.booleanValue())) {
                int llIIIIIllIlIllI = lIIIIIII[2];
                if (TNTRun.llIllllIl((int)llIIIIIllIIllll.hasTNT(llIIIIIllIlIlII).booleanValue())) {
                    llIIIIIllIlIllI = lIIIIIII[3];
                }
                "".length();
                llIIIIIllIlIlII.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, lIIIIIII[4], lIIIIIII[1], lIIIIIII[5]), lIIIIIII[5]);
                "".length();
                llIIIIIllIlIlII.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIIIIIII[4], llIIIIIllIlIllI, lIIIIIII[5]), lIIIIIII[5]);
                "".length();
                if (null != null) {
                    return;
                }
            } else {
                "".length();
                llIIIIIllIlIlII.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIIIIIII[4], lIIIIIII[6], lIIIIIII[5]), lIIIIIII[5]);
            }
            "".length();
            if ("   ".length() > 0) continue;
            return;
        }
    }

    @Override
    public String getGameName() {
        return llIIIII[lIIIIIII[1]];
    }
}

