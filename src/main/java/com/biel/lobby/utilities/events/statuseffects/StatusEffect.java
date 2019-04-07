/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.biel.BielAPI.events.PlayerWorldEventBus
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 */
package com.biel.lobby.utilities.events.statuseffects;

import com.biel.BielAPI.events.PlayerWorldEventBus;
import com.biel.lobby.Com;
import com.biel.lobby.GestorMapes;
import com.biel.lobby.Mapa;
import com.biel.lobby.lobby;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.utilities.Utils;
import com.biel.lobby.utilities.events.statuseffects.AuraStatusEffect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class StatusEffect
extends PlayerWorldEventBus {
    private /* synthetic */ StatusEffectType type;
    public /* synthetic */ int id;
    private static final /* synthetic */ int[] lIlIIIIll;
    private /* synthetic */ int ticksLived;
    private /* synthetic */ int modalRemainingTicks;
    private /* synthetic */ double value;
    private /* synthetic */ AuraStatusEffect aura;
    private /* synthetic */ boolean modal;
    private static final /* synthetic */ String[] lIIllIlIl;
    private /* synthetic */ int remainingTicks;
    private /* synthetic */ double maxValue;
    private /* synthetic */ String ownerName;

    protected Joc getGame() {
        StatusEffect llIIIlIIlllllI;
        Mapa llIIIlIIllllll = Com.getPlugin().gest.getMapWherePlayerIs(llIIIlIIlllllI.getPlayer());
        if (StatusEffect.lIIlIIllIlI((Object)llIIIlIIllllll)) {
            return null;
        }
        if (StatusEffect.lIIlIIlIIll(llIIIlIIllllll instanceof Joc)) {
            return (Joc)llIIIlIIllllll;
        }
        return null;
    }

    public void setRemainingTicks(int llIIIllIIIIIII) {
        llIIIllIIIIIll.remainingTicks = llIIIllIIIIIII;
    }

    private static int lIIlIIllIIl(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean lIIlIIlIlIl(int n) {
        return n < 0;
    }

    public void onMaxUp() {
    }

    protected void sendPlayerMessage(Player llIIIlIIlIllll, String llIIIlIIllIIIl) {
        StatusEffect llIIIlIIllIIll;
        llIIIlIIllIIll.getGame().sendPlayerMessage(llIIIlIIlIllll, llIIIlIIllIIIl);
    }

    private static boolean lIIlIIlIIll(int n) {
        return n != 0;
    }

    static {
        StatusEffect.lIIlIIIllll();
        StatusEffect.lIIlIIIlIII();
    }

    public void onMaxLose() {
    }

    public Joc.PlayerInfo getPlayerInfo(Player llIIIlIIIIllII) {
        StatusEffect llIIIlIIIIllll;
        return llIIIlIIIIllll.getGame().getPlayerInfo(llIIIlIIIIllII);
    }

    public String getValueString() {
        StatusEffect llIIIllIIIlIIl;
        if (StatusEffect.lIIlIIlIIll(StatusEffect.lIIlIIlllIl(llIIIllIIIlIIl.value, -1.0)) && StatusEffect.lIIlIIlIIll(StatusEffect.lIIlIIlllIl(llIIIllIIIlIIl.getMaxValue(), -1.0))) {
            return String.valueOf(new StringBuilder().append(lIIllIlIl[lIlIIIIll[9]]).append(Math.round(llIIIllIIIlIIl.getValue())).append(lIIllIlIl[lIlIIIIll[10]]).append(Math.round(llIIIllIIIlIIl.getMaxValue())).append(lIIllIlIl[lIlIIIIll[11]]));
        }
        if (StatusEffect.lIIlIIlIIll(StatusEffect.lIIlIIlllIl(llIIIllIIIlIIl.value, -1.0)) && StatusEffect.lIIlIIllIII(StatusEffect.lIIlIIlllIl(llIIIllIIIlIIl.getMaxValue(), -1.0))) {
            return String.valueOf(new StringBuilder().append(lIIllIlIl[lIlIIIIll[12]]).append(Math.round(llIIIllIIIlIIl.getValue())).append(lIIllIlIl[lIlIIIIll[13]]));
        }
        return lIIllIlIl[lIlIIIIll[14]];
    }

    public Joc.PlayerInfo getPlayerInfo() {
        StatusEffect llIIIlIIIIlIlI;
        return llIIIlIIIIlIlI.getGame().getPlayerInfo(llIIIlIIIIlIlI.getPlayer());
    }

    private static void lIIlIIIlIII() {
        lIIllIlIl = new String[lIlIIIIll[17]];
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[1]] = StatusEffect.lIIIllIIllI("", "BNPjm");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[3]] = StatusEffect.lIIIllIlIIl("q0ljIgHaV2w=", "IZkJI");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[4]] = StatusEffect.lIIIllIlIIl("MwZVppwiHHU=", "toDjf");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[5]] = StatusEffect.lIIIllIIllI("Sg==", "jRijg");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[6]] = StatusEffect.lIIIllllIlI("bd6khNh11wA=", "UdGOm");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[7]] = StatusEffect.lIIIllIIllI("Tg==", "fYPgB");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[8]] = StatusEffect.lIIIllIlIIl("u0PbX1Z8yCw=", "jtqKq");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[9]] = StatusEffect.lIIIllIlIIl("4GLYh/FBeRg=", "ktLkF");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[10]] = StatusEffect.lIIIllIIllI("Vw==", "xEUnv");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[11]] = StatusEffect.lIIIllIlIIl("emqp/hGo5HY=", "HwRmJ");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[12]] = StatusEffect.lIIIllIlIIl("eySNYWya/VM=", "innMI");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[13]] = StatusEffect.lIIIllllIlI("Jb0a6GtZY3s=", "dBHTC");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[14]] = StatusEffect.lIIIllIIllI("", "CVtDw");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[15]] = StatusEffect.lIIIllIIllI("Fg==", "mATkW");
        StatusEffect.lIIllIlIl[StatusEffect.lIlIIIIll[16]] = StatusEffect.lIIIllllIlI("wzVI6PJT+Mc=", "imuet");
    }

    protected void sendPlayerMessage(String llIIIlIIlIIlII) {
        StatusEffect llIIIlIIlIIlIl;
        llIIIlIIlIIlIl.sendPlayerMessage(llIIIlIIlIIlIl.getPlayer(), llIIIlIIlIIlII);
    }

    public boolean hasExpired() {
        StatusEffect llIIIlIlIlIllI;
        int n;
        if (StatusEffect.lIIlIIllIll(llIIIlIlIlIllI.remainingTicks, lIlIIIIll[0])) {
            return lIlIIIIll[1];
        }
        if (StatusEffect.lIIlIIllllI(llIIIlIlIlIllI.remainingTicks) && StatusEffect.lIIlIIlIIll((int)llIIIlIlIlIllI.isValid())) {
            n = lIlIIIIll[3];
            "".length();
            if (" ".length() == 0) {
                return (boolean)((5 ^ 51) & ~(146 ^ 164));
            }
        } else {
            n = lIlIIIIll[1];
        }
        return (boolean)n;
    }

    public abstract String getDescription();

    public String getRemainingSecondsString() {
        StatusEffect llIIIllIIIllIl;
        if (StatusEffect.lIIlIIllIll(llIIIllIIIllIl.remainingTicks, lIlIIIIll[0])) {
            return lIIllIlIl[lIlIIIIll[6]];
        }
        return String.valueOf(new StringBuilder().append(lIIllIlIl[lIlIIIIll[7]]).append((double)Math.round(llIIIllIIIllIl.getRemainingSeconds() * 10.0) / 10.0).append(lIIllIlIl[lIlIIIIll[8]]));
    }

    private static int lIIlIIlIIII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    protected void sendEffectMessage(String llIIIlIIIlllIl) {
        StatusEffect llIIIlIIIllllI;
        llIIIlIIIllllI.sendEffectMessage(llIIIlIIIllllI.getPlayer(), llIIIlIIIlllIl);
    }

    public double getRemainingSeconds() {
        StatusEffect llIIIlIllllllI;
        return (double)llIIIlIllllllI.getRemainingTicks() / StatusEffect.getTickSpacing();
    }

    private static String lIIIllIlIIl(String llIIIIllIllIIl, String llIIIIllIlIlIl) {
        try {
            SecretKeySpec llIIIIllIlllII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIIllIlIlIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher llIIIIllIllIll = Cipher.getInstance("Blowfish");
            llIIIIllIllIll.init(lIlIIIIll[4], llIIIIllIlllII);
            return new String(llIIIIllIllIll.doFinal(Base64.getDecoder().decode(llIIIIllIllIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIIllIllIlI) {
            llIIIIllIllIlI.printStackTrace();
            return null;
        }
    }

    public String getDisplayText() {
        StatusEffect llIIIllIIllIII;
        String llIIIllIIlIlll = llIIIllIIllIII.getRemainingSecondsString();
        String llIIIllIIlIllI = llIIIllIIllIII.getValueString();
        String llIIIllIIlIlIl = lIIllIlIl[lIlIIIIll[1]];
        if (StatusEffect.lIIlIIlIIll((int)llIIIllIIllIII.modal)) {
            llIIIllIIlIlIl = String.valueOf(new StringBuilder().append(llIIIllIIlIlIl).append((Object)ChatColor.BOLD));
        }
        String llIIIllIIlIlII = String.valueOf(new StringBuilder().append((Object)ChatColor.RESET).append(lIIllIlIl[lIlIIIIll[3]]).append((Object)llIIIllIIllIII.getChatColor()).append(llIIIllIIlIlIl).append(llIIIllIIllIII.getName()).append(lIIllIlIl[lIlIIIIll[4]]).append(llIIIllIIlIllI).append(lIIllIlIl[lIlIIIIll[5]]).append(llIIIllIIlIlll));
        return llIIIllIIlIlII.trim();
    }

    protected void sendGlobalMessage(String llIIIlIIlllIIl) {
        StatusEffect llIIIlIIlllIlI;
        llIIIlIIlllIlI.getGame().sendGlobalMessage(llIIIlIIlllIIl);
    }

    private static int lIIlIIlIIIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    private static int lIIlIIlllIl(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public int getModalRemainingTicks() {
        StatusEffect llIIIllIlIlIll;
        return llIIIllIlIlIll.modalRemainingTicks;
    }

    public abstract String getName();

    private void tryTriggerOnMaxes(double llIIlIIllIllll, boolean llIIlIIllIlllI) {
        int llIIlIIllIllIl;
        int n;
        StatusEffect llIIlIIlllIIII;
        if (StatusEffect.lIIlIIlIIll(StatusEffect.lIIlIIlIlll(llIIlIIlllIIII.getMaxValue(), -1.0)) && StatusEffect.lIIlIIlIIll(StatusEffect.lIIlIIlIlll(llIIlIIlllIIII.getValue(), -1.0))) {
            n = lIlIIIIll[3];
            "".length();
            if (null != null) {
                return;
            }
        } else {
            n = llIIlIIllIllIl = lIlIIIIll[1];
        }
        if (StatusEffect.lIIlIIllIII((int)llIIlIIllIlllI) && StatusEffect.lIIlIIlIIll((int)llIIlIIlllIIII.isMaxed()) && StatusEffect.lIIlIIlIIll(llIIlIIllIllIl)) {
            llIIlIIlllIIII.onMaxUp();
        }
        if (StatusEffect.lIIlIIlIIll((int)llIIlIIllIlllI) && StatusEffect.lIIlIIllIII((int)llIIlIIlllIIII.isMaxed()) && StatusEffect.lIIlIIlIIll(llIIlIIllIllIl)) {
            llIIlIIlllIIII.onMaxLose();
        }
    }

    public void setOwnerPlayer(Player llIIlIIlIlIIIl) {
        llIIlIIlIlIIlI.ownerName = llIIlIIlIlIIIl.getName();
    }

    public void tick() {
        StatusEffect llIIIlIllIIIlI;
        if (StatusEffect.lIIlIIllIII(llIIIlIllIIIlI.remainingTicks)) {
            return;
        }
        if (StatusEffect.lIIlIIlIlII(llIIIlIllIIIlI.remainingTicks)) {
            llIIIlIllIIIlI.remainingTicks = (int)((double)llIIIlIllIIIlI.remainingTicks - StatusEffect.getTickSpacing());
        }
        if (StatusEffect.lIIlIIllIII(llIIIlIllIIIlI.modalRemainingTicks)) {
            llIIIlIllIIIlI.setModal(lIlIIIIll[1]);
        }
        if (StatusEffect.lIIlIIlIlII(llIIIlIllIIIlI.modalRemainingTicks)) {
            llIIIlIllIIIlI.modalRemainingTicks = (int)((double)llIIIlIllIIIlI.modalRemainingTicks - StatusEffect.getTickSpacing());
        }
        if (StatusEffect.lIIlIIlIlIl(llIIIlIllIIIlI.modalRemainingTicks)) {
            llIIIlIllIIIlI.modalRemainingTicks = lIlIIIIll[1];
        }
        llIIIlIllIIIlI.ticksLived += lIlIIIIll[3];
    }

    public int getTicksLived() {
        StatusEffect llIIIlIllllIlI;
        return llIIIlIllllIlI.ticksLived;
    }

    private static void lIIlIIIllll() {
        lIlIIIIll = new int[18];
        StatusEffect.lIlIIIIll[0] = -" ".length();
        StatusEffect.lIlIIIIll[1] = (194 ^ 133 ^ (146 ^ 159)) & (191 + 91 - 262 + 178 ^ 47 + 60 - -12 + 21 ^ -" ".length());
        StatusEffect.lIlIIIIll[2] = 96 ^ 120 ^ (14 ^ 114);
        StatusEffect.lIlIIIIll[3] = " ".length();
        StatusEffect.lIlIIIIll[4] = "  ".length();
        StatusEffect.lIlIIIIll[5] = "   ".length();
        StatusEffect.lIlIIIIll[6] = 148 + 21 - 24 + 31 ^ 152 + 85 - 217 + 160;
        StatusEffect.lIlIIIIll[7] = 75 ^ 78;
        StatusEffect.lIlIIIIll[8] = 150 ^ 144;
        StatusEffect.lIlIIIIll[9] = 138 ^ 165 ^ (5 ^ 45);
        StatusEffect.lIlIIIIll[10] = 154 ^ 146;
        StatusEffect.lIlIIIIll[11] = 206 ^ 199;
        StatusEffect.lIlIIIIll[12] = 223 ^ 161 ^ (249 ^ 141);
        StatusEffect.lIlIIIIll[13] = 25 ^ 18;
        StatusEffect.lIlIIIIll[14] = 23 + 71 - 84 + 123 ^ 131 + 63 - 142 + 85;
        StatusEffect.lIlIIIIll[15] = 118 + 80 - 122 + 97 ^ 2 + 84 - 49 + 123;
        StatusEffect.lIlIIIIll[16] = 8 ^ 6;
        StatusEffect.lIlIIIIll[17] = 198 ^ 173 ^ (226 ^ 134);
    }

    private static String lIIIllIIllI(String llIIIIlIlIIllI, String llIIIIlIlIIlIl) {
        llIIIIlIlIIllI = new String(Base64.getDecoder().decode(llIIIIlIlIIllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIIIlIlIIlII = new StringBuilder();
        char[] llIIIIlIlIIIll = llIIIIlIlIIlIl.toCharArray();
        int llIIIIlIlIIIlI = lIlIIIIll[1];
        char[] llIIIIlIIlllII = llIIIIlIlIIllI.toCharArray();
        int llIIIIlIIllIll = llIIIIlIIlllII.length;
        int llIIIIlIIllIlI = lIlIIIIll[1];
        while (StatusEffect.lIIlIlIIIIl(llIIIIlIIllIlI, llIIIIlIIllIll)) {
            char llIIIIlIlIIlll = llIIIIlIIlllII[llIIIIlIIllIlI];
            "".length();
            llIIIIlIlIIlII.append((char)(llIIIIlIlIIlll ^ llIIIIlIlIIIll[llIIIIlIlIIIlI % llIIIIlIlIIIll.length]));
            ++llIIIIlIlIIIlI;
            ++llIIIIlIIllIlI;
            "".length();
            if ("   ".length() <= "   ".length()) continue;
            return null;
        }
        return String.valueOf(llIIIIlIlIIlII);
    }

    private static int lIIlIIlIlll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public StatusEffect(Player llIIlIlIIIIIll) {
        StatusEffect llIIlIlIIIIllI;
        super(llIIlIlIIIIIll);
        llIIlIlIIIIllI.remainingTicks = lIlIIIIll[0];
        llIIlIlIIIIllI.value = -1.0;
        llIIlIlIIIIllI.maxValue = -1.0;
        llIIlIlIIIIllI.modal = lIlIIIIll[1];
        llIIlIlIIIIllI.modalRemainingTicks = lIlIIIIll[0];
        llIIlIlIIIIllI.ticksLived = lIlIIIIll[1];
        llIIlIlIIIIllI.aura = null;
        llIIlIlIIIIllI.id = Utils.NombreEntre(lIlIIIIll[1], lIlIIIIll[2]);
        llIIlIlIIIIllI.type = StatusEffectType.UNDEFINED;
    }

    public void clearExternals() {
    }

    public void setModal(boolean llIIIllIlIllIl) {
        llIIIllIllIIII.modal = llIIIllIlIllIl;
        if (StatusEffect.lIIlIIllIII((int)llIIIllIlIllIl)) {
            StatusEffect llIIIllIllIIII;
            llIIIllIllIIII.setModalRemainingTicks(lIlIIIIll[0]);
        }
    }

    public boolean isModal() {
        StatusEffect llIIIllIllIIll;
        return llIIIllIllIIll.modal;
    }

    public void setValue(double llIIlIIlllIllI) {
        StatusEffect llIIlIIllllIll;
        double llIIlIIllllIIl = llIIlIIllllIll.value;
        boolean llIIlIIllllIII = llIIlIIllllIll.isMaxed();
        if (StatusEffect.lIIlIIlIIll(StatusEffect.lIIlIIlIIII(llIIlIIllllIll.maxValue, -1.0))) {
            if (StatusEffect.lIIlIIlIlII(StatusEffect.lIIlIIlIIII(llIIlIIlllIllI, llIIlIIllllIll.maxValue))) {
                llIIlIIlllIllI = llIIlIIllllIll.maxValue;
            }
            if (StatusEffect.lIIlIIlIlIl(StatusEffect.lIIlIIlIIIl(llIIlIIlllIllI, 0.0))) {
                llIIlIIlllIllI = 0.0;
            }
        }
        llIIlIIllllIll.value = llIIlIIlllIllI;
        llIIlIIllllIll.tryTriggerOnMaxes(llIIlIIllllIIl, llIIlIIllllIII);
    }

    public Player getOwnerPlayer() {
        StatusEffect llIIlIIlIlIllI;
        if (StatusEffect.lIIlIIllIlI(llIIlIIlIlIllI.ownerName)) {
            return null;
        }
        return Bukkit.getPlayer((String)llIIlIIlIlIllI.ownerName);
    }

    private static boolean lIIlIIlIlII(int n) {
        return n > 0;
    }

    private static boolean lIIlIIllIll(int n, int n2) {
        return n == n2;
    }

    private static boolean lIIlIIllIlI(Object object) {
        return object == null;
    }

    public StatusEffectType getType() {
        StatusEffect llIIlIIlIIllII;
        return llIIlIIlIIllII.type;
    }

    public void setModalRemainingTicks(int llIIIllIlIIlII) {
        llIIIllIlIIlIl.modalRemainingTicks = llIIIllIlIIlII;
        if (StatusEffect.lIIlIIlIlII(llIIIllIlIIlII)) {
            StatusEffect llIIIllIlIIlIl;
            llIIIllIlIIlIl.setModal(lIlIIIIll[3]);
        }
    }

    private static boolean lIIlIlIIIIl(int n, int n2) {
        return n < n2;
    }

    private static String lIIIllllIlI(String llIIIIlIllllIl, String llIIIIlIlllllI) {
        try {
            SecretKeySpec llIIIIllIIIIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIIIlIlllllI.getBytes(StandardCharsets.UTF_8)), lIlIIIIll[10]), "DES");
            Cipher llIIIIllIIIIIl = Cipher.getInstance("DES");
            llIIIIllIIIIIl.init(lIlIIIIll[4], llIIIIllIIIIlI);
            return new String(llIIIIllIIIIIl.doFinal(Base64.getDecoder().decode(llIIIIlIllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIIllIIIIII) {
            llIIIIllIIIIII.printStackTrace();
            return null;
        }
    }

    public boolean isValid() {
        StatusEffect llIIIlIllIIlIl;
        int n;
        if (StatusEffect.lIIlIIllIlI((Object)((Object)llIIIlIllIIlIl.getGame()))) {
            return lIlIIIIll[1];
        }
        if (StatusEffect.lIIlIIlIIll((int)super.isValid()) && StatusEffect.lIIlIIlIIll((int)llIIIlIllIIlIl.getGame().getPlayers().contains((Object)llIIIlIllIIlIl.getPlayer())) && (!StatusEffect.lIIlIIllllI(llIIIlIllIIlIl.remainingTicks) || StatusEffect.lIIlIIllIll(llIIIlIllIIlIl.remainingTicks, lIlIIIIll[0]))) {
            n = lIlIIIIll[3];
            "".length();
            if ("  ".length() <= -" ".length()) {
                return (boolean)((235 ^ 187) & ~(200 ^ 152) & ~((115 ^ 39) & ~(76 ^ 24)));
            }
        } else {
            n = lIlIIIIll[1];
        }
        return (boolean)n;
    }

    public double getMaxValue() {
        StatusEffect llIIlIIllIIlll;
        return llIIlIIllIIlll.maxValue;
    }

    public void expire() {
        StatusEffect llIIIlIlIIIlII;
        llIIIlIlIIIlII.setRemainingTicks(lIlIIIIll[1]);
    }

    public double getValue() {
        StatusEffect llIIlIlIIIIIIl;
        return llIIlIlIIIIIIl.value;
    }

    public int getRemainingTicks() {
        StatusEffect llIIIllIIIIlll;
        return llIIIllIIIIlll.remainingTicks;
    }

    private static boolean lIIlIIllIII(int n) {
        return n == 0;
    }

    private static boolean lIIlIIllllI(int n) {
        return n <= 0;
    }

    public AuraStatusEffect getAura() {
        StatusEffect llIIlIIlIIIlII;
        return llIIlIIlIIIlII.aura;
    }

    public boolean isNthTick(int llIIIlIlllIllI) {
        StatusEffect llIIIlIlllIlll;
        boolean bl;
        if (StatusEffect.lIIlIIllIII(llIIIlIlllIlll.ticksLived % llIIIlIlllIllI)) {
            bl = lIlIIIIll[3];
            "".length();
            if (((87 ^ 48 ^ (62 ^ 23)) & (61 ^ 67 ^ (174 ^ 158) ^ -" ".length())) != 0) {
                return (boolean)("  ".length() & ("  ".length() ^ -" ".length()));
            }
        } else {
            bl = lIlIIIIll[1];
        }
        return bl;
    }

    private static double getTickSpacing() {
        return 20.0;
    }

    public void setMaxValue(double llIIlIIllIIIIl) {
        StatusEffect llIIlIIllIIIlI;
        double llIIlIIllIIIII = llIIlIIllIIIlI.value;
        boolean llIIlIIlIlllll = llIIlIIllIIIlI.isMaxed();
        llIIlIIllIIIlI.maxValue = llIIlIIllIIIIl;
        llIIlIIllIIIlI.tryTriggerOnMaxes(llIIlIIllIIIII, llIIlIIlIlllll);
    }

    public boolean isMaxed() {
        StatusEffect llIIlIIlIllIII;
        boolean bl;
        if (StatusEffect.lIIlIIllIII(StatusEffect.lIIlIIllIIl(llIIlIIlIllIII.getValue(), llIIlIIlIllIII.getMaxValue()))) {
            bl = lIlIIIIll[3];
            "".length();
            if (-" ".length() >= "   ".length()) {
                return (boolean)((40 ^ 0) & ~(34 ^ 10));
            }
        } else {
            bl = lIlIIIIll[1];
        }
        return bl;
    }

    public void setAura(AuraStatusEffect llIIlIIIllllIl) {
        llIIlIIIlllllI.aura = llIIlIIIllllIl;
    }

    public ChatColor getChatColor() {
        StatusEffect llIIIllIlIIIIl;
        ChatColor llIIIllIlIIIII = ChatColor.GRAY;
        switch (1.$SwitchMap$com$biel$lobby$utilities$events$statuseffects$StatusEffect$StatusEffectType[llIIIllIlIIIIl.getType().ordinal()]) {
            case 1: {
                llIIIllIlIIIII = ChatColor.GREEN;
                if (!StatusEffect.lIIlIIlIIll((int)llIIIllIlIIIIl.modal)) break;
                llIIIllIlIIIII = ChatColor.DARK_GREEN;
                "".length();
                if (null == null) break;
                return null;
            }
            case 2: {
                llIIIllIlIIIII = ChatColor.RED;
                if (!StatusEffect.lIIlIIlIIll((int)llIIIllIlIIIIl.modal)) break;
                llIIIllIlIIIII = ChatColor.DARK_RED;
                "".length();
                if ((91 ^ 68 ^ (118 ^ 109)) >= (39 + 60 - -3 + 28 ^ 81 + 17 - 45 + 81)) break;
                return null;
            }
            case 3: {
                llIIIllIlIIIII = ChatColor.DARK_AQUA;
                if (!StatusEffect.lIIlIIlIIll((int)llIIIllIlIIIIl.modal)) break;
                llIIIllIlIIIII = ChatColor.AQUA;
                "".length();
                if ("  ".length() < (43 ^ 47)) break;
                return null;
            }
            case 4: {
                if (!StatusEffect.lIIlIIlIIll((int)llIIIllIlIIIIl.modal)) break;
                llIIIllIlIIIII = ChatColor.BLACK;
                "".length();
                if (" ".length() > ((96 ^ 23 ^ (31 ^ 124)) & (147 + 104 - 118 + 36 ^ 48 + 52 - 55 + 144 ^ -" ".length()))) break;
                return null;
            }
        }
        return llIIIllIlIIIII;
    }

    protected Boolean verifyEvent(Event llIIIlIIIIIIlI) {
        StatusEffect llIIIlIIIIIlIl;
        return super.verifyEvent(llIIIlIIIIIIlI);
    }

    public void setType(StatusEffectType llIIlIIlIIlIII) {
        llIIlIIlIIlIIl.type = llIIlIIlIIlIII;
    }

    protected void sendEffectMessage(Player llIIIlIIIlIllI, String llIIIlIIIlIIlI) {
        StatusEffect llIIIlIIIlIlll;
        llIIIlIIIlIlll.sendPlayerMessage(llIIIlIIIlIllI, String.valueOf(new StringBuilder().append((Object)ChatColor.DARK_AQUA).append(lIIllIlIl[lIlIIIIll[15]]).append(llIIIlIIIlIlll.getName()).append(lIIllIlIl[lIlIIIIll[16]]).append((Object)ChatColor.GRAY).append(llIIIlIIIlIIlI)));
    }

    public static final class StatusEffectType
    extends Enum<StatusEffectType> {
        public static final /* synthetic */ /* enum */ StatusEffectType SKILL_TRAY;
        public static final /* synthetic */ /* enum */ StatusEffectType UNDEFINED;
        private static final /* synthetic */ StatusEffectType[] $VALUES;
        private static final /* synthetic */ int[] llIlllII;
        private static final /* synthetic */ String[] lIllIlll;
        public static final /* synthetic */ /* enum */ StatusEffectType DEBUFF;
        public static final /* synthetic */ /* enum */ StatusEffectType BUFF;

        private static void llIlIIIlII() {
            llIlllII = new int[6];
            StatusEffectType.llIlllII[0] = (98 ^ 59 ^ (101 ^ 57)) & (80 + 16 - 41 + 103 ^ 93 + 7 - -47 + 8 ^ -" ".length());
            StatusEffectType.llIlllII[1] = " ".length();
            StatusEffectType.llIlllII[2] = "  ".length();
            StatusEffectType.llIlllII[3] = "   ".length();
            StatusEffectType.llIlllII[4] = 57 ^ 61;
            StatusEffectType.llIlllII[5] = 154 ^ 146;
        }

        private static String lIllIIlIll(String lIIIlIlIIlIIllI, String lIIIlIlIIlIIlIl) {
            try {
                SecretKeySpec lIIIlIlIIlIlIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIIlIlIIlIIlIl.getBytes(StandardCharsets.UTF_8)), llIlllII[5]), "DES");
                Cipher lIIIlIlIIlIlIlI = Cipher.getInstance("DES");
                lIIIlIlIIlIlIlI.init(llIlllII[2], lIIIlIlIIlIlIll);
                return new String(lIIIlIlIIlIlIlI.doFinal(Base64.getDecoder().decode(lIIIlIlIIlIIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIlIlIIlIlIIl) {
                lIIIlIlIIlIlIIl.printStackTrace();
                return null;
            }
        }

        private static void lIllIIllII() {
            lIllIlll = new String[llIlllII[4]];
            StatusEffectType.lIllIlll[StatusEffectType.llIlllII[0]] = StatusEffectType.lIllIIlIlI("fVNlbttD+ccfo4ANTR0/0g==", "iOCXp");
            StatusEffectType.lIllIlll[StatusEffectType.llIlllII[1]] = StatusEffectType.lIllIIlIlI("VRQoOoBJeYkHPq9DDx9CDw==", "hUUYE");
            StatusEffectType.lIllIlll[StatusEffectType.llIlllII[2]] = StatusEffectType.lIllIIlIll("VnQigCxZf8o=", "SUBXx");
            StatusEffectType.lIllIlll[StatusEffectType.llIlllII[3]] = StatusEffectType.lIllIIlIll("uTdJ5slL8wM=", "FeWRl");
        }

        private static String lIllIIlIlI(String lIIIlIlIIllIlIl, String lIIIlIlIIllIlII) {
            try {
                SecretKeySpec lIIIlIlIIlllIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIIlIlIIllIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIIlIlIIllIlll = Cipher.getInstance("Blowfish");
                lIIIlIlIIllIlll.init(llIlllII[2], lIIIlIlIIlllIII);
                return new String(lIIIlIlIIllIlll.doFinal(Base64.getDecoder().decode(lIIIlIlIIllIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIIlIlIIllIllI) {
                lIIIlIlIIllIllI.printStackTrace();
                return null;
            }
        }

        public static StatusEffectType valueOf(String lIIIlIlIlIIIlIl) {
            return Enum.valueOf(StatusEffectType.class, lIIIlIlIlIIIlIl);
        }

        private StatusEffectType() {
            StatusEffectType lIIIlIlIIllllll;
        }

        static {
            StatusEffectType.llIlIIIlII();
            StatusEffectType.lIllIIllII();
            UNDEFINED = new StatusEffectType();
            SKILL_TRAY = new StatusEffectType();
            BUFF = new StatusEffectType();
            DEBUFF = new StatusEffectType();
            StatusEffectType[] arrstatusEffectType = new StatusEffectType[llIlllII[4]];
            arrstatusEffectType[StatusEffectType.llIlllII[0]] = UNDEFINED;
            arrstatusEffectType[StatusEffectType.llIlllII[1]] = SKILL_TRAY;
            arrstatusEffectType[StatusEffectType.llIlllII[2]] = BUFF;
            arrstatusEffectType[StatusEffectType.llIlllII[3]] = DEBUFF;
            $VALUES = arrstatusEffectType;
        }

        public static StatusEffectType[] values() {
            return (StatusEffectType[])$VALUES.clone();
        }
    }

}

