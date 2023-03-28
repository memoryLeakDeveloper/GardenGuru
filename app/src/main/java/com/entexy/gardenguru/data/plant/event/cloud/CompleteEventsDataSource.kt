package com.entexy.gardenguru.data.plant.event.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.PlantEventData

interface CompleteEventsDataSource {

    suspend fun completeEvent(event: PlantEventData): CloudResponse<Unit>

    class Base : CompleteEventsDataSource {
        override suspend fun completeEvent(event: PlantEventData): CloudResponse<Unit> {
//            TODO()
            return CloudResponse.Success(Unit)
        }
    }
}