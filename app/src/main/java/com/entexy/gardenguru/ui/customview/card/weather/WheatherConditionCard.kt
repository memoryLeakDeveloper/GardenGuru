package com.entexy.gardenguru.ui.customview.card.weather

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.enums.Seasons
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.CardWheatherConditionBinding
import com.entexy.gardenguru.utils.toGone
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class WheatherConditionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding = CardWheatherConditionBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
//        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        if (isDataIsEmpty(data)) return
        setTemperature(data) //TODO
        setWatering(data, Seasons.Winter) // TODO
        setLightning(data)
    }

    private fun isDataIsEmpty(data: PlantData): Boolean {
        return if (data.minTemp == null && data.maxTemp == null && data.sunRelation == null) {
            toGone()
            true
        } else false
    }

    private fun setTemperature(data: PlantData) {
        with(binding) {
            if (data.minTemp == null || data.maxTemp == null) {
                temperature.toGone()
//                wateringDivider.toGone()
            } else {
                temperature.setTextInfo("${data.minTemp} ${R.string.celsium} / ${data.maxTemp} ${R.string.celsium}")
            }
        }
    }


    private fun setWatering(data: PlantData, season: Seasons = Seasons.Summer) = binding.apply {
        data.watering?.let {
            watering.setTextInfo("${R.string.every} ${data.watering} ${R.string.days}")
        } ?: run {
            watering.toGone()
//            lightingDivider.toGone()
        }
    }

    private fun setLightning(data: PlantData) = binding.apply {
        data.watering?.let {
            lighting.setSunRelation(data.sunRelation!!)
        } ?: run {
            lighting.toGone()
//            lightingDivider.toGone()
        }
    }

}