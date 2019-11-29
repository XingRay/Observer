package com.xingray.observer.observable

import com.xingray.observer.*
import com.xingray.observer.observer.ListObserver
import com.xingray.setmap.SetMap2
import java.util.concurrent.Executor

/**
 * 可观察的数据表，以[K]为`key`,[ObservableList]为`value`的数据结构
 *
 * @author : leixing
 * @date : 2019/11/22 23:02
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ObservableListMap<K, E : MutableObject, T : ObservableList<E>> {

    private val map by lazy { mutableMapOf<K, T?>() }
    private val observers by lazy { SetMap2<K, Executor?, ListObserver<E, T>>() }

    fun addObserver(key: K, executor: Executor, observer: ListObserver<E, T>) {
        observers.add(key, executor, observer)
    }

    fun removeObserver(observer: ListObserver<E, T>) {
        observers.remove(observer)
    }

    fun get(key: K): ObservableList<E>? {
        return map[key]
    }

    fun set(key: K, list: T?) {
        val last = map[key]
        map[key] = list

        observers.traverseOnExecutor(key) { listObserver ->
            listObserver.onChanged(list, last)
        }
    }

    fun update(key: K, patches: List<Patch>?): List<Pair<Patch, Any?>>? {
        val list = map[key] ?: return null
        val pairs = list.applyPatches(patches)
        if (pairs?.isNotEmpty() == true) {
            observers.traverseOnExecutor(key) { listObserver ->
                pairs.forEach { pair ->
                    listObserver.onUpdated(pair.first, pair.second)
                }
            }
        }
        return pairs
    }

    fun update(key: K, patch: Patch): Pair<Boolean, Any?>? {
        val list = map[key] ?: return null
        val pair = list.applyPatch(patch)
        if (pair?.first == true) {
            observers.traverseOnExecutor(key) { listObserver ->
                listObserver.onUpdated(patch, pair.second)
            }
        }
        return pair
    }

    fun insertItem(key: K, item: E?): Boolean {
        val list = map[key] ?: return false
        val position = list.size()
        return insertItem(key, position, item)
    }

    fun insertItem(key: K, position: Int, item: E?): Boolean {
        return insertItems(key, position, arrayListOf(item))
    }

    fun insertItems(key: K, items: List<E?>): Boolean {
        val list = map[key] ?: return false
        val position = list.size()
        return insertItems(key, position, items)
    }

    fun insertItems(key: K, position: Int, items: List<E?>): Boolean {
        val list = map[key] ?: return false
        val inserted = list.insertItems(position, items)
        if (inserted) {
            observers.traverseOnExecutor(key) {
                it.onItemsInserted(position, items)
            }
        }
        return inserted
    }

    fun removeItem(key: K, position: Int): Boolean {
        return removeItems(key, position, 1)
    }

    fun removeItems(key: K, position: Int, range: Int): Boolean {
        val list = map[key] ?: return false
        val remove = list.removeItems(position, range)
        if (remove) {
            observers.traverseOnExecutor(key) {
                it.onItemsRemoved(position, range)
            }
        }
        return remove
    }

    fun changeItems(key: K, items: MutableList<E?>?): Pair<Boolean, List<E?>?>? {
        val list = map[key] ?: return null
        val pair = list.changeList(items)
        if (pair != null && pair.first) {
            observers.traverseOnExecutor(key) {
                it.onListChanged(items, pair.second)
            }
        }
        return pair
    }


    fun clear(key: K) {
        changeItems(key, null)
    }

    fun setItem(key: K, position: Int, e: E?): Pair<Boolean, E?>? {
        val list = map[key] ?: return null
        val pair = list.setItem(position, e)
        if (pair != null && pair.first) {
            observers.traverseOnExecutor(key) {
                it.onItemChanged(position, e, pair.second)
            }
        }
        return pair
    }

    fun setItems(key: K, position: Int, items: List<E?>): Array<Pair<Boolean, E?>?>? {
        val list = map[key] ?: return null
        val pairs = list.setItems(position, items)
        if (pairs?.isNotEmpty() == true) {
            observers.traverseOnExecutor(key) {
                for (i in pairs.indices) {
                    val pair = pairs[i]
                    if (pair != null && pair.first) {
                        val index = position + i
                        it.onItemChanged(index, items[index], pair.second)
                    }
                }
            }
        }
        return pairs
    }

    fun getItem(key: K, position: Int): E? {
        return map[key]?.getItem(position)
    }

    fun updateItem(key: K, position: Int, patch: Patch): Pair<Boolean, Any?>? {
        val list = map[key] ?: return null
        val pair = list.updateItem(position, patch)
        if (pair?.first == true) {
            observers.traverseOnExecutor(key) { listObserver ->
                listObserver.onItemUpdated(position, patch, pair.second)
            }
        }
        return pair
    }

    fun updateItem(key: K, position: Int, patches: List<Patch>?): List<Pair<Patch, Any?>>? {
        val list = map[key] ?: return null
        val pairs = list.updateItem(position, patches)
        if (pairs?.isNotEmpty() == true) {
            observers.traverseOnExecutor(key) { listObserver ->
                pairs.forEach { pair ->
                    listObserver.onItemUpdated(position, pair.first, pair.second)
                }
            }
        }
        return pairs
    }

    fun moveItems(key: K, fromIndex: Int, toIndex: Int, size: Int): Boolean {
        val list = map[key] ?: return false
        val moved = list.moveItems(fromIndex, toIndex, size)
        if (moved) {
            observers.traverseOnExecutor(key) {
                it.onItemsMoved(fromIndex, toIndex, size)
            }
        }
        return moved
    }
}