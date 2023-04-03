package com.entexy.gardenguru.domain.usecases.events

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompleteEventUseCase @Inject constructor(private val repository: EventRepository) {

    suspend fun perform(event: EventData, eventToDelete: EventData?): Flow<CloudResponse<Pair<EventData?, EventData?>>> = flow {
        emit(CloudResponse.Loading())

        if (eventToDelete != null) {
            val deleteEvent = repository.updateEvent(eventToDelete.apply { isCompleted = false })
            if (deleteEvent is CloudResponse.Error) {
                emit(CloudResponse.Error((deleteEvent as? CloudResponse.Error)?.exception))
                return@flow
            }
        }

        val insertEvent = repository.updateEvent(event)
        if (insertEvent is CloudResponse.Success) {
            emit(CloudResponse.Success(insertEvent.result to eventToDelete))
        } else {
            emit(CloudResponse.Error((insertEvent as? CloudResponse.Error)?.exception))
        }
    }.catch {
        emit(CloudResponse.Error(it))
    }
}