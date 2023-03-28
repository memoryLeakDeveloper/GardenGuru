package com.entexy.gardenguru.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
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

fun ImageView.setImageByGlide(model: Any?) = Glide.with(context)
    .load(model)
    .circleCrop()
    .placeholder(ContextCompat.getDrawable(context, R.drawable.plant_placeholder))
    .into(this)

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}