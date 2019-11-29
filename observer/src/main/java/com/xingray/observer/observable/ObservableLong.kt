package com.xingray.observer.observable

import com.xingray.observer.observer.LongObserver
import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Long]
 *
 * @author : leixing
 * @date : 2019/11/26 22:49
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ObservableLong(private var value: Long) {

    constructor() : this(0)

    private val observers by lazy { SetMap<Executor?, (Long, Long) -> Unit>() }

    fun addObserver(executor: Executor, observer: LongObserver) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (Long, Long) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: LongObserver) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (Long, Long) -> Unit) {
        observers.remove(observer)
    }

    fun get(): Long {
        return value
    }

    fun set(value: Long): Pair<Boolean, Long>? {
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