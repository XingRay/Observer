package com.xingray.observer

/**
 * 可观察的列表数据
 *
 * @author : leixing
 * @date : 2019/10/22 17:33
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
@Suppress("unused")
class ObservableListData<E : Observable, T : ObservableList<E>>(
    private val listDataWrapper: ObservableListWrapper<E, T>
) : ObservableData<T, ListObserver<E, T>>(listDataWrapper) {

    constructor(t: T?) : this(ObservableListWrapper(t))
    constructor() : this(null)

    fun changeItems(items: MutableList<E?>?): Pair<Boolean, List<E?>?>? {
        val pair = listDataWrapper.change(items)
        if (pair != null && pair.first) {
            observers.traverseOnExecutor {
                it.onListChanged(items, pair.second)
            }
        }
        return pair
    }

    fun insertItems(position: Int, items: List<E?>): Boolean {
        val inserted = listDataWrapper.insert(position, items)
        if (inserted) {
            observers.traverseOnExecutor {
                it.onItemsInserted(position, items)
            }
        }
        return inserted
    }

    fun insertItems(items: List<E?>): Boolean {
        val list = listDataWrapper.t ?: return false
        return insertItems(list.size(), items)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun insertItem(position: Int, e: E): Boolean {
        return insertItems(position, arrayListOf(e))
    }

    fun insertItem(e: E): Boolean {
        val list = listDataWrapper.t ?: return false
        return insertItem(list.size(), e)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun removeItems(position: Int, range: Int): Boolean {
        val remove = listDataWrapper.remove(position, range)
        if (remove) {
            observers.traverseOnExecutor {
                it.onItemsRemoved(position, range)
            }
        }
        return remove
    }

    fun removeItem(position: Int): Boolean {
        return removeItems(position, 1)
    }

    fun clear() {
        changeItems(null)
    }

    fun setItem(position: Int, e: E?): Pair<Boolean, E?>? {
        val pair = listDataWrapper.setItem(position, e)
        if (pair != null && pair.first) {
            observers.traverseOnExecutor {
                it.onItemChanged(position, e, pair.second)
            }
        }
        return pair
    }

    fun setItems(position: Int, items: List<E?>): Array<Pair<Boolean, E?>?>? {
        val pairs = listDataWrapper.setItems(position, items)
        if (pairs?.isNotEmpty() == true) {
            observers.traverseOnExecutor {
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

    fun updateItem(position: Int, patches: List<Patch>?): List<Pair<Patch, Any?>>? {
        val pairs = listDataWrapper.updateItem(position, patches)
        if (pairs?.isNotEmpty() == true) {
            observers.traverseOnExecutor { listObserver ->
                pairs.forEach { pair ->
                    listObserver.onItemUpdated(position, pair.first, pair.second)
                }
            }
        }
        return pairs
    }

    fun updateItem(position: Int, patch: Patch): Pair<Boolean, Any?>? {
        val pair = listDataWrapper.updateItem(position, patch)
        if (pair?.first == true) {
            observers.traverseOnExecutor { listObserver ->
                listObserver.onItemUpdated(position, patch, pair.second)
            }
        }
        return pair
    }

    fun getItem(position: Int): E? {
        return listDataWrapper.t?.getItem(position)
    }

    fun moveItems(fromIndex: Int, toIndex: Int, size: Int): Boolean {
        val moved = listDataWrapper.moveItems(fromIndex, toIndex, size)
        if (moved) {
            observers.traverseOnExecutor {
                it.onItemsMoved(fromIndex, toIndex, size)
            }
        }
        return moved
    }
}