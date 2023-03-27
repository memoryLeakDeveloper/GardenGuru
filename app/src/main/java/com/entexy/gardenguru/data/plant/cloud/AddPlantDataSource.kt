package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface AddPlantDataSource {

    suspend fun addPlant(idPlant: String): CloudResponse<Boolean>

    class Base(private val firestorePlantsRef: CollectionReference) : AddPlantDataSource {

        override suspend fun addPlant(idPlant: String): CloudResponse<Boolean> {
            //todo
            return CloudResponse.Success(true)
        }

    }
}