package com.entexy.gardenguru.domain.usecases.plant

import com.entexy.gardenguru.domain.repository.PlantRepository
import javax.inject.Inject

class FetchPlantUseCase @Inject constructor(private val repository: PlantRepository) {

    suspend fun fetchPlant(idPlant: String) = repository.fetchPlant((idPlant))

}