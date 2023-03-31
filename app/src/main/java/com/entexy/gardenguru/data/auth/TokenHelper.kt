package com.entexy.gardenguru.data.auth

import android.content.SharedPreferences
import androidx.core.content.edit

interface TokenHelper {

    fun getToken(): String?
    fun setToken(value: String?)

    class Base constructor(private val sharedPreferences: SharedPreferences) : TokenHelper {

        private val prefKey = "ActiveTokenPrefs"

        override fun getToken() = sharedPreferences.getString(prefKey, null)

        override fun setToken(value: String?) = sharedPreferences.edit { putString(prefKey, value) }
    }

}