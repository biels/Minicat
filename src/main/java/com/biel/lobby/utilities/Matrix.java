/*
 * Decompiled with CFR 0.139.
 */
package com.biel.lobby.utilities;

import com.biel.lobby.utilities.Vec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

public class Matrix {
    public final /* synthetic */ double m13;
    public final /* synthetic */ double m33;
    public final /* synthetic */ double m21;
    public final /* synthetic */ double m32;
    public static final /* synthetic */ Matrix IDENTITY;
    public final /* synthetic */ double m23;
    public final /* synthetic */ double m31;
    public final /* synthetic */ double m22;
    private static final /* synthetic */ int[] llIIIIl;
    public final /* synthetic */ double m12;
    public final /* synthetic */ double m11;
    private static final /* synthetic */ String[] lIlIIll;

    private static boolean lIllIIIll(int n, int n2) {
        return n < n2;
    }

    public Matrix normalize() {
        Matrix llIIlIllIIIIlll;
        Vec llIIlIllIIIIllI = new Vec(llIIlIllIIIIlll.m11, llIIlIllIIIIlll.m21, llIIlIllIIIIlll.m31);
        Vec llIIlIllIIIIlIl = new Vec(llIIlIllIIIIlll.m12, llIIlIllIIIIlll.m22, llIIlIllIIIIlll.m32);
        Vec llIIlIllIIIIlII = new Vec(llIIlIllIIIIlll.m13, llIIlIllIIIIlll.m23, llIIlIllIIIIlll.m33);
        llIIlIllIIIIlII = llIIlIllIIIIllI.crossProduct(llIIlIllIIIIlIl);
        llIIlIllIIIIlIl = llIIlIllIIIIlII.crossProduct(llIIlIllIIIIllI);
        llIIlIllIIIIllI = llIIlIllIIIIllI.normalize();
        llIIlIllIIIIlIl = llIIlIllIIIIlIl.normalize();
        llIIlIllIIIIlII = llIIlIllIIIIlII.normalize();
        return new Matrix(llIIlIllIIIIllI, llIIlIllIIIIlIl, llIIlIllIIIIlII);
    }

    private static void lIIllIIlI() {
        lIlIIll = new String[llIIIIl[2]];
        Matrix.lIlIIll[Matrix.llIIIIl[1]] = Matrix.lIIllIIIl("MVV7aQRGUHB0UQxcdX9MWRYIUDlPXmY8TkpVe2kERlBwdFEMLV8BR0RDM3ZCT15mPE5KVXtpBDc=", "jpUZb");
    }

    public Matrix(Vec llIIlIllllllIll, Vec llIIlIllllllIlI, Vec llIIlIllllllIIl) {
        Matrix llIIlIllllllIII;
        double[] arrd = new double[llIIIIl[0]];
        arrd[Matrix.llIIIIl[1]] = llIIlIllllllIll.x;
        arrd[Matrix.llIIIIl[2]] = llIIlIllllllIlI.x;
        arrd[Matrix.llIIIIl[3]] = llIIlIllllllIIl.x;
        arrd[Matrix.llIIIIl[4]] = llIIlIllllllIll.y;
        arrd[Matrix.llIIIIl[5]] = llIIlIllllllIlI.y;
        arrd[Matrix.llIIIIl[6]] = llIIlIllllllIIl.y;
        arrd[Matrix.llIIIIl[7]] = llIIlIllllllIll.z;
        arrd[Matrix.llIIIIl[8]] = llIIlIllllllIlI.z;
        arrd[Matrix.llIIIIl[9]] = llIIlIllllllIIl.z;
        llIIlIllllllIII(arrd);
    }

    public Matrix transpose() {
        Matrix llIIlIlIlllllIl;
        double[] arrd = new double[llIIIIl[0]];
        arrd[Matrix.llIIIIl[1]] = llIIlIlIlllllIl.m11;
        arrd[Matrix.llIIIIl[2]] = llIIlIlIlllllIl.m21;
        arrd[Matrix.llIIIIl[3]] = llIIlIlIlllllIl.m31;
        arrd[Matrix.llIIIIl[4]] = llIIlIlIlllllIl.m12;
        arrd[Matrix.llIIIIl[5]] = llIIlIlIlllllIl.m22;
        arrd[Matrix.llIIIIl[6]] = llIIlIlIlllllIl.m32;
        arrd[Matrix.llIIIIl[7]] = llIIlIlIlllllIl.m13;
        arrd[Matrix.llIIIIl[8]] = llIIlIlIlllllIl.m23;
        arrd[Matrix.llIIIIl[9]] = llIIlIlIlllllIl.m33;
        return new Matrix(arrd);
    }

    public Matrix oppositeRotMatrix() {
        Matrix llIIlIlIllllIll;
        return llIIlIlIllllIll.transpose();
    }

    public /* varargs */ Matrix(double ... llIIllIIIIIIIll) {
        Matrix llIIllIIIIIIIlI;
        if (Matrix.lIllIIIII(llIIllIIIIIIIll.length, llIIIIl[0])) {
            throw new RuntimeException();
        }
        llIIllIIIIIIIlI.m11 = llIIllIIIIIIIll[llIIIIl[1]];
        llIIllIIIIIIIlI.m12 = llIIllIIIIIIIll[llIIIIl[2]];
        llIIllIIIIIIIlI.m13 = llIIllIIIIIIIll[llIIIIl[3]];
        llIIllIIIIIIIlI.m21 = llIIllIIIIIIIll[llIIIIl[4]];
        llIIllIIIIIIIlI.m22 = llIIllIIIIIIIll[llIIIIl[5]];
        llIIllIIIIIIIlI.m23 = llIIllIIIIIIIll[llIIIIl[6]];
        llIIllIIIIIIIlI.m31 = llIIllIIIIIIIll[llIIIIl[7]];
        llIIllIIIIIIIlI.m32 = llIIllIIIIIIIll[llIIIIl[8]];
        llIIllIIIIIIIlI.m33 = llIIllIIIIIIIll[llIIIIl[9]];
    }

    private static String lIIllIIIl(String llIIlIlIllIIlll, String llIIlIlIllIIllI) {
        llIIlIlIllIIlll = new String(Base64.getDecoder().decode(llIIlIlIllIIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder llIIlIlIllIlIlI = new StringBuilder();
        char[] llIIlIlIllIlIIl = llIIlIlIllIIllI.toCharArray();
        int llIIlIlIllIlIII = llIIIIl[1];
        char[] llIIlIlIllIIIlI = llIIlIlIllIIlll.toCharArray();
        int llIIlIlIllIIIIl = llIIlIlIllIIIlI.length;
        int llIIlIlIllIIIII = llIIIIl[1];
        while (Matrix.lIllIIIll(llIIlIlIllIIIII, llIIlIlIllIIIIl)) {
            char llIIlIlIllIllIl = llIIlIlIllIIIlI[llIIlIlIllIIIII];
            "".length();
            llIIlIlIllIlIlI.append((char)(llIIlIlIllIllIl ^ llIIlIlIllIlIIl[llIIlIlIllIlIII % llIIlIlIllIlIIl.length]));
            ++llIIlIlIllIlIII;
            ++llIIlIlIllIIIII;
            "".length();
            if ((37 ^ 61 ^ (49 ^ 45)) > -" ".length()) continue;
            return null;
        }
        return String.valueOf(llIIlIlIllIlIlI);
    }

    public Matrix rotateAbs(Matrix llIIlIllIIIlllI) {
        Matrix llIIlIllIIIllll;
        return llIIlIllIIIlllI.mul(llIIlIllIIIllll);
    }

    public Vec mul(Vec llIIlIllIllllII) {
        Matrix llIIlIllIllllIl;
        double llIIlIlllIIIIII = llIIlIllIllllIl.m11 * llIIlIllIllllII.x + llIIlIllIllllIl.m12 * llIIlIllIllllII.y + llIIlIllIllllIl.m13 * llIIlIllIllllII.z;
        double llIIlIllIllllll = llIIlIllIllllIl.m21 * llIIlIllIllllII.x + llIIlIllIllllIl.m22 * llIIlIllIllllII.y + llIIlIllIllllIl.m23 * llIIlIllIllllII.z;
        double llIIlIllIlllllI = llIIlIllIllllIl.m31 * llIIlIllIllllII.x + llIIlIllIllllIl.m32 * llIIlIllIllllII.y + llIIlIllIllllIl.m33 * llIIlIllIllllII.z;
        return new Vec(llIIlIlllIIIIII, llIIlIllIllllll, llIIlIllIlllllI);
    }

    public Matrix(Vec llIIlIllllIIlII, double llIIlIllllIIIll) {
        Matrix llIIlIllllIIlIl;
        Vec llIIlIllllIIIlI = llIIlIllllIIlII.normalize();
        double llIIlIllllIIIIl = Math.sin(llIIlIllllIIIll);
        double llIIlIllllIIIII = Math.cos(llIIlIllllIIIll);
        double llIIlIlllIlllll = llIIlIllllIIIlI.x * llIIlIllllIIIlI.y * (1.0 - llIIlIllllIIIII);
        double llIIlIlllIllllI = llIIlIllllIIIlI.y * llIIlIllllIIIlI.z * (1.0 - llIIlIllllIIIII);
        double llIIlIlllIlllIl = llIIlIllllIIIlI.x * llIIlIllllIIIlI.z * (1.0 - llIIlIllllIIIII);
        double llIIlIlllIlllII = llIIlIllllIIIlI.x * llIIlIllllIIIlI.x * (1.0 - llIIlIllllIIIII);
        double llIIlIlllIllIll = llIIlIllllIIIlI.y * llIIlIllllIIIlI.y * (1.0 - llIIlIllllIIIII);
        double llIIlIlllIllIlI = llIIlIllllIIIlI.z * llIIlIllllIIIlI.z * (1.0 - llIIlIllllIIIII);
        double llIIlIlllIllIIl = llIIlIllllIIIlI.x * llIIlIllllIIIIl;
        double llIIlIlllIllIII = llIIlIllllIIIlI.y * llIIlIllllIIIIl;
        double llIIlIlllIlIlll = llIIlIllllIIIlI.z * llIIlIllllIIIIl;
        llIIlIllllIIlIl.m11 = llIIlIllllIIIII + llIIlIlllIlllII;
        llIIlIllllIIlIl.m12 = llIIlIlllIlllll - llIIlIlllIlIlll;
        llIIlIllllIIlIl.m13 = llIIlIlllIlllIl + llIIlIlllIllIII;
        llIIlIllllIIlIl.m21 = llIIlIlllIlllll + llIIlIlllIlIlll;
        llIIlIllllIIlIl.m22 = llIIlIllllIIIII + llIIlIlllIllIll;
        llIIlIllllIIlIl.m23 = llIIlIlllIllllI - llIIlIlllIllIIl;
        llIIlIllllIIlIl.m31 = llIIlIlllIlllIl - llIIlIlllIllIII;
        llIIlIllllIIlIl.m32 = llIIlIlllIllllI + llIIlIlllIllIIl;
        llIIlIllllIIlIl.m33 = llIIlIllllIIIII + llIIlIlllIllIlI;
    }

    private static boolean lIllIIIII(int n, int n2) {
        return n != n2;
    }

    public Matrix rotateRel(Matrix llIIlIllIIlIlII) {
        Matrix llIIlIllIIlIlIl;
        return llIIlIllIIlIlIl.mul(llIIlIllIIlIlII);
    }

    public String toString() {
        Matrix llIIlIlIllllIII;
        Object[] arrobject = new Object[llIIIIl[0]];
        arrobject[Matrix.llIIIIl[1]] = llIIlIlIllllIII.m11;
        arrobject[Matrix.llIIIIl[2]] = llIIlIlIllllIII.m12;
        arrobject[Matrix.llIIIIl[3]] = llIIlIlIllllIII.m13;
        arrobject[Matrix.llIIIIl[4]] = llIIlIlIllllIII.m21;
        arrobject[Matrix.llIIIIl[5]] = llIIlIlIllllIII.m22;
        arrobject[Matrix.llIIIIl[6]] = llIIlIlIllllIII.m23;
        arrobject[Matrix.llIIIIl[7]] = llIIlIlIllllIII.m31;
        arrobject[Matrix.llIIIIl[8]] = llIIlIlIllllIII.m32;
        arrobject[Matrix.llIIIIl[9]] = llIIlIlIllllIII.m33;
        return String.format(Locale.ENGLISH, lIlIIll[llIIIIl[1]], arrobject);
    }

    static {
        Matrix.lIlIlllll();
        Matrix.lIIllIIlI();
        IDENTITY = new Matrix(Vec.X_AXIS, Vec.Y_AXIS, Vec.Z_AXIS);
    }

    private Matrix mul(Matrix llIIlIllIlIIIII) {
        Matrix llIIlIllIlIIIlI;
        double llIIlIllIlIlIll = llIIlIllIlIIIlI.m11 * llIIlIllIlIIIII.m11 + llIIlIllIlIIIlI.m12 * llIIlIllIlIIIII.m21 + llIIlIllIlIIIlI.m13 * llIIlIllIlIIIII.m31;
        double llIIlIllIlIlIlI = llIIlIllIlIIIlI.m11 * llIIlIllIlIIIII.m12 + llIIlIllIlIIIlI.m12 * llIIlIllIlIIIII.m22 + llIIlIllIlIIIlI.m13 * llIIlIllIlIIIII.m32;
        double llIIlIllIlIlIIl = llIIlIllIlIIIlI.m11 * llIIlIllIlIIIII.m13 + llIIlIllIlIIIlI.m12 * llIIlIllIlIIIII.m23 + llIIlIllIlIIIlI.m13 * llIIlIllIlIIIII.m33;
        double llIIlIllIlIlIII = llIIlIllIlIIIlI.m21 * llIIlIllIlIIIII.m11 + llIIlIllIlIIIlI.m22 * llIIlIllIlIIIII.m21 + llIIlIllIlIIIlI.m23 * llIIlIllIlIIIII.m31;
        double llIIlIllIlIIlll = llIIlIllIlIIIlI.m21 * llIIlIllIlIIIII.m12 + llIIlIllIlIIIlI.m22 * llIIlIllIlIIIII.m22 + llIIlIllIlIIIlI.m23 * llIIlIllIlIIIII.m32;
        double llIIlIllIlIIllI = llIIlIllIlIIIlI.m21 * llIIlIllIlIIIII.m13 + llIIlIllIlIIIlI.m22 * llIIlIllIlIIIII.m23 + llIIlIllIlIIIlI.m23 * llIIlIllIlIIIII.m33;
        double llIIlIllIlIIlIl = llIIlIllIlIIIlI.m31 * llIIlIllIlIIIII.m11 + llIIlIllIlIIIlI.m32 * llIIlIllIlIIIII.m21 + llIIlIllIlIIIlI.m33 * llIIlIllIlIIIII.m31;
        double llIIlIllIlIIlII = llIIlIllIlIIIlI.m31 * llIIlIllIlIIIII.m12 + llIIlIllIlIIIlI.m32 * llIIlIllIlIIIII.m22 + llIIlIllIlIIIlI.m33 * llIIlIllIlIIIII.m32;
        double llIIlIllIlIIIll = llIIlIllIlIIIlI.m31 * llIIlIllIlIIIII.m13 + llIIlIllIlIIIlI.m32 * llIIlIllIlIIIII.m23 + llIIlIllIlIIIlI.m33 * llIIlIllIlIIIII.m33;
        double[] arrd = new double[llIIIIl[0]];
        arrd[Matrix.llIIIIl[1]] = llIIlIllIlIlIll;
        arrd[Matrix.llIIIIl[2]] = llIIlIllIlIlIlI;
        arrd[Matrix.llIIIIl[3]] = llIIlIllIlIlIIl;
        arrd[Matrix.llIIIIl[4]] = llIIlIllIlIlIII;
        arrd[Matrix.llIIIIl[5]] = llIIlIllIlIIlll;
        arrd[Matrix.llIIIIl[6]] = llIIlIllIlIIllI;
        arrd[Matrix.llIIIIl[7]] = llIIlIllIlIIlIl;
        arrd[Matrix.llIIIIl[8]] = llIIlIllIlIIlII;
        arrd[Matrix.llIIIIl[9]] = llIIlIllIlIIIll;
        return new Matrix(arrd);
    }

    private static void lIlIlllll() {
        llIIIIl = new int[10];
        Matrix.llIIIIl[0] = 85 + 135 - 45 + 15 ^ 63 + 132 - 15 + 3;
        Matrix.llIIIIl[1] = (122 ^ 111 ^ (49 ^ 63)) & (113 ^ 69 ^ (37 ^ 10) ^ -" ".length());
        Matrix.llIIIIl[2] = " ".length();
        Matrix.llIIIIl[3] = "  ".length();
        Matrix.llIIIIl[4] = "   ".length();
        Matrix.llIIIIl[5] = 99 ^ 103;
        Matrix.llIIIIl[6] = 48 ^ 31 ^ (115 ^ 89);
        Matrix.llIIIIl[7] = 137 ^ 143;
        Matrix.llIIIIl[8] = 105 ^ 110;
        Matrix.llIIIIl[9] = 204 ^ 196 ^ (50 ^ 45) & ~(12 ^ 19);
    }
}

