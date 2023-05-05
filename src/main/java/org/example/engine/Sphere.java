package org.example.engine;

class Sphere extends Shape {
    double r;

    public Sphere(Vec3 c, double r, Material material) {
        super(c, material);
        this.r = r;
    }

    public Sphere(double x, double y, double z, double r, Material material) {
        super(new Vec3(x, y, z), material);
        this.r = r;
    }

    @Override
    public double[] intersectRay(Ray ray) {
        Vec3 C = new Vec3(this.center);
        double r = this.r;
        Vec3 oc = Vec3.Sub(ray.getA(), C);

        double a = Vec3.Dot(ray.getD(), ray.getD());
        double b = 2 * Vec3.Dot(oc, ray.getD());
        double c = Vec3.Dot(oc, oc) - r * r;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
        }

        double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
        return new double[]{t1, t2};
    }

    @Override
    public Vec3 getNormal(Vec3 p) {
        Vec3 s = Vec3.Sub(p, this.center);
        return Vec3.Mul(s, 1 / s.getLength());
    }

}
