package com.example.gardenguru.domain.garden

import com.example.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class DeleteParticipantUseCase @Inject constructor(private val repository: GardenRepository) {
    suspend fun perform(id: String): Boolean =
        repository.deleteParticipant(id)
}