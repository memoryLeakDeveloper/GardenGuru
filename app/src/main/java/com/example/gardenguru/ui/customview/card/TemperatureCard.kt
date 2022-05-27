package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CardTemperatureBinding
import com.example.gardenguru.ui.customview.calendar.Seasons
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class TemperatureCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var binding: CardTemperatureBinding

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CardTemperatureBinding.inflate(inflater, this)
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