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

fun Observable.applyPatches(patches: List<Patch>?): List<Pair<Patch, Any?>>? {
    if (patches == null || patches.isEmpty()) {
        return null
    }
    var appliedPatches: MutableList<Pair<Patch, Any?>>? = null
    for (patch in patches) {
        val pair: Pair<Boolean, Any?>? = applyPatch(patch)
        if (pair != null && pair.first) {
            if (appliedPatches == null) {
                appliedPatches = mutableListOf()
            }
            appliedPatches.add(Pair(patch, pair.second))
        }
    }
    return appliedPatches
}

fun <T> MutableList<T>.remove(index: Int, size: Int): Int {
    var removedCount = 0

    if (this is RandomAccess) {
        val lastIndex = index + size
        for (i in 0 until size) {
            removeAt(lastIndex - i)
            removedCount++
        }
    } else {
        var i = 0
        val iterator = iterator()
        while (i < index && iterator.hasNext()) {
            iterator.next()
            i++
        }

        if (i < index) {
            return removedCount
        }

        i = 0
        while (i < size && iterator.hasNext()) {
            iterator.next()
            iterator.remove()
            removedCount++
            i++
        }
    }

    return removedCount
}