package com.example.gardenguru.domain.plant

import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.plant.PlantRepository
import com.example.gardenguru.data.plant.cloud.create.CreatePlantBody
import com.example.gardenguru.data.plant.cloud.create.CreatePlantCloudObj
import javax.inject.Inject

class CreatePlantUseCase @Inject constructor(private val repository: PlantRepository) {

    suspend fun createPlant(gardenId: String,  plantData: PlantData) =
        repository.createPlant(gardenId, plantData)
}