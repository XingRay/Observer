package com.xingray.primary

import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Int]
 *
 * @author : leixing
 * @date : 2019/11/26 22:49
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ObservableInt(private var value: Int) {

    constructor() : this(0)

    private val observers by lazy { SetMap<Executor?, (Int, Int) -> Unit>() }

    fun addObserver(executor: Executor, observer: IntObserver) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (Int, Int) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: IntObserver) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (Int, Int) -> Unit) {
        observers.remove(observer)
    }

    fun get(): Int {
        return value
    }

    fun set(value: Int): Pair<Boolean, Int>? {
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