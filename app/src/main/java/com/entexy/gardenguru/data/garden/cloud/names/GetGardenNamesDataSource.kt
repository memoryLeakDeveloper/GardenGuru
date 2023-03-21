package com.entexy.gardenguru.data.garden.cloud.names

import com.entexy.gardenguru.core.exception.ErrorResponseCodeException
import com.entexy.gardenguru.data.garden.models.GardenName
import javax.inject.Inject

interface GetGardenNamesDataSource {

    suspend fun fetch(token: String): ArrayList<GardenName>

    class Base @Inject constructor(private val service: GetGardenNamesService) : GetGardenNamesDataSource {
        override suspend fun fetch(token: String): ArrayList<GardenName> {
            val response = service.fetch(token)
            if (response.isSuccessful)
                return response.body()!!
            throw ErrorResponseCodeException(response.code(), 200)
        }
    }
}