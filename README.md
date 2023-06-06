# Raytracing Java Project

This is a simple raytracing Java project created by me, Im beginner in Java developing, to get familiarized with the basic principles of Object-Oriented Programming (OOP) and the mathematical concepts of raytracing.

## Project Description

This project includes the basic principles of raytracing such as AmbientLight, DirectedLight, PointLight, ColoredLight, and light diffusion. However, the program does not support polygons and only accesses Sphere and Plane objects that must be colored. The most interesting part of this project is the implementation of Reflection, Mirror Reflection, and Refraction (which is not fully working yet) and some object materials such as color, specular, beta-roughness, and reflection.

As mentioned earlier, this pet project was created to get acquainted with the mathematical concepts of raytracing, and the code is NOT DOCUMENTED. However, the code is written in PURE Java, and the project is designed to be easily scalable. Adding a rotating camera, movement, GPU support, polygons, other 3D objects, more materials, etc., is possible since the basis is already there.

## Render Example

![Render Example](/out/output.bmp)

## Installation Instructions

This project was built using Gradle, so make sure you have it installed on your machine. Then, follow these steps:

1. Clone this repository.
```shell
git clone https://github.com/DopeySlime/RayTracing.git
```
2. Navigate to the root directory of the project.
```shell
cd RayTracing
```
3. Build the project.
```shell
./gradlew build
```
4. Run the project, to get render image in /out.
```shell
./gradlew run
```

That's it! You should now be able to see the image of the render. Feel free to modify the code and experiment with different settings.
