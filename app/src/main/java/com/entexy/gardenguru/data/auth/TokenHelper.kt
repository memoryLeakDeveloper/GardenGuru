package com.entexy.gardenguru.data.auth

import android.content.SharedPreferences
import androidx.core.content.edit


interface TokenHelper {

    fun getToken(): String
    fun setToken(value: String)

    class Base constructor(private val sharedPreferences: SharedPreferences) : TokenHelper {
        private val prefKey = "ActiveTokenPrefs"

        override fun getToken() = "Token 8f826acb5eb0dc77fdd0a0075cf59648ee723888"//sharedPreferences.getString(prefKey, "")!!

        override fun setToken(value: String) = sharedPreferences.edit { putString(prefKey, value) }
    }

}