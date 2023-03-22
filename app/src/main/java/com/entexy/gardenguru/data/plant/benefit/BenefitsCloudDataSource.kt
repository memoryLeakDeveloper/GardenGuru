package com.entexy.gardenguru.data.plant.benefit

interface BenefitsCloudDataSource {

    suspend fun fetchBenefits(lang: String, idPlant: String): BenefitData

    class Base() : BenefitsCloudDataSource {
        override suspend fun fetchBenefits(lang: String, idPlant: String): BenefitData {
            TODO()
        }
    }
}