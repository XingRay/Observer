package com.xingray.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xingray.observer.ListObserver
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

        roomManager.addObserver(TaskExecutor.uiPool(), object : ListObserver<Student, Room> {
            override fun onInsert(position: Int, insertList: List<Student>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onRemove(position: Int, range: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemUpdate(position: Int, patches: List<Patch>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChanged(t: Room?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onUpdated(patches: List<Patch>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
