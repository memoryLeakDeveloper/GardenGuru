package com.example.gardenguru.core.preferences

import android.content.SharedPreferences

abstract class AbstractStringPreference(
    private val sharedPreferences: SharedPreferences
): AbstractPreference<String>() {
    override fun get() = sharedPreferences.getString(getKey(), null)
    override fun put(value: String?) = sharedPreferences.edit().putString(getKey(), value).apply()
}