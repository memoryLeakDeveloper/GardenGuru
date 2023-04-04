package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface UpdatePlantCustomPhotoDataSource {

    suspend fun updatePhoto(idPlant: String, newPhotoUrl: String?): CloudResponse<Unit>

    class Base(private val firestoreUserPlantsRef: CollectionReference) : UpdatePlantCustomPhotoDataSource {
        override suspend fun updatePhoto(idPlant: String, newPhotoUrl: String?): CloudResponse<Unit> {
            val task = firestoreUserPlantsRef.document(idPlant).update("customPhoto", newPhotoUrl)
            return if (task.exception == null)
                CloudResponse.Success(Unit)
            else CloudResponse.Error(task.exception)
        }
    }
}