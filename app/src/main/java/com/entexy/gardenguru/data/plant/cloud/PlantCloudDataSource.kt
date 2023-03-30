package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface PlantCloudDataSource {

    suspend fun fetchPlant(idPlant: String): CloudResponse<PlantCloud>

    class Base(private val firestoreUserPlantRef: CollectionReference) : PlantCloudDataSource {
        override suspend fun fetchPlant(idPlant: String): CloudResponse<PlantCloud> {
            val task = firestoreUserPlantRef.document(idPlant).get()
            val result = task.await()
            return if (task.exception == null) {
                val plantCloud = result.toObject(PlantCloud::class.java)?.apply { id = idPlant }
                if (plantCloud != null)
                    CloudResponse.Success(plantCloud)
                else CloudResponse.Error(null)
            } else CloudResponse.Error(task.exception!!)
        }
    }
}