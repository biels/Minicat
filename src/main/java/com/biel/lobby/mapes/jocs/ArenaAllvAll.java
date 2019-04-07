/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
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

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocScoreRace;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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

public class ArenaAllvAll
extends JocScoreRace {
    private static final /* synthetic */ String[] lIIIllII;
    private static final /* synthetic */ int[] lIIIllll;

    static {
        ArenaAllvAll.lIIIlllllI();
        ArenaAllvAll.lIIIIIIIlI();
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lIlIlIllllIlllI) {
        ArrayList<ItemStack> lIlIlIllllIllIl = new ArrayList<ItemStack>();
        "".length();
        lIlIlIllllIllIl.add(new ItemStack(Material.WOOD_SWORD, lIIIllll[2]));
        "".length();
        lIlIlIllllIllIl.add(new ItemStack(Material.BOW, lIIIllll[2]));
        Potion lIlIlIllllIllII = new Potion(PotionType.INSTANT_DAMAGE);
        lIlIlIllllIllII.setSplash(lIIIllll[2]);
        "".length();
        lIlIlIllllIllIl.add(lIlIlIllllIllII.toItemStack(lIIIllll[2]));
        Potion lIlIlIllllIlIll = new Potion(PotionType.SLOWNESS);
        lIlIlIllllIlIll.setSplash(lIIIllll[2]);
        "".length();
        lIlIlIllllIllIl.add(lIlIlIllllIlIll.toItemStack(lIIIllll[0]));
        "".length();
        lIlIlIllllIllIl.add(new ItemStack(Material.ARROW, lIIIllll[3]));
        "".length();
        lIlIlIllllIllIl.add(new ItemStack(Material.CHAINMAIL_HELMET, lIIIllll[2]));
        "".length();
        lIlIlIllllIllIl.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE, lIIIllll[2]));
        "".length();
        lIlIlIllllIllIl.add(new ItemStack(Material.CHAINMAIL_LEGGINGS, lIIIllll[2]));
        "".length();
        lIlIlIllllIllIl.add(new ItemStack(Material.CHAINMAIL_BOOTS, lIIIllll[2]));
        return lIlIlIllllIllIl;
    }

    private static void lIIIlllllI() {
        lIIIllll = new int[7];
        ArenaAllvAll.lIIIllll[0] = "  ".length();
        ArenaAllvAll.lIIIllll[1] = (26 ^ 18) & ~(39 ^ 47);
        ArenaAllvAll.lIIIllll[2] = " ".length();
        ArenaAllvAll.lIIIllll[3] = 77 ^ 72;
        ArenaAllvAll.lIIIllll[4] = "   ".length();
        ArenaAllvAll.lIIIllll[5] = 190 ^ 186;
        ArenaAllvAll.lIIIllll[6] = 68 + 19 - 18 + 60 ^ 131 + 22 - 20 + 4;
    }

    private static boolean lIIlIIIIIl(int n, int n2) {
        return n >= n2;
    }

    @Override
    public String getGameName() {
        return lIIIllII[lIIIllll[1]];
    }

    private static String llllllIIl(String lIlIlIllIIllIIl, String lIlIlIllIIllIII) {
        try {
            SecretKeySpec lIlIlIllIIllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIlIllIIllIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIlIllIIlllIl = Cipher.getInstance("Blowfish");
            lIlIlIllIIlllIl.init(lIIIllll[0], lIlIlIllIIllllI);
            return new String(lIlIlIllIIlllIl.doFinal(Base64.getDecoder().decode(lIlIlIllIIllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIlIllIIlllII) {
            lIlIlIllIIlllII.printStackTrace();
            return null;
        }
    }

    @Override
    protected int getFinishScore() {
        ArenaAllvAll lIlIlIlllllIlII;
        return lIIIllll[0] + lIlIlIlllllIlII.getPlayers().size();
    }

    @Override
    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent lIlIlIllIlIIlll, Player lIlIlIllIlIIllI) {
        ArenaAllvAll lIlIlIllIlIlIII;
        super.onPlayerRespawnAfterTick(lIlIlIllIlIIlll, lIlIlIllIlIIllI);
        lIlIlIllIlIlIII.teleportToRandomSpawn(lIlIlIllIlIIllI);
    }

    private static boolean lIIlIIIIII(int n, int n2) {
        return n == n2;
    }

    @Override
    protected int getBaseSkillUnlockerAmount() {
        return lIIIllll[2];
    }

    private static String lllllIlll(String lIlIlIllIIIllII, String lIlIlIllIIIllIl) {
        try {
            SecretKeySpec lIlIlIllIIlIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIlIllIIIllIl.getBytes(StandardCharsets.UTF_8)), lIIIllll[6]), "DES");
            Cipher lIlIlIllIIlIIII = Cipher.getInstance("DES");
            lIlIlIllIIlIIII.init(lIIIllll[0], lIlIlIllIIlIIIl);
            return new String(lIlIlIllIIlIIII.doFinal(Base64.getDecoder().decode(lIlIlIllIIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIlIllIIIllll) {
            lIlIlIllIIIllll.printStackTrace();
            return null;
        }
    }

    @Override
    protected void teleportToRandomSpawn(Player lIlIlIlllIlIlll) {
        ArenaAllvAll lIlIlIlllIllIll;
        Location lIlIlIlllIllIIl = lIlIlIlllIllIll.getRandomSpawnLoc();
        "".length();
        lIlIlIlllIlIlll.teleport(lIlIlIlllIllIIl);
    }

    public ArenaAllvAll() {
        ArenaAllvAll lIlIlIlllllIlll;
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent lIlIlIllIlllllI, Player lIlIlIllIllllIl, Player lIlIlIlllIIIIII) {
        ArenaAllvAll lIlIlIlllIIIIll;
        super.onPlayerDeathByPlayer(lIlIlIllIlllllI, lIlIlIllIllllIl, lIlIlIlllIIIIII);
        lIlIlIlllIIIIll.incrementScore(lIlIlIlllIIIIII);
        if (ArenaAllvAll.lIIlIIIIII(lIlIlIlllIIIIll.getSpree(lIlIlIlllIIIIII), lIIIllll[2])) {
            Potion lIlIlIlllIIIlll = new Potion(PotionType.SPEED);
            lIlIlIlllIIIlll.setSplash(lIIIllll[2]);
            ItemStack[] arritemStack = new ItemStack[lIIIllll[2]];
            arritemStack[ArenaAllvAll.lIIIllll[1]] = lIlIlIlllIIIlll.toItemStack(lIIIllll[2]);
            "".length();
            lIlIlIlllIIIIII.getInventory().addItem(arritemStack);
        }
        if (ArenaAllvAll.lIIlIIIIII(lIlIlIlllIIIIll.getSpree(lIlIlIlllIIIIII), lIIIllll[0])) {
            Potion lIlIlIlllIIIllI = new Potion(PotionType.POISON);
            lIlIlIlllIIIllI.setSplash(lIIIllll[2]);
            ItemStack[] arritemStack = new ItemStack[lIIIllll[2]];
            arritemStack[ArenaAllvAll.lIIIllll[1]] = lIlIlIlllIIIllI.toItemStack(lIIIllll[2]);
            "".length();
            lIlIlIlllIIIIII.getInventory().addItem(arritemStack);
            lIlIlIlllIIIIll.getPlayerInfo(lIlIlIlllIIIIII).addAdditionalSkill();
        }
        if (ArenaAllvAll.lIIlIIIIII(lIlIlIlllIIIIll.getSpree(lIlIlIlllIIIIII), lIIIllll[4])) {
            Potion lIlIlIlllIIIlIl = new Potion(PotionType.REGEN);
            lIlIlIlllIIIlIl.setSplash(lIIIllll[2]);
            ItemStack[] arritemStack = new ItemStack[lIIIllll[2]];
            arritemStack[ArenaAllvAll.lIIIllll[1]] = lIlIlIlllIIIlIl.toItemStack(lIIIllll[2]);
            "".length();
            lIlIlIlllIIIIII.getInventory().addItem(arritemStack);
        }
        if (ArenaAllvAll.lIIlIIIIIl(lIlIlIlllIIIIll.getSpree(lIlIlIlllIIIIII), lIIIllll[5])) {
            Potion lIlIlIlllIIIlII = new Potion(Utils.getRandomPotionType());
            lIlIlIlllIIIlII.setSplash(lIIIllll[2]);
            ItemStack[] arritemStack = new ItemStack[lIIIllll[2]];
            arritemStack[ArenaAllvAll.lIIIllll[1]] = lIlIlIlllIIIlII.toItemStack(lIIIllll[2]);
            "".length();
            lIlIlIlllIIIIII.getInventory().addItem(arritemStack);
        }
        lIlIlIllIlllllI.getDrops().clear();
        lIlIlIllIlllllI.setDeathMessage(String.valueOf(new StringBuilder().append(lIlIlIlllIIIIII.getName()).append(lIIIllII[lIIIllll[0]]).append(lIlIlIllIllllIl.getName()).append(lIIIllII[lIIIllll[4]])));
        lIlIlIlllIIIIll.updateScoreBoards();
    }

    private Location getRandomSpawnLoc() {
        ArenaAllvAll lIlIlIlllIIllll;
        ArrayList<Location> lIlIlIlllIlIIII = lIlIlIlllIIllll.pMapaActual().ObtenirLocations(lIIIllII[lIIIllll[2]], lIlIlIlllIIllll.world);
        Location lIlIlIlllIlIIIl = new Location(lIlIlIlllIIllll.world, (double)Utils.NombreEntre(lIlIlIlllIlIIII.get(lIIIllll[1]).getBlockX(), lIlIlIlllIlIIII.get(lIIIllll[2]).getBlockX()), lIlIlIlllIlIIII.get(lIIIllll[1]).getY() + 1.0, (double)Utils.NombreEntre(lIlIlIlllIlIIII.get(lIIIllll[1]).getBlockZ(), lIlIlIlllIlIIII.get(lIIIllll[2]).getBlockZ()));
        return lIlIlIlllIlIIIl;
    }

    @Override
    protected void teletransportarTothom() {
        ArenaAllvAll lIlIlIllllIIIlI;
        Iterator<Player> lIlIlIllllIIIII = lIlIlIllllIIIlI.getPlayers().iterator();
        while (ArenaAllvAll.lIIIllllll((int)lIlIlIllllIIIII.hasNext())) {
            Player lIlIlIllllIIIll = lIlIlIllllIIIII.next();
            lIlIlIllllIIIlI.teleportToRandomSpawn(lIlIlIllllIIIll);
            "".length();
            if ("  ".length() != 0) continue;
            return;
        }
    }

    @Override
    protected void onPlayerDamageByPlayer(EntityDamageByEntityEvent lIlIlIllIllIlII, Player lIlIlIllIllIIll, Player lIlIlIllIllIIlI, boolean lIlIlIllIllIIIl) {
        ArenaAllvAll lIlIlIllIllIIII;
        super.onPlayerDamageByPlayer(lIlIlIllIllIlII, lIlIlIllIllIIll, lIlIlIllIllIIlI, lIlIlIllIllIIIl);
        if (ArenaAllvAll.lIIIllllll((int)lIlIlIllIllIIIl)) {
            ItemStack[] arritemStack = new ItemStack[lIIIllll[2]];
            arritemStack[ArenaAllvAll.lIIIllll[1]] = new ItemStack(Material.ARROW, lIIIllll[2]);
            "".length();
            lIlIlIllIllIIlI.getInventory().addItem(arritemStack);
        }
    }

    private static boolean lIIIllllll(int n) {
        return n != 0;
    }

    private static void lIIIIIIIlI() {
        lIIIllII = new String[lIIIllll[5]];
        ArenaAllvAll.lIIIllII[ArenaAllvAll.lIIIllll[1]] = ArenaAllvAll.lllllIlll("on4rHC+i6pg/N5Am/lIerw==", "Ulbjx");
        ArenaAllvAll.lIIIllII[ArenaAllvAll.lIIIllll[2]] = ArenaAllvAll.llllllIIl("rM+OdTyfej0=", "iuRnP");
        ArenaAllvAll.lIIIllII[ArenaAllvAll.lIIIllll[0]] = ArenaAllvAll.lllllIlll("RhttTNlKghVPo6LJKRw+FQ==", "GtfGa");
        ArenaAllvAll.lIIIllII[ArenaAllvAll.lIIIllll[4]] = ArenaAllvAll.lllllIlll("k99PlWb4Uv4=", "ZuAJH");
    }
}

