package com.xingray.primary

/**
 * [Boolean]观察者
 *
 * @author : leixing
 * @date : 2019/11/26 22:53
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface BooleanObserver {
    fun onChanged(current: Boolean, last: Boolean)
}