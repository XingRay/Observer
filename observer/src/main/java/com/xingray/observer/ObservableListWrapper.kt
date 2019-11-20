package com.xingray.observer

/**
 * 可观察列表对象包装器
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:39
 */
class ObservableListWrapper<E : Observable, T : ObservableList<E>>(list: T?) :
    ObservableWrapper<T>(list) {

    constructor() : this(null)

    fun change(list: MutableList<E?>?): Boolean {
        return t?.changeList(list) ?: false
    }

    fun insert(position: Int, list: List<E?>): Boolean {
        return t?.insertItems(position, list) ?: false
    }

    fun remove(position: Int, range: Int): Boolean {
        return t?.removeItems(position, range) ?: false
    }

    fun updateItem(position: Int, patches: List<Patch>?): List<Patch>? {
        if (patches == null || patches.isEmpty()) {
            return null
        }
        val list = t ?: return null
        return list.getItem(position)?.applyPatches(patches)
    }

    fun updateItem(position: Int, patch: Patch): Boolean {
        val list = t ?: return false
        val item = list.getItem(position) ?: return false
        return item.applyPatch(patch)
    }

    fun setItem(position: Int, e: E?): Boolean {
        val list = t ?: return false
        val value = list.getItem(position)
        if (value === e) {
            return false
        }

        list.setItem(position, e)
        return true
    }

    fun setItems(position: Int, items: List<E?>): Pair<Boolean, BooleanArray> {
        val itemsChanged = BooleanArray(items.size)
        var changed = false

        for (i in items.indices) {
            val set = setItem(position + i, items[i])
            itemsChanged[i] = set
            changed = changed || set
        }

        return Pair(changed, itemsChanged)
    }

    fun moveItems(fromIndex: Int, toIndex: Int, size: Int): Boolean {
        val list = t ?: return false
        return list.move(fromIndex, toIndex, size)
    }
}
