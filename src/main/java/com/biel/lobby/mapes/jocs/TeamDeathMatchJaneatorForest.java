/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Snowball
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.projectiles.ProjectileSource
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamDeathMatch;
import com.biel.lobby.mapes.JocTeamScoreRace;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class TeamDeathMatchJaneatorForest
extends JocTeamDeathMatch {
    private static final /* synthetic */ String[] ll;
    private static final /* synthetic */ int[] llI;

    static {
        TeamDeathMatchJaneatorForest.lIlIl();
        TeamDeathMatchJaneatorForest.llII();
    }

    private static boolean lIlll(Object object, Object object2) {
        return object == object2;
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        TeamDeathMatchJaneatorForest lllllllllIIllll;
        ArrayList<JocEquips.Equip> lllllllllIIlllI = new ArrayList<JocEquips.Equip>();
        "".length();
        lllllllllIIlllI.add(new JocTeamScoreRace.EquipScoreRace(lllllllllIIllll, DyeColor.RED, ll[llI[2]]));
        "".length();
        lllllllllIIlllI.add(new JocTeamScoreRace.EquipScoreRace(lllllllllIIllll, DyeColor.BLUE, ll[llI[3]]));
        return lllllllllIIlllI;
    }

    @Override
    protected void onProjectileHit(ProjectileHitEvent llllllllIIllllI, Projectile llllllllIIllIlI) {
        TeamDeathMatchJaneatorForest llllllllIIlllll;
        super.onProjectileHit(llllllllIIllllI, llllllllIIllIlI);
        if (TeamDeathMatchJaneatorForest.llIII(llllllllIIllIlI instanceof Snowball)) {
            Location llllllllIlIIIIl = llllllllIIllllI.getEntity().getLocation();
            ProjectileSource llllllllIlIIIII = llllllllIIllIlI.getShooter();
            if (TeamDeathMatchJaneatorForest.llIII(llllllllIlIIIII instanceof Player)) {
                Player llllllllIlIIIlI = (Player)llllllllIlIIIII;
                "".length();
                llllllllIIlllll.world.createExplosion(llllllllIlIIIIl.getX(), llllllllIlIIIIl.getY(), llllllllIlIIIIl.getZ(), 2.6f * (float)llllllllIIlllll.getBalancingMultiplier(llllllllIlIIIlI), llI[2], llI[2]);
            }
        }
    }

    @Override
    protected boolean canBeDropped(ItemStack llllllllIlIllII, Player llllllllIlIllll) {
        TeamDeathMatchJaneatorForest llllllllIllIIIl;
        Material llllllllIlIlllI = llllllllIlIllII.getType();
        if (!TeamDeathMatchJaneatorForest.lIllI((Object)llllllllIlIlllI, (Object)Material.GOLDEN_APPLE) || TeamDeathMatchJaneatorForest.lIlll((Object)llllllllIlIlllI, (Object)Material.SNOW_BALL)) {
            return llI[3];
        }
        return super.canBeDropped(llllllllIlIllII, llllllllIlIllll);
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lllllllllIIIIII) {
        TeamDeathMatchJaneatorForest lllllllllIIIIIl;
        ArrayList<ItemStack> llllllllIllllll = new ArrayList<ItemStack>();
        JocEquips.Equip llllllllIlllllI = lllllllllIIIIIl.obtenirEquip(lllllllllIIIIII);
        double llllllllIllllIl = lllllllllIIIIIl.getBalancingMultiplier(lllllllllIIIIII);
        "".length();
        llllllllIllllll.add(new ItemStack(Material.STONE_SWORD, llI[3]));
        ItemStack llllllllIllllII = new ItemStack(Material.BOW, llI[3]);
        llllllllIllllII.addUnsafeEnchantment(Enchantment.DURABILITY, llI[0]);
        "".length();
        llllllllIllllll.add(llllllllIllllII);
        "".length();
        llllllllIllllll.add(new ItemStack(Material.CHAINMAIL_HELMET, llI[3]));
        "".length();
        llllllllIllllll.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, llllllllIlllllI));
        "".length();
        llllllllIllllll.add(new ItemStack(Material.IRON_BOOTS, llI[3]));
        "".length();
        llllllllIllllll.add(Utils.createColoredTeamArmor(Material.LEATHER_LEGGINGS, llllllllIlllllI));
        "".length();
        llllllllIllllll.add(new ItemStack(Material.GOLDEN_APPLE, llI[1]));
        String[] arrstring = new String[llI[3]];
        arrstring[TeamDeathMatchJaneatorForest.llI[2]] = ll[llI[4]];
        "".length();
        llllllllIllllll.add(Utils.setItemNameAndLore(new ItemStack(Material.SNOW_BALL, (int)Math.round(4.0 * (llllllllIllllIl - 0.2))), ll[llI[1]], arrstring));
        "".length();
        llllllllIllllll.add(new ItemStack(Material.ARROW, (int)(20.0 * llllllllIllllIl)));
        return llllllllIllllll;
    }

    private static String I(String llllllllIIIIIII, String lllllllIlllllll) {
        try {
            SecretKeySpec llllllllIIIIlIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllllIlllllll.getBytes(StandardCharsets.UTF_8)), llI[7]), "DES");
            Cipher llllllllIIIIlII = Cipher.getInstance("DES");
            llllllllIIIIlII.init(llI[1], llllllllIIIIlIl);
            return new String(llllllllIIIIlII.doFinal(Base64.getDecoder().decode(llllllllIIIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllllllIIIIIll) {
            llllllllIIIIIll.printStackTrace();
            return null;
        }
    }

    private static void lIlIl() {
        llI = new int[8];
        TeamDeathMatchJaneatorForest.llI[0] = 216 ^ 186 ^ (173 ^ 197);
        TeamDeathMatchJaneatorForest.llI[1] = "  ".length();
        TeamDeathMatchJaneatorForest.llI[2] = (103 ^ 32) & ~(47 ^ 104);
        TeamDeathMatchJaneatorForest.llI[3] = " ".length();
        TeamDeathMatchJaneatorForest.llI[4] = "   ".length();
        TeamDeathMatchJaneatorForest.llI[5] = 75 ^ 79;
        TeamDeathMatchJaneatorForest.llI[6] = 67 ^ 70;
        TeamDeathMatchJaneatorForest.llI[7] = 125 ^ 110 ^ (22 ^ 13);
    }

    private static String lIII(String llllllllIIIllll, String llllllllIIIllII) {
        try {
            SecretKeySpec llllllllIIlIIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllllllIIIllII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llllllllIIlIIIl = Cipher.getInstance("Blowfish");
            llllllllIIlIIIl.init(llI[1], llllllllIIlIIlI);
            return new String(llllllllIIlIIIl.doFinal(Base64.getDecoder().decode(llllllllIIIllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllllllIIlIIII) {
            llllllllIIlIIII.printStackTrace();
            return null;
        }
    }

    private static String lIll(String lllllllIlllIIlI, String lllllllIllIllII) {
        lllllllIlllIIlI = new String(Base64.getDecoder().decode(lllllllIlllIIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllllllIlllIIII = new StringBuilder();
        char[] lllllllIllIllll = lllllllIllIllII.toCharArray();
        int lllllllIllIlllI = llI[2];
        char[] lllllllIllIlIII = lllllllIlllIIlI.toCharArray();
        int lllllllIllIIlll = lllllllIllIlIII.length;
        int lllllllIllIIllI = llI[2];
        while (TeamDeathMatchJaneatorForest.llIIl(lllllllIllIIllI, lllllllIllIIlll)) {
            char lllllllIlllIIll = lllllllIllIlIII[lllllllIllIIllI];
            "".length();
            lllllllIlllIIII.append((char)(lllllllIlllIIll ^ lllllllIllIllll[lllllllIllIlllI % lllllllIllIllll.length]));
            ++lllllllIllIlllI;
            ++lllllllIllIIllI;
            "".length();
            if ("  ".length() > ((122 ^ 76 ^ (113 ^ 84)) & (236 ^ 164 ^ (100 ^ 63) ^ -" ".length()))) continue;
            return null;
        }
        return String.valueOf(lllllllIlllIIII);
    }

    @Override
    protected void customJocIniciat() {
        TeamDeathMatchJaneatorForest lllllllllIIlIII;
        super.customJocIniciat();
        lllllllllIIlIII.world.setTime(4000L);
    }

    private static boolean llIII(int n) {
        return n != 0;
    }

    private static void llII() {
        ll = new String[llI[6]];
        TeamDeathMatchJaneatorForest.ll[TeamDeathMatchJaneatorForest.llI[2]] = TeamDeathMatchJaneatorForest.I("IDBGNzBBDsA=", "eypFk");
        TeamDeathMatchJaneatorForest.ll[TeamDeathMatchJaneatorForest.llI[3]] = TeamDeathMatchJaneatorForest.lIII("m9etdq/J90o=", "EpGpf");
        TeamDeathMatchJaneatorForest.ll[TeamDeathMatchJaneatorForest.llI[1]] = TeamDeathMatchJaneatorForest.lIll("KD4HBQA=", "jQjga");
        TeamDeathMatchJaneatorForest.ll[TeamDeathMatchJaneatorForest.llI[4]] = TeamDeathMatchJaneatorForest.lIll("CCEhOzU5OHE2eiF+ODoqLDolMg==", "MYQWZ");
        TeamDeathMatchJaneatorForest.ll[TeamDeathMatchJaneatorForest.llI[5]] = TeamDeathMatchJaneatorForest.lIll("GSopLRUnJDUOGyEuNDw=", "SKGHt");
    }

    private static boolean lIllI(Object object, Object object2) {
        return object != object2;
    }

    @Override
    public String getGameName() {
        return ll[llI[5]];
    }

    private static boolean llIIl(int n, int n2) {
        return n < n2;
    }

    @Override
    protected void setCustomGameRules() {
    }

    @Override
    protected int getFinishScore() {
        TeamDeathMatchJaneatorForest lllllllllIlIIll;
        return llI[0] + lllllllllIlIIll.getPlayers().size() * llI[1];
    }

    public TeamDeathMatchJaneatorForest() {
        TeamDeathMatchJaneatorForest lllllllllIlIlIl;
    }
}

