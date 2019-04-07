/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.biel.lobby.mapes.jocs;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.JocTeamScoreRace;
import com.biel.lobby.utilities.Cuboid;
import com.biel.lobby.utilities.GestorPropietats;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ResourceRush
extends JocTeamScoreRace {
    /* synthetic */ ArrayList<Item> thrownItems;
    private static final /* synthetic */ int[] llIllllI;
    private static final /* synthetic */ String[] llIllIII;

    private static boolean llIlIIllll(int n, int n2) {
        return n < n2;
    }

    public ResourceRush() {
        ResourceRush lIIIIlllllllIll;
        lIIIIlllllllIll.thrownItems = new ArrayList();
    }

    public double getMaterialValue(Material lIIIIlllllIIIll) {
        switch (1.$SwitchMap$org$bukkit$Material[lIIIIlllllIIIll.ordinal()]) {
            case 1: {
                return 30.0;
            }
            case 2: {
                return 25.0;
            }
            case 3: {
                return 20.0;
            }
            case 4: {
                return 15.0;
            }
            case 5: {
                return 15.0;
            }
            case 6: {
                return 4.0;
            }
            case 7: {
                return 1.0;
            }
        }
        return 0.0;
    }

    public void fillResourceSource(Location lIIIIlllIlIIlll) {
        ResourceRush lIIIIlllIlIlIII;
        Cuboid lIIIIlllIlIIllI = Utils.getCuboidAround(lIIIIlllIlIIlll, llIllllI[8]);
        int lIIIIlllIlIIlIl = llIllllI[0];
        int lIIIIlllIlIIlII = lIIIIlllIlIlIII.getTeamFilledChests();
        Iterator<Block> lIIIIlllIIllllI = lIIIIlllIlIIllI.getBlocks().iterator();
        while (ResourceRush.llIlIIllII((int)lIIIIlllIIllllI.hasNext())) {
            Block lIIIIlllIlIlIIl = lIIIIlllIIllllI.next();
            if (ResourceRush.llIlIIllIl((Object)lIIIIlllIlIlIIl.getType(), (Object)Material.CHEST)) {
                Utils.fillChestRandomly(lIIIIlllIlIlIIl, lIIIIlllIlIlIII.getChestStartingContents(llIllllI[2]));
                ++lIIIIlllIlIIlIl;
            }
            if (ResourceRush.llIlIIlllI(lIIIIlllIlIIlIl, lIIIIlllIlIIlII)) {
                "".length();
                if (" ".length() <= "  ".length()) break;
                return;
            }
            "".length();
            if (null == null) continue;
            return;
        }
    }

    @Override
    public void JocIniciat() {
        ResourceRush lIIIIlllllIllII;
        super.JocIniciat();
        lIIIIlllllIllII.fillResourceSources();
        Iterator<Player> lIIIIlllllIlIll = lIIIIlllllIllII.getPlayers().iterator();
        while (ResourceRush.llIlIIllII((int)lIIIIlllllIlIll.hasNext())) {
            Player lIIIIlllllIlllI = lIIIIlllllIlIll.next();
            lIIIIlllllIllII.getPlayerInfo(lIIIIlllllIlllI).addStatusEffect(lIIIIlllllIllII.new WeightStatusEffect(lIIIIlllllIlllI));
            lIIIIlllllIllII.getPlayerInfo(lIIIIlllllIlllI).addStatusEffect(lIIIIlllllIllII.new ValueStatusEffect(lIIIIlllllIlllI));
            "".length();
            if (((174 + 103 - 118 + 57 ^ 146 + 125 - 185 + 62) & (159 + 80 - 95 + 69 ^ 107 + 72 - 53 + 27 ^ -" ".length())) <= "  ".length()) continue;
            return;
        }
    }

    public ArrayList<ItemStack> getChestStartingContents(int lIIIIllllIllIll) {
        ArrayList<ItemStack> lIIIIllllIllIlI = new ArrayList<ItemStack>();
        "".length();
        lIIIIllllIllIlI.add(new ItemStack(Material.LOG, llIllllI[2] * lIIIIllllIllIll));
        "".length();
        lIIIIllllIllIlI.add(new ItemStack(Material.COAL, llIllllI[4] * lIIIIllllIllIll));
        "".length();
        lIIIIllllIllIlI.add(new ItemStack(Material.COAL, llIllllI[4] * lIIIIllllIllIll));
        "".length();
        lIIIIllllIllIlI.add(new ItemStack(Material.IRON_INGOT, llIllllI[4] * lIIIIllllIllIll));
        "".length();
        lIIIIllllIllIlI.add(new ItemStack(Material.IRON_INGOT, llIllllI[4] * lIIIIllllIllIll));
        "".length();
        lIIIIllllIllIlI.add(new ItemStack(Material.GOLD_INGOT, llIllllI[4] * lIIIIllllIllIll));
        "".length();
        lIIIIllllIllIlI.add(new ItemStack(Material.EMERALD, llIllllI[2] * lIIIIllllIllIll));
        "".length();
        lIIIIllllIllIlI.add(new ItemStack(Material.DIAMOND, llIllllI[1] * lIIIIllllIllIll));
        return lIIIIllllIllIlI;
    }

    public double getMaterialStartingAmount(Material lIIIIllllIlllll) {
        switch (1.$SwitchMap$org$bukkit$Material[lIIIIllllIlllll.ordinal()]) {
            case 1: {
                return 1.0;
            }
            case 2: {
                return 1.0;
            }
            case 3: {
                return 2.0;
            }
            case 4: {
                return 4.0;
            }
            case 5: {
                return 0.0;
            }
            case 6: {
                return 4.0;
            }
            case 7: {
                return 1.0;
            }
        }
        return 0.0;
    }

    private static String llIIllllII(String lIIIIllIlIlIlII, String lIIIIllIlIlIIll) {
        try {
            SecretKeySpec lIIIIllIlIlIlll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIIllIlIlIIll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIIIllIlIlIllI = Cipher.getInstance("Blowfish");
            lIIIIllIlIlIllI.init(llIllllI[2], lIIIIllIlIlIlll);
            return new String(lIIIIllIlIlIllI.doFinal(Base64.getDecoder().decode(lIIIIllIlIlIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIIllIlIlIlIl) {
            lIIIIllIlIlIlIl.printStackTrace();
            return null;
        }
    }

    protected void onPlayerPickupItem(PlayerPickupItemEvent lIIIIlllIlllIll, Player lIIIIlllIlllIlI) {
        ResourceRush lIIIIlllIllllII;
        super.onPlayerPickupItem(lIIIIlllIlllIll, lIIIIlllIlllIlI);
        double lIIIIlllIllllIl = lIIIIlllIllllII.getMaterialValue(lIIIIlllIlllIll.getItem().getItemStack().getType()) / 2.0;
        lIIIIlllIlllIll.getPlayer().damage(lIIIIlllIllllIl, (Entity)lIIIIlllIlllIll.getItem());
        lIIIIlllIllllII.sendPlayerMessage(lIIIIlllIlllIlI, String.valueOf(new StringBuilder().append((Object)ChatColor.GRAY).append(llIllIII[llIllllI[5]])));
    }

    private static boolean llIlIlIIII(Object object) {
        return object == null;
    }

    protected void onPlayerDropItem(PlayerDropItemEvent lIIIIllllIIIlll, Player lIIIIllllIIIllI) {
        ResourceRush lIIIIllllIIlIII;
        super.onPlayerDropItem(lIIIIllllIIIlll, lIIIIllllIIIllI);
        Item lIIIIllllIIlIIl = lIIIIllllIIIlll.getItemDrop();
    }

    private static void llIlIIIlIl() {
        llIllIII = new String[llIllllI[11]];
        ResourceRush.llIllIII[ResourceRush.llIllllI[0]] = ResourceRush.llIIlllIlI("cQ+wJJP9aCo=", "WTAuF");
        ResourceRush.llIllIII[ResourceRush.llIllllI[1]] = ResourceRush.llIIlllIll("JRkFHw==", "Gudjc");
        ResourceRush.llIllIII[ResourceRush.llIllllI[2]] = ResourceRush.llIIllllII("wVzyEocPl9vAZLpmb+tOAw==", "RunoZ");
        ResourceRush.llIllIII[ResourceRush.llIllllI[5]] = ResourceRush.llIIlllIlI("dWjrrPw59fdM9NHb1B6itPzocke2Jg258pCepPBfmYhKhY7a8zHGrW2QQAgNxpDeM7M04rJ5GoD+/0TMuPoNQn4DZnXl+A9E", "gcxxV");
        ResourceRush.llIllIII[ResourceRush.llIllllI[4]] = ResourceRush.llIIllllII("YVKwANJUhxGoMda/GN3PxA==", "gpnFD");
        ResourceRush.llIllIII[ResourceRush.llIllllI[6]] = ResourceRush.llIIllllII("wA9/rYZ1PA7ocG6gJCySS+QSBh9mxf0qH6wTlBjbUC9uBNMGGjzedQ==", "WwiKW");
        ResourceRush.llIllIII[ResourceRush.llIllllI[7]] = ResourceRush.llIIlllIll("VQAZDR0QEFYbCgdDExoaHBNX", "ucvko");
        ResourceRush.llIllIII[ResourceRush.llIllllI[9]] = ResourceRush.llIIlllIlI("Wch3YIxOYQImPhJSA++tUg==", "IfLtl");
        ResourceRush.llIllIII[ResourceRush.llIllllI[10]] = ResourceRush.llIIllllII("x+eJ1bw+OOrl7N0RhzNSXQ==", "HvVbY");
    }

    @Override
    protected void setCustomGameRules() {
    }

    @Override
    protected ArrayList<JocEquips.Equip> getDesiredTeams() {
        ResourceRush lIIIIllllllIlIl;
        ArrayList<JocEquips.Equip> lIIIIllllllIllI = new ArrayList<JocEquips.Equip>();
        "".length();
        lIIIIllllllIllI.add(new JocTeamScoreRace.EquipScoreRace(lIIIIllllllIlIl, DyeColor.RED, llIllIII[llIllllI[0]]));
        "".length();
        lIIIIllllllIllI.add(new JocTeamScoreRace.EquipScoreRace(lIIIIllllllIlIl, DyeColor.BLUE, llIllIII[llIllllI[1]]));
        return lIIIIllllllIllI;
    }

    @Override
    protected int getFinishScore() {
        ResourceRush lIIIIlllllIIlll;
        return llIllllI[3] * lIIIIlllllIIlll.getPlayers().size();
    }

    @Override
    public void heartbeat() {
        ResourceRush lIIIIllllIlIIlI;
        super.heartbeat();
    }

    public void fillResourceSources() {
        ResourceRush lIIIIlllIllIIll;
        Iterator<Location> lIIIIlllIllIIlI = lIIIIlllIllIIll.pMapaActual().ObtenirLocations(llIllIII[llIllllI[4]]).iterator();
        while (ResourceRush.llIlIIllII((int)lIIIIlllIllIIlI.hasNext())) {
            Location lIIIIlllIllIlIl = lIIIIlllIllIIlI.next();
            lIIIIlllIllIIll.fillResourceSource(lIIIIlllIllIlIl);
            "".length();
            if (-" ".length() < 0) continue;
            return;
        }
        lIIIIlllIllIIll.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(llIllIII[llIllllI[6]]).append((Object)ChatColor.YELLOW).append(lIIIIlllIllIIll.getTeamFilledChests()).append((Object)ChatColor.GREEN).append(llIllIII[llIllllI[7]])));
    }

    private static String llIIlllIll(String lIIIIllIllIlIIl, String lIIIIllIllIlIII) {
        lIIIIllIllIlIIl = new String(Base64.getDecoder().decode(lIIIIllIllIlIIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIIIllIllIIlll = new StringBuilder();
        char[] lIIIIllIllIIllI = lIIIIllIllIlIII.toCharArray();
        int lIIIIllIllIIlIl = llIllllI[0];
        char[] lIIIIllIlIlllll = lIIIIllIllIlIIl.toCharArray();
        int lIIIIllIlIllllI = lIIIIllIlIlllll.length;
        int lIIIIllIlIlllIl = llIllllI[0];
        while (ResourceRush.llIlIIllll(lIIIIllIlIlllIl, lIIIIllIlIllllI)) {
            char lIIIIllIllIlIlI = lIIIIllIlIlllll[lIIIIllIlIlllIl];
            "".length();
            lIIIIllIllIIlll.append((char)(lIIIIllIllIlIlI ^ lIIIIllIllIIllI[lIIIIllIllIIlIl % lIIIIllIllIIllI.length]));
            ++lIIIIllIllIIlIl;
            ++lIIIIllIlIlllIl;
            "".length();
            if ("  ".length() <= "  ".length()) continue;
            return null;
        }
        return String.valueOf(lIIIIllIllIIlll);
    }

    public int getTeamFilledChests() {
        ResourceRush lIIIIlllIIllIll;
        return llIllllI[2] + Math.round(lIIIIlllIIllIll.getPlayers().size() / llIllllI[5]);
    }

    public ChatColor getMaterialColor(Material lIIIIllllIlIlII) {
        switch (1.$SwitchMap$org$bukkit$Material[lIIIIllllIlIlII.ordinal()]) {
            case 1: {
                return ChatColor.AQUA;
            }
            case 2: {
                return ChatColor.DARK_GREEN;
            }
            case 3: {
                return ChatColor.GOLD;
            }
            case 4: {
                return ChatColor.DARK_GRAY;
            }
            case 5: {
                return ChatColor.DARK_BLUE;
            }
            case 6: {
                return ChatColor.BLACK;
            }
            case 7: {
                return ChatColor.GRAY;
            }
        }
        return ChatColor.WHITE;
    }

    static {
        ResourceRush.llIlIIlIll();
        ResourceRush.llIlIIIlIl();
    }

    private static boolean llIlIIllII(int n) {
        return n != 0;
    }

    private static boolean llIlIIlllI(int n, int n2) {
        return n > n2;
    }

    private static void llIlIIlIll() {
        llIllllI = new int[12];
        ResourceRush.llIllllI[0] = (61 ^ 10) & ~(6 ^ 49);
        ResourceRush.llIllllI[1] = " ".length();
        ResourceRush.llIllllI[2] = "  ".length();
        ResourceRush.llIllllI[3] = 195 ^ 166 ^ (89 ^ 14);
        ResourceRush.llIllllI[4] = 142 ^ 138;
        ResourceRush.llIllllI[5] = "   ".length();
        ResourceRush.llIllllI[6] = 206 ^ 182 ^ (121 ^ 4);
        ResourceRush.llIllllI[7] = 9 ^ 3 ^ (179 ^ 191);
        ResourceRush.llIllllI[8] = 158 ^ 148;
        ResourceRush.llIllllI[9] = 103 ^ 96;
        ResourceRush.llIllllI[10] = 133 + 128 - 208 + 115 ^ 63 + 19 - 62 + 140;
        ResourceRush.llIllllI[11] = 114 ^ 123;
    }

    @Override
    public String getGameName() {
        return llIllIII[llIllllI[2]];
    }

    private static String llIIlllIlI(String lIIIIllIlllIlll, String lIIIIllIlllIllI) {
        try {
            SecretKeySpec lIIIIllIlllllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIIllIlllIllI.getBytes(StandardCharsets.UTF_8)), llIllllI[10]), "DES");
            Cipher lIIIIllIllllIll = Cipher.getInstance("DES");
            lIIIIllIllllIll.init(llIllllI[2], lIIIIllIlllllII);
            return new String(lIIIIllIllllIll.doFinal(Base64.getDecoder().decode(lIIIIllIlllIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIIIllIllllIlI) {
            lIIIIllIllllIlI.printStackTrace();
            return null;
        }
    }

    public void deliverPlayerContents(Player lIIIIlllIIIllIl) {
        ItemStack[] lIIIIlllIIIlIll;
        ResourceRush lIIIIlllIIIlllI;
        int lIIIIlllIIIllII = llIllllI[0];
        ItemStack[] lIIIIlllIIIIlIl = lIIIIlllIIIlIll = lIIIIlllIIIllIl.getInventory().getContents();
        int lIIIIlllIIIIlII = lIIIIlllIIIIlIl.length;
        int lIIIIlllIIIIIll = llIllllI[0];
        while (ResourceRush.llIlIIllll(lIIIIlllIIIIIll, lIIIIlllIIIIlII)) {
            ItemStack lIIIIlllIIIllll = lIIIIlllIIIIlIl[lIIIIlllIIIIIll];
            if (ResourceRush.llIlIlIIII((Object)lIIIIlllIIIllll)) {
                "".length();
                if (-" ".length() > "  ".length()) {
                    return;
                }
            } else {
                double lIIIIlllIIlIIII = lIIIIlllIIIlllI.getMaterialValue(lIIIIlllIIIllll.getType());
                lIIIIlllIIIllII = (int)((double)lIIIIlllIIIllII + (double)lIIIIlllIIIllll.getAmount() * lIIIIlllIIlIIII);
            }
            ++lIIIIlllIIIIIll;
            "".length();
            if ((209 ^ 178 ^ (252 ^ 155)) >= -" ".length()) continue;
            return;
        }
        JocTeamScoreRace.EquipScoreRace lIIIIlllIIIlIlI = (JocTeamScoreRace.EquipScoreRace)lIIIIlllIIIlllI.obtenirEquip(lIIIIlllIIIllIl);
        lIIIIlllIIIlllI.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)lIIIIlllIIIlIlI.getChatColor()).append(lIIIIlllIIIllIl.getName()).append((Object)ChatColor.WHITE).append(llIllIII[llIllllI[9]]).append((Object)ChatColor.BOLD).append(lIIIIlllIIIllII).append((Object)ChatColor.RESET).append(llIllIII[llIllllI[10]])));
        lIIIIlllIIIlIlI.incrementScore(lIIIIlllIIIllII);
    }

    private static boolean llIlIIllIl(Object object, Object object2) {
        return object == object2;
    }

    public class ValueStatusEffect
    extends StatusEffect {
        private static final /* synthetic */ String[] lIlllII;
        private static final /* synthetic */ int[] llIIlII;

        @Override
        public String getDescription() {
            return null;
        }

        private static boolean lIllIIlll(Object object) {
            return object == null;
        }

        public int getCarriedValue() {
            ItemStack[] llIIlIlIIllllIl;
            ValueStatusEffect llIIlIlIIllllII;
            int llIIlIlIIlllllI = llIIlII[0];
            ItemStack[] llIIlIlIIlllIIl = llIIlIlIIllllIl = llIIlIlIIllllII.getPlayer().getInventory().getContents();
            int llIIlIlIIlllIII = llIIlIlIIlllIIl.length;
            int llIIlIlIIllIlll = llIIlII[0];
            while (ValueStatusEffect.lIllIIllI(llIIlIlIIllIlll, llIIlIlIIlllIII)) {
                ItemStack llIIlIlIlIIIIII = llIIlIlIIlllIIl[llIIlIlIIllIlll];
                if (ValueStatusEffect.lIllIIlll((Object)llIIlIlIlIIIIII)) {
                    "".length();
                    if (null != null) {
                        return (80 ^ 100 ^ (87 ^ 52)) & (167 ^ 133 ^ (86 ^ 35) ^ -" ".length());
                    }
                } else {
                    double llIIlIlIlIIIIIl = llIIlIlIIllllII.ResourceRush.this.getMaterialValue(llIIlIlIlIIIIII.getType());
                    llIIlIlIIlllllI = (int)((double)llIIlIlIIlllllI + (double)llIIlIlIlIIIIII.getAmount() * llIIlIlIlIIIIIl);
                }
                ++llIIlIlIIllIlll;
                "".length();
                if ("   ".length() != "  ".length()) continue;
                return (44 ^ 20) & ~(106 ^ 82);
            }
            return llIIlIlIIlllllI;
        }

        @Override
        public String getName() {
            ValueStatusEffect llIIlIlIlIIlIll;
            return String.valueOf(new StringBuilder().append(lIlllII[llIIlII[0]]).append(llIIlIlIlIIlIll.getCarriedValue()));
        }

        public ValueStatusEffect(Player llIIlIlIlIIlllI) {
            ValueStatusEffect llIIlIlIlIlIIll;
            super(llIIlIlIlIIlllI);
        }

        private static void lIllIIlIl() {
            llIIlII = new int[4];
            ValueStatusEffect.llIIlII[0] = (136 ^ 165) & ~(28 ^ 49);
            ValueStatusEffect.llIIlII[1] = " ".length();
            ValueStatusEffect.llIIlII[2] = 118 + 96 - 200 + 118 ^ 129 + 107 - 115 + 19;
            ValueStatusEffect.llIIlII[3] = "  ".length();
        }

        private static void lIlIlIllI() {
            lIlllII = new String[llIIlII[1]];
            ValueStatusEffect.lIlllII[ValueStatusEffect.llIIlII[0]] = ValueStatusEffect.lIlIIlIlI("5fmLqx1ZG0I=", "qdsQY");
        }

        private static String lIlIIlIlI(String llIIlIlIIlIllIl, String llIIlIlIIlIlIlI) {
            try {
                SecretKeySpec llIIlIlIIllIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIlIlIIlIlIlI.getBytes(StandardCharsets.UTF_8)), llIIlII[2]), "DES");
                Cipher llIIlIlIIlIllll = Cipher.getInstance("DES");
                llIIlIlIIlIllll.init(llIIlII[3], llIIlIlIIllIIII);
                return new String(llIIlIlIIlIllll.doFinal(Base64.getDecoder().decode(llIIlIlIIlIllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llIIlIlIIlIlllI) {
                llIIlIlIIlIlllI.printStackTrace();
                return null;
            }
        }

        private static boolean lIllIIllI(int n, int n2) {
            return n < n2;
        }

        static {
            ValueStatusEffect.lIllIIlIl();
            ValueStatusEffect.lIlIlIllI();
        }
    }

    public class WeightStatusEffect
    extends StatusEffect {
        private static final /* synthetic */ int[] lIllll;
        private static final /* synthetic */ String[] lIllIl;

        @Override
        public String getName() {
            WeightStatusEffect llllIIIlIIIIIll;
            String llllIIIlIIIIlII = lIllIl[lIllll[0]];
            if (WeightStatusEffect.lIIIllII(llllIIIlIIIIIll.getSpeedPercentage())) {
                llllIIIlIIIIlII = lIllIl[lIllll[1]];
            }
            return String.valueOf(new StringBuilder().append(lIllIl[lIllll[2]]).append(Math.round(llllIIIlIIIIIll.getWeight() * 10.0) / 10L).append(lIllIl[lIllll[3]]).append(llllIIIlIIIIlII).append(llllIIIlIIIIIll.getSpeedPercentage()).append(lIllIl[lIllll[4]]));
        }

        public double getUnitWeight(Material llllIIIIllIIIll) {
            String llllIIIIllIIlII = llllIIIIllIIIll.name();
            if (WeightStatusEffect.lIIIllll((int)llllIIIIllIIlII.contains(lIllIl[lIllll[5]]))) {
                return 5.0;
            }
            if (WeightStatusEffect.lIIlIIII((Object)llllIIIIllIIIll, (Object)Material.ANVIL)) {
                return 12.0;
            }
            return 1.0;
        }

        static {
            WeightStatusEffect.lIIIlIlI();
            WeightStatusEffect.lIIIIlll();
        }

        private static String lIIIIllI(String llllIIIIIIllIll, String llllIIIIIIlllll) {
            llllIIIIIIllIll = new String(Base64.getDecoder().decode(llllIIIIIIllIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llllIIIIIIllllI = new StringBuilder();
            char[] llllIIIIIIlllIl = llllIIIIIIlllll.toCharArray();
            int llllIIIIIIlllII = lIllll[0];
            char[] llllIIIIIIlIllI = llllIIIIIIllIll.toCharArray();
            int llllIIIIIIlIlIl = llllIIIIIIlIllI.length;
            int llllIIIIIIlIlII = lIllll[0];
            while (WeightStatusEffect.lIIIllIl(llllIIIIIIlIlII, llllIIIIIIlIlIl)) {
                char llllIIIIIlIIIIl = llllIIIIIIlIllI[llllIIIIIIlIlII];
                "".length();
                llllIIIIIIllllI.append((char)(llllIIIIIlIIIIl ^ llllIIIIIIlllIl[llllIIIIIIlllII % llllIIIIIIlllIl.length]));
                ++llllIIIIIIlllII;
                ++llllIIIIIIlIlII;
                "".length();
                if (-(33 ^ 80 ^ (44 ^ 89)) < 0) continue;
                return null;
            }
            return String.valueOf(llllIIIIIIllllI);
        }

        public double getWeight() {
            WeightStatusEffect llllIIIIlllIIII;
            ItemStack[] llllIIIIlllIIIl;
            int llllIIIIlllIIlI = lIllll[0];
            ItemStack[] llllIIIIllIllIl = llllIIIIlllIIIl = llllIIIIlllIIII.getPlayer().getInventory().getContents();
            int llllIIIIllIllII = llllIIIIllIllIl.length;
            int llllIIIIllIlIll = lIllll[0];
            while (WeightStatusEffect.lIIIllIl(llllIIIIllIlIll, llllIIIIllIllII)) {
                ItemStack llllIIIIlllIlII = llllIIIIllIllIl[llllIIIIllIlIll];
                if (WeightStatusEffect.lIIIlllI((Object)llllIIIIlllIlII)) {
                    "".length();
                    if ("   ".length() == 0) {
                        return 0.0;
                    }
                } else {
                    double llllIIIIlllIlIl = llllIIIIlllIIII.getUnitWeight(llllIIIIlllIlII.getType());
                    llllIIIIlllIIlI = (int)((double)llllIIIIlllIIlI + (double)llllIIIIlllIlII.getAmount() * llllIIIIlllIlIl);
                }
                ++llllIIIIllIlIll;
                "".length();
                if (-(96 ^ 101) < 0) continue;
                return 0.0;
            }
            return llllIIIIlllIIlI;
        }

        private static String lIIIIlII(String llllIIIIIllllIl, String llllIIIIIlllIlI) {
            try {
                SecretKeySpec llllIIIIlIIIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIIIIIlllIlI.getBytes(StandardCharsets.UTF_8)), lIllll[12]), "DES");
                Cipher llllIIIIIllllll = Cipher.getInstance("DES");
                llllIIIIIllllll.init(lIllll[2], llllIIIIlIIIIII);
                return new String(llllIIIIIllllll.doFinal(Base64.getDecoder().decode(llllIIIIIllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIIIIIlllllI) {
                llllIIIIIlllllI.printStackTrace();
                return null;
            }
        }

        private static boolean lIIIllIl(int n, int n2) {
            return n < n2;
        }

        public WeightStatusEffect(Player llllIIIlIIIlIII) {
            WeightStatusEffect llllIIIlIIIllIl;
            super(llllIIIlIIIlIII);
            llllIIIlIIIllIl.setType(StatusEffect.StatusEffectType.SKILL_TRAY);
        }

        private static boolean lIIlIIIl(int n) {
            return n > 0;
        }

        private static void lIIIlIlI() {
            lIllll = new int[13];
            WeightStatusEffect.lIllll[0] = (94 ^ 17) & ~(124 ^ 51);
            WeightStatusEffect.lIllll[1] = " ".length();
            WeightStatusEffect.lIllll[2] = "  ".length();
            WeightStatusEffect.lIllll[3] = "   ".length();
            WeightStatusEffect.lIllll[4] = 34 ^ 110 ^ (211 ^ 155);
            WeightStatusEffect.lIllll[5] = 79 + 84 - 93 + 94 ^ 123 + 96 - 192 + 134;
            WeightStatusEffect.lIllll[6] = 113 ^ 104;
            WeightStatusEffect.lIllll[7] = -" ".length();
            WeightStatusEffect.lIllll[8] = 204 ^ 168;
            WeightStatusEffect.lIllll[9] = 168 ^ 188;
            WeightStatusEffect.lIllll[10] = 42 ^ 12 ^ (178 ^ 155);
            WeightStatusEffect.lIllll[11] = 251 ^ 195 ^ (65 ^ 127);
            WeightStatusEffect.lIllll[12] = 16 + 49 - 54 + 195 ^ 105 + 111 - 108 + 90;
        }

        private static boolean lIIIllII(int n) {
            return n < 0;
        }

        private static boolean lIIlIIII(Object object, Object object2) {
            return object == object2;
        }

        private static void lIIIIlll() {
            lIllIl = new String[lIllll[11]];
            WeightStatusEffect.lIllIl[WeightStatusEffect.lIllll[0]] = WeightStatusEffect.lIIIIlII("8mmdn1LuVrk=", "OwwEq");
            WeightStatusEffect.lIllIl[WeightStatusEffect.lIllll[1]] = WeightStatusEffect.lIIIIlIl("bLLs9iSEUeM=", "DlSBK");
            WeightStatusEffect.lIllIl[WeightStatusEffect.lIllll[2]] = WeightStatusEffect.lIIIIlIl("68xgF+QLVQ0=", "WdqkP");
            WeightStatusEffect.lIllIl[WeightStatusEffect.lIllll[3]] = WeightStatusEffect.lIIIIlII("a/nwxAXDxII=", "vbCRH");
            WeightStatusEffect.lIllIl[WeightStatusEffect.lIllll[4]] = WeightStatusEffect.lIIIIllI("c2wDACU5LxwRKCJl", "VLueI");
            WeightStatusEffect.lIllIl[WeightStatusEffect.lIllll[5]] = WeightStatusEffect.lIIIIlIl("DkoURGLOIKA=", "QVlNZ");
        }

        public void updatePotionEffects() {
            WeightStatusEffect llllIIIIlIlIlII;
            int llllIIIIlIlIIll = llllIIIIlIlIlII.getSpeedPercentage();
            int llllIIIIlIlIIlI = lIllll[0];
            int llllIIIIlIlIIIl = lIllll[0];
            if (WeightStatusEffect.lIIlIIIl(llllIIIIlIlIIll)) {
                llllIIIIlIlIIlI = (int)((double)llllIIIIlIlIIlI + Math.floor((double)llllIIIIlIlIIll / 20.0));
                double llllIIIIlIlIllI = (double)llllIIIIlIlIIll / 20.0 - (double)llllIIIIlIlIIlI;
                llllIIIIlIlIIlI = (int)((double)llllIIIIlIlIIlI + llllIIIIlIlIllI * 4.0);
                llllIIIIlIlIIIl = (int)((double)llllIIIIlIlIIIl + llllIIIIlIlIllI * 4.0);
                "".length();
                if ("  ".length() == 0) {
                    return;
                }
            } else {
                llllIIIIlIlIIIl = (int)((double)llllIIIIlIlIIIl + Math.floor((double)(llllIIIIlIlIIll *= lIllll[7]) / 15.0));
                double llllIIIIlIlIlIl = (double)llllIIIIlIlIIll / 15.0 - (double)llllIIIIlIlIIIl;
                llllIIIIlIlIIIl = (int)((long)llllIIIIlIlIIIl + Math.round(llllIIIIlIlIlIl * 3.0 * 3.0));
                llllIIIIlIlIIlI = (int)((long)llllIIIIlIlIIlI + Math.round(llllIIIIlIlIlIl * 3.0 * 2.0));
            }
            if (WeightStatusEffect.lIIlIIIl(llllIIIIlIlIIlI)) {
                "".length();
                llllIIIIlIlIlII.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, lIllll[8], llllIIIIlIlIIlI - lIllll[1], lIllll[1], lIllll[1]));
                "".length();
                if ("   ".length() != "   ".length()) {
                    return;
                }
            } else {
                llllIIIIlIlIlII.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            }
            if (WeightStatusEffect.lIIlIIIl(llllIIIIlIlIIIl)) {
                "".length();
                llllIIIIlIlIlII.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, lIllll[8], llllIIIIlIlIIIl - lIllll[1], lIllll[1], lIllll[1]));
                "".length();
                if ((194 ^ 198) < 0) {
                    return;
                }
            } else {
                llllIIIIlIlIlII.getPlayer().removePotionEffect(PotionEffectType.SLOW);
            }
        }

        public int getSpeedPercentage() {
            WeightStatusEffect llllIIIIlIlllll;
            int llllIIIIlIllllI = lIllll[6];
            return (int)((long)llllIIIIlIllllI - Math.round(llllIIIIlIlllll.getWeight()) * 5L);
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public void tick() {
            WeightStatusEffect llllIIIIllllllI;
            super.tick();
            llllIIIIllllllI.updatePotionEffects();
        }

        private static boolean lIIIllll(int n) {
            return n != 0;
        }

        private static boolean lIIIlllI(Object object) {
            return object == null;
        }

        private int getModifier(int llllIIIIlIIIllI, int llllIIIIlIIIlIl) {
            return lIllll[9] * llllIIIIlIIIllI - lIllll[10] * llllIIIIlIIIlIl;
        }

        private static String lIIIIlIl(String llllIIIIIlIlllI, String llllIIIIIlIllll) {
            try {
                SecretKeySpec llllIIIIIllIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllIIIIIlIllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher llllIIIIIllIIlI = Cipher.getInstance("Blowfish");
                llllIIIIIllIIlI.init(lIllll[2], llllIIIIIllIIll);
                return new String(llllIIIIIllIIlI.doFinal(Base64.getDecoder().decode(llllIIIIIlIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIIIIIllIIIl) {
                llllIIIIIllIIIl.printStackTrace();
                return null;
            }
        }
    }

}

