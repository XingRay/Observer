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

    fun change(list: List<E>) {
        listDataWrapper.change(list)
    }

    fun insert(position: Int, insertList: List<E>): Boolean {
        val inserted = listDataWrapper.insert(position, insertList)
        if (inserted) {
            observers.traverseOnExecutor {
                it.onInsert(position, insertList)
            }
        }
        return inserted
    }

    fun remove(position: Int, range: Int): Boolean {
        val remove = listDataWrapper.remove(position, range)
        if (remove) {
            observers.traverseOnExecutor {
                it.onRemove(position, range)
            }
        }
        return remove
    }

    fun updateItem(position: Int, patches: List<Patch>?): List<Patch>? {
        val appliedPatches = listDataWrapper.updateItem(position, patches)
        if (appliedPatches?.isNotEmpty() == true) {
            observers.traverseOnExecutor {
                it.onItemUpdate(position, appliedPatches)
            }
        }
        return appliedPatches
    }
}