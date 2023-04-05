package com.entexy.gardenguru.data.plant.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.mapToData
import com.entexy.gardenguru.ui.PlantMockData
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface SearchPlantDataSource {

    suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantData>>

    suspend fun searchPlantByVarietyCode(varietyCode: String): CloudResponse<PlantData>

    class Base(private val firestorePlantsRef: CollectionReference) : SearchPlantDataSource {

        override suspend fun searchPlantByName(plantName: String): CloudResponse<List<PlantData>> {
            val task = firestorePlantsRef.get()
            task.await()

            val result = arrayListOf<PlantData>()

            return if (task.exception == null) {
                val plantDocuments = task.result.documents.filter {
                    it["localizedVariety"].toString().contains(plantName, true)
                }

                plantDocuments.forEach {
                    val plant = it.toObject(PlantCloud::class.java)?.apply {
                        id = ""
                    }?.mapToData()

                    if (plant != null)
                        result.add(plant)
                }
                CloudResponse.Success(result)
            } else CloudResponse.Error(task.exception)
        }

        override suspend fun searchPlantByVarietyCode(varietyCode: String): CloudResponse<PlantData> {
//            val task = firestorePlantsRef.whereEqualTo("varietyCode", varietyCode).limit(1).get()
//            val querySnapshot = task.await()
//            return if (task.exception == null && querySnapshot.documents.isNotEmpty()) {
//                val plantCloud = querySnapshot.documents.first().toObject(PlantCloud::class.java)?.apply {
//                    id = ""
//                }
//                if (plantCloud != null){
//                    CloudResponse.Success(plantCloud.mapToData())
//                } else CloudResponse.Error(null)
//            } else CloudResponse.Error(task.exception)

            return CloudResponse.Success(PlantMockData.plantsData.first()) //todo
        }
    }
}