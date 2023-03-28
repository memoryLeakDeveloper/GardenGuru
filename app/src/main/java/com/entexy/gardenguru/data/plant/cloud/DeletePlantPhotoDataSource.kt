package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface DeletePlantPhotoDataSource {

    suspend fun deletePhoto(idPlant: String): CloudResponse<Unit>

    class Base(private val firestorePlantsRef: CollectionReference) : DeletePlantPhotoDataSource {
        override suspend fun deletePhoto(idPlant: String): CloudResponse<Unit> {
//            TODO()
            return CloudResponse.Success(Unit)
        }
    }
}