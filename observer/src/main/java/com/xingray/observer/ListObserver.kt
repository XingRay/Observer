package com.xingray.observer

/**
 * 列表数据观察者
 *
 * @param <T> 数据类型
 */
interface ListObserver<E : Observable?, T : ObservableList<E>> : Observer<T> {

    fun onInsert(position: Int, insertList: List<E>)

    fun onRemove(position: Int, range: Int)

    fun onItemUpdate(position: Int, patches: List<Patch>)
}
