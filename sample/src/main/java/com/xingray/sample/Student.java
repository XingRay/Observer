package com.xingray.sample;

import java.lang.reflect.Field;

public class Student {
    String name;
    int sex;
    int age;
    int mark;

    public static Field FIELD_NAME;
    public static Field FIELD_AGE;

    public Student(String name, int sex, int age, int mark) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.mark = mark;
    }

    static {
        try {
            FIELD_NAME = Student.class.getDeclaredField("name");
            FIELD_AGE = Student.class.getDeclaredField("age");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", mark=" + mark +
                '}';
    }
}
