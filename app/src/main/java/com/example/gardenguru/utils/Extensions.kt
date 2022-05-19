package com.example.gardenguru.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import androidx.core.content.ContextCompat

object Extensions {

    fun Context.getPrefs(): SharedPreferences = getSharedPreferences(PrefsKeys.PREFS, Context.MODE_PRIVATE)

    fun ImageView.setDrawable(drawable: Int) = this.setImageDrawable(ContextCompat.getDrawable(context, drawable))

    fun String.underLine(start: Int, end: Int): SpannableString {
        val spanStr = SpannableString(this)
        spanStr.setSpan(UnderlineSpan(), 0, spanStr.length, 0)
        return spanStr
    }

}