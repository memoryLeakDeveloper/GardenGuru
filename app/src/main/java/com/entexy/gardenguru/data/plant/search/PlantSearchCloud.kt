package com.entexy.gardenguru.data.plant.search

import com.entexy.gardenguru.data.plant.CareComplexity
import com.entexy.gardenguru.data.plant.SunRelation
import com.entexy.gardenguru.data.plant.benefit.BenefitData
import com.entexy.gardenguru.data.plant.pest.PestData
import com.entexy.gardenguru.data.plant.reproduction.Reproduction

data class PlantSearchCloud(
    var name: String? = null,
    var variety: String? = null,
    var localizedVariety: Map<String, String>? = null,
    var photo: String? = null,
    var customPhoto: String? = null,
    var coverPhoto: String? = null,
    var careComplexity: String? = null,
    var description: String? = null,
    var localizedDescription: Map<String, String>? = null,
    var plantingTime: String? = null,
    var localizedPlantingTime: Map<String, String>? = null,
    var pruning: String? = null,
    var localizedPruning: Map<String, String>,
    var sunRelation: String? = null,
    var pests: List<String>? = null,
    var reproduction: List<String>? = null,
    var benefits: List<String>? = null,
    var feedingSummer: Int? = null,
    var feedingWinter: Int? = null,
    var wateringSummer: Int? = null,
    var wateringWinter: Int? = null,
    var sprayingSummer: Int? = null,
    var sprayingWinter: Int? = null,
    var minTemp: Int? = null,
    var maxTemp: Int? = null,
)

fun PlantSearchCloud.mapToData(listBenefits: List<BenefitData>, listPests: List<PestData>) = PlantSearchData(
    name = name!!,
    variety = variety!!,
    photo = photo!!,
    coverPhoto = coverPhoto!!,
    customPhoto = customPhoto!!,
    careComplexity = CareComplexity.values().find {
        it.cloudName == careComplexity
    } ?: CareComplexity.Easy,
    description = description!!,
    localizedDescription = localizedDescription!!,
    localizedVariety = localizedVariety!!,
    sunRelation = SunRelation.values().find { it.cloudName == sunRelation }!!,
    pests = listPests,
    reproduction = reproduction!!.map { cloud ->
        Reproduction.values().find {
            it.cloudValue == cloud
        }!!
    },
    benefits = listBenefits,
    pruning = pruning!!,
    plantingTime = plantingTime!!,
    feedingSummer = feedingSummer!!,
    feedingWinter = feedingWinter!!,
    wateringSummer = wateringSummer!!,
    wateringWinter = wateringWinter!!,
    sprayingSummer = sprayingSummer!!,
    sprayingWinter = sprayingWinter!!,
    minTemp = minTemp!!,
    maxTemp = maxTemp!!,
    localizedPruning = localizedPruning,
    localizedPlantingTime = localizedPlantingTime!!
)