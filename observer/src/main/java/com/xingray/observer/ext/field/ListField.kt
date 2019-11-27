package com.xingray.observer.ext.field

import com.xingray.observer.ext.field.kotlin.TypeField
import com.xingray.observer.remove

/**
 * 可观察的列表字段
 *
 * @author : leixing
 * @date : 2019/11/22 0:54
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
class ListField<T>(value: MutableList<T?>?) :
    TypeField<MutableList<T?>>(value) {

    constructor() : this(null)

    fun changeList(list: MutableList<T?>?): Pair<Boolean, List<T?>?>? {
        if (value === list) {
            return null
        }
        val last = value
        value = list
        return Pair(true, last)
    }

    fun insertItems(position: Int, items: List<T?>): Boolean {
        return value?.addAll(position, items) ?: false
    }

    fun removeItems(position: Int, range: Int): Boolean {
        val removeCount = value?.remove(position, range) ?: return false
        return removeCount > 0
    }

    fun getItem(position: Int): T? {
        return value?.get(position)
    }

    fun setItem(position: Int, item: T?): Pair<Boolean, T?>? {
        val list = value ?: return null
        val last = list[position]
        if (last === item) {
            return null
        }
        list[position] = item
        return Pair(true, last)
    }

    fun size(): Int {
        return value?.size ?: 0
    }
}