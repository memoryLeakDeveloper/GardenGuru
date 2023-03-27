package com.entexy.gardenguru.data.plant

import android.os.Parcelable
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.cloud.PlantCloud
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.reproduction.Reproduction
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PlantData(
    var id: String,
    var name: String,
    var variety: String,
    var photo: String,
    var coverPhoto: String,
    var careComplexity: CareComplexity,
    var description: String,
    var sunRelation: SunRelation,
    var pests: List<PestData>? = null,
    var reproduction: List<Reproduction>,
    var benefits: List<BenefitData>? = null,
    var pruning: String,
    var feedingSummer: Int,
    var feedingWinter: Int,
    var plantingTime: Date,
    var wateringSummer: Int,
    var wateringWinter: Int,
    var sprayingSummer: Int,
    var sprayingWinter: Int,
    var minTemp: Int,
    var maxTemp: Int
) : Parcelable


fun PlantData.mapToPlantCloud() = PlantCloud(
    id = id,
    name = name,
    photo = photo,
    coverPhoto = coverPhoto,
    variety = variety,
    careComplexity = careComplexity.cloudName,
    description = description,
    sunRelation = sunRelation.cloudName,
    pestsIds = pests?.map { it.id },
    reproduction = reproduction.map { it.cloudValue },
    benefitsIds = benefits?.map { it.id },
    pruning = pruning,
    feedingSummer = feedingSummer,
    feedingWinter = feedingWinter,
    plantingTime = Timestamp(plantingTime),
    wateringSummer = wateringSummer,
    wateringWinter = wateringWinter,
    sprayingSummer = sprayingSummer,
    sprayingWinter = sprayingWinter,
    minTemp = minTemp,
    maxTemp = maxTemp
)

fun PlantCloud.mapToData(language: String): PlantData {
    return PlantData(
        id = id,
        variety = localizedName?.get(language) ?: name, //todo
        photo = photo,
        coverPhoto = coverPhoto,
        name = name,
        careComplexity = CareComplexity.values().find { it.cloudName == careComplexity } ?: CareComplexity.Easy,
        description = localizeDescription?.get(language) ?: description,
        sunRelation = SunRelation.values().find { it.cloudName == sunRelation } ?: SunRelation.DiffusedLight,
        //todo
        pests = listOf(PestData("1111", ":EEEEEEEEEEEEER"), PestData("2", ":ЖУУУУУУУУУУУУУУУУУУУК"),PestData("1111", ":ПАУК")),
        reproduction = reproduction.map { Reproduction.valueOf(it) },
        //todo
        benefits = arrayListOf(BenefitData("qweqweqweqweqwe", "Плюсы определенно есть")),
        pruning = pruning,
        feedingSummer = feedingSummer,
        feedingWinter = feedingWinter,
        plantingTime = plantingTime.toDate(),
        wateringSummer = wateringSummer,
        wateringWinter = wateringWinter,
        sprayingSummer = sprayingSummer,
        sprayingWinter = sprayingWinter,
        minTemp = minTemp,
        maxTemp = maxTemp
    )
}