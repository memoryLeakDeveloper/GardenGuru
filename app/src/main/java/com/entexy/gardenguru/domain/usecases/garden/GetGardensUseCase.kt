package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.GardenRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGardensUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun getGardens() = flow {
        emit(CloudResponse.Loading())
        emit(CloudResponse.Success(repository.getGardens()))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}