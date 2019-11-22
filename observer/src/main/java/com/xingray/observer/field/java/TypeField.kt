package com.xingray.observer.field.java

/**
 * 可观察字段
 *
 * @author : leixing
 * @date : 2019/11/22 0:36
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
open class TypeField<T>(internal var value: T?) {

    constructor() : this(null)

    fun get(): T? {
        return value
    }

    fun set(value: T?): Pair<Boolean, Any?>? {
        if (value === this.value) {
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