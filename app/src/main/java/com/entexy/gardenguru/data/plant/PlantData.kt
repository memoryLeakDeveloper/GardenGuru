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
    var photo: String,
    var careComplexity: CareComplexity = CareComplexity.Medium,
    var description: String? = null,
    var sunRelation: SunRelation? = null,
    var pests: ArrayList<PestData>? = null,
    var reproduction: List<Reproduction>? = null,
    var benefits: ArrayList<BenefitData>? = null,
    var pruning: String? = null,
    var plantingTime: Date? = null,
    var watering: Int? = null,
    var spraying: Int? = null,
    var minTemp: Int? = null,
    var maxTemp: Int? = null
) : Parcelable


fun PlantData.mapToPlantCloud() = PlantCloud(
    name = name,
    photo = photo,
    careComplexity = careComplexity.cloudName,
    description = description,
    sunRelation = sunRelation?.cloudName,
    pestsIds = pests?.map { it.id },
    reproduction = reproduction?.map {
        it.cloudValue
    },
    benefitsIds = benefits?.map { it.id },
    pruning = pruning,
    plantingTime = if (plantingTime != null) Timestamp(plantingTime!!) else null,
    watering = watering,
    spraying = spraying,
    minTemp = minTemp,
    maxTemp = maxTemp
)

fun PlantCloud.mapToData() = PlantData(
    id = "",
    name = name,
    photo = photo,
    careComplexity = try {
        if (careComplexity != null)
            CareComplexity.valueOf(careComplexity)
        else CareComplexity.Medium
    } catch (e: java.lang.IllegalArgumentException) {
        CareComplexity.Medium
    },
    description = description,
    sunRelation = try {
        if (sunRelation != null)
            SunRelation.valueOf(sunRelation)
        else null
    } catch (e: java.lang.IllegalArgumentException) {
        null
    },
    pests = null,
    reproduction =
    try {
        reproduction?.map { Reproduction.valueOf(it) }
    } catch (e: java.lang.IllegalArgumentException) {
        null
    },
    benefits = null,
    pruning = pruning,
    plantingTime = plantingTime?.toDate(),
    watering = watering,
    spraying = spraying,
    minTemp = minTemp,
    maxTemp = maxTemp
)
