/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.serialization.ConfigurationSerializable
 */
package com.biel.lobby.utilities;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Cuboid
implements Iterable<Block>,
Cloneable,
ConfigurationSerializable {
    protected final /* synthetic */ int x2;
    protected final /* synthetic */ int y2;
    private static final /* synthetic */ String[] lIIIIIlI;
    private static final /* synthetic */ int[] lIIlIlII;
    protected final /* synthetic */ int z1;
    protected final /* synthetic */ int y1;
    protected final /* synthetic */ int x1;
    protected final /* synthetic */ String worldName;
    protected final /* synthetic */ int z2;

    private static boolean lIIIllIIIl(int n, int n2) {
        return n >= n2;
    }

    public Location getUpperSW() {
        Cuboid lIlIlllIlIlIIIl;
        return new Location(lIlIlllIlIlIIIl.getWorld(), (double)lIlIlllIlIlIIIl.x2, (double)lIlIlllIlIlIIIl.y2, (double)lIlIlllIlIlIIIl.z2);
    }

    public Block[] corners() {
        Cuboid lIlIlllIIIlIlll;
        Block[] lIlIlllIIIlIllI = new Block[lIIlIlII[8]];
        World lIlIlllIIIlIlIl = lIlIlllIIIlIlll.getWorld();
        lIlIlllIIIlIllI[Cuboid.lIIlIlII[0]] = lIlIlllIIIlIlIl.getBlockAt(lIlIlllIIIlIlll.x1, lIlIlllIIIlIlll.y1, lIlIlllIIIlIlll.z1);
        lIlIlllIIIlIllI[Cuboid.lIIlIlII[1]] = lIlIlllIIIlIlIl.getBlockAt(lIlIlllIIIlIlll.x1, lIlIlllIIIlIlll.y1, lIlIlllIIIlIlll.z2);
        lIlIlllIIIlIllI[Cuboid.lIIlIlII[2]] = lIlIlllIIIlIlIl.getBlockAt(lIlIlllIIIlIlll.x1, lIlIlllIIIlIlll.y2, lIlIlllIIIlIlll.z1);
        lIlIlllIIIlIllI[Cuboid.lIIlIlII[3]] = lIlIlllIIIlIlIl.getBlockAt(lIlIlllIIIlIlll.x1, lIlIlllIIIlIlll.y2, lIlIlllIIIlIlll.z2);
        lIlIlllIIIlIllI[Cuboid.lIIlIlII[4]] = lIlIlllIIIlIlIl.getBlockAt(lIlIlllIIIlIlll.x2, lIlIlllIIIlIlll.y1, lIlIlllIIIlIlll.z1);
        lIlIlllIIIlIllI[Cuboid.lIIlIlII[5]] = lIlIlllIIIlIlIl.getBlockAt(lIlIlllIIIlIlll.x2, lIlIlllIIIlIlll.y1, lIlIlllIIIlIlll.z2);
        lIlIlllIIIlIllI[Cuboid.lIIlIlII[6]] = lIlIlllIIIlIlIl.getBlockAt(lIlIlllIIIlIlll.x2, lIlIlllIIIlIlll.y2, lIlIlllIIIlIlll.z1);
        lIlIlllIIIlIllI[Cuboid.lIIlIlII[7]] = lIlIlllIIIlIlIl.getBlockAt(lIlIlllIIIlIlll.x2, lIlIlllIIIlIlll.y2, lIlIlllIIIlIlll.z2);
        return lIlIlllIIIlIllI;
    }

    public Location getLowerNE() {
        Cuboid lIlIlllIlIlIlII;
        return new Location(lIlIlllIlIlIlII.getWorld(), (double)lIlIlllIlIlIlII.x1, (double)lIlIlllIlIlIlII.y1, (double)lIlIlllIlIlIlII.z1);
    }

    public Cuboid(Location lIlIllllIlIIIII, Location lIlIllllIlIIIlI) {
        Cuboid lIlIllllIlIIlII;
        if (Cuboid.lIIIlIIlll((int)lIlIllllIlIIIII.getWorld().equals((Object)lIlIllllIlIIIlI.getWorld()))) {
            throw new IllegalArgumentException(lIIIIIlI[lIIlIlII[0]]);
        }
        lIlIllllIlIIlII.worldName = lIlIllllIlIIIII.getWorld().getName();
        lIlIllllIlIIlII.x1 = Math.min(lIlIllllIlIIIII.getBlockX(), lIlIllllIlIIIlI.getBlockX());
        lIlIllllIlIIlII.y1 = Math.min(lIlIllllIlIIIII.getBlockY(), lIlIllllIlIIIlI.getBlockY());
        lIlIllllIlIIlII.z1 = Math.min(lIlIllllIlIIIII.getBlockZ(), lIlIllllIlIIIlI.getBlockZ());
        lIlIllllIlIIlII.x2 = Math.max(lIlIllllIlIIIII.getBlockX(), lIlIllllIlIIIlI.getBlockX());
        lIlIllllIlIIlII.y2 = Math.max(lIlIllllIlIIIII.getBlockY(), lIlIllllIlIIIlI.getBlockY());
        lIlIllllIlIIlII.z2 = Math.max(lIlIllllIlIIIII.getBlockZ(), lIlIllllIlIIIlI.getBlockZ());
    }

    private static boolean lIIIlIlIIl(int n) {
        return n != 0;
    }

    private static boolean lIIIllIlll(int n, int n2) {
        return n != n2;
    }

    public int getVolume() {
        Cuboid lIlIllIllIIlllI;
        return lIlIllIllIIlllI.getSizeX() * lIlIllIllIIlllI.getSizeY() * lIlIllIllIIlllI.getSizeZ();
    }

    private static boolean lIIIllIlII(int n, int n2) {
        return n > n2;
    }

    public boolean containsOnly(int lIlIllIlIlIIlII) {
        Cuboid lIlIllIlIlIIlll;
        Iterator<Block> lIlIllIlIlIIIll = lIlIllIlIlIIlll.iterator();
        while (Cuboid.lIIIlIlIIl((int)lIlIllIlIlIIIll.hasNext())) {
            Block lIlIllIlIlIlIII = lIlIllIlIlIIIll.next();
            if (Cuboid.lIIIllIlll(lIlIllIlIlIlIII.getTypeId(), lIlIllIlIlIIlII)) {
                return lIIlIlII[0];
            }
            "".length();
            if (-(113 ^ 117) <= 0) continue;
            return (boolean)((231 ^ 180) & ~(50 ^ 97));
        }
        return lIIlIlII[1];
    }

    private static boolean lIIIllIlIl(int n, int n2) {
        return n < n2;
    }

    public int getLowerZ() {
        Cuboid lIlIlllIIlIIlIl;
        return lIlIlllIIlIIlIl.z1;
    }

    public Block getRelativeBlock(int lIlIllIlIIIIIII, int lIlIllIIlllllll, int lIlIllIlIIIIIlI) {
        Cuboid lIlIllIlIIIIlIl;
        return lIlIllIlIIIIlIl.getWorld().getBlockAt(lIlIllIlIIIIlIl.x1 + lIlIllIlIIIIIII, lIlIllIlIIIIlIl.y1 + lIlIllIIlllllll, lIlIllIlIIIIlIl.z1 + lIlIllIlIIIIIlI);
    }

    private static boolean lIIIlIIlll(int n) {
        return n == 0;
    }

    public Block getRelativeBlock(World lIlIllIIlllIIlI, int lIlIllIIlllIllI, int lIlIllIIlllIIII, int lIlIllIIllIllll) {
        Cuboid lIlIllIIlllIIll;
        return lIlIllIIlllIIlI.getBlockAt(lIlIllIIlllIIll.x1 + lIlIllIIlllIllI, lIlIllIIlllIIll.y1 + lIlIllIIlllIIII, lIlIllIIlllIIll.z1 + lIlIllIIllIllll);
    }

    public List<Chunk> getChunks() {
        Cuboid lIlIllIIlIlllII;
        ArrayList<Chunk> lIlIllIIllIIIlI = new ArrayList<Chunk>();
        World lIlIllIIllIIIIl = lIlIllIIlIlllII.getWorld();
        int lIlIllIIllIIIII = lIlIllIIlIlllII.getLowerX() & lIIlIlII[21];
        int lIlIllIIlIlllll = lIlIllIIlIlllII.getUpperX() & lIIlIlII[21];
        int lIlIllIIlIllllI = lIlIllIIlIlllII.getLowerZ() & lIIlIlII[21];
        int lIlIllIIlIlllIl = lIlIllIIlIlllII.getUpperZ() & lIIlIlII[21];
        int lIlIllIIllIIlII = lIlIllIIllIIIII;
        while (Cuboid.lIIIllIIlI(lIlIllIIllIIlII, lIlIllIIlIlllll)) {
            int lIlIllIIllIIlIl = lIlIllIIlIllllI;
            while (Cuboid.lIIIllIIlI(lIlIllIIllIIlIl, lIlIllIIlIlllIl)) {
                "".length();
                lIlIllIIllIIIlI.add(lIlIllIIllIIIIl.getChunkAt(lIlIllIIllIIlII >> lIIlIlII[4], lIlIllIIllIIlIl >> lIIlIlII[4]));
                lIlIllIIllIIlIl += 16;
                "".length();
                if (" ".length() != 0) continue;
                return null;
            }
            lIlIllIIllIIlII += 16;
            "".length();
            if ("  ".length() >= ((218 ^ 192) & ~(122 ^ 96))) continue;
            return null;
        }
        return lIlIllIIllIIIlI;
    }

    public Cuboid getFace(CuboidDirection lIlIllIlIlIllIl) {
        switch (1.$SwitchMap$com$biel$lobby$utilities$Cuboid$CuboidDirection[lIlIllIlIlIllIl.ordinal()]) {
            Cuboid lIlIllIlIllIIII;
            case 5: {
                return new Cuboid(lIlIllIlIllIIII.worldName, lIlIllIlIllIIII.x1, lIlIllIlIllIIII.y1, lIlIllIlIllIIII.z1, lIlIllIlIllIIII.x2, lIlIllIlIllIIII.y1, lIlIllIlIllIIII.z2);
            }
            case 6: {
                return new Cuboid(lIlIllIlIllIIII.worldName, lIlIllIlIllIIII.x1, lIlIllIlIllIIII.y2, lIlIllIlIllIIII.z1, lIlIllIlIllIIII.x2, lIlIllIlIllIIII.y2, lIlIllIlIllIIII.z2);
            }
            case 1: {
                return new Cuboid(lIlIllIlIllIIII.worldName, lIlIllIlIllIIII.x1, lIlIllIlIllIIII.y1, lIlIllIlIllIIII.z1, lIlIllIlIllIIII.x1, lIlIllIlIllIIII.y2, lIlIllIlIllIIII.z2);
            }
            case 2: {
                return new Cuboid(lIlIllIlIllIIII.worldName, lIlIllIlIllIIII.x2, lIlIllIlIllIIII.y1, lIlIllIlIllIIII.z1, lIlIllIlIllIIII.x2, lIlIllIlIllIIII.y2, lIlIllIlIllIIII.z2);
            }
            case 3: {
                return new Cuboid(lIlIllIlIllIIII.worldName, lIlIllIlIllIIII.x1, lIlIllIlIllIIII.y1, lIlIllIlIllIIII.z1, lIlIllIlIllIIII.x2, lIlIllIlIllIIII.y2, lIlIllIlIllIIII.z1);
            }
            case 4: {
                return new Cuboid(lIlIllIlIllIIII.worldName, lIlIllIlIllIIII.x1, lIlIllIlIllIIII.y1, lIlIllIlIllIIII.z2, lIlIllIlIllIIII.x2, lIlIllIlIllIIII.y2, lIlIllIlIllIIII.z2);
            }
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(lIIIIIlI[lIIlIlII[20]]).append((Object)lIlIllIlIlIllIl)));
    }

    public boolean contains(int lIlIllIlllIIIlI, int lIlIllIlllIIIIl, int lIlIllIlllIIIII) {
        Cuboid lIlIllIlllIIIll;
        int n;
        if (Cuboid.lIIIllIIIl(lIlIllIlllIIIlI, lIlIllIlllIIIll.x1) && Cuboid.lIIIllIIlI(lIlIllIlllIIIlI, lIlIllIlllIIIll.x2) && Cuboid.lIIIllIIIl(lIlIllIlllIIIIl, lIlIllIlllIIIll.y1) && Cuboid.lIIIllIIlI(lIlIllIlllIIIIl, lIlIllIlllIIIll.y2) && Cuboid.lIIIllIIIl(lIlIllIlllIIIII, lIlIllIlllIIIll.z1) && Cuboid.lIIIllIIlI(lIlIllIlllIIIII, lIlIllIlllIIIll.z2)) {
            n = lIIlIlII[1];
            "".length();
            if ((69 ^ 65) <= 0) {
                return (boolean)((44 ^ 53) & ~(165 ^ 188));
            }
        } else {
            n = lIIlIlII[0];
        }
        return (boolean)n;
    }

    private static String lllIIllIl(String lIlIllIIIlIIllI, String lIlIllIIIlIIlll) {
        try {
            SecretKeySpec lIlIllIIIlIlIll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlIllIIIlIIlll.getBytes(StandardCharsets.UTF_8)), lIIlIlII[8]), "DES");
            Cipher lIlIllIIIlIlIlI = Cipher.getInstance("DES");
            lIlIllIIIlIlIlI.init(lIIlIlII[2], lIlIllIIIlIlIll);
            return new String(lIlIllIIIlIlIlI.doFinal(Base64.getDecoder().decode(lIlIllIIIlIIllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIllIIIlIlIIl) {
            lIlIllIIIlIlIIl.printStackTrace();
            return null;
        }
    }

    public int getLowerY() {
        Cuboid lIlIlllIIlIlIII;
        return lIlIlllIIlIlIII.y1;
    }

    public byte getAverageLightLevel() {
        byte by;
        Cuboid lIlIllIllIIIllI;
        long lIlIllIllIIIlIl = 0L;
        int lIlIllIllIIIlII = lIIlIlII[0];
        Iterator<Block> lIlIllIllIIIIII = lIlIllIllIIIllI.iterator();
        while (Cuboid.lIIIlIlIIl((int)lIlIllIllIIIIII.hasNext())) {
            Block lIlIllIllIIIlll = lIlIllIllIIIIII.next();
            if (Cuboid.lIIIlIlIIl((int)lIlIllIllIIIlll.isEmpty())) {
                lIlIllIllIIIlIl += (long)lIlIllIllIIIlll.getLightLevel();
                ++lIlIllIllIIIlII;
            }
            "".length();
            if (" ".length() != 0) continue;
            return (byte)((19 ^ 114) & ~(201 ^ 168));
        }
        if (Cuboid.lIIIllIIll(lIlIllIllIIIlII)) {
            by = (byte)(lIlIllIllIIIlIl / (long)lIlIllIllIIIlII);
            "".length();
            if ((55 ^ 50) == 0) {
                return (byte)((67 ^ 23) & ~(50 ^ 102));
            }
        } else {
            by = lIIlIlII[0];
        }
        return by;
    }

    public Cuboid shift(CuboidDirection lIlIlllIIIIIlII, int lIlIlllIIIIIIll) {
        Cuboid lIlIlllIIIIIIlI;
        return lIlIlllIIIIIIlI.expand(lIlIlllIIIIIlII, lIlIlllIIIIIIll).expand(lIlIlllIIIIIlII.opposite(), -lIlIlllIIIIIIll);
    }

    public Cuboid(Cuboid lIlIllllIIlIlIl) {
        Cuboid lIlIllllIIlIllI;
        lIlIllllIIlIllI(lIlIllllIIlIlIl.getWorld().getName(), lIlIllllIIlIlIl.x1, lIlIllllIIlIlIl.y1, lIlIllllIIlIlIl.z1, lIlIllllIIlIlIl.x2, lIlIllllIIlIlIl.y2, lIlIllllIIlIlIl.z2);
    }

    public Cuboid(World lIlIllllIIIlIIl, int lIlIllllIIIlIII, int lIlIlllIlllllll, int lIlIllllIIIIllI, int lIlIllllIIIIlIl, int lIlIlllIlllllII, int lIlIlllIllllIll) {
        Cuboid lIlIllllIIIlIlI;
        lIlIllllIIIlIlI.worldName = lIlIllllIIIlIIl.getName();
        lIlIllllIIIlIlI.x1 = Math.min(lIlIllllIIIlIII, lIlIllllIIIIlIl);
        lIlIllllIIIlIlI.x2 = Math.max(lIlIllllIIIlIII, lIlIllllIIIIlIl);
        lIlIllllIIIlIlI.y1 = Math.min(lIlIlllIlllllll, lIlIlllIlllllII);
        lIlIllllIIIlIlI.y2 = Math.max(lIlIlllIlllllll, lIlIlllIlllllII);
        lIlIllllIIIlIlI.z1 = Math.min(lIlIllllIIIIllI, lIlIlllIllllIll);
        lIlIllllIIIlIlI.z2 = Math.max(lIlIllllIIIIllI, lIlIlllIllllIll);
    }

    public int getLowerX() {
        Cuboid lIlIlllIIlIlIlI;
        return lIlIlllIIlIlIlI.x1;
    }

    private static boolean lIIIllIIll(int n) {
        return n > 0;
    }

    private static String lllIlllll(String lIlIllIIIIllIll, String lIlIllIIIIllIII) {
        try {
            SecretKeySpec lIlIllIIIIllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlIllIIIIllIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lIlIllIIIIlllIl = Cipher.getInstance("Blowfish");
            lIlIllIIIIlllIl.init(lIIlIlII[2], lIlIllIIIIllllI);
            return new String(lIlIllIIIIlllIl.doFinal(Base64.getDecoder().decode(lIlIllIIIIllIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlIllIIIIlllII) {
            lIlIllIIIIlllII.printStackTrace();
            return null;
        }
    }

    public World getWorld() {
        Cuboid lIlIlllIIlllIIl;
        World lIlIlllIIlllIII = Bukkit.getWorld((String)lIlIlllIIlllIIl.worldName);
        if (Cuboid.lIIIlIllII((Object)lIlIlllIIlllIII)) {
            throw new IllegalStateException(String.valueOf(new StringBuilder().append(lIIIIIlI[lIIlIlII[15]]).append(lIlIlllIIlllIIl.worldName).append(lIIIIIlI[lIIlIlII[16]])));
        }
        return lIlIlllIIlllIII;
    }

    static {
        Cuboid.lIIIlIIllI();
        Cuboid.llllIllIl();
    }

    public Cuboid expand(CuboidDirection lIlIlllIIIIllIl, int lIlIlllIIIIlIIl) {
        switch (1.$SwitchMap$com$biel$lobby$utilities$Cuboid$CuboidDirection[lIlIlllIIIIllIl.ordinal()]) {
            Cuboid lIlIlllIIIIlIll;
            case 1: {
                return new Cuboid(lIlIlllIIIIlIll.worldName, lIlIlllIIIIlIll.x1 - lIlIlllIIIIlIIl, lIlIlllIIIIlIll.y1, lIlIlllIIIIlIll.z1, lIlIlllIIIIlIll.x2, lIlIlllIIIIlIll.y2, lIlIlllIIIIlIll.z2);
            }
            case 2: {
                return new Cuboid(lIlIlllIIIIlIll.worldName, lIlIlllIIIIlIll.x1, lIlIlllIIIIlIll.y1, lIlIlllIIIIlIll.z1, lIlIlllIIIIlIll.x2 + lIlIlllIIIIlIIl, lIlIlllIIIIlIll.y2, lIlIlllIIIIlIll.z2);
            }
            case 3: {
                return new Cuboid(lIlIlllIIIIlIll.worldName, lIlIlllIIIIlIll.x1, lIlIlllIIIIlIll.y1, lIlIlllIIIIlIll.z1 - lIlIlllIIIIlIIl, lIlIlllIIIIlIll.x2, lIlIlllIIIIlIll.y2, lIlIlllIIIIlIll.z2);
            }
            case 4: {
                return new Cuboid(lIlIlllIIIIlIll.worldName, lIlIlllIIIIlIll.x1, lIlIlllIIIIlIll.y1, lIlIlllIIIIlIll.z1, lIlIlllIIIIlIll.x2, lIlIlllIIIIlIll.y2, lIlIlllIIIIlIll.z2 + lIlIlllIIIIlIIl);
            }
            case 5: {
                return new Cuboid(lIlIlllIIIIlIll.worldName, lIlIlllIIIIlIll.x1, lIlIlllIIIIlIll.y1 - lIlIlllIIIIlIIl, lIlIlllIIIIlIll.z1, lIlIlllIIIIlIll.x2, lIlIlllIIIIlIll.y2, lIlIlllIIIIlIll.z2);
            }
            case 6: {
                return new Cuboid(lIlIlllIIIIlIll.worldName, lIlIlllIIIIlIll.x1, lIlIlllIIIIlIll.y1, lIlIlllIIIIlIll.z1, lIlIlllIIIIlIll.x2, lIlIlllIIIIlIll.y2 + lIlIlllIIIIlIIl, lIlIlllIIIIlIll.z2);
            }
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(lIIIIIlI[lIIlIlII[17]]).append((Object)lIlIlllIIIIllIl)));
    }

    public Location getCenter() {
        Cuboid lIlIlllIIllllll;
        int lIlIlllIlIIIIlI = lIlIlllIIllllll.getUpperX() + lIIlIlII[1];
        int lIlIlllIlIIIIIl = lIlIlllIIllllll.getUpperY() + lIIlIlII[1];
        int lIlIlllIlIIIIII = lIlIlllIIllllll.getUpperZ() + lIIlIlII[1];
        return new Location(lIlIlllIIllllll.getWorld(), (double)lIlIlllIIllllll.getLowerX() + (double)(lIlIlllIlIIIIlI - lIlIlllIIllllll.getLowerX()) / 2.0, (double)lIlIlllIIllllll.getLowerY() + (double)(lIlIlllIlIIIIIl - lIlIlllIIllllll.getLowerY()) / 2.0, (double)lIlIlllIIllllll.getLowerZ() + (double)(lIlIlllIlIIIIII - lIlIlllIIllllll.getLowerZ()) / 2.0);
    }

    public Cuboid(Map<String, Object> lIlIlllIlIlllIl) {
        Cuboid lIlIlllIlIllllI;
        lIlIlllIlIllllI.worldName = (String)lIlIlllIlIlllIl.get(lIIIIIlI[lIIlIlII[1]]);
        lIlIlllIlIllllI.x1 = (Integer)lIlIlllIlIlllIl.get(lIIIIIlI[lIIlIlII[2]]);
        lIlIlllIlIllllI.x2 = (Integer)lIlIlllIlIlllIl.get(lIIIIIlI[lIIlIlII[3]]);
        lIlIlllIlIllllI.y1 = (Integer)lIlIlllIlIlllIl.get(lIIIIIlI[lIIlIlII[4]]);
        lIlIlllIlIllllI.y2 = (Integer)lIlIlllIlIlllIl.get(lIIIIIlI[lIIlIlII[5]]);
        lIlIlllIlIllllI.z1 = (Integer)lIlIlllIlIlllIl.get(lIIIIIlI[lIIlIlII[6]]);
        lIlIlllIlIllllI.z2 = (Integer)lIlIlllIlIlllIl.get(lIIIIIlI[lIIlIlII[7]]);
    }

    @Override
    public Iterator<Block> iterator() {
        Cuboid lIlIllIIlIlIIIl;
        return lIlIllIIlIlIIIl.new CuboidIterator(lIlIllIIlIlIIIl.getWorld(), lIlIllIIlIlIIIl.x1, lIlIllIIlIlIIIl.y1, lIlIllIIlIlIIIl.z1, lIlIllIIlIlIIIl.x2, lIlIllIIlIlIIIl.y2, lIlIllIIlIlIIIl.z2);
    }

    public Cuboid contract() {
        Cuboid lIlIllIlIllllIl;
        return lIlIllIlIllllIl.contract(CuboidDirection.Down).contract(CuboidDirection.South).contract(CuboidDirection.East).contract(CuboidDirection.Up).contract(CuboidDirection.North).contract(CuboidDirection.West);
    }

    public List<Block> getBlocks() {
        Cuboid lIlIlllIlIIlIlI;
        Iterator<Block> lIlIlllIlIIllII = lIlIlllIlIIlIlI.iterator();
        ArrayList<Block> lIlIlllIlIIlIll = new ArrayList<Block>();
        while (Cuboid.lIIIlIlIIl((int)lIlIlllIlIIllII.hasNext())) {
            "".length();
            lIlIlllIlIIlIll.add(lIlIlllIlIIllII.next());
            "".length();
            if (-" ".length() <= 0) continue;
            return null;
        }
        return lIlIlllIlIIlIll;
    }

    private Cuboid(String lIlIlllIllIlIIl, int lIlIlllIllIlIII, int lIlIlllIllIllll, int lIlIlllIllIIllI, int lIlIlllIllIIlIl, int lIlIlllIllIIlII, int lIlIlllIllIlIll) {
        Cuboid lIlIlllIlllIIlI;
        lIlIlllIlllIIlI.worldName = lIlIlllIllIlIIl;
        lIlIlllIlllIIlI.x1 = Math.min(lIlIlllIllIlIII, lIlIlllIllIIlIl);
        lIlIlllIlllIIlI.x2 = Math.max(lIlIlllIllIlIII, lIlIlllIllIIlIl);
        lIlIlllIlllIIlI.y1 = Math.min(lIlIlllIllIllll, lIlIlllIllIIlII);
        lIlIlllIlllIIlI.y2 = Math.max(lIlIlllIllIllll, lIlIlllIllIIlII);
        lIlIlllIlllIIlI.z1 = Math.min(lIlIlllIllIIllI, lIlIlllIllIlIll);
        lIlIlllIlllIIlI.z2 = Math.max(lIlIlllIllIIllI, lIlIlllIllIlIll);
    }

    private static void lIIIlIIllI() {
        lIIlIlII = new int[30];
        Cuboid.lIIlIlII[0] = (91 + 62 - 144 + 189 ^ 6 + 123 - 122 + 142) & (136 + 49 - 15 + 41 ^ 75 + 31 - 10 + 32 ^ -" ".length());
        Cuboid.lIIlIlII[1] = " ".length();
        Cuboid.lIIlIlII[2] = "  ".length();
        Cuboid.lIIlIlII[3] = "   ".length();
        Cuboid.lIIlIlII[4] = 142 ^ 138;
        Cuboid.lIIlIlII[5] = 28 ^ 39 ^ (6 ^ 56);
        Cuboid.lIIlIlII[6] = 44 ^ 42;
        Cuboid.lIIlIlII[7] = 114 ^ 117;
        Cuboid.lIIlIlII[8] = 18 ^ 26;
        Cuboid.lIIlIlII[9] = 115 + 80 - 184 + 131 ^ 66 + 19 - 19 + 69;
        Cuboid.lIIlIlII[10] = 150 ^ 156 ^ (209 ^ 130) & ~(5 ^ 86);
        Cuboid.lIIlIlII[11] = 29 ^ 22;
        Cuboid.lIIlIlII[12] = 180 ^ 184;
        Cuboid.lIIlIlII[13] = 242 ^ 199 ^ (22 ^ 46);
        Cuboid.lIIlIlII[14] = 252 ^ 194 ^ (47 ^ 31);
        Cuboid.lIIlIlII[15] = 172 ^ 163;
        Cuboid.lIIlIlII[16] = 14 ^ 17 ^ (53 ^ 58);
        Cuboid.lIIlIlII[17] = 63 ^ 46;
        Cuboid.lIIlIlII[18] = 177 ^ 163;
        Cuboid.lIIlIlII[19] = 22 ^ 112 ^ (69 ^ 48);
        Cuboid.lIIlIlII[20] = 172 ^ 184;
        Cuboid.lIIlIlII[21] = -(92 ^ 76);
        Cuboid.lIIlIlII[22] = 62 ^ 43;
        Cuboid.lIIlIlII[23] = 155 ^ 141;
        Cuboid.lIIlIlII[24] = 234 ^ 135 ^ (217 ^ 163);
        Cuboid.lIIlIlII[25] = 145 ^ 137;
        Cuboid.lIIlIlII[26] = 110 ^ 12 ^ (191 ^ 196);
        Cuboid.lIIlIlII[27] = 46 ^ 100 ^ (28 ^ 76);
        Cuboid.lIIlIlII[28] = 116 ^ 111;
        Cuboid.lIIlIlII[29] = 83 ^ 79;
    }

    public int getSizeZ() {
        Cuboid lIlIlllIIlIlllI;
        return lIlIlllIIlIlllI.z2 - lIlIlllIIlIlllI.z1 + lIIlIlII[1];
    }

    public Cuboid outset(CuboidDirection lIlIllIllllIlll, int lIlIllIllllIllI) {
        void lIlIllIllllIlIl;
        switch (1.$SwitchMap$com$biel$lobby$utilities$Cuboid$CuboidDirection[lIlIllIllllIlll.ordinal()]) {
            Cuboid lIlIllIllllIlII;
            case 7: {
                Cuboid lIlIllIlllllIll = lIlIllIllllIlII.expand(CuboidDirection.North, lIlIllIllllIllI).expand(CuboidDirection.South, lIlIllIllllIllI).expand(CuboidDirection.East, lIlIllIllllIllI).expand(CuboidDirection.West, lIlIllIllllIllI);
                "".length();
                if ((106 ^ 111) > 0) break;
                return null;
            }
            case 8: {
                Cuboid lIlIllIlllllIlI = lIlIllIllllIlII.expand(CuboidDirection.Down, lIlIllIllllIllI).expand(CuboidDirection.Up, lIlIllIllllIllI);
                "".length();
                if (null == null) break;
                return null;
            }
            case 9: {
                Cuboid lIlIllIlllllIIl = lIlIllIllllIlII.outset(CuboidDirection.Horizontal, lIlIllIllllIllI).outset(CuboidDirection.Vertical, lIlIllIllllIllI);
                "".length();
                if ("  ".length() > 0) break;
                return null;
            }
            default: {
                throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(lIIIIIlI[lIIlIlII[18]]).append((Object)lIlIllIllllIlll)));
            }
        }
        return lIlIllIllllIlIl;
    }

    private static boolean lIIIlIllII(Object object) {
        return object == null;
    }

    public Cuboid contract(CuboidDirection lIlIllIlIllIlII) {
        Cuboid lIlIllIlIllIlIl;
        Cuboid lIlIllIlIllIllI = lIlIllIlIllIlIl.getFace(lIlIllIlIllIlII.opposite());
        switch (1.$SwitchMap$com$biel$lobby$utilities$Cuboid$CuboidDirection[lIlIllIlIllIlII.ordinal()]) {
            case 5: {
                while (Cuboid.lIIIlIlIIl((int)lIlIllIlIllIllI.containsOnly(lIIlIlII[0])) && Cuboid.lIIIllIlII(lIlIllIlIllIllI.getLowerY(), lIlIllIlIllIlIl.getLowerY())) {
                    lIlIllIlIllIllI = lIlIllIlIllIllI.shift(CuboidDirection.Down, lIIlIlII[1]);
                    "".length();
                    if (" ".length() != 0) continue;
                    return null;
                }
                return new Cuboid(lIlIllIlIllIlIl.worldName, lIlIllIlIllIlIl.x1, lIlIllIlIllIlIl.y1, lIlIllIlIllIlIl.z1, lIlIllIlIllIlIl.x2, lIlIllIlIllIllI.getUpperY(), lIlIllIlIllIlIl.z2);
            }
            case 6: {
                while (Cuboid.lIIIlIlIIl((int)lIlIllIlIllIllI.containsOnly(lIIlIlII[0])) && Cuboid.lIIIllIlIl(lIlIllIlIllIllI.getUpperY(), lIlIllIlIllIlIl.getUpperY())) {
                    lIlIllIlIllIllI = lIlIllIlIllIllI.shift(CuboidDirection.Up, lIIlIlII[1]);
                    "".length();
                    if ("   ".length() != ((45 ^ 121 ^ (45 ^ 89)) & (71 ^ 11 ^ (125 ^ 17) ^ -" ".length()))) continue;
                    return null;
                }
                return new Cuboid(lIlIllIlIllIlIl.worldName, lIlIllIlIllIlIl.x1, lIlIllIlIllIllI.getLowerY(), lIlIllIlIllIlIl.z1, lIlIllIlIllIlIl.x2, lIlIllIlIllIlIl.y2, lIlIllIlIllIlIl.z2);
            }
            case 1: {
                while (Cuboid.lIIIlIlIIl((int)lIlIllIlIllIllI.containsOnly(lIIlIlII[0])) && Cuboid.lIIIllIlII(lIlIllIlIllIllI.getLowerX(), lIlIllIlIllIlIl.getLowerX())) {
                    lIlIllIlIllIllI = lIlIllIlIllIllI.shift(CuboidDirection.North, lIIlIlII[1]);
                    "".length();
                    if (((22 + 35 - 12 + 114 ^ 6 + 12 - -85 + 31) & (225 ^ 161 ^ (114 ^ 43) ^ -" ".length())) >= ((27 ^ 32 ^ (177 ^ 164)) & (55 + 42 - 53 + 121 ^ 83 + 123 - 159 + 92 ^ -" ".length()))) continue;
                    return null;
                }
                return new Cuboid(lIlIllIlIllIlIl.worldName, lIlIllIlIllIlIl.x1, lIlIllIlIllIlIl.y1, lIlIllIlIllIlIl.z1, lIlIllIlIllIllI.getUpperX(), lIlIllIlIllIlIl.y2, lIlIllIlIllIlIl.z2);
            }
            case 2: {
                while (Cuboid.lIIIlIlIIl((int)lIlIllIlIllIllI.containsOnly(lIIlIlII[0])) && Cuboid.lIIIllIlIl(lIlIllIlIllIllI.getUpperX(), lIlIllIlIllIlIl.getUpperX())) {
                    lIlIllIlIllIllI = lIlIllIlIllIllI.shift(CuboidDirection.South, lIIlIlII[1]);
                    "".length();
                    if (((32 ^ 110) & ~(205 ^ 131)) != (197 ^ 193)) continue;
                    return null;
                }
                return new Cuboid(lIlIllIlIllIlIl.worldName, lIlIllIlIllIllI.getLowerX(), lIlIllIlIllIlIl.y1, lIlIllIlIllIlIl.z1, lIlIllIlIllIlIl.x2, lIlIllIlIllIlIl.y2, lIlIllIlIllIlIl.z2);
            }
            case 3: {
                while (Cuboid.lIIIlIlIIl((int)lIlIllIlIllIllI.containsOnly(lIIlIlII[0])) && Cuboid.lIIIllIlII(lIlIllIlIllIllI.getLowerZ(), lIlIllIlIllIlIl.getLowerZ())) {
                    lIlIllIlIllIllI = lIlIllIlIllIllI.shift(CuboidDirection.East, lIIlIlII[1]);
                    "".length();
                    if ("   ".length() == "   ".length()) continue;
                    return null;
                }
                return new Cuboid(lIlIllIlIllIlIl.worldName, lIlIllIlIllIlIl.x1, lIlIllIlIllIlIl.y1, lIlIllIlIllIlIl.z1, lIlIllIlIllIlIl.x2, lIlIllIlIllIlIl.y2, lIlIllIlIllIllI.getUpperZ());
            }
            case 4: {
                while (Cuboid.lIIIlIlIIl((int)lIlIllIlIllIllI.containsOnly(lIIlIlII[0])) && Cuboid.lIIIllIlIl(lIlIllIlIllIllI.getUpperZ(), lIlIllIlIllIlIl.getUpperZ())) {
                    lIlIllIlIllIllI = lIlIllIlIllIllI.shift(CuboidDirection.West, lIIlIlII[1]);
                    "".length();
                    if (" ".length() < (123 ^ 127)) continue;
                    return null;
                }
                return new Cuboid(lIlIllIlIllIlIl.worldName, lIlIllIlIllIlIl.x1, lIlIllIlIllIlIl.y1, lIlIllIlIllIllI.getLowerZ(), lIlIllIlIllIlIl.x2, lIlIllIlIllIlIl.y2, lIlIllIlIllIlIl.z2);
            }
        }
        throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(lIIIIIlI[lIIlIlII[19]]).append((Object)lIlIllIlIllIlII)));
    }

    public Cuboid getBoundingCuboid(Cuboid lIlIllIlIIlIIII) {
        Cuboid lIlIllIlIIlIIIl;
        if (Cuboid.lIIIlIllII(lIlIllIlIIlIIII)) {
            return lIlIllIlIIlIIIl;
        }
        int lIlIllIlIIlIlll = Math.min(lIlIllIlIIlIIIl.getLowerX(), lIlIllIlIIlIIII.getLowerX());
        int lIlIllIlIIlIllI = Math.min(lIlIllIlIIlIIIl.getLowerY(), lIlIllIlIIlIIII.getLowerY());
        int lIlIllIlIIlIlIl = Math.min(lIlIllIlIIlIIIl.getLowerZ(), lIlIllIlIIlIIII.getLowerZ());
        int lIlIllIlIIlIlII = Math.max(lIlIllIlIIlIIIl.getUpperX(), lIlIllIlIIlIIII.getUpperX());
        int lIlIllIlIIlIIll = Math.max(lIlIllIlIIlIIIl.getUpperY(), lIlIllIlIIlIIII.getUpperY());
        int lIlIllIlIIlIIlI = Math.max(lIlIllIlIIlIIIl.getUpperZ(), lIlIllIlIIlIIII.getUpperZ());
        return new Cuboid(lIlIllIlIIlIIIl.worldName, lIlIllIlIIlIlll, lIlIllIlIIlIllI, lIlIllIlIIlIlIl, lIlIllIlIIlIlII, lIlIllIlIIlIIll, lIlIllIlIIlIIlI);
    }

    public int getSizeX() {
        Cuboid lIlIlllIIllIlII;
        return lIlIlllIIllIlII.x2 - lIlIlllIIllIlII.x1 + lIIlIlII[1];
    }

    private static void llllIllIl() {
        lIIIIIlI = new String[lIIlIlII[29]];
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[0]] = Cuboid.lllIIllIl("c+Kcmss/gog526O2AgmnwsZbdYpH/vBbNL822i1R3PoKCewL0nDDiA==", "ZBvyU");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[1]] = Cuboid.lllIIllIl("FBQ74v/akCXt8ozXfqhcWQ==", "WoucK");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[2]] = Cuboid.lllIIllIl("+UkEYhCFBeI=", "HHMfF");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[3]] = Cuboid.lllIIllIl("2w9iYAOy9cY=", "aOLAu");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[4]] = Cuboid.lllIlllll("sLAtRsGyxhk=", "iVtfb");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[5]] = Cuboid.llllIIIIl("E0Y=", "jtMhJ");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[6]] = Cuboid.llllIIIIl("KEM=", "RrWmN");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[7]] = Cuboid.lllIIllIl("3bPYbugWEEY=", "TGjBh");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[8]] = Cuboid.llllIIIIl("Gi0gBwsjIz8O", "mBRko");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[9]] = Cuboid.lllIlllll("5tciw4CoZMQ=", "ilxDC");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[10]] = Cuboid.llllIIIIl("IH8=", "YNaYJ");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[11]] = Cuboid.lllIIllIl("KkgaNP4cdBU=", "CVLHw");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[12]] = Cuboid.llllIIIIl("Pmo=", "FXMBw");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[13]] = Cuboid.lllIIllIl("YGVzfaVBMvw=", "gVqJM");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[14]] = Cuboid.lllIlllll("rICHxo+hft8=", "emDDi");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[15]] = Cuboid.llllIIIIl("ADwjJSd3dA==", "WSQIC");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[16]] = Cuboid.lllIIllIl("fqt3n3WQRIpXpRI4gHp5cQ==", "wZCwy");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[17]] = Cuboid.lllIlllll("j8pgrzH/C7k5Um7MqyQPYG/SSDgxaIht", "feBRD");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[18]] = Cuboid.lllIIllIl("Xn1cyLIR9BZtQlSN6vSDABxCwCJKaoBM", "cgWSu");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[19]] = Cuboid.lllIlllll("dku1iPG5SjZebk/Lvz/oihD8Huc5UYii", "yHZoc");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[20]] = Cuboid.llllIIIIl("CwgvNxYrAnkyEzADOiITLQh5", "BfYVz");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[22]] = Cuboid.lllIlllll("HpMsd995WzXKnQbqLKBShg==", "SZDBt");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[23]] = Cuboid.lllIlllll("Zk973KTzDYI=", "tnPMr");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[24]] = Cuboid.llllIIIIl("Sw==", "gzcRD");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[25]] = Cuboid.lllIIllIl("L6afJR3zN90=", "hyGYh");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[26]] = Cuboid.llllIIIIl("enY=", "GHZvs");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[27]] = Cuboid.lllIIllIl("gwJRI42+F3g=", "ZKxdj");
        Cuboid.lIIIIIlI[Cuboid.lIIlIlII[28]] = Cuboid.lllIIllIl("Su3E0SgVShM=", "XhaUl");
    }

    public int getSizeY() {
        Cuboid lIlIlllIIllIIIl;
        return lIlIlllIIllIIIl.y2 - lIlIlllIIllIIIl.y1 + lIIlIlII[1];
    }

    public Cuboid(Location lIlIllllIIllIll) {
        Cuboid lIlIllllIIllIlI;
        lIlIllllIIllIlI(lIlIllllIIllIll, lIlIllllIIllIll);
    }

    public int getUpperZ() {
        Cuboid lIlIlllIIIllIll;
        return lIlIlllIIIllIll.z2;
    }

    public String toString() {
        Cuboid lIlIllIIlIIlIll;
        return String.valueOf(new StringBuilder().append(lIIIIIlI[lIIlIlII[22]]).append(lIlIllIIlIIlIll.worldName).append(lIIIIIlI[lIIlIlII[23]]).append(lIlIllIIlIIlIll.x1).append(lIIIIIlI[lIIlIlII[24]]).append(lIlIllIIlIIlIll.y1).append(lIIIIIlI[lIIlIlII[25]]).append(lIlIllIIlIIlIll.z1).append(lIIIIIlI[lIIlIlII[26]]).append(lIlIllIIlIIlIll.x2).append(lIIIIIlI[lIIlIlII[27]]).append(lIlIllIIlIIlIll.y2).append(lIIIIIlI[lIIlIlII[28]]).append(lIlIllIIlIIlIll.z2));
    }

    public Cuboid clone() {
        Cuboid lIlIllIIlIIlllI;
        return new Cuboid(lIlIllIIlIIlllI);
    }

    public int getUpperY() {
        Cuboid lIlIlllIIIlllll;
        return lIlIlllIIIlllll.y2;
    }

    public Map<String, Object> serialize() {
        Cuboid lIlIlllIlIllIII;
        HashMap<String, Object> lIlIlllIlIllIIl = new HashMap<String, Object>();
        "".length();
        lIlIlllIlIllIIl.put(lIIIIIlI[lIIlIlII[8]], lIlIlllIlIllIII.worldName);
        "".length();
        lIlIlllIlIllIIl.put(lIIIIIlI[lIIlIlII[9]], lIlIlllIlIllIII.x1);
        "".length();
        lIlIlllIlIllIIl.put(lIIIIIlI[lIIlIlII[10]], lIlIlllIlIllIII.y1);
        "".length();
        lIlIlllIlIllIIl.put(lIIIIIlI[lIIlIlII[11]], lIlIlllIlIllIII.z1);
        "".length();
        lIlIlllIlIllIIl.put(lIIIIIlI[lIIlIlII[12]], lIlIlllIlIllIII.x2);
        "".length();
        lIlIlllIlIllIIl.put(lIIIIIlI[lIIlIlII[13]], lIlIlllIlIllIII.y2);
        "".length();
        lIlIlllIlIllIIl.put(lIIIIIlI[lIIlIlII[14]], lIlIlllIlIllIII.z2);
        return lIlIlllIlIllIIl;
    }

    public int getUpperX() {
        Cuboid lIlIlllIIlIIIIl;
        return lIlIlllIIlIIIIl.x2;
    }

    public boolean contains(Block lIlIllIllIllIII) {
        Cuboid lIlIllIllIllIIl;
        return lIlIllIllIllIIl.contains(lIlIllIllIllIII.getLocation());
    }

    public Cuboid inset(CuboidDirection lIlIllIlllIlIIl, int lIlIllIlllIlIll) {
        Cuboid lIlIllIlllIllIl;
        return lIlIllIlllIllIl.outset(lIlIllIlllIlIIl, -lIlIllIlllIlIll);
    }

    private static String llllIIIIl(String lIlIllIIIlllIII, String lIlIllIIIllIlll) {
        lIlIllIIIlllIII = new String(Base64.getDecoder().decode(lIlIllIIIlllIII.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder lIlIllIIIlllIll = new StringBuilder();
        char[] lIlIllIIIlllIlI = lIlIllIIIllIlll.toCharArray();
        int lIlIllIIIlllIIl = lIIlIlII[0];
        char[] lIlIllIIIllIIll = lIlIllIIIlllIII.toCharArray();
        int lIlIllIIIllIIlI = lIlIllIIIllIIll.length;
        int lIlIllIIIllIIIl = lIIlIlII[0];
        while (Cuboid.lIIIllIlIl(lIlIllIIIllIIIl, lIlIllIIIllIIlI)) {
            char lIlIllIIIlllllI = lIlIllIIIllIIll[lIlIllIIIllIIIl];
            "".length();
            lIlIllIIIlllIll.append((char)(lIlIllIIIlllllI ^ lIlIllIIIlllIlI[lIlIllIIIlllIIl % lIlIllIIIlllIlI.length]));
            ++lIlIllIIIlllIIl;
            ++lIlIllIIIllIIIl;
            "".length();
            if ((206 ^ 143 ^ (220 ^ 153)) >= "  ".length()) continue;
            return null;
        }
        return String.valueOf(lIlIllIIIlllIll);
    }

    public boolean contains(Location lIlIllIllIlIIII) {
        Cuboid lIlIllIllIlIIll;
        if (Cuboid.lIIIlIIlll((int)lIlIllIllIlIIll.worldName.equals(lIlIllIllIlIIII.getWorld().getName()))) {
            return lIIlIlII[0];
        }
        return lIlIllIllIlIIll.contains(lIlIllIllIlIIII.getBlockX(), lIlIllIllIlIIII.getBlockY(), lIlIllIllIlIIII.getBlockZ());
    }

    private static boolean lIIIllIIlI(int n, int n2) {
        return n <= n2;
    }

    public class CuboidIterator
    implements Iterator<Block> {
        private /* synthetic */ int y;
        private /* synthetic */ int sizeX;
        private /* synthetic */ World w;
        private /* synthetic */ int z;
        private /* synthetic */ int baseX;
        private /* synthetic */ int x;
        private /* synthetic */ int baseZ;
        private /* synthetic */ int sizeY;
        private static final /* synthetic */ int[] lIllIll;
        private /* synthetic */ int sizeZ;
        private /* synthetic */ int baseY;

        public CuboidIterator(World llIIlllIlllIIlI, int llIIlllIlllIIIl, int llIIlllIllllIIl, int llIIlllIllllIII, int llIIlllIlllIlll, int llIIlllIlllIllI, int llIIlllIllIllII) {
            CuboidIterator llIIlllIlllIlII;
            llIIlllIlllIlII.w = llIIlllIlllIIlI;
            llIIlllIlllIlII.baseX = llIIlllIlllIIIl;
            llIIlllIlllIlII.baseY = llIIlllIllllIIl;
            llIIlllIlllIlII.baseZ = llIIlllIllllIII;
            llIIlllIlllIlII.sizeX = Math.abs(llIIlllIlllIlll - llIIlllIlllIIIl) + lIllIll[0];
            llIIlllIlllIlII.sizeY = Math.abs(llIIlllIlllIllI - llIIlllIllllIIl) + lIllIll[0];
            llIIlllIlllIlII.sizeZ = Math.abs(llIIlllIllIllII - llIIlllIllllIII) + lIllIll[0];
            llIIlllIlllIlII.y = llIIlllIlllIlII.z = lIllIll[1];
            llIIlllIlllIlII.x = llIIlllIlllIlII.z;
        }

        private static boolean lIlIIlIII(int n, int n2) {
            return n < n2;
        }

        private static void lIlIIIlll() {
            lIllIll = new int[2];
            CuboidIterator.lIllIll[0] = " ".length();
            CuboidIterator.lIllIll[1] = (22 ^ 78) & ~(103 ^ 63);
        }

        @Override
        public void remove() {
        }

        static {
            CuboidIterator.lIlIIIlll();
        }

        private static boolean lIlIIlIIl(int n, int n2) {
            return n >= n2;
        }

        @Override
        public boolean hasNext() {
            CuboidIterator llIIlllIllIlIlI;
            int n;
            if (CuboidIterator.lIlIIlIII(llIIlllIllIlIlI.x, llIIlllIllIlIlI.sizeX) && CuboidIterator.lIlIIlIII(llIIlllIllIlIlI.y, llIIlllIllIlIlI.sizeY) && CuboidIterator.lIlIIlIII(llIIlllIllIlIlI.z, llIIlllIllIlIlI.sizeZ)) {
                n = lIllIll[0];
                "".length();
                if (-" ".length() >= "  ".length()) {
                    return (boolean)((187 ^ 152) & ~(165 ^ 134));
                }
            } else {
                n = lIllIll[1];
            }
            return (boolean)n;
        }

        @Override
        public Block next() {
            CuboidIterator llIIlllIllIIllI;
            Block llIIlllIllIIlIl = llIIlllIllIIllI.w.getBlockAt(llIIlllIllIIllI.baseX + llIIlllIllIIllI.x, llIIlllIllIIllI.baseY + llIIlllIllIIllI.y, llIIlllIllIIllI.baseZ + llIIlllIllIIllI.z);
            if (CuboidIterator.lIlIIlIIl(llIIlllIllIIllI.x += lIllIll[0], llIIlllIllIIllI.sizeX)) {
                llIIlllIllIIllI.x = lIllIll[1];
                if (CuboidIterator.lIlIIlIIl(llIIlllIllIIllI.y += lIllIll[0], llIIlllIllIIllI.sizeY)) {
                    llIIlllIllIIllI.y = lIllIll[1];
                    llIIlllIllIIllI.z += lIllIll[0];
                }
            }
            return llIIlllIllIIlIl;
        }
    }

    public static final class CuboidDirection
    extends Enum<CuboidDirection> {
        public static final /* synthetic */ /* enum */ CuboidDirection North;
        public static final /* synthetic */ /* enum */ CuboidDirection Up;
        public static final /* synthetic */ /* enum */ CuboidDirection East;
        private static final /* synthetic */ String[] lIlIIllI;
        private static final /* synthetic */ int[] lIllIIIl;
        public static final /* synthetic */ /* enum */ CuboidDirection Horizontal;
        public static final /* synthetic */ /* enum */ CuboidDirection South;
        public static final /* synthetic */ /* enum */ CuboidDirection Down;
        private static final /* synthetic */ CuboidDirection[] $VALUES;
        public static final /* synthetic */ /* enum */ CuboidDirection West;
        public static final /* synthetic */ /* enum */ CuboidDirection Vertical;
        public static final /* synthetic */ /* enum */ CuboidDirection Unknown;
        public static final /* synthetic */ /* enum */ CuboidDirection Both;

        static {
            CuboidDirection.lIlIlllIIl();
            CuboidDirection.lIlIIlIIIl();
            North = new CuboidDirection();
            East = new CuboidDirection();
            South = new CuboidDirection();
            West = new CuboidDirection();
            Up = new CuboidDirection();
            Down = new CuboidDirection();
            Horizontal = new CuboidDirection();
            Vertical = new CuboidDirection();
            Both = new CuboidDirection();
            Unknown = new CuboidDirection();
            CuboidDirection[] arrcuboidDirection = new CuboidDirection[lIllIIIl[10]];
            arrcuboidDirection[CuboidDirection.lIllIIIl[0]] = North;
            arrcuboidDirection[CuboidDirection.lIllIIIl[1]] = East;
            arrcuboidDirection[CuboidDirection.lIllIIIl[2]] = South;
            arrcuboidDirection[CuboidDirection.lIllIIIl[3]] = West;
            arrcuboidDirection[CuboidDirection.lIllIIIl[4]] = Up;
            arrcuboidDirection[CuboidDirection.lIllIIIl[5]] = Down;
            arrcuboidDirection[CuboidDirection.lIllIIIl[6]] = Horizontal;
            arrcuboidDirection[CuboidDirection.lIllIIIl[7]] = Vertical;
            arrcuboidDirection[CuboidDirection.lIllIIIl[8]] = Both;
            arrcuboidDirection[CuboidDirection.lIllIIIl[9]] = Unknown;
            $VALUES = arrcuboidDirection;
        }

        public static CuboidDirection[] values() {
            return (CuboidDirection[])$VALUES.clone();
        }

        private static String lIlIIlIIII(String lIIlllIIlIIIIIl, String lIIlllIIIlllllI) {
            try {
                SecretKeySpec lIIlllIIlIIIlII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIIlllIIIlllllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                Cipher lIIlllIIlIIIIll = Cipher.getInstance("Blowfish");
                lIIlllIIlIIIIll.init(lIllIIIl[2], lIIlllIIlIIIlII);
                return new String(lIIlllIIlIIIIll.doFinal(Base64.getDecoder().decode(lIIlllIIlIIIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIlllIIlIIIIlI) {
                lIIlllIIlIIIIlI.printStackTrace();
                return null;
            }
        }

        public CuboidDirection opposite() {
            CuboidDirection lIIlllIIllIIIIl;
            switch (1.$SwitchMap$com$biel$lobby$utilities$Cuboid$CuboidDirection[lIIlllIIllIIIIl.ordinal()]) {
                case 1: {
                    return South;
                }
                case 3: {
                    return West;
                }
                case 2: {
                    return North;
                }
                case 4: {
                    return East;
                }
                case 7: {
                    return Vertical;
                }
                case 8: {
                    return Horizontal;
                }
                case 6: {
                    return Down;
                }
                case 5: {
                    return Up;
                }
                case 9: {
                    return Both;
                }
            }
            return Unknown;
        }

        private static void lIlIIlIIIl() {
            lIlIIllI = new String[lIllIIIl[10]];
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[0]] = CuboidDirection.lIlIIIllII("AAEXAC0=", "NnetE");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[1]] = CuboidDirection.lIlIIIllII("AiYpFQ==", "GGZaE");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[2]] = CuboidDirection.lIlIIIllII("ECIEOTo=", "CMqMR");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[3]] = CuboidDirection.lIlIIIlllI("kb59XKhslIw=", "chzXz");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[4]] = CuboidDirection.lIlIIlIIII("/q5Xj/18WOI=", "hvgSA");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[5]] = CuboidDirection.lIlIIIllII("PBcZAg==", "xxnlp");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[6]] = CuboidDirection.lIlIIIllII("BBokKDcjGyIgIQ==", "LuVAM");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[7]] = CuboidDirection.lIlIIIllII("IBMDNSgVFx0=", "vvqAA");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[8]] = CuboidDirection.lIlIIIlllI("sIrEiOQmUMA=", "wWPEF");
            CuboidDirection.lIlIIllI[CuboidDirection.lIllIIIl[9]] = CuboidDirection.lIlIIlIIII("ammjyJxV3lc=", "vUZbN");
        }

        public static CuboidDirection valueOf(String lIIlllIIllIlIll) {
            return Enum.valueOf(CuboidDirection.class, lIIlllIIllIlIll);
        }

        private static String lIlIIIlllI(String lIIlllIIIllIlII, String lIIlllIIIllIIll) {
            try {
                SecretKeySpec lIIlllIIIllIlll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIIlllIIIllIIll.getBytes(StandardCharsets.UTF_8)), lIllIIIl[8]), "DES");
                Cipher lIIlllIIIllIllI = Cipher.getInstance("DES");
                lIIlllIIIllIllI.init(lIllIIIl[2], lIIlllIIIllIlll);
                return new String(lIIlllIIIllIllI.doFinal(Base64.getDecoder().decode(lIIlllIIIllIlII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception lIIlllIIIllIlIl) {
                lIIlllIIIllIlIl.printStackTrace();
                return null;
            }
        }

        private static String lIlIIIllII(String lIIlllIIlIlIllI, String lIIlllIIlIlIIII) {
            lIIlllIIlIlIllI = new String(Base64.getDecoder().decode(lIIlllIIlIlIllI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            StringBuilder lIIlllIIlIlIlII = new StringBuilder();
            char[] lIIlllIIlIlIIll = lIIlllIIlIlIIII.toCharArray();
            int lIIlllIIlIlIIlI = lIllIIIl[0];
            char[] lIIlllIIlIIllII = lIIlllIIlIlIllI.toCharArray();
            int lIIlllIIlIIlIll = lIIlllIIlIIllII.length;
            int lIIlllIIlIIlIlI = lIllIIIl[0];
            while (CuboidDirection.lIlIlllIlI(lIIlllIIlIIlIlI, lIIlllIIlIIlIll)) {
                char lIIlllIIlIlIlll = lIIlllIIlIIllII[lIIlllIIlIIlIlI];
                "".length();
                lIIlllIIlIlIlII.append((char)(lIIlllIIlIlIlll ^ lIIlllIIlIlIIll[lIIlllIIlIlIIlI % lIIlllIIlIlIIll.length]));
                ++lIIlllIIlIlIIlI;
                ++lIIlllIIlIIlIlI;
                "".length();
                if (-" ".length() <= 0) continue;
                return null;
            }
            return String.valueOf(lIIlllIIlIlIlII);
        }

        private static boolean lIlIlllIlI(int n, int n2) {
            return n < n2;
        }

        private static void lIlIlllIIl() {
            lIllIIIl = new int[11];
            CuboidDirection.lIllIIIl[0] = (100 ^ 41 ^ (97 ^ 112)) & (39 ^ 66 ^ (188 ^ 133) ^ -" ".length());
            CuboidDirection.lIllIIIl[1] = " ".length();
            CuboidDirection.lIllIIIl[2] = "  ".length();
            CuboidDirection.lIllIIIl[3] = "   ".length();
            CuboidDirection.lIllIIIl[4] = 10 ^ 14;
            CuboidDirection.lIllIIIl[5] = 180 ^ 177;
            CuboidDirection.lIllIIIl[6] = 57 ^ 63;
            CuboidDirection.lIllIIIl[7] = 186 ^ 189;
            CuboidDirection.lIllIIIl[8] = 26 ^ 33 ^ (83 ^ 96);
            CuboidDirection.lIllIIIl[9] = 148 ^ 157;
            CuboidDirection.lIllIIIl[10] = 51 ^ 57;
        }

        private CuboidDirection() {
            CuboidDirection lIIlllIIllIIllI;
        }
    }

}

