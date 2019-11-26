package com.xingray.primary

import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Float]
 *
 * @author : leixing
 * @date : 2019/11/26 22:49
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class ObservableFloat(private var value: Float) {

    constructor() : this(0f)

    private val observers by lazy { SetMap<Executor, (Float, Float) -> Unit>() }

    fun addObserver(executor: Executor, observer: FloatObserver) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (Float, Float) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: FloatObserver) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (Float, Float) -> Unit) {
        observers.remove(observer)
    }

    fun get(): Float {
        return value
    }

    fun set(value: Float): Pair<Boolean, Float>? {
        if (value == this.value) {
            return null
        }
        val last = this.value
        this.value = value

        observers.traverseOnExecutor {
            it.invoke(value, last)
        }

        return Pair(true, last)
    }
}