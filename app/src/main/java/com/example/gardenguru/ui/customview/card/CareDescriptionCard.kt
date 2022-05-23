package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CareDescriptionCardBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class CareDescriptionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding: CareDescriptionCardBinding

    init {
        //TODO
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CareDescriptionCardBinding.inflate(inflater, this)
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        setText(1, 1, binding.wateringText1)
        setText(2, 2, binding.wateringText2)
        setText(5, 1, binding.sprayingText1)
        setText(6, 2, binding.sprayingText2)
        setText(2, 1, binding.nutritionText1)
        setText(3, 2, binding.nutritionText2)
    }

    private fun setText(days: Int, season: Int, textView: TextView) {
//        when (Locale.getDefault().language.toString()) {
//            "ru" -> {
        val string = SpannableString("${if (season == 1) "Летом" else "Зимой"} каждые $days дня")
        string.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = string
//            }
//
//        }

    }
}