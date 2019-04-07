/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.potion.Potion
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.potion.PotionType
 *  org.bukkit.util.Vector
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
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class BaseLunar
extends JocScoreRace {
    private static final /* synthetic */ int[] llIIlIl;
    private static final /* synthetic */ String[] lIIlllI;

    public BaseLunar() {
        BaseLunar llIIlIlIIIlIllI;
    }

    @Override
    protected int getFinishScore() {
        return llIIlIl[0];
    }

    @Override
    protected boolean canBeDropped(ItemStack llIIlIIllIlIllI, Player llIIlIIllIlIlIl) {
        BaseLunar llIIlIIllIllIlI;
        if (BaseLunar.lIllIlllI((Object)llIIlIIllIlIllI.getType(), (Object)Material.ENDER_PEARL)) {
            return llIIlIl[1];
        }
        if (BaseLunar.lIllIlllI((Object)llIIlIIllIlIllI.getType(), (Object)Material.ARROW)) {
            return llIIlIl[1];
        }
        return super.canBeDropped(llIIlIIllIlIllI, llIIlIIllIlIlIl);
    }

    private static boolean lIlllIIIl(int n, int n2) {
        return n < n2;
    }

    private static void lIlIlIIlI() {
        lIIlllI = new String[llIIlIl[24]];
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[4]] = BaseLunar.lIIlIlIII("25KCBtBNVks8gKK7tgBBPw==", "uoCvF");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[1]] = BaseLunar.lIIlIlIII("Oi6l/dFOjPk=", "aVeJB");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[7]] = BaseLunar.lIIlIlIIl("BAMsEQc=", "ibXps");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[6]] = BaseLunar.lIIlIlIII("3eEq768ZP+oIv5mz+lFj8A==", "gXkIU");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[11]] = BaseLunar.lIIlIlIII("soKGI4nZoFk=", "QeVms");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[12]] = BaseLunar.lIlIIIllI("OD8QZURkkvY=", "nBwie");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[13]] = BaseLunar.lIIlIlIIl("JidLJCQ4NAo=", "JFkPA");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[14]] = BaseLunar.lIlIIIllI("cUeixawikxw=", "sxuef");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[0]] = BaseLunar.lIIlIlIIl("PMKKPgYHMwI=", "VpNos");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[15]] = BaseLunar.lIIlIlIII("eUxqk42ihvY=", "gboPH");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[2]] = BaseLunar.lIlIIIllI("89CBHpKbeas=", "JqCoo");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[10]] = BaseLunar.lIIlIlIII("ele1rM7wK2I=", "XYPoH");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[16]] = BaseLunar.lIIlIlIIl("EAw7ZQUJFigkRAIDNsKlHAwD", "ebZEd");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[17]] = BaseLunar.lIlIIIllI("DgAfbTv+GnlJIDTTRR9zqA==", "IMulO");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[18]] = BaseLunar.lIIlIlIII("fKO5ahOFJ6bbiRyvMtUZ8A==", "HBAvd");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[19]] = BaseLunar.lIlIIIllI("pyR8+3Rnux4iX0nWKOto+Q==", "XUOBC");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[20]] = BaseLunar.lIlIIIllI("zdgBHsl8Y8E=", "VIbbW");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[21]] = BaseLunar.lIlIIIllI("CvZ2qTRMnrc=", "ckUsH");
        BaseLunar.lIIlllI[BaseLunar.llIIlIl[22]] = BaseLunar.lIIlIlIIl("bThiSCc=", "McIyz");
    }

    @Override
    protected void onPlayerDeathByPlayer(PlayerDeathEvent llIIlIIllIIIlIl, Player llIIlIIllIIIlII, Player llIIlIIllIIIIll) {
        BaseLunar llIIlIIllIIlIll;
        llIIlIIllIIlIll.incrementScore(llIIlIIllIIIIll);
        ItemStack[] arritemStack = new ItemStack[llIIlIl[1]];
        arritemStack[BaseLunar.llIIlIl[4]] = new ItemStack(Material.ENDER_PEARL, llIIlIl[1]);
        "".length();
        llIIlIIllIIIIll.getInventory().addItem(arritemStack);
        String llIIlIIllIIIlll = lIIlllI[llIIlIl[7]];
        if (BaseLunar.lIllIllIl((int)Utils.Possibilitat(llIIlIl[8]))) {
            llIIlIIllIIIlll = lIIlllI[llIIlIl[6]];
        }
        if (BaseLunar.lIllIllIl((int)Utils.Possibilitat(llIIlIl[9]))) {
            String[] arrstring = new String[llIIlIl[10]];
            arrstring[BaseLunar.llIIlIl[4]] = lIIlllI[llIIlIl[11]];
            arrstring[BaseLunar.llIIlIl[1]] = lIIlllI[llIIlIl[12]];
            arrstring[BaseLunar.llIIlIl[7]] = lIIlllI[llIIlIl[13]];
            arrstring[BaseLunar.llIIlIl[6]] = lIIlllI[llIIlIl[14]];
            arrstring[BaseLunar.llIIlIl[11]] = lIIlllI[llIIlIl[0]];
            arrstring[BaseLunar.llIIlIl[12]] = lIIlllI[llIIlIl[15]];
            arrstring[BaseLunar.llIIlIl[13]] = lIIlllI[llIIlIl[2]];
            arrstring[BaseLunar.llIIlIl[14]] = lIIlllI[llIIlIl[10]];
            arrstring[BaseLunar.llIIlIl[0]] = lIIlllI[llIIlIl[16]];
            arrstring[BaseLunar.llIIlIl[15]] = lIIlllI[llIIlIl[17]];
            arrstring[BaseLunar.llIIlIl[2]] = lIIlllI[llIIlIl[18]];
            String[] llIIlIIllIIllIl = arrstring;
            String llIIlIIllIIllII = Utils.getRandomArrayItem(llIIlIIllIIllIl);
            llIIlIIllIIIlll = String.valueOf(new StringBuilder().append(lIIlllI[llIIlIl[19]]).append(llIIlIIllIIllII));
        }
        llIIlIIllIIIlIl.setDeathMessage(String.valueOf(new StringBuilder().append(llIIlIIllIIIIll.getName()).append((Object)ChatColor.DARK_AQUA).append(lIIlllI[llIIlIl[20]]).append(llIIlIIllIIIlll).append(lIIlllI[llIIlIl[21]]).append((Object)ChatColor.WHITE).append(llIIlIIllIIIlII.getName()).append((Object)ChatColor.GOLD).append(lIIlllI[llIIlIl[22]])));
        super.onPlayerDeathByPlayer(llIIlIIllIIIlIl, llIIlIIllIIIlII, llIIlIIllIIIIll);
    }

    private static void lIllIlIlI() {
        llIIlIl = new int[25];
        BaseLunar.llIIlIl[0] = 113 + 110 - 34 + 15 ^ 193 + 94 - 278 + 187;
        BaseLunar.llIIlIl[1] = " ".length();
        BaseLunar.llIIlIl[2] = 153 ^ 147;
        BaseLunar.llIIlIl[3] = 165 ^ 169 ^ (186 ^ 132);
        BaseLunar.llIIlIl[4] = (253 ^ 171 ^ (157 ^ 133)) & (142 ^ 185 ^ (226 ^ 155) ^ -" ".length());
        BaseLunar.llIIlIl[5] = -1 & Integer.MAX_VALUE;
        BaseLunar.llIIlIl[6] = "   ".length();
        BaseLunar.llIIlIl[7] = "  ".length();
        BaseLunar.llIIlIl[8] = 105 ^ 103 ^ (133 ^ 159);
        BaseLunar.llIIlIl[9] = 28 ^ 5;
        BaseLunar.llIIlIl[10] = 81 ^ 1 ^ (243 ^ 168);
        BaseLunar.llIIlIl[11] = 10 ^ 14;
        BaseLunar.llIIlIl[12] = 91 ^ 94;
        BaseLunar.llIIlIl[13] = 64 ^ 70;
        BaseLunar.llIIlIl[14] = 3 ^ 13 ^ (170 ^ 163);
        BaseLunar.llIIlIl[15] = 53 + 58 - 31 + 62 ^ 128 + 62 - 71 + 16;
        BaseLunar.llIIlIl[16] = 57 ^ 53;
        BaseLunar.llIIlIl[17] = 37 + 121 - 103 + 89 ^ 76 + 51 - 43 + 73;
        BaseLunar.llIIlIl[18] = 162 ^ 172;
        BaseLunar.llIIlIl[19] = 108 + 150 - 174 + 73 ^ 49 + 124 - 82 + 55;
        BaseLunar.llIIlIl[20] = 206 ^ 195 ^ (184 ^ 165);
        BaseLunar.llIIlIl[21] = 168 ^ 185;
        BaseLunar.llIIlIl[22] = 45 + 145 - 65 + 83 ^ 32 + 48 - 67 + 181;
        BaseLunar.llIIlIl[23] = -" ".length();
        BaseLunar.llIIlIl[24] = 95 ^ 116 ^ (56 ^ 0);
    }

    protected void onEntityDamageByEntity(EntityDamageByEntityEvent llIIlIIlIlIlIIl, Entity llIIlIIlIlIlIII, Entity llIIlIIlIlIllIl) {
        BaseLunar llIIlIIlIlIlIlI;
        super.onEntityDamageByEntity(llIIlIIlIlIlIIl, llIIlIIlIlIlIII, llIIlIIlIlIllIl);
        Vector llIIlIIlIlIllII = Utils.CrearVector(llIIlIIlIlIllIl.getLocation(), llIIlIIlIlIlIII.getLocation());
        "".length();
        llIIlIIlIlIllII.normalize();
        "".length();
        llIIlIIlIlIllII.multiply(1.0 + 3.0 * (llIIlIIlIlIlIIl.getDamage() / 20.0));
        "".length();
        llIIlIIlIlIllII.setY(llIIlIIlIlIllII.getY() / 2.2);
        Vector llIIlIIlIlIlIll = new Vector(0.0, 0.25, 0.0);
        llIIlIIlIlIlIII.setVelocity(llIIlIIlIlIlIII.getVelocity().add(llIIlIIlIlIllII.clone().add(llIIlIIlIlIlIll)));
        llIIlIIlIlIllIl.setVelocity(llIIlIIlIlIllIl.getVelocity().add(llIIlIIlIlIllII.clone().multiply(llIIlIl[23]).add(llIIlIIlIlIlIll)));
    }

    private Location getRandomSpawnLoc() {
        BaseLunar llIIlIIlllIIIII;
        ArrayList<Location> llIIlIIlllIIIlI = llIIlIIlllIIIII.pMapaActual().ObtenirLocations(lIIlllI[llIIlIl[1]], llIIlIIlllIIIII.world);
        Collections.shuffle(llIIlIIlllIIIlI);
        Location llIIlIIlllIIIIl = llIIlIIlllIIIlI.get(llIIlIl[4]);
        "".length();
        llIIlIIlllIIIIl.add(0.0, 2.0, 0.0);
        return llIIlIIlllIIIIl;
    }

    private static boolean lIllIlllI(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected void teleportToRandomSpawn(Player llIIlIIlllIlIll) {
        BaseLunar llIIlIIlllIllII;
        Location llIIlIIlllIlIlI = llIIlIIlllIllII.getRandomSpawnLoc();
        "".length();
        llIIlIIlllIlIll.teleport(llIIlIIlllIlIlI);
    }

    private static boolean lIllIllIl(int n) {
        return n != 0;
    }

    @Override
    public String getGameName() {
        return lIIlllI[llIIlIl[4]];
    }

    @Override
    protected void onPlayerRespawnAfterTick(PlayerRespawnEvent llIIlIIlIlllIII, Player llIIlIIlIlllIlI) {
        BaseLunar llIIlIIlIlllIIl;
        super.onPlayerRespawnAfterTick(llIIlIIlIlllIII, llIIlIIlIlllIlI);
        llIIlIIlIlllIlI.getEquipment().setHelmet(new ItemStack(Material.GLASS, llIIlIl[1]));
    }

    @Override
    protected void donarEfectesInicials(Player llIIlIIlllllIII) {
        BaseLunar llIIlIIlllllIIl;
        super.donarEfectesInicials(llIIlIIlllllIII);
        "".length();
        llIIlIIlllllIII.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, llIIlIl[5], llIIlIl[6], llIIlIl[1]), llIIlIl[1]);
        "".length();
        llIIlIIlllllIII.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, llIIlIl[5], llIIlIl[6], llIIlIl[1]), llIIlIl[1]);
    }

    private static String lIlIIIllI(String llIIlIIIllllIIl, String llIIlIIIllllIlI) {
        try {
            SecretKeySpec llIIlIIIllllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIlIIIllllIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIlIIIlllllIl = Cipher.getInstance("Blowfish");
            llIIlIIIlllllIl.init(llIIlIl[7], llIIlIIIllllllI);
            return new String(llIIlIIIlllllIl.doFinal(Base64.getDecoder().decode(llIIlIIIllllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIlIIIlllllII) {
            llIIlIIIlllllII.printStackTrace();
            return null;
        }
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player llIIlIlIIIIIlIl) {
        BaseLunar llIIlIlIIIIIllI;
        ArrayList<ItemStack> llIIlIlIIIIlIll = new ArrayList<ItemStack>();
        ItemStack llIIlIlIIIIlIlI = new ItemStack(Material.WOOD_SWORD, llIIlIl[1]);
        llIIlIlIIIIlIlI.addUnsafeEnchantment(Enchantment.DURABILITY, llIIlIl[2]);
        llIIlIlIIIIlIlI.addUnsafeEnchantment(Enchantment.KNOCKBACK, llIIlIl[1]);
        "".length();
        llIIlIlIIIIlIll.add(llIIlIlIIIIlIlI);
        "".length();
        llIIlIlIIIIlIll.add(new ItemStack(Material.WOOD_SWORD, llIIlIl[1]));
        "".length();
        llIIlIlIIIIlIll.add(new ItemStack(Material.BOW, llIIlIl[1]));
        Potion llIIlIlIIIIlIIl = new Potion(PotionType.INSTANT_DAMAGE);
        llIIlIlIIIIlIIl.setSplash(llIIlIl[1]);
        "".length();
        llIIlIlIIIIlIll.add(llIIlIlIIIIlIIl.toItemStack(llIIlIl[1]));
        Potion llIIlIlIIIIlIII = new Potion(PotionType.INSTANT_HEAL);
        llIIlIlIIIIlIII.setSplash(llIIlIl[1]);
        "".length();
        llIIlIlIIIIlIll.add(llIIlIlIIIIlIII.toItemStack(llIIlIl[1]));
        "".length();
        llIIlIlIIIIlIll.add(new ItemStack(Material.ARROW, llIIlIl[3]));
        "".length();
        llIIlIlIIIIlIll.add(llIIlIlIIIIIllI.getSnowLauncher(llIIlIl[2]));
        Color llIIlIlIIIIIlll = llIIlIlIIIIIllI.getDeterministicColorForPlayer(llIIlIlIIIIIlIl, llIIlIl[4]);
        "".length();
        llIIlIlIIIIlIll.add(GUtils.createColoredArmor((Material)Material.LEATHER_HELMET, (Color)llIIlIlIIIIIlll));
        "".length();
        llIIlIlIIIIlIll.add(Utils.createColoredArmor(Material.LEATHER_CHESTPLATE, Color.WHITE));
        "".length();
        llIIlIlIIIIlIll.add(Utils.createColoredArmor(Material.LEATHER_LEGGINGS, Color.BLUE));
        "".length();
        llIIlIlIIIIlIll.add(Utils.createColoredArmor(Material.LEATHER_BOOTS, Color.WHITE));
        return llIIlIlIIIIlIll;
    }

    @Override
    protected void onPlayerDamage(EntityDamageEvent llIIlIIlIIllIII, Player llIIlIIlIIlIlII) {
        BaseLunar llIIlIIlIIlIllI;
        super.onPlayerDamage(llIIlIIlIIllIII, llIIlIIlIIlIlII);
        if (BaseLunar.lIllIlllI((Object)llIIlIIlIIllIII.getCause(), (Object)EntityDamageEvent.DamageCause.FALL)) {
            double llIIlIIlIIlllIl = 0.18;
            double llIIlIIlIIlllII = llIIlIIlIIllIII.getDamage();
            double llIIlIIlIIllIll = llIIlIIlIIlllII * llIIlIIlIIlllIl;
            double llIIlIIlIIllIlI = llIIlIIlIIlllII - llIIlIIlIIllIll;
            llIIlIIlIIllIII.setDamage(llIIlIIlIIllIlI);
            llIIlIIlIIllIII.setDamage(llIIlIIlIIllIII.getDamage() - 3.0);
        }
    }

    @Override
    protected void teletransportarTothom() {
        BaseLunar llIIlIIllllIIlI;
        Iterator<Player> llIIlIIllllIIIl = llIIlIIllllIIlI.getPlayers().iterator();
        while (BaseLunar.lIllIllIl((int)llIIlIIllllIIIl.hasNext())) {
            Player llIIlIIllllIlII = llIIlIIllllIIIl.next();
            llIIlIIllllIIlI.teleportToRandomSpawn(llIIlIIllllIlII);
            "".length();
            if ((191 ^ 175 ^ (39 ^ 51)) >= 0) continue;
            return;
        }
    }

    private static String lIIlIlIII(String llIIlIIlIIIIllI, String llIIlIIlIIIIlIl) {
        try {
            SecretKeySpec llIIlIIlIIIlIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIlIIlIIIIlIl.getBytes(StandardCharsets.UTF_8)), llIIlIl[0]), "DES");
            Cipher llIIlIIlIIIlIlI = Cipher.getInstance("DES");
            llIIlIIlIIIlIlI.init(llIIlIl[7], llIIlIIlIIIlIll);
            return new String(llIIlIIlIIIlIlI.doFinal(Base64.getDecoder().decode(llIIlIIlIIIIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIlIIlIIIlIIl) {
            llIIlIIlIIIlIIl.printStackTrace();
            return null;
        }
    }

    private static String lIIlIlIIl(String llIIlIIIllIlIll, String llIIlIIIllIIlIl) {
        llIIlIIIllIlIll = new String(Base64.getDecoder().decode(llIIlIIIllIlIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIlIIIllIlIIl = new StringBuilder();
        char[] llIIlIIIllIlIII = llIIlIIIllIIlIl.toCharArray();
        int llIIlIIIllIIlll = llIIlIl[4];
        char[] llIIlIIIllIIIIl = llIIlIIIllIlIll.toCharArray();
        int llIIlIIIllIIIII = llIIlIIIllIIIIl.length;
        int llIIlIIIlIlllll = llIIlIl[4];
        while (BaseLunar.lIlllIIIl(llIIlIIIlIlllll, llIIlIIIllIIIII)) {
            char llIIlIIIllIllII = llIIlIIIllIIIIl[llIIlIIIlIlllll];
            "".length();
            llIIlIIIllIlIIl.append((char)(llIIlIIIllIllII ^ llIIlIIIllIlIII[llIIlIIIllIIlll % llIIlIIIllIlIII.length]));
            ++llIIlIIIllIIlll;
            ++llIIlIIIlIlllll;
            "".length();
            if ("  ".length() < (109 ^ 105)) continue;
            return null;
        }
        return String.valueOf(llIIlIIIllIlIIl);
    }

    static {
        BaseLunar.lIllIlIlI();
        BaseLunar.lIlIlIIlI();
    }

    @Override
    public boolean giveSnowLauncherOnKill() {
        return llIIlIl[1];
    }
}

