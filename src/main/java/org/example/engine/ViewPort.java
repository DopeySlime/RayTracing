package org.example.engine;

class ViewPort {
    double d;
    double w;
    double h;

    public ViewPort(double w, double h, double d) {
        this.d = d;
        this.w = w;
        this.h = h;
    }

    public ViewPort(double w, double h) {
        this.w = w;
        this.h = h;
    }

    public void setFOV(double a) {
        this.d = w / (2 * Math.tan(a / 2));
    }

    public Vec3 toCanvas(Vec3 p, Canvas c) {
        Vec3 r = getPoint(p);
        return new Vec3((p.getX() + (this.w / 2)) / (this.w / c.w), ((this.h / 2) - p.getY()) / (this.h / c.h), 0);
    }

    //    static double DistanceByFOV(double w, double angle){ return  }
    public double getFOV() {
        return 2 * Math.atan(this.w / (this.d * 2)) * 180 / Math.PI;
    }
    public Vec3 getPoint(Vec3 point) {
        return (new Ray(new Vec3(), point)).getPoint(this.d / point.getZ());
    }
}
