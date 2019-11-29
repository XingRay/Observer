package com.xingray.observer.observable

import com.xingray.observer.observer.ObjectObserver
import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Object]
 *
 * @author : leixing
 * @date : 2019/11/26 23:16
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ObservableObject<T>(private var value: T?) {
    constructor() : this(null)

    private val observers by lazy { SetMap<Executor?, (T?, T?) -> Unit>() }

    fun addObserver(executor: Executor, observer: ObjectObserver<T>) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (T?, T?) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: ObjectObserver<T>) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (T?, T?) -> Unit) {
        observers.remove(observer)
    }

    fun get(): T? {
        return value
    }

    fun set(value: T?): Pair<Boolean, T?>? {
        if (value === this.value) {
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