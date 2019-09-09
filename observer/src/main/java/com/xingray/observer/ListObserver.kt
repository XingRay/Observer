package com.xingray.observer

/**
 * 列表数据观察者
 *
 * @param <T> 数据类型
 */
interface ListObserver<T> : Observer<MutableList<T>> {

    fun onInsert(position: Int, addList: MutableList<T>)

    fun onRemove(position: Int, range: Int)

    fun onItemUpdate(position: Int, patches: Array<Patch>)
}
