package com.xingray.observer

/**
 * 可观察者对象包装器
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:35
 */
open class ObservableWrapper<T : Observable>(var t: T?) {

    constructor() : this(null)

    fun set(t: T?): Boolean {
        if (this.t == t) {
            return false
        }
        this.t = t
        return true
    }

    fun update(patches: List<Patch>?): List<Patch>? {
        if (patches == null || patches.isEmpty()) {
            return null
        }

        val t: T = this.t ?: return null
        return t.applyPatches(patches)
    }
}
