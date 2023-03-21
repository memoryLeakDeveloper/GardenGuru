package com.entexy.gardenguru.core.preferences

abstract class AbstractPreference<T> {
    protected fun getKey(): String = this::class.java.name

    abstract fun get(): T?

    abstract fun put(value: T?)
}