package com.entexy.gardenguru.core.preferences

import android.content.SharedPreferences

abstract class AbstractBooleanPreferences(private val sharedPreferences: SharedPreferences) : AbstractPreference<Boolean>() {
    override fun get() = sharedPreferences.getBoolean(getKey(), true)
    override fun put(value: Boolean?) = sharedPreferences.edit().putBoolean(getKey(), value ?: false).apply()
}