package com.xingray.sample


import com.xingray.observer.ListObserver
import com.xingray.observer.ObservableListData
import com.xingray.observer.Patch
import com.xingray.primary.ObservableInt
import java.util.concurrent.Executor

/**
 * RoomManager
 */
class RoomManager {

    private var roomData = ObservableListData<Student, Room>()
    val bedCount = ObservableInt()

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
        @Suppress("UNCHECKED_CAST")
        roomData.changeItems(students as MutableList<Student?>)
    }

    fun addStudents(students: List<Student>) {
        roomData.insertItems(0, students)
    }

    fun updateStudent(position: Int, name: String, age: Int) {
        val patches = Patch.builder()
            .add(Student.FIELD_NAME, name)
            .add(Student.FIELD_AGE, age).build()
        roomData.updateItem(position, patches)
    }

    fun addObserver(executor: Executor, observer: ListObserver<Student, Room>) {
        roomData.addObserver(executor, observer)
    }
}
