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
open class ObservableData<T : Observable, O : Observer<in T>>(
    private val dataWrapper: ObservableWrapper<in T>
) {

    val observers by lazy { SetMap<Executor, O>() }

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
            observers.traverseOnExecutor {
                it.onUpdated(appliedPatches)
            }
        }
        return appliedPatches
    }

    open fun addObserver(executor: Executor, observer: O) {
        observers.add(executor, observer)
    }

    open fun removeObserver(observer: O) {
        observers.remove(observer)
    }
}
