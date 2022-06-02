package com.example.gardenguru.data.garden.cloud

import com.example.gardenguru.core.Api
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GardenService {
    @GET("${Api.apiVersion}/gardens/")
    fun fetch(@Header("Authorization") auth: String): Call<ArrayList<GardenCloud>>
}