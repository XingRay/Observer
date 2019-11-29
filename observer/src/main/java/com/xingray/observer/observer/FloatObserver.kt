package com.xingray.observer.observer

/**
 * [Float]观察者
 *
 * @author : leixing
 * @date : 2019/11/26 22:53
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
interface FloatObserver {
    fun onChanged(current: Float, last: Float)
}