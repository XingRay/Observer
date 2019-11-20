package com.xingray.observer

/**
 * 可观察对象
 *
 * @author : leixing
 * @date : 2019/10/21 22:10
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface Observable {

    fun applyPatches(patches: List<Patch>?): List<Patch>? {
        if (patches == null || patches.isEmpty()) {
            return null
        }
        var appliedPatches: MutableList<Patch>? = null
        for (patch in patches) {
            if (applyPatch(patch)) {
                if (appliedPatches == null) {
                    appliedPatches = mutableListOf()
                }
                appliedPatches.add(patch)
            }
        }
        return appliedPatches
    }

    fun applyPatch(patch: Patch): Boolean
}