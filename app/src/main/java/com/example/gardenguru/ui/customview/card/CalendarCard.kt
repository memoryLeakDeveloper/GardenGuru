package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CardCalendarBinding
import com.example.gardenguru.data.enums.Seasons
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class CalendarCard(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private lateinit var binding: CardCalendarBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CardCalendarBinding.inflate(inflater, this)
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
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