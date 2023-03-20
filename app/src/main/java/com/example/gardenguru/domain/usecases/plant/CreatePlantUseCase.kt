package com.example.gardenguru.domain.usecases.plant

import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.plant.PlantRepository
import javax.inject.Inject

class CreatePlantUseCase @Inject constructor(private val repository: PlantRepository) {

    suspend fun createPlant(gardenId: String, plantData: PlantData) =
        repository.createPlant(gardenId, plantData)
}