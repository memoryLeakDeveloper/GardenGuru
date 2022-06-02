package com.example.gardenguru.data.garden.cloud

import com.example.gardenguru.core.Api
import com.example.gardenguru.core.exception.ErrorResponseCodeException
import javax.inject.Inject

interface GardenCloudDataSource {

    suspend fun fetch(token: String): ArrayList<GardenCloud>

    class Base @Inject constructor(private val service: GardenService) : GardenCloudDataSource {
        override suspend fun fetch(token: String): ArrayList<GardenCloud> {
            val response = service.fetch(token).execute()
            if (response.code() == 200)
                return response.body()!!
            throw ErrorResponseCodeException(response.code(), 200)
        }
    }
}