package com.example.gardenguru.data.auth

import android.content.SharedPreferences
import androidx.core.content.edit

interface UserEmailHelper {
    fun getEmail(): String
    fun setEmail(value: String)

    class Base constructor(private val sharedPreferences: SharedPreferences) : UserEmailHelper {
        private val prefKey = "EmailPrefs"

        override fun getEmail() = sharedPreferences.getString(prefKey, "")!!

        override fun setEmail(value: String) = sharedPreferences.edit { putString(prefKey, value) }
    }


}