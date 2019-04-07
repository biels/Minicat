/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.bukkit.util.Vector
 */
package com.biel.lobby.utilities;

import com.biel.lobby.utilities.Matrix;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bukkit.util.Vector;

public class Vec {
    public static final /* synthetic */ Vec Y_AXIS;
    public final /* synthetic */ double x;
    public static final /* synthetic */ Vec Z_AXIS;
    private static final /* synthetic */ String[] llIIIl;
    private static final /* synthetic */ int[] llIlIl;
    public final /* synthetic */ double z;
    public static final /* synthetic */ Vec ORIGIN;
    public static final /* synthetic */ Vec X_AXIS;
    public final /* synthetic */ double y;

    private static void lIIlIlIl() {
        llIIIl = new String[llIlIl[0]];
        Vec.llIIIl[Vec.llIlIl[1]] = Vec.lIIlIlII("4zIk/AT0YXWld6bQJTGptdisQwjwlGp5", "pfEdO");
    }

    private static String lIIlIlII(String lllIlllIIIlllIl, String lllIlllIIIllllI) {
        try {
            SecretKeySpec lllIlllIIlIIIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIlllIIIllllI.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher lllIlllIIlIIIIl = Cipher.getInstance("Blowfish");
            lllIlllIIlIIIIl.init(llIlIl[3], lllIlllIIlIIIlI);
            return new String(lllIlllIIlIIIIl.doFinal(Base64.getDecoder().decode(lllIlllIIIlllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIlllIIlIIIII) {
            lllIlllIIlIIIII.printStackTrace();
            return null;
        }
    }

    private static boolean lIlIIIII(int n) {
        return n == 0;
    }

    public Vec rotationAxis() {
        Vec lllIllllIlIIlIl;
        return lllIllllIlIIlIl.rotationAxis(X_AXIS);
    }

    public Vec mul(double lllIlllIlIllIIl) {
        Vec lllIlllIlIllIlI;
        return new Vec(lllIlllIlIllIlI.x * lllIlllIlIllIIl, lllIlllIlIllIlI.y * lllIlllIlIllIIl, lllIlllIlIllIlI.z * lllIlllIlIllIIl);
    }

    public Vec div(double lllIlllIlIlIIIl) {
        Vec lllIlllIlIlIlII;
        return new Vec(lllIlllIlIlIlII.x / lllIlllIlIlIIIl, lllIlllIlIlIlII.y / lllIlllIlIlIIIl, lllIlllIlIlIlII.z / lllIlllIlIlIIIl);
    }

    public Vec crossProduct(Vec lllIllllIlllIIl) {
        Vec lllIllllIllIlIl;
        double lllIllllIlllIII = lllIllllIllIlIl.y * lllIllllIlllIIl.z - lllIllllIllIlIl.z * lllIllllIlllIIl.y;
        double lllIllllIllIlll = lllIllllIllIlIl.z * lllIllllIlllIIl.x - lllIllllIllIlIl.x * lllIllllIlllIIl.z;
        double lllIllllIllIllI = lllIllllIllIlIl.x * lllIllllIlllIIl.y - lllIllllIllIlIl.y * lllIllllIlllIIl.x;
        return new Vec(lllIllllIlllIII, lllIllllIllIlll, lllIllllIllIllI);
    }

    public Vec addY(double lllIlllIllIllll) {
        Vec lllIlllIlllIIlI;
        return new Vec(lllIlllIlllIIlI.x, lllIlllIlllIIlI.y + lllIlllIllIllll, lllIlllIlllIIlI.z);
    }

    public boolean equals(Object lllIlllIIlIlllI) {
        Vec lllIlllIIllIIlI;
        int n;
        Vec lllIlllIIllIIII = (Vec)lllIlllIIlIlllI;
        if (!Vec.lIlIIlIl(lllIlllIIllIIII, lllIlllIIllIIlI) || Vec.lIlIIIII(Vec.lIlIIlII(lllIlllIIllIIlI.sub(lllIlllIIllIIII).getLength(), 0.0))) {
            n = llIlIl[0];
            "".length();
            if ("   ".length() < 0) {
                return (boolean)((161 + 37 - 62 + 43 ^ 42 + 110 - 82 + 86) & ((12 ^ 61) & ~(39 ^ 22) ^ (98 ^ 77) ^ -" ".length()));
            }
        } else {
            n = llIlIl[1];
        }
        return (boolean)n;
    }

    public Vec(double lllIlllllIIlIll, double lllIlllllIIlllI, double lllIlllllIIllIl) {
        Vec lllIlllllIlIIII;
        lllIlllllIlIIII.x = lllIlllllIIlIll;
        lllIlllllIlIIII.y = lllIlllllIIlllI;
        lllIlllllIlIIII.z = lllIlllllIIllIl;
    }

    private static int lIlIIIll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public double angleTo(Vec lllIllllIIIlllI) {
        Vec lllIllllIIIllll;
        double lllIllllIIIllIl = lllIllllIIIllll.dotProduct(lllIllllIIIlllI) / (lllIllllIIIllll.getLength() * lllIllllIIIlllI.getLength());
        return Math.acos(lllIllllIIIllIl);
    }

    public Vec newLength(double lllIllllIlIllII) {
        Vec lllIllllIlIlIlI;
        double lllIllllIlIlIll = lllIllllIlIlIlI.getLength();
        if (Vec.lIlIIIII(Vec.lIIlllll(lllIllllIlIlIll, lllIllllIlIllII))) {
            return lllIllllIlIlIlI;
        }
        if (Vec.lIlIIIII(Vec.lIIlllll(lllIllllIlIlIll, 0.0))) {
            return X_AXIS.newLength(lllIllllIlIllII);
        }
        return new Vec(lllIllllIlIlIlI.x * lllIllllIlIllII / lllIllllIlIlIll, lllIllllIlIlIlI.y * lllIllllIlIllII / lllIllllIlIlIll, lllIllllIlIlIlI.z * lllIllllIlIllII / lllIllllIlIlIll);
    }

    public Vec sub(Vec lllIlllIlIlllIl) {
        Vec lllIlllIlIllllI;
        return new Vec(lllIlllIlIllllI.x - lllIlllIlIlllIl.x, lllIlllIlIllllI.y - lllIlllIlIlllIl.y, lllIlllIlIllllI.z - lllIlllIlIlllIl.z);
    }

    public Vec normalize() {
        Vec lllIlllIlllllII;
        double lllIlllIlllllIl = lllIlllIlllllII.getLength();
        if (Vec.lIlIIIII(Vec.lIlIIIll(lllIlllIlllllIl, 1.0))) {
            return lllIlllIlllllII;
        }
        if (Vec.lIlIIIII(Vec.lIlIIIll(lllIlllIlllllIl, 0.0))) {
            return X_AXIS;
        }
        return new Vec(lllIlllIlllllII.x / lllIlllIlllllIl, lllIlllIlllllII.y / lllIlllIlllllIl, lllIlllIlllllII.z / lllIlllIlllllIl);
    }

    public String toString() {
        Vec lllIlllIIlIlIlI;
        Object[] arrobject = new Object[llIlIl[2]];
        arrobject[Vec.llIlIl[1]] = lllIlllIIlIlIlI.x;
        arrobject[Vec.llIlIl[0]] = lllIlllIIlIlIlI.y;
        arrobject[Vec.llIlIl[3]] = lllIlllIIlIlIlI.z;
        return String.format(Locale.ENGLISH, llIIIl[llIlIl[1]], arrobject);
    }

    private static int lIlIIIIl(double d, double d2) {
        return (int)(d DCMPG d2);
    }

    public Vec(Vector lllIlllllIlIlIl) {
        Vec lllIlllllIllIII;
        lllIlllllIllIII.x = lllIlllllIlIlIl.getX();
        lllIlllllIllIII.y = lllIlllllIlIlIl.getY();
        lllIlllllIllIII.z = lllIlllllIlIlIl.getZ();
    }

    public Vec rotationAxis(Vec lllIllllIlIIIIl) {
        Vec lllIllllIlIIIII;
        return lllIllllIlIIIII.normalizedCrossProduct(lllIllllIlIIIIl);
    }

    public Vec translate(double lllIlllIlIIlIII, double lllIlllIlIIIlll, double lllIlllIlIIIllI) {
        Vec lllIlllIlIIlIIl;
        return new Vec(lllIlllIlIIlIIl.x - lllIlllIlIIlIII, lllIlllIlIIlIIl.y - lllIlllIlIIIlll, lllIlllIlIIlIIl.z - lllIlllIlIIIllI);
    }

    public Vec translateBack(Vec lllIlllIIllIllI) {
        Vec lllIlllIIlllIIl;
        return lllIlllIIlllIIl.add(lllIlllIIllIllI);
    }

    public Vec addZ(double lllIlllIllIlIll) {
        Vec lllIlllIllIlIlI;
        return new Vec(lllIlllIllIlIlI.x, lllIlllIllIlIlI.y, lllIlllIllIlIlI.z + lllIlllIllIlIll);
    }

    static {
        Vec.lIIllllI();
        Vec.lIIlIlIl();
        X_AXIS = new Vec(1.0, 0.0, 0.0);
        Y_AXIS = new Vec(0.0, 1.0, 0.0);
        Z_AXIS = new Vec(0.0, 0.0, 1.0);
        ORIGIN = new Vec(0.0, 0.0, 0.0);
    }

    private static int lIlIIlII(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    private static boolean lIlIIIlI(int n) {
        return n < 0;
    }

    public Vec rotate(Matrix lllIllllIIIIIIl) {
        Vec lllIllllIIIIlII;
        return lllIllllIIIIIIl.mul(lllIllllIIIIlII);
    }

    public Vec normalizedCrossProduct(Vec lllIllllIIllIlI) {
        Vec lllIllllIIllIll;
        Vec lllIllllIIllIIl = lllIllllIIllIll.crossProduct(lllIllllIIllIlI);
        if (Vec.lIlIIIlI(Vec.lIlIIIIl(lllIllllIIllIIl.getLength(), 1.0E-4))) {
            lllIllllIIllIIl = lllIllllIIllIll.crossProduct(X_AXIS);
        }
        if (Vec.lIlIIIlI(Vec.lIlIIIIl(lllIllllIIllIIl.getLength(), 1.0E-4))) {
            lllIllllIIllIIl = lllIllllIIllIll.crossProduct(Y_AXIS);
        }
        if (Vec.lIlIIIlI(Vec.lIlIIIIl(lllIllllIIllIIl.getLength(), 1.0E-4))) {
            return X_AXIS;
        }
        return lllIllllIIllIIl.normalize();
    }

    public double getLength() {
        Vec lllIllllIIIIlll;
        return Math.sqrt(lllIllllIIIIlll.x * lllIllllIIIIlll.x + lllIllllIIIIlll.y * lllIllllIIIIlll.y + lllIllllIIIIlll.z * lllIllllIIIIlll.z);
    }

    public Vec translate(Vec lllIlllIIllllII) {
        Vec lllIlllIIllllIl;
        return lllIlllIIllllIl.sub(lllIlllIIllllII);
    }

    public int hashCode() {
        Vec lllIlllIIlIIlll;
        return super.hashCode();
    }

    public Vector getBukkitVector() {
        Vec lllIlllllIIIlll;
        return new Vector(lllIlllllIIIlll.x, lllIlllllIIIlll.y, lllIlllllIIIlll.z);
    }

    public Vec add(Vec lllIlllIllIIIll) {
        Vec lllIlllIllIIllI;
        return new Vec(lllIlllIllIIllI.x + lllIlllIllIIIll.x, lllIlllIllIIllI.y + lllIlllIllIIIll.y, lllIlllIllIIllI.z + lllIlllIllIIIll.z);
    }

    public Vec neg() {
        Vec lllIlllIlIIllll;
        return new Vec(-lllIlllIlIIllll.x, -lllIlllIlIIllll.y, -lllIlllIlIIllll.z);
    }

    private static void lIIllllI() {
        llIlIl = new int[4];
        Vec.llIlIl[0] = " ".length();
        Vec.llIlIl[1] = (234 ^ 192 ^ (171 ^ 143)) & (59 ^ 124 ^ (104 ^ 33) ^ -" ".length());
        Vec.llIlIl[2] = "   ".length();
        Vec.llIlIl[3] = "  ".length();
    }

    public Vec addX(double lllIlllIlllIlll) {
        Vec lllIlllIllllIII;
        return new Vec(lllIlllIllllIII.x + lllIlllIlllIlll, lllIlllIllllIII.y, lllIlllIllllIII.z);
    }

    public double dotProduct(Vec lllIlllllIIIIlI) {
        Vec lllIlllllIIIIll;
        return lllIlllllIIIIll.x * lllIlllllIIIIlI.x + lllIlllllIIIIll.y * lllIlllllIIIIlI.y + lllIlllllIIIIll.z * lllIlllllIIIIlI.z;
    }

    private static int lIIlllll(double d, double d2) {
        return (int)(d DCMPL d2);
    }

    public double angle() {
        Vec lllIllllIIlIlII;
        return lllIllllIIlIlII.angleTo(X_AXIS);
    }

    private static boolean lIlIIlIl(Object object, Object object2) {
        return object != object2;
    }
}

