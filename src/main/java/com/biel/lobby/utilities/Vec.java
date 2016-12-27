package com.biel.lobby.utilities;

public class Vec {
	public final static Vec X_AXIS=new Vec(1,0,0);
	public final static Vec Y_AXIS=new Vec(0,1,0);
	public final static Vec Z_AXIS=new Vec(0,0,1);
	public final static Vec ORIGIN=new Vec(0,0,0);

	public final double x,y,z;

	public Vec(org.bukkit.util.Vector v){
		this.x=v.getX();
		this.y=v.getY();
		this.z=v.getZ();
	}
	public Vec(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public org.bukkit.util.Vector getBukkitVector(){
		return new org.bukkit.util.Vector(x, y, z);
	}
	public double dotProduct(Vec v) {
		return x*v.x+y*v.y+z*v.z;
	}

	public Vec crossProduct(Vec v) {
		double rx=y*v.z-z*v.y;
		double ry=z*v.x-x*v.z;
		double rz=x*v.y-y*v.x;
		return new Vec(rx, ry, rz);
	}

	public Vec newLength(double newLength) {
		double length=getLength();
		if(length==newLength) return this;
		if(length==0) return X_AXIS.newLength(newLength);
		return new Vec(x*newLength/length, y*newLength/length, z*newLength/length);
	}


	public Vec rotationAxis() {
		return rotationAxis(X_AXIS);
	}


	//The rotation axis to rotate v onto this
	public Vec rotationAxis(Vec v) {
		return normalizedCrossProduct(v);
	}

	public Vec normalizedCrossProduct(Vec v) {
		Vec r=crossProduct(v);
		if(r.getLength()<0.0001) {
			r=crossProduct(X_AXIS);
		}
		if(r.getLength()<0.0001) {
			r=crossProduct(Y_AXIS);
		}
		if(r.getLength()<0.0001) {
			return X_AXIS;
		}
		return r.normalize();
	}

	public double angle() {
		return angleTo(X_AXIS);
	}

	public double angleTo(Vec v) {
		double cosTheta=dotProduct(v)/(getLength()*v.getLength());

		return Math.acos(cosTheta);
	}

	public double getLength() {
		return Math.sqrt(x*x+y*y+z*z);
	}        


	public Vec rotate(Matrix m) {
		return m.mul(this);
	}

	public Vec normalize() {
		double length=getLength();

		if(length==1) return this;
		if(length==0) return X_AXIS;
		return new Vec(x/length, y/length, z/length);
	}

	public Vec addX(double a) {
		return new Vec(x+a, y, z);
	}

	public Vec addY(double a) {
		return new Vec(x, y+a, z);
	}

	public Vec addZ(double a) {
		return new Vec(x, y, z+a);
	}

	public Vec add(Vec v) {
		return new Vec(x+v.x, y+v.y, z+v.z);
	}

	public Vec sub(Vec v) {
		return new Vec(x-v.x, y-v.y, z-v.z);
	}

	public Vec mul(double m) {
		return new Vec(x*m, y*m, z*m);
	}

	public Vec div(double d) {
		return new Vec(x/d, y/d, z/d);
	}

	public Vec neg() {
		return new Vec(-x, -y, -z);
	}

	public Vec translate(double x, double y, double z) {
		return new Vec(this.x-x, this.y-y, this.z-z);
	}

	public Vec translate(Vec v) {
		return sub(v);
	}

	public Vec translateBack(Vec v) {
		return add(v);
	}

	@Override
	public boolean equals(Object o) {
		Vec v=(Vec)o;
		return v==this || this.sub(v).getLength() == 0;
	}

	public String toString() {
		return String.format(java.util.Locale.ENGLISH,
				"(%.3f, %.3f, %.3f)", x, y, z);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

}
