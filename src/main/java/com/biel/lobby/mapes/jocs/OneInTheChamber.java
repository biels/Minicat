/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.potion.Potion
 *  org.bukkit.potion.PotionType
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class OneInTheChamber
extends JocScoreRace {
    private static final /* synthetic */ String[] lIlIllIll;
    private static final /* synthetic */ int[] lIllIIlII;

    private static boolean lIlIIIIIIll(int n, int n2) {
        return n < n2;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIllIIIIIllllI) {
        OneInTheChamber lIllIIIIIlllll;
        ArrayList<ItemStack> lIllIIIIlIIIll = new ArrayList<ItemStack>();
        "".length();
        lIllIIIIlIIIll.add(new ItemStack(Material.IRON_SWORD));
        "".length();
        lIllIIIIlIIIll.add(new ItemStack(Material.BOW));
        "".length();
        lIllIIIIlIIIll.add(new ItemStack(Material.ARROW));
        Color lIllIIIIlIIIlI = lIllIIIIIlllll.getDeterministicColorForPlayer(lIllIIIIIllllI, lIllIIlII[3]);
        Color lIllIIIIlIIIIl = lIllIIIIIlllll.getDeterministicColorForPlayer(lIllIIIIIllllI, lIllIIlII[4]);
        "".length();
        lIllIIIIlIIIll.add(GUtils.createColoredArmor((Material)Material.LEATHER_HELMET, (Color)lIllIIIIlIIIIl));
        "".length();
        lIllIIIIlIIIll.add(GUtils.createColoredArmor((Material)Material.LEATHER_CHESTPLATE, (Color)lIllIIIIlIIIlI));
        "".length();
        lIllIIIIlIIIll.add(GUtils.createColoredArmor((Material)Material.LEATHER_LEGGINGS, (Color)lIllIIIIlIIIIl));
        "".length();
        lIllIIIIlIIIll.add(GUtils.createColoredArmor((Material)Material.LEATHER_BOOTS, (Color)lIllIIIIlIIIlI));
        Potion lIllIIIIlIIIII = new Potion(PotionType.INSTANT_DAMAGE);
        lIllIIIIlIIIII.setSplash(lIllIIlII[4]);
        "".length();
        lIllIIIIlIIIll.add(lIllIIIIlIIIII.toItemStack(lIllIIlII[4]));
        return lIllIIIIlIIIll;
    }

    private static boolean lIIllllllIl(int n) {
        return n == 0;
    }

    static {
        OneInTheChamber.lIIlllllIll();
        OneInTheChamber.lIIllllIlII();
    }

    @Override
    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIlIlllllIIlll, Player lIlIlllllIlIll, Player lIlIlllllIIlIl, boolean lIlIlllllIlIIl) {
        OneInTheChamber lIlIlllllIlIII;
        super.onPlayerDamageByPlayer(lIlIlllllIIlll, lIlIlllllIlIll, lIlIlllllIIlIl, lIlIlllllIlIIl);
        if (OneInTheChamber.lIIllllllII((int)lIlIlllllIlIIl)) {
            lIlIlllllIIlll.setDamage(100.0);
        }
    }

    @Override
    protected void teletransportarTothom() {
        OneInTheChamber lIllIIIlIIIIIl;
        Iterator<Player> lIllIIIIllllll = lIllIIIlIIIIIl.getPlayers().iterator();
        while (OneInTheChamber.lIIllllllII((int)lIllIIIIllllll.hasNext())) {
            Player lIllIIIlIIIIlI = lIllIIIIllllll.next();
            lIllIIIlIIIIIl.teleportToRandomSpawn(lIllIIIlIIIIlI);
            "".length();
            if (((85 ^ 127) & ~(76 ^ 102)) <= ((110 ^ 85) & ~(124 ^ 71))) continue;
            return;
        }
    }

    private static String lIIllIlIlll(String lIlIlllIllIIIl, String lIlIlllIllIIII) {
        try {
            SecretKeySpec lIlIlllIllIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIlllIllIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIlllIllIlIl = Cipher.getInstance("Blowfish");
            lIlIlllIllIlIl.init(lIllIIlII[2], lIlIlllIllIllI);
            return new String(lIlIlllIllIlIl.doFinal(Base64.getDecoder().decode(lIlIlllIllIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIlllIllIIlI) {
            lIlIlllIllIIlI.printStackTrace();
            return null;
        }
    }

    @Override
    protected void teleportToRandomSpawn(Player lIllIIIIllIllI) {
        OneInTheChamber lIllIIIIllIlll;
        Location lIllIIIIlllIII = lIllIIIIllIlll.getRandomSpawnLoc();
        "".length();
        lIllIIIIllIllI.teleport(lIllIIIIlllIII);
    }

    private static String lIIllIlIllI(String lIlIllIlllllIl, String lIlIllIlllllII) {
        lIlIllIlllllIl = new String(Base64.getDecoder().decode(lIlIllIlllllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlIllIllllIll = new StringBuilder();
        char[] lIlIllIllllIlI = lIlIllIlllllII.toCharArray();
        int lIlIllIllllIIl = lIllIIlII[3];
        char[] lIlIllIlllIIll = lIlIllIlllllIl.toCharArray();
        int lIlIllIlllIIlI = lIlIllIlllIIll.length;
        int lIlIllIlllIIIl = lIllIIlII[3];
        while (OneInTheChamber.lIlIIIIIIll(lIlIllIlllIIIl, lIlIllIlllIIlI)) {
            char lIlIllIllllllI = lIlIllIlllIIll[lIlIllIlllIIIl];
            "".length();
            lIlIllIllllIll.append((char)(lIlIllIllllllI ^ lIlIllIllllIlI[lIlIllIllllIIl % lIlIllIllllIlI.length]));
            ++lIlIllIllllIIl;
            ++lIlIllIlllIIIl;
            "".length();
            if ("   ".length() > -" ".length()) continue;
            return null;
        }
        return String.valueOf(lIlIllIllllIll);
    }

    @Override
    protected int getFinishScore() {
        OneInTheChamber lIllIIIlIIlIII;
        return Math.min(lIllIIlII[0], lIllIIlII[1] + lIllIIIlIIlIII.getPlayers().size() * lIllIIlII[2]);
    }

    private static void lIIlllllIll() {
        lIllIIlII = new int[5];
        OneInTheChamber.lIllIIlII[0] = 110 + 119 - 60 + 8 ^ 142 + 37 - 136 + 125;
        OneInTheChamber.lIllIIlII[1] = 201 ^ 195;
        OneInTheChamber.lIllIIlII[2] = "  ".length();
        OneInTheChamber.lIllIIlII[3] = (225 ^ 139 ^ (98 ^ 79)) & (52 ^ 80 ^ (131 ^ 160) ^ -" ".length());
        OneInTheChamber.lIllIIlII[4] = " ".length();
    }

    public OneInTheChamber() {
        OneInTheChamber lIllIIIlIIlIlI;
    }

    private static boolean lIIllllllII(int n) {
        return n != 0;
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent lIlIlllllllIII, Player lIlIllllllllII, Player lIlIllllllIlIl) {
        OneInTheChamber lIlIlllllllllI;
        super.onPlayerDeathByPlayer(lIlIlllllllIII, lIlIllllllllII, lIlIllllllIlIl);
        lIlIlllllllllI.incrementScore(lIlIllllllIlIl);
        ItemStack lIlIlllllllIlI = new ItemStack(Material.ARROW);
        ItemStack[] arritemStack = new ItemStack[lIllIIlII[4]];
        arritemStack[OneInTheChamber.lIllIIlII[3]] = lIlIlllllllIlI;
        "".length();
        lIlIllllllIlIl.getInventory().addItem(arritemStack);
    }

    @Override
    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent lIlIllllIIlIlI, Player lIlIllllIIllIl) {
        OneInTheChamber lIlIllllIlIIII;
        super.onPlayerRespawnAfterTick(lIlIllllIIlIlI, lIlIllllIIllIl);
        lIlIllllIlIIII.teleportToRandomSpawn(lIlIllllIIllIl);
    }

    @Override
    public String getGameName() {
        return lIlIllIll[lIllIIlII[3]];
    }

    private Location getRandomSpawnLoc() {
        OneInTheChamber lIllIIIIlIlllI;
        ArrayList<Location> lIllIIIIlIllll = lIllIIIIlIlllI.pMapaActual().ObtenirLocations(lIlIllIll[lIllIIlII[4]], lIllIIIIlIlllI.world);
        if (OneInTheChamber.lIIllllllIl(lIllIIIIlIllll.size())) {
            return new Location(lIllIIIIlIlllI.world, -2343.0, 62.0, 647.0);
        }
        Location lIllIIIIllIIII = new Location(lIllIIIIlIlllI.world, (double)Utils.NombreEntre(lIllIIIIlIllll.get(lIllIIlII[3]).getBlockX(), lIllIIIIlIllll.get(lIllIIlII[4]).getBlockX()), lIllIIIIlIllll.get(lIllIIlII[3]).getY() + 1.0, (double)Utils.NombreEntre(lIllIIIIlIllll.get(lIllIIlII[3]).getBlockZ(), lIllIIIIlIllll.get(lIllIIlII[4]).getBlockZ()));
        return lIllIIIIllIIII;
    }

    private static void lIIllllIlII() {
        lIlIllIll = new String[lIllIIlII[2]];
        OneInTheChamber.lIlIllIll[OneInTheChamber.lIllIIlII[3]] = OneInTheChamber.lIIllIlIllI("HTomOywGPCYxKjM5IRcw", "RTCrB");
        OneInTheChamber.lIlIllIll[OneInTheChamber.lIllIIlII[4]] = OneInTheChamber.lIIllIlIlll("ux13A3fM/II=", "AuQvE");
    }
}

