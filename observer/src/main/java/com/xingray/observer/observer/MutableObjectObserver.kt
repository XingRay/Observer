package com.xingray.observer.observer

import com.xingray.observer.Patch

/**
 * 观察者
 *
 */
interface MutableObjectObserver<T> {

    fun onChanged(current: T?, last: T?)

    fun onUpdated(patch: Patch, lastFiledValue: Any?)
}
