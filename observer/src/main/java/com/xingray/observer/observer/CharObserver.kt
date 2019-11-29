package com.xingray.observer.observer

/**
 * [Char]观察者
 *
 * @author : leixing
 * @date : 2019/11/26 22:53
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface CharObserver {
    fun onChanged(current: Char, last: Char)
}