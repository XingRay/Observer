package com.xingray.observer

/**
 * 列表数据观察者
 *
 * @param <T> 数据类型
 */
interface ListObserver<E : Observable, T : ObservableList<E>> : Observer<T> {

    /**
     * 列表的内容内替换了
     */
    fun onListChanged(current: List<E?>?, last: List<E?>?)

    /**
     * 列表中的元素被替换了
     */
    fun onItemChanged(position: Int, current: E?, last: E?)

    /**
     * 列表中的元素更新了（字段的值变化了）
     */
    fun onItemUpdated(position: Int, appliedPatch: Patch, lastFiledValue: Any?)

    /**
     * 有元素插入列表了
     */
    fun onItemsInserted(position: Int, insertList: List<E?>)

    /**
     * 有元素从列表中移除了
     */
    fun onItemsRemoved(position: Int, range: Int)

    /**
     * 有元素在列表中发生了移动
     */
    fun onItemsMoved(fromIndex: Int, toIndex: Int, size: Int) {

    }
}
