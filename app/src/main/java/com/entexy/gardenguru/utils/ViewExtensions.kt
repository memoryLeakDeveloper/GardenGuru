package com.entexy.gardenguru.utils

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.entexy.gardenguru.R
import com.google.android.material.snackbar.Snackbar

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


fun View.showSnackBar(@StringRes resId: Int) {
    val snackBar = Snackbar.make(this, context.getString(resId), Snackbar.LENGTH_LONG).apply {
        setAction(context.getString(R.string.ok)) { dismiss() }
        setActionTextColor(context.getColor(R.color.blue))
        setBackgroundTint(context.getColor(R.color.black_1))
    }
    (snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).apply {
        setTextColor(context.getColor(R.color.white))
        textSize = 14F
    }
    snackBar.show()
}
