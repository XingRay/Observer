package com.xingray.observer.field.java

/**
 * 可观察的`Byte`字段
 *
 * @author : leixing
 * @date : 2019/11/22 14:29
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class ByteField(private var value: Byte) {

    constructor() : this(0)

    fun get(): Byte {
        return value
    }

    fun set(value: Byte): Pair<Boolean, Any?>? {
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