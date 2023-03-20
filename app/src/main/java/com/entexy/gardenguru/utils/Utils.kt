package com.entexy.gardenguru.utils

import android.content.Context

object Utils {

    fun convertPxToDp(px: Float, context: Context) = (px * context.resources.displayMetrics.density).toInt()

}