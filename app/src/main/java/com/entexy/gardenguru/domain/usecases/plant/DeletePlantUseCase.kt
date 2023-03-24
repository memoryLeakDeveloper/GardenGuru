package com.entexy.gardenguru.domain.usecases.plant

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeletePlantUseCase @Inject constructor(private val repository: PlantRepository) {
    suspend fun perform(plantId: String): Flow<CloudResponse<Unit>> = flow {
        emit(CloudResponse.Loading())
        repository.deletePlant(plantId)
        emit(CloudResponse.Success(Unit))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}