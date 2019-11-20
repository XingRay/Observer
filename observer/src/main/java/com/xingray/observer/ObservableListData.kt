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

    fun changeItems(list: MutableList<E?>?): Boolean {
        val changed = listDataWrapper.change(list)
        if (changed) {
            observers.traverseOnExecutor {
                it.onListChanged(list)
            }
        }
        return changed
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

    fun clear(): Boolean {
        return changeItems(null)
    }

    fun setItem(position: Int, e: E?): Boolean {
        val changed = listDataWrapper.setItem(position, e)
        if (changed) {
            observers.traverseOnExecutor {
                it.onItemChanged(position, e)
            }
        }
        return changed
    }

    fun setItems(position: Int, items: List<E?>): Boolean {
        val (changed, itemsChanged) = listDataWrapper.setItems(position, items)
        if (changed) {
            observers.traverseOnExecutor {
                for (i in itemsChanged.indices) {
                    if (itemsChanged[i]) {
                        val index = position + i
                        it.onItemChanged(index, items[index])
                    }
                }
            }
        }
        return changed
    }

    fun updateItem(position: Int, patches: List<Patch>?): List<Patch>? {
        val appliedPatches = listDataWrapper.updateItem(position, patches)
        if (appliedPatches?.isNotEmpty() == true) {
            observers.traverseOnExecutor { listObserver ->
                appliedPatches.forEach { patch ->
                    listObserver.onItemUpdated(position, patch)
                }
            }
        }
        return appliedPatches
    }

    fun updateItem(position: Int, patch: Patch): Boolean {
        val applied = listDataWrapper.updateItem(position, patch)
        if (applied) {
            observers.traverseOnExecutor { listObserver ->
                listObserver.onItemUpdated(position, patch)
            }
        }
        return applied
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