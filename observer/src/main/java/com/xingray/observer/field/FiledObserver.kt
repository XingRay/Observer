package com.xingray.observer.field

interface FiledObserver<T> {
    fun onFieldUpdated(t: T?)
}