package com.entexy.gardenguru.data.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.entexy.gardenguru.core.preferences.AbstractBooleanPreferences

class FirstLaunchPref(private val sharedPreferences: SharedPreferences) : AbstractBooleanPreferences(sharedPreferences)