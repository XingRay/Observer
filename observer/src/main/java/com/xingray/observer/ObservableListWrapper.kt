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

    fun change(list: MutableList<E>): Boolean {
        return this.t?.change(list) ?: false
    }

    fun insert(position: Int, list: List<E>): Boolean {
        return this.t?.insert(position, list) ?: false
    }

    fun remove(position: Int, range: Int): Boolean {
        return this.t?.remove(position, range) ?: false
    }

    fun updateItem(position: Int, patches: List<Patch>?): List<Patch>? {
        if (patches == null || patches.isEmpty()) {
            return null
        }
        val list = this.t ?: return null
        return list.get(position)?.applyPatches(patches)
    }
}
