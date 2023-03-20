package com.example.gardenguru.ui.customview

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.ui.fragments.add_plant.AddingPlantFragment

class ExpandableTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    private var callback: AddingPlantFragment.UpdateLayoutHeightCallback? = null
    private var isDescriptionShowed: Boolean = false
    private var fullText: String = ""

    fun initView(fullText: String, callback: AddingPlantFragment.UpdateLayoutHeightCallback? = null) {
        if (callback != null) this.callback = callback
        this.fullText = fullText
        this.text = getSpannableNextString()
        this.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun expandText() {
        isDescriptionShowed = true
        this.text = getSpannableHideString()
    }

    private fun hideText() {
        isDescriptionShowed = false
        this.text = getSpannableNextString()
    }

    private fun getSpannableNextString(): SpannableString {
        val text = fullText.substringBefore(".")
        return SpannableString(text + ". " + context.getString(R.string.next_dots)).apply {
            setSpan(
                getClickableSpan(),
                0,
                text.length + context.getString(R.string.next_dots).length + 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.primary_green)),
                text.length + 2,
                text.length + context.getString(R.string.next_dots).length + 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun getSpannableHideString(): SpannableString {
        return SpannableString(fullText + " " + context.getString(R.string.hide)).apply {
            setSpan(
                getClickableSpan(),
                0,
                fullText.length + context.getString(R.string.hide).length + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.primary_green)),
                fullText.length + 1,
                fullText.length + context.getString(R.string.hide).length + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun getClickableSpan() = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            if (isDescriptionShowed) hideText() else expandText()
            callback?.update()
        }
    }
}