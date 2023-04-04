package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface DeletePlantDataSource {

    suspend fun deletePlant(idPlant: String): CloudResponse<Unit>

    class Base(private val firestoreUserPlantsRef: CollectionReference) : DeletePlantDataSource {
        override suspend fun deletePlant(idPlant: String): CloudResponse<Unit> {
            val task = firestoreUserPlantsRef.document(idPlant).delete()
            return if (task.exception == null)
                CloudResponse.Success(Unit)
            else CloudResponse.Error(task.exception)
        }
    }
}