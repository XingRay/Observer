package com.xingray.sample

import com.xingray.observer.ObservableList
import com.xingray.observer.Patch

class Room(var name: String, var id: String, var area: Int) : ObservableList<Student> {

    companion object {
        var FIELD_NAME = "Room#name"
        var FIELD_ID = "Room#id"
        var FIELD_AREA = "Room#area"
    }

    val students by lazy { mutableListOf<Student?>() }

    override fun toString(): String {
        return "Room{" +
                "name='" + name + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", area=" + area +
                ", students=" + students +
                '}'.toString()
    }

    override fun changeList(list: MutableList<Student?>?): Boolean {
        if (students === list) {
            return false
        }
        students.clear()
        if (list != null) {
            students.addAll(list)
        }
        return true
    }

    override fun insertItems(position: Int, list: List<Student?>): Boolean {
        return students.addAll(position, list)
    }

    override fun removeItems(position: Int, range: Int): Boolean {
        val removeCount = students.remove(position, range)
        return removeCount > 0
    }

    override fun getItem(position: Int): Student? {
        return students[position]
    }

    override fun setItem(position: Int, e: Student?): Student? {
        val previous = students[position]
        students[position] = e
        return previous
    }

    override fun size(): Int {
        return students.size
    }

    override fun applyPatch(patch: Patch): Boolean {
        when (patch.name) {
            FIELD_NAME -> {
                val name: String = patch.getPayload()
                if (this.name != name) {
                    this.name = name
                    return true
                }
            }

            FIELD_AREA -> {
                val area: Int = patch.getPayload()
                if (this.area != area) {
                    this.area = area
                    return true
                }
            }

            FIELD_ID -> {
                val id: String = patch.getPayload()
                if (this.id != id) {
                    this.id = id
                    return true
                }
            }
        }
        return false
    }
}
