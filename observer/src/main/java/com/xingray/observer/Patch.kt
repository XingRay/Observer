package com.xingray.observer

import java.lang.reflect.Field

/**
 * 修改补丁
 */
class Patch(var field: Field, var payload: Any?) {

    override fun toString(): String {
        return "Patch{" +
                "field=" + field +
                ", payload=" + payload +
                '}'.toString()
    }
}
