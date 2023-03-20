package com.example.gardenguru.data.plant.cloud.create

import com.example.gardenguru.data.plant.PlantData

class CreatePlantCloudMapper{

    fun map(data: PlantData): CreatePlantCloudObj {
        return CreatePlantCloudObj(
            name = data.name,
            photosIds = arrayListOf(data.photo.id),
            careComplexity = data.careComplexity,
            summerWatering = data.summerWatering,
            summerSpraying = data.summerSpraying,
            summerFeeding = data.summerFeeding,
            summerMinTemp = data.summerMinTemp,
            summerMaxTemp = data.summerMaxTemp,
            winterWatering = data.winterWatering,
            winterSpraying = data.winterSpraying,
            winterFeeding = data.winterFeeding,
            winterMinTemp = data.winterMinTemp,
            winterMaxTemp = data.winterMaxTemp,
            reproductionIds = data.reproduction?.map { it.id },
            description = data.description,
            pestsIds = data.pests?.map { it.id },
            benefitsIds = data.benefits?.map { it.id }
        )
    }

}