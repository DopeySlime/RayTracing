package org.example.engine;

class Canvas {
    int w;
    int h;

    public Vec3 toViewport(double x, double y, ViewPort view) {
        return new Vec3((-view.w / 2) + x * (view.w / this.w), (view.h / 2) - y * (view.h / this.h), view.d);
    }

    public Canvas(int w, int h) {
        this.w = w;
        this.h = h;
    }
}
