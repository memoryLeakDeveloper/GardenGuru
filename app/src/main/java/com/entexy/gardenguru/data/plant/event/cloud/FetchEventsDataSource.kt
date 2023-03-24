package com.entexy.gardenguru.data.plant.event.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData

interface FetchEventsDataSource {

    suspend fun fetchEvents(): CloudResponse<List<EventData?>>

    class Base : FetchEventsDataSource {
        override suspend fun fetchEvents(): CloudResponse<List<EventData?>> {
            TODO()
        }
    }
}