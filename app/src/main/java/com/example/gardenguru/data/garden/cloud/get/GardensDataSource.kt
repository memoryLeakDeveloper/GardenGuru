package com.example.gardenguru.data.garden.cloud.get

import com.example.gardenguru.core.exception.ErrorResponseCodeException
import com.example.gardenguru.data.garden.cloud.GardenCloud
import javax.inject.Inject

interface GardensDataSource {

    suspend fun fetch(token: String, lang: String): ArrayList<GardenCloud>

    class Base @Inject constructor(private val service: GetGardensService) : GardensDataSource {
        override suspend fun fetch(token: String, lang: String): ArrayList<GardenCloud> {
            val response = service.fetch(token, lang).execute()
            if (response.code() == 200)
                return response.body()!!
            throw ErrorResponseCodeException(response.code(), 200)
        }
    }
}