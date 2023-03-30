package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface DeletePlantDataSource {

    suspend fun deletePlant(idPlant: String): CloudResponse<Unit>

    class Base(private val firestoreUserPlantsRef: CollectionReference) : DeletePlantDataSource {
        override suspend fun deletePlant(idPlant: String): CloudResponse<Unit> {
            val task = firestoreUserPlantsRef.document(idPlant).delete()
            task.await()
            return if (task.exception == null)
                CloudResponse.Success(Unit)
            else CloudResponse.Error(task.exception)
        }
    }
}