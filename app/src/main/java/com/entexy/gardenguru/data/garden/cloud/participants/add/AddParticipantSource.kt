package com.entexy.gardenguru.data.garden.cloud.participants.add

import com.entexy.gardenguru.core.exception.ErrorResponseCodeException
import com.entexy.gardenguru.data.garden.models.Participant
import okhttp3.MultipartBody
import javax.inject.Inject

interface AddParticipantSource {

    suspend fun addParticipant(token: String, email: String, gardenId: String): Boolean

    class Base @Inject constructor(private val service: AddParticipantService) : AddParticipantSource {
        override suspend fun addParticipant(token: String, email: String, gardenId: String): Boolean {
            val emailPart = MultipartBody.Part.createFormData("email", email)
            val rolePart = MultipartBody.Part.createFormData("role", Participant.RoleInGarden.Beginner.value)
            val gardenPart = MultipartBody.Part.createFormData("garden", gardenId)
            val response = service.fetch(token, emailPart, rolePart, gardenPart)
            if (response.isSuccessful)
                return true
            throw ErrorResponseCodeException(response.code(), 200)
        }
    }
}
