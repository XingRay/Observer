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

    fun set(t: T?): Pair<Boolean, T?>? {
        if (this.t === t) {
            return null
        }
        val last = this.t
        this.t = t
        return Pair(true, last)
    }

    fun update(patches: List<Patch>?): List<Pair<Patch, Any?>>? {
        if (patches == null || patches.isEmpty()) {
            return null
        }

        val t: T = this.t ?: return null
        return t.applyPatches(patches)
    }

    fun update(patch: Patch): Pair<Boolean, Any?>? {
        val t: T = this.t ?: return null
        return t.applyPatch(patch)
    }
}
