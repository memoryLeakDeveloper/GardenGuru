package com.example.gardenguru.ui.customview.card.weather

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.gardenguru.R
import com.example.gardenguru.data.enums.Seasons
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.CardWheatherConditionBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat

class WheatherConditionCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding = CardWheatherConditionBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
    }

    fun initView(data: PlantData) {
        if (isDataIsEmpty(data)) return
        setTemperature(data, Seasons.Summer) //TODO
        setWatering(data, Seasons.Winter) // TODO
        setLightning(data)
    }

    private fun isDataIsEmpty(data: PlantData): Boolean {
        return if (data.summerMinTemp == null &&
            data.summerMaxTemp == null &&
            data.winterMinTemp == null &&
            data.winterMaxTemp == null &&
            data.winterWatering == null &&
            data.summerWatering == null &&
            data.sunRelation == null
        ) {
            this.visibility = GONE
            true
        } else false
    }

    private fun setTemperature(data: PlantData, season: Seasons = Seasons.Summer) {
        with(binding) {
            when (season) {
                Seasons.Summer -> {
                    if (data.summerMinTemp == null || data.summerMaxTemp == null) {
                        temperature.visibility = GONE
                        wateringDivider.visibility = GONE
                    } else {
                        temperature.setTextInfo("${data.summerMinTemp} ${R.string.celsium} / ${data.summerMaxTemp} ${R.string.celsium}")
                    }
                }
                Seasons.Winter -> {
                    if (data.winterMinTemp == null || data.winterMaxTemp == null) {
                        temperature.visibility = GONE
                        wateringDivider.visibility = GONE
                    } else {
                        temperature.setTextInfo("${data.winterMinTemp} ${R.string.celsium} / ${data.winterMaxTemp} ${R.string.celsium}")
                    }
                }
            }
        }
    }

    private fun setWatering(data: PlantData, season: Seasons = Seasons.Summer) {
        with(binding) {
            when (season) {
                Seasons.Summer -> {
                    if (data.summerWatering == null) {
                        watering.visibility = GONE
                        lightingDivider.visibility = GONE
                    } else {
                        watering.setTextInfo("${R.string.every} ${data.summerWatering} ${R.string.days}")
                    }
                }
                Seasons.Winter -> {
                    if (data.winterWatering == null) {
                        watering.visibility = GONE
                        lightingDivider.visibility = GONE
                    } else {
                        watering.setTextInfo("${R.string.every} ${data.winterWatering} ${R.string.days}")
                    }
                }
            }
        }
    }

    private fun setLightning(data: PlantData) {
        if (data.sunRelation == null) {
            binding.lighting.visibility = GONE
            binding.lightingDivider.visibility = GONE
        } else {
            binding.lighting.setSunRelation(data.sunRelation!!)
        }
    }

}