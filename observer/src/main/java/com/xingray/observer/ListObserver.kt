package com.xingray.observer

/**
 * 列表数据观察者
 *
 * @param <T> 数据类型
 */
interface ListObserver<E : Observable, T : ObservableList<E>> : Observer<T> {

    fun onListChanged(list: List<E?>?)

    fun onItemChanged(position: Int, e: E?)

    fun onItemUpdated(position: Int, appliedPatch: Patch)

    fun onItemsInserted(position: Int, insertList: List<E?>)

    fun onItemsRemoved(position: Int, range: Int)

    fun onItemsMoved(fromIndex: Int, toIndex: Int, size: Int)
}
