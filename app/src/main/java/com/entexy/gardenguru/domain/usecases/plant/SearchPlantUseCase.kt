package com.entexy.gardenguru.domain.usecases.plant

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.PlantRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchPlantUseCase @Inject constructor(private val repository: PlantRepository) {
    suspend fun searchPlantByName(plantSearchQuires: String) = flow {
        emit(CloudResponse.Loading())
        emit(repository.searchPlantByName(plantSearchQuires))
    }.catch {
        emit(CloudResponse.Error(null))
    }

    suspend fun searchPlantByVarietyCode(plantSearchQuires: List<String>) = flow {
        emit(CloudResponse.Loading())
        emit(repository.searchPlantByVarietyCode(plantSearchQuires))
    }.catch {
        emit(CloudResponse.Error(null))
    }

}