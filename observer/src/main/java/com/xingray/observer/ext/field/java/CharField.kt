package com.xingray.observer.ext.field.java

/**
 * 可观察的`Char`字段
 *
 * @author : leixing
 * @date : 2019/11/22 14:29
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class CharField(private var value: Char) {

    constructor() : this(0.toChar())

    fun get(): Char {
        return value
    }

    fun set(value: Char): Pair<Boolean, Any?>? {
        if (value == this.value) {
            return null
        }
        val last = this.value
        this.value = value
        return Pair(true, last)
    }

    override fun toString(): String {
        return value.toString()
    }
}