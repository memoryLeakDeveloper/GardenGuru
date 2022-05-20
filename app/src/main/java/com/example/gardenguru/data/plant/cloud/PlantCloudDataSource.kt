package com.example.gardenguru.data.plant.cloud

import com.example.gardenguru.core.exception.ErrorResponseCodeException

interface PlantCloudDataSource {

    suspend fun fetchPlant(idPlant: String): PlantCloud

    class Base(private val service: PlantService) : PlantCloudDataSource {
        override suspend fun fetchPlant(idPlant: String): PlantCloud {
            val response = service.fetchPlant(idPlant).execute()
            if (response.code() == 201)
                return response.body()!!
            throw ErrorResponseCodeException(response.code(), 201)
        }
    }
}