package com.entexy.gardenguru.data.support.cloud

import com.entexy.gardenguru.core.SupportApi
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SendMediaService {

    @Multipart
    @POST("${SupportApi.apiVersion}/media/")
    suspend fun sendMedia(
        @Part files: MultipartBody.Part,
    ): Response<SendMediaResponse>
}