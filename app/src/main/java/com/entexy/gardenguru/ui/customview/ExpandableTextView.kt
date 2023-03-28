package com.entexy.gardenguru.ui.customview

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
import com.entexy.gardenguru.R

class ExpandableTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    private var isDescriptionShowed: Boolean = false
    private var fullText: String = ""

    fun initView(fullText: String) {
        this.fullText = fullText
        text = getSpannableNextString()
        movementMethod = LinkMovementMethod.getInstance()
    }

    private fun expandText() {
        isDescriptionShowed = true
        text = getSpannableHideString()
    }

    private fun hideText() {
        isDescriptionShowed = false
        text = getSpannableNextString()
    }

    private fun getSpannableNextString(): SpannableString {
        val newText = fullText.substringBefore(".")
        return SpannableString(newText + ". " + context.getString(R.string.next_dots)).apply {
            setSpan(
                getClickableSpan(),
                0,
                newText.length + context.getString(R.string.next_dots).length + 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.primary_green)),
                newText.length + 2,
                newText.length + context.getString(R.string.next_dots).length + 2,
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
        }
    }
}