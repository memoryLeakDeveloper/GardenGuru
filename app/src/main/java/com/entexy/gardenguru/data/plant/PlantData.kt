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
    var name: String?,
    var variety: String,
    var localizedVariety: Map<String, String>,
    var photo: String,
    var customPhoto: String?,
    var coverPhoto: String,
    var careComplexity: CareComplexity,
    var description: String,
    var localizeDescription: Map<String, String>,
    var plantingTime: String,
    var localizedPlantingTime: Map<String, String>,
    var pruning: String,
    var localizedPruning: Map<String, String>,
    var sunRelation: SunRelation,
    var pests: List<PestData>,
    var reproduction: List<Reproduction>,
    var benefits: List<BenefitData>,
    var addingTime: Date,
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
        return name ?: getPlantVariety(locale)
    }

    fun getPlantVariety(locale: String): String {
        return localizedVariety.get(locale) ?: variety
    }

    fun getPlantDescription(locale: String): String {
        return localizeDescription.get(locale) ?: description
    }

    fun getPlantPhoto(): String {
        return customPhoto ?: photo
    }
}

fun PlantData.mapToPlantCloud() = PlantCloud(
    id = id,
    name = name,
    photo = photo,
    coverPhoto = coverPhoto,
    variety = variety,
    careComplexity = careComplexity.cloudName,
    description = description,
    customPhoto = customPhoto,
    sunRelation = sunRelation.cloudName,
    pestsIds = pests.map { it.id },
    reproduction = reproduction.map { it.cloudValue },
    benefitsIds = benefits.map { it.id },
    pruning = pruning,
    plantingTime = plantingTime,
    feedingSummer = feedingSummer,
    feedingWinter = feedingWinter,
    addingDate = Timestamp(addingTime),
    wateringSummer = wateringSummer,
    wateringWinter = wateringWinter,
    sprayingSummer = sprayingSummer,
    sprayingWinter = sprayingWinter,
    minTemp = minTemp,
    maxTemp = maxTemp,
    localizedDescription = localizeDescription,
    localizedVariety = localizedVariety,
    localizedPlantingTime = localizedPlantingTime,
    localizedPruning = localizedPruning
)

fun PlantCloud.mapToData(): PlantData? {
    if (allRequiredFields()) {
        return PlantData(
            id = id!!,
            variety = variety!!,
            photo = photo!!,
            coverPhoto = coverPhoto!!,
            name = name,
            customPhoto = customPhoto,
            careComplexity = CareComplexity.values().find {
                it.cloudName == careComplexity
            } ?: CareComplexity.Easy,
            description = description!!,
            localizeDescription = localizedDescription!!,
            localizedVariety = localizedVariety!!,
            sunRelation = SunRelation.values().find { it.cloudName == sunRelation }!!,
            pests = arrayListOf(),
            reproduction = reproduction!!.map { cloud ->
                Reproduction.values().find {
                    it.cloudValue == cloud
                }!!
            },
            benefits = listOf(),
            pruning = pruning!!,
            plantingTime = plantingTime!!,
            addingTime = addingDate!!.toDate(),
            feedingSummer = feedingSummer!!,
            feedingWinter = feedingWinter!!,
            wateringSummer = wateringSummer!!,
            wateringWinter = wateringWinter!!,
            sprayingSummer = sprayingSummer!!,
            sprayingWinter = sprayingWinter!!,
            minTemp = minTemp!!,
            maxTemp = maxTemp!!,
            localizedPruning = localizedPruning!!,
            localizedPlantingTime = localizedPlantingTime!!
        )
    }
    return null
}