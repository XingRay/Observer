package com.xingray.sample


import com.xingray.observer.ListObserver
import com.xingray.observer.ObservableListData
import com.xingray.observer.Patch
import java.util.concurrent.Executor

/**
 * RoomManager
 */
class RoomManager {
    var room = ObservableListData<Student, Room>()

    fun setRoom(room: Room) {
        this.room.set(room)
    }

    fun updateRoom(name: String, id: String) {
        val patches = Patch.builder()
            .add(Room.FIELD_NAME, name)
            .add(Room.FIELD_ID, id).build()
        this.room.update(patches)
    }

    fun setStudents(students: MutableList<Student>) {
        room.changeList(students)
    }

    fun addStudents(students: List<Student>) {
        room.insertList(0, students)
    }

    fun updateStudent(position: Int, name: String, age: Int) {
        val patches = Patch.builder()
            .add(Student.FIELD_NAME, name)
            .add(Student.FIELD_AGE, age).build()
        room.updateListItem(position, patches)
    }

    fun addObserver(executor: Executor, observer: ListObserver<Student, Room>) {
        room.addObserver(executor, observer)
    }
}
