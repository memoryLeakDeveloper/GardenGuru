package com.entexy.gardenguru.ui.customview.card

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.CardCareDescriptionBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class CareDescriptionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding = CardCareDescriptionBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        if (isDataIsEmpty(data)) return
        setWatering(data)
        setSpraying(data)
        setFeeding(data)
        setPlanting(data)
        setReproduction(data)
        setPruning(data)
    }

    private fun isDataIsEmpty(data: PlantData): Boolean {
        return if (data.summerWatering == null &&
            data.winterWatering == null &&
            data.summerSpraying == null &&
            data.winterSpraying == null &&
            data.summerFeeding == null &&
            data.winterFeeding == null &&
            data.plantingTime == null &&
            data.reproduction == null &&
            data.pruning == null
        ) {
            this.visibility = View.GONE
            true
        } else false
    }

    private fun setWatering(data: PlantData) {
        with(binding) {
            if (data.summerWatering == null && data.winterWatering == null) {
                watering.visibility = View.GONE
            } else {
                data.summerWatering?.let { wateringSummer.text = getString(1, it) } ?: run { wateringSummer.visibility = View.GONE }
                data.winterWatering?.let { wateringWinter.text = getString(2, it) } ?: run { wateringWinter.visibility = View.GONE }
            }
        }
    }

    private fun setSpraying(data: PlantData) {
        with(binding) {
            if (data.summerSpraying == null && data.winterSpraying == null) {
                spraying.visibility = View.GONE

            } else {
                data.summerSpraying?.let { sprayingSummer.text = getString(1, it) } ?: run { sprayingSummer.visibility = View.GONE }
                data.winterSpraying?.let { sprayingWinter.text = getString(2, it) } ?: run { sprayingWinter.visibility = View.GONE }
            }
        }
    }

    private fun setFeeding(data: PlantData) {
        with(binding) {
            if (data.summerFeeding == null && data.winterFeeding == null) {
                feeding.visibility = View.GONE
            } else {
                data.summerFeeding?.let { feedingSummer.text = getString(1, it) } ?: run { feedingSummer.visibility = View.GONE }
                data.winterFeeding?.let { feedingWinter.text = getString(2, it) } ?: run { feedingWinter.visibility = View.GONE }
            }
        }
    }

    private fun setPlanting(data: PlantData) {
        data.plantingTime?.let { binding.plantingText.text = it } ?: run { binding.planting.visibility = View.GONE }
    }

    private fun setReproduction(data: PlantData) {
        data.reproduction?.let {
            val listString = it.map { item -> item.type }
            val string = listString.joinToString(", ")
            binding.plantingText.text = string
        } ?: run { binding.reproduction.visibility = View.GONE }
    }

    private fun setPruning(data: PlantData) {
        data.pruning?.let { binding.pruningText.text = it } ?: run { binding.pruning.visibility = View.GONE }
    }

    private fun getString(season: Int, days: Int): SpannableString {
        val string = SpannableString("${if (season == 1) "Летом" else "Зимой"} каждые $days дня")
        string.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }
}