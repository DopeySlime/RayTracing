package org.example.engine;

class Material {
    Color color = new Color(1, 1, 1);
    double specular = -1;
    double reflective = -1;
    double refractive = -1;

    public void setReflective(double reflective) {
        this.reflective = reflective;
    }

    public void setRefractive(double refractive) {
        this.refractive = refractive;
    }

    Vec3 albedo = new Vec3(1, 0, 1);
    double roughness = 0.1;
    double metallic = 0.1;
    double ao = 0.1;

    public Material(Color color, double specular) {
        this.color = color;
        this.specular = specular;
    }

    public Material(Color color, double specular, double reflective) {
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
    }

    public Material(Color color, double specular, double reflective, double refractive) {
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
        this.refractive = refractive;
    }

    public Material(Color color) {
        this.color = color;
    }

    public Material(double specular) {
        this.specular = specular;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSpecular(double specular) {
        this.specular = specular;
    }

    @Override
    public String toString() {
        return "color: " + this.color.toString() + " | specular: " + this.specular;
    }
}
