package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface MovePlantDataSource {

    suspend fun movePlant(idPlant: String, idGarden: String): CloudResponse<Boolean>

    class Base(private val firestorePlantsRef: CollectionReference) : MovePlantDataSource {
        override suspend fun movePlant(idPlant: String, idGarden: String): CloudResponse<Boolean> {
            TODO()
        }
    }
}