package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface UpdatePlantCustomPhotoDataSource {

    suspend fun renamePlant(idPlant: String, newPhotoUrl: String?): CloudResponse<Unit>

    class Base(private val firestoreUserPlantsRef: CollectionReference) : UpdatePlantCustomPhotoDataSource {
        override suspend fun renamePlant(idPlant: String, newPhotoUrl: String?): CloudResponse<Unit> {
            val task = firestoreUserPlantsRef.document(idPlant).update("customPhoto", newPhotoUrl)
            task.await()
            return if (task.exception == null)
                CloudResponse.Success(Unit)
            else CloudResponse.Error(task.exception)
        }
    }
}