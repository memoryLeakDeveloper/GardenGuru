package com.entexy.gardenguru.data.garden.cloud.delete

import com.entexy.gardenguru.core.Api
import retrofit2.Response
import retrofit2.http.*

interface DeleteGardenService {

    @DELETE("${Api.apiVersion}/gardens/{id}/")
    suspend fun delete(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
    ): Response<Unit>
}