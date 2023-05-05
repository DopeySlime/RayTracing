package org.example.engine;

import java.util.List;

class PointLight extends Light {
    double r;

    public PointLight(Vec3 v, double intensity, Vec3 color, double radius) {
        super(v, intensity, color);
        this.r = radius;
        this.addType("_" + this.getClass().getSimpleName());
    }

    @Override
    public boolean inShadow(Vec3 p, List<RayCollision> object) {
        double t_max = 1;
        Ray ray = new Ray(p, new Vec3());
        ray.setD(Vec3.Sub(this.center, p));
        if (Ray.RayIntersection(ray, object, 0.001, t_max)[0] != -1) {
            return true;
        }
        return false;
    }

    @Override
    public Vec3 getRadiance(Vec3 p, Vec3 n, Vec3 camera, Material material) {
        Vec3 kIntensity = new Vec3(0, 0, 0);
        Vec3 sub = Vec3.Sub(this.center, p);
        double d = sub.getLength();
        double attenuation = (2 * (1 - (d / (Math.sqrt(Math.pow(d, 2) + Math.pow(r, 2)))))) / Math.pow(r, 2);
//        attenuation = 1;
        kIntensity = Vec3.Sum(kIntensity, Vec3.Mul(this.color, this.intensity * Math.max(0, (new Vec3(p, this.center)).cosCorner(n)) * attenuation));
        if (material.specular != -1) {
            Vec3 reflect = Ray.RayReflect(Vec3.Mul(sub, -1), n);

            kIntensity = Vec3.Sum(kIntensity, Vec3.Mul(this.color, this.intensity * Math.pow(Math.max(0, reflect.cosCorner(camera)), material.specular) * attenuation));
        }

//        Vec3 V = new Vec3(0, 0, 0);
//        Vec3 H = Vec3.Sum(sub, V).normalized();
//        double D = GGX(Math.max(Vec3.Dot(n, H), 0.0), material.roughness);
//        double G = GASmith(Math.max(Vec3.Dot(n, V), 0.0), Math.max(Vec3.Dot(n, sub), 0.0), material.roughness);
//        double F = Fresnel(Math.max(Vec3.Dot(H, V), 0.0), material.metallic);
//        double denominator = 4.0 * Math.max(Vec3.Dot(n, V), 0.0) * Math.max(Vec3.Dot(n, sub), 0.0) + 0.0001;
//        double specular = D * F * G / denominator;
//        Vec3 total = Vec3.Mul(Vec3.Sum(Vec3.Mul(material.albedo, 1/ Math.PI), new Vec3(specular, specular, specular)), this.intensity * Math.max(0, (new Vec3(p, this.center)).cosCorner(n)) * attenuation);
//        return Vec3.Sum(kIntensity, total);
        return kIntensity;
    }

    @Override
    public String toString() {
        return super.toString() + " | radius: " + this.r;
    }
}
