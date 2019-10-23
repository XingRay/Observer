package com.xingray.sample

import com.xingray.observer.Patch

/**
 * xxx
 *
 * @author : leixing
 * @date : 2019/10/23 13:39
 * @version : 1.0.0
 * mail : leixing@baidu.com
 *
 */


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