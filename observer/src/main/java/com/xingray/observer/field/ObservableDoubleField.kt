package com.xingray.observer.field

/**
 * 可观察的`Double`字段
 *
 * @author : leixing
 * @date : 2019/11/22 14:29
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class ObservableDoubleField(private var value: Double) {

    constructor() : this(0.0)

    fun get(): Double {
        return value
    }

    fun set(value: Double): Pair<Boolean, Double>? {
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