package com.xingray.sample

import com.xingray.observer.ObservableList
import com.xingray.observer.Patch

class Room(var name: String, var id: String, var area: Int) : ObservableList<Student> {

    companion object {
        var FIELD_NAME = "Room#name"
        var FIELD_ID = "Room#id"
        var FIELD_AREA = "Room#area"
    }

    var students: MutableList<Student?>? = null

    override fun toString(): String {
        return "Room{" +
                "name='" + name + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", area=" + area +
                ", students=" + students +
                '}'.toString()
    }

    override fun changeList(list: MutableList<Student?>?): Pair<Boolean, List<Student?>?>? {
        if (students === list) {
            return null
        }
        val last = students
        students = list
        return Pair(true, last)
    }

    override fun insertItems(position: Int, items: List<Student?>): Boolean {
        return students?.addAll(position, items) ?: false
    }

    override fun removeItems(position: Int, range: Int): Boolean {
        val removeCount = students?.remove(position, range) ?: return false
        return removeCount > 0
    }

    override fun getItem(position: Int): Student? {
        return students?.get(position)
    }

    override fun setItem(position: Int, item: Student?): Pair<Boolean, Student?>? {
        val list = students ?: return null
        val last = list[position]
        if (last === item) {
            return null
        }
        list[position] = item
        return Pair(true, last)
    }

    override fun size(): Int {
        return students?.size ?: 0
    }

    override fun applyPatch(patch: Patch): Pair<Boolean, Any?>? {
        when (patch.name) {
            FIELD_NAME -> {
                val name: String = patch.getPayload()
                val last = this.name
                if (last != name) {
                    this.name = name
                    return Pair(true, last)
                }
            }

            FIELD_AREA -> {
                val area: Int = patch.getPayload()
                val last = this.area
                if (last != area) {
                    this.area = area
                    return Pair(true, last)
                }
            }

            FIELD_ID -> {
                val id: String = patch.getPayload()
                val last = this.id
                if (last != id) {
                    this.id = id
                    return Pair(true, last)
                }
            }
        }
        return null
    }
}
