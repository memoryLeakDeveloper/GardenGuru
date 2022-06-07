package com.example.gardenguru.data.plant.cloud

import com.example.gardenguru.core.Api
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PlantService {
    @GET("${Api.apiVersion}/plants/{id}/")
    fun fetchPlant(
        @Header("Authorization") auth: String,
        @Header("Accept-Language") lang: String,
        @Path("id") idPlant: String
    ): Call<PlantCloud>
}