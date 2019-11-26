package com.xingray.primary

/**
 * [Byte]观察者
 *
 * @author : leixing
 * @date : 2019/11/26 22:53
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
interface ByteObserver {
    fun onChanged(current: Byte, last: Byte)
}