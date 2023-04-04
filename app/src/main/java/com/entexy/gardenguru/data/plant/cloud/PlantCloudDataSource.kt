package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.utils.bugger
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface PlantCloudDataSource {

    suspend fun fetchPlant(idPlant: String): CloudResponse<PlantCloud>

    suspend fun fetchPlants(): CloudResponse<List<PlantCloud>>

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

        override suspend fun fetchPlants(): CloudResponse<List<PlantCloud>> {
            val task = firestoreUserPlantRef.get()
            task.await()
            val result = arrayListOf<PlantCloud>()
            return if (task.exception == null) {
                task.result.forEach {
                    result.add(it.toObject(PlantCloud::class.java).apply { id = it.id })
                }
                CloudResponse.Success(result)
            } else CloudResponse.Error(task.exception!!)
        }
    }
}