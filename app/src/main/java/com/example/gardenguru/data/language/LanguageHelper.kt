package com.example.gardenguru.data.language

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject


interface LanguageHelper {

    fun getLanguage(): String
    fun setLanguage(value: String)

    class Base constructor(private val sharedPreferences: SharedPreferences) : LanguageHelper {
        private val prefKey = "PrefLanguage"

        override fun getLanguage() = "ru"//sharedPreferences.getString(prefKey, "")!!

        override fun setLanguage(value: String) = sharedPreferences.edit { putString(prefKey, value) }
    }

}