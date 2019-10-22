package com.xingray.sample

import com.xingray.observer.ObservableList
import com.xingray.observer.Patch

class Room(var name: String, var id: String, var area: Int) : ObservableList<Student> {

    var students: List<Student>? = null

    override fun toString(): String {
        return "Room{" +
                "name='" + name + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", area=" + area +
                ", students=" + students +
                '}'.toString()
    }

    override fun change(list: List<Student>): Boolean {
        return false
    }

    override fun insert(position: Int, list: List<Student>): Boolean {
        return false
    }

    override fun remove(position: Int, range: Int): Boolean {
        return false
    }

    override fun get(position: Int): Student? {
        return students?.get(position)
    }

    override fun applyPatches(patches: List<Patch>): List<Patch>? {
        return null
    }

    companion object {
        var FIELD_NAME = "name"
        var FIELD_ID = "id"
        var FIELD_AREA = "area"
    }
}
