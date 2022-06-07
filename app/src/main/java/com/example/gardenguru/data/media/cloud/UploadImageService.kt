package com.example.gardenguru.data.media.cloud

import com.example.gardenguru.core.Api
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.plant.cloud.PhotoDataCloud
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UploadImageService {

    @Multipart
    @POST("${Api.apiVersion}/media/photo/")
    fun fetch(
        @Header("Authorization") auth: String,
        @Part type: MultipartBody.Part,
        @Part file: MultipartBody.Part
    ): Call<PhotoData>
}
