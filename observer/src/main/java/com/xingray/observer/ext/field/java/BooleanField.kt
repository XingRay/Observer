package com.xingray.observer.ext.field.java

/**
 * 可观察的`Boolean`字段
 *
 * @author : leixing
 * @date : 2019/11/22 14:29
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class BooleanField(private var value: Boolean) {

    constructor() : this(false)

    fun get(): Boolean {
        return value
    }

    fun set(value: Boolean): Pair<Boolean, Any?>? {
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