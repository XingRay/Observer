package com.xingray.observer.observer

/**
 * [Short]观察者
 *
 * @author : leixing
 * @date : 2019/11/26 22:53
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface ShortObserver {
    fun onChanged(current: Short, last: Short)
}