package com.xingray.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xingray.observer.ListObserver
import com.xingray.observer.Observer
import com.xingray.observer.Patch
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTest.setOnClickListener {
            test()
        }
    }

    fun test() {
        val roomManager = RoomManager()

        roomManager.addObserver(null, object : Observer<Room> {
            override fun onChanged(t: Room) {
                log("1 onChanged: $t")
            }

            override fun onUpdated(patches: Array<Patch>) {
                log("2 onUpdated: " + patches.contentToString())
            }
        })

        roomManager.addListObserver(null, object : ListObserver<Student> {

            override fun onChanged(t: MutableList<Student>) {
                log("3 onChanged: $t")
            }

            override fun onUpdated(patches: Array<Patch>) {
                log("4 onUpdated: " + patches.contentToString())
            }

            override fun onInsert(position: Int, addList: MutableList<Student>) {
                log(
                    "5 onInsert: "
                            + "\nposition: " + position
                            + "\naddList: " + addList
                )
            }

            override fun onRemove(position: Int, range: Int) {
                log(
                    ("6 onRemove: "
                            + "\nposition: " + position
                            + "\nrange: " + range)
                )
            }

            override fun onItemUpdate(position: Int, patches: Array<Patch>) {
                log(
                    ("7 onItemUpdate: "
                            + "\nposition: " + position
                            + "\npatches: " + patches.contentToString())
                )
            }
        })

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
        Log.i(MainActivity::class.java.simpleName, "" + log)
    }
}
