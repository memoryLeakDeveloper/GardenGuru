package com.entexy.gardenguru.data.plant.event

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.cloud.CompleteEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchEventsDataSource
import com.entexy.gardenguru.data.plant.event.cloud.FetchPlantEventsDataSource
import com.entexy.gardenguru.domain.repository.EventRepository

class EventRepositoryImpl(
    private val fetchPlantEventsDataSource: FetchPlantEventsDataSource,
    private val fetchEventsDataSource: FetchEventsDataSource,
    private val completeEventsDataSource: CompleteEventsDataSource
) : EventRepository {

    override suspend fun completeEvent(event: PlantEventData) =
        completeEventsDataSource.completeEvent(event)

    override suspend fun fetchPlantEvents(): CloudResponse<List<PlantEventData>> {
        return fetchPlantEventsDataSource.fetchEvents()
    }

    override suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>> {
        return fetchEventsDataSource.fetchEvents(plantId)
    }
}