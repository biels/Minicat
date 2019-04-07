/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.bukkit.entity.Player
 */
package com.biel.lobby.utilities.data;

import com.biel.lobby.Com;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.JocEquips;
import com.biel.lobby.mapes.MapaResetejable;
import com.biel.lobby.utilities.data.DatalessMatchData;
import com.biel.lobby.utilities.data.PlayerData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

public class MatchData {
    /* synthetic */ int id;
    /* synthetic */ int currentFrame;
    private static final /* synthetic */ int[] lIIIIlIIl;

    public void registerEnd(Player llllIlIlllllII) {
        MatchData llllIlIllllIll;
        llllIlIllllIll.registerEnd(new PlayerData(llllIlIlllllII).getId());
    }

    private static void llllIlIIIl() {
        lIIIIlIIl = new int[5];
        MatchData.lIIIIlIIl[0] = (30 ^ 4) & ~(70 ^ 92);
        MatchData.lIIIIlIIl[1] = 163 ^ 143;
        MatchData.lIIIIlIIl[2] = 51 ^ 8;
        MatchData.lIIIIlIIl[3] = -" ".length();
        MatchData.lIIIIlIIl[4] = " ".length();
    }

    public void registerEnd(int llllIllIIIIllI) {
        MatchData llllIllIIIlIIl;
        Com.getDataAPI().registerMatchEnd(llllIllIIIlIIl.id, llllIllIIIIllI);
    }

    public static String getTeamsString(Joc llllIllIIlIIII) {
        Object llllIllIIlIllI;
        ArrayList llllIllIIlIIlI = new ArrayList();
        if (MatchData.llllIlIIlI(llllIllIIlIIII instanceof JocEquips)) {
            Iterator<JocEquips.Equip> llllIllIIIlllI = ((JocEquips)llllIllIIlIIII).Equips.iterator();
            while (MatchData.llllIlIIlI((int)llllIllIIIlllI.hasNext())) {
                llllIllIIlIllI = llllIllIIIlllI.next();
                ArrayList llllIllIIlIlll = new ArrayList();
                ((JocEquips.Equip)llllIllIIlIllI).getPlayers().forEach(llllIlIlIIlIlI -> {
                    "".length();
                    llllIllIIlIlll.add(new PlayerData((Player)llllIlIlIIlIlI).getId());
                });
                "".length();
                llllIllIIlIIlI.add(llllIllIIlIlll);
                "".length();
                if (((187 ^ 183) & ~(21 ^ 25)) <= (74 ^ 78)) continue;
                return null;
            }
            "".length();
            if (-" ".length() == ((120 ^ 75) & ~(116 ^ 71))) {
                return null;
            }
        } else {
            ArrayList llllIllIIlIlIl = new ArrayList();
            llllIllIIlIIII.getPlayers().forEach(llllIlIlIlIIlI -> {
                "".length();
                llllIllIIlIlIl.add(new PlayerData((Player)llllIlIlIlIIlI).getId());
            });
            "".length();
            llllIllIIlIIlI.add(llllIllIIlIlIl);
        }
        "".length();
        llllIllIIlIIII.getGameName();
        ArrayList<String> llllIllIIlIIIl = new ArrayList<String>();
        llllIllIIlIllI = llllIllIIlIIlI.iterator();
        while (MatchData.llllIlIIlI((int)llllIllIIlIllI.hasNext())) {
            ArrayList llllIllIIlIlII = (ArrayList)llllIllIIlIllI.next();
            "".length();
            llllIllIIlIIIl.add(StringUtils.join((Iterable)llllIllIIlIlII, (char)lIIIIlIIl[1]));
            "".length();
            if (((125 + 6 - 30 + 73 ^ 54 + 135 - 113 + 107) & (98 + 169 - 219 + 130 ^ 6 + 30 - -75 + 60 ^ -" ".length())) >= 0) continue;
            return null;
        }
        return StringUtils.join(llllIllIIlIIIl, (char)lIIIIlIIl[2]);
    }

    public MatchData(int llllIllIlIllll) {
        MatchData llllIllIllIIII;
        llllIllIllIIII.currentFrame = lIIIIlIIl[0];
        llllIllIllIIII.id = llllIllIlIllll;
    }

    private static boolean llllIlIIlI(int n) {
        return n != 0;
    }

    public void registerEnd(JocEquips.Equip llllIllIIIIIII) {
        MatchData llllIllIIIIIIl;
        llllIllIIIIIIl.registerEnd(llllIllIIIIIII.getId());
    }

    public static MatchData registerStart(int llllIllIlIlIll, int llllIllIlIlIlI, String llllIllIlIlIIl) {
        if (MatchData.llllIlIIlI((int)Com.getDataAPI().isInDatalessMode())) {
            return new DatalessMatchData();
        }
        return Com.getDataAPI().registerMatchStart(llllIllIlIlIll, llllIllIlIlIlI, llllIllIlIlIIl);
    }

    private static boolean llllIlIIll(Object object, Object object2) {
        return object == object2;
    }

    public static MatchData registerStart(Joc llllIllIIlllll) {
        String string;
        int llllIllIlIIIIl = Com.getDataAPI().getGameId(llllIllIIlllll.getGameName());
        if (MatchData.llllIlIIll((Object)llllIllIIlllll.getMapMode(), (Object)MapaResetejable.MapMode.MULTIPLE)) {
            string = llllIllIIlllll.getMultiMapName();
            "".length();
            if (null != null) {
                return null;
            }
        } else {
            string = llllIllIIlllll.getGameName();
        }
        int llllIllIlIIIII = Com.getDataAPI().getMapId(string, llllIllIlIIIIl);
        return MatchData.registerStart(llllIllIlIIIIl, llllIllIlIIIII, MatchData.getTeamsString(llllIllIIlllll));
    }

    public void registerTimestamp(Player llllIlIllIIIII, boolean llllIlIllIlIll, int llllIlIllIlIlI, int llllIlIlIlllIl, double llllIlIllIlIII, boolean llllIlIllIIlll, int llllIlIllIIllI, int llllIlIlIllIIl, int llllIlIlIllIII, int llllIlIllIIIll, int llllIlIlIlIllI) {
        int n;
        MatchData llllIlIllIIIIl;
        if (MatchData.llllIlIIlI((int)llllIlIllIlIll)) {
            n = lIIIIlIIl[3];
            "".length();
            if ((172 ^ 168) < 0) {
                return;
            }
        } else {
            n = llllIlIllIIIIl.currentFrame;
        }
        Com.getDataAPI().registerTimestamp(llllIlIllIIIIl.id, new PlayerData(llllIlIllIIIII).getId(), n, llllIlIllIlIlI, llllIlIlIlllIl, llllIlIllIlIII, llllIlIllIIlll, llllIlIllIIllI, llllIlIlIllIIl, llllIlIlIllIII, llllIlIllIIIll, llllIlIlIlIllI);
        llllIlIllIIIIl.currentFrame += lIIIIlIIl[4];
    }

    static {
        MatchData.llllIlIIIl();
    }
}

