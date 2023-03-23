package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface SearchPlantDataSource {

    suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantCloud>?>

    suspend fun searchPlantByVarietyCode(varietyCode: String): CloudResponse<PlantCloud?>

    class Base(private val firestorePlantsRef: CollectionReference) : SearchPlantDataSource {

        override suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantCloud>?> {
            val task = firestorePlantsRef.whereEqualTo("name", plantName).get()
            val querySnapshot = task.await()
            val result = arrayListOf<PlantCloud>()

            return if (task.exception == null) {
                querySnapshot.documents.forEach {
                    val plantCloud = it.toObject(PlantCloud::class.java)?.apply {
                        id = ""
                    }

                    if (plantCloud != null)
                        result.add(plantCloud)
                }
                CloudResponse.Success(result)
            } else CloudResponse.Error(task.exception)
        }

        override suspend fun searchPlantByVarietyCode(varietyCode: String): CloudResponse<PlantCloud?> {
            val task = firestorePlantsRef.whereEqualTo("varietyCode", varietyCode).limit(1).get()
            val querySnapshot = task.await()

            return if (task.exception == null) {
                val plantCloud = querySnapshot.documents.first().toObject(PlantCloud::class.java)?.apply {
                    id = ""
                }
                CloudResponse.Success(plantCloud)
            } else CloudResponse.Error(task.exception)
        }
    }
}