package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.PlantEventData
import com.entexy.gardenguru.domain.repository.EventRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompleteEventUseCase @Inject constructor(private val repository: EventRepository) {

    suspend fun perform(event: PlantEventData) = flow {
        emit(CloudResponse.Loading())
        repository.completeEvent(event)
        emit(CloudResponse.Success(Unit))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}