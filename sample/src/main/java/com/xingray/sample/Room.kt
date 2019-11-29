package com.xingray.sample

import com.xingray.observer.MutableList
import com.xingray.observer.Patch
import com.xingray.observer.field.ObjectField

class Room(name: String, id: String, area: Int) :
    MutableList<Student> {

    companion object {
        var FIELD_NAME = "Room#name"
        var FIELD_ID = "Room#id"
        var FIELD_AREA = "Room#area"
    }

    val name = ObjectField(name)
    val id = ObjectField(id)
    val area = ObjectField(area)

    var students = _root_ide_package_.com.xingray.observer.field.ListField<Student>()

    override fun toString(): String {
        return "Room{" +
                "name='" + name + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", area=" + area +
                ", students=" + students +
                '}'.toString()
    }

    override fun changeList(list: kotlin.collections.MutableList<Student?>?): Pair<Boolean, List<Student?>?>? {
        return students.changeList(list)
    }

    override fun insertItems(position: Int, items: List<Student?>): Boolean {
        return students.insertItems(position, items)
    }

    override fun removeItems(position: Int, range: Int): Boolean {
        return students.removeItems(position, range)
    }

    override fun getItem(position: Int): Student? {
        return students.getItem(position)
    }

    override fun setItem(position: Int, item: Student?): Pair<Boolean, Student?>? {
        return students.setItem(position, item)
    }

    override fun size(): Int {
        return students.size()
    }

    //    override fun applyPatch(patch: Patch): Pair<Boolean, Any?>? {
//        when (patch.name) {
//            FIELD_NAME -> {
//                return name.set(patch.getPayload())
//            }
//
//            FIELD_AREA -> {
//                return area.set(patch.getPayload())
//            }
//
//            FIELD_ID -> {
//                return id.set(patch.getPayload())
//            }
//        }
//        return null
//    }
    override fun applyPatch(patch: Patch): Pair<Boolean, *>? {
        when (patch.name) {
            FIELD_NAME -> {
                return name.set(patch.getPayload())
            }

            FIELD_AREA -> {
                return area.set(patch.getPayload())
            }

            FIELD_ID -> {
                return id.set(patch.getPayload())
            }
        }
        return null
    }
}
