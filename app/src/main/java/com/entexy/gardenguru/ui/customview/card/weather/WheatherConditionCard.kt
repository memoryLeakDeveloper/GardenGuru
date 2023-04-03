package com.entexy.gardenguru.ui.customview.card.weather

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.enums.Seasons
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.search.PlantSearchData
import com.entexy.gardenguru.databinding.CardWheatherConditionBinding

class WheatherConditionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding = CardWheatherConditionBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
    }

    fun initView(data: PlantSearchData) {
        setTemperature(data) //TODO
        setWatering(data, Seasons.Summer)
        setLightning(data)
    }

    private fun setTemperature(data: PlantSearchData) {
        with(binding) {
            temperature.setTextInfo("${data.minTemp} ${context.getString(R.string.celsium)} / ${data.maxTemp} ${context.getString(R.string.celsium)}")
        }
    }

    private fun setWatering(data: PlantSearchData, season: Seasons = Seasons.Summer) = binding.apply {
        //todo
        watering.setTextInfo(context.getString(R.string.interval_days, data.wateringSummer))
    }

    private fun setLightning(data: PlantSearchData) = binding.apply {
        lighting.setSunRelation(data.sunRelation)
    }

}