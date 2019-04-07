/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.events.PlayerWorldEventBus
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.biel.lobby.utilities.events.skills;

import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.GestorMapes;
import com.biel.lobby.Mapa;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.Joc;
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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Skill
extends PlayerWorldEventBus {
    private static final /* synthetic */ int[] lIlllIlI;
    private /* synthetic */ Player player;
    public /* synthetic */ int id;
    private static final /* synthetic */ String[] lIlIlIll;

    public Joc.PlayerInfo getPlayerInfo() {
        Skill lIIllIlIIIIllIl;
        return lIIllIlIIIIllIl.getPlayerInfo(lIIllIlIIIIllIl.getPlayer());
    }

    public void setPlayer(Player lIIllIlIlIIlIlI) {
        lIIllIlIlIIlIll.player = lIIllIlIlIIlIlI;
    }

    private static int lIllIllIIl(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean hasValue() {
        boolean bl;
        if (Skill.lIllIlIIll(Skill.lIllIllIIl(Skill.getMaxValue(), 0.0))) {
            bl = lIlllIlI[2];
            "".length();
            if (null != null) {
                return (boolean)((158 ^ 182) & ~(118 ^ 94));
            }
        } else {
            bl = lIlllIlI[0];
        }
        return bl;
    }

    public Joc.PlayerInfo getPlayerInfo(Player lIIllIlIIIlIIII) {
        Skill lIIllIlIIIlIIIl;
        if (Skill.lIllIlIlIl((Object)lIIllIlIIIlIIIl.player)) {
            return null;
        }
        return lIIllIlIIIlIIIl.getGame().getPlayerInfo(lIIllIlIIIlIIII);
    }

    private static Double getValue() {
        return 0.0;
    }

    protected void sendPlayerMessage(String lIIllIlIIlIIlll) {
        Skill lIIllIlIIlIlIII;
        lIIllIlIIlIlIII.sendPlayerMessage(lIIllIlIIlIlIII.getPlayer(), lIIllIlIIlIIlll);
    }

    public int getTier() {
        return lIlllIlI[4];
    }

    private static int getCharsForDescLine() {
        return lIlllIlI[8];
    }

    public Byte getData() {
        return lIlllIlI[0];
    }

    public Skill() {
        Skill lIIllIlIlIllIII;
        super(null);
        lIIllIlIlIllIII.id = Utils.NombreEntre(lIlllIlI[0], lIlllIlI[1]);
    }

    private static boolean lIllIlllll(int n, int n2) {
        return n == n2;
    }

    protected ItemStack getItemStack() {
        Skill lIIllIIllIIIIII;
        ItemStack lIIllIIlIllllll = Utils.setItemNameAndLore(new ItemStack(lIIllIIllIIIIII.getMaterial(), lIlllIlI[2], lIIllIIllIIIIII.getDamageValue().shortValue(), lIIllIIllIIIIII.getData()), String.valueOf(new StringBuilder().append((Object)lIIllIIllIIIIII.getTierChatColor()).append(lIIllIIllIIIIII.getName())), lIIllIIllIIIIII.getLoreArr());
        lIIllIIlIllllll.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, lIIllIIllIIIIII.getTier());
        return lIIllIIlIllllll;
    }

    public ChatColor getTierChatColor() {
        Skill lIIllIlIIIIIlIl;
        switch (lIIllIlIIIIIlIl.getTier()) {
            case 1: {
                return ChatColor.YELLOW;
            }
            case 2: {
                return ChatColor.AQUA;
            }
            case 3: {
                return ChatColor.DARK_PURPLE;
            }
        }
        return ChatColor.WHITE;
    }

    private static boolean lIlllIIIII(int n, int n2) {
        return n > n2;
    }

    private static boolean lIllIlllII(int n) {
        return n > 0;
    }

    public boolean isValid() {
        Skill lIIllIlIlIIIlIl;
        int n;
        if (Skill.lIllIlIIll((int)super.isValid()) && Skill.lIllIlIIll((int)lIIllIlIlIIIlIl.getPlayer().getWorld().equals((Object)lIIllIlIlIIIlIl.getWorld())) && Skill.lIllIlIlII((Object)lIIllIlIlIIIlIl.getGame())) {
            n = lIlllIlI[2];
            "".length();
            if ("   ".length() == 0) {
                return (boolean)((180 ^ 155) & ~(60 ^ 19));
            }
        } else {
            n = lIlllIlI[0];
        }
        return (boolean)n;
    }

    public String getValueLine() {
        if (Skill.lIllIlIIll((int)Skill.hasValue())) {
            Double lIIllIIlllIllII = Skill.getValue();
            Double lIIllIIlllIlIll = (double)Math.round(lIIllIIlllIllII * 100.0) / 100.0;
            String lIIllIIlllIlIlI = String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(Double.toString(lIIllIIlllIlIll)));
            if (Skill.lIllIlllII(Skill.lIllIllIlI(Skill.getMaxValue(), 0.0))) {
                Double lIIllIIlllIlllI = Skill.getMaxValue();
                Double lIIllIIlllIllIl = (double)Math.round(lIIllIIlllIlllI * 100.0) / 100.0;
                lIIllIIlllIlIlI = String.valueOf(new StringBuilder().append(lIIllIIlllIlIlI).append(lIlIlIll[lIlllIlI[5]]).append(Double.toString(lIIllIIlllIllIl)));
                if (Skill.lIllIlllII(Skill.lIllIllIlI(lIIllIIlllIlllI, 3.0))) {
                    Double lIIllIIllllIIII = lIIllIIlllIllII / lIIllIIlllIlllI;
                    lIIllIIllllIIII = lIIllIIllllIIII * 100.0;
                    Double lIIllIIlllIllll = (double)Math.round(lIIllIIllllIIII * 10.0) / 10.0;
                    lIIllIIlllIlIlI = String.valueOf(new StringBuilder().append(lIIllIIlllIlIlI).append(lIlIlIll[lIlllIlI[6]]).append(Double.toString(lIIllIIlllIllll)).append(lIlIlIll[lIlllIlI[7]]));
                }
            }
            return lIIllIIlllIlIlI;
        }
        return null;
    }

    private static boolean lIllIlIIll(int n) {
        return n != 0;
    }

    private static boolean lIllIlIlIl(Object object) {
        return object == null;
    }

    private static boolean lIllIllllI(int n, int n2) {
        return n <= n2;
    }

    protected void sendSkillMessage(Player lIIllIlIIIlIlll, String lIIllIlIIIllIIl) {
        Skill lIIllIlIIIllIll;
        lIIllIlIIIllIll.sendPlayerMessage(lIIllIlIIIlIlll, String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_AQUA).append(lIlIlIll[lIlllIlI[0]]).append(lIIllIlIIIllIll.getName()).append(lIlIlIll[lIlllIlI[2]]).append((Object)ChatColor.GRAY).append(lIIllIlIIIllIIl)));
    }

    public Boolean hasDataField() {
        return lIlllIlI[0];
    }

    private static int lIllIllIlI(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    protected void sendPlayerMessage(Player lIIllIlIIlIllll, String lIIllIlIIlIlllI) {
        Skill lIIllIlIIlIllIl;
        lIIllIlIIlIllIl.getGame().sendPlayerMessage(lIIllIlIIlIllll, lIIllIlIIlIlllI);
    }

    public abstract String getName();

    public Player getPlayer() {
        Skill lIIllIlIlIIlllI;
        return lIIllIlIlIIlllI.player;
    }

    protected void sendGlobalMessage(String lIIllIlIIllIlII) {
        Skill lIIllIlIIllIlll;
        lIIllIlIIllIlll.getGame().sendGlobalMessage(lIIllIlIIllIlII);
    }

    protected int getTickSpacing() {
        return lIlllIlI[3];
    }

    private static Double getMaxValue() {
        return 0.0;
    }

    private static String lIlIIlIlII(String lIIllIIlIIlIIII, String lIIllIIlIIIllIl) {
        try {
            SecretKeySpec lIIllIIlIIlIIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIllIIlIIIllIl.getBytes(StandardCharsets.UTF_8)), lIlllIlI[10]), "DES");
            Cipher lIIllIIlIIlIIlI = Cipher.getInstance("DES");
            lIIllIIlIIlIIlI.init(lIlllIlI[5], lIIllIIlIIlIIll);
            return new String(lIIllIIlIIlIIlI.doFinal(Base64.getDecoder().decode(lIIllIIlIIlIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIllIIlIIlIIIl) {
            lIIllIIlIIlIIIl.printStackTrace();
            return null;
        }
    }

    public void tick() {
    }

    public Short getDamageValue() {
        return lIlllIlI[0];
    }

    public abstract String getDescription();

    static {
        Skill.lIllIlIIIl();
        Skill.lIlIIlIlll();
    }

    public String getValueName() {
        return null;
    }

    protected void removeDefaultNamedAura() {
        Skill lIIllIlIlIIIIIl;
        lIIllIlIlIIIIIl.getPlayerInfo().removeAura(lIIllIlIlIIIIIl.getName());
    }

    protected Joc getGame() {
        Skill lIIllIlIIlllIll;
        Mapa lIIllIlIIllllII = Com.getPlugin().gest.getMapWherePlayerIs(lIIllIlIIlllIll.getPlayer());
        if (Skill.lIllIlIlIl((Object)((Object)lIIllIlIIllllII))) {
            return null;
        }
        if (Skill.lIllIlIIll(lIIllIlIIllllII instanceof Joc)) {
            return (Joc)lIIllIlIIllllII;
        }
        return null;
    }

    private static String lIlIIlIllI(String lIIllIIlIlIIlIl, String lIIllIIlIIlllll) {
        lIIllIIlIlIIlIl = new String(Base64.getDecoder().decode(lIIllIIlIlIIlIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIIllIIlIlIIIll = new StringBuilder();
        char[] lIIllIIlIlIIIlI = lIIllIIlIIlllll.toCharArray();
        int lIIllIIlIlIIIIl = lIlllIlI[0];
        char[] lIIllIIlIIllIll = lIIllIIlIlIIlIl.toCharArray();
        int lIIllIIlIIllIlI = lIIllIIlIIllIll.length;
        int lIIllIIlIIllIIl = lIlllIlI[0];
        while (Skill.lIlllIIIIl(lIIllIIlIIllIIl, lIIllIIlIIllIlI)) {
            char lIIllIIlIlIIllI = lIIllIIlIIllIll[lIIllIIlIIllIIl];
            "".length();
            lIIllIIlIlIIIll.append((char)(lIIllIIlIlIIllI ^ lIIllIIlIlIIIlI[lIIllIIlIlIIIIl % lIIllIIlIlIIIlI.length]));
            ++lIIllIIlIlIIIIl;
            ++lIIllIIlIIllIIl;
            "".length();
            if (" ".length() != 0) continue;
            return null;
        }
        return String.valueOf(lIIllIIlIlIIIll);
    }

    public Skill(Player lIIllIlIlIlIIll) {
        Skill lIIllIlIlIlIlII;
        super(lIIllIlIlIlIIll);
        lIIllIlIlIlIlII.id = Utils.NombreEntre(lIlllIlI[0], lIlllIlI[1]);
        lIIllIlIlIlIlII.player = lIIllIlIlIlIIll;
    }

    private String[] getLoreArr() {
        Skill lIIllIIllllllII;
        ArrayList<String> lIIllIIllllllll = lIIllIIllllllII.getDescLines();
        String lIIllIIlllllllI = lIIllIIllllllII.getValueLine();
        if (Skill.lIllIlIlII(lIIllIIlllllllI)) {
            "".length();
            lIIllIIllllllll.add(lIIllIIlllllllI);
        }
        String[] lIIllIIllllllIl = lIIllIIllllllll.toArray(new String[lIIllIIllllllll.size() - lIlllIlI[2]]);
        return lIIllIIllllllIl;
    }

    private static boolean lIlllIIIIl(int n, int n2) {
        return n < n2;
    }

    private static String lIlIIlIlIl(String lIIllIIlIllIIll, String lIIllIIlIllIIlI) {
        try {
            SecretKeySpec lIIllIIlIlllIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIllIIlIllIIlI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIIllIIlIllIlll = Cipher.getInstance("Blowfish");
            lIIllIIlIllIlll.init(lIlllIlI[5], lIIllIIlIlllIII);
            return new String(lIIllIIlIllIlll.doFinal(Base64.getDecoder().decode(lIIllIIlIllIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIIllIIlIllIllI) {
            lIIllIIlIllIllI.printStackTrace();
            return null;
        }
    }

    protected void sendSkillMessage(String lIIllIlIIIlllll) {
        Skill lIIllIlIIlIIIlI;
        lIIllIlIIlIIIlI.sendSkillMessage(lIIllIlIIlIIIlI.getPlayer(), lIIllIlIIIlllll);
    }

    private static void lIlIIlIlll() {
        lIlIlIll = new String[lIlllIlI[9]];
        Skill.lIlIlIll[Skill.lIlllIlI[0]] = Skill.lIlIIlIlII("Hha9djrlIwQ=", "qgBvX");
        Skill.lIlIlIll[Skill.lIlllIlI[2]] = Skill.lIlIIlIlII("S14l+/BXuR4=", "aNtVp");
        Skill.lIlIlIll[Skill.lIlllIlI[5]] = Skill.lIlIIlIlIl("kztMUYgzPYo=", "qcKlm");
        Skill.lIlIlIll[Skill.lIlllIlI[6]] = Skill.lIlIIlIllI("VGhZ", "tEyFi");
        Skill.lIlIlIll[Skill.lIlllIlI[7]] = Skill.lIlIIlIlIl("kyLSiHSQucg=", "jqgRt");
    }

    private static void lIllIlIIIl() {
        lIlllIlI = new int[11];
        Skill.lIlllIlI[0] = (236 ^ 198) & ~(8 ^ 34);
        Skill.lIlllIlI[1] = 29 ^ 121;
        Skill.lIlllIlI[2] = " ".length();
        Skill.lIlllIlI[3] = 33 ^ 53;
        Skill.lIlllIlI[4] = -" ".length();
        Skill.lIlllIlI[5] = "  ".length();
        Skill.lIlllIlI[6] = "   ".length();
        Skill.lIlllIlI[7] = 42 ^ 56 ^ (138 ^ 156);
        Skill.lIlllIlI[8] = 77 ^ 83 ^ (80 ^ 110);
        Skill.lIlllIlI[9] = 10 ^ 15;
        Skill.lIlllIlI[10] = 66 ^ 76 ^ (72 ^ 78);
    }

    private static boolean lIllIlIlII(Object object) {
        return object != null;
    }

    public Material getMaterial() {
        return Material.DIAMOND;
    }

    public ArrayList<String> getDescLines() {
        Skill lIIllIIllIlIIll;
        ArrayList<String> lIIllIIllIlIIlI = new ArrayList<String>();
        String lIIllIIllIlIIIl = lIIllIIllIlIIll.getDescription();
        int lIIllIIllIlIIII = lIlllIlI[0];
        int lIIllIIllIIllll = lIlllIlI[0];
        int lIIllIIllIIlllI = Skill.getCharsForDescLine();
        int lIIllIIllIIllIl = lIIllIIllIlIIIl.length();
        while (Skill.lIllIllllI(lIIllIIllIlIIII, lIIllIIllIIllIl)) {
            if (Skill.lIllIlllll(lIIllIIllIlIIII, lIIllIIllIIllIl)) {
                String lIIllIIllIlIlll = lIIllIIllIlIIIl.substring(lIIllIIllIIllll, lIIllIIllIlIIII);
                "".length();
                lIIllIIllIlIIlI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIllIIllIlIlll.trim())));
                "".length();
                if (null != null) {
                    return null;
                }
            } else {
                int lIIllIIllIlIlIl;
                char lIIllIIllIlIlII = lIIllIIllIlIIIl.charAt(lIIllIIllIlIIII);
                if (Skill.lIllIlllll(lIIllIIllIlIlII, lIlllIlI[8]) && Skill.lIlllIIIII(lIIllIIllIlIlIl = lIIllIIllIlIIII - lIIllIIllIIllll, lIIllIIllIIlllI)) {
                    String lIIllIIllIlIllI = lIIllIIllIlIIIl.substring(lIIllIIllIIllll, lIIllIIllIlIIII);
                    "".length();
                    lIIllIIllIlIIlI.add(String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(lIIllIIllIlIllI.trim())));
                    lIIllIIllIIllll = lIIllIIllIlIIII;
                }
            }
            ++lIIllIIllIlIIII;
            "".length();
            if ("  ".length() > 0) continue;
            return null;
        }
        return lIIllIIllIlIIlI;
    }
}

