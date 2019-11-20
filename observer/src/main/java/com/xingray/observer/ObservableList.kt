package com.xingray.observer

/**
 * 可观察列表对象
 *
 * @author : leixing
 * @date : 2019/10/21 22:09
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface ObservableList<E : Observable> : Observable {

    /**
     * 获取指定位置数据
     */
    fun getItem(position: Int): E?

    /**
     * 设置指定位置数据
     */
    fun setItem(position: Int, e: E?): E?

    /**
     * 获取列表元素数量
     */
    fun size(): Int

    /**
     * 替换整个列表
     */
    fun changeList(list: MutableList<E?>?): Boolean

    /**
     * 插入数据
     */
    fun insertItems(position: Int, items: List<E?>): Boolean

    /**
     * 删除数据
     */
    fun removeItems(position: Int, range: Int): Boolean
}