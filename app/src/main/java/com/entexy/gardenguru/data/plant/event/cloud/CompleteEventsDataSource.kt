package com.entexy.gardenguru.data.plant.event.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData

interface CompleteEventsDataSource {

    suspend fun completeEvent(event: EventData): CloudResponse<Unit>

    class Base : CompleteEventsDataSource {
        override suspend fun completeEvent(event: EventData): CloudResponse<Unit> {
//            TODO()
            return CloudResponse.Success(Unit)
        }
    }
}