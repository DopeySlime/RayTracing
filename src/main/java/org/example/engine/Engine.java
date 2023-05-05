package org.example.engine;

import java.util.Scanner;
class Main {
    static Canvas CANVAS = new Canvas(1024, 1024);
    static ViewPort VIEW = new ViewPort(1, 1, 1);
    static String FILEPATH = "out/output.bmp";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Scene world = new Scene(VIEW, Color.black);

        System.out.println("Objects adding");

        world.addLight(new PointLight(new Vec3(0, 5, 0), 25, new Vec3(0.5, 0.5, 0), 0.3));
        world.addLight(new PointLight(new Vec3(-5, 0, -5), 25, new Vec3(0, 0, 0.8), 0.3));
        Material refr = new Material(Color.lightLemon, 100, 0.8);
        refr.setRefractive(0.8);
        world.addObject(new Sphere(new Vec3(0.9, 0, 2), 0.5, refr));
        world.addObject(new Sphere(new Vec3(0, 0, 3), 0.5, new Material(Color.darkorchid, 80, 0.3)));
//        world.addObject(new Plane(new Vec3(0, -0.5, 3), new Vec3(0, 1, -0.3), new Material(Color.lightLemon, 100, 0.3)));


        world.addObject(new Plane(new Vec3(0, -0.5, 3), new Vec3(0, 1, -0.1), new Material(Color.lightLemon, 100, 0.3)));
        world.printAllObjects();

        System.out.println("Complete snapshot");
        world.getSnap(CANVAS);

        System.out.println("Saving it...");
        world.saveSnap(FILEPATH);
    }
}



