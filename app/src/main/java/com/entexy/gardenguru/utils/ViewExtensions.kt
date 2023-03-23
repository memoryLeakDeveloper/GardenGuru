package com.entexy.gardenguru.utils

import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

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

fun View.setBackground(@DrawableRes drawable: Int) {
    background = (ContextCompat.getDrawable(context, drawable))
}
