package com.xingray.sample


import com.xingray.observer.ListObserver
import com.xingray.observer.ObservableListData
import com.xingray.observer.Patch
import java.util.concurrent.Executor

/**
 * RoomManager
 */
class RoomManager {

    private var roomData = ObservableListData<Student, Room>()

    var room: Room?
        get() = roomData.get()
        set(value) {
            roomData.set(value)
        }

    fun updateRoom(name: String, id: String) {
        val patches = Patch.builder()
            .add(Room.FIELD_NAME, name)
            .add(Room.FIELD_ID, id).build()
        this.roomData.update(patches)
    }

    fun setStudents(students: MutableList<Student>) {
        roomData.changeList(students)
    }

    fun addStudents(students: List<Student>) {
        roomData.insertList(0, students)
    }

    fun updateStudent(position: Int, name: String, age: Int) {
        val patches = Patch.builder()
            .add(Student.FIELD_NAME, name)
            .add(Student.FIELD_AGE, age).build()
        roomData.updateListItem(position, patches)
    }

    fun addObserver(executor: Executor, observer: ListObserver<Student, Room>) {
        roomData.addObserver(executor, observer)
    }
}
