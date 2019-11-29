package com.xingray.observer.field

import com.xingray.observer.*

/**
 * 可观察列表对象包装器
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:39
 */
class MutableListField<E : MutableObject, T : com.xingray.observer.MutableList<E>>(list: T?) :
    MutableObjectField<T>(list) {

    constructor() : this(null)

    fun change(list: MutableList<E?>?): Pair<Boolean, List<E?>?>? {
        return t?.changeList(list)
    }

    fun insert(position: Int, list: List<E?>): Boolean {
        return t?.insertItems(position, list) ?: false
    }

    fun remove(position: Int, range: Int): Boolean {
        return t?.removeItems(position, range) ?: false
    }

    fun updateItem(position: Int, patches: List<Patch>?): List<Pair<Patch, Any?>>? {
        if (patches == null || patches.isEmpty()) {
            return null
        }
        val list = t ?: return null
        return list.getItem(position)?.applyPatches(patches)
    }

    fun updateItem(position: Int, patch: Patch): Pair<Boolean, Any?>? {
        val list = t ?: return null
        val item = list.getItem(position) ?: return null
        return item.applyPatch(patch)
    }

    fun setItem(position: Int, e: E?): Pair<Boolean, E?>? {
        val list = t ?: return null
        val value = list.getItem(position)
        if (value === e) {
            return null
        }

        return list.setItem(position, e)
    }

    fun setItems(position: Int, items: List<E?>): Array<Pair<Boolean, E?>?>? {
        var pairs: Array<Pair<Boolean, E?>?>? = null

        for (i in items.indices) {
            val pair = setItem(position + i, items[i])
            if (pair != null && pair.first) {
                if (pairs == null) {
                    pairs = Array(items.size) { null }
                }
                pairs[i] = pair
            }
        }

        return pairs
    }

    fun moveItems(fromIndex: Int, toIndex: Int, size: Int): Boolean {
        val list = t ?: return false
        return list.move(fromIndex, toIndex, size)
    }
}
