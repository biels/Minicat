/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerItemHeldEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package com.biel.lobby.utilities.events.skills.types;

import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.events.skills.types.InherentSkill;
import com.biel.lobby.utilities.events.statuseffects.StatusEffect;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class ItemAttatchedModeSkill
extends InherentSkill {
    private /* synthetic */ ArrayList<SkillMode> modes;
    /* synthetic */ int selectedMode;
    private static final /* synthetic */ String[] lllIIlI;
    /* synthetic */ boolean knowsHowToUse;
    /* synthetic */ boolean trayAdded;
    private static final /* synthetic */ int[] lllIlII;

    public List<String> getModeFormattedList() {
        ItemAttatchedModeSkill llIIIlllIIIIlIl;
        return llIIIlllIIIIlIl.getModes().stream().map(llIIIllIlIIIlIl -> String.valueOf(new StringBuilder().append(lllIIlI[lllIlII[8]]).append(llIIIllIlIIIlIl.toString()))).collect(Collectors.toList());
    }

    private ItemAttatchedModeSkillTrayEffect getTrayEffectInstance() {
        try {
            ItemAttatchedModeSkill llIIIllIlIIllII;
            Constructor<?> llIIIllIlIIlllI = llIIIllIlIIllII.getTrayEffectClass().getConstructors()[lllIlII[0]];
            Object[] arrobject = new Object[lllIlII[2]];
            arrobject[ItemAttatchedModeSkill.lllIlII[0]] = llIIIllIlIIllII;
            arrobject[ItemAttatchedModeSkill.lllIlII[1]] = llIIIllIlIIllII.getPlayer();
            return (ItemAttatchedModeSkillTrayEffect)((Object)llIIIllIlIIlllI.newInstance(arrobject));
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException | InvocationTargetException llIIIllIlIIllIl) {
            llIIIllIlIIllIl.printStackTrace();
            System.out.println(lllIIlI[lllIlII[7]]);
            return null;
        }
    }

    protected void onPlayerItemHeld(PlayerItemHeldEvent llIIIllIlllIIII, Player llIIIllIlllIlII) {
        ItemAttatchedModeSkill llIIIllIlllIIIl;
        super.onPlayerItemHeld(llIIIllIlllIIII, llIIIllIlllIlII);
        PlayerInventory llIIIllIlllIIll = llIIIllIlllIIIl.getPlayer().getInventory();
        ItemStack llIIIllIlllIIlI = llIIIllIlllIIll.getContents()[llIIIllIlllIIII.getPreviousSlot()];
        if (ItemAttatchedModeSkill.llIIlIIII((Object)llIIIllIlllIIlI) && ItemAttatchedModeSkill.llIIlIIIl((int)llIIIllIlllIIIl.matchesItem(llIIIllIlllIIlI))) {
            llIIIllIlllIIIl.tellHowToUse();
            "".length();
            llIIIllIlllIIIl.getTrayEffect();
        }
        if (ItemAttatchedModeSkill.llIIlIIIl((int)llIIIllIlllIIIl.matchesItem(llIIIllIlllIIlI)) && ItemAttatchedModeSkill.llIIlIIIl((int)llIIIllIlllIlII.isSneaking())) {
            int llIIIllIlllIlll = llIIIllIlllIIII.getNewSlot() - llIIIllIlllIIII.getPreviousSlot();
            llIIIllIlllIIIl.getPlayer().getInventory().setHeldItemSlot(llIIIllIlllIIII.getPreviousSlot());
            llIIIllIlllIIIl.scroll(llIIIllIlllIlll);
        }
    }

    public SkillMode getSelectedMode() {
        ItemAttatchedModeSkill llIIIlllIIIlIll;
        if (ItemAttatchedModeSkill.llIIIlllI(llIIIlllIIIlIll.selectedMode) && ItemAttatchedModeSkill.llIIIllll(llIIIlllIIIlIll.selectedMode, llIIIlllIIIlIll.modes.size())) {
            return llIIIlllIIIlIll.modes.get(llIIIlllIIIlIll.selectedMode);
        }
        return null;
    }

    private static void llIIIIlll() {
        lllIIlI = new String[lllIlII[9]];
        ItemAttatchedModeSkill.lllIIlI[ItemAttatchedModeSkill.lllIlII[0]] = ItemAttatchedModeSkill.llIIIIIll("xLRTBHg71buke/jiB/1mPkHPRYRY7OqGKsJrYiyoGXwcXFGFqQiWPA==", "dMSYF");
        ItemAttatchedModeSkill.lllIIlI[ItemAttatchedModeSkill.lllIlII[1]] = ItemAttatchedModeSkill.llIIIIlII("e2M=", "WCMla");
        ItemAttatchedModeSkill.lllIIlI[ItemAttatchedModeSkill.lllIlII[2]] = ItemAttatchedModeSkill.llIIIIlII("Hg==", "EfAPn");
        ItemAttatchedModeSkill.lllIIlI[ItemAttatchedModeSkill.lllIlII[3]] = ItemAttatchedModeSkill.llIIIIllI("eSWp29ec904=", "lnwvW");
        ItemAttatchedModeSkill.lllIIlI[ItemAttatchedModeSkill.lllIlII[5]] = ItemAttatchedModeSkill.llIIIIllI("DiU7+MkgK9KeyLI3BMdozD7xl9j+qPO+eZhwdmzqZi98Vpj2dA9qdQ==", "VptAx");
        ItemAttatchedModeSkill.lllIIlI[ItemAttatchedModeSkill.lllIlII[6]] = ItemAttatchedModeSkill.llIIIIlII("Bzk2aRoIOjEt", "iVXIl");
        ItemAttatchedModeSkill.lllIIlI[ItemAttatchedModeSkill.lllIlII[7]] = ItemAttatchedModeSkill.llIIIIIll("CPdwDC3brXco7wfwov0LiJfe6/4qdbEN", "KjWAk");
        ItemAttatchedModeSkill.lllIIlI[ItemAttatchedModeSkill.lllIlII[8]] = ItemAttatchedModeSkill.llIIIIllI("rdG0VNJPc4M=", "BhqxZ");
    }

    private static void llIIIllII() {
        lllIlII = new int[10];
        ItemAttatchedModeSkill.lllIlII[0] = (50 + 18 - -55 + 10 ^ 133 + 26 - 67 + 55) & (86 + 58 - 22 + 43 ^ 104 + 56 - 133 + 152 ^ -" ".length());
        ItemAttatchedModeSkill.lllIlII[1] = " ".length();
        ItemAttatchedModeSkill.lllIlII[2] = "  ".length();
        ItemAttatchedModeSkill.lllIlII[3] = "   ".length();
        ItemAttatchedModeSkill.lllIlII[4] = -" ".length();
        ItemAttatchedModeSkill.lllIlII[5] = 145 + 145 - 264 + 158 ^ 80 + 39 - 10 + 79;
        ItemAttatchedModeSkill.lllIlII[6] = 130 ^ 135;
        ItemAttatchedModeSkill.lllIlII[7] = 200 ^ 192 ^ (155 ^ 149);
        ItemAttatchedModeSkill.lllIlII[8] = 35 ^ 36;
        ItemAttatchedModeSkill.lllIlII[9] = 7 ^ 15 ^ (236 ^ 188) & ~(223 ^ 143);
    }

    private static boolean llIIlIIII(Object object) {
        return object != null;
    }

    private static boolean llIIlIIll(int n, int n2) {
        return n >= n2;
    }

    protected abstract Class<? extends ItemAttatchedModeSkillTrayEffect> getTrayEffectClass();

    private static String llIIIIllI(String llIIIllIIllllII, String llIIIllIIlllIIl) {
        try {
            SecretKeySpec llIIIllIIllllll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIllIIlllIIl.getBytes(StandardCharsets.UTF_8)), lllIlII[9]), "DES");
            Cipher llIIIllIIlllllI = Cipher.getInstance("DES");
            llIIIllIIlllllI.init(lllIlII[2], llIIIllIIllllll);
            return new String(llIIIllIIlllllI.doFinal(Base64.getDecoder().decode(llIIIllIIllllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIllIIllllIl) {
            llIIIllIIllllIl.printStackTrace();
            return null;
        }
    }

    public ArrayList<SkillMode> getModes() {
        ItemAttatchedModeSkill llIIIlllIIIllll;
        return llIIIlllIIIllll.modes;
    }

    private static boolean llIIlIIIl(int n) {
        return n != 0;
    }

    public abstract boolean matchesItem(ItemStack var1);

    public void scroll(boolean llIIIllIllIIlll) {
        ItemAttatchedModeSkill llIIIllIllIlIII;
        int n;
        if (ItemAttatchedModeSkill.llIIlIIIl((int)llIIIllIllIIlll)) {
            n = lllIlII[4];
            "".length();
            if (" ".length() <= 0) {
                return;
            }
        } else {
            n = lllIlII[1];
        }
        int llIIIllIllIIllI = n;
        llIIIllIllIlIII.scroll(llIIIllIllIIllI);
    }

    public void scroll(int llIIIllIlIlllll) {
        ItemAttatchedModeSkill llIIIllIllIIIII;
        llIIIllIllIIIII.selectedMode += llIIIllIlIlllll;
        if (ItemAttatchedModeSkill.llIIlIIlI(llIIIllIllIIIII.selectedMode)) {
            llIIIllIllIIIII.selectedMode = llIIIllIllIIIII.modes.size() - lllIlII[1];
        }
        if (ItemAttatchedModeSkill.llIIlIIll(llIIIllIllIIIII.selectedMode, llIIIllIllIIIII.modes.size())) {
            llIIIllIllIIIII.selectedMode = lllIlII[0];
        }
        llIIIllIllIIIII.onModeSwitch(llIIIllIllIIIII.getSelectedMode());
    }

    public String getModeList() {
        ItemAttatchedModeSkill llIIIlllIIIIIIl;
        return llIIIlllIIIIIIl.getModes().stream().map(llIIIllIlIIlIII -> String.valueOf(new StringBuilder().append((Object)llIIIllIlIIlIII.getChatColor()).append(llIIIllIlIIlIII.getName()).append((Object)ChatColor.WHITE))).collect(Collectors.joining(lllIIlI[lllIlII[1]], lllIIlI[lllIlII[2]], lllIIlI[lllIlII[3]]));
    }

    public void onModeSwitch(SkillMode llIIIlllIIIlIII) {
        llIIIlllIIIIlll.knowsHowToUse = lllIlII[1];
    }

    public boolean isInHand() {
        ItemAttatchedModeSkill llIIIllIllllllI;
        return llIIIllIllllllI.matchesItem(llIIIllIllllllI.getPlayer().getItemInHand());
    }

    static {
        ItemAttatchedModeSkill.llIIIllII();
        ItemAttatchedModeSkill.llIIIIlll();
    }

    private static boolean llIIIllIl(int n) {
        return n == 0;
    }

    private static String llIIIIIll(String llIIIllIIIlIlll, String llIIIllIIIlIlII) {
        try {
            SecretKeySpec llIIIllIIIllIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIllIIIlIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIIllIIIllIIl = Cipher.getInstance("Blowfish");
            llIIIllIIIllIIl.init(lllIlII[2], llIIIllIIIllIlI);
            return new String(llIIIllIIIllIIl.doFinal(Base64.getDecoder().decode(llIIIllIIIlIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIllIIIllIII) {
            llIIIllIIIllIII.printStackTrace();
            return null;
        }
    }

    protected ItemAttatchedModeSkillTrayEffect getTrayEffect() {
        ItemAttatchedModeSkill llIIIllIlIlIIlI;
        Joc.PlayerInfo llIIIllIlIlIIll = llIIIllIlIlIIlI.getPlayerInfo(llIIIllIlIlIIlI.getPlayer());
        if (ItemAttatchedModeSkill.llIIIllIl((int)llIIIllIlIlIIlI.trayAdded)) {
            llIIIllIlIlIIll.addStatusEffect(llIIIllIlIlIIlI.getTrayEffectInstance());
        }
        llIIIllIlIlIIlI.trayAdded = lllIlII[1];
        if (ItemAttatchedModeSkill.llIIIllIl((int)llIIIllIlIlIIll.getStatusEffect(llIIIllIlIlIIlI.getTrayEffectClass()).isValid())) {
            llIIIllIlIlIIlI.sendGlobalMessage(lllIIlI[lllIlII[6]]);
        }
        return llIIIllIlIlIIll.getStatusEffect(llIIIllIlIlIIlI.getTrayEffectClass());
    }

    public void registerMode(SkillMode llIIIlllIIlIIIl) {
        ItemAttatchedModeSkill llIIIlllIIlIIlI;
        "".length();
        llIIIlllIIlIIlI.modes.add(llIIIlllIIlIIIl);
    }

    public ItemAttatchedModeSkill(Player llIIIlllIIllIIl) {
        ItemAttatchedModeSkill llIIIlllIIllIlI;
        super(llIIIlllIIllIIl);
        llIIIlllIIllIlI.modes = new ArrayList<E>();
        llIIIlllIIllIlI.knowsHowToUse = lllIlII[0];
        llIIIlllIIllIlI.trayAdded = lllIlII[0];
        llIIIlllIIllIlI.selectedMode = lllIlII[0];
        llIIIlllIIllIlI.registerModes();
        if (ItemAttatchedModeSkill.llIIIllIl(llIIIlllIIllIlI.modes.size())) {
            llIIIlllIIllIlI.sendSkillMessage(lllIIlI[lllIlII[0]]);
        }
    }

    private static String llIIIIlII(String llIIIllIIlIllII, String llIIIllIIlIlIll) {
        llIIIllIIlIllII = new String(Base64.getDecoder().decode(llIIIllIIlIllII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIIllIIlIlIlI = new StringBuilder();
        char[] llIIIllIIlIlIIl = llIIIllIIlIlIll.toCharArray();
        int llIIIllIIlIlIII = lllIlII[0];
        char[] llIIIllIIlIIIlI = llIIIllIIlIllII.toCharArray();
        int llIIIllIIlIIIIl = llIIIllIIlIIIlI.length;
        int llIIIllIIlIIIII = lllIlII[0];
        while (ItemAttatchedModeSkill.llIIIllll(llIIIllIIlIIIII, llIIIllIIlIIIIl)) {
            char llIIIllIIlIllIl = llIIIllIIlIIIlI[llIIIllIIlIIIII];
            "".length();
            llIIIllIIlIlIlI.append((char)(llIIIllIIlIllIl ^ llIIIllIIlIlIIl[llIIIllIIlIlIII % llIIIllIIlIlIIl.length]));
            ++llIIIllIIlIlIII;
            ++llIIIllIIlIIIII;
            "".length();
            if (null == null) continue;
            return null;
        }
        return String.valueOf(llIIIllIIlIlIlI);
    }

    public abstract void registerModes();

    private void tellHowToUse() {
        ItemAttatchedModeSkill llIIIllIlIllIlI;
        if (ItemAttatchedModeSkill.llIIIllIl((int)llIIIllIlIllIlI.knowsHowToUse)) {
            llIIIllIlIllIlI.sendSkillMessage(lllIIlI[lllIlII[5]]);
        }
    }

    @Override
    public void tick() {
        ItemAttatchedModeSkill llIIIllIlIllIII;
        super.tick();
    }

    private static boolean llIIlIIlI(int n) {
        return n < 0;
    }

    private static boolean llIIIllll(int n, int n2) {
        return n < n2;
    }

    private static boolean llIIIlllI(int n) {
        return n >= 0;
    }

    public class ItemAttatchedModeSkillTrayEffect
    extends StatusEffect {
        private static final /* synthetic */ String[] lIII;
        private static final /* synthetic */ int[] lIIl;

        public ItemAttatchedModeSkillTrayEffect(Player lllllllIlIlIIIl) {
            ItemAttatchedModeSkillTrayEffect lllllllIlIlIIII;
            super(lllllllIlIlIIIl);
            lllllllIlIlIIII.setType(StatusEffect.StatusEffectType.SKILL_TRAY);
        }

        private static void llIlI() {
            lIIl = new int[4];
            ItemAttatchedModeSkillTrayEffect.lIIl[0] = (147 ^ 161) & ~(33 ^ 19);
            ItemAttatchedModeSkillTrayEffect.lIIl[1] = " ".length();
            ItemAttatchedModeSkillTrayEffect.lIIl[2] = "  ".length();
            ItemAttatchedModeSkillTrayEffect.lIIl[3] = 25 ^ 17;
        }

        private static void lIIll() {
            lIII = new String[lIIl[2]];
            ItemAttatchedModeSkillTrayEffect.lIII[ItemAttatchedModeSkillTrayEffect.lIIl[0]] = ItemAttatchedModeSkillTrayEffect.lllI("ghb4rJTZ2CI=", "RouZt");
            ItemAttatchedModeSkillTrayEffect.lIII[ItemAttatchedModeSkillTrayEffect.lIIl[1]] = ItemAttatchedModeSkillTrayEffect.lIIIl("MkmsdmZp1ec=", "hKrbA");
        }

        private static boolean llIll(int n) {
            return n == 0;
        }

        @Override
        public String getDisplayText() {
            ItemAttatchedModeSkillTrayEffect lllllllIlIIlIII;
            if (ItemAttatchedModeSkillTrayEffect.llIll((int)lllllllIlIIlIII.ItemAttatchedModeSkill.this.isInHand())) {
                return null;
            }
            return super.getDisplayText();
        }

        static {
            ItemAttatchedModeSkillTrayEffect.llIlI();
            ItemAttatchedModeSkillTrayEffect.lIIll();
        }

        @Override
        public String getName() {
            ItemAttatchedModeSkillTrayEffect lllllllIlIIlIll;
            return lllllllIlIIlIll.ItemAttatchedModeSkill.this.getSelectedMode().getName();
        }

        @Override
        public String getDescription() {
            ItemAttatchedModeSkillTrayEffect lllllllIlIIIllI;
            return String.valueOf(new StringBuilder().append(lIII[lIIl[0]]).append(lllllllIlIIIllI.ItemAttatchedModeSkill.this.getSelectedMode().getName()).append(lIII[lIIl[1]]).append(lllllllIlIIIllI.ItemAttatchedModeSkill.this.getSelectedMode().getDescription()));
        }

        private static String lIIIl(String lllllllIIlIlIll, String lllllllIIlIlIlI) {
            try {
                SecretKeySpec lllllllIIllIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllllllIIlIlIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lllllllIIlIllll = Cipher.getInstance("Blowfish");
                lllllllIIlIllll.init(lIIl[2], lllllllIIllIIII);
                return new String(lllllllIIlIllll.doFinal(Base64.getDecoder().decode(lllllllIIlIlIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lllllllIIlIlllI) {
                lllllllIIlIlllI.printStackTrace();
                return null;
            }
        }

        @Override
        public ChatColor getChatColor() {
            ItemAttatchedModeSkillTrayEffect lllllllIlIIIIll;
            if (ItemAttatchedModeSkillTrayEffect.lllII((Object)lllllllIlIIIIll.ItemAttatchedModeSkill.this.getSelectedMode().getChatColor())) {
                return lllllllIlIIIIll.ItemAttatchedModeSkill.this.getSelectedMode().getChatColor();
            }
            return super.getChatColor();
        }

        private static boolean lllII(Object object) {
            return object != null;
        }

        private static String lllI(String lllllllIIlllIlI, String lllllllIIllIlll) {
            try {
                SecretKeySpec lllllllIIllllIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllllIIllIlll.getBytes(StandardCharsets.UTF_8)), lIIl[3]), "DES");
                Cipher lllllllIIllllII = Cipher.getInstance("DES");
                lllllllIIllllII.init(lIIl[2], lllllllIIllllIl);
                return new String(lllllllIIllllII.doFinal(Base64.getDecoder().decode(lllllllIIlllIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lllllllIIlllIll) {
                lllllllIIlllIll.printStackTrace();
                return null;
            }
        }
    }

    public class SkillMode {
        private static final /* synthetic */ String[] llIIIIll;
        private /* synthetic */ String description;
        private /* synthetic */ ChatColor chatColor;
        private /* synthetic */ String name;
        private /* synthetic */ int id;
        private static final /* synthetic */ int[] llIIlIlI;

        public SkillMode(int lIIIlllIllIlIlI, String lIIIlllIllIlIIl, String lIIIlllIllIlIII, ChatColor lIIIlllIllIllIl) {
            SkillMode lIIIlllIllIllII;
            lIIIlllIllIllII.id = lIIIlllIllIlIlI;
            lIIIlllIllIllII.name = lIIIlllIllIlIIl;
            lIIIlllIllIllII.description = lIIIlllIllIlIII;
            lIIIlllIllIllII.chatColor = lIIIlllIllIllIl;
        }

        private static boolean llIIlIIlII(int n, int n2) {
            return n == n2;
        }

        public boolean equals(Object lIIIlllIlIIIIlI) {
            SkillMode lIIIlllIlIIIIIl;
            if (SkillMode.llIIlIIIll(lIIIlllIlIIIIlI instanceof Integer)) {
                boolean bl;
                if (SkillMode.llIIlIIlII((Integer)lIIIlllIlIIIIlI, lIIIlllIlIIIIIl.getId())) {
                    bl = llIIlIlI[2];
                    "".length();
                    if (" ".length() <= ((27 + 101 - 92 + 108 ^ 40 + 30 - 54 + 180) & (56 + 191 - -2 + 6 ^ 110 + 123 - 91 + 29 ^ -" ".length()))) {
                        return (boolean)(("   ".length() ^ (103 ^ 36)) & (78 ^ 106 ^ (6 ^ 98) ^ -" ".length()));
                    }
                } else {
                    bl = llIIlIlI[0];
                }
                return bl;
            }
            if (SkillMode.llIIlIIIll(lIIIlllIlIIIIlI instanceof SkillMode)) {
                boolean bl;
                if (SkillMode.llIIlIIlII(((SkillMode)lIIIlllIlIIIIlI).getId(), lIIIlllIlIIIIIl.getId())) {
                    bl = llIIlIlI[2];
                    "".length();
                    if (null != null) {
                        return (boolean)("   ".length() & ("   ".length() ^ -" ".length()));
                    }
                } else {
                    bl = llIIlIlI[0];
                }
                return bl;
            }
            return super.equals(lIIIlllIlIIIIlI);
        }

        private static boolean llIIlIIIll(int n) {
            return n != 0;
        }

        public SkillMode(int lIIIlllIllllIll, String lIIIlllIlllllll, String lIIIlllIllllIIl) {
            SkillMode lIIIllllIIIIIlI;
            lIIIllllIIIIIlI.id = lIIIlllIllllIll;
            lIIIllllIIIIIlI.name = lIIIlllIlllllll;
            lIIIllllIIIIIlI.description = lIIIlllIllllIIl;
        }

        private static void llIIIIIIIl() {
            llIIIIll = new String[llIIlIlI[2]];
            SkillMode.llIIIIll[SkillMode.llIIlIlI[0]] = SkillMode.llIIIIIIII("AoUkHARP2oC5ZjpKDOggUQ==", "reGIO");
        }

        public ChatColor getChatColor() {
            SkillMode lIIIlllIlIlIIII;
            return lIIIlllIlIlIIII.chatColor;
        }

        public void setName(String lIIIlllIlIllIll) {
            lIIIlllIlIllllI.name = lIIIlllIlIllIll;
        }

        public String getDescription() {
            SkillMode lIIIlllIlIllIII;
            return lIIIlllIlIllIII.description;
        }

        public String toString() {
            SkillMode lIIIlllIlIIIlll;
            Object[] arrobject = new Object[llIIlIlI[1]];
            arrobject[SkillMode.llIIlIlI[0]] = lIIIlllIlIIIlll.chatColor;
            arrobject[SkillMode.llIIlIlI[2]] = lIIIlllIlIIIlll.name;
            arrobject[SkillMode.llIIlIlI[3]] = ChatColor.WHITE;
            arrobject[SkillMode.llIIlIlI[4]] = lIIIlllIlIIIlll.description;
            return MessageFormat.format(llIIIIll[llIIlIlI[0]], arrobject);
        }

        public int getId() {
            SkillMode lIIIlllIllIIlII;
            return lIIIlllIllIIlII.id;
        }

        public void setDescription(String lIIIlllIlIlIlII) {
            lIIIlllIlIlIlIl.description = lIIIlllIlIlIlII;
        }

        static {
            SkillMode.llIIlIIIII();
            SkillMode.llIIIIIIIl();
        }

        public String getName() {
            SkillMode lIIIlllIllIIIlI;
            return lIIIlllIllIIIlI.name;
        }

        private static String llIIIIIIII(String lIIIlllIIllIlIl, String lIIIlllIIllIlII) {
            try {
                SecretKeySpec lIIIlllIIlllIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIlllIIllIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIIlllIIllIlll = Cipher.getInstance("Blowfish");
                lIIIlllIIllIlll.init(llIIlIlI[3], lIIIlllIIlllIII);
                return new String(lIIIlllIIllIlll.doFinal(Base64.getDecoder().decode(lIIIlllIIllIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIlllIIllIllI) {
                lIIIlllIIllIllI.printStackTrace();
                return null;
            }
        }

        public void setChatColor(ChatColor lIIIlllIlIIlIll) {
            lIIIlllIlIIlIlI.chatColor = lIIIlllIlIIlIll;
        }

        public int hashCode() {
            SkillMode lIIIlllIIllllIl;
            return lIIIlllIIllllIl.getId();
        }

        private static void llIIlIIIII() {
            llIIlIlI = new int[5];
            SkillMode.llIIlIlI[0] = (25 ^ 70) & ~(241 ^ 174);
            SkillMode.llIIlIlI[1] = 6 ^ 2;
            SkillMode.llIIlIlI[2] = " ".length();
            SkillMode.llIIlIlI[3] = "  ".length();
            SkillMode.llIIlIlI[4] = "   ".length();
        }
    }

}

