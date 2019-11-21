package com.xingray.observer

/**
 * 观察者
 *
 */
interface Observer<T> {

    fun onChanged(current: T?, last: T?)

    fun onUpdated(patch: Patch, lastFiledValue: Any?)
}
