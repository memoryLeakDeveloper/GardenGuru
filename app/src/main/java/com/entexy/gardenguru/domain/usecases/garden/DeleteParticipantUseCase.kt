package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.domain.repository.GardenRepository
import javax.inject.Inject

class DeleteParticipantUseCase @Inject constructor(private val repository: GardenRepository) {
    suspend fun perform(id: String): Boolean = repository.deleteParticipant(id)

}