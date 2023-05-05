package org.example.engine;

class Color {
    protected double r;
    protected double g;
    protected double b;
    static Color black = new Color(0, 0., 0);
    static Color white = new Color(255, 255, 255);
    static Color blue = new Color(0, 0, 255);
    static Color darkorchid = new Color(153, 50, 204);
    static Color purple = new Color(128, 0, 128);
    static Color mediumorchid = new Color(186, 85, 211);
    static Color red = new Color(255, 0, 0);
    static Color lightLemon = new Color(255, 160, 122);
    static Color deepPink = new Color(255, 20, 147);
    static Color oliveDrab = new Color(107, 142, 35);

    public static double fmod(double a, double b) {
        int result = (int) Math.floor(a / b);
        return a - result * b;
    }

    static double[] RGBtoHSV(double r, double g, double b) {
        r = r / 255.0;
        g = g / 255.0;
        b = b / 255.0;

        double cmax = Math.max(r, Math.max(g, b));
        double cmin = Math.min(r, Math.min(g, b));
        double diff = cmax - cmin;
        double h = -1, s = -1;

        if (cmax == cmin)
            h = 0;

        else if (cmax == r)
            h = fmod(60 * ((g - b) / diff) + 360, 360);

        else if (cmax == g)
            h = fmod(60 * ((b - r) / diff) + 120, 360);

        else if (cmax == b)
            h = fmod(60 * ((r - g) / diff) + 240, 360);

        if (cmax == 0)
            s = 0;
        else
            s = (diff / cmax) * 100;

        // compute v
        double v = cmax * 100;
        return new double[]{h, s, v};
    }

    public double[] toHSV() {
        return RGBtoHSV(this.getR(), this.getG(), this.getB());
    }

    static double ValidateValue(double m1, double v, double m2) {
        if (v < m1) {
            return m1;
        } else if (m2 < v) {
            return m2;
        } else {
            return v;
        }
    }

    public void normalize() {
        this.setR(ValidateValue(0, this.getR(), 255));
        this.setG(ValidateValue(0, this.getG(), 255));
        this.setB(ValidateValue(0, this.getB(), 255));
    }

    static Color Normalize(Color color) {
        Color color1 = new Color();
        color1.setR(ValidateValue(0, color.getR(), 255));
        color1.setG(ValidateValue(0, color.getG(), 255));
        color1.setB(ValidateValue(0, color.getB(), 255));
        return color1;
    }

    static int[] ColorHexStringToByteArray(String s) {
        return new int[]{Integer.parseInt(s.substring(0, 2), 16), Integer.parseInt(s.substring(2, 4), 16), Integer.parseInt(s.substring(4), 16)};
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    static String ByteArrayToColorHexString(int[] arr) {
        String r = Integer.toHexString(arr[0]);
        if (r.length() == 1) {
            r = "0" + r;
        }
        String g = Integer.toHexString(arr[1]);
        if (g.length() == 1) {
            g = "0" + g;
        }
        String b = Integer.toHexString(arr[2]);
        if (b.length() == 1) {
            b = "0" + b;
        }
        return "#" + (r + g + b).toUpperCase();
    }

    static Color decode(String hexColor) {
        return new Color(hexColor);
    }

    public Color(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(Color color) {
        this.r = color.getR();
        this.g = color.getG();
        this.b = color.getB();
    }

    public Color(Vec3 vec3color) {
        this.r = vec3color.getX();
        this.g = vec3color.getY();
        this.b = vec3color.getZ();
    }

    public Color() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public Color(String hexColor) {
        int[] color = ColorHexStringToByteArray(hexColor.substring(1));
        this.r = color[0];
        this.g = color[1];
        this.b = color[2];
    }

    static Color Sum(Color color1, Color color2) {
        return Normalize(new Color(Vec3.Sum((new Vec3(color1.getR(), color1.getG(), color1.getB())), (new Vec3(color2.getR(), color2.getG(), color2.getB())))));
    }

    public void add(Color other) {
        Color r = new Color(Vec3.Sum((new Vec3(this.getR(), this.getG(), this.getB())), (new Vec3(other.getR(), other.getG(), other.getB()))));
        this.setR(r.getR());
        this.setG(r.getG());
        this.setB(r.getB());
    }

    static Color Mul(Color color, double k) {
        return Normalize(new Color(Vec3.Mul((new Vec3(color.getR(), color.getG(), color.getB())), k)));
    }

    static Color MulVec3(Color color, Vec3 v) {
        return Normalize(new Color(color.getR() * v.getX(), color.getG() * v.getY(), color.getB() * v.getZ()));
    }

    public void mul(double k) {
        Color r = new Color(Vec3.Mul((new Vec3(this.getR(), this.getG(), this.getB())), k));
        this.setR(r.getR());
        this.setG(r.getG());
        this.setB(r.getB());
    }
    @Override
    public String toString() {
        return "(" + this.getR() + ", " + this.getG() + ", " + this.getB() + ")";
    }

    public Color withIntensity(double intensity) {
        return Color.Mul(new Color(this.getR(), this.getG(), this.getB()), intensity);
    }

    public Color withIntensityVec3(Vec3 intensity) {
        return Color.MulVec3(new Color(this.getR(), this.getG(), this.getB()), intensity);
    }
}
