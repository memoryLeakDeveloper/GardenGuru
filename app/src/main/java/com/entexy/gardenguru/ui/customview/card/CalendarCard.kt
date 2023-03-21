package com.entexy.gardenguru.ui.customview.card

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.enums.DaysMode
import com.entexy.gardenguru.data.enums.Seasons
import com.entexy.gardenguru.databinding.CardCalendarBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class CalendarCard(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private var binding = CardCalendarBinding.inflate(LayoutInflater.from(context), this)
    private var isActive = true
    private var valueCallback: ValueCallback? = null

    fun interface ValueCallback {
        fun value(days: Int)
    }

    fun setValueListener(callback: ValueCallback) {
        valueCallback = callback
    }

    init {
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        binding.button.setOnClickListener {
            if (isActive) {
                setViewInactive()
                isActive = false
            } else {
                setViewActive()
                isActive = true
            }
        }
    }

    private fun setViewInactive() {
        val (number: Int, period: DaysMode) = binding.calendar.getValue()
        val value = when (period) {
            DaysMode.Days -> number * DaysMode.Days.days
            DaysMode.Weeks -> number * DaysMode.Weeks.days
            DaysMode.Months -> number * DaysMode.Months.days
        }
        valueCallback?.value(value)
        binding.button.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_unactive_background))
        binding.button.setText(R.string.change)
        binding.calendar.disableScrolling()
        isEnabled = false
    }

    private fun setViewActive() {
        valueCallback?.value(0)
        binding.button.setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.button_background))
        binding.calendar.enableScrolling()
        binding.button.setText(R.string.Ok)
        isEnabled = true
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

    private fun getSpannableText(season: String): SpannableString {
        val text = context.getText(R.string.set_watering_period).toString()
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