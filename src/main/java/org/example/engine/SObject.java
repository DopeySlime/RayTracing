package org.example.engine;

abstract class SObject extends ObjectBase implements RayCollision {
    Material material;
    public SObject(Vec3 c, Material material){
        super(c);
        this.material = material;
    }

    public abstract Vec3 getNormal(Vec3 p);
    public abstract double[] intersectRay(Ray ray);
    @Override
    public String toString(){
        return super.toString() + " | " + this.material.toString();
    }
}
