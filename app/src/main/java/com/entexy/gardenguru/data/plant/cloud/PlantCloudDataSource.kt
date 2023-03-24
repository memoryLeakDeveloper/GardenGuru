package com.entexy.gardenguru.data.plant.cloud

import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface PlantCloudDataSource {

    suspend fun fetchPlant(idPlant: String): PlantCloud?

    class Base(private val firestorePlantsRef: CollectionReference) : PlantCloudDataSource {
        override suspend fun fetchPlant(idPlant: String): PlantCloud? {
            val task = firestorePlantsRef.document(idPlant).get()
            val result = task.await()
            return if (task.exception == null) {
                result.toObject(PlantCloud::class.java)?.apply { id = idPlant }
            } else throw task.exception!!
        }
    }
}