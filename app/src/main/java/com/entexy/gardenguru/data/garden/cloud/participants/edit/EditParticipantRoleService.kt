package com.entexy.gardenguru.data.garden.cloud.participants.edit

import com.entexy.gardenguru.core.Api
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