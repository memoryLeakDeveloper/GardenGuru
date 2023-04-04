package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.search.PlantSearchCloud
import com.entexy.gardenguru.ui.PlantSearchMOCKData
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface SearchPlantDataSource {

    suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantSearchCloud>>

    suspend fun searchPlantByVarietyCode(varietyCode: String): CloudResponse<PlantSearchCloud>

    class Base(private val firestorePlantsRef: CollectionReference) : SearchPlantDataSource {

        override suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantSearchCloud>> {
            val task = firestorePlantsRef.whereEqualTo("name", plantName).get()
            val querySnapshot = task.await()
            val result = arrayListOf<PlantSearchCloud>()
            return if (task.exception == null) {
                querySnapshot.documents.forEach {
                    val plantCloud = it.toObject(PlantSearchCloud::class.java)
                    if (plantCloud != null)
                        result.add(plantCloud)
                }
                val list = PlantSearchMOCKData.plantsCloud
                CloudResponse.Success(list)
            } else CloudResponse.Error(task.exception)
        }

        override suspend fun searchPlantByVarietyCode(varietyCode: String): CloudResponse<PlantSearchCloud> {
            val task = firestorePlantsRef.whereEqualTo("varietyCode", varietyCode).limit(1).get()
            val querySnapshot = task.await()
            val list = PlantSearchMOCKData.plantsCloud.first()
            return CloudResponse.Success(list)
            return if (task.exception == null && querySnapshot.documents.isNotEmpty()) {
                querySnapshot.documents.map {
                    val plantCloud = it.toObject(PlantSearchCloud::class.java)
                    if (plantCloud != null) {
                        val list = PlantSearchMOCKData.plantsCloud
                        CloudResponse.Success(list)
                    }
                }
                CloudResponse.Error(null)
            } else CloudResponse.Error(task.exception)
        }
    }
}