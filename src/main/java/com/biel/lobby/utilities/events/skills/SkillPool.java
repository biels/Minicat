/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.Utils.IconMenu
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEvent
 *  com.biel.BielAPI.Utils.IconMenu$OptionClickEventHandler
 *  com.biel.BielAPI.Utils.ItemButton
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEvent
 *  com.biel.BielAPI.Utils.ItemButton$OptionClickEventHandler
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.biel.lobby.utilities.events.skills;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.ItemButton;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.skills.Skill;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class SkillPool {
    private static final /* synthetic */ int[] lIlI;
    /* synthetic */ ArrayList<Skill> registered;
    private static final /* synthetic */ String[] I;
    /* synthetic */ ArrayList<Skill> skills;

    public void giveRemainingUnlockers(Player llllllIlIIIlllI) {
    }

    public ArrayList<Skill> getSkillsForPlayer(Player lllllllIIIIlIII) {
        SkillPool lllllllIIIIlIIl;
        ArrayList<Skill> lllllllIIIIIlll = new ArrayList<Skill>();
        Iterator<Skill> lllllllIIIIIIll = lllllllIIIIlIIl.skills.iterator();
        while (SkillPool.lllll((int)lllllllIIIIIIll.hasNext())) {
            Skill lllllllIIIIlIlI = lllllllIIIIIIll.next();
            String lllllllIIIIlIll = lllllllIIIIlIlI.getPlayer().getName();
            if (SkillPool.lllll((int)lllllllIIIIlIll.equals(lllllllIIIIlIII.getName()))) {
                "".length();
                lllllllIIIIIlll.add(lllllllIIIIlIlI);
            }
            "".length();
            if ((124 ^ 120) >= ((73 ^ 14) & ~(43 ^ 108))) continue;
            return null;
        }
        return lllllllIIIIIlll;
    }

    private static boolean lIIIlI(int n, int n2) {
        return n == n2;
    }

    public SkillPool() {
        SkillPool lllllllIIlIIIIl;
        lllllllIIlIIIIl.registered = new ArrayList();
        lllllllIIlIIIIl.skills = new ArrayList();
    }

    private static void llIl() {
        I = new String[lIlI[11]];
        SkillPool.I[SkillPool.lIlI[1]] = SkillPool.lI("sAn1ExGyOzFQ9wLLBopV+Zs+BYFScv7m", "YMebF");
        SkillPool.I[SkillPool.lIlI[0]] = SkillPool.lI("p+QYa5nq+Oniy5Z8pq9AwSIPXqSDQwuz", "WPKhG");
        SkillPool.I[SkillPool.lIlI[4]] = SkillPool.lIlI("mgWltLHNCokPKFWV3MzQEw==", "PWRbv");
        SkillPool.I[SkillPool.lIlI[5]] = SkillPool.lIlI("Ix0Af+hkfump87OswKKbnkPS8GbzcdQm+uELf0IthNDcZrZns+6lseO/hmQRgiKG", "JZdhN");
    }

    public void removeSkill(Skill lllllllIIIlIlll) {
        SkillPool lllllllIIIllIII;
        if (SkillPool.lllll((int)lllllllIIIllIII.skills.contains((Object)lllllllIIIlIlll))) {
            "".length();
            lllllllIIIllIII.skills.remove((Object)lllllllIIIlIlll);
        }
    }

    public ItemButton getUnlockerButton(Player llllllIlIllIIII, int llllllIlIlIllll) {
        SkillPool llllllIlIllIIIl;
        ItemStack llllllIlIlIlllI = new ItemStack(Material.CHEST);
        llllllIlIlIlllI.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, lIlI[0]);
        String[] arrstring = new String[lIlI[4]];
        arrstring[SkillPool.lIlI[1]] = String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(I[lIlI[5]]));
        arrstring[SkillPool.lIlI[0]] = Integer.toString(llllllIlIlIllll);
        ItemButton llllllIlIlIllIl = new ItemButton(Utils.setItemNameAndLore(llllllIlIlIlllI, String.valueOf(new StringBuilder().append((Object)ChatColor.AQUA).append(I[lIlI[4]]).append(llllllIlIlIllll)), arrstring), llllllIlIllIIII, llllllIlIIIlIIl -> {
            SkillPool llllllIlIIIIlll;
            int llllllIlIIIlIII = (Integer)llllllIlIIIlIIl.getData();
            llllllIlIIIIlll.openSelectionMenu(llllllIlIIIlIIl.getPlayer(), lIlI[0], llllllIlIIIlIII);
        });
        llllllIlIlIllIl.setData((Object)llllllIlIlIllll);
        return llllllIlIlIllIl;
    }

    public boolean addSkill(Skill lllllllIIIlllIl) {
        SkillPool lllllllIIIllllI;
        if (SkillPool.llllI((int)lllllllIIIllllI.hasSkill(lllllllIIIlllIl.getPlayer(), ((Object)((Object)lllllllIIIlllIl)).getClass()))) {
            "".length();
            lllllllIIIllllI.skills.add(lllllllIIIlllIl);
            return lIlI[0];
        }
        return lIlI[1];
    }

    private static void lllIl() {
        lIlI = new int[12];
        SkillPool.lIlI[0] = " ".length();
        SkillPool.lIlI[1] = (18 ^ 35) & ~(241 ^ 192) & ~((181 ^ 159) & ~(234 ^ 192));
        SkillPool.lIlI[2] = 40 ^ 72 ^ (47 ^ 70);
        SkillPool.lIlI[3] = -" ".length();
        SkillPool.lIlI[4] = "  ".length();
        SkillPool.lIlI[5] = "   ".length();
        SkillPool.lIlI[6] = 112 ^ 120;
        SkillPool.lIlI[7] = 90 ^ 121;
        SkillPool.lIlI[8] = 64 ^ 90;
        SkillPool.lIlI[9] = 179 ^ 161;
        SkillPool.lIlI[10] = 177 ^ 166 ^ (147 ^ 149);
        SkillPool.lIlI[11] = 154 ^ 144 ^ (48 ^ 62);
    }

    private static boolean lllll(int n) {
        return n != 0;
    }

    public void giveUnlockers(Player llllllIlIIlIllI, int llllllIlIIlIlIl) {
        SkillPool llllllIlIIlllII;
        int n;
        if (SkillPool.lIIIIl((Object)llllllIlIIlIllI)) {
            return;
        }
        if (SkillPool.llllI(llllllIlIIlIlIl)) {
            return;
        }
        if (SkillPool.lIIIIl((Object)llllllIlIIlIllI.getInventory().getItem(lIlI[6]))) {
            n = lIlI[0];
            "".length();
            if (null != null) {
                return;
            }
        } else {
            n = lIlI[1];
        }
        int llllllIlIIllIIl = n;
        int llllllIlIIllIII = llllllIlIIlllII.getSkillsForPlayer(llllllIlIIlIllI).size() + lIlI[0];
        int llllllIlIIlllIl = lIlI[1];
        while (SkillPool.lIIIll(llllllIlIIlllIl, llllllIlIIlIlIl)) {
            int llllllIlIIlllll = lIlI[6] - llllllIlIIlllIl;
            if (SkillPool.lIIlII(llllllIlIIlllll)) {
                llllllIlIIlllll = lIlI[7] - llllllIlIIlllIl;
            }
            if (SkillPool.lIIlII(llllllIlIIlllll)) {
                llllllIlIIlllll = lIlI[8] - (llllllIlIIlllIl + lIlI[9]);
            }
            if (SkillPool.lIIlII(llllllIlIIlllll)) {
                llllllIlIIlllll = lIlI[10] - llllllIlIIlllIl;
            }
            if (SkillPool.lIIlII(llllllIlIIlllll)) {
                "".length();
                if (null == null) break;
                return;
            }
            if (SkillPool.lIIIII((Object)llllllIlIIlIllI.getInventory().getItem(llllllIlIIlllll))) {
                --llllllIlIIlllIl;
                "".length();
                if ((35 ^ 39) == 0) {
                    return;
                }
            } else {
                ItemButton llllllIlIIllllI = llllllIlIIlllII.getUnlockerButton(llllllIlIIlIllI, llllllIlIIllIII + llllllIlIIlllIl);
                llllllIlIIlIllI.getInventory().setItem(llllllIlIIlllll, llllllIlIIllllI.getItemStack());
            }
            ++llllllIlIIlllIl;
            "".length();
            if ((183 ^ 157 ^ (97 ^ 78)) != 0) continue;
            return;
        }
    }

    public void openSelectionMenu(Player llllllIllIIllIl, final boolean llllllIllIlIIlI, final int llllllIllIIlIll) {
        String llllllIllIlIIII;
        SkillPool llllllIllIIlllI;
        if (SkillPool.lllll((int)llllllIllIlIIlI)) {
            String llllllIllIlIllI = I[lIlI[1]];
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        } else {
            llllllIllIlIIII = I[lIlI[0]];
        }
        IconMenu llllllIllIIllll = new IconMenu(String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_GREEN).append(llllllIllIlIIII)), (int)(9.0 * (Math.ceil(llllllIllIIlllI.skills.size() / lIlI[2]) + 1.0 + 1.0)), new IconMenu.OptionClickEventHandler(){
            private static final /* synthetic */ int[] lIIlIll;
            private static final /* synthetic */ String[] lIIIlll;

            static {
                1.lIIlIIIlI();
                1.llllIIlI();
            }

            private static String lllIllll(String llIlIIlIlIIlIIl, String llIlIIlIlIIlIII) {
                try {
                    SecretKeySpec llIlIIlIlIIllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlIIlIlIIlIII.getBytes(StandardCharsets.UTF_8)), lIIlIll[5]), "DES");
                    Cipher llIlIIlIlIIlIll = Cipher.getInstance("DES");
                    llIlIIlIlIIlIll.init(lIIlIll[2], llIlIIlIlIIllII);
                    return new String(llIlIIlIlIIlIll.doFinal(Base64.getDecoder().decode(llIlIIlIlIIlIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception llIlIIlIlIIlIlI) {
                    llIlIIlIlIIlIlI.printStackTrace();
                    return null;
                }
            }

            private static boolean lIIlIIlIl(int n, int n2) {
                return n != n2;
            }

            private static boolean lIIlIIllI(int n, int n2) {
                return n < n2;
            }

            private static String llllIIII(String llIlIIlIIlllIlI, String llIlIIlIIlllIIl) {
                try {
                    SecretKeySpec llIlIIlIIllllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIIlIIlllIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                    Cipher llIlIIlIIlllllI = Cipher.getInstance("Blowfish");
                    llIlIIlIIlllllI.init(lIIlIll[2], llIlIIlIIllllll);
                    return new String(llIlIIlIIlllllI.doFinal(Base64.getDecoder().decode(llIlIIlIIlllIlI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
                }
                catch (Exception llIlIIlIIllllIl) {
                    llIlIIlIIllllIl.printStackTrace();
                    return null;
                }
            }

            private static boolean lIIlIIIll(int n) {
                return n != 0;
            }

            private static void llllIIlI() {
                lIIIlll = new String[lIIlIll[4]];
                1.lIIIlll[1.lIIlIll[0]] = 1.lllIllll("62+E40ipVmlRY97Vi9QRdg==", "etArB");
                1.lIIIlll[1.lIIlIll[1]] = 1.llllIIII("sY8rppvyByCMHctB8xmRgWIbKk7zlx1GEizr0Vek7nI=", "FTvrE");
                1.lIIIlll[1.lIIlIll[2]] = 1.llllIIIl("dHoMAQM2JWkBFD87LAsYwqp3ZlMDPDomBRg3MGkbEDs+JRoFOCNkXg==", "YWIsq");
            }

            private static boolean lIIlIIlII(int n, int n2) {
                return n == n2;
            }
            {
                1 llIlIIllIIlIllI;
            }

            public void removeUnlocker(Player llIlIIlIlllIlII, int llIlIIlIlllIIll) {
                PlayerInventory llIlIIlIlllIIlI = llIlIIlIlllIlII.getInventory();
                int llIlIIlIlllIIIl = lIIlIll[3];
                ListIterator llIlIIlIllIlIll = llIlIIlIlllIIlI.iterator();
                while (1.lIIlIIIll((int)llIlIIlIllIlIll.hasNext())) {
                    1 llIlIIlIlllIlIl;
                    ItemStack llIlIIlIlllIllI = (ItemStack)llIlIIlIllIlIll.next();
                    int llIlIIlIlllIlll = llIlIIlIlllIlIl.SkillPool.this.getUnlockerID(llIlIIlIlllIllI);
                    if (1.lIIlIIlII(llIlIIlIlllIIll, llIlIIlIlllIlll)) {
                        llIlIIlIlllIIIl = Arrays.asList(llIlIIlIlllIIlI.getContents()).indexOf((Object)llIlIIlIlllIllI);
                    }
                    "".length();
                    if (null == null) continue;
                    return;
                }
                if (1.lIIlIIlIl(llIlIIlIlllIIIl, lIIlIll[3])) {
                    llIlIIlIlllIIlI.setItem(llIlIIlIlllIIIl, new ItemStack(Material.AIR));
                }
            }

            private static void lIIlIIIlI() {
                lIIlIll = new int[6];
                1.lIIlIll[0] = (108 ^ 7 ^ (27 ^ 33)) & (78 ^ 123 ^ (220 ^ 184) ^ -" ".length());
                1.lIIlIll[1] = " ".length();
                1.lIIlIll[2] = "  ".length();
                1.lIIlIll[3] = -" ".length();
                1.lIIlIll[4] = "   ".length();
                1.lIIlIll[5] = 207 ^ 199;
            }

            private static String llllIIIl(String llIlIIlIlIllllI, String llIlIIlIlIllIII) {
                llIlIIlIlIllllI = new String(Base64.getDecoder().decode(llIlIIlIlIllllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                StringBuilder llIlIIlIlIlllII = new StringBuilder();
                char[] llIlIIlIlIllIll = llIlIIlIlIllIII.toCharArray();
                int llIlIIlIlIllIlI = lIIlIll[0];
                char[] llIlIIlIlIlIlII = llIlIIlIlIllllI.toCharArray();
                int llIlIIlIlIlIIll = llIlIIlIlIlIlII.length;
                int llIlIIlIlIlIIlI = lIIlIll[0];
                while (1.lIIlIIllI(llIlIIlIlIlIIlI, llIlIIlIlIlIIll)) {
                    char llIlIIlIlIlllll = llIlIIlIlIlIlII[llIlIIlIlIlIIlI];
                    "".length();
                    llIlIIlIlIlllII.append((char)(llIlIIlIlIlllll ^ llIlIIlIlIllIll[llIlIIlIlIllIlI % llIlIIlIlIllIll.length]));
                    ++llIlIIlIlIllIlI;
                    ++llIlIIlIlIlIIlI;
                    "".length();
                    if (((68 ^ 83 ^ (36 ^ 118)) & (8 ^ 59 ^ (225 ^ 151) ^ -" ".length())) == 0) continue;
                    return null;
                }
                return String.valueOf(llIlIIlIlIlllII);
            }

            public void onOptionClick(IconMenu.OptionClickEvent llIlIIllIIIIlll) {
                1 llIlIIllIIIIlIl;
                llIlIIllIIIIlll.setWillClose(llIlIIllIIIIlIl.llllllIllIlIIlI);
                int llIlIIllIIIIllI = llIlIIllIIIIlll.getPosition();
                System.out.println(lIIIlll[lIIlIll[0]]);
                if (1.lIIlIIIll((int)llIlIIllIIIIlIl.llllllIllIlIIlI)) {
                    Skill llIlIIllIIIlIIl = llIlIIllIIIIlIl.SkillPool.this.registered.get(llIlIIllIIIIllI);
                    try {
                        Class[] arrclass = new Class[lIIlIll[1]];
                        arrclass[1.lIIlIll[0]] = Player.class;
                        Object[] arrobject = new Object[lIIlIll[1]];
                        arrobject[1.lIIlIll[0]] = llIlIIllIIIIlll.getPlayer();
                        Skill llIlIIllIIIlIll = (Skill)((Object)((Object)((Object)llIlIIllIIIlIIl)).getClass().getConstructor(arrclass).newInstance(arrobject));
                        int llIlIIllIIIllII = llIlIIllIIIIlIl.SkillPool.this.addSkill(llIlIIllIIIlIll);
                        if (1.lIIlIIIll(llIlIIllIIIllII)) {
                            llIlIIllIIIlIll.getGame().sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(llIlIIllIIIIlll.getPlayer().getName()).append((Object)ChatColor.GRAY).append(lIIIlll[lIIlIll[1]]).append((Object)ChatColor.YELLOW).append(llIlIIllIIIlIll.getName())));
                            llIlIIllIIIIlIl.removeUnlocker(llIlIIllIIIIlll.getPlayer(), llIlIIllIIIIlIl.llllllIllIIlIll);
                        }
                        "".length();
                    }
                    catch (Exception llIlIIllIIIlIlI) {
                        System.out.println(lIIIlll[lIIlIll[2]]);
                        llIlIIllIIIlIlI.printStackTrace();
                    }
                    if ((61 ^ 73 ^ (48 ^ 64)) <= 0) {
                        return;
                    }
                }
            }
        });
        Iterator<Skill> llllllIllIIlIII = llllllIllIIlllI.registered.iterator();
        while (SkillPool.lllll((int)llllllIllIIlIII.hasNext())) {
            Skill llllllIllIlIlIl = llllllIllIIlIII.next();
            if (SkillPool.lllll((int)llllllIllIIlllI.hasSkill(llllllIllIIllIl, ((Object)((Object)llllllIllIlIlIl)).getClass()))) {
                "".length();
                if ("  ".length() >= ((114 + 170 - 234 + 183 ^ 107 + 112 - 120 + 89) & (32 ^ 40 ^ (221 ^ 128) ^ -" ".length()))) continue;
                return;
            }
            "".length();
            llllllIllIIllll.setOption(llllllIllIIlllI.registered.indexOf((Object)llllllIllIlIlIl), llllllIllIlIlIl.getItemStack(), llllllIllIlIlIl.getName(), llllllIllIlIlIl.getDescLines());
            "".length();
            if ("  ".length() != 0) continue;
            return;
        }
        llllllIllIIllll.open(llllllIllIIllIl);
    }

    public boolean hasSkill(Player llllllIllllIIlI, Class<? extends Skill> llllllIllllIIIl) {
        SkillPool llllllIllllIIll;
        Iterator<Skill> llllllIllllIIII = llllllIllllIIll.getSkillsForPlayer(llllllIllllIIlI).iterator();
        while (SkillPool.lllll((int)llllllIllllIIII.hasNext())) {
            String llllllIlllllIII;
            Skill llllllIllllIlll = llllllIllllIIII.next();
            String llllllIlllllIIl = ((Object)((Object)llllllIllllIlll)).getClass().getName();
            if (SkillPool.lllll((int)llllllIlllllIIl.equals(llllllIlllllIII = llllllIllllIIIl.getName()))) {
                return lIlI[0];
            }
            "".length();
            if (null == null) continue;
            return (boolean)((251 ^ 186) & ~(122 ^ 59));
        }
        return lIlI[1];
    }

    public void registerSkill(Skill llllllIlllIIIIl) {
        SkillPool llllllIlllIIIII;
        if (SkillPool.lIIIII((Object)llllllIlllIIIIl.getPlayer())) {
            llllllIlllIIIIl.setPlayer(null);
        }
        if (SkillPool.lllll((int)llllllIlllIIIII.registered.contains((Object)llllllIlllIIIIl))) {
            return;
        }
        "".length();
        llllllIlllIIIII.registered.add(llllllIlllIIIIl);
    }

    private static String lIlI(String llllllIIlllllIl, String llllllIIlllllII) {
        try {
            SecretKeySpec llllllIlIIIIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllllIIlllllII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llllllIIlllllll = Cipher.getInstance("Blowfish");
            llllllIIlllllll.init(lIlI[4], llllllIlIIIIIII);
            return new String(llllllIIlllllll.doFinal(Base64.getDecoder().decode(llllllIIlllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllllIIllllllI) {
            llllllIIllllllI.printStackTrace();
            return null;
        }
    }

    private static String lI(String llllllIIlllIIII, String llllllIIllIllll) {
        try {
            SecretKeySpec llllllIIlllIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllllIIllIllll.getBytes(StandardCharsets.UTF_8)), lIlI[6]), "DES");
            Cipher llllllIIlllIIlI = Cipher.getInstance("DES");
            llllllIIlllIIlI.init(lIlI[4], llllllIIlllIIll);
            return new String(llllllIIlllIIlI.doFinal(Base64.getDecoder().decode(llllllIIlllIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llllllIIlllIIIl) {
            llllllIIlllIIIl.printStackTrace();
            return null;
        }
    }

    public int getUnlockerID(ItemStack llllllIlIlllIll) {
        ItemMeta llllllIlIlllllI;
        List llllllIlIllllll;
        if (SkillPool.lIIIIl((Object)llllllIlIlllIll)) {
            return lIlI[3];
        }
        if (SkillPool.lllll((int)llllllIlIlllIll.hasItemMeta()) && SkillPool.lllll((int)(llllllIlIlllllI = llllllIlIlllIll.getItemMeta()).hasLore()) && SkillPool.lIIIlI((llllllIlIllllll = llllllIlIlllllI.getLore()).size(), lIlI[4])) {
            String llllllIllIIIIIl = (String)llllllIlIllllll.get(lIlI[0]);
            int llllllIllIIIIII = Integer.parseInt(llllllIllIIIIIl);
            return llllllIllIIIIII;
        }
        return lIlI[3];
    }

    private static boolean llllI(int n) {
        return n == 0;
    }

    private static boolean lIIlII(int n) {
        return n < 0;
    }

    private static boolean lIIIIl(Object object) {
        return object == null;
    }

    static {
        SkillPool.lllIl();
        SkillPool.llIl();
    }

    public void clear() {
        SkillPool lllllllIIIlIIll;
        lllllllIIIlIIll.skills.clear();
    }

    public void tickPool() {
        SkillPool llllllIlllIlIII;
        Iterator<Skill> llllllIlllIIllI = llllllIlllIlIII.skills.iterator();
        while (SkillPool.lllll((int)llllllIlllIIllI.hasNext())) {
            Skill llllllIlllIlIIl = llllllIlllIIllI.next();
            if (SkillPool.lllll((int)llllllIlllIlIIl.isValid())) {
                llllllIlllIlIIl.tick();
            }
            "".length();
            if ("  ".length() > " ".length()) continue;
            return;
        }
    }

    private static boolean lIIIll(int n, int n2) {
        return n < n2;
    }

    private static boolean lIIIII(Object object) {
        return object != null;
    }

}

