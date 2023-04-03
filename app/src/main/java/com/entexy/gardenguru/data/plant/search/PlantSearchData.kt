package com.entexy.gardenguru.data.plant.search

import android.os.Parcelable
import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.reproduction.Reproduction
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PlantSearchData(
    var id: String = "",
    var name: String,
    var variety: String,
    var localizedVariety: Map<String, String>,
    var photo: String,
    var customPhoto: String?,
    var coverPhoto: String,
    var careComplexity: CareComplexity,
    var description: String,
    var localizedDescription: Map<String, String>,
    var plantingTime: String,
    var localizedPlantingTime: Map<String, String>,
    var pruning: String,
    var localizedPruning: Map<String, String>,
    var sunRelation: SunRelation,
    var pests: List<PestData>,
    var reproduction: List<Reproduction>,
    var benefits: List<BenefitData>,
    var addingTime: Date = Date(),
    var feedingSummer: Int,
    var feedingWinter: Int,
    var wateringSummer: Int,
    var wateringWinter: Int,
    var sprayingSummer: Int,
    var sprayingWinter: Int,
    var minTemp: Int,
    var maxTemp: Int,
) : Parcelable {

    fun getPlantName(locale: String): String {
        return name
    }

    fun getPlantVariety(locale: String): String {
        return localizedVariety.get(locale) ?: variety
    }

    fun getPlantDescription(locale: String): String {
        return localizedDescription.get(locale) ?: description
    }

    fun getPlantPhoto(): String {
        return customPhoto ?: photo
    }
}

fun PlantSearchData.mapToPlantData(): PlantData {
    return PlantData(
        id = id,
        variety = variety,
        photo = photo,
        coverPhoto = coverPhoto,
        name = name,
        customPhoto = customPhoto,
        careComplexity = careComplexity,
        description = description,
        localizeDescription = localizedDescription,
        localizedVariety = localizedVariety,
        sunRelation = sunRelation,
        pests = arrayListOf(),
        reproduction = reproduction,
        benefits = listOf(),
        pruning = pruning,
        plantingTime = plantingTime,
        addingTime = Date(),
        feedingSummer = feedingSummer,
        feedingWinter = feedingWinter,
        wateringSummer = wateringSummer,
        wateringWinter = wateringWinter,
        sprayingSummer = sprayingSummer,
        sprayingWinter = sprayingWinter,
        minTemp = minTemp,
        maxTemp = maxTemp,
        localizedPruning = localizedPruning,
        localizedPlantingTime = localizedPlantingTime
    )
}