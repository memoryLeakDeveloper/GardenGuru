package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData

interface EventRepository {

    suspend fun updateEvent(event: EventData): CloudResponse<EventData?>

    suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>>
}