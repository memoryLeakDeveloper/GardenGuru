package com.example.gardenguru.data.plant.cloud.create

import com.example.gardenguru.core.Api
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CreatePlantService {

    @POST("${Api.apiVersion}/plants/create/")
    suspend fun createPlant(
        @Header("Authorization") auth: String,
        @Body plant: CreatePlantBody
    ): Call<Unit>
}