package org.example.engine;

import java.util.List;

class DirectLight extends Light {
    public DirectLight(Vec3 v, double intensity, Vec3 color) {
        super(v, intensity, color);
        this.addType("_" + this.getClass().getSimpleName());
    }

    @Override
    public boolean inShadow(Vec3 p, List<RayCollision> object) {
        double t_max = Double.POSITIVE_INFINITY;
        Ray ray = new Ray(p, new Vec3());
        ray.setD(Vec3.Mul(this.center, -1));
        double[] inter = Ray.RayIntersection(ray, object, 0.001, t_max);
        if (inter[0] != -1) {
            return true;
        }
        return false;
    }

    @Override
    public Vec3 getRadiance(Vec3 p, Vec3 n, Vec3 camera, Material material) {
        Vec3 kIntensity = new Vec3(0, 0, 0);

        Vec3 sub = Vec3.Mul(this.center, -1);
        kIntensity = Vec3.Sum(kIntensity, Vec3.Mul(this.color, this.intensity * Math.max(0, (new Vec3(p, this.center)).cosCorner(n))));
        if (material.specular != -1) {
            Vec3 reflect = Ray.RayReflect(Vec3.Mul(sub, -1), n);

            kIntensity = Vec3.Sum(kIntensity, Vec3.Mul(this.color, Math.pow(Math.max(0, reflect.cosCorner(camera)), material.specular)));
        }

        return kIntensity;
    }
}
