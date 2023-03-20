package com.entexy.gardenguru.data.media.cloud

import com.entexy.gardenguru.core.Api
import com.entexy.gardenguru.data.media.PhotoData
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
