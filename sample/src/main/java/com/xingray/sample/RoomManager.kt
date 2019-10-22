package com.xingray.sample


import com.xingray.observer.ListObserver
import com.xingray.observer.ObservableListData
import com.xingray.observer.ObservableListWrapper
import com.xingray.observer.Patch
import java.util.*
import java.util.concurrent.Executor

/**
 * RoomManager
 */
class RoomManager {
    var room =
        ObservableListData<Student, Room>(
            ObservableListWrapper(
                Room("room_001", "0000001", 12)
            )
        )

    fun setRoom(room: Room) {
        this.room.set(room)
    }

    fun updateRoom(name: String, id: String) {
        val patches = Patch.builder()
            .add(Room.FIELD_NAME, name)
            .add(Room.FIELD_ID, id).build()
        this.room.update(patches)
    }

    fun setStudents(students: List<Student>) {
        room.change(students)
    }

    fun addStudents(students: List<Student>) {
        room.insert(0, students)
    }

    fun updateStudent(position: Int, name: String, age: Int) {
        val patches = Patch.builder()
            .add(Student.FIELD_NAME, name)
            .add(Student.FIELD_AGE, age).build()
        room.updateItem(position, patches)
    }

    fun addObserver(executor: Executor, observer: ListObserver<Student, Room>) {
        room.addObserver(executor, observer)
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val roomManager = RoomManager()

            roomManager.addObserver(TaskExecutor.uiPool(), object : ListObserver<Student, Room> {
                override fun onChanged(room: Room?) {
                    log("1 onChanged: " + room!!)
                }

                override fun onUpdated(patches: List<Patch>) {
                    log("2 onUpdated: " + patches.toString())
                }

                override fun onInsert(position: Int, insertList: List<Student>) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onRemove(position: Int, range: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemUpdate(position: Int, patches: List<Patch>) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })

//            roomManager.addListObserver(null, object : ListObserver<Student> {
//
//                fun onChanged(students: List<Student>) {
//                    log("3 onChanged: $students")
//                }
//
//                override fun onUpdated(patches: Array<Patch>) {
//                    log("4 onUpdated: " + Arrays.toString(patches))
//                }
//
//                override fun onInsert(position: Int, addList: List<Student>) {
//                    log(
//                        "5 onInsert: "
//                                + "\nposition: " + position
//                                + "\naddList: " + addList
//                    )
//                }
//
//                override fun onRemove(position: Int, range: Int) {
//                    log(
//                        "6 onRemove: "
//                                + "\nposition: " + position
//                                + "\nrange: " + range
//                    )
//                }
//
//                override fun onItemUpdate(position: Int, patches: List<Patch>) {
//                    log(
//                        "7 onItemUpdate: "
//                                + "\nposition: " + position
//                                + "\npatches: " + patches.toString()
//                    )
//                }
//            })

            val room = Room("room001", "r001", 100)
            var students: MutableList<Student> = ArrayList()

            roomManager.setRoom(room)
            roomManager.setStudents(students)
            roomManager.updateRoom("room100", "001")

            students = ArrayList()
            students.add(Student("aaa", 0, 10, 400))
            students.add(Student("bbb", 1, 20, 300))
            students.add(Student("ccc", 0, 30, 200))
            students.add(Student("ddd", 1, 40, 100))
            roomManager.addStudents(students)

            roomManager.updateStudent(1, "xxx", 12)
            roomManager.updateStudent(2, "yyy", 21)
        }

        fun log(log: Any) {
            println("" + log)
        }
    }
}
