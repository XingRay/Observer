package com.xingray.observer

import com.xingray.setmap.SetMap
import java.util.concurrent.Executor

/**
 * 工具类
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/21 18:28
 */

fun <V> SetMap<Executor, V>.traverseOnExecutor(processor: (V) -> Unit) {
    if (isEmpty()) {
        return
    }

    for ((executor, values) in this) {
        if (values.isEmpty()) {
            continue
        }
        executor.execute {
            for (v in values) {
                processor.invoke(v)
            }
        }
    }
}

fun <E : Observable> ObservableList<E>.move(fromIndex: Int, toIndex: Int, size: Int): Boolean {
    if (fromIndex == toIndex
        || size <= 0
        || isOutOfIndex(fromIndex)
        || isOutOfIndex(fromIndex + size)
        || isOutOfIndex(toIndex)
        || isOutOfIndex(toIndex + size)
    ) {
        return false
    }

    val items = List(size) { index -> getItem(index) }
    if (fromIndex > toIndex) {
        for (i in fromIndex + size - 1 downTo toIndex + size) {
            setItem(i, getItem(i - size))
        }
    } else {
        for (i in fromIndex until toIndex) {
            setItem(i, getItem(i + size))
        }
    }
    for (i in 0 until size) {
        setItem(toIndex + i, items[i])
    }
    return true
}

fun <E : Observable> ObservableList<E>.isOutOfIndex(index: Int): Boolean {
    return index < 0 || index >= size()
}