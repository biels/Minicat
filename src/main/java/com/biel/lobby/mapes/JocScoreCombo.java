/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.BookMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.biel.lobby.mapes;

import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.ScoreBoardUpdater;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class JocScoreCombo
extends Joc {
    private static final /* synthetic */ String[] lll;
    private static final /* synthetic */ int[] llIl;

    private static String llll(String lllllIIlllIllIl, String lllllIIllllIIIl) {
        lllllIIlllIllIl = new String(Base64.getDecoder().decode(lllllIIlllIllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lllllIIllllIIII = new StringBuilder();
        char[] lllllIIlllIllll = lllllIIllllIIIl.toCharArray();
        int lllllIIlllIlllI = llIl[3];
        char[] lllllIIlllIlIII = lllllIIlllIllIl.toCharArray();
        int lllllIIlllIIlll = lllllIIlllIlIII.length;
        int lllllIIlllIIllI = llIl[3];
        while (JocScoreCombo.lIllIl(lllllIIlllIIllI, lllllIIlllIIlll)) {
            char lllllIIllllIIll = lllllIIlllIlIII[lllllIIlllIIllI];
            "".length();
            lllllIIllllIIII.append((char)(lllllIIllllIIll ^ lllllIIlllIllll[lllllIIlllIlllI % lllllIIlllIllll.length]));
            ++lllllIIlllIlllI;
            ++lllllIIlllIIllI;
            "".length();
            if ("  ".length() <= (171 ^ 175)) continue;
            return null;
        }
        return String.valueOf(lllllIIllllIIII);
    }

    private static boolean llIIII(int n) {
        return n != 0;
    }

    public JocScoreCombo() {
        JocScoreCombo lllllIllllIIlll;
    }

    private static boolean llIlIl(int n, int n2) {
        return n >= n2;
    }

    @Override
    protected void customJocIniciat() {
    }

    private static void lIlIll() {
        lll = new String[llIl[20]];
        JocScoreCombo.lll[JocScoreCombo.llIl[3]] = JocScoreCombo.llll("", "Kvdef");
        JocScoreCombo.lll[JocScoreCombo.llIl[1]] = JocScoreCombo.llll("VR0vaxcAFCAyEQFU", "uuNKp");
        JocScoreCombo.lll[JocScoreCombo.llIl[4]] = JocScoreCombo.lIIII("nAo9KGovZsi25znqhLhk0iohKDMk9VKC", "PtJSW");
        JocScoreCombo.lll[JocScoreCombo.llIl[5]] = JocScoreCombo.llll("", "KKzMr");
        JocScoreCombo.lll[JocScoreCombo.llIl[6]] = JocScoreCombo.lIIII("DjWEqBS+Ydw=", "kPfuI");
        JocScoreCombo.lll[JocScoreCombo.llIl[7]] = JocScoreCombo.lIIlI("X9tTOerjjo4=", "sQsvL");
        JocScoreCombo.lll[JocScoreCombo.llIl[8]] = JocScoreCombo.lIIlI("EsDC2ZGRNCk=", "PnFZM");
        JocScoreCombo.lll[JocScoreCombo.llIl[9]] = JocScoreCombo.lIIII("mO8cO9SdTqc=", "RvBGG");
        JocScoreCombo.lll[JocScoreCombo.llIl[10]] = JocScoreCombo.lIIII("Ke/S77K8vZ4=", "BLYfQ");
        JocScoreCombo.lll[JocScoreCombo.llIl[11]] = JocScoreCombo.lIIII("1OgGv1EqEFg=", "ZmlmV");
        JocScoreCombo.lll[JocScoreCombo.llIl[2]] = JocScoreCombo.lIIII("+van5R7q/rk=", "qOZmt");
        JocScoreCombo.lll[JocScoreCombo.llIl[12]] = JocScoreCombo.lIIlI("uHzlZMEs9to=", "qxUha");
        JocScoreCombo.lll[JocScoreCombo.llIl[13]] = JocScoreCombo.llll("", "BfUSm");
        JocScoreCombo.lll[JocScoreCombo.llIl[14]] = JocScoreCombo.lIIII("CCabEJE8Dj8=", "wTchC");
        JocScoreCombo.lll[JocScoreCombo.llIl[15]] = JocScoreCombo.lIIII("y154F3TaPqI=", "GDqcM");
        JocScoreCombo.lll[JocScoreCombo.llIl[16]] = JocScoreCombo.lIIII("ZPV92809a8Y=", "Opgva");
        JocScoreCombo.lll[JocScoreCombo.llIl[17]] = JocScoreCombo.lIIII("esmxQTS388luYtRHKs7NKg==", "VNiiY");
        JocScoreCombo.lll[JocScoreCombo.llIl[18]] = JocScoreCombo.lIIII("eKgqjskouM4=", "WnlbC");
        JocScoreCombo.lll[JocScoreCombo.llIl[19]] = JocScoreCombo.lIIlI("6vChljx3zSo=", "EFloa");
    }

    private static boolean llIIIl(int n, int n2) {
        return n <= n2;
    }

    @Override
    protected void customJocFinalitzat() {
        JocScoreCombo lllllIllllIIIII;
        lllllIllllIIIII.planificarReseteig(llIl[0] / (lllllIllllIIIII.getPlayers().size() + llIl[1]));
    }

    public ArrayList<Player> getOrderedWinnerList() {
        JocScoreCombo lllllIlIlllIIII;
        ArrayList<Player> lllllIlIllIllll = lllllIlIlllIIII.getPlayers();
        lllllIlIllIllll.sort((lllllIlIIIlIlll, lllllIlIIIlIllI) -> {
            JocScoreCombo lllllIlIIIllIll;
            return lllllIlIIIllIll.getScore((Player)lllllIlIIIlIllI) - lllllIlIIIllIll.getScore((Player)lllllIlIIIlIlll);
        });
        return lllllIlIllIllll;
    }

    public void displayRanking() {
        JocScoreCombo lllllIlIllllIlI;
        ArrayList<Player> lllllIlIllllIll = lllllIlIllllIlI.getOrderedWinnerList();
        lllllIlIllllIlI.sendGlobalMessage(lll[llIl[4]]);
        Iterator<Player> lllllIlIllllIII = lllllIlIllllIll.iterator();
        while (JocScoreCombo.llIIII((int)lllllIlIllllIII.hasNext())) {
            Player lllllIlIlllllIl = lllllIlIllllIII.next();
            int lllllIllIIIIIII = lllllIlIllllIll.indexOf((Object)lllllIlIlllllIl) + llIl[1];
            String lllllIlIlllllll = lll[llIl[5]];
            String lllllIlIllllllI = lll[llIl[6]];
            lllllIlIllllllI = String.valueOf(new StringBuilder().append(lll[llIl[7]]).append((Object)ChatColor.BLUE));
            if (JocScoreCombo.llIIIl(lllllIllIIIIIII, llIl[1])) {
                lllllIlIllllllI = String.valueOf(new StringBuilder().append(lll[llIl[8]]).append((Object)ChatColor.GREEN));
            }
            if (JocScoreCombo.llIIIl(lllllIllIIIIIII, llIl[5])) {
                lllllIlIllllllI = String.valueOf(new StringBuilder().append(lll[llIl[9]]).append((Object)ChatColor.YELLOW));
            }
            if (JocScoreCombo.llIIlI(lllllIllIIIIIII, lllllIlIllllIll.size())) {
                lllllIlIllllllI = String.valueOf(new StringBuilder().append(lll[llIl[10]]).append((Object)ChatColor.RED));
            }
            lllllIlIlllllll = String.valueOf(new StringBuilder().append(lllllIlIlllllll).append(lllllIlIllllllI));
            lllllIlIlllllll = String.valueOf(new StringBuilder().append(lllllIlIlllllll).append(Integer.toString(lllllIllIIIIIII)));
            lllllIlIlllllll = String.valueOf(new StringBuilder().append(lllllIlIlllllll).append(lll[llIl[11]]));
            lllllIlIlllllll = String.valueOf(new StringBuilder().append(lllllIlIlllllll).append(lllllIlIlllllIl.getName()));
            lllllIlIllllIlI.sendGlobalMessage(lllllIlIlllllll);
            "".length();
            if ((99 ^ 118 ^ (100 ^ 117)) != "  ".length()) continue;
            return;
        }
    }

    protected void incrementScore(Player lllllIllIIllIII, Score lllllIllIIlIlll) {
        JocScoreCombo lllllIllIIlllII;
        if (JocScoreCombo.lIlllI((Object)lllllIllIIlIlll, (Object)Score.FAIL)) {
            lllllIllIIlllII.resetCombo(lllllIllIIllIII);
            "".length();
            if (((35 ^ 127) & ~(68 ^ 24)) < 0) {
                return;
            }
        } else {
            lllllIllIIlllII.setScore(lllllIllIIllIII, (int)((long)lllllIllIIlllII.getScore(lllllIllIIllIII) + Math.round((double)lllllIllIIlIlll.getValue() * lllllIllIIlllII.getCombo(lllllIllIIllIII))));
            lllllIllIIlllII.incrementCombo(lllllIllIIllIII);
        }
        "".length();
        ((JocScoreComboPlayerInfo)lllllIllIIlllII.getPlayerInfo(lllllIllIIllIII)).getScoreHistory().add(lllllIllIIlIlll);
        lllllIllIIlllII.updateScoreBoards();
        Com.setSuffix(lllllIllIIllIII, Rank.getRank(((JocScoreComboPlayerInfo)lllllIllIIlllII.getPlayerInfo(lllllIllIIllIII)).getAccuracy()).getFormattedString());
    }

    public ItemStack getFancyBookReport() {
        JocScoreCombo lllllIlIlIIIlII;
        ItemStack lllllIlIlIIIIll = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta lllllIlIlIIIIlI = (BookMeta)lllllIlIlIIIIll.getItemMeta();
        lllllIlIlIIIIlI.setAuthor(lllllIlIlIIIlII.getGameName());
        "".length();
        lllllIlIlIIIIlI.setTitle(String.valueOf(new StringBuilder().append((Object)ChatColor.YELLOW).append(lll[llIl[17]])));
        lllllIlIlIIIlII.getPlayers().stream().sorted((lllllIlIIlIIIII, lllllIlIIlIIIlI) -> {
            JocScoreCombo lllllIlIIlIIlII;
            return Integer.compare(lllllIlIIlIIlII.getScore((Player)lllllIlIIlIIIlI), lllllIlIIlIIlII.getScore((Player)lllllIlIIlIIIII));
        }).forEach(lllllIlIIlIllIl -> {
            JocScoreCombo lllllIlIIlIllll;
            ArrayList<String> lllllIlIIlIllII = ((JocScoreComboPlayerInfo)lllllIlIIlIllll.getPlayerInfo((Player)lllllIlIIlIllIl)).getScoreFancyReport(ChatColor.BLACK);
            lllllIlIIlIllII.add(llIl[3], String.valueOf(new StringBuilder().append((Object)ChatColor.BLACK).append(lll[llIl[18]]).append((Object)ChatColor.BOLD).append(lll[llIl[19]]).append((Object)ChatColor.UNDERLINE).append(lllllIlIIlIllIl.getName()).append((Object)ChatColor.RESET)));
            String[] arrstring = new String[llIl[1]];
            arrstring[JocScoreCombo.llIl[3]] = StringUtils.join(lllllIlIIlIllII, (String)String.valueOf(new StringBuilder().append((Object)ChatColor.WHITE).append(System.lineSeparator()).append((Object)ChatColor.BLACK)));
            lllllIlIlIIIIlI.addPage(arrstring);
        });
        "".length();
        lllllIlIlIIIIll.setItemMeta((ItemMeta)lllllIlIlIIIIlI);
        return lllllIlIlIIIIll;
    }

    private static boolean llIIlI(int n, int n2) {
        return n == n2;
    }

    @Override
    protected void updateScoreBoard(Player lllllIlIlIlIlIl) {
        JocScoreCombo lllllIlIlIlIllI;
        if (JocScoreCombo.llIIII((int)lllllIlIlIlIllI.JocIniciat.booleanValue())) {
            ArrayList<String> lllllIlIlIllIIl = new ArrayList<String>();
            ArrayList<Integer> lllllIlIlIllIII = new ArrayList<Integer>();
            ArrayList<Player> lllllIlIlIlIlll = lllllIlIlIlIllI.getOrderedWinnerList();
            if (JocScoreCombo.llIlII(lllllIlIlIlIlll.size())) {
                int lllllIlIlIllIlI = lllllIlIlIlIllI.getScore(lllllIlIlIlIlll.get(llIl[3]));
                Iterator<Player> lllllIlIlIIllll = lllllIlIlIlIlll.iterator();
                while (JocScoreCombo.llIIII((int)lllllIlIlIIllll.hasNext())) {
                    Player lllllIlIlIllIll = lllllIlIlIIllll.next();
                    String lllllIlIllIIIII = lll[llIl[2]];
                    int lllllIlIlIlllll = lllllIlIlIlIlll.indexOf((Object)lllllIlIlIllIll) + llIl[1];
                    lllllIlIllIIIII = String.valueOf(new StringBuilder().append(lll[llIl[12]]).append((Object)ChatColor.BLUE));
                    if (JocScoreCombo.llIIIl(lllllIlIlIlllll, llIl[5])) {
                        lllllIlIllIIIII = String.valueOf(new StringBuilder().append(lll[llIl[13]]).append((Object)ChatColor.YELLOW));
                    }
                    if (JocScoreCombo.llIIIl(lllllIlIlIlllll, llIl[1])) {
                        lllllIlIllIIIII = String.valueOf(new StringBuilder().append(lll[llIl[14]]).append((Object)ChatColor.GREEN));
                    }
                    if (JocScoreCombo.llIIlI(lllllIlIlIlllll, lllllIlIlIlIlll.size()) && JocScoreCombo.llIlIl(lllllIlIlIlIlll.size(), llIl[4])) {
                        lllllIlIllIIIII = String.valueOf(new StringBuilder().append(lll[llIl[15]]).append((Object)ChatColor.RED));
                    }
                    String lllllIlIlIllllI = lllllIlIlIllIll.getName();
                    String lllllIlIlIlllIl = String.valueOf(new StringBuilder().append((Object)Rank.getRank(((JocScoreComboPlayerInfo)lllllIlIlIlIllI.getPlayerInfo(lllllIlIlIllIll)).getAccuracy())).append(lllllIlIllIIIII).append(StringUtils.abbreviate((String)lllllIlIlIllllI, (int)llIl[7])));
                    double lllllIlIlIlllII = lllllIlIlIlIllI.getCombo(lllllIlIlIllIll);
                    if (JocScoreCombo.llIlII(JocScoreCombo.llIIll(lllllIlIlIlllII, 0.0))) {
                        lllllIlIlIlllIl = String.valueOf(new StringBuilder().append(lllllIlIlIlllIl).append(lll[llIl[16]]).append(Double.toString(lllllIlIlIlllII)));
                    }
                    if (JocScoreCombo.llIllI(lllllIlIlIlllIl.length(), llIl[17])) {
                        lllllIlIlIlllIl = lllllIlIlIlllIl.substring(llIl[3], llIl[17]);
                    }
                    "".length();
                    lllllIlIlIllIIl.add(lllllIlIlIlllIl);
                    "".length();
                    lllllIlIlIllIII.add(lllllIlIlIlIllI.getScore(lllllIlIlIllIll));
                    "".length();
                    if (-"   ".length() < 0) continue;
                    return;
                }
                ScoreBoardUpdater.setScoreBoard(lllllIlIlIlIllI.getPlayers(), lllllIlIlIlIllI.getGameName(), lllllIlIlIllIIl, lllllIlIlIllIII);
            }
        }
    }

    @Override
    protected ArrayList<ItemStack> getStartingItems(Player lllllIllllIIlII) {
        return null;
    }

    protected void setCombo(Player lllllIllIlllIll, double lllllIllIllIllI) {
        JocScoreCombo lllllIllIllllII;
        Joc.PlayerInfo lllllIllIlllIIl = lllllIllIllllII.getPlayerInfo(lllllIllIlllIll);
        lllllIllIlllIIl.setSpree((int)Math.round(lllllIllIllIllI * 10.0));
    }

    public boolean getDisplayRanking() {
        return llIl[1];
    }

    protected void resetScore(Player lllllIllIIlIIIl) {
        JocScoreCombo lllllIllIIlIlII;
        lllllIllIIlIlII.setScore(lllllIllIIlIIIl, llIl[3]);
    }

    protected void incrementCombo(Player lllllIllIlIlIII) {
        JocScoreCombo lllllIllIlIIlll;
        lllllIllIlIIlll.incrementCombo(lllllIllIlIlIII, 0.1);
    }

    private static int llIIll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    @Override
    public boolean getDisplayHealthBar() {
        return llIl[3];
    }

    private static String lIIII(String lllllIlIIIIIIII, String lllllIlIIIIIIIl) {
        try {
            SecretKeySpec lllllIlIIIIIlIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllllIlIIIIIIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllllIlIIIIIlII = Cipher.getInstance("Blowfish");
            lllllIlIIIIIlII.init(llIl[4], lllllIlIIIIIlIl);
            return new String(lllllIlIIIIIlII.doFinal(Base64.getDecoder().decode(lllllIlIIIIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllIlIIIIIIll) {
            lllllIlIIIIIIll.printStackTrace();
            return null;
        }
    }

    private static boolean lIlllI(Object object, Object object2) {
        return object == object2;
    }

    protected void resetCombo(Player lllllIllIlIIIII) {
        JocScoreCombo lllllIllIlIIIIl;
        lllllIllIlIIIIl.setCombo(lllllIllIlIIIII, 1.0);
    }

    @Override
    public JocScoreComboPlayerInfo getPlayerInfo(Player lllllIlIIlllIll) {
        JocScoreCombo lllllIlIIllllII;
        return lllllIlIIllllII.getPlayerInfo(lllllIlIIlllIll, JocScoreComboPlayerInfo.class);
    }

    protected int getScore(Player lllllIlllIllIll) {
        JocScoreCombo lllllIlllIlllII;
        return lllllIlllIlllII.getPlayerInfo(lllllIlllIllIll).getValue();
    }

    public void comprovarGuanyador() {
        JocScoreCombo lllllIllIIIllIl;
        ArrayList<Player> lllllIllIIIllII = lllllIllIIIllIl.getOrderedWinnerList();
        if (JocScoreCombo.lIllll(lllllIllIIIllII.size())) {
            return;
        }
        Player lllllIllIIIlIll = lllllIllIIIllII.get(llIl[3]);
        lllllIllIIIllIl.sendGlobalMessage(String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lll[llIl[3]]).append((Object)ChatColor.BOLD).append(lllllIllIIIlIll.getName()).append(lll[llIl[1]])));
        if (JocScoreCombo.llIIII((int)lllllIllIIIllIl.getDisplayRanking())) {
            lllllIllIIIllIl.displayRanking();
        }
        lllllIllIIIllIl.getPlayers().forEach(lllllIlIIIIllII -> {
            JocScoreCombo lllllIlIIIIllIl;
            Player player = lllllIlIIIIllII;
            "".length();
            player.getClass();
            ((JocScoreComboPlayerInfo)lllllIlIIIIllIl.getPlayerInfo((Player)lllllIlIIIIllII)).getScoreFancyReport(ChatColor.WHITE).forEach(((Player)player)::sendMessage);
        });
        lllllIllIIIllIl.getPlayers().forEach(lllllIlIIIlIIII -> {
            JocScoreCombo lllllIlIIIlIIIl;
            lllllIlIIIlIIII.getInventory().setHeldItemSlot(llIl[5]);
            lllllIlIIIlIIII.getInventory().setItem(llIl[5], lllllIlIIIlIIIl.getFancyBookReport());
        });
        lllllIllIIIllIl.winGame(lllllIllIIIlIll);
    }

    private static void lIllII() {
        llIl = new int[21];
        JocScoreCombo.llIl[0] = 36 ^ 26 ^ (165 ^ 179);
        JocScoreCombo.llIl[1] = " ".length();
        JocScoreCombo.llIl[2] = 155 ^ 195 ^ (84 ^ 6);
        JocScoreCombo.llIl[3] = (78 ^ 123 ^ (154 ^ 188)) & (54 ^ 9 ^ (117 ^ 89) ^ -" ".length());
        JocScoreCombo.llIl[4] = "  ".length();
        JocScoreCombo.llIl[5] = "   ".length();
        JocScoreCombo.llIl[6] = 67 ^ 71;
        JocScoreCombo.llIl[7] = 132 ^ 148 ^ (20 ^ 1);
        JocScoreCombo.llIl[8] = 28 ^ 26;
        JocScoreCombo.llIl[9] = 17 ^ 22;
        JocScoreCombo.llIl[10] = 115 + 48 - 139 + 151 ^ 153 + 40 - 28 + 2;
        JocScoreCombo.llIl[11] = 104 + 118 - 116 + 26 ^ 117 + 99 - 146 + 71;
        JocScoreCombo.llIl[12] = 87 ^ 66 ^ (8 ^ 22);
        JocScoreCombo.llIl[13] = 183 ^ 144 ^ (85 ^ 126);
        JocScoreCombo.llIl[14] = 77 ^ 64;
        JocScoreCombo.llIl[15] = 94 ^ 80;
        JocScoreCombo.llIl[16] = 145 ^ 158;
        JocScoreCombo.llIl[17] = 3 + 65 - 21 + 131 ^ 109 + 117 - 126 + 62;
        JocScoreCombo.llIl[18] = 200 ^ 135 ^ (227 ^ 189);
        JocScoreCombo.llIl[19] = 8 ^ 26;
        JocScoreCombo.llIl[20] = 148 ^ 135;
    }

    protected void setScore(Player lllllIlllIIIlll, int lllllIlllIIIllI) {
        JocScoreCombo lllllIlllIIIlII;
        Joc.PlayerInfo lllllIlllIIIlIl = lllllIlllIIIlII.getPlayerInfo(lllllIlllIIIlll);
        lllllIlllIIIlIl.setValue(lllllIlllIIIllI);
        lllllIlllIIIlll.setLevel((int)Math.round(((JocScoreComboPlayerInfo)lllllIlllIIIlII.getPlayerInfo(lllllIlllIIIlll)).getAccuracy()));
        lllllIlllIIIlII.updateScoreBoards();
    }

    private static boolean lIllll(int n) {
        return n == 0;
    }

    protected void incrementCombo(Player lllllIllIlIllIl, double lllllIllIlIllII) {
        JocScoreCombo lllllIllIllIIIl;
        lllllIllIllIIIl.setCombo(lllllIllIlIllIl, lllllIllIllIIIl.getCombo(lllllIllIlIllIl) + lllllIllIlIllII);
    }

    static {
        JocScoreCombo.lIllII();
        JocScoreCombo.lIlIll();
    }

    @Override
    protected void setCustomGameRules() {
    }

    @Override
    protected void teletransportarTothom() {
    }

    private static boolean lIllIl(int n, int n2) {
        return n < n2;
    }

    private static boolean llIllI(int n, int n2) {
        return n > n2;
    }

    protected double getCombo(Player lllllIlllIlIIll) {
        JocScoreCombo lllllIlllIlIIII;
        Joc.PlayerInfo lllllIlllIlIIlI = lllllIlllIlIIII.getPlayerInfo(lllllIlllIlIIll);
        int lllllIlllIlIIIl = lllllIlllIlIIlI.getSpree();
        if (JocScoreCombo.lIllIl(lllllIlllIlIIIl, llIl[2])) {
            lllllIlllIlIIII.resetCombo(lllllIlllIlIIll);
        }
        return (double)lllllIlllIlIIlI.getSpree() / 10.0;
    }

    private static boolean llIlII(int n) {
        return n > 0;
    }

    private static String lIIlI(String lllllIIllIlllIl, String lllllIIllIllIlI) {
        try {
            SecretKeySpec lllllIIlllIIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllIIllIllIlI.getBytes(StandardCharsets.UTF_8)), llIl[10]), "DES");
            Cipher lllllIIllIlllll = Cipher.getInstance("DES");
            lllllIIllIlllll.init(llIl[4], lllllIIlllIIIII);
            return new String(lllllIIllIlllll.doFinal(Base64.getDecoder().decode(lllllIIllIlllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllllIIllIllllI) {
            lllllIIllIllllI.printStackTrace();
            return null;
        }
    }

    public static final class Rank
    extends Enum<Rank> {
        public static final /* synthetic */ /* enum */ Rank S;
        private static final /* synthetic */ Rank[] $VALUES;
        public static final /* synthetic */ /* enum */ Rank B;
        private static final /* synthetic */ String[] llIlI;
        public static final /* synthetic */ /* enum */ Rank SS;
        private /* synthetic */ int value;
        public static final /* synthetic */ /* enum */ Rank FF;
        public static final /* synthetic */ /* enum */ Rank F;
        private static final /* synthetic */ int[] lllIl;
        public static final /* synthetic */ /* enum */ Rank A;
        public static final /* synthetic */ /* enum */ Rank D;
        public static final /* synthetic */ /* enum */ Rank C;

        private static int lIlIIlI(double d, double d2) {
            return (int)(d DCMPL d2);
        }

        private static void lIlIIIl() {
            lllIl = new int[17];
            Rank.lllIl[0] = (153 ^ 130 ^ (155 ^ 136)) & (77 ^ 103 ^ (137 ^ 171) ^ -" ".length());
            Rank.lllIl[1] = " ".length();
            Rank.lllIl[2] = 241 ^ 146;
            Rank.lllIl[3] = "  ".length();
            Rank.lllIl[4] = 108 ^ 51;
            Rank.lllIl[5] = "   ".length();
            Rank.lllIl[6] = 241 ^ 171;
            Rank.lllIl[7] = 2 ^ 6;
            Rank.lllIl[8] = 52 ^ 100;
            Rank.lllIl[9] = 66 ^ 74 ^ (106 ^ 103);
            Rank.lllIl[10] = 29 ^ 56 ^ (194 ^ 161);
            Rank.lllIl[11] = 151 + 134 - 272 + 177 ^ 159 + 92 - 142 + 75;
            Rank.lllIl[12] = 157 ^ 174 ^ (28 ^ 19);
            Rank.lllIl[13] = 155 + 112 - 110 + 4 ^ 80 + 6 - 75 + 155;
            Rank.lllIl[14] = 42 ^ 2;
            Rank.lllIl[15] = 47 ^ 39;
            Rank.lllIl[16] = 20 ^ 19 ^ (167 ^ 169);
        }

        public static Rank[] values() {
            return (Rank[])$VALUES.clone();
        }

        static {
            Rank.lIlIIIl();
            Rank.lIIllIl();
            SS = new Rank(lllIl[2]);
            S = new Rank(lllIl[4]);
            A = new Rank(lllIl[6]);
            B = new Rank(lllIl[8]);
            C = new Rank(lllIl[10]);
            D = new Rank(lllIl[12]);
            F = new Rank(lllIl[14]);
            FF = new Rank(lllIl[9]);
            Rank[] arrrank = new Rank[lllIl[15]];
            arrrank[Rank.lllIl[0]] = SS;
            arrrank[Rank.lllIl[1]] = S;
            arrrank[Rank.lllIl[3]] = A;
            arrrank[Rank.lllIl[5]] = B;
            arrrank[Rank.lllIl[7]] = C;
            arrrank[Rank.lllIl[9]] = D;
            arrrank[Rank.lllIl[11]] = F;
            arrrank[Rank.lllIl[13]] = FF;
            $VALUES = arrrank;
        }

        public String toString() {
            Rank llllIllIIIlllll;
            return llllIllIIIlllll.getFormattedString();
        }

        public static Rank valueOf(String llllIllIlIIIIll) {
            return Enum.valueOf(Rank.class, llllIllIlIIIIll);
        }

        private static String lIIllII(String llllIlIlllllllI, String llllIlIllllllIl) {
            try {
                SecretKeySpec llllIllIIIIIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llllIlIllllllIl.getBytes(StandardCharsets.UTF_8)), lllIl[15]), "DES");
                Cipher llllIllIIIIIIII = Cipher.getInstance("DES");
                llllIllIIIIIIII.init(lllIl[3], llllIllIIIIIIIl);
                return new String(llllIllIIIIIIII.doFinal(Base64.getDecoder().decode(llllIlIlllllllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception llllIlIllllllll) {
                llllIlIllllllll.printStackTrace();
                return null;
            }
        }

        private static boolean lIlIlII(int n) {
            return n > 0;
        }

        private static String lIIlIll(String llllIllIIIlIIll, String llllIllIIIlIIlI) {
            llllIllIIIlIIll = new String(Base64.getDecoder().decode(llllIllIIIlIIll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llllIllIIIlIIIl = new StringBuilder();
            char[] llllIllIIIlIIII = llllIllIIIlIIlI.toCharArray();
            int llllIllIIIIllll = lllIl[0];
            char[] llllIllIIIIlIIl = llllIllIIIlIIll.toCharArray();
            int llllIllIIIIlIII = llllIllIIIIlIIl.length;
            int llllIllIIIIIlll = lllIl[0];
            while (Rank.lIlIIll(llllIllIIIIIlll, llllIllIIIIlIII)) {
                char llllIllIIIlIlII = llllIllIIIIlIIl[llllIllIIIIIlll];
                "".length();
                llllIllIIIlIIIl.append((char)(llllIllIIIlIlII ^ llllIllIIIlIIII[llllIllIIIIllll % llllIllIIIlIIII.length]));
                ++llllIllIIIIllll;
                ++llllIllIIIIIlll;
                "".length();
                if (null == null) continue;
                return null;
            }
            return String.valueOf(llllIllIIIlIIIl);
        }

        private Rank(int llllIllIIllllIl) {
            Rank llllIllIIllllII;
            llllIllIIllllII.value = llllIllIIllllIl;
        }

        private static boolean lIlIlIl(int n, int n2) {
            return n > n2;
        }

        private static void lIIllIl() {
            llIlI = new String[lllIl[16]];
            Rank.llIlI[Rank.lllIl[0]] = Rank.lIIlIll("", "izOct");
            Rank.llIlI[Rank.lllIl[1]] = Rank.lIIllII("cKsYRJAjXmg=", "SoBBn");
            Rank.llIlI[Rank.lllIl[3]] = Rank.lIIllII("JZDR7zX9PIQ=", "jxMou");
            Rank.llIlI[Rank.lllIl[5]] = Rank.lIIllII("7NwWAdMkOLM=", "zQJZm");
            Rank.llIlI[Rank.lllIl[7]] = Rank.lIIlIll("Eg==", "PFEVG");
            Rank.llIlI[Rank.lllIl[9]] = Rank.lIIllII("DKvADOuYELQ=", "TOPOd");
            Rank.llIlI[Rank.lllIl[11]] = Rank.lIIllII("Z99hFjI8mBo=", "nHgCo");
            Rank.llIlI[Rank.lllIl[13]] = Rank.lIIlIll("FQ==", "SDQCz");
            Rank.llIlI[Rank.lllIl[15]] = Rank.lIIllII("RkQjD2cPs/I=", "ztKxt");
        }

        private static boolean lIlIIll(int n, int n2) {
            return n < n2;
        }

        public String getFormattedString() {
            Rank llllIllIIlIIlII;
            ChatColor llllIllIIlIIIll = ChatColor.GRAY;
            switch (1.$SwitchMap$com$biel$lobby$mapes$JocScoreCombo$Rank[llllIllIIlIIlII.ordinal()]) {
                case 1: {
                    llllIllIIlIIIll = ChatColor.DARK_GREEN;
                    "".length();
                    if (-(37 ^ 32) < 0) break;
                    return null;
                }
                case 2: {
                    llllIllIIlIIIll = ChatColor.DARK_BLUE;
                    "".length();
                    if ((88 ^ 92) >= 0) break;
                    return null;
                }
                case 3: {
                    llllIllIIlIIIll = ChatColor.DARK_PURPLE;
                    "".length();
                    if ((154 ^ 158) >= (0 ^ 4)) break;
                    return null;
                }
                case 4: {
                    llllIllIIlIIIll = ChatColor.DARK_RED;
                    "".length();
                    if (null == null) break;
                    return null;
                }
                case 5: {
                    llllIllIIlIIIll = ChatColor.GOLD;
                    "".length();
                    if (((78 ^ 14 ^ (106 ^ 16)) & (32 + 0 - 27 + 243 ^ 31 + 84 - -63 + 16 ^ -" ".length())) == 0) break;
                    return null;
                }
                case 6: {
                    llllIllIIlIIIll = ChatColor.YELLOW;
                }
                case 7: {
                    llllIllIIlIIIll = ChatColor.DARK_GRAY;
                    "".length();
                    if (null == null) break;
                    return null;
                }
                case 8: {
                    llllIllIIlIIIll = ChatColor.BLACK;
                    "".length();
                    if (null == null) break;
                    return null;
                }
            }
            return String.valueOf(new StringBuilder().append((Object)llllIllIIlIIIll).append(llIlI[lllIl[0]]).append((Object)ChatColor.BOLD).append(llllIllIIlIIlII.name()));
        }

        public int getValue() {
            Rank llllIllIIllIlll;
            return llllIllIIllIlll.value;
        }

        public static Rank getRank(double llllIllIIlIlllI) {
            Rank llllIllIIlIllIl = FF;
            Rank[] llllIllIIlIlIlI = Rank.values();
            int llllIllIIlIlIIl = llllIllIIlIlIlI.length;
            int llllIllIIlIlIII = lllIl[0];
            while (Rank.lIlIIll(llllIllIIlIlIII, llllIllIIlIlIIl)) {
                Rank llllIllIIlIllll = llllIllIIlIlIlI[llllIllIIlIlIII];
                if (Rank.lIlIlII(Rank.lIlIIlI(llllIllIIlIlllI, llllIllIIlIllll.getValue())) && Rank.lIlIlIl(llllIllIIlIllll.getValue(), llllIllIIlIllIl.getValue())) {
                    llllIllIIlIllIl = llllIllIIlIllll;
                }
                ++llllIllIIlIlIII;
                "".length();
                if (null == null) continue;
                return null;
            }
            return llllIllIIlIllIl;
        }
    }

    public class JocScoreComboPlayerInfo
    extends Joc.PlayerInfo {
        private static final /* synthetic */ String[] lllIlIIl;
        private static final /* synthetic */ int[] llllIlll;
        /* synthetic */ ArrayList<Score> scoreHistory;
        /* synthetic */ boolean inGame;

        public void setInGame(boolean llllllllllIIII) {
            llllllllllIIIl.inGame = llllllllllIIII;
        }

        private static boolean lllIlIlIlI(Object object, Object object2) {
            return object == object2;
        }

        static {
            JocScoreComboPlayerInfo.lllIlIlIII();
            JocScoreComboPlayerInfo.llIlllllIl();
        }

        public String getScoreInlineReport() {
            ArrayList<String> lIIIIIIIIlIIIII = new ArrayList<String>();
            Score[] lIIIIIIIIIlllIl = Score.values();
            int lIIIIIIIIIlllII = lIIIIIIIIIlllIl.length;
            int lIIIIIIIIIllIll = llllIlll[1];
            while (JocScoreComboPlayerInfo.lllIlIlIIl(lIIIIIIIIIllIll, lIIIIIIIIIlllII)) {
                JocScoreComboPlayerInfo lIIIIIIIIlIIIIl;
                Score lIIIIIIIIlIIIlI = lIIIIIIIIIlllIl[lIIIIIIIIIllIll];
                "".length();
                lIIIIIIIIlIIIII.add(lIIIIIIIIlIIIIl.getScoreTypeLabeled(lIIIIIIIIlIIIlI, ChatColor.WHITE));
                ++lIIIIIIIIIllIll;
                "".length();
                if (null == null) continue;
                return null;
            }
            return String.join((CharSequence)lllIlIIl[llllIlll[1]], lIIIIIIIIlIIIII);
        }

        public ArrayList<Score> getScoreHistory() {
            JocScoreComboPlayerInfo lIIIIIIIIlIlIIl;
            return lIIIIIIIIlIlIIl.scoreHistory;
        }

        private static boolean lllIlIlIIl(int n, int n2) {
            return n < n2;
        }

        private static String llIlllIlll(String llllllllIIllIl, String llllllllIlIIIl) {
            llllllllIIllIl = new String(Base64.getDecoder().decode(llllllllIIllIl.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder llllllllIlIIII = new StringBuilder();
            char[] llllllllIIllll = llllllllIlIIIl.toCharArray();
            int llllllllIIlllI = llllIlll[1];
            char[] llllllllIIlIII = llllllllIIllIl.toCharArray();
            int llllllllIIIlll = llllllllIIlIII.length;
            int llllllllIIIllI = llllIlll[1];
            while (JocScoreComboPlayerInfo.lllIlIlIIl(llllllllIIIllI, llllllllIIIlll)) {
                char llllllllIlIIll = llllllllIIlIII[llllllllIIIllI];
                "".length();
                llllllllIlIIII.append((char)(llllllllIlIIll ^ llllllllIIllll[llllllllIIlllI % llllllllIIllll.length]));
                ++llllllllIIlllI;
                ++llllllllIIIllI;
                "".length();
                if (-" ".length() < (134 ^ 130)) continue;
                return null;
            }
            return String.valueOf(llllllllIlIIII);
        }

        private static String llIllllIII(String lllllllIlllIll, String lllllllIlllIlI) {
            try {
                SecretKeySpec llllllllIIIIII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllllllIlllIlI.getBytes(StandardCharsets.UTF_8)), llllIlll[9]), "DES");
                Cipher lllllllIllllll = Cipher.getInstance("DES");
                lllllllIllllll.init(llllIlll[2], llllllllIIIIII);
                return new String(lllllllIllllll.doFinal(Base64.getDecoder().decode(lllllllIlllIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lllllllIlllllI) {
                lllllllIlllllI.printStackTrace();
                return null;
            }
        }

        public long getScoreTypeAmount(Score lllllllllllllI) {
            JocScoreComboPlayerInfo llllllllllllll;
            return llllllllllllll.getScoreHistory().stream().filter(lllllllllIlIlI -> {
                boolean bl;
                if (JocScoreComboPlayerInfo.lllIlIlIlI(lllllllllIlIlI, (Object)lllllllllllllI)) {
                    bl = llllIlll[0];
                    "".length();
                    if (((136 + 66 - 84 + 72 ^ 73 + 5 - 56 + 153) & (168 ^ 166 ^ (31 ^ 0) ^ -" ".length())) >= " ".length()) {
                        return (boolean)((229 ^ 182 ^ (233 ^ 147)) & (77 + 14 - 21 + 100 ^ 21 + 38 - 43 + 115 ^ -" ".length()));
                    }
                } else {
                    bl = llllIlll[1];
                }
                return bl;
            }).count();
        }

        private static String llIllllIIl(String lllllllllIIIII, String llllllllIlllll) {
            try {
                SecretKeySpec lllllllllIIlIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llllllllIlllll.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lllllllllIIlII = Cipher.getInstance("Blowfish");
                lllllllllIIlII.init(llllIlll[2], lllllllllIIlIl);
                return new String(lllllllllIIlII.doFinal(Base64.getDecoder().decode(lllllllllIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lllllllllIIIll) {
                lllllllllIIIll.printStackTrace();
                return null;
            }
        }

        public double getAccuracy() {
            JocScoreComboPlayerInfo lllllllllllIIl;
            return (double)(lllllllllllIIl.scoreHistory.stream().mapToInt(Score::getAccuracyValue).sum() * llllIlll[8]) / ((double)lllllllllllIIl.scoreHistory.size() * 300.0);
        }

        private static void llIlllllIl() {
            lllIlIIl = new String[llllIlll[9]];
            JocScoreComboPlayerInfo.lllIlIIl[JocScoreComboPlayerInfo.llllIlll[1]] = JocScoreComboPlayerInfo.llIlllIlll("aWo=", "EJKaf");
            JocScoreComboPlayerInfo.lllIlIIl[JocScoreComboPlayerInfo.llllIlll[0]] = JocScoreComboPlayerInfo.llIllllIII("WOX68dM+ecg=", "hrWka");
            JocScoreComboPlayerInfo.lllIlIIl[JocScoreComboPlayerInfo.llllIlll[2]] = JocScoreComboPlayerInfo.llIllllIII("kl4YI7kNb6w=", "cWbdt");
            JocScoreComboPlayerInfo.lllIlIIl[JocScoreComboPlayerInfo.llllIlll[3]] = JocScoreComboPlayerInfo.llIllllIII("I1Pl5xdY/OvCfwBz/OvbOQ==", "pDJXJ");
            JocScoreComboPlayerInfo.lllIlIIl[JocScoreComboPlayerInfo.llllIlll[4]] = JocScoreComboPlayerInfo.llIllllIIl("TpldG6tRAbhvA1P6+RyGVA==", "pWQrS");
            JocScoreComboPlayerInfo.lllIlIIl[JocScoreComboPlayerInfo.llllIlll[5]] = JocScoreComboPlayerInfo.llIllllIIl("3hGzSr0+P+U=", "SCvdO");
            JocScoreComboPlayerInfo.lllIlIIl[JocScoreComboPlayerInfo.llllIlll[6]] = JocScoreComboPlayerInfo.llIllllIII("fRopxbUaEvs=", "sBerl");
            JocScoreComboPlayerInfo.lllIlIIl[JocScoreComboPlayerInfo.llllIlll[7]] = JocScoreComboPlayerInfo.llIlllIlll("eEc=", "XgPYr");
        }

        public boolean isInGame() {
            JocScoreComboPlayerInfo llllllllllIlll;
            return llllllllllIlll.inGame;
        }

        public ArrayList<String> getScoreFancyReport(ChatColor lIIIIIIIIIIlIII) {
            JocScoreComboPlayerInfo lIIIIIIIIIIlIIl;
            ArrayList<String> lIIIIIIIIIIIlll = new ArrayList<String>();
            String lIIIIIIIIIIIllI = lllIlIIl[llllIlll[2]];
            "".length();
            lIIIIIIIIIIIlll.add(String.valueOf(new StringBuilder().append(lllIlIIl[llllIlll[3]]).append((Object)Rank.getRank(lIIIIIIIIIIlIIl.getAccuracy()))));
            "".length();
            lIIIIIIIIIIIlll.add(String.valueOf(new StringBuilder().append(lllIlIIl[llllIlll[4]]).append((double)Math.round(lIIIIIIIIIIlIIl.getAccuracy() * 100.0) / 100.0).append(lllIlIIl[llllIlll[5]])));
            "".length();
            lIIIIIIIIIIIlll.add(String.valueOf(new StringBuilder().append(lllIlIIl[llllIlll[6]]).append(lIIIIIIIIIIlIIl.JocScoreCombo.this.getScore(lIIIIIIIIIIlIIl.getPlayer()))));
            "".length();
            lIIIIIIIIIIIlll.add(String.valueOf(new StringBuilder().append(lIIIIIIIIIIlIIl.getScoreTypeLabeled(Score.N300, lIIIIIIIIIIlIII)).append(lIIIIIIIIIIIllI).append(lIIIIIIIIIIlIIl.getScoreTypeLabeled(Score.C300, lIIIIIIIIIIlIII))));
            "".length();
            lIIIIIIIIIIIlll.add(String.valueOf(new StringBuilder().append(lIIIIIIIIIIlIIl.getScoreTypeLabeled(Score.N200, lIIIIIIIIIIlIII)).append(lIIIIIIIIIIIllI).append(lIIIIIIIIIIlIIl.getScoreTypeLabeled(Score.N100, lIIIIIIIIIIlIII))));
            "".length();
            lIIIIIIIIIIIlll.add(String.valueOf(new StringBuilder().append(lllIlIIl[llllIlll[7]]).append(lIIIIIIIIIIlIIl.getScoreTypeLabeled(Score.N50, lIIIIIIIIIIlIII)).append(lIIIIIIIIIIIllI).append(lIIIIIIIIIIlIIl.getScoreTypeLabeled(Score.FAIL, lIIIIIIIIIIlIII))));
            return lIIIIIIIIIIIlll;
        }

        private static void lllIlIlIII() {
            llllIlll = new int[10];
            JocScoreComboPlayerInfo.llllIlll[0] = " ".length();
            JocScoreComboPlayerInfo.llllIlll[1] = (91 ^ 123) & ~(69 ^ 101);
            JocScoreComboPlayerInfo.llllIlll[2] = "  ".length();
            JocScoreComboPlayerInfo.llllIlll[3] = "   ".length();
            JocScoreComboPlayerInfo.llllIlll[4] = 69 + 56 - 108 + 150 ^ 107 + 91 - 75 + 40;
            JocScoreComboPlayerInfo.llllIlll[5] = 173 ^ 168;
            JocScoreComboPlayerInfo.llllIlll[6] = 130 ^ 132;
            JocScoreComboPlayerInfo.llllIlll[7] = 125 + 99 - 212 + 174 ^ 3 + 13 - -26 + 147;
            JocScoreComboPlayerInfo.llllIlll[8] = 118 + 191 - 184 + 72 ^ 26 + 69 - 29 + 95;
            JocScoreComboPlayerInfo.llllIlll[9] = 44 ^ 36;
        }

        public String getScoreTypeLabeled(Score lIIIIIIIIIlIlII, ChatColor lIIIIIIIIIlIIll) {
            JocScoreComboPlayerInfo lIIIIIIIIIlIIIl;
            long lIIIIIIIIIlIIlI = lIIIIIIIIIlIIIl.getScoreTypeAmount(lIIIIIIIIIlIlII);
            return String.valueOf(new StringBuilder().append(lIIIIIIIIIlIlII.getFormattedString()).append((Object)lIIIIIIIIIlIIll).append(lllIlIIl[llllIlll[0]]).append(lIIIIIIIIIlIIlI));
        }

        public JocScoreComboPlayerInfo() {
            JocScoreComboPlayerInfo lIIIIIIIIlIllIl;
            super(lIIIIIIIIlIllIl.JocScoreCombo.this);
            lIIIIIIIIlIllIl.inGame = llllIlll[0];
            lIIIIIIIIlIllIl.scoreHistory = new ArrayList();
        }
    }

    public static final class Score
    extends Enum<Score> {
        public static final /* synthetic */ /* enum */ Score N200;
        public static final /* synthetic */ /* enum */ Score FAIL;
        public static final /* synthetic */ /* enum */ Score N300;
        private static final /* synthetic */ String[] lIllIIlll;
        public static final /* synthetic */ /* enum */ Score N50;
        public static final /* synthetic */ /* enum */ Score C300;
        private static final /* synthetic */ int[] lIllIllII;
        private /* synthetic */ int value;
        public static final /* synthetic */ /* enum */ Score N100;
        private static final /* synthetic */ Score[] $VALUES;

        private static String lIlIIIlIlII(String lIIlIIIIlIIlII, String lIIlIIIIlIIIIl) {
            try {
                SecretKeySpec lIIlIIIIlIIlll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIlIIIIlIIIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIlIIIIlIIllI = Cipher.getInstance("Blowfish");
                lIIlIIIIlIIllI.init(lIllIllII[3], lIIlIIIIlIIlll);
                return new String(lIIlIIIIlIIllI.doFinal(Base64.getDecoder().decode(lIIlIIIIlIIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIlIIIIlIIlIl) {
                lIIlIIIIlIIlIl.printStackTrace();
                return null;
            }
        }

        public int getValue() {
            Score lIIlIIIlIllIll;
            return lIIlIIIlIllIll.value;
        }

        private static String lIlIIIlIIll(String lIIlIIIIllIlII, String lIIlIIIIlllIII) {
            lIIlIIIIllIlII = new String(Base64.getDecoder().decode(lIIlIIIIllIlII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIlIIIIllIlll = new StringBuilder();
            char[] lIIlIIIIllIllI = lIIlIIIIlllIII.toCharArray();
            int lIIlIIIIllIlIl = lIllIllII[1];
            char[] lIIlIIIIlIllll = lIIlIIIIllIlII.toCharArray();
            int lIIlIIIIlIlllI = lIIlIIIIlIllll.length;
            int lIIlIIIIlIllIl = lIllIllII[1];
            while (Score.lIlIlIIIlII(lIIlIIIIlIllIl, lIIlIIIIlIlllI)) {
                char lIIlIIIIlllIlI = lIIlIIIIlIllll[lIIlIIIIlIllIl];
                "".length();
                lIIlIIIIllIlll.append((char)(lIIlIIIIlllIlI ^ lIIlIIIIllIllI[lIIlIIIIllIlIl % lIIlIIIIllIllI.length]));
                ++lIIlIIIIllIlIl;
                ++lIIlIIIIlIllIl;
                "".length();
                if (((5 ^ 60 ^ (139 ^ 153)) & (113 ^ 76 ^ (13 ^ 27) ^ -" ".length())) == ((84 ^ 104 ^ (55 ^ 91)) & (121 ^ 106 ^ (213 ^ 150) ^ -" ".length()))) continue;
                return null;
            }
            return String.valueOf(lIIlIIIIllIlll);
        }

        public static Score[] values() {
            return (Score[])$VALUES.clone();
        }

        static {
            Score.lIlIlIIIIlI();
            Score.lIlIIIlIllI();
            FAIL = new Score(lIllIllII[1]);
            N50 = new Score(lIllIllII[12]);
            N100 = new Score(lIllIllII[14]);
            N200 = new Score(lIllIllII[16]);
            N300 = new Score(lIllIllII[0]);
            C300 = new Score(lIllIllII[19]);
            Score[] arrscore = new Score[lIllIllII[7]];
            arrscore[Score.lIllIllII[1]] = FAIL;
            arrscore[Score.lIllIllII[2]] = N50;
            arrscore[Score.lIllIllII[3]] = N100;
            arrscore[Score.lIllIllII[4]] = N200;
            arrscore[Score.lIllIllII[5]] = N300;
            arrscore[Score.lIllIllII[6]] = C300;
            $VALUES = arrscore;
        }

        private Score(int lIIlIIIllIIIIl) {
            Score lIIlIIIllIIIlI;
            lIIlIIIllIIIlI.value = lIIlIIIllIIIIl;
        }

        public int getAccuracyValue() {
            Score lIIlIIIlIlIlll;
            if (Score.lIlIlIIIIll(lIIlIIIlIlIlll.value, lIllIllII[0])) {
                return lIllIllII[0];
            }
            return lIIlIIIlIlIlll.value;
        }

        private static void lIlIlIIIIlI() {
            lIllIllII = new int[21];
            Score.lIllIllII[0] = -(-16427 & 26238) & (-22657 & 32767);
            Score.lIllIllII[1] = (116 ^ 53 ^ (210 ^ 167)) & (46 ^ 42 ^ (183 ^ 135) ^ -" ".length());
            Score.lIllIllII[2] = " ".length();
            Score.lIllIllII[3] = "  ".length();
            Score.lIllIllII[4] = "   ".length();
            Score.lIllIllII[5] = 18 ^ 22;
            Score.lIllIllII[6] = 9 ^ 12;
            Score.lIllIllII[7] = 110 ^ 84 ^ (178 ^ 142);
            Score.lIllIllII[8] = 21 ^ 18;
            Score.lIllIllII[9] = 146 ^ 154;
            Score.lIllIllII[10] = 75 ^ 66;
            Score.lIllIllII[11] = 17 ^ 27;
            Score.lIllIllII[12] = 118 ^ 113 ^ (72 ^ 125);
            Score.lIllIllII[13] = 106 + 99 - 86 + 72 ^ 172 + 10 - 145 + 143;
            Score.lIllIllII[14] = 134 ^ 151 ^ (243 ^ 134);
            Score.lIllIllII[15] = 65 + 102 - 99 + 87 ^ 110 + 38 - 31 + 34;
            Score.lIllIllII[16] = 0 + 150 - 112 + 162;
            Score.lIllIllII[17] = 35 + 116 - 74 + 63 ^ 63 + 93 - 33 + 6;
            Score.lIllIllII[18] = 162 ^ 172;
            Score.lIllIllII[19] = -(-321 & 32103) & (-666 & 32767);
            Score.lIllIllII[20] = 119 ^ 22 ^ (123 ^ 21);
        }

        private static void lIlIIIlIllI() {
            lIllIIlll = new String[lIllIllII[20]];
            Score.lIllIIlll[Score.lIllIllII[1]] = Score.lIlIIIlIIII("YORZr+wCyWk=", "WkUFx");
            Score.lIllIIlll[Score.lIllIllII[2]] = Score.lIlIIIlIIII("5gXnl78Kl3Q=", "WxsoF");
            Score.lIllIIlll[Score.lIllIllII[3]] = Score.lIlIIIlIIll("dA==", "DrwWk");
            Score.lIllIIlll[Score.lIllIllII[4]] = Score.lIlIIIlIIII("4HDeWqqn7qo=", "Cxokj");
            Score.lIllIIlll[Score.lIllIllII[5]] = Score.lIlIIIlIIII("ZUWlA6cc9HE=", "JWoPJ");
            Score.lIllIIlll[Score.lIllIllII[6]] = Score.lIlIIIlIIll("emh+", "HXNDv");
            Score.lIllIIlll[Score.lIllIllII[7]] = Score.lIlIIIlIlII("IQZDWxul1V4=", "BXjfH");
            Score.lIllIIlll[Score.lIllIllII[8]] = Score.lIlIIIlIIll("f3o=", "JJRvi");
            Score.lIllIIlll[Score.lIllIllII[9]] = Score.lIlIIIlIIll("", "iUtEP");
            Score.lIllIIlll[Score.lIllIllII[10]] = Score.lIlIIIlIIll("DQ0YHQ==", "KLQQx");
            Score.lIllIIlll[Score.lIllIllII[11]] = Score.lIlIIIlIIll("IHRh", "nAQmK");
            Score.lIllIIlll[Score.lIllIllII[13]] = Score.lIlIIIlIlII("BAImklM8P/8=", "HWuYG");
            Score.lIllIIlll[Score.lIllIllII[15]] = Score.lIlIIIlIIII("zFBjl/k6qMI=", "YJJQG");
            Score.lIllIIlll[Score.lIllIllII[17]] = Score.lIlIIIlIlII("bWCO8CEOidQ=", "dZsqN");
            Score.lIllIIlll[Score.lIllIllII[18]] = Score.lIlIIIlIIll("AkRfdA==", "AwoDS");
        }

        public String getFormattedString() {
            Score lIIlIIIlIlIlIl;
            switch (1.$SwitchMap$com$biel$lobby$mapes$JocScoreCombo$Score[lIIlIIIlIlIlIl.ordinal()]) {
                case 1: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_BLUE).append(lIllIIlll[lIllIllII[1]]).append((Object)ChatColor.GOLD).append(lIllIIlll[lIllIllII[2]]).append((Object)ChatColor.DARK_RED).append(lIllIIlll[lIllIllII[3]]));
                }
                case 2: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.RED).append(lIllIIlll[lIllIllII[4]]));
                }
                case 3: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.BLUE).append(lIllIIlll[lIllIllII[5]]));
                }
                case 4: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.GREEN).append(lIllIIlll[lIllIllII[6]]));
                }
                case 5: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.GOLD).append(lIllIIlll[lIllIllII[7]]));
                }
                case 6: {
                    return String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_GRAY).append(lIllIIlll[lIllIllII[8]]));
                }
            }
            return lIllIIlll[lIllIllII[9]];
        }

        private static String lIlIIIlIIII(String lIIlIIIlIIIlll, String lIIlIIIlIIIllI) {
            try {
                SecretKeySpec lIIlIIIlIIllII = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlIIIlIIIllI.getBytes(StandardCharsets.UTF_8)), lIllIllII[9]), "DES");
                Cipher lIIlIIIlIIlIll = Cipher.getInstance("DES");
                lIIlIIIlIIlIll.init(lIllIllII[3], lIIlIIIlIIllII);
                return new String(lIIlIIIlIIlIll.doFinal(Base64.getDecoder().decode(lIIlIIIlIIIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIlIIIlIIlIlI) {
                lIIlIIIlIIlIlI.printStackTrace();
                return null;
            }
        }

        public String toString() {
            Score lIIlIIIlIlIIIl;
            return lIIlIIIlIlIIIl.getFormattedString();
        }

        private static boolean lIlIlIIIlII(int n, int n2) {
            return n < n2;
        }

        public static Score valueOf(String lIIlIIIllIlIII) {
            return Enum.valueOf(Score.class, lIIlIIIllIlIII);
        }

        private static boolean lIlIlIIIIll(int n, int n2) {
            return n > n2;
        }
    }

}

