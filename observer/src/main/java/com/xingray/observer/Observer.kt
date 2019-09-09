package com.xingray.observer

/**
 * 观察者
 *
 * @param <T> 类型
</T> */
interface Observer<T> {

    fun onChanged(t: T)

    fun onUpdated(patches: Array<Patch>)
}
