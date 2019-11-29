package com.xingray.observer.observable

import com.xingray.observer.*
import com.xingray.observer.observer.MutableObjectObserver
import com.xingray.setmap.SetMap2
import java.util.concurrent.Executor

/**
 * 可观察的数据表，以[K]为`key`,[ObservableList]为`value`的数据结构
 *
 * @author : leixing
 * @date : 2019/11/27 20:50
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ObserverableMap<K, V : MutableObject> {

    private val map by lazy { mutableMapOf<K, V?>() }
    private val observers by lazy { SetMap2<K, Executor?, MutableObjectObserver<V>>() }

    fun addObserver(key: K, executor: Executor, observer: MutableObjectObserver<V>) {
        observers.add(key, executor, observer)
    }

    fun removeObserver(observer: MutableObjectObserver<V>) {
        observers.remove(observer)
    }

    fun get(key: K): V? {
        return map[key]
    }

    fun set(key: K, value: V?): Pair<Boolean, V?>? {
        val last = map[key]
        if (value === last) {
            return null
        }
        map[key] = value
        val pair = Pair(true, last)
        observers.traverseOnExecutor(key) { observer ->
            observer.onChanged(value, last)
        }
        return pair
    }

    fun update(key: K, patch: Patch): Pair<Boolean, Any?>? {
        val value = map[key] ?: return null
        val pair = value.applyPatch(patch)
        if (pair != null && pair.first) {
            observers.traverseOnExecutor(key) { observer ->
                observer.onUpdated(patch, pair.second)
            }
        }
        return pair
    }

    fun update(key: K, patches: List<Patch>?): List<Pair<Patch, Any?>>? {
        val value = map[key] ?: return null
        val pairs = value.applyPatches(patches)
        if (pairs?.isNotEmpty() == true) {
            observers.traverseOnExecutor(key) { observer ->
                pairs.forEach { pair ->
                    observer.onUpdated(pair.first, pair.second)
                }
            }
        }
        return pairs
    }
}