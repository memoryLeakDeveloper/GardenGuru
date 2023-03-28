package com.entexy.gardenguru.data.plant.event.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.ui.EventMockData

interface FetchEventsDataSource {

    suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>>

    class Base : FetchEventsDataSource {
        override suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>> {
            //val task = firestorePlantsRef.document(idPlant).get()
            //val result = task.await()
            //return if (task.exception == null) {
            //    result.toObject(PlantCloud::class.java)?.apply { id = idPlant }
            //} else throw task.exception!!
            return CloudResponse.Success(EventMockData.timetableListData) //todo
        }
    }
}