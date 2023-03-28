package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface DeletePlantDataSource {

    suspend fun deletePlant(idPlant: String): CloudResponse<Unit>

    class Base(private val firestorePlantsRef: CollectionReference) : DeletePlantDataSource {
        override suspend fun deletePlant(idPlant: String): CloudResponse<Unit> {
//            TODO()
            return CloudResponse.Success(Unit)
        }
    }
}