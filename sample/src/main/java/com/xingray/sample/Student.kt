package com.xingray.sample

import com.xingray.observer.MutableObject
import com.xingray.observer.Patch
import com.xingray.observer.field.ObjectField

class Student(
    name: String,
    sex: Int,
    age: Int,
    mark: Int
) : MutableObject {

    val name = ObjectField(name)
    val sex = ObjectField(sex)
    val age = ObjectField(age)
    val mark = ObjectField(mark)

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
