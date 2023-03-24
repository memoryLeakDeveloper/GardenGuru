package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface RenamePlantDataSource {

    suspend fun renamePlant(idPlant: String, newName: String): CloudResponse<Boolean>

    class Base(private val firestorePlantsRef: CollectionReference) : RenamePlantDataSource {
        override suspend fun renamePlant(idPlant: String, newName: String): CloudResponse<Boolean> {
            TODO("Not yet implemented")
        }
    }
}