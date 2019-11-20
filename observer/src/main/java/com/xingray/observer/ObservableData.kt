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

    val observers by lazy { SetMap<Executor, O>() }

    open fun addObserver(executor: Executor, observer: O) {
        observers.add(executor, observer)
    }

    open fun removeObserver(observer: O) {
        observers.remove(observer)
    }

    fun get(): T? {
        return dataWrapper.t
    }

    fun set(t: T?): Boolean {
        val set = dataWrapper.set(t)
        if (set) {
            observers.traverseOnExecutor {
                it.onChanged(t)
            }
        }
        return set
    }

    fun update(patches: List<Patch>?): List<Patch>? {
        val appliedPatches = dataWrapper.update(patches)
        if (appliedPatches?.isNotEmpty() == true) {
            observers.traverseOnExecutor { observer ->
                appliedPatches.forEach { patch ->
                    observer.onUpdated(patch)
                }
            }
        }
        return appliedPatches
    }

    fun update(patch: Patch): Boolean {
        val applied = dataWrapper.update(patch)
        if (applied) {
            observers.traverseOnExecutor { observer ->
                observer.onUpdated(patch)
            }
        }
        return applied
    }
}
