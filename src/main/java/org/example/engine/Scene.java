package org.example.engine;

import java.util.ArrayList;
import java.util.List;

class Scene {
    static double sceneReflective = 1;
    static int mirrorDepth = 3;
    //    static double bias = 0.001;
    ViewPort view;
    List<SObject> objects;
    List<Light> lights;

    Color[][] lastSnap;
    Color bgColor;

    Vec3 camera = new Vec3();

    public static List<RayCollision> SObjectToRayCollision(List<SObject> obj) {
        List<RayCollision> r = new ArrayList<RayCollision>();
        for (int i = 0; i < obj.size(); i++) {
            r.add(obj.get(i));
        }
        return r;
    }

    public List<RayCollision> SObjectToRayCollision() {
        List<RayCollision> r = new ArrayList<RayCollision>();
        for (int i = 0; i < this.objects.size(); i++) {
            r.add(this.objects.get(i));
        }
        return r;
    }

    public Scene(ViewPort view) {
        this.objects = new ArrayList<SObject>();
        this.lights = new ArrayList<Light>();
        this.view = view;
        this.bgColor = Color.black;
    }

    public Scene(ViewPort view, Color bg) {
        this.objects = new ArrayList<SObject>();
        this.lights = new ArrayList<Light>();
        this.view = view;
        this.bgColor = bg;
    }

    public void setBackground(Color color) {
        this.bgColor = color;
    }

    public void addObject(Shape obj) {
        objects.add(obj);
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public Color[][] getSnap(Canvas c) {
        Color[][] snap = new Color[c.h][c.w];
        for (int y = 0; y < c.h; y++) {
            for (int x = 0; x < c.w; x++) {
                snap[y][x] = this.getPixelColor(x, y, c);
            }
        }
        this.lastSnap = snap;
        return snap;
    }

    public void saveSnap(String filepath) {
        int w = this.lastSnap[0].length;
        int h = this.lastSnap.length;
        BMP bmp = new BMP(w, h, filepath);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                bmp.fillPixel(x, y, this.lastSnap[y][x]);
            }
        }
        bmp.save();
    }

    public Color TraceRay(Ray ray, double t_min, double t_max, int depth) {
        double[] objInter = Ray.RayIntersection(ray, this.SObjectToRayCollision(), t_min, t_max);
        if (objInter[0] == -1) {
            return this.bgColor;
        }
        SObject obj = this.objects.get((int) objInter[0]);
        Vec3 normal = obj.getNormal(ray.getPoint(objInter[1]));
        Vec3 p = ray.getPoint(objInter[1]);
        Material objMaterial = obj.material;

        double reflective = objMaterial.reflective;
        double refractive = objMaterial.refractive;
        Color currentColor = objMaterial.color.withIntensityVec3(this.pointRadiance(p, normal, ray.getD(), objMaterial));

        if (depth <= 0 || reflective <= 0) {
            return currentColor;
        }
        Color reflectedColor = null;
        Color refractedColor = null;
        double kr = 1;

        if (reflective > 0) {
            Vec3 mirrorReflect = Ray.RayReflect(Vec3.Mul(ray.getD(), -1), normal);
            Ray reflectRay = new Ray(p, new Vec3());
            reflectRay.setD(mirrorReflect);
            reflectedColor = TraceRay(reflectRay, 0.0001, Double.POSITIVE_INFINITY, depth - 1);
        }
        if (refractive > 0) {
            kr = Ray.Fresnel(ray.getD(), Vec3.Mul(normal, -1), refractive, sceneReflective);

            if (kr < 1) {
                Vec3 refract = Ray.RayRefract(Vec3.Mul(ray.getD(), 1), normal, refractive, sceneReflective);

//                System.out.println(refract);
                if (Double.isInfinite(refract.getX()) && Double.isInfinite(refract.getX()) && Double.isInfinite(refract.getX())) {
                    refractedColor = new Color(0, 0, 0);
                } else {
                    Ray refractRay = new Ray(p, new Vec3());
                    refractRay.setD(refract);
                    refractedColor = TraceRay(refractRay, 0.0001, Double.POSITIVE_INFINITY, depth - 1);
                }
            }
        }
        reflectedColor = reflectedColor == null ? new Color(0, 0, 0) : reflectedColor;
        refractedColor = refractedColor == null ? new Color(0, 0, 0) : refractedColor;

//        return Color.Sum(Color.Sum(currentColor.withIntensity(1 - reflective), Color.Mul(reflectedColor, reflective)), Color.Mul(reflectedColor, 1 - kr));
        return Color.Sum(currentColor.withIntensity(1 - reflective), Color.Mul(reflectedColor, reflective));
    }

    public Color getPixelColor(int x, int y, Canvas canvas) {
        return TraceRay(new Ray(this.camera, canvas.toViewport(x, y, this.view)), 0.99999999, Double.POSITIVE_INFINITY, mirrorDepth);
    }
    public Vec3 pointRadiance(Vec3 p, Vec3 n, Vec3 cameraPosition, Material material) {
        Vec3 r = new Vec3(0, 0, 0);
        for (Light light : this.lights) {
            if (light.inShadow(p, SObjectToRayCollision())) {
                continue;
            }
            r = Vec3.Sum(r, light.getRadiance(p, n, cameraPosition, material));
        }
        return r;
    }

    public void printAllObjects() {
        for (ObjectBase obj : this.objects) {
            System.out.println(obj);
        }
        for (ObjectBase obj : this.lights) {
            System.out.println(obj);
        }
    }
}
