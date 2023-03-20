package com.entexy.gardenguru.data.garden.cloud.participants.add

import com.entexy.gardenguru.core.Api
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface AddParticipantService {

    @Multipart
    @POST("${Api.apiVersion}/gardens/participants/")
    suspend fun fetch(
        @Header("Authorization") auth: String,
        @Part email: MultipartBody.Part,
        @Part role: MultipartBody.Part,
        @Part garden: MultipartBody.Part
    ): Response<Unit>
}