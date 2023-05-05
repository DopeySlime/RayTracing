package org.example.engine;

import java.util.List;

class Ray {
    protected Vec3 a;
    protected Vec3 b;
    protected Vec3 d;

    public Ray(Vec3 a, Vec3 b) {
        this.a = a;
        this.b = b;
        this.d = Vec3.Sub(b, a);
    }

    public Vec3 getA() {
        return a;
    }

    public Vec3 getD() {
        return d;
    }

    public Vec3 getB() {
        return b;
    }

    public void setA(Vec3 a) {
        this.a = a;
    }

    public void setB(Vec3 b) {
        this.b = b;
    }

    public void setD(Vec3 d) {
        this.d = d;
        this.b = Vec3.Sum(d, this.a);
    }

    public Vec3 origin() {
        return this.a;
    }

    public Vec3 direction() {
        return this.b;
    }

    public Vec3 getPoint(double t) {
        return Vec3.Sum(this.a, Vec3.Mul(this.d, t));
    }

    public static boolean ValueIn(double value, double s, double e) {
        if (s < value && value < e) {
            return true;
        }
        return false;
    }

    public static double[] RayIntersection(Ray ray, List<RayCollision> objects, double t_min, double t_max) {
        double t = Double.POSITIVE_INFINITY;
        RayCollision obj = null;
        double objIndex = 0;

        for (int j = 0; j < objects.size(); j++) {
            double[] inter = objects.get(j).intersectRay(ray);
            if (inter.length == 0) {
                continue;
            }
            if (ValueIn(inter[0], t_min, t_max) && inter[0] < t) {
                t = inter[0];
                obj = objects.get(j);
                objIndex = j;
            }
            if (ValueIn(inter[1], t_min, t_max) && inter[1] < t) {
                t = inter[1];
                obj = objects.get(j);
                objIndex = j;
            }
        }
        if (obj == null) {
            return new double[]{-1, -1};
        }
//        object on scene index, and t
        return new double[]{objIndex, t};
    }

    public static Vec3 RayReflect(Vec3 r, Vec3 n) {
        return Vec3.Sub(Vec3.Mul(n, 2 * Vec3.Dot(n, r)), r);
    }

    public static Vec3 RayRefract(Vec3 r, Vec3 n, double ir1, double ir2) {
        double cosi = clamp(-1, 1, Vec3.Dot(r, n));
        double etai = ir2, etat = ir1;
        Vec3 N = n;
        if (cosi < 0) {
            cosi = -cosi;
        } else {
            double etaiC = etai;
            etai = etat;
            etat = etaiC;
            N = Vec3.Mul(n, -1);
        }
        double eta = etai / etat;
        double k = 1 - Math.pow(eta, 2) * (1 - Math.pow(cosi, 2));
        return k < 0 ? new Vec3(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY) : Vec3.Sum(Vec3.Mul(r, eta), Vec3.Mul(N, (eta * cosi - Math.sqrt(k))));
    }

    public static double Fresnel(Vec3 r, Vec3 n, double ir1, double ir2) {
        double kr;
        double cosi = clamp(-1, 1, Vec3.Dot(r, n));
        double etai = ir2, etat = ir1;
        if (cosi > 0) {
            double etaiC = etai;
            etai = etat;
            etat = etaiC;
        }
        // Compute sini using Snell's law
        double sint = etai / etat * Math.sqrt(Math.max(0, 1 - Math.pow(cosi, 2)));
        // Total internal reflection
        if (sint >= 1) {
            kr = 1;
        } else {
            double cost = Math.sqrt(Math.max(0, 1 - Math.pow(sint, 2)));
            cosi = Math.abs(cosi);
            double Rs = ((etat * cosi) - (etai * cost)) / ((etat * cosi) + (etai * cost));
            double Rp = ((etai * cosi) - (etat * cost)) / ((etai * cosi) + (etat * cost));
            kr = (Rs * Rs + Rp * Rp) / 2;
        }
        // kt = 1 - kr;
        return kr;
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
