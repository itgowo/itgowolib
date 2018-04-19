package com.itgowo.itgowodemo;

public class Entity {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public Entity setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Entity setAge(int age) {
        this.age = age;
        return this;
    }
}
