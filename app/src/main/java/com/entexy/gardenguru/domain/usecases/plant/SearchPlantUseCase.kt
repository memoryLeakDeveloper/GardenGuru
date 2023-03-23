package com.entexy.gardenguru.domain.usecases.plant

import com.entexy.gardenguru.domain.repository.PlantRepository
import javax.inject.Inject

class SearchPlantUseCase @Inject constructor(private val repository: PlantRepository) {
    suspend fun searchPlantByName(plantSearchQuires: List<String>) = repository.searchPlantByVarietyCode(plantSearchQuires)

    suspend fun searchPlantByVarietyCode(plantSearchQuires: List<String>) = repository.searchPlantByVarietyCode(plantSearchQuires)

}