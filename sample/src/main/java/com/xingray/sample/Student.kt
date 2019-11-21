package com.xingray.sample

import com.xingray.observer.Observable
import com.xingray.observer.Patch
import com.xingray.observer.field.ObservableField

class Student(
    name: String,
    sex: Int,
    age: Int,
    mark: Int
) : Observable {

    val name = ObservableField(name)
    val sex = ObservableField(sex)
    val age = ObservableField(age)
    val mark = ObservableField(mark)

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
                return age.set(patch.getPayload())
            }

            FIELD_NAME -> {
                return name.set(patch.getPayload())
            }
        }
        return null
    }
}
