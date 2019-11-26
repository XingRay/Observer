package com.xingray.primary

/**
 * [Int]观察者
 *
 * @author : leixing
 * @date : 2019/11/26 22:53
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
interface IntObserver {
    fun onChanged(current: Int, last: Int)
}