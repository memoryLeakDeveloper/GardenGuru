package com.example.gardenguru.ui.customview.card

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.CardCareDescriptionBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class CareDescriptionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding: CardCareDescriptionBinding

    init {
        //TODO
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CardCareDescriptionBinding.inflate(inflater, this)
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        binding.wateringText1.text = getString(1, data.summerWatering)
        binding.wateringText2.text = getString(2, data.winterWatering)
        binding.sprayingText1.text = getString(1, data.summerSpraying)
        binding.sprayingText2.text = getString(2, data.winterSpraying)
        binding.feedingText1.text = getString(1, data.summerFeeding)
        binding.feedingText2.text = getString(2, data.winterFeeding)
        binding.plantingText1.text = data.plantingTime
        if(data.reproduction.isNotEmpty()) {
            binding.reproductionText1.text = data.reproduction.first().type //TODO
        }
        binding.pruningText1.text = data.pruning
    }

    private fun getString(season: Int, days: Int): SpannableString {
        val string = SpannableString("${if (season == 1) "Летом" else "Зимой"} каждые $days дня")
        string.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }
}