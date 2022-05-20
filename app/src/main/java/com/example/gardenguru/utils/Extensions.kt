package com.example.gardenguru.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

object Extensions {

    fun Context.getPrefs(): SharedPreferences = getSharedPreferences(PrefsKeys.PREFS, Context.MODE_PRIVATE)

    fun ImageView.setDrawable(drawable: Int) = this.setImageDrawable(ContextCompat.getDrawable(context, drawable))

    fun TextView.setString(stringRes: Int) {
        this.text = context.getString(stringRes)
    }

}