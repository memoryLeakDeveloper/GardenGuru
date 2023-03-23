package com.entexy.gardenguru.utils

import android.content.Context
import android.view.View

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.isGone() = this.visibility == View.GONE

fun Context.convertDpToPx(dp: Float) = (dp * resources.displayMetrics.density).toInt()
