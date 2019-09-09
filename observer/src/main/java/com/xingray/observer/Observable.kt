package com.xingray.observer

/**
 * 可观察者
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:35
 */
class Observable<T> : ObserverManager<Observer<T>>(), Observer<T> {

    override fun onChanged(t: T) {
        traversal { observer -> observer.onChanged(t) }
    }

    override fun onUpdated(patches: Array<Patch>) {
        traversal { observer -> observer.onUpdated(patches) }
    }
}
