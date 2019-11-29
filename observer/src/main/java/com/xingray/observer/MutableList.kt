package com.xingray.observer

import kotlin.collections.MutableList

/**
 * 可观察列表对象
 *
 * @author : leixing
 * @date : 2019/10/21 22:09
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface MutableList<E : MutableObject> : MutableObject {

    /**
     * 获取指定位置数据
     */
    fun getItem(position: Int): E?

    /**
     * 设置指定位置数据
     */
    fun setItem(position: Int, item: E?): Pair<Boolean, E?>?

    /**
     * 获取列表元素数量
     */
    fun size(): Int

    /**
     * 替换整个列表
     */
    fun changeList(list: MutableList<E?>?): Pair<Boolean, List<E?>?>?

    /**
     * 插入数据
     */
    fun insertItems(position: Int, items: List<E?>): Boolean

    /**
     * 删除数据
     */
    fun removeItems(position: Int, range: Int): Boolean
}