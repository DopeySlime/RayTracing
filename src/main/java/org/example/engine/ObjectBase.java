package org.example.engine;

abstract class ObjectBase {
    Vec3 center;
    String object_type;
    String name;

    public ObjectBase(Vec3 center, String name) {
        this.center = center;
        this.name = name;
        this.object_type = "";
    }

    public ObjectBase(Vec3 center) {
        this.center = center;
        this.name = "None";
        this.object_type = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addType(String type) {
        if (this.object_type.equals("")) {
            this.object_type += type.substring(1);
        } else {
            this.object_type += type;
        }

    }

    public String getType() {
        return object_type;
    }

    @Override
    public String toString() {
        return this.object_type + "/" + this.name + " | center: " + this.center.toString();
    }
}
