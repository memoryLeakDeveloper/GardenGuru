package com.entexy.gardenguru.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.entexy.gardenguru.core.preferences.AbstractBooleanPreferences

class FirstLaunchPref(private val sharedPreferences: SharedPreferences) : AbstractBooleanPreferences(sharedPreferences) {

    private val prefKey = "first app launch"
    override fun get() = sharedPreferences.getBoolean(prefKey, true)
    override fun put(value: Boolean?) = sharedPreferences.edit { putBoolean(prefKey, value ?: false) }

}