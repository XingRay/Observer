package com.xingray.observer.field

/**
 * 可观察的`Char`字段
 *
 * @author : leixing
 * @date : 2019/11/22 14:29
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class CharField(private var value: Char) {

    constructor() : this(0.toChar())

    fun get(): Char {
        return value
    }

    fun set(value: Char): Pair<Boolean, Char>? {
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