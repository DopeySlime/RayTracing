package org.example.engine;

import java.util.List;

abstract class Light extends ObjectBase {
    double intensity;
    Vec3 color;

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public Light(Vec3 v, double intensity, Vec3 color) {
        super(v);
        this.intensity = intensity;
        this.color = color;
    }

    public double getIntensity() {
        return intensity;
    }

    public abstract boolean inShadow(Vec3 p, List<RayCollision> object);

    public abstract Vec3 getRadiance(Vec3 p, Vec3 n, Vec3 camera, Material material);

    @Override
    public String toString() {
        return super.toString() + " | intensity: " + this.intensity;
    }

    public static double GGX(double NoH, double roughness) {
        return Math.pow(roughness, 4) / (Math.PI * Math.pow(Math.pow(NoH, 2) * (Math.pow(roughness, 4) - 1.0) + 1.0, 2));
    }

    public static double Fresnel(double HoV, double metallic) {
        return metallic + (1.0 - metallic) * Math.pow(clamp(1.0 - HoV, 0.0, 1.0), 5.0);
    }

    public static double GASchlick(double NoV, double roughness) {
        double k = Math.pow(roughness + 1.0, 2) / 8.0;
        return NoV / (NoV * (1.0 - k) + k);
    }

    public static double GASmith(double NoV, double NoL, double roughness) {
        return GASchlick(NoV, roughness) * GASchlick(NoL, roughness);
    }

}
