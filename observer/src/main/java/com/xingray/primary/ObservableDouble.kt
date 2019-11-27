package com.xingray.primary

import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Double]
 *
 * @author : leixing
 * @date : 2019/11/26 22:49
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ObservableDouble(private var value: Double) {

    constructor() : this(0.0)

    private val observers by lazy { SetMap<Executor?, (Double, Double) -> Unit>() }

    fun addObserver(executor: Executor, observer: DoubleObserver) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (Double, Double) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: DoubleObserver) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (Double, Double) -> Unit) {
        observers.remove(observer)
    }

    fun get(): Double {
        return value
    }

    fun set(value: Double): Pair<Boolean, Double>? {
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