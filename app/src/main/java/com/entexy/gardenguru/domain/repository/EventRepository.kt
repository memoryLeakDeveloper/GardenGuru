package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData

interface EventRepository {

    suspend fun completeEvent(event: EventData): CloudResponse<Unit>

    suspend fun fetchEvents(): CloudResponse<List<EventData>>

}