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
