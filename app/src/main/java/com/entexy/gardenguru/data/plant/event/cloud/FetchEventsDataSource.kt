package com.entexy.gardenguru.data.plant.event.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.mapToData
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface FetchEventsDataSource {

    suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>>

    class Base(private val firestorePlantsRef: CollectionReference) : FetchEventsDataSource {
        override suspend fun fetchEvents(plantId: String): CloudResponse<List<EventData>> {
            val task = firestorePlantsRef.document(plantId).collection("events").get()
            val result = mutableListOf<EventData>()
            task.await()
            return if (task.exception == null) {
                task.result.forEach {
                    val event = it.toObject(EventCloud::class.java).apply {
                        id = it.id
                    }.mapToData() ?: return@forEach

                    result.add(event)
                }
                CloudResponse.Success(result)
            } else CloudResponse.Error(task.exception)
        }
    }
}