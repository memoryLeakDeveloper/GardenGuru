package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchEventsUseCase @Inject constructor(private val repository: EventRepository) {

    suspend fun perform(): Flow<CloudResponse<List<EventData>>> = flow {
        emit(CloudResponse.Loading())
        kotlinx.coroutines.delay(3000)
        emit(repository.fetchEvents())
    }.catch {
        emit(CloudResponse.Error(it))
    }
}