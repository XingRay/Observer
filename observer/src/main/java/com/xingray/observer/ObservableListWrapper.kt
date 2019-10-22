package com.xingray.observer

/**
 * 可观察列表对象包装器
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:39
 */
class ObservableListWrapper<T : Observable?>(var list: ObservableList<T>?) :
    ObservableWrapper<ObservableList<T>>(list) {

    constructor() : this(null)

    fun change(list: List<T>): Boolean {
        return this.list?.change(list) ?: false
    }

    fun insert(position: Int, list: List<T>): Boolean {
        return this.list?.insert(position, list) ?: false
    }

    fun remove(position: Int, range: Int): Boolean {
        return this.list?.remove(position, range) ?: false
    }

    fun updateItem(position: Int, patches: List<Patch>?): List<Patch>? {
        if (patches == null || patches.isEmpty()) {
            return null
        }
        val list = this.list ?: return null
        return list.get(position)?.applyPatches(patches)
    }
}
