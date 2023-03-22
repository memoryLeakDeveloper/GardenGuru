package com.entexy.gardenguru.data.plant.cloud

interface PlantCloudDataSource {

    suspend fun fetchPlant(token: String, lang: String, idPlant: String): PlantCloud

    class Base() : PlantCloudDataSource {
        override suspend fun fetchPlant(token: String, lang: String, idPlant: String): PlantCloud {
            TODO()
        }
    }
}