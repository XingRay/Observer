package com.xingray.observer

/**
 * 列表数据观察者
 *
 * @param <T> 数据类型
 */
interface ListObserver<E : Observable?, T : ObservableList<E>> : Observer<T> {

    fun onListChanged(list: List<E>)

    fun onListInserted(position: Int, insertList: List<E>)

    fun onListRemoved(position: Int, range: Int)

    fun onListItemUpdated(position: Int, appliedPatches: List<Patch>)

}
