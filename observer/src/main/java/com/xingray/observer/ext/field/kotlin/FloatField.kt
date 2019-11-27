package com.xingray.observer.ext.field.kotlin

/**
 * 可观察的`Float`字段
 *
 * @author : leixing
 * @date : 2019/11/22 14:29
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class FloatField(private var value: Float) {

    constructor() : this(0f)

    fun get(): Float {
        return value
    }

    fun set(value: Float): Pair<Boolean, Float>? {
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