package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.CardCareDescriptionBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class CareDescriptionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding = CardCareDescriptionBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        Log.d("bugger", data.toString())
        if (data.summerWatering == null &&
            data.winterWatering == null &&
            data.summerSpraying == null &&
            data.winterSpraying == null &&
            data.summerFeeding == null &&
            data.winterFeeding == null &&
            data.plantingTime == null &&
            data.reproduction == null &&
            data.pruning == null
        ) this.visibility = View.GONE
        binding.wateringText1.text = data.summerWatering?.let { getString(1, it) }
        binding.wateringText2.text = data.winterWatering?.let { getString(2, it) }
        binding.sprayingText1.text = data.summerSpraying?.let { getString(1, it) }
        binding.sprayingText2.text = data.winterSpraying?.let { getString(2, it) }
        binding.feedingText1.text = data.summerFeeding?.let { getString(1, it) }
        binding.feedingText2.text = data.winterFeeding?.let { getString(2, it) }
        binding.plantingText1.text = data.plantingTime
        if (data.reproduction?.isNotEmpty() == true) {
            binding.reproductionText1.text = data.reproduction?.first()?.type //TODO
        }
        binding.pruningText1.text = data.pruning
    }

    private fun getString(season: Int, days: Int): SpannableString {
        val string = SpannableString("${if (season == 1) "Летом" else "Зимой"} каждые $days дня")
        string.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }
}