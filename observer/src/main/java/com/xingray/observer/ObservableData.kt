package com.xingray.observer


import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 可观察的数据
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:36
 */
open class ObservableData<T : Observable, O : Observer<T>>(
    private val dataWrapper: ObservableWrapper<T>
) {

    constructor(t: T?) : this(ObservableWrapper(t))

    constructor() : this(null)

    val observers by lazy { SetMap<Executor?, O>() }

    open fun addObserver(executor: Executor, observer: O) {
        observers.add(executor, observer)
    }

    open fun removeObserver(observer: O) {
        observers.remove(observer)
    }

    fun get(): T? {
        return dataWrapper.t
    }

    fun set(t: T?): Pair<Boolean, T?>? {
        val pair = dataWrapper.set(t)
        if (pair != null && pair.first) {
            observers.traverseOnExecutor {
                it.onChanged(t, pair.second)
            }
        }
        return pair
    }

    fun update(patches: List<Patch>?): List<Pair<Patch, Any?>>? {
        val pairs = dataWrapper.update(patches)
        if (pairs?.isNotEmpty() == true) {
            observers.traverseOnExecutor { observer ->
                pairs.forEach { pair ->
                    observer.onUpdated(pair.first, pair.second)
                }
            }
        }
        return pairs
    }

    fun update(patch: Patch): Pair<Boolean, Any?>? {
        val pair = dataWrapper.update(patch)
        if (pair != null && pair.first) {
            observers.traverseOnExecutor { observer ->
                observer.onUpdated(patch, pair.second)
            }
        }
        return pair
    }
}
