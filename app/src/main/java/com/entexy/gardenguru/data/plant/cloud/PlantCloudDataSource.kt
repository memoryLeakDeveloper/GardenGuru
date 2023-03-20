package com.entexy.gardenguru.data.plant.cloud

import android.util.Log
import com.entexy.gardenguru.core.exception.ErrorResponseCodeException

interface PlantCloudDataSource {

    suspend fun fetchPlant(token: String, lang: String, idPlant: String): PlantCloud

    class Base(private val service: PlantService) : PlantCloudDataSource {
        override suspend fun fetchPlant(token: String, lang: String, idPlant: String): PlantCloud {
            val response = service.fetchPlant(token, lang, idPlant)
            Log.d("bugger", "response = ${response.code()}")
            if (response.code() == 200)
                return response.body()!!
            throw ErrorResponseCodeException(response.code(), 200)
        }
    }
}