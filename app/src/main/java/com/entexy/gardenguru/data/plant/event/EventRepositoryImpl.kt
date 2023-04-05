package com.entexy.gardenguru.data.plant.event

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.cloud.CompleteEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchEventsDataSource
import com.entexy.gardenguru.domain.repository.EventRepository

class EventRepositoryImpl(
    private val fetchEventsDataSource: FetchEventsDataSource,
    private val completeEventsDataSource: CompleteEventsDataSource,
) : EventRepository {

    override suspend fun updateEvent(event: EventData): CloudResponse<EventData?> {
        return if (event.isCompleted)
            completeEventsDataSource.setEvent(event)
        else {
            val removePlant = completeEventsDataSource.removeEvent(event)

            if (removePlant is CloudResponse.Success)
                CloudResponse.Success(null)
            else CloudResponse.Error((removePlant as? CloudResponse.Error)?.exception)
        }
    }

    override suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>> {
        return fetchEventsDataSource.fetchEvents(plantId)
    }
}

