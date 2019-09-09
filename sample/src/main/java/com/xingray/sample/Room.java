package com.xingray.sample;

import java.lang.reflect.Field;
import java.util.List;

public class Room {
    String name;
    String id;
    int area;

    List<Student> students;

    public Room(String name, String id, int area) {
        this.name = name;
        this.id = id;
        this.area = area;
    }

    public static Field FIELD_NAME;
    public static Field FIELD_ID;
    public static Field FIELD_AREA;

    static {
        try {
            FIELD_NAME = Room.class.getDeclaredField("name");
            FIELD_ID = Room.class.getDeclaredField("id");
            FIELD_AREA = Room.class.getDeclaredField("area");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", area=" + area +
                ", students=" + students +
                '}';
    }
}
