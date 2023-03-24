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
    var variety: String,
    var photo: String,
    var coverPhoto: String,
    var name: String,
    var careComplexity: CareComplexity,
    var description: String,
    var sunRelation: SunRelation,
    var pests: ArrayList<PestData>? = null,
    var reproduction: List<Reproduction>,
    var benefits: ArrayList<BenefitData>? = null,
    var pruning: String,
    var plantingTime: Date,
    var watering: Int,
    var spraying: Int,
    var minTemp: Int,
    var maxTemp: Int
) : Parcelable


fun PlantData.mapToPlantCloud() = PlantCloud(
    id = id,
    name = name,
    photo = photo,
    cover = coverPhoto,
    variety = variety,
    careComplexity = careComplexity.cloudName,
    description = description,
    sunRelation = sunRelation.cloudName,
    pestsIds = pests?.map { it.id },
    reproduction = reproduction.map { it.cloudValue },
    benefitsIds = benefits?.map { it.id },
    pruning = pruning,
    plantingTime = Timestamp(plantingTime),
    watering = watering,
    spraying = spraying,
    minTemp = minTemp,
    maxTemp = maxTemp
)

fun PlantCloud.mapToData(language: String): PlantData {
    return PlantData(
        id = id,
        variety = localizedName?.get(language) ?: name, //todo
        photo = photo,
        coverPhoto = cover,
        name = name,
        careComplexity = CareComplexity.valueOf(careComplexity),
        description = localizeDescription?.get(language) ?: description,
        sunRelation = SunRelation.valueOf(sunRelation),
        pests = null,
        reproduction = reproduction.map { Reproduction.valueOf(it) },
        benefits = null,
        pruning = pruning,
        plantingTime = plantingTime.toDate(),
        watering = watering,
        spraying = spraying,
        minTemp = minTemp,
        maxTemp = maxTemp
    )
}

