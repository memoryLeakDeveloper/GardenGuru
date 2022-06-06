package com.example.gardenguru.domain.plant

import com.example.gardenguru.data.plant.PlantRepository
import com.example.gardenguru.data.plant.cloud.create.CreatePlantBody
import javax.inject.Inject

class CreatePlantUseCase @Inject constructor(private val repository: PlantRepository) {

    suspend fun createPlant(name: String, gardenId: String, plant: CreatePlantBody) =
        repository.createPlant(name, gardenId, plant)
}