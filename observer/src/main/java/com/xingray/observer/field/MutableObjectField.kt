package com.xingray.observer.field

import com.xingray.observer.MutableObject
import com.xingray.observer.Patch

/**
 * 可观察者对象包装器
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:35
 */
open class MutableObjectField<T : MutableObject>(var t: T?) : ObjectField<T>(t) {

    constructor() : this(null)

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
