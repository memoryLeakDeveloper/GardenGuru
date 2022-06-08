package com.example.gardenguru.data.garden.cloud.participants.edit

import com.example.gardenguru.core.Api
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface EditParticipantRoleService {
    @PATCH("${Api.apiVersion}/gardens/participants/{id}/")
    suspend fun fetch(
        @Header("Authorization") auth: String,
        @Path("id") id: String,
        @Body role: String,
    ): Response<Unit>
}