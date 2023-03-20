package com.example.gardenguru.data.garden.cloud.names

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.garden.models.GardenName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetGardenNamesService {
    @GET("${Api.apiVersion}/gardens/names/")
    suspend fun fetch(@Header("Authorization") auth: String): Response<ArrayList<GardenName>>
}