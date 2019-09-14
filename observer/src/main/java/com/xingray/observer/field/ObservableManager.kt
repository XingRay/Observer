package com.xingray.observer.field

import java.lang.reflect.Field

class ObservableManager {

    private val fieldObservableMap = mutableMapOf<Field, FieldObservable<*>?>()

    fun <T> getFieldObservable(field: Field): FieldObservable<T> {
        var observable = fieldObservableMap[field]
        if (observable == null) {
            observable = FieldObservable<T>()
            fieldObservableMap[field] = observable
        }
        @Suppress("UNCHECKED_CAST")
        return observable as FieldObservable<T>
    }

    fun <T> notifyFiledUpdated(field: Field, t: T?) {
        val observable = getFieldObservable<T>(field)
        observable.notifyFiledUpdated(t)
    }
}