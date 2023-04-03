package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.mapToPlantCloud
import com.entexy.gardenguru.utils.bugger
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface AddPlantDataSource {

    suspend fun addPlant(data: PlantData): CloudResponse<Boolean>

    class Base(private val firestoreUserPlantRef: CollectionReference) : AddPlantDataSource {

        override suspend fun addPlant(data: PlantData): CloudResponse<Boolean> {
            val document = firestoreUserPlantRef.document()
            val id = document.id
            data.id = id
            val task = firestoreUserPlantRef.document(data.id).set(data.mapToPlantCloud()).addOnCompleteListener {
                bugger("SUCCESS")
            }.addOnFailureListener {
                bugger("FAILURE")
            }
            task.await()
            return if (task.exception == null)
                CloudResponse.Success(true)
            else CloudResponse.Error(task.exception)
        }

    }
}