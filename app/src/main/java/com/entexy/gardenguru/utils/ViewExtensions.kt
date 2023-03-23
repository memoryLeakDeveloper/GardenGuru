package com.entexy.gardenguru.utils

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R

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

fun ImageView.setImageByGlide(model: Any?) = Glide.with(context)
    .load(model)
    .circleCrop()
    .placeholder(ContextCompat.getDrawable(context, R.drawable.plant_placeholder))
    .into(this)