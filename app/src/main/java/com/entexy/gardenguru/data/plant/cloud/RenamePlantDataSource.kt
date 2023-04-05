package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference

interface RenamePlantDataSource {

    suspend fun renamePlant(idPlant: String, newName: String): CloudResponse<Unit>

    class Base(private val firestoreUserPlantsRef: CollectionReference) : RenamePlantDataSource {
        override suspend fun renamePlant(idPlant: String, newName: String): CloudResponse<Unit> {
            val task = firestoreUserPlantsRef.document(idPlant).update("name", newName)
            return if (task.exception == null)
                CloudResponse.Success(Unit)
            else CloudResponse.Error(task.exception)
        }
    }
}