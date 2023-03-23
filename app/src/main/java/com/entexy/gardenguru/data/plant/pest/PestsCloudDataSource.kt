package com.entexy.gardenguru.data.plant.pest

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.benefit.cloud.BenefitCloud
import com.entexy.gardenguru.data.plant.pest.cloud.PestCloud
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface PestsCloudDataSource {

    suspend fun fetchPests(idPests: String): CloudResponse<PestCloud?>

    class Base(private val firestorePestsRef: CollectionReference) : PestsCloudDataSource {
        override suspend fun fetchPests(idPests: String): CloudResponse<PestCloud?> {
            val task = firestorePestsRef.document(idPests).get()
            val result = task.await()
            return if (task.exception == null) {
                CloudResponse.Success(result.toObject(PestCloud::class.java)?.apply {
                    id = idPests
                })
            } else CloudResponse.Error(task.exception)
        }
    }
}