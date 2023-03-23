package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface PlantCloudDataSource {

    suspend fun fetchPlant(idPlant: String): CloudResponse<PlantCloud?>

    class Base(private val firestorePlantsRef: CollectionReference) : PlantCloudDataSource {
        override suspend fun fetchPlant(idPlant: String): CloudResponse<PlantCloud?> {
            val task = firestorePlantsRef.document(idPlant).get()
            val result = task.await()
            return if (task.exception == null){
                CloudResponse.Success(result.toObject(PlantCloud::class.java)?.apply {
                    id = idPlant
                })
            } else CloudResponse.Error(task.exception)
        }
    }
}