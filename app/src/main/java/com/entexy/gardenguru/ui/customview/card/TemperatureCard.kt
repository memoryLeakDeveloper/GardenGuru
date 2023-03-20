package com.entexy.gardenguru.ui.customview.card

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.enums.Seasons
import com.entexy.gardenguru.databinding.CardTemperatureBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class TemperatureCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = CardTemperatureBinding.inflate(LayoutInflater.from(context), this)
    private var isActive = true
    private var valueCallback: ValueCallback? = null

    fun interface ValueCallback {
        fun value(value: Pair<Int, Int>)
    }

    fun setValueListener(callback: ValueCallback) {
        valueCallback = callback
    }

    init {
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        binding.button.setOnClickListener {
            binding.button.setOnClickListener {
                if (isActive) {
                    checkValidInput()
                } else {
                    setViewActive()
                    isActive = true
                }
            }
        }
    }

    fun initView(seasons: Seasons) {
        when (seasons) {
            Seasons.Winter -> {
                binding.textView.text = getSpannableText(resources.getString(R.string.in_winter))
            }
            Seasons.Summer -> {
                binding.textView.text = getSpannableText(resources.getString(R.string.in_summer))
            }
            else -> {}
        }
    }

    private fun checkValidInput() {
        with(binding) {
            val textFrom = editTextFrom.text.toString()
            val textTo = editTextTo.text.toString()
            if (textFrom.isBlank()) {
                editTextFrom.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.error_card_background))
                return
            }
            if (textTo.isBlank()) {
                editTextTo.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.error_card_background))
                return
            }
            if (textFrom.isNotBlank() && textTo.isNotBlank()) {
                if (textFrom.toInt() > textTo.toInt()) {
                    editTextFrom.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.error_card_background))
                    editTextTo.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.error_card_background))
                    return
                }
                setViewInactive()
            }
        }
    }

    private fun setViewActive() {
        with(binding) {
            button.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_background))
            button.setText(R.string.Ok)
            button.isEnabled = true
            editTextFrom.text?.clear()
            editTextTo.text?.clear()
            disableEditText(true)
            valueCallback?.value(Pair(0, 0))
        }
    }

    private fun setViewInactive() {
        with(binding) {
            editTextFrom.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
            editTextTo.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
            button.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_unactive_background))
            button.setText(R.string.change)
            button.isEnabled = false
            disableEditText(false)
            valueCallback?.value(Pair(editTextFrom.toString().toInt(), editTextTo.toString().toInt()))
        }
    }

    private fun disableEditText(isEnabled: Boolean) {
        with(binding) {
            editTextFrom.apply {
                this.isEnabled = isEnabled
                isActivated = isEnabled
                isClickable = isEnabled
            }
            editTextTo.apply {
                this.isEnabled = isEnabled
                isActivated = isEnabled
                isClickable = isEnabled
            }
        }
    }

    private fun getSpannableText(season: String): SpannableString {
        val text = context.getText(R.string.define_comfort_temperature).toString()
        val span = SpannableString("$text $season")
        span.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.primary_green)),
            text.length + 1,
            text.length + season.length + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }
}