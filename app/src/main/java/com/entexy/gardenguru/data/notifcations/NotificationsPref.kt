package com.entexy.gardenguru.data.notifcations

import android.content.SharedPreferences
import androidx.core.content.edit
import com.entexy.gardenguru.core.preferences.AbstractBooleanPreferences

class NotificationsPref(private val sharedPreferences: SharedPreferences) : AbstractBooleanPreferences(sharedPreferences)