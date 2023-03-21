package com.entexy.gardenguru.data.garden.cloud.delete

import com.entexy.gardenguru.core.exception.ErrorResponseCodeException
import javax.inject.Inject

interface DeleteGardenSource {

    suspend fun delete(token: String, id: String, ): Boolean

    class Base @Inject constructor(private val service: DeleteGardenService) : DeleteGardenSource {
        override suspend fun delete(
            token: String,
            id: String,
        ): Boolean {
            val response = service.delete(token, id)
            if (response.code() == 204)
                return true
            throw ErrorResponseCodeException(response.code(), 204)
        }
    }
}