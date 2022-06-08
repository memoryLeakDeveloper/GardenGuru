package com.example.gardenguru.data.garden.cloud.participants.edit

import com.example.gardenguru.core.exception.ErrorResponseCodeException
import javax.inject.Inject

interface EditParticipantRoleSource {

    suspend fun editRole(token: String,
                     id: String,
                     role: String): Boolean

    class Base @Inject constructor(private val service: EditParticipantRoleService) : EditParticipantRoleSource {

        override suspend fun editRole(token: String, id: String, role: String): Boolean {

            val response = service.fetch(token, id, role)
            if (response.code() == 204)
                return true
            throw ErrorResponseCodeException(response.code(), 204)
        }
    }
}