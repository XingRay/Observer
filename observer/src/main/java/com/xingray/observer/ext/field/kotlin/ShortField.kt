package com.xingray.observer.ext.field.kotlin

/**
 * 可观察的`Short`字段
 *
 * @author : leixing
 * @date : 2019/11/22 14:29
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class ShortField(private var value: Short) {

    constructor() : this(0)

    fun get(): Short {
        return value
    }

    fun set(value: Short): Pair<Boolean, Short>? {
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