package com.xingray.sample

import com.xingray.observer.Observable
import com.xingray.observer.Patch

class Student(
    var name: String,
    var sex: Int,
    var age: Int,
    var mark: Int
) : Observable {

    override fun toString(): String {
        return "Student{" +
                "name='" + name + '\''.toString() +
                ", sex=" + sex +
                ", age=" + age +
                ", mark=" + mark +
                '}'.toString()
    }

    override fun applyPatches(patches: List<Patch>): List<Patch>? {
        return null
    }

    companion object {
        var FIELD_NAME: String = "name"
        var FIELD_AGE: String = "age"
    }
}
