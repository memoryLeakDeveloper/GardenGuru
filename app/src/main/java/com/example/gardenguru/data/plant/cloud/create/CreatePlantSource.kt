package com.example.gardenguru.data.plant.cloud.create

import com.example.gardenguru.core.exception.ErrorResponseCodeException

interface CreatePlantSource {

    suspend fun createPlant(token: String, plant: CreatePlantBody): Boolean

    class Base(private val service: CreatePlantService) : CreatePlantSource {
        override suspend fun createPlant(token: String, plant: CreatePlantBody): Boolean {
            val response = service.createPlant(token, plant).execute()
            if (response.code() == 201)
                return true
            throw ErrorResponseCodeException(response.code(), 201)
        }
    }
}