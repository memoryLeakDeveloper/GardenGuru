package com.entexy.gardenguru.data.garden.cloud.get

import com.entexy.gardenguru.core.Api
import com.entexy.gardenguru.data.garden.cloud.GardenCloud
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GetGardensService {
    @GET("${Api.apiVersion}/gardens/")
    fun fetch(
        @Header("Authorization") auth: String,
        @Header("Accept-Language") lang: String
    ): Call<ArrayList<GardenCloud>>
}