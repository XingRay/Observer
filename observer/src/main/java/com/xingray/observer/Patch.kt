package com.xingray.observer

import java.util.*

/**
 * 补丁
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/9/17 19:54
 */
class Patch internal constructor(val name: String, private val payload: Any) {

    fun <T> getPayload(): T {
        @Suppress("UNCHECKED_CAST")
        return payload as T
    }

    override fun toString(): String {
        return "Patch{" +
                "name=" + name +
                ", payload=" + payload +
                '}'.toString()
    }

    class Builder {

        private val map by lazy { mutableMapOf<String, Any>() }

        fun add(name: String, payload: Any): Builder {
            map[name] = payload
            return this
        }

        fun build(): List<Patch>? {
            if (map.isEmpty()) {
                return null
            }
            val patches = ArrayList<Patch>(map.size)
            for ((key, value) in map) {
                patches.add(Patch(key, value))
            }
            return patches
        }
    }

    companion object {

        fun builder(): Builder {
            return Builder()
        }
    }
}
