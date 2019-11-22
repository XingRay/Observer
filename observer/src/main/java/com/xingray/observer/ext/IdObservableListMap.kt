package com.xingray.observer.ext

import com.xingray.observer.ListObserver
import com.xingray.observer.Observable
import com.xingray.observer.ObservableList
import com.xingray.setmap.SetMap2
import java.util.concurrent.Executor

/**
 * 可观察的数据表，以[String]为`key`,[ObservableList]为`value`的数据结构
 *
 * @author : leixing
 * @date : 2019/11/22 23:02
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */
class IdObservableListMap<E : Observable, T : ObservableList<E>> {

    private val map by lazy { mutableMapOf<String, T?>() }
    private val observers by lazy { SetMap2<String, Executor, ListObserver<E, T>>() }

    fun addObserver(id: String, executor: Executor, observer: ListObserver<E, T>) {
        observers.add(id, executor, observer)
    }

    fun removeObserver(observer: ListObserver<E, T>) {
        observers.remove(observer)
    }

    fun get(id: String): ObservableList<E>? {
        return map[id]
    }

    fun set(id: String, list: T?) {
        val last = map[id]
        map[id] = list

        observers[id]?.traverse { executor, listObserver ->
            executor.execute {
                listObserver.onChanged(list, last)
            }
        }
    }
}