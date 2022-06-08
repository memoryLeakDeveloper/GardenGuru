package com.example.gardenguru.data.garden.cloud.delete

import com.example.gardenguru.core.Api
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface DeleteGardenService {

    @DELETE("${Api.apiVersion}/gardens/{id}/")
    suspend fun delete(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
    ): Response<Unit>
}