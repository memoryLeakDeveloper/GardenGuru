package com.example.gardenguru.data.plant.cloud

import com.example.gardenguru.data.plant.PhotoData
import com.example.gardenguru.data.plant.PlantData

interface PlantCloudMapper {

    fun mapCloudToData(cloud: PlantCloud): PlantData
    fun mapDataToCloud(data: PlantData): PlantCloud

    class Base : PlantCloudMapper {
        override fun mapCloudToData(cloud: PlantCloud) = PlantData(
            cloud.id,
            cloud.care_complexity,
            cloud.name,
            cloud.description,
            PhotoData(cloud.photo.first().id, cloud.photo.first().thumbnail, cloud.photo.first().file),
            cloud.sunRelation,
            cloud.pests,
            cloud.reproduction,
            cloud.benefits,
            cloud.pruning,
            cloud.plantingTime,
            cloud.summerWatering,
            cloud.summerSpraying,
            cloud.summerFeeding,
            cloud.summerMinTemp,
            cloud.summerMaxTemp,
            cloud.winterWatering,
            cloud.winterSpraying,
            cloud.winterFeeding,
            cloud.winterMinTemp,
            cloud.winterMaxTemp
        )

        override fun mapDataToCloud(data: PlantData) = PlantCloud(
            data.id,
            data.care_complexity,
            data.name,
            data.description,
            arrayListOf(PhotoDataCloud(data.photo.id, data.photo.thumbnail, data.photo.file)),
            data.sunRelation,
            data.pests,
            data.reproduction,
            data.benefits,
            data.pruning,
            data.plantingTime,
            data.summerWatering,
            data.summerSpraying,
            data.summerFeeding,
            data.summerMinTemp,
            data.summerMaxTemp,
            data.winterWatering,
            data.winterSpraying,
            data.winterFeeding,
            data.winterMinTemp,
            data.winterMaxTemp
        )
    }
}