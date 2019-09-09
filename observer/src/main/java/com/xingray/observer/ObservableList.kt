package com.xingray.observer

/**
 * 可观察列表
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/8 13:39
 */
class ObservableList<T> : ObserverManager<ListObserver<T>>(), ListObserver<T> {

    override fun onChanged(t: MutableList<T>) {
        traversal { observer -> observer.onChanged(t) }
    }

    override fun onUpdated(patches: Array<Patch>) {
        traversal { observer -> observer.onUpdated(patches) }
    }

    override fun onInsert(position: Int, addList: MutableList<T>) {
        traversal { observer -> observer.onInsert(position, addList) }
    }

    override fun onRemove(position: Int, range: Int) {
        traversal { observer -> observer.onRemove(position, range) }
    }

    override fun onItemUpdate(position: Int, patches: Array<Patch>) {
        traversal { observer -> observer.onItemUpdate(position, patches) }
    }
}
