/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 */
package com.biel.lobby.utilities;

import com.biel.lobby.utilities.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BUtils {
    private static final /* synthetic */ int[] lIlIllIlI;

    public static void fillBlocks(List<Block> lIllIIlIIIlIlI, Material lIllIIlIIIlIIl) {
        BUtils.fillBlocks(lIllIIlIIIlIlI, lIllIIlIIIlIIl, lIlIllIlI[0]);
    }

    private static void lIIllIlIIlI() {
        lIlIllIlI = new int[2];
        BUtils.lIlIllIlI[0] = "  ".length() & ("  ".length() ^ -" ".length());
        BUtils.lIlIllIlI[1] = -(-426 & 20925) & (-9221 & 30719);
    }

    public static ArrayList<Block> locListToBlock(List<Location> lIllIIlIIIIIll) {
        ArrayList<Block> lIllIIlIIIIIlI = new ArrayList<Block>();
        Iterator<Location> lIllIIIlllllll = lIllIIlIIIIIll.iterator();
        while (BUtils.lIIllIlIIll((int)lIllIIIlllllll.hasNext())) {
            Location lIllIIlIIIIlII = lIllIIIlllllll.next();
            "".length();
            lIllIIlIIIIIlI.add(lIllIIlIIIIlII.getBlock());
            "".length();
            if (" ".length() <= "   ".length()) continue;
            return null;
        }
        return lIllIIlIIIIIlI;
    }

    static {
        BUtils.lIIllIlIIlI();
    }

    private static boolean lIIllIlIlIl(Object object) {
        return object != null;
    }

    public BUtils() {
        BUtils lIllIIllIIIIIl;
    }

    public static void fillBlocks(List<Block> lIllIIlIIlIlll, Material lIllIIlIIlIllI, byte lIllIIlIIlIIIl) {
        ArrayList<Material> lIllIIlIIlIlII = new ArrayList<Material>();
        "".length();
        lIllIIlIIlIlII.add(lIllIIlIIlIllI);
        if (BUtils.lIIllIlIIll(lIllIIlIIlIIIl)) {
            ArrayList<Byte> lIllIIlIIllIII = new ArrayList<Byte>();
            "".length();
            lIllIIlIIllIII.add(lIllIIlIIlIIIl);
            BUtils.fillBlocks(lIllIIlIIlIlll, lIllIIlIIlIlII, lIllIIlIIllIII, null);
            "".length();
            if (-"  ".length() > 0) {
                return;
            }
        } else {
            BUtils.fillBlocks(lIllIIlIIlIlll, lIllIIlIIlIlII, null, null);
        }
    }

    private static boolean lIIllIlIIll(int n) {
        return n != 0;
    }

    private static boolean lIIllIlIlII(Object object) {
        return object == null;
    }

    public static void fillBlocks(List<Block> lIllIIlIlIllll, ArrayList<Material> lIllIIlIlIlllI, ArrayList<Byte> lIllIIlIlIllIl, ArrayList<Integer> lIllIIlIllIIII) {
        Iterator<Block> lIllIIlIlIlIll = lIllIIlIlIllll.iterator();
        while (BUtils.lIIllIlIIll((int)lIllIIlIlIlIll.hasNext())) {
            Block lIllIIlIllIlII = lIllIIlIlIlIll.next();
            Material lIllIIlIllIlIl = null;
            if (BUtils.lIIllIlIlII(lIllIIlIllIIII)) {
                lIllIIlIllIlIl = lIllIIlIlIlllI.get(lIlIllIlI[0]);
                "".length();
                if ("  ".length() > "   ".length()) {
                    return;
                }
            } else {
                while (BUtils.lIIllIlIlII(lIllIIlIllIlIl)) {
                    Iterator<Material> lIllIIlIlIlIII = lIllIIlIlIlllI.iterator();
                    while (BUtils.lIIllIlIIll((int)lIllIIlIlIlIII.hasNext())) {
                        Material lIllIIlIllIllI = lIllIIlIlIlIII.next();
                        if (BUtils.lIIllIlIIll((int)Utils.Possibilitat(lIllIIlIllIIII.get(lIllIIlIlIlllI.indexOf((Object)lIllIIlIllIllI)), lIlIllIlI[1]))) {
                            lIllIIlIllIlIl = lIllIIlIllIllI;
                        }
                        "".length();
                        if (" ".length() != 0) continue;
                        return;
                    }
                    "".length();
                    if (" ".length() < (73 ^ 77)) continue;
                    return;
                }
            }
            lIllIIlIllIlII.setType(lIllIIlIllIlIl);
            if (BUtils.lIIllIlIlIl(lIllIIlIlIllIl)) {
                lIllIIlIllIlII.setData(lIllIIlIlIllIl.get(lIllIIlIlIlllI.indexOf((Object)lIllIIlIllIlIl)).byteValue());
            }
            "".length();
            if (-(96 ^ 78 ^ (51 ^ 24)) < 0) continue;
            return;
        }
    }

    public static void fillBlocks(List<Block> lIllIIlIlIIIll, ArrayList<Material> lIllIIlIlIIIlI, ArrayList<Integer> lIllIIlIlIIIIl) {
        BUtils.fillBlocks(lIllIIlIlIIIll, lIllIIlIlIIIlI, null, lIllIIlIlIIIIl);
    }
}

