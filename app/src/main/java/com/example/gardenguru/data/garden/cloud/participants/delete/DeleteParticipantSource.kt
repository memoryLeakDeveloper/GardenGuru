package com.example.gardenguru.data.garden.cloud.participants.delete

import com.example.gardenguru.core.exception.ErrorResponseCodeException
import javax.inject.Inject

interface DeleteParticipantSource {

    suspend fun deleteParticipant(
        token: String,
        participantId: String
    ): Boolean

    class Base @Inject constructor(private val service: DeleteParticipantService) : DeleteParticipantSource {
        override suspend fun deleteParticipant(token: String, participantId: String): Boolean {
            val response = service.delete(token, participantId)
            if (response.code() == 200)
                return true
            throw ErrorResponseCodeException(response.code(), 200)
        }
    }
}
