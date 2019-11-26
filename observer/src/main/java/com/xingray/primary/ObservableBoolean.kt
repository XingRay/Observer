package com.xingray.primary

import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Boolean]
 *
 * @author : leixing
 * @date : 2019/11/26 22:49
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class ObservableBoolean(private var value: Boolean) {

    constructor() : this(false)

    private val observers by lazy { SetMap<Executor, (Boolean, Boolean) -> Unit>() }

    fun addObserver(executor: Executor, observer: BooleanObserver) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (Boolean, Boolean) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: BooleanObserver) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (Boolean, Boolean) -> Unit) {
        observers.remove(observer)
    }

    fun get(): Boolean {
        return value
    }

    fun set(value: Boolean): Pair<Boolean, Boolean>? {
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