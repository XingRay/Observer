package com.xingray.sample

import com.xingray.observer.ObservableList
import com.xingray.observer.Patch

class Room(var name: String, var id: String, var area: Int) : ObservableList<Student> {

    var students: MutableList<Student>? = null

    override fun toString(): String {
        return "Room{" +
                "name='" + name + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", area=" + area +
                ", students=" + students +
                '}'.toString()
    }

    override fun change(list: MutableList<Student>): Boolean {
        if (students === list) {
            return false
        }
        students = list
        return true
    }

    override fun insert(position: Int, list: List<Student>): Boolean {
        students?.addAll(position, list) ?: return false
        return true
    }

    override fun remove(position: Int, range: Int): Boolean {
        val removeCount = students?.remove(position, range) ?: return false
        return removeCount > 0
    }

    override fun get(position: Int): Student? {
        return students?.get(position)
    }

    override fun applyPatches(patches: List<Patch>): List<Patch>? {
        var appliedPatches: MutableList<Patch>? = null

        patches.forEach {
            var applied = false
            when (it.name) {
                FIELD_NAME -> {
                    val name: String = it.getPayload()
                    if (this.name != name) {
                        this.name = name
                        applied = true
                    }
                }

                FIELD_AREA -> {
                    val area: Int = it.getPayload()
                    if (this.area != area) {
                        this.area = area
                        applied = true
                    }
                }

                FIELD_ID -> {
                    val id: String = it.getPayload()
                    if (this.id != id) {
                        this.id = id
                        applied = true
                    }
                }
            }

            if (applied) {
                if (appliedPatches == null) {
                    appliedPatches = mutableListOf()
                }
                appliedPatches?.add(it)
            }
        }

        return appliedPatches
    }

    companion object {
        var FIELD_NAME = "name"
        var FIELD_ID = "id"
        var FIELD_AREA = "area"
    }
}
