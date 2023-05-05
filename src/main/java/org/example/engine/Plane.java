package org.example.engine;

class Plane extends Shape {
    double a;
    double b;
    double c;
    double d;
    Vec3 n;
    public Plane(Vec3 c, Vec3 n, Material material) {
        super(c, material);
        this.n = n;
        this.a = n.getX();
        this.b = n.getY();
        this.c = n.getZ();
        this.d = n.getX() * -1 * c.getX() + n.getY() * -1 * c.getY() + n.getZ() * -1 * c.getZ();
    }

    @Override
    public double[] intersectRay(Ray ray) {
        double t = -(this.a * ray.getA().getX() + this.b * ray.getA().getY() + this.c * ray.getA().getZ() + this.d) / (this.a * ray.getB().getX() + this.b * ray.getB().getY() + this.c * ray.getB().getZ());
        if (t < 0) {
            t = Double.POSITIVE_INFINITY;
        }
        return new double[]{t, Double.POSITIVE_INFINITY};
    }

    @Override
    public Vec3 getNormal(Vec3 p) {
        return n;
    }
}
