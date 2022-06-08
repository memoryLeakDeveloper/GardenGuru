package com.example.gardenguru.data.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject


interface TokenHelper {

    fun getToken(): String
    fun setToken(value: String)

    class Base constructor(private val sharedPreferences: SharedPreferences) : TokenHelper {
        private val prefKey = "ActiveTokenPrefs"

        override fun getToken() = "Token dfeeaff4c6674554945e043b62b1fc4006e4913e"//sharedPreferences.getString(prefKey, "")!!

        override fun setToken(value: String) = sharedPreferences.edit { putString(prefKey, value) }
    }

}