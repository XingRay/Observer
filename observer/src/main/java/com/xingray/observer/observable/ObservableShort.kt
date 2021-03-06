package com.xingray.observer.observable

import com.xingray.observer.observer.ShortObserver
import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Short]
 *
 * @author : leixing
 * @date : 2019/11/26 22:49
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ObservableShort(private var value: Short) {

    constructor() : this(0)

    private val observers by lazy { SetMap<Executor?, (Short, Short) -> Unit>() }

    fun addObserver(executor: Executor, observer: ShortObserver) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (Short, Short) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: ShortObserver) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (Short, Short) -> Unit) {
        observers.remove(observer)
    }

    fun get(): Short {
        return value
    }

    fun set(value: Short): Pair<Boolean, Short>? {
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