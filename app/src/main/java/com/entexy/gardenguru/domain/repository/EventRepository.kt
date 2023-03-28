package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.PlantEventData

interface EventRepository {

    suspend fun completeEvent(event: PlantEventData): CloudResponse<Unit>

    suspend fun fetchPlantEvents(): CloudResponse<List<PlantEventData>>

    suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>>

}