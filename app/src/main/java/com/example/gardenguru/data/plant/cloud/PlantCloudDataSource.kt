package com.example.gardenguru.data.plant.cloud

import com.example.gardenguru.core.exception.ErrorResponseCodeException

interface PlantCloudDataSource {

    suspend fun fetchPlant(token: String, lang: String, idPlant: String): PlantCloud

    class Base(private val service: PlantService) : PlantCloudDataSource {
        override suspend fun fetchPlant(token: String, lang: String, idPlant: String): PlantCloud {
            val response = service.fetchPlant(token, lang, idPlant).execute()
            if (response.code() == 200)
                return response.body()!!
            throw ErrorResponseCodeException(response.code(), 200)
        }
    }
}