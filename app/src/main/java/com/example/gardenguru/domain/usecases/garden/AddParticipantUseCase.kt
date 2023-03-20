package com.example.gardenguru.domain.usecases.garden

import com.example.gardenguru.data.garden.GardenRepositoryImpl
import javax.inject.Inject

class AddParticipantUseCase @Inject constructor(private val repository: GardenRepositoryImpl) {

    suspend fun perform(email: String, gardenId: String): Boolean = repository.addParticipant(email, gardenId)

}