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
    fun applyPatch(patch: Patch): Pair<Boolean, Any?>?
}