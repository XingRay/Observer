package com.xingray.observer

import kotlin.collections.MutableList

/**
 * 可观察对象
 *
 * @author : leixing
 * @date : 2019/10/21 22:10
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface MutableObject {
    fun applyPatch(patch: Patch): Pair<Boolean, *>?

    @JvmDefault
    fun applyPatches(patches: List<Patch>?): List<Pair<Patch, Any?>>? {
        if (patches == null || patches.isEmpty()) {
            return null
        }
        var appliedPatches: MutableList<Pair<Patch, Any?>>? = null
        for (patch in patches) {
            val pair: Pair<Boolean, Any?>? = applyPatch(patch)
            if (pair != null && pair.first) {
                if (appliedPatches == null) {
                    appliedPatches = mutableListOf()
                }
                appliedPatches.add(Pair(patch, pair.second))
            }
        }
        return appliedPatches
    }
}