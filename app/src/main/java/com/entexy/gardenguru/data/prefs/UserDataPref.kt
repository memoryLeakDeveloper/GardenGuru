package com.entexy.gardenguru.data.prefs

import android.content.SharedPreferences
import com.entexy.gardenguru.core.preferences.AbstractPreference
import com.entexy.gardenguru.data.user.UserData
import com.google.gson.Gson

class UserDataPref(private val sharedPreferences: SharedPreferences) : AbstractPreference<Any>() {

    override fun get(): UserData? = runCatching {
        Gson().fromJson(sharedPreferences.getString(getKey(), null), UserData::class.java)
    }.getOrNull()

    override fun put(value: Any?) {
        sharedPreferences.edit().putString(getKey(), Gson().toJson(value)).apply()
    }

}