package com.xingray.primary

/**
 * [Float]观察者
 *
 * @author : leixing
 * @date : 2019/11/26 22:53
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
interface FloatObserver {
    fun onChanged(current: Float, last: Float)
}