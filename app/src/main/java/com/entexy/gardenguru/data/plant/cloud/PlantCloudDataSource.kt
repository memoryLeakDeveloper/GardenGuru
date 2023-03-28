package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.mapToData
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface PlantCloudDataSource {

    suspend fun fetchPlant(idPlant: String): CloudResponse<PlantData>

    class Base(private val firestorePlantsRef: CollectionReference) : PlantCloudDataSource {
        override suspend fun fetchPlant(idPlant: String): CloudResponse<PlantData> {
            val task = firestorePlantsRef.document(idPlant).get()
            val result = task.await()
            return if (task.exception == null) {
                val plantCloud = result.toObject(PlantCloud::class.java)?.apply { id = idPlant }
                if (plantCloud != null)
                    CloudResponse.Success(plantCloud.mapToData())
                else CloudResponse.Error(null)
            } else CloudResponse.Error(task.exception!!)
        }
    }
}