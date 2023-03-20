package com.entexy.gardenguru.data.garden.cloud.names

import com.entexy.gardenguru.core.Api
import com.entexy.gardenguru.data.garden.models.GardenName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GetGardenNamesService {
    @GET("${Api.apiVersion}/gardens/names/")
    fun fetch(@Header("Authorization") auth: String): Call<ArrayList<GardenName>>
}