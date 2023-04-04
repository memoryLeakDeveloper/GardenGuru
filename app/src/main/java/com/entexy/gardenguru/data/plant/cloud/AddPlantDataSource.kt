package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.mapToPlantCloud
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface AddPlantDataSource {

    suspend fun addPlant(data: PlantData): CloudResponse<Unit>

    class Base(private val firestoreUserPlantRef: CollectionReference) : AddPlantDataSource {

        override suspend fun addPlant(data: PlantData): CloudResponse<Unit> {
            val document = firestoreUserPlantRef.document()
            data.id = document.id
            val task = document.set(data.mapToPlantCloud())
            task.await()
            return if (task.exception == null)
                CloudResponse.Success(Unit)
            else CloudResponse.Error(task.exception)
        }

    }
}