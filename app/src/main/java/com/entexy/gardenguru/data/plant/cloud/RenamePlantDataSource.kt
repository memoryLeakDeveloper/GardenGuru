package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface RenamePlantDataSource {

    suspend fun renamePlant(idPlant: String, newName: String): CloudResponse<Unit>

    class Base(private val firestorePlantsRef: CollectionReference) : RenamePlantDataSource {
        override suspend fun renamePlant(idPlant: String, newName: String): CloudResponse<Unit> {
            // TODO("Not yet implemented")
            return CloudResponse.Success(Unit)
        }
    }
}