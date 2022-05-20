package com.example.gardenguru.data.plant.cloud

import com.example.gardenguru.core.Api
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PlantService {
    @GET("${Api.apiVersion}/plants/{id}/")
    suspend fun fetchPlant(@Path("id") idPlant: String): Call<PlantCloud>
}