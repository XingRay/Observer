package com.xingray.observer.field
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executor

/**
 * 观察者管理器
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:36
 */
open class ObserverManager<T> internal constructor() {

    private var mObserverMap: MutableMap<Executor?, MutableList<T>>? = null
    private var mExecutorMap: MutableMap<T, Executor?>? = null

    private val observerMap: MutableMap<Executor?, MutableList<T>>
        get() {
            var map: MutableMap<Executor?, MutableList<T>>? = mObserverMap
            if (map == null) {
                map = HashMap()
                mObserverMap = map
            }
            return map
        }

    private val executorMap: MutableMap<T, Executor?>
        get() {
            var map: MutableMap<T, Executor?>? = mExecutorMap
            if (map == null) {
                map = HashMap()
                mExecutorMap = map;
            }
            return map
        }

    fun addObserver(executor: Executor?, t: T) {
        val observerMap = observerMap
        val list = getObserverList(observerMap, executor)
        list?.add(t)

        val executorMap = executorMap
        executorMap.put(t, executor)
    }

    fun removeObserver(t: T) {
        val executorMap: MutableMap<T, Executor?> = mExecutorMap ?: return
        val executor = executorMap[t]
        val observerMap: MutableMap<Executor?, MutableList<T>>? = mObserverMap ?: return
        val list = observerMap?.get(executor) ?: return

        list.remove(t)
        executorMap.remove(t)
        if (list.isEmpty()) {
            observerMap.remove(executor)
        }
    }

    internal fun traversal(processor: (T) -> Unit) {
        val map: MutableMap<Executor?, MutableList<T>> = mObserverMap ?: return
        val iterator = map.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            val executor = entry.key
            val list = entry.value
            if (executor == null) {
                for (t in list) {
                    processor.invoke(t)
                }
                continue
            }
            executor.execute {
                for (t in list) {
                    processor.invoke(t)
                }
            }
        }
    }

    private fun getObserverList(
            map: MutableMap<Executor?, MutableList<T>>,
            executor: Executor?
    ): MutableList<T>? {
        var list: MutableList<T>? = map[executor]
        if (list == null) {
            list = CopyOnWriteArrayList()
            map.put(executor, list)
        }
        return list
    }
}