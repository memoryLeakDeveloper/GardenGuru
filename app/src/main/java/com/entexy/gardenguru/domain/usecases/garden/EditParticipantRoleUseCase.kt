package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class EditParticipantRoleUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {
    suspend fun perform(id: String, role: String) = repository.changeParticipantRole(id, role)

}