package com.entexy.gardenguru.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit

interface FirstLaunchPref {

    fun get(): Boolean

    fun set(value: Boolean)

    class Base constructor(private val sharedPreferences: SharedPreferences) : FirstLaunchPref {

        private val prefKey = "first app launch"

        override fun get() = sharedPreferences.getBoolean(prefKey, true)

        override fun set(value: Boolean) = sharedPreferences.edit { putBoolean(prefKey, value) }
    }

}