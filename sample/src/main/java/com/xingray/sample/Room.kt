package com.xingray.sample

import com.xingray.observer.ObservableList
import com.xingray.observer.Patch
import com.xingray.observer.ext.field.kotlin.TypeField
import com.xingray.observer.ext.field.ListField

class Room(name: String, id: String, area: Int) : ObservableList<Student> {

    companion object {
        var FIELD_NAME = "Room#name"
        var FIELD_ID = "Room#id"
        var FIELD_AREA = "Room#area"
    }

    val name = TypeField(name)
    val id = TypeField(id)
    val area = TypeField(area)

    var students = ListField<Student>()

    override fun toString(): String {
        return "Room{" +
                "name='" + name + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", area=" + area +
                ", students=" + students +
                '}'.toString()
    }

    override fun changeList(list: MutableList<Student?>?): Pair<Boolean, List<Student?>?>? {
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

    override fun applyPatch(patch: Patch): Pair<Boolean, Any?>? {
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
