package com.entexy.gardenguru.data.plant.event.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.PlantEventData

interface FetchPlantEventsDataSource {

    suspend fun fetchEvents(): CloudResponse<List<PlantEventData>>

    class Base : FetchPlantEventsDataSource {
        override suspend fun fetchEvents(): CloudResponse<List<PlantEventData>> {
            //val task = firestorePlantsRef.document(idPlant).get()
            //val result = task.await()
            //return if (task.exception == null) {
            //    result.toObject(PlantCloud::class.java)?.apply { id = idPlant }
            //} else throw task.exception!!
            return CloudResponse.Success(arrayListOf()) //todo
        }
    }
}