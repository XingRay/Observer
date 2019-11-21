package com.xingray.sample

import com.xingray.observer.Observable
import com.xingray.observer.Patch

class Student(
    var name: String,
    var sex: Int,
    var age: Int,
    var mark: Int
) : Observable {

    companion object {
        var FIELD_NAME: String = "Student#name"
        var FIELD_AGE: String = "Student#age"
    }

    override fun toString(): String {
        return "Student{" +
                "name='" + name + '\''.toString() +
                ", sex=" + sex +
                ", age=" + age +
                ", mark=" + mark +
                '}'.toString()
    }

    override fun applyPatch(patch: Patch): Pair<Boolean, Any?>? {
        when (patch.name) {

            FIELD_AGE -> {
                val age: Int = patch.getPayload()
                val last = this.age
                if (last != age) {
                    this.age = age
                    return Pair(true, last)
                }
            }

            FIELD_NAME -> {
                val name: String = patch.getPayload()
                val last = this.name
                if (last != name) {
                    this.name = name
                    return Pair(true, last)
                }
            }
        }

        return null
    }
}
