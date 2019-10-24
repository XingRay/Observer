package com.xingray.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xingray.observer.ListObserver
import com.xingray.observer.Patch
import com.xingray.recycleradapter.LayoutId
import com.xingray.recycleradapter.RecyclerAdapter
import com.xingray.recycleradapter.ViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main_student_list.*
import java.util.*
import kotlinx.android.synthetic.main.item_main_student_list.tvName as tvStudentName

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var adapter: RecyclerAdapter? = null
    private val roomManager = RoomManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initList()

        bt01.setOnClickListener {
            val room = Room("room001", "r001", 100)
            roomManager.room = room
            val students: MutableList<Student> = ArrayList()
            roomManager.setStudents(students)
        }

        bt02.setOnClickListener {
            roomManager.updateRoom("room100", "001")
        }

        bt03.setOnClickListener {
            val students: MutableList<Student> = ArrayList()
            students.add(Student("aaa", 0, 10, 400))
            students.add(Student("bbb", 1, 20, 300))
            students.add(Student("ccc", 0, 30, 200))
            students.add(Student("ddd", 1, 40, 100))

            roomManager.setStudents(students)
        }

        bt04.setOnClickListener {
            roomManager.updateStudent(1, "xxx", 12)
            roomManager.updateStudent(2, "yyy", 21)
        }

        initObservers()

        showRoom(roomManager.room)
    }

    private fun initList() {
        rvStudents.layoutManager = LinearLayoutManager(applicationContext)
        adapter = RecyclerAdapter(applicationContext)
            .addType(StudentViewHolder::class.java)

        rvStudents.adapter = adapter
        rvStudents.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    private fun initObservers() {

        roomManager.addObserver(TaskExecutor.uiPool(), object : ListObserver<Student, Room> {

            override fun onListInserted(position: Int, insertList: List<Student>) {
                Log.i(TAG, "onListInserted: $position  $insertList")
                adapter?.add(position, insertList)
            }

            override fun onListChanged(list: List<Student>) {
                Log.i(TAG, "onListChanged: $list")
                adapter?.update(list)
            }

            override fun onListRemoved(position: Int, range: Int) {
                Log.i(TAG, "onListRemoved: $position  $range")
                adapter?.remove(position, range)
            }

            override fun onListItemUpdated(position: Int, appliedPatches: List<Patch>) {
                Log.i(TAG, "onListItemUpdated: $position  $appliedPatches")
                adapter?.notifyItemChanged(position, appliedPatches)
            }

            override fun onChanged(t: Room?) {
                Log.i(TAG, "onChanged: $t")
                showRoom(t)
            }

            override fun onUpdated(patches: List<Patch>) {
                Log.i(TAG, "onUpdated: $patches")
                patches.forEach {
                    when (it.name) {
                        Room.FIELD_ID -> {
                            val id: String = it.getPayload()
                            tvId.text = id
                        }
                        Room.FIELD_AREA -> {
                            val area: Int = it.getPayload()
                            tvArea.text = area.toString()
                        }
                        Room.FIELD_NAME -> {
                            val name: String = it.getPayload()
                            tvName.text = name
                        }
                    }
                }
            }
        })
    }

    private fun showRoom(t: Room?) {
        tvName.text = t?.name ?: ""
        tvArea.text = t?.area.toString()
        tvId.text = t?.id ?: ""

        adapter?.update(t?.students)
    }

    @LayoutId(R.layout.item_main_student_list)
    class StudentViewHolder(view: View) : ViewHolder<Student>(view), LayoutContainer {

        override fun onBindItemView(t: Student, position: Int) {
            tvStudentName.text = t.name
            tvSex.text = if (t.sex == 0) {
                "男"
            } else {
                "女"
            }
            tvAge.text = t.age.toString()
            tvMark.text = t.mark.toString()

        }

        override fun onRefreshItemView(payloads: List<Any>) {
            super.onRefreshItemView(payloads)
            payloads.forEach { payload ->
                if (payload is List<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val patches: List<Patch> = payload as List<Patch>
                    patches.forEach { patch ->
                        when (patch.name) {
                            Student.FIELD_NAME -> {
                                val name: String = patch.getPayload()
                                tvStudentName.text = name
                            }
                            Student.FIELD_AGE -> {
                                val age: Int = patch.getPayload()
                                tvAge.text = age.toString()
                            }
                        }
                    }
                }
            }

        }

        override val containerView: View?
            get() = itemView
    }
}
