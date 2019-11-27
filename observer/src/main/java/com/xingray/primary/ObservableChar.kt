package com.xingray.primary

import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Char]
 *
 * @author : leixing
 * @date : 2019/11/26 22:49
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ObservableChar(private var value: Char) {

    constructor() : this(0.toChar())

    private val observers by lazy { SetMap<Executor?, (Char, Char) -> Unit>() }

    fun addObserver(executor: Executor, observer: CharObserver) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (Char, Char) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: CharObserver) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (Char, Char) -> Unit) {
        observers.remove(observer)
    }

    fun get(): Char {
        return value
    }

    fun set(value: Char): Pair<Boolean, Char>? {
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