/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.GUtils
 *  com.biel.BielAPI.Utils.Regions.Cuboid
 *  org.bukkit.DyeColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.mapes.jocs;

import com.biel.BielAPI.Utils.GUtils;
import com.biel.BielAPI.Utils.Regions.Cuboid;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocEquipsLastStanding;
import com.biel.lobby.utilities.Utils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class BedWars
extends JocEquipsLastStanding {
    private static final /* synthetic */ int[] lIIIIIIlI;
    private static final /* synthetic */ String[] lIIIIIIIl;

    @Override
    protected void onPlayerDeath(PlayerDeathEvent lllllIIllllIII, Player lllllIIlllIlll) {
        BedWars lllllIIlllIllI;
        super.onPlayerDeath(lllllIIllllIII, lllllIIlllIlll);
        if (BedWars.llllIIIIlI((int)lllllIIlllIllI.obtenirEquip(lllllIIlllIlll).isBedAlive())) {
            lllllIIlllIllI.removeAlive(lllllIIlllIlll);
        }
    }

    private static String lllIlllIll(String lllllIIllIIlII, String lllllIIllIIIIl) {
        try {
            SecretKeySpec lllllIIllIIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllIIllIIIIl.getBytes(StandardCharsets.UTF_8)), lIIIIIIlI[4]), "DES");
            Cipher lllllIIllIIllI = Cipher.getInstance("DES");
            lllllIIllIIllI.init(lIIIIIIlI[2], lllllIIllIIlll);
            return new String(lllllIIllIIllI.doFinal(Base64.getDecoder().decode(lllllIIllIIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllIIllIIlIl) {
            lllllIIllIIlIl.printStackTrace();
            return null;
        }
    }

    @Override
    protected void setCustomGameRules() {
    }

    private static boolean llllIIIIlI(int n) {
        return n == 0;
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lllllIlIIIlIIl) {
        BedWars lllllIlIIIlIlI;
        ArrayList<ItemStack> lllllIlIIIlIII = new ArrayList<ItemStack>();
        EquipBedWars lllllIlIIIIlll = lllllIlIIIlIlI.obtenirEquip(lllllIlIIIlIIl);
        "".length();
        lllllIlIIIlIII.add(new ItemStack(Material.WOOD_SWORD, lIIIIIIlI[0]));
        "".length();
        lllllIlIIIlIII.add(Utils.createColoredTeamArmor(Material.LEATHER_CHESTPLATE, lllllIlIIIIlll));
        "".length();
        lllllIlIIIlIII.add(Utils.createColoredTeamArmor(Material.LEATHER_HELMET, lllllIlIIIIlll));
        "".length();
        lllllIlIIIlIII.add(Utils.createColoredTeamArmor(Material.LEATHER_BOOTS, lllllIlIIIIlll));
        "".length();
        lllllIlIIIlIII.add(new ItemStack(Material.ARROW, lIIIIIIlI[0]));
        return lllllIlIIIlIII;
    }

    @Override
    public EquipBedWars obtenirEquip(Player lllllIIlllllIl) {
        BedWars lllllIlIIIIIII;
        return lllllIlIIIIIII.obtenirEquip(lllllIIlllllIl, EquipBedWars.class);
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        BedWars lllllIlIIlllIl;
        ArrayList<JocEquips.Equip> lllllIlIIlllII = new ArrayList<JocEquips.Equip>();
        "".length();
        lllllIlIIlllII.add(lllllIlIIlllIl.new EquipBedWars(DyeColor.RED, lIIIIIIIl[lIIIIIIlI[1]]));
        "".length();
        lllllIlIIlllII.add(lllllIlIIlllIl.new EquipBedWars(DyeColor.BLUE, lIIIIIIIl[lIIIIIIlI[0]]));
        return lllllIlIIlllII;
    }

    protected void onPlayerDropItem(PlayerDropItemEvent lllllIlIIlIIIl, Player lllllIlIIlIIll) {
        BedWars lllllIlIIlIIlI;
        super.onPlayerDropItem(lllllIlIIlIIIl, lllllIlIIlIIll);
    }

    @Override
    protected void customJocIniciat() {
        BedWars lllllIlIlIIIIl;
        super.customJocIniciat();
        lllllIlIlIIIIl.Equips.stream().map(lllllIIllIllIl -> (EquipBedWars)lllllIIllIllIl).forEach(EquipBedWars::detectBed);
        lllllIlIlIIIIl.setBlockBreakPlace(lIIIIIIlI[0]);
        lllllIlIlIIIIl.setGiveStartingItemsRespawn(lIIIIIIlI[0]);
    }

    static {
        BedWars.llllIIIIIl();
        BedWars.llllIIIIII();
    }

    public BedWars() {
        BedWars lllllIlIlIIlII;
    }

    private static void llllIIIIII() {
        lIIIIIIIl = new String[lIIIIIIlI[3]];
        BedWars.lIIIIIIIl[BedWars.lIIIIIIlI[1]] = BedWars.lllIlllIll("fELWybkN35Y=", "UIiCx");
        BedWars.lIIIIIIIl[BedWars.lIIIIIIlI[0]] = BedWars.lllIlllIll("QHQEELqPlHk=", "UxvYX");
        BedWars.lIIIIIIIl[BedWars.lIIIIIIlI[2]] = BedWars.lllIlllIll("Yzc2O3/0gOA=", "EttME");
    }

    @Override
    public String getGameName() {
        return lIIIIIIIl[lIIIIIIlI[2]];
    }

    private static void llllIIIIIl() {
        lIIIIIIlI = new int[5];
        BedWars.lIIIIIIlI[0] = " ".length();
        BedWars.lIIIIIIlI[1] = (130 + 65 - 156 + 194 ^ 165 + 19 - 32 + 18) & (164 + 92 - 91 + 40 ^ 14 + 75 - 4 + 57 ^ -" ".length());
        BedWars.lIIIIIIlI[2] = "  ".length();
        BedWars.lIIIIIIlI[3] = "   ".length();
        BedWars.lIIIIIIlI[4] = 151 ^ 182 ^ (142 ^ 167);
    }

    public class EquipBedWars
    extends JocEquips.Equip {
        private static final /* synthetic */ String[] lIllIlII;
        private static final /* synthetic */ int[] lIllIlIl;
        private /* synthetic */ Location bedLocation;

        public EquipBedWars(DyeColor lIIlllIIIIlIlll, String lIIlllIIIIlIllI) {
            EquipBedWars lIIlllIIIIllIIl;
            super(lIIlllIIIIlIlll, lIIlllIIIIlIllI);
        }

        static {
            EquipBedWars.lIllIIIIlI();
            EquipBedWars.lIlIllllll();
        }

        public Location getBedLocation() {
            EquipBedWars lIIlllIIIIIlIII;
            return lIIlllIIIIIlIII.bedLocation;
        }

        private static boolean lIllIIIIll(int n) {
            return n != 0;
        }

        private static void lIlIllllll() {
            lIllIlII = new String[lIllIlIl[2]];
            EquipBedWars.lIllIlII[EquipBedWars.lIllIlIl[1]] = EquipBedWars.lIlIlllllI("MikTBwcbMUE7B0kfRh0JSRwOEh0dTBUHBwsNE1UNBUwNGQEdTBEQGkkNQRlPDB0UHBhJ", "ilauh");
        }

        private static void lIllIIIIlI() {
            lIllIlIl = new int[3];
            EquipBedWars.lIllIlIl[0] = 120 ^ 112;
            EquipBedWars.lIllIlIl[1] = (202 ^ 156 ^ (26 ^ 107)) & (170 ^ 175 ^ (65 ^ 99) ^ -" ".length());
            EquipBedWars.lIllIlIl[2] = " ".length();
        }

        private static boolean lIllIIIlIl(int n, int n2) {
            return n < n2;
        }

        public void detectBed() {
            EquipBedWars lIIlllIIIIlIIIl;
            int lIIlllIIIIlIIII = lIllIlIl[0];
            Cuboid lIIlllIIIIIllll = GUtils.getCuboidAround((Location)lIIlllIIIIlIIIl.getTeamSpawnLocation(), (int)lIIlllIIIIlIIII);
            Optional<Block> lIIlllIIIIIlllI = lIIlllIIIIIllll.getBlocks().stream().filter(lIIlllIIIIIIIIl -> lIIlllIIIIIIIIl.getType().equals((Object)Material.BED_BLOCK)).findAny();
            if (EquipBedWars.lIllIIIIll((int)lIIlllIIIIIlllI.isPresent())) {
                lIIlllIIIIlIIIl.bedLocation = lIIlllIIIIIlllI.get().getLocation();
                lIIlllIIIIlIIIl.BedWars.this.getWorld().playEffect(lIIlllIIIIlIIIl.bedLocation, Effect.MOBSPAWNER_FLAMES, lIllIlIl[1]);
                "".length();
                if (((41 ^ 59 ^ (75 ^ 109)) & (145 ^ 197 ^ (8 ^ 104) ^ -" ".length())) != 0) {
                    return;
                }
            } else {
                lIIlllIIIIlIIIl.sendMessage(String.valueOf(new StringBuilder().append(lIllIlII[lIllIlIl[1]]).append(lIIlllIIIIlIIIl.getAdjectiuColored())));
            }
        }

        private static String lIlIlllllI(String lIIllIlllllIIIl, String lIIllIlllllIIII) {
            lIIllIlllllIIIl = new String(Base64.getDecoder().decode(lIIllIlllllIIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIllIlllllIlII = new StringBuilder();
            char[] lIIllIlllllIIll = lIIllIlllllIIII.toCharArray();
            int lIIllIlllllIIlI = lIllIlIl[1];
            char[] lIIllIllllIllII = lIIllIlllllIIIl.toCharArray();
            int lIIllIllllIlIll = lIIllIllllIllII.length;
            int lIIllIllllIlIlI = lIllIlIl[1];
            while (EquipBedWars.lIllIIIlIl(lIIllIllllIlIlI, lIIllIllllIlIll)) {
                char lIIllIlllllIlll = lIIllIllllIllII[lIIllIllllIlIlI];
                "".length();
                lIIllIlllllIlII.append((char)(lIIllIlllllIlll ^ lIIllIlllllIIll[lIIllIlllllIIlI % lIIllIlllllIIll.length]));
                ++lIIllIlllllIIlI;
                ++lIIllIllllIlIlI;
                "".length();
                if ((192 ^ 196) > 0) continue;
                return null;
            }
            return String.valueOf(lIIllIlllllIlII);
        }

        private static boolean lIllIIIlII(Object object, Object object2) {
            return object == object2;
        }

        public boolean isBedAlive() {
            boolean bl;
            EquipBedWars lIIlllIIIIIIlII;
            if (EquipBedWars.lIllIIIlII((Object)lIIlllIIIIIIlII.bedLocation.getBlock().getType(), (Object)Material.BED)) {
                bl = lIllIlIl[2];
                "".length();
                if ((22 + 49 - -66 + 1 ^ 58 + 9 - -1 + 74) < 0) {
                    return (boolean)((25 ^ 64 ^ (87 ^ 79)) & (126 ^ 116 ^ (208 ^ 155) ^ -" ".length()));
                }
            } else {
                bl = lIllIlIl[1];
            }
            return bl;
        }
    }

}

