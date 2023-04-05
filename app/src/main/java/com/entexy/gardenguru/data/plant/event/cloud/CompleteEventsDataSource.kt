package com.entexy.gardenguru.data.plant.event.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.data.plant.event.mapToCloud
import com.google.firebase.firestore.CollectionReference

interface CompleteEventsDataSource {

    suspend fun setEvent(event: EventData): CloudResponse<EventData?>

    suspend fun removeEvent(event: EventData): CloudResponse<Unit>

    class Base(private val firestoreUserPlantRef: CollectionReference) : CompleteEventsDataSource {
        override suspend fun setEvent(event: EventData): CloudResponse<EventData?> {
            val document = firestoreUserPlantRef.document(event.plantId).collection("events").document()
            val id = document.id
            document.set(event.mapToCloud())
            return CloudResponse.Success(event.apply { this.id = id })
        }

        override suspend fun removeEvent(event: EventData): CloudResponse<Unit> {
            firestoreUserPlantRef.document(event.plantId).collection("events").document(event.id!!).delete()
            return CloudResponse.Success(Unit)
        }
    }
}