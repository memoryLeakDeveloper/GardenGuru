package com.example.gardenguru.data.plant

import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.plant.cloud.PhotoDataCloud
import com.example.gardenguru.data.plant.cloud.PlantCloud
import com.example.gardenguru.data.plant.cloud.create.CreatePlantCloud
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData

data class PlantData(
    var id: String? = null,
    var careComplexity: Int? = null,
    var name: String,
//    var plantType: String? = null,
    var code: String? = null,
    var description: String? = null,
    var photo: PhotoData,
    var sunRelation: SunRelationData? = null,
    var pests: ArrayList<PestData>? = null,
    var reproduction: ArrayList<ReproductionData>? = null,
    var benefits: ArrayList<BenefitData>? = null,
    var pruning: String? = null,
    var plantingTime: String? = null,
    var summerWatering: Int? = null,
    var summerSpraying: Int? = null,
    var summerFeeding: Int? = null,
    var summerMinTemp: Int? = null,
    var summerMaxTemp: Int? = null,
    var winterWatering: Int? = null,
    var winterSpraying: Int? = null,
    var winterFeeding: Int? = null,
    var winterMinTemp: Int? = null,
    var winterMaxTemp: Int? = null
)

fun PlantData.mapToCreatePlantCloud() = CreatePlantCloud(
    name = name,
    photosIds = arrayListOf(photo.id),
    careComplexity = careComplexity,
    summerWatering = summerWatering,
    summerSpraying = summerSpraying,
    summerFeeding = summerFeeding,
    summerMinTemp = summerMinTemp,
    summerMaxTemp = summerMaxTemp,
    winterWatering = winterWatering,
    winterSpraying = winterSpraying,
    winterFeeding = winterFeeding,
    winterMinTemp = winterMinTemp,
    winterMaxTemp = winterMaxTemp,
    reproductionIds = reproduction?.map { it.id },
    description = description,
    pestsIds = pests?.map { it.id },
    benefitsIds = benefits?.map { it.id }
)

fun PlantData.mapToCloud() = PlantCloud(
    id,
    careComplexity,
    name,
    code,
    description,
    arrayListOf(PhotoDataCloud(photo.id, photo.thumbnail, photo.file)),
    sunRelation,
    pests,
    reproduction,
    benefits,
    pruning,
    plantingTime,
    summerWatering,
    summerSpraying,
    summerFeeding,
    summerMinTemp,
    summerMaxTemp,
    winterWatering,
    winterSpraying,
    winterFeeding,
    winterMinTemp,
    winterMaxTemp
)
