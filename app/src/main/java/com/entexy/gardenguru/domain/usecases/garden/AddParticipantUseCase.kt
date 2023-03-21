package com.entexy.gardenguru.domain.usecases.garden

import com.entexy.gardenguru.domain.repository.GardenRepository
import javax.inject.Inject

class AddParticipantUseCase @Inject constructor(private val repository: GardenRepository) {

    suspend fun perform(email: String, gardenId: String): Boolean = repository.addParticipant(email, gardenId)

}