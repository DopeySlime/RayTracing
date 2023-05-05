package org.example.engine;

import java.util.List;

class AmbientLight extends Light {
    public AmbientLight(Vec3 vec3, double intensity, Vec3 color) {
        super(new Vec3(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), intensity, color);
        this.addType("_" + this.getClass().getSimpleName());
    }

    @Override
    public boolean inShadow(Vec3 p, List<RayCollision> object) {
        return false;
    }

    @Override
    public Vec3 getRadiance(Vec3 p, Vec3 n, Vec3 camera, Material material) {
        return Vec3.Mul(this.color, this.intensity);
    }

}
