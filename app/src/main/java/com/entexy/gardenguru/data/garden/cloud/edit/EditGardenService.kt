package com.entexy.gardenguru.data.garden.cloud.edit

import com.entexy.gardenguru.core.Api
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface EditGardenService {

    @Multipart
    @PATCH("${Api.apiVersion}/gardens/{id}/")
    fun fetch(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
        @Part name: MultipartBody.Part,
        @Part summerClimateType: MultipartBody.Part
    ): Call<Unit>
}