package org.example.engine;

abstract class Shape extends SObject {
    public Shape(Vec3 c, Material material) {
        super(c, material);
        this.addType("_" + this.getClass().getSimpleName());
    }
}
