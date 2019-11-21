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

            override fun onItemChanged(position: Int, current: Student?, last: Student?) {
                Log.i(TAG, "onListItemChanged: $position  $current $last")
                adapter?.notifyItemChanged(position, current)
            }

            override fun onItemsInserted(position: Int, insertList: List<Student?>) {
                Log.i(TAG, "onListInserted: $position  $insertList")
                adapter?.add(position, insertList)
            }

            override fun onListChanged(current: List<Student?>?, last: List<Student?>?) {
                Log.i(TAG, "onListChanged: $current $last")
                adapter?.update(current)
            }

            override fun onItemsRemoved(position: Int, range: Int) {
                Log.i(TAG, "onListRemoved: $position  $range")
                adapter?.remove(position, range)
            }

            override fun onItemUpdated(position: Int, appliedPatch: Patch, lastFiledValue: Any?) {
                Log.i(TAG, "onListItemUpdated: $position  $appliedPatch $lastFiledValue")
                adapter?.notifyItemChanged(position, appliedPatch)
            }

            override fun onItemsMoved(fromIndex: Int, toIndex: Int, size: Int) {
                Log.i(TAG, "onItemsMoved: $fromIndex  $toIndex $size")
                adapter?.notifyItemMoved(fromIndex, toIndex)
            }

            override fun onChanged(current: Room?, last: Room?) {
                Log.i(TAG, "onChanged: $current $last")
                showRoom(current)
            }

            override fun onUpdated(patch: Patch, lastFiledValue: Any?) {
                Log.i(TAG, "onUpdated: $patch $lastFiledValue")
                when (patch.name) {
                    Room.FIELD_ID -> {
                        val id: String = patch.getPayload()
                        tvId.text = id
                    }
                    Room.FIELD_AREA -> {
                        val area: Int = patch.getPayload()
                        tvArea.text = area.toString()
                    }
                    Room.FIELD_NAME -> {
                        val name: String = patch.getPayload()
                        tvName.text = name
                    }
                }
            }
        })
    }

    private fun showRoom(t: Room?) {
        tvName.text = t?.name ?: ""
        tvArea.text = t?.area.toString()
        tvId.text = t?.id ?: ""

        val students = t?.students?.filterNotNull()

        adapter?.update(students)
    }

    @LayoutId(R.layout.item_main_student_list)
    class StudentViewHolder(view: View) : ViewHolder<Student>(view), LayoutContainer {

        override fun onBindItemView(t: Student, position: Int) {
            showStudent(t)
        }

        override fun onRefreshItemView(payload: Any) {
            when (payload) {
                is Patch -> {
                    val patch: Patch = payload
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

                is Student -> {
                    showStudent(payload)
                }
            }
        }

        private fun showStudent(t: Student) {
            tvStudentName.text = t.name
            tvSex.text = if (t.sex == 0) {
                "男"
            } else {
                "女"
            }
            tvAge.text = t.age.toString()
            tvMark.text = t.mark.toString()
        }

        override val containerView: View?
            get() = itemView
    }
}
