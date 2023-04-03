package com.entexy.gardenguru.data.plant.event

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.cloud.CompleteEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchPlantEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchUserEventsDataSource
import com.entexy.gardenguru.domain.repository.EventRepository

class EventRepositoryImpl(
    private val fetchPlantEventsDataSource: FetchPlantEventsDataSource,
    private val fetchEventsDataSource: FetchEventsDataSource,
    private val completeEventsDataSource: CompleteEventsDataSource,
    private val fetchUserEventsDataSource: FetchUserEventsDataSource,
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

    override suspend fun fetchPlantEvents(): CloudResponse<List<PlantEventData>> {
        return fetchPlantEventsDataSource.fetchEvents()
    }

    override suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>> {
        return fetchEventsDataSource.fetchEvents(plantId)
    }

    override suspend fun fetchUserEvents(plantIds: List<String>): CloudResponse<List<EventData>> {
        return fetchUserEventsDataSource.fetchEvents(plantIds)
    }
}

