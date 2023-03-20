package com.example.gardenguru.domain.usecases.plant

import com.example.gardenguru.data.plant.PlantRepositoryImpl
import javax.inject.Inject

class FetchPlantUseCase @Inject constructor(private val repository: PlantRepositoryImpl) {
    suspend fun fetchPlant(idPlant: String) = repository.fetchPlant((idPlant))

}