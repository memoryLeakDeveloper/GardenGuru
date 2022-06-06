package com.example.gardenguru.data.garden.cloud.create

import com.example.gardenguru.core.exception.ErrorResponseCodeException
import com.example.gardenguru.data.garden.models.GardenName

interface CreateGardenSource {

    suspend fun createGarden(token: String, name: String, language: String): GardenName

    class Base(private val service: CreateGardenService) : CreateGardenSource {
        override suspend fun createGarden(token: String, name: String, language: String): GardenName {

            val response = service.createGarden(token, name, language).execute()
            if (response.code() == 201)
                return response.body()!!
            throw ErrorResponseCodeException(response.code(), 201)
        }
    }
}