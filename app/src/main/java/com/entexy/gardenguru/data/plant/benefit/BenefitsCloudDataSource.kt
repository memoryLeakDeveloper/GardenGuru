package com.entexy.gardenguru.data.plant.benefit

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.plant.benefit.cloud.BenefitCloud
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface BenefitsCloudDataSource {

    suspend fun fetchBenefits(idBenefits: String): CloudResponse<BenefitCloud?>

    class Base(private val firestoreBenefitsRef: CollectionReference) : BenefitsCloudDataSource {
        override suspend fun fetchBenefits(idBenefits: String): CloudResponse<BenefitCloud?> {
            val task = firestoreBenefitsRef.document(idBenefits).get()
            val result = task.await()
            return if (task.exception == null) {
                CloudResponse.Success(result.toObject(BenefitCloud::class.java)?.apply {
                    id = idBenefits
                })
            } else CloudResponse.Error(task.exception)
        }
    }
}