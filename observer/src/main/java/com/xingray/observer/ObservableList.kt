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
interface ObservableList<E : Observable?> : Observable {

    /**
     * 更新列表
     */
    fun change(list: MutableList<E>): Boolean

    /**
     * 插入数据
     */
    fun insert(position: Int, list: List<E>): Boolean

    /**
     * 删除数据
     */
    fun remove(position: Int, range: Int): Boolean

    /**
     * 获取元素
     */
    fun get(position: Int): E?
}