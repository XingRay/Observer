package com.xingray.primary

import com.xingray.observer.traverseOnExecutor
import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察[Byte]
 *
 * @author : leixing
 * @date : 2019/11/26 22:49
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class ObservableByte(private var value: Byte) {

    constructor() : this(0)

    private val observers by lazy { SetMap<Executor, (Byte, Byte) -> Unit>() }

    fun addObserver(executor: Executor, observer: ByteObserver) {
        addObserver(executor, observer::onChanged)
    }

    fun addObserver(executor: Executor, observer: (Byte, Byte) -> Unit) {
        observers.add(executor, observer)
    }

    fun removeObserver(observer: ByteObserver) {
        removeObserver(observer::onChanged)
    }

    fun removeObserver(observer: (Byte, Byte) -> Unit) {
        observers.remove(observer)
    }

    fun get(): Byte {
        return value
    }

    fun set(value: Byte): Pair<Boolean, Byte>? {
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