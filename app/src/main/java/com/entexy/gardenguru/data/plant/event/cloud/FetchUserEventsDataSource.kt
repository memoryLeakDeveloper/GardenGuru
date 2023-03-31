package com.entexy.gardenguru.data.plant.event.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.mapToData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FetchUserEventsDataSource {

    suspend fun fetchEvents(plantIds: List<String>): CloudResponse<List<EventData>>

    class Base @Inject constructor() : FetchUserEventsDataSource {
        override suspend fun fetchEvents(plantIds: List<String>): CloudResponse<List<EventData>> {
            val task = Firebase.firestore.collectionGroup("events").whereIn("plantId", plantIds).get()
            task.await()
            val result = mutableListOf<EventData>()
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