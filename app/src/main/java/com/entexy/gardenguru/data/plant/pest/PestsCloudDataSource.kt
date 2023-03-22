package com.entexy.gardenguru.data.plant.pest

interface PestsCloudDataSource {

    suspend fun fetchPests(lang: String, idPests: String): PestData

    class Base() : PestsCloudDataSource {
        override suspend fun fetchPests(lang: String, idPests: String): PestData {
            TODO()
        }
    }
}