package com.example.gardenguru.data.garden.cloud.participants.delete

import com.example.gardenguru.core.Api
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface DeleteParticipantService {

    @DELETE("${Api.apiVersion}/gardens/participants/{id}/")
    suspend fun delete(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Response<Unit>
}