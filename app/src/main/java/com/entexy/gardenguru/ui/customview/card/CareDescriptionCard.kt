package com.entexy.gardenguru.ui.customview.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.enums.Seasons
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.search.PlantSearchData
import com.entexy.gardenguru.databinding.CardCareDescriptionBinding

class CareDescriptionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding = CardCareDescriptionBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
    }

    fun initView(data: PlantSearchData) = binding.apply {
        wateringSummer.text = getString(Seasons.Summer, data.wateringSummer)
        wateringWinter.text = getString(Seasons.Winter, data.wateringWinter)
        sprayingSummer.text = getString(Seasons.Summer, data.sprayingSummer)
        sprayingWinter.text = getString(Seasons.Winter, data.sprayingWinter)
        feedingSummer.text = getString(Seasons.Summer, data.feedingSummer)
        feedingWinter.text = getString(Seasons.Winter, data.feedingWinter)
        plantingText.text = data.addingTime.toString()
        pruningText.text = data.pruning
        val listString = data.reproduction.map { item -> item.name }
        reproductionText.text = listString.joinToString(", ")
    }

    private fun getString(season: Seasons, days: Int): String {
        return if (season == Seasons.Winter) {
            context.getString(R.string.winter_interval_days, days)
        } else {
            context.getString(R.string.summer_interval_days, days)
        }
    }
}