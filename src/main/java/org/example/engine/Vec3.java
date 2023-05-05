package org.example.engine;

class Vec3 {

    private double vector[];

    public Vec3() {
        this.vector = new double[]{0, 0, 0};
    }

    public Vec3(double x, double y, double z) {
        this.vector = new double[]{x, y, z};
    }

    public Vec3(Vec3 v) {
        this.vector = new double[]{v.getX(), v.getY(), v.getZ()};
    }

    public Vec3(Vec3 v1, Vec3 v2) {
        this.vector = new double[]{v2.getX() - v1.getX(), v2.getY() - v1.getY(), v2.getZ() - v1.getZ()};
    }

    public void setX(double x) {
        this.vector[0] = x;
    }

    public void setY(double y) {
        this.vector[1] = y;
    }

    public void setZ(double z) {
        this.vector[2] = z;
    }

    public double getX() {
        return this.vector[0];
    }

    public double getY() {
        return this.vector[1];
    }

    public double getZ() {
        return this.vector[2];
    }

    public Vec3 normalized() {
        Vec3 c = new Vec3();
        c.setX(this.getX() / this.getLength());
        c.setY(this.getY() / this.getLength());
        c.setZ(this.getZ() / this.getLength());
        return c;
    }

    public static Vec3 Mul(Vec3 a, double c) {
        Vec3 b = new Vec3(a);
        b.setX(a.getX() * c);
        b.setY(a.getY() * c);
        b.setZ(a.getZ() * c);
        return b;
    }

    public double dot(Vec3 v) {
        return this.getX() * v.getX() + this.getY() * v.getY() + this.getZ() * v.getZ();
    }

    public static double Dot(Vec3 v1, Vec3 v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    public double Dot(Vec3 v1) {
        return v1.getX() * this.getX() + v1.getY() * this.getY() + v1.getZ() * this.getZ();
    }

    public static Vec3 Sum(Vec3... vector) {
        Vec3 c = new Vec3();
        for (Vec3 vectors : vector) {
            c.setX(c.getX() + vectors.getX());
            c.setY(c.getY() + vectors.getY());
            c.setZ(c.getZ() + vectors.getZ());
        }
        return c;
    }

    public static Vec3 Sub(Vec3 vector1, Vec3 vector2) {
        Vec3 c = new Vec3(vector1);
        c.setX(c.getX() - vector2.getX());
        c.setY(c.getY() - vector2.getY());
        c.setZ(c.getZ() - vector2.getZ());
        return c;
    }

    public static double cosCorner(Vec3 v1, Vec3 v2) {
        return Dot(v1, v2) / (v1.getLength() * v2.getLength());
    }

    public double cosCorner(Vec3 v) {
        return this.Dot(v) / (this.getLength() * v.getLength());
    }

    public static Vec3 productVector(Vec3 a, Vec3 b) {
        Vec3 c = new Vec3();
        c.setX(a.getY() * b.getZ() - a.getZ() * b.getY());
        c.setY(a.getZ() * b.getX() - a.getX() * b.getZ());
        c.setZ(a.getX() * b.getY() - a.getY() * b.getX());
//        int j = "sdf".length();
        return c;
    }

    public double getLength() {
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
    }

    public boolean isEqual(Vec3 v) {
        return this.getX() == v.getX() && this.getY() == v.getY() && this.getY() == v.getY();
    }

    public static Vec3 MulRGB(Vec3 v1, Vec3 v2) {
        Vec3 c = new Vec3();
        c.setX(v1.getX() * v2.getX());
        c.setY(v1.getY() * v2.getY());
        c.setZ(v1.getZ() * v2.getZ());
        return c;
    }

    public Vec3 mulRGB(Vec3 v) {
        Vec3 c = new Vec3();
        c.setX(this.getX() * v.getX());
        c.setY(this.getY() * v.getY());
        c.setZ(this.getZ() * v.getZ());
        return c;
    }

    @Override
    public String toString() {
        return vector[0] + " " + vector[1] + " " + vector[2];
    }

}
