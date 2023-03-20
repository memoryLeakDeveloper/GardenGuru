package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class EditParticipantRoleUseCase @Inject constructor(private val repository: GardenRepository) {
    suspend fun perform(id: String,
                        role: String): Boolean =
        repository.changeParticipantRole(id, role)
}