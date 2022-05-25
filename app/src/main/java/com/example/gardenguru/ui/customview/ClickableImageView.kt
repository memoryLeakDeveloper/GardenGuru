package com.example.gardenguru.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AlphaAnimation
import androidx.appcompat.widget.AppCompatImageView

class ClickableImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    init {
        setOnTouchListener { _, event ->
            if(event.action == MotionEvent.ACTION_UP) {
                val animation1 = AlphaAnimation(0.2f, 1.0f)
                animation1.duration = 400
                animation1.fillAfter = true
                this.startAnimation(animation1)
                performClick()
            }
            true
        }
    }
}
