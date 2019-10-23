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
class ObservableListData<E : Observable, T : ObservableList<E>>(
    private val listDataWrapper: ObservableListWrapper<E>
) : ObservableData<T, ListObserver<E, T>>(listDataWrapper) {

    constructor(t: T?) : this(ObservableListWrapper(t))
    constructor() : this(null)

    fun changeList(list: MutableList<E>): Boolean {
        val changed = listDataWrapper.change(list)
        if (changed) {
            observers.traverseOnExecutor {
                it.onListChanged(list)
            }
        }
        return changed
    }

    fun insertList(position: Int, list: List<E>): Boolean {
        val inserted = listDataWrapper.insert(position, list)
        if (inserted) {
            observers.traverseOnExecutor {
                it.onListInserted(position, list)
            }
        }
        return inserted
    }

    fun removeList(position: Int, range: Int): Boolean {
        val remove = listDataWrapper.remove(position, range)
        if (remove) {
            observers.traverseOnExecutor {
                it.onListRemoved(position, range)
            }
        }
        return remove
    }

    fun updateListItem(position: Int, patches: List<Patch>?): List<Patch>? {
        val appliedPatches = listDataWrapper.updateItem(position, patches)
        if (appliedPatches?.isNotEmpty() == true) {
            observers.traverseOnExecutor {
                it.onListItemUpdated(position, appliedPatches)
            }
        }
        return appliedPatches
    }
}