package com.xingray.sample;


import com.xingray.observer.ListObserver;
import com.xingray.observer.Observable;
import com.xingray.observer.ObservableList;
import com.xingray.observer.Observer;
import com.xingray.observer.Patch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * RoomManager
 */
public class RoomManager {
    Room mRoom;
    Observable<Room> observable;
    ObservableList<Student> observableList;

    public RoomManager() {
        observable = new Observable<>();
        observableList = new ObservableList<>();
    }

    public void setRoom(Room room) {
        mRoom = room;
        observable.onChanged(mRoom);
    }

    public void updateRoom(String name, String id) {
        mRoom.name = name;
        mRoom.id = id;
        Patch[] patches = new Patch[]{
                new Patch(Room.FIELD_NAME, name),
                new Patch(Room.FIELD_ID, id)
        };
        observable.onUpdated(patches);
    }

    public void setStudents(List<Student> students) {
        mRoom.students = students;
        observableList.onChanged(students);
    }

    public void addStudents(List<Student> students) {
        int position = mRoom.students.size();
        mRoom.students.addAll(students);
        observableList.onInsert(position, students);
    }

    public void updateStudent(int position, String name, int age) {
        Student student = mRoom.students.get(position);
        student.name = name;
        student.age = age;
        Patch[] patches = new Patch[]{
                new Patch(Student.FIELD_NAME, name),
                new Patch(Student.FIELD_AGE, age)
        };
        observableList.onItemUpdate(position, patches);
    }

    public void addObserver(Executor executor, Observer<Room> observer) {
        observable.addObserver(executor, observer);
    }

    public void addListObserver(Executor executor, ListObserver<Student> observer) {
        observableList.addObserver(executor, observer);
    }

    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager();

        roomManager.addObserver(null, new Observer<Room>() {
            @Override
            public void onChanged(Room room) {
                log("1 onChanged: " + room);
            }

            @Override
            public void onUpdated(Patch[] patches) {
                log("2 onUpdated: " + Arrays.toString(patches));
            }
        });

        roomManager.addListObserver(null, new ListObserver<Student>() {

            @Override
            public void onChanged(List<Student> students) {
                log("3 onChanged: " + students);
            }

            @Override
            public void onUpdated(Patch[] patches) {
                log("4 onUpdated: " + Arrays.toString(patches));
            }

            @Override
            public void onInsert(int position, List<Student> addList) {
                log("5 onInsert: "
                        + "\nposition: " + position
                        + "\naddList: " + addList);
            }

            @Override
            public void onRemove(int position, int range) {
                log("6 onRemove: "
                        + "\nposition: " + position
                        + "\nrange: " + range);
            }

            @Override
            public void onItemUpdate(int position, Patch[] patches) {
                log("7 onItemUpdate: "
                        + "\nposition: " + position
                        + "\npatches: " + Arrays.toString(patches));
            }
        });

        Room room = new Room("room001", "r001", 100);
        List<Student> students = new ArrayList<>();

        roomManager.setRoom(room);
        roomManager.setStudents(students);
        roomManager.updateRoom("room100", "001");

        students = new ArrayList<>();
        students.add(new Student("aaa", 0, 10, 400));
        students.add(new Student("bbb", 1, 20, 300));
        students.add(new Student("ccc", 0, 30, 200));
        students.add(new Student("ddd", 1, 40, 100));
        roomManager.addStudents(students);

        roomManager.updateStudent(1, "xxx", 12);
        roomManager.updateStudent(2, "yyy", 21);
    }

    public static void log(Object log) {
        System.out.println("" + log);
    }
}
