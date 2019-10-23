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
        var appliedPatches: MutableList<Patch>? = null

        patches.forEach {
            var applied = false
            when (it.name) {

                FIELD_AGE -> {
                    val age: Int = it.getPayload()
                    if (this.age != age) {
                        this.age = age
                        applied = true
                    }
                }

                FIELD_NAME -> {
                    val name: String = it.getPayload()
                    if (this.name != name) {
                        this.name = name
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
        var FIELD_NAME: String = "name"
        var FIELD_AGE: String = "age"
    }
}
