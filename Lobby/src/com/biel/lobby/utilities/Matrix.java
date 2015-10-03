package com.biel.lobby.utilities;

public class Matrix {
	   
    public final static Matrix IDENTITY=new Matrix(Vec.X_AXIS, Vec.Y_AXIS, Vec.Z_AXIS);
   
    public final double m11, m12, m13,
                        m21, m22, m23,
                        m31, m32, m33;
   
   
    public Matrix(double ... e) {
        if(e.length!=9) throw new RuntimeException();
        m11=e[0]; m12=e[1]; m13=e[2];
        m21=e[3]; m22=e[4]; m23=e[5];
        m31=e[6]; m32=e[7]; m33=e[8];
    }
   
    public Matrix(Vec vx, Vec vy, Vec vz) {
        this(vx.x, vy.x, vz.x,
             vx.y, vy.y, vz.y,
             vx.z, vy.z, vz.z);
    }
   
    public Matrix(Vec axis, double theta) {
        Vec u=axis.normalize();
        double sin=Math.sin(theta);
        double cos=Math.cos(theta);
        double uxy=u.x*u.y*(1-cos);
        double uyz=u.y*u.z*(1-cos);
        double uxz=u.x*u.z*(1-cos);
        double ux2=u.x*u.x*(1-cos);
        double uy2=u.y*u.y*(1-cos);
        double uz2=u.z*u.z*(1-cos);
        double uxsin=u.x*sin;
        double uysin=u.y*sin;
        double uzsin=u.z*sin;
       
       
        m11=cos+ux2;   m12=uxy-uzsin; m13=uxz+uysin;
        m21=uxy+uzsin; m22=cos+uy2;   m23=uyz-uxsin;
        m31=uxz-uysin; m32=uyz+uxsin; m33=cos+uz2;
    }
     
    public Vec mul(Vec v) {
        double rx = m11*v.x + m12*v.y + m13*v.z;
        double ry = m21*v.x + m22*v.y + m23*v.z;
        double rz = m31*v.x + m32*v.y + m33*v.z;
        return new Vec(rx, ry, rz);
    }
   
    private Matrix mul(Matrix m) {
       
        double r11 = m11*m.m11 + m12*m.m21 + m13*m.m31;
        double r12 = m11*m.m12 + m12*m.m22 + m13*m.m32;
        double r13 = m11*m.m13 + m12*m.m23 + m13*m.m33;
       
        double r21 = m21*m.m11 + m22*m.m21 + m23*m.m31;
        double r22 = m21*m.m12 + m22*m.m22 + m23*m.m32;
        double r23 = m21*m.m13 + m22*m.m23 + m23*m.m33;
       
        double r31 = m31*m.m11 + m32*m.m21 + m33*m.m31;
        double r32 = m31*m.m12 + m32*m.m22 + m33*m.m32;
        double r33 = m31*m.m13 + m32*m.m23 + m33*m.m33;
       
        return new Matrix(r11, r12, r13,
                          r21, r22, r23,
                          r31, r32, r33);
    }
   
    public Matrix rotateRel(Matrix m) {
        return mul(m);
    }
   
    public Matrix rotateAbs(Matrix m) {
        return m.mul(this);
    }
   
    public Matrix normalize() {
       
        Vec vx=new Vec(m11, m21, m31);
        Vec vy=new Vec(m12, m22, m32);
        Vec vz=new Vec(m13, m23, m33);
       
        vz=vx.crossProduct(vy);
        vy=vz.crossProduct(vx);
       
        vx=vx.normalize();
        vy=vy.normalize();
        vz=vz.normalize();
       
        return new Matrix(vx, vy, vz);
    }
   
    public Matrix transpose() {
        return new Matrix(m11, m21, m31,
                          m12, m22, m32,
                          m13, m23, m33);
    }
   
    public Matrix oppositeRotMatrix() {
        return transpose();
    }
   
   
    public String toString() {
        return String.format(java.util.Locale.ENGLISH,
            "[%.3f, %.3f, %.3f]\n[%.3f, %.3f, %.3f]\n[%.3f, %.3f, %.3f]",
            m11, m12, m13, m21, m22, m23, m31, m32, m33);
    }
   
}