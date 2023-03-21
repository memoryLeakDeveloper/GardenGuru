package com.entexy.gardenguru.data.plant.cloud.create

import com.entexy.gardenguru.core.Api
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CreatePlantService {

    @POST("${Api.apiVersion}/plants/create/")
    suspend fun createPlant(@Header("Authorization") auth: String, @Body plant: CreatePlantBody): Call<Unit>
}