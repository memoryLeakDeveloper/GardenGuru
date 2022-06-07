package com.example.gardenguru.domain.plant

import com.example.gardenguru.data.plant.PlantRepository
import javax.inject.Inject

class FetchPlantUseCase @Inject constructor(private val repository: PlantRepository) {
    suspend fun fetchPlant(idPlant: String) = repository.fetchPlant((idPlant))
}