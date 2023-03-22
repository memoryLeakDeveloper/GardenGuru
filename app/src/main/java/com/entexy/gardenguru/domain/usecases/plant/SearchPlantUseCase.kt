package com.entexy.gardenguru.domain.usecases.plant

import com.entexy.gardenguru.domain.repository.PlantRepository
import javax.inject.Inject

class SearchPlantUseCase @Inject constructor(private val repository: PlantRepository) {
    suspend fun searchPlant(plantRecognitionString: List<String>) = repository.searchPlant((plantRecognitionString))

}