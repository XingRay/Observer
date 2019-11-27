package com.xingray.primary

/**
 * [Object]观察者
 *
 * @author : leixing
 * @date : 2019/11/26 22:53
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface ObjectObserver<T> {
    fun onChanged(current: T?, last: T?)
}