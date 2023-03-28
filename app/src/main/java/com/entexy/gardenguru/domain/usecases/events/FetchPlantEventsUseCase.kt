package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.PlantEventData
import com.entexy.gardenguru.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchPlantEventsUseCase @Inject constructor(private val repository: EventRepository) {

    suspend fun perform(): Flow<CloudResponse<List<PlantEventData>>> = flow {
        emit(CloudResponse.Loading())
        kotlinx.coroutines.delay(3000)
        emit(repository.fetchPlantEvents())
    }.catch {
        emit(CloudResponse.Error(it))
    }
}