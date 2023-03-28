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
    var customName: String?,
    var customPhoto: String?,
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
    var maxTemp: Int,
    var localizedVariety: Map<String, String>? = null,
    var localizeDescription: Map<String, String>? = null,

    ) : Parcelable


fun PlantData.mapToPlantCloud() = PlantCloud(
    id = id,
    name = customName,
    photo = photo,
    cover = coverPhoto,
    variety = variety,
    careComplexity = careComplexity.cloudName,
    description = description,
    customPhoto = customPhoto,
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

fun PlantCloud.mapToData(): PlantData {
    return PlantData(
        id = id,
        variety = variety,
        photo = photo,
        coverPhoto = cover,
        customName = name,
        customPhoto = customPhoto,
        careComplexity = CareComplexity.valueOf(careComplexity),
        description = description,
        localizeDescription = localizeDescription,
        localizedVariety = localizedVariety,
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

