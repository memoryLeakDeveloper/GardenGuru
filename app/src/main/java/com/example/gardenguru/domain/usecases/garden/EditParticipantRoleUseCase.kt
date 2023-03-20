package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class EditParticipantRoleUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {
    suspend fun perform(id: String, role: String) = repository.changeParticipantRole(id, role)

}