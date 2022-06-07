package com.example.gardenguru.data.garden.cloud.create

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.garden.models.GardenName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CreateGardenService {

    @Multipart
    @POST("${Api.apiVersion}/gardens/")
    fun createGarden(
        @Header("Authorization") auth: String,
        @Header("Accept-Language") lang: String,
        @Part name: MultipartBody.Part
    ): Call<GardenName>
}
