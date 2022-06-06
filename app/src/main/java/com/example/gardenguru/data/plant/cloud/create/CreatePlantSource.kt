package com.example.gardenguru.data.plant.cloud.create

import com.example.gardenguru.core.exception.ErrorResponseCodeException
import okhttp3.MultipartBody

interface CreatePlantSource {

    suspend fun createPlant(token: String, garden: String, name: String, plant: CreatePlantBody): Boolean

    class Base(private val service: CreatePlantService) : CreatePlantSource {
        override suspend fun createPlant(token: String, garden: String, name: String, plant: CreatePlantBody): Boolean {

            val namePart = MultipartBody.Part.createFormData("name", name)
            val gardenPart = MultipartBody.Part.createFormData("garden", garden)

            val response = service.createPlant(token, gardenPart, namePart, plant).execute()
            if (response.code() == 201)
                return true
            throw ErrorResponseCodeException(response.code(), 201)
        }
    }
}