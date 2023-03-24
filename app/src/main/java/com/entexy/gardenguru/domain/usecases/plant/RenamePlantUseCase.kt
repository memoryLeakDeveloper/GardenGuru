package com.entexy.gardenguru.domain.usecases.plant

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RenamePlantUseCase @Inject constructor(private val repository: PlantRepository) {
    suspend fun perform(plantId: String, plantName: String): Flow<CloudResponse<Unit>> = flow {
        emit(CloudResponse.Loading())
        repository.renamePlant(plantId, plantName)
        emit(CloudResponse.Success(Unit))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}